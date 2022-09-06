package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class ToolSubCatHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, titleSubs, txtWatch;
    public NetworkImageView imageView;
    public LinearLayout layout;

    public ToolSubCatHolder(View itemView, Display display) {
        super(itemView);
        title = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        titleSubs = (CustomTextView) itemView.findViewById(R.id.gridItemSubs);
        txtWatch = (CustomTextView) itemView.findViewById(R.id.gridItemWatch);
        imageView = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        layout     = (LinearLayout) itemView.findViewById(R.id.layout);



        imageView.getLayoutParams().width  =  (display.getWidth() * 5) / 10;
        imageView.getLayoutParams().height =  (display.getWidth() * 5) / 26;

        layout.getLayoutParams().width  =  (display.getWidth() * 5) / 10;
        layout.getLayoutParams().height =  (display.getWidth() * 4) / 10;

        //title.getLayoutParams().width  =  (display.getWidth() * 5) / 10;
        //titleSubs.getLayoutParams().width  =  (display.getWidth() * 5) / 10;
        title.setPadding(2, 0, 2, 10);
        titleSubs.setPadding(2, 0, 2, 0);
        txtWatch.setPadding(2, 0, 2, 10);
        //title.getLayoutParams().width  =  (display.getWidth() * 5) / 26;
        // title.setPadding(2, 5, 2, 2);
    }
}
