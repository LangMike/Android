package com.amindset.ve.amindset.Fragment.user.Appoinment

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
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Fragment.user.Appoinment.Model.Info
import com.amindset.ve.amindset.Fragment.user.Appoinment.Model.ModelUserPassAppointments
import com.amindset.ve.amindset.Fragment.userservice.Movie
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import retrofit2.Call
import retrofit2.Response
import java.util.*

class UserAppoinments : BaseFragment() , View.OnClickListener {
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
    private fun Onbacktoolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.mipmap.amindset));
        tv_tolbar_center_text.setText("")
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
    }

    private lateinit var recyclerView : RecyclerView

    lateinit var movieList : ArrayList<Movie>
    var pref : AppPreferencesHelper? = null
    lateinit  var mAdapter: UserAppoinmentsAdapter

    var userpastAppointment = ArrayList<Info>()


    companion object {
        fun newInstance() = UserAppoinments()
    }

    private lateinit var viewModel: UserAppoinmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pref = AppPreferencesHelper(activity)


        if(NetworkUtils.isNetworkConnected(activity)) {
            userpastAppointment.clear()

            APIForUserPastAppoitments();
        }
        else
        {
            showSnackBar(getString(R.string.msg_check_internet))
            Onbacktoolbarsetting()
            activity!!.supportFragmentManager.popBackStackImmediate()
        }

        return inflater.inflate(R.layout.user_appoinments_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserAppoinmentsViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onResume() {
        super.onResume()
        recyclerView = activity!!.findViewById(R.id.rv_appinments_provider) as RecyclerView

        toolbarsetting();


    }
    private fun toolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText("Past Appointments")
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
        iv_toolbar_back.setOnClickListener(this)
    }
    private fun APIForUserPastAppoitments() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["usertype"] = "1"

        val call = apiService.getUserPastAppointments(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserPassAppointments> {

            override fun onResponse(call: Call<ModelUserPassAppointments>, response: Response<ModelUserPassAppointments>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {

                        if(response.body().status== Constant.RESPONSE_SUCCESSFULLY) {

//                        showMessage(response.body().info[0].question)

                            for (i in 0..response.body().info.size - 1) {
                                var info: Info? = Info()

                                info!!.callDuration = response.body().info[i].callDuration
                                info!!.callFrom = response.body().info[i].callFrom
                                info!!.callTime = response.body().info[i].callTime
                                info!!.callTo = response.body().info[i].callTo
                                info!!.calltypes = response.body().info[i].calltypes
                                info!!.cost = response.body().info[i].cost
                                info!!.tProfPic = response.body().info[i].tProfPic
                                info!!.tFname = response.body().info[i].tFname
                                info!!.tAbout = response.body().info[i].tAbout
                                info!!.createdon = response.body().info[i].createdon
                                userpastAppointment.add(info)
                            }

                            mAdapter = UserAppoinmentsAdapter(userpastAppointment,activity)
                            val mLayoutManager = LinearLayoutManager(activity)
                            recyclerView.setLayoutManager(mLayoutManager)
                            recyclerView.setItemAnimator(DefaultItemAnimator())
                            recyclerView.setAdapter(mAdapter)

                            hideLoading()
                        }else {
                            hideLoading()
                        }
                    }else {
                        hideLoading()
                    }
                }
            }


            override fun onFailure(call: Call<ModelUserPassAppointments>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

}
