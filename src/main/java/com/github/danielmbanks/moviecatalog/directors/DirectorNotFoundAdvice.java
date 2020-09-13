package com.github.danielmbanks.moviecatalog.directors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DirectorNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DirectorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String directorNotFoundHandler(DirectorNotFoundException ex) {
        return ex.getMessage();
    }
}
