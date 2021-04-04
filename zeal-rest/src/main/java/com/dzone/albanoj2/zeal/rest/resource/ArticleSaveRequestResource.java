package com.dzone.albanoj2.zeal.rest.resource;

/**
 * Resource representing a request to save an {@link Article}.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public class ArticleSaveRequestResource {

	private String title;
	private String content;

	public ArticleSaveRequestResource() {
	}

	public ArticleSaveRequestResource(String title, String content) {
		this.title = title;
		this.content = content;
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
