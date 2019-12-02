package com.amindset.ve.amindset.Fragment.user.FavList

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Dashboard.NavAdapter
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.CounselorQuestionAnswerAdapter
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.Info
import com.amindset.ve.amindset.Fragment.user.CounslorQuesAns.Model.ModelQuestionList
import com.amindset.ve.amindset.Fragment.userservice.ModelFavList.Datum
import com.amindset.ve.amindset.Fragment.userservice.ModelFavList.ModelUserFavList
import com.amindset.ve.amindset.Fragment.userservice.Movie

import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import retrofit2.Call
import retrofit2.Response
import java.util.HashMap

class FavListFragment : BaseFragment() {

    //Declare varaibles

    private lateinit var recyclerView : RecyclerView

    lateinit  var mAdapter: NavAdapter
    var infoList = ArrayList<Datum>()

    var pref : AppPreferencesHelper? = null


    companion object {
        fun newInstance() = FavListFragment()
    }

    private lateinit var viewModel: FavListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = AppPreferencesHelper(activity)

        if(infoList!=null)
            infoList.clear()

        APItoGetUserFavList();

        return inflater.inflate(R.layout.fav_list_fragment, container, false)
    }

      public fun APItoGetUserFavList() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["usertype"] = "1"

        val call = apiService.getUserFavList(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserFavList> {

            override fun onResponse(call: Call<ModelUserFavList>, response: Response<ModelUserFavList>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {

                        for(i in 0..response.body().data.size - 1) {
                            var info : Datum? = Datum()
                            info!!.tFname = response.body().data[i].tFname
                            info!!.tAbout = response.body().data[i].tAbout
                            info!!.tProfPic = response.body().data[i].tProfPic
                            info!!.tFname = response.body().data[i].tFname
                            info!!.favId = response.body().data[i].favId
                            info!!.favUserid = response.body().data[i].favUserid
                            infoList.add(info)
                        }
                        mAdapter = NavAdapter(infoList, pref!!.userBToken , activity , this@FavListFragment)
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



            override fun onFailure(call: Call<ModelUserFavList>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = activity!!.findViewById(R.id.fav_recylerview) as RecyclerView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavListViewModel::class.java)
        // TODO: Use the ViewModel
    }


    fun onClickNext(view : View)
    {
        
    }

}
