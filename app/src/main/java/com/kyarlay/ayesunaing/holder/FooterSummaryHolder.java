package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class FooterSummaryHolder extends RecyclerView.ViewHolder {

    public CustomTextView summary_title,product_discount_text,
            product_discount,flash_discount_text,flash_discount,
            member_discount_text,member_discount,point_text,point_use,
            total_price_text,total_price,title_use_point,gift_text,gift;
    public LinearLayout product_discount_layout,gift_layout,flesh_layout,
            member_layout,point_use_layout, point_layout,linearShopping;
    public RadioButton radioPoint;

    public FooterSummaryHolder(@NonNull View itemView) {
        super(itemView);

        summary_title = itemView.findViewById(R.id.summary_title);
        product_discount_text = itemView.findViewById(R.id.product_discount_text);
        product_discount = itemView.findViewById(R.id.product_discount);
        product_discount_layout = itemView.findViewById(R.id.product_discount_layout);
        flesh_layout = itemView.findViewById(R.id.flesh_layout);
        flash_discount_text = itemView.findViewById(R.id.flash_discount_text);
        flash_discount = itemView.findViewById(R.id.flash_discount);
        member_layout = itemView.findViewById(R.id.member_layout);
        member_discount_text = itemView.findViewById(R.id.member_discount_text);
        member_discount = itemView.findViewById(R.id.member_discount);
        point_text = itemView.findViewById(R.id.point_text);
        point_use = itemView.findViewById(R.id.point_use);
        total_price_text = itemView.findViewById(R.id.total_price_text);
        total_price = itemView.findViewById(R.id.total_price);
        point_use_layout = itemView.findViewById(R.id.point_use_layout);
        title_use_point = itemView.findViewById(R.id.title_use_point);
        radioPoint = itemView.findViewById(R.id.radioPoint);
        gift_layout = itemView.findViewById(R.id.gift_layout);
        gift_text = itemView.findViewById(R.id.gift_text);
        gift = itemView.findViewById(R.id.gift);
        point_layout = itemView.findViewById(R.id.point_layout);
        linearShopping = itemView.findViewById(R.id.linearShopping);
    }
}
