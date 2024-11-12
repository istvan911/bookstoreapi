package com.bookstoreapi.service;

import com.bookstoreapi.exception.BookNotFoundException;
import com.bookstoreapi.model.Book;
import com.bookstoreapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired      
    private BookRepository bookRepository;

    //Az összes könyv összegyűjtése listába
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //Könyv mentése adatbázisba a megfelelő előkészületek után
    @Override
    public Book saveBook(Book book) {
        if (book.getId() == null) {
            if (isTitleAlreadyExists(book.getTitle()))
                throw new IllegalArgumentException("Ez a könyv már regisztrálva van!");
            this.bookRepository.save(book);
        } else {
            Book existingBook = this.bookRepository.findById(book.getId()).orElseThrow(
                    () -> new BookNotFoundException("A könyv nem található!")
            );

            if (!book.getTitle().equals(existingBook.getTitle())) {
                if (isTitleAlreadyExists(book.getTitle())) {
                    throw new IllegalArgumentException("Ez a cím már regisztrálva van!");
                }
            }
            this.bookRepository.save(book);
        }

        return book;
    }

    // Címvizsgálat
    private boolean isTitleAlreadyExists(String title) {
        return bookRepository.findByTitle(title).isPresent();
    }

    //Könyv keresése id alapján
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "Az ID=" + id + " könyv nem található.";
            return new BookNotFoundException(errorMessage);
        });
    }

    //Könyv törlése id alapján adatbázisból
    @Override
    public void deleteBookById(Long id) {
        this.bookRepository.deleteById(id);
    }
}
