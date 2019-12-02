package com.amindset.ve.amindset.Fragment.providerservice

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Fragment.providerservice.providerAppointements.providerAppoinmententsList
import com.amindset.ve.amindset.Fragment.providerservice.providerEarning.EarningList
import com.amindset.ve.amindset.Fragment.providerservice.providerRecentList.providerRecentList
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ProviderProfile

import com.amindset.ve.amindset.R

class ProviderService : BaseFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0!!.id)
        {
            R.id.card_view_earning->
            {
                val ft = fragmentManager!!.beginTransaction()
                ft.replace(R.id.flContent, EarningList(), "appointmentlist").addToBackStack(null)
                ft.commitAllowingStateLoss()
            }

            R.id.card_view_using_app->
            {
                val ft = fragmentManager!!.beginTransaction()
                ft.replace(R.id.flContent, providerAppoinmententsList(), "appointmentlist").addToBackStack(null)
                ft.commitAllowingStateLoss()
            }

            R.id.card_view_recent->
            {
//                var fragment: RecentlyList =
//                    RecentlyList();
                val ft = fragmentManager!!.beginTransaction()
                ft.replace(R.id.flContent, providerRecentList(), "recentlist").addToBackStack(null)
                ft.commitAllowingStateLoss()
            }
            R.id.card_view_profile->
            {
                val ft = fragmentManager!!.beginTransaction()
                ft.replace(R.id.flContent, ProviderProfile(), "providerProfile").addToBackStack(null)
                ft.commitAllowingStateLoss()
            }


        }
    }


    private var card_view_earning : CardView? = null
    private var card_view_using_app : CardView? = null
    private var card_view_recent : CardView? = null
    private var card_view_profile : CardView? = null

    companion object {
        fun newInstance() = ProviderService()
    }

    private lateinit var viewModel: ProviderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProviderViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_view_earning = view!!.findViewById(R.id.card_view_earning);
        card_view_using_app = view!!.findViewById(R.id.card_view_using_app);
        card_view_recent = view!!.findViewById(R.id.card_view_recent);
        card_view_profile = view!!.findViewById(R.id.card_view_profile);


        card_view_earning!!.setOnClickListener(this)
        card_view_using_app!!.setOnClickListener(this)
        card_view_recent!!.setOnClickListener(this)
        card_view_profile!!.setOnClickListener(this)

    }

}
