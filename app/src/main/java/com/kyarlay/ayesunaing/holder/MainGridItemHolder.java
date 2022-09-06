package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class MainGridItemHolder extends RecyclerView.ViewHolder {

    public CustomTextView title;
    public NetworkImageView imageView;
    public LinearLayout layout;

    public MainGridItemHolder(View itemView, Display display) {
        super(itemView);
        title = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        imageView = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        layout     = (LinearLayout) itemView.findViewById(R.id.layout);


        imageView.getLayoutParams().width  =  (display.getWidth() * 5) / 26;
        imageView.getLayoutParams().height =  (display.getWidth() * 5) / 26;

        layout.getLayoutParams().width  =  (display.getWidth() * 7) / 20;
        title.getLayoutParams().width  =  (display.getWidth() * 5) / 26;
        title.setPadding(2, 5, 2, 30);

    }


}
