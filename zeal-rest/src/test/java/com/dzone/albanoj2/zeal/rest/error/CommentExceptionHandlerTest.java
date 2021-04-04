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

import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.rest.resource.ErrorResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

public class CommentExceptionHandlerTest {

	private TimeUtility timeUtility;
	private CommentExceptionHandler handler;
	
	@BeforeEach
	public void setUp() {
		
		timeUtility = mock(TimeUtility.class);
		handler = new CommentExceptionHandler(timeUtility);
	}
	
	@Test
	public void givenValidException_whenHandleNotFound_thenCorrectResourceReturned() {
		
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

	private void givenCurrentTimestampIs(String timestamp) {
		doReturn(timestamp).when(timeUtility).currentTimestamp();
	}

	private static void assertPopulated(String detail) {
		assertTrue(detail != null && !detail.isBlank());
	}
}
