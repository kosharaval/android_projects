package com.example.weatherviewerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class WeatherController extends AppCompatActivity {

    // Constants:
    final int REQUEST_CODE = 123;
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "8b10a70051e7d3ea827aa260ef98a214" ;
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    /*
    Set LOCATION_PROVIDER
    If you are using a physical device and have requested the access coarse location in your manifest file
    then you should set the location provider to network provider instead. Setting the provider as the network
    provider means that the app will request location information from cell towers.
    */
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;


    // Member Variables:
    TextView mCityLabel;
    ImageView mWeatherImage;
    TextView mTemperatureLabel;

    /* Declare a LocationManager and a LocationListener
    we're going to declare two more member variables for the location manager and for the location
    listener, the location manager is the component that will start or stop requesting location updates and
    the location listener is the component that will be notified if the location has actually changed.
    */
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        // Linking the elements in the layout to Java code
        mCityLabel = (TextView) findViewById(R.id.locationTV);
        mWeatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
        mTemperatureLabel = (TextView) findViewById(R.id.tempTV);
        ImageButton changeCityButton = (ImageButton) findViewById(R.id.changeCityButton);


        //Add an OnClickListener to the changeCityButton
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(WeatherController.this, ChangeCityController.class);
                startActivity(myIntent);
            }
        });
    }


    // Add onResume()
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Test", "onResume() called");

        Intent myIntent = getIntent();
        String city = myIntent.getStringExtra("City");

        if (city != null) {
            getWeatherForNewCity(city);

        } else {
            Log.d("Test", "Getting weather for current location");
            getWeatherForCurrentLocation();
        }
    }


    //Add getWeatherForNewCity(String city)
    private void getWeatherForNewCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);
        Connect(params);
    }

    // Add getWeatherForCurrentLocation()
    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Test", "onLocationChanged() callback received");
                //we need the longitude and latitude that we get them from the location object
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d("Test", "longitude is: " + longitude);
                Log.d("Test", "latitude is: " + latitude);
                //params will will hold the query that we need to pass to open weather map
                //The RequestParms class is part of the James networking library we added earlier
                RequestParams params = new RequestParams();
                params.put("lat", latitude);
                params.put("lon", longitude);
                params.put("appid", APP_ID);
                //the actual networking call is made via the Connect method we define below
                Connect(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Test", "onProviderDisabled() callback received");
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //  Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        /*
        When you try to write the line below, Android Studio complains since it recognizes you want to start listening for location
        updates even though you haven't requested the permission from the user, it displays a red light bulb to add the permission check and
        once you click that, Android studio enters a bunch of code (the if statement above with all commented lines). Prior to Android marshmallow
        permissions used to be granted at the point of installation of the app but since marshmallow and above, the permissions are to be
        granted when the permission is required. Now the above code implementing this run time permission in a two step process, first we
        have to request the permission with a method called request permissions which will present the user with a dialog box asking for permission
        to be granted or denied and then our activity has to listen for the result of the permission request and decide what to do if the permission
        was granted or has been denied. Now to identify and track our permission request we will need a request code, it is just a number that
        we are going to add as a constant a the top of the file (line 33 in this file). Now within the auto generated code (starting at line
        155 above) we can request the location permission from the user with ActivityCompat requestPermissions method (line 163 was added by us and
        not Android Studio!) which requires 3 inputs, the activity itself which we provide with the keyword "this" and and an array of strings
        containing the permissions being requested and the third is the request code we defined above (line 33) to track the permission request.
        Since I am using an emulator and GPS, I am going to request "ACCESS_FINE_lOCATION" but if you want to use the approximate location then you should
        specify "ACCESS_COARSE_LOCATION" here. This choice should be consistent with the network provider you chose and what you have put in
        the manifest. Next you need to check if the user granted our app location permission. Our app will get notified from the operating system
        via a call back called onRequestPermissionResult (the method below). To have Android Studio auto generate code for this method, right
        click below this method and select generate and then choose override methods and then start typing onRequestPermission.. until Android
        Studio selects onRequestPermissionResult method. Now when our app has received this callback from the operating system, the first thing we want
        to do is to make sure that the result in the callback pertains to our location permission request so we are going to insert an if statement
        that checks if the request code in the callback matches the constant that we supplied when we made the request. Then we look at the response
        which is stored in the grantResults parameter (method below) so we check in the nested if to make sure grantResults array has at least one
        element and the first element in this array is the permission granted.
        Note that when you run the app, the dialog for permission will show up on a physical device running Android marshmallow (API 23)and above but
        no dialog will show up on the older versions of android (API 21 for example).
         */
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Test", "onRequestPermissionsResult(): Permission granted!");
                getWeatherForCurrentLocation();
            } else {
                Log.d("Test", "Permission denied");
            }
        }
    }

    // Make the connection
    /*response contains the data that comes back from openweathermap.org server. we are converting it to a string and
    showing it at the log window.
    You can use copy and paste this string in an online jason visualizer such as www.jsoneditoronline.org
    or jasonmates.com to see the date in JSON object much more clearly.

     */

    private void Connect(RequestParams params) {
        //AsyncHttpClient class comes from the James library
        AsyncHttpClient client = new AsyncHttpClient();
        /*
        we use client object to make a get request
        The JsonHttpResponseHandler is the object that will be notified of the response to our
        get request and it is from the James library as well. The Json Http response handler will
        receive one of two messages on success or failure depending if the get request was successful or not
         */

        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
            @Override
            /*
            Make sure you choose Header(cz.msebera.android.httpclient) for the Header type out the
            3 choices android studio provides for you
           */
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Test", "Success! JSON: " + response.toString());
                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
                updateUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e("Test", "Fail " + e.toString());
                Log.d("Test", "Status Code " + statusCode);
                Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }

        });
    }


    //  Add updateUI()
    private void updateUI(WeatherDataModel weather) {
        mTemperatureLabel.setText(weather.getTemperature());
        mCityLabel.setText(weather.getCity());

        int resourceID = getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());
        mWeatherImage.setImageResource(resourceID);

    }


    //  Add onPause() 
    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null)
            mLocationManager.removeUpdates(mLocationListener);
    }


}
