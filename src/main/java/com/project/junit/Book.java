package com.project.junit;

public class Book {
    private String title;
    private int daysLate;

    public Book(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDaysLate() {
        return daysLate;
    }

    public void setDaysLate(int daysLate) {
        this.daysLate = daysLate;
    }

    public double calculateLateFee() {
        double expectedLateFee;
        int days = this.daysLate;

        if (days < 0) {
            throw new IllegalArgumentException("daysLate can't be negative");
        } else if (days == 0) {
            expectedLateFee = 0;
        } else if (days <= 5) {
            expectedLateFee = 2.0 * days;
        } else if (days <= 10) {
            expectedLateFee = (5 * 2) + ((days - 5) * 3);
        } else {
            expectedLateFee = (5 * 2) + (5 * 3) + ((days - 10) * 5);
        }
        return expectedLateFee;
    }
}
