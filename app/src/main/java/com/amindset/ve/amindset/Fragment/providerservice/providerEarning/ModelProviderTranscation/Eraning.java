
package com.amindset.ve.amindset.Fragment.providerservice.providerEarning.ModelProviderTranscation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eraning {

    @SerializedName("today")
    @Expose
    private String today;
    @SerializedName("yesterday")
    @Expose
    private String yesterday;
    @SerializedName("thismonth")
    @Expose
    private String thismonth;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }

    public String getThismonth() {
        return thismonth;
    }

    public void setThismonth(String thismonth) {
        this.thismonth = thismonth;
    }

}
