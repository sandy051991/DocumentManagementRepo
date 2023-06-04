package com.app.documentmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.app.documentmanagement.model.DocumentEntity;
import com.app.documentmanagement.repository.DocumentRepository;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {

	@Mock
	private DocumentRepository repository;

	@InjectMocks
	private DocumentService service;

	public DocumentEntity getEntity() {
		DocumentEntity entity = new DocumentEntity();
		entity.setDocumentId((long) 1);
		entity.setDocumentName("dummy Pdf.pdf");
		entity.setDocumentType("application/pdf");
		
		return entity;
	}
	
	public List<DocumentEntity> getEntityList() {
		List<DocumentEntity> list = new ArrayList<DocumentEntity>();
		list.add(getEntity());
		return list;
	}
	
	@Test
	public void testGetDocumentById() {
		when(repository.findById(anyLong())).thenReturn(Optional.of(getEntity()));
		DocumentEntity entity = service.getDocumentById((long) 1);
		assertEquals(1, entity.getDocumentId());
		assertEquals("dummy Pdf.pdf", entity.getDocumentName());
		assertEquals("application/pdf", entity.getDocumentType());
	}

	@Test
	public void testSave() {
		when(repository.save(any())).thenReturn(getEntity());
		DocumentEntity entity = repository.save(getEntity());
		assertEquals(1, entity.getDocumentId());
		assertEquals("dummy Pdf.pdf", entity.getDocumentName());
		assertEquals("application/pdf", entity.getDocumentType());
	}
	
	@Test
	public void testGetDocumentList() {
		when(repository.findAll()).thenReturn(getEntityList());
		DocumentEntity entity = repository.findAll().get(0);
		assertEquals(1, entity.getDocumentId());
		assertEquals("dummy Pdf.pdf", entity.getDocumentName());
		assertEquals("application/pdf", entity.getDocumentType());
		
	}

	@Test
	public void testUpdateDocumentById() {
		when(repository.save(any())).thenReturn(getEntity());
		DocumentEntity entity = repository.save(getEntity());
		assertEquals(1, entity.getDocumentId());
		assertEquals("dummy Pdf.pdf", entity.getDocumentName());
		assertEquals("application/pdf", entity.getDocumentType());
	}
}
