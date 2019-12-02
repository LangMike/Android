
package com.amindset.ve.amindset.Splash.ModelFCM;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFCmTokenResponse {

    @SerializedName("sucess")
    @Expose
    private Integer sucess;
    @SerializedName("fcmToken")
    @Expose
    private Object fcmToken;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getSucess() {
        return sucess;
    }

    public void setSucess(Integer sucess) {
        this.sucess = sucess;
    }

    public Object getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(Object fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
