package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class UserProfileNewHolder extends RecyclerView.ViewHolder {

    public CircularNetworkImageView imgProfile;
    public ImageView imgEdit;
    public CustomTextView txtName,txtPhone,txtMemberStatus;
    public TextView txtWishListCount,txtPoint,txtPostCount;
    public LinearLayout linearWishList,linearPoint, linearPost;

    public UserProfileNewHolder(@NonNull View itemView) {
        super(itemView);

        imgProfile = itemView.findViewById(R.id.imgProfile);
        imgEdit = itemView.findViewById(R.id.imgEdit);
        txtName = itemView.findViewById(R.id.txtName);
        txtPhone = itemView.findViewById(R.id.txtPhone);
        txtMemberStatus = itemView.findViewById(R.id.txtMemberStatus);
        txtWishListCount = itemView.findViewById(R.id.txtWishListCount);
        txtPoint = itemView.findViewById(R.id.txtPoint);
        txtPostCount = itemView.findViewById(R.id.txtPostCount);
        linearWishList = itemView.findViewById(R.id.linearWishList);
        linearPoint = itemView.findViewById(R.id.linearPoint);
        linearPost = itemView.findViewById(R.id.linearPost);
    }
}
