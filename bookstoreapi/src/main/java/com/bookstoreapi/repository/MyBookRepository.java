package com.bookstoreapi.repository;

import com.bookstoreapi.model.Book;
import com.bookstoreapi.model.MyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyBookRepository extends JpaRepository<MyBook, Long>{
    Optional<MyBook> findById(Long id);
}
