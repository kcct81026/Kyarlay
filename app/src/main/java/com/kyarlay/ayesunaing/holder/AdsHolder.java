package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;

public class AdsHolder extends RecyclerView.ViewHolder {

    public NetworkImageView imgAds;
    public LinearLayout info_layout;
    public RelativeLayout ads_layout;
    public TextView txtSponsor;


    public AdsHolder(@NonNull View itemView) {
        super(itemView);

        imgAds = itemView.findViewById(R.id.imgAds);
        info_layout = itemView.findViewById(R.id.info_layout);
        ads_layout = itemView.findViewById(R.id.ads_layout);
        txtSponsor = itemView.findViewById(R.id.txtSponsor);
    }
}
