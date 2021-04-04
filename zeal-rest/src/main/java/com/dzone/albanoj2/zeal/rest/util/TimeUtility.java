package com.dzone.albanoj2.zeal.rest.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

/**
 * Utility class responsible for common time conversions and formatting.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Component
public class TimeUtility {

	/**
	 * Converts the supplied Epoch into a {@link ZonedDateTime} object.
	 * 
	 * @param epoch
	 *        The Epoch, in milliseconds, to convert.
	 * 
	 * @return
	 *         The converted Epoch.
	 */
	public ZonedDateTime toUtcDateTime(long epoch) {
		Instant i = Instant.ofEpochMilli(epoch);
		return ZonedDateTime.ofInstant(i, ZoneOffset.UTC);
	}

	/**
	 * Obtains the current zoned date time.
	 * 
	 * @return
	 *         The current zoned date time.
	 */
	public ZonedDateTime now() {
		return ZonedDateTime.now(ZoneOffset.UTC);
	}

	/**
	 * Obtains a formatted string of the current UTC time using the ISO-8601
	 * formatting standard.
	 * 
	 * @return
	 *         An ISO-8601 formatted string representing the current time.
	 */
	public String currentTimestamp() {
		return now().format(DateTimeFormatter.ISO_INSTANT);
	}
}
