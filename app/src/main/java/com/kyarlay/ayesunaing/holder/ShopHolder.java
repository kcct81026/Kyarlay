package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.smarteist.autoimageslider.SliderView;

public class ShopHolder extends RecyclerView.ViewHolder{

    //public SliderLayout mDemoSlider;
    public RelativeLayout relativeSlider;
    public LinearLayout linearCall, linearShowLocation;
    public CustomTextView txtShopTitle, txtPhone, txtHidden, txtTownship, txtShowAddress;
    public SliderView sliderImageView;

    public ShopHolder(@NonNull View itemView) {
        super(itemView);

        relativeSlider  = (RelativeLayout) itemView.findViewById(R.id.relative_slider);


        txtShopTitle = itemView.findViewById(R.id.txtShopTitle);
        txtPhone = itemView.findViewById(R.id.txtPhone);
        txtHidden = itemView.findViewById(R.id.txtHidden);
        txtTownship = itemView.findViewById(R.id.txtTownship);
        linearCall = itemView.findViewById(R.id.linearCall);
        txtShowAddress = itemView.findViewById(R.id.txtShowAddress);
        linearShowLocation = itemView.findViewById(R.id.linearShowLocation);
        sliderImageView = itemView.findViewById(R.id.sliderImage);

    }
}
