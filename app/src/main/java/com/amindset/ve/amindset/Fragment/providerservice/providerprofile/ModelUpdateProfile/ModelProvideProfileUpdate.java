
package com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelUpdateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProvideProfileUpdate {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
