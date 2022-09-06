package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class SingleUiHolder extends RecyclerView.ViewHolder{

    public LinearLayout  linearSingleOne , linearSingleTwo ;
    public CustomTextView  txtCalculate , singleOneTitle , singleOneAns , singleTwoTitle , singleTwoAns;



    public SingleUiHolder(@NonNull View itemView) {

        super(itemView);


        linearSingleOne = itemView.findViewById(R.id.linearSingleOne);
        linearSingleTwo = itemView.findViewById(R.id.linearSingleTwo);


        txtCalculate = itemView.findViewById(R.id.txtCalculate);
        singleOneTitle = itemView.findViewById(R.id.singleOneTitle);
        singleOneAns = itemView.findViewById(R.id.singleOneAns);
        singleTwoTitle = itemView.findViewById(R.id.singleTwoTitle);
        singleTwoAns = itemView.findViewById(R.id.singleTwoAns);

    }
}
