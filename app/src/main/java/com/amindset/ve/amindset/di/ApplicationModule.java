package com.amindset.ve.amindset.di;

import android.app.Application;
import com.amindset.ve.amindset.data.AppPreferencesHelper;
import com.amindset.ve.amindset.data.PreferencesHelper;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by Satendra Singh on 27/01/17.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }


    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

}
