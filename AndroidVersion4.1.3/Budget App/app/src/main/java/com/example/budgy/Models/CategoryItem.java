package com.example.budgy.Models;

public class CategoryItem {

    public int id;
    public String categoryName;
    public String status;
    public int userId;

    public CategoryItem(int id, String categoryName, String status, int userId) {
        this.id = id;
        this.categoryName = categoryName;
        this.status = status;
        this.userId = userId;
    }

    public CategoryItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
