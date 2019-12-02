package com.amindset.ve.amindset.Splash

import android.Manifest
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.ShowLocation.ShowLocation
import com.amindset.ve.amindset.Splash.ModelFCM.ModelFCmTokenResponse
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.PreferencesHelper
import com.amindset.ve.amindset.databinding.ActivitySplashBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.sinch.android.rtc.Sinch
import com.sinch.android.rtc.SinchClient
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Inject


class splash : BaseActivity(), View.OnClickListener {

    @Inject
    lateinit var pref: PreferencesHelper
    lateinit var sinchClient: SinchClient

    override fun onClick(p0: View?) {

        if (p0!!.id == R.id.btn_started) {
            val intent = Intent(this, ShowLocation::class.java)
            startActivity(intent)
            finish()

            if (pref.providerT_mobile != null) {
                val context = this.applicationContext
             sinchClient = Sinch.getSinchClientBuilder().context(context)
            .applicationKey("ae6f9531-0a22-49dc-b8eb-c610179aa85c")
            .applicationSecret("nIcuV+wZPEafPIDJBknYDw==")
            .environmentHost("clientapi.sinch.com")
            .userId(pref.providerT_mobile )
            .build()
            sinchClient.setSupportCalling(true)
            sinchClient.startListeningOnActiveConnection()
             sinchClient.start()

            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        (application as AmidApp).getMyComponent().inject(this@splash)
        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.btnStarted.setOnClickListener(this)

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO
            )
            .check();

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)


                pref.fcmToken = token

//                MethodToRegisterFCMToken(token!!,CommonUtils.getDeviceId());
            })


    }


    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(this@splash, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(this@splash, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
        }


    }


    private fun MethodToRegisterFCMToken(fcmToken: String, deviceId: String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()


        paramObject["device_token"] = deviceId
        paramObject["token_fcm"] = fcmToken

        val call = apiService.registerFCMToken(paramObject, "")
        showLoading()
        call.enqueue(object : retrofit2.Callback<ModelFCmTokenResponse> {
            override fun onResponse(call: Call<ModelFCmTokenResponse>, response: Response<ModelFCmTokenResponse>?) {

                if (response != null) {
                    if (response.isSuccessful) {
                        pref.fcmToken = response.body().fcmToken as String?
                        hideLoading()

                    } else {
                        hideLoading()
                        showSnackBar("Some problem occur while fetching Token.Please try again!")
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
