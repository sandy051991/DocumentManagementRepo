package com.app.documentmanagement.feign.util;

public class Constant {
	
	public static final Integer FORMAT_NOR_SUPPORTED_CODE = 1001;
	public static final String FORMAT_NOR_SUPPORTED_MSG = "Format Not Supported!!";
	public static final String FORMAT_NOR_SUPPORTED_DTL = "Document format is not supported, Only PDF document allowed!";
	
	public static final Integer DOCUMENT_NOT_FOUND_CODE = 1002;
	public static final String DOCUMENT_NOT_FOUND_MSG = "Document Not Found!!";
	public static final String DOCUMENT_NOT_FOUND_DTL = "No document found for the given id - ";
	
	public static final Integer PROVIDER_TIMEOUT_ERROR_CODE = 10001;
	public static final String PROVIDER_TIMEOUT_MESSAGE = "TIMEOUT EXCEPTION: Provider Timeout";
	public static final String PROVIDER_TIMEOUT_DETAILS = "TIMEOUT EXCEPTION: The downstream system failed to respond within the expected time periods";
	
	public static final Integer HOST_UNAVAILABLE_ERROR_CODE = 10002;
	public static final String HOST_UNAVAILABLE_MESSAGE = "PROVIDER EXCEPTION: Host Unavialble";
	public static final String HOST_UNAVAILABLE_DETAILS = "PROVIDER EXCEPTION: The downstream system failed to accept the request";
	
}
