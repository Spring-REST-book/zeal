package com.dzone.albanoj2.zeal.rest.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An abstract base class that contains the common logic between all assemblers.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public abstract class AbstractResourceAssembler<D, R> implements ResourceAssembler<D, R> {

	@Override
	public List<R> toResources(Collection<? extends D> objects) {
		
		if (objects == null) {
			return new ArrayList<>();
		}
		else {
			return objects.stream()
				.map(this::toResource)
				.collect(Collectors.toList());
		}
	}
}
