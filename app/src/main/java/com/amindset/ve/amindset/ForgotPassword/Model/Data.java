
package com.amindset.ve.amindset.ForgotPassword.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("T_email")
    @Expose
    private String tEmail;
    @SerializedName("T_mobile")
    @Expose
    private String tMobile;
    @SerializedName("Token")
    @Expose
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTEmail() {
        return tEmail;
    }

    public void setTEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String getTMobile() {
        return tMobile;
    }

    public void setTMobile(String tMobile) {
        this.tMobile = tMobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
