package com.example.lec9flagdemo;

public class ColorSpec {
    private String ColorDesc;
    private int ColorVal;

    public ColorSpec(String colorDesc, int colorVal) {
        ColorDesc = colorDesc;
        ColorVal = colorVal;
    }

    public String getColorDesc() {
        return ColorDesc;
    }

    public void setColorDesc(String colorDesc) {
        ColorDesc = colorDesc;
    }

    public int getColorVal() {
        return ColorVal;
    }

    public void setColorVal(int colorVal) {
        ColorVal = colorVal;
    }
}

