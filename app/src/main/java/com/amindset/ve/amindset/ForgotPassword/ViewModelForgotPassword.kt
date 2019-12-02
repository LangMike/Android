package com.amindset.ve.amindset.ForgotPassword

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.text.TextUtils
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.UserRepository
import com.amindset.ve.amindset.ForgotPassword.Model.ModelProviderForgotPassword
import com.amindset.ve.amindset.ForgotPassword.Model.ModelUserForgot.ModelUserForgotPassword
import com.amindset.ve.amindset.Utils.CommonUtils
import javax.inject.Inject


class ViewModelForgotPassword(application : Application) : AndroidViewModel(application)
{


    init {
        (application as AmidApp).getMyComponent().inject(this@ViewModelForgotPassword)
    }


    @Inject
    lateinit var userRepository : UserRepository

    private var provider: LiveData<ModelProviderForgotPassword>? = null
    private var user: LiveData<ModelUserForgotPassword>? = null


    fun  checkPhoneValidation(email : String) : Boolean
    {
        if(!TextUtils.isEmpty(email))
            return true
        return false
    }

    fun isEmailValid(email: String) : Boolean
    {
        if(CommonUtils.isEmailValid(email))
            return true
        return false
    }



    fun methodProviderForgotPassword(mobileNumber : String  )
    {
        provider= userRepository.providerForgotPassword(mobileNumber);
    }

    fun getProviderForgotPassword(): LiveData<ModelProviderForgotPassword>? {
        return provider
    }



    fun methodForgotPassword(mobileNumber : String  )
    {
        user= userRepository.userForgotPassword(mobileNumber);
    }

    fun getUserForgotPassword(): LiveData<ModelUserForgotPassword>? {
        return user
    }
}