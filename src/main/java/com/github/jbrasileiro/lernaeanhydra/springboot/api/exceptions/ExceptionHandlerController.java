package com.github.jbrasileiro.lernaeanhydra.springboot.api.exceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({Exception.class})
	public final ResponseEntity<Object> anyException(
			final Exception exception,
			final WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return handle(exception, status, request);
	}

	@ExceptionHandler(value = {HttpClientErrorException.class})
	public final ResponseEntity<Object> httpClientErrorException(
		final HttpClientErrorException exception,
		final WebRequest request) {
		HttpStatus statusCode = exception.getStatusCode();
		String statusText = exception.getStatusText();
		HttpHeaders headers = exception.getResponseHeaders();
		String body = exception.getResponseBodyAsString();
		List<String> details = Collections.emptyList();
		LinkedHashMap<String, String> properties = new LinkedHashMap<>();
		properties.put("status", statusCode.toString());
		properties.put("status text", statusText);
		properties.put("body", body);
		return handle(details, properties, exception, headers, statusCode, request);
	}

	@ExceptionHandler(value = {TransactionSystemException.class})
	public final ResponseEntity<Object> transactionSystemException (
			final TransactionSystemException exception,
			final WebRequest request) {
		Throwable cause = exception.getRootCause();
		if(cause instanceof ConstraintDeclarationException) {
			ConstraintDeclarationException castException = (ConstraintDeclarationException) cause;
			return handle(castException, HttpStatus.BAD_REQUEST, request);
		} else {
			return handle(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
		}
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolation(
			final ConstraintViolationException exception,
			final WebRequest request) {
		List<String> errors = new ArrayList<>();
		Set<ConstraintViolation<?>> constraintViolations = new LinkedHashSet<>(exception.getConstraintViolations());
		for (ConstraintViolation<?> violation : constraintViolations) {
			Path property = violation.getPropertyPath();
			String message = violation.getMessage();
			errors.add(property + ": " + message);
		}
		return handle(errors, exception, HttpStatus.BAD_REQUEST, request);
	}

    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
    		final MethodArgumentNotValidException exception,
    		final HttpHeaders headers,
    		final HttpStatus status,
    		final WebRequest request) {
        List<String> details = new LinkedList<>();
        BindingResult bindingResult = exception.getBindingResult();
		for(FieldError error : bindingResult.getFieldErrors()) {
			details.add(error.getField() + " : " + error.getDefaultMessage());
        }
		Collections.sort(details);
        return handle(details, null, exception, headers, status, request);
    }

	private ResponseEntity<Object> handle(
			final Exception exception
			, final HttpStatus status
			, final WebRequest request) {
			return handle(null, exception, status, request);
	}

	private ResponseEntity<Object> handle(
			final Collection<String> message
			, final Exception exception
			, final HttpStatus status
			, final WebRequest request) {
			return handle(message, null, exception, null, status, request);
		}

	private ResponseEntity<Object> handle(
			final Collection<String> details
			, final LinkedHashMap<String, String> properties
			, final Exception exception
			, final HttpHeaders httpHeaders
			, final HttpStatus status
			, final WebRequest request) {
		CustomExceptionResponse body = CustomExceptionResponse.builder()
				.path(uri(request))
				.details(details)
				.properties(properties)
				.from(exception)
				.build();
		return handleExceptionInternal(exception, body, httpHeaders, status, request);
	}

	protected String uri(final WebRequest request) {
		return ((ServletWebRequest)request).getRequest().getRequestURI();
	}

	/**
	 * Overring method to add body when body is null.
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
		final Exception ex,
		final Object body,
		final HttpHeaders headers,
		final HttpStatus status,
		final WebRequest request) {
		ex.printStackTrace();
		ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, status, request);
		Object newBody = response.getBody();
		if(Objects.nonNull(newBody)) {
			return new ResponseEntity<>(newBody, response.getHeaders(), response.getStatusCode());
		} else {
			return new ResponseEntity<>(ex.getMessage(), response.getHeaders(), response.getStatusCode());
		}
	}

}
