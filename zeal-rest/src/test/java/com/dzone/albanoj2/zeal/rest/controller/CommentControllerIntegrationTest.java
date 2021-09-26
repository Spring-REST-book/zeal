package com.dzone.albanoj2.zeal.rest.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.dzone.albanoj2.zeal.app.service.CommentService;
import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.resource.CommentSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestRestConfiguration.class)
public class CommentControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CommentService service;
	
	@Autowired
	private TimeUtility timeUtility;
	
	@Test
	public void givenNoComments_whenFindById_thenCommentNotFound() throws Exception {
		
		String articleId = "someArticleId";
		
		givenNoComments(articleId);
		
		mockMvc.perform(get("/comment/{id}", "1"))
			.andExpect(status().isNotFound());
	}

	private void givenNoComments(String articleId) {
		doReturn(new ArrayList<>()).when(service).findAllByArticleId(eq(articleId));
		doThrow(CommentNotFoundException.class).when(service).findById(anyString());
	}
	
	@Test
	public void givenOneNonMatchingComment_whenFindAll_thenCommentNotFound() throws Exception {

		String articleId = "someArticleId";
		Comment comment = new Comment("123", articleId, date(30), "bar");
		
		givenComments(articleId, comment);
		
		mockMvc.perform(get("/comment/{id}", comment.getId() + "000"))
			.andExpect(status().isNotFound());
	}

	private static ZonedDateTime date(int day) {
		return LocalDateTime.of(2021, Month.MARCH, day, 17, 41, 00)
			.atZone(ZoneOffset.UTC);
	}
	
	private void givenComments(String articleId, Comment... comments) {
		
		doReturn(Arrays.asList(comments)).when(service).findAllByArticleId(eq(articleId));
		doThrow(CommentNotFoundException.class).when(service).findById(anyString());
		
		for (Comment comment: comments) {
			doReturn(comment).when(service).findById(eq(comment.getId()));
		}
	}
	
	@Test
	public void givenOneMatchingComment_whenFindAll_thenCommentFound() throws Exception {

		String articleId = "someArticleId";
		Comment comment = new Comment("123", articleId, date(30), "bar");
		
		givenComments(articleId, comment);
		
		mockMvc.perform(get("/comment/{id}", comment.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(comment.getId())))
			.andExpect(jsonPath("$.articleId", is(comment.getArticleId())))
			.andExpect(jsonPath("$.creationTimestamp", is(comment.getCreationEpoch())))
			.andExpect(jsonPath("$.content", is(comment.getContent())));
	}
	
	@Test
	public void givenOneNonMatchingAndOneMatchingComment_whenFindAll_thenCommentFound() throws Exception {

		String articleId = "someArticleId";
		Comment nonMatching = new Comment("123", articleId, date(29), "bar");
		Comment matching = new Comment("456", articleId, date(30), "something");
		
		givenComments(articleId, nonMatching, matching);
		
		mockMvc.perform(get("/comment/{id}", matching.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(matching.getId())))
			.andExpect(jsonPath("$.articleId", is(matching.getArticleId())))
			.andExpect(jsonPath("$.creationTimestamp", is(matching.getCreationEpoch())))
			.andExpect(jsonPath("$.content", is(matching.getContent())));
	}
	
	@Test
	public void givenNoRequestBody_whenUpdate_thenBadRequestReturned() throws Exception {
				
		mockMvc.perform(put("/comment/{id}", "1"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenValidRequestBody_whenSave_thenCommentCreated() throws Exception {
		
		String id = "123";
		CommentSaveRequestResource request = new CommentSaveRequestResource("foo", "bar");
		
		givenCommentWillBeUpdated();
		
		mockMvc.perform(put("/comment/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(id)))
			.andExpect(jsonPath("$.articleId", is(request.getArticleId())))
			.andExpect(jsonPath("$.creationTimestamp", isWithinSeconds(10)))
			.andExpect(jsonPath("$.content", is(request.getContent())));

		assertCommentSaved(id, request);
	}

	private void givenCommentWillBeUpdated() {
		doAnswer(i -> i.getArgument(0))
			.when(service)
			.save(any(Comment.class));
	}
	
	private String toJson(Object value) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(value);
	}
	
	private Matcher<Long> isWithinSeconds(int seconds) {
		
		ZonedDateTime now = timeUtility.now();
		ZonedDateTime lowerBound = now.minusSeconds(seconds);
		
		return allOf(
			greaterThan(lowerBound.toInstant().toEpochMilli()), 
			lessThan(now.toInstant().toEpochMilli())
		);
	}
	
	private boolean matches(CommentSaveRequestResource request, Comment comment) {
		return request.getArticleId().equals(comment.getArticleId()) &&
			isWithinSeconds(10).matches(comment.getCreationEpoch()) &&
			request.getContent().equals(comment.getContent());
	}

	private void assertCommentSaved(String id, CommentSaveRequestResource request) {
		verify(service, times(1)).save(argThat(comment -> {
			return id.equals(comment.getId()) && matches(request, comment);
		}));
	}
	
	@Test
	public void whenDeleteById_thenCommentDeleted() throws Exception {
		
		String id = "123";
		
		mockMvc.perform(delete("/comment/{id}", id))
			.andExpect(status().isNoContent());
		
		assertDeleted(id);
	}

	private void assertDeleted(String id) {
		verify(service, times(1)).deleteById(eq(id));
	}
	
	@Test
	public void givenNullArticleId_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		CommentSaveRequestResource request = new CommentSaveRequestResource(null, "bar");
		
		mockMvc.perform(put("/comment/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenNullContent_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		CommentSaveRequestResource request = new CommentSaveRequestResource("foo", null);
		
		mockMvc.perform(put("/comment/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyContent_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		CommentSaveRequestResource request = new CommentSaveRequestResource("foo", "");
		
		mockMvc.perform(put("/comment/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenBlankContent_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		CommentSaveRequestResource request = new CommentSaveRequestResource("foo", " ");
		
		mockMvc.perform(put("/comment/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
}
