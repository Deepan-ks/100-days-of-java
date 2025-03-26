package com.project.junit;

public class Student {

    public boolean isEligibleForExam(int attendancePercentage, int totalLecturesAttended) {
        if (attendancePercentage >= 75 || totalLecturesAttended >= 60)
            return true;
        return false;
    }

    public Boolean hasPassed(int marks){
        if(marks >= 50)
            return true;
        return false;
    }

    public String getRank(double cgpa) {
        if (cgpa >= 9.0)
            return "Topper";
        else if (cgpa >= 7.5)
            return "First Class";
        else if (cgpa >= 6.0)
            return "Second Class";
        else
            return "fail";
    }
}
