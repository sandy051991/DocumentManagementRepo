package com.app.documentmanagement.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.documentmanagement.feign.util.PostsCommentsFeignUtil;
import com.app.documentmanagement.model.DocumentEntity;
import com.app.documentmanagement.service.DocumentService;

@RestController()
@RequestMapping(value = "/info/pdf/documents/management/app/v1")
public class DocumentController {

	@Autowired
	private DocumentService service;

	@Autowired
	private PostsCommentsFeignUtil util;

	@PostMapping("/save")
	public ResponseEntity<DocumentEntity> uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
		String extn = FilenameUtils.getExtension(file.getOriginalFilename());
		if (extn.equalsIgnoreCase("PDF")) {
			return new ResponseEntity<>(service.save(file), HttpStatus.CREATED);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping("/document/{id}")
	public ResponseEntity<DocumentEntity> getDocument(@PathVariable String id) {
		return new ResponseEntity<>(service.getDocumentById(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<DocumentEntity>> getDocumentList() {
		return new ResponseEntity<>(service.getDocumentList(), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDocumentById(@PathVariable String id) {
		service.deleteDocumentById(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@PutMapping("/update")
	public String updateDocumentById(@RequestParam("id") String id, @RequestParam("file") MultipartFile file)
			throws IOException {
		String extn = FilenameUtils.getExtension(file.getOriginalFilename());
		if (extn.equalsIgnoreCase("PDF")) {
			DocumentEntity entity = service.getDocumentById(id);
			if (entity != null) {
				service.UpdateDocumentById(entity, file);
				return "Document updated sucessfully";
			}
			return "No document found by the given id::" + id;
		}
		return "Document type is not supported. Only pdf document is allowed";
	}

	@GetMapping("/document/{docId}/posts")
	public String getPostsByDocId(@PathVariable String docId) {
		return util.getPostsByDocId(docId);
	}

	@GetMapping("/posts/{postId}/comments")
	public String getCommentsData(@PathVariable String postId) {
		return util.getCommentsByPostId(postId);
	}

}