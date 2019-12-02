
package com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderAccountDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("routing_number")
    @Expose
    private String routingNumber;
    @SerializedName("account_number")
    @Expose
    private String accountNumber;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("address_on_bank")
    @Expose
    private String addressOnBank;
    @SerializedName("regionBank")
    @Expose
    private String regionBank;
    @SerializedName("strip_bank_id")
    @Expose
    private String stripBankId;
    @SerializedName("Account_id")
    @Expose
    private String accountId;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAddressOnBank() {
        return addressOnBank;
    }

    public void setAddressOnBank(String addressOnBank) {
        this.addressOnBank = addressOnBank;
    }

    public String getRegionBank() {
        return regionBank;
    }

    public void setRegionBank(String regionBank) {
        this.regionBank = regionBank;
    }

    public String getStripBankId() {
        return stripBankId;
    }

    public void setStripBankId(String stripBankId) {
        this.stripBankId = stripBankId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
