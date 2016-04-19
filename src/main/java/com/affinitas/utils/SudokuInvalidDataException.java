package com.affinitas.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SudokuInvalidDataException extends RuntimeException {

	public SudokuInvalidDataException(String message) {
		super(message);
	}
}
