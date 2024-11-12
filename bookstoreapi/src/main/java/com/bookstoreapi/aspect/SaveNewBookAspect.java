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
public class SaveNewBookAspect {
    private static final Logger logger = LoggerFactory.getLogger("fileLogger");

    @Before("execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book))")
    public void beforeSaveEmployee(JoinPoint joinPoint) {
        Book book = (Book) joinPoint.getArgs()[0];
        if (book.getId() == null) {
            logger.info("---------- @Before - Új könyv kezdete ---------------");
            logger.info("Új könyv, saveNewBook() metódus hívása");
            List<String> parameters = Arrays.asList(
                    "Cím - " + book.getTitle() ,
                    "Szerző - " + book.getAuthor() ,
                    "Ár - " + book.getPrice() ,
                    "Elérhető - " + book.getAvailable()
            );
            logger.info("Könyv létrehozása - paraméterei: " + parameters + "'");
            logger.info("---------- @Before - Új könyv vége ---------------");
        }
    }

    @AfterReturning(value = "execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book))")
    public void afterReturningSaveEmployee(JoinPoint joinPoint) {
        Book book = (Book) joinPoint.getArgs()[0];
        logger.info("---------- @AfterReturning - Új könyv kezdete ---------------");
        List<String> parameters = Arrays.asList(
                "ID - " + book.getId() ,
                "Cím - " + book.getTitle() ,
                "Szerző - " + book.getAuthor() ,
                "Ár - " + book.getPrice() ,
                "Elérhető - " + book.getAvailable()
        );
        logger.info("Könyv létrehozva - paraméterei: " + parameters);
        logger.info("---------- @AfterReturning - Új könyv vége ---------------");
    }

    @AfterThrowing(value = "execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book)) && args(book)", throwing = "exception")
    public void afterThrowingSaveEmployee(JoinPoint joinPoint, Book book, Throwable exception) {
        logger.error("Kivétel a könyv létrehozása közben, Kivétel: '" + exception.getMessage() + "'");
    }
}
