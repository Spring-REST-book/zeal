package com.dzone.albanoj2.zeal.persistence.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.persistence.article.ArticleRepository;

/**
 * An in-memory implementation of an {@link ArticleRepository}. This
 * implementation is intended for testing a deployed JAR application and is not
 * intended for production use.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Repository
public class InMemoryArticleRepository implements ArticleRepository {
	
	private final List<Article> articles = new ArrayList<>();

	@Override
	public List<Article> findAll() {
		return articles;
	}

	@Override
	public Optional<Article> findById(String id) {
		return articles.stream()
			.filter(matchesId(id))
			.findFirst();
	}

	private static Predicate<? super Article> matchesId(String id) {
		return article -> article.getId().equals(id);
	}

	@Override
	public Article save(Article article) {
		
		if (article.getId() == null) {
			article.setId(generateId());
		}
		else {
			deleteById(article.getId());
		}
		
		articles.add(article);
		
		return article;
	}
	
	private static String generateId() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void deleteById(String id) {
		articles.removeIf(matchesId(id));
	}

	@Override
	public boolean exists(String id) {
		return articles.stream()
			.anyMatch(matchesId(id));
	}
}
