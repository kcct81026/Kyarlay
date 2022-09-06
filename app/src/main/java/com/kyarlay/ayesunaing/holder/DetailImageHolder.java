package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

public class DetailImageHolder extends RecyclerView.ViewHolder {
    public NetworkImageView img;
    public TextView txtHidden;


    public DetailImageHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.detailNetworkImg);
        txtHidden = itemView.findViewById(R.id.txtHidden);
    }
}
