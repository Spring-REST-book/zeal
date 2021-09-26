package com.dzone.albanoj2.zeal.rest.resource.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.error.InvalidRequestDataException;
import com.dzone.albanoj2.zeal.rest.resource.CommentSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;
import com.dzone.albanoj2.zeal.test.data.Dates;

public class CommentSaveRequestResourceMapperTest {

	private TimeUtility timeUtility;
	private CommentSaveRequestResourceMapper mapper;
	
	@BeforeEach
	public void setUp() {
		
		timeUtility = mock(TimeUtility.class);
		
		mapper = new CommentSaveRequestResourceMapper(timeUtility);
	}
	
	@Test
	public void givenPopulatedComment_whenTo_thenPopulatedResourceReturned() {

		ZonedDateTime expectedDate = Dates.arbitrary();
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", "foo");
	
		givenCurrentTimeIs(expectedDate);
		
		Comment mapped = mapper.to(resource);
		
		assertNull(mapped.getId());
		assertEquals(resource.getArticleId(), mapped.getArticleId());
		assertEquals(expectedDate, mapped.getCreationDate());
		assertEquals(resource.getContent(), mapped.getContent());
	}
	
	private void givenCurrentTimeIs(ZonedDateTime date) {
		doReturn(date).when(timeUtility).now();
	}

	@Test
	public void givenPopulatedComment_whenToWithId_thenPopulatedResourceReturned() {
		
		String id = "123";
		ZonedDateTime expectedDate = Dates.arbitrary();
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", "foo");
		
		givenCurrentTimeIs(expectedDate);
		
		Comment mapped = mapper.to(id, resource);
		
		assertEquals(id, mapped.getId());
		assertEquals(resource.getArticleId(), mapped.getArticleId());
		assertEquals(expectedDate, mapped.getCreationDate());
		assertEquals(resource.getContent(), mapped.getContent());
	}
	
	@Test
	public void givenNullArticleId_whenTo_thenExceptionThrown() {

		CommentSaveRequestResource resource = new CommentSaveRequestResource(null, "foo");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenNullContent_whenTo_thenExceptionThrown() {
		
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", null);
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenEmptyContent_whenTo_thenExceptionThrown() {
		
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", "");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenBlankContent_whenTo_thenExceptionThrown() {
		
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", " ");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(resource)
		);
	}
	
	@Test
	public void givenNullArticleId_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		CommentSaveRequestResource resource = new CommentSaveRequestResource(null, "foo");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenNullContent_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", null);
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenEmptyContent_whenToWithId_thenExceptionThrown() {

		String id = "someId";
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", "");
		
		assertThrows(
			InvalidRequestDataException.class,
			() -> mapper.to(id, resource)
		);
	}
	
	@Test
	public void givenBlankContent_whenToWithId_thenExceptionThrown() {
		
		String id = "someId";
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", " ");
		
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
		
		CommentSaveRequestResource resource = new CommentSaveRequestResource("123", "foo");
		
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
