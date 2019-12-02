
package com.amindset.ve.amindset.Fragment.providerservice.providerRecentList.ModelRecent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

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
    @SerializedName("P_fname")
    @Expose
    private String pFname;
    @SerializedName("P_Prof_pic")
    @Expose
    private String pProfPic;
    @SerializedName("about")
    @Expose
    private String about;

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

    public String getPFname() {
        return pFname;
    }

    public void setPFname(String pFname) {
        this.pFname = pFname;
    }

    public String getPProfPic() {
        return pProfPic;
    }

    public void setPProfPic(String pProfPic) {
        this.pProfPic = pProfPic;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
