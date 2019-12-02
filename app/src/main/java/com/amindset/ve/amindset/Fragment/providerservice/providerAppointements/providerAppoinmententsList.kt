package com.amindset.ve.amindset.Fragment.providerservice.providerAppointements

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Fragment.userservice.Movie
import com.amindset.ve.amindset.R


class providerAppoinmententsList : BaseFragment() , View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0!!.id)
        {
            R.id.iv_toobar_back->
            {
                Onbacktoolbarsetting()
                activity!!.supportFragmentManager.popBackStackImmediate()
            }

            R.id.click_here->
            {
                val browserIntent : Intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/about.php"));
                startActivity(browserIntent);
            }

            R.id.faqs->
            {
                val browserIntent : Intent =  Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/faqs.php"));
                startActivity(browserIntent);
            }

        }
    }

    private lateinit var click_here : TextView
    private lateinit var faqs : TextView

    lateinit var movieList : ArrayList<Movie>
    lateinit  var mAdapter: ProviderAppoinmentsAdapter

    companion object {
        fun newInstance() = providerAppoinmententsList()
    }

    private lateinit var viewModel: ProviderAppoinmententsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.provider_appoinmentents_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProviderAppoinmententsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        click_here = view.findViewById(R.id.click_here)
        faqs = view.findViewById(R.id.faqs)

        val first : String= "to learn more about About Amindset"
        val next : String = "<font color='#1756d9'><u> Click here </u></font>"


        val firstt : String= "Check out our "
        val faq : String = "<font color='#1756d9'>Faqs</font>"
        val by : String= " by "

        val nextt : String = "<font color='#1756d9'><u> clicking here</u></font>"
        click_here!!.setText(Html.fromHtml(next + first));
        faqs!!.setText(Html.fromHtml(firstt + faq+by+ nextt ));


        click_here.setOnClickListener(this)
        faqs.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
         toolbarsetting();
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
        tv_tolbar_center_text.setText("Using app")
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE
        iv_toolbar_back.setOnClickListener(this)
    }

    //Custom ttolbar setting

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_navigation_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



}
