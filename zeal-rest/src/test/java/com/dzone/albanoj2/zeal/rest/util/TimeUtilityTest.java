package com.dzone.albanoj2.zeal.rest.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeUtilityTest {

	private TimeUtility timeUtility;
	
	@BeforeEach
	public void setUp() {
		timeUtility = new TimeUtility();
	}
	
	@Test
	public void givenPositiveEpoch_whenToUtcDateTime_thenCorrectDateTimeReturned() {
		
		long epoch = 100;
		
		ZonedDateTime dateTime = timeUtility.toUtcDateTime(epoch);
		
		assertEquals(ZoneOffset.UTC, dateTime.getZone());
		assertEquals(epoch, dateTime.toInstant().toEpochMilli());
	}
	
	@Test
	public void givenZeroEpoch_whenToUtcDateTime_thenCorrectDateTimeReturned() {
		
		long epoch = 0;
		
		ZonedDateTime dateTime = timeUtility.toUtcDateTime(epoch);
		
		assertEquals(ZoneOffset.UTC, dateTime.getZone());
		assertEquals(epoch, dateTime.toInstant().toEpochMilli());
	}
	
	@Test
	public void givenNegativeEpoch_whenToUtcDateTime_thenCorrectDateTimeReturned() {
		
		long epoch = -100;
		
		ZonedDateTime dateTime = timeUtility.toUtcDateTime(epoch);
		
		assertEquals(ZoneOffset.UTC, dateTime.getZone());
		assertEquals(epoch, dateTime.toInstant().toEpochMilli());
	}
	
	@Test
	public void whenNow_thenDateTimeWithinAcceptableBounds() {
		
		ZonedDateTime dateTime = timeUtility.now();
		
		long dateTimeEpoch = dateTime.toInstant().toEpochMilli();
		
		assertEquals(ZoneOffset.UTC, dateTime.getZone());
		assertWithinSeconds(dateTimeEpoch, 5);
	}

	private static void assertWithinSeconds(long dateTimeEpoch, int seconds) {
		
		long nowEpoch = System.currentTimeMillis();
		
		assertThat(dateTimeEpoch, is(greaterThan(nowEpoch - (seconds * 500))));
		assertThat(dateTimeEpoch, is(lessThan(nowEpoch + (seconds * 500))));
	}
	
	@Test
	public void whenCurrentTimestamp_thenTimestampWithinAcceptableBounds() {
		
		String timestamp = timeUtility.currentTimestamp();
		ZonedDateTime parsedTimestamp = ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
		
		long dateTimeEpoch = parsedTimestamp.toInstant().toEpochMilli();
		
		assertEquals(ZoneOffset.UTC, parsedTimestamp.getZone());
		assertWithinSeconds(dateTimeEpoch, 5);
	}
}
