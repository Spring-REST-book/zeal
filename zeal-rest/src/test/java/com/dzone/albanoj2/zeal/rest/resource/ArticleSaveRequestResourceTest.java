package com.dzone.albanoj2.zeal.rest.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArticleSaveRequestResourceTest {
	
	@Test
	public void givenValidData_whenConstruct_thenGetReturnsCorrectData() {
		
		String title = "foo";
		String content = "bar";
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource(title, content);
		
		assertEquals(title, resource.getTitle());
		assertEquals(content, resource.getContent());
	}

	@Test
	public void givenValidData_whenSet_thenGetReturnsCorrectData() {
		
		String title = "foo";
		String content = "bar";
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource();
		
		resource.setTitle(title);
		resource.setContent(content);
		
		assertEquals(title, resource.getTitle());
		assertEquals(content, resource.getContent());
	}
}
