package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class WatchMoreHolder extends RecyclerView.ViewHolder {

    public CustomTextView more;

    public WatchMoreHolder(@NonNull View itemView) {
        super(itemView);

        more = itemView.findViewById(R.id.more);
    }
}
