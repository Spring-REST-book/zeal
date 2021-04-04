package com.dzone.albanoj2.zeal.app.service.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzone.albanoj2.zeal.app.service.ArticleService;
import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.persistence.article.ArticleRepository;

/**
 * Article service that utilizes an article repository to manage articles.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Service
public class PersistentArticleService implements ArticleService {
	
	private final ArticleRepository repository;

	@Autowired
	public PersistentArticleService(ArticleRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Article> findAll() {
		return repository.findAll();
	}

	@Override
	public Article findById(String id) {
		return repository.findById(id)
			.orElseThrow(() -> new ArticleNotFoundException(id));
	}

	@Override
	public Article save(Article article) {
		return repository.save(article);
	}

	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}
}
