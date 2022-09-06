package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class OrderDetailsFooter extends RecyclerView.ViewHolder {

    public RecyclerView recycler_view;
    public TextView txtViewAll;
    public CustomTextView product_discount_text,product_discount,point_use,delivery_price_text,
            delivery_price,payment_text,payment_price,total_price_text,total_price,txtPayment,
            txtRemark,txtContact,point_text,flash_discount_text,flash_discount,member_discount,
            memeber_discount_text,txtAccountNumberTittle,txtAccountNumber,txtCopy,txtAccountNameTittle,txtAccountName;


    public LinearLayout linear_payment,payment_layout,flash_discount_layout,member_discount_layout;
    public NetworkImageView imgPayment;

    public OrderDetailsFooter(@NonNull View itemView) {
        super(itemView);

        recycler_view = itemView.findViewById(R.id.recycler_view);
        txtViewAll = itemView.findViewById(R.id.txtViewAll);
        product_discount_text = itemView.findViewById(R.id.product_discount_text);
        product_discount = itemView.findViewById(R.id.product_discount);
        point_use = itemView.findViewById(R.id.point_use);
        delivery_price_text = itemView.findViewById(R.id.delivery_price_text);
        delivery_price = itemView.findViewById(R.id.delivery_price);
        payment_text = itemView.findViewById(R.id.payment_text);
        payment_price = itemView.findViewById(R.id.payment_price);
        total_price_text = itemView.findViewById(R.id.total_price_text);
        total_price = itemView.findViewById(R.id.total_price);
        linear_payment = itemView.findViewById(R.id.linear_payment);
        imgPayment = itemView.findViewById(R.id.imgPayment);
        txtPayment = itemView.findViewById(R.id.txtPayment);
        txtRemark = itemView.findViewById(R.id.txtRemark);
        txtContact = itemView.findViewById(R.id.txtContact);
        payment_layout = itemView.findViewById(R.id.payment_layout);
        point_text = itemView.findViewById(R.id.point_text);
        flash_discount_layout = itemView.findViewById(R.id.flash_discount_layout);
        flash_discount_text = itemView.findViewById(R.id.flash_discount_text);
        flash_discount = itemView.findViewById(R.id.flash_discount);
        member_discount = itemView.findViewById(R.id.member_discount);
        memeber_discount_text = itemView.findViewById(R.id.memeber_discount_text);
        member_discount_layout = itemView.findViewById(R.id.member_discount_layout);
        txtAccountNumberTittle = itemView.findViewById(R.id.txtAccountNumberTittle);
        txtAccountNumber = itemView.findViewById(R.id.txtAccountNumber);
        txtCopy = itemView.findViewById(R.id.txtCopy);
        txtAccountNameTittle = itemView.findViewById(R.id.txtAccountNameTittle);
        txtAccountName = itemView.findViewById(R.id.txtAccountName);



    }
}
