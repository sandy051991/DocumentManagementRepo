package com.app.documentmanagement.dto;

import java.time.LocalDateTime;

public class ResponsePostsDto {
	
	private String correlationId;
	private LocalDateTime dateTime;
	private DataPostsDto Data;
	
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
	public DataPostsDto getData() {
		return Data;
	}
	public void setData(DataPostsDto data) {
		Data = data;
	}
	
	
	
	

}
