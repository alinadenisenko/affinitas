package com.affinitas.dao;

import com.affinitas.model.Sudoku;
import org.springframework.stereotype.Component;

@Component
public interface SudokuDao {

	Sudoku getSudokuById(String id);

	void saveSudoku(Sudoku sudoku);
}
