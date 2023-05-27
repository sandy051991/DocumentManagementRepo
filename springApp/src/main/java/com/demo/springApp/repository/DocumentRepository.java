package com.demo.springApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.springApp.model.DocumentEntity;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, String>{

}
