package com.app.documentmanagement.dto;

public class ErrorDto {
	
	private Integer code;
	
	private String message;
	
	private String detail;
	
	public ErrorDto() {
		
	}

	public ErrorDto(Integer code, String message, String detail) {
		super();
		this.code = code;
		this.message = message;
		this.detail = detail;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}	

	
	
}
