package com.github.jbrasileiro.lernaeanhydra.springboot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardVOTest {

	private static final boolean Y = Boolean.TRUE;
	private static final boolean N = Boolean.FALSE;
	
	public BoardVO newInstance(int height, int width, int mines, int row, int col) {
		return new BoardVO(height, width, mines) {
			@Override
			int randomRow(int limit) {
				return row;
			}
			@Override
			int randomColumn(int limit) {
				return col;
			}
		};
	}
	
	@Test
	void toBoardFields() {
		int[][] result =BoardVO.of(2, 2, 0).toBoardFields();
		Assertions.assertArrayEquals(new int[][] {{0,0},{0,0}}, result);
	}
	
	@Test
	void toBoardFieldsFirst() {
		boolean[][] result = newInstance(2, 2, 1, 0, 0).toBoardMines();
		Assertions.assertArrayEquals(new boolean[][] {{Y,N},{N,N}}, result);
	}
	
	@Test
	void toBoardLast() {
		boolean[][] result = newInstance(2, 2, 1, 1, 1).toBoardMines();
		Assertions.assertArrayEquals(new boolean[][] {{N,N},{N,Y}}, result);
	}

}
