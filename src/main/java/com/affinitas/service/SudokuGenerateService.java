package com.affinitas.service;

import com.affinitas.model.Sudoku;
import org.springframework.stereotype.Service;

@Service
public interface SudokuGenerateService {

	/**
	 * generates a new sudoku board 9*9 with a new id and saves it to dataStore
	 *
	 * @return Sudoku - a new sudoku board
	 * */
	Sudoku generateSudoku();
}
