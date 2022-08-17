package com.example.weatherviewerapp;
//The WeatherDataModel is a class that will be responsible for holding and managing our weather data.

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel {

    // Declare the member variables
    private String mTemperature ;
    private String mCity;
    private String mIconName ;
    private int mCondition ;

    // Create a WeatherDataModel from a JSON
    /*
    We are going to access data from the jsonObject using their corresponding keys and it is
    handy to have the weather JSON hierarchy displayed on a website like jasonmate.com to see the keys.
    Also note the use of a try catch black due to the posibility of the different errors we might encounter
     */
    public static WeatherDataModel fromJson(JSONObject jsonObject){
        try {
            WeatherDataModel weatherData = new WeatherDataModel();
            weatherData.mCity = jsonObject.getString("name");
            //The weather code for the condition is nested under wether, then 0, then id so we use the line below to
            //get to the condition
            weatherData.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherData.mIconName = updateWeatherIcon(weatherData.mCondition);
            //Use -273.15 to To get the temperature in Celsius
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15 ;
            int roundedValue = (int) Math.rint(tempResult);

            weatherData.mTemperature = Integer.toString(roundedValue);
            return weatherData;
        }catch(JSONException e){
            e.printStackTrace();
            return null ;
        }
    }

    // Get the weather image name from the condition
    /*
    open weather map has a number of predefined weather codes to identify the type of weather and
    this is what we are using the code below
     */
    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

    // Create getter methods for temperature, city, and icon name


    public String getTemperature() {
        return mTemperature + "°";
    }

    public String getCity() {
        return mCity;
    }

    public String getIconName() {
        return mIconName;
    }
}

