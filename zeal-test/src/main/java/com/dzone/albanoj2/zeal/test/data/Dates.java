package com.dzone.albanoj2.zeal.test.data;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Dates {

	public static ZonedDateTime arbitrary() {
		return LocalDateTime.of(2021, Month.MARCH, 26, 17, 41, 00)
		    .atZone(ZoneOffset.UTC);
	}
}
