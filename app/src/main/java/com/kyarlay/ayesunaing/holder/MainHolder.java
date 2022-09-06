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
/**
 * Created by ayesunaing on 8/23/16.
 */
public class MainHolder extends RecyclerView.ViewHolder {

    public CustomTextView title;
    public NetworkImageView imageView;
    public LinearLayout layout;

    public MainHolder(View itemView, Display display) {
        super(itemView);
        title = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        imageView = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        layout     = (LinearLayout) itemView.findViewById(R.id.layout);


        imageView.getLayoutParams().width  =  display.getWidth()   / 2 ;
        imageView.getLayoutParams().height =  display.getWidth() / 2 ;

        imageView.setPadding(0,0,0,0);

        layout.getLayoutParams().width  =  display.getWidth() / 2;
        title.getLayoutParams().width  = display.getWidth() / 2;
        layout.setPadding(20, 0, 20, 30);
    }




}
