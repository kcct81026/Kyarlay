package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class ClinicHolder extends RecyclerView.ViewHolder {
    public CustomTextView txtTitle, txtAddress, txtPhone, txtWatch, txtDesc;
    public CustomTextView  addTitle, phoneTitle, descTitle;
    public NetworkImageView img;
    public CardView cardClinic;

    public ClinicHolder(@NonNull View itemView) {
        super(itemView);

        txtWatch = itemView.findViewById(R.id.txtWatch);
        txtTitle = itemView.findViewById(R.id.txtClinicTitle);
        txtAddress = itemView.findViewById(R.id.txtClinicAddress);
        txtPhone = itemView.findViewById(R.id.txtClinicPhone);
        img = itemView.findViewById(R.id.imgClicnic);
        cardClinic = itemView.findViewById(R.id.gridItemImageView_Cart);

        txtDesc = itemView.findViewById(R.id.txtClinicDesc);
        addTitle = itemView.findViewById(R.id.txtAddressTitle);
        phoneTitle = itemView.findViewById(R.id.txtPhoneTitle);
        descTitle = itemView.findViewById(R.id.txtDescTitle);

    }
}
