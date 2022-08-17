package com.example.sqlitetutorial;

public class Student
{
   private String sName;
   private String sMajor;
   private String sID;

    public Student() {
    }

    public Student(String sName, String sMajor, String sID) {
        this.sName = sName;
        this.sMajor = sMajor;
        this.sID = sID;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsMajor() {
        return sMajor;
    }

    public void setsMajor(String sMajor) {
        this.sMajor = sMajor;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }
}
