package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class VideoLatestHolder extends RecyclerView.ViewHolder {

    public YouTubeThumbnailView thumbnailVideo;
    public NetworkImageView img;
    public CustomTextView title;
    public boolean readyFroLoadingYoutubeThumbnail = true;
    public CardView videoCard;

    public VideoLatestHolder(@NonNull View itemView, Display display) {
        super(itemView);

        thumbnailVideo = itemView.findViewById(R.id.thumbnailVideo);
        img = itemView.findViewById(R.id.thumbnailImg);
        title = itemView.findViewById(R.id.title);
        videoCard = itemView.findViewById(R.id.videoCard);

        thumbnailVideo.getLayoutParams().width   = (display.getWidth() * 3 / 7);
        thumbnailVideo.getLayoutParams().height  = (display.getWidth() * 2/7);

        img.getLayoutParams().width   = (display.getWidth() * 3 / 7);
        img.getLayoutParams().height  = (display.getWidth() * 2/7);
    }
}
