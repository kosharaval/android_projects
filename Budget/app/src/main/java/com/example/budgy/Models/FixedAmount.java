package com.example.budgy.Models;

public class FixedAmount {
    int id;
    String name;
    Double amount;
    int userId;

    public FixedAmount() {
    }

    public FixedAmount(int id, String name, Double amount, int userId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.userId = userId;
    }

    public FixedAmount(String name, Double amount, int userId) {
        this.name = name;
        this.amount = amount;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
