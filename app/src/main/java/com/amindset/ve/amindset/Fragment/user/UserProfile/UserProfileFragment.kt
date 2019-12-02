package com.amindset.ve.amindset.Fragment.user.UserProfile
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelAvtar.ModelUpdateAvtar
import com.amindset.ve.amindset.Fragment.user.UserProfile.Model.Info
import com.amindset.ve.amindset.Fragment.user.UserProfile.Model.ModelUserDetails
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelAboutYou.ModelAboutYou
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelChangePassword.ModelPassChange
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelEmergectCont.ModelEmergecyContact
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelLogout.ModelUserLogout
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelPayment.ModelUserPayment
import com.amindset.ve.amindset.Fragment.user.UserProfile.ModelUpdateProfile.ModelUserProfileUpdate
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Splash.splash
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import com.squareup.picasso.Picasso
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import com.stripe.android.view.CardInputWidget
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserProfileFragment : BaseFragment(),View.OnClickListener {

    //Declare Object

    var rl_security_setting : RelativeLayout?=null
    var rl_emergrncy_conctact : RelativeLayout?=null
    var rl_payment_settings : RelativeLayout?=null
    var rl_about_you : RelativeLayout?=null
    var legal : RelativeLayout?=null

    lateinit var profile_image : ImageView
    lateinit var tv_age : TextView
    lateinit var tv_height : TextView
    lateinit var tv_weight : TextView
    lateinit var phonenumber : TextView
    lateinit var emailId : TextView
    lateinit var et_aboutYou : EditText
    lateinit var logout : TextView
    lateinit var user_name : TextView
    lateinit var et_password : EditText
    lateinit var et_phoneNumber : EditText
    lateinit var et_emer_name : EditText
    lateinit var tv_saveEmergencyContact : TextView
    lateinit var et_repassword : EditText
    lateinit var tv_change_password : TextView
    lateinit var tv_forgot_password : TextView
    lateinit var tv_emergencyContact : TextView
    lateinit var tv_saveabout : TextView
    lateinit var Edit : TextView


    lateinit var card_input_widget : CardInputWidget
//    lateinit var et_verficode2 : EditText
//    lateinit var et_verficode3 : EditText
//    lateinit var et_verficode4 : EditText


    lateinit var mCurrentPhotoPath : String

    lateinit var card_holder_name : EditText
//    lateinit var tv_cvv : EditText
//    lateinit var tv_exp_date : EditText

    internal  var photoFile: File?=null
    internal  var photoURI: Uri?=null
    var filepath : String = ""


    lateinit var tv_save_card_details : TextView
    lateinit var tv_update : TextView
    lateinit var et_user_age : TextInputEditText
    lateinit var et_user_height : TextInputEditText
    lateinit var et_user_weight : TextInputEditText
    lateinit var et_user_phone : TextInputEditText
    lateinit var et_user_email : TextInputEditText

    lateinit var et_aboutYou_dialog: EditText


    var  maintainEyeView : Boolean = false
    var  maintainEyeViewNewPasss : Boolean = false

    lateinit var dialog : Dialog
    lateinit var dialogPayment : Dialog
    lateinit var dialogUpdateProfile : Dialog
    lateinit var dialogEmergency : Dialog
    lateinit var save_details_provider_about_you: TextView
    lateinit var dialogAboutYou: Dialog





    private val integerFileHashMap = HashMap<Int, File>()
    private val integerURIHashMap = HashMap<Int, Uri>()

    companion object {
        fun newInstance() = UserProfileFragment()
    }

    private lateinit var viewModel: UserProfileViewModel

    var pref : AppPreferencesHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pref = AppPreferencesHelper(activity)
        if(NetworkUtils.isNetworkConnected(activity))
        APIforGetUserDeatils()
        else
            showMessage(R.string.msg_check_internet)
        return inflater.inflate(R.layout.user_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        legal = view.findViewById(R.id.legal)
        legal!!.setOnClickListener(this)
        profile_image = view.findViewById(R.id.profile_image)
        profile_image.setOnClickListener(this)
        tv_age = view.findViewById(R.id.tv_age)
        tv_height = view.findViewById(R.id.tv_height)
        tv_weight = view.findViewById(R.id.tv_weight)
        phonenumber = view.findViewById(R.id.phonenumber)
        emailId = view.findViewById(R.id.emailId)
        et_aboutYou = view.findViewById(R.id.et_aboutYou)
        logout = view.findViewById(R.id.logout)
        user_name = view.findViewById(R.id.user_name)
        tv_saveabout = view.findViewById(R.id.tv_saveabout)
        tv_emergencyContact = view.findViewById(R.id.tv_emergencyContact)
        tv_saveabout = view.findViewById(R.id.tv_saveabout)
        Edit = view.findViewById(R.id.Edit)



        Edit.setOnClickListener(this)

        rl_security_setting = view.findViewById(R.id.rl_security_setting)
        rl_emergrncy_conctact = view.findViewById(R.id.rl_emergrncy_conctact)

        rl_payment_settings = view.findViewById(R.id.rl_payment_settings)
        rl_payment_settings!!.setOnClickListener(this)

        rl_about_you = view.findViewById(R.id.rl_about_you)
        rl_about_you!!.setOnClickListener(this)

        rl_security_setting!!.setOnClickListener(this)
        rl_emergrncy_conctact!!.setOnClickListener(this)
        tv_saveabout!!.setOnClickListener(this)
        logout!!.setOnClickListener(this)
    }

     private fun checkCardsDetail(card_holder_name : String) {

        val cardToSave = card_input_widget.getCard();

        if (cardToSave == null) {
            showMessage("Invalid Card Details")
        } else {
            if (NetworkUtils.isNetworkConnected(activity)) {

                showLoading()
                generateToken(cardToSave,card_holder_name);
            } else {
                showMessage(R.string.msg_check_internet)
            }
        }
    }
   // "pk_test_6U2hreecErPSjVv71hfoah6700Owl8lSy3"
  //  "pk_live_ojaRvkfthNfamSPdSb4HW11M" live key
    fun generateToken(card : Card , card_holder_name :String ) {
        val stripe = Stripe(activity!!,"pk_live_ojaRvkfthNfamSPdSb4HW11M");
        stripe.createToken(
                card,  object : TokenCallback {
                override fun onSuccess(token: Token?) {
                    Log.v("Token!","Token Created!!"+ token!!.getId())
                    hideLoading()
                    APIforSaveUserCardDetails(card.cvc,""+card.expMonth,""+card.expYear,
                        card.number,token.id,card_holder_name)
                    chargeCard(token.id);
                }

                override fun onError(error: Exception?) {
                    hideLoading()

                    showSnackBar(""+error!!.message)
                    error.printStackTrace()
                }

            })

   }

    private fun chargeCard(token: String?) {
        // Pass that token, amount to your server using API to process payment.

    }

    override fun onClick(p0: View?) {

        when(p0!!.id)
        {
            R.id.rl_security_setting-> {
              displaysecuritySettingDialog();
            }

            R.id.profile_image-> {

                selectImage(1)

            }

            R.id.legal->
            {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Legal")
                builder.setItems(arrayOf<CharSequence>("Term/Condition", "Privacy", "Faqs"),
                    object:DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which:Int) {
                            // The 'which' argument contains the index position
                            // of the selected item
                            when (which) {
                                0 -> {
                                    val browserIntent: Intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("http://ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/terms-conditions.php")
                                    );
                                    startActivity(browserIntent);
                                }
                                1 -> {
                                    val browserIntent : Intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse("http://ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/privacy-policy.php"));
                                    startActivity(browserIntent);
                                }
                                2 -> {
                                    val browserIntent : Intent =  Intent(Intent.ACTION_VIEW, Uri.parse("http://ec2-52-15-107-221.us-east-2.compute.amazonaws.com/amindset/faqs.php"));
                                    startActivity(browserIntent);
                                }
                            }
                        }
                    })
                builder.create().show()
            }

            R.id.rl_payment_settings-> {
                displayPaymentSettingDialog()
            }

            R.id.tv_forgot_password-> {
            }

            R.id.tv_save_card_details-> {
                if( !TextUtils.isEmpty(card_holder_name.text.toString()))
                {
                    if(NetworkUtils.isNetworkConnected(activity)) {

                        checkCardsDetail(card_holder_name.text.toString());
                    }
                    else
                    {
                        showMessage(R.string.msg_check_internet)
                    }

                }
                else
                {
                    showMessage(R.string.msg_fill_login_data)
                }

            }

            R.id.Edit-> {
                displayUpdateProfileDialog()
            }

            R.id.rl_about_you-> {
                displayAboutyouDialog(""+et_aboutYou.text.toString());

            }


            R.id.tv_update-> {
                if(!TextUtils.isEmpty(et_user_age.text.toString()) &&
                    !TextUtils.isEmpty(et_user_height.text.toString())  &&
                    !TextUtils.isEmpty(et_user_weight.text.toString())  &&
                    !TextUtils.isEmpty(et_user_email.text.toString())  &&
                    !TextUtils.isEmpty(et_user_phone.text.toString()))
                {
                    if(NetworkUtils.isNetworkConnected(activity)) {
                        APIforUpdateUserProfile(et_user_age.text.toString(),
                            et_user_height.text.toString(),
                            et_user_weight.text.toString(),
                            et_user_email.text.toString(),
                            et_user_phone.text.toString());

                    }
                    else
                    {
                        showMessage(R.string.msg_check_internet)
                    }

                }
                else
                {
                    showMessage(R.string.msg_fill_login_data)
                }
            }


            R.id.logout-> {

                if(NetworkUtils.isNetworkConnected(activity)) {
                    APIforUserLogout();
                }
                else
                {
                    showMessage(R.string.msg_check_internet)
                }

            }

            R.id.save_details_provider_about_you-> {
                hideKeyboard()
                if(!TextUtils.isEmpty(et_aboutYou_dialog.text.toString())) {
                    if(NetworkUtils.isNetworkConnected(activity)) {
                        APIforSaveAboutYou(et_aboutYou_dialog.text.toString());
                    }
                    else
                    {
                        showMessage(R.string.msg_check_internet)
                    }
                }
                else
                    showSnackBar("Please enter about you!")

            }


            R.id.tv_saveEmergencyContact-> {

                if(!TextUtils.isEmpty(et_phoneNumber.text.toString())) {
                    if(NetworkUtils.isNetworkConnected(activity)) {
                        APIforChangeEmergencyNumber(et_emer_name.text.toString(),et_phoneNumber.text.toString());
                    }
                    else
                    {
                        showMessage(R.string.msg_check_internet)
                    }
                }
                else
                    showSnackBar(getString(R.string.msg_fill_login_data))
            }

            R.id.tv_change_password-> {
                if(!TextUtils.isEmpty(et_password.text.toString()) &&
                    !TextUtils.isEmpty(et_repassword.text.toString())) {
                    if(NetworkUtils.isNetworkConnected(activity)) {
                        APIforChangePassword(et_password.text.toString(), et_repassword.text.toString());
                    }
                    else
                    {
                        showMessage(R.string.msg_check_internet)
                    }
                }
                else
                    showSnackBar(getString(R.string.msg_fill_login_data))
            }

            R.id.rl_emergrncy_conctact-> {
               showEmergencyContactDialog();
            }

            R.id.iv_toobar_back-> {
                Onbacktoolbarsetting()
                activity!!.supportFragmentManager.popBackStackImmediate()
            }

        }
    }

    private fun Onbacktoolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.mipmap.amindset));
        tv_tolbar_center_text.setText("")
//        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
//        iv_toolbar_back.visibility = View.GONE
    }

    private fun displayAboutyouDialog(aboutYouString : String ) {
        dialogAboutYou = Dialog(activity);
        dialogAboutYou.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAboutYou.setContentView(R.layout.dialog_provider_editaboutyou);

        et_aboutYou_dialog = dialogAboutYou!!.findViewById(R.id.et_aboutYou_dialog)

        et_aboutYou_dialog.setText(aboutYouString)


        save_details_provider_about_you = dialogAboutYou!!.findViewById(R.id.save_details_provider_about_you)
        save_details_provider_about_you.setOnClickListener(this)


        dialogAboutYou.show();
    }

    private fun showEmergencyContactDialog() {
        dialogEmergency  =  Dialog(activity);
        dialogEmergency.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEmergency.setContentView(R.layout.dialog_emergency_contact);
        et_phoneNumber = dialogEmergency.findViewById(R.id.et_phoneNumber)
        et_emer_name = dialogEmergency.findViewById(R.id.et_emer_name)
        tv_saveEmergencyContact = dialogEmergency.findViewById(R.id.tv_saveEmergencyContact)
        tv_saveEmergencyContact.setOnClickListener(this)
        dialogEmergency.show();
    }


    override fun onResume() {
        super.onResume()
        toolbarsetting()
    }


    private fun toolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText("")
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
        iv_toolbar_back.setOnClickListener(this)
    }




    private fun APIforUserLogout( ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val call = apiService.userLogout("Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserLogout> {

            override fun onResponse(call: Call<ModelUserLogout>, response: Response<ModelUserLogout>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status==Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            activity!!.finish()
                            hideLoading()


                            val preferences =activity!!.getSharedPreferences(AppPreferencesHelper.PREF_NAME,Context.MODE_PRIVATE);
                            val  editor = preferences.edit();
                            editor.clear();
                            editor.commit();

                            val intent = Intent(activity, splash::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent)

//                            val intent = Intent(activity, splash::class.java)
//                            startActivity(intent)

                        }else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }

            override fun onFailure(call: Call<ModelUserLogout>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


//    {
//        "account_holder_name":"account_holder_name",
//        "account_number":"4000056655665556",
//        "exp_month":"02",
//        "exp_year":"2023",
//        "cvv":"123",
//        "stripe_card_token":"tok_1EUY6mHFDPL7EiHAZo1dClrx"
//    }

    //card.cvc,card.expMonth,card.expYear,card.number,token.id

    private fun APIforSaveUserCardDetails( userCardCVV : String, card_exp_month : String,
                                           card_exp_year : String, user_card_number : String,
                                           user_token_id : String, card_holder_name : String ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["account_holder_name"] = card_holder_name
        paramObject["account_number"] = user_card_number
        paramObject["exp_month"] = card_exp_month
        paramObject["exp_year"] = card_exp_year
        paramObject["cvv"] = userCardCVV
        paramObject["stripe_card_token"] = user_token_id
//user_token_id live stripe key id replace it when you want to add live key
        val call = apiService.UserPayment(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserPayment> {

            override fun onResponse(call: Call<ModelUserPayment>, response: Response<ModelUserPayment>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            try {
                                pref!!.useraccount_holder_name = response.body().info.accountHolderName
                                pref!!.useraccount_number = response.body().info.accountNumber
                                pref!!.userexp_month = response.body().info.expMonth
                                pref!!.userexp_year = response.body().info.expYear
                                pref!!.usercvv = response.body().info.cvv
                                pref!!.usercard_id = response.body().info.cardId
                            }catch (e:java.lang.Exception)
                            {
                                showMessage(getString(R.string.error_some_problem_occur))
                            }
                            dialogPayment.dismiss()
                            hideLoading()
                        }else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelUserPayment>, t: Throwable) {
                hideLoading()
            }


        })
    }

    private fun APIforUpdateUserProfile( userAge : String, userHeight : String,
                                         userWeight : String, useremail : String,
                                         userPhone : String ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["age"] = userAge
        paramObject["height"] = userHeight
        paramObject["weight"] = userWeight
        paramObject["phone_no"] = userPhone
        paramObject["email"] = useremail

        val call = apiService.updateUserProfile(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserProfileUpdate> {

            override fun onResponse(call: Call<ModelUserProfileUpdate>, response: Response<ModelUserProfileUpdate>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            tv_age.setText( et_user_age.text.toString())
                            tv_height.setText( et_user_height.text.toString())
                            tv_weight.setText( et_user_weight.text.toString())
                            emailId.setText( et_user_email.text.toString())
                            phonenumber.setText( et_user_phone.text.toString())
                            dialogUpdateProfile.dismiss()
                            hideLoading()
                        }else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelUserProfileUpdate>, t: Throwable) {
                hideLoading()
            }


        })
    }


    private fun APIforSaveAboutYou( aboutYou : String ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["description"] = aboutYou

        val call = apiService.saveAboutUser(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelAboutYou> {

            override fun onResponse(call: Call<ModelAboutYou>, response: Response<ModelAboutYou>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status==Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            hideLoading()
                            if(dialogAboutYou!=null && dialogAboutYou.isShowing)
                                dialogAboutYou.dismiss()

                        }else {
                            hideLoading()
                            showMessage(response.message())
                            if(dialogAboutYou!=null && dialogAboutYou.isShowing)
                                dialogAboutYou.dismiss()

                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelAboutYou>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }













    private fun APIforChangeEmergencyNumber( emr_name : String ,newPhoneNumber : String ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["emergency_contact"] = newPhoneNumber
        paramObject["P_ename"] = emr_name

        val call = apiService.getEmergecyNumberChanged(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelEmergecyContact> {

            override fun onResponse(call: Call<ModelEmergecyContact>, response: Response<ModelEmergecyContact>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status==Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            tv_emergencyContact.setText(newPhoneNumber)
                            dialogEmergency.dismiss()
                            hideLoading()
                        }else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelEmergecyContact>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }







    private fun APIforChangePassword(oldPass: String , newPass : String ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["old_password"] = oldPass
        paramObject["new_password"] = newPass

        val call = apiService.getChangePassword(paramObject , "Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelPassChange> {

            override fun onResponse(call: Call<ModelPassChange>, response: Response<ModelPassChange>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {

                        if(response.body().status==Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            dialog.dismiss()
                            hideLoading()
                        }else {
                            hideLoading()

                            showMessage(response.message())
                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelPassChange>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    private fun displayPaymentSettingDialog() {
        dialogPayment  =  Dialog(activity);
        dialogPayment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPayment.setContentView(R.layout.dialog_addcart_details);
        card_input_widget  = dialogPayment.findViewById(R.id.card_input_widget)

        card_holder_name = dialogPayment.findViewById(R.id.card_holder_name)
        tv_save_card_details = dialogPayment.findViewById(R.id.tv_save_card_details)
        tv_save_card_details.setOnClickListener(this)

        dialogPayment.show();
    }



    private fun displaysecuritySettingDialog() {
         dialog  =  Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_security_setting);
        et_password = dialog.findViewById(R.id.et_password);
        tv_forgot_password = dialog.findViewById(R.id.tv_forgot_password)
        tv_forgot_password.setOnClickListener(this)
        etPasswordSetting();
        et_repassword = dialog.findViewById(R.id.et_repassword);
        etRepasswordSetting();
        tv_change_password = dialog.findViewById(R.id.tv_change_password);
        tv_change_password.setOnClickListener(this)
        dialog.show();
    }



    private fun displayUpdateProfileDialog() {
        dialogUpdateProfile  =  Dialog(activity);
        dialogUpdateProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdateProfile.setContentView(R.layout.dialog_update_profile);

        tv_update = dialogUpdateProfile.findViewById(R.id.tv_update);
        et_user_age = dialogUpdateProfile.findViewById(R.id.et_user_age);
        et_user_height = dialogUpdateProfile.findViewById(R.id.et_user_height);
        et_user_weight = dialogUpdateProfile.findViewById(R.id.et_user_weight);
        et_user_phone = dialogUpdateProfile.findViewById(R.id.et_user_phone);
        et_user_email = dialogUpdateProfile.findViewById(R.id.et_user_email);


        if(!TextUtils.isEmpty(tv_age.text.toString()))
            et_user_age.setText(tv_age.text.toString())

        if(!TextUtils.isEmpty(tv_height.text.toString()))
            et_user_height.setText(tv_height.text.toString())

        if(!TextUtils.isEmpty(tv_weight.text.toString()))
            et_user_weight.setText(tv_weight.text.toString())

        if(!TextUtils.isEmpty(phonenumber.text.toString()))
            et_user_phone.setText(phonenumber.text.toString())

        if(!TextUtils.isEmpty(emailId.text.toString()))
            et_user_email.setText(emailId.text.toString())


        tv_update.setOnClickListener(this)
        dialogUpdateProfile.show();
    }



    private fun etRepasswordSetting() {
        et_repassword.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= et_repassword.getRight() - et_repassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                    // your action here

                    if (!maintainEyeViewNewPasss) {
                        et_repassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        et_repassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_on_login, 0
                        );
                        maintainEyeViewNewPasss = true
                    } else {
//                        editText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        et_repassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        et_repassword.setCompoundDrawablesWithIntrinsicBounds(
                            R.mipmap.ic_lock_password_login,
                            0, R.mipmap.ic_eye_off_login, 0
                        );
                        maintainEyeViewNewPasss = false
                    }


                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun etPasswordSetting() {
       et_password.setOnTouchListener(View.OnTouchListener { v, event ->
           val DRAWABLE_LEFT = 0
           val DRAWABLE_TOP = 1
           val DRAWABLE_RIGHT = 2
           val DRAWABLE_BOTTOM = 3

           if (event.action == MotionEvent.ACTION_UP) {
               if (event.rawX >= et_password.getRight() - et_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                   // your action here

                   if (!maintainEyeView) {
                       et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                       et_password.setCompoundDrawablesWithIntrinsicBounds(
                           R.mipmap.ic_lock_password_login,
                           0, R.mipmap.ic_eye_on_login, 0
                       );
                       maintainEyeView = true
                   } else {
//                        editText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                       et_password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD);
                       et_password.setCompoundDrawablesWithIntrinsicBounds(
                           R.mipmap.ic_lock_password_login,
                           0, R.mipmap.ic_eye_off_login, 0
                       );
                       maintainEyeView = false
                   }


                   return@OnTouchListener true
               }
           }
           false
       })
    }

    private fun APIforGetUserDeatils() {

        val apiService = ApiClient.getClient().create(WebService::class.java)
        val call = apiService.getUserDetails("Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelUserDetails> {

            override fun onResponse(call: Call<ModelUserDetails>, response: Response<ModelUserDetails>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status== Constant.RESPONSE_SUCCESSFULLY) {
                            setDataOnView(response.body().info);
                            hideLoading()
                        }
                        else
                        {
                            showMessage(response.message())
                            hideLoading()

                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelUserDetails>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    private fun setDataOnView(info: Info?) {

        if(!TextUtils.isEmpty(info!!.name))
        user_name.setText(info!!.name)

        if(!TextUtils.isEmpty(info!!.weight))
        tv_weight.setText(info!!.weight)

        if(!TextUtils.isEmpty(info!!.height))
        tv_height.setText(info!!.height)


        if(!TextUtils.isEmpty(info!!.about))
            et_aboutYou.setText(info!!.about)

        if(!TextUtils.isEmpty(info!!.age))
        tv_age.setText(info!!.age)

        if(!TextUtils.isEmpty(info!!.phone)) {
            phonenumber.setText(info!!.phone)
            tv_emergencyContact.setText(info!!.phone)
        }

        if(!TextUtils.isEmpty(info!!.email))
        emailId.setText(info!!.email)

        if(!TextUtils.isEmpty(info!!.eContact))
            tv_emergencyContact.setText(info!!.eContact)

//        if(!TextUtils.isEmpty(info!!.a))
//            tv_emergencyContact.setText(info!!.eContact)

        if(!TextUtils.isEmpty(info!!.profPic))
        Picasso.get().load(info.profPic).into(profile_image)

    }

    fun selectImage(imagePosition: Int) {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")
        val title = TextView(activity)
        title.text = "Add Photo!"
        title.setBackgroundColor(Color.BLACK)
        title.setPadding(10, 15, 15, 10)
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.WHITE)
        title.textSize = 22f


        val builder = AlertDialog.Builder(        activity
        )


        builder.setCustomTitle(title)

        // builder.setTitle("Add Photo!");
        builder.setItems(items) { dialog, item ->
            if (items[item] == "Take Photo") {


                if (ContextCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.CAMERA
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.CAMERA),
                            0
                        )
                    }

                } else {
                    captureProfilePhoto(2)

                }

            } else if (items[item] == "Choose from Library") {
                if (activity?.let {
                        ContextCompat.checkSelfPermission(
                            it,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            0
                        )
                    }

                }
                if (ContextCompat.checkSelfPermission(
                        activity!!,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            0
                        )
                    }

                } else {
                    browseProfilePhoto(imagePosition)


                }

            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun HitServerToUpdateAvtar() {

        val a = ArrayList<MultipartBody.Part>()

        showLoading()
        val apiService = ApiClient.getClient().create(WebService::class.java)

        if (integerFileHashMap.size > 0) {

            for (i in 1..integerFileHashMap.size) {

                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), integerFileHashMap.get(i))
                val body = MultipartBody.Part.createFormData("prof_pic",integerFileHashMap.get(i)!!.getName(), requestFile)
                a.add(body)
            }

        }
        apiService.updateCounselorAvtar(a , "Bearer "+pref!!.userBToken).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ModelUpdateAvtar> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(updateAvtar: ModelUpdateAvtar) {
                    a.clear()
                    hideLoading()
                    showMessage(updateAvtar.message)
                    hideLoading()

                }

                override fun onError(e: Throwable) {
                    a.clear()
                    hideLoading()
                    handleError(e)
                }
            })


    }
    private fun handleError(t: Throwable?) {
        if (t != null)
            showMessage(t.message)
    }


    private fun browseProfilePhoto(imagePosition: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("imageNumber", imagePosition)
        startActivityForResult(Intent.createChooser(intent, "Select picture"), imagePosition)
    }

    private fun handleGalleryIntent(data: Intent, imagePosition: Int) {
        try {
            val selectedImageUri = data.data
            val file = File(getRealPathForGalley(activity, selectedImageUri))

            integerFileHashMap.put(imagePosition, file)
            integerURIHashMap.put(imagePosition, selectedImageUri)

            val imageBitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, selectedImageUri)
            if (imagePosition == 1)
                Picasso.get().load(data.data).into(profile_image)

            HitServerToUpdateAvtar()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    fun getRealPathForGalley(activity: FragmentActivity?, captureImageUri: Uri): String {
        var wholeID: String? = null
        try {
            wholeID = DocumentsContract.getDocumentId(captureImageUri)
        } catch (e: Exception) {
            return getRealPathFromURI(getActivity(), captureImageUri)
        }

        // Split at colon, use second item in the array
        val id = wholeID!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"

        val cursor = getActivity()!!.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )

        var filePath = ""

        val columnIndex = cursor!!.getColumnIndex(column[0])

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }

        cursor.close()
        return CommonUtils.compressImage(filePath, getActivity())
    }


    fun getRealPathFromURI(context: Context?, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context!!.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }


    fun getImageUri(inContext: Context?, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()

        val path = MediaStore.Images.Media.insertImage(inContext!!.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
    fun grabImage(imageView: ImageView, pos: Int) {

        activity!!.contentResolver.notifyChange(photoURI, null)
        val cr = activity!!.contentResolver
        val bitmap: Bitmap
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoURI)
            imageView.setImageBitmap(bitmap)

            val uri = getImageUri(activity!!.applicationContext, bitmap)
            val file = File(getRealPathFromURI(activity!!.applicationContext, uri))
            integerFileHashMap[1] = file
            imageView.setImageBitmap(bitmap)

            HitServerToUpdateAvtar()


        } catch (e: Exception) {
            Toast.makeText(activity, "Failed to load", Toast.LENGTH_SHORT).show()
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            1->
            {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        handleGalleryIntent(data,1)
                    }
                    else {
                        showMessage("Fail to upload image ")
                    }
                }
            }
            2->
            {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        grabImage(profile_image, 2)
                    }
                    else {
                        showMessage("Fail to upload image ")
                    }
                }
            }
        }

    }





//    private fun launchCamera() {
//        val values = ContentValues(1)
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
//        val fileUri = contentResolver
//            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                values)
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if(intent.resolveActivity(packageManager) != null) {
//            mCurrentPhotoPath = fileUri.toString()
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
//        }
//    }




    private fun captureProfilePhoto(imagePosition: Int) {


        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
            }
//            mCurrentPhotoPath = fileUri.toString()
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(
                    activity!!,
                    "com.amindset.ve.amindset.provider",
                    photoFile!!
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(takePictureIntent, imagePosition )
            }
        }
//                           val pictureIntent =  Intent(
//                              MediaStore.ACTION_IMAGE_CAPTURE
//                           );
//    if(pictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
//           startActivityForResult(pictureIntent,
//                              imagePosition);
    }


    }
//    @Throws(IOException::class)
//    private fun createImageFile(part: String, ext: String): File {
//        // Create an image file name
//        var tempDir = Environment.getExternalStorageDirectory()
//        tempDir = File(tempDir.absolutePath + "/.temp/")
//        if (!tempDir.exists()) {
//            tempDir.mkdirs()
//        }
//        return File.createTempFile(part, ext, tempDir)
//    }

   lateinit var  imageFilePath : String ;

     @Throws(IOException::class)
    private fun createImageFile(): File? {
    val timeStamp =
          SimpleDateFormat("yyyyMMdd_HHmmss",
                      Locale.getDefault()).format( Date());
    val imageFileName = "IMG_" + timeStamp + "_";
    val storageDir = Environment.getExternalStorageDirectory()
    val image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
    );

    imageFilePath = image.getAbsolutePath();
    return image;

}
