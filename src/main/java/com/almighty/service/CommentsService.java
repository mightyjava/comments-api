package com.almighty.service;

import com.almighty.data.request.CommentRequestData;
import com.almighty.data.response.ResponseData;

public interface CommentsService {

	ResponseData allComments(CommentRequestData requestData);

	ResponseData saveOrUpdateComment(CommentRequestData requestData);

	ResponseData deleteComment(String id);

}
