package com.github.jbrasileiro.lernaeanhydra.springboot.api.exceptions;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonPropertyOrder({"name", "message"})
public class ExceptionReason {
	
	private String name;
	private String message;
	
	public static ExceptionReason from(final Throwable exception) {
		return ExceptionReason.builder()
					.name(exception.getClass().getName())
					.message(exception.getMessage())
					.build();
	}
}