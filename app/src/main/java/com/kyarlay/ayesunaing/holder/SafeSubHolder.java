package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.FadingEdgeLayout;

public class SafeSubHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, txtQuestion, txtAnswerShort, txtAnswerLong, titleAlert;
    public FadingEdgeLayout fadeEdge;
    public ImageView imgSafe;

    public SafeSubHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        txtQuestion = itemView.findViewById(R.id.txtQuestion);
        txtAnswerShort = itemView.findViewById(R.id.txtAnswerShort);
        txtAnswerLong = itemView.findViewById(R.id.txtAnswerLong);
        fadeEdge = itemView.findViewById(R.id.fadeEdge);
        titleAlert = itemView.findViewById(R.id.titleAlert);
        imgSafe = itemView.findViewById(R.id.imgSafe);
    }
}
