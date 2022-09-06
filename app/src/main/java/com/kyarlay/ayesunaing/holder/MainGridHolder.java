package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class MainGridHolder extends RecyclerView.ViewHolder implements ConstantVariable {
    public RecyclerView recyclerView;
    public CustomTextView title;
    public CardView cardView;

    public MainGridHolder(View itemView) {
        super(itemView);
        title           = (CustomTextView) itemView.findViewById(R.id.title);
        recyclerView    = (RecyclerView) itemView.findViewById(R.id.main_recycler_view);
        cardView    = itemView.findViewById(R.id.fragment_category);


    }

}
