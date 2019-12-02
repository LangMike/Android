package com.amindset.ve.amindset.BaseActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;



import java.util.HashMap;

import android.util.Log;
import com.amindset.ve.amindset.Constant;
import com.amindset.ve.amindset.ForgotPassword.Model.ModelProviderForgotPassword;
import com.amindset.ve.amindset.ForgotPassword.Model.ModelUserForgot.ModelUserForgotPassword;
import com.amindset.ve.amindset.NewPassword.Model.ModelUserResetPassword;
import com.amindset.ve.amindset.NewPassword.Model.providermodel.ModelProviderResetPassword;
import com.amindset.ve.amindset.OTP.UserModel.ModelUserOtpVerify;
import com.amindset.ve.amindset.OTP.providerModel.ModelProviderOtpVerify;
import com.amindset.ve.amindset.SignUp.DataModel.ModelProficiencyList;
import com.amindset.ve.amindset.SignUp.DataModel.ModelSignUp.ModelSignUp;
import com.amindset.ve.amindset.SignUp.UsersignUp.ModelUserSignUp;
import com.amindset.ve.amindset.Signin.DataModel.ProviderSignModel;
import com.amindset.ve.amindset.Signin.DataModel.UserSigninModel.ModelUserSignin;
import com.amindset.ve.amindset.Utils.CommonUtils;
import com.amindset.ve.amindset.VdoCall.ModelAccessTokenVDOcall.ModelAccessTokenVDO;
import com.amindset.ve.amindset.VdoCall.ModelNotificationData.ModelTwilloNotificationUserData;
import com.amindset.ve.amindset.Web.ApiClient;
import com.amindset.ve.amindset.Web.WebService;
import com.amindset.ve.amindset.data.PreferencesHelper;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;

public class UserRepository {

    // Simple in-memory cache. Details omitted for brevity.

    MutableLiveData<ProviderSignModel> providerSignIndata = new MutableLiveData<>();
    MutableLiveData<ModelUserSignin> modelUserSignin = new MutableLiveData<>();
    MutableLiveData<ModelProviderForgotPassword> providerForgotPassworddata = new MutableLiveData<>();
    MutableLiveData<ModelUserForgotPassword> userForgotPassword = new MutableLiveData<>();
    MutableLiveData<ModelProficiencyList> providerModelProficiencyList= new MutableLiveData<>();
    MutableLiveData<ModelSignUp> modelSignUpProviderMutableLiveData= new MutableLiveData<>();
    MutableLiveData<ModelUserSignUp> modelSignUpUserMutableLiveData= new MutableLiveData<>();
    MutableLiveData<ModelUserOtpVerify> modelUserOtpVerify= new MutableLiveData<>();
    MutableLiveData<ModelUserResetPassword> modelUserResetPassword= new MutableLiveData<>();
    MutableLiveData<ModelProviderOtpVerify> modelProviderOtpVerify= new MutableLiveData<>();
    MutableLiveData<ModelProviderResetPassword> modelProviderResetPassword= new MutableLiveData<>();


    MutableLiveData<ModelAccessTokenVDO> modelAccessTokenVDOMutableLiveData= new MutableLiveData<>();


    MutableLiveData<ModelTwilloNotificationUserData> modelTwilloNotificationUserData= new MutableLiveData<>();





    @Inject
    PreferencesHelper preferencesHelper;


    public UserRepository() {
    }


    // SignIn Provider method to hit the server
    public LiveData<ProviderSignModel> getProvider(String email, String pass) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("User_name", email);
        paramObject.put("password", pass);
        paramObject.put("device_type", Constant.deviceType);
        paramObject.put("device_id", CommonUtils.getDeviceId());
        paramObject.put("user_type", "provider");

        Call<ProviderSignModel> call = apiService.getProviderData(paramObject);

        call.enqueue(new retrofit2.Callback<ProviderSignModel>() {
            @Override
            public void onResponse(Call<ProviderSignModel> call, Response<ProviderSignModel> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        providerSignIndata.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProviderSignModel> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return providerSignIndata;
    }


    //Sign in User Or Patients
    public LiveData<ModelUserSignin> getUser(String userName, String password) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("User_name", userName);
        paramObject.put("password", password);
        paramObject.put("device_type", Constant.deviceType);
        paramObject.put("device_id", CommonUtils.getDeviceId());
        paramObject.put("user_type", "patient");


        Call<ModelUserSignin> call = apiService.getUserData(paramObject);

        call.enqueue(new retrofit2.Callback<ModelUserSignin>() {
            @Override
            public void onResponse(Call<ModelUserSignin> call, Response<ModelUserSignin> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelUserSignin.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUserSignin> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelUserSignin;
    }



    // SignUp or Register Provider Api
    public LiveData<ModelSignUp> registerProvider(String providerName ,String createUserName ,String providerEmailId ,
                                              String providerPhoneNumber ,String providerPassword ,
                                              String providerExp ,String providerProfessiontitle,String proficientId ,String providerGenderid) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("name", providerName);
        paramObject.put("email", providerEmailId);
        paramObject.put("password",providerPassword);
        paramObject.put("mobile_no", providerPhoneNumber);
        paramObject.put("proficiencies", proficientId);
        paramObject.put("total_exp", providerExp);
        paramObject.put("gender",providerGenderid);
        paramObject.put("title", providerProfessiontitle);
        paramObject.put("User_name", createUserName);


        Call<ModelSignUp> call = apiService.registerProvider(paramObject);

        call.enqueue(new retrofit2.Callback<ModelSignUp>() {
            @Override
            public void onResponse(Call<ModelSignUp> call, Response<ModelSignUp> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelSignUpProviderMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelSignUp> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelSignUpProviderMutableLiveData;
    }

//    userName,createUserName , userId,usernumber,
//    userNewpassword,userAge,userHeight,userWeight,""+providerGenderid

    //Register user
    public LiveData<ModelUserSignUp> registerUser(String userName , String createUserName , String userIdEmailId ,
                                                  String userPhoneNumber , String userPassword ,
                                                  String userAge , String userHeight,
                                                  String userWeight , String userGenderid) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("fname", userName);
        paramObject.put("email", userIdEmailId);
        paramObject.put("password",userPassword);
        paramObject.put("P_phone", userPhoneNumber);
        paramObject.put("age", userAge);
        paramObject.put("Height", userHeight);
        paramObject.put("Weight",userWeight);
        paramObject.put("P_gender", userGenderid);
        paramObject.put("User_name", createUserName);



//    }
        Call<ModelUserSignUp> call = apiService.registerUser(paramObject);

        call.enqueue(new retrofit2.Callback<ModelUserSignUp>() {
            @Override
            public void onResponse(Call<ModelUserSignUp> call, Response<ModelUserSignUp> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelSignUpUserMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUserSignUp> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelSignUpUserMutableLiveData;
    }




    // Get Profeciency List
    public LiveData<ModelProficiencyList> getProficiency() {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);


        Call<ModelProficiencyList> call = apiService.getProficiency();

        call.enqueue(new retrofit2.Callback<ModelProficiencyList>() {
            @Override
            public void onResponse(Call<ModelProficiencyList> call, Response<ModelProficiencyList> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        providerModelProficiencyList.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelProficiencyList> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return providerModelProficiencyList;
    }



    // Forgot Password Provider

    public LiveData<ModelProviderForgotPassword> providerForgotPassword(String mobileNumber) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("mobile_number", mobileNumber);


        Call<ModelProviderForgotPassword> call = apiService.postProviderForgotPassword(paramObject);

        call.enqueue(new retrofit2.Callback<ModelProviderForgotPassword>() {
            @Override
            public void onResponse(Call<ModelProviderForgotPassword> call, Response<ModelProviderForgotPassword> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        providerForgotPassworddata.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelProviderForgotPassword> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return providerForgotPassworddata;
    }


 // Send User Data to make a call
    public LiveData<ModelTwilloNotificationUserData> sendPushnotificationVDOData(String calltype,String bearerToken ,
                                                                                 String roomName ,
                                                                                 String userProfilePic,
                                                                                 String userContactNumber,
                                                                                 String providerNumber,String userName,String p_id) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);
        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("my_number", userContactNumber);
        paramObject.put("my_profile_pic_url", userProfilePic);
        paramObject.put("roomName", roomName);
        paramObject.put("to_number", providerNumber);
        paramObject.put("customer", "provider");
        paramObject.put("type", calltype);
        paramObject.put("userName", ""+userName);
        paramObject.put("patient_id", p_id);
        paramObject.put("proficiency_type", p_id);

        Call<ModelTwilloNotificationUserData> call = apiService.postPushNotification(paramObject , "Bearer " + bearerToken);

        call.enqueue(new retrofit2.Callback<ModelTwilloNotificationUserData>() {
            @Override
            public void onResponse(Call<ModelTwilloNotificationUserData> call, Response<ModelTwilloNotificationUserData> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelTwilloNotificationUserData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelTwilloNotificationUserData> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelTwilloNotificationUserData;
    }



    // Access Token For VDO call
    public LiveData<ModelAccessTokenVDO> accessTokenVDO( String deviceId , String phone) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("device_id",deviceId);
        paramObject.put("mobile_number", phone);
        paramObject.put("room_name", "roomName");

        Call<ModelAccessTokenVDO> call = apiService.getAccessTokenVDO(paramObject);

        call.enqueue(new retrofit2.Callback<ModelAccessTokenVDO>() {
            @Override
            public void onResponse(Call<ModelAccessTokenVDO> call, Response<ModelAccessTokenVDO> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelAccessTokenVDOMutableLiveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelAccessTokenVDO> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelAccessTokenVDOMutableLiveData;
    }


    //Forgot password for user

    public LiveData<ModelUserForgotPassword> userForgotPassword(String mobileNumber) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("mobile_number", mobileNumber);


        Call<ModelUserForgotPassword> call = apiService.postUserForgotPassword(paramObject);

        call.enqueue(new retrofit2.Callback<ModelUserForgotPassword>() {
            @Override
            public void onResponse(Call<ModelUserForgotPassword> call, Response<ModelUserForgotPassword> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        userForgotPassword.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUserForgotPassword> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return userForgotPassword;
    }

  // Verify OTP for User
    public LiveData<ModelUserOtpVerify> userOTPverify(String code1,String code2,String code3,String code4 , String bearetoken) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("otp", code1+code2+code3+code4);
        paramObject.put("device_id", CommonUtils.getDeviceId());
        paramObject.put("device_type", Constant.deviceType);


        Call<ModelUserOtpVerify> call = apiService.postUserOTPVerify(paramObject,"Bearer "+bearetoken);

        call.enqueue(new retrofit2.Callback<ModelUserOtpVerify>() {
            @Override
            public void onResponse(Call<ModelUserOtpVerify> call, Response<ModelUserOtpVerify> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelUserOtpVerify.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUserOtpVerify> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelUserOtpVerify;
    }


   // provider otp verify
    public LiveData<ModelProviderOtpVerify> providerOTPverify(String code1, String code2, String code3,
                                                              String code4, String prefTokenProviderB) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("otp", code1 + code2 +code3 +code4  );
        paramObject.put("device_id", CommonUtils.getDeviceId());
        paramObject.put("device_type", Constant.deviceType);


        Call<ModelProviderOtpVerify> call = apiService.postProviderOTPVerify(paramObject,"Bearer "+ prefTokenProviderB);

        call.enqueue(new retrofit2.Callback<ModelProviderOtpVerify>() {
            @Override
            public void onResponse(Call<ModelProviderOtpVerify> call, Response<ModelProviderOtpVerify> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelProviderOtpVerify.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelProviderOtpVerify> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelProviderOtpVerify;
    }



    // Reset User Password

    public LiveData<ModelUserResetPassword> userResetPassword(String password) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("password", password);
        paramObject.put("user_id", "15");
        paramObject.put("token", "c6T2yLsdxE7pi9U");


        Call<ModelUserResetPassword> call = apiService.postUserResetPassword(paramObject,"Bearer "+"eyJhbGciOiAiSFMyNTYiLCJ0eXAiOiAiSldUIn0=.eyJ1c2VyaWQiOiIxNSIsInRpbWUiOiIyMDE5LTA0LTExIDA1OjIyOjAwIiwiZGV2ZWxvcGVyIjoiU3BpZWxlciIsInRpbWV6b25lIjoiQW1lcmljYVwvTmV3X1lvcmsifQ==.q5noJonvttLKM+L9xxqdF45H5T/EF8yLSuuiWBV+Be0=");

        call.enqueue(new retrofit2.Callback<ModelUserResetPassword>() {
            @Override
            public void onResponse(Call<ModelUserResetPassword> call, Response<ModelUserResetPassword> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelUserResetPassword.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUserResetPassword> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelUserResetPassword;
    }
 // Reser provider password
    public LiveData<ModelProviderResetPassword> providerResetPassword(String password, PreferencesHelper pref) {

        WebService apiService =
                ApiClient.getClient().create(WebService.class);

        HashMap<String, String> paramObject = new HashMap<>();
        paramObject.put("password", password);
        paramObject.put("user_id", pref.getProviderTherap_id());
        paramObject.put("token", pref.getProviderTokenU());


        Call<ModelProviderResetPassword> call = apiService.postProviderResetPassword(paramObject,"Bearer "+ pref.getProviderToken());

        call.enqueue(new retrofit2.Callback<ModelProviderResetPassword>() {
            @Override
            public void onResponse(Call<ModelProviderResetPassword> call, Response<ModelProviderResetPassword> response) {
                if (response != null) {
                    if (response.isSuccessful()) {
                        modelProviderResetPassword.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelProviderResetPassword> call, Throwable t) {
                // Log error here since request failed
            }
        });


        return modelProviderResetPassword;
    }

}
