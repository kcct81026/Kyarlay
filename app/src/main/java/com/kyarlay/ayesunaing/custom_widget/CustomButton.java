package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by aye on 6/29/15.
 */
public class CustomButton extends androidx.appcompat.widget.AppCompatButton {

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomButtonFontHelper.setCustomFont(this, context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomButtonFontHelper.setCustomFont(this, context, attrs);
    }
}