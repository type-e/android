package com.typee.typee.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by ndduy on 18/2/14.
 */

public class TypefaceHelper {

    public static final String ROBOTO_LIGHT = "Roboto-Light.ttf";
    public static final String ROBOTO_CONDENSED_REGULAR = "RobotoCondensed-Regular.ttf";
    public static final String ROBOTO_REGULAR = "Roboto-Regular.ttf";
    public static final String ROBOTO_BOLD = "Roboto-Bold.ttf";
    public static final String ROBOTO_MEDIUM = "Roboto-Medium.ttf";

    public static void applyFont(Context context, String font, TextView... textViews){

        if(context == null || textViews == null || font == null) {
            return;
        }

        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/"+font);
        for (TextView textView : textViews) {
            if(textView != null) {
                textView.setTypeface(typeFace);
            }
        }
    }
}
