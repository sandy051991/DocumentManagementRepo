package com.demo.springApp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.springApp.dto.Comments;
import com.demo.springApp.dto.PostComments;
import com.demo.springApp.model.DocumentEntity;
import com.demo.springApp.repository.DocumentRepository;
import com.demo.springApp.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.client.RestTemplate;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository repository;


	public DocumentEntity save(MultipartFile file) throws IOException {
		String fileName = FilenameUtils.getExtension(file.getOriginalFilename());
		
		DocumentEntity entity =  new DocumentEntity();
		
		entity.setData(file.getBytes());
		entity.setName(fileName);
		entity.setType(file.getContentType());
		return repository.save(entity);
	}
	
	

	public DocumentEntity getFileById(String id) {
		Optional<DocumentEntity> documentOptional = repository.findById(id);
		if (documentOptional.isPresent()) {
			return documentOptional.get();
		}
		return null;
	}

	public List<DocumentEntity> getFileList() {
		return repository.findAll();
	}

	public void deleteDocumentById(String id) {
		repository.deleteById(id);
	}

	public DocumentEntity UpdateDocumentById(DocumentEntity entity, MultipartFile file) throws IOException {
		entity.setName(file.getOriginalFilename());
		entity.setType(file.getContentType());
		entity.setData(file.getBytes());
		return repository.save(entity);
	}



	public List<PostComments> callThirdPrtyAPI() {
		
		String uri = Constants.URL;
        RestTemplate restTemplate = new RestTemplate();
        
        ObjectMapper mapper = new ObjectMapper();
        
        Comments object  = restTemplate.getForObject(uri, Comments.class);
        
        // (List<PostComments>) list = mapper.readValue(object, PostComments);
		return null;
	}

}
