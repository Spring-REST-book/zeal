package com.dzone.albanoj2.zeal.rest.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommentResourceTest {

	@Test
	public void givenValidData_whenSet_thenGetReturnsCorrectData() {
		
		String id = "123";
		String articleId = "456";
		long creationTimestamp = 700;
		String content = "bar";
		
		CommentResource resource = new CommentResource();
		
		resource.setId(id);
		resource.setArticleId(articleId);
		resource.setCreationTimestamp(creationTimestamp);
		resource.setContent(content);
		
		assertEquals(id, resource.getId());
		assertEquals(articleId, resource.getArticleId());
		assertEquals(creationTimestamp, resource.getCreationTimestamp());
		assertEquals(content, resource.getContent());
	}
}
