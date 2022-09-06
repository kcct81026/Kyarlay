package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 7/24/17.
 */

public class CampianHolder extends RecyclerView.ViewHolder {
    public CustomTextView title , time;
    public NetworkImageView image;
    public LinearLayout layout;
    public CampianHolder(View itemView) {
        super(itemView);
        title       = (CustomTextView) itemView.findViewById(R.id.title);
        time        = (CustomTextView) itemView.findViewById(R.id.time);
        image       = (NetworkImageView) itemView.findViewById(R.id.image);
        layout      = (LinearLayout) itemView.findViewById(R.id.layout);
    }
}
