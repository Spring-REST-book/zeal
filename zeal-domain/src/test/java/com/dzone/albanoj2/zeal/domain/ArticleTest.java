package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.test.data.Dates;

public class ArticleTest {

	@Test
	public void givenValidData_whenConstruct_thenGettersReturnValueData() {
		
		String id = "12345";
		ZonedDateTime creationDate = Dates.arbitrary();
		String title = "foo";
		String content = "bar";
		
		Article article = new Article(id, creationDate, title, content);
		
		assertEquals(id, article.getId());
		assertEquals(creationDate, article.getCreationDate());
		assertEquals(title, article.getTitle());
		assertEquals(content, article.getContent());
	}

	@Test
	public void givenValidData_whenSet_thenGettersReturnValueData() {
		
		String id = "12345";
		ZonedDateTime originalCreationDate = Dates.arbitrary();
		ZonedDateTime updatedCreationDate = originalCreationDate.plus(1, ChronoUnit.MINUTES);
		String updatedTitle = "updated title";
		String updatedContent = "updated content";

		Article article = new Article(originalCreationDate, "foo", "bar");
		
		article.setId(id);
		article.setCreationDate(updatedCreationDate);
		article.setTitle(updatedTitle);
		article.setContent(updatedContent);
		
		assertEquals(id, article.getId());
		assertEquals(updatedCreationDate, article.getCreationDate());
		assertEquals(updatedTitle, article.getTitle());
		assertEquals(updatedContent, article.getContent());
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
			() -> new Article("12345", Dates.arbitrary(), null, "bar")
		);
	}
	
	@Test
	public void givenNullContent_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Article("12345", Dates.arbitrary(), "foo", null)
		);
	}
	
	@Test
	public void givenNullCreationDate_whenSetCreationDate_thenExceptionThrown() {
		
		Article article = new Article(Dates.arbitrary(), "foo", "bar");
		
		assertThrows(
			NullPointerException.class,
			() -> article.setCreationDate(null)
		);
	}
	
	@Test
	public void givenNullTitle_whenSetTitle_thenExceptionThrown() {

		Article article = new Article(Dates.arbitrary(), "foo", "bar");
		
		assertThrows(
			NullPointerException.class,
			() -> article.setTitle(null)
		);
	}
	
	@Test
	public void givenNullContent_whenSetContent_thenExceptionThrown() {

		Article article = new Article(Dates.arbitrary(), "foo", "bar");
		
		assertThrows(
			NullPointerException.class,
			() -> article.setContent(null)
		);
	}
	
	@Test
	public void givenEmptyTitle_whenConstruct_thenExceptionThrown() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new Article(Dates.arbitrary(), "", "foo")
		);
	}
	
	@Test
	public void givenEmptyContent_whenConstruct_thenExceptionThrown() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new Article(Dates.arbitrary(), "foo", "")
		);
	}
	
	@Test
	public void givenBlankTitle_whenConstruct_thenExceptionThrown() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new Article(Dates.arbitrary(), " ", "foo")
		);
	}
	
	@Test
	public void givenBlankContent_whenConstruct_thenExceptionThrown() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new Article(Dates.arbitrary(), "foo", " ")
		);
	}
	
	@Test
	public void givenEmptyTitle_whenSetTitle_thenExceptionThrown() {

		Article article = new Article(Dates.arbitrary(), "foo", "bar");
		
		assertThrows(
			IllegalArgumentException.class,
			() -> article.setTitle("")
		);
	}
	
	@Test
	public void givenEmptyContent_whenSetContent_thenExceptionThrown() {

		Article article = new Article(Dates.arbitrary(), "foo", "bar");
		
		assertThrows(
			IllegalArgumentException.class,
			() -> article.setContent("")
		);
	}
	
	@Test
	public void givenBlankTitle_whenSetTitle_thenExceptionThrown() {

		Article article = new Article(Dates.arbitrary(), "foo", "bar");
		
		assertThrows(
			IllegalArgumentException.class,
			() -> article.setTitle(" ")
		);
	}
	
	@Test
	public void givenBlankContent_whenSetContent_thenExceptionThrown() {

		Article article = new Article(Dates.arbitrary(), "foo", "bar");
		
		assertThrows(
			IllegalArgumentException.class,
			() -> article.setContent(" ")
		);
	}
}
