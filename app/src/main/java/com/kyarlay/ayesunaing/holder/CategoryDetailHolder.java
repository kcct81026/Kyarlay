package com.kyarlay.ayesunaing.holder;

import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 9/8/16.
 */
public class CategoryDetailHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, price, priceStrike;
    public com.kyarlay.ayesunaing.custom_widget.CircularTextView discount;
    public NetworkImageView imageView;
    public LinearLayout  linearMain;


    public CategoryDetailHolder(View itemView) {
        super(itemView);
        title           = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        price           = (CustomTextView) itemView.findViewById(R.id.gridItemPriceView);

        imageView       = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        priceStrike     = (CustomTextView) itemView.findViewById(R.id.gridItemPriceStrikeView);

        linearMain      = (LinearLayout) itemView.findViewById(R.id.linearMain);
        priceStrike.setPaintFlags(priceStrike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }


}
