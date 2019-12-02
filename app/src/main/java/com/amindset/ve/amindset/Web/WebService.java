package com.amindset.ve.amindset.Web;


import com.amindset.ve.amindset.CallCost.ModelUserCallDetails;
import com.amindset.ve.amindset.CallCost.ModelUserSaveRating;
import com.amindset.ve.amindset.CallCost.ProviderCharge.ProviderGetChargeDetails;
import com.amindset.ve.amindset.ForgotPassword.Model.ModelProviderForgotPassword;
import com.amindset.ve.amindset.ForgotPassword.Model.ModelUserForgot.ModelUserForgotPassword;
import com.amindset.ve.amindset.Fragment.providerservice.providerEarning.ModelProviderTranscation.ModelProviderRecentTransaction;
import com.amindset.ve.amindset.Fragment.providerservice.providerRecentList.ModelRecent.ModelProviderRecentCall;
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelAboutYou.ModelProviderAboutyou;
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelEmergecyProvider.ModelProviderEmergency;
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderAccountDetails.ModelProviderAccountDetails;
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderDetails.ModelProviderDetails;
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderSetting.ModelProviderSecuritySetting;
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelUpdateProfile.ModelProvideProfileUpdate;
import com.amindset.ve.amindset.Fragment.user.Appoinment.Model.ModelUserPassAppointments;
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelAddProviderFavList.ModelUserFavListAdd;
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelAvtar.ModelUpdateAvtar;
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelCallRate.ModelCounselorCallRate;
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelDetails.ModelCounselorDetails;
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelLiability.ModelLiabilityDoc;
import com.amindset.ve.amindset.Fragment.user.Counselorlist.Model.ModelCounselorList;
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.ModelQuestionList;
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.ModelQuesAns.ModelSaveAnswer;
import com.amindset.ve.amindset.Fragment.user.FavList.ModelRemoveFavList.ModelUserFavListRemove;
import com.amindset.ve.amindset.Fragment.user.Notification.ModelProviderNotifi.ModelProviderNotification;
import com.amindset.ve.amindset.Fragment.user.Notification.ModelUserNotifica.ModelUserNotification;
import com.amindset.ve.amindset.Fragment.user.UserProfile.Model.ModelUserDetails;
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelAboutYou.ModelAboutYou;
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelChangePassword.ModelPassChange;
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelEmergectCont.ModelEmergecyContact;
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelLogout.ModelUserLogout;
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelPayment.ModelUserPayment;
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelUpdateProfile.ModelUserProfileUpdate;
import com.amindset.ve.amindset.Fragment.userservice.Model.ModelUserService;
import com.amindset.ve.amindset.Fragment.userservice.ModelFavList.ModelUserFavList;
import com.amindset.ve.amindset.NewPassword.Model.ModelUserResetPassword;
import com.amindset.ve.amindset.NewPassword.Model.providermodel.ModelProviderResetPassword;
import com.amindset.ve.amindset.OTP.UserModel.ModelUserOtpVerify;
import com.amindset.ve.amindset.OTP.providerModel.ModelProviderOtpVerify;
import com.amindset.ve.amindset.ProfessionalVerification.ModelCountryList.ModelCountryList;
import com.amindset.ve.amindset.OTP.ModelLang.ModelLangList;
import com.amindset.ve.amindset.ProfessionalVerification.ModelState.ModelStateList;
import com.amindset.ve.amindset.ProfessionalVerification.ModelUpdateDocument.ModelProvideDocumentUpload;
import com.amindset.ve.amindset.SignUp.DataModel.ModelProficiencyList;
import com.amindset.ve.amindset.SignUp.DataModel.ModelSignUp.ModelSignUp;
import com.amindset.ve.amindset.SignUp.UsersignUp.ModelUserSignUp;
import com.amindset.ve.amindset.Signin.DataModel.ProviderSignModel;
import com.amindset.ve.amindset.Signin.DataModel.UserSigninModel.ModelUserSignin;
import com.amindset.ve.amindset.Splash.ModelFCM.ModelFCmTokenResponse;
import com.amindset.ve.amindset.TextChat.ModelTextChat.ModelTextChatNotification;
import com.amindset.ve.amindset.TextChat.ModelTextChatToken;
import com.amindset.ve.amindset.VdoCall.ModelAccessTokenVDOcall.ModelAccessTokenVDO;
import com.amindset.ve.amindset.VdoCall.ModelNotificationData.ModelTwilloNotificationUserData;
import com.amindset.ve.amindset.VdoCall.ProviderQuestionAndAnswer.ProviderGetQuestionAndAnswer;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

public interface WebService {

    @POST("mobile/Login.php")
    Call<ProviderSignModel> getProviderData(@Body HashMap<String, String> body);

    @POST("mobile_user/Login.php")
    Call<ModelUserSignin> getUserData(@Body HashMap<String, String> body);

    @POST("mobile/Register.php")
    Call<ModelSignUp> registerProvider(@Body HashMap<String, String> body);

    @POST("mobile_user/Registers.php")
    Call<ModelUserSignUp> registerUser(@Body HashMap<String, String> body);

    @POST("calling/updatedeviceinfo.php")
    Call<ModelFCmTokenResponse> registerFCMToken(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile_user/GetServices.php")
    Call<ModelUserService> getUserService();

    @POST("mobile_user/GetQuestions.php")
    Call<ModelQuestionList> getQuestionList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile_user/GetNotificationList.php")
    Call<ModelUserNotification> GetNotificationList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/Appointments.php")
    Call<ModelProviderNotification> GetProviderNotificationList(@Header("Authorization") String authHeader);

    @POST("mobile_user/saveCallDeatails.php")
    Call<ModelUserCallDetails> saveCallDetails(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile_user/saveRate.php")
    Call<ModelUserSaveRating> saveCallRating(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/getChargeDetails.php")
    Call<ProviderGetChargeDetails> getProviderChargeDetails(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile/ProviderRecentTranscation.php")
    Call<ModelProviderRecentTransaction> getProviderTranstion(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile/FavoriteList.php")
    Call<ModelUserFavList> getUserFavList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/AddFavorite.php")
    Call<ModelUserFavListAdd> AddProviderInUserFavList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/RemoveFavoritelist.php")
    Call<ModelUserFavListAdd> RemoveProviderInUserFavList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/CounselorsList.php")
    Call<ModelCounselorList> getCounselorList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/CountryList.php")
    Call<ModelCountryList> getCountryList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/language.php")
    Call<ModelLangList> getLangList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/State.php")
    Call<ModelStateList> getStateList(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);



    @POST("mobile_user/recent.php")
    Call<ModelUserPassAppointments> getUserPastAppointments(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/changePassword.php")
    Call<ModelPassChange> getChangePassword(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/changePassword.php")
    Call<ModelProviderSecuritySetting> getChangePasswordProvider(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/emergencyInformation.php")
    Call<ModelEmergecyContact> getEmergecyNumberChanged(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile/Provider_emergencyContact.php")
    Call<ModelProviderEmergency> getEmergecyNumberProvider(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/AddBankDetails.php")
    Call<ModelProviderAccountDetails> saveProviderBankDetails(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/saveAbout.php")
    Call<ModelAboutYou> saveAboutUser(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile/saveAbout.php")
    Call<ModelProviderAboutyou> saveAboutProvider(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/edit.php")
    Call<ModelUserProfileUpdate> updateUserProfile(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile/updateProviderProfile.php")
    Call<ModelProvideProfileUpdate> updateProviderProfile(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/AddCardDetails.php")
    Call<ModelUserPayment> UserPayment(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/logout.php")
    Call<ModelUserLogout> userLogout(@Header("Authorization") String authHeader);


    @POST("mobile_user/CouncelorDetail.php")
    Call<ModelCounselorDetails> getCounselorDetails(@Body HashMap<String, String> body, @Header("Authorization") String authHeader);

    @POST("mobile_user/fetchCallRate.php")
    Call<ModelCounselorCallRate> getCounselorCallRate(@Body HashMap<String, String> body, @Header("Authorization") String authHeader);


    @POST("mobile_user/GetPersonalDetails.php")
    Call<ModelUserDetails> getUserDetails(@Header("Authorization") String authHeader);

    @POST("mobile_user/recent.php")
    Call<ModelProviderRecentCall> getProviderRecentCallList(@Body HashMap<String, Integer> body, @Header("Authorization") String authHeader);


    @POST("mobile/GetProvidersDetails.php")
    Call<ModelProviderDetails> GetProvidersDetails(@Header("Authorization") String authHeader);

    @POST("mobile/ForgotPassord.php")
    Call<ModelProviderForgotPassword> postProviderForgotPassword(@Body HashMap<String, String> body);

    @POST("calling/getTokenOnVideoCall.php")
    Call<ModelAccessTokenVDO> getAccessTokenVDO(@Body HashMap<String, String> body);

    @POST("mobile/getReport.php")
    Call<ProviderGetQuestionAndAnswer> getQuestionAndAnswerReport(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/AddAnswers.php")
    Call<ModelSaveAnswer> postAnswer(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/RemoveFavoritelist.php")
    Call<ModelUserFavListRemove> postForProviderDislike(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("calling/pushnotification.php")
    Call<ModelTwilloNotificationUserData> postPushNotification(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile_user/ForgotPassord.php")
    Call<ModelUserForgotPassword> postUserForgotPassword(@Body HashMap<String, String> body);


    @Multipart
    @POST("mobile_user/updateAvatar.php")
    Single<ModelUpdateAvtar> updateCounselorAvtar(@Part List<MultipartBody.Part> l , @Header("Authorization") String authHeader);



    @Multipart
    @POST("mobile/ProviderLiabilityInsurance.php")
    Single<ModelLiabilityDoc> updateProvideLiabilityDoc(@Part List<MultipartBody.Part> l , @Header("Authorization") String authHeader);



    @Multipart
    @POST("mobile/updateAvatar.php")
    Single<ModelUpdateAvtar> updateProvideProfileAvtar(@Part List<MultipartBody.Part> l , @Header("Authorization") String authHeader);



//    lang:eng
//    country_id:1
//    state_id:6
//    doc_type6:3

    @Multipart
    @POST("mobile/UploadDocument.php")
    Single<ModelProvideDocumentUpload> postDriverDocument( @Part("country_id") RequestBody countryId,
                                                          @Part("state_id") RequestBody stateId,
                                                           @Part List<MultipartBody.Part> image1Body,
                                                          @Part List<MultipartBody.Part> image2Body,
                                                          @Part List<MultipartBody.Part> image3Body,
                                                          @Part List<MultipartBody.Part> resumeBody,
                                                          @Part List<MultipartBody.Part> w8w9Body,
                                                           @Part("doc_id1") RequestBody doc_id1,
                                                          @Part("doc_name1") RequestBody doc_name1,
                                                          @Part("doc_type1") RequestBody doc_type1,
                                                          @Part("doc_id2") RequestBody doc_id2,
                                                          @Part("doc_name2") RequestBody doc_name2,
                                                          @Part("doc_type2") RequestBody doc_type2,
                                                          @Part("doc_type3") RequestBody doc_type3,
                                                          @Part("doc_type4") RequestBody doc_type4,
                                                           @Part("doc_type5") RequestBody doc_type5,
                                                           @Part("doc_type6") RequestBody doc_type6,
                                                          @Part("lang") RequestBody lang,
                                                          @Header("Authorization") String authHeader,
                                                           @Part List<MultipartBody.Part> w9Body

                                                           );

    @POST("Chat/GenerateToken.php")
    Call<ModelTextChatToken> getTextChatAccessToken(@Body HashMap<String, String> body);

    @POST("Chat/chatNotification.php")
    Call<ModelTextChatNotification> getTextNotification(@Body HashMap<String, String> body, @Header("Authorization") String authHeader);


    @POST("mobile_user/VerifyOtp.php")
    Call<ModelUserOtpVerify> postUserOTPVerify(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);


    @POST("mobile/VerifyOtp.php")
    Call<ModelProviderOtpVerify> postProviderOTPVerify(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile_user/ResetPassword.php")
    Call<ModelUserResetPassword> postUserResetPassword(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @POST("mobile/ResetPassword.php")
    Call<ModelProviderResetPassword> postProviderResetPassword(@Body HashMap<String, String> body , @Header("Authorization") String authHeader);

    @GET("mobile/GetProficiencyList.php")
    Call<ModelProficiencyList> getProficiency();


}