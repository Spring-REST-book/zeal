package com.dzone.albanoj2.zeal.rest.resource.mapper;

/**
 * A mapper responsible for converting a resource to a domain object.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 * 
 * @param <D>
 *        The type of the domain object.
 * @param <R>
 *        The type of the resource.
 */
public interface ResourceMapper<D, R> {

	/**
	 * Converts the supplied resource to a domain object.
	 * 
	 * @param resource
	 *        The resource to convert.
	 * 
	 * @return
	 *         The converted domain object.
	 */
	public D to(R resource);

	/**
	 * Converts the supplied resource and ID to a domain object.
	 * 
	 * @param id
	 *        The desired ID of the converted domain object.
	 * @param resource
	 *        The resource to convert.
	 * 
	 * @return
	 *         The converted domain object.
	 */
	public D to(String id, R resource);
}
