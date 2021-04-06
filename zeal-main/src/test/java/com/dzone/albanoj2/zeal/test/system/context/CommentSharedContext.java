package com.dzone.albanoj2.zeal.test.system.context;

import org.springframework.stereotype.Component;

import com.dzone.albanoj2.zeal.domain.Comment;

import io.cucumber.spring.ScenarioScope;

@Component
@ScenarioScope
public class CommentSharedContext extends SharedContext<Comment> {
}
