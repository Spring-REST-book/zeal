package com.dzone.albanoj2.zeal.app.service.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommentNotFoundExceptionTest {

	@Test
	public void givenValidId_whenConstruct_thenGetterReturnedCorrectValue() {
		
		String id = "foo";
		
		CommentNotFoundException exception = new CommentNotFoundException(id);
		
		assertEquals(id, exception.getId());
	}

	@Test
	public void givenValidId_whenConstructWithCause_thenGetterReturnedCorrectValue() {
		
		String id = "foo";
		RuntimeException cause = new RuntimeException();
		
		CommentNotFoundException exception = new CommentNotFoundException(id, cause);
		
		assertEquals(id, exception.getId());
	}
}
