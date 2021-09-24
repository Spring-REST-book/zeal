package com.dzone.albanoj2.zeal.app.service.error;

/**
 * An exception denoting that a desired comment could not be found.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class CommentNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new exception denoting a comment with the supplied ID could not
	 * be found.
	 * 
	 * @param id
	 *        The ID of the comment that could not be found.
	 */
	public CommentNotFoundException(String id) {
		super(id);
	}

	/**
	 * Creates a new exception with a cause denoting a comment with the supplied
	 * ID could not be found.
	 * 
	 * @param id
	 *        The ID of the comment that could not be found.
	 * @param cause
	 *        The cause of the exception.
	 */
	public CommentNotFoundException(String id, Throwable cause) {
		super(id, cause);
	}
}
