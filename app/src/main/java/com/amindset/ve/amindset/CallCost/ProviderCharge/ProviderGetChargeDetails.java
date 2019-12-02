
package com.amindset.ve.amindset.CallCost.ProviderCharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderGetChargeDetails {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("Info")
    @Expose
    private Info info;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
