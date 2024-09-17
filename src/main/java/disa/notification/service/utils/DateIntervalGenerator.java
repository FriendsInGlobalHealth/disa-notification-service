package disa.notification.service.utils.date;

import disa.notification.service.utils.DateInterval;

@FunctionalInterface
public interface DateIntervalGenerator {
    DateInterval generateDateInterval();
}
