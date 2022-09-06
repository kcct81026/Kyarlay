package com.kyarlay.ayesunaing.holder;


import android.view.Display;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

/**
 * Created by ayesu on 1/16/18.
 */

public class BannerPhotoHolder extends RecyclerView.ViewHolder {
    public NetworkImageView imageView;
    public BannerPhotoHolder(View itemView , Display display) {
        super(itemView);
        imageView = (NetworkImageView) itemView.findViewById(R.id.imageview);

    }
}
