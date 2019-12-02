
package com.amindset.ve.amindset.Fragment.user.Appoinment.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("Therapist_id")
    @Expose
    private String therapistId;
    @SerializedName("call_from")
    @Expose
    private String callFrom;
    @SerializedName("call_to")
    @Expose
    private String callTo;
    @SerializedName("call_duration")
    @Expose
    private String callDuration;
    @SerializedName("calltypes")
    @Expose
    private String calltypes;
    @SerializedName("call_time")
    @Expose
    private String callTime;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("provider_cost")
    @Expose
    private String providerCost;
    @SerializedName("T_fname")
    @Expose
    private String tFname;
    @SerializedName("T_lname")
    @Expose
    private String tLname;
    @SerializedName("T_prof_pic")
    @Expose
    private String tProfPic;
    @SerializedName("T_about")
    @Expose
    private String tAbout;
    @SerializedName("favorite_status")
    @Expose
    private Integer favoriteStatus;
    @SerializedName("createdon")
    @Expose
    private String createdon;

    public String getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(String therapistId) {
        this.therapistId = therapistId;
    }

    public String getCallFrom() {
        return callFrom;
    }

    public void setCallFrom(String callFrom) {
        this.callFrom = callFrom;
    }

    public String getCallTo() {
        return callTo;
    }

    public void setCallTo(String callTo) {
        this.callTo = callTo;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCalltypes() {
        return calltypes;
    }

    public void setCalltypes(String calltypes) {
        this.calltypes = calltypes;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getProviderCost() {
        return providerCost;
    }

    public void setProviderCost(String providerCost) {
        this.providerCost = providerCost;
    }

    public String getTFname() {
        return tFname;
    }

    public void setTFname(String tFname) {
        this.tFname = tFname;
    }

    public String getTLname() {
        return tLname;
    }

    public void setTLname(String tLname) {
        this.tLname = tLname;
    }

    public String getTProfPic() {
        return tProfPic;
    }

    public void setTProfPic(String tProfPic) {
        this.tProfPic = tProfPic;
    }

    public String getTAbout() {
        return tAbout;
    }

    public void setTAbout(String tAbout) {
        this.tAbout = tAbout;
    }

    public Integer getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(Integer favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

}
