package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 4/5/18.
 */

public class NoItemHolder extends RecyclerView.ViewHolder {


    public LinearLayout layout;
    public CustomTextView space, space2;
    public NoItemHolder(View itemView) {
        super(itemView);
        space = (CustomTextView) itemView.findViewById(R.id.cart_detail_foooter);
        space2 = (CustomTextView) itemView.findViewById(R.id.cart_detail_backgorund);
        layout = itemView.findViewById(R.id.layout);
    }
}
