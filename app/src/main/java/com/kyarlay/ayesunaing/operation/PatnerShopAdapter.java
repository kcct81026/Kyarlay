package com.kyarlay.ayesunaing.operation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.BrandActivity;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.holder.BrandHolder;
import com.kyarlay.ayesunaing.holder.FooterHoloder;
import com.kyarlay.ayesunaing.holder.NoItemHolder;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.UniversalPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatnerShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements ConstantVariable, Constant {

    private static final String TAG = "PatnerShopAdapter";

    ArrayList<UniversalPost> universalList;
    AppCompatActivity activity;
    ImageLoader imageLoader;
    Display display;
    DatabaseAdapter databaseAdapter;
    MyPreference prefs;
    Resources resources;


    public PatnerShopAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList) {
        this.universalList  = universalList;
        this.activity       = activity1;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        new MyFlurry(activity);
    }

    @Override
    public int getItemCount() {
        return universalList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(universalList.get(position).getPostType().equals(CART_DETAIL_FOOTER)){
            return VIEW_TYPE_CART_DETAIL_FOOTER;
        }
        else if(universalList.get(position).getPostType().equals(CART_DETAIL_NO_ITEM)){
            return VIEW_TYPE_CART_DETAIL_NO_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(BRAND)){
            return VIEW_TYPE_BRAND;
        }

        return VIEW_TYPE_DEFAULT;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == VIEW_TYPE_CART_DETAIL_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_footer, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }else if(viewType == VIEW_TYPE_CART_DETAIL_NO_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new NoItemHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_BRAND){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_category_item, parent, false);
            viewHolder = new BrandHolder(viewItem, display);
        }

        return viewHolder;

    }
    @SuppressLint({"ResourceType", "StringFormatMatches"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder parentHolder, int position) {
        int type = getItemViewType(position);

        switch (type) {

            case VIEW_TYPE_BRAND:{
                BrandHolder itemHolder = (BrandHolder) parentHolder;
                Brand brand = (Brand) universalList.get(position);
                itemHolder.imageView.setImageUrl(brand.getImageUrl(), imageLoader);

                itemHolder.title.setText(brand.getTitle());

                if(brand.getDesc() != null && brand.getDesc().trim().length() > 0){
                    itemHolder.description.setVisibility(View.VISIBLE);
                    itemHolder.description.setText(brand.getDesc());
                }else{
                    itemHolder.description.setVisibility(View.GONE);
                }

                itemHolder.all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source","brand");
                            mix.put("source_id", brand.getTag());
                            FlurryAgent.logEvent("View Brand Detail", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, BrandActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("fromBrandAll", true);
                        bundle.putInt("id", brand.getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                break;
            }

            case VIEW_TYPE_CART_DETAIL_NO_ITEM:{
                NoItemHolder noitem = (NoItemHolder) parentHolder;
                noitem.space.getLayoutParams().height = 350 ;
                noitem.space.setText(resources.getString(R.string.noitem));
                noitem.space.setGravity(Gravity.CENTER);
                noitem.space.setTextSize(12);
                break;
            }

            case VIEW_TYPE_CART_DETAIL_FOOTER:{

                final FooterHoloder footerHoloder = (FooterHoloder) parentHolder;
                break;
            }

        }

    }
}
