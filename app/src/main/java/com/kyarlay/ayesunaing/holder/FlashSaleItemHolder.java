package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class FlashSaleItemHolder extends RecyclerView.ViewHolder {

    public RelativeLayout relative;
    public CustomTextView  txtTitle, txtPrice, txtStrike, txtAvailable,addtocart_text;
    public NetworkImageView gridItemImageView;
    public TextView txtDay,txtHour, txtMinute, txtSecond;
    public TextView txtDayONe,txtHourONe, txtMinuteONe, txtSecondONe;
    public LinearLayout addtocart,linearLayout1;
    public LinearLayout cardView;
    //public RibbonView txtAllMark;
    public CustomTextView txtAllMark;


    public FlashSaleItemHolder(@NonNull View itemView) {
        super(itemView);


        relative = itemView.findViewById(R.id.relative);
        txtAllMark = itemView.findViewById(R.id.txtAllMark);
        gridItemImageView = itemView.findViewById(R.id.gridItemImageView);
        txtDay = itemView.findViewById(R.id.txtDay);
        txtHour = itemView.findViewById(R.id.txtHour);
        txtMinute = itemView.findViewById(R.id.txtMinute);
        txtSecond = itemView.findViewById(R.id.txtSecond);
        txtSecondONe = itemView.findViewById(R.id.txtSecondONe);
        txtDayONe = itemView.findViewById(R.id.txtDayONe);
        txtHourONe = itemView.findViewById(R.id.txtHourONe);
        txtMinuteONe = itemView.findViewById(R.id.txtMinuteONe);
        txtTitle = itemView.findViewById(R.id.gridItemTitleView);
        txtPrice = itemView.findViewById(R.id.gridItemPriceView);
        txtStrike = itemView.findViewById(R.id.price_strike);
        txtAvailable = itemView.findViewById(R.id.txtAvailable);
        addtocart = itemView.findViewById(R.id.addtocart);
        addtocart_text = itemView.findViewById(R.id.addtocart_text);
        linearLayout1 = itemView.findViewById(R.id.linearLayout1);
        cardView = itemView.findViewById(R.id.gridItemImageView_Cart);

    }
}
