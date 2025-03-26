package com.project.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void test() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3), "Addition result should be 5");
    }

    @Test
    void subtract() {
        Calculator calc = new Calculator();
        assertEquals(3, calc.subtract(6, 3), "Subtraction result should be 3");
    }

    @Test
    void subtract_throwsExceptionForInvalidInput(){
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class,
                () -> calc.subtract(1,5), "Should throw IllegalArgumentException when a < b");
    }

    @Test
    void divide_throwsExceptionForInvalidInput() {
        Calculator calc = new Calculator();
        assertThrows(ArithmeticException.class,
                () -> calc.divide(8, 0), "Should throw ArithmeticException when a is divided by zero");
    }
}