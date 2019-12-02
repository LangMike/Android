
package com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderSetting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Therap_id")
    @Expose
    private String therapId;
    @SerializedName("T_name")
    @Expose
    private String tName;
    @SerializedName("T_email")
    @Expose
    private String tEmail;
    @SerializedName("T_prof_pic")
    @Expose
    private String tProfPic;
    @SerializedName("T_gender")
    @Expose
    private String tGender;
    @SerializedName("T_dob")
    @Expose
    private String tDob;
    @SerializedName("T_mobile")
    @Expose
    private String tMobile;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("T_pswd")
    @Expose
    private String tPswd;

    public String getTherapId() {
        return therapId;
    }

    public void setTherapId(String therapId) {
        this.therapId = therapId;
    }

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getTEmail() {
        return tEmail;
    }

    public void setTEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String getTProfPic() {
        return tProfPic;
    }

    public void setTProfPic(String tProfPic) {
        this.tProfPic = tProfPic;
    }

    public String getTGender() {
        return tGender;
    }

    public void setTGender(String tGender) {
        this.tGender = tGender;
    }

    public String getTDob() {
        return tDob;
    }

    public void setTDob(String tDob) {
        this.tDob = tDob;
    }

    public String getTMobile() {
        return tMobile;
    }

    public void setTMobile(String tMobile) {
        this.tMobile = tMobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getTPswd() {
        return tPswd;
    }

    public void setTPswd(String tPswd) {
        this.tPswd = tPswd;
    }

}
