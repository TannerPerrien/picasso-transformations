
package com.example.picassotransformations;

import android.app.Application;
import android.content.Context;

public class SampleApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

}
