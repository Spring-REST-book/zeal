package com.dzone.albanoj2.zeal.persistence.comment;

import java.util.List;
import java.util.Optional;

import com.dzone.albanoj2.zeal.domain.Comment;

/**
 * Repository responsible for persisting {@link Comment} objects.
 * 
 * @author Justin Albano <justin.albano.author@gmail.com>
 *
 * @since 1.0.0
 */
public interface CommentRepository {

	/**
	 * Finds all existing comments associated with the supplied article ID.
	 * 
	 * @param articleId
	 *        The ID of the article to find the comments for.
	 * 
	 * @return
	 *         A list containing the existing comments associated with the
	 *         supplied article ID.
	 */
	public List<Comment> findAllByArticleId(String articleId);

	/**
	 * Finds an existing comment by its ID (if it exists).
	 * 
	 * @param id
	 *        The ID of the desired comment.
	 * 
	 * @return
	 *         An {@link Optional} populated with the comment matching the
	 *         supplied ID if such a comment exists; an empty {@link Optional}
	 *         if there does not exist a comment with the supplied ID.
	 */
	public Optional<Comment> findById(String id);

	/**
	 * Saves a comment. If the supplied comment has a populated ID, the save
	 * operation is treated as an update. If the supplied comment does not have
	 * a populated ID, the save operation is treated as a create.
	 * 
	 * @param comment
	 *        The comment to save.
	 * 
	 * @return
	 *         The saved comment.
	 */
	public Comment save(Comment comment);

	/**
	 * Deletes the comment associated with the supplied ID. If no comment with
	 * the supplied ID exists, then no action is taken. Regardless of whether an
	 * comment did exist with the supplied ID, at the completion of this method,
	 * no comment will exist with the supplied ID.
	 * 
	 * @param id
	 *        The ID of the comment to delete.
	 */
	public void deleteById(String id);
}
