package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
