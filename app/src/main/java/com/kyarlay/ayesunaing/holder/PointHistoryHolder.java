package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class PointHistoryHolder extends RecyclerView.ViewHolder {

    public CustomTextView date, point, usage_type;

    public PointHistoryHolder(@NonNull View itemView, Display display) {
        super(itemView);

        date        = itemView.findViewById(R.id.date);
        point       = itemView.findViewById(R.id.point);
        usage_type  = itemView.findViewById(R.id.usage_type);

        date.getLayoutParams().width    = ( display.getWidth() * 3 ) / 9;
        point.getLayoutParams().width   = ( display.getWidth() * 3 ) / 9;
        usage_type.getLayoutParams().width  = ( display.getWidth() * 3 ) / 9 ;
    }
}
