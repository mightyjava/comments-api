package com.almighty.data.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentRequestData {
	@Schema(hidden = true)
	private String id;
	private String text;
	@Schema(hidden = true)
	private String parentId;
	@Schema(hidden = true)
	private int page;
	@Schema(hidden = true)
	private int pageSize;
}
