package com.dzone.albanoj2.zeal.rest.assembler;

import org.mockito.ArgumentMatcher;

import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.resource.CommentResource;
import com.dzone.albanoj2.zeal.test.data.Dates;

public class CommentResourceAssemblerTest 
	extends AbstractResourceAssemblerTest<Comment, CommentResource> {

	@Override
	protected Comment createPopulatedDomainObject(String id) {
		return new Comment(id, "articleId" + id, Dates.arbitrary(), "bar" + id);
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
