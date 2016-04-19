package com.affinitas.dao;

import com.affinitas.model.Sudoku;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SudokuInMemoryDao implements SudokuDao{

	private Map<String, Sudoku> data = new ConcurrentHashMap<>();

	@Override
	public Sudoku getSudokuById(String id) {
		return data.get(id);
	}

	@Override
	public void saveSudoku(Sudoku sudoku) {
		data.put(sudoku.getId(), sudoku);
	}
}
