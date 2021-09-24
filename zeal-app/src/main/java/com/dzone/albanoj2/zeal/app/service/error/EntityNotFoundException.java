package com.dzone.albanoj2.zeal.app.service.error;

/**
 * An exception denoting that a desired entity could not be found.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public abstract class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String id;

	/**
	 * Creates a new exception denoting an entity with the supplied ID could not
	 * be found.
	 * 
	 * @param id
	 *        The ID of the entity that could not be found.
	 */
	public EntityNotFoundException(String id) {
		super(message(id));
		this.id = id;
	}
	
	private static String message(String id) {
		return "Entity with ID " + id + " not found.";
	}

	/**
	 * Creates a new exception with a cause denoting an entity with the supplied
	 * ID could not be found.
	 * 
	 * @param id
	 *        The ID of the comment that could not be found.
	 * @param cause
	 *        The cause of the exception.
	 */
	public EntityNotFoundException(String id, Throwable cause) {
		super(message(id), cause);
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
