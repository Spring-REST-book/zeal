package com.dzone.albanoj2.zeal.test.system.context;

import org.springframework.stereotype.Component;

import com.dzone.albanoj2.zeal.domain.Article;

import io.cucumber.spring.ScenarioScope;

@Component
@ScenarioScope
public class ArticleSharedContext extends SharedContext<Article> {
}
