package com.dhbk.ai_app_4_statics_image;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DjangoApi {
    DjangoApi djangoApi = new Retrofit.Builder()
            .baseUrl(":8000/image/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DjangoApi.class);
    @Multipart
    @POST("upload/")
    Call<RequestBody>  uploadPhoto(@Part MultipartBody.Part files);
}