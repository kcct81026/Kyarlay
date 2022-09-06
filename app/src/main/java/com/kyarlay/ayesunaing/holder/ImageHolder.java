package com.kyarlay.ayesunaing.holder;


import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 11/22/16.
 */

public class ImageHolder extends RecyclerView.ViewHolder{

    public NetworkImageView imageView;
    public CircularNetworkImageView pageImage;
    public CustomTextView title, time, body, titleText, babyage, txtType;
    public FrameLayout frame;
    public CardView cardView;
    public LinearLayout linearTop;

    public ImageHolder(View itemView) {
        super(itemView);

        pageImage   = itemView.findViewById(R.id.pageImage);
        imageView   = (NetworkImageView) itemView.findViewById(R.id.image);
        title       = (CustomTextView) itemView.findViewById(R.id.title);
        titleText   = (CustomTextView) itemView.findViewById(R.id.titleText);
        body        = (CustomTextView) itemView.findViewById(R.id.body);
        frame       = (FrameLayout) itemView.findViewById(R.id.imageframe);
        time        = (CustomTextView) itemView.findViewById(R.id.time);
        babyage        = (CustomTextView) itemView.findViewById(R.id.babyage);
        txtType        = (CustomTextView) itemView.findViewById(R.id.txtType);
        cardView        = (CardView) itemView.findViewById(R.id.cardView);
        linearTop = itemView.findViewById(R.id.linearTop);


    }
}

