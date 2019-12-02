package com.amindset.ve.amindset.ProfessionalVerification

import android.arch.lifecycle.ViewModel
import android.text.TextUtils


class ViewModelProfessionalVerification : ViewModel()

{
    fun  checkValidation(userName : String , userId : String ,usernumber : String,
                         userNewpassword : String, userAge : String , userHeight : String , userWeight : String ) : Boolean
    {
        if(!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(usernumber))
            return true
        return false
    }
}