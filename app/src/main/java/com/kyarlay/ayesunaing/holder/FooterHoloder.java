package com.kyarlay.ayesunaing.holder;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 2/19/17.
 */

public class FooterHoloder extends RecyclerView.ViewHolder {

    public CustomTextView space;
    public CustomTextView titleBackground;
    public LinearLayout layout_first_time;
    public TextView txtFirstTime;

    public FooterHoloder(View itemView) {
        super(itemView);
        space = (CustomTextView) itemView.findViewById(R.id.cart_detail_foooter);
        titleBackground = (CustomTextView) itemView.findViewById(R.id.cart_detail_backgorund);
        txtFirstTime = itemView.findViewById(R.id.txtFirstTime);
        layout_first_time = itemView.findViewById(R.id.layout_first_time);
    }
}
