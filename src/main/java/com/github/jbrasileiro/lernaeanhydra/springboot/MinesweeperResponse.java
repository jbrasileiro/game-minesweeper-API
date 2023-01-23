package com.github.jbrasileiro.lernaeanhydra.springboot;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MinesweeperResponse<T> extends RepresentationModel<MinesweeperResponse<T>> {
	
	public static <T> MinesweeperResponse<T> of(T value) {
		return new MinesweeperResponse<T>(value);
	}
	
	private T content;

	private MinesweeperResponse(T content) {
		super();
		this.content = content;
	}
	
	
}
