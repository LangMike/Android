package com.amindset.ve.amindset.Fragment.providerservice.providerEarning

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Fragment.providerservice.providerEarning.ModelProviderTranscation.Datum
import com.amindset.ve.amindset.Fragment.providerservice.providerEarning.ModelProviderTranscation.ModelProviderRecentTransaction
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.CounselorQuestionAnswerAdapter
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.Info
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.ModelQuestionList
import com.amindset.ve.amindset.Fragment.user.Recently.MoviesAdapter
import com.amindset.ve.amindset.Fragment.userservice.Movie

import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import retrofit2.Call
import retrofit2.Response
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EarningList.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EarningList.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EarningList : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var pref : AppPreferencesHelper? = null


    var rv_recent_transaction : RecyclerView?=null
    var tv_today : TextView?=null
    var tv_yesterday : TextView?=null
    var tv_this_month : TextView?=null
    var tv_today_earning : TextView?=null
    var tv_yesterday_earning : TextView?=null
    var tv_this_month_earning : TextView?=null

    lateinit  var mAdapter: RecentTransactionAdapter
    lateinit var transList : ArrayList<Datum>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun toolbarsetting() {
//        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
//        tv_tolbar_center_text.setBackgroundResource(0)
//        tv_tolbar_center_text.setText("Counselor")
//        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
//        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE
        iv_toolbar_back.setOnClickListener(View.OnClickListener {  Onbacktoolbarsetting()
            activity!!.supportFragmentManager.popBackStackImmediate() })
    }
    private fun Onbacktoolbarsetting() {
//        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
//        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.mipmap.amindset));
//        tv_tolbar_center_text.setText("")
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
//        prepareMovieData()
//        mAdapter = MoviesAdapter(movieList)
//        val mLayoutManager = LinearLayoutManager(context)
//        rv_recent_transaction!!.setLayoutManager(mLayoutManager)
//        rv_recent_transaction!!.setItemAnimator(DefaultItemAnimator())
//        rv_recent_transaction!!.setAdapter(mAdapter)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = AppPreferencesHelper(activity)
        if(NetworkUtils.isNetworkConnected(activity)) {
            APIForGetRecentTransaction();
        }
        else
        {
            showSnackBar(getString(R.string.msg_check_internet))
            Onbacktoolbarsetting()
            activity!!.supportFragmentManager.popBackStackImmediate()
        }



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_earning_list, container, false)
    }

    private fun APIForGetRecentTransaction() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["usertype"] = "2"

        val call = apiService.getProviderTranstion(paramObject , "Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderRecentTransaction> {

            override fun onResponse(call: Call<ModelProviderRecentTransaction>, response: Response<ModelProviderRecentTransaction>?) {

                if (response != null) {
                    if (response.isSuccessful && response.body().status==Constant.RESPONSE_SUCCESSFULLY)
                    {

//                        showMessage(response.body().msg)

                         transList = ArrayList()

                        if(response.body().eraning!=null) {

                            if (!TextUtils.isEmpty(response.body().eraning.thismonth))
                                tv_this_month_earning!!.setText("$ " + response.body().eraning.thismonth)


                            if (!TextUtils.isEmpty(response.body().eraning.today))
                                tv_today_earning!!.setText("$ " + response.body().eraning.today)


                            if (!TextUtils.isEmpty(response.body().eraning.yesterday))
                                tv_yesterday_earning!!.setText("$ " + response.body().eraning.yesterday)

                        }



                        if(response.body().data!=null && response.body().data.size>0) {
                            for (i in 0..response.body().data.size - 1) {
                                var info: Datum? = Datum()
                                info!!.about = response.body().data[i].about
                                info!!.callDuration = response.body().data[i].callDuration
                                info!!.totalEarn = response.body().data[i].totalEarn
                                info!!.pFname = response.body().data[i].pFname
                                info!!.pLname = response.body().data[i].pLname
                                info!!.transDate = response.body().data[i].transDate
                                info!!.transTime = response.body().data[i].transTime
                                info!!.pProfPic = response.body().data[i].pProfPic
                                transList.add(info)
                            }
                            mAdapter = RecentTransactionAdapter(transList, activity)
                            val mLayoutManager = LinearLayoutManager(activity)
                            rv_recent_transaction!!.setLayoutManager(mLayoutManager)
                            rv_recent_transaction!!.setItemAnimator(DefaultItemAnimator())
                            rv_recent_transaction!!.setAdapter(mAdapter)
                        }
                        else
                        {
                            showSnackBar("No record exist")
                        }
                        hideLoading()
                    }else {
                        hideLoading()
                        showSnackBar(response.body().msg)

                    }


                }
            }



            override fun onFailure(call: Call<ModelProviderRecentTransaction>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_recent_transaction = view!!.findViewById(R.id.rv_recent_transaction);
        tv_today = view!!.findViewById(R.id.tv_today);
        tv_yesterday = view!!.findViewById(R.id.tv_yesterday);
        tv_this_month = view!!.findViewById(R.id.tv_this_month);
        tv_today_earning = view!!.findViewById(R.id.tv_today_earning);
        tv_yesterday_earning = view!!.findViewById(R.id.tv_yesterday_earning);
        tv_this_month_earning = view!!.findViewById(R.id.tv_this_month_earning);
        toolbarsetting();
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EarningList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EarningList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    private fun prepareMovieData() {
//
//        movieList = ArrayList()
//        var movie = Movie("Mad Max: Fury Road", "Action & Adventure", "2015")
//        movieList.add(movie)
//
//        movie = Movie("Inside Out", "Animation, Kids & Family", "2015")
//        movieList.add(movie)
//
//        movie = Movie(
//            "Star Wars: Episode VII - The Force Awakens",
//            "Action",
//            "2015"
//        )
//        movieList.add(movie)
//
//        movie = Movie("Shaun the Sheep", "Animation", "2015")
//        movieList.add(movie)
//
//        movie = Movie("The Martian", "Science Fiction & Fantasy", "2015")
//        movieList.add(movie)
//
//        movie = Movie("Mission: Impossible Rogue Nation", "Action", "2015")
//        movieList.add(movie)
//
//        movie = Movie("Up", "Animation", "2009")
//        movieList.add(movie)
//
//        movie = Movie("Star Trek", "Science Fiction", "2009")
//        movieList.add(movie)
//
//        movie = Movie("The LEGO Movie", "Animation", "2014")
//        movieList.add(movie)
//
//        movie = Movie("Iron Man", "Action & Adventure", "2008")
//        movieList.add(movie)
//
//        movie = Movie("Aliens", "Science Fiction", "1986")
//        movieList.add(movie)
//
//        movie = Movie("Chicken Run", "Animation", "2000")
//        movieList.add(movie)
//
//        movie = Movie("Back to the Future", "Science Fiction", "1985")
//        movieList.add(movie)
//
//        movie = Movie("Raiders of the Lost Ark", "Action & Adventure", "1981")
//        movieList.add(movie)
//
//        movie = Movie("Goldfinger", "Action & Adventure", "1965")
//        movieList.add(movie)
//
//        movie = Movie(
//            "Guardians of the Galaxy",
//            "Science Fiction & Fantasy",
//            "2014"
//        )
//        movieList.add(movie)
//
////        mAdapter.notifyDataSetChanged()
//    }

}
