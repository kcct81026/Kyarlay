package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class CustomMainHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearMain, linearBackground;
    public CustomTextView title;
    public NetworkImageView  imageView;

    public CustomMainHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        title = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        linearMain     = (LinearLayout) itemView.findViewById(R.id.linearMain);
        linearBackground     = (LinearLayout) itemView.findViewById(R.id.linearBackground);
    }
}
