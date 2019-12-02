
package com.amindset.ve.amindset.Fragment.user.UserProfile.ModelPayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("exp_month")
    @Expose
    private String expMonth;
    @SerializedName("exp_year")
    @Expose
    private String expYear;
    @SerializedName("cvv")
    @Expose
    private String cvv;
    @SerializedName("stripe_card_token")
    @Expose
    private String stripeCardToken;
    @SerializedName("cardId")
    @Expose
    private String cardId;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getStripeCardToken() {
        return stripeCardToken;
    }

    public void setStripeCardToken(String stripeCardToken) {
        this.stripeCardToken = stripeCardToken;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

}
