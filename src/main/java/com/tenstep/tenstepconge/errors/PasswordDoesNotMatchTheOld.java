package com.tenstep.tenstepconge.errors;

public class PasswordDoesNotMatchTheOld extends RuntimeException{
    private static final long serialVerisionUID = 5;
    public PasswordDoesNotMatchTheOld(String message) {
        super(message);
    }
}
