package com.dzone.albanoj2.zeal.rest.controller;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.dzone.albanoj2.zeal.app.service.ArticleService;
import com.dzone.albanoj2.zeal.app.service.CommentService;
import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.resource.ArticleSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.resource.CommentSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestRestConfiguration.class)
public class ArticleControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ArticleService service;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private TimeUtility timeUtility;
	
	@Test
	public void givenNoArticles_whenFindAll_thenNoArticlesFound() throws Exception {
		
		givenNoArticles();
		
		mockMvc.perform(get("/article"))
			.andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
	}

	private void givenNoArticles() {
		doReturn(new ArrayList<>()).when(service).findAll();
		doThrow(ArticleNotFoundException.class).when(service).findById(anyString());
	}
	
	@Test
	public void givenOneArticle_whenFindAll_thenOneArticleFound() throws Exception {
		
		Article article = new Article("123", date(30), "foo", "bar");
		
		givenArticles(article);
		
		mockMvc.perform(get("/article"))
			.andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$.[0].id", is(article.getId())))
			.andExpect(jsonPath("$.[0].creationTimestamp", is(article.getCreationEpoch())))
			.andExpect(jsonPath("$.[0].title", is(article.getTitle())))
			.andExpect(jsonPath("$.[0].content", is(article.getContent())));
	}

	private static ZonedDateTime date(int day) {
		return LocalDateTime.of(2021, Month.MARCH, day, 17, 41, 00)
			.atZone(ZoneOffset.UTC);
	}
	
	private void givenArticles(Article... articles) {
		
		doReturn(Arrays.asList(articles)).when(service).findAll();
		doThrow(ArticleNotFoundException.class).when(service).findById(anyString());
		
		for (Article article: articles) {
			doReturn(article).when(service).findById(eq(article.getId()));
		}
	}
	
	@Test
	public void givenTwoArticle_whenFindAll_thenTwoArticleFound() throws Exception {
		
		Article article1 = new Article("123", date(29), "foo", "bar");
		Article article2 = new Article("456", date(30), "baz", "something");
		
		givenArticles(article1, article2);
		
		mockMvc.perform(get("/article"))
			.andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id", is(article1.getId())))
			.andExpect(jsonPath("$.[0].creationTimestamp", is(article1.getCreationEpoch())))
			.andExpect(jsonPath("$.[0].title", is(article1.getTitle())))
			.andExpect(jsonPath("$.[0].content", is(article1.getContent())))
			.andExpect(jsonPath("$.[1].id", is(article2.getId())))
			.andExpect(jsonPath("$.[1].creationTimestamp", is(article2.getCreationEpoch())))
			.andExpect(jsonPath("$.[1].title", is(article2.getTitle())))
			.andExpect(jsonPath("$.[1].content", is(article2.getContent())));
	}
	
	@Test
	public void givenNoArticles_whenFindById_thenArticleNotFound() throws Exception {
		
		givenNoArticles();
		
		mockMvc.perform(get("/article/{id}", "1"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void givenOneNonMatchingArticle_whenFindAll_thenArticleNotFound() throws Exception {
		
		Article article = new Article("123", date(30), "foo", "bar");
		
		givenArticles(article);
		
		mockMvc.perform(get("/article/{id}", article.getId() + "000"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void givenOneMatchingArticle_whenFindAll_thenArticleFound() throws Exception {
		
		Article article = new Article("123", date(30), "foo", "bar");
		
		givenArticles(article);
		
		mockMvc.perform(get("/article/{id}", article.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(article.getId())))
			.andExpect(jsonPath("$.creationTimestamp", is(article.getCreationEpoch())))
			.andExpect(jsonPath("$.title", is(article.getTitle())))
			.andExpect(jsonPath("$.content", is(article.getContent())));
	}
	
	@Test
	public void givenOneNonMatchingAndOneMatchingArticle_whenFindAll_thenArticleFound() throws Exception {

		Article nonMatching = new Article("123", date(29), "foo", "bar");
		Article matching = new Article("456", date(30), "baz", "something");
		
		givenArticles(nonMatching, matching);
		
		mockMvc.perform(get("/article/{id}", matching.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(matching.getId())))
			.andExpect(jsonPath("$.creationTimestamp", is(matching.getCreationEpoch())))
			.andExpect(jsonPath("$.title", is(matching.getTitle())))
			.andExpect(jsonPath("$.content", is(matching.getContent())));
	}
	
	@Test
	public void givenNoRequestBody_whenCreate_thenBadRequestReturned() throws Exception {
				
		mockMvc.perform(post("/article"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenValidRequestBody_whenCreate_thenArticleCreated() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", "bar");
		
		givenArticleWillBeCreatedWithId(id);
		
		mockMvc.perform(post("/article")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(id)))
			.andExpect(jsonPath("$.creationTimestamp", isWithinSeconds(10)))
			.andExpect(jsonPath("$.title", is(request.getTitle())))
			.andExpect(jsonPath("$.content", is(request.getContent())));
		
		assertArticleSaved(request);
	}

	private void givenArticleWillBeCreatedWithId(String id) {
		doAnswer(i -> {
				Article article = i.getArgument(0);
				article.setId(id);
				
				return article;
			})
			.when(service)
			.save(any(Article.class));
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
	
	private void assertArticleSaved(ArticleSaveRequestResource request) {
		verify(service, times(1)).save(argThat(article -> matches(request, article)));
	}
	
	private boolean matches(ArticleSaveRequestResource request, Article article) {
		return isWithinSeconds(10).matches(article.getCreationEpoch()) &&
			request.getTitle().equals(article.getTitle()) &&
			request.getContent().equals(article.getContent());
	}
	
	@Test
	public void givenNoRequestBody_whenUpdate_thenBadRequestReturned() throws Exception {
				
		mockMvc.perform(put("/article/{id}", "1"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenValidRequestBody_whenSave_thenArticleCreated() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", "bar");
		
		givenArticleWillBeUpdated();
		
		mockMvc.perform(put("/article/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(id)))
			.andExpect(jsonPath("$.creationTimestamp", isWithinSeconds(10)))
			.andExpect(jsonPath("$.title", is(request.getTitle())))
			.andExpect(jsonPath("$.content", is(request.getContent())));

		assertArticleSaved(id, request);
	}

	private void givenArticleWillBeUpdated() {
		doAnswer(i -> i.getArgument(0))
			.when(service)
			.save(any(Article.class));
	}

	private void assertArticleSaved(String id, ArticleSaveRequestResource request) {
		verify(service, times(1)).save(argThat(article -> {
			return id.equals(article.getId()) && matches(request, article);
		}));
	}
	
	@Test
	public void whenDeleteById_thenArticleDeleted() throws Exception {
		
		String id = "123";
		
		mockMvc.perform(delete("/article/{id}", id))
			.andExpect(status().isNoContent());
		
		assertDeleted(id);
	}

	private void assertDeleted(String id) {
		verify(service, times(1)).deleteById(eq(id));
	}
	
	@Test
	public void givenArticleDoesNotExist_whenFindAllComments_thenCommentsNotFound() throws Exception {
		
		String articleId = "123";
		
		givenArticleWithIdDoesNotExist(articleId);
		
		mockMvc.perform(get("/article/{id}/comment", articleId))
			.andExpect(status().isNotFound());
	}
	
	private void givenArticleWithIdDoesNotExist(String articleId) {
		doThrow(ArticleNotFoundException.class).when(commentService).findAllByArticleId(eq(articleId));
		doThrow(ArticleNotFoundException.class).when(commentService)
			.save(argThat(comment -> comment.getArticleId().equals(articleId)));
	}

	@Test
	public void givenNoComments_whenFindAllComments_thenEmptyCommentListReturned() throws Exception {
		
		String articleId = "123";
		
		givenNoCommentsForArticleWithId(articleId);
		
		mockMvc.perform(get("/article/{id}/comment", articleId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
	}
	
	private void givenNoCommentsForArticleWithId(String articleId) {
		doReturn(new ArrayList<>()).when(commentService).findAllByArticleId(eq(articleId));
	}

	@Test
	public void givenOneComment_whenFindAllComments_thenOneCommentFound() throws Exception {
		
		String articleId = "123";
		Comment comment = new Comment("456", articleId, date(30), "foo");
		
		givenCommentsForArticleWithId(articleId, comment);
		
		mockMvc.perform(get("/article/{id}/comment", articleId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$.[0].id", is(comment.getId())))
			.andExpect(jsonPath("$.[0].articleId", is(comment.getArticleId())))
			.andExpect(jsonPath("$.[0].creationTimestamp", is(comment.getCreationEpoch())))
			.andExpect(jsonPath("$.[0].content", is(comment.getContent())));
	}
	
	private void givenCommentsForArticleWithId(String articleId, Comment... comments) {
		doReturn(Arrays.asList(comments)).when(commentService).findAllByArticleId(eq(articleId));
	}

	@Test
	public void givenTwoComments_whenFindAllComments_thenTwoCommentsFound() throws Exception {
		
		String articleId = "123";
		Comment comment1 = new Comment("456", articleId, date(29), "foo");
		Comment comment2 = new Comment("789", articleId, date(30), "bar");
		
		givenCommentsForArticleWithId(articleId, comment1, comment2);
		
		mockMvc.perform(get("/article/{id}/comment", articleId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id", is(comment1.getId())))
			.andExpect(jsonPath("$.[0].articleId", is(comment1.getArticleId())))
			.andExpect(jsonPath("$.[0].creationTimestamp", is(comment1.getCreationEpoch())))
			.andExpect(jsonPath("$.[0].content", is(comment1.getContent())))
			.andExpect(jsonPath("$.[1].id", is(comment2.getId())))
			.andExpect(jsonPath("$.[1].articleId", is(comment2.getArticleId())))
			.andExpect(jsonPath("$.[1].creationTimestamp", is(comment2.getCreationEpoch())))
			.andExpect(jsonPath("$.[1].content", is(comment2.getContent())));
	}
	
	@Test
	public void givenNoRequestBody_whenPostComment_thenBadRequestReturned() throws Exception {
		
		mockMvc.perform(post("/article/{id}/comment", "1"))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenArticleDoesNotExist_whenPostComment_thenArticleNotFound() throws Exception {
		
		String articleId = "1";
		CommentSaveRequestResource request = new CommentSaveRequestResource(articleId, "foo");
		
		givenArticleWithIdDoesNotExist(articleId);
		
		mockMvc.perform(post("/article/{id}/comment", articleId)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void givenValidRequestBody_whenPostComment_thenCommentPosted() throws Exception {
		
		String articleId = "1";
		String expectedCommentId = "2";
		CommentSaveRequestResource request = new CommentSaveRequestResource(articleId, "foo");
		
		givenCommentWillBeCreatedWithId(expectedCommentId);
		
		mockMvc.perform(post("/article/{id}/comment", articleId)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(expectedCommentId)))
			.andExpect(jsonPath("$.articleId", is(request.getArticleId())))
			.andExpect(jsonPath("$.creationTimestamp", isWithinSeconds(10)))
			.andExpect(jsonPath("$.content", is(request.getContent())));
		
		assertCommentSaved(request);
	}

	private void givenCommentWillBeCreatedWithId(String id) {
		doAnswer(i -> {
				Comment comment = i.getArgument(0);
				comment.setId(id);
				
				return comment;
			})
			.when(commentService)
			.save(any(Comment.class));
	}
	
	private void assertCommentSaved(CommentSaveRequestResource request) {
		verify(commentService, times(1)).save(argThat(article -> matches(request, article)));
	}
	
	private boolean matches(CommentSaveRequestResource request, Comment comment) {
		return request.getArticleId().equals(comment.getArticleId()) &&
			isWithinSeconds(10).matches(comment.getCreationEpoch()) &&
			request.getContent().equals(comment.getContent());
	}
	
	@Test
	public void givenNullTitle_whenCreate_thenBadRequestReturned() throws Exception {
		
		ArticleSaveRequestResource request = new ArticleSaveRequestResource(null, "bar");
		
		mockMvc.perform(post("/article")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenNullContent_whenCreate_thenBadRequestReturned() throws Exception {
		
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", null);
		
		mockMvc.perform(post("/article")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyTitle_whenCreate_thenBadRequestReturned() throws Exception {
		
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("", "bar");
		
		mockMvc.perform(post("/article")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyContent_whenCreate_thenBadRequestReturned() throws Exception {
		
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", "");
		
		mockMvc.perform(post("/article")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenBlankTitle_whenCreate_thenBadRequestReturned() throws Exception {
		
		ArticleSaveRequestResource request = new ArticleSaveRequestResource(" ", "bar");
		
		mockMvc.perform(post("/article")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenBlankContent_whenCreate_thenBadRequestReturned() throws Exception {
		
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", " ");
		
		mockMvc.perform(post("/article")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenNullTitle_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource(null, "bar");
		
		mockMvc.perform(put("/article/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenNullContent_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", null);
		
		mockMvc.perform(put("/article/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyTitle_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("", "bar");
		
		mockMvc.perform(put("/article/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyContent_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", "");
		
		mockMvc.perform(put("/article/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenBlankTitle_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource(" ", "bar");
		
		mockMvc.perform(put("/article/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenBlankContent_whenSave_thenBadRequestReturned() throws Exception {
		
		String id = "123";
		ArticleSaveRequestResource request = new ArticleSaveRequestResource("foo", " ");
		
		mockMvc.perform(put("/article/{id}", id)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenNullArticleId_whenPostComment_thenBadRequestReturned() throws Exception {
		
		CommentSaveRequestResource request = new CommentSaveRequestResource(null, "foo");
		
		mockMvc.perform(post("/article/{id}/comment", "1")
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenNullContent_whenPostComment_thenBadRequestReturned() throws Exception {
		
		String articleId = "1";
		CommentSaveRequestResource request = new CommentSaveRequestResource(articleId, null);
		
		mockMvc.perform(post("/article/{id}/comment", articleId)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenEmptyContent_whenPostComment_thenBadRequestReturned() throws Exception {
		
		String articleId = "1";
		CommentSaveRequestResource request = new CommentSaveRequestResource(articleId, "");
		
		mockMvc.perform(post("/article/{id}/comment", articleId)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenBlankContent_whenPostComment_thenBadRequestReturned() throws Exception {
		
		String articleId = "1";
		CommentSaveRequestResource request = new CommentSaveRequestResource(articleId, " ");
		
		mockMvc.perform(post("/article/{id}/comment", articleId)
				.content(toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isBadRequest());
	}
}
