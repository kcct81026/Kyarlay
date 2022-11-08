package com.kyarlay.ayesunaing.operation;


import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityAdsList;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.ActivityStepOneCart;
import com.kyarlay.ayesunaing.activity.ActivityStepTwoCart;
import com.kyarlay.ayesunaing.activity.ActivityWebView;
import com.kyarlay.ayesunaing.activity.AndroidLoadImageFromAdsUrl;
import com.kyarlay.ayesunaing.activity.AskProdcutAcitivity;
import com.kyarlay.ayesunaing.activity.BrandActivity;
import com.kyarlay.ayesunaing.activity.BrandAllActivity;
import com.kyarlay.ayesunaing.activity.BrandPartnerAllActivity;
import com.kyarlay.ayesunaing.activity.BrandedDetailActivity;
import com.kyarlay.ayesunaing.activity.CampainDetailActivity;
import com.kyarlay.ayesunaing.activity.CategoryActivity;
import com.kyarlay.ayesunaing.activity.ClinicActivity;
import com.kyarlay.ayesunaing.activity.CollectionDetailActivity;
import com.kyarlay.ayesunaing.activity.DiscountActivity;
import com.kyarlay.ayesunaing.activity.FlashSalesActivity;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.activity.NameWishListActivity;
import com.kyarlay.ayesunaing.activity.NewsClickActivity;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.OlderedActivity;
import com.kyarlay.ayesunaing.activity.OrderDetailActivity;
import com.kyarlay.ayesunaing.activity.PointDashboardActivity;
import com.kyarlay.ayesunaing.activity.ProductActivity;
import com.kyarlay.ayesunaing.activity.ProductListClickActivity;
import com.kyarlay.ayesunaing.activity.ReadingWishlistActivity;
import com.kyarlay.ayesunaing.activity.SearchResultActivity;
import com.kyarlay.ayesunaing.activity.ShoppingCartActivity;
import com.kyarlay.ayesunaing.activity.ShowAllNamesActivity;
import com.kyarlay.ayesunaing.activity.WishListActivity;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.fcm.DeeplinkingListActivity;
import com.kyarlay.ayesunaing.holder.BannerHolder;
import com.kyarlay.ayesunaing.holder.BrandBannerHolder;
import com.kyarlay.ayesunaing.holder.CampianHolder;
import com.kyarlay.ayesunaing.holder.FlashSaleItemHolder;
import com.kyarlay.ayesunaing.holder.FooterHoloder;
import com.kyarlay.ayesunaing.holder.HotItemHolder;
import com.kyarlay.ayesunaing.holder.MainDiscountGridHolder;
import com.kyarlay.ayesunaing.holder.MallGridHolder;
import com.kyarlay.ayesunaing.holder.MoreHolder;
import com.kyarlay.ayesunaing.holder.NewArrivalHolder;
import com.kyarlay.ayesunaing.holder.NewArrivalMainHolder;
import com.kyarlay.ayesunaing.holder.NoItemHolder;
import com.kyarlay.ayesunaing.holder.OrderPendingHolder;
import com.kyarlay.ayesunaing.holder.OrderTrackHolder;
import com.kyarlay.ayesunaing.holder.PointHolder;
import com.kyarlay.ayesunaing.holder.ProductItemHolder;
import com.kyarlay.ayesunaing.holder.RecyclerOnlyHolder;
import com.kyarlay.ayesunaing.holder.RefreshHolder;
import com.kyarlay.ayesunaing.holder.SliderHolder;
import com.kyarlay.ayesunaing.holder.UploadPostGetPointHolder;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.Campaign;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.Discount;
import com.kyarlay.ayesunaing.object.FlashList;
import com.kyarlay.ayesunaing.object.Flash_Sales;
import com.kyarlay.ayesunaing.object.MainBrand;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainMallGrid;
import com.kyarlay.ayesunaing.object.MainNav;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.MemberBenefitObject;
import com.kyarlay.ayesunaing.object.Order;
import com.kyarlay.ayesunaing.object.OrderDetailsObj;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by ayesunaing on 2/17/17.
 */

public class MallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements ConstantVariable, Constant, ConstantsDB {

    private static final String TAG = "MallAdapter";

    ArrayList<UniversalPost> universalList;
    AppCompatActivity activity;
    ImageLoader imageLoader;
    Display display;
    DatabaseAdapter databaseAdapter;
    MyPreference prefs;
    FirebaseAnalytics firebaseAnalytics;
    Resources resources;
    ArrayList<Delivery> delList = new ArrayList<>();
    MallAdapter gridAdapter;
    RecyclerView.LayoutManager layoutManager;
    Handler handler;
    Runnable runnable;
    int countIndex = 0;
    int speedScroll = 5000;
    int numClick    = -1;
    Map<Integer, CountDownTimer> countDownMap;
    DecimalFormat formatter = new DecimalFormat("#,###,###");



    public MallAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList, Map<Integer, CountDownTimer> countDownMap) {
        this.countDownMap = countDownMap;
        this.universalList  = universalList;
        this.activity       = activity1;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        delList = databaseAdapter.getDelivery();


        new MyFlurry(activity);

        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);




    }


    public MallAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList) {
        this.universalList  = universalList;
        this.activity       = activity1;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        delList = databaseAdapter.getDelivery();


        new MyFlurry(activity);

        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);


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
        else if(universalList.get(position).getPostType().equals(HOT_CATEGORY_MAIN)){
            return VIEW_TYPE_HOT_CATEGORY_MAIN;
        }
        else if (universalList.get(position).getPostType().equals(HOT_CATEGORY_ITEM)) {
            return VIEW_TYPE_HOT_CATEGORY_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(CART_DETAIL_NO_ITEM)){
            return VIEW_TYPE_CART_DETAIL_NO_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(CUSTOM_SLIDER_MAIN)){
            return VIEW_TYPE_CUSTOM_SLIDER_MAIN;
        }
        else if(universalList.get(position).getPostType().equals(CUSTOM_SLIDER_ITEM_NO_PADDING)){
            return VIEW_TYPE_CUSTOM_SLIDER_ITEM_NO_PADDING ;
        }
        else if(universalList.get(position).getPostType().equals(CUSTOM_SLIDER_ITEM)){
            return VIEW_TYPE_CUSTOM_SLIDER_ITEM ;
        }
        else if (universalList.get(position).getPostType().equals(MAIN_NAV)) {
            return VIEW_TYPE_MAIN_NAV;
        }
        else if (universalList.get(position).getPostType().equals(MAIN_NAV_ITEM)) {
            return VIEW_TYPE_MAIN_NAV_ITEM;
        }
        else if (universalList.get(position).getPostType().equals(MAIN_FLASH_SALE)) {
            return VIEW_TYPE_MAIN_FLASH_SALE;
        }
        else if(universalList.get(position).getPostType().equals(MORE_ALL)){
            return VIEW_TYPE_MORE_ALL;
        }
        else if (universalList.get(position).getPostType().equals(MALL_FLASH_SALE_ITEM)) {
            return VIEW_TYPE_MALL_FLASH_SALE_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(PARTNER_BRAND)){
            return VIEW_TYPE_PARTNER_BRAND;
        }
        else if(universalList.get(position).getPostType().equals(PARTNER_BRAND_ITEM)) {
            return VIEW_TYPE_PARTNER_BRAND_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(RECOMMEND_BRAND)){
            return VIEW_TYPE_RECOMMEND_BRAND;
        }
        else if(universalList.get(position).getPostType().equals(RECOMMEND_BRAND_ITEM)) {
            return VIEW_TYPE_RECOMMEND_BRAND_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(POPULAR_BANNER)){
            return VIEW_TYPE_POPULAR_BANNER;
        }
        else if (universalList.get(position).getPostType().equals(MAIN_DISCOUNT)) {
            return VIEW_TYPE_MAIN_DISCOUNT;
        }
        else if(universalList.get(position).getPostType().equals(CAMPAIGN)){
            return VIEW_TYPE_CAMPAIGN;
        }
        else if (universalList.get(position).getPostType().equals(CATEGORY_BANNER)) {
            return VIEW_TYPE_CATEGORY_BANNER;
        }
        else if(universalList.get(position).getPostType().equals(COLLECTIONS_LIST)){
            return VIEW_TYPE_COLLECTIONS_LIST;
        }
        else if(universalList.get(position).getPostType().equals(COLLECTIONS_LIST_ITEM)){
            return VIEW_TYPE_COLLECTIONS_LIST_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(NEW_ARRIVAL)){
            return VIEW_TYPE_NEW_ARRIVAL;
        }
        else if(universalList.get(position).getPostType().equals(NEW_COLLECTION_LIST)){
            return VIEW_TYPE_NEW_COLLECTION_LIST;
        }
        else if(universalList.get(position).getPostType().equals(REFRESH_FOOTER)){
            return VIEW_TYPE_REFRESH_FOOTER;
        }
        else if(universalList.get(position).getPostType().equals(NEW_ARRIVAL_ITEM)){
            return VIEW_TYPE_NEW_ARRIVAL_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(STAGGERED_ITEM)){
            return VIEW_TYPE_STAGGERED_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(ORDER_PENDING)){
            return VIEW_TYPE_ORDER_PENDING;
        }
        else if(universalList.get(position).getPostType().equals(ORDER_PENDING_TRACK)){
            return VIEW_TYPE_ORDER_PENDING_TRACK;
        }
        else if (universalList.get(position).getPostType().equals(invite)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }
        else if (universalList.get(position).getPostType().equals(posting)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(signup)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(invite)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(feedback)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }
        else if (universalList.get(position).getPostType().equals(MALL_GRID)) {
            return VIEW_TYPE_MALL_GRID;
        }

        else if (universalList.get(position).getPostType().equals(POINT_DASHBOARD)) {
            return VIEW_TYPE_POINT_DASHBOARD;
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
        }
        else if(viewType == VIEW_TYPE_ORDER_PENDING ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_pending_order, parent, false);
            viewHolder = new OrderPendingHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_ORDER_PENDING_TRACK ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_pending_order_mall, parent, false);
            viewHolder = new OrderTrackHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_NEW_ARRIVAL) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_NEW_COLLECTION_LIST) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_NEW_ARRIVAL_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival_item, parent, false);
            viewHolder = new NewArrivalHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_REFRESH_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_touch_refresh, parent, false);
            viewHolder = new RefreshHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_HOT_CATEGORY_MAIN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_STAGGERED_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_product_item_staggered, parent, false);
            viewHolder = new ProductItemHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_POPULAR_BANNER ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_banner, parent, false);
            viewHolder = new BannerHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_CATEGORY_BANNER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_banner, parent, false);
            viewHolder = new BannerHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_COLLECTIONS_LIST_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.campian, parent, false);
            viewHolder = new CampianHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_COLLECTIONS_LIST) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_main_flash_sale, parent, false);
            viewHolder = new MainDiscountGridHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_CAMPAIGN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.campian, parent, false);
            viewHolder = new CampianHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_HOT_CATEGORY_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_hot_item, parent, false);
            viewHolder = new HotItemHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_MAIN_DISCOUNT) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_CART_DETAIL_NO_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new NoItemHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_MALL_FLASH_SALE_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_mall_flash_sale_item, parent, false);
            viewHolder = new FlashSaleItemHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_CUSTOM_SLIDER_MAIN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_recycler_only, parent, false);
            viewHolder = new RecyclerOnlyHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_CUSTOM_SLIDER_ITEM_NO_PADDING) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_slider_img_nopadding, parent, false);
            viewHolder = new SliderHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_PARTNER_BRAND){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_PARTNER_BRAND_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.brand_banner_image, parent, false);
            viewHolder = new BrandBannerHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_CUSTOM_SLIDER_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_slider_image, parent, false);
            viewHolder = new SliderHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_MAIN_NAV) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_MAIN_NAV_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_hot_item, parent, false);
            viewHolder = new HotItemHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_MAIN_FLASH_SALE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_MORE_ALL) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_more, parent, false);
            viewHolder = new MoreHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_RECOMMEND_BRAND){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_RECOMMEND_BRAND_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.brand_banner_image, parent, false);
            viewHolder = new BrandBannerHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_UPLOAD_POST_GET_POINT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upload_post_get_point, parent, false);
            viewHolder = new UploadPostGetPointHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_MALL_GRID){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_grid_mall, parent, false);
            viewHolder = new MallGridHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_POINT_DASHBOARD){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_dashboard, parent, false);
            viewHolder = new PointHolder(viewItem);
        }

        return viewHolder;

    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder parentHolder, int position) {
        int type = getItemViewType(position);

        switch (type){
            case VIEW_TYPE_POINT_DASHBOARD: {
                PointHolder pointHolder = (PointHolder) parentHolder;
                MemberBenefitObject memberBenefitObject = (MemberBenefitObject)universalList.get(position);
                if(prefs.getIntPreferences(SP_VIP_ID) == 1){
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                        Drawable wrapDrawable = DrawableCompat.wrap(pointHolder.progress.getIndeterminateDrawable());
                        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(activity.getApplicationContext(), android.R.color.white));
                        pointHolder.progress.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
                    } else {
                        pointHolder.progress.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.custom_purple_deep)));
                        pointHolder.progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity.getApplicationContext(), android.R.color.white), PorterDuff.Mode.SRC_IN);
                    }
                    pointHolder.txtDeadline.setText(memberBenefitObject.getExpired_month() + " " +  resources.getString(R.string.point_left));
                    pointHolder.linearBackground.setBackgroundResource(R.drawable.dashboard_yellow);
                }
                else{
                    pointHolder.linearBackground.setBackgroundResource(R.drawable.dashboard_gray);
                    pointHolder.txtMemberTitle.setTextColor(activity.getResources().getColor(R.color.white));
                    pointHolder.txtWatchMore.setTextColor(activity.getResources().getColor(R.color.white));
                    pointHolder.txtAmount.setTextColor(activity.getResources().getColor(R.color.white));
                    pointHolder.txtBottom.setTextColor(activity.getResources().getColor(R.color.white));



                    pointHolder.txtDeadline.setVisibility(View.GONE);
                    pointHolder.imgHiddenTwo.setVisibility(View.VISIBLE);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                        Drawable wrapDrawable = DrawableCompat.wrap(pointHolder.progress.getIndeterminateDrawable());
                        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(activity.getApplicationContext(), android.R.color.white));
                        pointHolder.progress.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));


                    } else {
                        pointHolder.progress.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.colorSplashScreen)));

                        pointHolder.progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity.getApplicationContext(),  android.R.color.white), PorterDuff.Mode.SRC_IN);
                    }
                    pointHolder.linearBackground.setBackgroundResource(R.drawable.dashboard_gray);

                }



                pointHolder.txtMemberTitle.setText(memberBenefitObject.getTitle());
                String currentAmt = memberBenefitObject.getCurrent_amt() + "/" + memberBenefitObject.getMember_target_amt() ;
                pointHolder.txtAmount.setText(currentAmt + " " + resources.getString(R.string.currency));
                pointHolder.txtPoint.setText(prefs.getIntPreferences(SP_USER_POINT) + " ");
                pointHolder.txtBottom.setText(memberBenefitObject.getDescription());
                pointHolder.txtWatchMore.setText(resources.getString(R.string.more_items));

                int progressValue = ( memberBenefitObject.getCurrent_amt() * 100 ) / memberBenefitObject.getMember_target_amt();
                pointHolder.progress.setProgress(progressValue);

                pointHolder.txtWatchMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, PointDashboardActivity.class);
                        Bundle b = new Bundle();
                        b.putParcelable("member_benefit", memberBenefitObject);
                        intent.putExtras(b);

                        activity.startActivity(intent);
                    }
                });

                break;
            }
            case VIEW_TYPE_MALL_GRID : {
                ////
                MallGridHolder mallGridHolder = (MallGridHolder) parentHolder;
                MainMallGrid mainMallGrid = (MainMallGrid) universalList.get(position);

                int width = display.getWidth() * 19 / 20 ;


                switch (mainMallGrid.getMallGridObjectList().size()){
                    case 1 : {
                        mallGridHolder.layoutForTwo.setVisibility(View.GONE);
                        mallGridHolder.layoutForFour.setVisibility(View.GONE);
                        mallGridHolder.imgOne.setImageUrl(mainMallGrid.getMallGridObjectList().get(0).getUrl(), imageLoader);
                        mallGridHolder.imgOne.getLayoutParams().width = width;
                        mallGridHolder.imgOne.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(0).getDimen() * width) / 100;
                        break;
                    }
                    case 2 : {
                        int twoWidth = (int) (width * 2.4) / 5 ;
                        mallGridHolder.layoutForFour.setVisibility(View.GONE);
                        mallGridHolder.imgOne.setImageUrl(mainMallGrid.getMallGridObjectList().get(0).getUrl(), imageLoader);
                        mallGridHolder.imgOne.getLayoutParams().width =   twoWidth;
                        mallGridHolder.imgOne.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(0).getDimen() * twoWidth) / 100;


                        mallGridHolder.imgTwo.setImageUrl(mainMallGrid.getMallGridObjectList().get(1).getUrl(), imageLoader);
                        mallGridHolder.imgTwo.getLayoutParams().width =  (int) (twoWidth * 2.4) / 5 ;
                        mallGridHolder.imgTwo.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(1).getDimen() * twoWidth) / 100;

                        mallGridHolder.imgThree.setVisibility(View.GONE);
                        break;
                    }
                    case 3 : {
                        int threeWidth = (int) (width * 2.4) / 5 ;
                        mallGridHolder.layoutForFour.setVisibility(View.GONE);
                        mallGridHolder.imgOne.setImageUrl(mainMallGrid.getMallGridObjectList().get(0).getUrl(), imageLoader);
                        mallGridHolder.imgOne.getLayoutParams().width =  threeWidth;
                        mallGridHolder.imgOne.getLayoutParams().height = ( (mainMallGrid.getMallGridObjectList().get(0).getDimen() * threeWidth) / 100 ) + 15;


                        mallGridHolder.imgTwo.setImageUrl(mainMallGrid.getMallGridObjectList().get(1).getUrl(), imageLoader);
                        mallGridHolder.imgTwo.getLayoutParams().width =  threeWidth ;
                        mallGridHolder.imgTwo.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(1).getDimen() * threeWidth) / 100;



                        mallGridHolder.imgThree.setImageUrl(mainMallGrid.getMallGridObjectList().get(2).getUrl(), imageLoader);
                        mallGridHolder.imgThree.getLayoutParams().width =  threeWidth;
                        mallGridHolder.imgThree.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(2).getDimen() * threeWidth) / 100;

                        break;
                    }
                    case 4 : {
                        int fourOneWidth = (int) (width * 2.4) / 5 ;
                        mallGridHolder.imgFive.setVisibility(View.GONE);
                        mallGridHolder.imgOne.setImageUrl(mainMallGrid.getMallGridObjectList().get(0).getUrl(), imageLoader);
                        mallGridHolder.imgOne.getLayoutParams().width =  fourOneWidth ;
                        mallGridHolder.imgOne.getLayoutParams().height = ( (mainMallGrid.getMallGridObjectList().get(0).getDimen() * fourOneWidth) / 100 ) + 15;


                        mallGridHolder.imgTwo.setImageUrl(mainMallGrid.getMallGridObjectList().get(1).getUrl(), imageLoader);
                        mallGridHolder.imgTwo.getLayoutParams().width =  fourOneWidth ;
                        mallGridHolder.imgTwo.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(1).getDimen() * fourOneWidth) / 100;


                        mallGridHolder.imgThree.setImageUrl(mainMallGrid.getMallGridObjectList().get(2).getUrl(), imageLoader);
                        mallGridHolder.imgThree.getLayoutParams().width =  fourOneWidth ;
                        mallGridHolder.imgThree.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(2).getDimen() * fourOneWidth) / 100;


                        mallGridHolder.imgFour.setImageUrl(mainMallGrid.getMallGridObjectList().get(3).getUrl(), imageLoader);
                        mallGridHolder.imgFour.getLayoutParams().width =  width ;
                        mallGridHolder.imgFour.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(3).getDimen() * width) / 100;

                        break;
                    }
                    case 5 : {
                        int fiveOneWidth = (int) (width * 2.4) / 5 ;
                        mallGridHolder.imgOne.setImageUrl(mainMallGrid.getMallGridObjectList().get(0).getUrl(), imageLoader);
                        mallGridHolder.imgOne.getLayoutParams().width =  fiveOneWidth;
                        mallGridHolder.imgOne.getLayoutParams().height = ( (mainMallGrid.getMallGridObjectList().get(0).getDimen() * fiveOneWidth) / 100 ) + 15;


                        mallGridHolder.imgTwo.setImageUrl(mainMallGrid.getMallGridObjectList().get(1).getUrl(), imageLoader);
                        mallGridHolder.imgTwo.getLayoutParams().width =  fiveOneWidth;
                        mallGridHolder.imgTwo.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(1).getDimen() * fiveOneWidth) / 100;


                        mallGridHolder.imgThree.setImageUrl(mainMallGrid.getMallGridObjectList().get(2).getUrl(), imageLoader);
                        mallGridHolder.imgThree.getLayoutParams().width =  fiveOneWidth ;
                        mallGridHolder.imgThree.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(2).getDimen() * fiveOneWidth) / 100;


                        mallGridHolder.imgFour.setImageUrl(mainMallGrid.getMallGridObjectList().get(3).getUrl(), imageLoader);
                        mallGridHolder.imgFour.getLayoutParams().width =  fiveOneWidth ;
                        mallGridHolder.imgFour.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(3).getDimen() * fiveOneWidth) / 100;


                        mallGridHolder.imgFive.setImageUrl(mainMallGrid.getMallGridObjectList().get(4).getUrl(), imageLoader);
                        mallGridHolder.imgFive.getLayoutParams().width =   fiveOneWidth;
                        mallGridHolder.imgFive.getLayoutParams().height = (mainMallGrid.getMallGridObjectList().get(4).getDimen() * fiveOneWidth) / 100;

                        break;


                    }
                }
                mallGridHolder.imgOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainMallGrid.getMallGridObjectList().size() > 1 ){
                            Log.e(TAG, "onClick: "  + mainMallGrid.getMallGridObjectList().get(0).getType() );
                            goToNextIntent(mainMallGrid.getMallGridObjectList().get(0).getType());


                        }
                    }
                });
                mallGridHolder.imgTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainMallGrid.getMallGridObjectList().size() >= 2){
                            Log.e(TAG, "onClick: "  + mainMallGrid.getMallGridObjectList().get(1).getType() );
                            goToNextIntent(mainMallGrid.getMallGridObjectList().get(1).getType());

                        }
                    }
                });
                mallGridHolder.imgThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainMallGrid.getMallGridObjectList().size() >= 3 ){
                            Log.e(TAG, "onClick: "  + mainMallGrid.getMallGridObjectList().get(2).getType() );
                            goToNextIntent(mainMallGrid.getMallGridObjectList().get(2).getType());

                        }
                    }
                });
                mallGridHolder.imgFour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainMallGrid.getMallGridObjectList().size() >= 4 ){
                            Log.e(TAG, "onClick: "  + mainMallGrid.getMallGridObjectList().get(3).getType() );
                            goToNextIntent(mainMallGrid.getMallGridObjectList().get(3).getType());

                        }
                    }
                });


                mallGridHolder.imgFive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainMallGrid.getMallGridObjectList().size() == 5){
                            Log.e(TAG, "onClick: "  + mainMallGrid.getMallGridObjectList().get(4).getType() );
                            goToNextIntent(mainMallGrid.getMallGridObjectList().get(4).getType());
                        }
                    }
                });



                break;
            }
             case VIEW_TYPE_ORDER_PENDING_TRACK:{
                OrderTrackHolder orderTrackHolder = (OrderTrackHolder) parentHolder;
                Order orderTrack = (Order) universalList.get(position);

                if (orderTrack.getPendingCount() > 1){
                    orderTrackHolder.linearOrder.setVisibility(View.VISIBLE);
                    orderTrackHolder.txtCount.setText(orderTrack.getPendingCount()+"");
                    orderTrackHolder.countTitle.setText(resources.getString(R.string.order_count));

                }

                orderTrackHolder.orderTitle.setText(resources.getString(R.string.order_Title));
                orderTrackHolder.confirmTitle.setText(resources.getString(R.string.confirm_Title));
                orderTrackHolder.readyTitle.setText(resources.getString(R.string.ready_Title));
                orderTrackHolder.deliverTitle.setText(resources.getString(R.string.deliver_Title));

                orderTrackHolder.txtViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "profile_pending_order");
                            FlurryAgent.logEvent("Click Pending Orders", mix);
                        } catch (Exception e) {
                        }

                        Intent intent1 = new Intent(activity, OlderedActivity.class);
                        intent1.putExtra("type", "pending");
                        intent1.putExtra("title",resources.getString(R.string.current_order) );
                        activity.startActivity(intent1);
                    }
                });

                if (orderTrack.getStatus().equals("ordered")){
                    orderTrackHolder.hide_order_place.setVisibility(View.VISIBLE);
                    orderTrackHolder.linearOrderConfirmed.setAlpha(0.5f);
                    orderTrackHolder.linearOrderReady.setAlpha(0.5f);
                    orderTrackHolder.linearOrderDelivery.setAlpha(0.5f);
                }
                else if (orderTrack.getStatus().equals("confirmed") || orderTrack.getStatus().equals("voucher_issued")){
                    orderTrackHolder.hide_order_confirm.setVisibility(View.VISIBLE);
                    orderTrackHolder.linearOrderReady.setAlpha(0.5f);
                    orderTrackHolder.linearOrderDelivery.setAlpha(0.5f);


                }
                else if (orderTrack.getStatus().equals("ready_for_delivery")){
                    orderTrackHolder.hide_order_ready.setVisibility(View.VISIBLE);
                    orderTrackHolder.linearOrderDelivery.setAlpha(0.5f);
                }
                else if (orderTrack.getStatus().equals("on_route")){
                    orderTrackHolder.hide_order_deliver.setVisibility(View.VISIBLE);

                }

                orderTrackHolder.txtTotal.setText(formatter.format(  orderTrack.getTotal_price() ) +" "+resources.getString(R.string.currency));

                if (!orderTrack.getPayment_method().equals("cod")){
                    orderTrackHolder.linear_payment.setVisibility(View.VISIBLE);

                    orderTrackHolder.imgPayment.setImageUrl(orderTrack.getPayment_icon(), imageLoader);

                    orderTrackHolder.txtPayName.setText(orderTrack.getPayment_name() + " Payment");
                    orderTrackHolder.txtPayName.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                    orderTrackHolder.txtTotal.setTypeface(orderTrackHolder.txtTotal.getTypeface(), Typeface.BOLD);
                    orderTrackHolder.txtPayName.setTypeface(orderTrackHolder.txtPayName.getTypeface(), Typeface.BOLD);
                    orderTrackHolder.txtStatus.setTypeface(orderTrackHolder.txtStatus.getTypeface(), Typeface.BOLD);
                    if (orderTrack.getPayment_status().equals("waiting_for_payment")){
                        orderTrackHolder.txtStatus.setText("PENDING PAYMENT");

                    }
                    else
                        orderTrackHolder.txtStatus.setText("PAID");

                    orderTrackHolder.txtStatus.setText(" ");


                    // orderTrackHolder.txtPayName.setVisibility(View.GONE);


                }
                else
                    orderTrackHolder.linear_payment.setVisibility(View.GONE);

                orderTrackHolder.linearOrderStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (orderTrack.getStatus().equals("completed") || orderTrack.getStatus().equals("delivered")){
                            feedBackDialog(orderTrack, position, false);
                        }
                        else{
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("order_id",String.valueOf(orderTrack.getOrder_id()));
                                FlurryAgent.logEvent("Click Past Order", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, OrderDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("order", orderTrack);

                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    }
                });

                break;

            }

            case VIEW_TYPE_ORDER_PENDING :{

                final OrderPendingHolder orderPendingHolder = (OrderPendingHolder) parentHolder;
                final Order orderPendingObj = (Order) universalList.get(position);

                if (orderPendingObj.getAddress() != null && orderPendingObj.getAddress().trim().length() > 0){
                    orderPendingHolder.img.setImageUrl(orderPendingObj.getPhoto_url(), imageLoader);
                    orderPendingHolder.img.getLayoutParams().height = (orderPendingObj.getDimen() * display.getWidth()) / 100;
                }

                String slot = "";

                if (orderPendingObj.getDelivery_date() != null && orderPendingObj.getDelivery_date().trim().length() > 0){
                    slot = orderPendingObj.getDelivery_date();
                }
                else if (orderPendingObj.getCompleted_at() != null && orderPendingObj.getCompleted_at().trim().length() > 0){
                    slot = orderPendingObj.getCompleted_at();
                }

                if (orderPendingObj.getDelivery_slot() != null && orderPendingObj.getDelivery_slot().trim().length() > 0){
                    if (orderPendingObj.getDelivery_slot().equals("from_9_12")){
                        slot = slot + " ( " +  resources.getString(R.string.nine_am) + " )";
                    }else if (orderPendingObj.getDelivery_slot().equals("from_12_3")){
                        slot =  slot + " ( " +resources.getString(R.string.twelve_am)+ " )";
                    }else if (orderPendingObj.getDelivery_slot().equals("from_3_6")){
                        slot = slot + " ( " +resources.getString(R.string.three_pm)+ " )";
                    }else if (orderPendingObj.getDelivery_slot().equals("from_6_9")){
                        slot =  slot +" ( " + resources.getString(R.string.six_pm)+ " )";
                    }
                }




                if (orderPendingObj.getStatus().equals("ordered")){
                    orderPendingHolder.txtDetail.setText("( Voucher No - " + orderPendingObj.getVoucher_number() + " ) " + resources.getString(R.string.order_ordered));
                }
                else if (orderPendingObj.getStatus().equals("confirmed") || orderPendingObj.getStatus().equals("voucher_issued")){
                    orderPendingHolder.txtDetail.setText("( Voucher No - " + orderPendingObj.getVoucher_number() + " ) " +String.format(resources.getString(R.string.order_confirmed) , slot));
                }
                else if (orderPendingObj.getStatus().equals("ready_for_delivery")){
                    orderPendingHolder.txtDetail.setText("( Voucher No - " + orderPendingObj.getVoucher_number() + " ) " +String.format(resources.getString(R.string.order_ready_for_delivery) , slot));
                }
                else if (orderPendingObj.getStatus().equals("on_route")){
                    orderPendingHolder.txtDetail.setText("( Voucher No - " + orderPendingObj.getVoucher_number() + " ) " +String.format(resources.getString(R.string.order_on_route) , slot));
                }else{
                    orderPendingHolder.txtDetail.setVisibility(View.GONE);
                }


                orderPendingHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (orderPendingObj.getStatus().equals("completed") || orderPendingObj.getStatus().equals("delivered")){
                            feedBackDialog(orderPendingObj, position, false);
                        }
                        else{
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("order_id",String.valueOf(orderPendingObj.getOrder_id()));
                                FlurryAgent.logEvent("Click Past Order", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, OrderDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("order", orderPendingObj);

                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    }
                });

                break;
            }

            case VIEW_TYPE_STAGGERED_ITEM : {
                boolean hideDiscount = false;
                boolean hideDiscountTwo = false;
                int percentage1 = 0;
                int percentageTwo = 0;

                int backgroundCheck = 0;
                int backgroundCheckTwo = 0;


                final ProductItemHolder catDetailHolder = (ProductItemHolder) parentHolder;
                OrderDetailsObj orderDetailsObj = (OrderDetailsObj) universalList.get(position);

                catDetailHolder.linearMainTwo.getLayoutParams().width  = (int) ( display.getWidth()  /2.1);
                catDetailHolder.gridItemImageViewTwo.getLayoutParams().width  = display.getWidth()  / 3 ;
                catDetailHolder.gridItemImageViewTwo.getLayoutParams().height  = display.getWidth() / 3  ;

                catDetailHolder.linearMain.getLayoutParams().width  = (int) ( display.getWidth()  /2.1);
                catDetailHolder.gridItemImageView.getLayoutParams().width  = display.getWidth()  / 3 ;
                catDetailHolder.gridItemImageView.getLayoutParams().height  = display.getWidth() / 3  ;

                catDetailHolder.price_strike.setText("");
                catDetailHolder.price_strikeTwo.setText("");



                catDetailHolder.linearMainTwo.setVisibility(View.GONE);
                catDetailHolder.sold_out.setVisibility(View.GONE);
                catDetailHolder.sold_outTwo.setVisibility(View.GONE);


                if (orderDetailsObj.getProductList().size() > 1){
                    catDetailHolder.linearMainTwo.setVisibility(View.VISIBLE);
                    catDetailHolder.cardTwo.setVisibility(View.VISIBLE);
                    final Product detailProductTwo = orderDetailsObj.getProductList().get(1);


                    catDetailHolder.txtTitleTwo.setText(detailProductTwo.getTitle());
                    catDetailHolder.priceTwo.setText(formatter.format(detailProductTwo.getPrice()) +" "+activity.getResources().getString(R.string.currency));


                    // catDetailHolder.txt_one_day.setText(resources.getString(R.string.delivery_take_time));
                    catDetailHolder.priceTwo.setTypeface(catDetailHolder.priceTwo.getTypeface(), Typeface.BOLD);

                    int checkProductWarning1Two = 0;


                    if (detailProductTwo.getChannel().equals("all_channels")  &&  detailProductTwo.getRecommended().trim().length() == 0 ){

                        checkProductWarning1Two = 1;
                    }

                    if (checkProductWarning1Two == 1){

                        //catDetailHolder.txt_one_dayTwo.setText(resources.getString(R.string.immediate_delivery));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            catDetailHolder.txt_one_dayTwo.setText(Html.fromHtml(
                                    resources.getString(R.string.one_day_delivery)  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            catDetailHolder.txt_one_dayTwo.setText(Html.fromHtml(resources.getString(R.string.one_day_delivery)));
                        }

                        catDetailHolder.img_one_dayTwo.setVisibility(View.GONE);
                        catDetailHolder.img_one_todayTwo.setVisibility(View.VISIBLE);

                    }else{
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            catDetailHolder.txt_one_dayTwo.setText(Html.fromHtml(
                                    resources.getString(R.string.option_day_delivery)  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            catDetailHolder.txt_one_dayTwo.setText(Html.fromHtml(resources.getString(R.string.option_day_delivery)));
                        }
                        catDetailHolder.img_one_dayTwo.setVisibility(View.VISIBLE);
                        catDetailHolder.img_one_todayTwo.setVisibility(View.GONE);
                    }

                    if(detailProductTwo.getRecommended() != null &&  detailProductTwo.getRecommended().trim().length() > 0){
                        catDetailHolder.sold_outTwo.setVisibility(View.VISIBLE);


                    }
                    else  if(detailProductTwo.getChannel() != null && detailProductTwo.getChannel().trim().length() > 0){


                        if (!detailProductTwo.getChannel().equals("all_channels")){

                            catDetailHolder.pre_orderTwo.setTypeface(catDetailHolder.pre_order.getTypeface(), Typeface.BOLD);
                            catDetailHolder.pre_orderTwo.setVisibility(View.VISIBLE);
                        }
                        else{
                            catDetailHolder.pre_orderTwo.setVisibility(View.GONE);
                        }
                    }else {
                        catDetailHolder.pre_orderTwo.setVisibility(View.GONE);
                    }




                    if (detailProductTwo.getPreviewImage() != null && !(detailProductTwo.getPreviewImage().equals(""))) {
                        catDetailHolder.gridItemImageViewTwo.setImageUrl(detailProductTwo.getPreviewImage(), AppController.getInstance().getImageLoader());

                    }



                    boolean flashSaleCheck1Two = false;

                    if (detailProductTwo.getFlashSalesArrayList().size() > 0) {
                        final Flash_Sales flash_salesTwo = detailProductTwo.getFlashSalesArrayList().get(0);


                        Date currentDate1 = new Date();
                        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                        // Please here set your event date//YYYY-MM-DD
                        Date futureDate1 = null;
                        try {
                            futureDate1 = df1.parse(flash_salesTwo.getEnd_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                        }

                        if (!currentDate1.after(futureDate1)) {

                            if (flash_salesTwo.getReserved_quantity() < flash_salesTwo.getAvailable_quantity())
                                flashSaleCheck1Two = true;
                        }
                    }

                    int cat_total_discountTwo = 0;
                    if (!flashSaleCheck1Two){

                        if(detailProductTwo.getDiscounts().size() > 0) {
                            for (int k = 0; k < detailProductTwo.getDiscounts().size(); k++) {


                                Discount dis = (Discount) detailProductTwo.getDiscounts().get(k);
                                String str = dis.getDiscountType();


                                if (dis.getMember_only() == 1) {
                                    if (prefs.getIntPreferences(SP_VIP_ID) == 1) {
                                        switch (str) {
                                            case DISCOUNT_PERCENTAGE:
                                                if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                                    cat_total_discountTwo = (detailProductTwo.getPrice() * dis.getPercentage()) / 100;
                                                }
                                                break;

                                            default:
                                                break;
                                        }
                                    }
                                } else {
                                    switch (str) {
                                        case DISCOUNT_PERCENTAGE:
                                            if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                                cat_total_discountTwo = (detailProductTwo.getPrice() * dis.getPercentage()) / 100;
                                            }
                                            break;
                                    }
                                }
                            }


                            if(detailProductTwo.getDiscounts().size() > 0){
                                String str = "";
                                for(int i = 1; i <= detailProductTwo.getDiscounts().size(); i++){
                                    Discount disTwo = (Discount) detailProductTwo.getDiscounts().get(i-1);
                                    if(disTwo.getMember_only() == 1){
                                        if(prefs.getIntPreferences(SP_VIP_ID) != 0){
                                            if(disTwo.getDiscountType().equals(DISCOUNT_PERCENTAGE)){
                                                if(disTwo.getPercentage() > percentageTwo)
                                                    percentageTwo = disTwo.getPercentage();
                                            }else if(disTwo.getDiscountType().equals(DISCOUNT_GIFT)){

                                                str = str.concat(disTwo.getCampaign_info());

                                            }
                                        }
                                        if(str.trim().length() > 0){
                                            hideDiscountTwo = true;



                                            catDetailHolder.presentTwo.setVisibility(View.VISIBLE);
                                            //  catDetailHolder.present.setText("Present");
                                        }
                                    }else {
                                        if(disTwo.getDiscountType().equals(DISCOUNT_PERCENTAGE)){
                                            if(disTwo.getPercentage() > percentageTwo)
                                                percentageTwo = disTwo.getPercentage();
                                        }else if(disTwo.getDiscountType().equals(DISCOUNT_GIFT)){
                                            str = str.concat(disTwo.getCampaign_info());
                                        }


                                        if(str.trim().length() > 0){
                                            catDetailHolder.presentTwo.setVisibility(View.VISIBLE);

                                            //   catDetailHolder.present.setText("Present");
                                            hideDiscountTwo = true;
                                        }else{
                                            hideDiscountTwo = false;

                                            catDetailHolder.presentTwo.setVisibility(View.GONE);
                                        }
                                    }

                                }



                            }else{

                                catDetailHolder.discountTwo.setVisibility(View.GONE);
                            }


                        }


                    }
                    else{

                        percentageTwo = detailProductTwo.getFlashSalesArrayList().get(0).getDiscount();
                        cat_total_discountTwo =  ( detailProductTwo.getPrice() * detailProductTwo.getFlashSalesArrayList().get(0).getDiscount()) / 100 ;
                    }


                    if(detailProductTwo.getMember_discount() != 0 && prefs.getIntPreferences(SP_VIP_ID) != 0 ) {
                        cat_total_discountTwo += ((detailProductTwo.getPrice() - cat_total_discountTwo)*
                                prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) / 100);
                    }



                    if(percentageTwo != 0 && flashSaleCheck1Two == false ) {

                        backgroundCheckTwo = backgroundCheckTwo + 1;
                        catDetailHolder.discountTwo.setVisibility(View.VISIBLE);
                        catDetailHolder.discountTwo.setText( percentageTwo + "%");
                        catDetailHolder.discount_hiddenTwo.setText( percentageTwo + "%");

                        catDetailHolder.priceTwo.setText(formatter.format(detailProductTwo.getPrice() - (detailProductTwo.getPrice() * percentageTwo) / 100 )+
                                " "+resources.getString(R.string.currency));
                        catDetailHolder.price_strikeTwo.setText(formatter.format(detailProductTwo.getPrice()  )+
                                " "+resources.getString(R.string.currency));

                        catDetailHolder.price_strikeTwo.setPaintFlags(catDetailHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        catDetailHolder.price_strikeTwo.setVisibility(View.VISIBLE);

                    }
                    else{
                        catDetailHolder.discountTwo.setVisibility(View.GONE);
                        catDetailHolder.price_strikeTwo.setVisibility(View.GONE);

                    }



                    if(hideDiscountTwo) {
                        backgroundCheckTwo = backgroundCheckTwo + 1;
                        catDetailHolder.presentTwo.setVisibility(View.VISIBLE);

                    }
                    else
                        catDetailHolder.presentTwo.setVisibility(View.GONE);




                    if (backgroundCheckTwo == 0){
                        catDetailHolder.linearHiddenTwo.setVisibility(View.GONE);
                        catDetailHolder.discountTwo.setVisibility(View.GONE);
                        catDetailHolder.presentTwo.setVisibility(View.GONE);
                    }
                    else if(backgroundCheckTwo > 1){
                        catDetailHolder.discountTwo.setVisibility(View.GONE);
                        catDetailHolder.presentTwo.setVisibility(View.GONE);
                        catDetailHolder.linearHiddenTwo.setVisibility(View.VISIBLE);
                    }
                    else{
                        catDetailHolder.linearHiddenTwo.setVisibility(View.GONE);
                    }

                    catDetailHolder.linearMainTwo.setOnClickListener(new OnClickProduct(activity, detailProductTwo));
                }

                final Product detailProduct = orderDetailsObj.getProductList().get(0);
                catDetailHolder.linearMain.setVisibility(View.VISIBLE);



                catDetailHolder.txtTitle.setText(detailProduct.getTitle());
                catDetailHolder.price.setText(formatter.format(detailProduct.getPrice()) +" "+activity.getResources().getString(R.string.currency));


                // catDetailHolder.txt_one_day.setText(resources.getString(R.string.delivery_take_time));
                catDetailHolder.price.setTypeface(catDetailHolder.txt_one_day.getTypeface(), Typeface.BOLD);

                int checkProductWarning1 = 0;
                if (detailProduct.getChannel().equals("all_channels")  &&  detailProduct.getRecommended().trim().length() == 0 ){

                    checkProductWarning1 = 1;
                }



                if (checkProductWarning1 == 1){
                    //catDetailHolder.txt_one_day.setText(resources.getString(R.string.immediate_delivery));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        catDetailHolder.txt_one_day.setText(Html.fromHtml(
                                resources.getString(R.string.one_day_delivery)  , Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        catDetailHolder.txt_one_day.setText(Html.fromHtml(resources.getString(R.string.one_day_delivery)));
                    }
                    catDetailHolder.img_one_day.setVisibility(View.GONE);
                    catDetailHolder.img_one_today.setVisibility(View.VISIBLE);

                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        catDetailHolder.txt_one_day.setText(Html.fromHtml(
                                resources.getString(R.string.option_day_delivery)  , Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        catDetailHolder.txt_one_day.setText(Html.fromHtml(resources.getString(R.string.option_day_delivery)));
                    }
                    catDetailHolder.img_one_day.setVisibility(View.VISIBLE);
                    catDetailHolder.img_one_today.setVisibility(View.GONE);
                }

                if(detailProduct.getRecommended() != null &&  detailProduct.getRecommended().trim().length() > 0){
                    catDetailHolder.sold_out.setVisibility(View.VISIBLE);


                }
                else  if(detailProduct.getChannel() != null && detailProduct.getChannel().trim().length() > 0){


                    if (!detailProduct.getChannel().equals("all_channels")){

                        catDetailHolder.pre_order.setTypeface(catDetailHolder.pre_order.getTypeface(), Typeface.BOLD);
                        catDetailHolder.pre_order.setVisibility(View.VISIBLE);

                    }
                    else{
                        catDetailHolder.pre_order.setVisibility(View.GONE);
                    }
                }else {
                    catDetailHolder.pre_order.setVisibility(View.GONE);
                }




                if (detailProduct.getPreviewImage() != null && !(detailProduct.getPreviewImage().equals(""))) {
                    catDetailHolder.gridItemImageView.setImageUrl(detailProduct.getPreviewImage(), AppController.getInstance().getImageLoader());

                }



                boolean flashSaleCheck1 = false;

                if (detailProduct.getFlashSalesArrayList().size() > 0) {
                    final Flash_Sales flash_sales = detailProduct.getFlashSalesArrayList().get(0);


                    Date currentDate1 = new Date();
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate1 = null;
                    try {
                        futureDate1 = df1.parse(flash_sales.getEnd_date());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                    }

                    if (!currentDate1.after(futureDate1)) {

                        if (flash_sales.getReserved_quantity() < flash_sales.getAvailable_quantity())
                            flashSaleCheck1 = true;
                    }
                }

                int cat_total_discount = 0;
                if (!flashSaleCheck1){

                    if(detailProduct.getDiscounts().size() > 0) {
                        for (int k = 0; k < detailProduct.getDiscounts().size(); k++) {


                            Discount dis = (Discount) detailProduct.getDiscounts().get(k);
                            String str = dis.getDiscountType();


                            if (dis.getMember_only() == 1) {
                                if (prefs.getIntPreferences(SP_VIP_ID) == 1) {
                                    switch (str) {
                                        case DISCOUNT_PERCENTAGE:
                                            if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                                cat_total_discount = (detailProduct.getPrice() * dis.getPercentage()) / 100;
                                            }
                                            break;

                                        default:
                                            break;
                                    }
                                }
                            } else {
                                switch (str) {
                                    case DISCOUNT_PERCENTAGE:
                                        if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {
                                            cat_total_discount = (detailProduct.getPrice() * dis.getPercentage()) / 100;
                                        }
                                        break;
                                }
                            }
                        }


                        if(detailProduct.getDiscounts().size() > 0){
                            String str = "";
                            for(int i = 1; i <= detailProduct.getDiscounts().size(); i++){
                                Discount dis = (Discount) detailProduct.getDiscounts().get(i-1);
                                if(dis.getMember_only() == 1){
                                    if(prefs.getIntPreferences(SP_VIP_ID) != 0){
                                        if(dis.getDiscountType().equals(DISCOUNT_PERCENTAGE)){
                                            if(dis.getPercentage() > percentage1)
                                                percentage1 = dis.getPercentage();
                                        }else if(dis.getDiscountType().equals(DISCOUNT_GIFT)){

                                            str = str.concat(dis.getCampaign_info());

                                        }
                                    }
                                    if(str.trim().length() > 0){
                                        hideDiscount = true;

                                        catDetailHolder.present.setVisibility(View.VISIBLE);
                                        //  catDetailHolder.present.setText("Present");
                                    }
                                }else {
                                    if(dis.getDiscountType().equals(DISCOUNT_PERCENTAGE)){
                                        if(dis.getPercentage() > percentage1)
                                            percentage1 = dis.getPercentage();
                                    }else if(dis.getDiscountType().equals(DISCOUNT_GIFT)){
                                        str = str.concat(dis.getCampaign_info());
                                    }


                                    if(str.trim().length() > 0){
                                        catDetailHolder.present.setVisibility(View.VISIBLE);

                                        //   catDetailHolder.present.setText("Present");
                                        hideDiscount = true;
                                    }else{
                                        hideDiscount = false;

                                        catDetailHolder.present.setVisibility(View.GONE);
                                    }
                                }

                            }



                        }else{

                            catDetailHolder.discount.setVisibility(View.GONE);
                        }


                    }


                }
                else{

                    percentage1 = detailProduct.getFlashSalesArrayList().get(0).getDiscount();
                    cat_total_discount =  ( detailProduct.getPrice() * detailProduct.getFlashSalesArrayList().get(0).getDiscount()) / 100 ;
                }


                if(detailProduct.getMember_discount() != 0 && prefs.getIntPreferences(SP_VIP_ID) != 0 ) {
                    cat_total_discount += ((detailProduct.getPrice() - cat_total_discount)*
                            prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) / 100);
                }



                if(percentage1 != 0 && flashSaleCheck1 == false ) {

                    backgroundCheck = backgroundCheck + 1;
                    catDetailHolder.discount.setVisibility(View.VISIBLE);
                    catDetailHolder.discount.setText( percentage1 + "%");
                    catDetailHolder.discount_hidden.setText( percentage1 + "%");

                    catDetailHolder.price.setText(formatter.format(detailProduct.getPrice() - (detailProduct.getPrice() * percentage1) / 100 )+
                            " "+resources.getString(R.string.currency));
                    catDetailHolder.price_strike.setText(formatter.format(detailProduct.getPrice()  )+
                            " "+resources.getString(R.string.currency));

                    catDetailHolder.price_strike.setPaintFlags(catDetailHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    catDetailHolder.price_strike.setVisibility(View.VISIBLE);

                }
                else{
                    catDetailHolder.discount.setVisibility(View.GONE);
                    catDetailHolder.price_strike.setVisibility(View.GONE);

                }



                if(hideDiscount) {
                    backgroundCheck = backgroundCheck + 1;
                    catDetailHolder.present.setVisibility(View.VISIBLE);

                }
                else
                    catDetailHolder.present.setVisibility(View.GONE);




                if (backgroundCheck == 0){
                    catDetailHolder.linearHidden.setVisibility(View.GONE);
                    catDetailHolder.discount.setVisibility(View.GONE);
                    catDetailHolder.present.setVisibility(View.GONE);
                }
                else if(backgroundCheck > 1){
                    catDetailHolder.discount.setVisibility(View.GONE);
                    catDetailHolder.present.setVisibility(View.GONE);
                    catDetailHolder.linearHidden.setVisibility(View.VISIBLE);
                }
                else{
                    catDetailHolder.linearHidden.setVisibility(View.GONE);
                }

                catDetailHolder.linearMain.setOnClickListener(new OnClickProduct(activity, detailProduct));


                break;
            }

            case VIEW_TYPE_UPLOAD_POST_GET_POINT:{
                final UploadPostGetPointHolder uploadPostGetPointHolder   = (UploadPostGetPointHolder) parentHolder;
                final Reading getPoint = (Reading) universalList.get(position);
                uploadPostGetPointHolder.image.setImageUrl(getPoint.getPreview_image_url(), imageLoader);
                uploadPostGetPointHolder.image.getLayoutParams().height  = (display.getWidth() * getPoint.getDimen()) / 100;
                uploadPostGetPointHolder.image.getLayoutParams().width   = display.getWidth();
                uploadPostGetPointHolder.layout.setBackgroundColor(activity.getResources().getColor(R.color.background));
                uploadPostGetPointHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getPoint.getPostType().equals(signup)){
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                            //activity.overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);
                        }
                       /* else if(getPoint.getPostType().equals(invite)){
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            Intent intent = new Intent(activity, InviteFriendActivity.class);
                            intent.putExtra("source", "fragment_reading");
                            activity.startActivity(intent);
                        }else if(getPoint.getPostType().equals(feedback)){
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            //sendFriendFeedback();
                        }else{
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }*/


                    }
                });

                break;

            }


            case VIEW_TYPE_NEW_ARRIVAL_ITEM :{
                NewArrivalHolder newArrivalHolder = (NewArrivalHolder) parentHolder;
                Product product = (Product) universalList.get(position);

                newArrivalHolder.img.setImageUrl(product.getPreviewImage(), imageLoader);
                newArrivalHolder.title.setText(product.getTitle());
                newArrivalHolder.price.setText(formatter.format(product.getPrice()) +" "+ resources.getString(R.string.currency));
                newArrivalHolder.price_strike.setText("");
                newArrivalHolder.price.setTypeface(newArrivalHolder.price.getTypeface(), Typeface.BOLD);
                newArrivalHolder.price_strike.setPaintFlags(newArrivalHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                newArrivalHolder.linear.getLayoutParams().width  =  display.getWidth()  / 3 ;
                newArrivalHolder.relative.getLayoutParams().width  =  display.getWidth()  * 2/ 7 ;
                newArrivalHolder.relative.getLayoutParams().height  =  display.getWidth()  * 2/ 7 ;

                newArrivalHolder.txtInfo.setVisibility(View.GONE);

                if (product.getFlashSalesArrayList().size() > 0) {
                    Flash_Sales flash_sales = product.getFlashSalesArrayList().get(0);
                    DateFormat df8 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                    Date futureDate8 = null;
                    try {
                        futureDate8 = df8.parse(flash_sales.getEnd_date());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                    }
                    Date currentDate8 = new Date();
                    if (!currentDate8.after(futureDate8)) {
                        if (flash_sales.getReserved_quantity() < flash_sales.getAvailable_quantity()) {
                            newArrivalHolder.txtInfo.setText(  "-" + flash_sales.getDiscount() + " % ");
                            newArrivalHolder.txtInfo.setVisibility(View.VISIBLE);

                            int flashPercent = 0;
                            flashPercent =  ( product.getPrice() * flash_sales.getDiscount()) / 100 ;

                            newArrivalHolder.price.setText(formatter.format((product.getPrice() - flashPercent))+" "+resources.getString(R.string.currency));
                            newArrivalHolder.price_strike.setText( formatter.format(product.getPrice()) +" "+ resources.getString(R.string.currency));

                        }


                    }
                }
                else
                {

                    if(product.getDiscounts().size() > 0) {

                        for (int k = 0; k < product.getDiscounts().size(); k++) {

                            Discount dis = (Discount) product.getDiscounts().get(k);
                            String str = dis.getDiscountType();

                            int total_discountProudct = 0;

                            if (dis.getMember_only() == 1) {
                                if (prefs.getIntPreferences(SP_VIP_ID) == 1) {
                                    switch (str) {
                                        case DISCOUNT_PERCENTAGE:
                                            if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {

                                                newArrivalHolder.txtInfo.setVisibility(View.VISIBLE);
                                                newArrivalHolder.txtInfo.setText("-" + dis.getPercentage() + " % ");


                                                total_discountProudct = (product.getPrice() * dis.getPercentage()) / 100;

                                                newArrivalHolder.price.setText(formatter.format((product.getPrice() - total_discountProudct)) + " " + resources.getString(R.string.currency));
                                                newArrivalHolder.price_strike.setText(formatter.format(product.getPrice()) + " " + resources.getString(R.string.currency));


                                            }

                                        default:
                                            break;
                                    }
                                }
                            } else {

                                switch (str) {
                                    case DISCOUNT_PERCENTAGE:
                                        if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {

                                            newArrivalHolder.txtInfo.setVisibility(View.VISIBLE);
                                            newArrivalHolder.txtInfo.setText("-" + dis.getPercentage() + " % ");


                                            total_discountProudct = (product.getPrice() * dis.getPercentage()) / 100;

                                            newArrivalHolder.price.setText(formatter.format((product.getPrice() - total_discountProudct)) + " " + resources.getString(R.string.currency));
                                            newArrivalHolder.price_strike.setText(formatter.format(product.getPrice()) + " " + resources.getString(R.string.currency));
                                        }

                                    default:
                                        break;
                                }
                            }
                        }

                    }

                }

                newArrivalHolder.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity, ProductActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("product", product);
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });



                break;
            }

            case VIEW_TYPE_NEW_ARRIVAL :{
                NewArrivalMainHolder newArrivalMainHolder = (NewArrivalMainHolder) parentHolder;
                OrderDetailsObj orderDetailsObj = (OrderDetailsObj) universalList.get(position);

                newArrivalMainHolder.txtNewArrival.setText(orderDetailsObj.getTitle());
                newArrivalMainHolder.txtSeeMore.setText(resources.getString(R.string.see_more));

                ArrayList<UniversalPost> mainGridChoose1 = new ArrayList<>();



                List<Product> moreItemsChoose = orderDetailsObj.getProductList();

                if (moreItemsChoose.size() > 0 ){
                    for(int i = 0 ; i < moreItemsChoose.size() ; i++){
                        Product uni = moreItemsChoose.get(i);
                        uni.setPostType(NEW_ARRIVAL_ITEM);
                        mainGridChoose1.add(uni);
                    }
                    gridAdapter = new MallAdapter(activity, mainGridChoose1);
                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                    if (newArrivalMainHolder.recyclerView != null) {
                        newArrivalMainHolder.recyclerView.setLayoutManager(layoutManager);
                        newArrivalMainHolder.recyclerView.setAdapter(gridAdapter);
                    }

                }
                newArrivalMainHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                newArrivalMainHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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



                newArrivalMainHolder.linearSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, ProductListClickActivity.class);
                        intent.putExtra("title",orderDetailsObj.getTitle());
                        intent.putExtra("url", orderDetailsObj.getUrl());
                        activity.startActivity(intent);

                    }
                });


                break;
            }

            case VIEW_TYPE_REFRESH_FOOTER:{
                final RefreshHolder footerHoloder1 = (RefreshHolder) parentHolder;
                footerHoloder1.refresh.setText("Touch To Refresh");
                footerHoloder1.refresh.setTypeface(footerHoloder1.refresh.getTypeface(), Typeface.BOLD);
                footerHoloder1.refresh.setPaintFlags(footerHoloder1.refresh.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                footerHoloder1.refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (prefs.isNetworkAvailable()){


                            refreshClick(activity);
                        }else{
                            ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));
                        }
                    }
                });
                break;
            }

            case VIEW_TYPE_COLLECTIONS_LIST_ITEM:{
                CampianHolder collectionHolder = (CampianHolder) parentHolder;
                final Campaign collectionObject       = (Campaign) universalList.get(position);

                if(collectionObject.getDimen() > 0){
                    collectionHolder.image.setVisibility(View.VISIBLE);
                    collectionHolder.image.getLayoutParams().width = (display.getWidth() * 2 ) / 3;
                    collectionHolder.image.getLayoutParams().height = (collectionObject.getDimen() *  (display.getWidth() * 2 ) / 3 ) / 100;
                    collectionHolder.image.setImageUrl(collectionObject.getUrl(), imageLoader);
                }else {
                    collectionHolder.image.setVisibility(View.GONE);
                }
                collectionHolder.title.setVisibility(View.GONE);
                collectionHolder.time.setVisibility(View.GONE);
                collectionHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("collection_id", String.valueOf(collectionObject.getId()));
                            FlurryAgent.logEvent("Click Collection List", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, CollectionDetailActivity.class);
                        intent.putExtra("id",collectionObject.getId());
                        intent.putExtra("name",collectionObject.getTitle());
                        activity.startActivity(intent);
                    }
                });

                break;

            }

            case VIEW_TYPE_NEW_COLLECTION_LIST:{
                 NewArrivalMainHolder newCollecitonHolder = (NewArrivalMainHolder) parentHolder;
                ArrayList<UniversalPost> mainCollectsList1 = new ArrayList<>();
                MainObject mainObject1 = (MainObject) universalList.get(position);
                newCollecitonHolder.txtNewArrival.setText(mainObject1.getTitle());
                newCollecitonHolder.txtSeeMore.setText(resources.getString(R.string.see_more));

                List<MainItem> itemsMainItem = new ArrayList<>();
                itemsMainItem = mainObject1.getItems();
                gridAdapter = new MallAdapter(activity, mainCollectsList1);
                layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);

                if (newCollecitonHolder.recyclerView != null) {
                    newCollecitonHolder.recyclerView.setLayoutManager(layoutManager);
                    newCollecitonHolder.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < itemsMainItem.size() ; i++){
                    MainItem uni = itemsMainItem.get(i);
                    Campaign campaign = new Campaign();
                    campaign.setId(uni.getId());
                    campaign.setDimen(uni.getDimen());
                    campaign.setTitle(uni.getTitle());
                    campaign.setUrl(uni.getUrl());
                    campaign.setPostType(uni.getPostType());

                    mainCollectsList1.add(campaign);
                }

                Product moreProduct2 = new Product();
                moreProduct2.setPriId(3);
                moreProduct2.setPostType(MORE_ALL);
                mainCollectsList1.add(moreProduct2);

                newCollecitonHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                newCollecitonHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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


                newCollecitonHolder.linearSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, DiscountActivity.class);
                        intent.putExtra("discount", false);
                        activity.startActivity(intent);

                    }
                });

            }


            case VIEW_TYPE_COLLECTIONS_LIST:{

                NewArrivalMainHolder mainCollectionHolder = (NewArrivalMainHolder) parentHolder;
                ArrayList<UniversalPost> mainCollectsList = new ArrayList<>();
                MainObject obj13 = (MainObject) universalList.get(position);
                mainCollectionHolder.txtNewArrival.setText(obj13.getTitle());
                List<MainItem> items13 = new ArrayList<>();
                items13 = obj13.getItems();
                gridAdapter = new MallAdapter(activity, mainCollectsList);
                layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);

                if (mainCollectionHolder.recyclerView != null) {
                    mainCollectionHolder.recyclerView.setLayoutManager(layoutManager);
                    mainCollectionHolder.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < items13.size() ; i++){
                    MainItem uni = items13.get(i);
                    Campaign campaign = new Campaign();
                    campaign.setId(uni.getId());
                    campaign.setDimen(uni.getDimen());
                    campaign.setTitle(uni.getTitle());
                    campaign.setUrl(uni.getUrl());
                    campaign.setPostType(uni.getPostType());

                    mainCollectsList.add(campaign);
                }

                Product moreProduct2 = new Product();
                moreProduct2.setPriId(3);
                moreProduct2.setPostType(MORE_ALL);
                mainCollectsList.add(moreProduct2);

                mainCollectionHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                mainCollectionHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

                mainCollectionHolder.txtSeeMore.setText(resources.getString(R.string.see_more));
                mainCollectionHolder.txtSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //To updae Discount

                        Intent intent = new Intent(activity, DiscountActivity.class);
                        intent.putExtra("discount", false);
                        activity.startActivity(intent);
                    }
                });

                break;
            }


            case VIEW_TYPE_CATEGORY_BANNER :{
                BannerHolder bannerHolder = (BannerHolder) parentHolder;
                MainCategoryObj categoryMainBanner = (MainCategoryObj) universalList.get(position);

                if (categoryMainBanner.getShowTitle() == 0) {
                    bannerHolder.textTitle.setVisibility(View.GONE);
                    bannerHolder.txtHidden.setVisibility(View.GONE);
                }
                else if (categoryMainBanner.getShowTitle() == 2) {
                    bannerHolder.txtHidden.setVisibility(View.VISIBLE);
                    bannerHolder.textTitle.setVisibility(View.VISIBLE);
                    bannerHolder.textTitle.setText("Our Products");
                    bannerHolder.textTitle.setTypeface( bannerHolder.textTitle.getTypeface(), Typeface.BOLD);

                }else if (categoryMainBanner.getShowTitle() == 1){
                    bannerHolder.txtHidden.setVisibility(View.GONE);
                    bannerHolder.textTitle.setVisibility(View.GONE);
                    bannerHolder.textTitle.setText("");
                    bannerHolder.textTitle.setTypeface( bannerHolder.textTitle.getTypeface(), Typeface.BOLD);
                    bannerHolder.textTitle.setBackgroundColor(activity.getResources().getColor(R.color.background));
                }


                bannerHolder.img.setImageUrl(categoryMainBanner.getHeader_image(), imageLoader);
                bannerHolder.img.getLayoutParams().height = (categoryMainBanner.getHeader_image_dimen() * display.getWidth()) / 100;

                break;
            }

            case VIEW_TYPE_CAMPAIGN:{
                CampianHolder camHolder = (CampianHolder) parentHolder;
                final Campaign campaign       = (Campaign) universalList.get(position);

                if(campaign.getTitle().trim().length() != 0 && campaign.getTitle() != null){
                    camHolder.title.setVisibility(View.VISIBLE);
                    camHolder.title.setText(campaign.getTitle());
                }
                else{
                    camHolder.title.setVisibility(View.GONE);
                }


                if(campaign.getDimen() > 0){
                    camHolder.image.setVisibility(View.VISIBLE);
                    camHolder.image.getLayoutParams().width = (display.getWidth() * 2 ) / 3;
                    camHolder.image.getLayoutParams().height = (campaign.getDimen() *  (display.getWidth() * 2 ) / 3 ) / 100;
                    camHolder.image.setImageUrl(campaign.getUrl(), imageLoader);
                }else {
                    camHolder.image.setVisibility(View.GONE);
                }
                if(campaign.getStart() != null && campaign.getStart().trim().length() > 0){
                    camHolder.time.setVisibility(View.VISIBLE);
                    camHolder.time.setText(campaign.getStart()+" - "+campaign.getEnd());
                }else{
                    camHolder.time.setVisibility(View.GONE);
                }
                camHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("campaign_id", String.valueOf(campaign.getId()));
                            FlurryAgent.logEvent("Click Discount Campaign", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, CampainDetailActivity.class);
                        intent.putExtra("id",campaign.getId());
                        intent.putExtra("name",campaign.getTitle());
                        activity.startActivity(intent);
                    }
                });

                break;

            }

            case VIEW_TYPE_MAIN_DISCOUNT:{

                NewArrivalMainHolder mainDiscoutHolder = (NewArrivalMainHolder) parentHolder;

                ArrayList<UniversalPost> mainGrids1 = new ArrayList<>();
                MainObject obj1 = (MainObject) universalList.get(position);
                mainDiscoutHolder.txtNewArrival.setText(obj1.getTitle());
                mainDiscoutHolder.txtSeeMore.setText(resources.getString(R.string.see_more));
                List<MainItem> items1 = new ArrayList<>();
                items1 = obj1.getItems();
                gridAdapter = new MallAdapter(activity, mainGrids1);
                layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);

                if (mainDiscoutHolder.recyclerView != null) {
                    mainDiscoutHolder.recyclerView.setLayoutManager(layoutManager);
                    mainDiscoutHolder.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < items1.size() ; i++){
                    MainItem uni = items1.get(i);
                    Campaign campaign = new Campaign();
                    campaign.setId(uni.getId());
                    campaign.setDimen(uni.getDimen());
                    campaign.setTitle(uni.getTitle());
                    campaign.setUrl(uni.getUrl());
                    campaign.setPostType(uni.getPostType());

                    mainGrids1.add(campaign);
                }

                Product moreProduct1 = new Product();
                moreProduct1.setPriId(2);
                moreProduct1.setPostType(MORE_ALL);
                mainGrids1.add(moreProduct1);


                mainDiscoutHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                mainDiscoutHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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


                mainDiscoutHolder.linearSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //To updae Discount
                        Intent intent = new Intent(activity, DiscountActivity.class);
                        intent.putExtra("discount", true);
                        activity.startActivity(intent);
                    }
                });

                break;
            }

            case VIEW_TYPE_RECOMMEND_BRAND:{
                ArrayList<UniversalPost> brandLists = new ArrayList<>();
                NewArrivalMainHolder brandBannerHolder2 = (NewArrivalMainHolder) parentHolder;
                final MainBrand brandBanner2 = (MainBrand) universalList.get(position);


                brandBannerHolder2.txtNewArrival.setText(brandBanner2.getTitle());
                brandBannerHolder2.txtSeeMore.setText(resources.getString(R.string.see_more));
                List<Brand> brands2 = new ArrayList<>();
                brands2 = brandBanner2.getItems();
                if(brands2.size() > 0){
                    for(int i = 0 ; i < brands2.size(); i++){
                        Brand uni = brands2.get(i);
                        uni.setPostType(RECOMMEND_BRAND_ITEM);
                        brandLists.add(uni);




                    }
                }
                if(brands2.size() > 0) {
                    gridAdapter = new MallAdapter(activity, brandLists);

                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                    if (brandBannerHolder2.recyclerView != null) {
                        brandBannerHolder2.recyclerView.setLayoutManager(layoutManager);
                        brandBannerHolder2.recyclerView.setAdapter(gridAdapter);

                        ((LinearLayout.LayoutParams) brandBannerHolder2.recyclerView.getLayoutParams()).setMargins(10, 10, 10, 10);

                    }
                }

                brandBannerHolder2.linearSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();

                            FlurryAgent.logEvent("Click All Brands Button", mix);
                        } catch (Exception e) {
                        }

                        prefs.saveStringPreferences(SP_BRAND_TAG,"");
                        Intent intent = new Intent(activity, BrandAllActivity.class);
                        activity.startActivity(intent);
                    }
                });


                break;
            }

            case VIEW_TYPE_RECOMMEND_BRAND_ITEM :{

                BrandBannerHolder bbHolder1 = (BrandBannerHolder) parentHolder;
                final Brand brandObject  = (Brand) universalList.get(position);
                bbHolder1.image.setVisibility(View.GONE);
                bbHolder1.cardViewBrand.setVisibility(View.VISIBLE);



                bbHolder1.imageCardView.setImageUrl(brandObject.getImageUrl(), imageLoader);
                bbHolder1.imageCardView.getLayoutParams().width = (int) (display.getWidth() / 5.5) ;
                bbHolder1.imageCardView.getLayoutParams().height =  (int) (display.getWidth() / 6) ;
                bbHolder1.imageCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, BrandActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", brandObject.getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("promotion", String.valueOf(brandObject.getId()));
                            FlurryAgent.logEvent(" Click Recommended Brand Banner", mix);
                        } catch (Exception e) {
                        }


                    }
                });


                break;

            }

            case VIEW_TYPE_PARTNER_BRAND_ITEM :{

                BrandBannerHolder pbHolder = (BrandBannerHolder) parentHolder;
                final Brand pbrandObject  = (Brand) universalList.get(position);
                pbHolder.image.setVisibility(View.GONE);
                pbHolder.cardViewBrand.setVisibility(View.VISIBLE);
                pbHolder.imageCardView.setVisibility(View.GONE);
                pbHolder.imagePatner.setVisibility(View.VISIBLE);



                pbHolder.imagePatner.setImageUrl(pbrandObject.getImageUrl(), imageLoader);
                pbHolder.imagePatner.getLayoutParams().width = (int) (display.getWidth() / 2.5) ;
                pbHolder.imagePatner.getLayoutParams().height =  (int) (display.getWidth() / 2.5) ;
                pbHolder.imagePatner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, BrandActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", pbrandObject.getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("promotion", String.valueOf(pbrandObject.getId()));
                            FlurryAgent.logEvent(" Click Recommended Brand Banner", mix);
                        } catch (Exception e) {
                        }


                    }
                });


                break;

            }

            case VIEW_TYPE_POPULAR_BANNER:{
                BannerHolder popularHolder = (BannerHolder) parentHolder;
                Product popular_product   = (Product) universalList.get(position);

                if (popular_product.getPriId() == 0){
                    popularHolder.textTitleLeft.setText(popular_product.getTitle());
                    // popularHolder.textTitleLeft.getLayoutParams().width = (int) (display.getWidth()  * 2.5/3);
                    popularHolder.textTitle.setVisibility(View.GONE);
                    popularHolder.txtHidden.setVisibility(View.GONE);
                    popularHolder.img.setVisibility(View.GONE);
                    popularHolder.textTitleLeft.setVisibility(View.VISIBLE);
                }
                else{
                    popularHolder.textTitle.setText(popular_product.getTitle());
                    // popularHolder.textTitle.getLayoutParams().width = (int) (display.getWidth()  * 2.5/3);
                    popularHolder.textTitle.setVisibility(View.VISIBLE);
                    popularHolder.txtHidden.setVisibility(View.GONE);
                    popularHolder.img.setVisibility(View.GONE);
                    popularHolder.textTitleLeft.setVisibility(View.GONE);
                }


                if (popular_product.getTitle().equals(resources.getString(R.string.you_may_like))){
                    popularHolder.textTitle.setBackground(activity.getResources().getDrawable(R.color.background));
                    popularHolder.textTitleLeft.setBackground(activity.getResources().getDrawable(R.color.background));
                }

                popularHolder.textTitleLeft.setTypeface(popularHolder.textTitleLeft.getTypeface(), Typeface.BOLD);
                popularHolder.textTitle.setTypeface(popularHolder.textTitle.getTypeface(), Typeface.BOLD);





                break;


            }


            case VIEW_TYPE_PARTNER_BRAND:{
                ArrayList<UniversalPost> partnerList = new ArrayList<>();
                NewArrivalMainHolder partnerHolder = (NewArrivalMainHolder) parentHolder;
                final MainBrand brandBannerPatner = (MainBrand) universalList.get(position);


                partnerHolder.txtNewArrival.setText(brandBannerPatner.getTitle());
                partnerHolder.txtSeeMore.setText(resources.getString(R.string.see_more));
                List<Brand> brandsPranterList = new ArrayList<>();
                brandsPranterList = brandBannerPatner.getItems();
                if(brandsPranterList.size() > 0){
                    for(int i = 0 ; i < brandsPranterList.size(); i++){
                        Brand uni = brandsPranterList.get(i);
                        uni.setPostType(PARTNER_BRAND_ITEM);
                        partnerList.add(uni);




                    }
                }
                if(brandsPranterList.size() > 0) {
                    gridAdapter = new MallAdapter(activity, partnerList);

                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                    if (partnerHolder.recyclerView != null) {
                        partnerHolder.recyclerView.setLayoutManager(layoutManager);
                        partnerHolder.recyclerView.setAdapter(gridAdapter);

                        ((LinearLayout.LayoutParams) partnerHolder.recyclerView.getLayoutParams()).setMargins(10, 10, 10, 10);

                    }
                }

                partnerHolder.linearSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();

                            FlurryAgent.logEvent("Click All Partnered Brand Button", mix);
                        } catch (Exception e) {
                        }

                        prefs.saveStringPreferences(SP_BRAND_TAG,"");
                        Intent intent = new Intent(activity, BrandPartnerAllActivity.class);
                        activity.startActivity(intent);
                    }
                });


                break;
            }

            case VIEW_TYPE_MORE_ALL:{
                MoreHolder moreHolder = (MoreHolder) parentHolder;
                Product moreProductCheck  = (Product) universalList.get(position);
                moreHolder.linearMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (moreProductCheck.getPriId() == 1){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();

                                FlurryAgent.logEvent("Click All Flash Sale List Button", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, FlashSalesActivity.class);
                            intent.putExtra("fromClass", "fromClass");
                            activity.startActivity(intent);
                        }

                        else if (moreProductCheck.getPriId() == 2){
                            Intent intent = new Intent(activity, DiscountActivity.class);
                            intent.putExtra("discount", true);
                            activity.startActivity(intent);
                        }

                        else if (moreProductCheck.getPriId() == 3){
                            Intent intent = new Intent(activity, DiscountActivity.class);
                            intent.putExtra("discount", false);
                            activity.startActivity(intent);
                        }


                    }
                });
                break;
            }
            case VIEW_TYPE_MALL_FLASH_SALE_ITEM:{

                int flashPercent = 0;

                final FlashSaleItemHolder flashSalesHolder = (FlashSaleItemHolder) parentHolder;
                final Product flashProduct = (Product) universalList.get(position);
                final Flash_Sales proFlashSale = flashProduct.getFlashSalesArrayList().get(0);

                CountDownTimer countDownTimer ;



                if (proFlashSale.getReserved_quantity() > proFlashSale.getAvailable_quantity())
                    proFlashSale.setReserved_quantity(proFlashSale.getAvailable_quantity());




                int barTextCount1 = proFlashSale.getAvailable_quantity() - proFlashSale.getReserved_quantity();
                flashSalesHolder.txtAvailable.setText(String.format(resources.getString(R.string.flash_sale_avail), barTextCount1 + ""));

                if (barTextCount1 > 0){
                    flashSalesHolder.cardView.setAlpha(1.0f);
                    // flashSalesHolder.addtocart.setVisibility(View.VISIBLE);
                    flashSalesHolder.addtocart.setEnabled(true);
                    flashSalesHolder.cardView.setEnabled(true);
                    flashSalesHolder.gridItemImageView.setEnabled(true);
                }else{
                    flashSalesHolder.cardView.setAlpha(0.5f);
                    flashSalesHolder.addtocart.setVisibility(View.GONE);
                    flashSalesHolder.addtocart.setEnabled(false);
                    flashSalesHolder.cardView.setEnabled(false);
                    flashSalesHolder.gridItemImageView.setEnabled(false);

                }

                flashSalesHolder.txtTitle.setText(flashProduct.getTitle());
                flashSalesHolder.addtocart_text.setText(resources.getString(R.string.addtocart_buy));

                flashSalesHolder.txtPrice.setText(formatter.format(flashProduct.getPrice()) +" "+activity.getResources().getString(R.string.currency));


                if (flashProduct.getPreviewImage() != null && !(flashProduct.getPreviewImage().equals(""))) {
                    flashSalesHolder.gridItemImageView.setImageUrl(flashProduct.getPreviewImage(), AppController.getInstance().getImageLoader());

                }






                DateFormat df8 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                Date futureDate8 = null;
                try {
                    futureDate8 = df8.parse(proFlashSale.getEnd_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }

                flashPercent = proFlashSale.getDiscount();
                if(flashPercent != 0 ) {

                    flashSalesHolder.txtStrike.setVisibility(View.VISIBLE);


                    flashSalesHolder.txtStrike.setText(formatter.format(flashProduct.getPrice()) +" "+activity.getResources().getString(R.string.currency));
                    flashSalesHolder.txtPrice.setTypeface(flashSalesHolder.txtPrice.getTypeface(), Typeface.BOLD);
                    flashSalesHolder.txtStrike.setPaintFlags(flashSalesHolder.txtStrike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    flashSalesHolder.txtPrice.setText(formatter.format(flashProduct.getPrice() - (flashProduct.getPrice() * flashPercent) / 100 )+
                            " "+resources.getString(R.string.currency));


                    flashSalesHolder.txtAllMark.setText( "-" + proFlashSale.getDiscount() + "%");
                    flashSalesHolder.txtAllMark.setVisibility(View.VISIBLE);
                }else{

                    flashSalesHolder.txtStrike.setVisibility(View.GONE);
                    flashSalesHolder.txtStrike.setVisibility(View.GONE);
                    flashSalesHolder.txtAllMark.setVisibility(View.GONE);
                }

                Date currentDate8 = new Date();
                if (!currentDate8.after(futureDate8)) {

                    final long[] diff = {
                            futureDate8.getTime()
                                    - currentDate8.getTime()};



                    countDownTimer = this.countDownMap.get(flashSalesHolder.txtDay.hashCode());
                    if (countDownTimer == null){
                        countDownTimer = new CountDownTimer(diff[0], 1000) {
                            public void onTick(long millisUntilFinished) {

                                Date currentDate = new Date();
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                                // Please here set your event date//YYYY-MM-DD
                                Date futureDate = null;
                                try {
                                    futureDate = df.parse(proFlashSale.getEnd_date());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "onTick: "  + e.getMessage() );
                                }

                                long diff1 = futureDate.getTime()
                                        - currentDate.getTime();



                                long days = diff1 / (24 * 60 * 60 * 1000);
                                diff1 -= days * (24 * 60 * 60 * 1000);
                                long hours = diff1 / (60 * 60 * 1000);
                                diff1 -= hours * (60 * 60 * 1000);
                                long minutes = diff1 / (60 * 1000);
                                diff1 -= minutes * (60 * 1000);
                                long seconds = diff1 / 1000;

                                if (days == 0)
                                    flashSalesHolder.linearLayout1.setVisibility(View.GONE);
                                else
                                    flashSalesHolder.linearLayout1.setVisibility(View.VISIBLE);



                                flashSalesHolder.txtSecond.setText(String.valueOf(String.format("%02d", seconds).charAt(0)));
                                flashSalesHolder.txtSecondONe.setText(String.valueOf(String.format("%02d", seconds).charAt(1)));

                                flashSalesHolder.txtDay.setText(String.valueOf(String.format("%02d", days).charAt(0)));
                                flashSalesHolder.txtDayONe.setText(String.valueOf(String.format("%02d", days).charAt(1)));

                                flashSalesHolder.txtMinute.setText(String.valueOf(String.format("%02d", minutes).charAt(0)));
                                flashSalesHolder.txtMinuteONe.setText(String.valueOf(String.format("%02d", minutes).charAt(1)));

                                flashSalesHolder.txtHour.setText(String.valueOf(String.format("%02d", hours).charAt(0)));
                                flashSalesHolder.txtHourONe.setText(String.valueOf(String.format("%02d", hours).charAt(1)));


                            }

                            public void onFinish() {
                                flashSalesHolder.txtDay.setText("0");
                                flashSalesHolder.txtDayONe.setText("0");
                                flashSalesHolder.txtHour.setText("0" );
                                flashSalesHolder.txtHourONe.setText("0" );
                                flashSalesHolder.txtSecond.setText("0" );
                                flashSalesHolder.txtSecondONe.setText("0" );
                                flashSalesHolder.txtMinute.setText("0" );
                                flashSalesHolder.txtMinuteONe.setText("0" );

                                flashSalesHolder.linearLayout1.setVisibility(View.VISIBLE);
                            }
                        }.start();

                        countDownMap.put(flashSalesHolder.txtDay.hashCode(), countDownTimer);
                    }

                }
                else{
                    flashSalesHolder.txtDay.setText("0");
                    flashSalesHolder.txtDayONe.setText("0");
                    flashSalesHolder.txtHour.setText("0" );
                    flashSalesHolder.txtHourONe.setText("0" );
                    flashSalesHolder.txtSecond.setText("0" );
                    flashSalesHolder.txtSecondONe.setText("0" );
                    flashSalesHolder.txtMinute.setText("0" );
                    flashSalesHolder.txtMinuteONe.setText("0" );
                    flashSalesHolder.linearLayout1.setVisibility(View.VISIBLE);
                }


                flashSalesHolder.gridItemImageView.setOnClickListener(new OnClickProduct(activity, flashProduct));
                flashSalesHolder.cardView.setOnClickListener(new OnClickProduct(activity, flashProduct));

                flashSalesHolder.cardView.getLayoutParams().width = display.getWidth() * 3/5;
                //flashSalesHolder.cardView.getLayoutParams().height = display.getHeight() * 3/5;



                flashSalesHolder.addtocart.setOnClickListener(new CategoryAddtoCartClickListener(flashProduct,  activity,
                        1, (flashProduct.getPrice() - ( (flashProduct.getPrice() * flashPercent) / 100))));

                break;

            }


            case VIEW_TYPE_MAIN_FLASH_SALE:{
                NewArrivalMainHolder recycleTwoColorHolder = (NewArrivalMainHolder) parentHolder;
                recycleTwoColorHolder.imgFlash.setVisibility(View.VISIBLE);
                recycleTwoColorHolder.txtNewArrival.setText(" ");
                recycleTwoColorHolder.txtSeeMore.setText(resources.getString(R.string.see_more));

                recycleTwoColorHolder.linearArrivalMain.setBackgroundColor(activity.getResources().getColor(R.color.background));

                ArrayList<UniversalPost> mainGrids207 = new ArrayList<>();
                FlashList productFlash = (FlashList) universalList.get(position);


                List<Product> proFlashList = new ArrayList<>();
                proFlashList = productFlash.getProductArrayList();
                gridAdapter = new MallAdapter(activity, mainGrids207, this.countDownMap);
                layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);

                if (recycleTwoColorHolder.recyclerView != null) {
                    recycleTwoColorHolder.recyclerView.setLayoutManager(layoutManager);
                    recycleTwoColorHolder.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < proFlashList.size() ; i++){
                    Product uni = proFlashList.get(i);
                    mainGrids207.add(uni);
                }

                Product moreProduct = new Product();
                moreProduct.setPriId(1);
                moreProduct.setPostType(MORE_ALL);
                mainGrids207.add(moreProduct);



                recycleTwoColorHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                recycleTwoColorHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                    @Override
                    public void onChildViewAttachedToWindow(View view) {
                        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
                        if (linearLayout != null){
                            linearLayout.setVisibility(View.VISIBLE);


                        }
                    }

                    @Override
                    public void onChildViewDetachedFromWindow(View view) {
                        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
                        if (linearLayout != null){
                            linearLayout.setVisibility(View.GONE);



                        }


                    }
                });


                recycleTwoColorHolder.linearSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();

                            FlurryAgent.logEvent("Click All Flash Sale List Button", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, FlashSalesActivity.class);
                        intent.putExtra("fromClass", "fromClass");
                        activity.startActivity(intent);
                    }
                });


                break;
            }

            case VIEW_TYPE_MAIN_NAV_ITEM:{
                HotItemHolder itemHolder = (HotItemHolder) parentHolder;
                MainNav mainNav = (MainNav) universalList.get(position);
                itemHolder.title.setText(mainNav.getTitle());
                if (mainNav.getImg_url() != null && mainNav.getImg_url().trim().length() > 0){
                    itemHolder.img.setImageUrl(mainNav.getImg_url(), imageLoader);
                }
                else {
                    itemHolder.img.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }

                itemHolder.img.getLayoutParams().width  =  (display.getWidth() / 6) ;
                itemHolder.img.getLayoutParams().height =  display.getWidth() / 6;

                itemHolder.linear.getLayoutParams().width  = (int)  (display.getWidth() / 4.4) ;

                itemHolder.linear.setBackgroundColor(activity.getResources().getColor(R.color.background));
                itemHolder.title.setBackgroundColor(activity.getResources().getColor(R.color.background));

                itemHolder.title.setLines(1);


                itemHolder.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mainNav.getType().equals("flashsale")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                FlurryAgent.logEvent("Click Main Nav Flashsale", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, FlashSalesActivity.class);
                            intent.putExtra("fromClass", "fromClass");
                            activity.startActivity(intent);
                        }
                        else if (mainNav.getType().equals("promotions")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                FlurryAgent.logEvent("Click Main Nav Promotions", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, DiscountActivity.class);
                            intent.putExtra("discount", true);
                            activity.startActivity(intent);

                        }
                        else if (mainNav.getType().equals("brands")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                FlurryAgent.logEvent("Click Main Nav Brands", mix);
                            } catch (Exception e) {
                            }
                            prefs.saveStringPreferences(SP_BRAND_TAG,"");
                            Intent intent = new Intent(activity, BrandAllActivity.class);
                            activity.startActivity(intent);

                        }
                        else if (mainNav.getType().equals("new_arrival")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                FlurryAgent.logEvent("Click Main Nav NewArrival", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, ProductListClickActivity.class);
                            intent.putExtra("title",resources.getString(R.string.top_seller));
                            intent.putExtra("url", constantProductTopList +   "language=" + prefs.getStringPreferences(SP_LANGUAGE));
                            activity.startActivity(intent);

                        }
                    }
                });

                break;
            }

            case VIEW_TYPE_MAIN_NAV:{


                NewArrivalMainHolder newArrivalMainHolder = (NewArrivalMainHolder) parentHolder;
                OrderDetailsObj orderDetailsObj = (OrderDetailsObj) universalList.get(position);


                newArrivalMainHolder.linear.setVisibility(View.GONE);
                newArrivalMainHolder.linearArrivalMain.setBackgroundColor(activity.getResources().getColor(R.color.background));

                ArrayList<UniversalPost> mainGridChoose1 = new ArrayList<>();


                List<MainNav> moreItemsChoose = orderDetailsObj.getMainNavList();



                if (moreItemsChoose.size() > 0) {
                    for (int i = 0; i < moreItemsChoose.size(); i++) {
                        MainNav categoryMain = moreItemsChoose.get(i);
                        categoryMain.setPostType(MAIN_NAV_ITEM);
                        mainGridChoose1.add(categoryMain);
                    }

                    gridAdapter = new MallAdapter(activity, mainGridChoose1);
                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                    if (newArrivalMainHolder.recyclerView != null) {
                        newArrivalMainHolder.recyclerView.setLayoutManager(layoutManager);
                        newArrivalMainHolder.recyclerView.setAdapter(gridAdapter);
                        //newArrivalMainHolder.recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());


                    }

                }
                newArrivalMainHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                newArrivalMainHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                    @Override
                    public void onChildViewAttachedToWindow(View view) {
                        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                        if (image != null) {
                            image.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onChildViewDetachedFromWindow(View view) {
                        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                        if (image != null) {
                            image.setVisibility(View.GONE);
                        }

                    }
                });





                break;
            }

            case VIEW_TYPE_CUSTOM_SLIDER_MAIN :{


                RecyclerOnlyHolder newArrivalMainHolder = (RecyclerOnlyHolder) parentHolder;
                MainObject orderDetailsObj = (MainObject) universalList.get(position);

                if(newArrivalMainHolder.recyclerView.getId() != 810) {
                    newArrivalMainHolder.recyclerView.setId(810);


                    ArrayList<UniversalPost> mainGridChoose1 = new ArrayList<>();

                    List<MainItem> moreItemsChoose = orderDetailsObj.getItems();

                    if (moreItemsChoose.size() > 0 ){
                        for(int i = 0 ; i < moreItemsChoose.size() ; i++){
                            MainItem uni = moreItemsChoose.get(i);

                            if (orderDetailsObj.getFullSize() == 1)
                                uni.setPostType(CUSTOM_SLIDER_ITEM_NO_PADDING);
                            else
                                uni.setPostType(CUSTOM_SLIDER_ITEM);

                            mainGridChoose1.add(uni);



                        }

                        layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                        gridAdapter = new MallAdapter(activity, mainGridChoose1);
                        if (newArrivalMainHolder.recyclerView != null) {
                            newArrivalMainHolder.recyclerView.setLayoutManager(layoutManager);
                            newArrivalMainHolder.recyclerView.setAdapter(gridAdapter);


                            if (mainGridChoose1.size() > 1){

                                handler = new Handler();
                                runnable = new Runnable() {
                                    @Override
                                    public void run() {

                                        countIndex++;

                                        if (countIndex<moreItemsChoose.size())
                                        {
                                            newArrivalMainHolder.recyclerView.smoothScrollToPosition(countIndex);

                                        }else if (countIndex==moreItemsChoose.size())
                                        {

                                            countIndex=-1;

                                        }

                                        handler.postDelayed(this, speedScroll);


                                    }
                                };

                                handler.postDelayed(runnable,speedScroll);
                            }

                        }

                    }
                    newArrivalMainHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                    newArrivalMainHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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
                }


                break;
            }

            case VIEW_TYPE_CUSTOM_SLIDER_ITEM_NO_PADDING:{
                MainItem mainItem = (MainItem) universalList.get(position);
                SliderHolder sliderHolder = (SliderHolder) parentHolder;
                int width = display.getWidth();
                sliderHolder.img.getLayoutParams().width =  display.getWidth();
                sliderHolder.img.getLayoutParams().height = (mainItem.getDimen() * width) / 120;
                sliderHolder.img.setImageUrl(mainItem.getPreview_url(), AppController.getInstance().getImageLoader());

                sliderHolder.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("ads_id", String.valueOf(mainItem.getId()));
                            FlurryAgent.logEvent("Click Ads Banner", mix);
                        } catch (Exception e) {
                        }

                        if(mainItem.getPost_type().equals("link")){

                            if(mainItem.getOpen_target() != null){
                                if(mainItem.getOpen_target() != null && mainItem.getOpen_target().equals("outside_app")){


                                    try {
                                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mainItem.getUrl()));
                                        activity.startActivity(myIntent);
                                    }catch(ActivityNotFoundException e){
                                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                    }
                                }else{

                                    Intent intent = new Intent(activity, ActivityWebView.class );
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url",mainItem.getUrl().toString());
                                    bundle.putString("title", "");
                                    intent.putExtras(bundle);
                                    activity.startActivity(intent);
                                }
                            }

                        }else if(mainItem.getPost_type().equals("api")){
                            try {

                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_progress);
                                dialog.setCancelable(true);

                                Window window = dialog.getWindow();
                                WindowManager.LayoutParams wlp = window.getAttributes();
                                wlp.gravity = Gravity.CENTER;
                                wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                                window.setAttributes(wlp);
                                dialog.setCanceledOnTouchOutside(false);

                                CustomTextView title  = (CustomTextView) dialog.findViewById(R.id.title);
                                CustomTextView text  = (CustomTextView) dialog.findViewById(R.id.text);

                                title.setText(resources.getString(R.string.progress_dialog_title));
                                text.setText(resources.getString(R.string.progerss_dialog_text));

                                dialog.show();


                                JsonArrayRequest jsonArrayRequest = adsApi(mainItem.getUrl(), dialog);
                                AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                            } catch (Exception e) {
                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                            }
                        }else if(mainItem.getPost_type().equals("image")){
                            try {

                                Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", mainItem.getPreview_url().toString());
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                            }
                        }else if(mainItem.getPost_type().equals("brand_page")){
                            try {

                                Intent intent = new Intent(activity, BrandActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id",Integer.parseInt(mainItem.getUrl()));
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );
                            }
                        }
                    }
                });

                break;
            }

            case VIEW_TYPE_CUSTOM_SLIDER_ITEM:{
                MainItem mainItem = (MainItem) universalList.get(position);
                SliderHolder sliderHolder = (SliderHolder) parentHolder;
                int width =  (int) (display.getWidth() * 4 / 5);
                sliderHolder.img.getLayoutParams().width =  width;
                sliderHolder.img.getLayoutParams().height = (mainItem.getDimen() * width) / 100;
                sliderHolder.img.setImageUrl(mainItem.getPreview_url(), AppController.getInstance().getImageLoader());

                sliderHolder.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("ads_id", String.valueOf(mainItem.getId()));
                            FlurryAgent.logEvent("Click Ads Banner", mix);
                        } catch (Exception e) {
                        }

                        if(mainItem.getPost_type().equals("link")){

                            if(mainItem.getOpen_target() != null){
                                if(mainItem.getOpen_target() != null && mainItem.getOpen_target().equals("outside_app")){


                                    try {
                                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mainItem.getUrl()));
                                        activity.startActivity(myIntent);
                                    }catch(ActivityNotFoundException e){
                                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                    }
                                }else{

                                    Intent intent = new Intent(activity, ActivityWebView.class );
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url",mainItem.getUrl().toString());
                                    bundle.putString("title", "");
                                    intent.putExtras(bundle);
                                    activity.startActivity(intent);
                                }
                            }

                        }else if(mainItem.getPost_type().equals("api")){
                            try {

                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_progress);
                                dialog.setCancelable(true);

                                Window window = dialog.getWindow();
                                WindowManager.LayoutParams wlp = window.getAttributes();
                                wlp.gravity = Gravity.CENTER;
                                wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                                window.setAttributes(wlp);
                                dialog.setCanceledOnTouchOutside(false);

                                CustomTextView title  = (CustomTextView) dialog.findViewById(R.id.title);
                                CustomTextView text  = (CustomTextView) dialog.findViewById(R.id.text);

                                title.setText(resources.getString(R.string.progress_dialog_title));
                                text.setText(resources.getString(R.string.progerss_dialog_text));

                                dialog.show();


                                JsonArrayRequest jsonArrayRequest = adsApi(mainItem.getUrl(), dialog);
                                AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                            } catch (Exception e) {
                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                            }
                        }else if(mainItem.getPost_type().equals("image")){
                            try {

                                Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", mainItem.getPreview_url().toString());
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                            }
                        }else if(mainItem.getPost_type().equals("brand_page")){
                            try {

                                Intent intent = new Intent(activity, BrandActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id",Integer.parseInt(mainItem.getUrl()));
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );
                            }
                        }
                    }
                });

                break;
            }

            case VIEW_TYPE_HOT_CATEGORY_ITEM:{
                HotItemHolder hotItemHolder = (HotItemHolder) parentHolder;
                CategoryMain gridSuperMain = (CategoryMain) universalList.get(position);
                hotItemHolder.title.setText(gridSuperMain.getTitle());
                if (gridSuperMain.getImage() != null && gridSuperMain.getImage().trim().length() > 0){
                    hotItemHolder.img.setImageUrl(gridSuperMain.getImage(), imageLoader);
                }
                else {
                    hotItemHolder.img.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }



                hotItemHolder.img.getLayoutParams().width  =  (display.getWidth() / 7) ;
                hotItemHolder.img.getLayoutParams().height =  display.getWidth() / 7;

                hotItemHolder.linear.getLayoutParams().width  = (int)  (display.getWidth() / 4.4) ;

                hotItemHolder.title.setLines(1);


                hotItemHolder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                hotItemHolder.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        prefs.saveStringPreferences(SP_BRAND_TAG, gridSuperMain.getTag());
                        prefs.saveIntPerferences(SP_BRAND_ID, gridSuperMain.getId());

                        MainItem grids = new MainItem();
                        grids.setId(gridSuperMain.getId());
                        grids.setTitle(gridSuperMain.getTitle());
                        grids.setPost_type(gridSuperMain.getTag());
                        grids.setPreview_url(gridSuperMain.getImage());


                        // Intent intent = new Intent(activity, CategoryActivity.class);
                        Intent intent = new Intent(activity, CategoryActivity.class);
                        Bundle b = new Bundle();
                        b.putParcelable("mainCat", grids);
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", grids.getPost_type());
                            FlurryAgent.logEvent("Click Main Category", mix);
                        } catch (Exception e) {
                        }
                        intent.putExtras(b);
                        activity.startActivity(intent);
                    }
                });



                break;

            }

            case VIEW_TYPE_HOT_CATEGORY_MAIN:{
                NewArrivalMainHolder newArrivalMainHolder = (NewArrivalMainHolder) parentHolder;
                MainCategoryObj orderDetailsObj = (MainCategoryObj) universalList.get(position);

                newArrivalMainHolder.txtNewArrival.setText(resources.getText(R.string.populars_items));
                newArrivalMainHolder.txtSeeMore.setText(resources.getText(R.string.see_more));
                newArrivalMainHolder.linearSeeMore.setVisibility(View.GONE);

                ArrayList<UniversalPost> mainGridChoose1 = new ArrayList<>();


                List<CategoryMain> moreItemsChoose = orderDetailsObj.getCategoryMainList();

                if (moreItemsChoose.size() > 0 ){
                    for(int i = 0 ; i < moreItemsChoose.size() ; i++){
                        CategoryMain categoryMain = moreItemsChoose.get(i);
                        categoryMain.setPostType(HOT_CATEGORY_ITEM);
                        mainGridChoose1.add(categoryMain);
                    }

                    gridAdapter = new MallAdapter(activity, mainGridChoose1);
                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                    if (newArrivalMainHolder.recyclerView != null) {
                        newArrivalMainHolder.recyclerView.setLayoutManager(layoutManager);
                        newArrivalMainHolder.recyclerView.setAdapter(gridAdapter);
                    }

                }
                newArrivalMainHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                newArrivalMainHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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



                newArrivalMainHolder.linearSeeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

    private void goToNextIntent(String type){
        if(type.equals("product_categories")){
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK, 3);

            //FragmentCategory fragmentCategory = new FragmentCategory();
            //activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentCategory).commitAllowingStateLoss();

            Intent intent = new Intent(activity,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.finish();

        }
        else if(type.equals("forum")){
            /*FragmentReadingPager fragmentTest = new FragmentReadingPager();
            Bundle bundle = new Bundle();
            bundle.putInt("position",0);
            fragmentTest.setArguments(bundle);
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 2);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentTest).commitAllowingStateLoss();
*/
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK , 2);
            Intent intent = new Intent(activity,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.finish();

        }
        else if(type.equals("knowledge")){
            prefs.saveIntPerferences(SP_MAINACTIVITY_CLICK, 1);

            Intent intent = new Intent(activity,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.finish();
         /*   FragmentMedia fragmentMedia = new FragmentMedia();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentMedia).commitAllowingStateLoss();

*/
        }
        else if(type.equals("marketplace")){
            try {
                Map<String, String> mix = new HashMap<String, String>();

                FlurryAgent.logEvent("Click All Partnered Brand Button", mix);
            } catch (Exception e) {
            }

            prefs.saveStringPreferences(SP_BRAND_TAG,"");
            Intent intent = new Intent(activity, BrandPartnerAllActivity.class);
            activity.startActivity(intent);
        }
        else if(type.equals("best_selling_products")){
            Intent intent = new Intent(activity, ProductListClickActivity.class);
            intent.putExtra("title","");
            intent.putExtra("url", "https://www.kyarlay.com/api/products/top?language=uni");
            activity.startActivity(intent);
        }
    }


    private JsonArrayRequest adsApi(String url, final Dialog dialog)
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            ArrayList<Product> productArrayList = new ArrayList<>();
                            Type listType = new TypeToken<List<Product>>() {}.getType();
                            List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                            for(int i = 0; i < deliveries.size(); i++) {

                                Product pro = new Product();
                                pro = deliveries.get(i);
                                pro.setPostType(CATEGORY_DETAIL);
                                productArrayList.add(pro);
                            }


                            goToNextIntent(productArrayList, dialog);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        return jsonObjReq;
    }

    private void goToNextIntent(ArrayList<Product> list, Dialog dialog) {

        if(list.size() == 1){
            Product product = (Product) list.get(0);
            Intent intent = new Intent(activity, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", product);
            intent.putExtras(bundle);
            activity.startActivity(intent);
            dialog.dismiss();

        }
        else if (list.size() > 1) {
            Intent intent = new Intent(activity, ActivityAdsList.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("product", list);
            bundle.putString("fromClass", "");
            intent.putExtras(bundle);
            activity.startActivity(intent);
            dialog.dismiss();

        }
    }

    private void feedBackDialog(final Order order, final int index, final boolean checking) {
        try {
            Map<String, String> mix = new HashMap<String, String>();
            mix.put("order_id",String.valueOf(order.getOrder_id()));
            FlurryAgent.logEvent("Click Rating Order Button", mix);
        } catch (Exception e) {
        }

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_friend_feedback);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        CustomTextView invite= dialog.findViewById(R.id.send_feedback);
        CircularTextView one  = dialog.findViewById(R.id.one);
        CircularTextView two  = dialog.findViewById(R.id.two);
        CircularTextView three  = dialog.findViewById(R.id.three);
        CircularTextView four  = dialog.findViewById(R.id.four);
        CircularTextView five  = dialog.findViewById(R.id.five);
        CircularTextView six  = dialog.findViewById(R.id.six);
        CircularTextView seven  = dialog.findViewById(R.id.seven);
        CircularTextView eight  = dialog.findViewById(R.id.eight);
        CircularTextView nine  = dialog.findViewById(R.id.nine);
        CircularTextView ten  = dialog.findViewById(R.id.ten);
        CircularTextView zero  = dialog.findViewById(R.id.zero);
        CustomTextView text         = dialog.findViewById(R.id.text);
        CustomTextView least_like   = dialog.findViewById(R.id.least_like);
        CustomTextView most_like    = dialog.findViewById(R.id.most_like);
        final CustomEditText editFeedBack = dialog.findViewById(R.id.editFeedBack);
        editFeedBack.setVisibility(View.VISIBLE);
        editFeedBack.setHint(resources.getString(R.string.write_feedback));

        zero.setVisibility(View.GONE);
        least_like.setText(resources.getString(R.string.least_feedback));
        most_like.setText(resources.getString(R.string.most_feedback));

        invite.setText(resources.getString(R.string.send));
        text.setText(resources.getString(R.string.order_feedback));


        CircularTextView [] textViews = {zero, one,two, three, four, five, six, seven, eight,nine, ten};
        for(int i = 0 ; i < 11; i ++){
            textViews[i].setStrokeWidth(1);
            textViews[i].setStrokeColor("#000000");
            textViews[i].setSolidColor("#ffffff");
        }

        zero.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 0));
        one.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 1));
        two.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 2));
        three.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 3));
        four.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 4));
        five.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 5));
        six.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 6));
        seven.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 7));
        eight.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 8));
        nine.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 9));
        ten.setOnClickListener(new MallAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 10));



        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numClick != -1){
                    dialog.dismiss();
                    final Dialog ratingProgress = new Dialog(activity);
                    ratingProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ratingProgress.setContentView(R.layout.dialog_comment_delete);
                    ratingProgress.setCancelable(false);
                    ratingProgress.setCanceledOnTouchOutside(false);
                    Window window = ratingProgress.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                    window.setAttributes(wlp);

                    CustomTextView txtProgressStatus = ratingProgress.findViewById(R.id.txtProgressStatus);
                    txtProgressStatus.setText("Rating your order...");
                    ratingProgress.show();



                    ratingOrder(order, numClick, editFeedBack.getText().toString(), ratingProgress, index, checking);

                }else{

                    ToastHelper.showToast(activity, resources.getString(R.string.choose_num_feedback));
                }

            }
        });

        dialog.show();
    }

    private void ratingOrder(final Order order, final int ratingScore, final String feedback, final Dialog progress, final  int index, final boolean checking){
        JSONObject uploadOrder = new JSONObject();
        try {
            uploadOrder.put("shopping_cart_id",    order.getOrder_id());
            uploadOrder.put("feedback_point",    ratingScore);
            uploadOrder.put("feedback_text",    feedback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://www.kyarlay.com/api/shopping_carts/feedback"  , uploadOrder, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progress.dismiss();

                try{
                    int returnSatatus = response.getInt("status");

                    if(returnSatatus == 1){

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("order_id", String.valueOf(order.getOrder_id()));
                            FlurryAgent.logEvent("Rating Order Done", mix);
                        } catch (Exception e) {
                        }


                        prefs.saveIntPerferences(SP_USER_POINT, response.getInt("points"));

                        order.setFeedback_point(ratingScore);
                        order.setFeedback_text(feedback);
                        universalList.remove(index);


                        if (checking)
                            universalList.add(index, order);

                        notifyDataSetChanged();

                    }else{
                        ToastHelper.showToast(activity, resources.getString(R.string.search_error));
                    }
                }catch (Exception e){

                    Log.e(TAG, "onResponse: "  + e.getMessage() );
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progress.dismiss();

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(request);

    }


    public  class clickFriendFeedback implements View.OnClickListener{

        CircularTextView zero, one, two, three, four, five, six, seven, eight, nine, ten;
        CircularTextView [] textViews = {};

        int position;

        public clickFriendFeedback(CircularTextView zero, CircularTextView one,
                                   CircularTextView two, CircularTextView three,
                                   CircularTextView four, CircularTextView five,
                                   CircularTextView six, CircularTextView seven,
                                   CircularTextView eight, CircularTextView nine,
                                   CircularTextView ten, int position) {
            this.zero = zero;
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;
            this.five = five;
            this.six = six;
            this.seven = seven;
            this.eight = eight;
            this.nine = nine;
            this.ten = ten;
            this.position = position;
            textViews= new CircularTextView[]{zero, one, two, three, four, five, six, seven, eight, nine,ten};
        }

        @Override
        public void onClick(View v) {
            numClick    = position;
            for(int i= 0 ; i < 11; i ++){
                if(i == position){
                    textViews[i].setStrokeWidth(1);
                    textViews[i].setStrokeColor("#000000");
                    textViews[i].setSolidColor("#F2cc23");

                }else{
                    textViews[i].setStrokeWidth(1);
                    textViews[i].setStrokeColor("#000000");
                    textViews[i].setSolidColor("#ffffff");

                }
            }

        }
    }



    private void refreshClick(Activity activity1){
        if(activity1.getLocalClassName().contains("AskProdcutAcitivity")) {

            AskProdcutAcitivity pro = (AskProdcutAcitivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("CollectionDetailActivity")) {

            CollectionDetailActivity pro = (CollectionDetailActivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("FlashSalesActivity")) {

            FlashSalesActivity pro = (FlashSalesActivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("NameWishListActivity")) {

            NameWishListActivity pro = (NameWishListActivity) activity1;
            pro.clickRefresh();
        }
        else if(activity1.getLocalClassName().contains("ShowAllNamesActivity")) {

            ShowAllNamesActivity pro = (ShowAllNamesActivity) activity1;
            pro.clickRefresh();
        }
        else if(activity1.getLocalClassName().contains("NewsClickActivity")) {

            NewsClickActivity pro = (NewsClickActivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("ClinicActivity")) {

            ClinicActivity pro = (ClinicActivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("ReadingWishlistActivity")) {

            ReadingWishlistActivity pro = (ReadingWishlistActivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("DiscountActivity")) {

            DiscountActivity pro = (DiscountActivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("OlderedActivity")) {

            OlderedActivity pro = (OlderedActivity) activity1;
            pro.clickRefresh();
        }


        else if(activity1.getLocalClassName().contains("BrandActivity")) {

            BrandActivity pro = (BrandActivity) activity1;
            pro.clickRefresh();
        }


        else if(activity1.getLocalClassName().contains("SearchResultActivity")) {

            SearchResultActivity pro = (SearchResultActivity) activity1;
            pro.clickRefresh();
        }
        else if(activity1.getLocalClassName().contains("NotificationAcitivity")) {

            NotificationAcitivity pro = (NotificationAcitivity) activity1;
            pro.clickRefresh();
        }
        else if(activity1.getLocalClassName().contains("CampainDetailActivity")) {

            CampainDetailActivity pro = (CampainDetailActivity) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("ActivityStepTwoCart")) {

            ActivityStepTwoCart pro = (ActivityStepTwoCart) activity1;
            pro.clickRefresh();
        }

        else if(activity1.getLocalClassName().contains("ActivityStepOneCart")) {

            ActivityStepOneCart pro = (ActivityStepOneCart) activity1;
            pro.clickRefresh();
        }



    }


    public class OnClickProduct implements View.OnClickListener{

        Activity activity;
        Product product;

        public OnClickProduct(Activity activity, Product product) {
            this.activity = activity;
            this.product = product;

        }

        @Override
        public void onClick(View v) {
            if (product.getFlashSalesArrayList().size() > 0){
                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("product_id", String.valueOf(product.getId()));
                    FlurryAgent.logEvent("Click Flash Product", mix);
                } catch (Exception e) {
                }
            }else{
                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("product_id", String.valueOf(product.getId()));
                    FlurryAgent.logEvent("Click Product", mix);
                } catch (Exception e) {
                }
            }


            Bundle fbundle = new Bundle();
            fbundle.putString("item_id", product.getId()+"");
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, fbundle);

            Intent intent = new Intent(activity, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", product);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }
    }

    public class CategoryAddtoCartClickListener implements View.OnClickListener{
        Activity activity;
        Product product;
        Animation bounce;
        AnimatorSet animationSet;
        int final_item_price;
        int count;

        public CategoryAddtoCartClickListener(Product product, Activity activity, int count, int final_item_price) {
            this.count              = count;
            this.activity           = activity;
            this.product            = product;
            this.final_item_price   = final_item_price;
        }

        @Override
        public void onClick(View view) {


            Bundle fbundle = new Bundle();
            fbundle.putString("item_id",product.getId()+"");
            fbundle.putString("item_name",product.getTitle()+"");
            fbundle.putString("item_category",product.getCategory_name()+"");
            fbundle.putString("price",product.getPrice()+"");
            fbundle.putString("quantity",count+"");
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, fbundle);


            if(prefs.getIntPreferences(SP_MEMBER_ID) != 0){

                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("product_id", String.valueOf(product.getId()));

                    FlurryAgent.logEvent("Add To Cart", mix);
                } catch (Exception e) {}

                if(product.getOptions() != null && product.getOptions().trim().length() > 0){

                    final ArrayList<String> phoneArray = new ArrayList<String>();
                    StringTokenizer st = new StringTokenizer(product.getOptions(), ",");
                    while (st.hasMoreTokens()) {
                        phoneArray.add(st.nextToken());
                    }
                    if (phoneArray.size() > 1) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_call);
                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        LinearLayout mainlayout = (LinearLayout) dialog.findViewById(R.id.layout);

                        CustomTextView title1 = (CustomTextView) dialog.findViewById(R.id.title);
                        title1.setText(resources.getString(R.string.product_options));
                        for(int i = 0; i < phoneArray.size(); i ++){
                            final String phoneString = phoneArray.get(i);
                            LinearLayout phoneLayout = new LinearLayout(activity);
                            phoneLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
                            phoneLayout.setPadding(0, 20, 0, 20);


                            LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            childParam1.weight = 0.1f;
                            CustomTextView price = new CustomTextView(activity);
                            price.setTextSize(16);
                            price.setPadding(10, 10, 10, 10);
                            //price.setGravity(Gravity.LEFT);
                            price.setText(phoneString);
                            price.setLayoutParams(childParam1);



                            RadioButton radioButton = new RadioButton(activity);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            parms.gravity = Gravity.CENTER;
                            parms.weight = 0.9f;
                            radioButton.setLayoutParams(parms);

                            radioButton.setClickable(false);

                            phoneLayout.addView(price);
                            phoneLayout.addView(radioButton);
                            phoneLayout.setWeightSum(1f);




                            TextView space = new TextView(activity);
                            space.setHeight(6);
                            space.setBackgroundResource(R.color.checked_bg);

                            mainlayout.addView(phoneLayout);
                            mainlayout.addView(space);

                            phoneLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    addToCartProduct(product, activity, count, final_item_price, phoneString);
                                }
                            });


                        }
                        dialog.show();
                    } else {

                        addToCartProduct(product, activity, count, final_item_price, "");
                    }

                }else{

                    addToCartProduct(product, activity, count, final_item_price, "");
                }

            }else{


                //databaseAdapter.insertOrder(product, count, final_item_price, option);
                prefs.saveBooleanPreference(LOGIN_SAVECART, false);
                Intent intent   = new Intent(activity, ActivityLogin.class);
                activity.startActivity(intent);

            }

        }
    }

    public void addToCartProduct(Product product, final Activity activity, int count, int final_item_price, String option){
        prefs.saveBooleanPreference(LOGIN_SAVECART, false);

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("quantity", count);
            uploadMessage.put("option", option);
            uploadMessage.put("product_id", product.getId());


        } catch (JSONException e) {
            e.printStackTrace();
        }




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantAddProductToServerCart, uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getInt("status") == 1) {
                                prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT,(prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT ) + count));

                                if(activity.getLocalClassName().contains("MainActivity")) {

                                    MainActivity pro = (MainActivity) activity;
                                    pro.bounceCount();
                                }else if(activity.getLocalClassName().contains("CategoryActivity")) {

                                    CategoryActivity pro = (CategoryActivity) activity;
                                    pro.bounceCount();
                                }
                                else if(activity.getLocalClassName().contains("ProductActivity")) {

                                    ProductActivity pro = (ProductActivity) activity;
                                    pro.bounceCount();
                                }

                                else if(activity.getLocalClassName().contains("CampainDetailActivity")) {

                                    CampainDetailActivity pro = (CampainDetailActivity) activity;
                                    pro.bounceCount();
                                }else if(activity.getLocalClassName().contains("ActivityAdsList")) {

                                    ActivityAdsList pro = (ActivityAdsList) activity;
                                    pro.bounceCount();
                                }else if(activity.getLocalClassName().contains("BrandedDetailActivity")) {

                                    BrandedDetailActivity pro = (BrandedDetailActivity) activity;
                                    pro.bounceCount();
                                }else if(activity.getLocalClassName().contains("WishListActivity")) {

                                    WishListActivity pro = (WishListActivity) activity;
                                    pro.bounceCount();
                                }else if(activity.getLocalClassName().contains("BrandActivity")) {

                                    BrandActivity pro = (BrandActivity) activity;
                                    pro.bounceCount();
                                }else if(activity.getLocalClassName().contains("SearchResultActivity")) {

                                    SearchResultActivity pro = (SearchResultActivity) activity;
                                    pro.bounceCount();

                                }
                                else if(activity.getLocalClassName().contains("DeeplinkingListActivity")) {

                                    DeeplinkingListActivity pro = (DeeplinkingListActivity) activity;
                                    pro.bounceCount();
                                }

                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                dialog.setContentView(R.layout.dialog_add_to_cart);

                                dialog.setCanceledOnTouchOutside(true);
                                Window window = dialog.getWindow();
                                WindowManager.LayoutParams wlp = window.getAttributes();
                                wlp.gravity = Gravity.CENTER;
                                wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                window.setAttributes(wlp);

                                CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
                                CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
                                // CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                                CustomTextView text = (CustomTextView) dialog.findViewById(R.id.text);

                                //title.setText(resources.getString(R.string.added_to_cart_title));
                                text.setText(product.getTitle() + "\t " + resources.getString(R.string.save_to_cart_error));
                                cancel.setText(resources.getString(R.string.added_to_cart_cancel));
                                confirm.setText(resources.getString(R.string.added_to_cart_confirm));


                                CircularTextView circularTextView = (CircularTextView) dialog.findViewById(R.id.menu_cart_idenfier);
                                circularTextView.setText(String.valueOf(prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT)));


                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        try {

                                            Map<String, String> mix = new HashMap<String, String>();
                                            mix.put("source", "product_detail_dialog");
                                            FlurryAgent.logEvent("Click Shopping Cart", mix);

                                        } catch (Exception e) {
                                        }

                                        dialog.dismiss();
                                        Intent intent = new Intent(activity, ShoppingCartActivity.class);
                                        activity.startActivity(intent);
                                    }

                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }

                        }catch (Exception e){
                            Log.e(TAG, "onResponse:  "  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {



                Log.e(TAG, "onErrorResponse:   "   + error.getLocalizedMessage() );
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");



       // databaseAdapter.insertOrder(product, count, final_item_price, option);

/*

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_add_to_cart);


        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
        CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
        CustomTextView text = (CustomTextView) dialog.findViewById(R.id.text);

        int countBefore  = databaseAdapter.getOrderCount();
        CircularTextView circularTextView = (CircularTextView) dialog.findViewById(R.id.menu_cart_idenfier);
        circularTextView.setText(String.valueOf( countBefore));





        text.setText(product.getTitle()+"\t "+resources.getString(R.string.save_to_cart_error));
        cancel.setText(resources.getString(R.string.added_to_cart_cancel));
        confirm.setText(resources.getString(R.string.added_to_cart_confirm));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "product_detail_dialog");
                    FlurryAgent.logEvent("Click Shopping Cart", mix);
                } catch (Exception e) {
                }

                dialog.dismiss();
                prefs.saveBooleanPreference(ALREADY_USE_POINT, false);
                Intent intent = new Intent(activity, ShoppingCartActivity.class);
                activity.startActivity(intent);
            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();*/

    }


    public void stopHandler(){
        try{
            if(handler != null)
                handler.removeCallbacks(runnable);
        }catch (Exception e){

        }
    }


}
