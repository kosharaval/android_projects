package com.example.beerapp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Beer {

    private int id;
    private String name;
    private String tagline;
    private String firstbrew;
    private String description;
    private String imageLink;
    private List<String> foodPair;

    public Beer()
    {
        this.id = 0;
        this.name = "";
        this.tagline = "";
        this.firstbrew = "";
        this.description = "";
        this.imageLink = "";
        this.foodPair = new ArrayList<>();
    }


    public static com.example.beerapp.model.Beer convertToBeerObject(JSONObject obj)
    {
        try
        {
            com.example.beerapp.model.Beer beer = new com.example.beerapp.model.Beer();
            beer.setId(Integer.parseInt(obj.get("id").toString()));
            beer.setName(obj.get("name").toString());
            beer.setImageLink(obj.get("image_url").toString());
            beer.setTagline(obj.get("tagline").toString());
            beer.setDescription(obj.get("description").toString());
            beer.setFirstbrew(obj.get("first_brewed").toString());

            JSONArray foodpair = obj.getJSONArray("food_pairing");
            List<String> foodpairList = new ArrayList<>();
            for(int i = 0; i < foodpair.length(); i++)
            {
                foodpairList.add(foodpair.getString(i));
            }
            beer.setFoodPair(foodpairList);

            return beer;
        }catch(JSONException jsex)
        {
            Log.e("Exception beer", jsex.getMessage());
        }

        return null;
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

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getFirstbrew() {
        return firstbrew;
    }

    public void setFirstbrew(String firstbrew) {
        this.firstbrew = firstbrew;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<String> getFoodPair() {
        return foodPair;
    }

    public void setFoodPair(List<String> foodPair) {
        this.foodPair = foodPair;
    }
}
