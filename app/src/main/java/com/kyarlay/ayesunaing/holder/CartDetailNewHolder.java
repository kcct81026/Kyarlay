package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 2/10/17.
 */

public class CartDetailNewHolder extends RecyclerView.ViewHolder {

   // public Spinner count;
    public NetworkImageView image;
    public CustomTextView title, total_price, count, option;
    public ImageView remove;
    public LinearLayout  middle, decrease, increase;
    public FrameLayout frame;

    public CartDetailNewHolder(View itemView, Display display) {
        super(itemView);

        image               = (NetworkImageView) itemView.findViewById(R.id.image);
        title               = (CustomTextView) itemView.findViewById(R.id.title);
        total_price         = (CustomTextView) itemView.findViewById(R.id.total_price);
        count               = (CustomTextView) itemView.findViewById(R.id.product_count);

        remove              = (ImageView) itemView.findViewById(R.id.remove);
        decrease            = (LinearLayout) itemView.findViewById(R.id.decrease);
        increase            = (LinearLayout) itemView.findViewById(R.id.increase);
        middle              = (LinearLayout) itemView.findViewById(R.id.middle);
        frame               = (FrameLayout) itemView.findViewById(R.id.frame);
        option              = itemView.findViewById(R.id.option);

        frame.getLayoutParams().width       = (display.getWidth() * 5 ) / 20;
        frame.getLayoutParams().height      = (display.getWidth() * 5 ) / 20;
        middle.getLayoutParams().width      = (display.getWidth() * 15 ) / 20;

        decrease.getLayoutParams().width        = (display.getWidth() * 2) /20;
        increase.getLayoutParams().width        = (display.getWidth() * 3) /20;
        count.getLayoutParams().width           = (display.getWidth() * 2 ) /20;
        total_price.getLayoutParams().width     = (display.getWidth() * 11) / 20;

        remove.getLayoutParams().width          = (display.getWidth() * 1 ) / 20;
        remove.getLayoutParams().height         = (display.getWidth() * 1 ) / 20;


    }
}
