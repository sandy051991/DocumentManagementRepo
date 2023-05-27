package com.demo.springApp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.springApp.dto.PostComments;
import com.demo.springApp.model.DocumentEntity;
import com.demo.springApp.service.DocumentService;

@RestController("/file")
public class DocumentController {
	
	@Autowired
	private DocumentService service;

	
	@PostMapping("/saveDocument")
	public DocumentEntity uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
		
		String extn= FilenameUtils.getExtension(file.getOriginalFilename());
		if(extn.equalsIgnoreCase("PDF")) {
			return service.save(file);
		}
		
		return null;
		
		
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
		DocumentEntity entity = service.getFileById(id);
		if(entity!=null) {
			service.UpdateDocumentById(entity, file);
			return "Document updated sucessfully";
		}
		return "Data not found by given id::"+id;
	}
	
	
	
	
	
}