## **1️⃣ What is JUnit?**

JUnit is a **Java testing framework** used to write and run **unit tests** for Java applications.
To perform unit testing, we need to create test cases.
The **unit test case** is a code which ensures that the program logic works as expected.

## **2️⃣ Why Use JUnit?**

Without JUnit, testing looks like this:

``` java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.add(2, 3) == 5 ? "Test Passed" : "Test Failed");
    }
}

```

🚨 **Problems:**  
❌ Manual verification needed.  
❌ No proper test reporting.  
❌ Hard to maintain when tests increase.

✅ **JUnit solves this by automating the testing process.**

## **3️⃣ Junit Architecture**

The JUnit 5 version has three different modules for defining and performing different functionalities in testing. The components are:

**JUnit Platform**
- In Java If we want to run the test cases, we need JVM. So, the JUnit Platform provides a launching mechanism for testing frameworks on the JVM.

**JUnit Jupiter**
- The second component in Junit 5 is JUnit Jupiter. This component provides new programming techniques for developing the test cases in JUnit 5.

**JUnit Vintage**
- The JUnit Vintage functionality is different from the above Two. Before JUnit 5, The Tester uses JUnit 4, JUnit 3, or some other Versions.
- The Main functionality of JUnit Vintage is allowing JUnit 3 and JUnit 4 Test cases on the JUnit 5 Platform.


## **4️⃣ Understanding Assertions in JUnit 5**

**Assertions** are used to verify expected results in JUnit.

| Assertion                                       | Purpose                          |
| ----------------------------------------------- | -------------------------------- |
| `assertEquals(expected, actual)`                | Checks if two values are equal   |
| `assertTrue(condition)`                         | Checks if a condition is `true`  |
| `assertFalse(condition)`                        | Checks if a condition is `false` |
| `assertThrows(Exception.class, () -> method())` | Checks if an exception is thrown |
|                                                 |                                  |
|                                                 |                                  |
## **5️⃣ JUnit 5 Lifecycle Methods**

JUnit provides **lifecycle annotations** to execute setup/cleanup logic **before and after** test methods. These are:

### **🔹 Lifecycle Annotations**

|Annotation|Description|
|---|---|
|`@BeforeEach`|Runs **before** each test method|
|`@AfterEach`|Runs **after** each test method|
|`@BeforeAll`|Runs **once before all** tests (static method)|
|`@AfterAll`|Runs **once after all** tests (static method)|
### **Why `assertAll()`?**

Instead of writing multiple separate assertions, `assertAll()` allows us to **group multiple assertions** inside a single test case. If one assertion fails, the rest will still be executed (unlike normal assertions where failure stops execution).


## **6️⃣ Lifecycle Methods Example Use Case**

Let's say we have a `Calculator` class, and we want to **initialize** an instance before each test and **clean up** after tests.

### **✅ Example with Lifecycle Methods**

``` java
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeAll
    static void setupAll() {
        System.out.println("Runs once before all tests");
    }

    @BeforeEach
    void setup() {
        calculator = new Calculator();  // Initialize before each test
        System.out.println("Setup before each test");
    }

    @Test
    void testAddition() {
        assertEquals(5, calculator.add(2, 3), "Addition should be 5");
    }

    @Test
    void testSubtraction() {
        assertEquals(3, calculator.subtract(6, 3), "Subtraction should be 3");
    }

    @AfterEach
    void teardown() {
        System.out.println("🗑 Cleanup after each test");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("Runs once after all tests");
    }
}

```

## **7️⃣Points to be remembered**
### **`@BeforeAll` & `@AfterAll` Must Be Static**

🔹 `@BeforeAll` and `@AfterAll` **must be `static` methods** because they run **before/after** all test instances are created.  
🔹 If they are **non-static**, JUnit **will not execute them**.

✅ **Correct way:**

```java
@BeforeAll
static void setupAll() { 
    System.out.println("Runs once before all tests");
}
```

❌ **Incorrect way (JUnit won’t call this method)**

```java
@BeforeAll
void setupAll() {  // ❌ NOT STATIC
    System.out.println("This will NOT run!");
}
```

💡 **Fix:** Add `static`.

---

### **`@BeforeEach` & `@AfterEach` Run for Every Test**

🔹 These methods execute **before and after every test method**, so don’t put expensive operations inside them.  
🔹 Example: **Re-initializing a database connection in `@BeforeEach` is bad**; use `@BeforeAll` instead.

---

### **Test Execution Order Is Not Guaranteed**

🔹 JUnit **does not guarantee** the order in which tests run.  
🔹 If you need a specific order, use `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)` and `@Order(n)`.

✅ **Example:**

```java
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTest {
    
    @Test
    @Order(2)
    void secondTest() {
        System.out.println("Second Test");
    }

    @Test
    @Order(1)
    void firstTest() {
        System.out.println("First Test");
    }
}
```

📌 **Now, `firstTest()` runs before `secondTest()`.**

---

### **Exception in Lifecycle Method Fails All Tests**

🔹 If `@BeforeAll` **throws an exception**, **none of the tests will run**.  
🔹 If `@BeforeEach` **throws an exception**, **only that test is skipped**.

✅ **Example:**

```java
@BeforeAll
static void setupAll() {
    throw new RuntimeException("Setup failed!");  // ❌ All tests will be skipped!
}
```

---

### ** `@AfterAll` Runs Even If Tests Fail**

🔹 `@AfterAll` **always runs**, even if some tests fail.  
🔹 Useful for **closing connections or cleaning up resources** after tests.

---

### **✅ Summary of Tricky Points**

✅ `@BeforeAll` and `@AfterAll` **must be static**.  
✅ `@BeforeEach` and `@AfterEach` **run for every test**—avoid expensive operations.  
✅ **Test execution order is not guaranteed** unless explicitly set.  
✅ **Exception in `@BeforeAll` fails all tests**, but `@BeforeEach` only skips that test.  
✅ **`@AfterAll` runs even if tests fail** (useful for cleanup).

---
## **8️⃣Best Practices:**

#### **1️⃣ Fix the Exception Message in `assertThrows()`**

Currently, your message in `assertThrows()` is written as **part of the failure message** rather than the **exception message check**.  
✅ If you want to **validate the exception message**, you can do this:

**🔹 Recommended Fix:**

```java
@Test
void deposit_exceptionThrowsForInvalidInput() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> account.deposit(-500));
    assertEquals("Deposit amount must be positive", exception.getMessage()); // ✅ Correctly validating exception message
}
```

This makes sure that the thrown exception contains the correct message.

---

#### **2️⃣ (Optional) Use `@DisplayName` for More Readable Test Names**

JUnit 5 allows you to use `@DisplayName` to make test names **more human-readable**.  
**🔹 Example:**

```java
@DisplayName("💰 Deposit should increase the balance correctly")
@Test
void deposit() {
    account.deposit(5000);
    assertEquals(15000, account.getBalance(), "New balance should be 15000");
}
```

This makes test reports and logs **more descriptive**.

---

#### **3️⃣ (Optional) Use `@TestMethodOrder` If Order Matters**

Your tests **run independently**, but if you ever need a controlled execution order, you can use:

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
```

Then, assign orders:

```java
@Test
@Order(1)
void deposit() { ... }

@Test
@Order(2)
void withdraw() { ... }
```

This isn’t needed **unless test dependencies exist**, but good to know.

---
## **9️⃣ Parameterized Tests in JUnit 5**

Instead of writing multiple test cases manually with different inputs, **Parameterized Tests** allow us to **run the same test multiple times** with different sets of inputs.

### **1️⃣ Why Use Parameterized Tests?**

✅ **Avoid Code Duplication** – No need to write multiple test methods.  
✅ **Improve Readability** – The test remains clean while covering multiple cases.  
✅ **Increase Maintainability** – Easy to add new test cases without modifying test methods.

---

### **2️⃣ How to Use Parameterized Tests?**

JUnit 5 provides various **source annotations** to pass different inputs. The most commonly used ones are:

|Annotation|Description|
|---|---|
|`@ValueSource`|Passes a single primitive type (int, double, string, etc.).|
|`@CsvSource`|Passes multiple arguments as CSV values.|
|`@CsvFileSource`|Reads test data from a CSV file.|
|`@MethodSource`|Provides inputs using a static method.|
|`@EnumSource`|Passes enum values.|

---

### **3️⃣ Example: Using `@ValueSource`**

Let’s test a simple method that checks whether a number is even.

#### **📝 Code:**

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberUtilsTest {

    static boolean isEven(int number) {
        return number % 2 == 0;
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 8, 10})  // Test with multiple values
    void testIsEven(int number) {
        assertTrue(isEven(number), number + " should be even");
    }
}
```

✅ **JUnit will run this test 5 times**, once for each value in `{2, 4, 6, 8, 10}`.

---

### **4️⃣ Example: Using `@CsvSource` (Multiple Parameters)**

Let’s test a method that **adds two numbers**.

#### **📝 Code:**

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    static int add(int a, int b) {
        return a + b;
    }

    @ParameterizedTest
    @CsvSource({
        "2, 3, 5",
        "4, 5, 9",
        "10, 20, 30"
    })
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, add(a, b), "Addition result should be correct");
    }
}
```

✅ The test runs **three times**, once for each set of values.

---

### **5️⃣ Example: Using `@MethodSource`**

Instead of hardcoding values, we can **provide inputs from a method**.

#### **📝 Code:**

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

    static Stream<String> stringProvider() {
        return Stream.of("java", "junit", "test");
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void testStringLength(String input) {
        assertEquals(4, input.length(), "String length should be 4");
    }
}
```

✅ The test will run **three times** for `"java"`, `"junit"`, and `"test"`.

---

## **1️⃣0️⃣ Tagging Tests with `@Tag` `@Disabled`**

### **📌 Tagging Tests with `@Tag`**
JUnit allows us to categorize tests using `@Tag`, which is useful for:  
✅ Running only specific groups of tests (e.g., fast, slow, database, UI tests).  
✅ Skipping unnecessary tests in different environments (e.g., CI/CD).

#### **Example:**

```java
@Tag("fast")
@Test
void quickTest() {
    assertTrue(5 > 2, "This test is fast");
}

@Tag("slow")
@Test
void timeConsumingTest() {
    Thread.sleep(5000);
    assertEquals(10, 5 + 5, "This test is slow");
}
```

👉 You can run specific tags using:

```
mvn test -Dgroups=fast
```

---

### **📌 Skipping Tests with `@Disabled`**

If you want to **temporarily disable a test**, use `@Disabled`.  
This is useful when:  
✅ A test is failing due to a known bug.  
✅ You are working on an incomplete feature.

#### **Example:**

```java
@Disabled("Bug ID: 123 - Fix pending")
@Test
void skippedTest() {
    fail("This test should not run");
}
```

---
### **📌 3. Timeouts with `assertTimeout()`**

JUnit allows you to set a **maximum execution time** for a test using `assertTimeout()`.  
This is useful when:  
✅ Ensuring methods complete within acceptable performance limits.  
✅ Detecting performance bottlenecks.

#### **Example:**

```java
import static org.junit.jupiter.api.Assertions.assertTimeout;
import java.time.Duration;

@Test
void testMethodExecutesWithinTime() {
    assertTimeout(Duration.ofMillis(500), () -> {
        // Simulating some work
        Thread.sleep(400);
    }, "Test should complete within 500ms");
}
```

👉 If execution **exceeds 500ms**, the test will **fail**.

---

## **1️⃣1️⃣Nested Tests (`@Nested`)**

#### **Understanding `@Nested` Annotation in JUnit**

In **JUnit 5**, `@Nested` is used to **structure related test cases** inside an outer test class.  
Instead of having **all tests in one big class**, `@Nested` allows you to **group them logically** into inner classes.

---

#### **💡 Why Use `@Nested`?**

✅ Helps organize **test cases with similar conditions**.  
✅ Avoids **mixing multiple scenarios** in one class.  
✅ Improves **code readability** and **test maintenance**.

---

#### **🚀 Example Without `@Nested` (Messy Structure)**

Imagine testing a `Product` class with **"In-Stock"** and **"Out-of-Stock"** conditions.

```java
class ProductTest {
    Product laptop;
    Product mobile;

    @BeforeEach
    void initialize() {
        laptop = new Product("Lenovo LOQ", 65000, true);
        mobile = new Product("Samsung S21", 45000, false);
    }

    @Test
    void laptopIsInStock() {
        assertTrue(laptop.isInStock(), "Laptop should be in stock");
    }

    @Test
    void mobileIsOutOfStock() {
        assertFalse(mobile.isInStock(), "Mobile should be out of stock");
    }

    @Test
    void productNameIsNotNull() {
        assertNotNull(laptop.getName(), "Product name should not be null");
    }
}
```

🚨 **Problem**:

- This test **mixes different product states (in-stock & out-of-stock)** in the same class.

- As the class grows, it becomes **hard to manage**.


---

### **✅ Solution: Using `@Nested` for Better Structure**

Now, we **group related tests** inside inner classes using `@Nested`:

```java
class ProductTest {

    @Nested
    class AvailableProductTests {
        Product laptop;

        @BeforeEach
        void setUp() {
            laptop = new Product("Lenovo LOQ", 65000, true);
        }

        @Test
        void laptopIsInStock() {
            assertTrue(laptop.isInStock(), "Laptop should be in stock");
        }

        @Test
        void productNameIsNotNull() {
            assertNotNull(laptop.getName(), "Product name should not be null");
        }
    }

    @Nested
    class OutOfStockProductTests {
        Product mobile;

        @BeforeEach
        void setUp() {
            mobile = new Product("Samsung S21", 45000, false);
        }

        @Test
        void mobileIsOutOfStock() {
            assertFalse(mobile.isInStock(), "Mobile should be out of stock");
        }
    }
}
```

---

### **🎯 Benefits of `@Nested`**

-  **Separation of Concerns** → "Available Products" and "Out-of-Stock Products" have separate groups.
- **Better Readability** → Easier to find test cases when debugging.
- **Encapsulation** → Each `@Nested` class has **its own `@BeforeEach`**, avoiding interference between tests.

---

---

## **1️⃣2️⃣ Dynamic Tests with `@TestFactory`**

Unlike normal `@Test` methods, **`@TestFactory` generates test cases dynamically at runtime.**

---

### **💡 Why Use `@TestFactory`?**

Instead of writing multiple test cases manually, we can generate them **dynamically based on a data set.**  
✅ Useful when testing **multiple inputs dynamically.**  
✅ Eliminates **duplicate test methods.**  
✅ Helps in **data-driven testing** (similar to parameterized tests but more flexible).

---
Got it! Let's **simplify** the concept step by step using the **Product Discount example.**

---

### **📌 What is `@TestFactory`?**

✅ Unlike `@Test`, which defines a single test case, `@TestFactory` **dynamically generates multiple test cases** at runtime.  
✅ It **returns a collection of test cases** instead of a single test.

---

### **💡 Think About It Like This**

Instead of writing **3 separate test methods** like this:

```java
@Test
void testDiscountForLaptop() { /* logic here */ }

@Test
void testDiscountForMobile() { /* logic here */ }

@Test
void testDiscountForHeadphones() { /* logic here */ }
```

We can **generate all these test cases dynamically** using `@TestFactory`.  
It helps when you have **multiple values to test, without repeating code!** 🚀

---

### **📌 Let's Start Step by Step**

#### **1️⃣ Create the `Product` Class**

```java
class Product {
    private String name;
    private double price;
    private double discount;

    public Product(String name, double price, double discount) {
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public double calculateDiscountedPrice() {
        return price - (price * discount / 100);
    }
}
```

✅ **This class has a method `calculateDiscountedPrice()`** that applies the discount.

---

#### **2️⃣ Now, Create a JUnit Test with `@TestFactory`**

```java
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class ProductTest {

    @TestFactory
    Stream<DynamicTest> testProductDiscountCalculation() {
        List<Product> products = List.of(
                new Product("Laptop", 50000, 10),  // 10% discount
                new Product("Mobile", 30000, 20),  // 20% discount
                new Product("Headphones", 2000, 15) // 15% discount
        );

        return products.stream().map(product -> 
            dynamicTest("Testing discount for " + product.getName(), () -> {
                double expectedFinalPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
                assertEquals(expectedFinalPrice, product.calculateDiscountedPrice(), "Discounted price calculation failed");
            })
        );
    }
}
```

---

### **📌 Explanation of Code**

1️ **Create a list of products** (Laptop, Mobile, Headphones).  
2️ **Loop through each product** and **generate a test dynamically**.  
3️ **Each test checks** if the **discounted price is correct**.  
4️⃣ `dynamicTest("Test Name", () -> { Test Logic })` is used to generate tests.

---

### **📌 Example Test Execution Output**

```bash
✔ Testing discount for Laptop ✅
✔ Testing discount for Mobile ✅
✔ Testing discount for Headphones ✅
```

---
Notes Updated [26-03-2025]