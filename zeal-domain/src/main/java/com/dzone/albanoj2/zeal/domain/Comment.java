package com.dzone.albanoj2.zeal.domain;

import java.time.ZonedDateTime;

/**
 * A comment made by a user on an article.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class Comment {

	private String id;
	private String articleId;
	private ZonedDateTime creationDate;
	private String content;
	
	public Comment() {}

	public Comment(String id, String articleId, ZonedDateTime creationDate, String content) {
		this.id = id;
		this.articleId = articleId;
		this.creationDate = creationDate;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public ZonedDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	public long getCreationEpoch() {
		
		if (getCreationDate() == null) {
			return 0;
		}
		else {
			return getCreationDate().toInstant().toEpochMilli();
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
