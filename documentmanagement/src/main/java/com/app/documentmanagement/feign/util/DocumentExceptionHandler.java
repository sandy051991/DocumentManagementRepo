package com.app.documentmanagement.feign.util;

import java.net.UnknownHostException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.app.documentmanagement.dto.ErrorDto;

@ControllerAdvice
public class DocumentExceptionHandler {

	public DocumentExceptionHandler() {

	}

	@ExceptionHandler
	@ResponseBody
	public static ResponseEntity handleException(Exception ex) {

		ResponseEntity response = null;
		// when passed value is not identifiable
		if (ex instanceof HttpMessageNotReadableException) {
			HttpMessageNotReadableException mnrex = (HttpMessageNotReadableException) ex;

			String message = mnrex.getMessage();
			if (mnrex.getRootCause() != null) {
				message = mnrex.getRootCause().getMessage();
			}

			ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), message, ex.getLocalizedMessage());
			response = new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		// when missed request param
		else if (ex instanceof MissingServletRequestParameterException) {
			MissingServletRequestParameterException mspx = (MissingServletRequestParameterException) ex;

			ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), mspx.getParameterName(), ex.getMessage());
			response = new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		// when passed variable is not of required type
		else if (ex instanceof MethodArgumentTypeMismatchException) {
			MethodArgumentTypeMismatchException mmex = (MethodArgumentTypeMismatchException) ex;

			String message = mmex.getMessage();
			if (mmex.getRootCause() != null) {
				message = mmex.getRootCause().getMessage();
			}

			ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), message, mmex.getLocalizedMessage());
			response = new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

		} else if (ex instanceof MissingServletRequestPartException) {
			MissingServletRequestPartException msex = (MissingServletRequestPartException) ex;

			String message = msex.getMessage();
			if (msex.getRootCause() != null) {
				message = msex.getRootCause().getMessage();
			}
			ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), msex.getRequestPartName(), message);
			response = new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		// Provider timeout exception
		else if (ex instanceof ResourceAccessException) {
			ErrorDto errorDto = new ErrorDto(Constant.PROVIDER_TIMEOUT_ERROR_CODE, Constant.PROVIDER_TIMEOUT_MESSAGE,
					Constant.PROVIDER_TIMEOUT_DETAILS);
			response = new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		// Provider host exception
		else if (ex instanceof DataAccessResourceFailureException || ex instanceof UnknownHostException) {
			ErrorDto errorDto = new ErrorDto(Constant.HOST_UNAVAILABLE_ERROR_CODE, Constant.HOST_UNAVAILABLE_MESSAGE,
					Constant.HOST_UNAVAILABLE_DETAILS);
			response = new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);

		} else {
			response = new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
