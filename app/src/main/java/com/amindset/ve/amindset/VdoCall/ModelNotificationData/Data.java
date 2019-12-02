
package com.amindset.ve.amindset.VdoCall.ModelNotificationData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("fcmToken")
    @Expose
    private String fcmToken;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("my_number")
    @Expose
    private String myNumber;
    @SerializedName("my_profile_pic_url")
    @Expose
    private String myProfilePicUrl;
    @SerializedName("roomName")
    @Expose
    private String roomName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(String myNumber) {
        this.myNumber = myNumber;
    }

    public String getMyProfilePicUrl() {
        return myProfilePicUrl;
    }

    public void setMyProfilePicUrl(String myProfilePicUrl) {
        this.myProfilePicUrl = myProfilePicUrl;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
