package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.SelectableRoundedImageView;

public class BannerHolder extends RecyclerView.ViewHolder{

    public SelectableRoundedImageView img;
    public CustomTextView textTitle,txtHidden,textTitleLeft;


    public BannerHolder(@NonNull View itemView) {
        super(itemView);

        img = itemView.findViewById(R.id.img);
        textTitle = itemView.findViewById(R.id.textTitle);
        txtHidden = itemView.findViewById(R.id.txtHidden);
        textTitleLeft = itemView.findViewById(R.id.textTitleLeft);

    }
}
