package com.dzone.albanoj2.zeal.rest.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.app.service.error.EntityNotFoundException;
import com.dzone.albanoj2.zeal.rest.resource.ErrorResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

/**
 * Exception handler responsible for handling exceptions related to entities.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@ControllerAdvice
public class EntityExceptionHandler {

	private final TimeUtility timeUtility;

	@Autowired
	public EntityExceptionHandler(TimeUtility timeUtility) {
		this.timeUtility = timeUtility;
	}

	@ExceptionHandler({ 
		ArticleNotFoundException.class,
		CommentNotFoundException.class
	})
	public ResponseEntity<ErrorResource> handleNotFound(EntityNotFoundException ex,
		WebRequest request) {

		ErrorResource resource = new ErrorResource();

		resource.setTimestamp(timeUtility.currentTimestamp());
		resource.setMessage("Could not find entity");
		resource.setDetail("Entity with ID " + ex.getId() + " could not be found.");

		return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
	}
}
