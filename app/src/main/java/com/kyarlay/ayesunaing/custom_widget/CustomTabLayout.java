package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;

import me.myatminsoe.mdetect.MDetect;


/**
 * Created by user on 12/8/15.
 */
public class CustomTabLayout extends TabLayout {
    private Typeface mTypeface;

    public CustomTabLayout(Context context) {
        super(context);
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        if (MDetect.INSTANCE.isUnicode()){
            mTypeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
        }
        else{
            mTypeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
        }

    }

    @Override
    public void addTab(Tab tab) {
        super.addTab(tab);

        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                //tabViewChild.setBackground(getResources().getDrawable(R.drawable.tab_color_selector));
                ((TextView) tabViewChild).setGravity(GRAVITY_CENTER);
                ((TextView) tabViewChild).setTextSize(getResources().getDimension(R.dimen.text));
                ((TextView) tabViewChild).setTypeface(mTypeface);


            }
        }
    }

}