package com.affinitas.model;

public class Sudoku {

	private String id;
	private int[][] data;

	public Sudoku(String id, int[][] data) {
		this.id = id;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public int[][] getData() {
		return data;
	}
}
