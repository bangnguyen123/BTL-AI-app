package com.dhbk.ai_app_4_statics_image.Retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {

    @Multipart
    @POST("upload/")
    Call<PicTure> UploadPhoto(@Part MultipartBody.Part photo);
}
