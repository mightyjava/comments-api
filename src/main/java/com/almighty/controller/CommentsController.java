package com.almighty.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.almighty.data.request.CommentRequestData;
import com.almighty.data.response.CommentResponseData;
import com.almighty.data.response.ResponseData;
import com.almighty.service.CommentsService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Comments Controller")
public class CommentsController {

	private final CommentsService service;

	@GetMapping("/all")
	public ResponseEntity<ResponseData> allComments(@RequestParam(name = "parentId", required = false) String parentId,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize) {
		try {
			return new ResponseEntity<ResponseData>(
					service.allComments(
							CommentRequestData.builder().parentId(parentId).page(page).pageSize(pageSize).build()),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<ResponseData> saveComment(@RequestBody CommentRequestData requestData,
			@RequestParam(name = "parentId", required = false) String parentId) {
		try {
			requestData.setParentId(parentId);
			return new ResponseEntity<ResponseData>(service.saveOrUpdateComment(requestData), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseData> updateComment(@PathVariable(name = "id", required = false) String id,
			@RequestBody CommentRequestData requestData) {
		try {
			requestData.setId(id);
			return new ResponseEntity<ResponseData>(service.saveOrUpdateComment(requestData), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<ResponseData> deleteComment(@PathVariable(name = "id", required = false) String id) {
		try {
			return new ResponseEntity<ResponseData>(service.deleteComment(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
