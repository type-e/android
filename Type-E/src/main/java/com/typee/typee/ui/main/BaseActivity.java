package com.typee.typee.ui.main;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by winsonlim on 20/1/14.
 */
public class BaseActivity extends Activity {
    public String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // todo implement sliding menu here (open / back)
    }
}
