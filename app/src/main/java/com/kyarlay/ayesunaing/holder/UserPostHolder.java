package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class UserPostHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, date, question, like_count, comment_count, babyage, txtType;
    public LinearLayout like_layout, comment_layout, share_layout, linearLikeComment;
    public ImageView like, more,save;
    public NetworkImageView imageView;
    //public CircularNetworkImageView userNetworkImage;
    public NetworkImageView userNetworkImage;

    public UserPostHolder(View itemView, Display display) {
        super(itemView);
        title           = (CustomTextView) itemView.findViewById(R.id.title);
        txtType           = (CustomTextView) itemView.findViewById(R.id.txtType);
        babyage         = (CustomTextView) itemView.findViewById(R.id.babyage);
        date            = (CustomTextView) itemView.findViewById(R.id.date);
        question        = (CustomTextView) itemView.findViewById(R.id.question);
        like_count      = (CustomTextView) itemView.findViewById(R.id.likes);
        comment_count   = (CustomTextView) itemView.findViewById(R.id.comment_count);
        like_layout     = (LinearLayout) itemView.findViewById(R.id.like_layout);
        linearLikeComment     = (LinearLayout) itemView.findViewById(R.id.linearLikeComment);
        comment_layout  = (LinearLayout) itemView.findViewById(R.id.comment_layout);
        share_layout    = (LinearLayout) itemView.findViewById(R.id.share_layout);
        like            = (ImageView) itemView.findViewById(R.id.wishlist);
        imageView       = itemView.findViewById(R.id.image);
        userNetworkImage = itemView.findViewById(R.id.userNetworkImage);

        more    = (ImageView) itemView.findViewById(R.id.more);
        save    = (ImageView) itemView.findViewById(R.id.save);
        /*title.getLayoutParams().width = (display.getWidth() * 16)/20;
        more.getLayoutParams().width        = (display.getWidth() * 3)/20;*/


    }
}
