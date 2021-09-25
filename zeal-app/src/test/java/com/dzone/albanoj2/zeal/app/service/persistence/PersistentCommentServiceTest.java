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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.persistence.article.ArticleRepository;
import com.dzone.albanoj2.zeal.persistence.comment.CommentRepository;
import com.dzone.albanoj2.zeal.test.data.Dates;

public class PersistentCommentServiceTest {

	private ArticleRepository articleRepository;
	private CommentRepository repository;
	private PersistentCommentService service;
	
	@BeforeEach
	public void setUp() {
		
		articleRepository = mock(ArticleRepository.class);
		repository = mock(CommentRepository.class);
		service = new PersistentCommentService(articleRepository, repository);
	}
	
	@Test
	public void givenArticleDoesNotExist_whenFindAll_thenExceptionThrown() {
		
		assertThrows(ArticleNotFoundException.class, () -> {
			
			String articleId = "someArticleId";
			
			givenArticleWithIdDoesNotExist(articleId);
			
			service.findAllByArticleId(articleId);
		});
	}
	
	private void givenArticleWithIdDoesNotExist(String articleId) {
		doReturn(false).when(articleRepository).exists(eq(articleId));
	}
	
	@Test
	public void givenNoComments_whenFindAll_thenNoCommentsFound() {
		
		String articleId = "someArticleId";
		
		givenArticleWithIdExists(articleId);
		givenNoCommentsForArticleId(articleId);
		
		List<Comment> found = service.findAllByArticleId(articleId);
		
		assertTrue(found.isEmpty());
	}
	
	private void givenArticleWithIdExists(String articleId) {
		doReturn(true).when(articleRepository).exists(eq(articleId));
	}
	
	private void givenNoCommentsForArticleId(String articleId) {
		doReturn(new ArrayList<>()).when(repository).findAllByArticleId(articleId);
	}
	
	@Test
	public void givenOneComment_whenFindAll_thenCommentsFound() {
		
		String articleId = "someArticleId";
		Comment comment = comment("123", articleId);

		givenArticleWithIdExists(articleId);
		givenCommentsForArticle(articleId, comment);
		
		List<Comment> found = service.findAllByArticleId(articleId);
		
		assertEquals(1, found.size());
		assertTrue(found.contains(comment));
	}

	private static Comment comment(String id, String articleId) {
		return new Comment(id, articleId, Dates.arbitrary(), "foo");
	}

	private void givenCommentsForArticle(String articleId, Comment... comments) {
		doReturn(Arrays.asList(comments)).when(repository).findAllByArticleId(eq(articleId));
	}
	
	@Test
	public void givenTwoComments_whenFindAll_thenCommentsFound() {
		
		String articleId = "someArticleId";
		Comment comment1 = comment("123", articleId);
		Comment comment2 = comment("456", articleId);

		givenArticleWithIdExists(articleId);
		givenCommentsForArticle(articleId, comment1, comment2);
		
		List<Comment> found = service.findAllByArticleId(articleId);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(comment1));
		assertTrue(found.contains(comment2));
	}
	
	@Test
	public void givenNoComments_whenFindById_thenExceptionThrown() {
		
		assertThrows(CommentNotFoundException.class, () -> {
			
			givenNoComments();
		
			service.findById("123");
		});
	}
	
	private void givenNoComments() {
		doReturn(Optional.empty()).when(repository).findById(anyString());
	}

	@Test
	public void givenOneMatchingComment_whenFindById_thenCommentFound() {
		
		String articleId = "someArticleId";
		Comment comment = comment("123", articleId);
		
		givenComments(comment);
		
		Comment found = service.findById(comment.getId());
		
		assertEquals(comment, found);
	}
	
	private void givenComments(Comment... comments) {
		
		for (Comment comment: comments) {
			doReturn(Optional.of(comment)).when(repository).findById(eq(comment.getId()));
		}
	}

	@Test
	public void givenOneNonMatchingComment_whenFindById_thenExceptionThrown() {
		
		assertThrows(CommentNotFoundException.class, () -> {
			
			String articleId = "someArticleId";
			Comment nonMatching = comment("123", articleId);
			
			givenComments(nonMatching);
		
			service.findById(nonMatching.getId() + "098");
		});
	}
	
	@Test
	public void givenOneNonMatchingAndOneMatchingComment_whenFindById_thenCommentFound() {
		
		String articleId = "someArticleId";
		Comment nonMatching = comment("123", articleId);
		Comment matching = comment("098", articleId);
		
		givenComments(nonMatching, matching);
		
		Comment found = service.findById(matching.getId());
		
		assertEquals(matching, found);
	}
	
	@Test
	public void givenCommentCanBeSaved_whenSave_thenCommentSaved() {
		
		String articleId = "someArticleId";
		Comment comment = comment("123", articleId);
		Comment expectedSavedComment = comment("123", articleId);

		givenArticleWithIdExists(articleId);
		givenSavedCommentWillBe(expectedSavedComment);
		
		Comment saved = service.save(comment);
		
		assertEquals(expectedSavedComment, saved);
		assertCommentSaved(comment);
	}
	
	private void givenSavedCommentWillBe(Comment comment) {
		doReturn(comment).when(repository).save(any(Comment.class));
	}

	private void assertCommentSaved(Comment comment) {
		verify(repository, times(1)).save(eq(comment));
	}
	
	@Test
	public void givenArticleDoesNotExist_whenSave_thenExceptionThrown() {
		
		assertThrows(ArticleNotFoundException.class, () -> {
			
			String articleId = "someArticleId";
			Comment comment = comment("123", articleId);
			
			givenArticleWithIdDoesNotExist(articleId);
			
			service.save(comment);
		});
	}
	
	@Test
	public void givenCommentCanBeDeleted_whenDeleteById_thenCommentSaved() {
			
		Comment comment = comment("123", "098");
		
		service.deleteById(comment.getId());
		
		assertCommentDeleted(comment);
	}

	private void assertCommentDeleted(Comment comment) {
		verify(repository, times(1)).deleteById(eq(comment.getId()));
	}
}
