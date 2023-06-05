package com.app.documentmanagement.dto;

import java.util.List;

public class DataPostsDto {

	private List<FeignPostsDto> posts;

	public List<FeignPostsDto> getPosts() {
		return posts;
	}

	public void setPosts(List<FeignPostsDto> posts) {
		this.posts = posts;
	}

}
