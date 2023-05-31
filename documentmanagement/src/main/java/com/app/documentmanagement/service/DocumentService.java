package com.app.documentmanagement.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.app.documentmanagement.feign.util.PostsCommentsFeignUtil;
import com.app.documentmanagement.model.DocumentEntity;
import com.app.documentmanagement.repository.DocumentRepository;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository repository;
	
	@Autowired
	private PostsCommentsFeignUtil util;

	public DocumentEntity save(MultipartFile file) throws IOException {
		DocumentEntity entity = new DocumentEntity();
		entity.setDocumentData(file.getBytes());
		entity.setDocumentName(file.getOriginalFilename());
		entity.setDocumentType(file.getContentType());
		return repository.save(entity);
	}

	public DocumentEntity getDocumentById(Long id) {
		Optional<DocumentEntity> documentOptional = repository.findById(id);
		if (documentOptional.isPresent()) {
			return documentOptional.get();
		}
		return null;
	}

	public List<DocumentEntity> getDocumentList() {
		return repository.findAll();
	}

	public void deleteDocumentById(Long id) {
		repository.deleteById(id);
	}

	public DocumentEntity UpdateDocumentById(DocumentEntity entity, MultipartFile file) throws IOException {
		entity.setDocumentName(file.getOriginalFilename());
		entity.setDocumentType(file.getContentType());
		entity.setDocumentData(file.getBytes());
		return repository.save(entity);
	}
	
	@Retry(name = "retryPostService", fallbackMethod = "fallbackService")
	@GetMapping("/document/{docId}/posts")
	public String getPostsByDocId(@PathVariable Long docId) {
		return util.getPostsByDocId(docId);
	}

	@Retry(name = "retryCommentService", fallbackMethod = "fallbackService")
	@GetMapping("/posts/{postId}/comments")
	public String getCommentsData(@PathVariable Long postId) {
		return util.getCommentsByPostId(postId);
	}
	
	public String fallbackService(Throwable t) {
		return "jsonplaceholder downstream service is not working";
	}
	
	

}
