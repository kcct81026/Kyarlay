package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class PointInfoHolder extends RecyclerView.ViewHolder{

    public CustomTextView total_point,  point_text, point_desc;


    public PointInfoHolder(@NonNull View itemView) {
        super(itemView);

        total_point = itemView.findViewById(R.id.total_point);
        point_text = itemView.findViewById(R.id.point_text);
        point_desc = itemView.findViewById(R.id.point_desc);
    }
}
