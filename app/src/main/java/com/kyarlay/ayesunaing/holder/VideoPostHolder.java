package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class VideoPostHolder extends RecyclerView.ViewHolder {

    public NetworkImageView imageView;
    public CustomTextView title, playing;
    public boolean readyFroLoadingYoutubeThumbnail = true;

    public VideoPostHolder(View itemView, Display display) {
        super(itemView);

        imageView   = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
        title       = (CustomTextView) itemView.findViewById(R.id.title);
        playing     = (CustomTextView) itemView.findViewById(R.id.playing);


/*
        imageView.getLayoutParams().width   = (display.getWidth() * 3 / 7);
        imageView.getLayoutParams().height  = (display.getWidth() * 2/7);*/


    }
}
