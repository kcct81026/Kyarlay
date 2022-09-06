package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class OrderPendingHolder extends RecyclerView.ViewHolder {

    public NetworkImageView img;
    public CustomTextView txtTitle, txtInfo, txtDetail;
    public LinearLayout layout;


    public OrderPendingHolder(@NonNull View itemView) {
        super(itemView);


        img = itemView.findViewById(R.id.img);
        txtDetail = itemView.findViewById(R.id.txtDetail);
        layout = itemView.findViewById(R.id.layout);
    }
}
