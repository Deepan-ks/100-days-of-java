package com.project.junit;

public class Calculator {

    public long add(long a, long b) {
        return a + b;
    }

    public long subtract(long a, long b) {
        if (a < b)
            throw new IllegalArgumentException("Invalid Input");
        return a - b;
    }

    public long divide(long a, long b){
        long result;
        try{
             result = a/b;
        }catch (Exception e){
            throw new ArithmeticException("Invalid Input");
        }
        return result;
    }

}
