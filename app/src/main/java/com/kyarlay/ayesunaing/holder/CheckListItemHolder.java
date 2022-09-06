package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class CheckListItemHolder extends RecyclerView.ViewHolder {

    public CheckBox chk;
    public CustomTextView title, txtCount,txtShopTitle;
    public ImageView imgInfoSelf, imgInfoKyarlay,imgDelete;
    public LinearLayout linearShopping;

    public CheckListItemHolder(@NonNull View itemView) {
        super(itemView);

        chk = itemView.findViewById(R.id.chk);
        title = itemView.findViewById(R.id.title);
        txtCount = itemView.findViewById(R.id.txtCount);
        imgInfoSelf = itemView.findViewById(R.id.imgInfoSelf);
        imgInfoKyarlay = itemView.findViewById(R.id.imgInfoKyarlay);
        imgDelete = itemView.findViewById(R.id.imgDelete);
        linearShopping = itemView.findViewById(R.id.linearShopping);
        txtShopTitle = itemView.findViewById(R.id.txtShopTitle);
    }
}
