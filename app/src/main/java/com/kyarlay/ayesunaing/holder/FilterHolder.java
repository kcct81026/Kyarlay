package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class FilterHolder extends RecyclerView.ViewHolder {

    public LinearLayout filter_main_layout;
    public CustomTextView txtFilterTitle;
    public ImageView imgGrid, imgList;

    public FilterHolder(@NonNull View itemView) {
        super(itemView);

        filter_main_layout = itemView.findViewById(R.id.filter_main_layout);
        txtFilterTitle = itemView.findViewById(R.id.txtFilterTitle);
        imgGrid = itemView.findViewById(R.id.imgGrid);
        imgList = itemView.findViewById(R.id.imgList);
    }
}
