package com.amindset.ve.amindset.OTP

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.Editable
import android.view.View
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.databinding.ActivityEmailVerifyBinding
import android.text.TextWatcher
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Dashboard.Dashboard
import com.amindset.ve.amindset.NewPassword.ResetNewPassword
import com.amindset.ve.amindset.OTP.UserModel.ModelUserOtpVerify
import com.amindset.ve.amindset.OTP.providerModel.ModelProviderOtpVerify
import com.amindset.ve.amindset.ProfessionalVerification.ProfessionalVerification
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.data.PreferencesHelper
import javax.inject.Inject


class EmailVerify : BaseActivity() {


    //Declare varaibles
    lateinit var emailVerifyBinding: ActivityEmailVerifyBinding
    lateinit var viewModelEmailVerify: ViewModelEmailVerify

    @Inject
    lateinit var  pref : PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verify)

        emailVerifyBinding = DataBindingUtil.setContentView(this,R.layout.activity_email_verify)

        (application as AmidApp).getMyComponent().inject(this@EmailVerify)


        emailVerifyBinding.etVerficode1.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if( emailVerifyBinding.etVerficode1.text.toString().length==1)     //size as per your requirement
                {
                    emailVerifyBinding.etVerficode2.requestFocus();
                }
            }
        })

        emailVerifyBinding.etVerficode2.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if( emailVerifyBinding.etVerficode2.text.toString().length==1)     //size as per your requirement
                {
                    emailVerifyBinding.etVerficode3.requestFocus();
                }
            }
        })


        emailVerifyBinding.etVerficode3.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if( emailVerifyBinding.etVerficode3.text.toString().length==1)     //size as per your requirement
                {
                    emailVerifyBinding.etVerficode4.requestFocus();
                }
            }
        })


        viewModelEmailVerify = ViewModelProviders.of(this).get(ViewModelEmailVerify::class.java)




    }
    fun onClickBackButton (view :View)
    {
        finish()
    }

    // Click on Send email button
    fun onClickSendEmail(view : View)
    {


        if(viewModelEmailVerify.codeValidation(emailVerifyBinding.etVerficode1.text.toString(),
                emailVerifyBinding.etVerficode2.text.toString(),
                emailVerifyBinding.etVerficode3.text.toString(),
                emailVerifyBinding.etVerficode4.text.toString()))

        {

             if(intent.getStringExtra("type").equals("user"))
             {
                 if (NetworkUtils.isNetworkConnected(this)) {
                     showLoading()
                     viewModelEmailVerify!!.userOTPVerify(emailVerifyBinding.etVerficode1.text.toString(),
                         emailVerifyBinding.etVerficode2.text.toString(),
                         emailVerifyBinding.etVerficode3.text.toString(),
                         emailVerifyBinding.etVerficode4.text.toString(),
                        intent.getStringExtra("usertokenB"))
                     hideKeyboard()
                     viewModelEmailVerify.getuserOTPVerifyObserver()!!.observe(this, userOTPVerifyObserver)


                 } else {
                     showMessage(getString(R.string.msg_check_internet))
                 }
             }
            else
             {
                 if (NetworkUtils.isNetworkConnected(this)) {
                     showLoading()
                     viewModelEmailVerify!!.providerOTPVerify(emailVerifyBinding.etVerficode1.text.toString(),
                         emailVerifyBinding.etVerficode2.text.toString(),
                         emailVerifyBinding.etVerficode3.text.toString(),
                         emailVerifyBinding.etVerficode4.text.toString(),
                         intent.getStringExtra("providertokenB"))
                     hideKeyboard()
                     viewModelEmailVerify.getproviderOTPVerifyObserver()!!.observe(this, providerOTPVerifyObserver)


                 } else {
                     showMessage(getString(R.string.msg_check_internet))
                 }

             }
         }

        else
        {
            showMessage("Please enter all the digits!")
        }
    }


    internal val userOTPVerifyObserver: Observer<ModelUserOtpVerify> = Observer<ModelUserOtpVerify> { userOTPVerify ->
        hideLoading()
        if(userOTPVerify!!.status== Constant.response_Failure)
        {
            showMessage(userOTPVerify.message)
        }
        else  if(userOTPVerify!!.status== Constant.RESPONSE_SUCCESSFULLY){

            showMessage(userOTPVerify.message)
        }


        if(intent.getStringExtra("activity").equals("signin")) {
            val intent: Intent = Intent(this, ResetNewPassword::class.java)
            intent.putExtra("type", getIntent().getStringExtra("type"))
            startActivity(intent)

            finish()
        }
        else
        {
            val intent: Intent = Intent(this, Dashboard::class.java)
            intent.putExtra("type", getIntent().getStringExtra("type"))
            startActivity(intent)

            finish()
        }

    }


    internal val providerOTPVerifyObserver: Observer<ModelProviderOtpVerify> = Observer<ModelProviderOtpVerify> { providerOTPVerify ->
        hideLoading()

        if(providerOTPVerify!!.status== Constant.response_Failure)
        {
            showMessage(providerOTPVerify.message)

        }
        else  if(providerOTPVerify!!.status== Constant.RESPONSE_SUCCESSFULLY){

            showMessage(providerOTPVerify.message)

            if(intent.getStringExtra("activity").equals("signin")) {
                val intent: Intent = Intent(this, ResetNewPassword::class.java)
                intent.putExtra("type", getIntent().getStringExtra("type"))
                startActivity(intent)
                intent.getStringExtra("providertokenB")
                finish()
            }
            else
            {
                val intent: Intent = Intent(this, ProfessionalVerification::class.java)
                intent.putExtra("type", getIntent().getStringExtra("type"))
                startActivity(intent)
                finish()
            }

        }
    }


}
