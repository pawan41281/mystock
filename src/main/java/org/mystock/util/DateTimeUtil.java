package org.mystock.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtil {

	public LocalDate toLocalDate(Long millis) {
		if (millis == null)
			return null;
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
