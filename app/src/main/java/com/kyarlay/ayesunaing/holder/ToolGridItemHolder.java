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
public class ToolGridItemHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, titleSubs, txtWatch;
    public NetworkImageView imageView;
    public LinearLayout layout;

    public ToolGridItemHolder(View itemView, Display display) {
        super(itemView);
        title = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        titleSubs = (CustomTextView) itemView.findViewById(R.id.gridItemSubs);
        txtWatch = (CustomTextView) itemView.findViewById(R.id.gridItemWatch);
        imageView = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        layout     = (LinearLayout) itemView.findViewById(R.id.layout);



        imageView.getLayoutParams().width  =  (display.getWidth() * 4) / 26;
        imageView.getLayoutParams().height =  (display.getWidth() * 4) / 26;

        layout.getLayoutParams().width  =  (display.getWidth() * 7) / 20;
      //  layout.getLayoutParams().height =  (display.getWidth() *8) / 26;

        //title.getLayoutParams().width  =  (display.getWidth() * 5) / 10;
        //titleSubs.getLayoutParams().width  =  (display.getWidth() * 5) / 10;
        title.setPadding(2, 0, 2, 10);
        titleSubs.setPadding(2, 0, 2, 0);
        txtWatch.setPadding(2, 0, 2, 10);
        /*
         imageView.getLayoutParams().width  =  (display.getWidth() * 5) / 26;
        imageView.getLayoutParams().height =  (display.getWidth() * 5) / 26;

        layout.getLayoutParams().width  =  (display.getWidth() * 7) / 20;
       // layout.getLayoutParams().height =  (display.getWidth() * 3) / 9;

        title.getLayoutParams().width  =  (display.getWidth() * 5) / 26;
        Log.e("width " , " " +  ( display.getWidth() * 2) / 11);
        title.setPadding(2, 5, 2, 30);
         */
        //title.getLayoutParams().width  =  (display.getWidth() * 5) / 26;
       // title.setPadding(2, 5, 2, 2);
    }


}
