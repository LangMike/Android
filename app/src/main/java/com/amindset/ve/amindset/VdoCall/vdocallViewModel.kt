package com.amindset.ve.amindset.VdoCall

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.UserRepository
import com.amindset.ve.amindset.VdoCall.ModelAccessTokenVDOcall.ModelAccessTokenVDO
import com.amindset.ve.amindset.VdoCall.ModelNotificationData.ModelTwilloNotificationUserData
import javax.inject.Inject

class vdocallViewModel(application : Application) : AndroidViewModel(application)
{


    init {
        (application as AmidApp).getMyComponent().inject(this@vdocallViewModel)
    }


    @Inject
    lateinit var userRepository : UserRepository

    private var userAccessToken: LiveData<ModelAccessTokenVDO>? = null
    private var userpushNotificationData: LiveData<ModelTwilloNotificationUserData>? = null


//    fun methodgetTwilioAcccessToken(userTokenToken: String ,phone : String)
//    {
//        userAccessToken= userRepository.accessTokenVDO(userTokenToken ,phone);
//    }
//
//    fun getAccessToken(): LiveData<ModelAccessTokenVDO>? {
//        return userAccessToken
//    }

    fun methodpostTwilioVDOData(calltype : String,bearerToken : String , roomName : String ,
                                userProfilePic : String , userContactNumber : String  ,
                                providerNumber : String , userName : String , p_Id : String)
    {
        userpushNotificationData= userRepository.sendPushnotificationVDOData(calltype,bearerToken,
            roomName,userProfilePic,userContactNumber,providerNumber,userName,p_Id);
    }

    fun observerTwilioVDOData(): LiveData<ModelTwilloNotificationUserData>? {
        return userpushNotificationData
    }




}