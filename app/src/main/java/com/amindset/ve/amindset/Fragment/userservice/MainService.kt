package com.amindset.ve.amindset.Fragment.userservice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.CardView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Fragment.providerservice.providerAppointements.providerAppoinmententsList
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.CounselorQuestionService
import com.amindset.ve.amindset.Fragment.user.FavList.FavListFragment
import com.amindset.ve.amindset.Fragment.userservice.Model.ModelUserService
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import retrofit2.Call
import retrofit2.Response
import java.util.*




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var card_view_whattreat : CardView? = null
private var card_view_counseleor : CardView? = null

private var card_view_medical : CardView? = null
private var card_view_pharmacist : CardView? = null
private var card_view_favlist : CardView? = null


private var card_view_using_app : CardView? = null



private var  ll_main_under_cardview : LinearLayout?=null


private var llparent : LinearLayout? = null

private var tv_servicetitle : TextView?=null
private var tv_details : TextView?=null

private var tv_servicetitle2 : TextView?=null
private var tv_details2 : TextView?=null

private var tv_servicetitle3 : TextView?=null
private var tv_details3 : TextView?=null
private var tv_details_whatwetrip : TextView?=null

class MainService : BaseFragment(),View.OnClickListener {


    override fun onClick(p0: View?) {

        when(p0!!.id)
        {
            R.id.card_view_counseleor->{


                if(NetworkUtils.isNetworkConnected(activity)) {
                    val ft = fragmentManager!!.beginTransaction()
                    val counFrag : CounselorQuestionService = CounselorQuestionService()
                    val args = Bundle()
                    args.putString("id", hashMap.get(""+0))
                    args.putString("title","Counselor")
                    counFrag.arguments = args
                    ft.replace(R.id.child_fragment_container, counFrag, "Counselor")
                        .addToBackStack(null)
                    ft.commitAllowingStateLoss()

                }
                else
                {
                    showMessage(R.string.msg_check_internet)
                }
            }

            R.id.card_view_medical->{


                if(NetworkUtils.isNetworkConnected(activity)) {
                val ft = fragmentManager!!.beginTransaction()
                    val counMedical : CounselorQuestionService = CounselorQuestionService()
                    val args = Bundle()
                    args.putString("id", hashMap.get(""+1))
                    args.putString("title", "Medical Providers")
                    counMedical.arguments = args
                   ft.replace(R.id.child_fragment_container, counMedical, "medical").addToBackStack(null)
                   ft.commitAllowingStateLoss()
                }
                else
                {
                    showMessage(R.string.msg_check_internet)
                }
            }

            R.id.card_view_pharmacist->{
                if(NetworkUtils.isNetworkConnected(activity)) {
                val ft = fragmentManager!!.beginTransaction()

                    val counPharma : CounselorQuestionService = CounselorQuestionService()
                    val args = Bundle()
                    args.putString("id", hashMap.get(""+2))
                    args.putString("title", "Pharmacist")
                    counPharma.arguments = args
                ft.replace(R.id.child_fragment_container, counPharma, "pharmacist").addToBackStack(null)
                ft.commitAllowingStateLoss()
                }
                else
                {
                    showMessage(R.string.msg_check_internet)
                }
            }

            R.id.card_view_favlist->{
                if(NetworkUtils.isNetworkConnected(activity)) {

                val ft = fragmentManager!!.beginTransaction()
                ft.replace(R.id.child_fragment_container, FavListFragment(), "pharmacist").addToBackStack(null)
                ft.commitAllowingStateLoss()
            }
            else
            {
                showMessage(R.string.msg_check_internet)
            }
            }

            R.id.card_view_whattreat->{
//                if(NetworkUtils.isNetworkConnected(activity)) {
//
//                    val ft = fragmentManager!!.beginTransaction()
//                    ft.replace(R.id.child_fragment_container, FavListFragment(), "pharmacist").addToBackStack(null)
//                    ft.commitAllowingStateLoss()
//                }
//                else
//                {
//                    showMessage(R.string.msg_check_internet)
//                }
            }


            R.id.card_view_using_app->{

                val ft = fragmentManager!!.beginTransaction()
                ft.replace(R.id.flContent, providerAppoinmententsList(), "appointmentlist").addToBackStack(null)
                ft.commitAllowingStateLoss()
//                if(NetworkUtils.isNetworkConnected(activity)) {
//
//                    val ft = fragmentManager!!.beginTransaction()
//                    ft.replace(R.id.child_fragment_container, FavListFragment(), "pharmacist").addToBackStack(null)
//                    ft.commitAllowingStateLoss()
//                }
//                else
//                {
//                    showMessage(R.string.msg_check_internet)
//                }
            }

        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val hashMap:HashMap<String,String> = HashMap<String,String>() //define empty hashmap
    private lateinit var id : Integer
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {card_view_favlist
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_view_whattreat = view!!.findViewById(R.id.card_view_whattreat);
        card_view_counseleor = view!!.findViewById(R.id.card_view_counseleor);
        card_view_medical = view!!.findViewById(R.id.card_view_medical);

        card_view_using_app = view!!.findViewById(R.id.card_view_using_app);

        card_view_using_app!!.setOnClickListener(this)

        card_view_pharmacist = view!!.findViewById(R.id.card_view_pharmacist);
        card_view_favlist = view!!.findViewById(R.id.card_view_favlist);

        ll_main_under_cardview = view!!.findViewById(R.id.ll_main_under_cardview)


        tv_servicetitle = view!!.findViewById(R.id.tv_servicetitle)
        tv_details = view!!.findViewById(R.id.tv_details)
        tv_servicetitle2 = view!!.findViewById(R.id.tv_servicetitle2)
        tv_details2 = view!!.findViewById(R.id.tv_details2)
        tv_servicetitle3 = view!!.findViewById(R.id.tv_servicetitle3)
        tv_details3 = view!!.findViewById(R.id.tv_details3)
        tv_details_whatwetrip = view!!.findViewById(R.id.tv_details_whatwetrip)


        val first : String= "Save a trip and see one of our providers, to see what we treat"
        val next : String = "<font color='#1756d9'><u> click here</u></font>"


        tv_details_whatwetrip!!.setText(Html.fromHtml(first + next));

        http@ //ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/what-we-treat.php
        tv_details_whatwetrip!!.setOnClickListener(View.OnClickListener {
            val browserIntent : Intent =
                Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/what-we-treat.php"));
            startActivity(browserIntent);
        })

        llparent = view.findViewById(R.id.llparent);

        card_view_whattreat!!.setOnClickListener(this)

        card_view_counseleor!!.setOnClickListener(this)

        card_view_medical!!.setOnClickListener(this)

        card_view_pharmacist!!.setOnClickListener(this)

        card_view_favlist!!.setOnClickListener(this)

        getUserService();

    }



    private fun getUserService() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val call = apiService.getUserService()

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserService> {

            override fun onResponse(call: Call<ModelUserService>, response: Response<ModelUserService>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        for(i in 0..response.body().info.size - 1) {

                            hashMap.set(""+i,response.body().info.get(i).id)

                            if(i==0) {
                                tv_servicetitle!!.setText(response.body().info.get(i).proficiency)
//                                tv_details!!.setText(response.body().info.get(i).proficiency)
                            }

                            if(i==1) {
                                tv_servicetitle2!!.setText(response.body().info.get(i).proficiency)
//                                tv_details2!!.setText(response.body().info.get(i).proficiency)

                            }

                            if(i==2) {
                                tv_servicetitle3!!.setText(response.body().info.get(i).proficiency)
//                                tv_details3!!.setText(response.body().info.get(i).proficiency)

                            }
                        }
                        hideLoading()
                    }else {
                        hideLoading()
                    }
                }
            }



            override fun onFailure(call: Call<ModelUserService>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }



}
