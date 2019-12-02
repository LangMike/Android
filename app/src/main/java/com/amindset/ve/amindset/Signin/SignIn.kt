package com.amindset.ve.amindset.Signin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Dashboard.Dashboard
import com.amindset.ve.amindset.ForgotPassword.ForgotPass
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.SignUp.signup_activity
import com.amindset.ve.amindset.Signin.DataModel.ProviderSignModel
import com.amindset.ve.amindset.Signin.DataModel.UserSigninModel.ModelUserSignin
import com.amindset.ve.amindset.Splash.ModelFCM.ModelFCmTokenResponse
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import com.amindset.ve.amindset.data.PreferencesHelper
import com.amindset.ve.amindset.databinding.ActivitySignInBinding
import com.sinch.android.rtc.Sinch
import com.sinch.android.rtc.SinchClient
import retrofit2.Call
import retrofit2.Response
import java.util.HashMap
import javax.inject.Inject


class SignIn : BaseActivity() {

    var signInBind : ActivitySignInBinding? = null

    lateinit var sinchClient: SinchClient


    var  maintainEyeView : Boolean = false

    var selectionTye : String? = null;

    lateinit var viewModel: SignInViewModel

    @Inject
    lateinit var  pref : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInBind = DataBindingUtil.setContentView(this,R.layout.activity_sign_in)

        getSelectionActivityData();
      
        (application as AmidApp).getMyComponent().inject(this@SignIn)


        signInBind!!.etPassword.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= signInBind!!.etPassword.getRight() - signInBind!!.etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                    // your action here

                    if(!maintainEyeView) {
                        signInBind!!.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        signInBind!!.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_on_login, 0
                        );
                        maintainEyeView = true
                    }
                    else
                    {
//                        editText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        signInBind!!.etPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        signInBind!!.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_off_login, 0
                        );
                        maintainEyeView = false
                    }


                    return@OnTouchListener true
                }
            }
            false
        });
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

    }

    private fun getSelectionActivityData() {

        selectionTye= intent.getStringExtra("type")
    }

    internal val signinProviderObserver: Observer<ProviderSignModel> = Observer<ProviderSignModel> { providerRegisterData ->

        hideLoading()

        if(providerRegisterData!!.status==Constant.response_Failure)
        {
            showMessage(providerRegisterData.message)

        }
        else  if(providerRegisterData!!.status==Constant.RESPONSE_SUCCESSFULLY){
            MethodToRegisterFCMToken(pref.fcmToken,providerRegisterData.token , "provider");


            if (pref.providerT_mobile != null) {
                val context = this.applicationContext
                sinchClient = Sinch.getSinchClientBuilder().context(context)
                    .applicationKey("ae6f9531-0a22-49dc-b8eb-c610179aa85c")
                    .applicationSecret("nIcuV+wZPEafPIDJBknYDw==")
                    .environmentHost("clientapi.sinch.com")
                    .userId(pref.providerT_mobile)
                    .build()
                sinchClient.setSupportCalling(true)
                sinchClient.startListeningOnActiveConnection()
                sinchClient.start()

            }

            var fcmTokenLocal = pref.fcmToken
            val preferences = getSharedPreferences(AppPreferencesHelper.PREF_NAME, Context.MODE_PRIVATE);
            val editor = preferences.edit();
            editor.clear();
            editor.commit();
            pref.fcmToken = fcmTokenLocal;

            Thread({

                pref.providerToken = providerRegisterData.token
                pref.accountType= "provider"
                pref.providerTokenU = providerRegisterData.data.token
                pref.prof_stage = providerRegisterData.data.profStage
                pref.providerAccount_id = providerRegisterData.data.accountId
                pref.providerCreated_on = providerRegisterData.data.createdOn
                pref.providerIsEmailConfirm = providerRegisterData.data.isEmailConfirm
                pref.providerIs_reverify = providerRegisterData.data.isReverify
                pref.providerStatus = providerRegisterData.data.status
                pref.providerT_address1 = providerRegisterData.data.tAddress1
                pref.providerT_city = providerRegisterData.data.tCity
                pref.providerT_fname = providerRegisterData.data.tFname
                pref.providerT_lname = providerRegisterData.data.tLname
                pref.providerT_email = providerRegisterData.data.tEmail
                pref.providerT_pswd = providerRegisterData.data.tPswd
                pref.providerT_prof_pic = providerRegisterData.data.tProfPic
                pref.providerT_gender = providerRegisterData.data.tGender
                pref.providerT_dob = providerRegisterData.data.tDob
                pref.providerT_phone = providerRegisterData.data.tPhone
                pref.providerT_mobile = providerRegisterData.data.tMobile
                pref.providerT_econtact = providerRegisterData.data.tEcontact
                pref.providerT_country_id = providerRegisterData.data.tCountryId
                pref.providerUpdated_on = providerRegisterData.data.updatedOn
                pref.providerotp = providerRegisterData.data.otp
                pref.providerdevice_id = providerRegisterData.data.deviceId
                pref.providerdevice_type = providerRegisterData.data.deviceType
                pref.providerT_timezone_id = providerRegisterData.data.tTimezoneId
                pref.providerT_zip = providerRegisterData.data.tZip
                pref.providerT_country_id = providerRegisterData.data.tCountryId
                pref.t_address2 = providerRegisterData.data.tAddress2
                pref.providerTherap_id = providerRegisterData.data.therapId
                pref.provider_ConnectAc_Id = providerRegisterData.data.connectAccountId
                pref.provider_strip_bank_id = providerRegisterData.data.stripBankId
                pref.provider_regionBank = providerRegisterData.data.regionBank
                pref.provider_account_holder_name = providerRegisterData.data.accountHolderName
                pref.provider_account_number = providerRegisterData.data.accountNumber
                pref.provider_bank_name = providerRegisterData.data.bankName
                pref.provider_address_on_bank = providerRegisterData.data.addressOnBank
            }).start()
                val intent = Intent(this, Dashboard::class.java)
                intent.putExtra("type", selectionTye)
                startActivity(intent)
                finish()

        }
    }




    internal val signinUserObserver: Observer<ModelUserSignin> = Observer<ModelUserSignin> { userData ->

        hideLoading()

        if(userData!!.status==Constant.response_Failure)
        {
            showMessage(userData.message)

        }
        else  if(userData!!.status==Constant.RESPONSE_SUCCESSFULLY){

            var fcmTokenLocal = pref.fcmToken

            val preferences = getSharedPreferences(AppPreferencesHelper.PREF_NAME, Context.MODE_PRIVATE);
            val editor = preferences.edit();
            editor.clear();
            editor.commit();

            showMessage(userData.message)
            val intent = Intent(this, Dashboard::class.java)
             pref.fcmToken = fcmTokenLocal;
            MethodToRegisterFCMToken(pref.fcmToken, userData.token , "user");

            Thread({
                pref.accountType= "user"
                pref.userP_fname = userData.data.pFname
                pref.userP_lname = userData.data.pLname
                pref.userUser_name = userData.data.userName
                pref.userP_email = userData.data.pEmail
                pref.userPswd = userData.data.pswd
                pref.userP_Prof_pic = userData.data.pProfPic
                pref.userP_gender = userData.data.pGender
                pref.userP_phone = userData.data.pPhone
                pref.userT_mobile = userData.data.tMobile
                pref.userP_econtact = userData.data.pEcontact
                pref.userP_dob = userData.data.pDob
                pref.userP_nickname = userData.data.pNickname
                pref.userP_address1 = userData.data.pAddress1
                pref.userP_address2 = userData.data.pAddress2
                pref.userP_country_id = userData.data.pCountryId
                pref.userP_state_id = userData.data.pStateId
                pref.userP_city = userData.data.pCity
                pref.userP_zip = userData.data.pZip
                pref.userP_timezone_id = userData.data.pTimezoneId
                pref.userIsEmailConfirm = userData.data.isEmailConfirm
                pref.userToken = userData.data.token
                pref.userFacebook_id = userData.data.facebookId
                pref.userGoogle_id = userData.data.googleId
                pref.userIs_active = userData.data.isActive
                pref.userIs_anonymous = userData.data.isAnonymous
                pref.prof_stage = userData.data.profStage
                pref.userP_status = userData.data.pStatus
                pref.userdevice_type = userData.data.deviceType
                pref.userdevice_id = userData.data.deviceId
                pref.userdevice_token = userData.data.deviceToken
                pref.userotp = userData.data.otp
                pref.userCreated_on = userData.data.createdOn
                pref.userUpdated_on = userData.data.updatedOn
                pref.userUpdated_by = userData.data.updatedBy
                pref.userBToken = userData.token
                pref.userPat_id = userData.data.patId
                pref.userAccount_id = userData.data.accountId
                pref.usercard_id = userData.data.cardId
                pref.useraccount_holder_name = userData.data.accountHolderName
                pref.useraccount_number = userData.data.accountNumber
                pref.userexp_month = userData.data.expMonth
                pref.userexp_year = userData.data.expYear
                pref.usercvv = userData.data.cvv
                pref.useraccount_token = userData.data.accountToken
                pref.userisLogout = userData.data.isLogout



            }).start()

            intent.putExtra("type", selectionTye)
            startActivity(intent)
            finish()
        }
    }



    // Validation check and hit to server
    fun onLoginClick(view : View){

       if(selectionTye.equals("provider")) {

           if (viewModel!!.validation(
                   signInBind!!.etEmailaddress.text.toString(),
                   signInBind!!.etPassword.text.toString()
               )
           ) {
               if (viewModel.emailValidation(signInBind!!.etEmailaddress.text.toString())) {
                   if (NetworkUtils.isNetworkConnected(this)) {
                       showLoading()
                       viewModel!!.signProvider(
                           signInBind!!.etEmailaddress.text.toString(),
                           signInBind!!.etPassword.text.toString()
                       )
                       hideKeyboard()
                       viewModel.getProvider()!!.observe(this, signinProviderObserver)


                   } else {
                       showMessage(getString(R.string.msg_check_internet))
                   }

               } else
                   showMessage(R.string.email_format)
           } else {
               showMessage(getString(R.string.msg_fill_login_data))
           }

       }

        else if(selectionTye.equals("user"))
       {
           if (viewModel!!.validation(
                   signInBind!!.etEmailaddress.text.toString(),
                   signInBind!!.etPassword.text.toString()))

           {

               if (viewModel.emailValidation(signInBind!!.etEmailaddress.text.toString())) {
                   if (NetworkUtils.isNetworkConnected(this)) {
                       showLoading()
                       viewModel!!.signUserOrpatients(
                           signInBind!!.etEmailaddress.text.toString(),
                           signInBind!!.etPassword.text.toString()
                       )
                       hideKeyboard()
                       viewModel.getUserOrPatients()!!.observe(this, signinUserObserver)


                   } else {
                       showMessage(getString(R.string.msg_check_internet))
                   }

               } else
                   showMessage(R.string.email_format)
           } else {
               showMessage(getString(R.string.msg_fill_login_data))
           }

       }
    }


   fun OnClickNewUser(view : View)
   {
      val intent = Intent(this, signup_activity::class.java)
      intent.putExtra("type",getIntent().getStringExtra("type"))

       startActivity(intent)
//       finish()
   }

    fun onClickForgotPassword(view : View)
    {
        val intent = Intent(this, ForgotPass::class.java)
        intent.putExtra("type",getIntent().getStringExtra("type"))
        intent.putExtra("activity","signin")
        startActivity(intent)
    }

    private fun MethodToRegisterFCMToken(fcmToken: String, deviceId: String , userType : String) {
        val apiService = ApiClient.getClient().create(WebService::class.java)
        val paramObject = HashMap<String, String>()
        paramObject.put("Content-Type", "application/json");
        paramObject["device_id"] = deviceId
        paramObject["device_token"] = fcmToken
        paramObject["device_type"] = Constant.deviceType
        paramObject["customer"] = userType

        val call = apiService.registerFCMToken(paramObject , "Bearer " + deviceId)
        showLoading()
        call.enqueue(object : retrofit2.Callback<ModelFCmTokenResponse> {
            override fun onResponse(call: Call<ModelFCmTokenResponse>, response: Response<ModelFCmTokenResponse>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        pref.fcmToken = fcmToken
                        hideLoading()

                    }else {
                        hideLoading()
                        showSnackBar("Some problem occur while fetching Token. Please try again!")
                    }
                }
            }

            override fun onFailure(call: Call<ModelFCmTokenResponse>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

}
