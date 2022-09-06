package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class OrderHistoryHolder extends RecyclerView.ViewHolder {

    public CustomTextView txtOrderTime, txtOrderId, txtOrderPrice, txtOrderStatus, txtRatingTitle,txtDeliveredDate;
    public ImageView imgCheck;
    public LinearLayout linearView;
    public TextView txtItem;

    public OrderHistoryHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderTime = itemView.findViewById(R.id.txtOrderTime);
        txtOrderId = itemView.findViewById(R.id.txtOrderId);
        txtOrderPrice = itemView.findViewById(R.id.txtOrderPrice);
        txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
        txtRatingTitle = itemView.findViewById(R.id.txtRatingTitle);
        imgCheck = itemView.findViewById(R.id.imgCheck);
        linearView = itemView.findViewById(R.id.linearView);
        txtDeliveredDate = itemView.findViewById(R.id.txtDeliveredDate);
        txtItem = itemView.findViewById(R.id.txtItem);


    }
}
