package com.dzone.albanoj2.zeal.rest.resource.mapper;

import static java.util.Objects.requireNonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.rest.error.InvalidRequestDataException;
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
		
		requireNonNull(resource, "Resource cannot be null.");
		
		try {
			return new Article(
				timeUtility.now(),
				resource.getTitle(),
				resource.getContent()
			);
		}
		catch (NullPointerException | IllegalArgumentException e) {
			throw new InvalidRequestDataException(e);
		}
	}

	@Override
	public Article to(String id, ArticleSaveRequestResource resource) {
		
		requireNonNull(id, "ID cannot be null.");

		Article article = to(resource);

		article.setId(id);

		return article;
	}
}
