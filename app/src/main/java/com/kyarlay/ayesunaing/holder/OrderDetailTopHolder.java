package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class OrderDetailTopHolder extends RecyclerView.ViewHolder {

    public TextView txtOrderDate,txtOrderId,txtOrderStatus;
    public CustomTextView txtDeliveryAddresss,txtDeliverySlot;
    public LinearLayout linearOrderStatus,linearOrderPlaced,linearOrderConfirmed,linearOrderReady,linearOrderDelivery;
    public TextView orderdeliveyTitle,orderreadyTitle,orderconfirmTitle,orderplaceTitle;
    public TextView txtOrderPlaced,txtOrderConfrimed,txtOrderReady,txtOrderDelivery;
    public ImageView imgCheck;

    public OrderDetailTopHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
        txtOrderId = itemView.findViewById(R.id.txtOrderId);
        txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
        txtDeliveryAddresss = itemView.findViewById(R.id.txtDeliveryAddresss);
        txtDeliverySlot = itemView.findViewById(R.id.txtDeliverySlot);
        linearOrderStatus = itemView.findViewById(R.id.linearOrderStatus);
        linearOrderPlaced = itemView.findViewById(R.id.linearOrderPlaced);
        linearOrderConfirmed = itemView.findViewById(R.id.linearOrderConfirmed);
        linearOrderReady = itemView.findViewById(R.id.linearOrderReady);
        linearOrderDelivery = itemView.findViewById(R.id.linearOrderDelivery);
        orderdeliveyTitle = itemView.findViewById(R.id.orderdeliveyTitle);
        orderreadyTitle = itemView.findViewById(R.id.orderreadyTitle);
        orderconfirmTitle = itemView.findViewById(R.id.orderconfirmTitle);
        orderplaceTitle = itemView.findViewById(R.id.orderplaceTitle);

        txtOrderPlaced = itemView.findViewById(R.id.txtOrderPlaced);
        txtOrderConfrimed = itemView.findViewById(R.id.txtOrderConfrimed);
        txtOrderReady = itemView.findViewById(R.id.txtOrderReady);
        txtOrderDelivery = itemView.findViewById(R.id.txtOrderDelivery);
        imgCheck = itemView.findViewById(R.id.imgCheck);
    }
}
