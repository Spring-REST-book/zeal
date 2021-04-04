package com.dzone.albanoj2.zeal.rest.assembler;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.mockito.ArgumentMatcher;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.rest.resource.ArticleResource;

public class ArticleResourceAssemblerTest
	extends AbstractResourceAssemblerTest<Article, ArticleResource> {

	@Override
	protected Article createPopulatedDomainObject(String id) {
		return new Article(id, date(), "foo" + id, "bar" + id);
	}
	
	private static ZonedDateTime date() {
		return LocalDateTime.of(2021, Month.MARCH, 30, 17, 41, 00)
			.atZone(ZoneOffset.UTC);
	}

	@Override
	protected ResourceAssembler<Article, ArticleResource> getAssembler() {
		return new ArticleResourceAssembler();
	}

	@Override
	protected ArgumentMatcher<ArticleResource> matches(Article domainObject) {
		return article -> {
			return domainObject.getId().equals(article.getId()) &&
				domainObject.getCreationEpoch() == article.getCreationTimestamp() &&
				domainObject.getTitle().equals(article.getTitle()) &&
				domainObject.getContent().equals(article.getContent());
		};
	}
}
