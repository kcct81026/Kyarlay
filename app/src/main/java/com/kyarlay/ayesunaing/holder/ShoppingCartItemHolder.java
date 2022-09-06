package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class ShoppingCartItemHolder extends RecyclerView.ViewHolder {

    public RelativeLayout relativeOne, relativeTwo,relativeThree;
    public NetworkImageView img;
    public CustomTextView txtAllMark,title,price,price_strike;
    public ImageView imgDelete,imgMinus,imgPlus;
    public TextView quantity;
    public CustomTextView product_warning;
    public ImageView imgCar;

    public ShoppingCartItemHolder(@NonNull View itemView) {
        super(itemView);

        relativeOne = itemView.findViewById(R.id.relativeOne);
        img = itemView.findViewById(R.id.img);
        txtAllMark = itemView.findViewById(R.id.txtAllMark);
        relativeTwo = itemView.findViewById(R.id.relativeTwo);
        title = itemView.findViewById(R.id.title);
        price = itemView.findViewById(R.id.price);
        price_strike = itemView.findViewById(R.id.price_strike);
        relativeThree = itemView.findViewById(R.id.relativeThree);
        imgDelete = itemView.findViewById(R.id.imgDelete);
        imgMinus = itemView.findViewById(R.id.imgMinus);
        quantity = itemView.findViewById(R.id.quantity);
        imgPlus = itemView.findViewById(R.id.imgPlus);
        product_warning = itemView.findViewById(R.id.product_warning);
        imgCar = itemView.findViewById(R.id.imgCar);
    }
}
