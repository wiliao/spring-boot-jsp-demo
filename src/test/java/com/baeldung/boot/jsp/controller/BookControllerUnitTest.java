package com.baeldung.boot.jsp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.baeldung.boot.jsp.dto.BookDTO;
import com.baeldung.boot.jsp.service.BookService;

@WebMvcTest(BookController.class)
class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    public void whenViewBooks_thenReturnBooksView() throws Exception {
        when(bookService.getBooks()).thenReturn(existingBooks());
        ResultActions viewBooksResult = mockMvc.perform(get("/book/viewBooks"));

        viewBooksResult.andExpect(view().name("view-books"))
            .andExpect(model().attribute("books", hasSize(3)));
    }

    @Test
    public void whenAddBookView_thenReturnAddBooksView() throws Exception {
        ResultActions addBookViewResult = mockMvc.perform(get("/book/addBook"));

        addBookViewResult.andExpect(view().name("add-book"))
            .andExpect(model().attribute("book", isA(BookDTO.class)));
    }

    @Test
    public void whenAddBookPost_thenRedirectToAddBookView() throws Exception {
        when(bookService.addBook(any(BookDTO.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        MockHttpServletRequestBuilder addBookRequest = MockMvcRequestBuilders.post("/book/addBook")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("isbn", "isbn1")
            .param("name", "name1")
            .param("author", "author1");
        ResultActions addBookResult = mockMvc.perform(addBookRequest);

        addBookResult.andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/book/addBook"))
            .andExpect(flash().attribute("savedBook", hasProperty("isbn", equalTo("isbn1"))))
            .andExpect(flash().attribute("addBookSuccess", true));
    }

    private static Collection<BookDTO> existingBooks() {
        List<BookDTO> books = new ArrayList<>();
        books.add(new BookDTO("isbn1", "name1", "author1"));
        books.add(new BookDTO("isbn2", "name2", "author2"));
        books.add(new BookDTO("isbn3", "name3", "author3"));
        return books;
    }
}