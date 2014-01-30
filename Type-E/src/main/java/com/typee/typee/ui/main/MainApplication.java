package com.typee.typee.ui.main;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Winson Lim on 1/26/14.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "mFrewsKnWGVdz9AJJHfaN7WXKxq5LAz2Mp8B8mIR", "R88iw1KmwjFuUqaOxx8qceVGkzivBiQnKuICJCvJ");
    }
}
