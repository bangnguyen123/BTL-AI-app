package com.dhbk.ai_app_4_statics_image;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dhbk.ai_app_4_statics_image.Retrofit.APIUtils;
import com.dhbk.ai_app_4_statics_image.Retrofit.DataClient;
import com.dhbk.ai_app_4_statics_image.Retrofit.PicTure;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;

public class MainActivity extends AppCompatActivity {
    Button cap, up;
    ImageView img;
    int REQUEST_CODE_CAMERA = 123;
    int request_code_image = 123;
    Bitmap bitmap;
    String realPath;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE", "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cap = findViewById(R.id.button);
        up = findViewById(R.id.button2);
        img = findViewById(R.id.imageView);

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allPermissionsGranted()) {
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, request_code_image);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                            request_code_image);
                }
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, REQUEST_CODE_CAMERA);

            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(realPath);
                String file_path = file.getAbsolutePath();
                String[] fileNameArray = file_path.split("\\.");
                file_path = fileNameArray[0] + System.currentTimeMillis() + fileNameArray[1];
                Log.d("BBB",file_path);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"),file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("model_pic",file.getName(),requestBody);
                DataClient dataClient = APIUtils.getData();
                Call<PicTure> callBack = dataClient.UploadPhoto(body);
                callBack.enqueue(new Callback<PicTure>() {
                    @Override
                    public void onResponse(Call<PicTure> call, Response<PicTure> response) {
//                        Log.d("bbb",);
                        Log.d("bbb", response.body().getModelPic());
                        Intent intent = new Intent(MainActivity.this, ResultPredictActivity2.class);
                        intent.putExtra("Image", (Parcelable) response.body());
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<PicTure> call, Throwable t) {
                        Log.d("bbb","faith");
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == request_code_image && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realPath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                Matrix matrix = new Matrix();
//                matrix.postRotate(90);
//                bitmap = Bitmap.createBitmap(bitmap, 0, 0 , bitmap.getWidth(), bitmap.getHeight(),matrix, true);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
    public boolean allPermissionsGranted(){
        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}