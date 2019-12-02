package com.amindset.ve.amindset.NewPassword

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.NewPassword.Model.ModelUserResetPassword
import com.amindset.ve.amindset.NewPassword.Model.providermodel.ModelProviderResetPassword
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Signin.SignIn
import com.amindset.ve.amindset.data.PreferencesHelper
import com.amindset.ve.amindset.databinding.ActivityResetNewPasswordBinding
import javax.inject.Inject

class ResetNewPassword : BaseActivity() {

    var maintainEyeViewPass: Boolean = false
    var maintainEyeViewRePass: Boolean = false
    lateinit var resetNewPasswordBinding: ActivityResetNewPasswordBinding


    @Inject
    lateinit var  pref : PreferencesHelper

    lateinit var resetNewPasswordViewModel: ViewModelResetNewPassword
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetNewPasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_reset_new_password)
        (application as AmidApp).getMyComponent().inject(this@ResetNewPassword)

        resetNewPasswordBinding!!.etPassword.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= resetNewPasswordBinding!!.etPassword.getRight() - resetNewPasswordBinding!!.etPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                    // your action here

                    if (!maintainEyeViewPass) {
                        resetNewPasswordBinding!!.etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        resetNewPasswordBinding!!.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_on_login, 0
                        );
                        maintainEyeViewPass = true
                    } else {
//                        editText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        resetNewPasswordBinding!!.etPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        resetNewPasswordBinding!!.etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_off_login, 0
                        );
                        maintainEyeViewPass = false
                    }


                    return@OnTouchListener true
                }
            }
            false
        })


        resetNewPasswordBinding!!.etRepassword.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= resetNewPasswordBinding!!.etRepassword.getRight() - resetNewPasswordBinding!!.etRepassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                    // your action here

                    if (!maintainEyeViewRePass) {
                        resetNewPasswordBinding!!.etRepassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        resetNewPasswordBinding!!.etRepassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_on_login, 0
                        );
                        maintainEyeViewRePass = true
                    } else {
//                        editText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        resetNewPasswordBinding!!.etRepassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        resetNewPasswordBinding!!.etRepassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_off_login, 0
                        );
                        maintainEyeViewRePass = false
                    }


                    return@OnTouchListener true
                }
            }
            false
        })

        resetNewPasswordViewModel = ViewModelProviders.of(this).get(ViewModelResetNewPassword::class.java)

    }

    fun onSaveClick(view: View) {
        if (resetNewPasswordViewModel.isEmpty(
                resetNewPasswordBinding.etPassword.text.toString(),
                resetNewPasswordBinding.etRepassword.text.toString()
            )
        ) {

            if(resetNewPasswordViewModel.isPassworSame( resetNewPasswordBinding.etPassword.text.toString(),
                    resetNewPasswordBinding.etRepassword.text.toString())) {

                if(intent.getStringExtra("type").equals("user"))
                {
                    showLoading()
                    resetNewPasswordViewModel.userResetasssword( resetNewPasswordBinding.etPassword.text.toString())
                    hideKeyboard()
                    resetNewPasswordViewModel.getuserResetPasswordObserver()!!.observe(this, forgotUserPasswordObserver)
                }
                else
                {
                    showLoading()
                    resetNewPasswordViewModel.providerResetasssword( resetNewPasswordBinding.etPassword.text.toString() , pref)
                    hideKeyboard()
                    resetNewPasswordViewModel.getproviderResetPasswordObserver()!!.observe(this, forgotProviderPasswordObserver)

                }
            }
            else
                showSnackBar("Password don't match")
        } else {
            showMessage("Please fill all the data!")
        }

    }

    internal val forgotUserPasswordObserver: Observer<ModelUserResetPassword> = Observer<ModelUserResetPassword> { userResetPass ->
        hideLoading()
        if(userResetPass!!.status== Constant.response_Failure)
        {
            showMessage(userResetPass.message)
        }
        else  if(userResetPass!!.status== Constant.RESPONSE_SUCCESSFULLY){

            showMessage(userResetPass.message)
            val intent: Intent = Intent(this, SignIn::class.java)
            intent.putExtra("type",getIntent().getStringExtra("type"))
            startActivity(intent)
            finish()
        }
    }

    internal val forgotProviderPasswordObserver: Observer<ModelProviderResetPassword> = Observer<ModelProviderResetPassword> { providerResetPass ->
        hideLoading()
        if(providerResetPass!!.status== Constant.response_Failure)
        {
            showMessage(providerResetPass.message)
        }
        else  if(providerResetPass!!.status== Constant.RESPONSE_SUCCESSFULLY){

            showMessage(providerResetPass.message)

            val intent: Intent = Intent(this, SignIn::class.java)
            intent.putExtra("type",getIntent().getStringExtra("type"))
            startActivity(intent)
            finish()


        }
    }


}
