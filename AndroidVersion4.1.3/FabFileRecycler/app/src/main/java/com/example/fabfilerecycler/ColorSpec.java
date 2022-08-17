package com.example.fabfilerecycler;

public class ColorSpec {

    private String ColorDesc;
    private int ColorValue;

    public ColorSpec(String colorDesc, int colorValue) {
        ColorDesc = colorDesc;
        ColorValue = colorValue;
    }

    public String getColorDesc() {
        return ColorDesc;
    }

    public void setColorDesc(String colorDesc) {
        ColorDesc = colorDesc;
    }

    public int getColorValue() {
        return ColorValue;
    }

    public void setColorValue(int colorValue) {
        ColorValue = colorValue;
    }
}
