package com.example.reza.beeradviser;

import java.util.ArrayList;
import java.util.List;

public class BeerExpert {
    List<String> getBrands(String color){
        List<String> brands = new ArrayList<>();

        if (color.equals("amber")) {
            brands.add ("Jack Amber");
            brands.add("Red Moose");
        }
        else if (color.equals("brown")) {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        else if (color.equals("dark")){
            brands.add("Black Lager");
            brands.add("Dry Stout");
            brands.add("Dunkel");
        }
        else {
            brands.add("Munich Helles");
            brands.add("Dortmunder Export");
            brands.add("Lite American Lager");
        }
        return brands ;
    }
}
