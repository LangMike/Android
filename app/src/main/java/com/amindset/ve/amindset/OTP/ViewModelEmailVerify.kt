package com.amindset.ve.amindset.OTP

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.text.TextUtils
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.UserRepository
import com.amindset.ve.amindset.OTP.UserModel.ModelUserOtpVerify
import com.amindset.ve.amindset.OTP.providerModel.ModelProviderOtpVerify
import com.amindset.ve.amindset.data.PreferencesHelper
import javax.inject.Inject


class ViewModelEmailVerify(application : Application) : AndroidViewModel(application)
{



    init {
        (application as AmidApp).getMyComponent().inject(this@ViewModelEmailVerify)
    }

    @Inject
    lateinit var userRepository : UserRepository

    private var userOTP: LiveData<ModelUserOtpVerify>? = null
    private var providerOTP: LiveData<ModelProviderOtpVerify>? = null

    fun codeValidation(firstDigit : String , secondDigit: String , thirdDigit : String , fourDigit : String) : Boolean
    {
        if(!TextUtils.isEmpty(firstDigit) && !TextUtils.isEmpty(secondDigit) &&
            !TextUtils.isEmpty(thirdDigit) && !TextUtils.isEmpty(fourDigit)  )

            return true

        return false
    }





    fun userOTPVerify(code1 : String  ,code2 : String,code3 : String, code4 : String ,bearedToken : String  )
    {
        userOTP= userRepository.userOTPverify(code1,code2,code3,code4,bearedToken);
    }

    fun getuserOTPVerifyObserver(): LiveData<ModelUserOtpVerify>? {
        return userOTP
    }


    fun providerOTPVerify(
        code1: String,
        code2: String,
        code3: String,
        code4: String,
        providertokenB: String
    )
    {
        providerOTP= userRepository.providerOTPverify(code1,code2,code3,code4,providertokenB);
    }

    fun getproviderOTPVerifyObserver(): LiveData<ModelProviderOtpVerify>? {
        return providerOTP
    }


//    fun signUserOrpatients(email : String , pass : String )
//    {
//        user= userRepository.getUser(email,pass);
//    }
//
//    fun getUserOrPatients(): LiveData<ModelUserSignin>? {
//        return user
//    }


}