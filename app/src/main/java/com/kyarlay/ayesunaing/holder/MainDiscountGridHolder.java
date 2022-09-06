package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;

public class MainDiscountGridHolder extends RecyclerView.ViewHolder implements ConstantVariable {
    public RecyclerView recyclerView;
    public CustomTextView title, more;

    public MainDiscountGridHolder(View itemView) {
        super(itemView);
        title           = (CustomTextView) itemView.findViewById(R.id.title);
        recyclerView    = (RecyclerView) itemView.findViewById(R.id.main_recycler_view);
        more            = (CustomTextView) itemView.findViewById(R.id.more);




    }

}

