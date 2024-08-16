package com.almighty.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document(collection = "comment")
public class Comments {
	@Id
	private String id;
	private String text;
	private int totalSubComments;
	private String parentId;
	private Date createdAt;
	private Date updatedAt;
}
