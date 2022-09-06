package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class RecyclerOnlyHolder extends RecyclerView.ViewHolder {

    public RecyclerView recyclerView;
    public CustomTextView txt;

    public RecyclerOnlyHolder(@NonNull View itemView) {
        super(itemView);

        txt = itemView.findViewById(R.id.txt);
        recyclerView = itemView.findViewById(R.id.recycler_view);

    }
}
