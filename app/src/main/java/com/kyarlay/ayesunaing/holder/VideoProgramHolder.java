package com.kyarlay.ayesunaing.holder;


import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class VideoProgramHolder extends RecyclerView.ViewHolder{

    public NetworkImageView imageView, pageImage;
    public CustomTextView title, body;
    public CardView cardView;
    public FrameLayout frame;
    public LinearLayout like, comment, share, reading_detail;
    public CustomTextView likes, comment_count;
    public ImageView like_image, more;
    public LinearLayout linearBottom;

    public VideoProgramHolder(View itemView, Display display) {
        super(itemView);

        imageView       = (NetworkImageView) itemView.findViewById(R.id.image);
        title           = (CustomTextView) itemView.findViewById(R.id.title);
        body            = (CustomTextView) itemView.findViewById(R.id.body);
        cardView        = (CardView) itemView.findViewById(R.id.richtext_main_cardView);
        frame           = (FrameLayout) itemView.findViewById(R.id.frame);
        like            = (LinearLayout) itemView.findViewById(R.id.like_layout);
        comment         = (LinearLayout) itemView.findViewById(R.id.comment_layout);
        share           = (LinearLayout) itemView.findViewById(R.id.share_layout);
        likes           = (CustomTextView) itemView.findViewById(R.id.likes);
        pageImage       = (NetworkImageView) itemView.findViewById(R.id.page_image_url);
        comment_count   = (CustomTextView) itemView.findViewById(R.id.comment_count);
        like_image      = (ImageView) itemView.findViewById(R.id.like);
        more            = (ImageView) itemView.findViewById(R.id.more);
        reading_detail  = (LinearLayout) itemView.findViewById(R.id.reading_detail);
        linearBottom  = (LinearLayout) itemView.findViewById(R.id.linearBottom);





    }
}
