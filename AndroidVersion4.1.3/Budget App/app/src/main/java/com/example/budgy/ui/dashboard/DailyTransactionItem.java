package com.example.budgy.ui.dashboard;

public class DailyTransactionItem {

    private String type;
    private String category;
    private double amount;
    private String receipt;
    private int date;
    private int userId;
    String title;
    int id;

    public DailyTransactionItem(String type, String category, double amount, String receipt, int date, String title, int userId,int id) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.receipt = receipt;
        this.date = date;
        this.userId = userId;
        this.title = title;
        this.id = id;
    }

    public String getType() { return type; }

    public void setType(String type) {  this.type = type;  }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category;}

    public double getAmount() { return amount;}

    public void setAmount(double amount) { this.amount = amount;}

    public String getReceipt() {   return receipt;}

    public void setReceipt(String receipt) {   this.receipt = receipt;}

    public int getDate() { return date;}

    public void setDate(int date) {    this.date = date;}

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
