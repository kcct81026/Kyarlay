package com.kyarlay.ayesunaing.holder;


import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

/**
 * Created by ayesu on 2/1/18.
 */

public class BrandBannerHolder extends RecyclerView.ViewHolder {
    public NetworkImageView image,imageCardView, imagePatner;
    public CardView cardViewBrand;
    public BrandBannerHolder(View itemView) {
        super(itemView);
        image   = (NetworkImageView) itemView.findViewById(R.id.image);
        imageCardView   = (NetworkImageView) itemView.findViewById(R.id.imageCardView);
        cardViewBrand = itemView.findViewById(R.id.cardViewBrand);
        imagePatner = itemView.findViewById(R.id.imagePatner);
    }
}
