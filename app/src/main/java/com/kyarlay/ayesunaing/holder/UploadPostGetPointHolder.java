package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

public class UploadPostGetPointHolder extends RecyclerView.ViewHolder {

    public NetworkImageView image;
    public LinearLayout layout;

    public UploadPostGetPointHolder(@NonNull View itemView) {
        super(itemView);
        image   = itemView.findViewById(R.id.image);
        layout  = itemView.findViewById(R.id.layout);
    }
}
