package com.affinitas.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SudokuNotFoundException extends RuntimeException {

	public SudokuNotFoundException(String message) {
		super(message);
	}
}
