package com.dhbk.ai_app_4_statics_image.Retrofit;

public class APIUtils {
    public static final String base_url = "http://10.130.248.171:8000/image/";

    public static DataClient getData() {
        return RetrofitClient.getClient(base_url).create(DataClient.class);
    }
}
