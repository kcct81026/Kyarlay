package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class RefreshHolder extends RecyclerView.ViewHolder {

    public CustomTextView refresh;

    public RefreshHolder(@NonNull View itemView) {
        super(itemView);

        refresh = itemView.findViewById(R.id.cart_detail_foooter);
    }
}
