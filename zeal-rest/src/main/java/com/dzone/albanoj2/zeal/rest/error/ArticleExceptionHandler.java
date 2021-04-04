package com.dzone.albanoj2.zeal.rest.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.rest.resource.ErrorResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

/**
 * Exception handler responsible for handling exceptions related to articles.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@ControllerAdvice
public class ArticleExceptionHandler {

	private final TimeUtility timeUtility;

	@Autowired
	public ArticleExceptionHandler(TimeUtility timeUtility) {
		this.timeUtility = timeUtility;
	}

	@ExceptionHandler({ ArticleNotFoundException.class })
	public ResponseEntity<ErrorResource> handleNotFound(ArticleNotFoundException ex,
		WebRequest request) {

		ErrorResource resource = new ErrorResource();

		resource.setTimestamp(timeUtility.currentTimestamp());
		resource.setMessage("Could not find article");
		resource.setDetail("Article with ID " + ex.getId() + " could not be found.");

		return new ResponseEntity<>(resource, HttpStatus.NOT_FOUND);
	}
}
