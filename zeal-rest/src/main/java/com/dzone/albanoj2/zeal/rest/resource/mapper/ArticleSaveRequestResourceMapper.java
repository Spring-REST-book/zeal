package com.dzone.albanoj2.zeal.rest.resource.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.rest.resource.ArticleSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

/**
 * Mapper responsible for converting a save request for an article (with an
 * possible accompanying ID) into an {@link Article} domain object.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Component
public class ArticleSaveRequestResourceMapper
	implements ResourceMapper<Article, ArticleSaveRequestResource> {

	private final TimeUtility timeUtility;

	@Autowired
	public ArticleSaveRequestResourceMapper(TimeUtility timeUtility) {
		this.timeUtility = timeUtility;
	}

	@Override
	public Article to(ArticleSaveRequestResource resource) {

		Article article = new Article();

		article.setCreationDate(timeUtility.now());
		article.setTitle(resource.getTitle());
		article.setContent(resource.getContent());

		return article;
	}

	@Override
	public Article to(String id, ArticleSaveRequestResource resource) {

		Article article = to(resource);

		article.setId(id);

		return article;
	}
}
