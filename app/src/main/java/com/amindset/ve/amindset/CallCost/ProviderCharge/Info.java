
package com.amindset.ve.amindset.CallCost.ProviderCharge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("call_from")
    @Expose
    private String callFrom;
    @SerializedName("call_to")
    @Expose
    private String callTo;
    @SerializedName("call_duration")
    @Expose
    private String callDuration;
    @SerializedName("call_time")
    @Expose
    private String callTime;
    @SerializedName("end_at")
    @Expose
    private String endAt;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("provider_cost")
    @Expose
    private String providerCost;
    @SerializedName("call_result")
    @Expose
    private String callResult;
    @SerializedName("charge_id")
    @Expose
    private String chargeId;
    @SerializedName("usertype")
    @Expose
    private String usertype;
    @SerializedName("callaction_by_user")
    @Expose
    private String callactionByUser;
    @SerializedName("createdon")
    @Expose
    private String createdon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
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

    public String getCallResult() {
        return callResult;
    }

    public void setCallResult(String callResult) {
        this.callResult = callResult;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getCallactionByUser() {
        return callactionByUser;
    }

    public void setCallactionByUser(String callactionByUser) {
        this.callactionByUser = callactionByUser;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

}
