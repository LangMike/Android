package com.amindset.ve.amindset.di;

import android.content.Context;



import javax.inject.Singleton;

import com.amindset.ve.amindset.BaseActivity.UserRepository;
import com.amindset.ve.amindset.data.AppPreferencesHelper;
import com.amindset.ve.amindset.data.PreferencesHelper;
import dagger.Module;
import dagger.Provides;

@Module
public class UserRepositoryModule {
    private Context context;

    public UserRepositoryModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public UserRepository provideUserRepository() {
        return new UserRepository();
    }

    @Singleton
    @Provides
    public PreferencesHelper providePrefHelper() {
        return new AppPreferencesHelper(context);
    }


}