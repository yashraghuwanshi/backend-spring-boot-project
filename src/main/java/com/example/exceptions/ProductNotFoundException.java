package com.example.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super();
    }

    public ProductNotFoundException(String msg){
        super(msg);
    }

    public ProductNotFoundException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
