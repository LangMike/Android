
package com.amindset.ve.amindset.ForgotPassword.Model.ModelUserForgot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("P_email")
    @Expose
    private String pEmail;
    @SerializedName("P_phone")
    @Expose
    private String pPhone;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("P_timezone_id")
    @Expose
    private String pTimezoneId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPEmail() {
        return pEmail;
    }

    public void setPEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getPPhone() {
        return pPhone;
    }

    public void setPPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPTimezoneId() {
        return pTimezoneId;
    }

    public void setPTimezoneId(String pTimezoneId) {
        this.pTimezoneId = pTimezoneId;
    }

}
