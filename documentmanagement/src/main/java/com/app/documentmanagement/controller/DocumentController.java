package com.app.documentmanagement.controller;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

	Logger log = LoggerFactory.getLogger(DocumentController.class);
	
	@Autowired
	private DocumentService service;

	@PostMapping("/save")
	public ResponseEntity saveDocument(@RequestParam("file") MultipartFile file, @RequestHeader String correlationId) {
		log.info("DocumentController - inside saveDocument(): start");
		if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("PDF")) {
			log.info("DocumentController - inside saveDocument(): File format is not of type PDF!! coming format is " + FilenameUtils.getExtension(file.getOriginalFilename()));
			ErrorDto errorDto = new ErrorDto(Constant.FORMAT_NOR_SUPPORTED_CODE, Constant.FORMAT_NOR_SUPPORTED_MSG,
					Constant.FORMAT_NOR_SUPPORTED_DTL);
			return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
		}
		
		ResponseEntity response = null;
		try {
			log.info("DocumentController - inside saveDocument(): start saving document data in in-memory database");
			response = new ResponseEntity<>(service.save(file, correlationId), HttpStatus.CREATED);
			log.info("DocumentController - inside saveDocument(): Document saved successfully in in-memory database");
		} catch (Exception ex) {
			log.error("DocumentController - inside saveDocument(): Getting exception while saving Document "+ ex.getMessage());
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;

	}

	@PutMapping("/update")
	public ResponseEntity updateDocumentById(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file) {
		log.info("DocumentController - inside updateDocumentById(): start");
		if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("PDF")) {
			log.info("DocumentController - inside updateDocumentById(): File format is not of type PDF!! coming format is " + FilenameUtils.getExtension(file.getOriginalFilename()));
			ErrorDto errorDto = new ErrorDto(Constant.FORMAT_NOR_SUPPORTED_CODE, Constant.FORMAT_NOR_SUPPORTED_MSG,
					Constant.FORMAT_NOR_SUPPORTED_DTL);
			return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
		}

		ResponseEntity response = null;
		try {
			log.info("DocumentController - inside updateDocumentById(): start fetching document by Id");
			DocumentEntity entity = service.getDocumentById(id);
			if (entity == null) {
				log.info("DocumentController - inside updateDocumentById(): No found find by given Id");
				ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
						Constant.DOCUMENT_NOT_FOUND_DTL + id);
				response = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
			} else {
				log.info("DocumentController - inside updateDocumentById(): start updating document");
				service.UpdateDocumentById(entity, file);
				log.info("DocumentController - inside updateDocumentById(): document updated successfully");
				response = new ResponseEntity<>("Document updated sucessfully", HttpStatus.OK);
			}
		} catch (Exception ex) {
			log.error("DocumentController - inside updateDocumentById(): Getting exception while updating document "+ex.getMessage());
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteDocumentById(@PathVariable Long id) {
		log.info("DocumentController - inside deleteDocumentById(): start");
		if (service.getDocumentById(id) == null) {
			log.info("DocumentController - inside deleteDocumentById(): document not found by given Id");
			ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
					Constant.DOCUMENT_NOT_FOUND_DTL + id);
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		ResponseEntity response = null;
		try {
			log.info("DocumentController - inside deleteDocumentById(): deleting the document by Id");
			service.deleteDocumentById(id);
			log.info("DocumentController - inside deleteDocumentById(): document deleted successfully by given Id");
			response = new ResponseEntity<>("Document deleted successfully", HttpStatus.OK);
		}catch(Exception ex) {
			log.error("DocumentController - inside deleteDocumentById(): Exception occured while deleting document "+ex.getMessage());
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/document/{id}")
	public ResponseEntity getDocument(@PathVariable Long id) {
		log.info("DocumentController - inside getDocument(): start");
		if (service.getDocumentById(id) == null) {
			log.info("DocumentController - inside getDocument(): document not found by given Id");
			ErrorDto error = new ErrorDto(Constant.DOCUMENT_NOT_FOUND_CODE, Constant.DOCUMENT_NOT_FOUND_MSG,
					Constant.DOCUMENT_NOT_FOUND_DTL + id);
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		ResponseEntity response = null;
		try {
			log.info("DocumentController - inside getDocument(): start fetching document by given Id");
			response = new ResponseEntity<>(service.getDocumentById(id), HttpStatus.OK);
			log.info("DocumentController - inside getDocument(): document fetch successfully by given Id");
		}catch(Exception ex) {
			log.error("DocumentController - inside getDocument(): Exception occured while fetching document "+ex.getMessage());
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/list")
	public ResponseEntity<List<DocumentEntity>> getDocumentList() {
		log.info("DocumentController - inside getDocumentList(): start");
		ResponseEntity response = null;
		try {
			log.info("DocumentController - inside getDocumentList(): start fetching all documents");
			response = new ResponseEntity<>(service.getDocumentList(), HttpStatus.OK);
			log.info("DocumentController - inside getDocumentList(): Document fetch successfully");
		}catch(Exception ex) {
			log.error("DocumentController - inside getDocumentList(): Exception occured while fetching documents "+ex.getMessage());
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/document/{docId}/posts")
	public ResponseEntity getPostsByDocId(@PathVariable Long docId) {
		log.info("DocumentController - inside getPostsByDocId(): start");
		ResponseEntity response = null;
		try {
			log.info("DocumentController - inside getPostsByDocId(): start getting all posts");
			response = new ResponseEntity<>(service.getPostsByDocId(docId), HttpStatus.OK);
			log.info("DocumentController - inside getPostsByDocId(): getting all posts successfully");
		}catch(Exception ex) {
			log.error("DocumentController - inside getPostsByDocId(): Exception occured while getting all posts "+ex.getMessage());
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity getCommentsData(@PathVariable Long postId) {
		log.info("DocumentController - inside getCommentsData(): start");
		ResponseEntity response = null;
		try {
			log.info("DocumentController - inside getCommentsData(): start getting all comments based on the posts");
			response = new ResponseEntity<>(service.getCommentsData(postId), HttpStatus.OK);
			log.info("DocumentController - inside getCommentsData(): getting all comments successfully");
		}catch(Exception ex) {
			log.error("DocumentController - inside getCommentsData(): Exception occured while geting all comments "+ex.getMessage());
			response = DocumentExceptionHandler.handleException(ex);
		}
		return response;
	}

}