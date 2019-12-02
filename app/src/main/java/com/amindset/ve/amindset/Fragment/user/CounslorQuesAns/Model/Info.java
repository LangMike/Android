
package com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("Que_id")
    @Expose
    private String queId;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("Que_data")
    @Expose
    private String queData;
    @SerializedName("Answer")
    @Expose
    private String answer;
    @SerializedName("Que_status")
    @Expose
    private String queStatus;

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQueData() {
        return queData;
    }

    public void setQueData(String queData) {
        this.queData = queData;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQueStatus() {
        return queStatus;
    }

    public void setQueStatus(String queStatus) {
        this.queStatus = queStatus;
    }

}
