package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class NewArrivalHolder extends RecyclerView.ViewHolder {

    public LinearLayout linear;
    public RelativeLayout relative;
    public CustomTextView txtInfo, title,price,price_strike;
    public NetworkImageView img;

    public NewArrivalHolder(@NonNull View itemView) {
        super(itemView);

        linear = itemView.findViewById(R.id.linear);
        relative = itemView.findViewById(R.id.relative);
        txtInfo = itemView.findViewById(R.id.txtInfo);
        img = itemView.findViewById(R.id.img);
        title = itemView.findViewById(R.id.title);
        price = itemView.findViewById(R.id.price);
        price_strike = itemView.findViewById(R.id.price_strike);
    }
}
