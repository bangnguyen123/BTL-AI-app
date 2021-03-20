package com.dhbk.ai_app_4_statics_image.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl) {
        OkHttpClient builder = new OkHttpClient.Builder()
                                        .readTimeout(100000, TimeUnit.MILLISECONDS)
                                        .writeTimeout(100000,TimeUnit.MILLISECONDS)
                                        .connectTimeout(100000, TimeUnit.MILLISECONDS)
                                        .retryOnConnectionFailure(true)
                                        .build();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .client(builder)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();
        return retrofit;
    }
}
