package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class ProductDetailsHolder extends RecyclerView.ViewHolder {

    public RelativeLayout relative, relative_slider;
    public LinearLayout linearImage,linearFlashSale,linearLayout1,discount_layout;
    public SliderLayout slider;
    public CustomTextView txtDiscount,txtAllMark,title,txtCategory,txtPreOrderMark,price,price_strike,txtAvailable,promotion_title,txtFirstTime;
    public TextView txtImgCount,txtDay,txtDayONe,txtHour,txtHourONe,txtMinute,txtMinuteONe,txtSecond,txtSecondONe;
    public ImageView imgShare,imgWishList,imgChat,img_car;
    public FrameLayout frameLayout;
    public NetworkImageView imgYouTube;
    public PagerIndicator custom_indicator;

    public CustomTextView txtProductWarning;
    public LinearLayout linearProductWarning,layout_first_time;

    public ProductDetailsHolder(@NonNull View itemView) {
        super(itemView);

        relative = itemView.findViewById(R.id.relative);
        relative_slider = itemView.findViewById(R.id.relative_slider);
        slider = itemView.findViewById(R.id.slider);
        slider.setId(0);
        slider.startAutoCycle(10000,10000,true);
        custom_indicator = itemView.findViewById(R.id.custom_indicator);
        custom_indicator.setVisibility(View.GONE);
        //slider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

        txtDiscount = itemView.findViewById(R.id.txtDiscount);
        linearImage = itemView.findViewById(R.id.linearImage);
        txtImgCount = itemView.findViewById(R.id.txtImgCount);
        txtAllMark = itemView.findViewById(R.id.txtAllMark);
        imgShare = itemView.findViewById(R.id.imgShare);
        imgWishList = itemView.findViewById(R.id.imgWishList);
        imgChat = itemView.findViewById(R.id.imgChat);
        title = itemView.findViewById(R.id.title);
        txtCategory = itemView.findViewById(R.id.txtCategory);
        txtPreOrderMark = itemView.findViewById(R.id.txtPreOrderMark);
        price = itemView.findViewById(R.id.price);
        price_strike = itemView.findViewById(R.id.price_strike);
        linearFlashSale = itemView.findViewById(R.id.linearFlashSale);
        linearLayout1 = itemView.findViewById(R.id.linearLayout1);
        txtDay = itemView.findViewById(R.id.txtDay);
        txtDayONe = itemView.findViewById(R.id.txtDayONe);
        txtHour = itemView.findViewById(R.id.txtHour);
        txtHourONe = itemView.findViewById(R.id.txtHourONe);
        txtMinute = itemView.findViewById(R.id.txtMinute);
        txtMinuteONe = itemView.findViewById(R.id.txtMinuteONe);
        txtSecond = itemView.findViewById(R.id.txtSecond);
        txtSecondONe = itemView.findViewById(R.id.txtSecondONe);
        txtAvailable = itemView.findViewById(R.id.txtAvailable);
        promotion_title = itemView.findViewById(R.id.promotion_title);
        discount_layout = itemView.findViewById(R.id.discount_layout);
        frameLayout = itemView.findViewById(R.id.product_detail_youtube_imageview_framelayout);
        imgYouTube = itemView.findViewById(R.id.product_detail_youtube_imageview);

        linearProductWarning = itemView.findViewById(R.id.linearProductWarning);
        layout_first_time = itemView.findViewById(R.id.layout_first_time);
        txtProductWarning = itemView.findViewById(R.id.txtProductWarning);
        txtFirstTime = itemView.findViewById(R.id.txtFirstTime);
        img_car = itemView.findViewById(R.id.img_car);
    }
}
