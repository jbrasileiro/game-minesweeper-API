package com.github.jbrasileiro.lernaeanhydra.springboot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public final class CustomHttpExceptions {

	private static final HashMap<String, Collection<String>> NO_HEADER = new HashMap<>();

	private CustomHttpExceptions() {
		super();
		throw new UnsupportedOperationException("no new instance allowed");
	}

	public static HttpClientErrorException badRequest(
		final String detail) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return newHttpClientErrorException(detail, NO_HEADER, status);
	}

	public static HttpClientErrorException noEntityFound(
		final String detail) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		String message = "No entity found : " + detail;
		return newHttpClientErrorException(message, NO_HEADER, status);
	}

	private static HttpClientErrorException newHttpClientErrorException(
		final String message,
		final Map<String, Collection<String>> header,
		final HttpStatus status) {
		HttpHeaders headers = new HttpHeaders();
		header.forEach((
				key,
				values) -> {
					if(!values.isEmpty()) {
						headers.addAll(key, new ArrayList<>(values));
					}
				});
		String statusText = status.getReasonPhrase();
		return new HttpClientErrorException(message,
			status,
			statusText,
			headers,
			null,
			null);
	}

}
