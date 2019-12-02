package com.amindset.ve.amindset.Signin

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.text.TextUtils
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.UserRepository
import com.amindset.ve.amindset.Signin.DataModel.ProviderSignModel
import com.amindset.ve.amindset.Signin.DataModel.UserSigninModel.ModelUserSignin
import com.amindset.ve.amindset.Utils.CommonUtils
import javax.inject.Inject

class SignInViewModel(application : Application) : AndroidViewModel(application) {

    init {
        (application as AmidApp).getMyComponent().inject(this@SignInViewModel)
    }

     @Inject
     lateinit var userRepository : UserRepository

    private var provider: LiveData<ProviderSignModel>? = null
    private var user: LiveData<ModelUserSignin>? = null


    fun validation(email: String, pass: String): Boolean {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
            return true
        return false
    }

    fun emailValidation(email: String): Boolean {
        if (CommonUtils.isEmailValid(email))
            return true
        return true
    }


   fun signProvider(email : String , pass : String )
   {
       provider= userRepository.getProvider(email,pass);
   }

    fun getProvider(): LiveData<ProviderSignModel>? {
        return provider
    }


    fun signUserOrpatients(email : String , pass : String )
    {
        user= userRepository.getUser(email,pass);
    }

    fun getUserOrPatients(): LiveData<ModelUserSignin>? {
        return user
    }

}