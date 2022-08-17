package com.example.budgy.Models;

public class Budget {
    public String id;
    public String month;
    public String year;
    public String amount;
    public String userId;

    public Budget(String id, String month, String year, String amount, String userId) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.amount = amount;
        this.userId = userId;
    }

    public Budget(Budget budget) {
        this(budget.id, budget.month, budget.year, budget.amount, budget.userId);
    }

    public Budget() {
        this("","","","","");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
