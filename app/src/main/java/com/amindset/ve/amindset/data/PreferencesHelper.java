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


/**
 * Created by Satendra on 27/01/17.
 */

public interface PreferencesHelper {

    void setProvider_ConnectAc_Id(String userBearerToken);

    String getProvider_ConnectAc_Id();


    void setProvider_strip_bank_id(String userBearerToken);

    String getProvider_strip_bank_id();


    void setProvider_regionBank(String userBearerToken);

    String getProvider_regionBank();


    void setProvider_account_holder_name(String userBearerToken);

    String getProvider_account_holder_name();


    void setProvider_account_number(String userBearerToken);

    String getProvider_account_number();


    void setProvider_bank_name(String userBearerToken);

    String getProvider_bank_name();


    void setProvider_address_on_bank(String userBearerToken);

    String getProvider_address_on_bank();







    void setUserAccount_id(String userBearerToken);

    String getUserAccount_id();

    void setUsercard_id(String userBearerToken);

    String getUsercard_id();

    void setUseraccount_holder_name(String userBearerToken);

    String getUseraccount_holder_name();

    void setUseraccount_number(String userBearerToken);

    String getUseraccount_number();

    void setUserexp_month(String userBearerToken);

    String getUserexp_month();

    void setUserexp_year(String userBearerToken);

    String getUserexp_year();

    void setUsercvv(String userBearerToken);

    String getUsercvv();

    void setUseraccount_token(String userBearerToken);

    String getUseraccount_token();

    void setUserisLogout(String userBearerToken);

    String getUserisLogout();
















    void setUserBToken(String userBearerToken);

    String getUserBToken();


    void setUserPat_id(String userPat_id);

    String getUserPat_id();


    void setUserP_fname(String userP_fname);

    String getUserP_fname();

    void setUserP_lname(String userP_lname);

    String getUserP_lname();

    void setUserUser_name(String userUser_name);

    String getUserUser_name();

    void setUserP_email(String useruserP_email);

    String getUserP_email();


    void setUserPswd(String userPswd);

    String getUserPswd();

    void setUserP_Prof_pic(String userP_Prof_pic);

    String getUserP_Prof_pic();

    void setUserP_gender(String userP_gender);

    String getUserP_gender();

    void setUserP_phone(String userP_phone);

    String getUserP_phone();

    void setUserT_mobile(String userT_mobile);

    String getUserT_mobile();

    void setUserP_econtact(String userP_econtact);

    String getUserP_econtact();

    void setUserP_dob(String userP_dob);

    String getUserP_dob();

    void setUserP_nickname(String userP_nickname);

    String getUserP_nickname();

    void setUserP_address1(String userP_nickname);

    String getUserP_address1();

    void setUserP_address2(String userP_nickname);

    String getUserP_address2();

    void setUserP_country_id(String userP_country_id);

    String getUserP_country_id();

    void setUserP_state_id(String userP_state_id);

    String getUserP_state_id();

    void setUserP_city(String userP_city);

    String getUserP_city();

    void setUserP_zip(String userP_zip);

    String getUserP_zip();

    void setUserP_timezone_id(String userP_timezone_id);

    String getUserP_timezone_id();

    void setUserIsEmailConfirm(String userIsEmailConfirm);

    String getUserIsEmailConfirm();

    void setUserToken(String userToken);

    String getUserToken();

    void setUserFacebook_id(String userFacebook_id);

    String getUserFacebook_id();

    void setUserGoogle_id(String userGoogle_id);

    String getUserGoogle_id();

    void setUserIs_active(String userIs_active);

    String getUserIs_active();

    void setUserIs_anonymous(String userIs_anonymous);

    String getUserIs_anonymous();

    void setUserProf_stage(String userProf_stage);

    String getUserUserProf_stage();

    void setUserP_status(String userP_status);

    String getUserP_status();

    void setUserdevice_type(String userdevice_type);

    String getUserdevice_type();

    void setUserdevice_id(String userdevice_id);

    String getUserdevice_id();

    void setUserdevice_token(String userdevice_token);

    String getUserdevice_token();

    void setUserotp(String userotp);

    String getUserotp();

    void setUserCreated_on(String userCreated_on);

    String getUserCreated_on();

    void setUserUpdated_on(String userUpdated_on);

    String getUserUpdated_on();

    void setUserUpdated_by(String userUpdated_by);

    String getUserUpdated_by();


    //Provider Data

    void setProviderToken(String providerToken);

    String getProviderToken();


    void setAccountType(String type);

    String getAccountType();

    void setProf_stage(String providerProf_stage);

    String getProf_stage();

    void setT_address2(String provideraddress2);

    String getT_address2();

    void setFCMToken(String providerToken);

    String getFCMToken();

    void setProviderTherap_id(String therap_id);

    String getProviderTherap_id();

    void setProviderAccount_id(String providerAccount_id);

    String getProviderAccount_id();

    void setProviderT_fname(String providerT_fname);

    String getProviderT_fname();



    void setProviderT_lname(String providerT_lname);

    String getProviderT_lname();

    void setProviderT_email(String providerT_email);

    String getProviderT_email();

    void setProviderT_pswd(String providerT_pswd);

    String getProviderT_pswd();

    void setProviderT_prof_pic(String providerT_prof_pic);

    String getProviderT_prof_pic();

    void setProviderT_gender(String providerT_gender);

    String getProviderT_gender();

    void setProviderT_dob(String providerT_dob);

    String getProviderT_dob();


    void setProviderT_phone(String providerT_phone);

    String getProviderT_phone();

    void setProviderT_mobile(String providerT_mobile);

    String getProviderT_mobile();

    void setProviderT_econtact(String providerT_econtact);

    String getProviderT_econtact();

    void setProviderT_address1(String providerT_address1);

    String getProviderT_address1();

    void setProviderT_address2(String t_address2);

    String getProviderT_address2();

    void setProviderT_country_id(String providerT_country_id);

    String getProviderT_country_id();


    void setProviderT_state_id(String providerT_state_id);

    String getProviderT_state_id();


    void setProviderT_city(String providerT_city);

    String getProviderT_city();


    void setProviderT_zip(String providerT_zip);

    String getProviderT_zip();


    void setProviderT_timezone_id(String providerT_timezone_id);

    String getProviderT_timezone_id();


    void setProviderProf_stage(String prof_stage);

    String getProviderProf_stage();

    void setProviderIsEmailConfirm(String providerIsEmailConfirm);

    String getProviderIsEmailConfirm();


    void setProviderIs_reverify(String providerIs_reverify);

    String getProviderIs_reverify();


    void setProviderTokenU(String providerToken);

    String getProviderTokenU();


    void setProviderStatus(String providerStatus);

    String getProviderStatus();


    void setProviderdevice_type(String providerdevice_type);

    String getProviderdevice_type();

    void setProviderdevice_id(String providerdevice_id);

    String getProviderdevice_id();

    void setProviderotp(String providerotp);

    String getProviderotp();

    void setProviderCreated_on(String providerCreated_on);

    String getProviderCreated_on();

    void setProviderUpdated_on(String providerUpdated_on);

    String getProviderUpdated_on();






}
