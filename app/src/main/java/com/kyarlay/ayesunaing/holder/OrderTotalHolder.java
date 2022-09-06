package com.kyarlay.ayesunaing.holder;


import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 9/27/16.
 */
public class OrderTotalHolder extends RecyclerView.ViewHolder{
    public CustomTextView orderNo, totalPrice, date, status, orderNoString, totalPriceString, dateString, statusString;
    public LinearLayout orderLayout, imageLayout, all;

    public OrderTotalHolder(View itemView) {
        super(itemView);
        all         = (LinearLayout) itemView.findViewById(R.id.all);
        orderNo     = (CustomTextView) itemView.findViewById(R.id.order_no);
        totalPrice  = (CustomTextView) itemView.findViewById(R.id.order_total_price);
        date        = (CustomTextView) itemView.findViewById(R.id.order_date);
        status      = (CustomTextView) itemView.findViewById(R.id.order_status);

        orderNoString     = (CustomTextView) itemView.findViewById(R.id.order_no_string);
        totalPriceString  = (CustomTextView) itemView.findViewById(R.id.order_total_price_string);
        dateString        = (CustomTextView) itemView.findViewById(R.id.order_date_string);
        statusString      = (CustomTextView) itemView.findViewById(R.id.order_status_string);


    }

}