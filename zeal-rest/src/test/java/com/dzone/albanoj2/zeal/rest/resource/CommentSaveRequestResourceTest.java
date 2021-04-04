package com.dzone.albanoj2.zeal.rest.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommentSaveRequestResourceTest {
	
	@Test
	public void givenValidData_whenConstruct_thenGetReturnsCorrectData() {
		
		String articleId = "456";
		String content = "bar";
		
		CommentSaveRequestResource resource = new CommentSaveRequestResource(articleId, content);

		assertEquals(articleId, resource.getArticleId());
		assertEquals(content, resource.getContent());
	}

	@Test
	public void givenValidData_whenSet_thenGetReturnsCorrectData() {
		
		String articleId = "456";
		String content = "bar";
		
		CommentSaveRequestResource resource = new CommentSaveRequestResource();

		resource.setArticleId(articleId);
		resource.setContent(content);
		
		assertEquals(articleId, resource.getArticleId());
		assertEquals(content, resource.getContent());
	}
}
