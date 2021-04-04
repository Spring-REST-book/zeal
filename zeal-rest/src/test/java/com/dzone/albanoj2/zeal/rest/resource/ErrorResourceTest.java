package com.dzone.albanoj2.zeal.rest.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ErrorResourceTest {

	@Test
	public void givenValidData_whenSet_thenGetReturnsCorrectData() {
		
		String timestamp = "foo";
		String message = "bar";
		String detail = "baz";
		
		ErrorResource resource = new ErrorResource();
		
		resource.setTimestamp(timestamp);
		resource.setMessage(message);
		resource.setDetail(detail);
		
		assertEquals(timestamp, resource.getTimestamp());
		assertEquals(message, resource.getMessage());
		assertEquals(detail, resource.getDetail());
	}
}
