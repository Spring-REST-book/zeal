package com.dzone.albanoj2.zeal.rest.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArticleResourceTest {

	@Test
	public void givenValidData_whenSet_thenGetReturnsCorrectData() {
		
		String id = "123";
		long creationTimestamp = 700;
		String title = "foo";
		String content = "bar";
		
		ArticleResource resource = new ArticleResource();
		
		resource.setId(id);
		resource.setCreationTimestamp(creationTimestamp);
		resource.setTitle(title);
		resource.setContent(content);
		
		assertEquals(id, resource.getId());
		assertEquals(creationTimestamp, resource.getCreationTimestamp());
		assertEquals(title, resource.getTitle());
		assertEquals(content, resource.getContent());
	}
}
