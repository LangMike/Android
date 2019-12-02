
package com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("T_name")
    @Expose
    private String tName;
    @SerializedName("T_email")
    @Expose
    private String tEmail;
    @SerializedName("T_econtact")
    @Expose
    private String tEcontact;
    @SerializedName("T_prof_pic")
    @Expose
    private String tProfPic;
    @SerializedName("T_about")
    @Expose
    private String tAbout;
    @SerializedName("T_mobile")
    @Expose
    private String tMobile;
    @SerializedName("Bio")
    @Expose
    private String bio;
    @SerializedName("Total_exp")
    @Expose
    private String totalExp;
    @SerializedName("profession")
    @Expose
    private Object profession;
    @SerializedName("Rating")
    @Expose
    private String rating;

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getTEmail() {
        return tEmail;
    }

    public void setTEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String getTEcontact() {
        return tEcontact;
    }

    public void setTEcontact(String tEcontact) {
        this.tEcontact = tEcontact;
    }

    public String getTProfPic() {
        return tProfPic;
    }

    public void setTProfPic(String tProfPic) {
        this.tProfPic = tProfPic;
    }

    public String getTAbout() {
        return tAbout;
    }

    public void setTAbout(String tAbout) {
        this.tAbout = tAbout;
    }

    public String getTMobile() {
        return tMobile;
    }

    public void setTMobile(String tMobile) {
        this.tMobile = tMobile;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(String totalExp) {
        this.totalExp = totalExp;
    }

    public Object getProfession() {
        return profession;
    }

    public void setProfession(Object profession) {
        this.profession = profession;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
