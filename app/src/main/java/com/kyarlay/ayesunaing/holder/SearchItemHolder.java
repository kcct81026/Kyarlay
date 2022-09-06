package com.kyarlay.ayesunaing.holder;


import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 9/19/17.
 */

public class SearchItemHolder extends RecyclerView.ViewHolder {

    public CustomTextView title;
    public NetworkImageView imageView;
    public LinearLayout layout;

    public SearchItemHolder(View itemView) {
        super(itemView);

        title = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        imageView = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        layout     = (LinearLayout) itemView.findViewById(R.id.layout);
        /*imageView.getLayoutParams().width  =  (display.getWidth() * 1) / 6;
        imageView.getLayoutParams().height =  (display.getWidth() * 1) / 6;

        layout.getLayoutParams().width  =  (display.getWidth() * 1) / 3;
        layout.getLayoutParams().height =  (display.getHeight() * 1) / 4;
        title.getLayoutParams().width    = ( display.getWidth() * 4) / 15;
        title.setPadding(0, 0, 0, 2);*/
    }
}
