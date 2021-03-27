package com.dzone.albanoj2.zeal.domain;

import java.time.LocalDateTime;

/**
 * An article published by a user.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class Article {

	private String id;
	private LocalDateTime creationDate;
	private String title;
	private String content;
	
	public Article() {}

	public Article(String id, LocalDateTime creationDate, String title, String content) {
		this.id = id;
		this.creationDate = creationDate;
		this.title = title;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
