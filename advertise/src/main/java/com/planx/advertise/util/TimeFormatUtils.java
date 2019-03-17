package com.planx.advertise.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeFormatUtils {

	public static String format(Long time, String timeFormat) {
		ZoneId zoneId = ZoneId.systemDefault();
		return format(time, zoneId, timeFormat);
	}
	
	public static String format(Long time, String timeZone, String timeFormat) {
		ZoneId zoneId = ZoneId.of(timeZone);
		return format(time, zoneId, timeFormat);
	}
	
	public static String format(Long time, ZoneId zoneId, String timeFormat) {
		Instant instant = Instant.ofEpochMilli(time);
		return LocalDateTime.ofInstant(instant, zoneId)
				.format(DateTimeFormatter.ofPattern(timeFormat));
	}
	
}
