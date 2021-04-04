package com.dzone.albanoj2.zeal.rest.assembler;

import org.springframework.stereotype.Component;

import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.resource.CommentResource;

/**
 * Assembler response for converting {@link Comment} objects into
 * {@link CommenteResource} objects.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Component
public class CommentResourceAssembler extends AbstractResourceAssembler<Comment, CommentResource> {

	@Override
	public CommentResource toResource(Comment comment) {

		CommentResource resource = new CommentResource();

		resource.setId(comment.getId());
		resource.setArticleId(comment.getArticleId());
		resource.setCreationTimestamp(comment.getCreationEpoch());
		resource.setContent(comment.getContent());

		return resource;
	}
}
