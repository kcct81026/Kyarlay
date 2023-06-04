package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

public class ExpandableHolder extends RecyclerView.ViewHolder {

    public TextView title,txtHidden;
    public ImageView img, defaultIcon;
    public RecyclerView recyclerOne;
    public NetworkImageView icon;
    public LinearLayout layout;
    public ExpandableHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        img = itemView.findViewById(R.id.img);
        recyclerOne = itemView.findViewById(R.id.recyclerOne);
        icon = itemView.findViewById(R.id.icon);
        layout = itemView.findViewById(R.id.layout);
        txtHidden = itemView.findViewById(R.id.txtHidden);
        defaultIcon = itemView.findViewById(R.id.default_icon);
    }
}
