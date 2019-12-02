
package com.amindset.ve.amindset.Fragment.user.Notification.ModelProviderNotifi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("is_start")
    @Expose
    private String isStart;
    @SerializedName("appt_id")
    @Expose
    private String apptId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("prof_pic")
    @Expose
    private String profPic;
    @SerializedName("pat_id")
    @Expose
    private String patId;
    @SerializedName("Appt_date")
    @Expose
    private String apptDate;
    @SerializedName("Call_initiate")
    @Expose
    private String callInitiate;
    @SerializedName("token_id")
    @Expose
    private String tokenId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getApptId() {
        return apptId;
    }

    public void setApptId(String apptId) {
        this.apptId = apptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfPic() {
        return profPic;
    }

    public void setProfPic(String profPic) {
        this.profPic = profPic;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getApptDate() {
        return apptDate;
    }

    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getCallInitiate() {
        return callInitiate;
    }

    public void setCallInitiate(String callInitiate) {
        this.callInitiate = callInitiate;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

}
