package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class CheckListTitleHolder extends RecyclerView.ViewHolder{

    public ImageView img;
    public LinearLayout linear;
    public CustomTextView title, textTitleLeft;

    public CheckListTitleHolder(@NonNull View itemView) {
        super(itemView);

        linear = itemView.findViewById(R.id.linear);
        img = itemView.findViewById(R.id.img);
        title = itemView.findViewById(R.id.title);
        textTitleLeft = itemView.findViewById(R.id.textTitleLeft);
    }
}
