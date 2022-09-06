package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;

public class PointHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearMain, linearBackground,layoutPoint;
    public TextView txtMemberTitle, txtPoint, txtDeadline, txtAmount, txtWatchMore, txtBottom;
    public ImageView imgHiddenOne, imgHiddenTwo;
    public ProgressBar progress;

    public PointHolder(@NonNull View itemView) {
        super(itemView);

        linearMain = itemView.findViewById(R.id.linearMain);
        linearBackground = itemView.findViewById(R.id.linearBackground);
        txtMemberTitle = itemView.findViewById(R.id.txtMemberTitle);
        imgHiddenOne = itemView.findViewById(R.id.imgHiddenOne);
        layoutPoint = itemView.findViewById(R.id.layoutPoint);
        txtPoint = itemView.findViewById(R.id.txtPoint);
        txtDeadline = itemView.findViewById(R.id.txtDeadline);
        txtAmount = itemView.findViewById(R.id.txtAmount);
        progress = itemView.findViewById(R.id.progress);
        imgHiddenTwo = itemView.findViewById(R.id.imgHiddenTwo);
        txtWatchMore = itemView.findViewById(R.id.txtWatchMore);
        txtBottom = itemView.findViewById(R.id.txtBottom);
    }
}
