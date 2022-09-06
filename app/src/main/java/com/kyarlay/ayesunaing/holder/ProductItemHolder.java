package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.SlantedTextView;

public class ProductItemHolder extends RecyclerView.ViewHolder {

    public NetworkImageView gridItemImageView, gridItemImageViewTwo;
    public SlantedTextView sold_out, sold_outTwo;
    public CustomTextView pre_order,present,txt_one_day,txtTitle, price,price_strike;
    public CustomTextView pre_orderTwo,presentTwo,txt_one_dayTwo,txtTitleTwo, priceTwo,price_strikeTwo;
    //public RibbonView discount;
    public CustomTextView discount,present_hidden,discount_hidden;
    public CustomTextView discountTwo,present_hiddenTwo,discount_hiddenTwo;
    public ImageView img_one_day,img_one_today;
    public ImageView img_one_dayTwo,img_one_todayTwo;
    public LinearLayout linearMain, linearHidden;
    public LinearLayout linearMainTwo, linearHiddenTwo;
    public CardView cardOne, cardTwo;

    public ProductItemHolder(@NonNull View itemView) {
        super(itemView);

        gridItemImageView = itemView.findViewById(R.id.gridItemImageView);
        gridItemImageViewTwo = itemView.findViewById(R.id.gridItemImageViewTwo);

        sold_out = itemView.findViewById(R.id.sold_out);
        sold_outTwo = itemView.findViewById(R.id.sold_outTwo);

        discount = itemView.findViewById(R.id.discount);
        discountTwo = itemView.findViewById(R.id.discountTwo);

        pre_order = itemView.findViewById(R.id.pre_order);
        pre_orderTwo = itemView.findViewById(R.id.pre_orderTwo);

        presentTwo = itemView.findViewById(R.id.presentTwo);
        present = itemView.findViewById(R.id.present);

        txt_one_day = itemView.findViewById(R.id.txt_one_day);
        txt_one_dayTwo = itemView.findViewById(R.id.txt_one_dayTwo);

        img_one_day = itemView.findViewById(R.id.img_one_day);
        img_one_dayTwo = itemView.findViewById(R.id.img_one_dayTwo);

        txtTitle = itemView.findViewById(R.id.txtTitle);
        txtTitleTwo = itemView.findViewById(R.id.txtTitleTwo);

        price = itemView.findViewById(R.id.price);
        priceTwo = itemView.findViewById(R.id.priceTwo);


        linearMain = itemView.findViewById(R.id.linearMain);
        linearMainTwo = itemView.findViewById(R.id.linearMainTwo);
        linearHidden = itemView.findViewById(R.id.linearHidden);
        linearHiddenTwo = itemView.findViewById(R.id.linearHiddenTwo);


        price_strike = itemView.findViewById(R.id.price_strike);
        price_strikeTwo = itemView.findViewById(R.id.price_strikeTwo);

        img_one_today = itemView.findViewById(R.id.img_one_today);
        img_one_todayTwo = itemView.findViewById(R.id.img_one_todayTwo);


        present_hidden = itemView.findViewById(R.id.present_hidden);
        present_hiddenTwo = itemView.findViewById(R.id.present_hiddenTwo);

        discount_hidden = itemView.findViewById(R.id.discount_hidden);
        discount_hiddenTwo = itemView.findViewById(R.id.discount_hiddenTwo);

        cardOne = itemView.findViewById(R.id.cardOne);
        cardTwo = itemView.findViewById(R.id.cardTwo);
    }
}
