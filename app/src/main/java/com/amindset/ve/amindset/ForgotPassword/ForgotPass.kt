package com.amindset.ve.amindset.ForgotPassword

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.OTP.EmailVerify
import com.amindset.ve.amindset.ForgotPassword.Model.ModelProviderForgotPassword
import com.amindset.ve.amindset.ForgotPassword.Model.ModelUserForgot.ModelUserForgotPassword
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.databinding.ActivityForgotPassBinding

class ForgotPass : BaseActivity() {

    lateinit var forgotPassBinding: ActivityForgotPassBinding

    lateinit var viewModelForgotPassword: ViewModelForgotPassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)

        forgotPassBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_pass)


        viewModelForgotPassword = ViewModelProviders.of(this).get(ViewModelForgotPassword::class.java)


    }


    fun onClickBackButton(view: View) {
        finish()
    }

    internal val forgotPasswordObserver: Observer<ModelProviderForgotPassword> = Observer<ModelProviderForgotPassword> { forgotPasswordData ->
        hideLoading()
        if(forgotPasswordData!!.status== Constant.response_Failure)
        {
            showMessage(getString(R.string.error_some_problem_occur))
        }
        else  if(forgotPasswordData.status== Constant.RESPONSE_SUCCESSFULLY){
            showMessage(forgotPasswordData.message)


            val intent: Intent = Intent(this, EmailVerify::class.java)
            intent.putExtra("type",getIntent().getStringExtra("type"))
            intent.putExtra("activity",getIntent().getStringExtra("activity"))
            intent.putExtra("providertokenB",forgotPasswordData.token)
            startActivity(intent)
            finish()
        }
    }



    internal val forgotUserPasswordObserver: Observer<ModelUserForgotPassword> = Observer<ModelUserForgotPassword> { forgotUserPasswordData ->
        hideLoading()
        if(forgotUserPasswordData!!.status== Constant.response_Failure)
        {
            showMessage(getString(R.string.error_some_problem_occur))
        }
        else  if(forgotUserPasswordData.status== Constant.RESPONSE_SUCCESSFULLY){

            showMessage(forgotUserPasswordData.message)

            val intent: Intent = Intent(this, EmailVerify::class.java)
            intent.putExtra("type",getIntent().getStringExtra("type"))
            intent.putExtra("activity",getIntent().getStringExtra("activity"))
            intent.putExtra("usertokenB",forgotUserPasswordData.token)
            startActivity(intent)
            finish()
        }
    }



    fun onClickSendEmail(@Suppress("UNUSED_PARAMETER")view: View) {

        if(intent.getStringExtra("type").equals("provider")) {
            if (viewModelForgotPassword.checkPhoneValidation(forgotPassBinding.etMobileNumber.text.toString())) {
                if (NetworkUtils.isNetworkConnected(this)) {
                    showLoading()
                    viewModelForgotPassword.methodProviderForgotPassword(forgotPassBinding.etMobileNumber.text.toString())
                    hideKeyboard()
                    viewModelForgotPassword.getProviderForgotPassword()!!.observe(this, forgotPasswordObserver)


                } else {
                    showMessage(getString(R.string.msg_check_internet))
                }


            } else {
                showMessage(getString(R.string.enter_email))
            }

        }
        else

        {
            if (viewModelForgotPassword.checkPhoneValidation(forgotPassBinding.etMobileNumber.text.toString())) {
                if (NetworkUtils.isNetworkConnected(this)) {
                    showLoading()
                    viewModelForgotPassword.methodForgotPassword(forgotPassBinding.etMobileNumber.text.toString())
                    hideKeyboard()
                    viewModelForgotPassword.getUserForgotPassword()!!.observe(this, forgotUserPasswordObserver)


                } else {
                    showMessage(getString(R.string.msg_check_internet))
                }


            } else {
                showMessage(getString(R.string.enter_email))
            }

        }
    }
}
