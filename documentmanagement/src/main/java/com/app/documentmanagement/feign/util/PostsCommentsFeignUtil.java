package com.app.documentmanagement.feign.util;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.documentmanagement.dto.FeignCommentsDto;
import com.app.documentmanagement.dto.FeignPostsDto;

@FeignClient(value = "postComments", url = "https://jsonplaceholder.typicode.com")
public interface PostsCommentsFeignUtil {

	@GetMapping("/users/{documentId}/posts")
	public List<FeignPostsDto> getPostsByDocId(@PathVariable(value = "documentId") Long documentId);
	
	@GetMapping("/posts/{postId}/comments")
	public List<FeignCommentsDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId);

}
