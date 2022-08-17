package com.example.publicapi_tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textViewOutput;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewOutput = findViewById(R.id.textViewOutput);
        Button buttonParse =  findViewById(R.id.buttonParse);

        //
        requestQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {

        //URL for JSON file
        String URL = "https://jsonplaceholder.cypress.io/todos";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                //When request is successful
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++){

                                JSONObject todoList = jsonArray.getJSONObject(i);

                                int userId = todoList.getInt("userId");
                                int taskId = todoList.getInt("id");
                                String title = todoList.getString("title");
                                String status = todoList.getString("completed");

                                textViewOutput.append("USER ID: " + String.valueOf(userId) +
                                        " Task ID: " + String.valueOf(taskId) +
                                        "\n Title: " + title +
                                        "\n Completed: " + status +
                                        "\n\n");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, //When request is fails
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
}