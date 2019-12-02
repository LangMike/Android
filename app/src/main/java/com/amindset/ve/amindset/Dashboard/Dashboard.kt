package com.amindset.ve.amindset.Dashboard

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.Fragment.providerservice.ProviderService
import com.amindset.ve.amindset.Fragment.user.Appoinment.UserAppoinments
import com.amindset.ve.amindset.Fragment.user.Notification.Notification
import com.amindset.ve.amindset.Fragment.user.Provider.ProviderListing
import com.amindset.ve.amindset.Fragment.user.UserProfile.UserProfileFragment
import com.amindset.ve.amindset.Fragment.userservice.MainService
import com.amindset.ve.amindset.Fragment.userservice.Movie
import com.amindset.ve.amindset.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*


class Dashboard : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var recyclerView : RecyclerView

    lateinit var movieList : ArrayList<Movie>
    lateinit  var mAdapter: NavAdapter

    var selectionTye : String?=null;
    private lateinit var menu: Menu

    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var iv_notification : ImageView

    //    var menu = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        iv_notification = findViewById<View>(R.id.iv_notification) as ImageView
         bottomNavigationView.setItemIconTintList(null);

       iv_notification.setOnClickListener(View.OnClickListener {
           var fragment: Notification = Notification();
//           bottomNavigationView.visibility = View.GONE

           val args = Bundle()
           if(selectionTye.equals("user")) {

               args.putString("id", "user")
           }else
           {
               args.putString("id", "provider")

           }
           fragment.arguments = args
           val ft = supportFragmentManager.beginTransaction()
           ft.add(R.id.flContent, fragment).addToBackStack(null)
           ft.commit()})


        menu = bottomNavigationView.menu
        toggleColor(menu.findItem(R.id.action_home))
        menu.findItem(R.id.action_home).setIcon(R.mipmap.ic_home_tab_on)



        getSigninActiviyData();

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            toggleColor(item);
            when (item.itemId) {
                R.id.action_home -> {
                    item.setIcon(R.mipmap.ic_home_tab_on)
                    menu.findItem(R.id.action_appoitment).setIcon(R.mipmap.ic_recent_user_tab_off)
                    menu.findItem(R.id.action_providers).setIcon(R.mipmap.ic_provider_tab_off)
                    menu.findItem(R.id.action_profile).setIcon(R.mipmap.ic_user_tab_off)

                    var fragmentMain : MainService =
                        MainService();
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.flContent, fragmentMain).addToBackStack(null)
                    ft.commit()
                    Onbacktoolbarsetting()

                    for (fragmentMain in supportFragmentManager.fragments) {
                        supportFragmentManager.beginTransaction().remove(fragmentMain).commit()
                    }


                }
                R.id.action_profile -> {
                    menu.findItem(R.id.action_home).setIcon(R.mipmap.ic_home_tab_off)
                    menu.findItem(R.id.action_appoitment).setIcon(R.mipmap.ic_recent_user_tab_off)
                    menu.findItem(R.id.action_providers).setIcon(R.mipmap.ic_provider_tab_off)

                    item.setIcon(R.mipmap.ic_user_tab_on)

                    var fragment : UserProfileFragment = UserProfileFragment();
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.flContent, fragment).addToBackStack(null)
                    ft.commit()

                }
                R.id.action_providers -> {

                    item.setIcon(R.mipmap.ic_provider_tab_on)
                    menu.findItem(R.id.action_home).setIcon(R.mipmap.ic_home_tab_off)
                    menu.findItem(R.id.action_appoitment).setIcon(R.mipmap.ic_recent_user_tab_off)
                    menu.findItem(R.id.action_profile).setIcon(R.mipmap.ic_user_tab_off)
                    var fragment : ProviderListing = ProviderListing();
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.flContent, fragment).addToBackStack(null)
                    ft.commit()

                }
                R.id.action_appoitment -> {

                    item.setIcon(R.mipmap.ic_recent_user_tab_on)
                    menu.findItem(R.id.action_home).setIcon(R.mipmap.ic_home_tab_off)
                    menu.findItem(R.id.action_providers).setIcon(R.mipmap.ic_provider_tab_off)
                    menu.findItem(R.id.action_profile).setIcon(R.mipmap.ic_user_tab_off)
                    var fragment : UserAppoinments = UserAppoinments();
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.flContent, fragment).addToBackStack(null)
                    ft.commit()
                }
            }
            false
        }

   if(selectionTye.equals("user")) {
//
       var fragment: MainService =
           MainService();
       val ft = supportFragmentManager.beginTransaction()
       ft.add(R.id.flContent, fragment).addToBackStack(null)
       ft.commit()
   }
        else
   {
       var fragment: ProviderService =
           ProviderService();
       bottomNavigationView.visibility = View.GONE
       val ft = supportFragmentManager.beginTransaction()
       ft.add(R.id.flContent, fragment).addToBackStack(null)
       ft.commit()
   }

    }
    private fun Onbacktoolbarsetting() {
        val tv_tolbar_center_text =findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.amindset));
        tv_tolbar_center_text.setText("")
        val iv_toolbar_back =findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()

            finish()
        }

        this.doubleBackToExitPressedOnce = true
        showMessage("Please click BACK again to exit")

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun getSigninActiviyData() {

             if(intent.getStringExtra("type")!=null)
             selectionTye= intent.getStringExtra("type")
    }

    private fun toggleColor(item: MenuItem) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val selectedTitle = SpannableString(item.getTitle().toString())

        val homeTitle = SpannableString(menu.findItem(R.id.action_home).getTitle().toString())
        val profileTitle = SpannableString(menu.findItem(R.id.action_profile).getTitle().toString())
        val providerTitle = SpannableString(menu.findItem(R.id.action_providers).getTitle().toString())
        val appoitmentTitle = SpannableString(menu.findItem(R.id.action_appoitment).getTitle().toString())

        homeTitle.setSpan(ForegroundColorSpan(getColor(android.R.color.darker_gray)), 0, homeTitle.length, 0)
        profileTitle.setSpan(ForegroundColorSpan(getColor(android.R.color.darker_gray)), 0, profileTitle.length, 0)
        providerTitle.setSpan(ForegroundColorSpan(getColor(android.R.color.darker_gray)), 0, providerTitle.length, 0)
        appoitmentTitle.setSpan(ForegroundColorSpan(getColor(android.R.color.darker_gray)), 0, appoitmentTitle.length, 0)
        selectedTitle.setSpan(ForegroundColorSpan(getColor(R.color.colorPrimary)), 0, selectedTitle.length, 0)

        menu.findItem(R.id.action_home).setTitle(homeTitle);
        menu.findItem(R.id.action_profile).setTitle(profileTitle);
        menu.findItem(R.id.action_providers).setTitle(providerTitle);
        menu.findItem(R.id.action_appoitment).setTitle(appoitmentTitle);


        item.setTitle(selectedTitle)
    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//    }
}
