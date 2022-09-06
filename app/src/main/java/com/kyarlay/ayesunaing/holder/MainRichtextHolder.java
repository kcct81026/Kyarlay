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

/**
 * Created by ayesunaing on 11/18/16.
 */

public class MainRichtextHolder extends RecyclerView.ViewHolder{

    public NetworkImageView imageView, pageImage;
    public CustomTextView title, pageName, body;
    public CardView cardView;
    public FrameLayout frame;
    public LinearLayout like, comment, share, page_layout, reading_detail, linearTop, layoutComment,share_layoutMomo;
    public CustomTextView likes, comment_count, page_time;
    public ImageView like_image, more;

    public MainRichtextHolder(View itemView, Display display) {
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
        pageName        = (CustomTextView) itemView.findViewById(R.id.page_name);
        comment_count   = (CustomTextView) itemView.findViewById(R.id.comment_count);
        like_image      = (ImageView) itemView.findViewById(R.id.like);
        page_layout     = (LinearLayout) itemView.findViewById(R.id.page_layout);
        more            = (ImageView) itemView.findViewById(R.id.more);
        page_time       = (CustomTextView) itemView.findViewById(R.id.page_time);
        reading_detail  = (LinearLayout) itemView.findViewById(R.id.reading_detail);
        linearTop  = (LinearLayout) itemView.findViewById(R.id.linearTop);
        layoutComment  = (LinearLayout) itemView.findViewById(R.id.layoutComment);
        share_layoutMomo  = (LinearLayout) itemView.findViewById(R.id.share_layoutMomo);

        page_layout.getLayoutParams().width = (display.getWidth() * 16)/20;
        more.getLayoutParams().width        = (display.getWidth() * 3)/20;




    }
}
