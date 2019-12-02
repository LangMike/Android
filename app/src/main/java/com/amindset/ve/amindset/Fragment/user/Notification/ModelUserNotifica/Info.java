
package com.amindset.ve.amindset.Fragment.user.Notification.ModelUserNotifica;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("notification_text")
    @Expose
    private String notificationText;
    @SerializedName("Profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("notification_type")
    @Expose
    private String notificationType;
    @SerializedName("redirect_url")
    @Expose
    private String redirectUrl;
    @SerializedName("read_status")
    @Expose
    private String readStatus;
    @SerializedName("Created_on")
    @Expose
    private String createdOn;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
