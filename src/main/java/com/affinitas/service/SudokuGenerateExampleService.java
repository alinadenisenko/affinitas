package com.affinitas.service;

import com.affinitas.dao.SudokuDao;
import com.affinitas.model.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Generates a new sudoku board from example
 *
 * */
@Service
public class SudokuGenerateExampleService implements SudokuGenerateService{

	@Autowired
	private SudokuDao dao;


	@Override
	public Sudoku generateSudoku() {
		String id = UUID.randomUUID().toString();
		int[][] data = getSudokuData();
		Sudoku sudoku = new Sudoku(id, data);
		dao.saveSudoku(sudoku);
		return sudoku;
	}

	private int[][] getSudokuData() {
		int[][] data =  new int[9][9];
		data[0] = new int[]{7, 0, 0, 0, 4, 0, 5, 3, 0};
		data[1] = new int[]{0, 0, 5, 0, 0, 8, 0, 1, 0};
		data[2] = new int[]{0, 0, 8, 5, 0, 9, 0, 3, 0};
		data[3] = new int[]{5, 3, 9, 0, 6, 0, 0, 0, 1};
		data[4] = new int[]{0, 0, 0, 0, 1, 0, 0, 0, 5};
		data[5] = new int[]{8, 0, 0, 7, 2, 0, 9, 0, 0};
		data[6] = new int[]{9, 0, 7, 4, 0, 0, 0, 0, 0};
		data[7] = new int[]{0, 0, 0, 0, 5, 7, 0, 0, 0};
		data[8] = new int[]{6, 0, 0, 0, 0, 0, 0, 5, 0};

		return data;
	}
}
