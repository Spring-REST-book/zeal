package com.dzone.albanoj2.zeal.persistence.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.persistence.comment.CommentRepository;

/**
 * An in-memory implementation of an {@link CommentRepository}. This
 * implementation is intended for testing a deployed JAR application and is not
 * intended for production use.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Repository
public class InMemoryCommentRepository implements CommentRepository {

	private final List<Comment> comments = new ArrayList<>();

	@Override
	public List<Comment> findAllByArticleId(String articleId) {
		return comments.stream()
			.filter(comment -> comment.getArticleId().equals(articleId))
			.collect(Collectors.toList());
	}

	@Override
	public Optional<Comment> findById(String id) {
		return comments.stream()
			.filter(matchesId(id))
			.findFirst();
	}

	private Predicate<? super Comment> matchesId(String id) {
		return comment -> comment.getId().equals(id);
	}

	@Override
	public Comment save(Comment comment) {
		
		if (comment.getId() == null) {
			comment.setId(generateId());
		}
		else {
			deleteById(comment.getId());
		}
		
		comments.add(comment);
		
		return comment;
	}
	
	private static String generateId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void deleteById(String id) {
		comments.removeIf(matchesId(id));
	}
}
