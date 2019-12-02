
package com.amindset.ve.amindset.Fragment.providerservice.providerEarning.ModelProviderTranscation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("call_duration")
    @Expose
    private String callDuration;
    @SerializedName("trans_time")
    @Expose
    private String transTime;
    @SerializedName("total_earn")
    @Expose
    private String totalEarn;
    @SerializedName("totalcost")
    @Expose
    private String totalcost;
    @SerializedName("trans_date")
    @Expose
    private String transDate;
    @SerializedName("P_fname")
    @Expose
    private String pFname;
    @SerializedName("P_lname")
    @Expose
    private String pLname;
    @SerializedName("P_Prof_pic")
    @Expose
    private String pProfPic;
    @SerializedName("about")
    @Expose
    private String about;

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(String totalEarn) {
        this.totalEarn = totalEarn;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getPFname() {
        return pFname;
    }

    public void setPFname(String pFname) {
        this.pFname = pFname;
    }

    public String getPLname() {
        return pLname;
    }

    public void setPLname(String pLname) {
        this.pLname = pLname;
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
