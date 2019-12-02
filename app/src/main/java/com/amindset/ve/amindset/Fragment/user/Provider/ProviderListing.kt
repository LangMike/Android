package com.amindset.ve.amindset.Fragment.user.Provider

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
import com.amindset.ve.amindset.Fragment.user.Counselorlist.Model.Info
import com.amindset.ve.amindset.Fragment.user.Counselorlist.Model.ModelCounselorList

import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import retrofit2.Call
import retrofit2.Response
import java.util.HashMap

class ProviderListing : BaseFragment() , View.OnClickListener {
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


    //Declare varaibles

    private lateinit var recyclerView : RecyclerView
    var pref : AppPreferencesHelper? = null

    var providers = ArrayList<Info>()
    lateinit  var mAdapter: ProvidertAdapter
    companion object {
        fun newInstance() = ProviderListing()
    }

    private lateinit var viewModel: ProviderListingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = AppPreferencesHelper(activity)
        if(NetworkUtils.isNetworkConnected(activity)) {
            providers.clear()
            showLoading()
            APIforGetCunselorList("0");
        }
        else
        {
            showSnackBar(getString(R.string.msg_check_internet))
//            Onbacktoolbarsetting()
            activity!!.supportFragmentManager.popBackStackImmediate()
        }

        return inflater.inflate(R.layout.provider_listing_fragment, container, false)
    }

    private fun Onbacktoolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.mipmap.amindset));
        tv_tolbar_center_text.setText("")
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
    }

    private fun toolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText("Providers")
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
        iv_toolbar_back.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProviderListingViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onResume() {
        super.onResume()
        toolbarsetting();

        recyclerView = activity!!.findViewById(R.id.rv_provider) as RecyclerView

    }
    private fun APIforGetCunselorList(proficiency_id: String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["proficiency_id"] = proficiency_id

        val call = apiService.getCounselorList(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelCounselorList> {

            override fun onResponse(call: Call<ModelCounselorList>, response: Response<ModelCounselorList>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {

                        if(response.body().status== Constant.RESPONSE_SUCCESSFULLY) {

//                        showMessage(response.body().info[0].question)

                            for (i in 0..response.body().info.size - 1) {
                                var info: Info? = Info()
                                info!!.therapId = response.body().info[i].therapId
                                info!!.therapName = response.body().info[i].therapName
                                info!!.avgRatings = response.body().info[i].avgRatings
                                info!!.contact = response.body().info[i].contact
                                info!!.email = response.body().info[i].email
                                info!!.profPic=response.body().info[i].profPic
                                info!!.about=response.body().info[i].about
                                info!!.proficiency=response.body().info[i].proficiency
                                info!!.professionTitle=response.body().info[i].professionTitle
                                providers.add(info)
                            }

                            mAdapter = ProvidertAdapter(providers)
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


            override fun onFailure(call: Call<ModelCounselorList>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

}
