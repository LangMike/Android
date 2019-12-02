
package com.amindset.ve.amindset.OTP.ModelLang;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatumLang {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lanuage")
    @Expose
    private String lanuage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanuage() {
        return lanuage;
    }

    public void setLanuage(String lanuage) {
        this.lanuage = lanuage;
    }

    @NonNull
    @Override
    public String toString() {
        return lanuage;
    }
}
