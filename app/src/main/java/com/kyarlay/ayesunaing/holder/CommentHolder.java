package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 1/29/18.
 */

public class CommentHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, time, body, babyage, txtSubtitle, txtWatch, txtType;
    public LinearLayout cart, linearUser, linearComments, linearLayoutPhoto;
    public NetworkImageView userNetworkImage, imgComment, imgPreview, imgCommentLand;
    public ImageView imgMore;
    public CommentHolder(View itemView) {
        super(itemView);

        linearUser        = (LinearLayout) itemView.findViewById(R.id.linearUser);
        linearComments        = (LinearLayout) itemView.findViewById(R.id.linearComments);
        linearLayoutPhoto        = (LinearLayout) itemView.findViewById(R.id.linearLayoutPhoto);
        cart        = (LinearLayout) itemView.findViewById(R.id.cart);
        title       = (CustomTextView) itemView.findViewById(R.id.title);
        txtType       = (CustomTextView) itemView.findViewById(R.id.txtType);
        txtWatch       = (CustomTextView) itemView.findViewById(R.id.txtWatch);
        txtSubtitle       = (CustomTextView) itemView.findViewById(R.id.txtSubtitle);
        time        = (CustomTextView) itemView.findViewById(R.id.time);
        body        = (CustomTextView) itemView.findViewById(R.id.body);
        babyage        = (CustomTextView) itemView.findViewById(R.id.babyage);
        userNetworkImage = itemView.findViewById(R.id.userNetworkImage);
        imgComment = itemView.findViewById(R.id.imgComment);
        imgCommentLand = itemView.findViewById(R.id.imgCommentLand);
        imgPreview = itemView.findViewById(R.id.imgPreview);
        imgMore = itemView.findViewById(R.id.imgMore);
    }
}
