package com.app.documentmanagement.feign.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "postComments", url = "https://jsonplaceholder.typicode.com")
public interface PostsCommentsFeignUtil {

	@GetMapping("/users/{documentId}/posts")
	public String getPostsByDocId(@PathVariable(value = "documentId") Long documentId);
	
	@GetMapping("/posts/{postId}/comments")
	public String getCommentsByPostId(@PathVariable(value = "postId") Long postId);

}
