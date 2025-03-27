package com.project.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankAccountTest {

    BankAccount account;

    @BeforeAll
    static void startTest() {
        System.out.println("Setup before all tests");
    }

    @BeforeEach
    void initialize() {
        account = new BankAccount(10000);
        System.out.println("Setup before each test");
    }

    @DisplayName("Cash deposit function")
    @Test
    @Order(1)
    void deposit() {
        account.deposit(5000);
        assertEquals(15000, account.getBalance(), "New balance should be 15000");
        System.out.println("Test deposit passed");
    }

    @Test
    @Order(2)
    void deposit_exceptionThrowsForInvalidInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> account.deposit(-500));
        assertEquals("Deposit amount must be positive", exception.getMessage()); // ✅ Correctly validating exception message
    }

    @DisplayName("Cash withdraw function")
    @Test
    @Order(3)
    void withdraw() {
        account.withdraw(8000);
        assertEquals(2000, account.getBalance(), "Remaining balance should be 2000");
        System.out.println("Test withdrawal passed");
    }

    @Test
    void withdraw_exceptionThrowsForInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(45000), "Throws exception if withdraw amount is higher than current balance.");
        System.out.println("Test withdrawal exception passed");
    }

    @AfterEach
    void testLogs() {
        System.out.println("Clean up after each test");
    }

    @AfterAll
    static void endTest() {
        System.out.println("Cleanup after all tests");
    }
}