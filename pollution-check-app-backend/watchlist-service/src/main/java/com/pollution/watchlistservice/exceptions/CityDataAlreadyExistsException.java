package com.pollution.watchlistservice.exceptions;

public class CityDataAlreadyExistsException extends Exception{

    public CityDataAlreadyExistsException(String message) {
        super(message);
    }
    
}