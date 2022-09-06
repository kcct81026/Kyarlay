package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class PointTitleHolder extends RecyclerView.ViewHolder {
    public CustomTextView title;

    public PointTitleHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
    }
}
