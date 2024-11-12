package com.bookstoreapi.service;

import com.bookstoreapi.model.MyBook;

import java.util.List;
    //MyBook objektumokkal műveleteket végző metódusok interfésze
public interface MyBookService {
    List<MyBook> getAllMyBooks();
    MyBook saveMyBook(MyBook myBook);
    MyBook getMyBookById(Long id);
    void deleteMyBookById(Long id);
}
