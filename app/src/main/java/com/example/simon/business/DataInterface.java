package com.example.simon.business;

import com.example.simon.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DataInterface {

    // User methods
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{id}")
    Call<List<User>> getUser(@Path("id") int userId);

    @POST("users/connect")
    Call<User> connect(@Body User user);

    @POST("users")
    Call<User> post(@Body User user);

    @GET("users/cities")
    Call<String[]> getCities();
}
