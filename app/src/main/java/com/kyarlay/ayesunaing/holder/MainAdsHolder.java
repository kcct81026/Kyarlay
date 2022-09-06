package com.kyarlay.ayesunaing.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.kyarlay.ayesunaing.R;

/**
 * Created by kcct on 9/5/16.
 */

/*

 */
public class MainAdsHolder extends RecyclerView.ViewHolder{

    public SliderLayout mDemoSlider;
    public RelativeLayout relativeSlider;
    public ProgressBar progressBar;


    public MainAdsHolder(View itemView, Activity activity) {
        super(itemView);

        mDemoSlider     = (SliderLayout) itemView.findViewById(R.id.slider);
        relativeSlider  = (RelativeLayout) itemView.findViewById(R.id.relative_slider);
        progressBar     = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        mDemoSlider.setId(0);
        //mDemoSlider.startAutoCycle(2000,2000,true);
       // mDemoSlider.stopAutoCycle();
        mDemoSlider.setCustomIndicator((PagerIndicator) itemView.findViewById(R.id.custom_indicator));
    }
}
