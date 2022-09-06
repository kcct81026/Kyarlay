package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.kyarlay.ayesunaing.data.AppController;

import me.myatminsoe.mdetect.MDetect;

public class EditTextBackPressed extends androidx.appcompat.widget.AppCompatEditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public EditTextBackPressed(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EditTextBackPressed(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public EditTextBackPressed(Context context, AttributeSet attrs, int defStyle) {
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

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // User has pressed Back key. So hide the keyboard
            InputMethodManager mgr = (InputMethodManager)

                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(this.getWindowToken(), 0);
            // TODO: Hide your view as you do it in your activity
        }
        return false;
    }


}