package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class OrderNowHolder extends RecyclerView.ViewHolder {

    public CustomButton txtOrder;
    public CustomTextView txtcheckDeliPrice,delivery_price_text, delivery_price,
            total_price_text,total_price;
    public CustomTextView txtRemarkTitle, total_point_text, total_point,
            commission_price_text,commission_price;
    public CustomEditText edRemark;

    public LinearLayout linearDeliAll, layout_nine_am, layout_twelve_am,
            layout_three_pm, layout_six_pm,linearChooseDate,layoutDateForHidden;
    public CustomTextView title_choose_date, choose_date, nine_am, twelve_am, three_pm, six_pm,
            txtDeliveryRemark, txtExpress, txtChoose;


   // public RadioButton radioNineAm, radiotwelveAm, radioThreePm, radioSixPm;

    public LinearLayout layoutNormal, time_layout;
    public CustomTextView txtFullDelivery,txtRightNow,textDeliveryRemark;
    public CustomTextView nine_am_title,twelve_am_title,three_pm_title, six_pm_title;



    public LinearLayout layout_date_only;
    public CustomTextView txt_date_only;


    public OrderNowHolder(@NonNull View itemView) {
        super(itemView);

        txtOrder = itemView.findViewById(R.id.order_yes_button);
        txtcheckDeliPrice = itemView.findViewById(R.id.txtcheckDeliPrice);
        delivery_price_text = itemView.findViewById(R.id.delivery_price_text);
        delivery_price = itemView.findViewById(R.id.delivery_price);
        total_price_text = itemView.findViewById(R.id.total_price_text);
        total_price = itemView.findViewById(R.id.total_price);
        txtRemarkTitle = itemView.findViewById(R.id.txtRemarkTitle);
        total_point_text = itemView.findViewById(R.id.total_point_text);
        total_point = itemView.findViewById(R.id.total_point);
        commission_price_text = itemView.findViewById(R.id.commission_price_text);
        commission_price = itemView.findViewById(R.id.commission_price);
        edRemark = itemView.findViewById(R.id.edRemark);
        textDeliveryRemark = itemView.findViewById(R.id.textDeliveryRemark);


        layoutNormal = itemView.findViewById(R.id.layoutNormal);
        txtFullDelivery = itemView.findViewById(R.id.txtFullDelivery);
        txtRightNow = itemView.findViewById(R.id.txtRightNow);
        nine_am_title               = itemView.findViewById(R.id.nine_am_title);
        twelve_am_title               = itemView.findViewById(R.id.twelve_am_title);
        three_pm_title               = itemView.findViewById(R.id.three_pm_title);
        six_pm_title               = itemView.findViewById(R.id.six_pm_title);

        linearChooseDate            = itemView.findViewById(R.id.linearChooseDate);
        linearDeliAll               = itemView.findViewById(R.id.linearDeliAll);
        layout_nine_am              = itemView.findViewById(R.id.layout_nine_am);
        layout_twelve_am            = itemView.findViewById(R.id.layout_twelve_am);
        layout_three_pm             = itemView.findViewById(R.id.layout_three_pm);
        layout_six_pm               = itemView.findViewById(R.id.layout_six_pm);
        time_layout               = itemView.findViewById(R.id.time_layout);



        title_choose_date           = itemView.findViewById(R.id.title_choose_date);
        txtDeliveryRemark           = itemView.findViewById(R.id.txtDeliveryRemark);
        choose_date                 = itemView.findViewById(R.id.choose_date);
        nine_am                     = itemView.findViewById(R.id.nine_am);
        twelve_am                   = itemView.findViewById(R.id.twelve_am);
        three_pm                    = itemView.findViewById(R.id.three_pm);
        six_pm                      = itemView.findViewById(R.id.six_pm);


/*
        radioNineAm                 = itemView.findViewById(R.id.radioNineAm);
        radiotwelveAm               = itemView.findViewById(R.id.radiotwelveAm);
        radioThreePm                = itemView.findViewById(R.id.radioThreePm);
        radioSixPm                  = itemView.findViewById(R.id.radioSixPm);*/

        layout_date_only = itemView.findViewById(R.id.layout_date_only);
        txt_date_only = itemView.findViewById(R.id.txt_date_only);

        layoutDateForHidden = itemView.findViewById(R.id.layoutDateForHidden);
        txtExpress = itemView.findViewById(R.id.txtExpress);
        txtChoose = itemView.findViewById(R.id.txtChoose);




    }
}
