package com.app.documentmanagement.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="documents")

public class DocumentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_id")
	public Long documentId;
	
	@Column(name = "document_correlation_id")
	public String documentCorrelationId;
	
	@Column(name = "document_create_time")
	public LocalDateTime documentCreateTime;
	
	@Column(name = "document_name")
	public String documentName;
	
	@Column(name = "document_type")
	public String documentType;
	
	@Lob
	@Column(name = "document_data")
	public byte[] documentData;
	
	public DocumentEntity() {
		
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentCorrelationId() {
		return documentCorrelationId;
	}

	public void setDocumentCorrelationId(String documentCorrelationId) {
		this.documentCorrelationId = documentCorrelationId;
	}
	
	public LocalDateTime getDocumentCreateTime() {
		return documentCreateTime;
	}

	public void setDocumentCreateTime(LocalDateTime documentCreateTime) {
		this.documentCreateTime = documentCreateTime;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public byte[] getDocumentData() {
		return documentData;
	}

	public void setDocumentData(byte[] documentData) {
		this.documentData = documentData;
	}

}
