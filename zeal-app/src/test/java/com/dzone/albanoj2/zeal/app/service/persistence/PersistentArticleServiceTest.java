package com.dzone.albanoj2.zeal.app.service.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.persistence.article.ArticleRepository;

public class PersistentArticleServiceTest {
	
	private ArticleRepository repository;
	private PersistentArticleService service;
	
	@BeforeEach
	public void setUp() {
		
		repository = mock(ArticleRepository.class);
		service = new PersistentArticleService(repository);
	}
	
	@Test
	public void givenNoArticles_whenFindAll_thenNoArticlesFound() {
		
		givenNoArticles();
		
		List<Article> found = service.findAll();
		
		assertTrue(found.isEmpty());
	}
	
	private void givenNoArticles() {
		doReturn(new ArrayList<>()).when(repository).findAll();
		doReturn(Optional.empty()).when(repository).findById(anyString());
	}
	
	@Test
	public void givenOneArticle_whenFindAll_thenArticlesFound() {
		
		Article article = article("123");
		
		givenArticles(article);
		
		List<Article> found = service.findAll();
		
		assertEquals(1, found.size());
		assertTrue(found.contains(article));
	}

	private static Article article(String id) {
		return new Article(id, date(), "foo", "bar");
	}
	
	private static LocalDateTime date() {
		return LocalDateTime.of(2021, Month.MARCH, 26, 17, 41, 00);
	}

	private void givenArticles(Article... articles) {
		
		doReturn(Arrays.asList(articles)).when(repository).findAll();
		
		for (Article article: articles) {
			doReturn(Optional.of(article)).when(repository).findById(article.getId());
		}
	}
	
	@Test
	public void givenTwoArticles_whenFindAll_thenArticlesFound() {
		
		Article article1 = article("123");
		Article article2 = article("456");
		
		givenArticles(article1, article2);
		
		List<Article> found = service.findAll();
		
		assertEquals(2, found.size());
		assertTrue(found.contains(article1));
		assertTrue(found.contains(article2));
	}
	
	@Test
	public void givenNoArticles_whenFindById_thenExceptionThrown() {
		
		assertThrows(ArticleNotFoundException.class, () -> {
			
			givenNoArticles();
			
			service.findById("123");
		});
	}
	
	@Test
	public void givenOneMatchingArticle_whenFindById_thenArticleFound() {
		
		Article article = article("123");
			
		givenArticles(article);
		
		Article found = service.findById(article.getId());
		
		assertEquals(article, found);
	}
	
	@Test
	public void givenOneNonMatchingArticle_whenFindById_thenExceptionThrown() {
		
		assertThrows(ArticleNotFoundException.class, () -> {
			
			Article nonMatching = article("123");
				
			givenArticles(nonMatching);
			
			service.findById(nonMatching.getId() + "098");
		});
	}
	
	@Test
	public void givenOneNonMatchingAndOneMatchingArticle_whenFindById_thenArticleFound() {
			
		Article nonMatching = article("123");
		Article matching = article("098");
			
		givenArticles(nonMatching, matching);
		
		Article found = service.findById(matching.getId());
		
		assertEquals(matching, found);
	}
	
	@Test
	public void givenArticleCanBeSaved_whenSave_thenArticleSaved() {
			
		Article article = article("123");
		Article expectedSavedArticle = article("123");
		
		givenSavedArticleWillBe(expectedSavedArticle);
		
		Article saved = service.save(article);
		
		assertEquals(expectedSavedArticle, saved);
		assertArticleSaved(article);
	}
	
	private void givenSavedArticleWillBe(Article article) {
		doReturn(article).when(repository).save(any(Article.class));
	}

	private void assertArticleSaved(Article article) {
		verify(repository, times(1)).save(eq(article));
	}
	
	@Test
	public void givenArticleCanBeDeleted_whenDeleteById_thenArticleSaved() {
			
		Article article = article("123");
		
		service.deleteById(article.getId());
		
		assertArticleDeleted(article);
	}

	private void assertArticleDeleted(Article article) {
		verify(repository, times(1)).deleteById(eq(article.getId()));
	}
}
