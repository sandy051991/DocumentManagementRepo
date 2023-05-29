package com.app.documentmanagement.feign.util;

public class Constant {
	
	public static final Integer FORMAT_NOR_SUPPORTED_CODE = 1001;
	public static final String FORMAT_NOR_SUPPORTED_MSG = "Format Not Supported!!";
	public static final String FORMAT_NOR_SUPPORTED_DTL = "Document format is not support, Only PDF documents allowed!";
	
	public static final Integer DOCUMENT_NOT_FOUND_CODE = 1002;
	public static final String DOCUMENT_NOT_FOUND_MSG = "Document Not Found!!";
	public static final String DOCUMENT_NOT_FOUND_DTL = "No document found for the given id - ";
	
	public static final Integer DOCUMENTS_NOT_FOUND_CODE = 1003;
	public static final String DOCUMENTS_NOT_FOUND_MSG = "Documents Not Found!!";
	public static final String DOCUMENTS_NOT_FOUND_DTL = "There is no document found in the database";

}
