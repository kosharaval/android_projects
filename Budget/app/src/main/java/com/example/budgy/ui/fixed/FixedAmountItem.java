package com.example.budgy.ui.fixed;

public class FixedAmountItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private int id;

    public FixedAmountItem(int mImageResource, String mText1, String mText2, int id) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.id = id;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public int getId() {
        return id;
    }
}
