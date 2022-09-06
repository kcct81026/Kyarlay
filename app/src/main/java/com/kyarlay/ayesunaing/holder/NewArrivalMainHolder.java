package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;

public class NewArrivalMainHolder extends RecyclerView.ViewHolder {

    public RecyclerView recyclerView;
    public TextView txtNewArrival,txtSeeMore,txtContinue;
    public LinearLayout linearSeeMore,linearArrivalMain,linear, layoutContinue;
    public ImageView imgFlash;


    public NewArrivalMainHolder(@NonNull View itemView) {
        super(itemView);

        recyclerView = itemView.findViewById(R.id.recycler_view);

        txtNewArrival = itemView.findViewById(R.id.txtNewArrival);
        linearSeeMore = itemView.findViewById(R.id.linearSeeMore);
        imgFlash = itemView.findViewById(R.id.imgFlash);
        linearArrivalMain = itemView.findViewById(R.id.linearArrivalMain);
        linear = itemView.findViewById(R.id.linear);
        txtSeeMore = itemView.findViewById(R.id.txtSeeMore);
        txtContinue = itemView.findViewById(R.id.txtContinue);
        layoutContinue = itemView.findViewById(R.id.layoutContinue);

    }


}
