package com.dzone.albanoj2.zeal.app.service.error;

/**
 * An exception denoting that a desired article could not be found.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class ArticleNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new exception denoting an article with the supplied ID could
	 * not be found.
	 * 
	 * @param id
	 *        The ID of the article that could not be found.
	 */
	public ArticleNotFoundException(String id) {
		super(id);
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
		super(id, cause);
	}
}
