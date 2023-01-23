package com.github.jbrasileiro.lernaeanhydra.springboot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Board {

    private String id;

    private int height;
    
    private int width;
    
	private int mines;
    
	private int[][] boardFields;
	private boolean[][] boardMines;

	public void draw() {
		int currentColumn = 0;
		System.out.print("  ");
		while (currentColumn < width) {
			System.out.print(currentColumn + 1);
			currentColumn++;
		}
		System.out.print("\n");
		for (int row = 0; row < height; row++) {
			System.out.print(row + 1 + " ");
			for (int column = 0; column < width; column++) {
				String symbol = BoardField.symbol(boardFields[row][column]);
				System.out.print(symbol);
			}
			
			System.out.print("\n");
		}
	}

}
