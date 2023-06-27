package com.kyarlay.ayesunaing.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.smarteist.autoimageslider.SliderView;

/**
 * Created by kcct on 9/5/16.
 */

/*

 */
public class MainAdsHolder extends RecyclerView.ViewHolder{

    public RelativeLayout relativeSlider;
    public ProgressBar progressBar;
    public SliderView sliderImageView;


    public MainAdsHolder(View itemView, Activity activity) {
        super(itemView);

        relativeSlider  = (RelativeLayout) itemView.findViewById(R.id.relative_slider);
        progressBar     = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        sliderImageView = itemView.findViewById(R.id.sliderImage);
    }
}
