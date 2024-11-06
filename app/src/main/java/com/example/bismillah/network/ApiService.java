package com.example.bismillah.network;

import com.example.bismillah.models.LoginRequest;
import com.example.bismillah.models.LoginResponse;
import com.example.bismillah.models.RegisterRequest;
import com.example.bismillah.models.RegisterResponse;
import com.example.bismillah.models.UpdateRequest;
import com.example.bismillah.models.UserResponse;

import com.example.bismillah.models.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("formuser/create")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("formuser/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("formuser/{id}")
    Call<UserResponse> getUser(
            @Path("id") String userId,
            @Header("Authorization") String authToken
    );


    @PUT("formuser/{id}")
    Call<UpdateResponse> updateUser(
            @Path("userId") String userId,
            @Body UpdateRequest request,
            @Header("Authorization") String authToken
    );

    @DELETE("formuser/{id}")
    Call<Void> deleteUser(
            @Path("userId") String userId,
            @Header("Authorization") String authToken
    );
}