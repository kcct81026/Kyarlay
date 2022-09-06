package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class StepOneTopHolder extends RecyclerView.ViewHolder {


    public LinearLayout linearAddress,linearUserAddress,layout_pickup;
    public CustomTextView address_title,delivery_price_text,txtAddress,txtPickupTitle;
    public ImageView switch1;


    public StepOneTopHolder(@NonNull View itemView) {
        super(itemView);

        linearAddress = itemView.findViewById(R.id.linearAddress);
        address_title = itemView.findViewById(R.id.address_title);
        delivery_price_text = itemView.findViewById(R.id.delivery_price_text);
        txtAddress = itemView.findViewById(R.id.txtAddress);
        switch1 = itemView.findViewById(R.id.switch1);
        linearUserAddress = itemView.findViewById(R.id.linearUserAddress);
        txtPickupTitle = itemView.findViewById(R.id.txtPickupTitle);
        layout_pickup = itemView.findViewById(R.id.layout_pickup);
    }
}
