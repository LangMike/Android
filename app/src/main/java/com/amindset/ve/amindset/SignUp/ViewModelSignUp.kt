package com.amindset.ve.amindset.SignUp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.text.TextUtils
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.BaseActivity.UserRepository
import com.amindset.ve.amindset.SignUp.DataModel.ModelProficiencyList
import com.amindset.ve.amindset.SignUp.DataModel.ModelSignUp.ModelSignUp
import com.amindset.ve.amindset.SignUp.UsersignUp.ModelUserSignUp
import com.amindset.ve.amindset.Utils.CommonUtils
import javax.inject.Inject

class ViewModelSignUp (application : Application) : AndroidViewModel(application)
{

    init {
        (application as AmidApp).getMyComponent().inject(this@ViewModelSignUp)
    }

    @Inject
    lateinit var userRepository : UserRepository

    private var user: LiveData<ModelProficiencyList>? = null
    private var lvSignUpProvider: LiveData<ModelSignUp>? = null
    private var lvSignUpUser: LiveData<ModelUserSignUp>? = null


    fun  checkValidationUser(userName : String  , createUserName : String , userId : String ,usernumber : String,
                         userNewpassword : String, userAge : String , userHeight : String , userWeight : String ) : Boolean
    {
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(createUserName) &&
            !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(usernumber)
            && !TextUtils.isEmpty(userNewpassword)  && !TextUtils.isEmpty(userAge)
            && !TextUtils.isEmpty(userHeight) && !TextUtils.isEmpty(userWeight) )
            return true
        return false
    }

    fun  checkValidationProvider(providerName : String ,createUserName : String , providerId : String ,providerNumber : String,
                                 providerNewpassword : String, providerProfessionTitle : String , providerTotalExp : String , providerProfessionType : String ) : Boolean
    {
        if(!TextUtils.isEmpty(providerName) && !TextUtils.isEmpty(createUserName) &&
        !TextUtils.isEmpty(providerId) && !TextUtils.isEmpty(providerNumber)
            && !TextUtils.isEmpty(providerNewpassword)  && !TextUtils.isEmpty(providerProfessionTitle)
            && !TextUtils.isEmpty(providerTotalExp) && !TextUtils.isEmpty(providerProfessionType) )
            return true
        return false
    }


    fun emailValidation(email: String): Boolean {
        if (CommonUtils.isEmailValid(email))
            return true
        return false
    }

    fun isGenderSelected(id: Int): Boolean {
        if (id!=0)
            return true
        return false
    }


    fun checkAgeValidation(age: Int) : String
    {
        if(age>100)
        {
            return "Invalid Age"
        }

        if(age < 0)
            return "Invalid Age"

        return ""
    }

    fun isPassworSame(password: String, repassword : String): Boolean {
        if(password.equals(repassword)){
            return true
        }
        return false
    }

     fun isValidMobile(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }



    fun getProficiencyList()
    {
        user= userRepository.getProficiency();
    }


    fun obProficiency() : LiveData<ModelProficiencyList>?
    {
       return user
    }

    fun registerProvider(providerName : String , createUserName : String , providerEmailId : String  ,
                     providerPhoneNumber : String,providerPassword : String, providerProfessiontitle : String,
                     providerExp : String,proficientId : String, providerGenderid : Int)
    {
        lvSignUpProvider= userRepository.registerProvider(providerName,createUserName , providerEmailId,providerPhoneNumber,
            providerPassword,providerProfessiontitle,providerExp,proficientId,""+providerGenderid);
    }

    fun getSignUpProviderNotify(): LiveData<ModelSignUp>? {
        return lvSignUpProvider
    }


    fun registerUser(userName : String  , createUserName : String , userId : String ,usernumber : String,
                     userNewpassword : String, userAge : String , userHeight : String , userWeight : String ,providerGenderid : Int)
    {
        lvSignUpUser= userRepository.registerUser(userName,createUserName , userId,usernumber,
            userNewpassword,userAge,userHeight,userWeight,""+providerGenderid);
    }

    fun getSignUpUserNotify(): LiveData<ModelUserSignUp>? {
        return lvSignUpUser
    }
}