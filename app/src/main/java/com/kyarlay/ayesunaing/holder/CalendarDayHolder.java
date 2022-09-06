package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class CalendarDayHolder extends RecyclerView.ViewHolder {

    public CustomTextView day, date,desc, birth_group, work_group;
    public RatingBar ratingBar;
    public LinearLayout dateLayout, descLayout;

    public CalendarDayHolder(@NonNull View itemView, Display display) {
        super(itemView);
        day         = (CustomTextView) itemView.findViewById(R.id.days);
        date        = (CustomTextView) itemView.findViewById(R.id.date);
        desc        = (CustomTextView) itemView.findViewById(R.id.desc);
        birth_group = (CustomTextView) itemView.findViewById(R.id.birth_group);
        work_group  = (CustomTextView) itemView.findViewById(R.id.work_group);

        ratingBar   = (RatingBar) itemView.findViewById(R.id.rating);
        dateLayout  = (LinearLayout) itemView.findViewById(R.id.dateLayout);
        descLayout  = (LinearLayout) itemView.findViewById(R.id.descLayout);

        dateLayout.getLayoutParams().width          = (display.getWidth() * 2 ) / 10;
        descLayout.getLayoutParams().width         = (display.getWidth() * 8 ) / 10;
    }
}
