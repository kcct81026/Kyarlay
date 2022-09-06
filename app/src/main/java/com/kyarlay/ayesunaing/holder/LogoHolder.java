package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class LogoHolder extends RecyclerView.ViewHolder {
    public CustomTextView txtTitle, txtAddress, txtPhone, txtWatch;
    public CustomTextView  addTitle, phoneTitle, descTitle;
    public NetworkImageView img;
    public CardView videoCard;


    public LogoHolder(@NonNull View itemView, Display display) {
        super(itemView);

        txtWatch = itemView.findViewById(R.id.txtWatch);
        txtTitle = itemView.findViewById(R.id.txtClinicTitle);
        txtAddress = itemView.findViewById(R.id.txtClinicAddress);
        txtPhone = itemView.findViewById(R.id.txtClinicPhone);
        img = itemView.findViewById(R.id.imgLogo);
        addTitle = itemView.findViewById(R.id.txtAddressTitle);
        phoneTitle = itemView.findViewById(R.id.txtPhoneTitle);
        videoCard = itemView.findViewById(R.id.videoCard);

        img.getLayoutParams().width   = (display.getWidth() * 2 / 7);
        img.getLayoutParams().height   = (display.getWidth() * 2 / 7);


    }
}
