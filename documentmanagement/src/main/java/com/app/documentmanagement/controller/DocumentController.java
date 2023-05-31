package com.app.documentmanagement.controller;

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
import com.app.documentmanagement.feign.util.DocumentExceptionHandler;
import com.app.documentmanagement.model.DocumentEntity;
import com.app.documentmanagement.service.DocumentService;

@RestController()
@RequestMapping(value = "/info/pdf/documents/management/app/v1")
public class DocumentController {

	@Autowired
	private DocumentService service;

	@PostMapping("/save")
	public ResponseEntity uploadDocument(@RequestParam("file") MultipartFile file) {
		if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("PDF")) {
			ErrorDto errorDto = new ErrorDto(Constant.FORMAT_NOR_SUPPORTED_CODE, Constant.FORMAT_NOR_SUPPORTED_MSG,
					Constant.FORMAT_NOR_SUPPORTED_DTL);
			return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
		}

		ResponseEntity response = null;
		try {
			response = new ResponseEntity<>(service.save(file), HttpStatus.CREATED);
		} catch (Exception ex) {
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;

	}

	@PutMapping("/update")
	public ResponseEntity updateDocumentById(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {

		if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("PDF")) {
			ErrorDto errorDto = new ErrorDto(Constant.FORMAT_NOR_SUPPORTED_CODE, Constant.FORMAT_NOR_SUPPORTED_MSG,
					Constant.FORMAT_NOR_SUPPORTED_DTL);
			return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
		}

		ResponseEntity response = null;
		try {
			DocumentEntity entity = service.getDocumentById(id);
			if (entity == null) {
				ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
						Constant.DOCUMENT_NOT_FOUND_DTL + id);
				response = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			} else {
				service.UpdateDocumentById(entity, file);
				response = new ResponseEntity<>("Document updated sucessfully", HttpStatus.OK);
			}
		} catch (Exception ex) {
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteDocumentById(@PathVariable Long id) {
		if (service.getDocumentById(id) == null) {
			ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
					Constant.DOCUMENT_NOT_FOUND_DTL + id);
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		ResponseEntity response = null;
		try {
			service.deleteDocumentById(id);
			response = new ResponseEntity<>("Document deleted successfully", HttpStatus.OK);
		}catch(Exception ex) {
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/document/{id}")
	public ResponseEntity getDocument(@PathVariable Long id) {
		if (service.getDocumentById(id) == null) {
			ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
					Constant.DOCUMENT_NOT_FOUND_DTL + id);
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		ResponseEntity response = null;
		try {
			service.deleteDocumentById(id);
			response = new ResponseEntity<>(service.getDocumentById(id), HttpStatus.OK);
		}catch(Exception ex) {
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/list")
	public ResponseEntity<List<DocumentEntity>> getDocumentList() {
		ResponseEntity response = null;
		try {
			response = new ResponseEntity<>(service.getDocumentList(), HttpStatus.OK);
		}catch(Exception ex) {
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/document/{docId}/posts")
	public ResponseEntity getPostsByDocId(@PathVariable Long docId) {
		ResponseEntity response = null;
		try {
			response = new ResponseEntity<>(service.getPostsByDocId(docId), HttpStatus.OK);
		}catch(Exception ex) {
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity getCommentsData(@PathVariable Long postId) {
		ResponseEntity response = null;
		try {
			response = new ResponseEntity<>(service.getCommentsData(postId), HttpStatus.OK);
		}catch(Exception ex) {
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

}