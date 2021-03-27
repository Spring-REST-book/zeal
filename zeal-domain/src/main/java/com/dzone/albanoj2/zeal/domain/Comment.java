package com.dzone.albanoj2.zeal.domain;

import java.time.LocalDateTime;

/**
 * A comment made by a user on an article.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class Comment {

	private String id;
	private LocalDateTime creationDate;
	private String content;
	
	public Comment() {}

	public Comment(String id, LocalDateTime creationDate, String content) {
		this.id = id;
		this.creationDate = creationDate;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
