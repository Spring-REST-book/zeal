package com.dzone.albanoj2.zeal.test.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

public class Matchers {

	private static final String ANY_NON_NULL = "?";
	
	public static String anyNonNull() {
		return ANY_NON_NULL;
	}

	public static boolean idMatches(String expected, String actual) {
		
		if (anyNonNull().equals(expected)) {
			return actual != null;
		}
		else {
			return Objects.equals(expected, actual);
		}
	}
	
	public static <T> void assertMatches(List<T> expectedList, List<T> retrievedList, MatchingStrategy<T> strategy) {
		
		assertEquals(expectedList.size(), retrievedList.size());
		
		for (T expected: expectedList) {
			assertTrue(Matchers.isIn(retrievedList, expected, strategy));
		}
	}
	
	public static <T> boolean isIn(List<T> list, T expected, MatchingStrategy<T> strategy) {
		return list.stream()
			.anyMatch(actual -> strategy.matches(expected, actual));
	}
	
	@FunctionalInterface
	public static interface MatchingStrategy<T> {
		public boolean matches(T a, T b);
	}
}
