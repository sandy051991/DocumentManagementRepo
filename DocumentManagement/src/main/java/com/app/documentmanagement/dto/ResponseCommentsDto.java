package com.app.documentmanagement.dto;

import java.time.LocalDateTime;

public class ResponseCommentsDto {
	
	private String correlationId;
	private LocalDateTime dateTime;
	private DataCommentsDto Data;
	
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public DataCommentsDto getData() {
		return Data;
	}
	public void setData(DataCommentsDto dataDto) {
		Data = dataDto;
	}
	
	
	
	

}
