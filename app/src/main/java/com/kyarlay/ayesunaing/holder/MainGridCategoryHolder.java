package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;

public class MainGridCategoryHolder extends RecyclerView.ViewHolder {

    public TextView txtBackground;
    public RecyclerView recyclerView;

    public MainGridCategoryHolder(@NonNull View itemView) {
        super(itemView);

        txtBackground  = itemView.findViewById(R.id.txtBackground);
        recyclerView  = itemView.findViewById(R.id.main_recycler_view);
    }
}
