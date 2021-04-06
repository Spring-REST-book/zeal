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
import com.dzone.albanoj2.zeal.persistence.inmemory.InMemoryArticleRepository;
import com.dzone.albanoj2.zeal.rest.resource.ArticleResource;
import com.dzone.albanoj2.zeal.rest.resource.ArticleSaveRequestResource;
import com.dzone.albanoj2.zeal.test.system.Matchers;
import com.dzone.albanoj2.zeal.test.system.client.MockHttpClient;
import com.dzone.albanoj2.zeal.test.system.client.MockHttpClientResponse;
import com.dzone.albanoj2.zeal.test.system.context.ArticleSharedContext;
import com.fasterxml.jackson.core.type.TypeReference;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ArticleStepDefinitions {
	
	@Autowired
	private MockHttpClient httpClient;
	
	@Autowired
	private ArticleSharedContext context;
	
	@Autowired
	private InMemoryArticleRepository repository;
	
	@Before
	public void beforeScenario() {
		repository.deleteAll();
	}

	@Given("^I have not posted any articles$")
	public void noArticles() {}
	
	@Given("^the following articles exist:$")
	public void existingArticles(List<Article> articles) {
		articles.forEach(repository::save);
	}
	
	@DataTableType
	public Article articleEntry(Map<String, String> entry) {
		return new Article(
			entry.get("ID"),
			ZonedDateTime.now(ZoneOffset.UTC),
			entry.get("Title"),
			entry.get("Content")
		);
	}
	
	@When("^I retrieve all articles$")
	public void retrieveAllArticles() throws Exception {
		httpClient.get("/article")
			.then(context::setLastResponseCodeFrom)
			.then(response -> context.setLastResponseBody(extractArticles(response)));
	}

	private static List<Article> extractArticles(MockHttpClientResponse response) {
		return response.getResponseBody(new TypeReference<List<ArticleResource>>() {})
			.stream()
			.map(ArticleStepDefinitions::convert)
			.collect(Collectors.toList());
	}
	
	private static Article convert(ArticleResource resource) {
	
		Article article = new Article();
		
		article.setId(resource.getId());
		article.setCreationDate(creationDate(resource));
		article.setTitle(resource.getTitle());
		article.setContent(resource.getContent());
		
		return article;
	}
	
	private static ZonedDateTime creationDate(ArticleResource resource) {
		Instant instant = Instant.ofEpochMilli(resource.getCreationTimestamp());
		return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
	}
	
	@When("^I retrieve an article with ID \"(.+)\"$")
	public void retrieveById(String id) throws Exception {
		httpClient.get("/article/" + id)
			.then(context::setLastResponseCodeFrom)
			.thenIfSuccessful(response -> context.setLastResponseBody(extractArticle(response)));
	}

	private static Article extractArticle(MockHttpClientResponse response) {
		return convert(
			response.getResponseBody(new TypeReference<ArticleResource>() {})
		);
	}
	
	@When("^I update an article with ID \"(.+)\" to have a title of \"(.+)\" and content of \"(.+)\"$")
	public void updateAnArticle(String id, String title, String content) throws Exception {
		httpClient.put("/article/" + id, request(title, content))
			.then(context::setLastResponseCodeFrom)
			.thenIfSuccessful(response -> context.setLastResponseBody(extractArticle(response)));
	}

	private static ArticleSaveRequestResource request(String title, String content) {
		return new ArticleSaveRequestResource(title, content);
	}
	
	@When("^I post an article with a title of \"(.+)\" and content of \"(.+)\"$")
	public void postAnArticle(String title, String content) throws Exception {
		httpClient.post("/article", request(title, content))
			.then(context::setLastResponseCodeFrom)
			.thenIfSuccessful(response -> context.setLastResponseBody(extractArticle(response)));
	}
	
	@When("^I delete an article with ID \"(.+)\"$")
	public void deleteAnArticle(String id) throws Exception {
		httpClient.delete("/article/" + id)
			.then(context::setLastResponseCodeFrom);
	}
	
	@Then("^I find no articles$")
	public void findNoArticles() {
		assertTrue(context.nothingRetrieved());
	}
	
	@Then("^I receive the following articles in the response body:$")
	public void assertResponseBodyContains(List<Article> expected) {
		Matchers.assertMatches(
			expected,
			context.getLastResponseBody(),
			ArticleStepDefinitions::matches
		);
	}
	
	private static boolean matches(Article desired, Article actual) {
		return Matchers.idMatches(desired.getId(), actual.getId()) &&
			actual.getTitle().equals(desired.getTitle()) &&
			actual.getContent().equals(desired.getContent());
	}
	
	@Then("^I receive an article response status code of (\\d+)$")
	public void receiveResponseCode(int status) {
		assertEquals(status, context.getLastResponseCode());
	}
	
	@Then("^the following articles now exist:$")
	public void articlesNowExist(List<Article> expected) {		
		Matchers.assertMatches(
			expected, 
			repository.findAll(),
			ArticleStepDefinitions::matches
		);
	}
}
