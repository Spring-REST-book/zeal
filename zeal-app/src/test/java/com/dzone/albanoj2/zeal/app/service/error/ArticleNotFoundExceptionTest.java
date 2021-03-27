package com.dzone.albanoj2.zeal.app.service.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArticleNotFoundExceptionTest {

	@Test
	public void givenValidId_whenConstruct_thenGetterReturnedCorrectValue() {
		
		String id = "foo";
		
		ArticleNotFoundException exception = new ArticleNotFoundException(id);
		
		assertEquals(id, exception.getId());
	}

	@Test
	public void givenValidId_whenConstructWithCause_thenGetterReturnedCorrectValue() {
		
		String id = "foo";
		RuntimeException cause = new RuntimeException();
		
		ArticleNotFoundException exception = new ArticleNotFoundException(id, cause);
		
		assertEquals(id, exception.getId());
	}
}
