package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class NotificationHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, time, read;
    public LinearLayout layout, text_layout, image_layout;
    public CircularTextView showNoti;
    public NotificationHolder(View itemView ,Display display) {
        super(itemView);
        title   = (CustomTextView) itemView.findViewById(R.id.title);
        time    = (CustomTextView) itemView.findViewById(R.id.time);
        layout  = (LinearLayout) itemView.findViewById(R.id.noti_layout);
        text_layout = (LinearLayout) itemView.findViewById(R.id.noti_text_layout);
        image_layout    = (LinearLayout) itemView.findViewById(R.id.image_layout);
        showNoti        = (CircularTextView) itemView.findViewById(R.id.noti_show);
        read            = (CustomTextView) itemView.findViewById(R.id.read);

        text_layout.getLayoutParams().width = ( display.getWidth() * 17 ) /20;
        image_layout.getLayoutParams().width = ( display.getWidth() * 2 ) /20;


    }
}
