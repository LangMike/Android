package com.amindset.ve.amindset.Fragment.user.Notification

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.ModelQuestionList
import com.amindset.ve.amindset.Fragment.user.Notification.ModelProviderNotifi.InfoProvider
import com.amindset.ve.amindset.Fragment.user.Notification.ModelProviderNotifi.ModelProviderNotification
import com.amindset.ve.amindset.Fragment.user.Notification.ModelUserNotifica.Info
import com.amindset.ve.amindset.Fragment.user.Notification.ModelUserNotifica.ModelUserNotification
import com.amindset.ve.amindset.Fragment.userservice.Movie
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import retrofit2.Call
import retrofit2.Response
import java.util.*

class Notification : BaseFragment()  , View.OnClickListener{
    override fun onClick(p0: View?) {
        when(p0!!.id)
        {
            R.id.iv_toobar_back->
            {
                Onbacktoolbarsetting()
                activity!!.supportFragmentManager.popBackStackImmediate()
            }
        }
    }

    private lateinit var recyclerView : RecyclerView

    lateinit var movieList : ArrayList<Movie>
      var mAdapter: NotificationAdapter? = null
      var mAdapterProvider: NotificationProviderAdapter?=null
    var pref : AppPreferencesHelper? = null
    var infoList = ArrayList<Info>()
    var infoListProvider = ArrayList<InfoProvider>()
    lateinit var iv_notification: ImageView


    companion object {
        fun newInstance() = Notification()
    }

    private lateinit var viewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = AppPreferencesHelper(activity)


        if(arguments!!.getString("id") != null && arguments!!.getString("id") .equals("user")) {

            APItoUserGetNotification();

        }

        else{
            APItoProviderGetNotification();

        }


        return inflater.inflate(R.layout.notification_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = activity!!.findViewById(R.id.rv_appinments_provider) as RecyclerView

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onResume() {
        super.onResume()
        toolbarsetting();
//        prepareMovieData()

    }
    private fun Onbacktoolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.mipmap.amindset));
        tv_tolbar_center_text.setText("")
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE

        iv_notification =activity!!.findViewById(R.id.iv_notification) as ImageView
        iv_notification.isEnabled=true

    }


    private fun toolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText("Notification")
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE

         iv_notification =activity!!.findViewById(R.id.iv_notification) as ImageView
        iv_notification.isEnabled=false
        iv_toolbar_back.setOnClickListener(this)
    }


    private fun APItoProviderGetNotification() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()


        val call = apiService.GetProviderNotificationList( "Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderNotification> {

            override fun onResponse(call: Call<ModelProviderNotification>, response: Response<ModelProviderNotification>?) {

                if (response != null) {
                    if (response.isSuccessful && response.body()!=null && response.body().infoProvider!=null )
                    {
                        for(i in 0..response.body().infoProvider.size - 1) {
                            var info : InfoProvider? = InfoProvider()
                            info!!.name = response.body().infoProvider[i].name
                            info!!.apptDate = response.body().infoProvider[i].apptDate
//                            info!!.notificationText = response.body().infoProvider[i].notificationText
                            infoListProvider.add(info)
                        }
                        mAdapterProvider = NotificationProviderAdapter(infoListProvider)
                        val mLayoutManager = LinearLayoutManager(activity)
                        recyclerView.setLayoutManager(mLayoutManager)
                        recyclerView.setItemAnimator(DefaultItemAnimator())
                        recyclerView.setAdapter(mAdapterProvider)
                        hideLoading()
                    }else {
                        hideLoading()
//                            info!!.notificationType = response.body().infoProvider[i].notificationType
//                            info!!.profilePic = response.body().infoProvider[i].profilePic
//                            info!!.readStatus = response.body().infoProvider[i].readStatus
//                            info!!.redirectUrl = response.body().infoProvider[i].redirectUrl
                    }
                }
            }



            override fun onFailure(call: Call<ModelProviderNotification>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }




    private fun APItoUserGetNotification() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["category"] = "General"

        val call = apiService.GetNotificationList(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserNotification> {

            override fun onResponse(call: Call<ModelUserNotification>, response: Response<ModelUserNotification>?) {

                if (response != null) {
                    if (response.isSuccessful && response.body()!=null )
                    {

//                        showMessage(response.body().info[0].question)

                        for(i in 0..response.body().info.size - 1) {
                            var info : Info? = Info()
                            info!!.createdOn = response.body().info[i].createdOn
                            info!!.notificationId = response.body().info[i].notificationId
                            info!!.notificationText = response.body().info[i].notificationText
                            info!!.notificationType = response.body().info[i].notificationType
                            info!!.profilePic = response.body().info[i].profilePic
                            info!!.readStatus = response.body().info[i].readStatus
                            info!!.redirectUrl = response.body().info[i].redirectUrl
                            infoList.add(info)
                        }
                        mAdapter = NotificationAdapter(infoList)
                        val mLayoutManager = LinearLayoutManager(activity)
                        recyclerView.setLayoutManager(mLayoutManager)
                        recyclerView.setItemAnimator(DefaultItemAnimator())
                        recyclerView.setAdapter(mAdapter)
                        hideLoading()
                    }else {
                        hideLoading()
                    }
                }
            }



            override fun onFailure(call: Call<ModelUserNotification>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

}
