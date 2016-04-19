package com.affinitas.model;

public class Move {

	private int row;
	private int col;
	private int digit;

	public Move() {
	}

	public Move(int row, int col, int digit) {
		this.row = row;
		this.col = col;
		this.digit = digit;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row - 1;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col - 1;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}
}
