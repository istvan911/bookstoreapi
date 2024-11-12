package com.bookstoreapi.service;

import com.bookstoreapi.exception.MyBookNotFoundException;
import com.bookstoreapi.model.MyBook;
import com.bookstoreapi.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookServiceImpl implements MyBookService {
    @Autowired
    private MyBookRepository myBookRepository;

    //MyBook objektumok összegyűjtése listába
    @Override
    public List<MyBook> getAllMyBooks() {
        return myBookRepository.findAll();
    }

    //MyBook objektumok mentése adatbázisba a megfelelő kivételkezelés után
    @Override
    public MyBook saveMyBook(MyBook myBook) {
        if (myBook.getId() == null) {
            if (isBookIdAlreadyExists(myBook.getBookId()))
                throw new IllegalArgumentException("Ez az id már hozzá van adva a saját könyvek listájához!");
            this.myBookRepository.save(myBook);
        } else {
            MyBook existingBook = this.myBookRepository.findById(myBook.getId()).orElseThrow(
                    () -> new MyBookNotFoundException("A könyv id-ja nem található!")
            );

            if (!myBook.getBookId().equals(existingBook.getBookId())) {
                if (isBookIdAlreadyExists(myBook.getBookId())) {
                    throw new IllegalArgumentException("Ez a könyv id már hozzá van adva a saját könyvek listájához!");
                }
            }
            this.myBookRepository.save(myBook);
        }

        return myBook;
    }

    //Könyv id vizsgálata
    private boolean isBookIdAlreadyExists(Long id) {
        return myBookRepository.findById(id).isPresent();
    }

    //MyBook objektum adatainak megszerzése id alapján
    @Override
    public MyBook getMyBookById(Long id) {
        return myBookRepository.findById(id).orElseThrow(() -> {
            String errorMessage = "Az ID=" + id + " nem található.";
            return new MyBookNotFoundException(errorMessage);
        });
    }

    //MyBook objektum törlése adatbázisból
    @Override
    public void deleteMyBookById(Long id) {
        this.myBookRepository.deleteById(id);
    }
}
