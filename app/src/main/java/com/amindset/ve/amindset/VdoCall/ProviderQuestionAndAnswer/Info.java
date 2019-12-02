
package com.amindset.ve.amindset.VdoCall.ProviderQuestionAndAnswer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("Que_id")
    @Expose
    private String queId;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("Answer")
    @Expose
    private String answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
