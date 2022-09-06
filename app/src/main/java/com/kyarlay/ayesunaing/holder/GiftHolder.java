package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class GiftHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, body, time, count, price;
    public NetworkImageView image;


    public GiftHolder(@NonNull View itemView) {
        super(itemView);
        title       = (CustomTextView) itemView.findViewById(R.id.title);
        body        = (CustomTextView) itemView.findViewById(R.id.body);
        time        = (CustomTextView) itemView.findViewById(R.id.time);
        count       = (CustomTextView) itemView.findViewById(R.id.count);
        price       = (CustomTextView) itemView.findViewById(R.id.price);
        image       = (NetworkImageView) itemView.findViewById(R.id.image);
    }
}
