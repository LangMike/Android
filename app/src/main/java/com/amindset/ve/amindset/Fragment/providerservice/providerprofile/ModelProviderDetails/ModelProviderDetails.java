
package com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProviderDetails {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("Details")
    @Expose
    private Details details;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}
