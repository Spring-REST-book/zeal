package com.dzone.albanoj2.zeal.rest.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

public abstract class AbstractResourceAssemblerTest<D, R> {
	
	private ResourceAssembler<D, R> assembler;
	
	@BeforeEach
	public void setUp() {
		assembler = getAssembler();
	}
	
	@Test
	public void givenNullCollection_whenToResources_returnEmptyListReturned() {
		
		List<R> resources = assembler.toResources(null);
		
		assertNotNull(resources);
		assertTrue(resources.isEmpty());
	}
	
	@Test
	public void givenEmptyCollection_whenToResources_returnEmptyListReturned() {
		
		List<R> resources = assembler.toResources(Collections.emptyList());
		
		assertNotNull(resources);
		assertTrue(resources.isEmpty());
	}
	
	@Test
	public void givenListContaingOneElement_whenToResources_thenPopulatedListReturned() {
		
		D domainObject = createPopulatedDomainObject("1");
		
		List<R> resources = assembler.toResources(Arrays.asList(domainObject));
		
		assertNotNull(resources);
		assertEquals(1, resources.size());
		assertTrue(containsMatching(resources, matches(domainObject)));
	}
	
	private boolean containsMatching(Collection<R> collection, ArgumentMatcher<R> matcher) {
		return collection.stream()
			.anyMatch(r -> matcher.matches(r));
	}
	
	@Test
	public void givenListContaingTwoElements_whenToResources_thenPopulatedListReturned() {
		
		D domainObject1 = createPopulatedDomainObject("1");
		D domainObject2 = createPopulatedDomainObject("2");
		
		List<R> resources = assembler.toResources(Arrays.asList(domainObject1, domainObject2));
		
		assertNotNull(resources);
		assertEquals(2, resources.size());
		assertTrue(containsMatching(resources, matches(domainObject1)));
		assertTrue(containsMatching(resources, matches(domainObject2)));
	}

	protected abstract D createPopulatedDomainObject(String id);
	protected abstract ResourceAssembler<D, R> getAssembler();
	protected abstract ArgumentMatcher<R> matches(D domainObject);
}
