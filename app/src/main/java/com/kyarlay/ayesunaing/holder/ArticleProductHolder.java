package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class ArticleProductHolder extends RecyclerView.ViewHolder {

    public RelativeLayout relative;
    public NetworkImageView imgProduct;
    public CustomTextView title, txtWatch, txtBody;
    public CardView cardView;

    public ArticleProductHolder(@NonNull View itemView, Display display) {
        super(itemView);

        relative = itemView.findViewById(R.id.relative);
        imgProduct = itemView.findViewById(R.id.imgProduct);
        title = itemView.findViewById(R.id.title);
        txtWatch = itemView.findViewById(R.id.txtWatch);
        txtBody = itemView.findViewById(R.id.txtBody);
        cardView = itemView.findViewById(R.id.gridItemImageView_Cart);

        relative.getLayoutParams().height = ( display.getWidth() *  2 )/ 5;
        relative.getLayoutParams().width  = ( display.getWidth() * 2 ) / 5;
    }
}
