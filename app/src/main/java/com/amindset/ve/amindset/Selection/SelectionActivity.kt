package com.amindset.ve.amindset.Selection

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.Html
import android.view.View
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.SignUp.signup_activity
import com.amindset.ve.amindset.Signin.SignIn
import com.amindset.ve.amindset.databinding.ActivitySelectionBinding
import com.sinch.android.rtc.Sinch
import com.sinch.android.rtc.SinchClient
import com.sinch.android.rtc.calling.CallClientListener


class SelectionActivity : BaseActivity() {



    var selctionActivityBinding : ActivitySelectionBinding? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        selctionActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_selection)

        val second : String= "Create an acount as a "
        val third : String= " or"
        val next : String = "<font color='#2128d9'> Provider</font>"
        val last : String = "<font color='#2128d9'> Sign in </font>"

        selctionActivityBinding!!.joinasa.setText(Html.fromHtml( second ))

        selctionActivityBinding!!.userprovider.setText(
            Html.fromHtml( next +  third+last))
        hideKeyboard()
    }


    fun OnclickProviderSignup(view : View)
    {
            val intent = Intent(this, signup_activity::class.java)
            intent.putExtra("type","provider")
            startActivity(intent)
    }


    fun onClickSignUp(view : View)
    {
            val intent = Intent(this, signup_activity::class.java)
            intent.putExtra("type", "user")
            startActivity(intent)

    }

    fun onClickSignIn(view :View)
    {

        val intent = Intent(this, SignIn::class.java)
        intent.putExtra("type","user")
        startActivity(intent)
    }

    fun onClickUserProvider(view :View)
    {
        val intent = Intent(this, SignIn::class.java)
        intent.putExtra("type","provider")
        startActivity(intent)
    }

}
