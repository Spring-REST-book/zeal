package com.dzone.albanoj2.zeal.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dzone.albanoj2.zeal.app.service.CommentService;
import com.dzone.albanoj2.zeal.domain.Comment;
import com.dzone.albanoj2.zeal.rest.assembler.CommentResourceAssembler;
import com.dzone.albanoj2.zeal.rest.resource.CommentResource;
import com.dzone.albanoj2.zeal.rest.resource.CommentSaveRequestResource;
import com.dzone.albanoj2.zeal.rest.resource.mapper.CommentSaveRequestResourceMapper;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService service;

	@Autowired
	private CommentResourceAssembler assembler;
	
	@Autowired
	private CommentSaveRequestResourceMapper requestMapper;
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CommentResource findById(@PathVariable String id) {
		
		Comment comment = service.findById(id);
		
		return assembler.toResource(comment);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CommentResource update(@PathVariable String id, @RequestBody CommentSaveRequestResource request) {
		
		Comment comment = requestMapper.to(id, request);
		Comment updated = service.save(comment);
		
		return assembler.toResource(updated);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String id) {
		service.deleteById(id);
	}
}
