package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.FadingEdgeLayout;

public class DetailGridHolder extends RecyclerView.ViewHolder {

    public RecyclerView main_recycler_view;
    public CustomTextView brandTitle;
    public CustomTextView brand;
    public CardView card_detail_image;
    public LinearLayout linearBrand, options_layout, linearMain;
    public CustomTextView more;
    public FadingEdgeLayout fadeEdge;
    public CustomTextView desc, options_text, options;



    public CustomTextView  txtViewAllQA, txtNoQA, txtQuestion,txtAnswer, txtAskQA;
    public LinearLayout linearQuestion, linearAnswer;

    public DetailGridHolder(@NonNull View itemView) {
        super(itemView);

        main_recycler_view = itemView.findViewById(R.id.main_recycler_view);
        brand = itemView.findViewById(R.id.brand);
        brandTitle = itemView.findViewById(R.id.brandTitle);
        card_detail_image = itemView.findViewById(R.id.card_detail_image);
        linearBrand = itemView.findViewById(R.id.linearBrand);
        linearMain = itemView.findViewById(R.id.linearMain);
        more = itemView.findViewById(R.id.more);


        txtViewAllQA = itemView.findViewById(R.id.txtViewAllQA);
        txtNoQA = itemView.findViewById(R.id.txtNoQA);
        txtQuestion = itemView.findViewById(R.id.txtQuestion);
        txtAnswer = itemView.findViewById(R.id.txtAnswer);
        txtAskQA = itemView.findViewById(R.id.txtAskQA);
        linearQuestion = itemView.findViewById(R.id.linearQuestion);
        linearAnswer = itemView.findViewById(R.id.linearAnswer);

        options             = itemView.findViewById(R.id.product_detail_option);
        options_text        = itemView.findViewById(R.id.product_detail_option_text);
        options_layout      = itemView.findViewById(R.id.option_layout);
        desc            = (CustomTextView) itemView.findViewById(R.id.product_detail_description);
        fadeEdge        = itemView.findViewById(R.id.fadeEdge);
    }
}
