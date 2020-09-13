package com.github.danielmbanks.moviecatalog.ratings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RatingNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(RatingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ratingNotFoundHandler(RatingNotFoundException ex) {
        return ex.getMessage();
    }
}
