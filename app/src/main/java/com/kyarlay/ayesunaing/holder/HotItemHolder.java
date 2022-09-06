package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class HotItemHolder extends RecyclerView.ViewHolder {

    public LinearLayout linear;
    public NetworkImageView img;
    public CustomTextView title;

    public HotItemHolder(@NonNull View itemView) {
        super(itemView);

        linear = itemView.findViewById(R.id.linear);
        img = itemView.findViewById(R.id.img);
        title = itemView.findViewById(R.id.title);
    }
}
