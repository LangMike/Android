
package com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelCallRate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("therap_id")
    @Expose
    private String therapId;
    @SerializedName("voice_calling_rate")
    @Expose
    private String voiceCallingRate;
    @SerializedName("video_calling_rate")
    @Expose
    private String videoCallingRate;
    @SerializedName("messaging_rate")
    @Expose
    private String messagingRate;

    public String getTherapId() {
        return therapId;
    }

    public void setTherapId(String therapId) {
        this.therapId = therapId;
    }

    public String getVoiceCallingRate() {
        return voiceCallingRate;
    }

    public void setVoiceCallingRate(String voiceCallingRate) {
        this.voiceCallingRate = voiceCallingRate;
    }

    public String getVideoCallingRate() {
        return videoCallingRate;
    }

    public void setVideoCallingRate(String videoCallingRate) {
        this.videoCallingRate = videoCallingRate;
    }

    public String getMessagingRate() {
        return messagingRate;
    }

    public void setMessagingRate(String messagingRate) {
        this.messagingRate = messagingRate;
    }

}
