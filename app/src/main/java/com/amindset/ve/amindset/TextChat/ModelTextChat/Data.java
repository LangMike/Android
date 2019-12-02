
package com.amindset.ve.amindset.TextChat.ModelTextChat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("my_profile_pic_url")
    @Expose
    private String myProfilePicUrl;
    @SerializedName("providerName")
    @Expose
    private String providerName;
    @SerializedName("T_mobile")
    @Expose
    private String tMobile;
    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("userName")
    @Expose
    private Object userName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMyProfilePicUrl() {
        return myProfilePicUrl;
    }

    public void setMyProfilePicUrl(String myProfilePicUrl) {
        this.myProfilePicUrl = myProfilePicUrl;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getTMobile() {
        return tMobile;
    }

    public void setTMobile(String tMobile) {
        this.tMobile = tMobile;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

}
