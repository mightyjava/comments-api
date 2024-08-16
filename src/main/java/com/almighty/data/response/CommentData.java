package com.almighty.data.response;

import java.util.Date;

import lombok.Data;

@Data
public class CommentData {
	private String id;
	private String text;
	private String parentId;
	private int totalSubComments;
	private Date postedAt;
}
