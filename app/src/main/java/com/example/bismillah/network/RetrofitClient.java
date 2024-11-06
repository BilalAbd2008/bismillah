package com.example.bismillah.network;

import com.example.bismillah.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Request;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static Retrofit retrofit;

    private static Retrofit getRetrofitInstance(String authToken) {
        if (retrofit == null || !retrofit.baseUrl().toString().equals(Constants.BASE_URL)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Accept", "application/json");

                        if (authToken != null && !authToken.isEmpty()) {
                            requestBuilder.header("Authorization", "Bearer " + authToken);
                        }

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(String authToken) {
        return getRetrofitInstance(authToken).create(ApiService.class);
    }

    public static ApiService getApiService() {
        return getRetrofitInstance(null).create(ApiService.class);
    }
}
