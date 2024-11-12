package com.bookstoreapi.aspect;

import com.bookstoreapi.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j(topic = "fileLogger")
public class SaveBookAspect {
    @Before("execution(* com.bookstoreapi.service.BookService.saveBook(..))")
    public void beforeSaveEmployee(JoinPoint joinPoint) {
        Book book = (Book) joinPoint.getArgs()[0];
        if (book.getId() == null) {
            log.info("---------- @Before - Könyv mentésének kezdete ---------------");
            List<String> parameters = Arrays.asList(
                    "Cím - " + book.getTitle() ,
                    "Szerző - " + book.getAuthor() ,
                    "Ár - " + book.getPrice() ,
                    "Elérhető - " + book.getAvailable()
            );
            log.info("Könyv létrhozása - paraméterei: " + parameters + "'");
            log.info("---------- @Before Könyv mentésének vége -----------------");
        } else {
            log.info("---------- @Before - Könyv szerkesztésének kezdete ---------------");
                List<String> parameters = Arrays.asList(
                        "ID - " + book.getId() ,
                        "Cím - " + book.getTitle() ,
                        "Szerző - " + book.getAuthor() ,
                        "Ár - " + book.getPrice() ,
                        "Elérhető - " + book.getAvailable()
                );
            log.info("Könyv szerkesztése - paraméterei: " + parameters + "'");
            log.info("---------- @Before - Könyv szerkesztésének vége -----------------");
        }
    }

    @AfterReturning("execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book))")
    public void afterReturningSaveEmployee(JoinPoint joinPoint) {
        Book book = (Book) joinPoint.getArgs()[0];
        log.info("---------- @AfterReturning - Könyv mentésének kezdete ---------------");
        List<String> parameters = Arrays.asList(
                "ID - " + book.getId() ,
                "Cím - " + book.getTitle() ,
                "Szerző - " + book.getAuthor() ,
                "Ár - " + book.getPrice() ,
                "Elérhető - " + book.getAvailable()
        );
        log.info("Könyv elmentve - paraméterei: '" + parameters);
        log.info("---------- @AfterReturning - Könyv mentésének vége -----------------");
    }

    @AfterThrowing(value = "execution(* com.bookstoreapi.service.BookService.deleteBookById(com.bookstoreapi.model.Book))", throwing = "exception")
    public void afterThrowingSaveEmployee(JoinPoint joinPoint, Throwable exception) {
        log.error("Kivétel a könyv mentése közben a metódus használatakor: {}: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}
