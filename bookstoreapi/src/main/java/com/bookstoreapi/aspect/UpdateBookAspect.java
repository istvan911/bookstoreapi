package com.bookstoreapi.aspect;

import com.bookstoreapi.model.Book;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class UpdateBookAspect {
    private static final Logger logger = LoggerFactory.getLogger("fileLogger");

    @Before("execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book))")
    public void beforeUpdateEmployee(JoinPoint joinPoint) {
        Book book = (Book) joinPoint.getArgs()[0];
        if (book.getId() != null) {
            logger.info("---------- @Before - Könyv szerkesztésének kezdete ---------------");
            logger.info("A könyv megegyezik, updateBook() metódus hívása");
            List<String> parameters = Arrays.asList(
                    "ID - " + book.getId() ,
                    "Cím - " + book.getTitle() ,
                    "Szerző - " + book.getAuthor() ,
                    "Ár - " + book.getPrice() ,
                    "Elérhető - " + book.getAvailable()
            );
            logger.info("Könyv létrehozása - paraméterei: " + parameters + "'");
            logger.info("---------- @Before - Könyv szerkesztésének vége ---------------");
        }
    }

    @AfterReturning("execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book))")
    public void afterReturningUpdateEmployee(JoinPoint joinPoint) {
        Book book = (Book) joinPoint.getArgs()[0];
        logger.info("---------- @AfterReturning - Könyv szerkesztésének kezdete ---------------");
        List<String> parameters = Arrays.asList(
                "ID - " + book.getId() ,
                "Cím - " + book.getTitle() ,
                "Szerző - " + book.getAuthor() ,
                "Ár - " + book.getPrice() ,
                "Elérhető - " + book.getAvailable()
        );
        logger.info("Könyv szerkesztve - paraméterei: '" + parameters);
        logger.info("---------- @AfterReturning - Könyv szerkesztésének vége ---------------");
    }

    @AfterThrowing(value = "execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book)) && args(book)", throwing = "exception")
    public void afterThrowingUpdateEmployee(JoinPoint joinPoint, Book book, Throwable exception) {
        logger.error("Kivétel a könyv szerkesztése közben, Kivétel: " + exception.getMessage());
    }
}
