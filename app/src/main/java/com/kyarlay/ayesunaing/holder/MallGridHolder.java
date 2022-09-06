package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

public class MallGridHolder extends RecyclerView.ViewHolder {

    public NetworkImageView imgOne, imgTwo, imgThree, imgFour, imgFive;
    public LinearLayout layoutForTwo,layoutForFour;

    public MallGridHolder(@NonNull View itemView) {
        super(itemView);

        imgOne = itemView.findViewById(R.id.imgOne);
        imgTwo = itemView.findViewById(R.id.imgTwo);
        imgThree = itemView.findViewById(R.id.imgThree);
        imgFour = itemView.findViewById(R.id.imgFour);
        imgFive = itemView.findViewById(R.id.imgFive);
        layoutForTwo = itemView.findViewById(R.id.layoutForTwo);
        layoutForFour = itemView.findViewById(R.id.layoutForFour);
    }
}
