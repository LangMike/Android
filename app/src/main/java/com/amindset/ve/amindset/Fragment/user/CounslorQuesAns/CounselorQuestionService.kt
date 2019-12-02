package com.amindset.ve.amindset.Fragment.user.CounslorQuesAns

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Fragment.user.Counselorlist.Counselorlisting
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.Info
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.ModelQuestionList
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

class CounselorQuestionService : BaseFragment() , View.OnClickListener{



     override fun onClick(p0: View?) {
        when(p0!!.id)
        {
            R.id.next->
            {

                if(NetworkUtils.isNetworkConnected(activity)) {
                    val ft = fragmentManager!!.beginTransaction()
                    val counslerlisFrag: Counselorlisting = Counselorlisting()
                    val args = Bundle()
                    args.putString("id", value)
                    args.putString("title", title)
                    counslerlisFrag.arguments = args
                    ft.replace(R.id.child_fragment_container, counslerlisFrag, "next").addToBackStack(null)
                    ft.commitAllowingStateLoss()
                }
                else
                {
                    showMessage(R.string.msg_check_internet)
                }
            }

            R.id.iv_toobar_back->
            {
                Onbacktoolbarsetting()
                activity!!.supportFragmentManager.popBackStackImmediate()
            }

            R.id.tv_link->
            {

                val browserIntent : Intent =  Intent(Intent.ACTION_VIEW, Uri.parse("http://ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/admin/quetionaries.php"));
                   startActivity(browserIntent);
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


    private fun toolbarsetting(value:String) {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText(value)
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE
        iv_toolbar_back.setOnClickListener(this)
    }

    //Decalre var

    private lateinit var recyclerView : RecyclerView
    private lateinit var tv_next : TextView
    private lateinit var tv_link : TextView

    var infoList = ArrayList<Info>()
    lateinit  var mAdapter: CounselorQuestionAnswerAdapter

    var  value : String = ""
    var  title : String = ""
    lateinit var  mLayoutManager : LinearLayoutManager

    var pref : AppPreferencesHelper? = null


    private lateinit var viewModel: CounselorQuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = AppPreferencesHelper(activity)

        if(arguments!!.getString("id") != null) {
            value = arguments!!.getString("id")
            title= arguments!!.getString("title")
            toolbarsetting(arguments!!.getString("title"))
            infoList.clear()

            APItoGetQuestion(arguments!!.getString("id"));
        }

        return inflater.inflate(R.layout.question_fragment, container, false)
    }

    private fun APItoGetQuestion(proficiency_id : String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["proficiency_id"] = proficiency_id

        val call = apiService.getQuestionList(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelQuestionList> {

            override fun onResponse(call: Call<ModelQuestionList>, response: Response<ModelQuestionList>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {

//                        showMessage(response.body().info[0].question)

                        for(i in 0..response.body().info.size - 1) {
                            var info : Info? = Info()
                            info!!.question = response.body().info[i].question
                            info!!.answer = response.body().info[i].answer
                            info!!.queId = response.body().info[i].queId
                            infoList.add(info)
                        }
                        mAdapter = CounselorQuestionAnswerAdapter(infoList,activity)
                        mLayoutManager = LinearLayoutManager(activity)
                        recyclerView.setLayoutManager(mLayoutManager)
                        recyclerView.setItemAnimator(DefaultItemAnimator())
                        recyclerView.setAdapter(mAdapter)
                        hideLoading()
                    }else {
                        hideLoading()
                    }
                }
            }



            override fun onFailure(call: Call<ModelQuestionList>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CounselorQuestionViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onResume() {
        super.onResume()
        recyclerView = activity!!.findViewById(R.id.fav_recylerview) as RecyclerView
        tv_next = activity!!.findViewById(R.id.next) as TextView
        tv_link = activity!!.findViewById(R.id.tv_link) as TextView

         val first : String= "Providers only treat non-emergency issues, if you are experiencing  "
         val next : String = "<font color='#EE0000'><u>an emergency</u></font>"
         val third : String= " , do not use AMINDSET, click "
         val four : String = "<font color='#EE0000'><u>here</u?</font>"


        tv_link.setText(Html.fromHtml(first + next + third + four));


        tv_link.setOnClickListener(this)
        tv_next.setOnClickListener(this)

//        prepareMovieData

        toolbarsetting(title);




    }

//    fun onClickNext(view : View)
//    {
//        val ft = fragmentManager!!.beginTransaction()
//        ft.replace(R.id.child_fragment_container, Counselorlisting(), "next").addToBackStack(null)
//        ft.commitAllowingStateLoss()
//    }



}
