package com.baeldung.boot.jsp.service;

import java.util.Collection;

import com.baeldung.boot.jsp.dto.BookDTO;

public interface BookService {
    Collection<BookDTO> getBooks();

    BookDTO addBook(BookDTO book);
}