package com.dzone.albanoj2.zeal.rest.resource.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.resource.CommentSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.util.TimeUtility;

/**
 * Mapper responsible for converting a save request for a comment (with an
 * possible accompanying ID) into an {@link Comment} domain object.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
@Component
public class CommentSaveRequestResourceMapper
	implements ResourceMapper<Comment, CommentSaveRequestResource> {

	private final TimeUtility timeUtility;

	@Autowired
	public CommentSaveRequestResourceMapper(TimeUtility timeUtility) {
		this.timeUtility = timeUtility;
	}

	@Override
	public Comment to(CommentSaveRequestResource resource) {
		return new Comment(
			resource.getArticleId(),
			timeUtility.now(),
			resource.getContent()
		);
	}

	@Override
	public Comment to(String id, CommentSaveRequestResource resource) {

		Comment comment = to(resource);

		comment.setId(id);

		return comment;
	}
}
