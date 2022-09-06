package com.kyarlay.ayesunaing.custom_widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Switch;

import com.kyarlay.ayesunaing.data.AppController;

import me.myatminsoe.mdetect.MDetect;

/**
 * Created by ayesu on 3/18/18.
 */

public class CustomSwitchView extends Switch {
    public CustomSwitchView(Context context) {
        super(context);
    }

    public CustomSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,context);
    }

    public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomSwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs,context);
    }

    private void init(AttributeSet attrs, Context context) {
        Typeface myTypeface;
        if (MDetect.INSTANCE.isUnicode()){
            myTypeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
        }
        else{
            myTypeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
        }
        setTypeface(myTypeface);

    }
}
