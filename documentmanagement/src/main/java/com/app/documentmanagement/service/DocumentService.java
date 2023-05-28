package com.app.documentmanagement.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.documentmanagement.model.DocumentEntity;
import com.app.documentmanagement.repository.DocumentRepository;

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
	
	

	public DocumentEntity getDocumentById(String id) {
		Optional<DocumentEntity> documentOptional = repository.findById(id);
		if (documentOptional.isPresent()) {
			return documentOptional.get();
		}
		return null;
	}

	public List<DocumentEntity> getDocumentList() {
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

}
