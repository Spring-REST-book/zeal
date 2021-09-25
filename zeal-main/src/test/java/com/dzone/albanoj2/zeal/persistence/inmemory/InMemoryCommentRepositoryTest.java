package com.dzone.albanoj2.zeal.persistence.inmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.test.data.Dates;

public class InMemoryCommentRepositoryTest {

	private InMemoryCommentRepository repository;
	
	@BeforeEach
	public void setUp() {
		repository = new InMemoryCommentRepository();
	}
	
	@Test
	public void givenNoCommentsSaved_whenFindAllByArticleId_thenNoCommentsFound() {
		
		String articleId = "foo";
		
		List<Comment> found = repository.findAllByArticleId(articleId);
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenOneCommentSaved_whenFindAllByArticleId_thenOneCommentFound() {
		
		Comment comment = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");
		
		Comment saved = repository.save(comment);
		
		List<Comment> found = repository.findAllByArticleId(saved.getArticleId());
		
		assertEquals(1, found.size());
		assertTrue(found.contains(comment));
	}

	@Test
	public void givenTwoCommentsSaved_whenFindAllByArticleId_thenTwoCommentsFound() {
		
		String articleId = "foo";
		Comment comment1 = new Comment(null, articleId, Dates.arbitraryWithDay(1), "bar");
		Comment comment2 = new Comment(null, articleId, Dates.arbitraryWithDay(2), "something");
		
		repository.save(comment1);
		repository.save(comment2);
		
		List<Comment> found = repository.findAllByArticleId(articleId);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(comment1));
		assertTrue(found.contains(comment2));
	}
	
	@Test
	public void givenTwoCommentsWithDifferentArticleIdsSaved_whenFindAllByArticleId_thenOneCommentFound() {
		
		Comment matching = new Comment(null, "baz", Dates.arbitraryWithDay(2), "something");
		Comment nonMatching = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");

		repository.save(nonMatching);
		Comment savedMatching = repository.save(matching);
		
		List<Comment> found = repository.findAllByArticleId(savedMatching.getArticleId());
		
		assertEquals(1, found.size());
		assertTrue(found.contains(matching));
	}
	
	@Test
	public void givenNoCommentsSaved_whenFindById_thenCommentNotFound() {
		
		Optional<Comment> found = repository.findById("foo");
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenOneMatchingCommentSaved_whenFindById_thenCommentFound() {
		
		Comment comment = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");
		
		Comment saved = repository.save(comment);

		Optional<Comment> found = repository.findById(saved.getId());
		
		assertTrue(found.isPresent());
		assertEquals(comment, found.get());
	}
	
	@Test
	public void givenOneNonMatchingCommentSaved_whenFindById_thenCommentFound() {

		Comment comment = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");
		
		Comment saved = repository.save(comment);

		Optional<Comment> found = repository.findById(saved.getId() + "foo");
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenOneMatchingAndOneNonMatching_whenFindById_thenCommentFound() {

		Comment nonMatching = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");
		Comment matching = new Comment(null, "baz", Dates.arbitraryWithDay(2), "something");
		
		repository.save(nonMatching);
		Comment savedMatching = repository.save(matching);

		Optional<Comment> found = repository.findById(savedMatching.getId());
		
		assertTrue(found.isPresent());
		assertEquals(matching, found.get());
	}
	
	@Test
	public void givenCommentHasNoId_whenSave_thenCommentCreated() {
		
		Comment comment = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");
		
		Comment saved = repository.save(comment);

		Optional<Comment> found = repository.findById(saved.getId());
		
		assertTrue(found.isPresent());
		assertCommentMatches(comment, found);
	}

	private static void assertCommentMatches(Comment expected, Optional<Comment> actual) {
		
		Comment comment = actual.get();
		
		assertEquals(expected.getId(), comment.getId());
		assertEquals(expected.getArticleId(), comment.getArticleId());
		assertEquals(expected.getCreationDate(), comment.getCreationDate());
		assertEquals(expected.getContent(), comment.getContent());
	}
	
	@Test
	public void givenCommentHasIdAndNoCommentExistsWithId_whenSave_thenCommentCreated() {
		
		Comment comment = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");
		
		Comment saved = repository.save(comment);

		Optional<Comment> found = repository.findById(saved.getId());
		
		assertTrue(found.isPresent());
		assertCommentMatches(comment, found);
	}
	
	@Test
	public void givenCommentHasIdAndCommentAlreadyExistsWithId_whenSave_thenCommentUpdated() {
		
		Comment existing = new Comment("someId", "foo", Dates.arbitraryWithDay(1), "bar");
		Comment update = new Comment(existing.getId(), "baz", Dates.arbitraryWithDay(2), "something");
		
		Comment savedExisting = repository.save(existing);
		repository.save(update);

		Optional<Comment> found = repository.findById(savedExisting.getId());
		
		assertTrue(found.isPresent());
		assertCommentMatches(update, found);
	}
	
	@Test
	public void givenCommentDoesNotExist_whenDeleteById_thenCommentDeleted() {
		
		String id = "foo";
		
		repository.deleteById(id);
		
		Optional<Comment> found = repository.findById(id);
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenCommentExists_whenDeleteById_thenCommentDeleted() {
		
		Comment comment = new Comment(null, "foo", Dates.arbitraryWithDay(1), "bar");
		
		Comment saved = repository.save(comment);
		
		repository.deleteById(comment.getId());
		
		Optional<Comment> found = repository.findById(saved.getId());
		
		assertTrue(found.isEmpty());
	}
	
	@Test
	public void givenNoArticles_whenDeleteAll_thenNoArticlesRemain() {
		
		repository.deleteAll();
		
		assertTrue(repository.findAllByArticleId("foo").isEmpty());
	}
	
	@Test
	public void givenArticleExists_whenDeleteAll_thenNoArticlesRemain() {
		
		String articleId = "foo";
		Comment comment = new Comment(null, articleId, Dates.arbitraryWithDay(1), "bar");
		
		repository.save(comment);
		
		repository.deleteAll();
		
		assertTrue(repository.findAllByArticleId(articleId).isEmpty());
	}
}
