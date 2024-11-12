package com.bookstoreapi.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j(topic = "fileLogger")
public class GlobalExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public String handleEmployeeNotFoundException(BookNotFoundException e, Model model, HttpServletResponse response) {
        model.addAttribute("errorMessage", e.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("statusCode", response.getStatus());
        model.addAttribute("statusReason", HttpStatus.NOT_FOUND.getReasonPhrase());
        log.error(e.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Model model, HttpServletResponse response) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // Klasszikus módszer
        /*List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getDefaultMessage());
        }*/

        // Új módszer
        List<String> errorMessages = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        model.addAttribute("errorMessage", errorMessages);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        model.addAttribute("statusCode", response.getStatus());
        model.addAttribute("statusReason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        log.error(errorMessages.toString());
        return "errorPage";
    }
}
