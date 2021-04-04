package com.dzone.albanoj2.zeal.rest.resource.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.rest.resource.ArticleSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

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

		ZonedDateTime expectedDate = date();
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

	private static ZonedDateTime date() {
		return LocalDateTime.of(2021, Month.MARCH, 30, 17, 41, 00)
			.atZone(ZoneOffset.UTC);
	}
	
	@Test
	public void givenPopulatedArticle_whenToWithId_thenPopulatedResourceReturned() {
		
		String id = "123";
		ZonedDateTime expectedDate = date();
		ArticleSaveRequestResource resource = new ArticleSaveRequestResource("foo", "bar");
	
		givenCurrentTimeIs(expectedDate);
		
		Article mapped = mapper.to(id, resource);
		
		assertEquals(id, mapped.getId());
		assertEquals(expectedDate, mapped.getCreationDate());
		assertEquals(resource.getTitle(), mapped.getTitle());
		assertEquals(resource.getContent(), mapped.getContent());
	}
}
