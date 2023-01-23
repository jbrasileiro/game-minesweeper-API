package com.github.jbrasileiro.lernaeanhydra.springboot;

public class BoardField {
	
	public static final int MINE = -3;
	public static final int MARK = -2;
	public static final int NOTHING = -1;
	public static final int UNKNOWN = 0;

	public static String symbol(int value) {
		switch (value) {
		case MINE: return "*";
		case UNKNOWN: return "#";
		case MARK: return "?";
		case NOTHING: return "-";
		default:
			throw new IllegalArgumentException("Unexpected value: " + value);
		}
	}
	
}