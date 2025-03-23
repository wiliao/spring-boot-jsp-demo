package com.baeldung.boot.jsp.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baeldung.boot.jsp.exception.DuplicateBookException;
import org.springframework.stereotype.Service;

import com.baeldung.boot.jsp.dto.BookDTO;
import com.baeldung.boot.jsp.repository.BookRepository;
import com.baeldung.boot.jsp.repository.model.BookData;
import com.baeldung.boot.jsp.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Collection<BookDTO> getBooks() {
        return bookRepository.findAll()
            .stream()
            .map(BookServiceImpl::convertBookData)
            .collect(Collectors.toList());
    }

    @Override
    public BookDTO addBook(BookDTO book) {
        final Optional<BookData> existingBook = bookRepository.findById(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new DuplicateBookException(book);
        }

        final BookData savedBook = bookRepository.add(convertBook(book));
        return convertBookData(savedBook);
    }

    private static BookDTO convertBookData(BookData bookData) {
        return new BookDTO(bookData.getIsbn(), bookData.getName(), bookData.getAuthor());
    }

    private static BookData convertBook(BookDTO book) {
        return new BookData(book.getIsbn(), book.getName(), book.getAuthor());
    }
}
