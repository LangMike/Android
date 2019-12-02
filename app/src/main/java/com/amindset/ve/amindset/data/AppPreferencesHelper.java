/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.amindset.ve.amindset.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Singleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Satendra Singh on 27/01/17.
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_USER_B_TOKEN = "PREF_KEY_USER_B_TOKEN";
    private static final String PREF_KEY_USER_PAT_ID = "PREF_KEY_USER_PAT_ID";
    private static final String PREF_KEY_USER_PFNAME = "PREF_KEY_USER_PFNAME";
    private static final String PREF_KEY_USER_PLNAME = "PREF_KEY_USER_PLNAME";
    private static final String PREF_KEY_USER_USERNAME = "PREF_KEY_USER_USERNAME";
    private static final String PREF_KEY_USER_PEMAIL = "PREF_KEY_USER_PEMAIL";
    private static final String PREF_KEY_USER_PSWD = "PREF_KEY_USER_PSWD";
    private static final String PREF_KEY_USER_PGENDER = "PREF_KEY_USER_PGENDER";
    private static final String PREF_KEY_USER_P_PHONE = "PREF_KEY_USER_P_PHONE";
    private static final String PREF_KEY_USER_T_MOBILE = "PREF_KEY_USER_T_MOBILE";
    private static final String PREF_KEY_USER_P_PROFILE_PIC = "PREF_KEY_USER_P_PROFILE_PIC";
    private static final String PREF_KEY_USER_P_ECONTACT = "PREF_KEY_USER_P_ECONTACT";
    private static final String PREF_KEY_USER_P_DOB = "PREF_KEY_USER_P_DOB";
    private static final String PREF_KEY_USER_P_NICKNAME = "PREF_KEY_USER_P_NICKNAME";
    private static final String PREF_KEY_USER_P_ADDRESS1 = "PREF_KEY_USER_P_ADDRESS1";
    private static final String PREF_KEY_USER_P_ADDRESS2 = "PREF_KEY_USER_P_ADDRESS2";
    private static final String PREF_KEY_USER_P_COUNTRY_ID = "PREF_KEY_USER_P_COUNTRY_ID";
    private static final String PREF_KEY_USER_P_STATE_ID = "PREF_KEY_USER_P_STATE_ID";
    private static final String PREF_KEY_USER_P_CITY = "PREF_KEY_USER_P_CITY";
    private static final String PREF_KEY_USER_IS_ACTIVE = "PREF_KEY_USER_IS_ACTIVE";
    private static final String PREF_KEY_USER_P_ZIP = "PREF_KEY_USER_P_ZIP";
    private static final String PREF_KEY_USER_P_TIMEZONE_ID = "PREF_KEY_USER_P_TIMEZONE_ID";
    private static final String PREF_KEY_USER_ISEMAILCONFIRM = "PREF_KEY_USER_ISEMAILCONFIRM";
    private static final String PREF_KEY_USER_TOKEN = "PREF_KEY_USER_TOKEN";
    private static final String PREF_KEY_USER_FACBOOK_ID = "PREF_KEY_USER_FACBOOK_ID";
    private static final String PREF_KEY_USER_P_GOOGLE_ID= "PREF_KEY_USER_P_GOOGLE_ID";
    private static final String PREF_KEY_USER_IS_ANONYMOUS= "PREF_KEY_USER_IS_ANONYMOUS";
    private static final String PREF_KEY_USER_PROF_STAGE= "PREF_KEY_USER_PROF_STAGE";
    private static final String PREF_KEY_USER_P_STATUS= "PREF_KEY_USER_P_STATUS";
    private static final String PREF_KEY_USER_DEVICE_TYPE= "PREF_KEY_USER_DEVICE_TYPE";
    private static final String PREF_KEY_USER_DEVICE_ID= "PREF_KEY_USER_DEVICE_ID";
    private static final String PREF_KEY_USER_DEVICE_TOKEN= "PREF_KEY_USER_DEVICE_TOKEN";
    private static final String PREF_KEY_USER_OTP= "PREF_KEY_USER_OTP";
    private static final String PREF_KEY_USER_CREATD_ON= "PREF_KEY_USER_CREATD_ON";
    private static final String PREF_KEY_USER_UPDATED_ON= "PREF_KEY_USER_UPDATED_ON";
    private static final String PREF_KEY_USER_UPDATED_BY= "PREF_KEY_USER_UPDATED_BY";




    private static final String PREF_KEY_PROVIDER_TOKEN = "PREF_KEY_PROVIDER_TOKEN";
    private static final String PREF_KEY_ACCOUNT_TYPE = "PREF_KEY_ACCOUNT_TYPE";
    private static final String PREF_KEY_FCM_TOKEN = "PREF_KEY_FCM_TOKEN";
    private static final String PREF_KEY_PROVIDER_THERRAP_ID = "PREF_KEY_PROVIDER_THERRAP_ID";
    private static final String PREF_KEY_PROVIDER_ACCOUNT_ID = "PREF_KEY_PROVIDER_ACCOUNT_ID";
    private static final String PREF_KEY_PROVIDER_T_FNAME = "PREF_KEY_PROVIDER_T_FNAME";
    private static final String PREF_KEY_PROVIDER_T_LNAME= "PREF_KEY_PROVIDER_T_LNAME";
    private static final String PREF_KEY_PROVIDER_T_EMAIL = "PREF_KEY_PROVIDER_T_EMAIL";
    private static final String PREF_KEY_PROVIDER_T_PSWD = "PREF_KEY_PROVIDER_T_PSWD";
    private static final String PREF_KEY_PROVIDER_T_PROF_PIC = "PREF_KEY_PROVIDER_T_PROF_PIC";
    private static final String PREF_KEY_PROVIDER_T_GENDER = "PREF_KEY_PROVIDER_T_GENDER";
    private static final String PREF_KEY_PROVIDER_T_DOB = "PREF_KEY_PROVIDER_T_DOB";
    private static final String PREF_KEY_PROVIDER_T_PHONE = "PREF_KEY_PROVIDER_T_PHONE";
    private static final String PREF_KEY_PROVIDER_CREATED_ON = "PREF_KEY_PROVIDER_CREATED_ON";
    private static final String PREF_KEY_PROVIDER_UPDATED_ON = "PREF_KEY_PROVIDER_UPDATED_ON";
    private static final String PREF_KEY_PROVIDER_DEVICE_ID = "PREF_KEY_PROVIDER_DEVICE_ID";
    private static final String PREF_KEY_PROVIDER_OTP = "PREF_KEY_PROVIDER_OTP";
    private static final String PREF_KEY_PROVIDER_T_TIMEZONE_ID = "PREF_KEY_PROVIDER_T_TIMEZONE_ID";
    private static final String PREF_KEY_PROVIDER_PROF_STAGE = "PREF_KEY_PROVIDER_PROF_STAGE";
    private static final String PREF_KEY_PROVIDER_T_CITY = "PREF_KEY_PROVIDER_T_CITY";
    private static final String PREF_KEY_PROVIDER_T_ZIP = "PREF_KEY_PROVIDER_T_ZIP";
    private static final String PREF_KEY_PROVIDER_T_MOBILE = "PREF_KEY_PROVIDER_T_MOBILE";
    private static final String PREF_KEY_PROVIDER_T_ECONTACT = "PREF_KEY_PROVIDER_T_ECONTACT";
    private static final String PREF_KEY_PROVIDER_T_ADDRESS1 = "PREF_KEY_PROVIDER_T_ADDRESS1";

    private static final String PREF_KEY_PROVIDER_STATUS = "PREF_KEY_PROVIDER_STATUS";
    private static final String PREF_KEY_PROVIDER_DEVICETYPE = "PREF_KEY_PROVIDER_DEVICETYPE";


    private static final String PREF_KEY_PROVIDER_ISEMAILCONFIRM = "PREF_KEY_PROVIDER_ISEMAILCONFIRM";
    private static final String PREF_KEY_PROVIDER_IS_REVERIFY = "PREF_KEY_PROVIDER_IS_REVERIFY";
    private static final String PREF_KEY_PROVIDER_TOKENU = "PREF_KEY_PROVIDER_TOKENU";

    private static final String PREF_KEY_PROVIDER_T_ADDRESS2 = "PREF_KEY_PROVIDER_T_ADDRESS2";
    private static final String PREF_KEY_PROVIDER_T_COUNTRY_ID = "PREF_KEY_PROVIDER_T_COUNTRY_ID";
    private static final String PREF_KEY_PROVIDER_T_STATE_ID = "PREF_KEY_PROVIDER_T_STATE_ID";

    public static final String PREF_NAME = "AmindsetPref";



    private static final String PREF_KEY_USER_ACCOUNT_ID = "PREF_KEY_USER_ACCOUNT_ID";
    private static final String PREF_KEY_USER_CARD_ID = "PREF_KEY_USER_CARD_ID";
    private static final String PREF_KEY_USER_ACCOUNT_HOLDER_NAME= "PREF_KEY_USER_ACCOUNT_HOLDER_NAME";
    private static final String PREF_KEY_USER_ACCOUNT_NUMBER = "PREF_KEY_USER_ACCOUNT_NUMBER";
    private static final String PREF_KEY_USER_EXP_MONTH = "PREF_KEY_USER_EXP_MONTH";
    private static final String PREF_KEY_USER_EXP_YEAR = "PREF_KEY_USER_EXP_YEAR";
    private static final String PREF_KEY_USER_CVV = "PREF_KEY_USER_CVV";
    private static final String PREF_KEY_USER_ACCOUNT_TOKEN = "PREF_KEY_USER_ACCOUNT_TOKEN";
    private static final String PREF_KEY_USER_IS_LOGOUT = "PREF_KEY_USER_IS_LOGOUT";




    private static final String PREF_KEY_PROVIDER_CONNECT_ACCOUNTID = "PREF_KEY_PROVIDER_CONNECT_ACCOUNTID";
    private static final String PREF_KEY_PROVIDER_STRIPE_BANKID = "PREF_KEY_PROVIDER_STRIPE_BANKID";
    private static final String PREF_KEY_PROVIDER_REGION_BANK = "PREF_KEY_PROVIDER_REGION_BANK";
    private static final String PREF_KEY_PROVIDER_ACCOUNT_HOLDER_NAME = "PREF_KEY_PROVIDER_ACCOUNT_HOLDER_NAME";
    private static final String PREF_KEY_PROVIDER_ACCOUNT_NUMBER = "PREF_KEY_PROVIDER_ACCOUNT_NUMBER";
    private static final String PREF_KEY_PROVIDER_BANK_NAME= "PREF_KEY_PROVIDER_BANK_NAME";
    private static final String PREF_KEY_PROVIDER_ADDRESS_ON_BANK = "PREF_KEY_PROVIDER_ADDRESS_ON_BANK";

    public SharedPreferences pref;

    Context context;

    public AppPreferencesHelper(Context context)
    {
         pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
         this.context=context;
    }

    @Override
    public void setProvider_ConnectAc_Id(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_CONNECT_ACCOUNTID, userBearerToken);
        editor.commit();
    }

    @Override
    public String getProvider_ConnectAc_Id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_CONNECT_ACCOUNTID, null);
    }

    @Override
    public void setProvider_strip_bank_id(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_STRIPE_BANKID, userBearerToken);
        editor.commit();
    }

    @Override
    public String getProvider_strip_bank_id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_STRIPE_BANKID, null);
    }

    @Override
    public void setProvider_regionBank(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_REGION_BANK, userBearerToken);
        editor.commit();
    }

    @Override
    public String getProvider_regionBank() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_REGION_BANK, null);
    }

    @Override
    public void setProvider_account_holder_name(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_ACCOUNT_HOLDER_NAME, userBearerToken);
        editor.commit();
    }

    @Override
    public String getProvider_account_holder_name() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_ACCOUNT_HOLDER_NAME, null);
    }

    @Override
    public void setProvider_account_number(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_ACCOUNT_NUMBER, userBearerToken);
        editor.commit();
    }

    @Override
    public String getProvider_account_number() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_ACCOUNT_NUMBER, null);
    }

    @Override
    public void setProvider_bank_name(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_BANK_NAME, userBearerToken);
        editor.commit();
    }

    @Override
    public String getProvider_bank_name() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_BANK_NAME, null);
    }

    @Override
    public void setProvider_address_on_bank(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_ADDRESS_ON_BANK, userBearerToken);
        editor.commit();
    }

    @Override
    public String getProvider_address_on_bank() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_ADDRESS_ON_BANK, null);
    }

    @Override
    public void setUserAccount_id(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_ACCOUNT_ID, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUserAccount_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_ACCOUNT_ID, null);

    }

    @Override
    public void setUsercard_id(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_CARD_ID, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUsercard_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_CARD_ID, null);

    }

    @Override
    public void setUseraccount_holder_name(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_ACCOUNT_HOLDER_NAME, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUseraccount_holder_name() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_ACCOUNT_HOLDER_NAME, null);

    }

    @Override
    public void setUseraccount_number(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_ACCOUNT_NUMBER, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUseraccount_number() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_ACCOUNT_NUMBER, null);

    }

    @Override
    public void setUserexp_month(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_EXP_MONTH, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUserexp_month() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_EXP_MONTH, null);

    }

    @Override
    public void setUserexp_year(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_EXP_YEAR, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUserexp_year() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_EXP_YEAR, null);

    }

    @Override
    public void setUsercvv(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_CVV, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUsercvv() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_CVV, null);

    }

    @Override
    public void setUseraccount_token(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_ACCOUNT_TOKEN, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUseraccount_token() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_ACCOUNT_TOKEN, null);

    }

    @Override
    public void setUserisLogout(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_IS_LOGOUT, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUserisLogout() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_IS_LOGOUT, null);

    }

    @Override
    public void setUserBToken(String userBearerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_B_TOKEN, userBearerToken);
        editor.commit();
    }

    @Override
    public String getUserBToken() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_B_TOKEN, null);
    }

    @Override
    public void setUserPat_id(String userPat_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_PAT_ID, userPat_id);
        editor.commit();
    }

    @Override
    public String getUserPat_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_PAT_ID, null);
    }

    @Override
    public void setUserP_fname(String userP_fname) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_PFNAME, userP_fname);
        editor.commit();
    }

    @Override
    public String getUserP_fname() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_PFNAME, null);
    }

    @Override
    public void setUserP_lname(String PREF_KEY_USER_PLNAME) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_PLNAME, PREF_KEY_USER_PLNAME);
        editor.commit();
    }

    @Override
    public String getUserP_lname() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_PLNAME, null);
    }

    @Override
    public void setUserUser_name(String User_name) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_USERNAME, User_name);
        editor.commit();
    }

    @Override
    public String getUserUser_name() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_USERNAME, null);
    }

    @Override
    public void setUserP_email(String userP_email) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_PEMAIL, userP_email);
        editor.commit();
    }

    @Override
    public String getUserP_email() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_PEMAIL, null);
    }

    @Override
    public void setUserPswd(String userPswd) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_PSWD, userPswd);
        editor.commit();
    }

    @Override
    public String getUserPswd() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_PSWD, null);
    }

    @Override
    public void setUserP_Prof_pic(String userP_Prof_pic) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_PROFILE_PIC, userP_Prof_pic);
        editor.commit();
    }

    @Override
    public String getUserP_Prof_pic() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_PROFILE_PIC, null);
    }

    @Override
    public void setUserP_gender(String userP_gender) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_PGENDER, userP_gender);
        editor.commit();
    }

    @Override
    public String getUserP_gender() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_PGENDER, null);
    }

    @Override
    public void setUserP_phone(String userP_phone) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_PHONE, userP_phone);
        editor.commit();
    }

    @Override
    public String getUserP_phone() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_PHONE, null);
    }

    @Override
    public void setUserT_mobile(String userT_mobile) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_T_MOBILE, userT_mobile);
        editor.commit();
    }

    @Override
    public String getUserT_mobile() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_T_MOBILE, null);
    }

    @Override
    public void setUserP_econtact(String userP_econtact) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_ECONTACT, userP_econtact);
        editor.commit();
    }
    @Override
    public String getUserP_econtact() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_ECONTACT, null);
    }

    @Override
    public void setUserP_dob(String userP_dob) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_DOB, userP_dob);
        editor.commit();
    }

    @Override
    public String getUserP_dob() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_DOB, null);
    }

    @Override
    public void setUserP_nickname(String userP_nickname) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_NICKNAME, userP_nickname);
        editor.commit();
    }

    @Override
    public String getUserP_nickname() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_NICKNAME, null);
    }

    @Override
    public void setUserP_address1(String userP_address1) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_ADDRESS1, userP_address1);
        editor.commit();
    }

    @Override
    public String getUserP_address1() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_ADDRESS1, null);
    }

    @Override
    public void setUserP_address2(String userP_nickname) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_ADDRESS2, userP_nickname);
        editor.commit();
    }

    @Override
    public String getUserP_address2() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_ADDRESS2, null);
    }

    @Override
    public void setUserP_country_id(String userP_country_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_COUNTRY_ID, userP_country_id);
        editor.commit();
    }

    @Override
    public String getUserP_country_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_COUNTRY_ID, null);
    }

    @Override
    public void setUserP_state_id(String userP_state_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_STATE_ID, userP_state_id);
        editor.commit();
    }

    @Override
    public String getUserP_state_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_STATE_ID, null);
    }

    @Override
    public void setUserP_city(String userP_city) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_CITY, userP_city);
        editor.commit();
    }

    @Override
    public String getUserP_city() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_CITY, null);
    }

    @Override
    public void setUserP_zip(String userP_zip) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_ZIP, userP_zip);
        editor.commit();
    }

    @Override
    public String getUserP_zip() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_ZIP, null);
    }

    @Override
    public void setUserP_timezone_id(String userP_timezone_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_TIMEZONE_ID, userP_timezone_id);
        editor.commit();
    }

    @Override
    public String getUserP_timezone_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_TIMEZONE_ID, null);
    }

    @Override
    public void setUserIsEmailConfirm(String userIsEmailConfirm) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_ISEMAILCONFIRM, userIsEmailConfirm);
        editor.commit();
    }

    @Override
    public String getUserIsEmailConfirm() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_ISEMAILCONFIRM, null);
    }

    @Override
    public void setUserToken(String userToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_TOKEN, userToken);
        editor.commit();
    }

    @Override
    public String getUserToken() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_TOKEN, null);
    }

    @Override
    public void setUserFacebook_id(String userFacebook_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_FACBOOK_ID, userFacebook_id);
        editor.commit();
    }

    @Override
    public String getUserFacebook_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_FACBOOK_ID, null);
    }

    @Override
    public void setUserGoogle_id(String userGoogle_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_GOOGLE_ID, userGoogle_id);
        editor.commit();
    }

    @Override
    public String getUserGoogle_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_GOOGLE_ID, null);
    }

    @Override
    public void setUserIs_active(String userIs_active) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_IS_ACTIVE, userIs_active);
        editor.commit();
    }

    @Override
    public String getUserIs_active() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_IS_ACTIVE, null);
    }

    @Override
    public void setUserIs_anonymous(String userIs_anonymous) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_IS_ANONYMOUS, userIs_anonymous);
        editor.commit();
    }

    @Override
    public String getUserIs_anonymous() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_IS_ANONYMOUS, null);
    }


    @Override
    public void setUserProf_stage(String userProf_stage) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_PROF_STAGE, userProf_stage);
        editor.commit();
    }

    @Override
    public String getUserUserProf_stage() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_PROF_STAGE, null);
    }

    @Override
    public void setUserP_status(String userP_status) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_P_STATUS, userP_status);
        editor.commit();
    }

    @Override
    public String getUserP_status() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_P_STATUS, null);
    }

    @Override
    public void setUserdevice_type(String userdevice_type) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_DEVICE_TYPE, userdevice_type);
        editor.commit();
    }

    @Override
    public String getUserdevice_type() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_DEVICE_TYPE, null);
    }

    @Override
    public void setUserdevice_id(String userdevice_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_DEVICE_ID, userdevice_id);
        editor.commit();
    }

    @Override
    public String getUserdevice_id() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_DEVICE_ID, null);
    }

    @Override
    public void setUserdevice_token(String userdevice_token) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_DEVICE_TOKEN, userdevice_token);
        editor.commit();
    }

    @Override
    public String getUserdevice_token() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_DEVICE_TOKEN, null);
    }

    @Override
    public void setUserotp(String userotp) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_OTP, userotp);
        editor.commit();
    }

    @Override
    public String getUserotp() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_OTP, null);
    }
    @Override
    public void setUserCreated_on(String userCreated_on) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_CREATD_ON, userCreated_on);
        editor.commit();
    }

    @Override
    public String getUserCreated_on() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_CREATD_ON, null);
    }

    @Override
    public void setUserUpdated_on(String userUpdated_on) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_UPDATED_ON, userUpdated_on);
        editor.commit();
    }

    @Override
    public String getUserUpdated_on() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_UPDATED_ON, null);
    }

    @Override
    public void setUserUpdated_by(String userUpdated_by) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_USER_UPDATED_BY, userUpdated_by);
        editor.commit();
    }

    @Override
    public String getUserUpdated_by() {
        return getSharedPreferences(context).getString(PREF_KEY_USER_UPDATED_BY, null);
    }

    @Override
    public void setProviderToken(String providerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_TOKEN, providerToken);
        editor.commit();


    }

    @Override
    public String getProviderToken() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_TOKEN, null);

    }

    @Override
    public void setAccountType(String type) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_ACCOUNT_TYPE, type);
        editor.commit();
    }

    @Override
    public String getAccountType() {
        return getSharedPreferences(context).getString(PREF_KEY_ACCOUNT_TYPE, null);

    }

    @Override
    public void setFCMToken(String providerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_FCM_TOKEN, providerToken);
        editor.commit();
    }

    @Override
    public String getFCMToken() {
        return getSharedPreferences(context).getString(PREF_KEY_FCM_TOKEN, null);

    }

    @Override
    public void setProviderTherap_id(String therap_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_THERRAP_ID, therap_id);
        editor.commit();
    }

    @Override
    public String getProviderTherap_id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_THERRAP_ID, null);

    }

    @Override
    public void setProviderAccount_id(String providerAccount_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_ACCOUNT_ID, providerAccount_id);
        editor.commit();
    }

    @Override
    public String getProviderAccount_id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_ACCOUNT_ID, null);
    }

    @Override
    public void setProviderT_fname(String providerT_fname) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_FNAME, providerT_fname);
        editor.commit();
    }

    @Override
    public String getProviderT_fname() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_FNAME, null);
    }

    @Override
    public void setProviderT_lname(String providerT_lname) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_LNAME, providerT_lname);
        editor.commit();
    }

    @Override
    public String getProviderT_lname() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_LNAME, null);
    }

    @Override
    public void setProviderT_email(String providerT_email) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_EMAIL, providerT_email);
        editor.commit();
    }

    @Override
    public String getProviderT_email() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_EMAIL, null);
    }

    @Override
    public void setProviderT_pswd(String providerT_pswd) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_PSWD, providerT_pswd);
        editor.commit();
    }

    @Override
    public String getProviderT_pswd() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_PSWD, null);
    }

    @Override
    public void setProviderT_prof_pic(String providerT_prof_pic) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_PROF_PIC, providerT_prof_pic);
        editor.commit();
    }

    @Override
    public String getProviderT_prof_pic() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_PROF_PIC, null);
    }

    @Override
    public void setProviderT_gender(String providerT_gender) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_GENDER, providerT_gender);
        editor.commit();
    }

    @Override
    public String getProviderT_gender() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_GENDER, null);
    }

    @Override
    public void setProviderT_dob(String providerT_dob) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_DOB, providerT_dob);
        editor.commit();
    }

    @Override
    public String getProviderT_dob() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_DOB, null);
    }

    @Override
    public void setProviderT_phone(String providerT_phone) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_PHONE, providerT_phone);
        editor.commit();
    }

    @Override
    public String getProviderT_phone() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_PHONE, null);
    }


    @Override
    public void setProviderT_mobile(String providerT_mobile) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_MOBILE, providerT_mobile);
        editor.commit();
    }

    @Override
    public String getProviderT_mobile() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_MOBILE, null);
    }

    @Override
    public void setProviderT_econtact(String providerT_econtact) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_ECONTACT, providerT_econtact);
        editor.commit();
    }

    @Override
    public String getProviderT_econtact() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_ECONTACT, null);
    }

    @Override
    public void setProviderT_address1(String providerT_address1) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_ADDRESS1, providerT_address1);
        editor.commit();
    }

    @Override
    public String getProviderT_address1() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_ADDRESS1, null);
    }

    @Override
    public void setProviderT_address2(String t_address2) {

    }

    @Override
    public String getProviderT_address2() {
        return null;
    }


    @Override
    public void setT_address2(String t_address2) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_ADDRESS2, t_address2);
        editor.commit();
    }

    @Override
    public String getT_address2() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_ADDRESS2, null);
    }



    @Override
    public void setProviderT_country_id(String providerT_country_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_COUNTRY_ID, providerT_country_id);
        editor.commit();
    }

    @Override
    public String getProviderT_country_id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_COUNTRY_ID, null);
    }

    @Override
    public void setProviderT_state_id(String providerT_state_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_STATE_ID, providerT_state_id);
        editor.commit();
    }

    @Override
    public String getProviderT_state_id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_STATE_ID, null);
    }


    @Override
    public void setProviderT_city(String providerT_city) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_CITY, providerT_city);
        editor.commit();
    }

    @Override
    public String getProviderT_city() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_CITY, null);
    }

    @Override
    public void setProviderT_zip(String providerT_zip) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_ZIP, providerT_zip);
        editor.commit();
    }

    @Override
    public String getProviderT_zip() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_ZIP, null);
    }


    @Override
    public void setProviderT_timezone_id(String providerT_timezone_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_T_TIMEZONE_ID, providerT_timezone_id);
        editor.commit();
    }

    @Override
    public String getProviderT_timezone_id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_T_TIMEZONE_ID, null);
    }

    @Override
    public void setProviderProf_stage(String prof_stage) {

    }

    @Override
    public String getProviderProf_stage() {
        return null;
    }


    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setProf_stage(String prof_stage) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_PROF_STAGE, prof_stage);
        editor.commit();
    }

    @Override
    public String getProf_stage() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_PROF_STAGE, null);
    }




    @Override
    public void setProviderIsEmailConfirm(String providerIsEmailConfirm) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_ISEMAILCONFIRM, providerIsEmailConfirm);
        editor.commit();
    }

    @Override
    public String getProviderIsEmailConfirm() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_ISEMAILCONFIRM, null);
    }

    @Override
    public void setProviderIs_reverify(String providerIs_reverify) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_IS_REVERIFY, providerIs_reverify);
        editor.commit();
    }

    @Override
    public String getProviderIs_reverify() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_IS_REVERIFY, null);
    }

    @Override
    public void setProviderTokenU(String providerToken) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_TOKENU, providerToken);
        editor.commit();
    }

    @Override
    public String getProviderTokenU() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_TOKENU, null);
    }


    @Override
    public void setProviderStatus(String providerStatus) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_STATUS, providerStatus);
        editor.commit();
    }

    @Override
    public String getProviderStatus() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_STATUS, null);
    }

    @Override
    public void setProviderdevice_type(String providerdevice_type) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_DEVICETYPE, providerdevice_type);
        editor.commit();
    }

    @Override
    public String getProviderdevice_type() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_DEVICETYPE, null);
    }


    @Override
    public void setProviderdevice_id(String providerdevice_id) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_DEVICE_ID, providerdevice_id);
        editor.commit();
    }

    @Override
    public String getProviderdevice_id() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_DEVICE_ID, null);
    }

    @Override
    public void setProviderotp(String providerotp) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_OTP, providerotp);
        editor.commit();
    }

    @Override
    public String getProviderotp() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_OTP, null);
    }


    @Override
    public void setProviderCreated_on(String providerCreated_on) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_CREATED_ON, providerCreated_on);
        editor.commit();
    }

    @Override
    public String getProviderCreated_on() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_CREATED_ON, null);
    }

    @Override
    public void setProviderUpdated_on(String providerUpdated_on) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_KEY_PROVIDER_UPDATED_ON, providerUpdated_on);
        editor.commit();
    }

    @Override
    public String getProviderUpdated_on() {
        return getSharedPreferences(context).getString(PREF_KEY_PROVIDER_UPDATED_ON, null);
    }
}
