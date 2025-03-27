package com.project.junit;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product laptop;
    Product mobile;

    @BeforeEach
    void intialize() {
        laptop = new Product("Lenovo LOQ", 65000, true);
        mobile = new Product("Samsung S21", 45000, false);
    }


    @Nested
    class LaptopBasedTestCases {
        @Disabled("Bug - Fix pending")
        @Tag("quickTest")
        @Test
        void productNameNotNull() {
            assertNotNull(laptop.getName(), "Product name should not be null");
        }

        @Tag("Validation Test")
        @Test
        void validateProductProperties() {
            assertAll("Product properties",
                    () -> assertNotNull(laptop.getName(), "Product name should not be null"),
                    () -> assertTrue(laptop.getPrice() > 0, "Product price should be greater than zero"),
                    () -> assertTrue(laptop.isInStock(), "Product should be in stock")
            );
        }

        @Tag("quickTest")
        @Test
        void productInStock() {
            assertTrue(laptop.isInStock(), "product should be in stock");
        }
    }

    @Nested
    class MobileBasedTestCases {

        @Tag("quickTest")
        @Test
        void productOutOfStock() {
            assertFalse(mobile.isInStock(), "product should be out of stock");
        }

        @Test
        void searchResultWithinTime() {
            assertTimeout(Duration.ofMillis(5000),
                    () -> mobile.fetchSearchResults(), "Search operation should be completed within 50ms");
        }
    }






}