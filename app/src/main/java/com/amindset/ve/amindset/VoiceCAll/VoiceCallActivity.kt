package com.amindset.ve.amindset.VoiceCAll
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.CallCost.CallDetails
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.VdoCall.ModelAccessTokenVDOcall.ModelAccessTokenVDO
import com.amindset.ve.amindset.VdoCall.ModelNotificationData.ModelTwilloNotificationUserData
import com.amindset.ve.amindset.VdoCall.vdocallViewModel
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import com.sinch.android.rtc.*
import com.sinch.android.rtc.calling.Call
import com.sinch.android.rtc.calling.CallClient
import com.sinch.android.rtc.calling.CallClientListener
import com.sinch.android.rtc.calling.CallListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_voice_call.*
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class VoiceCallActivity : BaseActivity(),CallListener, CallClientListener  {


    lateinit var  pref : AppPreferencesHelper
    var roomNameprovider : String = ""
    lateinit var token : String
    lateinit var vdoViewModel: vdocallViewModel
    var incomingCall: Call? = null
    var fromCall: Boolean = false
    var isCallValid: Boolean = false
    lateinit var startTime :String
    lateinit var disconnectTime :String

    override fun onIncomingCall(p0: CallClient?, incomingCall: Call?) {
        this.incomingCall = incomingCall
        hideLoading()
        incomingCall!!.addCallListener(this@VoiceCallActivity)
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
       val  r = RingtoneManager.getRingtone(getApplicationContext(), notification)
        r.play()

        object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished:Long) {
                //here you can have your logic to set text to edittext
            }
            override fun onFinish() {

                if(iv_accept.visibility==View.VISIBLE && iv_reject.visibility == View.VISIBLE)
                {
                    finish()
                    if(r!=null)
                        r.stop()

                }

            }
        }.start()
    }




    override fun onCallEstablished(p0: Call?) {
        if(fromCall)
        {

            group_accept_reject.visibility = View.GONE
            group_cut.visibility = View.VISIBLE
        }
        isCallValid=true;
        caller_status.setText("Connected")
        chrono_meter.visibility=View.VISIBLE
        chrono_meter.setBase(SystemClock.elapsedRealtime());
        caller_status.visibility=View.GONE
        chrono_meter.start()
        val sdf = SimpleDateFormat("hh:mm:ss aa")
        startTime = sdf.format(Date())
        volumeControlStream = AudioManager.STREAM_VOICE_CALL

    }

    override fun onCallProgressing(p0: Call?) {
        caller_status.setText("Ringing/Connecting...")

    }

    override fun onShouldSendPushNotification(p0: Call?, p1: MutableList<PushPair>?) {

    }

    override fun onCallEnded(endedCall: Call?) {
        caller_status.setText("Disconnected")
        showMessage("Call Disconnected")
        sinchClient.stopListeningOnActiveConnection();
        sinchClient.terminate();
        incomingCall = null
        volumeControlStream = AudioManager.USE_DEFAULT_STREAM_TYPE
        val sdf = SimpleDateFormat("hh:mm:ss aa")
        disconnectTime = sdf.format(Date())

        if(isCallValid && !fromCall)
        {
            val intent11 = Intent(this, CallDetails::class.java)
            intent11.putExtra("disconnectTime",""+disconnectTime)
            intent11.putExtra("startTime",""+startTime)
            intent11.putExtra("providerImage",intent.getStringExtra("providerImage"))
            intent11.putExtra("providerName",intent.getStringExtra("providerName"))
            intent11.putExtra("rate",intent.getStringExtra("rate"))
            intent11.putExtra("type", intent.getStringExtra("type"))
            intent11.putExtra("t_id", intent.getStringExtra("t_id"))
            intent11.putExtra("callFrom", "activity")
            startActivity(intent11)
            finish()
        }

        else {

            if (isCallValid)
            {
            val intent11 = Intent(this, CallDetails::class.java)
            intent11.putExtra("pat_id", intent.getStringExtra("pat_id"))
            intent11.putExtra("callFrom", "notofication")
            startActivity(intent11)
            finish()
        }
        else
        {
            finish()
        }
        }
    }

    lateinit var sinchClient: SinchClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_call)


        vdoViewModel = ViewModelProviders.of(this).get(vdocallViewModel::class.java)
        (application as AmidApp).getMyComponent().inject(this@VoiceCallActivity)

        pref = AppPreferencesHelper(this)

        val context = this.applicationContext

        sinchClient = Sinch.getSinchClientBuilder().context(context)
            .applicationKey("ae6f9531-0a22-49dc-b8eb-c610179aa85c")
            .applicationSecret("nIcuV+wZPEafPIDJBknYDw==")
            .environmentHost("clientapi.sinch.com")
            .userId(intent.getStringExtra("callerId"))
            .build()
        sinchClient.setSupportCalling(true)
        sinchClient.startListeningOnActiveConnection()
        sinchClient.start()
        sinchClient.callClient.addCallClientListener(this@VoiceCallActivity)


        if(intent.getStringExtra("roomName")!=null)
        {
            showLoading()

            fromCall=true
            iv_reject.visibility = View.VISIBLE
            iv_accept.visibility = View.VISIBLE
            tv_reject.visibility = View.VISIBLE
            tv_accept.visibility = View.VISIBLE
            if(intent.getStringExtra("userName")!=null)
            {
                provider_name.setText(intent.getStringExtra("userName"))
            }
            if(intent.getStringExtra("userPicUrl")!=null)
            {
                Picasso.get().load(intent.getStringExtra("userPicUrl")).into(profile_image_to_caller)
            }

        }
        else {

            group_accept_reject.visibility = View.GONE
            if(!TextUtils.isEmpty(intent.getStringExtra("roomNameprovider")))
            {
                roomNameprovider = intent.getStringExtra("roomNameprovider")
            }

            if(!TextUtils.isEmpty(intent.getStringExtra("providerImage")))
            {
                Picasso.get().load(intent.getStringExtra("providerImage")!!).into(profile_image_to_caller)

            }

            if(!TextUtils.isEmpty(intent.getStringExtra("providerName")))
            {
                provider_name.setText(intent.getStringExtra("providerName"))

            }


            group_cut.visibility = View.VISIBLE

            Handler().postDelayed({
                if (NetworkUtils.isNetworkConnected(this@VoiceCallActivity)) {
                    showLoading()
                    MethodToGetAccessToken(pref.userToken, pref.userP_phone, roomNameprovider)
                    hideKeyboard()
                } else {
                    showSnackBar(getString(R.string.msg_check_internet))
                }

            }, 2000)

        }
    }

    fun ONClickCall(@Suppress("UNUSED_PARAMETER")view:View)
    {
        if(incomingCall!=null) {
            incomingCall!!.hangup()
            incomingCall=null
        }
    }

    fun onClickAccept(@Suppress("UNUSED_PARAMETER")view:View)
    {
        if(incomingCall!=null) {
            incomingCall!!.answer()
        }

    }

    fun ONClickReject(@Suppress("UNUSED_PARAMETER")view:View)
    {
        if(incomingCall!=null)
            incomingCall!!.hangup()
        else
            finish()
    }

    private fun MethodToGetAccessToken(deviceId: String ,phone : String, roomName : String) {
        val apiService = ApiClient.getClient().create(WebService::class.java)
        val paramObject = HashMap<String, String>()
        paramObject["device_id"] = deviceId
        paramObject["mobile_number"] = phone
        paramObject["room_name"] =roomName

        val call = apiService.getAccessTokenVDO(paramObject)
        showLoading()
        call.enqueue(object : retrofit2.Callback<ModelAccessTokenVDO> {
            override fun onResponse(call: retrofit2.Call<ModelAccessTokenVDO>, response: Response<ModelAccessTokenVDO>?) {

                if (response != null) {

                    if(response.body().status == Constant.RESPONSE_SUCCESSFULLY)
                    {
                        hideLoading()
                        token=  response.body().data.token

                        if ( response.body().data.token !=null && ! response.body().data.token.equals("")) {
                            if (NetworkUtils.isNetworkConnected(this@VoiceCallActivity)) {
                                showLoading()
                                MethodToSenNotification("audio",
                                    pref.userBToken, roomNameprovider,
                                    pref.userP_Prof_pic, pref.userP_phone, roomNameprovider, pref.userP_fname,pref.userPat_id)
                            } else {
                                showSnackBar(getString(R.string.msg_check_internet))
                            }
                        }
                        else
                        {
                            showMessage(getString(R.string.error_some_problem_occur))
                        }

                    }
                }
            }



            override fun onFailure(call: retrofit2.Call<ModelAccessTokenVDO>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

//    "audio",
//    pref.userBToken, roomNameprovider,
//    pref.userP_Prof_pic, pref.userP_phone, roomNameprovider, pref.userP_fname
    private fun MethodToSenNotification(calltype: String ,bearerToken : String, roomName : String,
                                        userProfilePic : String,userContactNumber :
String,providerNumber : String,userName : String,pat_id : String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)
        val paramObject = HashMap<String, String>()
        paramObject["my_number"] = userContactNumber
        paramObject["my_profile_pic_url"] = userProfilePic
        paramObject["roomName"] = roomName
        paramObject["to_number"] = providerNumber
        paramObject["customer"] = "provider"
        paramObject["type"] = calltype
        paramObject["userName"] = "" + userName
        paramObject["patient_id"] =  pat_id

        val call = apiService.postPushNotification(paramObject,"Bearer " + bearerToken)
        showLoading()
        call.enqueue(object : retrofit2.Callback<ModelTwilloNotificationUserData> {
            override fun onResponse(call: retrofit2.Call<ModelTwilloNotificationUserData>, response: Response<ModelTwilloNotificationUserData>?) {

                if (response != null) {

                    hideLoading()

                    if(response.body().status== Constant.response_Failure)
                    {
                        showMessage(getString(R.string.error_some_problem_occur))
                    }
                    else  if(response.body().status== Constant.RESPONSE_SUCCESSFULLY){
                        Handler().postDelayed({
                            val callClient = sinchClient.callClient
                            incomingCall = callClient.callUser(roomNameprovider)
                            incomingCall!!.addCallListener(this@VoiceCallActivity)
                        }, 5000)




                    }
                }
            }



            override fun onFailure(call: retrofit2.Call<ModelTwilloNotificationUserData>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


}
