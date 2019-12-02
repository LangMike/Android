
package com.amindset.ve.amindset.Fragment.user.Counselorlist.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("therap_id")
    @Expose
    private String therapId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("prof_pic")
    @Expose
    private String profPic;
    @SerializedName("therap_name")
    @Expose
    private String therapName;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("proficiency")
    @Expose
    private String proficiency;
    @SerializedName("no_of_pat")
    @Expose
    private String noOfPat;
    @SerializedName("avg_ratings")
    @Expose
    private String avgRatings;
    @SerializedName("is_block")
    @Expose
    private String isBlock;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_favorite")
    @Expose
    private String isFavorite;
    @SerializedName("boolean_favorite")
    @Expose
    private Integer booleanFavorite;
    @SerializedName("profession_title")
    @Expose
    private String professionTitle;

    public String getTherapId() {
        return therapId;
    }

    public void setTherapId(String therapId) {
        this.therapId = therapId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfPic() {
        return profPic;
    }

    public void setProfPic(String profPic) {
        this.profPic = profPic;
    }

    public String getTherapName() {
        return therapName;
    }

    public void setTherapName(String therapName) {
        this.therapName = therapName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public String getNoOfPat() {
        return noOfPat;
    }

    public void setNoOfPat(String noOfPat) {
        this.noOfPat = noOfPat;
    }

    public String getAvgRatings() {
        return avgRatings;
    }

    public void setAvgRatings(String avgRatings) {
        this.avgRatings = avgRatings;
    }

    public String getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Integer getBooleanFavorite() {
        return booleanFavorite;
    }

    public void setBooleanFavorite(Integer booleanFavorite) {
        this.booleanFavorite = booleanFavorite;
    }

    public String getProfessionTitle() {
        return professionTitle;
    }

    public void setProfessionTitle(String professionTitle) {
        this.professionTitle = professionTitle;
    }

}
