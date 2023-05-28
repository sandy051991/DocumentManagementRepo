package com.app.documentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.documentmanagement.model.DocumentEntity;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, String>{

}
