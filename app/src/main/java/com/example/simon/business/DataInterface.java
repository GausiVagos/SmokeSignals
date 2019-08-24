package com.example.simon.business;

import com.example.simon.models.Chat;
import com.example.simon.models.User;
import com.example.simon.models.Message;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DataInterface {
    // User methods
    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{userId}")
    Call<List<User>> getUser(@Path("userId") int userId);

    @POST("users/connect")
    Call<User> connect(@Body User user);

    @POST("users")
    Call<User> post(@Body User user);

    @GET("users/cities")
    Call<String[]> getCities();

    // Chat methods
    @GET("chats/ofUser/{userId}")
    Call<Chat[]> getChatsOfUser(@Path("userId") int userId);

    @POST("chats/{chatId}/addMessage")
    Call<Set<Message>> addMessage(@Path("chatId") int chatId, @Body Message message);

    // Messages methods
    @GET("messages/ofChat/{chatId}")
    Call<Set<Message>> getChatMessages(@Path("chatId") int chatId);
}
