package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class QAHolder extends RecyclerView.ViewHolder {

    public CustomTextView txtQuestion, txtAnswer, read, txtOneDp, txtTwelveDp;
    public LinearLayout linearQuestion, linearAnswer, linearMain;
    public CircularTextView showNoti;

    public QAHolder(@NonNull View itemView) {
        super(itemView);

        txtQuestion = itemView.findViewById(R.id.txtQuestion);
        txtAnswer = itemView.findViewById(R.id.txtAnswer);
        read = itemView.findViewById(R.id.read);
        txtOneDp = itemView.findViewById(R.id.txtOneDp);
        txtTwelveDp = itemView.findViewById(R.id.txtTwelveDp);
        linearQuestion = itemView.findViewById(R.id.linearQuestion);
        linearAnswer = itemView.findViewById(R.id.linearAnswer);
        linearMain = itemView.findViewById(R.id.linearMain);
        showNoti = itemView.findViewById(R.id.noti_show);
    }
}
