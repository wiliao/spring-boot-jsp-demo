package com.baeldung.boot.jsp.exception;

import com.baeldung.boot.jsp.dto.BookDTO;

import lombok.Getter;

@Getter
public class DuplicateBookException extends RuntimeException {
    private final BookDTO bookDTO;

    public DuplicateBookException(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }
}