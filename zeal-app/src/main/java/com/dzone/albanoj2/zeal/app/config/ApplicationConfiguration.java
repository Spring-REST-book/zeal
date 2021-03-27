package com.dzone.albanoj2.zeal.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dzone.albanoj2.zeal.app.service.ArticleService;
import com.dzone.albanoj2.zeal.app.service.CommentService;
import com.dzone.albanoj2.zeal.app.service.persistence.PersistentArticleService;
import com.dzone.albanoj2.zeal.app.service.persistence.PersistentCommentService;
import com.dzone.albanoj2.zeal.persistence.article.ArticleRepository;
import com.dzone.albanoj2.zeal.persistence.comment.CommentRepository;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public ArticleService articleService(ArticleRepository repository) {
		return new PersistentArticleService(repository);
	}

	@Bean
	public CommentService commentService(ArticleRepository articleRepository, CommentRepository repository) {
		return new PersistentCommentService(articleRepository, repository);
	}
}
