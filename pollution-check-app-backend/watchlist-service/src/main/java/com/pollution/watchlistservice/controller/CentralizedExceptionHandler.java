package com.pollution.watchlistservice.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import com.pollution.watchlistservice.exceptions.CityDataAlreadyExistsException;
import com.pollution.watchlistservice.exceptions.CityDataNotFoundException;

@RestControllerAdvice
public class CentralizedExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityDataNotFoundException.class)
    public String handleCityDataNotFound(CityDataNotFoundException e) {
        String msg = e.getMessage();
        return msg;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CityDataAlreadyExistsException.class)
    public String handleCityDataAlreadyExists(Exception e) {
        String msg = e.getMessage();
        return msg;
    }


}

