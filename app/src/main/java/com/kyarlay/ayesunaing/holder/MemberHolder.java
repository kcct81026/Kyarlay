package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.Switch;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesu on 5/31/17.
 */

public class MemberHolder extends RecyclerView.ViewHolder {

    /*public CustomTextView title, number_text, phone_text;
    public CustomEditText number, phone;
    public CustomButton button;*/
    public Switch aSwitch;
    public CustomTextView textView;
    public MemberHolder(View itemView) {
        super(itemView);

        aSwitch       = (Switch) itemView.findViewById(R.id.switch1);
        textView      = (CustomTextView) itemView.findViewById(R.id.text);

    }
}

