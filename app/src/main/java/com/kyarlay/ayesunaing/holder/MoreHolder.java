package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;

public class MoreHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearMore;

    public MoreHolder(@NonNull View itemView) {
        super(itemView);

        linearMore = itemView.findViewById(R.id.linearMore);
    }
}
