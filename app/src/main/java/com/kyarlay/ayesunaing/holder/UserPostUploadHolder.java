package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class UserPostUploadHolder extends RecyclerView.ViewHolder {

    public LinearLayout layout;
    public CustomTextView textview;
    public UserPostUploadHolder(View itemView) {
        super(itemView);

        layout      = (LinearLayout) itemView.findViewById(R.id.layout);
        textview    = (CustomTextView) itemView.findViewById(R.id.textview);
    }
}
