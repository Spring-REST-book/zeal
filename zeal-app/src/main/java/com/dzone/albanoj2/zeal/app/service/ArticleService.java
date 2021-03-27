package com.dzone.albanoj2.zeal.app.service;

import java.util.List;

import com.dzone.albanoj2.zeal.app.service.error.ArticleNotFoundException;
import com.dzone.albanoj2.zeal.domain.Article;

/**
 * Services responsible for managing all of the articles in the application.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public interface ArticleService {

	/**
	 * Finds all of the existing articles.
	 * 
	 * @return
	 *         A list of the existing articles.
	 */
	public List<Article> findAll();

	/**
	 * Finds the article with the supplied ID if it exists.
	 * 
	 * @param id
	 *        The ID of the desired article.
	 * 
	 * @throws ArticleNotFoundException
	 *         An article with the supplied ID does not exist.
	 * 
	 * @return
	 *         The article associated with the supplied ID (if it exists).
	 */
	public Article findById(String id);

	/**
	 * Saves an article.
	 * 
	 * @param article
	 *        The article to save.
	 * 
	 * @return
	 *         The saved article.
	 */
	public Article save(Article article);

	/**
	 * Deletes the article associated with the supplied ID if the article
	 * exists.
	 * 
	 * @param id
	 *        The ID of the article to delete.
	 */
	public void deleteById(String id);
}
