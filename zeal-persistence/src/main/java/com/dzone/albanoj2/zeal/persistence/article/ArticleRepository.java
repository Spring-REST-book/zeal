package com.dzone.albanoj2.zeal.persistence.article;

import java.util.List;
import java.util.Optional;

import com.dzone.albanoj2.zeal.domain.Article;

/**
 * Repository responsible for persisting {@link Article} objects.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public interface ArticleRepository {

	/**
	 * Finds all existing articles.
	 * 
	 * @return
	 *         A list containing the existing articles.
	 */
	public List<Article> findAll();

	/**
	 * Finds an existing article by its ID (if it exists).
	 * 
	 * @param id
	 *        The ID of the desired article.
	 * 
	 * @return
	 *         An {@link Optional} populated with the article matching the
	 *         supplied ID if such an article exists; an empty {@link Optional}
	 *         if there does not exist an article with the supplied ID.
	 */
	public Optional<Article> findById(String id);

	/**
	 * Saves an article. If the supplied article has a populated ID, the save
	 * operation is treated as an update. If the supplied article does not have
	 * a populated ID, the save operation is treated as a create.
	 * 
	 * @param article
	 *        The article to save.
	 * 
	 * @return
	 *         The saved article.
	 */
	public Article save(Article article);

	/**
	 * Deletes the article associated with the supplied ID. If no article with
	 * the supplied ID exists, then no action is taken. Regardless of whether an
	 * article did exist with the supplied ID, at the completion of this method,
	 * no article will exist with the supplied ID.
	 * 
	 * @param id
	 *        The ID of the article to delete.
	 */
	public void deleteById(String id);

	/**
	 * Checks if an article exists with the supplied ID.
	 * 
	 * @param id
	 *        The ID of the desired article.
	 * 
	 * @return
	 *         True if an article exists with the supplied ID; false otherwise.
	 */
	public boolean exists(String id);
}
