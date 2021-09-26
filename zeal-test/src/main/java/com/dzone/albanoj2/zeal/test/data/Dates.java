package com.dzone.albanoj2.zeal.test.data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Dates {

	public static ZonedDateTime arbitrary() {
		return arbitraryWithDay(26);
	}

	public static ZonedDateTime arbitraryWithDay(int day) {
		return LocalDateTime.of(2021, Month.MARCH, day, 17, 41, 00)
		    .atZone(ZoneOffset.UTC);
	}
	
	public static ZonedDateTime fromEpochMillis(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
	}
}
