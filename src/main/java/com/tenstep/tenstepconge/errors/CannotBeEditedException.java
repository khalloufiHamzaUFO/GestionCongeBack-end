package com.tenstep.tenstepconge.errors;

public class CannotBeEditedException extends RuntimeException{
    private static final long serialVerisionUID = 7;
    public CannotBeEditedException(String message) {
        super(message);
    }
}
