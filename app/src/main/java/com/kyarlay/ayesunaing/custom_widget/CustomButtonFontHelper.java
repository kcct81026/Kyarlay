package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kyarlay.ayesunaing.R;

import me.myatminsoe.mdetect.MDetect;


/**
 * Created by aye on 6/29/15.
 */
public class CustomButtonFontHelper  {
    /**
     * Sets a font on a textview based on the custom com.my.package:font attribute
     * If the custom font attribute isn't found in the attributes nothing happens
     * @param textview
     * @param context
     * @param attrs
     */
    public static void setCustomFont(TextView textview, Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
      //  String font = a.getString(R.styleable.CustomFont_font);
        //setCustomFont(textview, font, context);

        String font;
        if (MDetect.INSTANCE.isUnicode()){
            font = "fonts/pyidaungsu.ttf";
        }
        else{
            font = "fonts/zawgyitwo.ttf";
        }

        setCustomFont(textview, font, context);
        a.recycle();
    }

    /**
     * Sets a font on a textview
     * @param textview
     * @param font
     * @param context
     */
    public static void setCustomFont(TextView textview, String font, Context context) {
        if(font == null) {
            return;
        }
        Typeface tf = CustomButtonFontCache.get(font, context);
        if(tf != null) {
            textview.setTypeface(tf);
        }
    }

}
