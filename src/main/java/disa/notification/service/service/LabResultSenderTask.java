package disa.notification.service.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import disa.notification.service.entity.ImplementingPartner;
import disa.notification.service.repository.ImplementingPartnerRepository;
import disa.notification.service.repository.ViralLoaderRepository;
import disa.notification.service.service.interfaces.LabResultSummary;
import disa.notification.service.service.interfaces.LabResults;
import disa.notification.service.service.interfaces.MailService;
import disa.notification.service.service.interfaces.PendingHealthFacilitySummary;
import disa.notification.service.utils.DateInterval;
import disa.notification.service.utils.DateIntervalGenerator;
import disa.notification.service.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LabResultSenderTask {
    private static final Logger log = LoggerFactory.getLogger(LabResultSenderTask.class);

    private final Environment env;
    private final ImplementingPartnerRepository ipRepository;
    private final MailService mailService;
    private final ViralLoaderRepository viralLoaderRepository;

    @Scheduled(cron = "${task.cron}")
    public void sendLabResultReport() {
        DateInterval reportDateInterval = getDateIntervalGenerator().generateDateInterval();
        log.info("Starting weekly report task");
        log.info("Report date interval {}", reportDateInterval);

        // Custom query method that returns all implementing entities where the enabled
        // field is true,
        // and it uses the @EntityGraph annotation to ensure that related entities are
        // loaded along with
        // the query results
        List<ImplementingPartner> implementingPartners = ipRepository
                .findByEnabledTrueAndRepoLinkIsNotNullAndRepoIdIsNotNull();

        for (ImplementingPartner implementingPartner : implementingPartners) {
            log.info("Generating report for {}", implementingPartner.getOrgName());
            sendEmailForImplementingPartner(implementingPartner, reportDateInterval);
        }
    }

    private DateIntervalGenerator getDateIntervalGenerator() {
        DateIntervalGenerator generator = () -> DateTimeUtils.getLastWeekInterVal();
        String reportDateIntervalProp = this.env.getProperty("app.reportDateInterval");
        if (reportDateIntervalProp != null && reportDateIntervalProp.equals("custom")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(this.env.getProperty("app.startDate"), formatter);
            LocalDate endDate = LocalDate.parse(this.env.getProperty("app.endDate"), formatter);
            generator = () -> DateInterval.of(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        }
        return generator;
    }

    private void sendEmailForImplementingPartner(ImplementingPartner implementingPartner,
            DateInterval reportDateInterval) {
        LocalDateTime startDateTime = reportDateInterval.getStartDateTime();
        LocalDateTime endDateTime = reportDateInterval.getEndDateTime();
        Set<String> orgUnitCodes = implementingPartner.getOrgUnitCodes();
        List<LabResultSummary> labResultSummary = viralLoaderRepository.findViralLoadResultSummary(
                startDateTime,
                endDateTime,
                orgUnitCodes);
        List<LabResults> labResults = viralLoaderRepository.findViralLoadResults(
                startDateTime,
                endDateTime,
                orgUnitCodes);
        List<LabResults> pendingResultsForMoreThan2Days = viralLoaderRepository
                .findViralLoadResultsPendingMoreThan2Days(orgUnitCodes);
        List<PendingHealthFacilitySummary> pendingHealthFacilitySummaries = viralLoaderRepository
                .findUnsincronizedHealthFacilities(orgUnitCodes);

        try {
            if (!labResultSummary.isEmpty() || !pendingResultsForMoreThan2Days.isEmpty()) {
                mailService.sendEmail(implementingPartner, reportDateInterval, labResultSummary, labResults,
                        pendingResultsForMoreThan2Days,
                        pendingHealthFacilitySummaries);
            } else {
                mailService.sendNoResultsEmail(implementingPartner, reportDateInterval);
            }
        } catch (IOException | MessagingException e) {
            log.error("Erro ao enviar relatório de Cargas virais", e);
        }
    }
}
