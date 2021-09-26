package com.dzone.albanoj2.zeal.rest.error;

import org.junit.jupiter.api.Test;

public class InvalidRequestDataExceptionTest {

	@Test
	public void givenValidCause_whenConstruct_thenExceptionCreated() {
		RuntimeException cause = new RuntimeException();
		new InvalidRequestDataException(cause);
	}
	
	@Test
	public void givenNullCause_whenConstruct_thenExceptionCreated() {
		new InvalidRequestDataException(null);
	}
}
