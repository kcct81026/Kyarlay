package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class UserProfileHolder extends RecyclerView.ViewHolder{

    public CardView card_user_layout;
    public CircularNetworkImageView profile_image;
    public CustomTextView name_text, phone_text, address, member_text, txtEditProfile,txtPoints, txtRate, txtShare, txtQA;
    public LinearLayout linearEdit, linearPoint, linearRate, linearShare, linearQA;


    public UserProfileHolder(@NonNull View itemView, Display display) {
        super(itemView);

        card_user_layout = itemView.findViewById(R.id.card_user_layout);
        profile_image = itemView.findViewById(R.id.profile_image);
        name_text = itemView.findViewById(R.id.name_text);
        phone_text = itemView.findViewById(R.id.phone_text);
        address = itemView.findViewById(R.id.address);
        member_text = itemView.findViewById(R.id.member_text);
        txtEditProfile = itemView.findViewById(R.id.txtEditProfile);
        txtPoints = itemView.findViewById(R.id.txtPoint);
        txtShare = itemView.findViewById(R.id.txtEditShare);
        txtRate = itemView.findViewById(R.id.txtRate);
        txtQA = itemView.findViewById(R.id.txtQA);

        linearEdit = itemView.findViewById(R.id.linearEdit);
        linearPoint = itemView.findViewById(R.id.linearPoint);
        linearShare = itemView.findViewById(R.id.linearShare);
        linearRate = itemView.findViewById(R.id.linearRate);
        linearQA = itemView.findViewById(R.id.linearQA);

        linearRate.getLayoutParams().width    = ( display.getWidth() * 5 ) / 10;
        linearEdit.getLayoutParams().width    = ( display.getWidth() * 5 ) / 10;
        linearShare.getLayoutParams().width    = ( display.getWidth() * 5 ) / 10;
        linearPoint.getLayoutParams().width    = ( display.getWidth() * 5 ) / 10;

    }
}
