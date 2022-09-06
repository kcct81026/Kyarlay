package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 2/22/18.
 */

public class MainBrandBannerHolder extends RecyclerView.ViewHolder {

    public RecyclerView recyclerView;
    public CustomTextView title, more;

    public MainBrandBannerHolder(View itemView) {
        super(itemView);
        title           = (CustomTextView) itemView.findViewById(R.id.title);
        more            = (CustomTextView) itemView.findViewById(R.id.more);
        recyclerView    = (RecyclerView) itemView.findViewById(R.id.main_recycler_view);


    }
}
