package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircleBorderImageView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class PaymentItemHolder extends RecyclerView.ViewHolder {

    public CircleBorderImageView img;
    public ImageView imgCheck;
    public CustomTextView txt, txtPercent;
    public LinearLayout linearMain;


    public PaymentItemHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.img);
        imgCheck = itemView.findViewById(R.id.imgCheck);
        txt = itemView.findViewById(R.id.txt);
        txtPercent = itemView.findViewById(R.id.txtPercent);
        linearMain = itemView.findViewById(R.id.linearMain);
    }
}
