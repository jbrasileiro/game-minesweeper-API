package com.github.jbrasileiro.lernaeanhydra.springboot;

public class BoardVO {

	private final int height;
	private final int width;
	private final int mines;

	public BoardVO(int height, int width, int mines) {
		this.height = height;
		this.width = width;
		this.mines = mines;
	}

	public static BoardVO of(int height, int width, int mines) {
		return new BoardVO(height, width, mines);
	}

	public int[][] toBoardFields() {
		int[][] board = new int[height][width];
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				board[row][column] = BoardField.UNKNOWN;
			}
		}
		return board;
	}

	public boolean[][] toBoardMines() {
		int total = 0;
		boolean[][] board = new boolean[height][width];
		while (total < mines) {
			int row = randomRow(height);
			int col = randomColumn(width);
			if (!board[row][col]) {
				board[row][col] = true;
				total++;
			}
		}
		return board;
	}

	int randomRow(int limit) {
		return random(limit);
	}
	
	int randomColumn(int limit) {
		return random(limit);
	}


	private int random(int limit) {
		return (int) Math.floor(Math.random() * limit);
	}
}
