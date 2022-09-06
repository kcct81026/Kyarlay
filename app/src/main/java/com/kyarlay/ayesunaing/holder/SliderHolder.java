package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.SelectableRoundedImageView;

public class SliderHolder extends RecyclerView.ViewHolder {

    public LinearLayout linear;
    public SelectableRoundedImageView img;

    public SliderHolder(@NonNull View itemView) {
        super(itemView);

        linear = itemView.findViewById(R.id.linear);
        img = itemView.findViewById(R.id.img);
    }
}
