package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class ReadingToolHolder extends RecyclerView.ViewHolder {

    public NetworkImageView img;
    public CustomTextView txt, txtTitle;
    public LinearLayout linearLayout;

    public ReadingToolHolder(@NonNull View itemView) {
        super(itemView);

        txt = itemView.findViewById(R.id.txt);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        img = itemView.findViewById(R.id.img);
        linearLayout = itemView.findViewById(R.id.linearLayout);
    }
}
