package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

public class ArticleTest {

	@Test
	public void givenValidData_whenConstruct_thenGettersReturnValueData() {
		
		String id = "12345";
		ZonedDateTime creationDate = date();
		String title = "foo";
		String content = "bar";
		
		Article article = new Article(id, creationDate, title, content);
		
		assertEquals(id, article.getId());
		assertEquals(creationDate, article.getCreationDate());
		assertEquals(title, article.getTitle());
		assertEquals(content, article.getContent());
	}

	private static ZonedDateTime date() {
		return LocalDateTime.of(2021, Month.MARCH, 26, 17, 41, 00)
		    .atZone(ZoneOffset.UTC);
	}

	@Test
	public void givenValidData_whenSet_thenGettersReturnValueData() {
		
		String id = "12345";
		ZonedDateTime creationDate = date();
		String title = "foo";
		String content = "bar";
		
		Article article = new Article();
		
		article.setId(id);
		article.setCreationDate(creationDate);
		article.setTitle(title);
		article.setContent(content);
		
		assertEquals(id, article.getId());
		assertEquals(creationDate, article.getCreationDate());
		assertEquals(title, article.getTitle());
		assertEquals(content, article.getContent());
	}
	
	@Test
	public void givenNullCreationDate_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Article("12345", null, "foo", "bar")
		);
	}
	
	@Test
	public void givenNullTitle_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Article("12345", date(), null, "bar")
		);
	}
	
	@Test
	public void givenNullContent_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Article("12345", date(), "foo", null)
		);
	}
	
	@Test
	public void givenNullCreationDate_whenSetCreationDate_thenExceptionThrown() {
		
		Article article = new Article();
		
		assertThrows(
			NullPointerException.class,
			() -> article.setCreationDate(null)
		);
	}
	
	@Test
	public void givenNullTitle_whenSetTitle_thenExceptionThrown() {
		
		Article article = new Article();
		
		assertThrows(
			NullPointerException.class,
			() -> article.setTitle(null)
		);
	}
	
	@Test
	public void givenNullContent_whenSetContent_thenExceptionThrown() {
		
		Article article = new Article();
		
		assertThrows(
			NullPointerException.class,
			() -> article.setContent(null)
		);
	}
}
