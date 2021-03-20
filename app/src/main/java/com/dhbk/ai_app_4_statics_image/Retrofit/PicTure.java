package com.dhbk.ai_app_4_statics_image.Retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PicTure implements Parcelable {
    @SerializedName("model_pic")
    @Expose
    private String modelPic;

    protected PicTure(Parcel in) {
        modelPic = in.readString();
    }

    public static final Creator<PicTure> CREATOR = new Creator<PicTure>() {
        @Override
        public PicTure createFromParcel(Parcel in) {
            return new PicTure(in);
        }

        @Override
        public PicTure[] newArray(int size) {
            return new PicTure[size];
        }
    };

    public String getModelPic() {
        return modelPic;
    }

    public void setModelPic(String modelPic) {
        this.modelPic = modelPic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(modelPic);
    }
}

