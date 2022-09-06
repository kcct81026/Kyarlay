package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by aye on 6/29/15.
 */
public class CustomButtonFontCache {
//
    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }

}
