package com.app.documentmanagement.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.app.documentmanagement.dto.DataCommentsDto;
import com.app.documentmanagement.dto.DataPostsDto;
import com.app.documentmanagement.dto.ResponseCommentsDto;
import com.app.documentmanagement.dto.ResponsePostsDto;
import com.app.documentmanagement.feign.util.PostsCommentsFeignUtil;
import com.app.documentmanagement.model.DocumentEntity;
import com.app.documentmanagement.repository.DocumentRepository;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class DocumentService {
	
	Logger log = LoggerFactory.getLogger(DocumentService.class);

	@Autowired
	private DocumentRepository repository;
	
	@Autowired
	private PostsCommentsFeignUtil util;

	public DocumentEntity save(MultipartFile file, String correlationId) throws IOException {
		log.info("DocumentServices inside save() method");
		DocumentEntity entity = new DocumentEntity();
		entity.setDocumentData(file.getBytes());
		entity.setDocumentName(file.getOriginalFilename());
		entity.setDocumentType(file.getContentType());
		entity.setDocumentCorrelationId(correlationId);
		entity.setDocumentCreateTime(LocalDateTime.now());
		return repository.save(entity);
	}

	public DocumentEntity getDocumentById(Long id) {
		log.info("DocumentServices inside getDocumentById() method");
		Optional<DocumentEntity> documentOptional = repository.findById(id);
		if (documentOptional.isPresent()) {
			return documentOptional.get();
		}
		log.info("DocumentServices inside getDocumentById() method - no document found");
		return null;
	}

	public List<DocumentEntity> getDocumentList() {
		log.info("DocumentServices inside getDocumentList() method");
		return repository.findAll();
	}

	public void deleteDocumentById(Long id) {
		log.info("DocumentServices inside deleteDocumentById() method");
		repository.deleteById(id);
	}

	public DocumentEntity UpdateDocumentById(DocumentEntity entity, MultipartFile file) throws IOException {
		log.info("DocumentServices inside UpdateDocumentById() method");
		entity.setDocumentName(file.getOriginalFilename());
		entity.setDocumentType(file.getContentType());
		entity.setDocumentData(file.getBytes());
		return repository.save(entity);
	}
	
	@Retry(name = "retryPostService", fallbackMethod = "fallbackPostService")
	@GetMapping("/document/{docId}/posts")
	public ResponsePostsDto getPostsByDocId(@PathVariable Long docId) {
		log.info("DocumentServices inside getCommentsData() method");
		ResponsePostsDto dto = new ResponsePostsDto();
		dto.setCorrelationId(UUID.randomUUID().toString());
		dto.setDateTime(LocalDateTime.now());
		DataPostsDto dataDto = new DataPostsDto();
		dataDto.setPosts(util.getPostsByDocId(docId));
		dto.setData(dataDto);
		return dto;
	}

	public ResponsePostsDto fallbackPostService(Long id, Throwable ex) {
		log.info("DocumentServices fallbackPostService() - fallback is called because jsonplaceholder downstream service is down "+ex.getMessage());
		ResponsePostsDto dto = new ResponsePostsDto();
		dto.setCorrelationId(UUID.randomUUID().toString());
		dto.setDateTime(LocalDateTime.now());
		DataPostsDto dataDto = new DataPostsDto();
		dataDto.setPosts(null);
		dto.setData(dataDto);
		return dto;
	}
	
	@Retry(name = "retryCommentService", fallbackMethod = "fallbackCommentService")
	@GetMapping("/posts/{postId}/comments")
	public ResponseCommentsDto getCommentsData(@PathVariable Long postId) {
		log.info("DocumentServices inside getCommentsData() method");
		ResponseCommentsDto dto = new ResponseCommentsDto();
		dto.setCorrelationId(UUID.randomUUID().toString());
		dto.setDateTime(LocalDateTime.now());
		DataCommentsDto dataDto = new DataCommentsDto();
		dataDto.setComments(util.getCommentsByPostId(postId));
		dto.setData(dataDto);
		return dto;
	}
	
	public ResponseCommentsDto fallbackCommentService(Long id, Throwable ex) {
		log.info("DocumentServices fallbackCommentService() - fallback is called because jsonplaceholder downstream service is down "+ex.getMessage());
		ResponseCommentsDto dto = new ResponseCommentsDto();
		dto.setCorrelationId(UUID.randomUUID().toString());
		dto.setDateTime(LocalDateTime.now());
		DataCommentsDto dataDto = new DataCommentsDto();
		dataDto.setComments(null);
		dto.setData(dataDto);
		return dto;
	}
	
	

}
