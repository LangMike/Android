package com.amindset.ve.amindset.Fragment.providerservice.providerRecentList

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
import com.amindset.ve.amindset.Fragment.providerservice.providerRecentList.ModelRecent.Info
import com.amindset.ve.amindset.Fragment.providerservice.providerRecentList.ModelRecent.ModelProviderRecentCall

import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import retrofit2.Call
import retrofit2.Response
import java.util.HashMap

class providerRecentList : BaseFragment() , View.OnClickListener {
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

    lateinit  var mAdapter: ProviderRecentAdapter
    lateinit var transCallList : ArrayList<Info>



    companion object {
        fun newInstance() = providerRecentList()
    }

    private lateinit var viewModel: ProviderRecentListViewModel
    var pref : AppPreferencesHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pref = AppPreferencesHelper(activity)
        if(NetworkUtils.isNetworkConnected(activity))
            APIforGetRecentList()
        else
            showMessage(R.string.msg_check_internet)
        return inflater.inflate(R.layout.provider_recent_list_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = activity!!.findViewById(R.id.rv_recent_provider) as RecyclerView
        toolbarsetting()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProviderRecentListViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private fun APIforGetRecentList() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, Int>()


        paramObject["usertype"] = 2

        val call = apiService.getProviderRecentCallList(paramObject,"Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderRecentCall> {

            override fun onResponse(call: Call<ModelProviderRecentCall>, response: Response<ModelProviderRecentCall>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status== Constant.RESPONSE_SUCCESSFULLY) {

//                            setDataOnView(response.body().info);

                            transCallList = ArrayList()

                            for(i in 0..response.body().info.size - 1) {
                                var info : Info? = Info()
                                info!!.about = response.body().info[i].about
                                info!!.callDuration = response.body().info[i].callDuration
                                info!!.cost = response.body().info[i].cost
                                info!!.pFname = response.body().info[i].pFname
                                info!!.calltypes = response.body().info[i].calltypes
                                info!!.pProfPic = response.body().info[i].pProfPic
                                info!!.callTime = response.body().info[i].callTime
                                info!!.calltypes = response.body().info[i].calltypes
                                transCallList.add(info)
                            }


                            mAdapter = ProviderRecentAdapter(transCallList)
                            val mLayoutManager = LinearLayoutManager(activity)
                            recyclerView.setLayoutManager(mLayoutManager)
                            recyclerView.setItemAnimator(DefaultItemAnimator())
                            recyclerView.setAdapter(mAdapter)


                            showMessage(response.body().msg)
                            hideLoading()
                        }
                        else
                        {
                            showMessage(response.message())
                            hideLoading()

                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelProviderRecentCall>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    override fun onResume() {
        super.onResume()


    }
    private fun toolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText("Past Appointments")
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE
        iv_toolbar_back.setOnClickListener(this)
    }



}
