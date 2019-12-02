
package com.amindset.ve.amindset.Fragment.userservice.ModelFavList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("fav_id")
    @Expose
    private String favId;
    @SerializedName("fav_userid")
    @Expose
    private String favUserid;
    @SerializedName("T_fname")
    @Expose
    private String tFname;
    @SerializedName("T_prof_pic")
    @Expose
    private String tProfPic;
    @SerializedName("T_about")
    @Expose
    private String tAbout;

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
    }

    public String getFavUserid() {
        return favUserid;
    }

    public void setFavUserid(String favUserid) {
        this.favUserid = favUserid;
    }

    public String getTFname() {
        return tFname;
    }

    public void setTFname(String tFname) {
        this.tFname = tFname;
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

}
