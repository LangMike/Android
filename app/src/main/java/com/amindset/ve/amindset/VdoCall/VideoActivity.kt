package com.amindset.ve.amindset.VdoCall

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.BuildConfig
import com.amindset.ve.amindset.CallCost.CallDetails
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.CounselorQuestionAnswerAdapter
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.CommonDialog
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.VdoCall.ModelAccessTokenVDOcall.ModelAccessTokenVDO
import com.amindset.ve.amindset.VdoCall.ModelNotificationData.ModelTwilloNotificationUserData
import com.amindset.ve.amindset.VdoCall.ProviderQuestionAndAnswer.Info
import com.amindset.ve.amindset.VdoCall.ProviderQuestionAndAnswer.ProviderGetQuestionAndAnswer
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import com.squareup.picasso.Picasso
import com.twilio.video.*
import com.twilio.video.VideoView
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.content_video.*
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class VideoActivity : BaseActivity(), View.OnClickListener {

    private val CAMERA_MIC_PERMISSION_REQUEST_CODE = 1
    private val TAG = "VideoActivity"

    /*
     * You must provide a Twilio Access Token to connect to the Video service
     */
    private val TWILIO_ACCESS_TOKEN = BuildConfig.TWILIO_ACCESS_TOKEN
    private val ACCESS_TOKEN_SERVER = BuildConfig.TWILIO_ACCESS_TOKEN_SERVER

    /*
     * Access token used to connect. This field will be set either from the console generated token
     * or the request to the token server.
     */
    private lateinit var accessToken: String
    var infoList = ArrayList<Info>()
    lateinit  var mAdapter: ReportQuestionAnswerAdapter
    lateinit var  mLayoutManager : LinearLayoutManager

    /*
     * A Room represents communication between a local participant and one or more participants.
     */
    private var room: Room? = null
    private var localParticipant: LocalParticipant? = null


    /*
     * AudioCodec and VideoCodec represent the preferred codec for encoding and decoding audio and
     * video.
     */
    private val audioCodec: AudioCodec
        get() {
            val audioCodecName = sharedPreferences.getString(SettingsActivity.PREF_AUDIO_CODEC,
                    SettingsActivity.PREF_AUDIO_CODEC_DEFAULT)

            return when (audioCodecName) {
                IsacCodec.NAME -> IsacCodec()
                OpusCodec.NAME -> OpusCodec()
                PcmaCodec.NAME -> PcmaCodec()
                PcmuCodec.NAME -> PcmuCodec()
                G722Codec.NAME -> G722Codec()
                else -> OpusCodec()
            }
        }
    private val videoCodec: VideoCodec
        get() {
            val videoCodecName = sharedPreferences.getString(SettingsActivity.PREF_VIDEO_CODEC,
                    SettingsActivity.PREF_VIDEO_CODEC_DEFAULT)

            return when (videoCodecName) {
                Vp8Codec.NAME -> {
                    val simulcast = sharedPreferences.getBoolean(
                            SettingsActivity.PREF_VP8_SIMULCAST,
                            SettingsActivity.PREF_VP8_SIMULCAST_DEFAULT)
                    Vp8Codec(simulcast)
                }
                H264Codec.NAME -> H264Codec()
                Vp9Codec.NAME -> Vp9Codec()
                else -> Vp8Codec()
            }
        }

    /*
     * Encoding parameters represent the sender side bandwidth constraints.
     */
    private val encodingParameters: EncodingParameters
        get() {
            val maxAudioBitrate = Integer.parseInt(
                    sharedPreferences.getString(SettingsActivity.PREF_SENDER_MAX_AUDIO_BITRATE,
                            SettingsActivity.PREF_SENDER_MAX_AUDIO_BITRATE_DEFAULT))
            val maxVideoBitrate = Integer.parseInt(
                    sharedPreferences.getString(SettingsActivity.PREF_SENDER_MAX_VIDEO_BITRATE,
                            SettingsActivity.PREF_SENDER_MAX_VIDEO_BITRATE_DEFAULT))

            return EncodingParameters(maxAudioBitrate, maxVideoBitrate)
        }

    /*
     * Room events listener
     */
    private val roomListener = object : Room.Listener {

        override fun onConnected(room: Room) {
            localParticipant = room.localParticipant
            videoStatusTextView.text = "Ringing..."
            title = room.name


//           handleUiFromNotification()
            // Only one participant is supported
            room.remoteParticipants?.firstOrNull()?.let { addRemoteParticipant(it) }
        }

        override fun onReconnected(room: Room) {
            videoStatusTextView.text = "Connected "
            reconnectingProgressBar.visibility = View.GONE;
        }

        override fun onReconnecting(room: Room, twilioException: TwilioException) {
            videoStatusTextView.text = "Reconnecting "
            reconnectingProgressBar.visibility = View.VISIBLE;
        }

        override fun onConnectFailure(room: Room, e: TwilioException) {
            videoStatusTextView.text = "Failed to connect"
            configureAudio(false)
            initializeUI()
        }

        override fun onDisconnected(room: Room, e: TwilioException?) {
            localParticipant = null
            videoStatusTextView.text = "Disconnected"
            chrono_meter.stop()

            reconnectingProgressBar.visibility = View.GONE;
            this@VideoActivity.room = null

            if (!disconnectedFromOnDestroy) {
                configureAudio(false)
                initializeUI()
                moveLocalVideoToPrimaryView()
            }
        }

        override fun onParticipantConnected(room: Room, participant: RemoteParticipant) {
            addRemoteParticipant(participant)
        }

        override fun onParticipantDisconnected(room: Room, participant: RemoteParticipant) {
            removeRemoteParticipant(participant)
        }

        override fun onRecordingStarted(room: Room) {
            /*
             * Indicates when media shared to a Room is being recorded. Note that
             * recording is only available in our Group Rooms developer preview.
             */
        }

        override fun onRecordingStopped(room: Room) {
            /*
             * Indicates when media shared to a Room is no longer being recorded. Note that
             * recording is only available in our Group Rooms developer preview.
             */
        }
    }

    private fun handleUiFromNotification() {
        content_video.visibility = View.VISIBLE
        rl_header_dialog.visibility = View.VISIBLE
        profile_image.visibility = View.GONE

//        fl_accept.visibility = View.GONE
//        fl_reject.visibility = View.GONE

        user_name.visibility = View.GONE

        tv_reject.visibility = View.GONE
        tv_accept.visibility = View.GONE

        fl_finish_call.visibility  = View.VISIBLE
    }

    /*
     * RemoteParticipant events listener
     */
    private val participantListener = object : RemoteParticipant.Listener {

        override fun onAudioTrackPublished(remoteParticipant: RemoteParticipant,
                                           remoteAudioTrackPublication: RemoteAudioTrackPublication) {
                 videoStatusTextView.text = "onAudioTrackAdded"
        }

        override fun onAudioTrackUnpublished(remoteParticipant: RemoteParticipant,
                                             remoteAudioTrackPublication: RemoteAudioTrackPublication) {
            videoStatusTextView.text = "onAudioTrackRemoved"
        }

        override fun onDataTrackPublished(remoteParticipant: RemoteParticipant,
                                          remoteDataTrackPublication: RemoteDataTrackPublication) {
            videoStatusTextView.text = "onDataTrackPublished"
        }

        override fun onDataTrackUnpublished(remoteParticipant: RemoteParticipant,
                                            remoteDataTrackPublication: RemoteDataTrackPublication) {
            videoStatusTextView.text = "Disconnected"


        }

        override fun onVideoTrackPublished(remoteParticipant: RemoteParticipant,
                                           remoteVideoTrackPublication: RemoteVideoTrackPublication) {
            videoStatusTextView.text = "onVideoTrackPublished"
        }

        override fun onVideoTrackUnpublished(remoteParticipant: RemoteParticipant,
                                             remoteVideoTrackPublication: RemoteVideoTrackPublication) {
            videoStatusTextView.text = "onVideoTrackUnpublished"
        }

        override fun onAudioTrackSubscribed(remoteParticipant: RemoteParticipant,
                                            remoteAudioTrackPublication: RemoteAudioTrackPublication,
                                            remoteAudioTrack: RemoteAudioTrack) {
        }

        override fun onAudioTrackUnsubscribed(remoteParticipant: RemoteParticipant,
                                              remoteAudioTrackPublication: RemoteAudioTrackPublication,
                                              remoteAudioTrack: RemoteAudioTrack) {
                 videoStatusTextView.text = "onAudioTrackUnsubscribed"
        }

        override fun onAudioTrackSubscriptionFailed(remoteParticipant: RemoteParticipant,
                                                    remoteAudioTrackPublication: RemoteAudioTrackPublication,
                                                    twilioException: TwilioException) {
            videoStatusTextView.text = "onAudioTrackSubscriptionFailed"
        }

        override fun onDataTrackSubscribed(remoteParticipant: RemoteParticipant,
                                           remoteDataTrackPublication: RemoteDataTrackPublication,
                                           remoteDataTrack: RemoteDataTrack) {
            videoStatusTextView.text = "onDataTrackSubscribed"
        }

        override fun onDataTrackUnsubscribed(remoteParticipant: RemoteParticipant,
                                             remoteDataTrackPublication: RemoteDataTrackPublication,
                                             remoteDataTrack: RemoteDataTrack) {
            videoStatusTextView.text = "onDataTrackUnsubscribed"
        }

        override fun onDataTrackSubscriptionFailed(remoteParticipant: RemoteParticipant,
                                                    remoteDataTrackPublication: RemoteDataTrackPublication,
                                                    twilioException: TwilioException) {
            videoStatusTextView.text = "onDataTrackSubscriptionFailed"
        }

        override fun onVideoTrackSubscribed(remoteParticipant: RemoteParticipant,
                                            remoteVideoTrackPublication: RemoteVideoTrackPublication,
                                            remoteVideoTrack: RemoteVideoTrack) {

            addRemoteParticipantVideo(remoteVideoTrack)
        }

        override fun onVideoTrackUnsubscribed(remoteParticipant: RemoteParticipant,
                                          remoteVideoTrackPublication: RemoteVideoTrackPublication,
                                              remoteVideoTrack: RemoteVideoTrack) {
            videoStatusTextView.text = "Disconnected"
            val sdf = SimpleDateFormat("hh:mm:ss aa")
            disconnectTime = sdf.format(Date())
            val intent1 = Intent(this@VideoActivity, CallDetails::class.java)
            intent1.putExtra("disconnectTime",""+disconnectTime)
            intent1.putExtra("startTime",""+startTime)
            intent1.putExtra("providerImage",providerImageVDO)
            intent1.putExtra("providerName",providerNameVDO)
            intent1.putExtra("rate",intent.getStringExtra("rate"))
            intent1.putExtra("type", intent.getStringExtra("type"))
            intent1.putExtra("t_id", intent.getStringExtra("t_id"))
            intent1.putExtra("pat_id", ""+intent.getStringExtra("pat_id"))

            intent1.putExtra("callFrom", ""+abc)

            startActivity(intent1)
            finish()

            removeParticipantVideo(remoteVideoTrack)
        }

        override fun onVideoTrackSubscriptionFailed(remoteParticipant: RemoteParticipant,
                                                    remoteVideoTrackPublication: RemoteVideoTrackPublication,
                                                    twilioException: TwilioException) {
            videoStatusTextView.text = "onVideoTrackSubscriptionFailed"
            Snackbar.make(connectActionFab,
                    "Failed to subscribe to ${remoteParticipant.identity}",
                    Snackbar.LENGTH_LONG)
                    .show()
        }

        override fun onAudioTrackEnabled(remoteParticipant: RemoteParticipant,
                                         remoteAudioTrackPublication: RemoteAudioTrackPublication) {
        }

        override fun onVideoTrackEnabled(remoteParticipant: RemoteParticipant,
                                         remoteVideoTrackPublication: RemoteVideoTrackPublication) {
        }

        override fun onVideoTrackDisabled(remoteParticipant: RemoteParticipant,
                                          remoteVideoTrackPublication: RemoteVideoTrackPublication) {
        }

        override fun onAudioTrackDisabled(remoteParticipant: RemoteParticipant,
                                          remoteAudioTrackPublication: RemoteAudioTrackPublication) {
        }
    }

    private var localAudioTrack: LocalAudioTrack? = null
    private var localVideoTrack: LocalVideoTrack? = null
    private var alertDialog: android.support.v7.app.AlertDialog? = null
    private val cameraCapturerCompat by lazy {
        CameraCapturerCompat(this, getAvailableCameraSource())
    }
    private val sharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this@VideoActivity)
    }
    private val audioManager by lazy {
        this@VideoActivity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private var participantIdentity: String? = null

    private var previousAudioMode = 0
    private var previousMicrophoneMute = false
    private lateinit var localVideoView: VideoRenderer
    private var disconnectedFromOnDestroy = false
    private var isSpeakerPhoneEnabled = true

    lateinit var vdoViewModel: vdocallViewModel


    lateinit var  connectActionFab : FloatingActionButton
    lateinit var  fl_finish_call : ImageView

    lateinit var  ll_reject : LinearLayout
    lateinit var  ll_accept : LinearLayout
    lateinit var  profile_image : ImageView
    lateinit var  rl_header_dialog : RelativeLayout
    lateinit var  content_video : View


    lateinit var  user_name : TextView
    lateinit var  tv_reject : TextView
    lateinit var  tv_accept : TextView
    lateinit var  videoStatusTextView : TextView

    lateinit var rv : RecyclerView


    lateinit var primaryVideoView : VideoView
    lateinit var callerName : TextView
    lateinit var iv_cut : TextView

    lateinit var thumbnailVideoView : VideoView
    lateinit var profile_image_to_caller : ImageView
    lateinit var chrono_meter : Chronometer
    lateinit var qanswer : Button

    lateinit var roomNameprovider : String
    lateinit var providerNameVDO : String
    lateinit var providerImageVDO : String
    lateinit var dialogEmergency : Dialog
    lateinit var startTime :String
    lateinit var disconnectTime :String
    lateinit var pro_Id :String


    private var abc = false

    lateinit var calltype: String


    lateinit var  pref : AppPreferencesHelper




    override fun onClick(p0: View?) {

        when(p0!!.id)
        {
//            R.id.ll_accept->{
//                if(accessToken!=null && !accessToken.equals("")) {
//                    connectToRoom(intent.getStringExtra("roomName"));
//                }
//            }
//
//            R.id.fl_reject->{
//            }
//
//            R.id.fl_finish_call->{
//
//            }
        }
    }

    private fun showQAnswerDialog() {
        dialogEmergency  =  Dialog(this);
        dialogEmergency.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEmergency.setContentView(R.layout.dialog_question_nd_answer);
        rv = dialogEmergency.findViewById(R.id.rv_quesand_answer)
        dialogEmergency.show();
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)


        pref = AppPreferencesHelper(this)
        ll_reject = findViewById(R.id.ll_reject)
        ll_accept = findViewById(R.id.ll_accept)

        profile_image_to_caller = findViewById(R.id.profile_image_to_caller)
        chrono_meter = findViewById(R.id.chrono_meter)
        qanswer = findViewById(R.id.qanswer)
        qanswer.setOnClickListener(View.OnClickListener {
            MethodToGetQuestionAndAnswer();
        })


        callerName = findViewById(R.id.callerName)
        iv_cut = findViewById(R.id.iv_cut)
        videoStatusTextView = findViewById(R.id.videoStatusTextView)

        iv_cut.setOnClickListener(View.OnClickListener {

            if(room!=null)
            room!!.disconnect()

            if(chrono_meter!=null )
            chrono_meter.stop()

            accessToken = "";



        finish()
        })

        ll_accept.setOnClickListener(View.OnClickListener {


            if(accessToken!=null) {

                connectToRoom(intent.getStringExtra("roomName"));
                ll_accept.visibility = View.GONE
                ll_reject.visibility = View.GONE
                iv_cut.visibility = View.VISIBLE
            }
            else
            {
                showMessage("Sorry..We are not getting access token from server")
            }
        })

        ll_reject.setOnClickListener(View.OnClickListener {
            if(room!=null)
            room!!.disconnect()
            finish()
        })

        thumbnailVideoView = findViewById(R.id.thumbnailVideoView)
        primaryVideoView = findViewById(R.id.primaryVideoView)



        vdoViewModel = ViewModelProviders.of(this).get(vdocallViewModel::class.java)
        (application as AmidApp).getMyComponent().inject(this@VideoActivity)


        connectActionFab= findViewById(R.id.connectActionFab);

        connectActionFab.setOnClickListener(View.OnClickListener {

        })

        if(intent.extras.getString("type")!= null) {
             calltype = intent.getStringExtra("type")

            if(calltype.equals("audio")) {
                thumbnailVideoView.visibility = View.GONE
                primaryVideoView.visibility = View.GONE
            }
        }


        if(intent.getStringExtra("roomName")!=null)
        {

            if(!TextUtils.isEmpty(pref.provider_strip_bank_id))
            {
                abc=true
                qanswer.visibility=View.VISIBLE

                if (NetworkUtils.isNetworkConnected(this)) {
                    showLoading()

                    if(!TextUtils.isEmpty(intent.getStringExtra("userName"))) {
                        callerName.setText(intent.getStringExtra("userName"))
                        providerNameVDO = intent.getStringExtra("userName")
                        videoStatusTextView.text = "Ringing..."
                    }
                    if(!TextUtils.isEmpty(intent.getStringExtra("proficiency_type"))) {
                        pro_Id = intent.getStringExtra("proficiency_type")
                    }


                    if(!TextUtils.isEmpty(intent.getStringExtra("userPicUrl"))) {
                        profile_image_to_caller.visibility = View.VISIBLE
                        providerImageVDO = intent.getStringExtra("userPicUrl")
                        Picasso.get().load(intent.getStringExtra("userPicUrl")).into(profile_image_to_caller)
                    }
                    MethodToGetAccessToken(pref.providerTokenU , pref.providerT_mobile , intent.getStringExtra("roomName"))
                    hideKeyboard()

                } else {
                    showSnackBar(getString(R.string.msg_check_internet))
                }
            }

            else
            {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Please add bank deatils to receive call")
                    .setCancelable(false)
                    .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, id: Int) {
                            if (room != null)
                                room!!.disconnect()
                            finish()

                        }
                    })
                val alert = builder.create()
                alert.show()

            }



        }
        else {

            qanswer.visibility = View.GONE

            if(!TextUtils.isEmpty(intent.getStringExtra("providerName")))
            {
                providerNameVDO = intent.getStringExtra("providerName")
                callerName.setText(providerNameVDO)
            }

            if(!TextUtils.isEmpty(intent.getStringExtra("providerImage")))
            {
                providerImageVDO = intent.getStringExtra("providerImage")
            }

            if(!TextUtils.isEmpty(intent.getStringExtra("roomNameprovider")))
            {
                roomNameprovider = intent.getStringExtra("roomNameprovider")
            }

            if (NetworkUtils.isNetworkConnected(this)) {
                showLoading()

                MethodToGetAccessToken(pref.userToken,pref.userP_phone,roomNameprovider)
//                vdoViewModel!!.methodgetTwilioAcccessToken(pref.userToken,pref.userP_phone)
                hideKeyboard()
//                vdoViewModel.getAccessToken()!!.observe(this, accessTokenVDO)

            } else {
                showSnackBar(getString(R.string.msg_check_internet))
            }




        }
        /*
         * Set local video view to primary view
         */
        localVideoView = primaryVideoView

        /*
         * Enable changing the volume using the up/down keys during a conversation
         */
        volumeControlStream = AudioManager.STREAM_VOICE_CALL

        /*
         * Needed for setting/abandoning audio focus during call
         */
        audioManager.isSpeakerphoneOn = true

        /*
         * Set access token
         */
//        setAccessToken()

        /*
         * Request permissions.
         */
        requestPermissionForCameraAndMicrophone()

        /*
         * Set the initial state of the UI
         */
        initializeUI()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            var cameraAndMicPermissionGranted = true

            for (grantResult in grantResults) {
                cameraAndMicPermissionGranted = cameraAndMicPermissionGranted and
                        (grantResult == PackageManager.PERMISSION_GRANTED)
            }

            if (cameraAndMicPermissionGranted) {
                createAudioAndVideoTracks()
            } else {
                Toast.makeText(this,
                        R.string.permissions_needed,
                        Toast.LENGTH_LONG).show()
            }
        }
    }



    private fun MethodToGetQuestionAndAnswer() {
        val apiService = ApiClient.getClient().create(WebService::class.java)
        val paramObject = HashMap<String, String>()
        paramObject["type"] = ""+pro_Id
        paramObject["pat_id"] = ""+intent.getStringExtra("pat_id")
        val call = apiService.getQuestionAndAnswerReport(paramObject,"Bearer " + pref!!.providerToken)
        showLoading()
        call.enqueue(object : retrofit2.Callback<ProviderGetQuestionAndAnswer> {
            override fun onResponse(call: Call<ProviderGetQuestionAndAnswer>, response: Response<ProviderGetQuestionAndAnswer>?) {

                if (response != null) {

                    if(response.body().status==Constant.RESPONSE_SUCCESSFULLY)
                    {
                        infoList.clear()
                        for(i in 0..response.body().info.size - 1) {
                            var info : Info? = Info()
                            info!!.question = response.body().info[i].question
                            info!!.answer = response.body().info[i].answer
                            info!!.queId = response.body().info[i].queId
                            infoList.add(info)
                        }

                        dialogEmergency  =  Dialog(this@VideoActivity);
                        dialogEmergency.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialogEmergency.setContentView(R.layout.dialog_question_nd_answer);
                        rv = dialogEmergency.findViewById(R.id.rv_quesand_answer)

                        mAdapter = ReportQuestionAnswerAdapter(infoList,this@VideoActivity)
                        mLayoutManager = LinearLayoutManager(this@VideoActivity)
                        rv.setLayoutManager(mLayoutManager)
                        rv.setItemAnimator(DefaultItemAnimator())
                        rv.setAdapter(mAdapter)

                        dialogEmergency.show();

                        hideLoading()
                    }

                    else
                    {
                        hideLoading()
                        showMessage(response.message())

                    }
                }
            }



            override fun onFailure(call: Call<ProviderGetQuestionAndAnswer>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
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
            override fun onResponse(call: Call<ModelAccessTokenVDO>, response: Response<ModelAccessTokenVDO>?) {

                if (response != null) {

                    if(response.body().status==Constant.RESPONSE_SUCCESSFULLY)
                    {
                        hideLoading()
                          if(abc) {


                             accessToken = response.body().data.token;
                             if(accessToken!=null && !accessToken.equals("")) {
                             ll_accept.visibility = View.VISIBLE
                             ll_reject.visibility = View.VISIBLE

                                 val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                                 val r = RingtoneManager.getRingtone(getApplicationContext(), notification)
                                 r.play()

                                 object: CountDownTimer(60000, 1000) {
                                     override fun onTick(millisUntilFinished:Long) {
                                         //here you can have your logic to set text to edittext
                                     }
                                     override fun onFinish() {

                                         if(ll_accept.visibility==View.VISIBLE && ll_reject.visibility == View.VISIBLE)
                                         {
                                             finish()
                                             if(r!=null)
                                             r.stop()

                                         }

                                     }
                                 }.start()

                             }


                          }
                    else {
                               if(calltype.equals("audio")) {
                                   primaryVideoView.visibility = View.GONE
                               }

                              object: CountDownTimer(60000, 1000) {
                                  override fun onTick(millisUntilFinished:Long) {
                                      //here you can have your logic to set text to edittext
                                  }
                                  override fun onFinish() {

                                      if(videoStatusTextView.text.equals("Ringing...") || videoStatusTextView.text.equals("Connecting..."))
                                      {
                                          if(room!=null)
                                              room!!.disconnect()
                                          finish()
                                      }

                                  }
                              }.start()



                        accessToken = response.body().data.token;
                        iv_cut.visibility = View.VISIBLE
                        profile_image_to_caller.visibility = View.VISIBLE
                        Picasso.get().load(providerImageVDO).into(profile_image_to_caller)

                        if (accessToken != null && !accessToken.equals("")) {
                            connectToRoom(roomNameprovider);
                            if (NetworkUtils.isNetworkConnected(this@VideoActivity)) {
                                showLoading()

                                if(calltype.equals("vdo")) {
                                    vdoViewModel.methodpostTwilioVDOData("video",
                                        pref.userBToken, roomNameprovider,
                                        pref.userP_Prof_pic, pref.userP_phone, roomNameprovider, pref.userP_fname,pref.userPat_id
                                    )
                                }

                                else
                                {
                                    vdoViewModel.methodpostTwilioVDOData("audio",
                                        pref.userBToken, roomNameprovider,
                                        pref.userP_Prof_pic, pref.userP_phone, roomNameprovider, pref.userP_fname,pref.userPat_id
                                    )

                                }
                                hideKeyboard()
                                vdoViewModel.observerTwilioVDOData()!!.observe(this@VideoActivity, pushNotificationData)
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
            }



            override fun onFailure(call: Call<ModelAccessTokenVDO>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }




    override fun onResume() {
        super.onResume()
        /*
         * If the local video track was released when the app was put in the background, recreate.
         */
        localVideoTrack = if (localVideoTrack == null && checkPermissionForCameraAndMicrophone()) {
            LocalVideoTrack.create(this,
                    true,
                    cameraCapturerCompat.videoCapturer)
        } else {
            localVideoTrack
        }
        localVideoTrack?.addRenderer(localVideoView)

        /*
         * If connected to a Room then share the local video track.
         */
        localVideoTrack?.let { localParticipant?.publishTrack(it) }

        /*
         * Update encoding parameters if they have changed.
         */
        localParticipant?.setEncodingParameters(encodingParameters)

        /*
         * Route audio through cached value.
         */
        audioManager.isSpeakerphoneOn = isSpeakerPhoneEnabled

        /*
         * Update reconnecting UI
         */
        room?.let {
            reconnectingProgressBar.visibility = if (it.state != Room.State.RECONNECTING)
                View.GONE else
                View.VISIBLE
            videoStatusTextView.text = "Connecting..."
        }
    }

    override fun onPause() {
        /*
         * If this local video track is being shared in a Room, remove from local
         * participant before releasing the video track. Participants will be notified that
         * the track has been removed.
         */
//        localVideoTrack?.let { localParticipant?.unpublishTrack(it) }
//
//
//        /*
//         * Release the local video track before going in the background. This ensures that the
//         * camera can be used by other applications while this app is in the background.
//         */
//        localVideoTrack?.release()
//        localVideoTrack = null
        super.onPause()
    }

    override fun onDestroy() {
        /*
         * Always disconnect from the room before leaving the Activity to
         * ensure any memory allocated to the Room resource is freed.
         */
        room?.disconnect()
        disconnectedFromOnDestroy = true

        /*
         * Release the local audio and video tracks ensuring any memory allocated to audio
         * or video is freed.
         */
        localAudioTrack?.release()
        localVideoTrack?.release()

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
//        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))

            R.id.speaker_menu_item -> if (audioManager.isSpeakerphoneOn) {
                audioManager.isSpeakerphoneOn = false
                item.setIcon(R.drawable.ic_phonelink_ring_white_24dp)
                isSpeakerPhoneEnabled = false
            } else {
                audioManager.isSpeakerphoneOn = true
                item.setIcon(R.drawable.ic_volume_up_white_24dp)
                isSpeakerPhoneEnabled = true
            }
        }
        return true
    }

    private fun requestPermissionForCameraAndMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.RECORD_AUDIO)) {

            Toast.makeText(this,
                    R.string.permissions_needed,
                    Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
                    CAMERA_MIC_PERMISSION_REQUEST_CODE)
        }
    }

    private fun checkPermissionForCameraAndMicrophone(): Boolean {
        val resultCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)

        return resultCamera == PackageManager.PERMISSION_GRANTED &&
                resultMic == PackageManager.PERMISSION_GRANTED
    }

    private fun createAudioAndVideoTracks() {
        // Share your microphone
        localAudioTrack = LocalAudioTrack.create(this, true)

        // Share your camera
        localVideoTrack = LocalVideoTrack.create(this,
                true,
                cameraCapturerCompat.videoCapturer)
    }

    private fun getAvailableCameraSource(): CameraCapturer.CameraSource {
        return if (CameraCapturer.isSourceAvailable(CameraCapturer.CameraSource.FRONT_CAMERA))
            CameraCapturer.CameraSource.FRONT_CAMERA
        else
            CameraCapturer.CameraSource.BACK_CAMERA
    }

    private fun setAccessToken() {
        if (!BuildConfig.USE_TOKEN_SERVER) {
            /*
             * OPTION 1 - Generate an access token from the getting started portal
             * https://www.twilio.com/console/video/dev-tools/testing-tools and add
             * the variable TWILIO_ACCESS_TOKEN setting it equal to the access token
             * string in your local.properties file.
             */
            retrieveAccessTokenfromServer()
//            this.accessToken = TWILIO_ACCESS_TOKEN
        } else {
            /*
             * OPTION 2 - Retrieve an access token from your own web app.
             * Add the variable ACCESS_TOKEN_SERVER assigning it to the url of your
             * token server and the variable USE_TOKEN_SERVER=true to your
             * local.properties file.
             */
            retrieveAccessTokenfromServer()
        }
    }

    private fun connectToRoom(roomName: String) {
        configureAudio(true)
        val connectOptionsBuilder = ConnectOptions.Builder(accessToken)
                .roomName(roomName)

        /*
         * Add local audio track to connect options to share with participants.
         */
        localAudioTrack?.let { connectOptionsBuilder.audioTracks(listOf(it)) }

        /*
         * Add local video track to connect options to share with participants.
         */
        localVideoTrack?.let { connectOptionsBuilder.videoTracks(listOf(it)) }

        /*
         * Set the preferred audio and video codec for media.
         */
        connectOptionsBuilder.preferAudioCodecs(listOf(audioCodec))
        connectOptionsBuilder.preferVideoCodecs(listOf(videoCodec))

        /*
         * Set the sender side encoding parameters.
         */
        connectOptionsBuilder.encodingParameters(encodingParameters)

        room = Video.connect(this, connectOptionsBuilder.build(), roomListener)
        setDisconnectAction()
    }


    internal val pushNotificationData: android.arch.lifecycle.Observer<ModelTwilloNotificationUserData> = Observer<ModelTwilloNotificationUserData> { pushNotification ->

        hideLoading()

        if(pushNotification!!.status== Constant.response_Failure)
        {
            showMessage(getString(R.string.error_some_problem_occur))
        }
        else  if(pushNotification!!.status== Constant.RESPONSE_SUCCESSFULLY){

        }
    }






    /*
     * The initial state when there is no active room.
     */
    private fun initializeUI() {
        connectActionFab.setImageDrawable(ContextCompat.getDrawable(this,
                R.mipmap.ic_video_call_provider_detail_view))
        connectActionFab.hide()
        connectActionFab.setOnClickListener(connectActionClickListener())
        switchCameraActionFab.hide()
        switchCameraActionFab.setOnClickListener(switchCameraClickListener())
        localVideoActionFab.hide()
        localVideoActionFab.setOnClickListener(localVideoClickListener())
        muteActionFab.hide()
        muteActionFab.setOnClickListener(muteClickListener())
    }

    /*
     * The actions performed during disconnect.
     */
    private fun setDisconnectAction() {
        connectActionFab.setImageDrawable(ContextCompat.getDrawable(this,
                R.mipmap.ic_video_call_provider_detail_view))
        connectActionFab.hide()
        connectActionFab.setOnClickListener(disconnectClickListener())
    }

    /*
     * Creates an connect UI dialog
     */
    private fun showConnectDialog() {
        val roomEditText = EditText(this)
        alertDialog = createConnectDialog(roomEditText,
                connectClickListener(roomEditText), cancelConnectDialogClickListener(), this)
        alertDialog!!.show()
    }


    /*
     * Called when participant joins the room
     */
    private fun addRemoteParticipant(remoteParticipant: RemoteParticipant) {
        /*
         * This app only displays video for one additional participant per Room
         */
        if (thumbnailVideoView.visibility == View.VISIBLE) {
            Snackbar.make(connectActionFab,
                    "Multiple participants are not currently support in this UI",
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            return
        }
        participantIdentity = remoteParticipant.identity
        videoStatusTextView.text = "Connected"
//        videoStatusTextView.text = "onVideoTrackSubscribed"
        chrono_meter.visibility = View.VISIBLE
        chrono_meter.setBase(SystemClock.elapsedRealtime());
        chrono_meter.start()

        val sdf = SimpleDateFormat("hh:mm:ss aa")

         startTime = sdf.format(Date())


//            videoStatusTextView.visibility = View.GONE
        profile_image_to_caller.visibility = View.GONE
        /*
         * Add participant renderer
         */
        remoteParticipant.remoteVideoTracks.firstOrNull()?.let { remoteVideoTrackPublication ->
            if (remoteVideoTrackPublication.isTrackSubscribed) {
                remoteVideoTrackPublication.remoteVideoTrack?.let { addRemoteParticipantVideo(it) }
            }
        }

        /*
         * Start listening for participant events
         */
        remoteParticipant.setListener(participantListener)
    }

    /*
     * Set primary view as renderer for participant video track
     *
     *
     */


    private fun addRemoteParticipantVideo(videoTrack: VideoTrack) {
        moveLocalVideoToThumbnailView()
        primaryVideoView.mirror = false
        videoTrack.addRenderer(primaryVideoView)
    }

    private fun moveLocalVideoToThumbnailView() {
        if (thumbnailVideoView.visibility == View.GONE) {

            if (calltype.equals("audio")) {
                primaryVideoView.visibility = View.GONE
                thumbnailVideoView.visibility=View.GONE
                thumbnailVideoView.mirror = false


            } else {

                primaryVideoView.visibility = View.VISIBLE
                with(localVideoTrack) {
                    this?.removeRenderer(primaryVideoView)
                    this?.addRenderer(thumbnailVideoView)
                }
                localVideoView = thumbnailVideoView
                thumbnailVideoView.mirror = cameraCapturerCompat.cameraSource ==
                        CameraCapturer.CameraSource.FRONT_CAMERA

                thumbnailVideoView.visibility = View.VISIBLE
            }
        }
    }

    /*
     * Called when participant leaves the room
     */
    private fun removeRemoteParticipant(remoteParticipant: RemoteParticipant) {
//        videoStatusTextView.text = "Participant $remoteParticipant.identity left."

        videoStatusTextView.text = "Disconnected"
        chrono_meter.stop()
        val sdf = SimpleDateFormat("hh:mm:ss aa")
         disconnectTime = sdf.format(Date())
        val intent11 = Intent(this, CallDetails::class.java)
        intent11.putExtra("disconnectTime",""+disconnectTime)
        intent11.putExtra("startTime",""+startTime)
        intent11.putExtra("providerImage",providerImageVDO)
        intent11.putExtra("providerName",providerNameVDO)
        intent11.putExtra("rate",intent.getStringExtra("rate"))
        intent11.putExtra("type", intent.getStringExtra("type"))
        intent11.putExtra("t_id", intent.getStringExtra("t_id"))
        intent11.putExtra("pat_id", ""+intent.getStringExtra("pat_id"))
        intent11.putExtra("callFrom", ""+abc)
        startActivity(intent11)
        finish()

        if (remoteParticipant.identity != participantIdentity) {
            return
        }

        /*
         * Remove participant renderer
         */
        remoteParticipant.remoteVideoTracks.firstOrNull()?.let { remoteVideoTrackPublication ->
            if (remoteVideoTrackPublication.isTrackSubscribed) {
                remoteVideoTrackPublication.remoteVideoTrack?.let { removeParticipantVideo(it) }
            }
        }
        moveLocalVideoToPrimaryView()
    }

    private fun removeParticipantVideo(videoTrack: VideoTrack) {
        videoTrack.removeRenderer(primaryVideoView)
    }

    private fun moveLocalVideoToPrimaryView() {
        if (thumbnailVideoView.visibility == View.VISIBLE) {
            thumbnailVideoView.visibility = View.GONE
            with(localVideoTrack) {
                this?.removeRenderer(thumbnailVideoView)
                this?.addRenderer(primaryVideoView)
            }
            localVideoView = primaryVideoView
            primaryVideoView.mirror = cameraCapturerCompat.cameraSource ==
                    CameraCapturer.CameraSource.FRONT_CAMERA
        }
    }

    private fun connectClickListener(roomEditText: EditText): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, _ ->
            /*
             * Connect to room
             */
//            connectToRoom(roomEditText.text.toString())
        }
    }

    private fun disconnectClickListener(): View.OnClickListener {
        return View.OnClickListener {
            /*
             * Disconnect from room
             */
            room?.disconnect()
            initializeUI()
        }
    }

    private fun connectActionClickListener(): View.OnClickListener {
        return View.OnClickListener { showConnectDialog() }
    }

    private fun cancelConnectDialogClickListener(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, _ ->
            initializeUI()
            alertDialog!!.dismiss()
        }
    }

    private fun switchCameraClickListener(): View.OnClickListener {
        return View.OnClickListener {
            val cameraSource = cameraCapturerCompat.cameraSource
            cameraCapturerCompat.switchCamera()
            if (thumbnailVideoView.visibility == View.VISIBLE) {
                thumbnailVideoView.mirror = cameraSource == CameraCapturer.CameraSource.BACK_CAMERA
            } else {
                primaryVideoView.mirror = cameraSource == CameraCapturer.CameraSource.BACK_CAMERA
            }
        }
    }

    private fun localVideoClickListener(): View.OnClickListener {
        return View.OnClickListener {
            /*
             * Enable/disable the local video track
             */
            localVideoTrack?.let {
                val enable = !it.isEnabled
                it.enable(enable)
                val icon: Int
                if (enable) {
                    icon = R.mipmap.ic_video_call_provider_detail_view
                    switchCameraActionFab.show()
                } else {
                    icon = R.mipmap.ic_video_call_provider_detail_view
                    switchCameraActionFab.hide()
                }
                localVideoActionFab.setImageDrawable(
                        ContextCompat.getDrawable(this@VideoActivity, icon))
            }
        }
    }

    private fun muteClickListener(): View.OnClickListener {
        return View.OnClickListener {
            /*
             * Enable/disable the local audio track. The results of this operation are
             * signaled to other Participants in the same Room. When an audio track is
             * disabled, the audio is muted.
             */
            localAudioTrack?.let {
                val enable = !it.isEnabled
                it.enable(enable)
                val icon = if (enable)
                    R.mipmap.ic_video_call_provider_detail_view
                else
                    R.mipmap.ic_video_call_provider_detail_view
                muteActionFab.setImageDrawable(ContextCompat.getDrawable(
                        this@VideoActivity, icon))
            }
        }
    }

    private fun retrieveAccessTokenfromServer() {
//        Ion.with(this)
//                .load("$ACCESS_TOKEN_SERVER?identity=${UUID.randomUUID()}")
//                .asString()
//                .setCallback { e, token ->
//                    if (e == null) {
//
//                        val jsonObj = JSONObject(token.toString())
//                        val ttoken = jsonObj.getString("token")
//                        this@VideoActivity.accessToken = ttoken
//                    } else {
//                        Toast.makeText(this@VideoActivity,
//                                "Error while access token", Toast.LENGTH_LONG)
//                                .show()
//                    }
//                }
    }

    private fun configureAudio(enable: Boolean) {
        with(audioManager) {
            if (enable) {
                previousAudioMode = audioManager.mode
                // Request audio focus before making any device switch
                requestAudioFocus()
                /*
                 * Use MODE_IN_COMMUNICATION as the default audio mode. It is required
                 * to be in this mode when playout and/or recording starts for the best
                 * possible VoIP performance. Some devices have difficulties with
                 * speaker mode if this is not set.
                 */
                mode = AudioManager.MODE_IN_COMMUNICATION
                /*
                 * Always disable microphone mute during a WebRTC call.
                 */
                previousMicrophoneMute = isMicrophoneMute
                isMicrophoneMute = false
            } else {
                mode = previousAudioMode
                abandonAudioFocus(null)
                isMicrophoneMute = previousMicrophoneMute
            }
        }
    }

    private fun requestAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val playbackAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build()
            val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    .setAudioAttributes(playbackAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener { }
                    .build()
            audioManager.requestAudioFocus(focusRequest)
        } else {
            audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        }
    }

    private fun createConnectDialog(participantEditText: EditText,
                                    callParticipantsClickListener: DialogInterface.OnClickListener,
                                    cancelClickListener: DialogInterface.OnClickListener,
                                    context: Context): AlertDialog {
        val alertDialogBuilder = AlertDialog.Builder(context).apply {
            setIcon(R.mipmap.ic_video_call_provider_detail_view)
            setTitle("Connect to a room")
            setPositiveButton("Connect", callParticipantsClickListener)
            setNegativeButton("Cancel", cancelClickListener)
            setCancelable(false)
        }

        setRoomNameFieldInDialog(participantEditText, alertDialogBuilder, context)

        return alertDialogBuilder.create()
    }

    @SuppressLint("RestrictedApi")
    private fun setRoomNameFieldInDialog(roomNameEditText: EditText,
                                         alertDialogBuilder: AlertDialog.Builder,
                                         context: Context) {
        roomNameEditText.hint = "room name"
        val horizontalPadding = context.resources.getDimensionPixelOffset(R.dimen.activity_horizontal_margin)
        val verticalPadding = context.resources.getDimensionPixelOffset(R.dimen.activity_vertical_margin)
        alertDialogBuilder.setView(roomNameEditText,
                horizontalPadding,
                verticalPadding,
                horizontalPadding,
                0)
    }
}
