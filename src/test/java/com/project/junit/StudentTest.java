package com.project.junit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student student;

    @BeforeEach
    void intialize(){
        student = new Student();
    }

    @ParameterizedTest
    @CsvSource({
            "75, 80, true",
            "60, 70, true",
            "40, 55, false"
    })
    void isEligibleForExam(int attendancePercentage, int totalLecturesAttended, boolean expected) {
        assertEquals(expected, student.isEligibleForExam(attendancePercentage, totalLecturesAttended),
                "Eligibility criteria not satisfied for Attendance");
        System.out.println("Test passed for attendance eligibility");
    }

    public static Stream<Double> gpaList(){
        return Stream.of(9.8,9.1,9.2);
    }

    @ParameterizedTest
    @MethodSource("gpaList")
    void getRank(double gpa) {
        assertEquals("Topper", student.getRank(gpa),"GPA greater than 9.0 should be a topper");
        System.out.println("Test passed for student rank classification");
    }

    @ParameterizedTest
    @ValueSource(ints = {90,50,65})
    void hasPassed(int marks) {
        assertTrue(student.hasPassed(marks),"marks should be greater than 50");
        System.out.println("Test passed for student passing the subject");
    }

}