package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;


public class NameListHolder extends RecyclerView.ViewHolder {

    public CustomTextView text;
    public ImageView wishlist;

    public NameListHolder(@NonNull View itemView) {
        super(itemView);

        text = itemView.findViewById(R.id.txtName);
        wishlist = itemView.findViewById(R.id.wishlist);
    }
}
