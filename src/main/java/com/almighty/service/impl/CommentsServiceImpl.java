package com.almighty.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.almighty.data.enums.Status;
import com.almighty.data.request.CommentRequestData;
import com.almighty.data.response.CommentData;
import com.almighty.data.response.CommentResponseData;
import com.almighty.data.response.CommentsResponseData;
import com.almighty.data.response.ResponseData;
import com.almighty.domain.Comments;
import com.almighty.repository.CommentsRepository;
import com.almighty.service.CommentsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

	private final CommentsRepository repository;

	@Override
	public ResponseData allComments(CommentRequestData requestData) {
		List<CommentData> comments = new ArrayList<>();
		String parentId = requestData.getParentId();
		Page<Comments> commentsPage = repository.findAllByParentId(parentId, PageRequest
				.of(requestData.getPage() - 1, requestData.getPageSize()).withSort(Sort.by("updatedAt").descending()));
		List<Comments> commentList = commentsPage.getContent();
		if (!CollectionUtils.isEmpty(commentList)) {
			commentList.forEach(comment -> {
				CommentData responseData = new CommentData();
				BeanUtils.copyProperties(comment, responseData);
				responseData.setPostedAt(comment.getUpdatedAt());
				comments.add(responseData);
			});
		}
		long totalComments = commentsPage.getTotalElements();
		CommentsResponseData responseData = CommentsResponseData.builder().totalComments(totalComments)
				.comments(comments).build();
		responseData.setStatus(Status.SUCCESS);
		responseData.setMessage(String.format("%s comment(s) fetched successfully.", totalComments));
		return responseData;
	}

	@Override
	public ResponseData saveOrUpdateComment(CommentRequestData requestData) {
		Comments comment;
		String message = null, commentId = requestData.getId();
		Date date = new Date();
		if (StringUtils.hasText(commentId)) {
			Optional<Comments> commentOp = repository.findById(commentId);
			if (!commentOp.isPresent()) {
				return new ResponseData(Status.FAILURE, "comment id is invalid.");
			}
			comment = commentOp.get();
			comment.setText(requestData.getText());
			comment.setUpdatedAt(date);
			message = "comment updated successfully.";
		} else {
			comment = Comments.builder().text(requestData.getText()).createdAt(date).updatedAt(date).build();
			String parentId = requestData.getParentId();
			if (StringUtils.hasText(parentId)) {
				Optional<Comments> parentCommentOp = repository.findById(parentId);
				if (!parentCommentOp.isPresent()) {
					return new ResponseData(Status.FAILURE, "parent comment id is invalid.");
				}
				Comments parentComment = parentCommentOp.get();
				parentComment.setTotalSubComments(parentComment.getTotalSubComments() + 1);
				parentComment.setUpdatedAt(date);
				repository.save(parentComment);
				comment.setParentId(parentId);
			}
			message = "comment saved successfully.";
		}
		comment = repository.save(comment);
		CommentResponseData responseData = CommentResponseData.builder().build();
		BeanUtils.copyProperties(comment, responseData);
		responseData.setPostedAt(date);
		responseData.setStatus(Status.SUCCESS);
		responseData.setMessage(message);
		return responseData;
	}

	@Override
	public ResponseData deleteComment(String id) {
		Status status;
		String message = null;
		Optional<Comments> commentOp = repository.findById(id);
		if (commentOp.isPresent()) {
			repository.delete(commentOp.get());
			status = Status.SUCCESS;
			message = "comment deleted successfully.";
		} else {
			status = Status.FAILURE;
			message = "comment id is invalid.";
		}
		return new ResponseData(status, message);
	}

}
