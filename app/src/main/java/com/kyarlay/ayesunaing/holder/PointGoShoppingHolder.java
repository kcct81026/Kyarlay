package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;

public class PointGoShoppingHolder extends RecyclerView.ViewHolder {

    public RelativeLayout layout_no_vip,layoutShopping;
    public LinearLayout layout_vip;
    public TextView txtNoVipTitle, txtNoVipAmount,txtNoVipMonthBetween,txtNoVipRemiderText,txtVipTitle,txtVipRemiderText,txtShoppingTitle;

    public PointGoShoppingHolder(@NonNull View itemView) {
        super(itemView);

        layout_no_vip = itemView.findViewById(R.id.layout_no_vip);
        txtNoVipTitle = itemView.findViewById(R.id.txtNoVipTitle);
        txtNoVipAmount = itemView.findViewById(R.id.txtNoVipAmount);
        txtNoVipMonthBetween = itemView.findViewById(R.id.txtNoVipMonthBetween);
        txtNoVipRemiderText = itemView.findViewById(R.id.txtNoVipRemiderText);
        txtVipTitle = itemView.findViewById(R.id.txtVipTitle);
        txtVipRemiderText = itemView.findViewById(R.id.txtVipRemiderText);
        layout_vip = itemView.findViewById(R.id.layout_vip);
        layoutShopping = itemView.findViewById(R.id.layoutShopping);
        txtShoppingTitle = itemView.findViewById(R.id.txtShoppingTitle);
    }
}
