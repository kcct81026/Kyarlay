package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class StepOneItemHolder extends RecyclerView.ViewHolder {

    public CheckBox chkAddress;
    public CustomTextView addressText,name_text;
    public ImageView imgDelete,img_arrow;
    public LinearLayout linearAddress;

    public StepOneItemHolder(@NonNull View itemView) {
        super(itemView);

        addressText = itemView.findViewById(R.id.addressText);
        name_text = itemView.findViewById(R.id.name_text);
        chkAddress = itemView.findViewById(R.id.chkAddress);
        imgDelete = itemView.findViewById(R.id.imgDelete);
        img_arrow = itemView.findViewById(R.id.img_arrow);
        linearAddress = itemView.findViewById(R.id.linearAddress);
    }
}
