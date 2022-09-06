package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;

import me.myatminsoe.mdetect.MDetect;


/**
 * Created by aye on 6/29/15.
 */
public class CustomTextView extends androidx.appcompat.widget.AppCompatTextView {

    private static final String TAG = "CustomTextView";
    Typeface typeface;

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs,context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);

    }

    public CustomTextView(Context context) {
        super(context);
        init(null,context);
    }



    private void init(AttributeSet attrs, Context context) {


        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);
        String fontName = a.getString(R.styleable.MyTextView_fontName);


        try {
            if (MDetect.INSTANCE.isUnicode()){
                this.typeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
                setTypeface(typeface);
            }
            else{
                this.typeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
                setTypeface(typeface);
            }
        }catch (Exception e){
            Log.e(TAG, "init: Error :  " + e.getMessage() );
        }



        //setTypeface(new AppController().pyiDaungSuTP);

    }

}
