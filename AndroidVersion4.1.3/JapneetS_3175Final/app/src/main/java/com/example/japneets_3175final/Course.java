package com.example.japneets_3175final;

import java.time.LocalDate;

public class Course {

    int CourseId;
    LocalDate CourseDate;
    String CourseName;
    String CoursePic;
    int CourseDrawable;
    double CoursePrice;
    int CourseDiscount;
    int NumSession;
    double SubTotal;

    public Course(int courseId, LocalDate courseDate, String courseName, String coursePic, int courseDrawable, double coursePrice, int courseDiscount) {
        CourseId = courseId;
        CourseDate = courseDate;
        CourseName = courseName;
        CoursePic = coursePic;
        CourseDrawable = courseDrawable;
        CoursePrice = coursePrice;
        CourseDiscount = courseDiscount;
    }

    public Course(String courseName, double coursePrice, int courseDiscount, int numSession, double subTotal) {
        CourseName = courseName;
        CoursePrice = coursePrice;
        CourseDiscount = courseDiscount;
        NumSession = numSession;
        SubTotal = subTotal;
    }

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public LocalDate getCourseDate() {
        return CourseDate;
    }

    public void setCourseDate(LocalDate courseDate) {
        CourseDate = courseDate;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCoursePic() {
        return CoursePic;
    }

    public void setCoursePic(String coursePic) {
        CoursePic = coursePic;
    }

    public double getCoursePrice() {
        return CoursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        CoursePrice = coursePrice;
    }

    public int getCourseDiscount() {
        return CourseDiscount;
    }

    public void setCourseDiscount(int courseDiscount) {
        CourseDiscount = courseDiscount;
    }

    public int getNumSession() {
        return NumSession;
    }

    public void setNumSession(int numSession) {
        NumSession = numSession;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }

    public int getCourseDrawable() {
        return CourseDrawable;
    }

    public void setCourseDrawable(int courseDrawable) {
        CourseDrawable = courseDrawable;
    }
}
