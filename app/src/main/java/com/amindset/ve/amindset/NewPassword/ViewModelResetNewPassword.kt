package com.amindset.ve.amindset.NewPassword

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.text.TextUtils
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.UserRepository
import com.amindset.ve.amindset.NewPassword.Model.ModelUserResetPassword
import com.amindset.ve.amindset.NewPassword.Model.providermodel.ModelProviderResetPassword
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.data.PreferencesHelper
import javax.inject.Inject

class ViewModelResetNewPassword (application : Application) : AndroidViewModel(application)
{


    init {
        (application as AmidApp).getMyComponent().inject(this@ViewModelResetNewPassword)
    }
    @Inject
    lateinit var userRepository : UserRepository


    private var userResetPassword: LiveData<ModelUserResetPassword>? = null
    private var providerResetPassword: LiveData<ModelProviderResetPassword>? = null



    fun  isEmpty(firstPassword : String , secondPassword : String   ) : Boolean
    {
       if(!TextUtils.isEmpty(firstPassword) && !TextUtils.isEmpty(secondPassword))
           return true
        return false
    }

    fun emailValidation(email: String): Boolean {
        if (CommonUtils.isEmailValid(email))
            return true
        return false
    }


    fun checkAgeValidation(age: Int) : String
    {
        if(age>100)
        {
            return "Invalid Age"
        }

        if(age < 0)
            return "Invalid Age"

        return ""
    }

    fun isPassworSame(password: String, repassword : String): Boolean {
        if(password.equals(repassword)){
            return true
        }
        return false
    }

     fun isValidMobile(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }


    fun userResetasssword(password : String )
    {
        userResetPassword= userRepository.userResetPassword(password);
    }

    fun getuserResetPasswordObserver(): LiveData<ModelUserResetPassword>? {
        return userResetPassword
    }


    fun providerResetasssword(password: String, pref: PreferencesHelper)
    {
        providerResetPassword= userRepository.providerResetPassword(password,pref);
    }

    fun getproviderResetPasswordObserver(): LiveData<ModelProviderResetPassword>? {
        return providerResetPassword
    }


}