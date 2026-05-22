package com.example.lession9_1.Exception;

public class InvalidFileException  extends RuntimeException{
    public InvalidFileException(String message){
        super(message);
    }
}