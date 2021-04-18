package com.dzone.albanoj2.zeal.test.system.def;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.persistence.inmemory.InMemoryArticleRepository;
import com.dzone.albanoj2.zeal.persistence.inmemory.InMemoryCommentRepository;
import com.dzone.albanoj2.zeal.rest.resource.CommentResource;
import com.dzone.albanoj2.zeal.rest.resource.CommentSaveRequestResource;
import com.dzone.albanoj2.zeal.test.system.Matchers;
import com.dzone.albanoj2.zeal.test.system.client.MockHttpClient;
import com.dzone.albanoj2.zeal.test.system.client.MockHttpClientResponse;
import com.dzone.albanoj2.zeal.test.system.context.CommentSharedContext;
import com.fasterxml.jackson.core.type.TypeReference;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommentStepDefinitions {
	
	@Autowired
	private MockHttpClient httpClient;
	
	@Autowired
	private CommentSharedContext context;
	
	@Autowired
	private InMemoryCommentRepository repository;
	
	@Autowired
	private InMemoryArticleRepository articleRepository;
	
	@Before
	public void beforeScenario() {
		repository.deleteAll();
		articleRepository.deleteAll();
	}
	
	@Given("^I have not posted any comments$")
	public void noComments() {
	}
	
	@Given("^the following comments exist:$")
	public void existingArticles(List<Comment> comments) {
		comments.forEach(repository::save);
	}
	
	@DataTableType
	public Comment commentEntry(Map<String, String> entry) {
		return new Comment(
			entry.get("ID"),
			entry.get("Article ID"),
			ZonedDateTime.now(ZoneOffset.UTC),
			entry.get("Content")
		);
	}
	
	@When("^I retrieve all comments for article ID \"(.+)\"$")
	public void retrieveAllArticles(String articleId) throws Exception {
		httpClient.get("/article/" + articleId + "/comment")
			.then(context::setLastResponseCodeFrom)
			.thenIfSuccessful(response -> context.setLastResponseBody(extractComments(response)));
	}

	private static List<Comment> extractComments(MockHttpClientResponse response) {
		return response.getResponseBody(new TypeReference<List<CommentResource>>() {})
			.stream()
			.map(CommentStepDefinitions::convert)
			.collect(Collectors.toList());
	}
	
	@When("^I retrieve a comment with ID \"(.+)\"$")
	public void retrieveById(String id) throws Exception {
		httpClient.get("/comment/" + id)
			.then(context::setLastResponseCodeFrom)
			.thenIfSuccessful(response -> context.setLastResponseBody(extractComment(response)));
	}
	
	@When("^I update a comment with ID \"(.+)\" to have an article ID of \"(.+)\" and content of \"(.+)\"$")
	public void updateAComment(String id, String articleId, String content) throws Exception {
		httpClient.put("/comment/" + id, request(articleId, content))
			.then(context::setLastResponseCodeFrom)
			.thenIfSuccessful(response -> context.setLastResponseBody(extractComment(response)));
	}

	private static CommentSaveRequestResource request(String articleId, String content) {
		return new CommentSaveRequestResource(articleId, content);
	}

	private static Comment extractComment(MockHttpClientResponse response) {
		return convert(
			response.getResponseBody(new TypeReference<CommentResource>() {})
		);
	}
	
	private static Comment convert(CommentResource resource) {
	
		Comment comment = new Comment();
		
		Instant instant = Instant.ofEpochMilli(resource.getCreationTimestamp());
		
		comment.setId(resource.getId());
		comment.setArticleId(resource.getArticleId());
		comment.setCreationDate(ZonedDateTime.ofInstant(instant, ZoneOffset.UTC));
		comment.setContent(resource.getContent());
		
		return comment;
	}
	
	@When("^I post a comment with an article ID of \"(.+)\" and content of \"(.+)\"$")
	public void postAComment(String articleId, String content) throws Exception {
		httpClient.post(
				"/article/" + articleId + "/comment", 
				request(articleId, content)
			)
			.then(context::setLastResponseCodeFrom)
			.thenIfSuccessful(response -> context.setLastResponseBody(extractComment(response)));
	}
	
	@When("^I delete a comment with ID \"(.+)\"$")
	public void deleteAComment(String id) throws Exception {
		httpClient.delete("/comment/" + id)
			.then(context::setLastResponseCodeFrom);
	}
	
	@Then("^I find no comments$")
	public void findNoComments() {
		assertTrue(context.nothingRetrieved());
	}
	
	@Then("^I receive a comment response status code of (\\d+)$")
	public void receiveResponseCode(int status) {
		assertEquals(status, context.getLastResponseCode());
	}
	
	@Then("^I receive the following comments in the response body:$")
	public void assertResponseBodyContains(List<Comment> expected) {
		Matchers.assertMatches(
			expected,
			context.getLastResponseBody(),
			CommentStepDefinitions::matches
		);
	}
	
	@Then("^the following comments now exist:$")
	public void articlesNowExist(List<Comment> expected) {
		
		for (Article article: articleRepository.findAll()) {
			Matchers.assertMatches(
				filterByArticleId(article.getId(), expected), 
				repository.findAllByArticleId(article.getId()),
				CommentStepDefinitions::matches
			);
		}
	}

	private static List<Comment> filterByArticleId(String articleId, List<Comment> expected) {
		return expected.stream()
			.filter(comment -> comment.getArticleId().equals(articleId))
			.collect(Collectors.toList());
	}
	
	private static boolean matches(Comment desired, Comment actual) {
		return Matchers.idMatches(desired.getId(), actual.getId()) &&
			actual.getArticleId().equals(desired.getArticleId()) &&
			actual.getContent().equals(desired.getContent());
	}
}
