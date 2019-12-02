
package com.amindset.ve.amindset.Fragment.user.UserProfile.ModelChangePassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Pat_id")
    @Expose
    private String patId;
    @SerializedName("P_name")
    @Expose
    private String pName;
    @SerializedName("P_email")
    @Expose
    private String pEmail;
    @SerializedName("P_Prof_pic")
    @Expose
    private String pProfPic;
    @SerializedName("P_gender")
    @Expose
    private String pGender;
    @SerializedName("P_dob")
    @Expose
    private String pDob;
    @SerializedName("P_phone")
    @Expose
    private String pPhone;
    @SerializedName("P_status")
    @Expose
    private String pStatus;
    @SerializedName("otp")
    @Expose
    private String otp;

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public String getPEmail() {
        return pEmail;
    }

    public void setPEmail(String pEmail) {
        this.pEmail = pEmail;
    }

    public String getPProfPic() {
        return pProfPic;
    }

    public void setPProfPic(String pProfPic) {
        this.pProfPic = pProfPic;
    }

    public String getPGender() {
        return pGender;
    }

    public void setPGender(String pGender) {
        this.pGender = pGender;
    }

    public String getPDob() {
        return pDob;
    }

    public void setPDob(String pDob) {
        this.pDob = pDob;
    }

    public String getPPhone() {
        return pPhone;
    }

    public void setPPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getPStatus() {
        return pStatus;
    }

    public void setPStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
