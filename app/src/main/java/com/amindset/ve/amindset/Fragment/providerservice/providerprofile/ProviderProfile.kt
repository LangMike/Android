package com.amindset.ve.amindset.Fragment.providerservice.providerprofile

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
import android.view.*
import android.widget.*
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.Professional_liability
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelAboutYou.ModelProviderAboutyou
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelEmergecyProvider.ModelProviderEmergency
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderAccountDetails.ModelProviderAccountDetails
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderDetails.Details
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderDetails.ModelProviderDetails
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelProviderSetting.ModelProviderSecuritySetting
import com.amindset.ve.amindset.Fragment.providerservice.providerprofile.ModelUpdateProfile.ModelProvideProfileUpdate
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelAvtar.ModelUpdateAvtar
import com.amindset.ve.amindset.ProfessionalVerification.ProfessionalVerification
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Splash.splash
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import com.squareup.picasso.Picasso
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_provideraddcart_details.*
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

class ProviderProfile : BaseFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0!!.id) {


            R.id.provider_exp -> {
                showSnackBar("It's total experience")
            }


            R.id.rl_legal->
            {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Legal")
                builder.setItems(arrayOf<CharSequence>("Term/Condition", "Privacy", "Faqs"),
                    object: DialogInterface.OnClickListener {
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

            R.id.tv_update -> {
                if (!TextUtils.isEmpty(et_provider_name.text.toString()) &&
                    !TextUtils.isEmpty(et_provider_email.text.toString()) &&
                    !TextUtils.isEmpty(et_provider_mobile.text.toString()) &&
                    !TextUtils.isEmpty(et_provider_exp.text.toString()) &&
                    !TextUtils.isEmpty(et_provider_proffesion.text.toString())
                ) {
                    if (NetworkUtils.isNetworkConnected(activity)) {

                        APIforUpdateProviderProfile(
                            et_provider_name.text.toString(),
                            et_provider_email.text.toString(),
                            et_provider_mobile.text.toString(),
                            et_provider_exp.text.toString(),
                            et_provider_proffesion.text.toString()
                        );

                    } else {
                        showMessage(R.string.msg_check_internet)
                    }

                } else {
                    showMessage(R.string.msg_fill_login_data)
                }
            }
            R.id.Edit -> {
                displayUpdateProfileDialog()
            }

            R.id.logout -> {
                val preferences = activity!!.getSharedPreferences(AppPreferencesHelper.PREF_NAME, Context.MODE_PRIVATE);
                val editor = preferences.edit();
                editor.clear();
                editor.commit();

                val intent = Intent(activity, splash::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent)
            }

            R.id.save_details -> {
                if (!TextUtils.isEmpty(card_holder_name.text.toString())) {

                    if (!TextUtils.isEmpty(card_holder_address.text.toString())) {
                        if (!TextUtils.isEmpty(card_holder_bank_name.text.toString())) {
                            if (!TextUtils.isEmpty(card_holder_bank_region.text.toString())) {

                                if (!TextUtils.isEmpty(card_holde_account_number.text.toString())) {

                                    if (!TextUtils.isEmpty(card_holder_bic_short_swift_code.text.toString())) {


                                        var us: String = "false"

                                        if (rules.isChecked) {
                                            us = "true"
                                        }



                                        if (NetworkUtils.isNetworkConnected(this!!.activity)) {
                                            APIforSaveAccountDetails(
                                                card_holder_name.text.toString(),
                                                card_holder_address.text.toString(),
                                                card_holder_bank_name.text.toString(),
                                                card_holde_account_number.text.toString(),
                                                card_holder_bic_short_swift_code.text.toString(),
                                                us,
                                                card_holder_bank_region.text.toString()
                                            );
                                        } else {
                                            showMessage(R.string.msg_check_internet)
                                        }


                                    } else
                                        showMessage(getString(R.string.msg_fill_login_data))
                                } else
                                    showMessage(getString(R.string.msg_fill_login_data))
                            } else
                                showMessage(getString(R.string.msg_fill_login_data))
                        } else
                            showMessage(getString(R.string.msg_fill_login_data))
                    } else
                        showMessage(getString(R.string.msg_fill_login_data))
                } else
                    showMessage(getString(R.string.msg_fill_login_data))

            }

            R.id.tv_saveEmergencyContact -> {

                if (!TextUtils.isEmpty(et_phoneNumber.text.toString())) {
                    if (NetworkUtils.isNetworkConnected(this!!.activity)) {
                        APIforChangeEmergencyNumber(et_phoneNumber.text.toString());
                    } else {
                        showMessage(R.string.msg_check_internet)
                    }
                } else
                    showSnackBar(getString(R.string.msg_fill_login_data))
            }

            R.id.tv_change_password -> {

                if (!TextUtils.isEmpty(et_password.text.toString()) &&
                    !TextUtils.isEmpty(et_repassword.text.toString())
                ) {
                    if (NetworkUtils.isNetworkConnected(activity)) {
                        APIforChangePassword(et_password.text.toString(), et_repassword.text.toString());
                    } else {
                        showMessage(R.string.msg_check_internet)
                    }
                } else
                    showSnackBar(getString(R.string.msg_fill_login_data))
            }

            R.id.rl_security_setting -> {
                displaysecuritySettingDialog();
            }

            R.id.rl_emergrncy_conctact -> {
                showEmergencyContactDialog();
            }


            R.id.rl_payment_settings -> {
                displayPaymentSettingDialog()
            }

            R.id.tv_forgot_password -> {
            }

            R.id.tv_save_card_details -> {
//                if( !TextUtils.isEmpty(card_holder_name.text.toString()))
//                {
//                    if(NetworkUtils.isNetworkConnected(activity)) {
//
//
//                        checkCardsDetail(card_holder_name.text.toString());
//                    }
//                    else
//                    {
//                        showMessage(R.string.msg_check_internet)
//                    }
//
//                }
//                else
//                {
//                    showMessage(R.string.msg_fill_login_data)
//                }

            }


            R.id.iv_toobar_back -> {

                Onbacktoolbarsetting()
                activity!!.supportFragmentManager.popBackStackImmediate()
            }


            R.id.profile_image -> {
                selectImage(1)

            }


            R.id.rl_document -> {
                activity!!.startActivity(Intent(activity, ProfessionalVerification::class.java))


            }

            R.id.rl_professional_liability -> {
                activity!!.startActivity(Intent(activity, Professional_liability::class.java))
            }

            R.id.save_details_provider_about_you -> {
                if (!TextUtils.isEmpty(et_aboutYou.text.toString())) {
                    if (NetworkUtils.isNetworkConnected(activity)) {
                        APIforSaveAboutYou(et_aboutYou.text.toString());
                    } else {
                        showMessage(R.string.msg_check_internet)
                    }
                } else
                    showSnackBar("Please enter about you!")

            }

            R.id.rl_about_you -> {


                displayAboutyouDialog(""+et_aboutYou.text.toString());

            }
        }
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

    private fun displayPaymentSettingDialog() {
        dialogPayment = Dialog(activity);
        dialogPayment.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPayment.setContentView(R.layout.dialog_provideraddcart_details);

        card_holder_name = dialogPayment!!.findViewById(R.id.card_holder_name)
        card_holder_address = dialogPayment!!.findViewById(R.id.card_holder_address)
        card_holder_bank_name = dialogPayment!!.findViewById(R.id.card_holder_bank_name)
        card_holde_account_number = dialogPayment!!.findViewById(R.id.card_holde_account_number)
        card_holder_bic_short_swift_code = dialogPayment!!.findViewById(R.id.card_holder_bic_short_swift_code)
        card_holder_bank_region = dialogPayment!!.findViewById(R.id.card_holder_bank_region)


        rules = dialogPayment!!.findViewById(R.id.rules)
        save_details = dialogPayment!!.findViewById(R.id.save_details)
        save_details.setOnClickListener(this)

//        card_input_widget  = dialogPayment.findViewById(R.id.card_input_widget)
//
//        card_holder_name = dialogPayment.findViewById(R.id.card_holder_name)
//        tv_save_card_details = dialogPayment.findViewById(R.id.tv_save_card_details)
//        tv_save_card_details.setOnClickListener(this)

        dialogPayment.show();
    }

    private fun APIforUpdateProviderProfile(
        userName: String, userEmail: String,
        userMobile: String, userExp: String,
        userProfession: String
    ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()
        paramObject["T_name"] = userName
        paramObject["T_email"] = userEmail
        paramObject["T_lname"] = ""
        paramObject["T_mobile"] = userMobile
        paramObject["Total_exp"] = userExp
        paramObject["profession"] = userProfession
        paramObject["T_econtact"] = ""

        val call = apiService.updateProviderProfile(paramObject, "Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProvideProfileUpdate> {

            override fun onResponse(
                call: Call<ModelProvideProfileUpdate>,
                response: Response<ModelProvideProfileUpdate>?
            ) {

                if (response != null) {
                    if (response.isSuccessful) {
                        if (response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)

                            if (!TextUtils.isEmpty(et_provider_name.text.toString()))
                                user_name.setText(et_provider_name.text.toString())

                            if (!TextUtils.isEmpty(et_provider_email.text.toString()))
                                emailId.setText(et_provider_email.text.toString())

                            if (!TextUtils.isEmpty(et_provider_mobile.text.toString()))
                                phonenumber.setText(et_provider_mobile.text.toString())


                            if (!TextUtils.isEmpty(et_provider_exp.text.toString()))
                                provider_exp.setText(et_provider_exp.text.toString())

                            if (!TextUtils.isEmpty(et_provider_proffesion.text.toString()))
                                tv_provider_proficiency.setText(et_provider_proffesion.text.toString())

                            dialogUpdateProfile.dismiss()

                            hideLoading()
                        } else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    } else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelProvideProfileUpdate>, t: Throwable) {
                hideLoading()
            }


        })
    }


    private fun displayUpdateProfileDialog() {
        dialogUpdateProfile = Dialog(activity);
        dialogUpdateProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpdateProfile.setContentView(R.layout.dialog_update_provider_profile);

        et_provider_name = dialogUpdateProfile.findViewById(R.id.et_provider_name);
        et_provider_mobile = dialogUpdateProfile.findViewById(R.id.et_provider_mobile);
        et_provider_email = dialogUpdateProfile.findViewById(R.id.et_provider_email);
        et_provider_exp = dialogUpdateProfile.findViewById(R.id.et_provider_exp);
        et_provider_proffesion = dialogUpdateProfile.findViewById(R.id.et_provider_proffesion);
        tv_update = dialogUpdateProfile.findViewById(R.id.tv_update);

        if (!TextUtils.isEmpty(user_name.text.toString()))
            et_provider_name.setText(user_name.text.toString())

        if (!TextUtils.isEmpty(phonenumber.text.toString()))
            et_provider_mobile.setText(phonenumber.text.toString())

        if (!TextUtils.isEmpty(emailId.text.toString()))
            et_provider_email.setText(emailId.text.toString())

        if (!TextUtils.isEmpty(provider_exp.text.toString()))
            et_provider_exp.setText(provider_exp.text.toString())

        if (!TextUtils.isEmpty(tv_provider_proficiency.text.toString()))
            et_provider_proffesion.setText(tv_provider_proficiency.text.toString())
//Ap
//
        tv_update.setOnClickListener(this)
        dialogUpdateProfile.show();
    }


//    card_holder_name.text.toString(),
//    card_holder_address.text.toString(),
//    card_holder_bank_name.text.toString(),
//    card_holde_account_number.text.toString(),
//    card_holder_bic_short_swift_code.text.toS

    private fun APIforSaveAccountDetails(
        cardHolderName: String, cardHolderAddress: String, cardHolderBankName: String
        , cardHolderAccountNumber: String, cardHolderbic_short_swift_code: String, us: String, regionBank: String
    ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()
        paramObject["account_holder_name"] = cardHolderName
        paramObject["address_on_bank"] = cardHolderAddress
        paramObject["bank_name"] = cardHolderBankName
        paramObject["routing_number"] = cardHolderbic_short_swift_code
        paramObject["account_number"] = cardHolderAccountNumber
        paramObject["regionBank"] = regionBank

        val call = apiService.saveProviderBankDetails(paramObject, "Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderAccountDetails> {

            override fun onResponse(
                call: Call<ModelProviderAccountDetails>,
                response: Response<ModelProviderAccountDetails>?
            ) {

                if (response != null) {
                    if (response.isSuccessful) {
                        if (response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().msg)
//                                "account_holder_name": "fchgjb",
//                                "routing_number": "110000000",
//                                "account_number": "000123456789",
//                                "bank_name": "icici",
//                                "address_on_bank": "sector 82",
//                                "regionBank": "noida",
//                                "strip_bank_id": "ba_1EprPOJYqmxhyqP5Q7FDyKP5",
//                                "Account_id": "acct_1EprHPJYqmxhyqP5"

                            pref!!.provider_account_holder_name = response.body().info.accountHolderName
                            pref!!.provider_account_number = response.body().info.accountNumber
                            pref!!.provider_bank_name = response.body().info.bankName
                            pref!!.provider_address_on_bank = response.body().info.addressOnBank
                            pref!!.provider_regionBank = response.body().info.regionBank
                            pref!!.providerAccount_id = response.body().info.accountId
                            dialogPayment.dismiss()
                            hideLoading()
                        } else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    } else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelProviderAccountDetails>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


    private fun APIforChangeEmergencyNumber(newPhoneNumber: String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["T_econtact"] = newPhoneNumber

        val call = apiService.getEmergecyNumberProvider(paramObject, "Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderEmergency> {

            override fun onResponse(call: Call<ModelProviderEmergency>, response: Response<ModelProviderEmergency>?) {

                if (response != null) {
                    if (response.isSuccessful) {
                        if (response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            tv_emergencyContact.setText(newPhoneNumber)
                            dialogEmergency.dismiss()
                            hideLoading()
                        } else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    } else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelProviderEmergency>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


    private fun showEmergencyContactDialog() {
        dialogEmergency = Dialog(activity);
        dialogEmergency.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEmergency.setContentView(R.layout.dialog_emergency_contact);
        et_phoneNumber = dialogEmergency.findViewById(R.id.et_phoneNumber)

//        if (!TextUtils.isEmpty(tv_emergencyContact.text.toString()))
//            et_phoneNumber.setText(tv_emergencyContact.text.toString())

        tv_saveEmergencyContact = dialogEmergency.findViewById(R.id.tv_saveEmergencyContact)
        tv_saveEmergencyContact.setOnClickListener(this)

        dialogEmergency.show();
    }


    private fun displaysecuritySettingDialog() {


        dialog = Dialog(activity);
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


    private fun APIforSaveAboutYou(aboutYou: String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["description"] = aboutYou

        val call = apiService.saveAboutProvider(paramObject, "Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderAboutyou> {

            override fun onResponse(call: Call<ModelProviderAboutyou>, response: Response<ModelProviderAboutyou>?) {

                if (response != null) {
                    if (response.isSuccessful) {
                        if (response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            if(dialogAboutYou!=null && dialogAboutYou.isShowing)
                                dialogAboutYou.dismiss()
                            hideLoading()
                        } else {
                            hideLoading()
                            showMessage(response.message())
                            if(dialogAboutYou!=null && dialogAboutYou.isShowing)
                                dialogAboutYou.dismiss()

                        }
                    } else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelProviderAboutyou>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


    private fun Onbacktoolbarsetting() {
        val iv_toolbar_back = activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
    }

    companion object {
        fun newInstance() = ProviderProfile()
    }

    private lateinit var viewModel: ProviderProfileViewModel

    var pref: AppPreferencesHelper? = null

//    et_provider_name = dialogUpdateProfile.findViewById(R.id.et_provider_name);
//    et_provider_mobile = dialogUpdateProfile.findViewById(R.id.et_provider_mobile);
//    et_provider_email = dialogUpdateProfile.findViewById(R.id.et_provider_email);
//    et_provider_exp = dialogUpdateProfile.findViewById(R.id.et_provider_exp);
//    et_provider_proffesion =


    lateinit var et_provider_name: TextInputEditText
    lateinit var et_provider_mobile: TextInputEditText
    lateinit var et_provider_email: TextInputEditText
    lateinit var et_provider_exp: TextInputEditText
    lateinit var et_provider_proffesion: TextInputEditText
    lateinit var tv_update: TextView
//    lateinit var et_password : EditText


    lateinit var profile_image: ImageView
    lateinit var rl_legal: RelativeLayout
    lateinit var rl_document: RelativeLayout
    lateinit var provider_exp: TextView
    lateinit var tv_provider_proficiency: TextView
    lateinit var provider_rating: TextView
    lateinit var phonenumber: TextView
    lateinit var emailId: TextView
    lateinit var et_aboutYou: EditText
    lateinit var logout: TextView
    lateinit var user_name: TextView
    lateinit var save_about_you: TextView
    lateinit var rl_professional_liability: RelativeLayout
    lateinit var rl_about_you: RelativeLayout


    var maintainEyeView: Boolean = false
    var maintainEyeViewNewPasss: Boolean = false


    lateinit var et_password: EditText
    lateinit var et_phoneNumber: EditText
    lateinit var tv_saveEmergencyContact: TextView
    lateinit var et_repassword: EditText
    lateinit var tv_change_password: TextView
    lateinit var tv_forgot_password: TextView
    lateinit var tv_emergencyContact: TextView
    lateinit var tv_saveabout: TextView
    lateinit var Edit: TextView


    lateinit var card_holder_name: EditText
    lateinit var card_holder_address: EditText
    lateinit var card_holder_bank_name: EditText
    lateinit var card_holde_account_number: EditText
    lateinit var card_holder_bic_short_swift_code: EditText
    lateinit var card_holder_bank_region: EditText
    lateinit var et_aboutYou_dialog: EditText

    lateinit var rules: CheckBox
    lateinit var save_details: TextView
    lateinit var save_details_provider_about_you: TextView

    lateinit var dialog: Dialog
    lateinit var dialogPayment: Dialog
    lateinit var dialogAboutYou: Dialog
    lateinit var dialogUpdateProfile: Dialog
    lateinit var dialogEmergency: Dialog


    var rl_security_setting: RelativeLayout? = null
    var rl_emergrncy_conctact: RelativeLayout? = null
    var rl_payment_settings: RelativeLayout? = null


    private val integerFileHashMap = HashMap<Int, File>()
    private val integerURIHashMap = HashMap<Int, Uri>()


    internal var photoFile: File? = null
    internal var photoURI: Uri? = null
    var filepath: String = ""

//    lateinit var Edit : TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rl_document=(view).findViewById(R.id.rl_document);
        rl_legal=(view).findViewById(R.id.rl_legal);
        rl_legal.setOnClickListener(this)
        rl_document.setOnClickListener(this)
        profile_image = view.findViewById(R.id.profile_image)
        profile_image.setOnClickListener(this)
        provider_exp = view.findViewById(R.id.provider_exp)
        provider_exp.setOnClickListener(this)
        tv_provider_proficiency = view.findViewById(R.id.tv_provider_proficiency)
        provider_rating = view.findViewById(R.id.provider_rating)
        emailId = view.findViewById(R.id.gmail)
        et_aboutYou = view.findViewById(R.id.et_aboutYou)
        logout = view.findViewById(R.id.logout)
        logout.setOnClickListener(this)
        user_name = view.findViewById(R.id.user_name)
        phonenumber = view.findViewById(R.id.phonenumber)

        save_about_you = view.findViewById(R.id.save_about_you)
        save_about_you.setOnClickListener(this)

        rl_professional_liability = view.findViewById(R.id.rl_professional_liability)
        rl_professional_liability.setOnClickListener(this)

        rl_about_you = view.findViewById(R.id.rl_about_you)
        rl_about_you.setOnClickListener(this)


        tv_emergencyContact = view.findViewById(R.id.tv_emergencyContact)



        rl_security_setting = view.findViewById(R.id.rl_security_setting)
        rl_emergrncy_conctact = view.findViewById(R.id.rl_emergrncy_conctact)
        rl_payment_settings = view.findViewById(R.id.rl_payment_settings)
        rl_payment_settings!!.setOnClickListener(this)

        rl_security_setting!!.setOnClickListener(this)
        rl_emergrncy_conctact!!.setOnClickListener(this)

        Edit = view.findViewById(R.id.Edit)

        Edit.setOnClickListener(this)


//        lateinit var card_holder_name : EditText
//        lateinit var card_holder_address : EditText
//        lateinit var card_holder_bank_name : EditText
//        lateinit var card_holde_account_number : EditText
//        lateinit var card_holder_bic_short_swift_code : EditText
//        lateinit var   rules : CheckBox
//        lateinit var save_details : TextView

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        pref = AppPreferencesHelper(activity)
        if (NetworkUtils.isNetworkConnected(activity))
            APIforGetProviderDeatils()
        else
            showMessage(R.string.msg_check_internet)

        return inflater.inflate(R.layout.provider_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProviderProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        toolbarsetting();
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_navigation_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun toolbarsetting() {
        val tv_tolbar_center_text = activity!!.findViewById(R.id.iv_amindset) as TextView
        val iv_toolbar_back = activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE
        iv_toolbar_back.setOnClickListener(this)
    }

    private fun APIforGetProviderDeatils() {

        val apiService = ApiClient.getClient().create(WebService::class.java)
        val call = apiService.GetProvidersDetails("Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderDetails> {

            override fun onResponse(call: Call<ModelProviderDetails>, response: Response<ModelProviderDetails>?) {

                if (response != null) {
                    if (response.isSuccessful) {
                        if (response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            setDataOnView(response.body().details);
//                            showMessage(response.body().)
                            hideLoading()
                        } else {
                            showMessage(response.message())
                            hideLoading()

                        }
                    } else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelProviderDetails>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    private fun setDataOnView(info: Details?) {


        if (!TextUtils.isEmpty(info!!.tName))
            user_name.setText(info!!.tName)

        if (!TextUtils.isEmpty(info!!.totalExp))
            provider_exp.setText(info!!.totalExp)

        if (!TextUtils.isEmpty(info!!.profession as CharSequence?))
            tv_provider_proficiency.setText("" + info!!.profession)

        if (!TextUtils.isEmpty(info!!.tAbout))
            et_aboutYou.setText(info!!.tAbout)

        if (!TextUtils.isEmpty(info!!.tMobile)) {
            phonenumber.setText(info!!.tMobile)
        }
        if (!TextUtils.isEmpty(info!!.tEcontact)) {
            tv_emergencyContact.setText(info!!.tEcontact)
//            tv_emergencyContact.setText(info!!.phone)
        }

        if (!TextUtils.isEmpty(info!!.tEmail))
            emailId.setText(info!!.tEmail)

        if (!TextUtils.isEmpty(info!!.rating))
            provider_rating.setText(info!!.rating)

//        if(!TextUtils.isEmpty(info!!.a))
//            tv_emergencyContact.setText(info!!.e)

        if (!TextUtils.isEmpty(info!!.tProfPic))
            Picasso.get().load(info.tProfPic).into(profile_image)

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


        val builder = AlertDialog.Builder(
            activity
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
                takePictureIntent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                startActivityForResult(takePictureIntent, imagePosition)
            }
        }
//                           val pictureIntent =  Intent(
//                              MediaStore.ACTION_IMAGE_CAPTURE
//                           );
//    if(pictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
//           startActivityForResult(pictureIntent,
//                              imagePosition);
    }

    private fun browseProfilePhoto(imagePosition: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("imageNumber", imagePosition)
        startActivityForResult(Intent.createChooser(intent, "Select picture"), imagePosition)
    }

    lateinit var imageFilePath: String;

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp =
            SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()
            ).format(Date());
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        handleGalleryIntent(data, 1)
                    } else {
                        showMessage("Fail to upload image ")
                    }
                }
            }
            2 -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        grabImage(profile_image, 2)
                    } else {
                        showMessage("Fail to upload image ")
                    }
                }
            }
        }

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

    fun getImageUri(inContext: Context?, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()

        val path = MediaStore.Images.Media.insertImage(inContext!!.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
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

    private fun HitServerToUpdateAvtar() {

        val a = ArrayList<MultipartBody.Part>()

        showLoading()
        val apiService = ApiClient.getClient().create(WebService::class.java)

        if (integerFileHashMap.size > 0) {

            for (i in 1..integerFileHashMap.size) {

                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), integerFileHashMap.get(i))
//
//                   val requestFile =
//            RequestBody.create(
//                         MediaType.parse(activity!!.getContentResolver().getType(integerURIHashMap.get(i))),
//                integerFileHashMap.get(i)
//             );


                val body =
                    MultipartBody.Part.createFormData("prof_pic", integerFileHashMap.get(i)!!.getName(), requestFile)
                a.add(body)
            }

        }
        apiService.updateProvideProfileAvtar(a, "Bearer " + pref!!.providerToken).subscribeOn(Schedulers.io())
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


    private fun APIforChangePassword(oldPass: String, newPass: String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["oldpassword"] = oldPass
        paramObject["password"] = newPass

        val call = apiService.getChangePasswordProvider(paramObject, "Bearer " + pref!!.providerToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelProviderSecuritySetting> {

            override fun onResponse(
                call: Call<ModelProviderSecuritySetting>,
                response: Response<ModelProviderSecuritySetting>?
            ) {

                if (response != null) {
                    if (response.isSuccessful) {

                        if (response.body().status == Constant.RESPONSE_SUCCESSFULLY) {
                            showMessage(response.body().message)
                            dialog.dismiss()
                            hideLoading()
                        } else {
                            hideLoading()
                            showMessage(response.message())
                        }
                    } else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }


            override fun onFailure(call: Call<ModelProviderSecuritySetting>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

}
