package com.github.jbrasileiro.lernaeanhydra.springboot;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

public class CustomResponseEntity {

	public static ResponseEntity<Void> create(Class<?> clazz, Object o) {
		Link link = WebMvcLinkBuilder.linkTo(clazz).slash(o).withSelfRel();
		return ResponseEntity.created(link.toUri()).build();
	}

}
