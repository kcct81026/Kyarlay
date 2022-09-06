package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 2/27/17.
 */

public class BrandHolder extends RecyclerView.ViewHolder {

    public NetworkImageView imageView;
    public CustomTextView title, description;
    public LinearLayout detailLayout,all;

    public BrandHolder(View itemView, Display display) {
        super(itemView);
        imageView   = (NetworkImageView) itemView.findViewById(R.id.image);
        title       = (CustomTextView) itemView.findViewById(R.id.title);
        description = (CustomTextView) itemView.findViewById(R.id.description);
        detailLayout= (LinearLayout) itemView.findViewById(R.id.detailLayout);
        all= (LinearLayout) itemView.findViewById(R.id.all);


        imageView.getLayoutParams().width  = (display.getWidth() * 3 ) / 13;
        imageView.getLayoutParams().height = (display.getWidth() * 3 ) / 13;
       // detailLayout.getLayoutParams().width= (display.getWidth() * 10 ) / 14;

    }
}
