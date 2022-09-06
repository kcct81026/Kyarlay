package com.kyarlay.ayesunaing.holder;


import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 3/31/17.
 */

public class SettingsHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public CustomTextView text;
    public SettingsHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
        text  = (CustomTextView) itemView.findViewById(R.id.text);
    }
}
