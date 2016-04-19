package com.affinitas.service;

import com.affinitas.constant.MoveResult;
import com.affinitas.constant.SudokuConstants;
import com.affinitas.dao.SudokuDao;
import com.affinitas.model.Move;
import com.affinitas.model.Sudoku;
import com.affinitas.utils.SudokuInvalidDataException;
import com.affinitas.utils.SudokuNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SudokuValidateService {

	@Autowired
	private SudokuDao dao;

	/**
	 * validates a new move on the sudoku board
	 *
	 * @param id - sudoku unique identifier
	 * @param move - move on the sudoku board
	 *
	 * @return MoveResult - result of the move
	 * @throws
	 * 		SudokuNotFoundException when sudoku with id not found
	 * 		SudokuInvalidDataException when move data is invalid, out of bound
	 *
	 * */
	public MoveResult validateMove(String id, Move move) {
		Sudoku sudoku = dao.getSudokuById(id);
		if (sudoku == null) {
			throw new SudokuNotFoundException("Sudoku was not found");
		}

		MoveResult result = validateMove(sudoku.getData(), move);
		if (result == MoveResult.VALID) {
			sudoku.getData()[move.getRow()][move.getCol()] = move.getDigit();
			if (isSuccess(sudoku.getData())) {
				return MoveResult.FINISH;
			}
		}
		return result;
	}

	private MoveResult validateMove(int[][] sudoku, Move move) {
		if (move.getDigit() > SudokuConstants.MAX_DIGIT
				|| move.getDigit() < SudokuConstants.MIN_DIGIT) {
			throw new SudokuInvalidDataException("Invalid move digit: " + move.getDigit());
		}

		if (move.getRow() + 1 > SudokuConstants.MAX_DIGIT
				|| move.getRow() + 1 < SudokuConstants.MIN_DIGIT) {
			throw new SudokuInvalidDataException("Invalid move row: " + move.getRow());
		}

		if (move.getCol() + 1 > SudokuConstants.MAX_DIGIT
				|| move.getCol() + 1 < SudokuConstants.MIN_DIGIT) {
			throw new SudokuInvalidDataException("Invalid move col: " + move.getCol());
		}


		if (!validRow(sudoku, move.getRow(), move.getCol(), move.getDigit())) {
			return MoveResult.INVALID;
		}
		if (!validCol(sudoku, move.getRow(), move.getCol(), move.getDigit())) {
			return MoveResult.INVALID;
		}
		if (!validBox(sudoku, move.getRow(), move.getCol(), move.getDigit())) {
			return MoveResult.INVALID;
		}
		return MoveResult.VALID;
	}

	private boolean isSuccess(int[][] sudoku) {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				if (sudoku[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean validRow(int[][] sudoku, int row, int col, int digit) {
		for (int i = 0; i < sudoku.length; i++) {
			if (sudoku[row][i] == digit) {
				return false;
			}
		}
		return true;
	}

	private boolean validCol(int[][] sudoku, int row, int col, int digit) {
		for (int i = 0; i < sudoku.length; i++) {
			if (sudoku[i][col] == digit) {
				return false;
			}
		}
		return true;
	}

	private boolean validBox(int[][] sudoku, int row, int col, int digit) {
		int boxRowStart = getBoxStart(row);
		int boxColStart = getBoxStart(col);

		for (int i = boxRowStart; i < boxRowStart + SudokuConstants.BOX_SIZE; i++) {
			for (int j = boxColStart; j < boxColStart+ SudokuConstants.BOX_SIZE; j++) {
				if (sudoku[i][j] == digit) {
					return false;
				}
			}
		}
		return true;
	}

	private int getBoxStart(int pos) {
		if (pos < SudokuConstants.BOX_SIZE) {
			return 0;
		} else if (pos < 2 * SudokuConstants.BOX_SIZE) {
			return SudokuConstants.BOX_SIZE;
		} else {
			return 2 * SudokuConstants.BOX_SIZE;
		}
	}
}
