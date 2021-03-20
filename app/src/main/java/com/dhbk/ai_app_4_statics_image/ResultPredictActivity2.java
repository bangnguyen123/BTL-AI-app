package com.dhbk.ai_app_4_statics_image;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhbk.ai_app_4_statics_image.Retrofit.PicTure;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;

public class ResultPredictActivity2 extends AppCompatActivity {
    ImageView img;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_predict2);
        img = findViewById(R.id.imageView);
        Intent intent = getIntent();
        PicTure responseBody = intent.getParcelableExtra("Image");
        Log.d("ccc",responseBody.getModelPic());
        Picasso.get().load(responseBody.getModelPic()).into(img);
//        text.setText("Day la Bang dep trai");
    }
}