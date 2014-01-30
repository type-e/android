package com.typee.typee.util;

import android.content.Context;
import android.content.Intent;

import com.typee.typee.ui.base.BaseActivity;

/**
 * Created by Winson Lim on 1/31/14.
 */
public class Util {
    public static final String FRAGMENT_CLASS_NAME = "FRAGMENT_CLASS_NAME";

    public static void startActivity(Context context, String className) {
        Intent openFragmentInActivityIntent = new Intent(context, BaseActivity.class);
        openFragmentInActivityIntent.putExtra(FRAGMENT_CLASS_NAME, className);

        context.startActivity(openFragmentInActivityIntent);
    }

}
