package com.dzone.albanoj2.zeal.rest.error;

/**
 * Exception denoting that a request contains invalid data.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class InvalidRequestDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidRequestDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRequestDataException(String message) {
		super(message);
	}

	public InvalidRequestDataException(Throwable cause) {
		super(cause);
	}
}
