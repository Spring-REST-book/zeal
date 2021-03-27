package com.dzone.albanoj2.zeal.app.service;

import java.util.List;

import com.dzone.albanoj2.zeal.app.service.error.CommentNotFoundException;
import com.dzone.albanoj2.zeal.domain.Comment;

/**
 * Services responsible for managing all of the comments in the application.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public interface CommentService {

	/**
	 * Finds all of the existing comments associated with the supplied article
	 * ID.
	 * 
	 * @param articleId
	 *        The ID of the article to find the comments for.
	 * 
	 * @return
	 *         A list of the existing comments associated with the supplied
	 *         article ID.
	 */
	public List<Comment> findAllByArticleId(String articleId);

	/**
	 * Finds the comment with the supplied ID if it exists.
	 * 
	 * @param id
	 *        The ID of the desired comment.
	 * 
	 * @throws CommentNotFoundException
	 *         A comment with the supplied ID does not exist.
	 * 
	 * @return
	 *         The comment associated with the supplied ID (if it exists).
	 */
	public Comment findById(String id);

	/**
	 * Saves a comment.
	 * 
	 * @param comment
	 *        The comment to save.
	 * 
	 * @return
	 *         The saved comment.
	 */
	public Comment save(Comment comment);

	/**
	 * Deletes the comment associated with the supplied ID if the comment
	 * exists.
	 * 
	 * @param id
	 *        The ID of the comment to delete.
	 */
	public void deleteById(String id);
}
