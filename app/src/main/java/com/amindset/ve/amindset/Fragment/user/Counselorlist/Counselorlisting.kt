package com.amindset.ve.amindset.Fragment.user.Counselorlist

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
import kotlin.collections.ArrayList
import kotlin.collections.set

class Counselorlisting : BaseFragment() , View.OnClickListener {



//    override fun callActivityfromAdapter() {
//        val ft = fragmentManager!!.beginTransaction()
//        val counslerlisFrag: CounselorProfileDetails = CounselorProfileDetails()
//        val args = Bundle()
//        args.putString("id", "123")
//        counslerlisFrag.arguments = args
//        ft.replace(R.id.child_fragment_container, CounselorProfileDetails(), "next").addToBackStack(null)
//        ft.commitAllowingStateLoss()
//    }

//    fun callActivityfromAdapter(therepistId : String ) {

//         if(NetworkUtils.isNetworkConnected(context)) {

//         }
//         else
//         {
//             showMessage(R.string.msg_check_internet)
//         }

//        val ft = fragmentManager!!.beginTransaction()
//        ft.replace(R.id.child_fragment_container, CounselorProfileDetails(), "next").addToBackStack(null)
//        ft.commitAllowingStateLoss()
//    }

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

    var value : String = ""
    var title : String = ""

    lateinit  var mAdapter: CounselorlistAdapter
    var pref : AppPreferencesHelper? = null

    var couseleorList = ArrayList<Info>()

    companion object {
        fun newInstance() = Counselorlisting()
    }

    private lateinit var viewModel: CounselorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = AppPreferencesHelper(activity)
        value = arguments!!.getString("id")
        title= arguments!!.getString("title")

        if(NetworkUtils.isNetworkConnected(activity)) {
            couseleorList.clear()
            APIforGetCunselorList(value);
        }
        else
        {
            showSnackBar(getString(R.string.msg_check_internet))
            Onbacktoolbarsetting()
            activity!!.supportFragmentManager.popBackStackImmediate()
        }

        return inflater.inflate(R.layout.service_specialisting_fragment, container, false)
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

                        if(response.body().status==Constant.RESPONSE_SUCCESSFULLY) {

//                        showMessage(response.body().info[0].question)

                           if(response.body().info!=null && response.body().info.size>0) {
                               for (i in 0..response.body().info.size - 1) {
                                   var info: Info? = Info()

                                   info!!.therapId = response.body().info[i].therapId
                                   info!!.therapName = response.body().info[i].therapName
                                   info!!.avgRatings = response.body().info[i].avgRatings
                                   info!!.contact = response.body().info[i].contact
                                   info!!.email = response.body().info[i].email
                                   info!!.profPic = response.body().info[i].profPic
                                   info!!.about = response.body().info[i].about

                                   couseleorList.add(info)
                               }

                               mAdapter = CounselorlistAdapter(couseleorList, activity, Counselorlisting(), title)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CounselorViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = activity!!.findViewById(R.id.rv_couslorlisting) as RecyclerView

    }
    override fun onResume() {
        super.onResume()
        toolbarsetting()
//        prepareMovieData()

    }
    private fun toolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText(""+title)
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE
        iv_toolbar_back.setOnClickListener(this)
    }
    private fun Onbacktoolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.mipmap.amindset));
        tv_tolbar_center_text.setText("")
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
    }
}
