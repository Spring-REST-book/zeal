package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class CommentTest {

	@Test
	public void givenValidData_whenConstruct_thenGettersReturnValueData() {
		
		String id = "12345";
		String articleId = "09876";
		LocalDateTime creationDate = date();
		String content = "bar";
		
		Comment comment = new Comment(id, articleId, creationDate, content);
		
		assertEquals(id, comment.getId());
		assertEquals(articleId, comment.getArticleId());
		assertEquals(creationDate, comment.getCreationDate());
		assertEquals(content, comment.getContent());
	}

	private static LocalDateTime date() {
		return LocalDateTime.of(2021, Month.MARCH, 26, 17, 41, 00);
	}

	@Test
	public void givenValidData_whenSet_thenGettersReturnValueData() {
		
		String id = "12345";
		String articleId = "09876";
		LocalDateTime creationDate = date();
		String content = "bar";
		
		Comment comment = new Comment();
		
		comment.setId(id);
		comment.setArticleId(articleId);
		comment.setCreationDate(creationDate);
		comment.setContent(content);
		
		assertEquals(id, comment.getId());
		assertEquals(articleId, comment.getArticleId());
		assertEquals(creationDate, comment.getCreationDate());
		assertEquals(content, comment.getContent());
	}
}
