package com.dzone.albanoj2.zeal.rest.assembler;

import org.springframework.stereotype.Component;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.rest.resource.ArticleResource;

/**
 * Assembler response for converting {@link Article} objects into
 * {@link ArticleResource} objects.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Component
public class ArticleResourceAssembler extends AbstractResourceAssembler<Article, ArticleResource> {

	@Override
	public ArticleResource toResource(Article article) {

		ArticleResource resource = new ArticleResource();

		resource.setId(article.getId());
		resource.setCreationTimestamp(article.getCreationEpoch());
		resource.setTitle(article.getTitle());
		resource.setContent(article.getContent());

		return resource;
	}
}
