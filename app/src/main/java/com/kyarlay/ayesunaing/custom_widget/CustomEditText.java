package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.kyarlay.ayesunaing.data.AppController;

import me.myatminsoe.mdetect.MDetect;

/**
 * Created by aye on 6/29/15.
 */
public class CustomEditText extends androidx.appcompat.widget.AppCompatEditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public CustomEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {


        if (MDetect.INSTANCE.isUnicode()){
            Typeface font = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
            this.setTypeface(font);
        }
        else{
            Typeface font = AppController.typefaceManager.getCompanion().getZAWGYITWO();
            this.setTypeface(font);
        }
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        if (MDetect.INSTANCE.isUnicode()){
            tf = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
        }
        else{
            tf = AppController.typefaceManager.getCompanion().getZAWGYITWO();
        }
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        if (MDetect.INSTANCE.isUnicode()){
            tf = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
        }
        else{
            tf = AppController.typefaceManager.getCompanion().getZAWGYITWO();
        }
        super.setTypeface(tf);
    }




}