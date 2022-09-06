package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class SearchHolder extends RecyclerView.ViewHolder {

    public CustomTextView txtSearch;

    public SearchHolder(@NonNull View itemView) {
        super(itemView);

        txtSearch = itemView.findViewById(R.id.txtSearch);
    }
}
