package com.example.budgy.Models;

public class LoggedInUser {
    private static User user = null;
    private static String currentBudget;

    public static User getLoggedInUser() {
        return LoggedInUser.user;
    }

    public static void setLoggedInUser(User user) {
        LoggedInUser.user = new User(user.getID(), user.getFname(),user.getLname(),user.getEmail(), user.getPhone(), user.getPassword(), user.getGender());
    }

    public static String getCurrentBudget() {
        return currentBudget;
    }

    public static void setCurrentBudget(String currentBudget) {
        LoggedInUser.currentBudget = currentBudget;
    }

}
