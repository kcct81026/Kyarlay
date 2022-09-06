package com.kyarlay.ayesunaing.holder;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class PostLikedHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, time;
    public PostLikedHolder(View itemView) {
        super(itemView);
        title   = (CustomTextView) itemView.findViewById(R.id.title);
        time    = (CustomTextView) itemView.findViewById(R.id.time);
    }
}
