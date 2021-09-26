package com.dzone.albanoj2.zeal.rest.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.rest.resource.ErrorResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

public class EntityExceptionHandlerTest {

	private TimeUtility timeUtility;
	private EntityExceptionHandler handler;
	
	@BeforeEach
	public void setUp() {
		
		timeUtility = mock(TimeUtility.class);
		handler = new EntityExceptionHandler(timeUtility);
	}
	
	@Test
	public void givenValidArticleNotFoundException_whenHandleNotFound_thenCorrectResourceReturned() {
		
		String id = "123";
		String timestamp = "2021-04-01T19:40:26+0000";
		ArticleNotFoundException ex = new ArticleNotFoundException(id);
		WebRequest request = mock(WebRequest.class);
		
		givenCurrentTimestampIs(timestamp);
		
		ResponseEntity<ErrorResource> error = handler.handleNotFound(ex, request);
		ErrorResource errorResource = error.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());
		assertEquals(timestamp, errorResource.getTimestamp());
		assertPopulated(errorResource.getMessage());
		assertPopulated(errorResource.getDetail());
	}

	private void givenCurrentTimestampIs(String timestamp) {
		doReturn(timestamp).when(timeUtility).currentTimestamp();
	}

	private static void assertPopulated(String detail) {
		assertTrue(detail != null && !detail.isBlank());
	}

	@Test
	public void givenValidCommentNotFoundException_whenHandleNotFound_thenCorrectResourceReturned() {
		
		String id = "123";
		String timestamp = "2021-04-01T19:40:26+0000";
		CommentNotFoundException ex = new CommentNotFoundException(id);
		WebRequest request = mock(WebRequest.class);
		
		givenCurrentTimestampIs(timestamp);
		
		ResponseEntity<ErrorResource> error = handler.handleNotFound(ex, request);
		ErrorResource errorResource = error.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());
		assertEquals(timestamp, errorResource.getTimestamp());
		assertPopulated(errorResource.getMessage());
		assertPopulated(errorResource.getDetail());
	}
	
	@Test
	public void givenValidInvalidRequestDataException_whenHandleInvalidRequestData_thenCorrectResourceReturned() {

		String message = "some message";
		String timestamp = "2021-04-01T19:40:26+0000";
		NullPointerException npe = new NullPointerException(message);
		InvalidRequestDataException ex = new InvalidRequestDataException(npe);
		WebRequest request = mock(WebRequest.class);
		
		givenCurrentTimestampIs(timestamp);
		
		ResponseEntity<ErrorResource> error = handler.handleInvalidRequestData(ex, request);
		ErrorResource errorResource = error.getBody();
		
		assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
		assertEquals(timestamp, errorResource.getTimestamp());
		assertPopulated(errorResource.getMessage());
		assertEquals(message, errorResource.getDetail());
	}
}
