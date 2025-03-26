package com.project.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @TestFactory
    Stream<DynamicTest> testCalculateLateFee() {
        List<Book> bookList = Arrays.asList(
                new Book("Atomic Habits", 5),  // ₹10 (5 * 2)
                new Book("Show your work", 15), // ₹75 (5*2 + 5*3 + 5*5)
                new Book("Psychology of money", 0), // ₹0
                new Book("Deep Work", -3),  // Should throw an exception
                new Book("Eat the frog", 8)  // ₹24 (5*2 + 3*3)
        );

        return bookList.stream().map(book ->
                DynamicTest.dynamicTest("Testing late fee for: " + book.getTitle(), () -> {
                    if (book.getDaysLate() < 0) {
                        assertThrows(IllegalArgumentException.class, () -> book.calculateLateFee(),
                                "Negative days late should throw an exception");
                    } else {
                        double expectedLateFee;
                        int days = book.getDaysLate();

                        if (days == 0) {
                            expectedLateFee = 0;
                        } else if (days <= 5) {
                            expectedLateFee = 2.0 * days;
                        } else if (days <= 10) {
                            expectedLateFee = (5 * 2) + ((days - 5) * 3);
                        } else {
                            expectedLateFee = (5 * 2) + (5 * 3) + ((days - 10) * 5);
                        }

                        assertEquals(expectedLateFee, book.calculateLateFee(), "Late fee calculation incorrect!");
                    }
                })
        );
    }
}
