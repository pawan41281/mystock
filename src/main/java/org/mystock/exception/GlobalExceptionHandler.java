package org.mystock.exception;

import org.mystock.apiresponse.ApiResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseVo<?>> handleResourceNotFound(ResourceNotFoundException ex) {
		ApiResponseVo<Object> ApiResponseVo = new ApiResponseVo<Object>("error", ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseVo);
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ApiResponseVo<?>> handleRecordAlreadyExistsException(ResourceAlreadyExistsException ex) {
		ApiResponseVo<Object> ApiResponseVo = new ApiResponseVo<Object>("error", ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponseVo);
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<ApiResponseVo<?>> handleServiceNotRespondingException(
			ServiceNotRespondingException ex) {
		ApiResponseVo<Object> ApiResponseVo = new ApiResponseVo<Object>("error", ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiResponseVo);
	}

	@ExceptionHandler(UnableToProcessException.class)
	public ResponseEntity<ApiResponseVo<?>> handleUnableToProcessException(
			ServiceNotRespondingException ex) {
		ApiResponseVo<Object> ApiResponseVo = new ApiResponseVo<Object>("error", ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseVo);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseVo<?>> handleGenericException(
			Exception ex) {
		ApiResponseVo<Object> ApiResponseVo = new ApiResponseVo<Object>("error", ex.getMessage(), null, null);
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ApiResponseVo);
	}
}