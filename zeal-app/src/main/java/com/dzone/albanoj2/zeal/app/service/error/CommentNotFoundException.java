package com.dzone.albanoj2.zeal.app.service.error;

/**
 * An exception denoting that a desired comment could not be found.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class CommentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9128144832142207496L;
	private final String id;

	/**
	 * Creates a new exception denoting a comment with the supplied ID could not
	 * be found.
	 * 
	 * @param id
	 *        The ID of the comment that could not be found.
	 */
	public CommentNotFoundException(String id) {
		super(message(id));
		this.id = id;
	}

	private static String message(String id) {
		return "Comment with ID " + id + " not found.";
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
		super(message(id), cause);
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
