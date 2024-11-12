package com.bookstoreapi.service;

import com.bookstoreapi.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

//Book objektumokkal műveleteket végző metódusok interfésze
public interface BookService {
    List<Book> getAllBooks();

    Book saveBook(Book book);

    Book getBookById(Long id);

    void deleteBookById(Long id);
}
