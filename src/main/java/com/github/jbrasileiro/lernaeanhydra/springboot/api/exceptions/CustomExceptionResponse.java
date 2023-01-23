package com.github.jbrasileiro.lernaeanhydra.springboot.api.exceptions;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "path", "details", "properties", "exception", "cause"})
public class CustomExceptionResponse {

	public static class CustomExceptionResponseBuilder {

    	public CustomExceptionResponseBuilder from(final Exception exception) {
    		this.exception = ExceptionReason.from(exception);
    		if(Objects.nonNull(exception.getCause())) {
    			this.cause = ExceptionReason.from(exception.getCause());
    		}
    		return this;
    	}
	}

	private String path;
	private Collection<String> details;
	private LinkedHashMap<String, String> properties;
	private ExceptionReason exception;
	private ExceptionReason cause;
}


