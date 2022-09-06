package com.kyarlay.ayesunaing.holder;


import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 3/22/17.
 */

public class FlashSalesHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, price, discounts, member_discount, txtAllChannel, txtMark,
            recommended, category_name, price_strike, addtocart_text, option;
    public NetworkImageView imageView;
    public CardView frameLayout;
    public ImageView wishlist;
    //public LinearLayout price_layout, text_layout, wishlist_layout, all,;
    public LinearLayout  text_layout, all;
    public View test;
    public RelativeLayout relativeLayout;
    //public CustomTextView tveventStart;
    public TextView txtDay, txtHour, txtMinute, txtSecond;
    public Runnable runnable;

    public LinearLayout linearLayout;

    public ProgressBar barFull, barFill;
    public CustomTextView barText, txtAvailable;
    public LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4, layout;
    public TextView bg_hide;
    public ImageView imgGetPoint;

    //public CountDownTimer countDownTimer;



    public FlashSalesHolder(View itemView, Display display) {
        super(itemView);
        txtAllChannel   =  itemView.findViewById(R.id.txtAllChannel);
        txtMark         =  itemView.findViewById(R.id.txtAllMark);
        title           =  itemView.findViewById(R.id.gridItemTitleView);
        price           =  itemView.findViewById(R.id.gridItemPriceView);
        imageView       =  itemView.findViewById(R.id.gridItemImageView);
        frameLayout     =  itemView.findViewById(R.id.gridItemImageView_Cart);
        wishlist        =  itemView.findViewById(R.id.wishlist);
        discounts       =  itemView.findViewById(R.id.discounts);
        text_layout     =  itemView.findViewById(R.id.text_layout);
        member_discount =  itemView.findViewById(R.id.member_discount);
        recommended     =  itemView.findViewById(R.id.recommended);
        category_name   =  itemView.findViewById(R.id.category_name);
        price_strike    =  itemView.findViewById(R.id.price_strike);
        all             =  itemView.findViewById(R.id.all);
        barFull             =  itemView.findViewById(R.id.barFull);
        barFill             =  itemView.findViewById(R.id.barFill);
        barText             =  itemView.findViewById(R.id.barText);
        txtAvailable             =  itemView.findViewById(R.id.txtAvailable);
        imgGetPoint             =  itemView.findViewById(R.id.imgGetPoint);
        layout             =  itemView.findViewById(R.id.layout);


        bg_hide  = itemView.findViewById(R.id.bg_hide);
        txtDay  = itemView.findViewById(R.id.txtDay);
        txtHour  = itemView.findViewById(R.id.txtHour);
        txtMinute  = itemView.findViewById(R.id.txtMinute);
        txtSecond  = itemView.findViewById(R.id.txtSecond);
        linearLayout  = itemView.findViewById(R.id.linearLayout);
        linearLayout1  = itemView.findViewById(R.id.linearLayout1);
        linearLayout2  = itemView.findViewById(R.id.linearLayout2);
        linearLayout3  = itemView.findViewById(R.id.linearLayout3);
        linearLayout4  = itemView.findViewById(R.id.linearLayout4);


        option          = itemView.findViewById(R.id.option);

        relativeLayout = itemView.findViewById(R.id.relative);

        relativeLayout.getLayoutParams().height = ( display.getWidth() *  3 )/ 6;
        relativeLayout.getLayoutParams().width  = ( display.getWidth() * 3 ) / 6;
        text_layout.getLayoutParams().width  = ( display.getWidth() * 3 ) / 6;


    }


}
