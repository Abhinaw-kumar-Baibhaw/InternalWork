package com.example.TestOrderPlacement.exception;


public class NoEnoughInventoryException extends RuntimeException{
    public NoEnoughInventoryException(String message){
               super(message);
    }
}


