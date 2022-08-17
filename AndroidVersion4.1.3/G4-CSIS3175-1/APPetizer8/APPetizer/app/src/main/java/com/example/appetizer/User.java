package com.example.appetizer;

public class User {

    int userId;
    String email;
    String fname;
    String lname;
    String phone;
    String address;
    String ZIPCode;
    String Password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZIPCode() {
        return ZIPCode;
    }

    public void setZIPCode(String ZIPCode) {
        this.ZIPCode = ZIPCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName(){
        return this.getFname() + " " + this.getLname();
    }

    public User(String email, String fname, String lname, String phone, String address, String ZIPCode, String password) {
        this.setEmail(email);
        this.setFname(fname);
        this.setLname(lname);
        this.setPhone(phone);
        this.setAddress(address);
        this.setZIPCode(ZIPCode);
        this.setPassword(password);
    }

    public User(int userId, String email, String fname, String lname, String phone, String address, String ZIPCode) {
        this.setUserId(userId);
        this.setEmail(email);
        this.setFname(fname);
        this.setLname(lname);
        this.setPhone(phone);
        this.setAddress(address);
        this.setZIPCode(ZIPCode);
    }

    public User(){

    }

}
