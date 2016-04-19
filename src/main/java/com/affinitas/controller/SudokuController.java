package com.affinitas.controller;

import com.affinitas.model.Move;
import com.affinitas.model.Sudoku;
import com.affinitas.service.SudokuGenerateService;
import com.affinitas.service.SudokuValidateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/sudoku")
public class SudokuController {

	@Autowired
	private SudokuGenerateService sudokuGenerateService;
	@Autowired
	private SudokuValidateService sudokuValidateService;

	private static final Logger logger = LoggerFactory.getLogger(SudokuController.class);

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public Sudoku getSudoku() {
		logger.debug("generate()");
		return sudokuGenerateService.generateSudoku();
	}

	@RequestMapping(value = "/validate_move/{id}", method = RequestMethod.POST)
	public Map<String, String> validateMove(@PathVariable String id, @RequestBody Move move) {
		logger.debug("validateMove(): id={}, move={}", id, move);
		return Collections.singletonMap("result", sudokuValidateService.validateMove(id, move).toString());
	}
}
