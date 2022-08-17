package com.example.beerapp.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.beerapp.interfaces.PunkResponse;

import org.json.JSONArray;

public class PunkAPI {

    private PunkResponse punkResponse;
    private Context context;

    public PunkAPI(Activity context)
    {
        this.context = context;
        punkResponse = (PunkResponse)context;
    }

    public void call(String url)
    {
        Log.w("doInBackground","doInBackground");

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest requestObject = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest api",response.toString());
                        punkResponse.onFinish(response.toString());
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest error: ", error.toString());
            }
        });

        Log.w("b4 queue","b4 queue");

        requestQueue.add(requestObject);

        Log.w("after queue","after queue");
    }
}
