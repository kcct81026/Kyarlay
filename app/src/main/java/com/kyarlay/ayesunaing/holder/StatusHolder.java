package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class StatusHolder extends RecyclerView.ViewHolder {

    public CustomTextView txtStatus, txtEdit, txtOval;
    public ImageView imgWishList, imgNoti;


    public StatusHolder(@NonNull View itemView) {
        super(itemView);


        txtStatus = itemView.findViewById(R.id.txtStatus);
        txtEdit = itemView.findViewById(R.id.txtEdit);
        txtOval = itemView.findViewById(R.id.txtOval);

        imgWishList = itemView.findViewById(R.id.imgWishList);
        imgNoti = itemView.findViewById(R.id.imgNoti);
    }
}
