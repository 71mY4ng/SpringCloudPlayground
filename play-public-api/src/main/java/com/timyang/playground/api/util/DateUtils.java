package com.timyang.playground.api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    public static LocalDateTime toLocalDateTime(Date in) {
        return LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
    }

    public static Date toLegacyDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
