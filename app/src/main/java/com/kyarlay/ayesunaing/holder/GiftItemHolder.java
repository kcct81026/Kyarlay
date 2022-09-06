package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class GiftItemHolder extends RecyclerView.ViewHolder {

    public NetworkImageView imgGift;
    public CustomTextView txtGiftTitile, txtGiftPoint,txtGiftPurchase, txtGiftAcces, txtBar;
    public CardView card_point_item;
    public ProgressBar pFull, pFill;
    public LinearLayout linearThree;

    public GiftItemHolder(@NonNull View itemView) {
        super(itemView);

        card_point_item = itemView.findViewById(R.id.card_point_item);
        imgGift = itemView.findViewById(R.id.imgGift);
        txtGiftTitile = itemView.findViewById(R.id.txtGiftTitile);
        txtGiftPoint = itemView.findViewById(R.id.txtGiftPoint);
        txtGiftPurchase = itemView.findViewById(R.id.txtGiftPurchase);
        txtGiftAcces = itemView.findViewById(R.id.txtGiftAcces);
        txtBar = itemView.findViewById(R.id.barText);
        pFill = itemView.findViewById(R.id.barFill);
        pFull = itemView.findViewById(R.id.barFull);
        linearThree = itemView.findViewById(R.id.linearThree);
    }
}
