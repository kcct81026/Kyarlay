package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class PointItemHolder extends RecyclerView.ViewHolder {

    public NetworkImageView imgIcon, imgCover;
    public CustomTextView txtPointTitle, txtDone;
    public ImageView imgGet, imgDone,imgMember;
    public LinearLayout linearGetDone;
    public CardView card_point_item;



    public PointItemHolder(@NonNull View itemView) {
        super(itemView);

        card_point_item = itemView.findViewById(R.id.card_point_item);
        imgIcon = itemView.findViewById(R.id.imgIcon);
        imgCover = itemView.findViewById(R.id.imgCover);
        txtPointTitle = itemView.findViewById(R.id.txtPointTitle);
        txtDone = itemView.findViewById(R.id.txtDone);
        imgGet = itemView.findViewById(R.id.imgGet);
        imgDone = itemView.findViewById(R.id.imgDone);
        imgMember = itemView.findViewById(R.id.imgMember);
        linearGetDone = itemView.findViewById(R.id.linearGetDone);

    }
}
