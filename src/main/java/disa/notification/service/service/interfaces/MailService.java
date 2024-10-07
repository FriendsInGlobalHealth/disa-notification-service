package disa.notification.service.service.interfaces;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import disa.notification.service.entity.ImplementingPartner;
import disa.notification.service.utils.DateInterval;

public interface MailService {

    void sendEmail(ImplementingPartner ip, DateInterval dateInterval, final List<LabResultSummary> viralLoaders,
            List<LabResults> viralLoadResults, List<LabResults> unsyncronizedViralLoadResults,
            List<PendingHealthFacilitySummary> pendingHealthFacilitySummaries)
            throws MessagingException, UnsupportedEncodingException;

    void sendNoResultsEmail(ImplementingPartner ip, DateInterval dateInterval) throws MessagingException, UnsupportedEncodingException;
}
