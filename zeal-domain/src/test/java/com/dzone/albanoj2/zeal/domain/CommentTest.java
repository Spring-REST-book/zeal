package com.dzone.albanoj2.zeal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.test.data.Dates;

public class CommentTest {

	@Test
	public void givenValidData_whenConstruct_thenGettersReturnValueData() {
		
		String id = "12345";
		String articleId = "09876";
		ZonedDateTime creationDate = Dates.arbitrary();
		String content = "bar";
		
		Comment comment = new Comment(id, articleId, creationDate, content);
		
		assertEquals(id, comment.getId());
		assertEquals(articleId, comment.getArticleId());
		assertEquals(creationDate, comment.getCreationDate());
		assertEquals(content, comment.getContent());
	}

	@Test
	public void givenValidData_whenSet_thenGettersReturnValueData() {
		
		String id = "12345";
		String updatedArticleId = "09876";
		ZonedDateTime originalCreationDate = Dates.arbitrary();
		ZonedDateTime updatedCreationDate = originalCreationDate.plus(1, ChronoUnit.MINUTES);
		String updatedContent = "updated content";
		
		Comment comment = new Comment("55555", originalCreationDate, "foo");
		
		comment.setId(id);
		comment.setArticleId(updatedArticleId);
		comment.setCreationDate(updatedCreationDate);
		comment.setContent(updatedContent);
		
		assertEquals(id, comment.getId());
		assertEquals(updatedArticleId, comment.getArticleId());
		assertEquals(updatedCreationDate, comment.getCreationDate());
		assertEquals(updatedContent, comment.getContent());
	}
	
	@Test
	public void givenNullArticleId_whenConstruct_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> new Comment("12345", null, Dates.arbitrary(), "bar")
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
			() -> new Comment("12345", "foo", Dates.arbitrary(), null)
		);
	}
	
	@Test
	public void givenNullArticleId_whenSetArticleId_thenExceptionThrown() {
		
		Comment comment = new Comment("55555", Dates.arbitrary(), "bar");
		
		assertThrows(
			NullPointerException.class,
			() -> comment.setArticleId(null)
		);
	}
	
	@Test
	public void givenNullCreationDate_whenSetCreationDate_thenExceptionThrown() {
		
		Comment comment = new Comment("55555", Dates.arbitrary(), "bar");
		
		assertThrows(
			NullPointerException.class,
			() -> comment.setCreationDate(null)
		);
	}
	
	@Test
	public void givenNullContent_whenSetContent_thenExceptionThrown() {
		
		Comment comment = new Comment("55555", Dates.arbitrary(), "bar");
		
		assertThrows(
			NullPointerException.class,
			() -> comment.setContent(null)
		);
	}
	
	@Test
	public void givenEmptyContent_whenConstruct_thenExceptionThrown() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new Comment("55555", Dates.arbitrary(), "")
		);
	}
	
	@Test
	public void givenBlankContent_whenConstruct_thenExceptionThrown() {
		assertThrows(
			IllegalArgumentException.class,
			() -> new Comment("55555", Dates.arbitrary(), " ")
		);
	}
	
	@Test
	public void givenEmptyContent_whenSetContent_thenExceptionThrown() {

		Comment comment = new Comment("55555", Dates.arbitrary(), "bar");
		
		assertThrows(
			IllegalArgumentException.class,
			() -> comment.setContent("")
		);
	}
	
	@Test
	public void givenBlankContent_whenSetContent_thenExceptionThrown() {

		Comment comment = new Comment("55555", Dates.arbitrary(), "bar");
		
		assertThrows(
			IllegalArgumentException.class,
			() -> comment.setContent(" ")
		);
	}
}
