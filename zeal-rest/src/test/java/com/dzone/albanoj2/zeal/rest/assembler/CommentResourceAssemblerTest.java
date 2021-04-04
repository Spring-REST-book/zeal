package com.dzone.albanoj2.zeal.rest.assembler;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.mockito.ArgumentMatcher;

import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.resource.CommentResource;

public class CommentResourceAssemblerTest extends AbstractResourceAssemblerTest<Comment, CommentResource> {

	@Override
	protected Comment createPopulatedDomainObject(String id) {
		return new Comment(id, "articleId" + id, date(), "bar" + id);
	}
	
	private static ZonedDateTime date() {
		return LocalDateTime.of(2021, Month.MARCH, 30, 17, 41, 00)
			.atZone(ZoneOffset.UTC);
	}

	@Override
	protected ResourceAssembler<Comment, CommentResource> getAssembler() {
		return new CommentResourceAssembler();
	}

	@Override
	protected ArgumentMatcher<CommentResource> matches(Comment domainObject) {
		return article -> {
			return domainObject.getId().equals(article.getId()) &&
				domainObject.getArticleId().equals(article.getArticleId()) &&
				domainObject.getCreationEpoch() == article.getCreationTimestamp() &&
				domainObject.getContent().equals(article.getContent());
		};
	}
}
