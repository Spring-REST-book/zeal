package com.dzone.albanoj2.zeal.rest.resource;

import com.dzone.albanoj2.zeal.domain.Comment;

/**
 * Resource representing a request to save a {@link Comment}.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class CommentSaveRequestResource {

	private String articleId;
	private String content;

	public CommentSaveRequestResource() {
	}

	public CommentSaveRequestResource(String articleId, String content) {
		this.articleId = articleId;
		this.content = content;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
