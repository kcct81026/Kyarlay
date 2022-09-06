package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 9/19/17.
 */

public class TrendingItemHolder extends RecyclerView.ViewHolder {
    public CustomTextView text;
    public TrendingItemHolder(View itemView) {
        super(itemView);
        text    = (CustomTextView) itemView.findViewById(R.id.text);

    }
}
