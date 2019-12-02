package com.amindset.ve.amindset;

import android.app.Application;
import com.amindset.ve.amindset.di.DaggerMyComponent;
import com.amindset.ve.amindset.di.MyComponent;
import com.amindset.ve.amindset.di.UserRepositoryModule;

public class AmidApp extends Application  {


    private MyComponent mMyComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        mMyComponent = DaggerMyComponent.builder()
                .userRepositoryModule(new UserRepositoryModule(getApplicationContext()))
                .build();


    }


    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
    private static AmidApp singleton   ;

    public static AmidApp getInstance(){
        return singleton;
    }
    public MyComponent getMyComponent() {
        return mMyComponent;
    }

//    private MyComponent createMyComponent() {
//        return DaggerMyComponent
//                .builder()
//                .userRepositoryModule(new UserRepositoryModule(getApplicationContext()))
//                .build();
//
//    }




}
