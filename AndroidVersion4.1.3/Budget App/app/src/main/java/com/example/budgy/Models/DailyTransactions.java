package com.example.budgy.Models;

import android.net.Uri;

import java.net.URI;

public class DailyTransactions {
    int id;
    String category;
    Double amount;
    String type;
    int date;
    String receipt;
    int userId;
    String title;

    public DailyTransactions(int id, String category, Double amount, String type, int date, String receipt,String title, int userId) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.receipt = receipt;
        this.userId = userId;
        this.title = title;
    }

    public DailyTransactions() {

    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}

    public String getCategory() {   return category;}

    public void setCategory(String category) {  this.category = category;}

    public Double getAmount() { return amount;}

    public void setAmount(Double amount) {  this.amount = amount;}

    public String getType() {   return type;}

    public void setType(String type) {  this.type = type;}

    public int getDate() { return date;}

    public void setDate(int date) {  this.date = date;}

    public String getReceipt() { return receipt;}

    public void setReceipt(String receipt) {  this.receipt = receipt;}

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getUserId() {  return userId; }

    public void setUserId(int userId) {  this.userId = userId; }
}
