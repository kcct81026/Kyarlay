package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;

public class PointBenefitHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public LinearLayout layoutParent;

    public PointBenefitHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        layoutParent = itemView.findViewById(R.id.layoutParent);
    }
}
