package com.dzone.albanoj2.zeal.domain;

import java.time.ZonedDateTime;

/**
 * An article published by a user.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class Article {

	private String id;
	private ZonedDateTime creationDate;
	private String title;
	private String content;
	
	public Article() {}

	public Article(String id, ZonedDateTime creationDate, String title, String content) {
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
