package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

/**
 * Created by ayesunaing on 3/22/17.
 */

public class ProductCategoryDetailItem  extends RecyclerView.ViewHolder {

    public CustomTextView title, price, discounts, member_discount, txtAllChannel, txtMark,soldoutcart_text,
            recommended, category_name, price_strike, addtocart_text, option, member_discount_flash_sale;
    public NetworkImageView imageView;
    public CardView frameLayout;
    public ImageView wishlist, imgGetPoint;
    //public LinearLayout price_layout, text_layout, wishlist_layout, all, addtocart;
    public LinearLayout  text_layout, all, addtocart,soldOutCart;
    public View test;
    public RelativeLayout relativeLayout;
    public ImageView imgCart, imgCar;
    public CustomTextView product_warning;



    public ProductCategoryDetailItem(View itemView, Display display) {
        super(itemView);
        txtAllChannel   =  itemView.findViewById(R.id.txtAllChannel);
        txtMark         =  itemView.findViewById(R.id.txtAllMark);
        title           =  itemView.findViewById(R.id.gridItemTitleView);
        price           =  itemView.findViewById(R.id.gridItemPriceView);
        imageView       =  itemView.findViewById(R.id.gridItemImageView);
        frameLayout     =  itemView.findViewById(R.id.gridItemImageView_Cart);
        wishlist        =  itemView.findViewById(R.id.wishlist);
        imgGetPoint        =  itemView.findViewById(R.id.imgGetPoint);
        discounts       =  itemView.findViewById(R.id.discounts);
        text_layout     =  itemView.findViewById(R.id.text_layout);
        member_discount =  itemView.findViewById(R.id.member_discount);
        member_discount_flash_sale =  itemView.findViewById(R.id.member_discount_flash_sale);
        recommended     =  itemView.findViewById(R.id.recommended);
        category_name   =  itemView.findViewById(R.id.category_name);
        price_strike    =  itemView.findViewById(R.id.price_strike);
        all             =  itemView.findViewById(R.id.all);
        addtocart       =  itemView.findViewById(R.id.addtocart);
        addtocart_text  = itemView.findViewById(R.id.addtocart_text);
        imgCart         = itemView.findViewById(R.id.imgCart);
        imgCar         = itemView.findViewById(R.id.imgCar);
        // test            = itemView.findViewById(R.id.test);
        option          = itemView.findViewById(R.id.option);
        soldOutCart          = itemView.findViewById(R.id.soldOutCart);
        soldoutcart_text          = itemView.findViewById(R.id.soldoutcart_text);

        relativeLayout = itemView.findViewById(R.id.relative);
        product_warning = itemView.findViewById(R.id.product_warning);

        relativeLayout.getLayoutParams().height = (int) ( display.getWidth() *  3 )/ 5;
        relativeLayout.getLayoutParams().width  = (int) ( display.getWidth() * 2.5 ) / 5;
        // imageView.getLayoutParams().width  = (int) ( display.getWidth() * 2.2 ) / 5;
        // imageView.getLayoutParams().height  = (int) ( display.getWidth() * 2.2 ) / 5;
        text_layout.getLayoutParams().height  = (int)( display.getWidth() * 3 ) / 5;


    }


}
