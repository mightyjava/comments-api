package com.almighty.data.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentsResponseData extends ResponseData {

	private long totalComments;
	private List<CommentData> comments;
}
