package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;


public class BabyInfoHolder extends RecyclerView.ViewHolder{

    public CustomTextView txtNoInfo, babyAge, edit, txtPrev, txtNext, txtSingleTitle;
    public ProgressBar circularProgressbar;
    public NetworkImageView img;
    public LinearLayout linearAll;


    public BabyInfoHolder(@NonNull View itemView) {

        super(itemView);


        circularProgressbar = itemView.findViewById(R.id.circularProgressbar);
        img = itemView.findViewById(R.id.img);
        babyAge = itemView.findViewById(R.id.babyAge);
        txtNoInfo = itemView.findViewById(R.id.txtNoInfo);
        txtPrev = itemView.findViewById(R.id.txtPrev);
        txtNext = itemView.findViewById(R.id.txtNext);
        linearAll = itemView.findViewById(R.id.linearAll);

       // txtSingleTitle = itemView.findViewById(R.id.txtSingleTitle);


    }

}
