package com.demo.springApp.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.demo.springApp.dto.PostComments;
import com.demo.springApp.model.DocumentEntity;
import com.demo.springApp.repository.DocumentRepository;
import com.demo.springApp.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository repository;


	public DocumentEntity save(MultipartFile file) throws IOException {
		DocumentEntity entity =  new DocumentEntity();
		entity.setDocumentData(file.getBytes());
		entity.setDocumentName(file.getOriginalFilename());
		entity.setDocumentType(file.getContentType());
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
		entity.setDocumentName(file.getOriginalFilename());
		entity.setDocumentType(file.getContentType());
		entity.setDocumentData(file.getBytes());
		return repository.save(entity);
	}



	public List<PostComments> callThirdPrtyAPI() {
		
		String uri = Constants.POST_URL;
        RestTemplate restTemplate = new RestTemplate();
        
        ObjectMapper mapper = new ObjectMapper();
        
        Object object  = restTemplate.getForObject(uri, List.class);
        
        
        
        //mapper.readV // (List<PostComments>) list = mapper.readValue(object, PostComments);
		return null;
	}

}
