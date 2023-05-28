package com.demo.springApp.feign.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "feignDemo", url = "https://jsonplaceholder.typicode.com")
public interface PostsFeignServiceUtil {

	@GetMapping("/users/{documentId}/posts")
	public String getPostsByDocId(@PathVariable String documentId);
	
	@GetMapping("/posts/{postId}/comments")
	public String getCommentsByPostId(@PathVariable String postId);

}
