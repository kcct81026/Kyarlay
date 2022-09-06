package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class LikeCommentHolder extends RecyclerView.ViewHolder{

    public ImageView wishlist;
    public CustomTextView like_text;
    public ImageView share;
    public LinearLayout linearComment, linearShare, linearLike;

    public LikeCommentHolder(@NonNull View itemView) {
        super(itemView);

        wishlist = itemView.findViewById(R.id.wishlist);
        like_text = itemView.findViewById(R.id.like_text);
        share = itemView.findViewById(R.id.share);
        linearComment  = itemView.findViewById(R.id.comment_layout);
        linearShare  = itemView.findViewById(R.id.share_layout);
        linearLike  = itemView.findViewById(R.id.like_layout);
    }
}
