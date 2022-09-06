package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;


public class PackageHolder extends RecyclerView.ViewHolder {
    public CustomTextView txtTitle, txtAddress, txtPhone, txtShowLess;
    public NetworkImageView img;
    public CardView cardClinic;

    public PackageHolder(@NonNull View itemView) {
        super(itemView);

        txtTitle = itemView.findViewById(R.id.txtClinicTitle);
        txtShowLess = itemView.findViewById(R.id.txtShowLess);
        txtAddress = itemView.findViewById(R.id.txtClinicAddress);
        txtPhone = itemView.findViewById(R.id.txtClinicPhone);
        img = itemView.findViewById(R.id.imgClicnic);
        cardClinic = itemView.findViewById(R.id.gridItemImageView_Cart);

    }
}

