package com.dzone.albanoj2.zeal.persistence.inmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.domain.Article;

public class InMemoryArticleRepositoryTest {

	private InMemoryArticleRepository repository;
	
	@BeforeEach
	public void setUp() {
		repository = new InMemoryArticleRepository();
	}
	
	@Test
	public void givenNoArticlesSaved_whenFindAll_thenNoArticlesFound() {
		
		List<Article> found = repository.findAll();
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenOneArticleSaved_whenFindAll_thenOneArticleFound() {
		
		Article article = new Article(null, date(1), "foo", "bar");
		
		repository.save(article);
		
		List<Article> found = repository.findAll();
		
		assertEquals(1, found.size());
		assertTrue(found.contains(article));
	}

	private static ZonedDateTime date(int day) {
		return LocalDateTime.of(2021, Month.APRIL, day, 17, 41, 00)
		    .atZone(ZoneOffset.UTC);
	}
	
	@Test
	public void givenTwoArticlesSaved_whenFindAll_thenTwoArticlesFound() {
		
		Article article1 = new Article(null, date(1), "foo", "bar");
		Article article2 = new Article(null, date(2), "baz", "something");
		
		repository.save(article1);
		repository.save(article2);
		
		List<Article> found = repository.findAll();
		
		assertEquals(2, found.size());
		assertTrue(found.contains(article1));
		assertTrue(found.contains(article2));
	}
	
	@Test
	public void givenNoArticlesSaved_whenFindById_thenArticleNotFound() {
		
		Optional<Article> found = repository.findById("foo");
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenOneMatchingArticleSaved_whenFindById_thenArticleFound() {
		
		Article article = new Article(null, date(1), "foo", "bar");
		
		repository.save(article);

		Optional<Article> found = repository.findById(article.getId());
		
		assertTrue(found.isPresent());
		assertEquals(article, found.get());
	}
	
	@Test
	public void givenOneNonMatchingArticleSaved_whenFindById_thenArticleFound() {

		Article article = new Article(null, date(1), "foo", "bar");
		
		repository.save(article);

		Optional<Article> found = repository.findById(article.getId() + "foo");
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenOneMatchingAndOneNonMatching_whenFindById_thenArticleFound() {

		Article nonMatching = new Article(null, date(1), "foo", "bar");
		Article matching = new Article(null, date(2), "baz", "something");
		
		repository.save(nonMatching);
		repository.save(matching);

		Optional<Article> found = repository.findById(matching.getId());
		
		assertTrue(found.isPresent());
		assertEquals(matching, found.get());
	}
	
	@Test
	public void givenArticleHasNoId_whenSave_thenArticleCreated() {
		
		Article article = new Article(null, date(1), "foo", "bar");
		
		repository.save(article);

		Optional<Article> found = repository.findById(article.getId());
		
		assertTrue(found.isPresent());
		assertArticleMatches(article, found);
	}

	private static void assertArticleMatches(Article expected, Optional<Article> actual) {
		
		Article article = actual.get();
		
		assertEquals(expected.getId(), article.getId());
		assertEquals(expected.getCreationDate(), article.getCreationDate());
		assertEquals(expected.getTitle(), article.getTitle());
		assertEquals(expected.getContent(), article.getContent());
	}
	
	@Test
	public void givenArticleHasIdAndNoArticleExistsWithId_whenSave_thenArticleCreated() {
		
		Article article = new Article("someId", date(1), "foo", "bar");
		
		repository.save(article);

		Optional<Article> found = repository.findById(article.getId());
		
		assertTrue(found.isPresent());
		assertArticleMatches(article, found);
	}
	
	@Test
	public void givenArticleHasIdAndArticleAlreadyExistsWithId_whenSave_thenArticleUpdated() {
		
		Article existing = new Article("someId", date(1), "foo", "bar");
		Article update = new Article(existing.getId(), date(2), "foo", "bar");
		
		repository.save(existing);
		repository.save(update);

		Optional<Article> found = repository.findById(existing.getId());
		
		assertTrue(found.isPresent());
		assertArticleMatches(update, found);
	}
	
	@Test
	public void givenArticleDoesNotExist_whenDeleteById_thenArticleDeleted() {
		
		String id = "foo";
		
		repository.deleteById(id);
		
		Optional<Article> found = repository.findById(id);
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenArticleExists_whenDeleteById_thenArticleDeleted() {
		
		Article article = new Article(null, date(1), "foo", "bar");
		
		repository.save(article);
		
		repository.deleteById(article.getId());
		
		Optional<Article> found = repository.findById(article.getId());
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenArticleDoesNotExist_whenExists_thenFalseReturned() {
		
		String id = "foo";
		
		assertFalse(repository.exists(id));
	}
	
	@Test
	public void givenArticleExists_whenExists_thenTrueReturned() {
		
		Article article = new Article(null, date(1), "foo", "bar");
		
		repository.save(article);
		
		assertTrue(repository.exists(article.getId()));
	}
	
	@Test
	public void givenNoArticles_whenDeleteAll_thenNoArticlesRemain() {
		
		repository.deleteAll();
		
		assertTrue(repository.findAll().isEmpty());
	}
	
	@Test
	public void givenArticleExists_whenDeleteAll_thenNoArticlesRemain() {
		
		Article article = new Article(null, date(1), "foo", "bar");
		
		repository.save(article);
		
		repository.deleteAll();
		
		assertTrue(repository.findAll().isEmpty());
	}
}
