package com.kyarlay.ayesunaing.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class MainPaymentHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, txtPayment,txtPercent;
    public RecyclerView recyclerView;


    public MainPaymentHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        recyclerView = itemView.findViewById(R.id.recycler_view);
        txtPayment = itemView.findViewById(R.id.txtPayment);
        txtPercent = itemView.findViewById(R.id.txtPercent);
    }
}
