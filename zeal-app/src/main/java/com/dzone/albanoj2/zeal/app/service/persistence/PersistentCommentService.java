package com.dzone.albanoj2.zeal.app.service.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dzone.albanoj2.zeal.app.service.CommentService;
import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.persistence.article.ArticleRepository;
import com.dzone.albanoj2.zeal.persistence.comment.CommentRepository;

/**
 * Comment service that utilizes a comment repository to manage comments.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class PersistentCommentService implements CommentService {
	
	private final ArticleRepository articleRepository;
	private final CommentRepository repository;

	@Autowired
	public PersistentCommentService(ArticleRepository articleRepository,
		CommentRepository repository) {
		this.articleRepository = articleRepository;
		this.repository = repository;
	}

	@Override
	public List<Comment> findAllByArticleId(String articleId) {
		
		if (articleRepository.exists(articleId)) {
			return repository.findAllByArticleId(articleId);
		}
		else {
			throw new ArticleNotFoundException(articleId);
		}
	}

	@Override
	public Comment findById(String id) {
		return repository.findById(id)
			.orElseThrow(() -> new CommentNotFoundException(id));
	}

	@Override
	public Comment save(Comment comment) {
		
		String articleId = comment.getArticleId();
		
		if (articleRepository.exists(articleId)) {
			return repository.save(comment);
		}
		else {
			throw new ArticleNotFoundException(articleId);
		}
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}
}
