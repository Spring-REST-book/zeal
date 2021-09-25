package com.dzone.albanoj2.zeal.rest.resource.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.domain.Comment;
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
}
