package com.dzone.albanoj2.zeal.rest.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.rest.resource.ErrorResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

/**
 * Exception handler responsible for handling exceptions related to comments.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@ControllerAdvice
public class CommentExceptionHandler {

	private final TimeUtility timeUtility;

	@Autowired
	public CommentExceptionHandler(TimeUtility timeUtility) {
		this.timeUtility = timeUtility;
	}

	@ExceptionHandler({
		CommentNotFoundException.class
	})
	public ResponseEntity<ErrorResource> handleNotFound(CommentNotFoundException ex,
		WebRequest request) {

		ErrorResource resource = new ErrorResource();

		resource.setTimestamp(timeUtility.currentTimestamp());
		resource.setMessage("Could not find comment");
		resource.setDetail("Comment with ID " + ex.getId() + " could not be found.");

		return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
	}
}
