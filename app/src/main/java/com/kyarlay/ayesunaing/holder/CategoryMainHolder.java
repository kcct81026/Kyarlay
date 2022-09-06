package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class CategoryMainHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, more,txtHidden;
    public LinearLayout linearMore;


    public RecyclerView recyclerView;

    public CategoryMainHolder(@NonNull View itemView) {
        super(itemView);

        linearMore = itemView.findViewById(R.id.linearMore);
        title = itemView.findViewById(R.id.title);
        txtHidden = itemView.findViewById(R.id.txtHidden);
        more = itemView.findViewById(R.id.more);
        recyclerView = itemView.findViewById(R.id.main_recycler_view);
    }
}
