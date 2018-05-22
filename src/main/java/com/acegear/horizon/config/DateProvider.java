package com.acegear.horizon.config;

import org.springframework.data.auditing.DateTimeProvider;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by mercury on 2016/12/26.
 */
public class DateProvider implements DateTimeProvider {
    @Override
    public Calendar getNow() {
        return new GregorianCalendar();
    }
}
