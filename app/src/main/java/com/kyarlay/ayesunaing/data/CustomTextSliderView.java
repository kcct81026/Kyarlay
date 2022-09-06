package com.kyarlay.ayesunaing.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by kcct on 10/31/16.
 */

public class CustomTextSliderView extends BaseSliderView {
    public CustomTextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.custom_render_type_text,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        CustomTextView description = (CustomTextView)v.findViewById(R.id.description);


        description.setText(getDescription());
        bindEventAndShow(v, target);

        return v;
    }
}