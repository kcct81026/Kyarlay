package com.kyarlay.ayesunaing.operation;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
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
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.flurry.android.FlurryAgent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
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
import com.kyarlay.ayesunaing.activity.BrandedDetailActivity;
import com.kyarlay.ayesunaing.activity.CampainDetailActivity;
import com.kyarlay.ayesunaing.activity.CategoryActivity;
import com.kyarlay.ayesunaing.activity.ClinicActivity;
import com.kyarlay.ayesunaing.activity.ClinicDetailsActivity;
import com.kyarlay.ayesunaing.activity.CollectionDetailActivity;
import com.kyarlay.ayesunaing.activity.CustomCalendarActivity;
import com.kyarlay.ayesunaing.activity.DiscountActivity;
import com.kyarlay.ayesunaing.activity.DueDateActivity;
import com.kyarlay.ayesunaing.activity.FlashSalesActivity;
import com.kyarlay.ayesunaing.activity.Get_PregnantActivity;
import com.kyarlay.ayesunaing.activity.Get_PregnantCalendarActivity;
import com.kyarlay.ayesunaing.activity.GiveNameActivity;
import com.kyarlay.ayesunaing.activity.InviteFriendActivity;
import com.kyarlay.ayesunaing.activity.MaharboteActivity;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.activity.MomolayDetailsActivity;
import com.kyarlay.ayesunaing.activity.NameWishListActivity;
import com.kyarlay.ayesunaing.activity.NewsActivity;
import com.kyarlay.ayesunaing.activity.NewsClickActivity;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.OlderedActivity;
import com.kyarlay.ayesunaing.activity.PostLikedActivity;
import com.kyarlay.ayesunaing.activity.ProductActivity;
import com.kyarlay.ayesunaing.activity.ReadingCommentDetailsActivity;
import com.kyarlay.ayesunaing.activity.ReadingWishlistActivity;
import com.kyarlay.ayesunaing.activity.SafeDetailsActivity;
import com.kyarlay.ayesunaing.activity.SafeQuesMainActivity;
import com.kyarlay.ayesunaing.activity.SearchResultActivity;
import com.kyarlay.ayesunaing.activity.ShoppingCartActivity;
import com.kyarlay.ayesunaing.activity.ShowAllNamesActivity;
import com.kyarlay.ayesunaing.activity.ToolsClickActivity;
import com.kyarlay.ayesunaing.activity.UserPostUploadActivity;
import com.kyarlay.ayesunaing.activity.VideoListActivity;
import com.kyarlay.ayesunaing.activity.VideoProgramDetailActivity;
import com.kyarlay.ayesunaing.activity.WishListActivity;
import com.kyarlay.ayesunaing.activity.YouTubeDialog;
import com.kyarlay.ayesunaing.activity.Youtube;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomSwitchView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AgeConfig;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.CustomTextSliderView;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.fcm.DeeplinkingListActivity;
import com.kyarlay.ayesunaing.fragment.FragmentLatest;
import com.kyarlay.ayesunaing.fragment.FragmentMedia;
import com.kyarlay.ayesunaing.fragment.FragmentReading;
import com.kyarlay.ayesunaing.fragment.FragmentTest;
import com.kyarlay.ayesunaing.holder.AdsHolder;
import com.kyarlay.ayesunaing.holder.ArticleProductHolder;
import com.kyarlay.ayesunaing.holder.BabyInfoHolder;
import com.kyarlay.ayesunaing.holder.BrandBannerHolder;
import com.kyarlay.ayesunaing.holder.CategoryGridItemHolder;
import com.kyarlay.ayesunaing.holder.CategoryMainHolder;
import com.kyarlay.ayesunaing.holder.ClinicHolder;
import com.kyarlay.ayesunaing.holder.CommentHolder;
import com.kyarlay.ayesunaing.holder.CustomMainHolder;
import com.kyarlay.ayesunaing.holder.DetailImageHolder;
import com.kyarlay.ayesunaing.holder.FooterHoloder;
import com.kyarlay.ayesunaing.holder.GrowthHolder;
import com.kyarlay.ayesunaing.holder.ImageHolder;
import com.kyarlay.ayesunaing.holder.LikeCommentHolder;
import com.kyarlay.ayesunaing.holder.LogoHolder;
import com.kyarlay.ayesunaing.holder.MainAdsHolder;
import com.kyarlay.ayesunaing.holder.MainBrandBannerHolder;
import com.kyarlay.ayesunaing.holder.MainDiscountGridHolder;
import com.kyarlay.ayesunaing.holder.MainGridHolder;
import com.kyarlay.ayesunaing.holder.MainGridItemHolder;
import com.kyarlay.ayesunaing.holder.MainRichtextHolder;
import com.kyarlay.ayesunaing.holder.NameListHolder;
import com.kyarlay.ayesunaing.holder.NoItemHolder;
import com.kyarlay.ayesunaing.holder.PackageHolder;
import com.kyarlay.ayesunaing.holder.PostLikedHolder;
import com.kyarlay.ayesunaing.holder.ProductCategoryDetailItem;
import com.kyarlay.ayesunaing.holder.ReadingToolHolder;
import com.kyarlay.ayesunaing.holder.RefreshHolder;
import com.kyarlay.ayesunaing.holder.SafeSubHolder;
import com.kyarlay.ayesunaing.holder.SingleUiHolder;
import com.kyarlay.ayesunaing.holder.StatusHolder;
import com.kyarlay.ayesunaing.holder.TextHolder;
import com.kyarlay.ayesunaing.holder.ToolGridItemHolder;
import com.kyarlay.ayesunaing.holder.UploadPostGetPointHolder;
import com.kyarlay.ayesunaing.holder.UserPostHolder;
import com.kyarlay.ayesunaing.holder.VideoAllHolder;
import com.kyarlay.ayesunaing.holder.VideoProgramHolder;
import com.kyarlay.ayesunaing.object.CategoryGrid;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.Clicnic;
import com.kyarlay.ayesunaing.object.Comment;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.Discount;
import com.kyarlay.ayesunaing.object.Flash_Sales;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.NameObject;
import com.kyarlay.ayesunaing.object.PackageObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.Reading_Post;
import com.kyarlay.ayesunaing.object.SafeItem;
import com.kyarlay.ayesunaing.object.SafeMainObj;
import com.kyarlay.ayesunaing.object.ToolChildObject;
import com.kyarlay.ayesunaing.object.ToolGrid;
import com.kyarlay.ayesunaing.object.ToolObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.object.Videos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements ConstantVariable, Constant, ConstantsDB {

    private static final String TAG = "MediaAdapter";


    AppCompatActivity activity;
    ImageLoader imageLoader;
    Display display;
    Resources resources;
    FirebaseAnalytics firebaseAnalytics;
    MyPreference prefs;
    String android_id;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    AgeConfig ageConfig;

    MediaAdapter gridAdapter;
    RecyclerView.LayoutManager layoutManager;
    DatabaseAdapter databaseAdapter;
    int numClick    = -1;

    ArrayList<NameObject> nameObjectArrayList = new ArrayList<>();
    ArrayList<Delivery> delList = new ArrayList<>();
    ArrayList<UniversalPost> universalList;

    FragmentMedia fragmentMedia;
    FragmentLatest fragmentLatest;
    FragmentReading readingFragment;

    public MediaAdapter(FragmentMedia fragment, AppCompatActivity activity, ArrayList<UniversalPost> universalList) {
        this.universalList  = universalList;
        this.activity       = activity;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        fragmentMedia = fragment;

        new MyFlurry(activity);

        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);
        android_id = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        nameObjectArrayList = databaseAdapter.getNameList();
        delList = databaseAdapter.getDelivery();
        ageConfig = new AgeConfig(activity);

    }

    public MediaAdapter(FragmentReading fragment, AppCompatActivity activity, ArrayList<UniversalPost> universalList) {
        this.universalList  = universalList;
        this.activity       = activity;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        readingFragment = fragment;

        new MyFlurry(activity);

        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);
        android_id = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        nameObjectArrayList = databaseAdapter.getNameList();
        delList = databaseAdapter.getDelivery();
        ageConfig = new AgeConfig(activity);


    }


    public MediaAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList) {
        this.universalList  = universalList;
        this.activity       = activity1;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        new MyFlurry(activity);
        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);
        android_id = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        ageConfig = new AgeConfig(activity);

    }

    public MediaAdapter(FragmentLatest fragment, AppCompatActivity activity, ArrayList<UniversalPost> universalList) {
        this.universalList  = universalList;
        this.activity       = activity;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        fragmentLatest = fragment;

        new MyFlurry(activity);

        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);
        android_id = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        nameObjectArrayList = databaseAdapter.getNameList();
        delList = databaseAdapter.getDelivery();
        ageConfig = new AgeConfig(activity);


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
        else if(universalList.get(position).getPostType().equals(SINGLE_HEALTH)){
            return VIEW_TYPE_SINGLE_HEALTH;
        }
        else if(universalList.get(position).getPostType().equals(USER_STATUS)){
            return VIEW_TYPE_USER_STATUS;
        }
        else if (universalList.get(position).getPostType().equals(BABY_INFO)) {
            return VIEW_TYPE_BABY_INFO;
        }
        else if (universalList.get(position).getPostType().equals(CHOOSE_TOOL)) {
            return VIEW_TYPE_CHOOSE_TOOL;
        }
        else if(universalList.get(position).getPostType().equals(DISCOUNT_TITLE)){
            return VIEW_TYPE_DISCOUNT_TITLE;
        }
        else if (universalList.get(position).getPostType().equals(CHILD_GROWTH)) {
            return VIEW_TYPE_CHILD_GROWTH;
        }
        else if(universalList.get(position).getPostType().equals(ADS)){
            return VIEW_TYPE_ADS;
        }
        else if(universalList.get(position).getPostType().equals(NAME_OBJECT)){
            return VIEW_TYPE_NAME_OBJECT;
        }
        else if (universalList.get(position).getPostType().equals(VIDEO_NEWS)) {
            return VIEW_TYPE_VIDEO_NEWS;
        }
        else if(universalList.get(position).getPostType().equals(CART_DETAIL_NO_ITEM)){
            return VIEW_TYPE_CART_DETAIL_NO_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(REFRESH_FOOTER)){
            return VIEW_TYPE_REFRESH_FOOTER;
        }
        else if (universalList.get(position).getPostType().equals(CHOOSE_TOOL_ITEM)) {
            return VIEW_TYPE_CHOOSE_TOOL_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(USER_POST )){
            return VIEW_TYPE_USER_POST ;
        }
        else if(universalList.get(position).getPostType().equals(BRAND_BANNER)){
            return VIEW_TYPE_BRAND_BANNER;
        }
        else if(universalList.get(position).getPostType().equals(BRAND_BANNER_ITEM)){
            return VIEW_TYPE_BRAND_BANNER_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(READING)){
            return VIEW_TYPE_READING;
        }
        else if(universalList.get(position).getPostType().equals(READING_DETAIL)){
            return VIEW_TYPE_READING_DETAIL;
        }
        else if(universalList.get(position).getPostType().equals(USER_POST_READING_DETAILS)){
            return VIEW_TYPE_USER_POST_READING_DETAILS;
        }
        else if (universalList.get(position).getPostType().equals(CATEGORY_POST)) {
            return VIEW_TYPE_CATEGORY_POST;
        }
        else if (universalList.get(position).getPostType().equals(CATEGORY_DETAIL)) {
            return VIEW_TYPE_CATEGORYF_DETAIL_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(READING_COMMENT)){
            return VIEW_TYPE_READING_COMMENT;
        }
        else if(universalList.get(position).getPostType().equals(TOOL_ARTICLE)){
            return VIEW_TYPE_TOOL_ARTICLE;
        }
        else if (universalList.get(position).getPostType().equals(LIKECOMMNET)) {
            return VIEW_TYPE_LIKECOMMNET;
        }
        else if(universalList.get(position).getPostType().equals(READING_VIDEO)){
            return VIEW_TYPE_READING_VIDEO;
        }
        else if(universalList.get(position).getPostType().equals(READING_LIKED)){
            return VIEW_TYPE_READING_LIKED;
        }
        else if(universalList.get(position).getPostType().equals(READING_TEXT)){
            return VIEW_TYPE_READING_TEXE;
        }
        else if(universalList.get(position).getPostType().equals(READING_PRODUCT)){
            return VIEW_TYPE_READING_PRODUCT;
        }
        else if(universalList.get(position).getPostType().equals(READING_API)){
            return VIEW_TYPE_READING_API;
        }
        else if(universalList.get(position).getPostType().equals(READING_YOUTUBE)){
            return VIEW_TYPE_READING_YOUTUBE;
        }
        else if(universalList.get(position).getPostType().equals(VIDEO_ALL_CLICK)){
            return VIEW_TYPE_VIDEO_ALL_CLICK;
        }
        else if (universalList.get(position).getPostType().equals(VIDEO_ADS_IMAGE)) {
            return VIEW_TYPE_VIDEO_ADS_IMAGE;
        }
        else if (universalList.get(position).getPostType().equals(DETAIL_IMAGE)) {
            return VIEW_TYPE_DETAIL_IMAGE;
        }
        else if (universalList.get(position).getPostType().equals(CATEGORY_TOOL) ){
            return VIEW_TYPE_CATEGORY_TOOL;
        }
        else if(universalList.get(position).getPostType().equals(CATEGORY_TOOL_ITEM)){
            return VIEW_TYPE_CATEGORY_TOOL_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(POST_CATEGORY_ITEM)){
            return VIEW_TYPE_POST_CATEGORY_ITEM;
        }
        else if (universalList.get(position).getPostType().equals(TOOL_SUB) ){
            return VIEW_TYPE_TOOL_SUB;
        }
        else if (universalList.get(position).getPostType().equals(MAIN_SAFE) ){
            return VIEW_TYPE_MAIN_SAFE;
        }
        else if (universalList.get(position).getPostType().equals(SAFE_ITEM) ){
            return VIEW_TYPE_SAFE_ITEM;
        }
        else if (universalList.get(position).getPostType().equals(LOGO_ITEM)) {
            return VIEW_TYPE_LOGO_ITEM;
        }
        else if (universalList.get(position).getPostType().equals(CLINIC_ITEM)) {
            return VIEW_TYPE_CLINIC_ITEM;
        }
        else if (universalList.get(position).getPostType().equals(SLIDER)) {
            return VIEW_TYPE_MAIN_ADS;
        }
        else if (universalList.get(position).getPostType().equals(PACKAGE_ITEM)) {
            return VIEW_TYPE_PACKAGE_ITEM;
        }
        else if (universalList.get(position).getPostType().equals(posting)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(signup)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(invite)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }
        else if (universalList.get(position).getPostType().equals(SAFE_SUB_ITEM) ){
            return VIEW_TYPE_SAFE_SUB_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(VIDEO_PROGRAM)){
            return VIEW_TYPE_VIDEO_PROGRAM;
        }
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == VIEW_TYPE_CART_DETAIL_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_footer, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }
        else if(viewType == VIEW_TYPE_VIDEO_PROGRAM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_program,parent, false);
            viewHolder = new VideoProgramHolder(viewItem, display);
        }
        else if (viewType == VIEW_TYPE_ADS) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_ads, parent, false);
            viewHolder = new AdsHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_SAFE_SUB_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_safe_sub_item, parent, false);
            viewHolder = new SafeSubHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_CATEGORY_POST) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_UPLOAD_POST_GET_POINT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upload_post_get_point, parent, false);
            viewHolder = new UploadPostGetPointHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_VIDEO_ADS_IMAGE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_detail_item_image, parent, false);
            viewHolder = new DetailImageHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_SINGLE_HEALTH){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_single_ui, parent, false);
            viewHolder = new SingleUiHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_LOGO_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_logo, parent, false);
            viewHolder = new LogoHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_USER_STATUS){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_user_fillup_status, parent, false);
            viewHolder = new StatusHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_DETAIL_IMAGE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_detail_item_image, parent, false);
            viewHolder = new DetailImageHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_MAIN_SAFE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_discount_grid_layout, parent, false);
            viewHolder = new MainDiscountGridHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_SAFE_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new CategoryGridItemHolder(viewItem, display);
        }
        else if (viewType == VIEW_TYPE_PACKAGE_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_package, parent, false);
            viewHolder = new PackageHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_BABY_INFO ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_baby_info, parent, false);
            viewHolder = new BabyInfoHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_CHOOSE_TOOL) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_categories, parent, false);
            viewHolder = new CategoryMainHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_DISCOUNT_TITLE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }
        else if(viewType == VIEW_TYPE_CHILD_GROWTH){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_child_week_info, parent, false);
            viewHolder = new GrowthHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_VIDEO_NEWS){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_rich_text, parent, false);
            viewHolder = new MainRichtextHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_CART_DETAIL_NO_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new NoItemHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_REFRESH_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_touch_refresh, parent, false);
            viewHolder = new RefreshHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_NAME_OBJECT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_show_namelist, parent, false);
            viewHolder = new NameListHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_CHOOSE_TOOL_ITEM ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_grid_default_item, parent, false);
            viewHolder = new CustomMainHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_USER_POST){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_custom_user_post, parent, false);
            viewHolder = new UserPostHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_BRAND_BANNER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.brand_banner, parent, false);
            viewHolder = new MainBrandBannerHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_BRAND_BANNER_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.brand_banner_image, parent, false);
            viewHolder = new BrandBannerHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_READING){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.richtext_main, parent, false);
            viewHolder = new MainRichtextHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_READING_DETAIL){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image, parent, false);
            viewHolder = new ImageHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_USER_POST_READING_DETAILS){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image, parent, false);
            viewHolder = new ImageHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_CATEGORYF_DETAIL_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_detail_item_product, parent, false);
            viewHolder = new ProductCategoryDetailItem(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_READING_COMMENT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment, parent, false);
            viewHolder = new CommentHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_TOOL_ARTICLE ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_reading_tool, parent, false);
            viewHolder = new ReadingToolHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_LIKECOMMNET) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_like_comment, parent, false);
            viewHolder = new LikeCommentHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_READING_VIDEO){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.richtext_main, parent, false);
            viewHolder = new MainRichtextHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_READING_LIKED){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.post_liked, parent, false);
            viewHolder = new PostLikedHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_READING_TEXE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_text, parent, false);
            viewHolder = new TextHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_READING_PRODUCT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_article_product, parent, false);
            viewHolder = new ArticleProductHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_READING_API){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_article_product, parent, false);
            viewHolder = new ArticleProductHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_READING_YOUTUBE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_text, parent, false);
            viewHolder = new TextHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_VIDEO_ALL_CLICK){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_video_all,parent, false);
            viewHolder = new VideoAllHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_POST_CATEGORY_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new MainGridItemHolder(viewItem, display);
        }
        else if(viewType == VIEW_TYPE_CATEGORY_TOOL_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_tool_grid_item, parent, false);
            viewHolder = new ToolGridItemHolder(viewItem, display);
        }
        else if (viewType == VIEW_TYPE_CATEGORY_TOOL) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_CLINIC_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_clinic, parent, false);
            viewHolder = new ClinicHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_TOOL_SUB) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_MAIN_ADS){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_ads, parent, false);
            viewHolder = new MainAdsHolder(viewItem, activity);
        }




        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, final int position) {

        int type = getItemViewType(position);
        switch (type){
            case VIEW_TYPE_SAFE_SUB_ITEM :{
                final SafeItem safeItem = (SafeItem) universalList.get(position);
                final  SafeSubHolder safeSubHolder = (SafeSubHolder) parentHolder;

                safeSubHolder.title.setText(safeItem.getShort_title());
                safeSubHolder.title.setTypeface( safeSubHolder.title.getTypeface(), Typeface.BOLD);
                safeSubHolder.title.setPaintFlags(safeSubHolder.title.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                safeSubHolder.txtQuestion.setText(safeItem.getLong_title());



                if(safeItem.getDesc().length() > 150) {
                    safeSubHolder.txtAnswerShort.setText(safeItem.getDesc().substring(0, 150) + " ...... continue reading  ");
                    safeSubHolder.txtAnswerShort.setVisibility(View.VISIBLE);
                    safeSubHolder.fadeEdge.setVisibility(View.VISIBLE);
                    safeSubHolder.txtAnswerLong.setVisibility(View.GONE);
                }
                else {
                    safeSubHolder.fadeEdge.setVisibility(View.GONE);
                    safeSubHolder.txtAnswerShort.setVisibility(View.GONE);
                    safeSubHolder.txtAnswerLong.setVisibility(View.VISIBLE);
                    safeSubHolder.txtAnswerLong.setText(safeItem.getDesc());

                }

                if (safeItem.getSafe_status().equals("danger")){
                    safeSubHolder.titleAlert.setText(resources.getString(R.string.danger));
                    safeSubHolder.imgSafe.setImageDrawable(activity.getResources().getDrawable(R.drawable.warning));
                }
                else if (safeItem.getSafe_status().equals("caution")){
                    safeSubHolder.titleAlert.setText(resources.getString(R.string.caution));
                    safeSubHolder.imgSafe.setImageDrawable(activity.getResources().getDrawable(R.drawable.problem));
                }
                else{
                    safeSubHolder.titleAlert.setText(resources.getString(R.string.safe));
                    safeSubHolder.imgSafe.setImageDrawable(activity.getResources().getDrawable(R.drawable.shield));
                }

                safeSubHolder.txtAnswerShort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        safeSubHolder.txtAnswerLong.setText(safeItem.getDesc());
                        safeSubHolder.fadeEdge.setVisibility(View.GONE);
                        safeSubHolder.txtAnswerShort.setVisibility(View.GONE);
                        safeSubHolder.txtAnswerLong.setVisibility(View.VISIBLE);
                        notifyItemInserted(universalList.size());
                    }
                });



                break;
            }


            case VIEW_TYPE_VIDEO_PROGRAM:{
                final VideoProgramHolder videoProgramHolder  = (VideoProgramHolder) parentHolder;
                final Reading videoProgram  = (Reading) universalList.get(position);
                if(videoProgram.getDimen() > 0) {
                    videoProgramHolder.frame.setVisibility(View.VISIBLE);
                    videoProgramHolder.imageView.getLayoutParams().height = (videoProgram.getDimen() * display.getWidth()) / 100;
                    videoProgramHolder.imageView.setImageUrl(videoProgram.getPhoto_url(), imageLoader);
                }

                if(videoProgram.getTitle() != null  && videoProgram.getTitle().trim().length() > 0) {
                    videoProgramHolder.title.setVisibility(View.VISIBLE);
                    videoProgramHolder.title.setText(videoProgram.getTitle());
                    videoProgramHolder.title.setLineSpacing(1.8f, 1.0f);
                }else
                    videoProgramHolder.title.setVisibility(View.GONE);

                if(videoProgram.getBody() != null && videoProgram.getBody().trim().length() > 0){
                    videoProgramHolder.body.setVisibility(View.VISIBLE);
                    if(videoProgram.getBody().length() > 100)
                        videoProgramHolder.body.setText(videoProgram.getBody().substring(0, 100)+"  ..... ");
                    else
                        videoProgramHolder.body.setText(videoProgram.getBody());


                    Linkify.addLinks(videoProgramHolder.body,Linkify.ALL);
                }else
                    videoProgramHolder.body.setVisibility(View.GONE);

                if(databaseAdapter.checkVideoLiked(videoProgram.getId())){
                    videoProgramHolder.like_image.setImageResource(R.drawable.wishlist_clicked);
                }else {
                    videoProgramHolder.like_image.setImageResource(R.drawable.wishlist);
                }

                if(videoProgram.getLikes() > 0 || databaseAdapter.checkVideoLiked(videoProgram.getId())) {
                    videoProgramHolder.likes.setVisibility(View.VISIBLE);
                    videoProgramHolder.likes.setText(videoProgram.getLikes() + " Likes");

                    if(databaseAdapter.checkVideoLiked(videoProgram.getId())){
                        videoProgramHolder.like_image.setImageResource(R.drawable.wishlist_clicked);
                        if(videoProgram.getLikes() == 0)
                            videoProgramHolder.likes.setText(videoProgram.getLikes()+1 + " Like");
                    }else {
                        videoProgramHolder.like_image.setImageResource(R.drawable.wishlist);
                    }
                }
                else {
                    videoProgramHolder.likes.setVisibility(View.GONE);
                }

                if(videoProgram.getComment_coount() > 0) {
                    videoProgramHolder.comment_count.setText(videoProgram.getComment_coount()+" Comments");
                    videoProgramHolder.comment_count.setVisibility(View.VISIBLE);
                }else
                    videoProgramHolder.comment_count.setVisibility(View.GONE);

                videoProgramHolder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "video_program");
                            mix.put("post_id", String.valueOf(videoProgram.getId()));
                            FlurryAgent.logEvent("Videos Program Click Item", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, VideoProgramDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("comment", "read");
                        bundle.putInt("id", videoProgram.getId());
                        bundle.putString("title", videoProgram.getTitle());
                        bundle.putString("url", videoProgram.getUrl());
                        bundle.putInt("like_count", videoProgram.getLikes());
                        bundle.putInt("comment_count", videoProgram.getComment_coount());

                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                videoProgramHolder.reading_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "video_program");
                            mix.put("post_id", String.valueOf(videoProgram.getId()));
                            FlurryAgent.logEvent("Videos Program Click Item", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, VideoProgramDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("comment", "read");
                        bundle.putInt("id", videoProgram.getId());
                        bundle.putString("title", videoProgram.getTitle());
                        bundle.putString("url", videoProgram.getUrl());
                        bundle.putInt("like_count", videoProgram.getLikes());
                        bundle.putInt("comment_count", videoProgram.getComment_coount());

                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                videoProgramHolder.comment_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "video_program");
                            mix.put("post_id", String.valueOf(videoProgram.getId()));
                            FlurryAgent.logEvent("Click Video Program Comment Icon", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", videoProgram.getId());
                        bundle.putString("title", videoProgram.getTitle());
                        bundle.putInt("like_count", videoProgram.getLikes());
                        bundle.putInt("comment_count", videoProgram.getComment_coount());
                        bundle.putString("post_type", "video_program");

                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });
                videoProgramHolder.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "video_program");
                            mix.put("post_id", String.valueOf(videoProgram.getId()));
                            FlurryAgent.logEvent("Click Video Program Comment Icon", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", videoProgram.getId());
                        bundle.putString("title", videoProgram.getTitle());
                        bundle.putInt("like_count", videoProgram.getLikes());
                        bundle.putInt("comment_count", videoProgram.getComment_coount());
                        bundle.putString("post_type", "video_program");
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });


                videoProgramHolder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Bundle fbundle = new Bundle();
                        fbundle.putString("item_id", videoProgram.getId()+"");
                        fbundle.putString("item_category", "video program");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, fbundle);

                        videoProgramHolder.likes.setVisibility(View.VISIBLE);
                        sendVideoLike(videoProgram.getId(), videoProgramHolder);

                    }
                });

                videoProgramHolder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "video_program");
                            mix.put("post_id", String.valueOf(videoProgram.getId()));
                            FlurryAgent.logEvent("Click Video Program Share Icon", mix);
                        } catch (Exception e) {
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", videoProgram.getId()+"");
                        bundle.putString("content_type","video_program");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, videoProgram.getUrl());
                        activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                    }
                });

                videoProgramHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_more);

                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        final CustomTextView subscribe_text = (CustomTextView) dialog.findViewById(R.id.subscribe_text);
                        CustomSwitchView subscribe   = (CustomSwitchView) dialog.findViewById(R.id.subscribe);
                        CustomTextView like_text     = (CustomTextView) dialog.findViewById(R.id.like_text);
                        LinearLayout like_layout        = (LinearLayout) dialog.findViewById(R.id.like_layout);

                        like_text.setText(resources.getString(R.string.post_liked));

                        if(databaseAdapter.getSubscribe(videoProgram.getId())){
                            subscribe.setChecked(true);
                            subscribe_text.setText(resources.getString(R.string.unsubscribe));
                        }else{
                            subscribe.setChecked(false);
                            subscribe_text.setText(resources.getString(R.string.subscribe));
                        }

                        subscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                if(b){
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "video_program_"+videoProgram.getId());
                                        FlurryAgent.logEvent("SubscribeVideoProgram", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.insertPostSubscribe(videoProgram.getId());
                                    FirebaseMessaging.getInstance().subscribeToTopic("post_"+videoProgram.getId());
                                    subscribe_text.setText(resources.getString(R.string.unsubscribe));
                                }else {
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+videoProgram.getId());
                                        FlurryAgent.logEvent("UnsubscribeVideoProgram", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.deleteSubscribe(videoProgram.getId());
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("post_"+videoProgram.getId());
                                    subscribe_text.setText(resources.getString(R.string.subscribe));

                                }
                            }
                        });

                        like_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent   = new Intent(activity, PostLikedActivity.class);
                                intent.putExtra("post_id", videoProgram.getId());
                                activity.startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                break;
            }
            case VIEW_TYPE_LIKECOMMNET:{
                final LikeCommentHolder likeCommentHolder = (LikeCommentHolder) parentHolder;
                final Reading readLikeComment = (Reading) universalList.get(position);
                if (readLikeComment.getLikes() == 0){
                    likeCommentHolder.like_text.setVisibility(View.GONE);
                    likeCommentHolder.wishlist.setImageResource(R.drawable.wishlist);
                }else{
                    if(databaseAdapter.checkPostLiked(readLikeComment.getId())){
                        likeCommentHolder.wishlist.setImageResource(R.drawable.wishlist_clicked);

                    }else {
                        likeCommentHolder.wishlist.setImageResource(R.drawable.wishlist);
                    }
                    likeCommentHolder.like_text.setVisibility(View.VISIBLE);
                    likeCommentHolder.like_text.setText(readLikeComment.getLikes() + " ");

                }

                likeCommentHolder.linearComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        ReadingCommentDetailsActivity testActivity = (ReadingCommentDetailsActivity) activity;
                        testActivity.showKeyBoard();
                    }
                });

                likeCommentHolder.linearShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(readLikeComment.getId()));
                            FlurryAgent.logEvent("Click Post Share Icon", mix);
                        } catch (Exception e) {
                        }


                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", readLikeComment.getId()+"");
                        bundle.putString("content_type","post");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, readLikeComment.getUrl());
                        activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                    }
                });



                likeCommentHolder.linearLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url_end  = "/like";
                        if(databaseAdapter.checkPostLiked(readLikeComment.getId())){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "unlike");
                                mix.put("source", "post_list");
                                mix.put("post_id", String.valueOf(readLikeComment.getId()));
                                FlurryAgent.logEvent("Click Like Icon", mix);
                            } catch (Exception e) {
                            }
                            url_end = "/unlike";

                        }else {

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "like");
                                mix.put("source", "post_list");
                                mix.put("post_id", String.valueOf(readLikeComment.getId()));
                                FlurryAgent.logEvent("Click Like Icon", mix);
                            } catch (Exception e) {
                            }
                        }

                        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){

                            String url  = "https://www.kyarlay.com/api/posts/";
                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.POST, url + readLikeComment.getId() +url_end, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {


                                                readLikeComment.setLikes(response.getInt("cached_votes_score"));


                                                if (readLikeComment.getLikes() != 0){
                                                    likeCommentHolder.like_text.setText(readLikeComment.getLikes() + "");
                                                    likeCommentHolder.like_text.setVisibility(View.VISIBLE);
                                                }
                                                else
                                                    likeCommentHolder.like_text.setVisibility(View.GONE);



                                                if(databaseAdapter.checkPostLiked(readLikeComment.getId())){
                                                    likeCommentHolder.wishlist.setImageResource(R.drawable.wishlist);
                                                    databaseAdapter.insertPostLike(readLikeComment.getId(), "unlike");
                                                }else {

                                                    likeCommentHolder.wishlist.setImageResource(R.drawable.wishlist_clicked);
                                                    databaseAdapter.insertPostLike(readLikeComment.getId(), "like");
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
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
                        }else{
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }
                    }
                });

                break;
            }

            case VIEW_TYPE_SAFE_ITEM:{
                CategoryGridItemHolder safeItemHolder = (CategoryGridItemHolder) parentHolder;
                final SafeMainObj safeMainObj = (SafeMainObj) universalList.get(position);
                safeItemHolder.title.setText(safeMainObj.getTitle());
                if (safeMainObj.getPhoto_url() != null && safeMainObj.getPhoto_url().trim().length() > 0){
                    safeItemHolder.imageView.setImageUrl(safeMainObj.getPhoto_url(), imageLoader);
                }
                else {
                    safeItemHolder.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);

                }
                safeItemHolder.imageView.setVisibility(View.VISIBLE);

                safeItemHolder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                safeItemHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("sub_safe_id", String.valueOf(safeMainObj.getId()));
                            mix.put("source", "safe_list");
                            FlurryAgent.logEvent("Click Safe Subcategory", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, SafeDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("safe_id", safeMainObj.getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                break;

            }

            case VIEW_TYPE_NAME_OBJECT :{
                final NameListHolder nameListHolder = (NameListHolder) parentHolder;
                final NameObject nameObject = (NameObject) universalList.get(position);




                nameListHolder.text.setText(nameObject.getName());

                boolean checkHeart = false;

                if (nameObjectArrayList.size() > 0){
                    for (int k=0; k < nameObjectArrayList.size(); k++){
                        if (nameObject.getId() == nameObjectArrayList.get(k).getId()) {
                            checkHeart = true;

                        }
                    }
                }else
                    checkHeart  = false;

                if (checkHeart)
                    nameListHolder.wishlist.setImageResource(R.drawable.wishlist_clicked);
                else
                    nameListHolder.wishlist.setImageResource(R.drawable.wishlist);



                nameListHolder.wishlist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url_end  = "/like";
                        if(databaseAdapter.checkNamesLiked(nameObject.getId())){
                            try {


                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "unlike");
                                mix.put("source", "name_list");
                                mix.put("name_id", String.valueOf(nameObject.getId()));
                                FlurryAgent.logEvent("Click Name Icon", mix);

                            } catch (Exception e) {
                            }
                            url_end = "/unlike";

                        }else {

                            try {


                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "like");
                                mix.put("source", "name_list");
                                mix.put("name_id",  String.valueOf(nameObject.getId()));
                                FlurryAgent.logEvent("Click Name Icon", mix);
                            } catch (Exception e) {
                            }
                        }

                        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){

                            String url  = "https://www.kyarlay.com/api/baby_names/" + nameObject.getId()+ url_end;


                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.POST, url  , null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {


                                                if(databaseAdapter.checkNamesLiked(nameObject.getId())){
                                                    nameListHolder.wishlist.setImageResource(R.drawable.wishlist);
                                                    databaseAdapter.insertNamesLike(nameObject.getId(), "unlike");
                                                    nameObjectArrayList.clear();
                                                    nameObjectArrayList = databaseAdapter.getNameList();
                                                }else {

                                                    nameListHolder.wishlist.setImageResource(R.drawable.wishlist_clicked);
                                                    databaseAdapter.insertNamesLike(nameObject.getId(), "like");
                                                    nameObjectArrayList.clear();
                                                    nameObjectArrayList = databaseAdapter.getNameList();
                                                }

                                            } catch (Exception e) {
                                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
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
                        }else{
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }
                    }
                });

                break;
            }


            case VIEW_TYPE_PACKAGE_ITEM:{
                final PackageObject packageObject = (PackageObject) universalList.get(position);
                final PackageHolder clinicHolder1 = (PackageHolder) parentHolder;
                clinicHolder1.txtTitle.setText("\" " + packageObject.getName() + " \"");
                clinicHolder1.txtTitle.setTypeface(clinicHolder1.txtTitle.getTypeface(), Typeface.BOLD);
                clinicHolder1.txtTitle.setTextColor(activity.getResources().getColor(R.color.coloredInactive));

                clinicHolder1.txtPhone.setText(resources.getString(R.string.price_tag) + " " + formatter.format(packageObject.getPrice()) + " Ks");
                if(packageObject.getPhoto_url() != null  && packageObject.getPhoto_url().trim().length() > 0) {
                    clinicHolder1.img.setVisibility(View.VISIBLE);

                    clinicHolder1.img.getLayoutParams().height = (packageObject.getPhoto_dimen() * display.getWidth()) / 100;
                    clinicHolder1.img.setImageUrl(packageObject.getPhoto_url(), imageLoader);
                }else
                    clinicHolder1.img.setVisibility(View.GONE);
                clinicHolder1.txtAddress.setText(packageObject.getDesc());

                if(packageObject.getDesc().length() > 250){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        clinicHolder1.txtShowLess.setText(Html.fromHtml( packageObject.getDesc().substring(0, 250)+" ....."+
                                "<b><font color=#ff0000>Read More...</font></b>" , Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        clinicHolder1.txtShowLess.setText(Html.fromHtml(packageObject.getDesc().substring(0, 250)+" ....."+
                                "<b><font color=#ff0000>" + "Read More...</font></b>"));
                    }

                    clinicHolder1.txtShowLess.setVisibility(View.VISIBLE);
                    clinicHolder1.txtAddress.setVisibility(View.GONE);
                }

                else{
                    clinicHolder1.txtShowLess.setVisibility(View.GONE);
                    clinicHolder1.txtAddress.setVisibility(View.VISIBLE);

                }

                clinicHolder1.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", packageObject.getPhoto_url().toString());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: "   + e.getMessage() );

                        }
                    }
                });

                clinicHolder1.txtShowLess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinicHolder1.txtShowLess.setVisibility(View.GONE);
                        clinicHolder1.txtAddress.setVisibility(View.VISIBLE);
                        notifyItemInserted(universalList.size());
                    }
                });


                break;

            }

            case VIEW_TYPE_UPLOAD_POST_GET_POINT:{
                final UploadPostGetPointHolder uploadPostGetPointHolder   = (UploadPostGetPointHolder) parentHolder;
                final Reading getPoint = (Reading) universalList.get(position);
                uploadPostGetPointHolder.image.setImageUrl(getPoint.getPreview_image_url(), imageLoader);
                uploadPostGetPointHolder.image.getLayoutParams().height  = (display.getWidth() * getPoint.getDimen()) / 100;
                uploadPostGetPointHolder.image.getLayoutParams().width   = display.getWidth();
                uploadPostGetPointHolder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(getPoint.getPostType().equals(posting)){
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            Intent intent = new Intent(activity, UserPostUploadActivity.class);
                            activity.startActivity(intent);
                            //activity.overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);

                        }else if(getPoint.getPostType().equals(invite)){
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            Intent intent = new Intent(activity, InviteFriendActivity.class);
                            intent.putExtra("source", "fragment_reading");
                            activity.startActivity(intent);
                        }else if(getPoint.getPostType().equals(feedback)){
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            sendFriendFeedback();
                        }else{
                            uploadPostGetPointHolder.layout.setVisibility(View.GONE);
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }


                    }
                });

                break;

            }

            case VIEW_TYPE_MAIN_SAFE:{

                MainDiscountGridHolder mainSafeHolder = (MainDiscountGridHolder) parentHolder;

                ArrayList<UniversalPost> mainGrids190 = new ArrayList<>();
                MainObject obj190 = (MainObject) universalList.get(position);
                mainSafeHolder.title.setText(obj190.getTitle());
                List<MainItem> items190 = new ArrayList<>();
                items190 = obj190.getItems();
                gridAdapter = new MediaAdapter(activity, mainGrids190);
                layoutManager = new GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false);

                if (mainSafeHolder.recyclerView != null) {
                    mainSafeHolder.recyclerView.setLayoutManager(layoutManager);
                    mainSafeHolder.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < items190.size() ; i++){
                    MainItem uni = items190.get(i);

                    SafeMainObj safeMainObj = new SafeMainObj();
                    safeMainObj.setId(uni.getId());
                    safeMainObj.setTitle(uni.getTitle());
                    safeMainObj.setPhoto_url(uni.getUrl());
                    safeMainObj.setPhoto_dimen(uni.getDimen());
                    safeMainObj.setPostType(SAFE_ITEM);
                    mainGrids190.add(safeMainObj);
                }


                mainSafeHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                mainSafeHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

                mainSafeHolder.more.setVisibility(View.GONE);

                break;

            }

            case VIEW_TYPE_TOOL_SUB:{

                MainGridHolder toolSubHolder = (MainGridHolder) parentHolder;
                ArrayList<UniversalPost> uniList = new ArrayList<>();
                ToolObject toolObj = (ToolObject) universalList.get(position);
                toolSubHolder.title.setText(toolObj.getName());

                List<ToolChildObject> toolChildObjects = new ArrayList<>();
                toolChildObjects = toolObj.getToolChildObjects();
                if (toolChildObjects.size() > 0){
                    toolSubHolder.recyclerView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < toolChildObjects.size(); i++){
                        ToolChildObject toolObject1 = toolChildObjects.get(i);
                        toolObject1.setPostType(TOOL_SUB_CATEGORY);
                        uniList.add(toolObject1);
                    }

                }

                else
                    toolSubHolder.recyclerView.setVisibility(View.GONE);



                gridAdapter = new MediaAdapter(activity, uniList);
                layoutManager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);


                if (toolSubHolder.recyclerView != null) {
                    toolSubHolder.recyclerView.setLayoutManager(layoutManager);
                    toolSubHolder.recyclerView.setAdapter(gridAdapter);
                }


                toolSubHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                toolSubHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

                break;

            }

            case VIEW_TYPE_MAIN_ADS :{

                MainAdsHolder adsHolder = (MainAdsHolder) parentHolder;
                if(adsHolder.mDemoSlider.getId() != 1){
                    adsHolder.mDemoSlider.setId(1);
                    MainObject slider = (MainObject) universalList.get(position);
                    List<MainItem> sliderAds = new ArrayList<>();
                    sliderAds = slider.getItems();
                    for(int i=0; i< sliderAds.size(); i++){

                        final MainItem ads = sliderAds.get(i);
                        CustomTextSliderView textSliderView = new CustomTextSliderView(activity);


                        // set post dimension
                        int dimen = ads.getDimen();
                        int width = display.getWidth();  // deprecated
                        int height = (dimen * width) / 100;
                        adsHolder.mDemoSlider.getLayoutParams().height = height;

                        //adsHolder.mDemoSlider.getLayoutParams().width = width;

                        // initialize a SliderLayout
                        textSliderView
                                .description(ads.getTitle())
                                .image(ads.getPreview_url())
                                .setScaleType(BaseSliderView.ScaleType.Fit);

                        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("ads_id", String.valueOf(ads.getId()));
                                    FlurryAgent.logEvent("Click Ads Banner", mix);
                                } catch (Exception e) {
                                }

                                if(ads.getPost_type().equals("link")){

                                    if(ads.getOpen_target() != null){
                                        if(ads.getOpen_target() != null && ads.getOpen_target().equals("outside_app")){


                                            try {
                                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ads.getUrl()));
                                                activity.startActivity(myIntent);
                                            }catch(ActivityNotFoundException e){
                                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                            }
                                        }else{

                                            Intent intent = new Intent(activity, ActivityWebView.class );
                                            Bundle bundle = new Bundle();
                                            bundle.putString("url",ads.getUrl());
                                            bundle.putString("title", "");
                                            intent.putExtras(bundle);
                                            activity.startActivity(intent);
                                        }
                                    }

                                }else if(ads.getPost_type().equals("api")){
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


                                        JsonArrayRequest jsonArrayRequest = adsApi(ads.getUrl(), dialog);
                                        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                                    } catch (Exception e) {
                                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                    }
                                }else if(ads.getPost_type().equals("image")){
                                    try {

                                        Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("url", ads.getPreview_url().toString());
                                        intent.putExtras(bundle);
                                        activity.startActivity(intent);
                                    } catch (Exception e) {
                                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                    }
                                }else if(ads.getPost_type().equals("brand_page")){
                                    try {

                                        Intent intent = new Intent(activity, BrandActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("id",Integer.parseInt(ads.getUrl()));
                                        intent.putExtras(bundle);
                                        activity.startActivity(intent);
                                    } catch (Exception e) {
                                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );
                                    }
                                }else{
                                    // ringProgressDialog.dismiss();
                                }

                            }
                        });

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", ads.getTitle());
                        adsHolder.mDemoSlider.addSlider(textSliderView);

                    }
                }

                break;
            }

             case VIEW_TYPE_LOGO_ITEM:{
                final Clicnic clicnicLogo = (Clicnic) universalList.get(position);
                LogoHolder logoHolder = (LogoHolder) parentHolder;
                logoHolder.txtTitle.setText(clicnicLogo.getName());
                logoHolder.txtTitle.setTypeface(logoHolder.txtTitle.getTypeface(), Typeface.BOLD);
                logoHolder.txtWatch.setTypeface(logoHolder.txtTitle.getTypeface(), Typeface.BOLD);
                logoHolder.txtAddress.setText(clicnicLogo.getTownship());
                logoHolder.txtPhone.setText(clicnicLogo.getPhone());


                logoHolder.phoneTitle.setText(resources.getString(R.string.contact_info));
                logoHolder.phoneTitle.setTypeface(logoHolder.phoneTitle.getTypeface(), Typeface.BOLD);
                logoHolder.phoneTitle.setPaintFlags(logoHolder.phoneTitle.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

                logoHolder.addTitle.setText(resources.getString(R.string.address));
                logoHolder.addTitle.setVisibility(View.GONE);
                logoHolder.addTitle.setTypeface(logoHolder.addTitle.getTypeface(), Typeface.BOLD);
                logoHolder.addTitle.setPaintFlags(logoHolder.addTitle.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);


                try{
                    if(clicnicLogo.getLogo_url() != null  && clicnicLogo.getLogo_url().trim().length() > 0) {
                        logoHolder.img.setVisibility(View.VISIBLE);
                        logoHolder.txtWatch.setVisibility(View.VISIBLE);

                        logoHolder.img.setImageUrl(clicnicLogo.getLogo_url(), imageLoader);

                    }
                }catch (Exception e){
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );

                }
                logoHolder.txtWatch.setText(resources.getString(R.string.view_all));

                logoHolder.videoCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", prefs.getStringPreferences(SP_DIRECTORY_CLICK));
                            mix.put("post_id", String.valueOf(clicnicLogo.getId()));
                            FlurryAgent.logEvent("Logo Click Item", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, ClinicDetailsActivity.class);
                        intent.putExtra("id", clicnicLogo.getId());
                        activity.startActivity(intent);
                    }
                });



                break;

            }

            case VIEW_TYPE_CLINIC_ITEM:{
                final Clicnic clicnic = (Clicnic) universalList.get(position);
                ClinicHolder clinicHolder = (ClinicHolder) parentHolder;
                clinicHolder.txtTitle.setText(clicnic.getName());
                clinicHolder.txtTitle.setTypeface(clinicHolder.txtTitle.getTypeface(), Typeface.BOLD);
                clinicHolder.txtWatch.setTypeface(clinicHolder.txtTitle.getTypeface(), Typeface.BOLD);
                clinicHolder.txtAddress.setText(clicnic.getAddress());
                clinicHolder.txtPhone.setText(clicnic.getPhone());
                clinicHolder.txtDesc.setText(clicnic.getDesc());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    Drawable leftDrawable = AppCompatResources
                            .getDrawable(activity, R.drawable.call);
                    clinicHolder.txtPhone.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
                }

                clinicHolder.phoneTitle.setText(resources.getString(R.string.contact_info));
                clinicHolder.phoneTitle.setTypeface(clinicHolder.phoneTitle.getTypeface(), Typeface.BOLD);
                clinicHolder.phoneTitle.setPaintFlags(clinicHolder.phoneTitle.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

                clinicHolder.addTitle.setText(resources.getString(R.string.address));
                clinicHolder.addTitle.setTypeface(clinicHolder.addTitle.getTypeface(), Typeface.BOLD);
                clinicHolder.addTitle.setPaintFlags(clinicHolder.addTitle.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);


                clinicHolder.descTitle.setText(resources.getString(R.string.details));
                clinicHolder.descTitle.setTypeface(clinicHolder.descTitle.getTypeface(), Typeface.BOLD);
                clinicHolder.descTitle.setPaintFlags(clinicHolder.descTitle.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

                if(clicnic.getPreview_photo_url() != null  && clicnic.getPreview_photo_url().trim().length() > 0) {
                    clinicHolder.img.setVisibility(View.VISIBLE);
                    clinicHolder.txtWatch.setVisibility(View.VISIBLE);

                    clinicHolder.img.getLayoutParams().height = (clicnic.getPreview_photo_dimen() * display.getWidth()) / 100;
                    clinicHolder.img.setImageUrl(clicnic.getPreview_photo_url(), imageLoader);

                }else {
                    clinicHolder.img.setVisibility(View.GONE);
                    clinicHolder.txtWatch.setVisibility(View.GONE);

                }
                clinicHolder.txtWatch.setText(resources.getString(R.string.view_all));
                clinicHolder.txtWatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", prefs.getStringPreferences(SP_DIRECTORY_CLICK));
                            mix.put("post_id", String.valueOf(clicnic.getId()));
                            FlurryAgent.logEvent(prefs.getStringPreferences(SP_DIRECTORY_CLICK) +
                                    " Click Item", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, ClinicDetailsActivity.class);
                        intent.putExtra("id", clicnic.getId());
                        activity.startActivity(intent);
                    }
                });

                clinicHolder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", prefs.getStringPreferences(SP_DIRECTORY_CLICK));
                            mix.put("post_id", String.valueOf(clicnic.getId()));
                            FlurryAgent.logEvent(prefs.getStringPreferences(SP_DIRECTORY_CLICK) +
                                    " Click Item", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, ClinicDetailsActivity.class);
                        intent.putExtra("id", clicnic.getId());
                        activity.startActivity(intent);
                    }
                });





                clinicHolder.txtPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final String[] strList = clicnic.getPhone().split(",");

                        if(strList.length > 1) {
                            final Dialog dialog = new Dialog(activity);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_delivery);
                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                            window.setAttributes(wlp);

                            CustomTextView title    = (CustomTextView) dialog.findViewById(R.id.title);
                            LinearLayout mainlayout = (LinearLayout) dialog.findViewById(R.id.layout);
                            title.setText(resources.getString(R.string.contact_info));

                            for (int i = 0; i < strList.length; i++) {


                                LinearLayout linearHorizontal = new LinearLayout(activity);
                                linearHorizontal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                                LinearLayout deliveyLayout = new LinearLayout(activity);
                                LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                childParam1.weight = 0.1f;
                                deliveyLayout.setLayoutParams(childParam1);
                                deliveyLayout.setOrientation(LinearLayout.VERTICAL);
                                deliveyLayout.setPadding(10, 10, 10, 10);


                                LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                childParam2.weight = 0.9f;
                                childParam2.gravity = Gravity.CENTER;


                                RadioButton radioButton = new RadioButton(activity);
                                radioButton.setLayoutParams(childParam2);

                                radioButton.setClickable(false);


                                linearHorizontal.setWeightSum(1f);

                                CustomTextView price = new CustomTextView(activity);
                                price.setTextSize(16);
                                price.setPadding(10, 20, 10, 10);
                                price.setGravity(Gravity.LEFT);
                                price.setText(strList[i]);
                                price.setTextColor(activity.getResources().getColor(R.color.black));
                                deliveyLayout.addView(price);

                                TextView space = new TextView(activity);
                                space.setHeight(6);
                                space.setBackgroundResource(R.color.line_gray);


                                linearHorizontal.addView(deliveyLayout);
                                linearHorizontal.addView(radioButton);

                                mainlayout.addView(linearHorizontal);
                                mainlayout.addView(space);


                                final int finalI = i;
                                linearHorizontal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        String str = strList[finalI].replace("-", "").trim();

                                        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + str));
                                        activity.startActivity(callIntent);
                                    }
                                });
                            }

                            dialog.show();

                        }
                        else{
                            String str = clicnic.getPhone().replace("-", "").trim();
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + str));
                            activity.startActivity(callIntent);
                        }


                    }
                });

                break;

            }
            case VIEW_TYPE_POST_CATEGORY_ITEM :{
                MainGridItemHolder holderCat = (MainGridItemHolder) parentHolder;
                final CategoryMain postCategory = (CategoryMain) universalList.get(position);
                holderCat.title.setText(postCategory.getTitle());
                holderCat.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));

                if(postCategory.getImage() != null && postCategory.getImage().trim().length() > 0) {
                    holderCat.imageView.setImageUrl(postCategory.getImage(), imageLoader);
                    holderCat.imageView.setVisibility(View.VISIBLE);
                }
                else{
                    if (postCategory.getTag().equals("momolay"))
                        holderCat.imageView.setDefaultImageResId(R.mipmap.momolay);
                    else
                        holderCat.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }



                holderCat.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", postCategory.getTitle());
                            FlurryAgent.logEvent("Click Reading Post Category", mix);
                        } catch (Exception e) {
                        }



                        Intent intent = new Intent(activity, NewsClickActivity.class);
                        intent.putExtra("tag", postCategory.getTag());
                        intent.putExtra("name", postCategory.getTitle());
                        activity.startActivity(intent);

                    }
                });

                break;
            }

            case VIEW_TYPE_CATEGORY_TOOL:{
                MainGridHolder sub21 = (MainGridHolder) parentHolder;
                ArrayList<UniversalPost> toolObjectList = new ArrayList<>();
                ToolGrid toolGrid = (ToolGrid) universalList.get(position);
                sub21.title.setText(resources.getString(R.string.video_tools_more));
                sub21.title.setVisibility(View.GONE);

                //if(mainGrids.size() > 0) {
                List<ToolChildObject> toolObjectArrayList = new ArrayList<>();
                toolObjectArrayList = toolGrid.getToolObjectArrayList();
                if (toolObjectArrayList.size() > 0){
                    sub21.recyclerView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < toolObjectArrayList.size(); i++){


                        ToolChildObject toolObject1 = toolObjectArrayList.get(i);
                        toolObject1.setPostType(CATEGORY_TOOL_ITEM);
                        toolObjectList.add(toolObject1);
                    }


                }

                else
                    sub21.recyclerView.setVisibility(View.GONE);



                gridAdapter = new MediaAdapter(activity, toolObjectList);
                layoutManager = new GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false);


                if (sub21.recyclerView != null) {
                    sub21.recyclerView.setLayoutManager(layoutManager);
                    sub21.recyclerView.setAdapter(gridAdapter);

                }



                sub21.recyclerView.getItemAnimator().setChangeDuration(0);
                sub21.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

                break;

            }

            case VIEW_TYPE_CATEGORY_TOOL_ITEM :{
                ToolGridItemHolder toolGridItemHolder = (ToolGridItemHolder) parentHolder;
                final ToolChildObject toolObject = (ToolChildObject) universalList.get(position);
                toolGridItemHolder.title.setText(toolObject.getName());
                toolGridItemHolder.title.setTypeface(toolGridItemHolder.title.getTypeface(), Typeface.BOLD);
                toolGridItemHolder.txtWatch.setTypeface(toolGridItemHolder.txtWatch.getTypeface(), Typeface.BOLD);
                toolGridItemHolder.txtWatch.setText(resources.getString(R.string.more));
                toolGridItemHolder.titleSubs.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                toolGridItemHolder.txtWatch.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));

                toolGridItemHolder.txtWatch.setVisibility(View.GONE);





                if(toolObject.getName().equals("အားလံုး")){
                    toolGridItemHolder.imageView.setDefaultImageResId(R.drawable.list);
                }
                else{
                    if(toolObject.getImage() != null && toolObject.getImage().trim().length() > 0) {
                        toolGridItemHolder.imageView.setImageUrl(toolObject.getImage(), imageLoader);
                        toolGridItemHolder.imageView.setVisibility(View.VISIBLE);
                    }
                    else
                        toolGridItemHolder.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape)   ;
                }


                toolGridItemHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(toolObject.getTag().equals("all")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("tag", toolObject.getTag());
                                FlurryAgent.logEvent("Click Tools Category", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ToolsClickActivity.class);
                            intent.putExtra("tag", toolObject.getTag());
                            activity.startActivity(intent);

                        }

                        else if(toolObject.getTag().equals("is_it_safe")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "IsitSafe onClick");
                                FlurryAgent.logEvent("IsitSafe onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, SafeQuesMainActivity.class);
                            activity.startActivity(intent);
                        }

                        else if(toolObject.getTag().equals("news")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "News onClick");
                                FlurryAgent.logEvent("News onClick", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, NewsActivity.class);
                            activity.startActivity(intent);


                        }

                        else if(toolObject.getTag().equals("video")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Video onClick");
                                FlurryAgent.logEvent("Video onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, VideoListActivity.class);
                            activity.startActivity(intent);
                        }
                        else if(toolObject.getTag().equals("maharbote")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Maharbote onClick");
                                FlurryAgent.logEvent("Maharbote onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, MaharboteActivity.class);
                            intent.putExtra("url",toolObject.getImage());
                            intent.putExtra("title", toolObject.getName());
                            activity.startActivity(intent);
                        }


                        else if(toolObject.getTag().equals("momolay")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Momolay onClick");
                                FlurryAgent.logEvent("Momolay onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, NewsClickActivity.class);
                            intent.putExtra("tag", toolObject.getTag());
                            intent.putExtra("name", toolObject.getName());
                            activity.startActivity(intent);
                        }

                        else if(toolObject.getTag().equals("baby_names")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Baby Name onClick");
                                FlurryAgent.logEvent("Baby Name onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, GiveNameActivity.class);
                            activity.startActivity(intent);
                        }

                        else if (toolObject.getTag().equals("birthday_calendar")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Horoscope onClick");
                                FlurryAgent.logEvent("Horoscope onClick", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, CustomCalendarActivity.class);
                            activity.startActivity(intent);
                        }

                        else if (toolObject.getTag().equals("ovulation_calculator")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Pregnant onClick");
                                FlurryAgent.logEvent("Pregnant onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, Get_PregnantActivity.class);
                            activity.startActivity(intent);
                        }

                        else if (toolObject.getTag().equals("due_date")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Due Date onClick");
                                FlurryAgent.logEvent("Due Date onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, DueDateActivity.class);
                            activity.startActivity(intent);
                        }
                        else if (toolObject.getTag().equals("clinic_directory")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Clinic Diectory onClick");
                                FlurryAgent.logEvent("Clinic Diectory onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ClinicActivity.class);
                            intent.putExtra("name", toolObject.getName());
                            prefs.saveStringPreferences(SP_DIRECTORY_CLICK, toolObject.getTag());
                            activity.startActivity(intent);
                        }

                        else if (toolObject.getTag().equals("schools")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "School Diectory onClick");
                                FlurryAgent.logEvent("School Diectory onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ClinicActivity.class);
                            intent.putExtra("name", toolObject.getName());
                            prefs.saveStringPreferences(SP_DIRECTORY_CLICK, toolObject.getTag());
                            activity.startActivity(intent);
                        }



                    }
                });

                break;
            }

            case VIEW_TYPE_DETAIL_IMAGE :{
                DetailImageHolder imageHolder = (DetailImageHolder) parentHolder;
                Images image1 = (Images) universalList.get(position);
                imageHolder.txtHidden.setVisibility(View.GONE);
                int heightImg ;
                if (image1.getDimen() != 0){
                    heightImg  = ( image1.getDimen()  * activity.getWindowManager().getDefaultDisplay().getWidth()) / 100;
                }else{
                    heightImg  = ( 100  * activity.getWindowManager().getDefaultDisplay().getWidth()) / 100;
                }


                if (image1.getImgVisible() == 0)
                    imageHolder.img.setVisibility(View.GONE);
                else
                    imageHolder.img.setVisibility(View.VISIBLE);

                imageHolder.img.getLayoutParams().height = heightImg;
                imageHolder.img.getLayoutParams().width = activity.getWindowManager().getDefaultDisplay().getWidth();
                imageHolder.img.setImageUrl(image1.getUrl(), imageLoader);
                break;
            }

            case VIEW_TYPE_TOOL_ARTICLE :{

                Log.e(TAG, "onBindViewHolder: VIEW_TYPE_TOOL_ARTICLE "    );

                ReadingToolHolder readingToolHolder = (ReadingToolHolder) parentHolder;
                final Reading_Post toolPost = (Reading_Post)  universalList.get(position);
                if (toolPost.getPreview_url() != null && toolPost.getPreview_url().trim().length() > 0){
                    readingToolHolder.img.setImageUrl(toolPost.getPreview_url(), imageLoader);
                }
                else {
                    readingToolHolder.img.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }
                readingToolHolder.txtTitle.setText(toolPost.getBody());
                readingToolHolder.txt.setText(toolPost.getTitle());

                readingToolHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (toolPost.getPost_type().equals(READING_TOOL)){

                            if(toolPost.getYoutube_id().equals("all")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("tag", toolPost.getYoutube_id());
                                    FlurryAgent.logEvent("Click Tools Category", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, ToolsClickActivity.class);
                                intent.putExtra("tag", toolPost.getYoutube_id());
                                activity.startActivity(intent);

                            }

                            else if(toolPost.getYoutube_id().equals("is_it_safe")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "IsitSafe onClick");
                                    FlurryAgent.logEvent("IsitSafe onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, SafeQuesMainActivity.class);
                                // intent.putExtra("tag", toolObject.getTag());
                                activity.startActivity(intent);
                            }

                            else if(toolPost.getYoutube_id().equals("news")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "News onClick");
                                    FlurryAgent.logEvent("News onClick", mix);
                                } catch (Exception e) {
                                }

                                Intent intent = new Intent(activity, NewsActivity.class);
                                activity.startActivity(intent);


                            }

                            else if(toolPost.getYoutube_id().equals("video")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Video onClick");
                                    FlurryAgent.logEvent("Video onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, VideoListActivity.class);
                                activity.startActivity(intent);
                            }
                            else if(toolPost.getYoutube_id().equals("maharbote")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Maharbote onClick");
                                    FlurryAgent.logEvent("Maharbote onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, MaharboteActivity.class);
                                intent.putExtra("url",toolPost.getPreview_url());
                                intent.putExtra("title", toolPost.getTitle());
                                activity.startActivity(intent);
                            }


                            else if(toolPost.getYoutube_id().equals("momolay")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Momolay onClick");
                                    FlurryAgent.logEvent("Momolay onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, NewsClickActivity.class);
                                intent.putExtra("tag", toolPost.getYoutube_id());
                                intent.putExtra("name", toolPost.getTitle());
                                activity.startActivity(intent);
                            }

                            else if(toolPost.getYoutube_id().equals("baby_names")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Baby Name onClick");
                                    FlurryAgent.logEvent("Baby Name onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, GiveNameActivity.class);
                                activity.startActivity(intent);
                            }

                            else if (toolPost.getYoutube_id().equals("birthday_calendar")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Horoscope onClick");
                                    FlurryAgent.logEvent("Horoscope onClick", mix);
                                } catch (Exception e) {
                                }

                                Intent intent = new Intent(activity, CustomCalendarActivity.class);
                                activity.startActivity(intent);
                            }

                            else if (toolPost.getYoutube_id().equals("ovulation_calculator")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Pregnant onClick");
                                    FlurryAgent.logEvent("Pregnant onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, Get_PregnantActivity.class);
                                activity.startActivity(intent);
                            }

                            else if (toolPost.getYoutube_id().equals("due_date")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Due Date onClick");
                                    FlurryAgent.logEvent("Due Date onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, DueDateActivity.class);
                                activity.startActivity(intent);
                            }
                            else if (toolPost.getYoutube_id().equals("clinic_directory")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Clinic Diectory onClick");
                                    FlurryAgent.logEvent("Clinic Diectory onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, ClinicActivity.class);
                                intent.putExtra("name", toolPost.getTitle());
                                prefs.saveStringPreferences(SP_DIRECTORY_CLICK, toolPost.getYoutube_id());
                                activity.startActivity(intent);
                            }

                            else if (toolPost.getYoutube_id().equals("schools")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "School Diectory onClick");
                                    FlurryAgent.logEvent("School Diectory onClick", mix);
                                } catch (Exception e) {
                                }
                                Intent intent = new Intent(activity, ClinicActivity.class);
                                intent.putExtra("name", toolPost.getTitle());
                                prefs.saveStringPreferences(SP_DIRECTORY_CLICK, toolPost.getYoutube_id());
                                activity.startActivity(intent);
                            }

                        }
                        else if (toolPost.getPost_type().equals(READING_ARTICLE)){
                            try{
                                Intent intent1 = new Intent(activity, ReadingCommentDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", Integer.parseInt(toolPost.getYoutube_id()));
                                intent1.putExtras(bundle);
                                activity.startActivity(intent1);

                            }catch (Exception e){

                                Log.e(TAG, "onClick: "  + e.getMessage() );
                            }
                        }

                        else if (toolPost.getPost_type().equals(READING_PLAYLIST)){



                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "video_program");
                                mix.put("post_id", String.valueOf(toolPost.getYoutube_id()));
                                FlurryAgent.logEvent("Videos Program Click Item", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, VideoProgramDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("comment", "read");
                            bundle.putInt("id", Integer.parseInt(toolPost.getYoutube_id()));
                            bundle.putString("title", toolPost.getTitle());
                            bundle.putString("url", toolPost.getPreview_url());



                            intent.putExtras(bundle);
                            activity.startActivity(intent);

                        }
                    }
                });




                break;
            }

            case VIEW_TYPE_READING_COMMENT:{
                final Comment comment = (Comment) universalList.get(position);
                final CommentHolder commentHolder = (CommentHolder) parentHolder;


                try{
                    if (comment.getCommentor_profile() == null){
                        commentHolder.userNetworkImage.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                        commentHolder.userNetworkImage.setVisibility(View.VISIBLE);
                    }
                    else if (comment.getCommentor_profile().equals("") || comment.getCommentor_profile().equals("null")){
                        commentHolder.userNetworkImage.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                        commentHolder.userNetworkImage.setVisibility(View.VISIBLE);
                    }else if(comment.getCommentor_profile() != null && !comment.getCommentor_profile().isEmpty()){
                        commentHolder.userNetworkImage.setVisibility(View.VISIBLE);
                        commentHolder.userNetworkImage.setImageUrl(comment.getCommentor_profile(), imageLoader);
                    }


                    try{
                        if (comment.getComment_photo_url().equals("") || comment.getComment_photo_url().equals("null")){

                            commentHolder.linearLayoutPhoto.setVisibility(View.GONE);
                        }else{
                            commentHolder.linearLayoutPhoto.setVisibility(View.VISIBLE);
                            Log.e(TAG, "onBindViewHolder: ************* "  + comment.getComment_dimen() + " " + comment.getCommentor() );


                            if(comment.getComment_dimen() != 0){

                                if (comment.getComment_dimen() > 65){
                                    commentHolder.imgComment.setVisibility(View.GONE);
                                    commentHolder.imgCommentLand.setVisibility(View.VISIBLE);
                                    try{
                                        commentHolder.imgCommentLand.setImageUrl(comment.getComment_photo_url(), imageLoader);
                                    }catch ( Exception e){
                                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                                    }
                                }
                                else if (comment.getComment_dimen() <= 65){
                                    commentHolder.imgCommentLand.setVisibility(View.GONE);
                                    commentHolder.imgComment.setVisibility(View.VISIBLE);
                                    try{
                                        commentHolder.imgComment.setImageUrl(comment.getComment_photo_url(), imageLoader);
                                    }catch (Exception e){
                                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                                    }
                                }
                                else{
                                    Log.e(TAG, "onBindViewHolder: ************* "  + comment.getComment_dimen() );
                                }


                            }

                        }

                    }catch (Exception e){
                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                    }



                    try{
                        if (comment.getComment_type().equals("normal")){
                            commentHolder.linearComments.setVisibility(View.GONE);
                        }
                        else{
                            commentHolder.linearComments.setVisibility(View.VISIBLE);
                            if (comment.getPreview_img_url().equals("") || comment.getPreview_img_url().equals("null")){
                                commentHolder.imgPreview.setVisibility(View.GONE);
                                commentHolder.txtSubtitle.setText(comment.getSubtitle() );

                            }else{
                                commentHolder.imgPreview.setVisibility(View.VISIBLE);
                                commentHolder.imgPreview.setImageUrl(comment.getPreview_img_url(), imageLoader);
                                commentHolder.txtSubtitle.setText(comment.getSubtitle() );
                                if (comment.getComment_type().equals("product")){
                                    commentHolder.txtWatch.setVisibility(View.VISIBLE);
                                    commentHolder.txtWatch.setText(resources.getString(R.string.price_tag) + comment.getPrice());
                                }else
                                    commentHolder.txtWatch.setVisibility(View.GONE);

                            }
                        }
                    }catch (Exception e){

                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                    }

                    commentHolder.linearComments.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(comment.getComment_type().equals("post")){
                                try{
                                    Intent intent1 = new Intent(activity, ReadingCommentDetailsActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", comment.getPost_id());
                                    intent1.putExtras(bundle);
                                    activity.startActivity(intent1);

                                }catch (Exception e){

                                    Log.e(TAG, "onClick: "  + e.getMessage() );
                                }
                            }
                            else if (comment.getComment_type().equals("product")){
                                String url = "https://www.kyarlay.com/api/products/"+ comment.getProduct_id();

                                JsonArrayRequest jsonArrayRequest = product(url);
                                AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                            }
                        }
                    });

                    commentHolder.imgComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("comment_id", String.valueOf(comment.getId()));
                                    mix.put("type", "image" );
                                    FlurryAgent.logEvent("Click Comment Image", mix);
                                } catch (Exception e) {
                                }

                                Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", comment.getComment_photo_url());
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, "onClick: "  + e.getMessage() );
                            }
                        }
                    });

                    commentHolder.imgCommentLand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("comment_id", String.valueOf(comment.getId()));
                                    mix.put("type", "image" );
                                    FlurryAgent.logEvent("Click Comment Image", mix);
                                } catch (Exception e) {
                                }

                                Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", comment.getComment_photo_url());
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            } catch (Exception e) {
                                Log.e(TAG, "onClick:  "  + e.getMessage() );
                            }
                        }
                    });

                }catch (Exception e){

                    commentHolder.userNetworkImage.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                    commentHolder.userNetworkImage.setVisibility(View.VISIBLE);
                    Log.e(TAG, "onBindViewHolder:  "  + e.getMessage() );
                }


                try{

                    if (comment.getCommentor_type().equals("normal_customer")){
                        if(comment.getParent_status().equals("unknown_parent_status") || comment.getParent_status().equals("other") ){

                            commentHolder.babyage.setText(resources.getString(R.string.family_member));
                            commentHolder.babyage.setVisibility(View.VISIBLE);

                        }else{
                            String returnString1 =  ageConfig.calculateKidAge(comment.getBirth_month(), comment.getBirth_year(),
                                    comment.getKid_gender(), comment.getParent_status() ) ;

                            commentHolder.babyage.setText(returnString1);
                            commentHolder.babyage.setVisibility(View.VISIBLE);
                        }

                        commentHolder.txtType.setVisibility(View.GONE);

                    }else if (comment.getCommentor_type().trim().length() > 0 ){
                        commentHolder.txtType.setText( comment.getCommentor_type());
                        commentHolder.babyage.setVisibility(View.GONE);
                        commentHolder.txtType.setVisibility(View.VISIBLE);
                        commentHolder.txtType.setTypeface( commentHolder.title.getTypeface(), Typeface.BOLD);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        {
                            Drawable leftDrawable = AppCompatResources
                                    .getDrawable(activity, R.drawable.ic_stars_grey);
                            commentHolder.txtType.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
                        }



                    }



                    commentHolder.title.setText(comment.getCommentor());
                    commentHolder.title.setTypeface( commentHolder.title.getTypeface(), Typeface.BOLD);

                    commentHolder.time.setText(comment.getCreated_at());
                }catch (Exception e){
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }

                if (comment.getBody().equals("") || comment.getBody().equals("null")){
                    commentHolder.body.setVisibility(View.GONE);
                }else{
                    commentHolder.body.setText(comment.getBody());
                    commentHolder.body.setVisibility(View.VISIBLE);
                    if(comment.getBody().contains("https://www.kyarlay.com")){
                        // Linkify.addLinks(commentHolder.body,Linkify.ALL);
                    }
                    else
                        Linkify.addLinks(commentHolder.body,Linkify.ALL);
                }

                commentHolder.body.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(comment.getBody().contains("https://www.kyarlay.com")){
                            if(comment.getComment_type().equals("post")){
                                try{
                                    Intent intent1 = new Intent(activity, ReadingCommentDetailsActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", comment.getPost_id());
                                    intent1.putExtras(bundle);
                                    activity.startActivity(intent1);

                                }catch (Exception e){
                                    Log.e(TAG, "onClick: "  + e.getMessage() );

                                }
                            }
                            else if (comment.getComment_type().equals("product")){
                                String url = "https://www.kyarlay.com/api/products/"+ comment.getProduct_id();

                                JsonArrayRequest jsonArrayRequest = product(url);
                                AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                            }
                        }

                    }
                });



                if (comment.getCommentor_id() == prefs.getIntPreferences(SP_MEMBER_ID)){
                    commentHolder.imgMore.setVisibility(View.VISIBLE);
                }else
                    commentHolder.imgMore.setVisibility(View.GONE);


                commentHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (comment.getCommentor_id() == prefs.getIntPreferences(SP_MEMBER_ID)){
                            //
                            BottomSheetDialog dialog = new BottomSheetDialog(activity);


                            View sheetView = activity.getLayoutInflater().inflate(R.layout.bottom_dialog_edit_delete, null);
                            dialog.setContentView(sheetView);
                            dialog.setCancelable(true);

                            TextView txtDelete = dialog.findViewById(R.id.txtDelete);
                            final TextView txtEdit = dialog.findViewById(R.id.txtEdit);
                            TextView txtCancel = dialog.findViewById(R.id.txtCancel);
                            TextView txtCopy = dialog.findViewById(R.id.txtCopy);

                            txtCopy.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int sdk = android.os.Build.VERSION.SDK_INT;
                                    if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                                        android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                                                activity.getSystemService(Context.CLIPBOARD_SERVICE);
                                        clipboard.setText(comment.getBody());
                                    } else {
                                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                                                activity.getSystemService(Context.CLIPBOARD_SERVICE);
                                        android.content.ClipData clip = android.content.ClipData
                                                .newPlainText("text", comment.getBody());
                                        clipboard.setPrimaryClip(clip);
                                    }
                                    ToastHelper.showToast(activity, "Text Copied!");
                                    dialog.dismiss();
                                }
                            });

                            txtEdit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                    final Dialog dialogEdit = new Dialog(activity);
                                    dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialogEdit.setContentView(R.layout.dialog_comment_edit);

                                    dialogEdit.setCanceledOnTouchOutside(true);
                                    Window window = dialogEdit.getWindow();
                                    WindowManager.LayoutParams wlp = window.getAttributes();
                                    wlp.gravity = Gravity.CENTER;
                                    wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                    window.setAttributes(wlp);

                                    CircularNetworkImageView userImg = dialogEdit.findViewById(R.id.userNetworkImage);
                                    ImageView imgClose = dialogEdit.findViewById(R.id.imgClose);
                                    final CustomEditText txtComment = dialogEdit.findViewById(R.id.txtComment);
                                    CustomTextView txtCancel = dialogEdit.findViewById(R.id.txtCancel);
                                    final CustomTextView txtUpdate = dialogEdit.findViewById(R.id.txtUpdate);
                                    NetworkImageView imgMent = dialogEdit.findViewById(R.id.imgComment);
                                    NetworkImageView imgMentLand = dialogEdit.findViewById(R.id.imgCommentLand);


                                    imgClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogEdit.dismiss();
                                        }
                                    });

                                    try {
                                        if (comment.getCommentor_profile() == null) {
                                            userImg.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                                            userImg.setVisibility(View.VISIBLE);
                                        } else if (comment.getCommentor_profile().equals("") || comment.getCommentor_profile().equals("null")) {
                                            userImg.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                                            userImg.setVisibility(View.VISIBLE);
                                        } else if (comment.getCommentor_profile() != null && !comment.getCommentor_profile().isEmpty()) {
                                            userImg.setVisibility(View.VISIBLE);
                                            userImg.setImageUrl(comment.getCommentor_profile(), imageLoader);
                                        }
                                    }catch (Exception  e) {
                                        Log.e(TAG, "onClick: "  + e.getMessage() );
                                    }

                                    try{
                                        if (comment.getComment_photo_url().equals("") || comment.getComment_photo_url().equals("null")){
                                            imgMent.setVisibility(View.GONE);
                                            imgMentLand.setVisibility(View.GONE);

                                        }else{
                                            if(comment.getComment_dimen() != 0){
                                                if (comment.getComment_dimen() > 70){
                                                    imgMent.setVisibility(View.GONE);
                                                    imgMentLand.setVisibility(View.VISIBLE);
                                                    imgMentLand.setImageUrl(comment.getComment_photo_url(), imageLoader);
                                                }
                                                else if (comment.getComment_dimen() < 70){
                                                    imgMentLand.setVisibility(View.GONE);
                                                    imgMent.setVisibility(View.VISIBLE);
                                                    imgMent.setImageUrl(comment.getComment_photo_url(), imageLoader);
                                                }

                                            }
                                        }

                                    }catch (Exception e){
                                        Log.e(TAG, "onClick: "  + e.getMessage() );

                                    }

                                    txtComment.setText(comment.getBody());
                                    if (comment.getBody().length() > 50){
                                        LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams)
                                                txtComment.getLayoutParams();
                                        linearLayoutParams.height = (int) (display.getHeight() * 0.2);
                                        txtComment.setLayoutParams(linearLayoutParams);
                                    }

                                    InputMethodManager inputMethod = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    txtComment.requestFocus();
                                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethod.showSoftInput(txtEdit, InputMethodManager.SHOW_IMPLICIT);

                                    txtCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogEdit.dismiss();
                                        }
                                    });

                                    txtUpdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (txtComment.getText().toString().trim().length() > 0){
                                                dialogEdit.dismiss();
                                                final Dialog editProgress = new Dialog(activity);
                                                editProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                editProgress.setContentView(R.layout.dialog_comment_delete);
                                                editProgress.setCancelable(false);
                                                editProgress.setCanceledOnTouchOutside(false);
                                                Window window = editProgress.getWindow();
                                                WindowManager.LayoutParams wlp = window.getAttributes();
                                                wlp.gravity = Gravity.CENTER;
                                                wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                                window.setAttributes(wlp);

                                                CustomTextView txtProgressStatus = editProgress.findViewById(R.id.txtProgressStatus);
                                                txtProgressStatus.setText("Updating comment...");
                                                editProgress.show();
                                                commentHolder.body.setText(txtComment.getText().toString());
                                                updateComment(comment.getId(), txtComment.getText().toString(), editProgress, comment, position, commentHolder);
                                            }else{

                                                ToastHelper.showToast(activity, resources.getString(R.string.fill_update));
                                            }
                                        }
                                    });

                                    dialogEdit.show();
                                }
                            });

                            txtDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                                    builder.setMessage("Are you sure you want to delete this comment?");

                                    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog1, int which) {
                                            // Do nothing but close the dialog

                                            dialog1.dismiss();
                                            final Dialog dialogDelete = new Dialog(activity);
                                            dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialogDelete.setContentView(R.layout.dialog_comment_delete);
                                            dialogDelete.setCancelable(false);
                                            dialogDelete.setCanceledOnTouchOutside(false);
                                            Window window = dialogDelete.getWindow();
                                            WindowManager.LayoutParams wlp = window.getAttributes();
                                            wlp.gravity = Gravity.CENTER;
                                            wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                            window.setAttributes(wlp);

                                            CustomTextView txtProgressStatus = dialogDelete.findViewById(R.id.txtProgressStatus);
                                            txtProgressStatus.setText("Deleting comment...");
                                            dialogDelete.show();
                                            deleteComment(comment.getId(), position, dialogDelete);



                                        }
                                    });

                                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog1, int which) {

                                            // Do nothing
                                            dialog1.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            });

                            txtCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }
                    }
                });

                commentHolder.cart.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (comment.getCommentor_id() == prefs.getIntPreferences(SP_MEMBER_ID)){
                            final Dialog dialog = new Dialog(activity);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_edit_delete);

                            dialog.setCanceledOnTouchOutside(true);
                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.RIGHT;
                            window.setAttributes(wlp);

                            CustomTextView txtDelete = dialog.findViewById(R.id.txtDelete);
                            final CustomTextView txtEdit = dialog.findViewById(R.id.txtEdit);
                            CustomTextView txtCancel = dialog.findViewById(R.id.txtCancel);

                            txtEdit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                    final Dialog dialogEdit = new Dialog(activity);
                                    dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialogEdit.setContentView(R.layout.dialog_comment_edit);

                                    dialogEdit.setCanceledOnTouchOutside(true);
                                    Window window = dialogEdit.getWindow();
                                    WindowManager.LayoutParams wlp = window.getAttributes();
                                    wlp.gravity = Gravity.CENTER;
                                    wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                    window.setAttributes(wlp);

                                    CircularNetworkImageView userImg = dialogEdit.findViewById(R.id.userNetworkImage);
                                    final CustomEditText txtComment = dialogEdit.findViewById(R.id.txtComment);
                                    CustomTextView txtCancel = dialogEdit.findViewById(R.id.txtCancel);
                                    final CustomTextView txtUpdate = dialogEdit.findViewById(R.id.txtUpdate);
                                    NetworkImageView imgMent = dialogEdit.findViewById(R.id.imgComment);
                                    NetworkImageView imgMentLand = dialogEdit.findViewById(R.id.imgCommentLand);

                                    try {
                                        if (comment.getCommentor_profile() == null) {
                                            userImg.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                                            userImg.setVisibility(View.VISIBLE);
                                        } else if (comment.getCommentor_profile().equals("") || comment.getCommentor_profile().equals("null")) {
                                            userImg.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                                            userImg.setVisibility(View.VISIBLE);
                                        } else if (comment.getCommentor_profile() != null && !comment.getCommentor_profile().isEmpty()) {
                                            userImg.setVisibility(View.VISIBLE);
                                            userImg.setImageUrl(comment.getCommentor_profile(), imageLoader);
                                        }
                                    }catch (Exception  e) {
                                        Log.e(TAG, "onClick: "   + e.getMessage() );

                                    }

                                    try{
                                        if (comment.getComment_photo_url().equals("") || comment.getComment_photo_url().equals("null")){
                                            imgMent.setVisibility(View.GONE);
                                            imgMentLand.setVisibility(View.GONE);

                                        }else{
                                            if(comment.getComment_dimen() != 0){
                                                if (comment.getComment_dimen() > 70){
                                                    imgMent.setVisibility(View.GONE);
                                                    imgMentLand.setVisibility(View.VISIBLE);
                                                    imgMentLand.setImageUrl(comment.getComment_photo_url(), imageLoader);
                                                }
                                                else if (comment.getComment_dimen() < 70){
                                                    imgMentLand.setVisibility(View.GONE);
                                                    imgMent.setVisibility(View.VISIBLE);
                                                    imgMent.setImageUrl(comment.getComment_photo_url(), imageLoader);
                                                }

                                            }
                                        }

                                    }catch (Exception e){
                                        Log.e(TAG, "onClick: "  + e.getMessage() );

                                    }




                                    txtComment.setText(comment.getBody());
                                    if (comment.getBody().length() > 50){
                                        LinearLayout.LayoutParams linearLayoutParams = (LinearLayout.LayoutParams)
                                                txtComment.getLayoutParams();
                                        linearLayoutParams.height = (int) (display.getHeight() * 0.2);
                                        txtComment.setLayoutParams(linearLayoutParams);
                                    }

                                    InputMethodManager inputMethod = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    txtComment.requestFocus();
                                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputMethod.showSoftInput(txtEdit, InputMethodManager.SHOW_IMPLICIT);

                                    txtCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogEdit.dismiss();
                                        }
                                    });

                                    txtUpdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (txtComment.getText().toString().trim().length() > 0){
                                                dialogEdit.dismiss();
                                                final Dialog editProgress = new Dialog(activity);
                                                editProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                editProgress.setContentView(R.layout.dialog_comment_delete);
                                                editProgress.setCancelable(false);
                                                editProgress.setCanceledOnTouchOutside(false);
                                                Window window = editProgress.getWindow();
                                                WindowManager.LayoutParams wlp = window.getAttributes();
                                                wlp.gravity = Gravity.CENTER;
                                                wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                                window.setAttributes(wlp);

                                                CustomTextView txtProgressStatus = editProgress.findViewById(R.id.txtProgressStatus);
                                                txtProgressStatus.setText("Updating comment...");
                                                editProgress.show();
                                                commentHolder.body.setText(txtComment.getText().toString());
                                                updateComment(comment.getId(), txtComment.getText().toString(), editProgress, comment, position, commentHolder);

                                            }else{

                                                ToastHelper.showToast(activity, resources.getString(R.string.fill_update));
                                            }
                                        }
                                    });

                                    dialogEdit.show();
                                }
                            });


                            txtDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                                    builder.setMessage("Are you sure you want to delete this comment?");

                                    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog1, int which) {

                                            dialog1.dismiss();

                                            final Dialog dialogDelete = new Dialog(activity);
                                            dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialogDelete.setContentView(R.layout.dialog_comment_delete);
                                            dialogDelete.setCancelable(false);
                                            dialogDelete.setCanceledOnTouchOutside(false);
                                            Window window = dialogDelete.getWindow();
                                            WindowManager.LayoutParams wlp = window.getAttributes();
                                            wlp.gravity = Gravity.CENTER;
                                            wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                            window.setAttributes(wlp);

                                            CustomTextView txtProgressStatus = dialogDelete.findViewById(R.id.txtProgressStatus);
                                            txtProgressStatus.setText("Deleting comment...");
                                            dialogDelete.show();
                                            deleteComment(comment.getId(), position, dialogDelete);






                                        }
                                    });

                                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog1, int which) {

                                            dialog1.dismiss();
                                        }
                                    });

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            });

                            txtCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                        }


                        return false;
                    }
                });


                break;

            }

            case VIEW_TYPE_CATEGORYF_DETAIL_ITEM:{
                boolean hideDiscount = false;
                int percentage = 0;
                int countText = 0;
                final ProductCategoryDetailItem catDetailHolder = (ProductCategoryDetailItem) parentHolder;
                final Product detailProduct = (Product) universalList.get(position);
                catDetailHolder.title.setText(detailProduct.getTitle());
                catDetailHolder.addtocart_text.setText(resources.getString(R.string.addtocart_buy));
                catDetailHolder.price.setText(formatter.format(detailProduct.getPrice()) +" "+activity.getResources().getString(R.string.currency));

                catDetailHolder.product_warning.setText(resources.getString(R.string.delivery_take_time));
                catDetailHolder.product_warning.setTypeface(catDetailHolder.product_warning.getTypeface(), Typeface.BOLD);

                int checkProductWarning1 = 0;
                if (detailProduct.getChannel().equals("all_channels")  &&  detailProduct.getRecommended().trim().length() == 0 ){
                    /*if (prefs.getIntPreferences(SP_TOWNSHIP_STORE_ID) == -1 ){
                        checkProductWarning1 = 0;
                    }
                    else {
                        for(int i=0; i < detailProduct.getStockSummeriesList().size(); i++){
                            StockSummeries stockSummeries = detailProduct.getStockSummeriesList().get(i);

                            if (stockSummeries.getStore_location_id() == prefs.getIntPreferences(SP_TOWNSHIP_STORE_ID)){
                                if(stockSummeries.getQuantity() <= 0){
                                    checkProductWarning1 = 0;

                                }

                            }
                        }
                    }*/
                    checkProductWarning1 = 1;
                }

                if (checkProductWarning1 == 1){
                    //catDetailHolder.product_warning.setVisibility(View.GONE);
                    catDetailHolder.product_warning.setText(resources.getString(R.string.delivery_take_time));
                    catDetailHolder.imgCar.setImageDrawable(activity.getResources().getDrawable(R.drawable.car_one));
                    catDetailHolder.imgCar.setVisibility(View.VISIBLE);

                }else{
                    //catDetailHolder.product_warning.setVisibility(View.VISIBLE);
                    catDetailHolder.product_warning.setText(resources.getString(R.string.delivery_take_time_option));
                    catDetailHolder.imgCar.setImageDrawable(activity.getResources().getDrawable(R.drawable.car_option));
                    catDetailHolder.imgCar.setVisibility(View.VISIBLE);
                }

                if(detailProduct.getRecommended() != null &&  detailProduct.getRecommended().trim().length() > 0){
                    catDetailHolder.recommended.setVisibility(View.GONE);
                    catDetailHolder.txtMark.setVisibility(View.GONE);
                    catDetailHolder.soldoutcart_text.setText( " " + " " + " SOLD OUT " + " " + " " );
                    // catDetailHolder.addtocart_text.setPadding(10,10,10,10);

                }
                else  if(detailProduct.getChannel() != null && detailProduct.getChannel().trim().length() > 0){

                    catDetailHolder.imgCart.setVisibility(View.VISIBLE);

                    if (!detailProduct.getChannel().equals("all_channels")){

                        //catDetailHolder.txtAllChannel.setText(resources.getString(R.string.online_order_only));
                        catDetailHolder.txtAllChannel.setTypeface(catDetailHolder.txtAllChannel.getTypeface(), Typeface.BOLD);
                        catDetailHolder.txtMark.setText(resources.getString(R.string.pre_order));
                        catDetailHolder.txtMark.setTypeface(catDetailHolder.txtAllChannel.getTypeface(), Typeface.BOLD);
                        catDetailHolder.txtMark.setVisibility(View.VISIBLE);
                        catDetailHolder.txtAllChannel.setVisibility(View.GONE);


                    }
                    else{
                        catDetailHolder.txtAllChannel.setVisibility(View.GONE);
                        catDetailHolder.txtMark.setVisibility(View.GONE);
                    }
                }else {



                    catDetailHolder.imgCart.setVisibility(View.VISIBLE);
                    catDetailHolder.txtAllChannel.setVisibility(View.GONE);
                    catDetailHolder.txtMark.setVisibility(View.GONE);
                }


                if (detailProduct.getPoint_usage() == 0){
                    catDetailHolder.imgGetPoint.setImageDrawable(activity.getResources().getDrawable(R.drawable.info));
                }else{
                    catDetailHolder.imgGetPoint.setImageDrawable(activity.getResources().getDrawable(R.drawable.info));
                }


                if(detailProduct.getCategory_name() != null && detailProduct.getCategory_name().trim().length() > 0){
                    catDetailHolder.category_name.setVisibility(View.VISIBLE);
                    catDetailHolder.category_name.setText(detailProduct.getCategory_name());
                }else {
                    catDetailHolder.category_name.setVisibility(View.GONE);
                }

                if (detailProduct.getPreviewImage() != null && !(detailProduct.getPreviewImage().equals(""))) {
                    catDetailHolder.imageView.setImageUrl(detailProduct.getPreviewImage(), AppController.getInstance().getImageLoader());

                }





                if(detailProduct.getOptions() != null && detailProduct.getOptions().trim().length() > 0){
                    catDetailHolder.option.setVisibility(View.VISIBLE);
                    catDetailHolder.option.setText(detailProduct.getOptions());

                }else {
                    catDetailHolder.option.setVisibility(View.GONE);
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
                                            if(dis.getPercentage() > percentage)
                                                percentage = dis.getPercentage();
                                        }else if(dis.getDiscountType().equals(DISCOUNT_GIFT)){

                                            str = str.concat(dis.getCampaign_info());
                                        }
                                    }
                                    if(str.trim().length() > 0){
                                        hideDiscount = true;
                                        catDetailHolder.discounts.setVisibility(View.VISIBLE);
                                        catDetailHolder.discounts.setText(resources.getString(R.string.gift));
                                    }
                                }else {
                                    if(dis.getDiscountType().equals(DISCOUNT_PERCENTAGE)){
                                        if(dis.getPercentage() > percentage)
                                            percentage = dis.getPercentage();
                                    }else if(dis.getDiscountType().equals(DISCOUNT_GIFT)){
                                        str = str.concat(dis.getCampaign_info());
                                    }

                                    if(str.trim().length() > 0){
                                        catDetailHolder.discounts.setVisibility(View.VISIBLE);
                                        catDetailHolder.discounts.setText(resources.getString(R.string.gift));
                                        hideDiscount = true;
                                    }else{
                                        hideDiscount = false;

                                        catDetailHolder.discounts.setVisibility(View.GONE);
                                    }
                                }

                            }



                        }else{

                            catDetailHolder.discounts.setVisibility(View.GONE);
                        }


                    }


                }
                else{

                    percentage = detailProduct.getFlashSalesArrayList().get(0).getDiscount();
                    cat_total_discount =  ( detailProduct.getPrice() * detailProduct.getFlashSalesArrayList().get(0).getDiscount()) / 100 ;
                }


                if(detailProduct.getMember_discount() != 0 && prefs.getIntPreferences(SP_VIP_ID) != 0 ) {
                    cat_total_discount += ((detailProduct.getPrice() - cat_total_discount)*
                            prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) / 100);
                }

                catDetailHolder.imgGetPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);

                        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_get_point_info, null);
                        mBottomSheetDialog.setContentView(sheetView);
                        mBottomSheetDialog.setCancelable(true);


                        CustomTextView title = mBottomSheetDialog.findViewById(R.id.bottomTitle);
                        CustomTextView text = mBottomSheetDialog.findViewById(R.id.bottomText);

                        title.setText(resources.getString(R.string.point_product_title));



                        String str = "";

                        if (detailProduct.getMember_discount() == 1 && prefs.getIntPreferences(SP_VIP_ID) != 0 ){
                            str += "- " + resources.getString(R.string.member_discount)
                                    + " " + prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) + "% " + "\n";
                        }
                        else{
                            str += "- " + resources.getString(R.string.memeber_product_cannot) +"\n";
                        }

                        if (detailProduct.getFlashSalesArrayList().size() > 0){
                            str += "- " + resources.getString(R.string.flash_sale_discount)
                                    + " " + detailProduct.getFlashSalesArrayList().get(0).getDiscount() + "% " + "\n";
                        }

                        if (detailProduct.getPoint_usage() == 0){
                            //str += "- " +  String.format(resources.getString(R.string.point_product_can) , String.valueOf( prefs.getIntPreferences(SP_POINT_PERCENTAGE) ));
                            str +=   resources.getString(R.string.point_product_can);
                        }else{
                            str += "- " +  resources.getString(R.string.point_product_cannot) ;
                        }

                        text.setText(str);

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("product_id", String.valueOf(detailProduct.getId()));
                            FlurryAgent.logEvent("Click Info Tag", mix);
                        } catch (Exception e) {
                        }

                        mBottomSheetDialog.show();
                    }
                });





                if(percentage != 0 && flashSaleCheck1 == false ) {

                    catDetailHolder.member_discount_flash_sale.setVisibility(View.GONE);
                    catDetailHolder.member_discount.setVisibility(View.VISIBLE);
                    catDetailHolder.price_strike.setVisibility(View.VISIBLE);
                    catDetailHolder.member_discount.setText( percentage + "%");

                    catDetailHolder.price_strike.setText(formatter.format(detailProduct.getPrice()) +" "+activity.getResources().getString(R.string.currency));
                    catDetailHolder.price_strike.setPaintFlags(catDetailHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    catDetailHolder.price.setText(formatter.format(detailProduct.getPrice() - (detailProduct.getPrice() * percentage) / 100 )+
                            " "+resources.getString(R.string.currency));
                    countText = countText + 1 ;
                }

                else  if(percentage != 0 && flashSaleCheck1 == true ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {
                        Drawable leftDrawable = AppCompatResources
                                .getDrawable(activity, R.drawable.ic_flash_on_black_24dp);
                        catDetailHolder.member_discount_flash_sale.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
                    }
                    catDetailHolder.discounts.setVisibility(View.GONE);
                    catDetailHolder.member_discount.setVisibility(View.GONE);
                    catDetailHolder.member_discount_flash_sale.setVisibility(View.VISIBLE);
                    catDetailHolder.price_strike.setVisibility(View.VISIBLE);
                    catDetailHolder.member_discount_flash_sale.setText( percentage + "%");

                    catDetailHolder.price_strike.setText(formatter.format(detailProduct.getPrice()) +" "+activity.getResources().getString(R.string.currency));
                    catDetailHolder.price_strike.setPaintFlags(catDetailHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    catDetailHolder.price.setText(formatter.format(detailProduct.getPrice() - (detailProduct.getPrice() * percentage) / 100 )+
                            " "+resources.getString(R.string.currency));
                    countText = countText + 1 ;
                }
                else{

                    catDetailHolder.member_discount_flash_sale.setVisibility(View.GONE);
                    catDetailHolder.member_discount.setVisibility(View.GONE);
                    catDetailHolder.price_strike.setVisibility(View.GONE);
                }
                //

                if(hideDiscount) {
                    catDetailHolder.discounts.setVisibility(View.VISIBLE);
                    countText = countText + 1 ;
                }
                else
                    catDetailHolder.discounts.setVisibility(View.GONE);

                if (countText < 3 ){
                    catDetailHolder.discounts.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    catDetailHolder.member_discount_flash_sale.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    catDetailHolder.member_discount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    catDetailHolder.recommended.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                }
                else{
                    catDetailHolder.discounts.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                    catDetailHolder.member_discount_flash_sale.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                    catDetailHolder.member_discount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                    catDetailHolder.recommended.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                }



                catDetailHolder.imageView.setOnClickListener(new MediaAdapter.OnClickProduct(activity, detailProduct));
                catDetailHolder.text_layout.setOnClickListener(new MediaAdapter.OnClickProduct(activity, detailProduct));




                if(detailProduct.getRecommended() != null &&  detailProduct.getRecommended().trim().length() > 0){
                    catDetailHolder.soldOutCart.setVisibility(View.VISIBLE);
                    catDetailHolder.addtocart.setVisibility(View.GONE);
                }else{
                    catDetailHolder.addtocart.setVisibility(View.VISIBLE);
                    catDetailHolder.soldOutCart.setVisibility(View.GONE);
                }

                catDetailHolder.soldOutCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showWarningDialog(resources.getString(R.string.online_order_only));

                    }
                });

                catDetailHolder.addtocart.setOnClickListener(new MediaAdapter.CategoryAddtoCartClickListener(detailProduct,  activity,
                        1, (detailProduct.getPrice() - cat_total_discount)));


                break;

            }

            case VIEW_TYPE_USER_POST_READING_DETAILS:{
                final ImageHolder detailholder1 = (ImageHolder) parentHolder;
                final Reading detailPost1 = (Reading) universalList.get(position);

                detailholder1.title.setVisibility(View.VISIBLE);


                try{
                    if (detailPost1.getCustomer_type().equals("normal_customer")){
                        if (detailPost1.getSort_by().equals("unknown_parent_status") || detailPost1.getSort_by().equals("other")){
                            detailholder1.babyage.setText(resources.getString(R.string.family_member));
                            detailholder1.babyage.setVisibility(View.VISIBLE);
                        }else{

                            String returnString1 =  ageConfig.calculateKidAge(detailPost1.getBirth_month(), detailPost1.getBirth_year(),
                                    detailPost1.getCategory_type(), detailPost1.getSort_by() ) ;

                            detailholder1.babyage.setText(returnString1);
                            detailholder1.babyage.setVisibility(View.VISIBLE);
                        }

                        detailholder1.txtType.setVisibility(View.GONE);

                    }else if (detailPost1.getCustomer_type().trim().length() > 0 ){
                        detailholder1.babyage.setVisibility(View.GONE);
                        detailholder1.txtType.setVisibility(View.VISIBLE);
                        detailholder1.txtType.setTypeface(detailholder1.title.getTypeface(), Typeface.BOLD);
                        detailholder1.txtType.setText(  detailPost1.getCustomer_type());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        {
                            Drawable leftDrawable = AppCompatResources
                                    .getDrawable(activity, R.drawable.ic_stars_grey);
                            detailholder1.txtType.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
                        }


                    }





                }catch (Exception e){
                    //detailholder1.title.setText("");
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }

                detailholder1.title.setText(detailPost1.getPage_name());
                detailholder1.title.setTypeface(detailholder1.title.getTypeface(), Typeface.BOLD);

                if(detailPost1.getDimen() > 0){
                    detailholder1.frame.setVisibility(View.VISIBLE);
                    detailholder1.imageView.getLayoutParams().height = (detailPost1.getDimen() * display.getWidth() ) / 100;
                    detailholder1.imageView.setImageUrl(detailPost1.getPhoto_url(), imageLoader);
                }
                if(detailPost1.getBody() != null && detailPost1.getBody().trim().length() > 0){
                    detailholder1.body.setVisibility(View.VISIBLE);
                    detailholder1.body.setText(detailPost1.getBody());

                    Linkify.addLinks(detailholder1.body,Linkify.ALL);
                    detailholder1.body.setLineSpacing(1.0f, 1.0f);
                    //  detailholder.body.setLetterSpacing(0.1f);

                }
                if(detailPost1.getRelease_at() != null && detailPost1.getRelease_at().trim().length() > 0){
                    detailholder1.time.setVisibility(View.VISIBLE);
                    detailholder1.time.setText(detailPost1.getRelease_at());
                }

                if(detailPost1.getPage_img_url() != null && detailPost1.getPage_img_url().trim().length() > 0){
                    detailholder1.pageImage.setVisibility(View.VISIBLE);
                    detailholder1.pageImage.setImageUrl(detailPost1.getPage_img_url(), imageLoader);
                }else{
                    detailholder1.pageImage.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                    detailholder1.pageImage.setVisibility(View.VISIBLE);
                }

                detailholder1.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("comment_id", String.valueOf(detailPost1.getId()));
                                mix.put("type", "image" );
                                FlurryAgent.logEvent("Click User Post Image", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", detailPost1.getPhoto_url());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        } catch (Exception e) {
                            Log.e(TAG, "onClick: "  + e.getMessage() );
                        }
                    }
                });



                break;

            }

            case VIEW_TYPE_READING_DETAIL:{
                final ImageHolder detailholder = (ImageHolder) parentHolder;
                final Reading detailPost = (Reading) universalList.get(position);


                Log.e(TAG, "onBindViewHolder: !!!!!!!!!!!!! "  + detailPost.getPage_name() );

                if(detailPost.getPage_name() != null && detailPost.getPage_name().trim().length() > 0){
                    detailholder.title.setVisibility(View.VISIBLE);
                    detailholder.title.setText(detailPost.getPage_name());
                    detailholder.title.setTypeface(detailholder.title.getTypeface(), Typeface.BOLD);
                }

                if(detailPost.getTitle() != null && detailPost.getTitle().trim().length() > 0){
                    detailholder.titleText.setVisibility(View.VISIBLE);
                    detailholder.titleText.setText(detailPost.getTitle());
                    detailholder.titleText.setTypeface(detailholder.titleText.getTypeface(), Typeface.BOLD);
                }

                if(detailPost.getDimen() > 0){
                    detailholder.frame.setVisibility(View.VISIBLE);
                    detailholder.imageView.getLayoutParams().height = (detailPost.getDimen() * display.getWidth() ) / 100;
                    detailholder.imageView.setImageUrl(detailPost.getPhoto_url(), imageLoader);
                }
                if(detailPost.getBody() != null && detailPost.getBody().trim().length() > 0){
                    detailholder.body.setVisibility(View.VISIBLE);
                    detailholder.body.setText(detailPost.getBody());

                    Linkify.addLinks(detailholder.body,Linkify.ALL);
                    detailholder.body.setLineSpacing(1.0f, 1.0f);

                }
                if(detailPost.getRelease_at() != null && detailPost.getRelease_at().trim().length() > 0){
                    detailholder.time.setVisibility(View.VISIBLE);
                    detailholder.time.setText(detailPost.getRelease_at());
                }

                if(detailPost.getPage_img_url() != null && detailPost.getPage_img_url().trim().length() > 0){
                    detailholder.pageImage.setVisibility(View.VISIBLE);
                    detailholder.pageImage.setImageUrl(detailPost.getPage_img_url(), imageLoader);
                }else{
                    detailholder.pageImage.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                    detailholder.pageImage.setVisibility(View.VISIBLE);
                }



                break;


            }

            case VIEW_TYPE_READING:{
                final MainRichtextHolder richtextHolder = (MainRichtextHolder) parentHolder;
                final Reading reading  = (Reading) universalList.get(position);
                if(reading.getDimen() > 0) {
                    richtextHolder.frame.setVisibility(View.VISIBLE);
                    richtextHolder.imageView.getLayoutParams().height = (reading.getDimen() * display.getWidth()) / 100;
                    richtextHolder.imageView.setImageUrl(reading.getPhoto_url(), imageLoader);
                }
                try {
                    richtextHolder.page_time.setText(getDateInMillis(reading.getRelease_at()));

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }

                if(reading.getTitle() != null  && reading.getTitle().trim().length() > 0) {
                    richtextHolder.title.setVisibility(View.VISIBLE);
                    richtextHolder.title.setText(reading.getTitle());
                    richtextHolder.title.setLineSpacing(1.8f, 1.0f);
                    richtextHolder.title.setTypeface(richtextHolder.title.getTypeface(), Typeface.BOLD);

                }else
                    richtextHolder.title.setVisibility(View.GONE);

                if(reading.getBody() != null && reading.getBody().trim().length() > 0){
                    richtextHolder.body.setVisibility(View.VISIBLE);
                    if(reading.getBody().length() > 150)
                        richtextHolder.body.setText(reading.getBody().substring(0, 150)+"  ..... ");
                    else
                        richtextHolder.body.setText(reading.getBody());



                    Linkify.addLinks(richtextHolder.body,Linkify.ALL);
                }else
                    richtextHolder.body.setVisibility(View.GONE);


                if(reading.getPage_name() != null && reading.getPage_name().trim().length() > 0){
                    richtextHolder.pageName.setText(reading.getPage_name());
                    richtextHolder.pageName.setVisibility(View.VISIBLE);
                    richtextHolder.pageName.setTypeface(richtextHolder.pageName.getTypeface(), Typeface.BOLD);
                }

                if(reading.getPage_img_url() != null && reading.getPage_img_url().trim().length() > 0){

                    richtextHolder.pageImage.setImageUrl(reading.getPage_img_url(), imageLoader);
                    richtextHolder.pageImage.setVisibility(View.VISIBLE);
                }

                if(databaseAdapter.checkPostLiked(reading.getId())){
                    richtextHolder.like_image.setImageResource(R.drawable.wishlist_clicked);
                }else {
                    richtextHolder.like_image.setImageResource(R.drawable.wishlist);
                }

                if(reading.getLikes() > 0 || databaseAdapter.checkPostLiked(reading.getId())) {
                    richtextHolder.likes.setVisibility(View.VISIBLE);
                    richtextHolder.likes.setText(reading.getLikes() + " Likes");

                    if(databaseAdapter.checkPostLiked(reading.getId())){
                        richtextHolder.like_image.setImageResource(R.drawable.wishlist_clicked);
                        if(reading.getLikes() == 0)
                            richtextHolder.likes.setText(reading.getLikes()+1 + " Like");
                    }else {
                        richtextHolder.like_image.setImageResource(R.drawable.wishlist);
                    }
                }
                else {
                    richtextHolder.likes.setVisibility(View.GONE);
                }

                if(reading.getComment_coount() > 0) {
                    richtextHolder.comment_count.setText(reading.getComment_coount()+" Comments");
                    richtextHolder.comment_count.setVisibility(View.VISIBLE);
                }else
                    richtextHolder.comment_count.setVisibility(View.GONE);

                richtextHolder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(reading.getId()));
                            FlurryAgent.logEvent("Click Post", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("comment", "read");
                        bundle.putInt("id", reading.getId());
                        bundle.putString("page_url", reading.getPage_img_url());
                        bundle.putString("title", reading.getTitle());
                        bundle.putInt("like_count", reading.getLikes());
                        bundle.putInt("comment_count", reading.getComment_coount());
                        bundle.putString("page_url", reading.getPage_img_url());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                richtextHolder.reading_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(reading.getId()));
                            FlurryAgent.logEvent("Click Post", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("comment", "read");
                        bundle.putInt("id", reading.getId());
                        bundle.putString("title", reading.getTitle());
                        bundle.putInt("like_count", reading.getLikes());
                        bundle.putInt("comment_count", reading.getComment_coount());
                        bundle.putString("page_url", reading.getPage_img_url());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                richtextHolder.comment_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(reading.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }


                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", reading.getId());
                        bundle.putString("page_url", reading.getPage_img_url());
                        bundle.putString("title", reading.getTitle());
                        bundle.putInt("like_count", reading.getLikes());
                        bundle.putInt("comment_count", reading.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                    }
                });
                richtextHolder.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(reading.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }


                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("page_url", reading.getPage_img_url());
                        bundle.putInt("id", reading.getId());
                        bundle.putString("title", reading.getTitle());
                        bundle.putInt("like_count", reading.getLikes());
                        bundle.putInt("comment_count", reading.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                    }
                });

                richtextHolder.likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Bundle fbundle = new Bundle();
                        fbundle.putString("item_id", reading.getId()+"");
                        fbundle.putString("item_category", "post");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, fbundle);

                        Intent intent   = new Intent(activity, PostLikedActivity.class);
                        intent.putExtra("post_id", reading.getId());
                        activity.startActivity(intent);
                    }
                });
                richtextHolder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        richtextHolder.likes.setVisibility(View.VISIBLE);
                        sendLike(reading.getId(), richtextHolder, reading);

                    }
                });

                richtextHolder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(reading.getId()));
                            FlurryAgent.logEvent("Click Post Share Icon", mix);
                        } catch (Exception e) {
                        }


                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", reading.getId()+"");
                        bundle.putString("content_type","post");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, reading.getUrl());
                        activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                    }
                });

                richtextHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_more);

                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        final CustomTextView subscribe_text = (CustomTextView) dialog.findViewById(R.id.subscribe_text);
                        CustomSwitchView subscribe   = (CustomSwitchView) dialog.findViewById(R.id.subscribe);
                        CustomTextView like_text     = (CustomTextView) dialog.findViewById(R.id.like_text);
                        LinearLayout like_layout        = (LinearLayout) dialog.findViewById(R.id.like_layout);

                        like_text.setText(resources.getString(R.string.post_liked));

                        if(databaseAdapter.getSubscribe(reading.getId())){
                            subscribe.setChecked(true);
                            subscribe_text.setText(resources.getString(R.string.unsubscribe));
                        }else{
                            subscribe.setChecked(false);
                            subscribe_text.setText(resources.getString(R.string.subscribe));
                        }

                        subscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                if(b){
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+reading.getId());
                                        FlurryAgent.logEvent("SubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.insertPostSubscribe(reading.getId());
                                    FirebaseMessaging.getInstance().subscribeToTopic("post_"+reading.getId());
                                    subscribe_text.setText(resources.getString(R.string.unsubscribe));
                                }else {
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+reading.getId());
                                        FlurryAgent.logEvent("UnsubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.deleteSubscribe(reading.getId());
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("post_"+reading.getId());
                                    subscribe_text.setText(resources.getString(R.string.subscribe));

                                }
                            }
                        });

                        like_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent   = new Intent(activity, PostLikedActivity.class);
                                intent.putExtra("post_id", reading.getId());
                                activity.startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                break;
            }

            case VIEW_TYPE_READING_PRODUCT :{
                ArticleProductHolder articleHolder = (ArticleProductHolder) parentHolder;
                final Reading_Post readingProduct = (Reading_Post) universalList.get(position);
                articleHolder.title.setText(readingProduct.getTitle());

                if (readingProduct.getPreview_url().trim().length() != 0){
                    articleHolder.imgProduct.setImageUrl(readingProduct.getPreview_url(), imageLoader);
                }else{
                    articleHolder.imgProduct.setDefaultImageResId(R.drawable.ic_launcher);
                }

                articleHolder.txtWatch.setText(resources.getString(R.string.watch_details));
                articleHolder.txtWatch.setPaintFlags(articleHolder.txtWatch.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                if (readingProduct.getBody().trim().length() != 0){
                    articleHolder.txtBody.setText(readingProduct.getBody());
                    articleHolder.txtBody.setVisibility(View.VISIBLE);
                }else{
                    articleHolder.txtBody.setVisibility(View.GONE);
                }
                articleHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", "product");
                            FlurryAgent.logEvent("Article Product", mix);


                        } catch (Exception e) {
                        }
                        getProduct(readingProduct.getYoutube_id());
                    }
                });

                break;

            }

            case VIEW_TYPE_READING_VIDEO:{
                final MainRichtextHolder richVideo = (MainRichtextHolder) parentHolder;
                final Reading video  = (Reading) universalList.get(position);
                if(video.getDimen() > 0) {
                    richVideo.frame.setVisibility(View.VISIBLE);
                    richVideo.imageView.getLayoutParams().height = (video.getDimen() * display.getWidth()) / 100;
                    richVideo.imageView.setImageUrl(video.getPhoto_url(), imageLoader);
                }

                richVideo.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, Youtube.class);
                        intent.putExtra("youtubeID", video.getBody());
                        activity.startActivity(intent);
                    }
                });
                try {
                    richVideo.page_time.setText(getDateInMillis(video.getRelease_at()));

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onBindViewHolder: "   + e.getMessage() );
                }
                if(video.getTitle() != null  && video.getTitle().trim().length() > 0) {
                    richVideo.title.setVisibility(View.VISIBLE);
                    richVideo.title.setText(video.getTitle());
                    richVideo.title.setLineSpacing(1.8f, 1.0f);
                }else
                    richVideo.title.setVisibility(View.GONE);


                if(video.getPage_name() != null && video.getPage_name().trim().length() > 0){
                    richVideo.pageName.setText(video.getPage_name());
                    richVideo.pageName.setVisibility(View.VISIBLE);
                }

                if(video.getPage_img_url() != null && video.getPage_img_url().trim().length() > 0){

                    richVideo.pageImage.setImageUrl(video.getPage_img_url(), imageLoader);
                    richVideo.pageImage.setVisibility(View.VISIBLE);
                }

                if(databaseAdapter.checkPostLiked(video.getId())){
                    richVideo.like_image.setImageResource(R.drawable.wishlist_clicked);
                }else {
                    richVideo.like_image.setImageResource(R.drawable.wishlist);
                }

                if(video.getLikes() > 0 || databaseAdapter.checkPostLiked(video.getId())) {
                    richVideo.likes.setVisibility(View.VISIBLE);
                    if(video.getLikes() == 1)
                        richVideo.likes.setText(video.getLikes() + " Like");
                    else
                        richVideo.likes.setText(video.getLikes() + " Likes");

                    if(databaseAdapter.checkPostLiked(video.getId())){
                        richVideo.like_image.setImageResource(R.drawable.wishlist_clicked);
                        if(video.getLikes() == 0)
                            richVideo.likes.setText(video.getLikes()+1 + " Like");
                    }else {
                        richVideo.like_image.setImageResource(R.drawable.wishlist);
                    }
                }
                else {
                    richVideo.likes.setVisibility(View.GONE);
                }

                if(video.getComment_coount() > 0) {
                    if(video.getComment_coount() == 1)
                        richVideo.comment_count.setText(video.getComment_coount()+" Comment");
                    else
                        richVideo.comment_count.setText(video.getComment_coount()+" Comments");

                    richVideo.comment_count.setVisibility(View.VISIBLE);
                }else
                    richVideo.comment_count.setVisibility(View.GONE);


                richVideo.comment_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(video.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }


                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", video.getId());
                        bundle.putString("title", video.getTitle());
                        bundle.putInt("like_count", video.getLikes());
                        bundle.putInt("comment_count", video.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                    }
                });
                richVideo.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(video.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }


                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", video.getId());
                        bundle.putString("title", video.getTitle());
                        bundle.putInt("like_count", video.getLikes());
                        bundle.putInt("comment_count", video.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                    }
                });

                richVideo.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        richVideo.likes.setVisibility(View.VISIBLE);
                        sendLike(video.getId(), richVideo, video);

                    }
                });

                richVideo.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(video.getId()));
                            FlurryAgent.logEvent("Click Post Share Icon", mix);
                        } catch (Exception e) {
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", video.getId()+"");
                        bundle.putString("content_type","video");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, video.getUrl());
                        activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                    }
                });

                richVideo.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_more);

                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        final CustomTextView subscribe_text = (CustomTextView) dialog.findViewById(R.id.subscribe_text);
                        CustomSwitchView subscribe   = (CustomSwitchView) dialog.findViewById(R.id.subscribe);
                        CustomTextView like_text     = (CustomTextView) dialog.findViewById(R.id.like_text);
                        LinearLayout like_layout        = (LinearLayout) dialog.findViewById(R.id.like_layout);

                        like_text.setText(resources.getString(R.string.post_liked));

                        if(databaseAdapter.getSubscribe(video.getId())){
                            subscribe.setChecked(true);
                            subscribe_text.setText(resources.getString(R.string.unsubscribe));
                        }else{
                            subscribe.setChecked(false);
                            subscribe_text.setText(resources.getString(R.string.subscribe));
                        }

                        subscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(b){
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+video.getId());
                                        FlurryAgent.logEvent("SubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.insertPostSubscribe(video.getId());
                                    FirebaseMessaging.getInstance().subscribeToTopic("post_"+video.getId());
                                    subscribe_text.setText(resources.getString(R.string.unsubscribe));
                                }else {
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+video.getId());
                                        FlurryAgent.logEvent("UnsubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.deleteSubscribe(video.getId());
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("post_"+video.getId());
                                    subscribe_text.setText(resources.getString(R.string.subscribe));

                                }
                            }
                        });

                        like_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent   = new Intent(activity, PostLikedActivity.class);
                                intent.putExtra("post_id", video.getId());
                                activity.startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                break;

            }

             case VIEW_TYPE_VIDEO_ADS_IMAGE:{
                DetailImageHolder imageHolder1 = (DetailImageHolder) parentHolder;
                final Images image2 = (Images) universalList.get(position);

                int heightImg1 = ( image2.getDimen()  * activity.getWindowManager().getDefaultDisplay().getWidth()) / 100;
                imageHolder1.img.getLayoutParams().height = heightImg1;
                imageHolder1.img.getLayoutParams().width = activity.getWindowManager().getDefaultDisplay().getWidth();
                imageHolder1.img.setImageUrl(image2.getUrl(), imageLoader);
                imageHolder1.txtHidden.setVisibility(View.VISIBLE);

                imageHolder1.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (image2.getId() == 1 ){
                            try {
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(image2.getName()));
                                activity.startActivity(myIntent);
                            }catch(ActivityNotFoundException e){
                                Log.e(TAG, "onClick: "  + e.getMessage() );
                            }
                        }
                        else if (image2.getId() == 2){
                            Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", image2.getName());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    }
                });
                break;
            }

            case VIEW_TYPE_VIDEO_ALL_CLICK:{
                final VideoAllHolder videoClickHolder2  = (VideoAllHolder) parentHolder;
                final Videos videoProgram3  = (Videos) universalList.get(position);
                if(videoProgram3.getDimen() > 0) {
                    videoClickHolder2.frame.setVisibility(View.VISIBLE);
                    videoClickHolder2.imageView.getLayoutParams().height = (videoProgram3.getDimen() * display.getWidth()) / 100;
                    videoClickHolder2.imageView.setImageUrl(videoProgram3.getPreview_url(), imageLoader);
                }

                if(videoProgram3.getTitle() != null  && videoProgram3.getTitle().trim().length() > 0) {
                    videoClickHolder2.title.setVisibility(View.VISIBLE);
                    videoClickHolder2.title.setText(videoProgram3.getTitle());
                    videoClickHolder2.title.setLineSpacing(1.8f, 1.0f);
                }else
                    videoClickHolder2.title.setVisibility(View.GONE);



                videoClickHolder2.body.setVisibility(View.GONE);
                videoClickHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "video_program");
                            mix.put("post_id", String.valueOf(videoProgram3.getId()));
                            FlurryAgent.logEvent("Videos Program Click Item", mix);
                        } catch (Exception e) {
                        }
                        prefs.saveIntPerferences(FIRST_PLAY, 1);
                        Intent intent = new Intent(activity, YouTubeDialog.class);
                        intent.putExtra("youtube_object", videoProgram3);
                        activity.startActivity(intent);
                    }
                });

                break;

            }

            case VIEW_TYPE_READING_YOUTUBE:{
                TextHolder youtubeholder = (TextHolder) parentHolder;
                final Reading_Post youtubePost = (Reading_Post) universalList.get(position);




                if(youtubePost.getTitle().trim().length() > 0){
                    youtubeholder.title.setVisibility(View.VISIBLE);
                    youtubeholder.title.setText(youtubePost.getTitle());
                }
                else
                    youtubeholder.title.setVisibility(View.GONE);

                if(youtubePost.getDimen() > 0){
                    youtubeholder.layout_watch.setVisibility(View.VISIBLE);
                    youtubeholder.imageframe.setVisibility(View.VISIBLE);
                    youtubeholder.txtWatch.setText(resources.getString(R.string.video_play));
                    youtubeholder.image.getLayoutParams().height = (youtubePost.getDimen() * display.getWidth() ) / 100;
                    youtubeholder.image.setImageUrl(youtubePost.getPreview_url(), imageLoader);
                    youtubeholder.layout_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, Youtube.class);
                            intent.putExtra("youtubeID", youtubePost.getYoutube_id());
                            activity.startActivity(intent);
                        }
                    });
                }
                else {
                    youtubeholder.imageframe.setVisibility(View.GONE);
                    youtubeholder.layout_watch.setVisibility(View.GONE);

                }
                youtubeholder.layout_watch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, Youtube.class);
                        intent.putExtra("youtubeID", youtubePost.getYoutube_id());
                        activity.startActivity(intent);
                    }
                });

                if(youtubePost.getBody()!= null && youtubePost.getBody().trim().length() > 0){
                    youtubeholder.body.setVisibility(View.VISIBLE);
                    youtubeholder.body.setText(youtubePost.getBody());

                }
                else
                    youtubeholder.body.setVisibility(View.GONE);

                break;
            }

            case VIEW_TYPE_READING_LIKED:{
                final Comment postliked = (Comment) universalList.get(position);
                PostLikedHolder postLikedHolder = (PostLikedHolder) parentHolder;

                if(postliked.getCommentor() != null && postliked.getCommentor().trim().length() > 0) {
                    if(postliked.getParent_status().equals("unknown_parent_status" ) || postliked.getParent_status().equals("other" )){
                        postLikedHolder.title.setText(postliked.getCommentor()  + " - " + resources.getString(R.string.family_member));
                        postLikedHolder.title.setVisibility(View.VISIBLE);

                    }
                    else{
                        postLikedHolder.title.setText(postliked.getCommentor() + " - " + ageConfig.calculateKidAge(postliked.getBirth_month(),
                                postliked.getBirth_year(), postliked.getKid_gender(), postliked.getParent_status()));
                        postLikedHolder.title.setVisibility(View.VISIBLE);
                    }


                }else{
                    postLikedHolder.title.setVisibility(View.GONE);

                }


                break;

            }

            case VIEW_TYPE_READING_API :{
                ArticleProductHolder apiHolder = (ArticleProductHolder) parentHolder;
                final Reading_Post readingAPI = (Reading_Post) universalList.get(position);
                apiHolder.title.setText(readingAPI.getTitle());

                if (readingAPI.getPreview_url().trim().length() != 0){
                    apiHolder.imgProduct.setImageUrl(readingAPI.getPreview_url(), imageLoader);
                }else{
                    apiHolder.imgProduct.setDefaultImageResId(R.drawable.ic_launcher);
                }

                apiHolder.txtWatch.setText(resources.getString(R.string.watch_details));
                apiHolder.txtWatch.setPaintFlags(apiHolder.txtWatch.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);


                if (readingAPI.getBody().trim().length() != 0){
                    apiHolder.txtBody.setText(readingAPI.getBody());
                    apiHolder.txtBody.setVisibility(View.VISIBLE);
                }else{
                    apiHolder.txtBody.setVisibility(View.GONE);
                }
                apiHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", "api");
                            FlurryAgent.logEvent("Article Product", mix);
                        } catch (Exception e) {
                        }
                        JsonArrayRequest jsonArrayRequest = productList(readingAPI.getYoutube_id());
                        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                    }
                });

                break;

            }

            case VIEW_TYPE_READING_TEXE:{
                TextHolder textholder = (TextHolder) parentHolder;
                Reading_Post textPost = (Reading_Post) universalList.get(position);


                if(textPost.getTitle().trim().length() > 0){
                    textholder.title.setVisibility(View.VISIBLE);
                    textholder.title.setText(textPost.getTitle());
                }
                else{
                    textholder.title.setVisibility(View.GONE);
                }

                if(textPost.getDimen() > 0){
                    textholder.imageframe.setVisibility(View.VISIBLE);
                    textholder.image.getLayoutParams().height = (textPost.getDimen() * display.getWidth() ) / 100;
                    textholder.image.setImageUrl(textPost.getPreview_url(), imageLoader);
                }
                else{
                    textholder.imageframe.setVisibility(View.GONE);
                }
                if(textPost.getBody().trim().length() > 0){
                    textholder.body.setVisibility(View.VISIBLE);
                    textholder.body.setText(textPost.getBody());
                    textholder.body.setLineSpacing(1.0f, 1.0f);

                    Linkify.addLinks(textholder.body, Linkify.ALL);

                }
                else
                    textholder.body.setVisibility(View.GONE);
                break;
            }

            case VIEW_TYPE_CATEGORY_POST:{

                MainGridHolder subHolder21 = (MainGridHolder) parentHolder;
                ArrayList<UniversalPost> mainGrids141 = new ArrayList<>();
                CategoryGrid obj141 = (CategoryGrid) universalList.get(position);
                subHolder21.title.setText(resources.getString(R.string.news_categories));

                List<CategoryMain> categoryMainList = new ArrayList<>();
                categoryMainList = obj141.getCategoryMainList();
                if (categoryMainList.size() > 0){
                    subHolder21.recyclerView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < categoryMainList.size(); i++){
                        CategoryMain uni = categoryMainList.get(i);
                        uni.setPostType(POST_CATEGORY_ITEM);
                        mainGrids141.add(uni);


                    }

                }

                else
                    subHolder21.recyclerView.setVisibility(View.GONE);

                gridAdapter = new MediaAdapter(activity, mainGrids141);
                layoutManager = new GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false);


                if (subHolder21.recyclerView != null) {
                    subHolder21.recyclerView.setLayoutManager(layoutManager);
                    subHolder21.recyclerView.setAdapter(gridAdapter);
                    gridAdapter.notifyDataSetChanged();
                }



                subHolder21.recyclerView.getItemAnimator().setChangeDuration(0);
                subHolder21.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

                break;

            }

            case VIEW_TYPE_BRAND_BANNER_ITEM:{
                BrandBannerHolder bbHolder = (BrandBannerHolder) parentHolder;
                final MainItem bbitem  = (MainItem) universalList.get(position);


                bbHolder.image.setVisibility(View.GONE);
                bbHolder.cardViewBrand.setVisibility(View.VISIBLE);
                bbHolder.imageCardView.setImageUrl(bbitem.getPreview_url(), imageLoader);
                bbHolder.imageCardView.getLayoutParams().width = display.getWidth() / 5;
                bbHolder.imageCardView.getLayoutParams().height = display.getWidth() / 5;
                bbHolder.imageCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, BrandActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", bbitem.getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("promotion", String.valueOf(bbitem.getId()));
                            FlurryAgent.logEvent(" Click Promotion Banner", mix);
                        } catch (Exception e) {
                        }


                    }
                });


                break;
            }

            case VIEW_TYPE_BRAND_BANNER:{
                ArrayList<UniversalPost> brands = new ArrayList<>();
                MainBrandBannerHolder brandBannerHolder = (MainBrandBannerHolder) parentHolder;
                final MainObject brandBanner = (MainObject) universalList.get(position);
                brandBannerHolder.more.setText(resources.getString(R.string.see_more));
                brandBannerHolder.more.setTypeface(brandBannerHolder.more.getTypeface(), Typeface.BOLD);

                brandBannerHolder.title.setText(brandBanner.getTitle());
                List<MainItem> brandBanners = new ArrayList<>();
                brandBanners = brandBanner.getItems();
                if(brandBanners.size() > 0){
                    for(int i = 0 ; i < brandBanners.size(); i++){
                        MainItem uni = brandBanners.get(i);
                        uni.setPostType(BRAND_BANNER_ITEM);
                        brands.add(uni);


                    }
                }
                if(brands.size() > 0) {
                    gridAdapter = new MediaAdapter(activity, brands);
                    layoutManager = new GridLayoutManager(activity.getApplicationContext(), 4);
                    if (brandBannerHolder.recyclerView != null) {
                        brandBannerHolder.recyclerView.setLayoutManager(layoutManager);
                        brandBannerHolder.recyclerView.setAdapter(gridAdapter);

                    }
                }
                if(brandBanner.getUrl() != null && brandBanner.getUrl().trim().length() > 0) {
                    brandBannerHolder.more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            try {
                                Map<String, String> mix = new HashMap<String, String>();

                                FlurryAgent.logEvent("Click All Brands Button", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, BrandAllActivity.class);
                            activity.startActivity(intent);
                        }
                    });
                }else
                    brandBannerHolder.more.setVisibility(View.GONE);

                break;

            }

            case VIEW_TYPE_USER_POST:{

                final Reading userPost   = (Reading) universalList.get(position);
                final UserPostHolder userPostHolder = (UserPostHolder) parentHolder;


                try{

                    if (userPost.getPage_img_url() != null && !userPost.getPage_img_url().equals("")){
                        userPostHolder.userNetworkImage.setImageUrl(userPost.getPage_img_url(), imageLoader);
                    }else{
                        userPostHolder.userNetworkImage.setImageUrl("http://res.cloudinary.com/tech-myanmar/image/upload/t_media_lib_thumb/v1548219510/site/50593752_587691448322384_2965038700087476224_n.jpg", imageLoader);
                    }
                }catch (Exception e){
                    userPostHolder.userNetworkImage.setImageUrl("http://res.cloudinary.com/tech-myanmar/image/upload/t_media_lib_thumb/v1548219510/site/50593752_587691448322384_2965038700087476224_n.jpg", imageLoader);
                    Log.e(TAG, "onBindViewHolder: " + e.getMessage() );
                }


                try{

                    if (userPost.getCustomer_type().equals("normal_customer")){
                        if(userPost.getSort_by().equals("unknown_parent_status" ) || userPost.getSort_by().equals("other" )){
                            userPostHolder.babyage.setText(resources.getString(R.string.family_member));
                            userPostHolder.babyage.setVisibility(View.VISIBLE);

                        }else{
                            String returnString1 =  ageConfig.calculateKidAge(userPost.getBirth_month(), userPost.getBirth_year(),
                                    userPost.getCategory_type(), userPost.getSort_by() ) ;

                            userPostHolder.babyage.setText(returnString1);
                            userPostHolder.babyage.setVisibility(View.VISIBLE);
                        }

                        userPostHolder.txtType.setVisibility(View.GONE);
                    }else if (userPost.getCustomer_type().trim().length() > 0 ){


                        userPostHolder.babyage.setVisibility(View.GONE);
                        userPostHolder.txtType.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        {
                            Drawable leftDrawable = AppCompatResources
                                    .getDrawable(activity, R.drawable.ic_stars_grey);
                            userPostHolder.txtType.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
                        }
                        userPostHolder.txtType.setText( userPost.getCustomer_type() );
                        userPostHolder.txtType.setTypeface(userPostHolder.txtType.getTypeface(), Typeface.BOLD);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        {
                            Drawable leftDrawable = AppCompatResources
                                    .getDrawable(activity, R.drawable.ic_stars_grey);
                            userPostHolder.txtType.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
                        }

                    }





                    userPostHolder.title.setText(userPost.getPage_name());

                    try{

                        if (userPost.getPage_name() == null){
                            userPostHolder.title.setText("Kylary Member");
                        }
                        else if (userPost.getPage_name().trim().length() == 0){
                            userPostHolder.title.setText("Kylary Member");
                        }

                    }catch (Exception e){
                        Log.e(TAG, "onBindViewHolder: ************************* Error "   + e.getMessage() );
                        userPostHolder.title.setText("Kylary Member");
                    }

                    userPostHolder.title.setTypeface(userPostHolder.title.getTypeface(), Typeface.BOLD);


                }catch (Exception e){

                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }



                try {

                    userPostHolder.date.setText( getDateInMillis(userPost.getRelease_at()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );


                }
                if(userPost.getBody().length() > 150){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        userPostHolder.question.setText(Html.fromHtml( userPost.getBody().substring(0, 150)+" ....."+
                                "<b><font color=#000000>" + "Continue Reading" + "</font></b>" , Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        userPostHolder.question.setText(Html.fromHtml(userPost.getBody().substring(0, 150)+" ....."+
                                "<b><font color=#000000>" + "Continue Reading" + "</font></b>"));
                    }

                    userPostHolder.question.setTextSize(15);
                    userPostHolder.question.setBackgroundResource(R.color.white);

                    userPostHolder.question.setEnabled(true);
                    userPostHolder.question.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "post_list");
                                mix.put("post_id", String.valueOf(userPost.getId()));
                                FlurryAgent.logEvent("Click User Post", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", userPost.getCustomer_type());
                            bundle.putString("comment", "read");
                            bundle.putString("page_url", userPost.getPage_img_url());
                            bundle.putInt("id", userPost.getId());
                            bundle.putString("title", userPost.getTitle());
                            bundle.putInt("like_count", userPost.getLikes());
                            bundle.putInt("comment_count", userPost.getComment_coount());

                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    });

                }else{
                    if(userPost.getDimen() == 0) {
                        userPostHolder.question.setEnabled(false);
                        userPostHolder.question.setText(userPost.getBody());
                        userPostHolder.question.setBackgroundResource(R.color.white);

                    }else{
                        userPostHolder.question.setText(userPost.getBody());
                        userPostHolder.question.setTextSize(15);
                        userPostHolder.question.setBackgroundResource(R.color.white);
                        userPostHolder.question.setEnabled(true);
                        userPostHolder.question.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("source", "post_list");
                                    mix.put("post_id", String.valueOf(userPost.getId()));
                                    FlurryAgent.logEvent("Click User Post", mix);
                                } catch (Exception e) {
                                }


                                Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("page_url", userPost.getPage_img_url());
                                bundle.putString("comment", "read");
                                bundle.putInt("id", userPost.getId());
                                bundle.putString("title", userPost.getTitle());
                                bundle.putInt("like_count", userPost.getLikes());
                                bundle.putInt("comment_count", userPost.getComment_coount());

                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            }
                        });
                    }
                }



                Linkify.addLinks(userPostHolder.question, Linkify.ALL);


                if(userPost.getLikes() > 0 || databaseAdapter.checkPostLiked(userPost.getId())) {

                    if(userPost.getLikes() == 1)
                        userPostHolder.like_count.setText(userPost.getLikes() + " Like");
                    else
                        userPostHolder.like_count.setText(userPost.getLikes() + " Likes");
                    if (databaseAdapter.checkPostLiked(userPost.getId())) {
                        userPostHolder.like.setImageResource(R.drawable.ic_favorite_red_24dp);
                        if(userPost.getLikes() == 0)
                            userPostHolder.like_count.setText(userPost.getLikes()+1 + " Like");
                    } else {
                        userPostHolder.like.setImageResource(R.drawable.wishlist);
                    }
                } else {
                    userPostHolder.like.setImageResource(R.drawable.wishlist);
                    userPostHolder.like_count.setText("");
                }

                userPostHolder.like_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent   = new Intent(activity, PostLikedActivity.class);
                        intent.putExtra("post_id", userPost.getId());
                        activity.startActivity(intent);
                    }
                });

                if(userPost.getComment_coount() == 0)
                    userPostHolder.comment_count.setText("");
                else if(userPost.getComment_coount() == 1)
                    userPostHolder.comment_count.setText(userPost.getComment_coount()+" Comment");
                else
                    userPostHolder.comment_count.setText(userPost.getComment_coount()+" Comments");


                if(userPostHolder.like_count.getText().equals("") && userPostHolder.comment_count.getText().equals("")){
                    userPostHolder.linearLikeComment.setVisibility(View.GONE);
                }else{
                    userPostHolder.linearLikeComment.setVisibility(View.VISIBLE);
                }



                userPostHolder.like_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        sendGroupChatLike(userPost.getId(), userPostHolder, userPost);
                    }
                });

                userPostHolder.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastHelper.showToast(activity, "Saved");
                    }
                });

                if (userPost.getDimen() > 0) {
                    userPostHolder.imageView.setVisibility(View.VISIBLE);
                    userPostHolder.imageView.setImageUrl(userPost.getPhoto_url(), imageLoader);
                    userPostHolder.imageView.getLayoutParams().height   = (userPost.getDimen() * display.getWidth()) / 100;
                    userPostHolder.imageView.getLayoutParams().width    = display.getWidth();
                    userPostHolder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "post_list");
                                mix.put("post_id", String.valueOf(userPost.getId()));
                                FlurryAgent.logEvent("Click User Post", mix);
                            } catch (Exception e) {
                            }


                            Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("type", userPost.getCustomer_type());
                            bundle.putString("page_url", userPost.getPage_img_url());
                            bundle.putString("comment", "read");
                            bundle.putInt("id", userPost.getId());
                            bundle.putString("title", userPost.getTitle());
                            bundle.putInt("like_count", userPost.getLikes());
                            bundle.putInt("comment_count", userPost.getComment_coount());

                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    });
                }else{
                    userPostHolder.imageView.setVisibility(View.GONE);
                }

                userPostHolder.comment_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(userPost.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("page_url", userPost.getPage_img_url());
                        bundle.putInt("id", userPost.getId());
                        bundle.putString("title", userPost.getTitle());
                        bundle.putInt("like_count", userPost.getLikes());
                        bundle.putInt("comment_count", userPost.getComment_coount());

                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                userPostHolder.comment_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(userPost.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("page_url", userPost.getPage_img_url());
                        bundle.putInt("id", userPost.getId());
                        bundle.putString("title", userPost.getTitle());
                        bundle.putInt("like_count", userPost.getLikes());
                        bundle.putInt("comment_count", userPost.getComment_coount());

                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                userPostHolder.share_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("product_id", String.valueOf(userPost.getId()));
                            FlurryAgent.logEvent("Click Group Chat Share Icon", mix);
                        } catch (Exception e) {
                        }


                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", userPost.getId()+"");
                        bundle.putString("content_type","post");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, userPost.getUrl());
                        activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                    }
                });

                userPostHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_more);

                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        final CustomTextView subscribe_text   = (CustomTextView) dialog.findViewById(R.id.subscribe_text);
                        CustomTextView delete_text      = (CustomTextView) dialog.findViewById(R.id.delete_text);
                        LinearLayout deleteLayout       = (LinearLayout) dialog.findViewById(R.id.delete_layout);
                        CustomSwitchView subscribe      = (CustomSwitchView) dialog.findViewById(R.id.subscribe);
                        CustomTextView like_text        = (CustomTextView) dialog.findViewById(R.id.like_text);
                        CustomTextView txtCopy        = (CustomTextView) dialog.findViewById(R.id.txtCopy);
                        LinearLayout like_layout        = (LinearLayout) dialog.findViewById(R.id.like_layout);
                        LinearLayout copy_layout        = (LinearLayout) dialog.findViewById(R.id.copy_layout);

                        txtCopy.setText(resources.getString(R.string.copy_text));
                        copy_layout.setVisibility(View.VISIBLE);
                        copy_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int sdk = android.os.Build.VERSION.SDK_INT;
                                if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                                            activity.getSystemService(Context.CLIPBOARD_SERVICE);
                                    clipboard.setText(userPost.getBody());
                                } else {
                                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                                            activity.getSystemService(Context.CLIPBOARD_SERVICE);
                                    android.content.ClipData clip = android.content.ClipData
                                            .newPlainText("text", userPost.getBody());
                                    clipboard.setPrimaryClip(clip);
                                }
                                ToastHelper.showToast(activity, "Text Copied!");
                                dialog.dismiss();
                            }
                        });


                        delete_text.setText(resources.getString(R.string.post_delete));
                        like_text.setText(resources.getString(R.string.post_liked));
                        if(databaseAdapter.getSubscribe(userPost.getId())){
                            subscribe.setChecked(true);
                        }else
                            subscribe.setChecked(false);

                        if(prefs.getIntPreferences(SP_MEMBER_ID) == userPost.getCustomer_id()){
                            deleteLayout.setVisibility(View.VISIBLE);
                        }else{
                            deleteLayout.setVisibility(View.GONE);

                            if (prefs.getIntPreferences(SP_KYARLAY_ACCESS) == 1)
                                deleteLayout.setVisibility(View.VISIBLE);
                        }

                        if(databaseAdapter.getSubscribe(userPost.getId())){
                            subscribe.setChecked(true);
                            subscribe_text.setText(resources.getString(R.string.unsubscribe));
                        }else{
                            subscribe.setChecked(false);
                            subscribe_text.setText(resources.getString(R.string.subscribe));
                        }



                        subscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                if(b){
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+userPost.getId());
                                        FlurryAgent.logEvent("SubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.insertPostSubscribe(userPost.getId());
                                    FirebaseMessaging.getInstance().subscribeToTopic("post_"+userPost.getId());
                                    subscribe_text.setText(resources.getString(R.string.unsubscribe));
                                }else {
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+userPost.getId());
                                        FlurryAgent.logEvent("UnsubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.deleteSubscribe(userPost.getId());
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("post_"+userPost.getId());
                                    subscribe_text.setText(resources.getString(R.string.subscribe));

                                }
                            }
                        });

                        deleteLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                final Dialog dialogDelete = new Dialog(activity);
                                dialogDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialogDelete.setContentView(R.layout.dialog_logout);

                                dialogDelete.setCanceledOnTouchOutside(true);
                                Window window = dialogDelete.getWindow();
                                WindowManager.LayoutParams wlp = window.getAttributes();
                                wlp.gravity = Gravity.CENTER;
                                wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                                window.setAttributes(wlp);

                                CustomButton cancel = (CustomButton) dialogDelete.findViewById(R.id.dialog_delete_cancel);
                                CustomButton confirm = (CustomButton) dialogDelete.findViewById(R.id.dialog_delete_confirm);
                                CustomTextView title = (CustomTextView) dialogDelete.findViewById(R.id.title);
                                CustomTextView text = (CustomTextView) dialogDelete.findViewById(R.id.text);

                                title.setText(resources.getString(R.string.delete));
                                text.setText(resources.getString(R.string.delete_text));
                                cancel.setText(resources.getString(R.string.delete_cancel));
                                confirm.setText(resources.getString(R.string.delete_confirm));

                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogDelete.dismiss();
                                        userPostDelete(userPost.getId(), position, dialog);

                                    }

                                });

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogDelete.dismiss();
                                    }
                                });
                                dialogDelete.show();
                                dialog.dismiss();

                            }
                        });

                        like_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent   = new Intent(activity, PostLikedActivity.class);
                                intent.putExtra("post_id", userPost.getId());
                                activity.startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });


                break;

            }

            case VIEW_TYPE_CHOOSE_TOOL_ITEM :{



                CustomMainHolder chooseItemHolder = (CustomMainHolder) parentHolder;
                final ToolChildObject chooseObject = (ToolChildObject) universalList.get(position);
                chooseItemHolder.title.setText(chooseObject.getName());




                chooseItemHolder.linearBackground.setBackgroundResource(R.drawable.tags_rounded_corners);
                GradientDrawable drawable1 = (GradientDrawable) chooseItemHolder.linearBackground.getBackground();
                drawable1.setColor(Integer.parseInt("efefef", 16)+0xFF000000);
                chooseItemHolder.title.setTextColor(Integer.parseInt("000000", 16)+0xFF000000);


                if (chooseObject.getImage() != null && chooseObject.getImage().trim().length() > 0){
                    chooseItemHolder.imageView.setImageUrl(chooseObject.getImage(), imageLoader);
                }
                else {
                    chooseItemHolder.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }

                chooseItemHolder.imageView.getLayoutParams().width  =  (display.getWidth() / 6) ;
                chooseItemHolder.imageView.getLayoutParams().height =  display.getWidth() / 6;
                chooseItemHolder.linearMain.getLayoutParams().width  =  ( (int) (display.getWidth() * 1 ) / 3) ;
                chooseItemHolder.linearMain.getLayoutParams().height  =  ((int) (display.getWidth() * 2) ) / 5;

                chooseItemHolder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                chooseItemHolder.title.setLines(2);

                //((LinearLayout.LayoutParams) chooseItemHolder.linearBackground.getLayoutParams()).setMargins(20, 20, 20, 20);


                chooseItemHolder.linearMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(chooseObject.getTag().equals("all")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("tag", chooseObject.getTag());
                                FlurryAgent.logEvent("Click Tools Category", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ToolsClickActivity.class);
                            intent.putExtra("tag", chooseObject.getTag());
                            activity.startActivity(intent);

                        }

                        else if(chooseObject.getTag().equals("is_it_safe")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "IsitSafe onClick");
                                FlurryAgent.logEvent("IsitSafe onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, SafeQuesMainActivity.class);
                            activity.startActivity(intent);
                        }

                        else if(chooseObject.getTag().equals("news")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "News onClick");
                                FlurryAgent.logEvent("News onClick", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, NewsActivity.class);
                            activity.startActivity(intent);


                        }

                        else if(chooseObject.getTag().equals("video")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Video onClick");
                                FlurryAgent.logEvent("Video onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, VideoListActivity.class);
                            activity.startActivity(intent);
                        }
                        else if(chooseObject.getTag().equals("maharbote")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Maharbote onClick");
                                FlurryAgent.logEvent("Maharbote onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, MaharboteActivity.class);
                            intent.putExtra("url",chooseObject.getImage());
                            intent.putExtra("title", chooseObject.getName());
                            activity.startActivity(intent);
                        }


                        else if(chooseObject.getTag().equals("momolay")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Momolay onClick");
                                FlurryAgent.logEvent("Momolay onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, NewsClickActivity.class);
                            intent.putExtra("tag", chooseObject.getTag());
                            intent.putExtra("name", chooseObject.getName());
                            activity.startActivity(intent);
                        }

                        else if(chooseObject.getTag().equals("baby_names")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Baby Name onClick");
                                FlurryAgent.logEvent("Baby Name onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, GiveNameActivity.class);
                            activity.startActivity(intent);
                        }

                        else if (chooseObject.getTag().equals("birthday_calendar")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Horoscope onClick");
                                FlurryAgent.logEvent("Horoscope onClick", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, CustomCalendarActivity.class);
                            activity.startActivity(intent);
                        }

                        else if (chooseObject.getTag().equals("ovulation_calculator")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Pregnant onClick");
                                FlurryAgent.logEvent("Pregnant onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, Get_PregnantActivity.class);
                            activity.startActivity(intent);
                        }

                        else if (chooseObject.getTag().equals("due_date")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Due Date onClick");
                                FlurryAgent.logEvent("Due Date onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, DueDateActivity.class);
                            activity.startActivity(intent);
                        }
                        else if (chooseObject.getTag().equals("clinic_directory")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Clinic Diectory onClick");
                                FlurryAgent.logEvent("Clinic Diectory onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ClinicActivity.class);
                            intent.putExtra("name", chooseObject.getName());
                            prefs.saveStringPreferences(SP_DIRECTORY_CLICK, chooseObject.getTag());
                            activity.startActivity(intent);
                        }

                        else if (chooseObject.getTag().equals("schools")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "School Diectory onClick");
                                FlurryAgent.logEvent("School Diectory onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ClinicActivity.class);
                            intent.putExtra("name", chooseObject.getName());
                            prefs.saveStringPreferences(SP_DIRECTORY_CLICK, chooseObject.getTag());
                            activity.startActivity(intent);
                        }


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

            case VIEW_TYPE_CART_DETAIL_NO_ITEM:{
                NoItemHolder noitem = (NoItemHolder) parentHolder;
                noitem.space.getLayoutParams().height = 350 ;
                noitem.space.setText(resources.getString(R.string.noitem));
                noitem.space.setGravity(Gravity.CENTER);
                noitem.space.setTextSize(12);
                break;
            }

            case VIEW_TYPE_VIDEO_NEWS :{
                final MainRichtextHolder richVideoHolder = (MainRichtextHolder) parentHolder;
                final Reading readingObj  = (Reading) universalList.get(position);
                if(readingObj.getDimen() > 0) {
                    richVideoHolder.frame.setVisibility(View.VISIBLE);
                    richVideoHolder.imageView.getLayoutParams().height = (readingObj.getDimen() * display.getWidth()) / 100;
                    richVideoHolder.imageView.setImageUrl(readingObj.getPhoto_url(), imageLoader);
                }
                try {
                    richVideoHolder.page_time.setText(getDateInMillis(readingObj.getRelease_at()));

                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }

                if (readingObj.getCategory_type().equals("momolay")) {
                    richVideoHolder.more.setVisibility(View.GONE);
                    richVideoHolder.layoutComment.setVisibility(View.GONE);
                    richVideoHolder.share_layoutMomo.setVisibility(View.VISIBLE);
                }
                else{
                    richVideoHolder.more.setVisibility(View.VISIBLE);
                    richVideoHolder.layoutComment.setVisibility(View.GONE);
                    richVideoHolder.share_layoutMomo.setVisibility(View.GONE);
                }


                if(readingObj.getTitle() != null  && readingObj.getTitle().trim().length() > 0) {
                    richVideoHolder.title.setVisibility(View.VISIBLE);
                    richVideoHolder.title.setText(readingObj.getTitle());
                    richVideoHolder.title.setLineSpacing(1.8f, 1.0f);

                }else
                    richVideoHolder.title.setVisibility(View.GONE);

                if(readingObj.getBody() != null && readingObj.getBody().trim().length() > 0){
                    richVideoHolder.body.setVisibility(View.VISIBLE);
                    if(readingObj.getBody().length() > 150)
                        richVideoHolder.body.setText(readingObj.getBody().substring(0, 150)+"  ..... ");
                    else
                        richVideoHolder.body.setText(readingObj.getBody());



                    Linkify.addLinks(richVideoHolder.body,Linkify.ALL);
                }else
                    richVideoHolder.body.setVisibility(View.GONE);


                if(readingObj.getPage_name() != null && readingObj.getPage_name().trim().length() > 0){
                    richVideoHolder.pageName.setText(readingObj.getPage_name());
                    richVideoHolder.pageName.setVisibility(View.VISIBLE);
                    richVideoHolder.pageName.setTypeface(richVideoHolder.pageName.getTypeface(), Typeface.BOLD);
                }


                if(readingObj.getPage_img_url() != null && readingObj.getPage_img_url().trim().length() > 0){

                    richVideoHolder.pageImage.setImageUrl(readingObj.getPage_img_url(), imageLoader);
                    richVideoHolder.pageImage.setVisibility(View.VISIBLE);
                }

                if(databaseAdapter.checkPostLiked(readingObj.getId())){
                    richVideoHolder.like_image.setImageResource(R.drawable.wishlist_clicked);
                }else {
                    richVideoHolder.like_image.setImageResource(R.drawable.wishlist);
                }

                if(readingObj.getLikes() > 0 || databaseAdapter.checkPostLiked(readingObj.getId())) {
                    richVideoHolder.likes.setVisibility(View.VISIBLE);
                    richVideoHolder.likes.setText(readingObj.getLikes() + " Likes");

                    if(databaseAdapter.checkPostLiked(readingObj.getId())){
                        richVideoHolder.like_image.setImageResource(R.drawable.wishlist_clicked);
                        if(readingObj.getLikes() == 0)
                            richVideoHolder.likes.setText(readingObj.getLikes()+1 + " Like");
                    }else {
                        richVideoHolder.like_image.setImageResource(R.drawable.wishlist);
                    }
                }
                else {
                    richVideoHolder.likes.setVisibility(View.GONE);
                }

                if(readingObj.getComment_coount() > 0) {
                    richVideoHolder.comment_count.setText(readingObj.getComment_coount()+" Comments");
                    richVideoHolder.comment_count.setVisibility(View.VISIBLE);
                }else
                    richVideoHolder.comment_count.setVisibility(View.GONE);

                richVideoHolder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (readingObj.getCategory_type().equals("momolay")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "post_list");
                                mix.put("post_id",String.valueOf( readingObj.getId()));
                                FlurryAgent.logEvent("Click Momolay Post", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, MomolayDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", readingObj.getId());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                        else{
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "post_list");
                                mix.put("post_id", String.valueOf(readingObj.getId()));
                                FlurryAgent.logEvent("Click Post", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("comment", "read");
                            bundle.putInt("id", readingObj.getId());
                            bundle.putString("page_url", readingObj.getPage_img_url());
                            bundle.putString("title", readingObj.getTitle());
                            bundle.putInt("like_count", readingObj.getLikes());
                            bundle.putInt("comment_count", readingObj.getComment_coount());
                            bundle.putString("page_url", readingObj.getPage_img_url());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }




                    }
                });

                richVideoHolder.reading_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (readingObj.getCategory_type().equals("momolay")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "post_list");
                                mix.put("post_id", String.valueOf(readingObj.getId()));
                                FlurryAgent.logEvent("Click Momolay Post", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, MomolayDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", readingObj.getId());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                        else{
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "post_list");
                                mix.put("post_id", String.valueOf(readingObj.getId()));
                                FlurryAgent.logEvent("Click Post", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("comment", "read");
                            bundle.putInt("id", readingObj.getId());
                            bundle.putString("page_url", readingObj.getPage_img_url());
                            bundle.putString("title", readingObj.getTitle());
                            bundle.putInt("like_count", readingObj.getLikes());
                            bundle.putInt("comment_count", readingObj.getComment_coount());
                            bundle.putString("page_url", readingObj.getPage_img_url());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    }
                });

                richVideoHolder.comment_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(readingObj.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }



                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", readingObj.getId());
                        bundle.putString("page_url", readingObj.getPage_img_url());
                        bundle.putString("title", readingObj.getTitle());
                        bundle.putInt("like_count", readingObj.getLikes());
                        bundle.putInt("comment_count", readingObj.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                    }
                });
                richVideoHolder.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(readingObj.getId()));
                            FlurryAgent.logEvent("Click Comment Icon", mix);
                        } catch (Exception e) {
                        }


                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("page_url", readingObj.getPage_img_url());
                        bundle.putInt("id", readingObj.getId());
                        bundle.putString("title", readingObj.getTitle());
                        bundle.putInt("like_count", readingObj.getLikes());
                        bundle.putInt("comment_count", readingObj.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);

                    }
                });

                richVideoHolder.likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Bundle fbundle = new Bundle();
                        fbundle.putString("item_id", readingObj.getId()+"");
                        fbundle.putString("item_category", "post");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, fbundle);

                        Intent intent   = new Intent(activity, PostLikedActivity.class);
                        intent.putExtra("post_id", readingObj.getId());
                        activity.startActivity(intent);
                    }
                });
                richVideoHolder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        richVideoHolder.likes.setVisibility(View.VISIBLE);
                        sendLike(readingObj.getId(), richVideoHolder, readingObj);

                    }
                });

                richVideoHolder.share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(readingObj.getId()));
                            FlurryAgent.logEvent("Click Post Share Icon", mix);
                        } catch (Exception e) {
                        }


                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", readingObj.getId()+"");
                        bundle.putString("content_type","post");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, readingObj.getUrl());
                        activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                    }
                });

                richVideoHolder.share_layoutMomo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(readingObj.getId()));
                            FlurryAgent.logEvent("Click Momolay Share Icon", mix);
                        } catch (Exception e) {
                        }


                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", readingObj.getId()+"");
                        bundle.putString("content_type","post");
                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, readingObj.getUrl());
                        activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                    }
                });

                richVideoHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_more);

                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        final CustomTextView subscribe_text = (CustomTextView) dialog.findViewById(R.id.subscribe_text);
                        CustomSwitchView subscribe   = (CustomSwitchView) dialog.findViewById(R.id.subscribe);
                        CustomTextView like_text     = (CustomTextView) dialog.findViewById(R.id.like_text);
                        LinearLayout like_layout        = (LinearLayout) dialog.findViewById(R.id.like_layout);

                        like_text.setText(resources.getString(R.string.post_liked));

                        if(databaseAdapter.getSubscribe(readingObj.getId())){
                            subscribe.setChecked(true);
                            subscribe_text.setText(resources.getString(R.string.unsubscribe));
                        }else{
                            subscribe.setChecked(false);
                            subscribe_text.setText(resources.getString(R.string.subscribe));
                        }

                        subscribe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



                                if(b){
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+readingObj.getId());
                                        FlurryAgent.logEvent("SubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.insertPostSubscribe(readingObj.getId());
                                    FirebaseMessaging.getInstance().subscribeToTopic("post_"+readingObj.getId());
                                    subscribe_text.setText(resources.getString(R.string.unsubscribe));
                                }else {
                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("source", "post_"+readingObj.getId());
                                        FlurryAgent.logEvent("UnsubscribePost", mix);
                                    } catch (Exception e) {
                                    }
                                    databaseAdapter.deleteSubscribe(readingObj.getId());
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic("post_"+readingObj.getId());
                                    subscribe_text.setText(resources.getString(R.string.subscribe));

                                }
                            }
                        });

                        like_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent   = new Intent(activity, PostLikedActivity.class);
                                intent.putExtra("post_id", readingObj.getId());
                                activity.startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });
                break;

            }

            case VIEW_TYPE_ADS:{
                AdsHolder kyarlayAdsHolder = (AdsHolder) parentHolder;
                KyarlayAds kyarlayAds = (KyarlayAds) universalList.get(position);

                kyarlayAdsHolder.imgAds.getLayoutParams().height = ( display.getWidth() * kyarlayAds.getImage_dimen() ) / 100;
                kyarlayAdsHolder.ads_layout.getLayoutParams().height = ( display.getWidth() * kyarlayAds.getImage_dimen() ) / 100;
                kyarlayAdsHolder.imgAds.setImageUrl(kyarlayAds.getImage_url(), imageLoader);

                kyarlayAdsHolder.ads_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                Request.Method.GET, String.format(constantAdsClick, kyarlayAds.getId()+""), null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        try{



                                            if (response.getInt("status")  == 1){

                                                if (kyarlayAds.getType().equals("link")){
                                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(kyarlayAds.getTarget_url()));
                                                    activity.startActivity(myIntent);
                                                }
                                                else if(kyarlayAds.getType().equals("image")){
                                                    try {
                                                        Intent intent = new Intent(activity, AndroidLoadImageFromAdsUrl.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("url", kyarlayAds.getTarget_url().toString());
                                                        intent.putExtras(bundle);
                                                        activity.startActivity(intent);
                                                    } catch (Exception e) {
                                                        Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                                    }
                                                }
                                            }
                                            else{

                                                ToastHelper.showToast(activity, resources.getString(R.string.search_error));
                                            }
                                        }catch (Exception e){

                                        }


                                    }
                                },  new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );


                            }
                        });
                        AppController.getInstance().addToRequestQueue(jsonObjReq, "VersionDownload");


                    }
                });



                break;
            }

            case VIEW_TYPE_CHILD_GROWTH:{
                GrowthHolder growthHolder = (GrowthHolder) parentHolder;
                final Reading grwothReading = (Reading) universalList.get(position);

                growthHolder.txtQuestion.setVisibility(View.GONE);


                if (grwothReading.getBody() != null && grwothReading.getBody().trim().length() > 0){
                    growthHolder.txtAnswer.setText(grwothReading.getBody());
                    growthHolder.txtAnswer.setVisibility(View.VISIBLE);
                }
                else
                    growthHolder.txtAnswer.setVisibility(View.GONE);




                if (grwothReading.getSort_by() != null && grwothReading.getSort_by().trim().length() > 0){
                    growthHolder.txtLength.setText(grwothReading.getSort_by());
                    growthHolder.imgLength.setVisibility(View.VISIBLE);
                    growthHolder.txtLength.setVisibility(View.VISIBLE);
                }
                else
                {
                    growthHolder.imgLength.setVisibility(View.GONE);
                    growthHolder.txtLength.setVisibility(View.GONE);
                }


                if (grwothReading.getPage_name() != null && grwothReading.getPage_name().trim().length() > 0){
                    growthHolder.txtWeight.setText(grwothReading.getPage_name());
                    growthHolder.imgWeight.setVisibility(View.VISIBLE);
                    growthHolder.txtWeight.setVisibility(View.VISIBLE);
                }
                else
                {
                    growthHolder.imgWeight.setVisibility(View.GONE);
                    growthHolder.txtWeight.setVisibility(View.GONE);
                }


                if ((grwothReading.getSort_by() != null && grwothReading.getSort_by().trim().length() > 0) ||

                        (grwothReading.getPage_name() != null && grwothReading.getPage_name().trim().length() > 0)
                ){
                    growthHolder.linearWeight.setVisibility(View.VISIBLE);
                }
                else
                    growthHolder.linearWeight.setVisibility(View.GONE);


                growthHolder.more.setText(resources.getString(R.string.see_more));

                if(grwothReading.getBody().length() > 150) {
                    growthHolder.txtAnswer.setText(grwothReading.getBody().substring(0, 150) + " ...... continue reading  ");
                }else{
                    growthHolder.txtAnswer.setText(grwothReading.getBody() + " ...... continue reading  ");
                }

                growthHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(grwothReading.getId()));
                            FlurryAgent.logEvent("Weekly_Highligh_Post", mix);
                        } catch (Exception e) {
                        }


                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", grwothReading.getId());
                        bundle.putString("page_url", grwothReading.getPage_img_url());
                        bundle.putString("title", grwothReading.getTitle());
                        bundle.putInt("like_count", grwothReading.getLikes());
                        bundle.putInt("comment_count", grwothReading.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });

                growthHolder.linearMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            mix.put("post_id", String.valueOf(grwothReading.getId()));
                            FlurryAgent.logEvent("Weekly_Highligh_Post", mix);
                        } catch (Exception e) {
                        }


                        Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("id", grwothReading.getId());
                        bundle.putString("page_url", grwothReading.getPage_img_url());
                        bundle.putString("title", grwothReading.getTitle());
                        bundle.putInt("like_count", grwothReading.getLikes());
                        bundle.putInt("comment_count", grwothReading.getComment_coount());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });


                break;
            }

            case VIEW_TYPE_DISCOUNT_TITLE:{
                FooterHoloder discountTitleHolder = (FooterHoloder) parentHolder;
                Product title_product   = (Product) universalList.get(position);
                discountTitleHolder.space.setText(title_product.getTitle());
                discountTitleHolder.space.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                discountTitleHolder.space.setPadding(20, 20, 20,  20);
                discountTitleHolder.space.setBackgroundResource(R.color.background);
                discountTitleHolder.space.setTextColor(activity.getResources().getColor(R.color.text_title));
                discountTitleHolder.space.setGravity(Gravity.CENTER);

                discountTitleHolder.titleBackground.setVisibility(View.GONE);
                discountTitleHolder.space.setVisibility(View.VISIBLE);

                if(title_product.getTitle().equals(resources.getString(R.string.more_order_noti))){
                    discountTitleHolder.space.setBackgroundResource(R.color.custome_blue);
                    discountTitleHolder.space.setTextColor(activity.getResources().getColor(R.color.white));
                    discountTitleHolder.space.setPadding(50, 50, 50,  50);
                }

                break;

            }

            case VIEW_TYPE_CHOOSE_TOOL :{
                CategoryMainHolder chooseMainHolder = (CategoryMainHolder) parentHolder;

                ArrayList<UniversalPost> mainGridChoose1 = new ArrayList<>();
                final ToolGrid objChoose = (ToolGrid) universalList.get(position);

                chooseMainHolder.more.setText(resources.getString(R.string.see_more));

                chooseMainHolder.linearMore.setVisibility(View.GONE);
                chooseMainHolder.txtHidden.setVisibility(View.VISIBLE);

                chooseMainHolder.more.setTypeface(chooseMainHolder.more.getTypeface(), Typeface.BOLD);


                List<ToolChildObject> moreItemsChoose = objChoose.getToolObjectArrayList();

                if (moreItemsChoose.size() > 0 ){
                    chooseMainHolder.title.setText("");


                    for(int i = 0 ; i < moreItemsChoose.size() ; i++){
                        ToolChildObject uni = moreItemsChoose.get(i);
                        uni.setPostType(CHOOSE_TOOL_ITEM);
                        Log.e(TAG, "onBindViewHolder: ------------------------------------------------------ VIEW_TYPE_CHOOSE_TOOL_ITEM"   );
                        mainGridChoose1.add(uni);
                    }


                    gridAdapter = new MediaAdapter(activity, mainGridChoose1);
                    //layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                    layoutManager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
                    if (chooseMainHolder.recyclerView != null) {
                        chooseMainHolder.recyclerView.setLayoutManager(layoutManager);
                        chooseMainHolder.recyclerView.setAdapter(gridAdapter);
                    }

                    ((LinearLayout.LayoutParams) chooseMainHolder.recyclerView.getLayoutParams()).setMargins(10, 10, 10, 10);

                    chooseMainHolder.more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Bundle bundle = new Bundle();
                            bundle.putInt("position" , 1);
                            FragmentTest fragmentTest = new FragmentTest();
                            fragmentTest.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentTest).commit();
                        }
                    });

                }


                chooseMainHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                chooseMainHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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



                break;
            }

            case VIEW_TYPE_BABY_INFO :{
                final BabyInfoHolder infoHolder = (BabyInfoHolder) parentHolder;
                final Reading readingBaby = (Reading) universalList.get(position);



                if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PARENT)){
                    infoHolder.txtNoInfo.setText( resources.getString(R.string.no_baby_age_info));


                }else  if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PREGNANCY)){
                    infoHolder.txtNoInfo.setText( resources.getString(R.string.no_due_date_info));

                }


                if (prefs.getIntPreferences(SP_KID_CHANGE_YEAR) == 0 && prefs.getIntPreferences(SP_KID_CHANGE_MONTH) == 0 ) {



                    infoHolder.linearAll.setVisibility(View.GONE);
                    infoHolder.img.setVisibility(View.GONE);
                    infoHolder.txtNoInfo.setVisibility(View.VISIBLE);
                }
                else {

                    infoHolder.txtPrev.setVisibility(View.VISIBLE);
                    infoHolder.txtNext.setVisibility(View.VISIBLE);


                    if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 0 && prefs.getIntPreferences(SP_KID_CHANGE_WEEK) < 2 ){
                        infoHolder.txtPrev.setVisibility(View.INVISIBLE);

                    }

                    infoHolder.txtNoInfo.setVisibility(View.INVISIBLE);
                    infoHolder.linearAll.setVisibility(View.VISIBLE);
                    infoHolder.img.setVisibility(View.VISIBLE);
                    String status = calculateKidInfo();

                    if (readingBaby.getPhoto_url() != null && readingBaby.getPhoto_url().trim().length() > 0){
                        infoHolder.img.setImageUrl(readingBaby.getPhoto_url(), imageLoader);
                        infoHolder.img.getLayoutParams().height = (readingBaby.getDimen() * display.getWidth()) / 100;
                    }
                    else{
                        infoHolder.img.setImageUrl(prefs.getStringPreferences(SP_BABY_INFO_PHOTO), imageLoader);
                        infoHolder.img.getLayoutParams().height = ( prefs.getIntPreferences(SP_BABY_INFO_PHOTO_DIMEN) * display.getWidth()) / 100;
                    }




                    infoHolder.babyAge.setText(status);
                    infoHolder.babyAge.setTypeface( infoHolder.babyAge.getTypeface(), Typeface.BOLD);
                    if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 1){
                        int percentage = 0;
                        if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) == 0){
                            percentage =  1 ;
                        }
                        else{
                            percentage =  (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) * 100 ) / 52 ;
                        }
                        infoHolder.circularProgressbar.setProgress(percentage);

                    }else if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 0){
                        int pecentage =  (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) * 100 ) / 42 ;
                        infoHolder.circularProgressbar.setProgress(pecentage);
                    }



                }




                infoHolder.txtNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        prefs.saveIntPerferences(SP_KID_CHANGE_WEEK,  prefs.getIntPreferences(SP_KID_CHANGE_WEEK) + SP_DEFAULT);
                        if (fragmentMedia != null) {
                            fragmentMedia.changeNextInfo();
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("user_type", prefs.getStringPreferences(SP_FILLUP_STATUS) );
                                mix.put("week", String.valueOf(prefs.getIntPreferences(SP_KID_CHANGE_WEEK)));
                                FlurryAgent.logEvent("Next Click Event", mix);
                            } catch (Exception e) {
                            }

                        }
                    }
                });

                infoHolder.txtPrev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveIntPerferences(SP_KID_CHANGE_WEEK,  prefs.getIntPreferences(SP_KID_CHANGE_WEEK) - SP_DEFAULT);
                        if (fragmentMedia != null) {
                            fragmentMedia.changeNextInfo();

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("user_type", prefs.getStringPreferences(SP_FILLUP_STATUS) );
                                mix.put("week", String.valueOf(prefs.getIntPreferences(SP_KID_CHANGE_WEEK)));
                                FlurryAgent.logEvent("Previous Click Event", mix);
                            } catch (Exception e) {
                            }

                        }

                    }
                });

                infoHolder.txtNoInfo.setVisibility(View.GONE);

                break;

            }

            case VIEW_TYPE_USER_STATUS :{
                UniversalPost uniPost = (UniversalPost) universalList.get(position);
                StatusHolder userStatusHolder = (StatusHolder) parentHolder;

                userStatusHolder.txtOval.setVisibility(View.GONE);

                if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PARENT))
                    userStatusHolder.txtStatus.setText(resources.getString(R.string.parent));
                else if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PREGNANCY))
                    userStatusHolder.txtStatus.setText(resources.getString(R.string.pregnancy));
                else if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_SINGLE)) {
                    userStatusHolder.txtStatus.setText(resources.getString(R.string.single));
                    userStatusHolder.txtOval.setVisibility(View.VISIBLE);

                    if (prefs.getIntPreferences(SP_SINGLE_CAL_DAY)== 0 && prefs.getIntPreferences(SP_SINGLE_CAL_MONTH) == 0  && prefs.getIntPreferences(SP_SINGLE_CAL_NO_OF_DAY) == 0){
                        userStatusHolder.txtOval.setVisibility(View.GONE);
                    }

                }

                userStatusHolder.txtOval.setText(resources.getString(R.string.get_pargnant_cat));
                userStatusHolder.txtOval.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveBooleanPreference(SP_OVAL_DATE, true);
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", "Pregnant onClick");
                            FlurryAgent.logEvent("Pregnant onClick", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, Get_PregnantActivity.class);
                        activity.startActivity(intent);
                    }
                });


                userStatusHolder.txtEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fragmentMedia != null) {
                            fragmentMedia.sendDialogStatus();
                            prefs.saveBooleanPreference(SP_DIALOG_CLICK, true);
                        }
                    }
                });
                userStatusHolder.txtStatus.setTypeface( userStatusHolder.txtStatus.getTypeface(), Typeface.BOLD);
                userStatusHolder.txtEdit.setTypeface( userStatusHolder.txtEdit.getTypeface(), Typeface.BOLD);
                userStatusHolder.txtEdit.setText(resources.getString(R.string.group_chat_name_edit));
                userStatusHolder.txtEdit.setPaintFlags(userStatusHolder.txtEdit.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                userStatusHolder.txtOval.setPaintFlags(userStatusHolder.txtEdit.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

                userStatusHolder.imgWishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0) {
                            try {


                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("source", "post_list");
                                FlurryAgent.logEvent("Click Liked Posts Icon", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, ReadingWishlistActivity.class);
                            activity.startActivity(intent);
                        }else{
                            Intent intent = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }
                    }
                });

                userStatusHolder.imgNoti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0) {
                            try {

                                FlurryAgent.logEvent("Click Notification");
                            } catch (Exception e) {
                            }


                            Intent intent   = new Intent(activity, NotificationAcitivity.class);
                            intent.putExtra("post_type","");
                            activity.startActivity(intent);
                        }else{
                            Intent intent = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }
                    }
                });

                break;
            }

            case VIEW_TYPE_SINGLE_HEALTH:{
                UniversalPost uniPost1 = (UniversalPost) universalList.get(position);
                final SingleUiHolder singleUiHolder = (SingleUiHolder) parentHolder;

                singleUiHolder.singleOneTitle.setText(resources.getString(R.string.get_peroid));
                singleUiHolder.singleTwoTitle.setText(resources.getString(R.string.single_oval_day));
                singleUiHolder.txtCalculate.setText(resources.getString(R.string.get_pargnant_cat));
                singleUiHolder.txtCalculate.setTypeface( singleUiHolder.txtCalculate.getTypeface(), Typeface.BOLD);
                singleUiHolder.txtCalculate.setPaintFlags(singleUiHolder.txtCalculate.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

                if (prefs.getIntPreferences(SP_SINGLE_CAL_DAY)== 0 && prefs.getIntPreferences(SP_SINGLE_CAL_MONTH) == 0  && prefs.getIntPreferences(SP_SINGLE_CAL_NO_OF_DAY) == 0){
                    singleUiHolder.txtCalculate.setVisibility(View.VISIBLE);
                    singleUiHolder.linearSingleOne.setVisibility(View.GONE);
                    singleUiHolder.linearSingleTwo.setVisibility(View.GONE);

                }else{
                    singleUiHolder.txtCalculate.setVisibility(View.GONE);
                    singleUiHolder.linearSingleOne.setVisibility(View.VISIBLE);
                    singleUiHolder.linearSingleTwo.setVisibility(View.VISIBLE);


                    if (prefs.getStringPreferences(SP_SINGLE_COMING_MENSTRAL_DAY).trim().length() > 0 && prefs.getStringPreferences(SP_SINGLE_COMING_MENSTRAL_DAY)!= null ){
                        Date currentDate = new Date();
                        SimpleDateFormat formatter =  new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                        Date futureDate = null;
                        Date prevDate = null;
                        Date ovalDate = null;

                        try {
                            futureDate =formatter.parse(prefs.getStringPreferences(SP_SINGLE_COMING_MENSTRAL_DAY));
                            prevDate =formatter.parse(prefs.getStringPreferences(SP_SINGLE_PREV_MENSTRAL_DAY));
                            ovalDate =formatter.parse(prefs.getStringPreferences(SP_SINGLE_OVAL_DAY));
                        }catch (Exception e){
                            Log.e(TAG, "onBindViewHolder: "  +  e.getMessage());
                        }


                        long diff1 = futureDate.getTime()
                                - currentDate.getTime();
                        SimpleDateFormat cformat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

                        while(diff1 < 0){
                            SimpleDateFormat formateDate = new SimpleDateFormat("M-dd-yyyy", Locale.US);
                            Date d  = new Date();

                            d.setTime(futureDate.getTime() );

                            String str = formateDate.format(d);
                            String[] strings = str.split("-");

                            prefs.saveIntPerferences(SP_SINGLE_CAL_DAY, Integer.parseInt(strings[1]));
                            prefs.saveIntPerferences(SP_SINGLE_CAL_MONTH, Integer.parseInt(strings[0] )-1);
                            prefs.saveIntPerferences(SP_SINGLE_CAL_YEAR, Integer.parseInt(strings[2]));

                            int start_day, start_month, start_year, num_of_day, life_cycle  = 0 ;

                            start_day     = prefs.getIntPreferences(SP_SINGLE_CAL_DAY);
                            start_month   =prefs.getIntPreferences(SP_SINGLE_CAL_MONTH);
                            start_year    = prefs.getIntPreferences(SP_SINGLE_CAL_YEAR);
                            life_cycle  = prefs.getIntPreferences(SP_SINGLE_CAL_LIFE_CYCLE);
                            num_of_day  = prefs.getIntPreferences(SP_SINGLE_CAL_NO_OF_DAY);



                            int difference = life_cycle - 28;
                            int adjusted_day  = start_day + difference;

                            Calendar calendar  = Calendar.getInstance();
                            calendar.set(start_year, start_month, start_day);

                            Calendar tempCalendar2 = (Calendar) calendar.clone();
                            prefs.saveStringPreferences(SP_SINGLE_PREV_MENSTRAL_DAY, tempCalendar2.getTime().toString());



                            Calendar tempCalendar = (Calendar) calendar.clone();
                            tempCalendar.add(Calendar.DATE, life_cycle - 12);


                            for (int i = 0; i < 4; i++) {
                                Calendar calendar1  = (Calendar) tempCalendar.clone();
                                calendar1.add(Calendar.DATE, -1);


                                tempCalendar = (Calendar) calendar1.clone();


                                if (i == 3)
                                    prefs.saveStringPreferences(SP_SINGLE_OVAL_DAY, tempCalendar.getTime().toString());


                            }



                            Calendar tempCalendar1 = (Calendar) calendar.clone();
                            tempCalendar1.add(Calendar.DATE, life_cycle-1);


                            for (int i = 0; i < num_of_day; i++) {
                                Calendar calendar1  = (Calendar) tempCalendar1.clone();
                                calendar1.add(Calendar.DATE, 1);


                                tempCalendar1 = (Calendar) calendar1.clone();



                                if (i == 0)
                                    prefs.saveStringPreferences(SP_SINGLE_COMING_MENSTRAL_DAY, tempCalendar1.getTime().toString());

                            }


                            try {
                                futureDate =formatter.parse(prefs.getStringPreferences(SP_SINGLE_COMING_MENSTRAL_DAY));
                                ovalDate =formatter.parse(prefs.getStringPreferences(SP_SINGLE_OVAL_DAY));
                            }catch (Exception e){
                                Log.e(TAG, "onBindViewHolder: "  +  e.getMessage());
                            }

                            diff1 = futureDate.getTime()
                                    - currentDate.getTime();
                        }

                        singleUiHolder.singleOneAns.setText(cformat.format(futureDate));
                        singleUiHolder.singleTwoAns.setText(cformat.format(ovalDate));




                    }
                }

                singleUiHolder.linearSingleTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveBooleanPreference(SP_OVAL_DATE, true);
                        Intent intent = new Intent(activity, Get_PregnantCalendarActivity.class);
                        intent.putExtra("start_day", prefs.getIntPreferences(SP_SINGLE_CAL_DAY));
                        intent.putExtra("start_month", prefs.getIntPreferences(SP_SINGLE_CAL_MONTH));
                        intent.putExtra("start_year", prefs.getIntPreferences(SP_SINGLE_CAL_YEAR));
                        intent.putExtra("life_cycle", prefs.getIntPreferences(SP_SINGLE_CAL_LIFE_CYCLE));
                        intent.putExtra("num_of_day", prefs.getIntPreferences(SP_SINGLE_CAL_NO_OF_DAY));
                        activity.startActivity(intent);

                    }
                });


                singleUiHolder.linearSingleOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveBooleanPreference(SP_OVAL_DATE, true);
                        Intent intent = new Intent(activity, Get_PregnantCalendarActivity.class);
                        intent.putExtra("start_day", prefs.getIntPreferences(SP_SINGLE_CAL_DAY));
                        intent.putExtra("start_month", prefs.getIntPreferences(SP_SINGLE_CAL_MONTH));
                        intent.putExtra("start_year", prefs.getIntPreferences(SP_SINGLE_CAL_YEAR));
                        intent.putExtra("life_cycle", prefs.getIntPreferences(SP_SINGLE_CAL_LIFE_CYCLE));
                        intent.putExtra("num_of_day", prefs.getIntPreferences(SP_SINGLE_CAL_NO_OF_DAY));
                        activity.startActivity(intent);
                    }
                });

                singleUiHolder.txtCalculate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        prefs.saveBooleanPreference(SP_OVAL_DATE, true);
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", "Pregnant onClick");
                            FlurryAgent.logEvent("Pregnant onClick", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, Get_PregnantActivity.class);
                        activity.startActivity(intent);
                    }
                });

                break;

            }

            case VIEW_TYPE_CART_DETAIL_FOOTER:{

                final FooterHoloder footerHoloder = (FooterHoloder) parentHolder;
                break;
            }





            default:
                break;

        }
    }

    private JsonArrayRequest productList(String url) {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();

                            mix.put("type", "product_list" );
                            mix.put("status", "success" );

                            FlurryAgent.logEvent("Incoming from Deep Linking", mix);
                        } catch (Exception e) {
                        }
                        if(response.length() > 0) {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            ArrayList<Product> arrayList = new ArrayList<>();
                            Type listType = new TypeToken<List<Product>>() {}.getType();
                            List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                            for(int i = 0; i < deliveries.size(); i++) {
                                Product pro = new Product();
                                pro = deliveries.get(i);
                                pro.setPostType(CATEGORY_DETAIL);
                                arrayList.add(pro);

                            }
                            Intent intent = new Intent(activity, DeeplinkingListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("product", arrayList);
                            bundle.putString("fromclass", "universal");
                            intent.putExtras(bundle);
                            activity.startActivity(intent);


                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "product_list" );
                    mix.put("status", "error" );

                    FlurryAgent.logEvent("Incoming from Deep Linking", mix);
                } catch (Exception e) {
                }


            }
        });
        return jsonObjReq;
    }

    public void getProduct(String  productID){
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantProduct+productID,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> productList = mGson.fromJson(response.toString(), listType);

                                for(int i = 0; i < productList.size(); i++){
                                    Product product = new Product();
                                    product   = productList.get(i);

                                    try {
                                        Map<String, String> mix = new HashMap<String, String>();
                                        mix.put("product_id", String.valueOf(product.getId()));
                                        FlurryAgent.logEvent("Click Product", mix);
                                    } catch (Exception e) {
                                    }

                                    i = productList.size();
                                    Intent intent = new Intent(activity, ProductActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("product", product);
                                    intent.putExtras(bundle);
                                    activity.startActivity(intent);


                                }




                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){            /**
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
        AppController.getInstance().addToRequestQueue(jsonObjReq);
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


                databaseAdapter.insertOrder(product, count, final_item_price, option);
                prefs.saveBooleanPreference(LOGIN_SAVECART, true);
                Intent intent   = new Intent(activity, ActivityLogin.class);
                activity.startActivity(intent);

            }

        }
    }
    public void addToCartProduct(Product product, final Activity activity, int count, int final_item_price, String option){
        prefs.saveBooleanPreference(LOGIN_SAVECART, false);
        databaseAdapter.insertOrder(product, count, final_item_price, option);
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
        else {

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
        dialog.show();

    }

    private void sendVideoLike(int id, final VideoProgramHolder holder)
    {
        String url_end  = "/like";

        if(databaseAdapter.checkVideoLiked(id)){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "unlike");
                mix.put("source", "video_list");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Video Program Like Icon", mix);
            } catch (Exception e) {
            }

            url_end = "/unlike";
            holder.like_image.setImageResource(R.drawable.wishlist);
            databaseAdapter.insertVideoLike(id, "unlike");
            prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)-1));
        }else {
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "like");
                mix.put("source", "video_list");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Video Program Like Icon", mix);
            } catch (Exception e) {
            }
            holder.like_image.setImageResource(R.drawable.wishlist_clicked);
            databaseAdapter.insertVideoLike(id, "like");
            prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)+1));
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, constantVideoProgramLike+id+url_end, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if(response.getInt("cached_votes_score") == 1) {

                                holder.likes.setText(response.getInt("cached_votes_score") + " Like");
                                holder.likes.setVisibility(View.VISIBLE);
                            }else if (response.getInt("cached_votes_score") > 1){

                                holder.likes.setText(response.getInt("cached_votes_score") + " Likes");
                                holder.likes.setVisibility(View.VISIBLE);

                            }else {
                                holder.likes.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                holder.like_image.setImageResource(R.drawable.wishlist);

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
    }

    private void deleteComment (int indexId, final int positionIndex, final Dialog dialogDelete) {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                constantCommentUpdateDelete + indexId , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                dialogDelete.dismiss();
                try{
                    int returnSatatus =  response.getInt("status");

                    if(returnSatatus == 1){
                        universalList.remove(positionIndex);
                        notifyDataSetChanged();
                    }else{
                        ToastHelper.showToast(activity, resources.getString(R.string.search_error));
                    }


                }catch (Exception e){
                    Log.e(TAG, "onResponse:  "  + e.getMessage() );

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialogDelete.dismiss();
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

    private void updateComment (int commentId, final String commentText, final Dialog dlog, final Comment comment, final int positionIndex, final CommentHolder cmHolder) {


        JSONObject uploadOrder = new JSONObject();
        try {
            uploadOrder.put("comment_text",    commentText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, constantCommentUpdateDelete + commentId , uploadOrder, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                dlog.dismiss();

                try{
                    int returnSatatus =  response.getInt("status");

                    if(returnSatatus == 1){

                        cmHolder.body.setText(commentText);
                        comment.setBody(commentText);



                    }else{
                        ToastHelper.showToast(activity, resources.getString(R.string.search_error));
                    }
                }catch (Exception e){
                    Log.e(TAG, "onResponse: "   + e.getMessage() );


                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dlog.dismiss();

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

    private JsonArrayRequest product(String url) {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            ArrayList<Product> arrayList = new ArrayList<>();
                            Type listType = new TypeToken<List<Product>>() {}.getType();
                            List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                            for(int i = 0; i < deliveries.size(); i++) {
                                Product pro = new Product();
                                pro = deliveries.get(i);
                                pro.setPostType(OTHER);
                                arrayList.add(pro);
                            }
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "product" );
                                mix.put("product_id", String.valueOf(arrayList.get(0).getId()));
                                FlurryAgent.logEvent("Incoming from Article Product Api", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, ProductActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("product", arrayList.get(0));
                            bundle.putString("status", "pending");
                            intent.putExtras(bundle);
                            activity.startActivity(intent);


                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    // Tracking Deeplinking error
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "product" );
                    mix.put("product_id", "-1" );

                    FlurryAgent.logEvent("Incoming from Deep Linking", mix);
                } catch (Exception e) {
                }





            }
        });
        return jsonObjReq;
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
        else{
            if (readingFragment != null){
                readingFragment.clickRefresh();
            }
        }


    }

    private void userPostDelete(int id, final int position, final Dialog dialog){
        String url_end  = "/delete";
        String url  = "https://www.kyarlay.com/api/posts/"+id+url_end;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();

                        try{
                            if(response.getInt("status") == 1){

                                dialog.dismiss();
                                universalList.remove(position);
                                notifyItemRemoved(position );



                                ToastHelper.showToast(activity, resources.getString(R.string.post_delete_toast));

                            }
                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

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
    }

    private void sendGroupChatLike(int id, final UserPostHolder holder, final Reading group_chat) {

        String url_end  = "/like";

        if(databaseAdapter.checkPostLiked(id)){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "unlike");
                mix.put("source", "post_list");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Like Icon", mix);
            } catch (Exception e) {
            }
            url_end = "/unlike";

        }else {

            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "like");
                mix.put("source", "post_list");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Like Icon", mix);
            } catch (Exception e) {
            }

        }

        Bundle fbundle = new Bundle();
        fbundle.putString("item_id", group_chat.getId()+"");
        fbundle.putString("item_category", "usre post");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, fbundle);

        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){

            String url  = "https://www.kyarlay.com/api/posts/";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url+id+url_end, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                group_chat.setLikes(response.getInt("cached_votes_score"));

                                if(group_chat.getLikes() == 1)
                                    holder.like_count.setText(group_chat.getLikes()+" Like");
                                else if (group_chat.getLikes() == 0) {
                                    holder.like_count.setText("");
                                    holder.like.setImageResource(R.drawable.wishlist);
                                }
                                else
                                    holder.like_count.setText(group_chat.getLikes()+" Likes");

                                if(databaseAdapter.checkPostLiked(group_chat.getId())){
                                    holder.like.setVisibility(View.VISIBLE);
                                    holder.like.setImageResource(R.drawable.wishlist);
                                    databaseAdapter.insertPostLike(group_chat.getId(), "unlike");
                                    prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)-1));
                                }else {
                                    holder.like.setVisibility(View.VISIBLE);
                                    holder.like.setImageResource(R.drawable.ic_favorite_red_24dp);
                                    databaseAdapter.insertPostLike(group_chat.getId(), "like");
                                    prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)+1));
                                }


                                if(holder.like_count.getText().equals("") && holder.comment_count.getText().equals("")){
                                    holder.linearLikeComment.setVisibility(View.GONE);
                                }else{
                                    holder.linearLikeComment.setVisibility(View.VISIBLE);
                                }

                               /* if(readingFragment != null)
                                    readingFragment.updateLikeCount();
*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
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
        }else{
            Intent intent   = new Intent(activity, ActivityLogin.class);
            activity.startActivity(intent);
        }
    }

    private String calculateKidInfo(){
        String age = "";
        int kid_month   = 0;



        kid_month = (int) (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) / 4.34524);

        if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE)  == 1){

            if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK)  <= 7 ) {
                if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) == 0){
                    age = "NEWBORN";
                }
                else if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) == 1){
                    age = "1 WEEK OLD";
                }
                else {
                    age =  prefs.getIntPreferences(SP_KID_WEEK) + " WEEKS OLD";
                }
            }
            else{
                if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) == 52)
                    age = "1 YEAR OLD"  + "\n" + "( " + prefs.getIntPreferences(SP_KID_CHANGE_WEEK) + " ) weeks";
                else if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK  ) < 52)
                    age = kid_month + " MONTHS OLD"  + "\n" + "( " + prefs.getIntPreferences(SP_KID_CHANGE_WEEK) + " ) weeks";
                else{
                    int year = (int) (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) / 52.1429);
                    int mm ;
                    mm = prefs.getIntPreferences (SP_KID_CHANGE_WEEK )  - (year * 52) ;

                    mm = (int)( mm / 4.34524) ;

                    if (year == 1) {
                        age = "1 YEAR AND"  + "\n" + mm + " MONTHS OLD " + "\n" + "( " + prefs.getIntPreferences(SP_KID_CHANGE_WEEK) + " ) weeks";
                    }
                    else{
                        age = year + " YEARS AND"+ "\n" + mm + " MONTHS OLD "  +  "\n" + "( " + prefs.getIntPreferences(SP_KID_CHANGE_WEEK) + " ) weeks";
                    }


                }

            }


        }
        else{
            age = prefs.getIntPreferences(SP_KID_WEEK) + " WEEKS PREGNANT";
        }

        return age;

    }

    public String getDateInMillis(String srcDate) throws ParseException {
        Calendar cal =  Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            cal.setTime(sdf.parse(srcDate));
            Calendar calendar = Calendar.getInstance();
            long now = calendar.getTimeInMillis();
            long time = cal.getTimeInMillis();

            long diff = now - time;

            int seconds = (int) (diff / 1000) % 60 ;
            int minutes = (int) ((diff / (1000*60)) % 60);
            int hours   = (int) ((diff / (1000*60*60)) % 24);
            int days = (int) (diff / (1000*60*60*24));

            if(days == 0 && hours ==0 && minutes ==0 && seconds > 0){
                return  seconds +" sec ago";
            }else if(days == 0 && hours ==0 && minutes > 0){
                return  minutes+" min ago";
            }else if(days == 0 && hours > 0){
                return hours+" hour ago";
            }else if(days > 0 ){
                return days+" day ago";
            }else if(days > 7){
                return srcDate;
            }else {
                return "";
            }


        } catch (ParseException e1) {
            Log.e(TAG, "getDateInMillis: "  + e1.getMessage() );
            return "";
        }

    }

    private void sendLike(int id, final MainRichtextHolder holder, final Reading reading) {
        String url_end  = "/like";

        if(databaseAdapter.checkPostLiked(id)){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "unlike");
                mix.put("source", "post_list");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Like Icon", mix);
            } catch (Exception e) {
            }
            url_end = "/unlike";

        }else {

            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "like");
                mix.put("source", "post_list");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Like Icon", mix);
            } catch (Exception e) {
            }
        }

        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){

            String url  = "https://www.kyarlay.com/api/posts/";
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url+id+url_end, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                reading.setLikes(response.getInt("cached_votes_score"));

                                if(reading.getLikes() == 1)
                                    holder.likes.setText(reading.getLikes()+" Like");
                                else
                                    holder.likes.setText(reading.getLikes()+" Likes");

                                if(databaseAdapter.checkPostLiked(reading.getId())){
                                    holder.like_image.setVisibility(View.VISIBLE);
                                    holder.like_image.setImageResource(R.drawable.wishlist);
                                    databaseAdapter.insertPostLike(reading.getId(), "unlike");
                                    prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)-1));
                                }else {
                                    holder.like_image.setVisibility(View.VISIBLE);
                                    holder.like_image.setImageResource(R.drawable.wishlist_clicked);
                                    databaseAdapter.insertPostLike(reading.getId(), "like");
                                    prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)+1));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
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
        }else{
            Intent intent   = new Intent(activity, ActivityLogin.class);
            activity.startActivity(intent);
        }


    }

    private void showWarningDialog(String waringText){
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);


        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_warning, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setCancelable(true);


        CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
        CustomTextView txtWarning = mBottomSheetDialog.findViewById(R.id.txtWarning);
        ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);

        txtWarning.setText(waringText);
        title.setText(resources.getString(R.string.warning));
        txtWarning.setTextColor(activity.getResources().getColor(R.color.coloredInactive));


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();
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
    public void sendFriendFeedback(){


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

        least_like.setText(resources.getString(R.string.least_like));
        most_like.setText(resources.getString(R.string.most_like));

        invite.setText(resources.getString(R.string.send));
        text.setText(resources.getString(R.string.friend_feedback));


        CircularTextView [] textViews = {zero, one,two, three, four, five, six, seven, eight,nine, ten};
        for(int i = 0 ; i < 11; i ++){
            textViews[i].setStrokeWidth(1);
            textViews[i].setStrokeColor("#000000");
            textViews[i].setSolidColor("#ffffff");
        }

        zero.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 0));
        one.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 1));
        two.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 2));
        three.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 3));
        four.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 4));
        five.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 5));
        six.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 6));
        seven.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 7));
        eight.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 8));
        nine.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 9));
        ten.setOnClickListener(new clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 10));



        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(numClick != -1){
                    dialog.dismiss();
                    JSONObject invite = new JSONObject();
                    try {
                        invite.put("score", numClick);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                            String.format(constantSendFriendFeedback, prefs.getIntPreferences(SP_MEMBER_ID) )  + "?language=" + prefs.getStringPreferences(SP_LANGUAGE), invite,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.e(TAG, "onResponse: "  + response.toString() );

                                    try {

                                        final Dialog dialog2 = new Dialog(activity);
                                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        dialog2.setContentView(R.layout.dialog_loading);
                                        dialog2.setCancelable(true);
                                        Window window = dialog2.getWindow();
                                        WindowManager.LayoutParams wlp = window.getAttributes();
                                        wlp.gravity = Gravity.CENTER;
                                        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                                        window.setAttributes(wlp);
                                        ProgressBar progressBar = dialog2.findViewById(R.id.progressBar1);
                                        progressBar.setVisibility(View.GONE);
                                        CustomTextView text = dialog2.findViewById(R.id.dialog_loading_text);
                                        text.setVisibility(View.VISIBLE);
                                        text.setText(response.getString("text"));
                                        dialog2.show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e(TAG, "onResponse:  "  + e.getMessage() );
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                            headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                            return headers;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");


                }else{

                    ToastHelper.showToast(activity,resources.getString(R.string.choose_num_feedback));
                }

            }
        });

        dialog.show();

    }

}
