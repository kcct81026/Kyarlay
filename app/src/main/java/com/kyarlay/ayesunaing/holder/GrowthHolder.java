package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class GrowthHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearWeight, linearMain;
    public ImageView imgLength, imgWeight;
    public CustomTextView txtLength, txtWeight, txtQuestion, txtAnswer, more;

    public GrowthHolder(@NonNull View itemView) {
        super(itemView);

        linearWeight = itemView.findViewById(R.id.linearWeight);
        imgLength = itemView.findViewById(R.id.imgLength);
        imgWeight = itemView.findViewById(R.id.imgWeight);
        txtLength = itemView.findViewById(R.id.txtLength);
        txtWeight = itemView.findViewById(R.id.txtWeight);
        txtQuestion = itemView.findViewById(R.id.txtQuestion);
        txtAnswer = itemView.findViewById(R.id.txtAnswer);
        more = itemView.findViewById(R.id.more);
        linearMain = itemView.findViewById(R.id.linearMain);
    }
}
