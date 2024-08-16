package com.almighty.data.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class CommentResponseData extends ResponseData {
	private String id;
	private String text;
	private String parentId;
	private Date postedAt;
}
