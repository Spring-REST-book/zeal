package com.dzone.albanoj2.zeal.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dzone.albanoj2.zeal.app.service.ArticleService;
import com.dzone.albanoj2.zeal.app.service.CommentService;
import com.dzone.albanoj2.zeal.domain.Article;
import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.assembler.ArticleResourceAssembler;
import com.dzone.albanoj2.zeal.rest.assembler.CommentResourceAssembler;
import com.dzone.albanoj2.zeal.rest.resource.ArticleResource;
import com.dzone.albanoj2.zeal.rest.resource.ArticleSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.resource.CommentResource;
import com.dzone.albanoj2.zeal.rest.resource.CommentSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.resource.mapper.ArticleSaveRequestResourceMapper;
import com.dzone.albanoj2.zeal.rest.resource.mapper.CommentSaveRequestResourceMapper;

@RestController
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService service;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private ArticleResourceAssembler assembler;

	@Autowired
	private CommentResourceAssembler commentAssembler;
	
	@Autowired
	private ArticleSaveRequestResourceMapper requestMapper;
	
	@Autowired
	private CommentSaveRequestResourceMapper commentRequestMapper;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ArticleResource> findAll() {
		
		List<Article> articles = service.findAll();
		
		return assembler.toResources(articles);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ArticleResource findById(@PathVariable String id) {
		
		Article article = service.findById(id);
		
		return assembler.toResource(article);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ArticleResource create(@RequestBody ArticleSaveRequestResource request) {
		
		Article article = requestMapper.to(request);
		Article created = service.save(article);
		
		return assembler.toResource(created);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ArticleResource update(@PathVariable String id, @RequestBody ArticleSaveRequestResource request) {
		
		Article article = requestMapper.to(id, request);
		Article updated = service.save(article);
		
		return assembler.toResource(updated);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		service.deleteById(id);
	}
	
	@GetMapping("/{id}/comment")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentResource> findAllComments(@PathVariable String id) {
		
		List<Comment> comments = commentService.findAllByArticleId(id);
		
		return commentAssembler.toResources(comments);
	}
	
	@PostMapping("/{id}/comment")
	@ResponseStatus(HttpStatus.CREATED)
	public CommentResource postComment(@PathVariable String id, @RequestBody CommentSaveRequestResource request) {
		
		Comment comment = commentRequestMapper.to(request);
		Comment created = commentService.save(comment);
		
		return commentAssembler.toResource(created);
	}
}
