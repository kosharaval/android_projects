package com.example.budgy.Models;

public class User
{
    private String ID;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String password;
    private String gender;

    public User(String fname, String lname, String email, String phone, String password, String gender)
    {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
    }

    public String getID() {
        return ID;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public User(String ID, String fname, String lname, String email, String phone, String password, String gender) {
        this.ID = ID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
    }
}
