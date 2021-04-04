package com.dzone.albanoj2.zeal.rest.controller;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.dzone.albanoj2.zeal.app.service.ArticleService;
import com.dzone.albanoj2.zeal.app.service.CommentService;
import com.dzone.albanoj2.zeal.rest.RestConfiguration;

@Configuration
@Import(RestConfiguration.class)
public class TestRestConfiguration {

	@MockBean
	private ArticleService articleService;
	
	@MockBean
	private CommentService commentService;
}
