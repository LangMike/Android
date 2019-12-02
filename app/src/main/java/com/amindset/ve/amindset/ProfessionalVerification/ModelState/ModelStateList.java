
package com.amindset.ve.amindset.ProfessionalVerification.ModelState;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelStateList {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("State")
    @Expose
    private List<State> state = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<State> getState() {
        return state;
    }

    public void setState(List<State> state) {
        this.state = state;
    }

}
