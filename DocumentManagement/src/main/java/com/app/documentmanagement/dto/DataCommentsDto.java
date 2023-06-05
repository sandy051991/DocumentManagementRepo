package com.app.documentmanagement.dto;

import java.util.List;

public class DataCommentsDto {

	private List<FeignCommentsDto> comments;

	public List<FeignCommentsDto> getComments() {
		return comments;
	}

	public void setComments(List<FeignCommentsDto> comments) {
		this.comments = comments;
	}

}
