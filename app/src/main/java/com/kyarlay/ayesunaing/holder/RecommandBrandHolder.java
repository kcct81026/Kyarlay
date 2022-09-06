package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 8/10/17.
 */

public class RecommandBrandHolder extends RecyclerView.ViewHolder {

    public CustomTextView textView, to_view;
    public NetworkImageView image;
    public LinearLayout all, to_view_frame;
    public RecommandBrandHolder(View itemView, Display display) {
        super(itemView);
        textView    = (CustomTextView) itemView.findViewById(R.id.onclick);
        to_view     = (CustomTextView) itemView.findViewById(R.id.to_view);
        image       = (NetworkImageView) itemView.findViewById(R.id.image);
        all         = (LinearLayout) itemView.findViewById(R.id.all);
        to_view_frame= (LinearLayout) itemView.findViewById(R.id.to_view_frame);
        image.getLayoutParams().height          = (display.getWidth() * 3 ) / 20;
        image.getLayoutParams().width           = (display.getWidth() * 3 ) / 20;
        to_view_frame.getLayoutParams().width   = (display.getWidth() * 4 ) / 20;
        textView.getLayoutParams().width        = (display.getWidth() * 12 ) / 20;
        all.getLayoutParams().height            = (display.getWidth() * 3 ) / 20;
    }
}
