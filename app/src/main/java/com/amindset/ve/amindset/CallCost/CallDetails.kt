package com.amindset.ve.amindset.CallCost

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.CallCost.ProviderCharge.ProviderGetChargeDetails
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CallDetails : BaseActivity() , View.OnClickListener{
    // Deaclre varaible
    lateinit var tv_starttime: TextView
    lateinit var name: TextView
    lateinit var tv_endtime: TextView
    lateinit var tv_durationtime: TextView
    lateinit var tv_totalcost: TextView
    lateinit var submit: TextView
    lateinit var ratingBar: RatingBar
    lateinit var profile_image: CircleImageView
     lateinit var iv_toobar_back : ImageView
    lateinit var costPermin : String
    lateinit var diff : String
    lateinit var ratingValue : String
    lateinit var  pref : AppPreferencesHelper
    var totalCost:Float = 0.0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_call_details)
            pref = AppPreferencesHelper(this)
            getView()
            getDataFromPreviousCallDetailsActivity()
        }catch (e : Exception){
            e.printStackTrace()
        }

    }
    override fun onClick(id: View?) {
        when(id!!.id)
        {
            R.id.iv_toobar_back->{
                finish()
            }

            R.id.submit->{
//                getProviderCharge();

                if(submit.text.toString().equals("Ok"))
                {
                    finish()
                }

                else {
//                    getProviderCharge();

//                    APItoGetSubmitCallDetails("");

                    APItoGetSubmitReview()
                }
            }

        }
    }

    private fun getView() {
        iv_toobar_back = findViewById(R.id.iv_toobar_back)
        iv_toobar_back.setOnClickListener(this)
        tv_starttime = findViewById(R.id.tv_starttime)
        name = findViewById(R.id.name)
        tv_endtime = findViewById(R.id.tv_endtime)
        tv_durationtime = findViewById(R.id.tv_durationtime)
        tv_totalcost = findViewById(R.id.tv_totalcost)
        submit = findViewById(R.id.submit)
        submit.setOnClickListener(this)
        ratingBar = findViewById(R.id.ratingBar)
        profile_image = findViewById(R.id.profile_image)

        ratingBar.setOnClickListener {
            ratingValue   = ratingBar.rating.toString()
        }
    }

    private fun getDataFromPreviousCallDetailsActivity() {


        if(!TextUtils.isEmpty(intent.getStringExtra("type")) &&
            (intent.getStringExtra("type").equals("vdo") || intent.getStringExtra("type").equals("video")))
        {


            if(intent.getStringExtra("callFrom").equals("false"))
            {

                if (!TextUtils.isEmpty(intent.getStringExtra("disconnectTime"))) {
                    tv_endtime.setText(intent.getStringExtra("disconnectTime"))
                }

                if (!TextUtils.isEmpty(intent.getStringExtra("startTime"))) {
                    tv_starttime.setText(intent.getStringExtra("startTime"))
                }

                if (!TextUtils.isEmpty(intent.getStringExtra("providerImage"))) {
                    Picasso.get().load(intent.getStringExtra("providerImage")).into(profile_image)
                }

                if (!TextUtils.isEmpty(intent.getStringExtra("providerName"))) {
                    name.setText(intent.getStringExtra("providerName"))
                }

                if (!TextUtils.isEmpty(intent.getStringExtra("rate"))) {
                    costPermin = intent.getStringExtra("rate")
                }

                TimeDifference(
                    intent.getStringExtra("disconnectTime"),
                    intent.getStringExtra("startTime"), costPermin
                )

                APItoGetSubmitCallDetails("2");

            }

            else {

               submit.visibility= View.GONE
                getProviderCharge();
            }

        }
         else
          {
             if (intent.getStringExtra("callFrom").equals("activity")) {
            if (!TextUtils.isEmpty(intent.getStringExtra("disconnectTime"))) {
                tv_endtime.setText(intent.getStringExtra("disconnectTime"))
            }

            if (!TextUtils.isEmpty(intent.getStringExtra("startTime"))) {
                tv_starttime.setText(intent.getStringExtra("startTime"))
            }

            if (!TextUtils.isEmpty(intent.getStringExtra("providerImage"))) {
                Picasso.get().load(intent.getStringExtra("providerImage")).into(profile_image)
            }

            if (!TextUtils.isEmpty(intent.getStringExtra("providerName"))) {
                name.setText(intent.getStringExtra("providerName"))
            }

            if (!TextUtils.isEmpty(intent.getStringExtra("rate"))) {
                costPermin = intent.getStringExtra("rate")
            }

            TimeDifference(
                intent.getStringExtra("disconnectTime"),
                intent.getStringExtra("startTime"), costPermin
            )

                 APItoGetSubmitCallDetails("1");

             } else {
            getProviderCharge();
                 submit.visibility= View.GONE

             }
    }}

    private fun getProviderCharge() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["call_to"] = pref.providerTherap_id
        paramObject["call_from"] = intent.getStringExtra("pat_id")

        val call = apiService.getProviderChargeDetails(paramObject , "Bearer " + pref.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ProviderGetChargeDetails> {

            override fun onResponse(call: Call<ProviderGetChargeDetails>, response: Response<ProviderGetChargeDetails>?) {

                if (response != null) {
                    if (response.isSuccessful && response.body().status.equals(Constant.RESPONSE_SUCCESSFULLY))
                    {

                        if(response.body().info!=null)
                        {
                            if(!TextUtils.isEmpty(response.body().info.callDuration))
                                tv_durationtime.setText(response.body().info.callDuration);

                            if(!TextUtils.isEmpty(response.body().info.callTime))
                                tv_starttime.setText(response.body().info.callDuration)

                            if(!TextUtils.isEmpty(response.body().info.endAt))
                                tv_endtime.setText(response.body().info.endAt)

                            if(!TextUtils.isEmpty(response.body().info.cost))
                                tv_totalcost.setText(response.body().info.cost + "$")


                        }
                        showMessage(response.body().info.cost)
                        hideLoading()
                    }else {
                        hideLoading()
                        showMessage(getString(R.string.error_some_problem_occur))

                    }
                }
            }



            override fun onFailure(call: Call<ProviderGetChargeDetails>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    private fun TimeDifference(stringDate1: String?, stringDate2: String? , cost : String) {
        try {
                val sdf = SimpleDateFormat("hh:mm:ss aa")
                val date1 = sdf.parse(stringDate1)
                val date2 = sdf.parse(stringDate2)
                var mills = date1.getTime() - date2.getTime()
                var hours = mills / (1000 * 60 * 60)
                var mins = ((mills / (1000 * 60)) % 60).toInt()
                var sec= (mills /1000)%60
             diff = "" + hours + ":" + mins + ":" + sec
            tv_durationtime.setText(diff)
                castCalculation(hours,mins,sec,cost)
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }

    private fun castCalculation(hours: Long, mins: Int, sec: Long , cost : String) {


        var totalSecondCost : Float = 0.0f
        var totalMinCost : Int = 0
        var totalhourCost : Int = 0

        if(sec>0)
        {
            var persec:Float = ((Integer.parseInt(cost).toFloat()) / 60).toFloat()
            totalSecondCost  = sec * persec
        }

        if(mins>0)
        {

            totalMinCost = mins * Integer.parseInt(cost);
        }

        if(hours>0)
        {
            totalMinCost = mins * Integer.parseInt(cost) * 60;

        }

        totalCost = totalSecondCost+totalMinCost+totalhourCost
        tv_totalcost.setText(""+ totalCost + " $")

    }



    private fun APItoGetSubmitReview() {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["to_therap"] = intent.getStringExtra("t_id")
        paramObject["rating"] = ratingBar.rating.toString()

        val call = apiService.saveCallRating(paramObject , "Bearer " + pref.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserSaveRating> {

            override fun onResponse(call: Call<ModelUserSaveRating>, response: Response<ModelUserSaveRating>?) {

                if (response != null) {
                    if (response.isSuccessful && response.body().status.equals(Constant.RESPONSE_SUCCESSFULLY))
                    {
                        showMessage(response.body().message)
                        hideLoading()
                        finish()
                    }else {
                        hideLoading()
                        showMessage(response.body().message)

                    }
                }
            }



            override fun onFailure(call: Call<ModelUserSaveRating>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }




    private fun APItoGetSubmitCallDetails(call_type : String) {
        val apiService = ApiClient.getClient().create(WebService::class.java)
        val paramObject = HashMap<String, String>()
        paramObject["therap_id"] = intent.getStringExtra("t_id")
        paramObject["pat_id"] = pref.userPat_id
        paramObject["call_type"] = call_type
        paramObject["total_call_cost"] = ""+totalCost
        paramObject["provider_call_cost"] = " "
        paramObject["start_at"] = intent.getStringExtra("startTime")
        paramObject["end_at"] =  intent.getStringExtra("disconnectTime")
        paramObject["duration"] = diff
        paramObject["ratings"] = "-1"

        val call = apiService.saveCallDetails(paramObject , "Bearer " + pref.userBToken)
        showLoading()
        call.enqueue(object : retrofit2.Callback<ModelUserCallDetails> {

            override fun onResponse(call: Call<ModelUserCallDetails>, response: Response<ModelUserCallDetails>?) {
                if (response != null) {

                    try{
                    if (response.isSuccessful && response.body().status.equals(Constant.RESPONSE_SUCCESSFULLY))
                    {
                        showSnackBar(response.body().message)
                        hideLoading()
                    }else {
                        hideLoading()
                        showSnackBar("Amount too small")

                    }
                }catch (e :java.lang.Exception)
                    {

                    }
                }
            }



            override fun onFailure(call: Call<ModelUserCallDetails>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

}
