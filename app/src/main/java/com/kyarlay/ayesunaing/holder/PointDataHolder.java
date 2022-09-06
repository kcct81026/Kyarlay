package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;

public class PointDataHolder extends RecyclerView.ViewHolder {

    public LinearLayout layoutPointHistory;
    public TextView txtHistoryTitle, txtPoint,txtUserPointTitle, txtExpireDate;

    public PointDataHolder(@NonNull View itemView) {
        super(itemView);

        layoutPointHistory = itemView.findViewById(R.id.layoutPointHistory);
        txtHistoryTitle = itemView.findViewById(R.id.txtHistoryTitle);
        txtPoint = itemView.findViewById(R.id.txtPoint );
        txtUserPointTitle = itemView.findViewById(R.id.txtUserPointTitle );
        txtExpireDate = itemView.findViewById(R.id.txtExpireDate );
    }
}
