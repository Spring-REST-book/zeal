package com.dzone.albanoj2.zeal.rest.resource.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.rest.error.InvalidRequestDataException;
import com.dzone.albanoj2.zeal.rest.resource.ArticleSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;
import com.dzone.albanoj2.zeal.test.data.Dates;

public class ArticleSaveRequestResourceMapperTest {

	private TimeUtility timeUtility;
	private ArticleSaveRequestResourceMapper mapper;
	
	@BeforeEach
	public void setUp() {
		
		timeUtility = mock(TimeUtility.class);
		
		mapper = new ArticleSaveRequestResourceMapper(timeUtility);
	}
	
	@Test
	public void givenPopulatedArticle_whenTo_thenPopulatedResourceReturned() {

		ZonedDateTime expectedDate = Dates.arbitrary();
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", "bar");
	
		givenCurrentTimeIs(expectedDate);
		
		Article mapped = mapper.to(resource);
		
		assertNull(mapped.getId());
		assertEquals(expectedDate, mapped.getCreationDate());
		assertEquals(resource.getTitle(), mapped.getTitle());
		assertEquals(resource.getContent(), mapped.getContent());
	}
	
	private void givenCurrentTimeIs(ZonedDateTime date) {
		doReturn(date).when(timeUtility).now();
	}

	@Test
	public void givenPopulatedArticle_whenToWithId_thenPopulatedResourceReturned() {
		
		String id = "123";
		ZonedDateTime expectedDate = Dates.arbitrary();
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", "bar");
	
		givenCurrentTimeIs(expectedDate);
		
		Article mapped = mapper.to(id, resource);
		
		assertEquals(id, mapped.getId());
		assertEquals(expectedDate, mapped.getCreationDate());
		assertEquals(resource.getTitle(), mapped.getTitle());
		assertEquals(resource.getContent(), mapped.getContent());
	}
	
	@Test
	public void givenNullTitle_whenTo_thenExceptionThrown() {
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource(null, "bar");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenNullContent_whenTo_thenExceptionThrown() {
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", null);
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenEmptyTitle_whenTo_thenExceptionThrown() {
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("", "bar");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenEmptyContent_whenTo_thenExceptionThrown() {
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", "");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenBlankTitle_whenTo_thenExceptionThrown() {
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource(" ", "bar");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenBlankContent_whenTo_thenExceptionThrown() {
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", " ");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenNullTitle_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource(null, "bar");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenNullContent_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", null);
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenEmptyTitle_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("", "bar");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenEmptyContent_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", "");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenBlankTitle_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource(" ", "bar");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenBlankContent_whenToWithId_thenExceptionThrown() {
		
		String id = "someId";
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", " ");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenNullResource_whenTo_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> mapper.to(null)
		);
	}
	
	@Test
	public void givenNullId_whenToWithId_thenExceptionThrown() {
		
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", "bar");
		
		assertThrows(
			NullPointerException.class,
			() -> mapper.to(null, resource)
		);
	}
	
	@Test
	public void givenNullResource_whenToWithId_thenExceptionThrown() {
		assertThrows(
			NullPointerException.class,
			() -> mapper.to("foo", null)
		);
	}
}
