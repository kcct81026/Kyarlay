package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

public class SubExpandableHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageView img;
    public NetworkImageView icon;
    public LinearLayout layout;

    public SubExpandableHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        img = itemView.findViewById(R.id.img);
        icon = itemView.findViewById(R.id.icon);
        layout = itemView.findViewById(R.id.layout);
    }
}
