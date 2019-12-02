package com.amindset.ve.amindset.di;


import com.amindset.ve.amindset.BaseActivity.BaseActivity;
import com.amindset.ve.amindset.ForgotPassword.ViewModelForgotPassword;
import com.amindset.ve.amindset.NewPassword.ResetNewPassword;
import com.amindset.ve.amindset.NewPassword.ViewModelResetNewPassword;
import com.amindset.ve.amindset.OTP.EmailVerify;
import com.amindset.ve.amindset.OTP.ViewModelEmailVerify;
import com.amindset.ve.amindset.SignUp.ViewModelSignUp;
import com.amindset.ve.amindset.SignUp.signup_activity;
import com.amindset.ve.amindset.Signin.SignIn;
import com.amindset.ve.amindset.Signin.SignInViewModel;

import javax.inject.Singleton;

import com.amindset.ve.amindset.Splash.splash;
import com.amindset.ve.amindset.VdoCall.VideoActivity;
import com.amindset.ve.amindset.VdoCall.vdocallViewModel;
import com.amindset.ve.amindset.VoiceCAll.VoiceCallActivity;
import dagger.Component;

@Singleton
@Component(modules = {UserRepositoryModule.class})
public interface MyComponent {

    void inject(SignInViewModel signInViewModel);

    void inject(ViewModelForgotPassword viewModelForgotPassword);

    void inject(ViewModelEmailVerify viewModelEmailVerify);

    void inject(ViewModelSignUp viewModelSignUp);

    void inject(signup_activity signup_activity);

    void inject(SignIn signIn);

    void inject(ViewModelResetNewPassword viewModelResetNewPassword);

    void inject(EmailVerify emailVerify);

    void inject(ResetNewPassword resetNewPassword);


    void inject(vdocallViewModel vdocallViewModel);
    void inject(VoiceCallActivity voiceCallActivity);

    void inject(splash splash);
    void inject(VideoActivity videoActivity);


}