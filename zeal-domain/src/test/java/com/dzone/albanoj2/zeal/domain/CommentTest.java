package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

public class CommentTest {

	@Test
	public void givenValidData_whenConstruct_thenGettersReturnValueData() {
		
		String id = "12345";
		String articleId = "09876";
		ZonedDateTime creationDate = date();
		String content = "bar";
		
		Comment comment = new Comment(id, articleId, creationDate, content);
		
		assertEquals(id, comment.getId());
		assertEquals(articleId, comment.getArticleId());
		assertEquals(creationDate, comment.getCreationDate());
		assertEquals(content, comment.getContent());
	}

	private static ZonedDateTime date() {
		return LocalDateTime.of(2021, Month.MARCH, 26, 17, 41, 00)
		    .atZone(ZoneOffset.UTC);
	}

	@Test
	public void givenValidData_whenSet_thenGettersReturnValueData() {
		
		String id = "12345";
		String articleId = "09876";
		ZonedDateTime creationDate = date();
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
	
	@Test
	public void givenNullArticleId_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Comment("12345", null, date(), "bar")
		);
	}
	
	@Test
	public void givenNullCreationDate_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Comment("12345", "foo", null, "bar")
		);
	}
	
	@Test
	public void givenNullContent_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Comment("12345", "foo", date(), null)
		);
	}
	
	@Test
	public void givenNullArticleId_whenSetArticleId_thenExceptionThrown() {
		
		Comment comment = new Comment();
		
		assertThrows(
			NullPointerException.class,
			() -> comment.setArticleId(null)
		);
	}
	
	@Test
	public void givenNullCreationDate_whenSetCreationDate_thenExceptionThrown() {
		
		Comment comment = new Comment();
		
		assertThrows(
			NullPointerException.class,
			() -> comment.setCreationDate(null)
		);
	}
	
	@Test
	public void givenNullContent_whenSetContent_thenExceptionThrown() {
		
		Comment comment = new Comment();
		
		assertThrows(
			NullPointerException.class,
			() -> comment.setContent(null)
		);
	}
}
