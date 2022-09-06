package com.kyarlay.ayesunaing.holder;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 9/27/16.
 */
public class OrderDetailHeaderHolder extends RecyclerView.ViewHolder{
    public CustomTextView date, totalPrice, deliveryPrice, name, phoneNo, address;

    public OrderDetailHeaderHolder(View itemView) {
        super(itemView);
        date             = (CustomTextView) itemView.findViewById(R.id.order_date);
        totalPrice       = (CustomTextView) itemView.findViewById(R.id.order_total_price);
        deliveryPrice    = (CustomTextView) itemView.findViewById(R.id.order_delivery_price);
        name             = (CustomTextView) itemView.findViewById(R.id.order_name);
        phoneNo          = (CustomTextView) itemView.findViewById(R.id.order_phone_no);
        address          = (CustomTextView) itemView.findViewById(R.id.order_address);
    }

}
