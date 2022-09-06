package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class EditAddressHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearDeliverTo, linearAddAddress,linearAddress,linearTownship;
    public CustomTextView add_address_text, delivery_price_text,txtTownship,txtContinue,txtPickupTitle;
    public CustomEditText edAddress;
    public ImageView switch1;
    public LinearLayout linearPickUp;


    public EditAddressHolder(@NonNull View itemView) {
        super(itemView);


        linearDeliverTo = itemView.findViewById(R.id.linearDeliverTo);
        linearAddAddress = itemView.findViewById(R.id.linearAddAddress);
        add_address_text = itemView.findViewById(R.id.add_address_text);
        delivery_price_text = itemView.findViewById(R.id.delivery_price_text);
        edAddress = itemView.findViewById(R.id.edAddress);
        linearAddress = itemView.findViewById(R.id.linearAddress);
        linearTownship = itemView.findViewById(R.id.linearTownship);
        txtTownship = itemView.findViewById(R.id.txtTownship);
        txtContinue = itemView.findViewById(R.id.txtContinue);
        txtPickupTitle = itemView.findViewById(R.id.txtPickupTitle);
        switch1 = itemView.findViewById(R.id.switch1);
        linearPickUp = itemView.findViewById(R.id.linearPickUp);
    }
}
