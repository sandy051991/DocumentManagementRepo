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

import com.app.documentmanagement.dto.ErrorDto;
import com.app.documentmanagement.feign.util.Constant;
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
		if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("PDF")) {
			ErrorDto errorDto = new ErrorDto(Constant.FORMAT_NOR_SUPPORTED_CODE, Constant.FORMAT_NOR_SUPPORTED_MSG,
					Constant.FORMAT_NOR_SUPPORTED_DTL);
			return new ResponseEntity(errorDto, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(service.save(file), HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity updateDocumentById(@RequestParam("id") String id, @RequestParam("file") MultipartFile file)
			throws IOException {
		if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("PDF")) {
			ErrorDto errorDto = new ErrorDto(Constant.FORMAT_NOR_SUPPORTED_CODE, Constant.FORMAT_NOR_SUPPORTED_MSG,
					Constant.FORMAT_NOR_SUPPORTED_DTL);
			return new ResponseEntity(errorDto, HttpStatus.BAD_REQUEST);
		}

		DocumentEntity entity = service.getDocumentById(id);
		if (entity == null) {
			ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
					Constant.DOCUMENT_NOT_FOUND_DTL + id);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}

		service.UpdateDocumentById(entity, file);
		return new ResponseEntity("Document updated sucessfully", HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteDocumentById(@PathVariable String id) {
		if (service.getDocumentById(id) == null) {
			ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
					Constant.DOCUMENT_NOT_FOUND_DTL + id);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
		service.deleteDocumentById(id);
		return new ResponseEntity("Document deleted successfully", HttpStatus.OK);
	}

	@GetMapping("/document/{id}")
	public ResponseEntity<DocumentEntity> getDocument(@PathVariable String id) {
		if (service.getDocumentById(id) == null) {
			ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
					Constant.DOCUMENT_NOT_FOUND_DTL + id);
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.getDocumentById(id), HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<DocumentEntity>> getDocumentList() {
		return new ResponseEntity<>(service.getDocumentList(), HttpStatus.OK);
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