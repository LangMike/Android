package com.amindset.ve.amindset.SignUp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.OTP.EmailVerify
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.SignUp.DataModel.ModelProficiencyList
import com.amindset.ve.amindset.SignUp.DataModel.ModelSignUp.ModelSignUp
import com.amindset.ve.amindset.SignUp.SpinnerAdapter.CustomAdapter
import com.amindset.ve.amindset.SignUp.UsersignUp.ModelUserSignUp
import com.amindset.ve.amindset.Signin.SignIn
import com.amindset.ve.amindset.Splash.ModelFCM.ModelFCmTokenResponse
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.PreferencesHelper
import com.amindset.ve.amindset.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class signup_activity : BaseActivity(), TextWatcher, AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


        proficientId = proficienctataList.data.get(p2).id

        signupBinding!!.etProviderSpecialization.setText(proficienctataList.data.get(p2).proficiency)
    }

     override fun afterTextChanged(p0: Editable?) {

        if (signupBinding.etUsernumber.text.hashCode() == p0.hashCode()) {

        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

//        if (signupBinding.etDateOfBirth.text.hashCode() == p0.hashCode()) {
//
//            if (p0!!.length > 0) {
//
//                var ageMsg: String = signupViewModel.checkAgeValidation(Integer.parseInt(p0.toString()));
//                if (ageMsg.equals("")) {
//                } else {
//                    showSnackBar("Invalid Age")
//                }
//            }
//        } else if (signupBinding.etProviderSpecialization.text.hashCode() == p0.hashCode()) {
//        }


    }

    // Declare Varaibles
    lateinit var signupBinding: ActivitySignupBinding

    lateinit var signupViewModel: ViewModelSignUp

    lateinit var proficienctataList :ModelProficiencyList

    lateinit var proficientId : String

     var genderId : Int = 0

     @Inject
     lateinit var  pref : PreferencesHelper


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)



        signupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        signupViewModel = ViewModelProviders.of(this).get(ViewModelSignUp::class.java)
        signupBinding!!.spinner.setOnItemSelectedListener(this);
//        if(intent.getStringExtra("type").equals("user")) {
//
//        }
//
//        else
//        {
//            signupBinding!!.etCreateusername.setHint("Create provider name")
//        }

        signupBinding!!.etCreateusername.setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(v:View, event: MotionEvent):Boolean {
                val DRAWABLE_LEFT = 0
                val DRAWABLE_TOP = 1
                val DRAWABLE_RIGHT = 2
                val DRAWABLE_BOTTOM = 3
                if (event.getAction() === MotionEvent.ACTION_UP)
                {
                    if (event.getRawX() >= (signupBinding!!.etCreateusername.getRight() - signupBinding!!.etCreateusername.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
                    {
                       showMessage("Choose a user name that you will remember. This is the name that you will use to sign in "

                        )
                        return true
                    }
                }
                return false
            }
        })

        getSignInIntentData()

        getDataFromSelectorActivity()

        (application as AmidApp).getMyComponent().inject(this@signup_activity)


    }




    internal val proficiencyObserver: Observer<ModelProficiencyList> = Observer<ModelProficiencyList> { proficiencyList ->
        hideLoading()

        this.proficienctataList= proficiencyList!!;
        if(proficiencyList!!.status== Constant.response_Failure)
        {
        showSnackBar(getString(R.string.error_some_problem_occur))
            finish()
        }
        else  if(proficiencyList!!.status== Constant.RESPONSE_SUCCESSFULLY){


            val aa = CustomAdapter(this, android.R.layout.simple_spinner_item,
                proficiencyList.data,null
            )
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            //Setting the ArrayAdapter data on the Spinner
            signupBinding!!.spinner.setAdapter(aa)

        }
    }






    internal val UserSignUpObserver: Observer<ModelUserSignUp> = Observer<ModelUserSignUp> { userSignUp ->
        hideLoading()

        if(userSignUp!!.status== Constant.response_Failure)
        {
            showSnackBar(userSignUp.message)
        }
        else  if(userSignUp!!.status== Constant.RESPONSE_SUCCESSFULLY){

            showSnackBar(userSignUp.message)

            val intent: Intent = Intent(this, EmailVerify::class.java)
            intent.putExtra("activity", "signup")
            intent.putExtra("type",getIntent().getStringExtra("type"))
            intent.putExtra("usertokenB",userSignUp.token)
            startActivity(intent)
            MethodToRegisterFCMToken(pref.fcmToken, userSignUp.token , "user");
            Thread({

                pref.userP_fname = userSignUp.data.pFname
                pref.userP_lname = userSignUp.data.pLname
                pref.userUser_name = userSignUp.data.userName
                pref.userP_email = userSignUp.data.pEmail
                pref.userPswd = userSignUp.data.pswd
                pref.userP_Prof_pic = userSignUp.data.pProfPic
                pref.userP_gender = userSignUp.data.pGender
                pref.userP_phone = userSignUp.data.pPhone
                pref.userT_mobile = userSignUp.data.tMobile
                pref.userP_econtact = userSignUp.data.pEcontact
                pref.userP_dob = userSignUp.data.pDob
                pref.userP_nickname = userSignUp.data.pNickname
                pref.userP_address1 = userSignUp.data.pAddress1
                pref.userP_address2 = userSignUp.data.pAddress2
                pref.userP_country_id = userSignUp.data.pCountryId
                pref.userP_state_id = userSignUp.data.pStateId
                pref.userP_city = userSignUp.data.pCity
                pref.userP_zip = userSignUp.data.pZip
                pref.userP_timezone_id = userSignUp.data.pTimezoneId
                pref.userIsEmailConfirm = userSignUp.data.isEmailConfirm
                pref.userToken = userSignUp.data.token
                pref.userFacebook_id = userSignUp.data.facebookId
                pref.userGoogle_id = userSignUp.data.googleId
                pref.userIs_active = userSignUp.data.isActive
                pref.userIs_anonymous = userSignUp.data.isAnonymous
                pref.prof_stage = userSignUp.data.profStage
                pref.userP_status = userSignUp.data.pStatus
                pref.userdevice_type = userSignUp.data.deviceType
                pref.userdevice_id = userSignUp.data.deviceId
                pref.userdevice_token = userSignUp.data.deviceToken
                pref.userotp = userSignUp.data.otp
                pref.userCreated_on = userSignUp.data.createdOn
                pref.userUpdated_on = userSignUp.data.updatedOn
                pref.userUpdated_by = userSignUp.data.updatedBy
                pref.userBToken = userSignUp.token
                pref.userPat_id = userSignUp.data.patId
                pref.userAccount_id = userSignUp.data.accountId
                pref.usercard_id = userSignUp.data.cardId
                pref.useraccount_holder_name = userSignUp.data.accountHolderName
                pref.useraccount_number = userSignUp.data.accountNumber
                pref.userexp_month = userSignUp.data.expMonth
                pref.userexp_year = userSignUp.data.expYear
                pref.usercvv = userSignUp.data.cvv
                pref.useraccount_token = userSignUp.data.accountToken
                pref.userisLogout = userSignUp.data.isLogout

            }).start()

        }
    }




    internal val providerSignUpObserver: Observer<ModelSignUp> = Observer<ModelSignUp> { providerSignUp ->
        hideLoading()

        if(providerSignUp!!.status== Constant.response_Failure)
        {
            showSnackBar(providerSignUp.message)
        }
        else  if(providerSignUp!!.status== Constant.RESPONSE_SUCCESSFULLY){

            showSnackBar(providerSignUp.message)
            val intent: Intent = Intent(this, EmailVerify::class.java)
            intent.putExtra("activity", "signup")
            intent.putExtra("type",getIntent().getStringExtra("type"))
            intent.putExtra("providertokenB",providerSignUp.token)
            startActivity(intent)


        MethodToRegisterFCMToken(pref.fcmToken, providerSignUp.token , "provider");

            Thread({
                pref.providerToken = providerSignUp.token
                pref.providerTokenU = providerSignUp.data.token
                pref.prof_stage = providerSignUp.data.profStage
                pref.providerAccount_id = providerSignUp.data.accountId
                pref.providerCreated_on = providerSignUp.data.createdOn
                pref.providerIsEmailConfirm = providerSignUp.data.isEmailConfirm
                pref.providerIs_reverify = providerSignUp.data.isReverify
                pref.providerStatus = providerSignUp.data.status
                pref.providerT_address1 = providerSignUp.data.tAddress1
                pref.providerT_city = providerSignUp.data.tCity
                pref.providerT_fname = providerSignUp.data.tFname
                pref.providerT_lname = providerSignUp.data.tLname
                pref.providerT_email = providerSignUp.data.tEmail
                pref.providerT_pswd = providerSignUp.data.tPswd
                pref.providerT_prof_pic = providerSignUp.data.tProfPic
                pref.providerT_gender = providerSignUp.data.tGender
                pref.providerT_dob = providerSignUp.data.tDob
                pref.providerT_phone = providerSignUp.data.tPhone
                pref.providerT_mobile = providerSignUp.data.tMobile
                pref.providerT_econtact = providerSignUp.data.tEcontact
                pref.providerT_country_id = providerSignUp.data.tCountryId
                pref.providerUpdated_on = providerSignUp.data.updatedOn
                pref.providerotp = providerSignUp.data.otp
                pref.providerdevice_id = providerSignUp.data.deviceId
                pref.providerdevice_type = providerSignUp.data.deviceType
                pref.providerT_timezone_id = providerSignUp.data.tTimezoneId
                pref.providerT_zip = providerSignUp.data.tZip
                pref.providerT_country_id = providerSignUp.data.tCountryId
                pref.t_address2 = providerSignUp.data.tAddress2

//                /    "Connect_Account_id": "cus_FKFay2Wqr8wbcE",
//            "strip_bank_id": "",
//            "regionBank": "",
//            "account_holder_name": "",
//            "account_number": "",
//            "bank_name": "",
//            "address_on_bank": "",

                pref.provider_ConnectAc_Id = providerSignUp.data.connectAccountId
                pref.provider_strip_bank_id = providerSignUp.data.stripBankId
                pref.provider_regionBank = providerSignUp.data.regionBank
                pref.provider_account_holder_name = providerSignUp.data.accountHolderName
                pref.provider_account_number = providerSignUp.data.accountNumber
                pref.provider_bank_name = providerSignUp.data.bankName
                pref.provider_address_on_bank = providerSignUp.data.addressOnBank


            }).start()

        }
    }


    private fun getSignInIntentData() {
        if (intent.getStringExtra("type").equals("user")) {
            signupBinding.llUserInformation.visibility = View.VISIBLE
            signupBinding.llProviderInformation.visibility = View.GONE
        } else
        {
            signupBinding.llProviderInformation.visibility = View.VISIBLE
            signupBinding.llUserInformation.visibility = View.GONE

            if(NetworkUtils.isNetworkConnected(this))
            {
                showLoading()
                signupViewModel!!.getProficiencyList()
                hideKeyboard()
                signupViewModel.obProficiency()!!.observe(this,proficiencyObserver)
            }
            else
            {
                showMessage(R.string.msg_check_internet)
                finish()
            }
        }
    }

    fun onNextClick(view: View) {
        onCreateActionClick(view);
    }
    private fun getDataFromSelectorActivity() {

        if (intent.getStringExtra("type") != null) {

            var type: String = intent.getStringExtra("type")

            if (type.equals("provider")) {
                signupBinding.tvHealthInformation.setText("Your professional details")
                signupBinding.tvSubSignuplevel.setText("You are signing up for provider")
                signupBinding.etDateOfBirth.setHint("Profession title")
                signupBinding.etProviderExp.setHint("Years of Experience")
                signupBinding.tvHealthInformation.setHint("Profession")
            } else {
                signupBinding.etDateOfBirth.addTextChangedListener(this)
                signupBinding.etProviderSpecialization.addTextChangedListener(this)
                signupBinding.etUsernumber.addTextChangedListener(this)
            }
        }
    }

    fun onClickBackButton(view: View) {
        finish()
    }

    fun onCreateActionClick(view: View) {
        if(intent.getStringExtra("type").equals("user")) {

            if (signupViewModel.checkValidationUser(
                    signupBinding.etUsername.text.toString(),
                    signupBinding.etCreateusername.text.toString(),
                    signupBinding.etUserid.text.toString(),
                    signupBinding.etUsernumber.text.toString(),
                    signupBinding.etUserpassword.text.toString(),
                    signupBinding.etDateOfBirth.text.toString(),
                    signupBinding.etUserHeight.text.toString(),
                    signupBinding.etUserWeight.text.toString()
                )
            ) {
                if (signupBinding!!.rules.isChecked) {

                if (NetworkUtils.isNetworkConnected(this)) {

                    if (signupBinding!!.rules.isChecked) {

                        if(signupViewModel.isGenderSelected(genderId)) {

                            if (CommonUtils.getAge(signupBinding.etDateOfBirth.text.toString()) >= 17)
                            {
                                if (NetworkUtils.isNetworkConnected(this)) {

                                    showLoading()
                                    signupViewModel.registerUser(
                                        signupBinding.etUsername.text.toString(),
                                        signupBinding.etCreateusername.text.toString(),
                                        signupBinding.etUserid.text.toString(),
                                        signupBinding.etUsernumber.text.toString(),
                                        signupBinding.etUserpassword.text.toString(),
                                        signupBinding.etDateOfBirth.text.toString(),
                                        signupBinding.etUserHeight.text.toString(),
                                        signupBinding.etUserWeight.text.toString(),
                                        genderId
                                    )

                                    hideKeyboard()
                                    signupViewModel.getSignUpUserNotify()!!.observe(this, UserSignUpObserver)

//                            startActivity(Intent(this, EmailVerify::class.java))

                                } else {
                                    showMessage(getString(R.string.msg_check_internet))
                                }
                        }
                        else
                        {
                            showSnackBar("You are not eligible because your age is below 18")
                        }

                        }
                        else
                        {
                            showMessage(getString(R.string.msg_select_gender))
                        }


                    } else
                        showMessage("Please select term and condition")


                } else {
                    showMessage(getString(R.string.msg_check_internet))
                }

                } else
                    showMessage("Please select term and condition")
            } else {
                showMessage("Please fill all the data!")
            }

        }
        else
        {



            if (signupViewModel.checkValidationProvider(
                    signupBinding.etUsername.text.toString(),
                    signupBinding.etCreateusername.text.toString(),
                    signupBinding.etUserid.text.toString(),
                    signupBinding.etUsernumber.text.toString(),
                    signupBinding.etUserpassword.text.toString(),
                    signupBinding.etProffessionTitle.text.toString(),
                    signupBinding.etProviderExp.text.toString(),
                    proficientId
                )) {

                if (signupBinding!!.rules.isChecked) {

                    if(signupViewModel.isGenderSelected(genderId))
                    {

                        if (NetworkUtils.isNetworkConnected(this)) {

                             showLoading()
                                signupViewModel.registerProvider(signupBinding.etUsername.text.toString(),
                                    signupBinding.etCreateusername.text.toString(),
                                signupBinding.etUserid.text.toString(),
                                signupBinding.etUsernumber.text.toString(),
                                signupBinding.etUserpassword.text.toString(),
                                signupBinding.etProffessionTitle.text.toString(),
                                signupBinding.etProviderExp.text.toString(),
                                proficientId,genderId)

                               hideKeyboard()
                               signupViewModel.getSignUpProviderNotify()!!.observe(this,providerSignUpObserver)

//                            startActivity(Intent(this, EmailVerify::class.java))

                        } else {
                            showMessage(getString(R.string.msg_check_internet))
                        }

                    }
                    else
                    {
                        showMessage(getString(R.string.msg_select_gender))
                    }


                } else
                    showMessage("Please select term and condition")
            } else {
                showMessage("Please fill all the data!")
            }

        }
    }

    fun OnClickAlreadySingin(view: View) {
        startActivity(Intent(this, SignIn::class.java))
    }

    fun onClickFemale(view : View)
    {
        changeGenderBackgroundSetting(signupBinding.female)

        genderId = 2

    }

    fun onClickMale(view : View)
    {
        changeGenderBackgroundSetting(signupBinding.male)
        genderId = 1

    }

    fun onClickOthers(view : View)
    {
        changeGenderBackgroundSetting(signupBinding.others)
        genderId = 3

    }



    var cal = Calendar.getInstance()

    val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        signupBinding.etDateOfBirth.setText(sdf.format(cal.time))
    }

    fun selectUserDate (view : View)
    {
        DatePickerDialog(this@signup_activity, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun changeGenderBackgroundSetting(one: TextView)
    {
        signupBinding.female.setBackgroundResource(R.drawable.et_corner)
        signupBinding.male.setBackgroundResource(R.drawable.et_corner)
        signupBinding.others.setBackgroundResource(R.drawable.et_corner)

        one.setBackgroundResource(R.color.colorPrimary)

    }

    private fun MethodToRegisterFCMToken(fcmToken: String, deviceId: String, type : String ) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()


        paramObject["device_id"] = deviceId
        paramObject["device_token"] = fcmToken
        paramObject["device_type"] = Constant.deviceType
        paramObject["customer"] = type

        val call = apiService.registerFCMToken(paramObject , "Bearer " + deviceId)
        showLoading()
        call.enqueue(object : retrofit2.Callback<ModelFCmTokenResponse> {
            override fun onResponse(call: Call<ModelFCmTokenResponse>, response: Response<ModelFCmTokenResponse>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        pref.fcmToken = fcmToken
                        hideLoading()

                    }else {
                        hideLoading()
                        showSnackBar("Some problem occur while fetching Token. Please try again!")
                    }
                }
            }



            override fun onFailure(call: Call<ModelFCmTokenResponse>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

}
