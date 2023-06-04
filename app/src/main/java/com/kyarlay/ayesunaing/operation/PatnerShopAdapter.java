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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.BrandActivity;
import com.kyarlay.ayesunaing.activity.CategoryActivity;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.holder.BrandHolder;
import com.kyarlay.ayesunaing.holder.ExpandableHolder;
import com.kyarlay.ayesunaing.holder.FooterHoloder;
import com.kyarlay.ayesunaing.holder.NoItemHolder;
import com.kyarlay.ayesunaing.holder.SubExpandableHolder;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.MainItem;
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

    PatnerShopAdapter gridAdapter;
    RecyclerView.LayoutManager layoutManager;


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
        else if(universalList.get(position).getPostType().equals(EXPANDABLE_LIST)){
            return VIEW_TYPE_EXPANDABLE_LIST;
        }
        else if(universalList.get(position).getPostType().equals(EXPANDABLE_SUB_LIST)){
            return VIEW_TYPE_EXPANDABLE_SUB_LIST;
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

        else if(viewType == VIEW_TYPE_EXPANDABLE_LIST){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_expandable_list_view, parent, false);
            viewHolder = new ExpandableHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_EXPANDABLE_SUB_LIST){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_sub_expandable_list, parent, false);
            viewHolder = new SubExpandableHolder(viewItem);
        }

        return viewHolder;

    }
    @SuppressLint({"ResourceType", "StringFormatMatches"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder parentHolder, int position) {
        int type = getItemViewType(position);

        switch (type) {
            case VIEW_TYPE_EXPANDABLE_SUB_LIST:{
                SubExpandableHolder subExpandableHolder = (SubExpandableHolder) parentHolder;
                CategoryMain categoryMainSub = (CategoryMain) universalList.get(position);

                if (categoryMainSub.getImage() != null && !(categoryMainSub.getImage().equals(""))) {
                    subExpandableHolder.icon.setImageUrl(categoryMainSub.getImage(), imageLoader);

                }
                subExpandableHolder.title.setText(categoryMainSub.getTitle());
                subExpandableHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveStringPreferences(SP_BRAND_TAG, categoryMainSub.getTag());
                        prefs.saveIntPerferences(SP_BRAND_ID, categoryMainSub.getId());


                        Intent intent = new Intent(activity, CategoryActivity.class);
                        MainItem grid = new MainItem();
                        grid.setId(categoryMainSub.getId());
                        grid.setPost_type(categoryMainSub.getTag());
                        Bundle b = new Bundle();
                        b.putParcelable("mainCat", grid);
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", grid.getPost_type());
                            FlurryAgent.logEvent("Click Main Category", mix);
                        } catch (Exception e) {
                        }
                        intent.putExtras(b);
                        activity.startActivity(intent);
                    }
                });
                break;
            }

            case VIEW_TYPE_EXPANDABLE_LIST : {

                ExpandableHolder expandableHolder = (ExpandableHolder) parentHolder;
                MainCategoryObj  mainCategoryObj = (MainCategoryObj) universalList.get(position);
                ArrayList<UniversalPost> universalCategories = new ArrayList<>();

                if(mainCategoryObj.getCategoryMainList().size() > 0){
                    expandableHolder.recyclerOne.setVisibility(View.VISIBLE);


                    if (mainCategoryObj.getShowTitle() == 0){
                        expandableHolder.txtHidden.setVisibility(View.GONE);
                        expandableHolder.recyclerOne.setVisibility(View.GONE);
                        expandableHolder.img.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_circle_24));
                    }
                    else{
                        expandableHolder.txtHidden.setVisibility(View.VISIBLE);
                        expandableHolder.recyclerOne.setVisibility(View.VISIBLE);
                        expandableHolder.img.setImageDrawable(activity.getResources().getDrawable(R.drawable.up_arrow_black));

                    }

                    for(int i = 0 ; i < mainCategoryObj.getCategoryMainList().size() ; i++){
                        CategoryMain uni = mainCategoryObj.getCategoryMainList().get(i);
                        uni.setPostType(EXPANDABLE_SUB_LIST);
                        universalCategories.add(uni);
                    }

                    gridAdapter = new PatnerShopAdapter(activity, universalCategories);
                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);
                    if (expandableHolder.recyclerOne != null) {
                        expandableHolder.recyclerOne.setLayoutManager(layoutManager);
                        expandableHolder.recyclerOne.setAdapter(gridAdapter);
                    }

                    if(mainCategoryObj.getImage() != null && mainCategoryObj.getImage().trim().length() > 0) {
                        expandableHolder.icon.setImageUrl(mainCategoryObj.getImage(), imageLoader);
                        expandableHolder.icon.setVisibility(View.VISIBLE);
                        expandableHolder.defaultIcon.setVisibility(View.GONE);
                    }else{
                        expandableHolder.icon.setVisibility(View.GONE);
                        expandableHolder.defaultIcon.setVisibility(View.VISIBLE);
                    }

                    expandableHolder.recyclerOne.getItemAnimator().setChangeDuration(0);
                    expandableHolder.recyclerOne.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                        @Override
                        public void onChildViewAttachedToWindow(View view) {
                            NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                            if(image != null) {
                                image.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onChildViewDetachedFromWindow(View view) {
                            NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                            if(image != null) {
                                image.setVisibility(View.GONE);
                            }

                        }
                    });
                }else{
                    expandableHolder.recyclerOne.setVisibility(View.GONE);
                    expandableHolder.txtHidden.setVisibility(View.GONE);
                }




                /*if (mainCategoryObj.get() != null && !(detailProductTwo.getPreviewImage().equals(""))) {
                    catDetailHolder.gridItemImageViewTwo.setImageUrl(detailProductTwo.getPreviewImage(), AppController.getInstance().getImageLoader());

                }*/

                expandableHolder.title.setText(mainCategoryObj.getTitle());


                expandableHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainCategoryObj.getShowTitle() == 0){
                            mainCategoryObj.setShowTitle(1);
                        }
                        else{
                            mainCategoryObj.setShowTitle(0);
                        }

                        notifyDataSetChanged();
                    }
                });



                break;
            }

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
