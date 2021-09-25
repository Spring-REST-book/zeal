package com.dzone.albanoj2.zeal.rest.assembler;

import org.mockito.ArgumentMatcher;

import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.rest.resource.ArticleResource;
import com.dzone.albanoj2.zeal.test.data.Dates;

public class ArticleResourceAssemblerTest
	extends AbstractResourceAssemblerTest<Article, ArticleResource> {

	@Override
	protected Article createPopulatedDomainObject(String id) {
		return new Article(id, Dates.arbitrary(), "foo" + id, "bar" + id);
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
