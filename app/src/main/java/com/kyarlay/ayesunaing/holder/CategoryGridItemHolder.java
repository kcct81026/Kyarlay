package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class CategoryGridItemHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, txtHidden;
    public NetworkImageView imageView;
    public LinearLayout layout, linearMain;
    public TextView txtSelect;

    public CategoryGridItemHolder(View itemView, Display display) {
        super(itemView);
        title = (CustomTextView) itemView.findViewById(R.id.gridItemTitleView);
        txtHidden = (CustomTextView) itemView.findViewById(R.id.txtHidden);
        imageView = (NetworkImageView) itemView.findViewById(R.id.gridItemImageView);
        layout     = (LinearLayout) itemView.findViewById(R.id.layout);
        linearMain     = (LinearLayout) itemView.findViewById(R.id.linearMain);
        txtSelect     = itemView.findViewById(R.id.txtSelect);

        imageView.getLayoutParams().width  =  (display.getWidth() * 5) / 26;
        imageView.getLayoutParams().height =  (display.getWidth() * 5) / 26;

        layout.getLayoutParams().width  =  (display.getWidth() * 7) / 20;
        layout.getLayoutParams().height =  (display.getWidth() * 10) / 26;

        title.getLayoutParams().width  =  (display.getWidth() * 5) / 26;

    }


}
