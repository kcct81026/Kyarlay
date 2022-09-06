package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class OrderTrackHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearOrderPlaced,linearOrderConfirmed,linearOrderReady,linearOrderDelivery,linearOrderStatus,linear_payment,linearOrder ;

    public TextView hide_order_place, hide_order_confirm,hide_order_ready,hide_order_deliver,txtCount,txtViewAll;

    public TextView orderTitle,confirmTitle,readyTitle,deliverTitle;

    public CustomTextView txtPayName,txtStatus,txtTotal,countTitle;
    public NetworkImageView imgPayment;

    public OrderTrackHolder(@NonNull View itemView) {
        super(itemView);

        linearOrderPlaced = itemView.findViewById(R.id.linearOrderPlaced);
        linearOrderConfirmed = itemView.findViewById(R.id.linearOrderConfirmed);
        linearOrderReady = itemView.findViewById(R.id.linearOrderReady);
        linearOrderDelivery = itemView.findViewById(R.id.linearOrderDelivery);
        linearOrderStatus = itemView.findViewById(R.id.linearOrderStatus);
        linear_payment = itemView.findViewById(R.id.linear_payment);

        hide_order_place = itemView.findViewById(R.id.hide_order_place);
        hide_order_confirm = itemView.findViewById(R.id.hide_order_confirm);
        hide_order_ready = itemView.findViewById(R.id.hide_order_ready);
        hide_order_deliver = itemView.findViewById(R.id.hide_order_deliver);

        imgPayment = itemView.findViewById(R.id.imgPayment);
        txtPayName = itemView.findViewById(R.id.txtPayName);
        txtStatus = itemView.findViewById(R.id.txtStatus);
        txtTotal = itemView.findViewById(R.id.txtTotal);

        linearOrder = itemView.findViewById(R.id.linearOrder);
        txtCount = itemView.findViewById(R.id.txtCount);
        countTitle = itemView.findViewById(R.id.countTitle);
        txtViewAll = itemView.findViewById(R.id.txtViewAll);

        orderTitle = itemView.findViewById(R.id.orderTitle);
        confirmTitle = itemView.findViewById(R.id.confirmTitle);
        readyTitle = itemView.findViewById(R.id.readyTitle);
        deliverTitle = itemView.findViewById(R.id.deliverTitle);

    }
}
