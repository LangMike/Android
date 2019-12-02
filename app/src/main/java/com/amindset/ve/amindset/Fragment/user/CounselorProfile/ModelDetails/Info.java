
package com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pro_pic")
    @Expose
    private String proPic;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("T_mobile")
    @Expose
    private String tMobile;
    @SerializedName("voice_calling_rate")
    @Expose
    private String voiceCallingRate;
    @SerializedName("video_calling_rate")
    @Expose
    private String videoCallingRate;
    @SerializedName("messaging_rate")
    @Expose
    private String messagingRate;
    @SerializedName("Ratings")
    @Expose
    private String ratings;
    @SerializedName("proficiency")
    @Expose
    private String proficiency;
    @SerializedName("like_status")
    @Expose
    private Integer likeStatus;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("About")
    @Expose
    private String about;
    @SerializedName("profession_title")
    @Expose
    private String professionTitle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTMobile() {
        return tMobile;
    }

    public void setTMobile(String tMobile) {
        this.tMobile = tMobile;
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

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public Integer getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(Integer likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfessionTitle() {
        return professionTitle;
    }

    public void setProfessionTitle(String professionTitle) {
        this.professionTitle = professionTitle;
    }

}
