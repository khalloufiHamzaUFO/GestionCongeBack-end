package com.tenstep.tenstepconge.errors;

public class PeriodOverlaps extends RuntimeException{
    private static final long serialVerisionUID = 6;
    public PeriodOverlaps(String message) {
        super(message);
    }
}
