package com.example.retrofitapi_tutorial;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    //URL of the Json placeholder and write the relevant string
    @GET("todos")
    Call<List<Post>> getPosts();
}
