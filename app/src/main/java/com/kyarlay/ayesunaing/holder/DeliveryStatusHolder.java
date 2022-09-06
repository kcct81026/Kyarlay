package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class DeliveryStatusHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearBasket, linearPending, linearComplete;
    public CustomTextView txtBasket, txtPending, txtComplete;
    public ImageView imgBasket, imgPending, imgComplete;

    public DeliveryStatusHolder(@NonNull View itemView, Display display) {
        super(itemView);

        linearComplete = itemView.findViewById(R.id.layoutComplete);
        linearPending = itemView.findViewById(R.id.layoutPending);
        linearBasket = itemView.findViewById(R.id.layoutBasket);
        txtBasket = itemView.findViewById(R.id.txtBasket);
        txtPending = itemView.findViewById(R.id.txtPending);
        txtComplete = itemView.findViewById(R.id.txtComplete);
        imgComplete = itemView.findViewById(R.id.imgComplete);
        imgBasket = itemView.findViewById(R.id.imgBasket);
        imgPending = itemView.findViewById(R.id.imgPending);

        imgBasket.getLayoutParams().width  =  (display.getWidth() * 3) / 26;
        imgBasket.getLayoutParams().height =  (display.getWidth() * 3) / 26;
        txtBasket.getLayoutParams().height =  (display.getWidth() * 4) / 26;

        linearBasket.getLayoutParams().width  =  (display.getWidth() * 6) / 18;
        linearBasket.getLayoutParams().height  =  (display.getWidth() * 10) / 26;

        imgPending.getLayoutParams().width  =  (display.getWidth() * 3) / 26;
        imgPending.getLayoutParams().height =  (display.getWidth() * 3) / 26;
        txtPending.getLayoutParams().height =  (display.getWidth() * 4) / 26;
        linearPending.getLayoutParams().width  =  (display.getWidth() * 6) / 18;
        linearPending.getLayoutParams().height  =  (display.getWidth() * 10) / 26;


        imgComplete.getLayoutParams().width  =  (display.getWidth() * 3) / 26;
        imgComplete.getLayoutParams().height =  (display.getWidth() * 3) / 26;
        txtComplete.getLayoutParams().height =  (display.getWidth() * 4) / 26;
        linearComplete.getLayoutParams().width  =  (display.getWidth() * 6) / 18;
        linearComplete.getLayoutParams().height  =  (display.getWidth() * 10) / 26;
    }
}
