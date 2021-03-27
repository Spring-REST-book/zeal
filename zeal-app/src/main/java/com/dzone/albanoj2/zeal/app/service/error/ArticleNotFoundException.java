package com.dzone.albanoj2.zeal.app.service.error;

/**
 * An exception denoting that a desired article could not be found.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class ArticleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9128144832142207496L;
	private final String id;

	/**
	 * Creates a new exception denoting an article with the supplied ID could
	 * not be found.
	 * 
	 * @param id
	 *        The ID of the article that could not be found.
	 */
	public ArticleNotFoundException(String id) {
		super(message(id));
		this.id = id;
	}

	private static String message(String id) {
		return "Article with ID " + id + " not found.";
	}

	/**
	 * Creates a new exception with a cause denoting an article with the
	 * supplied ID could not be found.
	 * 
	 * @param id
	 *        The ID of the article that could not be found.
	 * @param cause
	 *        The cause of the exception.
	 */
	public ArticleNotFoundException(String id, Throwable cause) {
		super(message(id), cause);
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
