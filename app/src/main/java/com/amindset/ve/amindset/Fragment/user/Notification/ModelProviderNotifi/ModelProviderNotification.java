
package com.amindset.ve.amindset.Fragment.user.Notification.ModelProviderNotifi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProviderNotification {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("infoProvider")
    @Expose
    private List<InfoProvider> infoProvider = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<InfoProvider> getInfoProvider() {
        return infoProvider;
    }

    public void setInfoProvider(List<InfoProvider> infoProvider) {
        this.infoProvider = infoProvider;
    }

}
