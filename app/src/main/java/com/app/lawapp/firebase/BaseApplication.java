package com.app.lawapp.firebase;

import android.app.Application;
import android.content.Context;


/**
 * Created by Rahul Hooda on 14/7/17.
 */
public class BaseApplication extends Application {


    private static Application sApplication;

    public static Context getAppContext() {
        return getApplication().getApplicationContext();
    }

    public static Application getApplication() {

        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;

      /* PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51HcS1PBQD1DEOBM7fnLQVA4sR0JqVbnGKGPcUdzxyjp8lBK20TIPjSEQiUWt5yQwcGDzSCqWP4LYLaARnwjt5MQY000xxz1pxs"
        );*/
    }
}
