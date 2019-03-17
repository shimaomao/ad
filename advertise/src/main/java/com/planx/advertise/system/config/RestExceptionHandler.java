package com.planx.advertise.system.config;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.system.exception.ApplicationException;

@RestControllerAdvice(basePackages="com.planx.advertise.controller")
public class RestExceptionHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler({ BindException.class })
	public ResponseEntity<?> handleBindExceptionException(BindException ex) {
		List<ObjectError> errors = ex.getAllErrors();
		StringBuilder sb = new StringBuilder();
		errors.stream().forEach(
				error -> sb.append(error.getDefaultMessage()).append("; "));
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage(sb.toString());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ ServletRequestBindingException.class })
	public ResponseEntity<?> handleServletRequestBindingException(ServletRequestBindingException ex) {
		log.info("handleServletRequestBindingException", ex);
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage(ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		log.info("handleMethodArgumentTypeMismatchException", ex);
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage(ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ PropertyAccessException.class })
	public ResponseEntity<?> handlePropertyAccessException(PropertyAccessException ex) {
		log.info("handlePropertyAccessException", ex);
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage(ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage(ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage(ex.getMessage());
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({ ApplicationException.class })
	public ResponseEntity<?> handleApplicationException(ApplicationException ex) {
		log.info("handleApplicationException", ex);
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage(Optional.ofNullable(ex.getMessage()).orElse("internal error"));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<?> handleDefaultException(Exception ex) {
		log.error("handleDefaultException", ex);
		RestResponseBody<String> body = new RestResponseBody<>(RestStatus.ERROR);
		body.setMessage("internal error");
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}
