package com.demo.springApp.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.demo.springApp.dto.PostComments;
import com.demo.springApp.feign.util.PostsFeignServiceUtil;
import com.demo.springApp.model.DocumentEntity;
import com.demo.springApp.service.DocumentService;

@RestController()
@RequestMapping(value = "/info/document/manage/pdf")
public class DocumentController {
	
	@Autowired
	private DocumentService service;
	
	@Autowired
	private PostsFeignServiceUtil util;

	
	@PostMapping("/saveDocument")
	public ResponseEntity<DocumentEntity> uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
		
		String extn= FilenameUtils.getExtension(file.getOriginalFilename());
		if(extn.equalsIgnoreCase("PDF")) {
			return new ResponseEntity<>(service.save(file), HttpStatus.CREATED);
//			return service.save(file);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
		
	}
	
	@GetMapping("/{id}")
	public DocumentEntity getDocument(@PathVariable String id) {
		
		DocumentEntity enty= service.getFileById(id);
		
		try {
			List<PostComments> comments= service.callThirdPrtyAPI(); 
			
			System.out.println(comments);
		}catch(Exception e) {
			
			return enty;
		}
		
		return service.getFileById(id);
	}
	
	@GetMapping("/list")
	public List<DocumentEntity> getDocumentList() {
		return service.getFileList();
	}
	
	@DeleteMapping("/{id}")
	public String deleteDocumentById(@PathVariable String id) {
		service.deleteDocumentById(id);
		return "success";
	}
	
	@PutMapping("/updateDocument")
	public String updateDocumentById(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) throws IOException {
		String extn= FilenameUtils.getExtension(file.getOriginalFilename());
		if(extn.equalsIgnoreCase("PDF")) {
			DocumentEntity entity = service.getFileById(id);
			if(entity!=null) {
				service.UpdateDocumentById(entity, file);
				return "Document updated sucessfully";
			}
			return "No document found by the given id::"+id;
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