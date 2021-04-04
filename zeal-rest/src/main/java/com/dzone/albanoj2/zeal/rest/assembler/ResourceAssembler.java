package com.dzone.albanoj2.zeal.rest.assembler;

import java.util.Collection;
import java.util.List;

/**
 * Assembler responsible for converting domain objects of type {@code D} to
 * resource objects of type {@code R}.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 * 
 * @param <D>
 *        The type of the domain object.
 * @param <R>
 *        The type of the resource object.
 */
public interface ResourceAssembler<D, R> {

	/**
	 * Converts a domain object into a resource object.
	 * 
	 * @param object
	 *        The domain object to convert.
	 * 
	 * @return
	 *         The converted resource object.
	 */
	public R toResource(D object);

	/**
	 * Converts a collection of domain objects into a list of resource objects.
	 * 
	 * @param objects
	 *        The domain objects to convert.
	 * 
	 * @return
	 *         The converted resource objects.
	 */
	public List<R> toResources(Collection<? extends D> objects);
}
