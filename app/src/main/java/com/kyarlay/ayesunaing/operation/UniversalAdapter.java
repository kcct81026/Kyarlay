package com.kyarlay.ayesunaing.operation;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.slider.library.Indicators.PagerIndicator;
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
import com.kyarlay.ayesunaing.activity.AndroidLoadImageFromURLActivity;
import com.kyarlay.ayesunaing.activity.AskProdcutAcitivity;
import com.kyarlay.ayesunaing.activity.BrandActivity;
import com.kyarlay.ayesunaing.activity.BrandAllActivity;
import com.kyarlay.ayesunaing.activity.BrandedDetailActivity;
import com.kyarlay.ayesunaing.activity.CampainActivity;
import com.kyarlay.ayesunaing.activity.CampainDetailActivity;
import com.kyarlay.ayesunaing.activity.CategoryActivity;
import com.kyarlay.ayesunaing.activity.ClinicActivity;
import com.kyarlay.ayesunaing.activity.CollectionDetailActivity;
import com.kyarlay.ayesunaing.activity.CustomCalendarActivity;
import com.kyarlay.ayesunaing.activity.DiscountActivity;
import com.kyarlay.ayesunaing.activity.DueDateActivity;
import com.kyarlay.ayesunaing.activity.EditProfileActivity;
import com.kyarlay.ayesunaing.activity.FlashSalesActivity;
import com.kyarlay.ayesunaing.activity.Get_PregnantActivity;
import com.kyarlay.ayesunaing.activity.GiftDetailsActivity;
import com.kyarlay.ayesunaing.activity.GiftExchangeActivity;
import com.kyarlay.ayesunaing.activity.InviteFriendActivity;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.activity.MainSuperActivity;
import com.kyarlay.ayesunaing.activity.NameWishListActivity;
import com.kyarlay.ayesunaing.activity.NewsClickActivity;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.OlderedActivity;
import com.kyarlay.ayesunaing.activity.OrderDetailActivity;
import com.kyarlay.ayesunaing.activity.PointHistoryActivity;
import com.kyarlay.ayesunaing.activity.ProductActivity;
import com.kyarlay.ayesunaing.activity.ProductListClickActivity;
import com.kyarlay.ayesunaing.activity.ReadingCommentDetailsActivity;
import com.kyarlay.ayesunaing.activity.ReadingWishlistActivity;
import com.kyarlay.ayesunaing.activity.SafeQuesMainActivity;
import com.kyarlay.ayesunaing.activity.SearchResultActivity;
import com.kyarlay.ayesunaing.activity.ShoppingCartActivity;
import com.kyarlay.ayesunaing.activity.ShowAllNamesActivity;
import com.kyarlay.ayesunaing.activity.UserPostDetailActivity;
import com.kyarlay.ayesunaing.activity.UserPostUploadActivity;
import com.kyarlay.ayesunaing.activity.WishListActivity;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
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
import com.kyarlay.ayesunaing.fragment.FragmentTest;
import com.kyarlay.ayesunaing.holder.BannerHolder;
import com.kyarlay.ayesunaing.holder.BannerPhotoHolder;
import com.kyarlay.ayesunaing.holder.BrandBannerHolder;
import com.kyarlay.ayesunaing.holder.BrandHolder;
import com.kyarlay.ayesunaing.holder.CalendarDayHolder;
import com.kyarlay.ayesunaing.holder.CampianHolder;
import com.kyarlay.ayesunaing.holder.CategoryDetailHolder;
import com.kyarlay.ayesunaing.holder.CategoryGridItemHolder;
import com.kyarlay.ayesunaing.holder.CustomMainHolder;
import com.kyarlay.ayesunaing.holder.DeliveryStatusHolder;
import com.kyarlay.ayesunaing.holder.DetailGridHolder;
import com.kyarlay.ayesunaing.holder.DetailImageHolder;
import com.kyarlay.ayesunaing.holder.FilterHolder;
import com.kyarlay.ayesunaing.holder.FlashSalesHolder;
import com.kyarlay.ayesunaing.holder.FooterHoloder;
import com.kyarlay.ayesunaing.holder.GiftHolder;
import com.kyarlay.ayesunaing.holder.GiftItemHolder;
import com.kyarlay.ayesunaing.holder.HotItemHolder;
import com.kyarlay.ayesunaing.holder.ItemPaymentHolder;
import com.kyarlay.ayesunaing.holder.MainAdsHolder;
import com.kyarlay.ayesunaing.holder.MainDiscountGridHolder;
import com.kyarlay.ayesunaing.holder.MainGridCategoryHolder;
import com.kyarlay.ayesunaing.holder.MainGridHolder;
import com.kyarlay.ayesunaing.holder.MainGridItemHolder;
import com.kyarlay.ayesunaing.holder.MainHolder;
import com.kyarlay.ayesunaing.holder.MemberHolder;
import com.kyarlay.ayesunaing.holder.MoreHolder;
import com.kyarlay.ayesunaing.holder.NewArrivalMainHolder;
import com.kyarlay.ayesunaing.holder.NoItemHolder;
import com.kyarlay.ayesunaing.holder.NotificationHolder;
import com.kyarlay.ayesunaing.holder.OrderDetailHeaderHolder;
import com.kyarlay.ayesunaing.holder.OrderDetailTopHolder;
import com.kyarlay.ayesunaing.holder.OrderDetailsFooter;
import com.kyarlay.ayesunaing.holder.OrderHistoryHolder;
import com.kyarlay.ayesunaing.holder.OrderNowHolder;
import com.kyarlay.ayesunaing.holder.OrderTotalHolder;
import com.kyarlay.ayesunaing.holder.PointHistoryHolder;
import com.kyarlay.ayesunaing.holder.PointInfoHolder;
import com.kyarlay.ayesunaing.holder.PointItemHolder;
import com.kyarlay.ayesunaing.holder.PointTitleHolder;
import com.kyarlay.ayesunaing.holder.ProductCategoryDetailItem;
import com.kyarlay.ayesunaing.holder.ProductDetailsHolder;
import com.kyarlay.ayesunaing.holder.ProductItemHolder;
import com.kyarlay.ayesunaing.holder.QAHolder;
import com.kyarlay.ayesunaing.holder.RefreshHolder;
import com.kyarlay.ayesunaing.holder.SearchItemHolder;
import com.kyarlay.ayesunaing.holder.SettingsHolder;
import com.kyarlay.ayesunaing.holder.ShopHolder;
import com.kyarlay.ayesunaing.holder.ToolSubCatHolder;
import com.kyarlay.ayesunaing.holder.TrendingItemHolder;
import com.kyarlay.ayesunaing.holder.UploadPostGetPointHolder;
import com.kyarlay.ayesunaing.holder.UserPostUploadHolder;
import com.kyarlay.ayesunaing.holder.UserProfileHolder;
import com.kyarlay.ayesunaing.holder.UserProfileNewHolder;
import com.kyarlay.ayesunaing.holder.VideoAllHolder;
import com.kyarlay.ayesunaing.holder.VideoPostHolder;
import com.kyarlay.ayesunaing.holder.VideoProgramHolder;
import com.kyarlay.ayesunaing.holder.WatchMoreHolder;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.Campaign;
import com.kyarlay.ayesunaing.object.CategoryGrid;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.Discount;
import com.kyarlay.ayesunaing.object.Flash_Sales;
import com.kyarlay.ayesunaing.object.Gift;
import com.kyarlay.ayesunaing.object.GiftObject;
import com.kyarlay.ayesunaing.object.GiftObjectList;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.object.InviteHistory;
import com.kyarlay.ayesunaing.object.LuckyDay;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.NameObject;
import com.kyarlay.ayesunaing.object.Order;
import com.kyarlay.ayesunaing.object.Order123;
import com.kyarlay.ayesunaing.object.OrderDetailsObj;
import com.kyarlay.ayesunaing.object.PaymentObject;
import com.kyarlay.ayesunaing.object.PointHistory;
import com.kyarlay.ayesunaing.object.PointInfo;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.QAObject;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.SafeItem;
import com.kyarlay.ayesunaing.object.SafeMainObj;
import com.kyarlay.ayesunaing.object.Settings;
import com.kyarlay.ayesunaing.object.ShopLocation;
import com.kyarlay.ayesunaing.object.ToolChildObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.object.Videos;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

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

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class UniversalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements ConstantVariable, Constant, ConstantsDB {

    private static final String TAG = "UniversalAdapter";
    private static List<CategoryMain> moreItems = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    int numClick    = -1;

    ArrayList<UniversalPost> universalList;
    AppCompatActivity activity;
    ImageLoader imageLoader;
    Display display;
    DatabaseAdapter databaseAdapter;
    MyPreference prefs;
    ArrayList<Delivery> delList = new ArrayList<>();
    ArrayList<NameObject> nameObjectArrayList = new ArrayList<>();

    UniversalAdapter gridAdapter;
    AgeConfig ageConfig;


    int totalPointToServer  = 0;

    DecimalFormat formatter = new DecimalFormat("#,###,###");
    Resources resources;
    String android_id;
    FirebaseAnalytics firebaseAnalytics;
    Map<Integer, CountDownTimer> countDownMap;

    //Handler handler;



    Date today = new Date();

    public UniversalAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList, Map<Integer, CountDownTimer> countDownMap) {
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

        android_id = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
            prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);
        }

        nameObjectArrayList = databaseAdapter.getNameList();
        ageConfig = new AgeConfig(activity);

    }



    public UniversalAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList) {
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

        android_id = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
            prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);
        }

        nameObjectArrayList = databaseAdapter.getNameList();
        ageConfig = new AgeConfig(activity);
    }




    @Override
    public int getItemCount() {
        return universalList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (universalList.get(position).getPostType().equals(SLIDER)) {
            return VIEW_TYPE_MAIN_ADS;
        }else if (universalList.get(position).getPostType().equals(MAIN_GRIDVIEW)) {
            return VIEW_TYPE_MAIN;
        }else if (universalList.get(position).getPostType().equals(MAIN_DISCOUNT)) {
            return VIEW_TYPE_MAIN_DISCOUNT;
        }else if (universalList.get(position).getPostType().equals(SEARCHED)) {
            return VIEW_TYPE_SEARCHED;
        }else if (universalList.get(position).getPostType().equals(TRENDING)) {
            return VIEW_TYPE_TRENDING;
        }else if (universalList.get(position).getPostType().equals(SEARCHED_ITEM_NEW)) {
            return VIEW_TYPE_SEARCHED_ITEM_NEW;
        }else if (universalList.get(position).getPostType().equals(TRENDING_ITEM)) {
            return VIEW_TYPE_TRENDING_ITEM;
        }else if (universalList.get(position).getPostType().equals(TOTAL_ORDER)) {
            return VIEW_TYPE_CART_TOTAL_ORDER;
        }else if(universalList.get(position).getPostType().equals(CART_DETAIL_FOOTER)){
            return VIEW_TYPE_CART_DETAIL_FOOTER;
        }

        else if(universalList.get(position).getPostType().equals(BRAND)){
            return VIEW_TYPE_BRAND;
        }else if(universalList.get(position).getPostType().equals(SETTINGS_TYPE)){
            return VIEW_TYPE_SETTINGS_TYPE;
        }

        else if(universalList.get(position).getPostType().equals(MEMBER_CHECKING)){
            return VIEW_TYPE_MEMBER_CHECKING;
        }

        else if (universalList.get(position).getPostType().equals(MAIN_GRIDVIEW_ITEM)) {
            return VIEW_TYPE_MAIN_GRIDVIEW;
        }else if (universalList.get(position).getPostType().equals(CATEGORY_DETAIL)) {
            return VIEW_TYPE_CATEGORYF_DETAIL_ITEM;
        }else if (universalList.get(position).getPostType().equals(BRANDED_DETAIL)) {
            return VIEW_TYPE_CATEGORYF_DETAIL_ITEM;
        }else if (universalList.get(position).getPostType().equals(ORDER_DETAIL_HEADER)) {
            return VIEW_TYPE_ORDER_DETAIL_HEADER;
        }else if (universalList.get(position).getPostType().equals(ORDER_DETAIL)) {
            return VIEW_TYPE_ORDER_DETAIL;
        }else if(universalList.get(position).getPostType().equals(REFRESH_FOOTER)){
            return VIEW_TYPE_REFRESH_FOOTER;
        }else if(universalList.get(position).getPostType().equals(CART_DETAIL_NO_ITEM)){
            return VIEW_TYPE_CART_DETAIL_NO_ITEM;
        }else if(universalList.get(position).getPostType().equals(CATEGORY_DETAIL_WISHLIST)){
            return VIEW_TYPE_CATEGORY_DETAIL_WISHLIST;
        }else if(universalList.get(position).getPostType().equals(CAMPAIGN)){
            return VIEW_TYPE_CAMPAIGN;
        }else if(universalList.get(position).getPostType().equals(CAMPAIGN_MORE)){
            return VIEW_TYPE_CAMPAIGN_MORE;
        }

        else if(universalList.get(position).getPostType().equals(BANNER_PHOTO)){
            return VIEW_TYPE_BANNER_PHOTO;
        }else if(universalList.get(position).getPostType().equals(BRAND_BANNER)){
            return VIEW_TYPE_BRAND_BANNER;
        }else if(universalList.get(position).getPostType().equals(BRAND_BANNER_ITEM)){
            return VIEW_TYPE_BRAND_BANNER_ITEM;
        }else if(universalList.get(position).getPostType().equals(DISCOUNT_TITLE)){
            return VIEW_TYPE_DISCOUNT_TITLE;
        }


        else if(universalList.get(position).getPostType().equals(NOTIFICATION)){
            return VIEW_TYPE_NOTIFICATION;
        }

        else if(universalList.get(position).getPostType().equals(VIDEO_POST)){
            return VIEW_TYPE_VIDEO_POST;
        }else if(universalList.get(position).getPostType().equals(USER_POST_UPLOAD)){
            return VIEW_TYPE_USER_POST_UPLOAD;
        }else if(universalList.get(position).getPostType().equals(GIFT)){
            return VIEW_TYPE_GIFT;
        }

        else if (universalList.get(position).getPostType().equals(CALENDAR_DAY)) {
            return VIEW_TYPE_CALENDAR_DAYS;
        }else if (universalList.get(position).getPostType().equals(posting)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(signup)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(invite)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(feedback)) {
            return VIEW_TYPE_UPLOAD_POST_GET_POINT;
        }else if (universalList.get(position).getPostType().equals(POINT_HISTORY)) {
            return VIEW_TYPE_POINT_HISTORY;
        }
        else if (universalList.get(position).getPostType().equals(CATEGORY_GRID)) {
            return VIEW_TYPE_CATEGORY_GRID;
        }
        else if (universalList.get(position).getPostType().equals(CATEGORY_GRID_ITEM)) {
            return VIEW_TYPE_CATEGORY_GRID_ITEM;
        }



        else if (universalList.get(position).getPostType().equals(SUB_CATEGORY_GRID)) {
            return VIEW_TYPE_SUB_CATEGORY_GRID;
        }
        else if (universalList.get(position).getPostType().equals(SUB_CATEGORY_GRID_ITEM)) {
            return VIEW_TYPE_SUB_CATEGORY_GRID_ITEM;
        }

        else if (universalList.get(position).getPostType().equals(USER_LOGIN)) {
            return VIEW_TYPE_USER_LOGIN;
        }

        else if (universalList.get(position).getPostType().equals(USER_PROFILE)) {
            return VIEW_TYPE_USER_PROFILE;
        }



        else if (universalList.get(position).getPostType().equals(POINT_TITLE)) {
            return VIEW_TYPE_POINT_TITLE;
        }

        else if (universalList.get(position).getPostType().equals(INVITE_HISTORY)) {
            return VIEW_TYPE_INVITE_HISTORY;
        }

        else if (universalList.get(position).getPostType().equals(DETAIL_GRID)) {
            return VIEW_TYPE_DETAIL_GRID;
        }

        else if (universalList.get(position).getPostType().equals(DETAIL_IMAGE)) {
            return VIEW_TYPE_DETAIL_IMAGE;
        }

        else if (universalList.get(position).getPostType().equals(GIFT_ITEM)) {
            return VIEW_TYPE_GIFT_ITEM;
        }

        else if (universalList.get(position).getPostType().equals(VIDEO_NEWS_SMALL)) {
            return VIEW_TYPE_VIDEO_NEWS_SMALL;
        }



        else if (universalList.get(position).getPostType().equals(VIDEO_MORE)) {
            return VIEW_TYPE_VIDEO_MORE;
        }

        else if(universalList.get(position).getPostType().equals(VIDEO_ALL)){
            return VIEW_TYPE_VIDEO_ALL;
        }

        else if(universalList.get(position).getPostType().equals(BACKGROUND_TITLE)){
            return VIEW_TYPE_BACKGROUND_TITLE;
        }

        else if(universalList.get(position).getPostType().equals(SHOW_IMAGE)){
            return VIEW_TYPE_SHOW_IMAGE;
        }

        else if (universalList.get(position).getPostType().equals(TOOL_SUB_CATEGORY)) {
            return VIEW_TYPE_TOOL_SUB_CATEGORY;
        }


        else if(universalList.get(position).getPostType().equals(ORDER_DETAILS_FOOTER)) {
            return VIEW_TYPE_ORDER_DETAILS_FOOTER;
        }

        else if(universalList.get(position).getPostType().equals(ORDER_HISTORY)) {
            return VIEW_TYPE_ORDER_HISTORY;
        }

        else if(universalList.get(position).getPostType().equals(ORDER_DELIVERY_STATUS)) {
            return VIEW_TYPE_ORDER_DELIVERY_STATUS;
        }

        else if (universalList.get(position).getPostType().equals(MAIN_GIFT_OBJECT)) {
            return VIEW_TYPE_MAIN_GIFT_OBJECT;
        }


        else if (universalList.get(position).getPostType().equals(GIFT_OBJECT_SHOW_ITEM)) {
            return VIEW_TYPE_GIFT_OBJECT_SHOW_ITEM;
        }

        else if (universalList.get(position).getPostType().equals(PRODUCT_FLASH_SALE)) {
            return VIEW_TYPE_PRODUCT_FLASH_SALE;
        }
        else if (universalList.get(position).getPostType().equals(MAIN_PAGE_CATEGORY)) {
            return VIEW_TYPE_MAIN_PAGE_CATEGORY;
        }

        else if (universalList.get(position).getPostType().equals(MAIN_PAGE_CATEGORY_ITEM)) {
            return VIEW_TYPE_MAIN_PAGE_CATEGORY_ITEM;
        }


        else if (universalList.get(position).getPostType().equals(SUPER_CATEGORY_ITEM)) {
            return VIEW_TYPE_SUPER_CATEGORY_ITEM;
        }

        else  if (universalList.get(position).getPostType().equals(MASTER_LEFT_ITEM)) {
            return VIEW_TYPE_MASTER_LEFT_ITEM;
        }


        else if(universalList.get(position).getPostType().equals(COLLECTIONS_MORE)){
            return VIEW_TYPE_COLLECTIONS_MORE;
        }

        else if (universalList.get(position).getPostType().equals(SAFE_SUB_MAIN) ){
            return VIEW_TYPE_SAFE_SUB_MAIN;
        }

        else if (universalList.get(position).getPostType().equals(QA_ITEM) ){
            return VIEW_TYPE_QA_ITEM;
        }

        else if (universalList.get(position).getPostType().equals(QA_PRODUCT_ITEM) ){
            return VIEW_TYPE_QA_PRODUCT_ITEM;
        }

        else if (universalList.get(position).getPostType().equals(MAIN_GRID_CATEOGRY)) {
            return VIEW_TYPE_MAIN_GRID_CATEOGRY;
        }

        else if (universalList.get(position).getPostType().equals(MAIN_GRID_CATEOGRY_ITEM)) {
            return VIEW_TYPE_MAIN_GRID_CATEOGRY_ITEM;
        }
        else if (universalList.get(position).getPostType().equals(CATEGORY_BANNER)) {
            return VIEW_TYPE_CATEGORY_BANNER;
        }

        else if (universalList.get(position).getPostType().equals(TOOL_SUPER_GRID_ITEM)) {
            return VIEW_TYPE_TOOL_SUPER_GRID_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(READING_TOOL)){
            return VIEW_TYPE_READING_TOOL;
        }

        else if(universalList.get(position).getPostType().equals(POINT_INFO_TEXT)){
            return VIEW_TYPE_POINT_INFO_TEXT;
        }

        else if(universalList.get(position).getPostType().equals(SHOP_LOCATION)){
            return VIEW_TYPE_SHOP_LOCATION;
        }


        else if(universalList.get(position).getPostType().equals(STEP_ONE_BOTTOM)){
            return VIEW_TYPE_STEP_ONE_BOTTOM;
        }

        else if(universalList.get(position).getPostType().equals(POPULAR_BANNER)){
            return VIEW_TYPE_POPULAR_BANNER;
        }

        else if(universalList.get(position).getPostType().equals(STEP_TWO_PAYMENT_ITEM)){
            return VIEW_TYPE_STEP_TWO_PAYMENT_ITEM;
        }

        else if(universalList.get(position).getPostType().equals(MORE_ALL)){
            return VIEW_TYPE_MORE_ALL;
        }

        else if(universalList.get(position).getPostType().equals(PRODUCT_DETAIL)){
            return VIEW_TYPE_PRODUCT_DETAIL;
        }


        else if(universalList.get(position).getPostType().equals(ORDER_DETAIL_TOP)){
            return VIEW_TYPE_ORDER_DETAIL_TOP;
        }

        else if(universalList.get(position).getPostType().equals(STAGGERED_ITEM)){
            return VIEW_TYPE_STAGGERED_ITEM;
        }


        else if (universalList.get(position).getPostType().equals(FILTER_UI)) {
            return VIEW_TYPE_FILTER_UI;
        }
        else if (universalList.get(position).getPostType().equals(USER_PROFILE_NEW)) {
            return VIEW_TYPE_USER_PROFILE_NEW;
        }

        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == VIEW_TYPE_MAIN_ADS){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_ads, parent, false);
            viewHolder = new MainAdsHolder(viewItem, activity);
        }else if(viewType == VIEW_TYPE_UPLOAD_POST_GET_POINT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.upload_post_get_point, parent, false);
            viewHolder = new UploadPostGetPointHolder(viewItem);
        }else if(viewType == VIEW_TYPE_CALENDAR_DAYS){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.calendar_days, parent, false);
            viewHolder = new CalendarDayHolder(viewItem, display);
        }

        else if(viewType == VIEW_TYPE_USER_PROFILE_NEW) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_user_profile_new, parent, false);
            viewHolder = new UserProfileNewHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_MAIN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }else if (viewType == VIEW_TYPE_MAIN_DISCOUNT) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }else if (viewType == VIEW_TYPE_SEARCHED) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }else if (viewType == VIEW_TYPE_TRENDING) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }else if (viewType == VIEW_TYPE_SEARCHED_ITEM_NEW) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_item, parent, false);
            viewHolder = new SearchItemHolder(viewItem);
        }else if (viewType == VIEW_TYPE_TRENDING_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trending_item, parent, false);
            viewHolder = new TrendingItemHolder(viewItem);
        } else if (viewType == VIEW_TYPE_CART_TOTAL_ORDER) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.orders, parent, false);
            viewHolder = new OrderTotalHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_SETTINGS_TYPE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.settings_layout, parent, false);
            viewHolder = new SettingsHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_MEMBER_CHECKING){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.member, parent, false);
            viewHolder = new MemberHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_MAIN_GRIDVIEW) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new MainGridItemHolder(viewItem, display);
        }else if(viewType == VIEW_TYPE_CATEGORY_DETAIL_WISHLIST){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_detail_item_product, parent, false);
            viewHolder = new ProductCategoryDetailItem(viewItem, display);
        }else if(viewType == VIEW_TYPE_CATEGORYF_DETAIL_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_detail_item_product, parent, false);
            viewHolder = new ProductCategoryDetailItem(viewItem, display);
        }else if (viewType == VIEW_TYPE_ORDER_DETAIL_HEADER) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_detail_header, parent, false);
            viewHolder = new OrderDetailHeaderHolder(viewItem);
        }else if (viewType == VIEW_TYPE_ORDER_DETAIL) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_detail_item, parent, false);
            viewHolder = new CategoryDetailHolder(viewItem);
        }else if(viewType == VIEW_TYPE_CART_DETAIL_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_footer, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }else if(viewType == VIEW_TYPE_CART_DETAIL_NO_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new NoItemHolder(viewItem);
        }


        else if (viewType == VIEW_TYPE_CAMPAIGN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.campian, parent, false);
            viewHolder = new CampianHolder(viewItem);
        }else if (viewType == VIEW_TYPE_CAMPAIGN_MORE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.campian, parent, false);
            viewHolder = new CampianHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_BANNER_PHOTO) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.banner_photo, parent, false);
            viewHolder = new BannerPhotoHolder(viewItem, display);
        }else if(viewType == VIEW_TYPE_BRAND_BANNER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }else if(viewType == VIEW_TYPE_BRAND){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_category_item, parent, false);
            viewHolder = new BrandHolder(viewItem, display);
        }else if(viewType == VIEW_TYPE_BRAND_BANNER_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.brand_banner_image, parent, false);
            viewHolder = new BrandBannerHolder(viewItem);
        }else if(viewType == VIEW_TYPE_DISCOUNT_TITLE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }

        else if(viewType == VIEW_TYPE_NOTIFICATION){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notification,parent, false);
            viewHolder = new NotificationHolder(viewItem, display);
        }else if(viewType == VIEW_TYPE_VIDEO_POST){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_post,parent, false);
            viewHolder = new VideoPostHolder(viewItem, display);
        }else if(viewType == VIEW_TYPE_USER_POST_UPLOAD){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_post_upload_item, parent, false);
            viewHolder = new UserPostUploadHolder(viewItem);
        }else if(viewType == VIEW_TYPE_GIFT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gift_adapter, parent, false);
            viewHolder = new GiftHolder(viewItem);
        }else if(viewType == VIEW_TYPE_POINT_HISTORY){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.point_history, parent, false);
            viewHolder = new PointHistoryHolder(viewItem, display);
        }

        else if (viewType == VIEW_TYPE_CATEGORY_GRID) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_CATEGORY_GRID_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new CategoryGridItemHolder(viewItem, display);
        }


        else if (viewType == VIEW_TYPE_SUB_CATEGORY_GRID) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_new_arrival, parent, false);
            viewHolder = new NewArrivalMainHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_SUB_CATEGORY_GRID_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_hot_item, parent, false);
            viewHolder = new HotItemHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_USER_LOGIN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_item, parent, false);
            viewHolder = new PointItemHolder(viewItem);
        }


        else if(viewType == VIEW_TYPE_USER_PROFILE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_user_profile, parent, false);
            viewHolder = new UserProfileHolder(viewItem, display);
        }



        else if(viewType == VIEW_TYPE_POINT_TITLE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_title, parent, false);
            viewHolder = new PointTitleHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_INVITE_HISTORY){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.point_history, parent, false);
            viewHolder = new PointHistoryHolder(viewItem, display);
        }

        else if(viewType == VIEW_TYPE_DETAIL_GRID){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_detail_image, parent, false);
            viewHolder = new DetailGridHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_DETAIL_IMAGE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_detail_item_image, parent, false);
            viewHolder = new DetailImageHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_GIFT_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_gift_item, parent, false);
            viewHolder = new GiftItemHolder(viewItem);
        }

      /*  else if(viewType == VIEW_TYPE_VIDEO_NEWS_SMALL){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_video_latest, parent, false);
            viewHolder = new VideoLatestHolder(viewItem, display);
        }
*/
        else if(viewType == VIEW_TYPE_TOOL_SUB_CATEGORY){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_tool_grid_item, parent, false);
            viewHolder = new ToolSubCatHolder(viewItem, display);
        }


        else if(viewType == VIEW_TYPE_VIDEO_MORE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_watch_more, parent, false);
            viewHolder = new WatchMoreHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_VIDEO_ALL){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_video_all,parent, false);
            viewHolder = new VideoAllHolder(viewItem, display);
        }

        else if(viewType == VIEW_TYPE_BACKGROUND_TITLE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }
        else if(viewType == VIEW_TYPE_SHOW_IMAGE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new MainGridItemHolder(viewItem, display);
        }





        else if(viewType == VIEW_TYPE_ORDER_DETAILS_FOOTER) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_order_details_footer, parent, false);
            viewHolder = new OrderDetailsFooter(viewItem);
        }

        else if(viewType == VIEW_TYPE_ORDER_HISTORY) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_order_history, parent, false);
            viewHolder = new OrderHistoryHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_ORDER_DELIVERY_STATUS) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_delivery_status, parent, false);
            viewHolder = new DeliveryStatusHolder(viewItem, display);
        }

        else if(viewType == VIEW_TYPE_REFRESH_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_touch_refresh, parent, false);
            viewHolder = new RefreshHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_MAIN_GIFT_OBJECT) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_discount_grid_layout, parent, false);
            viewHolder = new MainDiscountGridHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_GIFT_OBJECT_SHOW_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_main_gift_item, parent, false);
            viewHolder = new GiftItemHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_PRODUCT_FLASH_SALE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_flash_sale_item, parent, false);
            viewHolder = new FlashSalesHolder(viewItem, display);
        }

        else if (viewType == VIEW_TYPE_MAIN_PAGE_CATEGORY) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_grid_layout, parent, false);
            viewHolder = new MainGridHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_MAIN_PAGE_CATEGORY_ITEM ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new MainHolder(viewItem, display);
        }



        else if(viewType == VIEW_TYPE_SUPER_CATEGORY_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new CategoryGridItemHolder(viewItem, display);
        }




        else if(viewType == VIEW_TYPE_MASTER_LEFT_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new CategoryGridItemHolder(viewItem, display);
        }


        else if (viewType == VIEW_TYPE_COLLECTIONS_MORE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.campian, parent, false);
            viewHolder = new CampianHolder(viewItem);
        }


        else if (viewType == VIEW_TYPE_SAFE_SUB_MAIN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_discount_grid_layout, parent, false);
            viewHolder = new MainDiscountGridHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_QA_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_q_a_item, parent, false);
            viewHolder = new QAHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_QA_PRODUCT_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_q_a_item, parent, false);
            viewHolder = new QAHolder(viewItem);
        }


        else if (viewType == VIEW_TYPE_MAIN_GRID_CATEOGRY) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_main_grid_category, parent, false);
            viewHolder = new MainGridCategoryHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_MAIN_GRID_CATEOGRY_ITEM ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_grid_default_item, parent, false);
            viewHolder = new CustomMainHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_CATEGORY_BANNER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_banner, parent, false);
            viewHolder = new BannerHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_TOOL_SUPER_GRID_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maingrid_item, parent, false);
            viewHolder = new CategoryGridItemHolder(viewItem, display);
        }

        else if(viewType == VIEW_TYPE_POINT_INFO_TEXT){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_info, parent, false);
            viewHolder = new PointInfoHolder(viewItem);
        }


        else if(viewType == VIEW_TYPE_SHOP_LOCATION){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_shop_location, parent, false);
            viewHolder = new ShopHolder(viewItem);
        }


        else if(viewType == VIEW_TYPE_STEP_ONE_BOTTOM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_shopping_order_now, parent, false);
            viewHolder = new OrderNowHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_POPULAR_BANNER ){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_banner, parent, false);
            viewHolder = new BannerHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_STEP_TWO_PAYMENT_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_payment_item, parent, false);
            viewHolder = new ItemPaymentHolder(viewItem);
        }


        else if (viewType == VIEW_TYPE_MORE_ALL) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_more, parent, false);
            viewHolder = new MoreHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_PRODUCT_DETAIL) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_product_detail, parent, false);
            viewHolder = new ProductDetailsHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_ORDER_DETAIL_TOP) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_order_details_top, parent, false);
            viewHolder = new OrderDetailTopHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_STAGGERED_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_product_item_staggered, parent, false);
            viewHolder = new ProductItemHolder(viewItem);
        }

        else if(viewType == VIEW_TYPE_FILTER_UI) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_filter_listgrid, parent, false);
            viewHolder = new FilterHolder(viewItem);
        }




        return viewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"WrongConstant", "StringFormatInvalid", "StringFormatMatches"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, final int position) {

        int type = getItemViewType(position);

        switch (type){


            case VIEW_TYPE_FILTER_UI:{
                FilterHolder filterHolder = (FilterHolder) parentHolder;
                Product product = (Product) universalList.get(position);
                // filterHolder.txtFilterTitle.setText(product.getTitle());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    filterHolder.txtFilterTitle.setText(Html.fromHtml(
                            product.getTitle()  , Html.FROM_HTML_MODE_COMPACT));
                } else {
                    filterHolder.txtFilterTitle.setText(Html.fromHtml(product.getTitle()));

                }

                if (product.getId() == 1){
                    filterHolder.imgList.setVisibility(View.VISIBLE);
                    filterHolder.imgGrid.setVisibility(View.GONE);
                }
                else{
                    filterHolder.imgList.setVisibility(View.GONE);
                    filterHolder.imgGrid.setVisibility(View.VISIBLE);
                }

                filterHolder.filter_main_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(activity.getLocalClassName().contains("CategoryActivity")) {

                            CategoryActivity pro = (CategoryActivity) activity;
                            pro.showFilterDilaog();
                        }
                    }
                });

                filterHolder.imgGrid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(activity.getLocalClassName().contains("CategoryActivity")) {


                            CategoryActivity pro = (CategoryActivity) activity;
                            pro.setUI(0, product.getTitle());
                        }
                    }
                });

                filterHolder.imgList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(activity.getLocalClassName().contains("CategoryActivity")) {

                            CategoryActivity pro = (CategoryActivity) activity;
                            pro.setUI(1, product.getTitle());
                        }
                    }
                });


                break;
            }

            case VIEW_TYPE_USER_PROFILE_NEW:{
                UserProfileNewHolder userProfileNewHolder = (UserProfileNewHolder) parentHolder;

                try {
                    if (prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("null")) {
                        userProfileNewHolder.imgProfile.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
                    } else if (!prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals(""))
                        userProfileNewHolder.imgProfile.setImageUrl(prefs.getStringPreferences(SP_USER_PROFILEIMAGE), AppController.getInstance().getImageLoader());
                    else
                        userProfileNewHolder.imgProfile.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);


                } catch (Exception e) {
                    userProfileNewHolder.imgProfile.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }
                userProfileNewHolder.txtMemberStatus.setTypeface(userProfileNewHolder.txtMemberStatus.getTypeface(), Typeface.BOLD);

                if (prefs.getStringPreferences(SP_USER_NAME) != "") {
                    userProfileNewHolder.txtName.setVisibility(View.VISIBLE);
                    String age;

                    if (prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status") || prefs.getStringPreferences(SP_USER_MOMORDAD).equals("other")) {
                        age = resources.getString(R.string.family_member);
                    } else {
                        age = ageConfig.calculateKidAge(prefs.getIntPreferences(SP_USER_MONOTH), prefs.getIntPreferences(SP_USER_YEAR),
                                prefs.getStringPreferences(SP_USER_BOYORGIRL), prefs.getStringPreferences(SP_USER_MOMORDAD));
                    }


                    userProfileNewHolder.txtName.setText(prefs.getStringPreferences(SP_USER_NAME) + " \n" + age);
                } else
                    userProfileNewHolder.txtName.setVisibility(View.GONE);

                if (prefs.getStringPreferences(SP_USER_PHONE) != "") {
                    userProfileNewHolder.txtPhone.setVisibility(View.VISIBLE);
                    userProfileNewHolder.txtPhone.setText(prefs.getStringPreferences(SP_USER_PHONE));
                } else
                    userProfileNewHolder.txtPhone.setVisibility(View.GONE);

                if (prefs.getStringPreferences(SP_USER_VIP_ACCOUNT) != "") {
                    userProfileNewHolder.txtMemberStatus.setVisibility(View.VISIBLE);
                    userProfileNewHolder.txtMemberStatus.setText(prefs.getStringPreferences(SP_USER_VIP_ACCOUNT));

                } else {
                    userProfileNewHolder.txtMemberStatus.setVisibility(View.GONE);
                }

                userProfileNewHolder.txtWishListCount.setText(prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT)+"");
                userProfileNewHolder.txtPostCount.setText(prefs.getIntPreferences(SP_POST_LIKED_COUNT)+"");
                userProfileNewHolder.txtPoint.setText(prefs.getIntPreferences(SP_USER_POINT)+"");

                userProfileNewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveIntPerferences(SP_CHANGE, 0 );
                        Intent intent =  new Intent(activity, EditProfileActivity.class);
                        activity.startActivity(intent);
                    }
                });

                userProfileNewHolder.linearPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "post_list");
                            FlurryAgent.logEvent("Click Liked Posts Icon", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ReadingWishlistActivity.class);
                        activity.startActivity(intent);
                    }
                });


                userProfileNewHolder.linearPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(activity, PointHistoryActivity.class);
                        activity.startActivity(intent);
                    }
                });

                userProfileNewHolder.linearWishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "collection_list");
                            FlurryAgent.logEvent("Click Product Wishlist Icon", mix);

                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, WishListActivity.class);
                        activity.startActivity(intent);
                    }
                });

                userProfileNewHolder.imgProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.layout_qr_code);
                        dialog.setCancelable(true);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        window.setAttributes(wlp);
                        dialog.setCanceledOnTouchOutside(true);

                        ImageView img  = (ImageView) dialog.findViewById(R.id.imgQrCode);

                        Bitmap qrCodeBitmap = EncodingUtils.createQRCode(prefs.getStringPreferences(SP_USER_PHONE), 800, 800, null);
                        img.setImageBitmap(qrCodeBitmap);


                        dialog.show();
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

                    catDetailHolder.linearMainTwo.setOnClickListener(new UniversalAdapter.OnClickProduct(activity, detailProductTwo));
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

                catDetailHolder.linearMain.setOnClickListener(new UniversalAdapter.OnClickProduct(activity, detailProduct));


                break;
            }

            case VIEW_TYPE_ORDER_DETAIL_TOP :{

                OrderDetailTopHolder orderDetailTopHolder = (OrderDetailTopHolder) parentHolder;
                Order orderTop = (Order) universalList.get(position);

                orderDetailTopHolder.txtOrderDate.setText(orderTop.getDelivery_date());
                orderDetailTopHolder.txtDeliveryAddresss.setText(orderTop.getAddress());
                orderDetailTopHolder.txtOrderStatus.setText(orderTop.getStatus());
                orderDetailTopHolder.txtOrderId.setText("#"+orderTop.getOrder_id());

                orderDetailTopHolder.orderconfirmTitle.setText(resources.getString(R.string.order_confirm));
                orderDetailTopHolder.orderdeliveyTitle.setText(resources.getString(R.string.order_Delivery));
                orderDetailTopHolder.orderplaceTitle.setText(resources.getString(R.string.order_placed));
                orderDetailTopHolder.orderreadyTitle.setText(resources.getString(R.string.order_Ready));


                if (orderTop.getDelivery_slot() == null ){
                    orderDetailTopHolder.txtDeliverySlot.setVisibility(View.GONE);
                }
                else{
                    if (orderTop.getDelivery_slot().equals("no_slot") ) {
                        orderDetailTopHolder.txtDeliverySlot.setText(orderTop.getDelivery_date() + " " + orderTop.getDelivery_slot());
                        orderDetailTopHolder.txtDeliverySlot.setVisibility(View.VISIBLE);
                    }
                    else
                        orderDetailTopHolder.txtDeliverySlot.setVisibility(View.GONE);
                }





                if (orderTop.getStatus().equals("canceled")){
                    orderDetailTopHolder.linearOrderStatus.setVisibility(View.GONE);
                    orderDetailTopHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                    orderDetailTopHolder.txtOrderStatus.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    orderDetailTopHolder.txtOrderStatus.setPadding(0,0,0,0);
                }
                else if(orderTop.getStatus().equals("completed")){
                    orderDetailTopHolder.linearOrderStatus.setVisibility(View.GONE);
                    orderDetailTopHolder.imgCheck.setVisibility(View.VISIBLE);
                    orderDetailTopHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.custome_blue));
                    orderDetailTopHolder.txtOrderStatus.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    orderDetailTopHolder.txtOrderStatus.setPadding(0,0,0,0);
                }
                else{

                    if (!orderTop.getPayment_method().equals("cod") &&  orderTop.getPayment_status().equals("waiting_for_payment")){
                        orderDetailTopHolder.txtOrderStatus.setText(orderTop.getPayment_name() + " Payment Pending" );
                        orderDetailTopHolder.txtOrderStatus.setPadding(0,0,0,0);
                        orderDetailTopHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                        orderDetailTopHolder.txtOrderStatus.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    }


                    orderDetailTopHolder.linearOrderStatus.setVisibility(View.VISIBLE);


                    if (orderTop.getStatus().equals("ordered")){
                        orderDetailTopHolder.orderplaceTitle.setTextColor(activity.getResources().getColor(R.color.colorSplashScreen));
                        orderDetailTopHolder.txtOrderPlaced.setText( String.format(resources.getString(R.string.order_date_text),orderTop.getOrdered_at()));
                        orderDetailTopHolder.linearOrderDelivery.setAlpha(0.3f);
                        orderDetailTopHolder.linearOrderConfirmed.setAlpha(0.3f);
                        orderDetailTopHolder.linearOrderReady.setAlpha(0.3f);
                    }
                    else  if (orderTop.getStatus().equals("confirmed") || orderTop.getStatus().equals("voucher_issued")){

                        orderDetailTopHolder.orderconfirmTitle.setTextColor(activity.getResources().getColor(R.color.colorSplashScreen));
                        orderDetailTopHolder.txtOrderConfrimed.setText(String.format(resources.getString(R.string.confrim_date_text), orderTop.getOrdered_at()));
                        orderDetailTopHolder.linearOrderDelivery.setAlpha(0.3f);
                        orderDetailTopHolder.linearOrderReady.setAlpha(0.3f);

                    }
                    else if (orderTop.getStatus().equals("ready_for_delivery")){
                        orderDetailTopHolder.orderreadyTitle.setTextColor(activity.getResources().getColor(R.color.colorSplashScreen));
                        orderDetailTopHolder.txtOrderReady.setText( String.format(resources.getString(R.string.ready_date_text),orderTop.getOrdered_at()));
                        orderDetailTopHolder.linearOrderDelivery.setAlpha(0.3f);
                    }
                    else if (orderTop.getStatus().equals("on_route")){
                        orderDetailTopHolder.orderdeliveyTitle.setTextColor(activity.getResources().getColor(R.color.colorSplashScreen));
                        orderDetailTopHolder.txtOrderDelivery.setText(String.format(resources.getString(R.string.deliver_date_text),orderTop.getDelivery_date()));
                    }




                    orderDetailTopHolder.linearOrderStatus.setVisibility(View.VISIBLE);
                    orderDetailTopHolder.txtOrderStatus.setPadding(20,20,20,20);
                    orderDetailTopHolder.txtOrderStatus.setText(orderTop.getStatus() );



                }

                break;

            }

            case VIEW_TYPE_PRODUCT_DETAIL :{
                final ProductDetailsHolder productDetailsHolder = (ProductDetailsHolder) parentHolder;
                final Product productDetail = (Product) universalList.get(position);

                boolean flashSaleCheckProudcut = false;
                int total_discountProudct = 0;

                productDetailsHolder.price.setTypeface(productDetailsHolder.price.getTypeface(), Typeface.BOLD);
                Log.e(TAG, "onBindViewHolder: **-------------" );
                productDetailsHolder.layout_first_time.setVisibility(View.GONE);
                if (prefs.getIntPreferences(SP_ORDER_COUNT) == 0 && prefs.getIntPreferences(SP_TOWNSHIP_ID)!=0) {

                    int checkFree = 0;

                    for (int i=0; i< delList.size(); i++){
                        if(delList.get(i).getId() == prefs.getIntPreferences(SP_TOWNSHIP_ID)) {
                            checkFree = delList.get(i).getFirst_time_free();

                        }
                    }

                    if (checkFree != 0){
                        productDetailsHolder.layout_first_time.setVisibility(View.VISIBLE);
                    }

                }

                if (productDetail.getFlashSalesArrayList().size() > 0 ){
                    if (productDetail.getFlashSalesArrayList().get(0).getAvailable_quantity() > productDetail.getFlashSalesArrayList().get(0).getReserved_quantity()){



                        productDetailsHolder.linearFlashSale.setVisibility(View.VISIBLE);
                        final Flash_Sales flash_sales = productDetail.getFlashSalesArrayList().get(0);

                        if (flash_sales.getReserved_quantity() > flash_sales.getAvailable_quantity())
                            flash_sales.setReserved_quantity(flash_sales.getAvailable_quantity());


                        int barTextCount2 = flash_sales.getAvailable_quantity() - flash_sales.getReserved_quantity();

                        if (barTextCount2 == 0)
                        {
                            productDetailsHolder.txtAvailable.setText("Sold Out");

                        }else{

                            productDetailsHolder.txtAvailable.setText(String.format(resources.getString(R.string.flash_sale_avail),flash_sales.getAvailable_quantity() + "" ))  ;

                        }
                        DateFormat df8 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                        Date futureDate8 = null;
                        try {
                            futureDate8 = df8.parse(flash_sales.getEnd_date());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                        }
                        CountDownTimer countDownTimeProduct ;
                        Date currentDate8 = new Date();
                        if (!currentDate8.after(futureDate8)) {

                            if (flash_sales.getReserved_quantity() < flash_sales.getAvailable_quantity())
                                flashSaleCheckProudcut = true;

                            final long[] diff = {
                                    futureDate8.getTime()
                                            - currentDate8.getTime()};



                            countDownTimeProduct = this.countDownMap.get(productDetailsHolder.txtDay.hashCode());
                            if (countDownTimeProduct == null){
                                countDownTimeProduct = new CountDownTimer(diff[0], 1000) {
                                    public void onTick(long millisUntilFinished) {

                                        Date currentDate = new Date();
                                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                                        // Please here set your event date//YYYY-MM-DD
                                        Date futureDate = null;
                                        try {
                                            futureDate = df.parse(flash_sales.getEnd_date());
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
                                            productDetailsHolder.linearLayout1.setVisibility(View.GONE);
                                        else
                                            productDetailsHolder.linearLayout1.setVisibility(View.VISIBLE);



                                        productDetailsHolder.txtSecond.setText(String.valueOf(String.format("%02d", seconds).charAt(0)));
                                        productDetailsHolder.txtSecondONe.setText(String.valueOf(String.format("%02d", seconds).charAt(1)));

                                        productDetailsHolder.txtDay.setText(String.valueOf(String.format("%02d", days).charAt(0)));
                                        productDetailsHolder.txtDayONe.setText(String.valueOf(String.format("%02d", days).charAt(1)));

                                        productDetailsHolder.txtMinute.setText(String.valueOf(String.format("%02d", minutes).charAt(0)));
                                        productDetailsHolder.txtMinuteONe.setText(String.valueOf(String.format("%02d", minutes).charAt(1)));

                                        productDetailsHolder.txtHour.setText(String.valueOf(String.format("%02d", hours).charAt(0)));
                                        productDetailsHolder.txtHourONe.setText(String.valueOf(String.format("%02d", hours).charAt(1)));


                                    }

                                    public void onFinish() {
                                        productDetailsHolder.txtDay.setText("0");
                                        productDetailsHolder.txtDayONe.setText("0");
                                        productDetailsHolder.txtHour.setText("0" );
                                        productDetailsHolder.txtHourONe.setText("0" );
                                        productDetailsHolder.txtSecond.setText("0" );
                                        productDetailsHolder.txtSecondONe.setText("0" );
                                        productDetailsHolder.txtMinute.setText("0" );
                                        productDetailsHolder.txtMinuteONe.setText("0" );

                                        productDetailsHolder.linearLayout1.setVisibility(View.VISIBLE);
                                    }
                                }.start();

                                countDownMap.put(productDetailsHolder.txtDay.hashCode(), countDownTimeProduct);
                            }

                        }
                        else{
                            productDetailsHolder.txtDay.setText("0");
                            productDetailsHolder.txtDayONe.setText("0");
                            productDetailsHolder.txtHour.setText("0" );
                            productDetailsHolder.txtHourONe.setText("0" );
                            productDetailsHolder.txtSecond.setText("0" );
                            productDetailsHolder.txtSecondONe.setText("0" );
                            productDetailsHolder.txtMinute.setText("0" );
                            productDetailsHolder.txtMinuteONe.setText("0" );
                            productDetailsHolder.linearLayout1.setVisibility(View.VISIBLE);
                        }


                    }
                    else{
                        productDetailsHolder.linearFlashSale.setVisibility(View.GONE);
                    }
                }else{
                    productDetailsHolder.linearFlashSale.setVisibility(View.GONE);
                }



                if(productDetailsHolder.title.getId() != productDetail.getId()) {
                    productDetailsHolder.title.setId(productDetail.getId());
                    productDetailsHolder.title.setText(productDetail.getTitle());
                    productDetailsHolder.txtCategory.setText(productDetail.getCategory_name());

                    final int original_price_product = productDetail.getPrice();




                    if(productDetail.getChannel() != null && productDetail.getChannel().trim().length() > 0){

                        if (!productDetail.getChannel().equals("all_channels")){
                            productDetailsHolder.txtAllMark.setText(resources.getString(R.string.pre_order));
                            productDetailsHolder.txtAllMark.setTypeface(productDetailsHolder.txtAllMark.getTypeface(), Typeface.BOLD);
                            productDetailsHolder.txtPreOrderMark.setVisibility(View.VISIBLE);
                            productDetailsHolder.txtAllMark.setVisibility(View.VISIBLE);
                            productDetailsHolder.txtPreOrderMark.setText(productDetail.getChannel_message());
                            productDetailsHolder.txtAllMark.setBackgroundColor(activity.getResources().getColor(R.color.coloredInactive));
                            productDetailsHolder.txtAllMark.setTextColor(activity.getResources().getColor(R.color.white));

                        }

                        else if(productDetail.getRecommended() != null && productDetail.getRecommended().trim().length() > 0){
                            productDetailsHolder.txtAllMark.setText(productDetail.getRecommended());
                            productDetailsHolder.txtAllMark.setTypeface(productDetailsHolder.txtAllMark.getTypeface(), Typeface.BOLD);
                            productDetailsHolder.txtPreOrderMark.setVisibility(View.VISIBLE);
                            productDetailsHolder.txtPreOrderMark.setText(resources.getString(R.string.online_order_only));
                            productDetailsHolder.txtAllMark.setVisibility(View.VISIBLE);
                            productDetailsHolder.txtAllMark.setBackgroundColor(activity.getResources().getColor(R.color.coloredInactive));
                            productDetailsHolder.txtAllMark.setTextColor(activity.getResources().getColor(R.color.white));
                        }
                        else{
                            // productDetailsHolder.txtAllMark.setVisibility(View.GONE);
                            productDetailsHolder.txtAllMark.setText("");
                            productDetailsHolder.txtAllMark.setBackgroundColor(activity.getResources().getColor(R.color.transparent));
                            productDetailsHolder.txtPreOrderMark.setVisibility(View.GONE);
                        }
                    }
                    else{
                        productDetailsHolder.txtAllMark.setText("");
                        productDetailsHolder.txtAllMark.setBackground(activity.getResources().getDrawable(R.drawable.background_transparent));
                        productDetailsHolder.txtPreOrderMark.setVisibility(View.GONE);
                    }




                    if (flashSaleCheckProudcut) {
                        productDetailsHolder.promotion_title.setVisibility(View.VISIBLE);
                        productDetailsHolder.promotion_title.setText(resources.getString(R.string.promotion));
                        productDetailsHolder.discount_layout.setVisibility(View.VISIBLE);




                        Flash_Sales flash_salesObj = productDetail.getFlashSalesArrayList().get(0);
                        int flashPercent = 0;
                        flashPercent =  ( original_price_product * flash_salesObj.getDiscount()) / 100 ;


                        productDetailsHolder.txtDiscount.setVisibility(View.VISIBLE);
                        productDetailsHolder.txtDiscount.setText(  "-" + flash_salesObj.getDiscount() + " % ");

                        String str = "";
                        str += resources.getString(R.string.flash_sale_discount)
                                + " " + flash_salesObj.getDiscount() + "%  ( - "
                                + formatter.format(flashPercent) + " "
                                + resources.getString(R.string.currency)+" )";


                        View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                        CustomTextView txt1 = addView1.findViewById(R.id.txt);
                        txt1.setText(str);
                        productDetailsHolder.discount_layout.addView(addView1);




                        str = "";
                        if (productDetail.getPoint_usage() == 0){
                            //str +=   String.format(resources.getString(R.string.point_product_can) , String.valueOf( prefs.getIntPreferences(SP_POINT_PERCENTAGE) ));
                            str +=   resources.getString(R.string.point_product_can);
                        }else{
                            str +=   resources.getString(R.string.point_product_cannot) ;
                        }



                        View addView = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                        CustomTextView txt = addView.findViewById(R.id.txt);
                        txt.setText(str);
                        productDetailsHolder.discount_layout.addView(addView);




                        if(flashPercent == 0){
                            productDetailsHolder.price.setText(formatter.format((original_price_product - flashPercent))+" "+resources.getString(R.string.currency));
                            productDetailsHolder.price_strike.setVisibility(View.GONE);
                        }else{
                            productDetailsHolder.price_strike.setVisibility(View.VISIBLE);
                            productDetailsHolder.price.setText(formatter.format((original_price_product - flashPercent))+" "+resources.getString(R.string.currency));
                            productDetailsHolder.price_strike.setText( formatter.format(original_price_product) +" "+ resources.getString(R.string.currency));
                            productDetailsHolder.price_strike.setPaintFlags(productDetailsHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }

                        total_discountProudct = flashPercent;



                    }else{



                        if(productDetail.getDiscounts().size() > 0) {
                            productDetailsHolder.promotion_title.setVisibility(View.VISIBLE);
                            productDetailsHolder.promotion_title.setText(resources.getString(R.string.promotion));
                            productDetailsHolder.discount_layout.setVisibility(View.VISIBLE);

                            for (int k = 0; k < productDetail.getDiscounts().size(); k++) {

                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                parms.gravity = Gravity.CENTER;
                                parms.setMargins(5, 5, 5, 5);


                                LinearLayout eachLayout = new LinearLayout(activity);
                                eachLayout.setPadding(15, 15, 15, 15);
                                eachLayout.setLayoutParams(parms);
                                eachLayout.setOrientation(LinearLayout.VERTICAL);
                                Discount dis = (Discount) productDetail.getDiscounts().get(k);
                                String str = dis.getDiscountType();


                                if (dis.getMember_only() == 1) {
                                    if (prefs.getIntPreferences(SP_VIP_ID) == 1) {
                                        switch (str) {
                                            case DISCOUNT_PERCENTAGE:
                                                if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {

                                                    productDetailsHolder.txtDiscount.setVisibility(View.VISIBLE);
                                                    productDetailsHolder.txtDiscount.setText(  "-" + dis.getPercentage()+ " % ");


                                                    total_discountProudct = (productDetail.getPrice() * dis.getPercentage()) / 100;

                                                    View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                                                    CustomTextView txt1 = addView1.findViewById(R.id.txt);
                                                    txt1.setText(dis.getCampaign_info()+"\n"+ "  ( - "
                                                            + formatter.format((productDetail.getPrice() * dis.getPercentage()) / 100) + " "
                                                            + resources.getString(R.string.currency) + " )"+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                                    productDetailsHolder.discount_layout.addView(addView1);



                                                } else if (dis.getCount_type().equals(DISCOUNT_TYPE_AMOUNT)) {



                                                    View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                                                    CustomTextView txt1 = addView1.findViewById(R.id.txt);
                                                    txt1.setText(dis.getCampaign_info()+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                                    productDetailsHolder.discount_layout.addView(addView1);


                                                }
                                                break;
                                            case DISCOUNT_GIFT:


                                                View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                                                if (dis.getGift_img_url().trim().length() > 0){
                                                    LinearLayout linearLayout = addView1.findViewById(R.id.linearLayout);
                                                    linearLayout.setVisibility(View.GONE);
                                                    LinearLayout linearLayoutPhoto = addView1.findViewById(R.id.linearLayoutPhoto);
                                                    linearLayoutPhoto.setVisibility(View.VISIBLE);
                                                    NetworkImageView img = addView1.findViewById(R.id.img);
                                                    CustomTextView txtPhoto = addView1.findViewById(R.id.txtPhoto);
                                                    txtPhoto.setText(dis.getCampaign_info()+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                                    img.setImageUrl(dis.getGift_img_url(), imageLoader);



                                                }else{
                                                    CustomTextView txt1 = addView1.findViewById(R.id.txt);
                                                    txt1.setText(dis.getCampaign_info()+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                                }

                                                productDetailsHolder.discount_layout.addView(addView1);

                                            default:
                                                break;
                                        }
                                    }
                                } else {

                                    switch (str) {
                                        case DISCOUNT_PERCENTAGE:
                                            if (dis.getCount_type().equals(DISCOUNT_TYPE_QUANTITY)) {

                                                total_discountProudct = (productDetail.getPrice() * dis.getPercentage()) / 100;
                                                View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                                                CustomTextView txt1 = addView1.findViewById(R.id.txt);
                                                txt1.setText(dis.getCampaign_info()+"\n" + "  ( - "
                                                        + formatter.format((productDetail.getPrice() * dis.getPercentage()) / 100) + " "
                                                        + resources.getString(R.string.currency) + " )"+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                                productDetailsHolder.discount_layout.addView(addView1);

                                                productDetailsHolder.txtDiscount.setVisibility(View.VISIBLE);
                                                productDetailsHolder.txtDiscount.setText(  "-" + dis.getPercentage()+ " % ");


                                            } else if (dis.getCount_type().equals(DISCOUNT_TYPE_AMOUNT)) {

                                                View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                                                CustomTextView txt1 = addView1.findViewById(R.id.txt);
                                                txt1.setText(dis.getCampaign_info()+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                                productDetailsHolder.discount_layout.addView(addView1);
                                            }
                                            break;

                                        case DISCOUNT_GIFT:
                                            View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                                            if (dis.getGift_img_url().trim().length() > 0){
                                                LinearLayout linearLayout = addView1.findViewById(R.id.linearLayout);
                                                linearLayout.setVisibility(View.GONE);
                                                LinearLayout linearLayoutPhoto = addView1.findViewById(R.id.linearLayoutPhoto);
                                                linearLayoutPhoto.setVisibility(View.VISIBLE);
                                                NetworkImageView img = addView1.findViewById(R.id.img);
                                                CustomTextView txtPhoto = addView1.findViewById(R.id.txtPhoto);
                                                txtPhoto.setText(dis.getCampaign_info()+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                                img.setImageUrl(dis.getGift_img_url(), imageLoader);

                                                //https://res.cloudinary.com/tech-myanmar/image/upload/q_auto/jmoxtvzviipwfl3pojsc.jpg

                                            }else{
                                                CustomTextView txt1 = addView1.findViewById(R.id.txt);
                                                txt1.setText(dis.getCampaign_info()+"\n" +dis.getStart_at()+" - "+dis.getEnd_at());
                                            }

                                            productDetailsHolder.discount_layout.addView(addView1);
                                        default:
                                            break;
                                    }
                                }
                            }


                        }

                        if(productDetail.getMember_discount() != 0 && prefs.getIntPreferences(SP_VIP_ID) != 0 ) {

                            productDetailsHolder.promotion_title.setVisibility(View.VISIBLE);
                            productDetailsHolder.promotion_title.setText(resources.getString(R.string.promotion));

                            productDetailsHolder.discount_layout.setVisibility(View.VISIBLE);


                            String str = resources.getString(R.string.member_discount)
                                    + " " + prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) + "%  ( - "
                                    + formatter.format(((productDetail.getPrice() - total_discountProudct)* prefs.getIntPreferences(SP_MEMBER_PERCENTAGE)) / 100) + " "
                                    + resources.getString(R.string.currency)+" )" ;

                            View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                            CustomTextView txt1 = addView1.findViewById(R.id.txt);
                            txt1.setText(str);
                            productDetailsHolder.discount_layout.addView(addView1);


                            String str1 = "";
                            if (productDetail.getPoint_usage() == 0){
                                //str1 += String.format(resources.getString(R.string.point_product_can) , String.valueOf( prefs.getIntPreferences(SP_POINT_PERCENTAGE) ));
                                str1 +=   resources.getString(R.string.point_product_can);


                            }else{
                                str1 +=   resources.getString(R.string.point_product_cannot) ;
                            }



                            View addView = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                            CustomTextView txt = addView.findViewById(R.id.txt);
                            txt.setText(str1);
                            productDetailsHolder.discount_layout.addView(addView);




                            total_discountProudct += ((productDetail.getPrice() - total_discountProudct)* prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) / 100);
                        }else{
                            productDetailsHolder.promotion_title.setVisibility(View.VISIBLE);
                            productDetailsHolder.promotion_title.setText(resources.getString(R.string.promotion));

                            productDetailsHolder.discount_layout.setVisibility(View.VISIBLE);

                            String str = "";

                            if (productDetail.getPoint_usage() == 0){
                                //str +=   String.format(resources.getString(R.string.point_product_can) , String.valueOf( prefs.getIntPreferences(SP_POINT_PERCENTAGE) ));
                                str +=   resources.getString(R.string.point_product_can);
                            }else{
                                str +=   resources.getString(R.string.point_product_cannot) ;
                            }



                            View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                            CustomTextView txt1 = addView1.findViewById(R.id.txt);
                            txt1.setText(str);
                            productDetailsHolder.discount_layout.addView(addView1);


                            View addView12 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                            CustomTextView txt12 = addView12.findViewById(R.id.txt);
                            txt12.setText(resources.getString(R.string.memeber_product_cannot));
                            productDetailsHolder.discount_layout.addView(addView12);
                        }

                        if(total_discountProudct == 0){
                            productDetailsHolder.price.setText(formatter.format((original_price_product - total_discountProudct))+" "+resources.getString(R.string.currency));
                            productDetailsHolder.price_strike.setVisibility(View.GONE);
                        }else{
                            productDetailsHolder.price_strike.setVisibility(View.VISIBLE);
                            productDetailsHolder.price.setText(formatter.format((original_price_product - total_discountProudct))+" "+resources.getString(R.string.currency));
                            productDetailsHolder.price_strike.setText( formatter.format(original_price_product) +" "+ resources.getString(R.string.currency));
                            productDetailsHolder.price_strike.setPaintFlags(productDetailsHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }



                    }

                    int checkProductWarning2 = 0;
                    if (productDetail.getChannel().equals("all_channels")  &&  productDetail.getRecommended().trim().length() == 0 ){

                        checkProductWarning2 = 1;
                    }
                    productDetailsHolder.txtProductWarning.setTypeface(productDetailsHolder.txtProductWarning.getTypeface(), Typeface.BOLD);

                    if (checkProductWarning2 == 1){
                        // productDetailsHolder.linearProductWarning.setVisibility(View.GONE);
                        productDetailsHolder.img_car.setImageDrawable(activity.getResources().getDrawable(R.drawable.car_one));
                        productDetailsHolder.txtProductWarning.setText(resources.getString(R.string.delivery_take_time));


                    }else{
                        //productDetailsHolder.linearProductWarning.setVisibility(View.VISIBLE);
                        productDetailsHolder.img_car.setImageDrawable(activity.getResources().getDrawable(R.drawable.car_option));
                        productDetailsHolder.txtProductWarning.setText(resources.getString(R.string.delivery_take_time_option));

                    }

                    if(databaseAdapter.checkProductLiked(productDetail.getId())){
                        productDetailsHolder.imgWishList.setImageResource(R.drawable.wishlist_clicked);
                    }else {
                        productDetailsHolder.imgWishList.setImageResource(R.drawable.ic_wishlist_black);
                    }


                    productDetailsHolder.imgShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("product_id",String.valueOf(productDetail.getId()));
                                FlurryAgent.logEvent("Click Product Share Icon", mix);
                            } catch (Exception e) {
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("item_id", productDetail.getId()+"");
                            bundle.putString("content_type","product");
                            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, productDetail.getProductUrl() );
                            activity.startActivity(Intent.createChooser(shareIntent, "Share link using"));
                        }
                    });


                    if (productDetail.getYoutubeId() != null && !productDetail.getYoutubeId().equals("")) {
                        productDetailsHolder.frameLayout.setVisibility(View.VISIBLE);
                        productDetailsHolder.imgYouTube.getLayoutParams().height = (display.getWidth() * productDetail.getYoutubeImageDimen()) / 100;
                        productDetailsHolder.imgYouTube.setImageUrl(productDetail.getYoutubeImageUrl(), imageLoader);
                        productDetailsHolder.imgYouTube.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               /* Intent intent = new Intent(activity, Youtube.class);
                                intent.putExtra("youtubeID", productDetail.getYoutubeId());
                                activity.startActivity(intent);*/
                            }
                        });
                    }


                    final List<Images> imagesDetail = productDetail.getImages();

                    if (imagesDetail.size() > 1){
                        productDetailsHolder.txtImgCount.setText(imagesDetail.size() + "");
                        productDetailsHolder.linearImage.setVisibility(View.VISIBLE);
                        productDetailsHolder.slider.startAutoCycle();
                    }else{
                        productDetailsHolder.slider.stopAutoCycle();
                        productDetailsHolder.linearImage.setVisibility(View.GONE);
                    }

                    productDetailsHolder.slider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

                    for ( int i = 0; i < imagesDetail.size(); i++) {
                        final int pos = i;
                        final Images image = imagesDetail.get(i);
                        final CustomTextSliderView textSliderView = new CustomTextSliderView(activity);

                        int height = (image.getDimen() * activity.getWindowManager().getDefaultDisplay().getWidth()) / 100;
                        productDetailsHolder.slider.getLayoutParams().height = height;
                        productDetailsHolder.slider.getLayoutParams().width = activity.getWindowManager().getDefaultDisplay().getWidth();

                        productDetailsHolder.relative_slider.setVisibility(View.VISIBLE);
                        textSliderView.description(image.getName());
                        textSliderView.image(image.getUrl());
                        textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);

                        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {

                                Intent intent = new Intent(activity, AndroidLoadImageFromURLActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("list_image", (ArrayList<? extends Parcelable>) imagesDetail);
                                bundle.putString("url", image.getUrl().toString());
                                bundle.putInt("index", pos);
                                intent.putExtras(bundle);
                                activity.startActivity(intent);
                            }
                        });


                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", "Product");
                        productDetailsHolder.slider.addSlider(textSliderView);

                    }


                    productDetailsHolder.imgWishList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Bundle fbundle = new Bundle();
                            fbundle.putString("item_id", productDetail.getId()+"");
                            fbundle.putString("item_category", "porduct");
                            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, fbundle);

                            if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                                    prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){
                                sendProductDetailLike(productDetail.getId(), productDetailsHolder);
                            }else{
                                Intent intent   = new Intent(activity, ActivityLogin.class);
                                activity.startActivity(intent);
                            }


                        }
                    });


                }


                productDetailsHolder.imgChat.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("product_id", String.valueOf(productDetail.getId()));
                            mix.put("brand_id",String.valueOf(productDetail.getBrand_id()));
                            FlurryAgent.logEvent("Click Message Icon", mix);

                        } catch (Exception e) {}

                        if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                            final Dialog dialog = new Dialog(activity);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_feedback);
                            dialog.setCanceledOnTouchOutside(true);

                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                            window.setAttributes(wlp);

                            CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                            CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                            final CustomEditText editText =   (CustomEditText) dialog.findViewById(R.id.edittext);
                            title.setText(resources.getString(R.string.send_message));
                            title.setTextSize(14);
                            feedback.setText(resources.getString(R.string.chat_message_button));
                            feedback.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(prefs.isNetworkAvailable()) {
                                        if (editText.getText().toString().trim().length() > 0) {
                                            try {

                                                Map<String, String> mix = new HashMap<String, String>();
                                                mix.put("product_id",String.valueOf(productDetail.getId()));
                                                mix.put("brand_id",String.valueOf(productDetail.getBrand_id()));
                                                FlurryAgent.logEvent("Send Product Message", mix);

                                            } catch (Exception e) {}
/*
                                            FreshchatMessage message1  = new FreshchatMessage();
                                            message1.setTag(String.valueOf(productDetail.getId()));
                                            message1.setMessage(constantChatProduct+productDetail.getId()+"<br>"+
                                                    "<b>"+productDetail.getTitle()+"</b>"+"<br>"+
                                                    "<i>"+productDetail.getBrand_name()+"</i>"+"<br>"+
                                                    "<i>"+productDetail.getCategory_name()+"</i>"+"<br>"+
                                                    editText.getText().toString());
                                            Freshchat.sendMessage(activity, message1);

                                            dialog.dismiss();
                                            Freshchat.showConversations(activity);*/

                                        }else{

                                            ToastHelper.showToast(activity, resources.getString(R.string.chat_mesage_toast));

                                        }
                                    }else {

                                        ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));

                                    }
                                }

                            });
                            dialog.show();
                        }else{
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }


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

            case VIEW_TYPE_STEP_TWO_PAYMENT_ITEM :{
                ItemPaymentHolder itemPaymentHolder = (ItemPaymentHolder) parentHolder;
                PaymentObject paymentItemObj = (PaymentObject) universalList.get(position);
                itemPaymentHolder.img.setImageUrl(paymentItemObj.getImg_url(), imageLoader);

                itemPaymentHolder.img.getLayoutParams().height = (int) (activity.getWindowManager().getDefaultDisplay().getWidth() / 4);
                itemPaymentHolder.img.getLayoutParams().width = (int) (activity.getWindowManager().getDefaultDisplay().getWidth() / 4);

                itemPaymentHolder.txt.setText(paymentItemObj.getName());
                itemPaymentHolder.txtPercent.setText(paymentItemObj.getCommission() + " % ");
                itemPaymentHolder.linearMain.getLayoutParams().width = (int) (activity.getWindowManager().getDefaultDisplay().getWidth() /3.5);

                if (paymentItemObj.isSelected()){
                    itemPaymentHolder.imgCheck.setVisibility(View.VISIBLE);
                    itemPaymentHolder.img.setBorderColor(activity.getResources().getColor(R.color.custome_blue));
                    itemPaymentHolder.txt.setTextColor(activity.getResources().getColor(R.color.custome_blue));
                    itemPaymentHolder.txtPercent.setTextColor(activity.getResources().getColor(R.color.custome_blue));
                }else{
                    itemPaymentHolder.imgCheck.setVisibility(View.GONE);
                    itemPaymentHolder.img.setBorderColor(activity.getResources().getColor(R.color.background));
                    itemPaymentHolder.txt.setTextColor(activity.getResources().getColor(R.color.text_grey));
                    itemPaymentHolder.txtPercent.setTextColor(activity.getResources().getColor(R.color.text_grey));

                }

                itemPaymentHolder.linearMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int amtCheckPrice = 0;

                        if(prefs.getIntPreferences(SP_CUSTOMER_TOTAL) >= prefs.getIntPreferences(DELIVERY_FREE_AMOUNT)){
                            amtCheckPrice = prefs.getIntPreferences(SP_CUSTOMER_TOTAL)- prefs.getIntPreferences(CHECK_USE_POINT);
                        }
                        else{

                            amtCheckPrice = prefs.getIntPreferences(SP_CUSTOMER_TOTAL) +prefs.getIntPreferences(DELIVERY_PRICE)- prefs.getIntPreferences(CHECK_USE_POINT);
                        }



                        if (amtCheckPrice < paymentItemObj.getMin()){
                            //showBottomDialogWarning(String msg, int amount, int option, String payName){
                            showBottomDialogWarning(paymentItemObj.getMin(), 0, paymentItemObj.getName());
                        }
                        else if (amtCheckPrice > paymentItemObj.getMax()){
                            showBottomDialogWarning(paymentItemObj.getMax(), 1, paymentItemObj.getName());
                        }
                        else{

                            paymentItemObj.setSelected(true);

                            for (int i=0; i < universalList.size(); i++){


                                if (universalList.get(i).getPostType().equals(STEP_TWO_PAYMENT_ITEM))
                                {

                                    PaymentObject fakePayment =(PaymentObject) universalList.get(i);
                                    if (paymentItemObj.getId() == fakePayment.getId()) {
///

                                        fakePayment.setSelected(true);
                                        prefs.saveIntPerferences(TEMP_CHOOSE_PAYMENT_ID, paymentItemObj.getId());
                                        prefs.saveFloatPerferences(TEMP_COMMISSION_RATE,  paymentItemObj.getCommission());
                                        prefs.saveStringPreferences(TEMP_COMMISSION_IMG,  paymentItemObj.getImg_url());
                                        prefs.saveStringPreferences(TEMP_COMMISSION_NAME,  paymentItemObj.getName());
                                        prefs.saveStringPreferences(TEMP_CHOOSE_PAYMENT_TAG,  paymentItemObj.getTag());
                                        prefs.saveStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NAME,  paymentItemObj.getAcc_name());
                                        prefs.saveStringPreferences(TEMP_CHOOSE_PAYMENT_ACC_NUM,  paymentItemObj.getAcc_num());



                                    }
                                    else {
                                        fakePayment.setSelected(false);
                                    }
                                    universalList.remove(i);
                                    universalList.add(i, fakePayment);


                                }
                            }

                            ActivityStepTwoCart testActivity = (ActivityStepTwoCart) activity;
                            testActivity.clickNoify();

                        }

                    }
                });



                break;
            }





            case VIEW_TYPE_SHOP_LOCATION:{

                ShopHolder shopHolder = (ShopHolder) parentHolder;
                final ShopLocation shopLocation = (ShopLocation) universalList.get(position);

                if (shopLocation.getAddress() != null && shopLocation.getAddress().trim().length() > 0){
                    shopHolder.txtShopTitle.setText(shopLocation.getAddress());
                }

                if (shopLocation.getPhone() != null && shopLocation.getPhone().trim().length() > 0){
                    shopHolder.txtPhone.setText(shopLocation.getPhone());
                }

                shopHolder.txtShowAddress.setText(resources.getString(R.string.show_location_map));
                shopHolder.txtShowAddress.setPaintFlags(shopHolder.txtShowAddress.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                shopHolder.txtShowAddress.setTypeface(shopHolder.txtShowAddress.getTypeface(), Typeface.BOLD);

                final List<Images> imageLocations = shopLocation.getPhotos();
                if (imageLocations.size() > 0) {
                    shopHolder.txtHidden.setVisibility(View.VISIBLE);
                    shopHolder.txtTownship.setVisibility(View.VISIBLE);
                    if (shopLocation.getTownship_name() != null && shopLocation.getTownship_name().trim().length() > 0){
                        shopHolder.txtTownship.setText(shopLocation.getTownship_name());
                    }
                }
                else {
                    shopHolder.txtHidden.setVisibility(View.GONE);
                    shopHolder.txtTownship.setVisibility(View.GONE);

                }

                for ( int i = 0; i < imageLocations.size(); i++) {
                    final int pos = i;
                    final Images image = imageLocations.get(i);
                    final CustomTextSliderView textSliderView = new CustomTextSliderView(activity);

                    int height = (image.getDimen() * activity.getWindowManager().getDefaultDisplay().getWidth()) / 100;
                    shopHolder.mDemoSlider.getLayoutParams().height = height;
                    shopHolder.mDemoSlider.getLayoutParams().width = activity.getWindowManager().getDefaultDisplay().getWidth();

                    shopHolder.relativeSlider.setVisibility(View.VISIBLE);
                    textSliderView.description(image.getName());
                    textSliderView.image(image.getUrl());
                    textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Intent intent = new Intent(activity, AndroidLoadImageFromURLActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelableArrayList("list_image", (ArrayList<? extends Parcelable>) imageLocations);
                                    bundle.putString("url", image.getUrl().toString());
                                    bundle.putInt("index", pos);
                                    intent.putExtras(bundle);
                                    activity.startActivity(intent);
                                }
                            });
                        }
                    });


                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", "Product");
                    shopHolder.mDemoSlider.addSlider(textSliderView);

                }

                shopHolder.linearShowLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%s,%s", shopLocation.getLat(),shopLocation.getLng());
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        activity.startActivity(intent);
                    }
                });

                shopHolder.linearCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String[] strList = shopLocation.getPhone().split(",");

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
                            String str = shopLocation.getPhone().replace("-", "").trim();
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + str));
                            activity.startActivity(callIntent);
                        }

                    }
                });


                break;
            }

            case VIEW_TYPE_POINT_INFO_TEXT :{

                PointInfoHolder pointInfoHolder = (PointInfoHolder) parentHolder;

                pointInfoHolder.point_text.setText(String.format(resources.getString(R.string.point_text), prefs.getIntPreferences(SP_USER_POINT) + ""));
                pointInfoHolder.total_point.setText(formatter.format(prefs.getIntPreferences(SP_USER_POINT)));
                pointInfoHolder.point_desc.setText(String.format(resources.getString(R.string.point_desc), prefs.getIntPreferences(SP_POINT_PERCENTAGE) + ""));

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





            case VIEW_TYPE_QA_PRODUCT_ITEM :{
                final QAHolder qaHolder1 = (QAHolder) parentHolder;
                final QAObject qaObject1 = (QAObject) universalList.get(position);
                if(qaObject1.getQuestion() != null && qaObject1.getQuestion().trim().length() > 0){
                    qaHolder1.txtQuestion.setText(qaObject1.getQuestion());

                }

                if(qaObject1.getAnswer() != null && qaObject1.getAnswer().trim().length() > 0){
                    qaHolder1.txtAnswer.setText(qaObject1.getAnswer());
                    qaHolder1.linearAnswer.setVisibility(View.VISIBLE);

                }else{
                    qaHolder1.linearAnswer.setVisibility(View.GONE);
                }

                qaHolder1.read.setVisibility(View.GONE);

                qaHolder1.txtTwelveDp.setVisibility(View.GONE);
                qaHolder1.txtOneDp.setVisibility(View.VISIBLE);



                break;
            }


            case VIEW_TYPE_QA_ITEM :{
                final QAHolder qaHolder = (QAHolder) parentHolder;
                final QAObject qaObject = (QAObject) universalList.get(position);
                if(qaObject.getQuestion() != null && qaObject.getQuestion().trim().length() > 0){
                    qaHolder.txtQuestion.setText(qaObject.getQuestion());

                }

                if(qaObject.getAnswer() != null && qaObject.getAnswer().trim().length() > 0){
                    qaHolder.txtAnswer.setText(qaObject.getAnswer());
                    qaHolder.linearAnswer.setVisibility(View.VISIBLE);

                }else{
                    qaHolder.linearAnswer.setVisibility(View.GONE);
                }

                qaHolder.read.setText(resources.getString(R.string.view_post));

                qaHolder.txtTwelveDp.setVisibility(View.VISIBLE);
                qaHolder.txtOneDp.setVisibility(View.GONE);

                qaHolder.linearMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qaHolder.showNoti.setVisibility(View.GONE);
                        getProduct(String.valueOf(qaObject.getProduct_id()));
                    }
                });



                break;
            }
            case VIEW_TYPE_ORDER_DELIVERY_STATUS :{
                final DeliveryStatusHolder statusHolder = (DeliveryStatusHolder) parentHolder;
                statusHolder.txtBasket.setText(resources.getString(R.string.go_to_basket));
                statusHolder.txtPending.setText(resources.getString(R.string.current_order));
                statusHolder.txtComplete.setText(resources.getString(R.string.previous_order));

                statusHolder.txtPending.setTypeface(statusHolder.txtPending.getTypeface(), Typeface.BOLD);
                statusHolder.txtComplete.setTypeface(statusHolder.txtComplete.getTypeface(), Typeface.BOLD);
                statusHolder.txtBasket.setTypeface(statusHolder.txtBasket.getTypeface(), Typeface.BOLD);

                statusHolder.linearBasket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "profile_shopping cart");
                            FlurryAgent.logEvent("Click Shopping Cart", mix);
                        } catch (Exception e) {
                        }
                        prefs.saveBooleanPreference(ALREADY_USE_POINT, false);
                        Intent intent1 = new Intent(activity, ShoppingCartActivity.class);
                        activity.startActivity(intent1);

                    }
                });

                statusHolder.linearPending.setOnClickListener(new View.OnClickListener() {
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

                statusHolder.linearComplete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "profile_complete_order");
                            FlurryAgent.logEvent("Click Completed Orders", mix);
                        } catch (Exception e) {
                        }

                        Intent intent1 = new Intent(activity, OlderedActivity.class);
                        intent1.putExtra("type", "completed");
                        intent1.putExtra("title", resources.getString(R.string.previous_order));
                        activity.startActivity(intent1);

                    }
                });


                break;
            }


            case VIEW_TYPE_ORDER_HISTORY :{
                final OrderHistoryHolder historyHolder = (OrderHistoryHolder) parentHolder;
                final Order histroyOrder = (Order) universalList.get(position);
                historyHolder.txtOrderTime.setText(histroyOrder.getOrdered_at() );
                historyHolder.txtItem.setText(histroyOrder.getItem_count() + " items" );

                if (histroyOrder.getItem_count() == 1)
                    historyHolder.txtItem.setText(histroyOrder.getItem_count() + " item" );

                historyHolder.txtOrderPrice.setText( formatter.format(histroyOrder.getTotal_price() ) +" "+resources.getString(R.string.currency));
                historyHolder.txtOrderId.setText("Order Id #" + histroyOrder.getVoucher_number());

                historyHolder.txtOrderPrice.setTypeface(historyHolder.txtOrderPrice.getTypeface(), Typeface.BOLD);
                historyHolder.txtOrderStatus.setTypeface(historyHolder.txtOrderId.getTypeface(), Typeface.BOLD);
                historyHolder.txtOrderId.setTypeface(historyHolder.txtOrderId.getTypeface(), Typeface.BOLD);





                if (histroyOrder.getStatus().equals("completed") ){
                    historyHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.custome_blue));
                    historyHolder.txtOrderStatus.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    historyHolder.imgCheck.setVisibility(View.VISIBLE);

                    if (histroyOrder.getFeedback_point() == 0){
                        historyHolder.txtRatingTitle.setVisibility(View.VISIBLE);
                        //historyHolder.txtRatingTitle.setText(resources.getString(R.string.order_feedback) + " (+" + prefs.getIntPreferences(SP_FEEDBACK_POINT)  +" " + resources.getString(R.string.point) + ")");
                        historyHolder.txtRatingTitle.setText(resources.getString(R.string.order_feedback) );


                    }else{

                        historyHolder.txtRatingTitle.setVisibility(View.GONE);
                        historyHolder.txtRatingTitle.setText(resources.getString(R.string.your_feedback));

                    }

                    historyHolder.txtOrderStatus.setText("Completed" );
                }else if (histroyOrder.getStatus().equals("canceled") ){
                    historyHolder.imgCheck.setVisibility(View.GONE);
                    historyHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                    historyHolder.txtOrderStatus.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    historyHolder.txtRatingTitle.setVisibility(View.GONE);
                    historyHolder.txtOrderStatus.setText("Canceled" );

                }
                else{
                    historyHolder.txtOrderPrice.setText( formatter.format(histroyOrder.getTotal_price()  ) +" "+resources.getString(R.string.currency));

                    historyHolder.imgCheck.setVisibility(View.GONE);
                    historyHolder.txtRatingTitle.setVisibility(View.GONE);
                    historyHolder.txtOrderStatus.setText(histroyOrder.getStatus() );
                    historyHolder.txtOrderStatus.setPadding(30,15,30,15);
                    historyHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.text_custom_yellow));
                    historyHolder.txtOrderStatus.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_yellow_light));


                    /*if (histroyOrder.getPayment_method().equals("cod") ){
                        historyHolder.txtOrderStatus.setText(histroyOrder.getStatus() );
                        historyHolder.txtOrderStatus.setPadding(30,15,30,15);
                        historyHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.text_custom_yellow));
                        historyHolder.txtOrderStatus.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_yellow_light));

                    }
                    else {
                        if (histroyOrder.getPayment_status().equals("waiting_for_payment")){
                            historyHolder.txtOrderStatus.setText(histroyOrder.getPayment_name() + " Payment Pending" );
                            historyHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                            historyHolder.txtOrderStatus.setBackgroundColor(activity.getResources().getColor(R.color.white));

                        }
                        else{
                            historyHolder.txtOrderStatus.setText(histroyOrder.getStatus() );
                            historyHolder.txtOrderStatus.setPadding(30,15,30,15);
                            historyHolder.txtOrderStatus.setTextColor(activity.getResources().getColor(R.color.text_custom_yellow));
                            historyHolder.txtOrderStatus.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_yellow_light));

                        }
                    }*/




                }



                historyHolder.linearView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("order_id",String.valueOf(histroyOrder.getOrder_id()));
                            FlurryAgent.logEvent("Click Past Order", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, OrderDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("order", histroyOrder);

                        intent.putExtras(bundle);
                        activity.startActivity(intent);
                    }
                });




                historyHolder.txtRatingTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (histroyOrder.getFeedback_point() == 0){
                            feedBackDialog(histroyOrder, position, true);
                        }

                    }
                });



                break;
            }


            case VIEW_TYPE_ORDER_DETAILS_FOOTER:{

                OrderDetailsFooter detailsHolder = (OrderDetailsFooter) parentHolder;
                OrderDetailsObj orderDetailsObj = (OrderDetailsObj) universalList.get(position);

                Order orderDetails = orderDetailsObj.getOrder();




                detailsHolder.delivery_price_text.setText( resources.getString(R.string.cart_detail_delivery_fees));

                detailsHolder.delivery_price.setText(  formatter.format(orderDetails.getDelivery_fee())+" "+resources.getString(R.string.currency) );
                detailsHolder.total_price_text.setText(resources.getString(R.string.total_price));

                /*if (orderDetails.getStatus().equals("canceled")){
                    detailsHolder.total_price.setText(formatter.format(orderDetails.getTotal_price() )+" "+resources.getString(R.string.currency));
                }
                else if (orderDetails.getStatus().equals("completed")){
                    detailsHolder.total_price.setText(formatter.format(orderDetails.getTotal_price() )+" "+resources.getString(R.string.currency));
                }
                else{
                    detailsHolder.total_price.setText(formatter.format(orderDetails.getTotal_price() - orderDetails.getPoint() )+" "+resources.getString(R.string.currency));
                }*/

                detailsHolder.total_price.setText(formatter.format(orderDetails.getTotal_price() )+" "+resources.getString(R.string.currency));


                detailsHolder.total_price.setTypeface(detailsHolder.total_price.getTypeface(), Typeface.BOLD);
                if (orderDetails.getPayment_method().equals("cod")){
                    detailsHolder.linear_payment.setVisibility(View.GONE);
                    detailsHolder.payment_layout.setVisibility(View.GONE);
                }else{
                    detailsHolder.linear_payment.setVisibility(View.VISIBLE);
                    detailsHolder.payment_layout.setVisibility(View.VISIBLE);
                    detailsHolder.imgPayment.setImageUrl(orderDetails.getPayment_icon(), imageLoader);
                    detailsHolder.txtPayment.setText(orderDetails.getPayment_name());
                    detailsHolder.payment_text.setText(orderDetails.getPayment_name() + " Service Fee");
                    detailsHolder.payment_price.setText(formatter.format(orderDetails.getPayment_fee() )+" "+resources.getString(R.string.currency));

                    detailsHolder.txtAccountNumber.setText(orderDetails.getAcc_num());
                    detailsHolder.txtCopy.setText(resources.getString(R.string.copy));
                    detailsHolder.txtAccountNameTittle.setText(resources.getString(R.string.acc_name));
                    detailsHolder.txtAccountNumberTittle.setText(resources.getString(R.string.acc_num));
                    detailsHolder.txtAccountName.setText( orderDetails.getAcc_name());


                    detailsHolder.txtAccountNumber.setTypeface(detailsHolder.txtAccountNumber.getTypeface(), Typeface.BOLD);
                    detailsHolder. txtAccountName.setTypeface(detailsHolder.txtAccountName.getTypeface(), Typeface.BOLD);

                    detailsHolder.txtCopy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Copied", orderDetails.getAcc_num());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(activity, "Copied", Toast.LENGTH_LONG).show();
                        }
                    });
                }


                detailsHolder.product_discount_text.setText(resources.getString(R.string.product_discount) );
                detailsHolder.product_discount.setText("( "  +formatter.format(0)+" ) "+resources.getString(R.string.currency));
                detailsHolder.flash_discount_text.setText(resources.getString(R.string.flash_sale_discount) );
                detailsHolder.flash_discount.setText("( "  +formatter.format(0)+" ) "+resources.getString(R.string.currency));
                detailsHolder.memeber_discount_text.setText(resources.getString(R.string.member_discount) );
                detailsHolder.member_discount.setText("( "  +formatter.format(orderDetails.getMember_discount())+" ) "+resources.getString(R.string.currency));
                int discountText = 0, flashText = 0;

                for (int i=0; i < orderDetails.getOrderDiscountList().size(); i++){
                    if (orderDetails.getOrderDiscountList().get(i).getDiscount_type().equals("discount_percentage")){
                        discountText += orderDetails.getOrderDiscountList().get(i).getDiscount_amount();
                    }
                    else if (orderDetails.getOrderDiscountList().get(i).getDiscount_type().equals("flash_sale")){
                        flashText += orderDetails.getOrderDiscountList().get(i).getDiscount_amount();
                    }
                }
                detailsHolder.flash_discount.setText("( "  +formatter.format(flashText)+" ) "+resources.getString(R.string.currency));
                detailsHolder.product_discount.setText("( "  +formatter.format(discountText)+" ) "+resources.getString(R.string.currency));

                try{
                    if (orderDetails.getRemark() != null ){
                        detailsHolder.txtRemark.setText(orderDetails.getRemark());
                    }
                    else{
                        detailsHolder.txtRemark.setText("You don\'t have any note for this order.");
                    }
                }catch (Exception e){
                    detailsHolder.txtRemark.setText("You don\'t have any note for this order.");
                }



                detailsHolder.point_text.setText( resources.getString(R.string.point_use_text));
                detailsHolder.point_use.setText("( "+ formatter.format(orderDetails.getPoint())+" ) "+resources.getString(R.string.currency));



                ArrayList<UniversalPost> universalProduct = new ArrayList<>();
                List<Product> productLists = orderDetailsObj.getProductList();

                if (productLists.size() > 0 ){


                    if (orderDetailsObj.isViewAll()){
                        for(int i = 0 ; i < productLists.size() ; i++){
                            Product uni = productLists.get(i);
                            uni.setPostType(ORDER_DETAIL);
                            universalProduct.add(uni);
                        }

                        detailsHolder.txtViewAll.setVisibility(View.GONE);
                    }
                    else{

                        if (productLists.size() > 2){
                            for(int i = 0 ; i < 2 ; i++){
                                Product uni = productLists.get(i);
                                uni.setPostType(ORDER_DETAIL);
                                universalProduct.add(uni);

                            }
                            detailsHolder.txtViewAll.setVisibility(View.VISIBLE);
                        }else{
                            for(int i = 0 ; i < productLists.size() ; i++){
                                Product uni = productLists.get(i);
                                uni.setPostType(ORDER_DETAIL);
                                universalProduct.add(uni);
                            }
                            detailsHolder.txtViewAll.setVisibility(View.GONE);
                        }


                    }




                    gridAdapter = new UniversalAdapter(activity, universalProduct);
                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);
                    if (detailsHolder.recycler_view != null) {
                        detailsHolder.recycler_view.setLayoutManager(layoutManager);
                        detailsHolder.recycler_view.setAdapter(gridAdapter);
                    }

                }

                detailsHolder.txtContact.setText(resources.getString(R.string.to_contact));

                detailsHolder.txtContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (prefs.getStringPreferences(CALL_CENTER_NO).trim().length() > 0) {

                            final ArrayList<String> phoneArray = new ArrayList<String>();
                            StringTokenizer st = new StringTokenizer(prefs.getStringPreferences(CALL_CENTER_NO), ",");
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
                                for(int i = 0; i < phoneArray.size(); i ++) {
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
                                            try {

                                                Map<String, String> mix = new HashMap<String, String>();
                                                mix.put("source", "login");
                                                mix.put("call_id", phoneString);
                                                FlurryAgent.logEvent("Call Call Center", mix);
                                            } catch (Exception e) {
                                            }
                                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneString));
                                            activity.startActivity(callIntent);
                                            dialog.dismiss();
                                        }
                                    });
                                }

                                dialog.show();

                            } else {
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + prefs.getStringPreferences(CALL_CENTER_NO)));
                                activity.startActivity(callIntent);
                            }
                        }

                    }
                });



                detailsHolder.txtViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     /*   for (int i =2; i < productLists.size(); i++){
                            Product uni = productLists.get(i);
                            uni.setPostType(ORDER_DETAIL);
                            universalProduct.add(uni);
                        }
*/
                        orderDetailsObj.setViewAll(true);
                        notifyDataSetChanged();
                        detailsHolder.txtViewAll.setVisibility(View.GONE);
                    }
                });

                detailsHolder.recycler_view.getItemAnimator().setChangeDuration(0);
                detailsHolder.recycler_view.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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


            case VIEW_TYPE_VIDEO_ALL:{
                final VideoAllHolder videoProgramHolder2  = (VideoAllHolder) parentHolder;
                final Videos videoProgram2  = (Videos) universalList.get(position);
                if(videoProgram2.getDimen() > 0) {
                    videoProgramHolder2.frame.setVisibility(View.VISIBLE);
                    videoProgramHolder2.imageView.getLayoutParams().height = (videoProgram2.getDimen() * display.getWidth()) / 100;
                    videoProgramHolder2.imageView.setImageUrl(videoProgram2.getPreview_url(), imageLoader);
                }

                if(videoProgram2.getTitle() != null  && videoProgram2.getTitle().trim().length() > 0) {
                    videoProgramHolder2.title.setVisibility(View.VISIBLE);
                    videoProgramHolder2.title.setText(videoProgram2.getTitle());
                    videoProgramHolder2.title.setLineSpacing(1.8f, 1.0f);
                }else
                    videoProgramHolder2.title.setVisibility(View.GONE);


                videoProgramHolder2.body.setVisibility(View.GONE);
                videoProgramHolder2.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("post_id", String.valueOf(videoProgram2.getId()));
                            mix.put("source", "videos_list");
                            FlurryAgent.logEvent("Videos List Click Item", mix);
                        } catch (Exception e) {
                        }

                        prefs.saveIntPerferences(FIRST_PLAY, 0);
                       /* Intent intent = new Intent(activity, YouTubeDialog.class);
                        intent.putExtra("youtube_object", videoProgram2);
                        activity.startActivity(intent);*/
                    }
                });


                break;

            }

            case VIEW_TYPE_VIDEO_MORE :{
                final WatchMoreHolder watchMoreHolder = (WatchMoreHolder) parentHolder;
                final Product watchPro = (Product) universalList.get(position);
                watchMoreHolder.more.setText(resources.getString(R.string.brand_more));
                watchMoreHolder.more.setTypeface(watchMoreHolder.more.getTypeface(), Typeface.BOLD);
                watchMoreHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            if (watchPro.getId() == 1){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("source", "watch all reading list");
                                    FlurryAgent.logEvent("Click Watch More Reading Posts", mix);
                                } catch (Exception e) {
                                }

                            }

                            else if (watchPro.getId() == 2){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("source", "watch all videos list");
                                    FlurryAgent.logEvent("Click Watch More Videos", mix);
                                } catch (Exception e) {
                                }

                            }
                            else if (watchPro.getId() == 1){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("source", "watch all tools");
                                    FlurryAgent.logEvent("Click Watch More Tools", mix);
                                } catch (Exception e) {
                                }

                            }
                        }catch (Exception e){
                            Log.e(TAG, "onClick: "  + e.getMessage() );
                        }


                        Bundle bundle = new Bundle();
                        bundle.putInt("position" , watchPro.getCategoryId());
                        FragmentTest fragmentTest = new FragmentTest();
                        fragmentTest.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentTest).commit();
                    }
                });
                break;

            }

            case VIEW_TYPE_VIDEO_NEWS_SMALL :{

                break;


            }

            case VIEW_TYPE_TOOL_SUB_CATEGORY :{
                final ToolSubCatHolder toolShowHolder = (ToolSubCatHolder) parentHolder;
                final ToolChildObject toolChildObject = (ToolChildObject) universalList.get(position);

                if(toolChildObject.getName() != null  && toolChildObject.getName().trim().length() > 0) {
                    toolShowHolder.title.setVisibility(View.VISIBLE);
                    toolShowHolder.title.setText(toolChildObject.getName());
                    toolShowHolder.title.setLineSpacing(1.8f, 1.0f);
                    toolShowHolder.title.setTypeface(toolShowHolder.title.getTypeface(), Typeface.BOLD);

                }else
                    toolShowHolder.title.setVisibility(View.GONE);

                toolShowHolder.txtWatch.setText(resources.getString(R.string.more));
                toolShowHolder.titleSubs.setVisibility(View.GONE);


                if(toolChildObject.getImage() != null && toolChildObject.getImage().trim().length() > 0){

                    toolShowHolder.imageView.setImageUrl(toolChildObject.getImage(), imageLoader);
                    toolShowHolder.imageView.setVisibility(View.VISIBLE);
                }






                toolShowHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (toolChildObject.getTag().equals("ovulation_calculator")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Pregnant onClick");
                                FlurryAgent.logEvent("Pregnant onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, Get_PregnantActivity.class);
                            activity.startActivity(intent);
                        }

                        else if(toolChildObject.getTag().equals("baby_names")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("tag", toolChildObject.getTag());
                                FlurryAgent.logEvent("Click Tools Category", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, SafeQuesMainActivity.class);
                            activity.startActivity(intent);
                        }
                        else if (toolChildObject.getTag().equals("birthday_calendar")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Horoscope onClick");
                                FlurryAgent.logEvent("Horoscope onClick", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, CustomCalendarActivity.class);
                            activity.startActivity(intent);
                        }

                        else if (toolChildObject.getTag().equals("due_date")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Due Date onClick");
                                FlurryAgent.logEvent("Due Date onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, DueDateActivity.class);
                            activity.startActivity(intent);
                        }
                        else if (toolChildObject.getTag().equals("clinic_directory")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "Clinic Diectory onClick");
                                FlurryAgent.logEvent("Clinic Diectory onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ClinicActivity.class);
                            intent.putExtra("name", toolChildObject.getName());
                            prefs.saveStringPreferences(SP_DIRECTORY_CLICK, toolChildObject.getTag());
                            activity.startActivity(intent);
                        }

                        else if (toolChildObject.getTag().equals("schools")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "School Diectory onClick");
                                FlurryAgent.logEvent("School Diectory onClick", mix);
                            } catch (Exception e) {
                            }
                            Intent intent = new Intent(activity, ClinicActivity.class);
                            intent.putExtra("name", toolChildObject.getName());
                            prefs.saveStringPreferences(SP_DIRECTORY_CLICK, toolChildObject.getTag());
                            activity.startActivity(intent);
                        }


                    }
                });


                break;


            }


          /*  case VIEW_TYPE_VIDEO_LIST :{
                final VideoLatestHolder latestHolder1 = (VideoLatestHolder) parentHolder;
                final Videos videosObj = (Videos) universalList.get(position);
                latestHolder1.img.setVisibility(View.GONE);
                if(videosObj.getTitle() != null  && videosObj.getTitle().trim().length() > 0) {
                    latestHolder1.title.setVisibility(View.VISIBLE);
                    latestHolder1.title.setText(videosObj.getTitle());
                    latestHolder1.title.setLineSpacing(1.8f, 1.0f);
                    latestHolder1.title.setTypeface(latestHolder1.title.getTypeface(), Typeface.BOLD);

                }else
                    latestHolder1.title.setVisibility(View.GONE);


                if(videosObj.getPreview_url() != null && videosObj.getPreview_url().trim().length() > 0){

                    latestHolder1.img.setImageUrl(videosObj.getPreview_url(), imageLoader);
                    latestHolder1.img.setVisibility(View.VISIBLE);
                }



                latestHolder1.videoCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prefs.saveIntPerferences(FIRST_PLAY, 0);
                        Intent intent = new Intent(activity, YouTubeDialog.class);
                        intent.putExtra("youtube_object", videosObj);
                        activity.startActivity(intent);
                    }
                });


                break;

            }*/

            case VIEW_TYPE_GIFT_OBJECT_SHOW_ITEM:{

                final GiftItemHolder giftItemHolder1 = (GiftItemHolder) parentHolder;
                final GiftObject giftObject1 = (GiftObject) universalList.get(position);

                if(giftObject1.getTitle() != null && giftObject1.getTitle().trim().length() > 0){
                    giftItemHolder1.txtGiftTitile.setVisibility(View.VISIBLE);
                    giftItemHolder1.txtGiftTitile.setText(giftObject1.getTitle());
                }else
                    giftItemHolder1.txtGiftTitile.setVisibility(View.GONE);

                if(giftObject1.getPrice() != 0){
                    giftItemHolder1.txtGiftPoint.setVisibility(View.VISIBLE);
                    giftItemHolder1.txtGiftPoint.setText(giftObject1.getPrice() + " points");
                }else
                    giftItemHolder1.txtGiftPoint.setVisibility(View.GONE);
                if (!giftObject1.getPhoto_url().equals("")){
                    giftItemHolder1.imgGift.setImageUrl(giftObject1.getPhoto_url(), AppController.getInstance().getImageLoader());

                }else{
                    giftItemHolder1.imgGift.setDefaultImageResId(R.drawable.ic_launcher);
                }


                if (giftObject1.getAccess().equals("everyone")){
                    giftItemHolder1.txtGiftAcces.setVisibility(View.GONE);
                }
                else if(giftObject1.getAccess().equals("member_only")){
                    giftItemHolder1.txtGiftAcces.setVisibility(View.VISIBLE);
                    giftItemHolder1.txtGiftAcces.setText("* Vip Member Only");
                    giftItemHolder1.txtGiftAcces.setTypeface(giftItemHolder1.txtGiftAcces .getTypeface(), Typeface.BOLD);
                }

                giftItemHolder1.txtGiftPurchase.setText(resources.getString(R.string.purchase));
                giftItemHolder1.txtGiftPurchase.setTypeface(giftItemHolder1.txtGiftPurchase.getTypeface(), Typeface.BOLD);



                giftItemHolder1.card_point_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, GiftDetailsActivity.class);
                        intent.putExtra("giftObject", giftObject1);
                        activity.startActivity(intent);
                    }
                });


                break;

            }

            case VIEW_TYPE_GIFT_ITEM :{
                final GiftItemHolder giftItemHolder = (GiftItemHolder) parentHolder;
                final GiftObject giftObject = (GiftObject) universalList.get(position);

                if(giftObject.getTitle() != null && giftObject.getTitle().trim().length() > 0){
                    giftItemHolder.txtGiftTitile.setVisibility(View.VISIBLE);
                    giftItemHolder.txtGiftTitile.setText(giftObject.getTitle());
                    giftItemHolder.txtGiftTitile.setTypeface(giftItemHolder.txtGiftTitile.getTypeface(), Typeface.BOLD);
                }else
                    giftItemHolder.txtGiftTitile.setVisibility(View.GONE);

                if(giftObject.getPrice() != 0){
                    giftItemHolder.txtGiftPoint.setVisibility(View.VISIBLE);
                    giftItemHolder.txtGiftPoint.setText(giftObject.getPrice() + " points");
                    giftItemHolder.txtGiftPoint.setTypeface(giftItemHolder.txtGiftPoint.getTypeface(), Typeface.BOLD);
                }else
                    giftItemHolder.txtGiftPoint.setVisibility(View.GONE);

                if (!giftObject.getPhoto_url().equals("")){
                    giftItemHolder.imgGift.setImageUrl(giftObject.getPhoto_url(), AppController.getInstance().getImageLoader());

                    giftItemHolder.imgGift.getLayoutParams().height  = ((display.getWidth() * giftObject.getDimen()) / 100 ) * 3/5;
                    giftItemHolder.imgGift.getLayoutParams().width   = display.getWidth() * 3/5;
                }else{
                    giftItemHolder.imgGift.setDefaultImageResId(R.drawable.ic_launcher);
                }


                if (giftObject.getAccess().equals("everyone")){
                    giftItemHolder.txtGiftAcces.setVisibility(View.GONE);
                }
                else if(giftObject.getAccess().equals("member_only")){
                    giftItemHolder.txtGiftAcces.setVisibility(View.VISIBLE);
                    giftItemHolder.txtGiftAcces.setText("* Vip Member Only");
                    giftItemHolder.txtGiftAcces.setTypeface(giftItemHolder.txtGiftAcces .getTypeface(), Typeface.BOLD);
                }

                giftItemHolder.txtGiftPurchase.setText(resources.getString(R.string.purchase));
                giftItemHolder.txtGiftPurchase.setTypeface(giftItemHolder.txtGiftPurchase.getTypeface(), Typeface.BOLD);


                ViewGroup.LayoutParams layoutParams = giftItemHolder.pFull.getLayoutParams();
                int percentWidth  = (int)(layoutParams.width / giftObject.getAvailable_count());

                int percent = (int) ((100 * giftObject.getRedeemed_count()) / giftObject.getAvailable_count());

                giftItemHolder.txtBar.setText(giftObject.getRedeemed_count() + " sold");
                if (giftObject.getRedeemed_count() == 0) {
                    giftItemHolder.pFill.setVisibility(View.GONE);
                    giftItemHolder.txtBar.setText(giftObject.getAvailable_count() +  " available");
                }
                else if (percent <= 20){
                    percentWidth = (int)(layoutParams.width  * 20 /100);
                }
                else
                    percentWidth = layoutParams.width * percent / 100;


                ViewGroup.LayoutParams param = giftItemHolder.pFill.getLayoutParams();
                param.width = percentWidth;
                giftItemHolder.pFill.setLayoutParams(param);

                giftItemHolder.card_point_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, GiftDetailsActivity.class);
                        intent.putExtra("giftObject", giftObject);
                        activity.startActivity(intent);
                    }
                });

                giftItemHolder.txtGiftPurchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                                || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);

                        }
                        else {
                            int makePurchase = 0;
                            if (giftObject.getAccess().equals("member_only")){
                                if (prefs.getIntPreferences(SP_VIP_ID) == 0){
                                    makePurchase = 0;


                                    ToastHelper.showToast(activity, resources.getString(R.string.fail_purchase));
                                }else{
                                    makePurchase = 1;
                                }
                            }
                            else if (giftObject.getAccess().equals("everyone")) {
                                makePurchase = 1;
                            }

                            if (makePurchase == 1){
                                if (giftObject.getAvailable_count() >0){
                                    final Dialog dialog = new Dialog(activity);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.dialog_activate_member);
                                    dialog.setCanceledOnTouchOutside(true);

                                    Window window = dialog.getWindow();
                                    WindowManager.LayoutParams wlp = window.getAttributes();
                                    wlp.gravity = Gravity.CENTER;
                                    wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                                    window.setAttributes(wlp);

                                    CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                                    final CustomEditText editText =   (CustomEditText) dialog.findViewById(R.id.edittext);
                                    final CustomTextView txtTitle =   (CustomTextView) dialog.findViewById(R.id.dialog_activate_member);

                                    txtTitle.setText(giftObject.getPrompt_message());
                                    feedback.setText(resources.getString(R.string.purchase));






                                    feedback.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(prefs.isNetworkAvailable()) {
                                                if (editText.getText().toString().trim().length() > 0) {
                                                    makePurchaseGift(editText.getText().toString(), dialog, giftObject.getId());
                                                }else{
                                                    ToastHelper.showToast(activity, resources.getString(R.string.enter_code));
                                                }
                                            }
                                        }

                                    });
                                    dialog.show();

                                }
                                else{

                                    ToastHelper.showToast(activity, resources.getString(R.string.fully_reddem));

                                }
                            }
                        }


                    }
                });

                break;


            }

            case VIEW_TYPE_POINT_TITLE :{
                PointTitleHolder pointTitleHolder = (PointTitleHolder) parentHolder;
                PointInfo pInfo = (PointInfo) universalList.get(position);

                if (pInfo.getType().equals("use")){
                    pointTitleHolder.title.setText(resources.getString(R.string.how_to_use_point)+"");
                }
                else if (pInfo.getType().equals("earn")){
                    pointTitleHolder.title.setText(resources.getString(R.string.how_to_get_point)+"");
                }
                else if (pInfo.getType().equals("space")){
                    pointTitleHolder.title.setVisibility(View.GONE);
                }


                pointTitleHolder.title.setTypeface(pointTitleHolder.title.getTypeface(), Typeface.BOLD);


                break;

            }

            case VIEW_TYPE_USER_LOGIN :{

                PointItemHolder loginHolder = (PointItemHolder) parentHolder;
                final PointInfo pointInfo = (PointInfo) universalList.get(position);

                if (pointInfo.getName().equals("login") ){
                    loginHolder.linearGetDone.setVisibility(View.GONE);
                    loginHolder.txtPointTitle.setText(resources.getString(R.string.login_title));
                    loginHolder.imgIcon.setDefaultImageResId(R.drawable.ic_launcher);

                    loginHolder.imgIcon.setVisibility(View.VISIBLE);
                    loginHolder.imgMember.setVisibility(View.GONE);

                }
                else if (pointInfo.getName().equals("connect_member")){
                    loginHolder.imgIcon.setVisibility(View.GONE);
                    loginHolder.imgMember.setVisibility(View.VISIBLE);


                    if (prefs.getIntPreferences(SP_VIP_ID) == 0){

                        loginHolder.txtPointTitle.setText(resources.getString(R.string.member_activate));
                    }
                    else{
                        loginHolder.txtPointTitle.setText(resources.getString(R.string.connect_with_memberid ));
                    }

                    loginHolder.txtPointTitle.setTextColor(resources.getColor(R.color.black));
                    loginHolder.linearGetDone.setVisibility(View.VISIBLE);
                    loginHolder.imgGet.setVisibility(View.VISIBLE);
                    loginHolder.imgDone.setVisibility(View.GONE);
                    loginHolder.txtDone.setText(resources.getString(R.string.point_get));

                }

                else{

                    loginHolder.imgIcon.setVisibility(View.VISIBLE);
                    loginHolder.imgMember.setVisibility(View.GONE);

                    try{
                        if (!pointInfo.getIcon().equals("")){
                            loginHolder.imgIcon.setImageUrl(pointInfo.getIcon(), AppController.getInstance().getImageLoader());
                        }else{
                            loginHolder.imgIcon.setDefaultImageResId(R.drawable.ic_launcher);
                        }

                        if (!pointInfo.getCover_photo().equals("") && pointInfo.getCover_photo().trim().length() > 0){
                            loginHolder.imgCover.setImageUrl(pointInfo.getCover_photo(), AppController.getInstance().getImageLoader());
                            loginHolder.imgCover.setVisibility(View.VISIBLE);
                            loginHolder.imgCover.getLayoutParams().height  = (display.getWidth() * pointInfo.getCover_photo_dimen()) / 100;
                        }else{
                            loginHolder.imgCover.setVisibility(View.GONE);
                        }

                        loginHolder.txtPointTitle.setText(pointInfo.getBody());


                        if ( pointInfo.getType().equals("use")){
                            loginHolder.linearGetDone.setVisibility(View.GONE);

                        }
                        else if (pointInfo.getType().equals("earn")){
                            loginHolder.linearGetDone.setVisibility(View.VISIBLE);

                            if (pointInfo.getStatus().equals("false")){
                                loginHolder.txtPointTitle.setTextColor(resources.getColor(R.color.line_gray));
                                loginHolder.linearGetDone.setVisibility(View.VISIBLE);
                                loginHolder.imgGet.setVisibility(View.GONE);
                                loginHolder.imgDone.setVisibility(View.VISIBLE);
                                loginHolder.txtDone.setText(resources.getString(R.string.point_done));
                            }
                            else if (pointInfo.getStatus().equals("true") ){

                                loginHolder.txtPointTitle.setTextColor(resources.getColor(R.color.black));
                                loginHolder.linearGetDone.setVisibility(View.VISIBLE);
                                loginHolder.imgGet.setVisibility(View.VISIBLE);
                                loginHolder.imgDone.setVisibility(View.GONE);
                                loginHolder.txtDone.setText(resources.getString(R.string.point_get));

                            }
                        }
                    }catch (Exception e){
                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                    }

                }




                loginHolder.card_point_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if (pointInfo.getName().equals("login")){
                            prefs.saveIntPerferences(SP_CHANGE, 1 );
                            Intent intent = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }
                        else if(pointInfo.getName().equals("connect_member")){
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "click activate dialog");
                                FlurryAgent.logEvent("Member Activate", mix);
                            } catch (Exception e) {
                            }

                            final Dialog dialog = new Dialog(activity);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_activate_member);
                            dialog.setCanceledOnTouchOutside(true);

                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                            window.setAttributes(wlp);

                            CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                            final CustomEditText editText =   (CustomEditText) dialog.findViewById(R.id.edittext);
                            final CustomTextView txtTitle =   (CustomTextView) dialog.findViewById(R.id.dialog_activate_member);

                            if (prefs.getIntPreferences(SP_VIP_ID) == 0){

                                txtTitle.setText(resources.getString(R.string.member_activate_title));
                                feedback.setText(resources.getString(R.string.member_activate));
                            }
                            else{
                                txtTitle.setText(resources.getString(R.string.connect_with_memberid_title));
                                feedback.setText(resources.getString(R.string.connect_with_memberid));
                            }

                            feedback.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(prefs.isNetworkAvailable()) {
                                        if (editText.getText().toString().trim().length() > 0) {
                                            memberActivate(editText.getText().toString(), dialog);
                                        }else{
                                            ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));
                                        }
                                    }
                                }

                            });
                            dialog.show();

                        }

                        else{
                            if (pointInfo.getName().equals("order")){

                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Point Order Type onClick");
                                    FlurryAgent.logEvent("Point Order Type onClick", mix);
                                } catch (Exception e) {
                                }

                                Intent intent = new Intent(activity, ActivityWebView.class);
                                intent.putExtra("url", pointInfo.getData());
                                intent.putExtra("title", "help");
                                activity.startActivity(intent);
                            }
                            else if (pointInfo.getName().equals("redeem")){

                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Point Redeem Type onClick");
                                    FlurryAgent.logEvent("Point Redeem Type onClick", mix);
                                } catch (Exception e) {
                                }

                                prefs.saveIntPerferences(SP_CHANGE, 0);
                                Intent intent = new Intent(activity, GiftExchangeActivity.class);
                                activity.startActivity(intent);

                            }
                            else if (pointInfo.getName().equals("signup") && pointInfo.getStatus().equals("true")){

                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Point Signup Type onClick");
                                    FlurryAgent.logEvent("Point Signup Type onClick", mix);
                                } catch (Exception e) {
                                }

                                prefs.saveIntPerferences(SP_CHANGE, 1);
                                Intent intent = new Intent(activity, ActivityLogin.class);
                                activity.startActivity(intent);
                            }
                            else if (pointInfo.getName().equals("posting") && pointInfo.getStatus().equals("true")){

                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Point Posting Type onClick");
                                    FlurryAgent.logEvent("Point Posting Type onClick", mix);
                                } catch (Exception e) {
                                }
                                if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                                        || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                                    prefs.saveIntPerferences(SP_CHANGE, 1);
                                    Intent intent   = new Intent(activity, ActivityLogin.class);
                                    activity.startActivity(intent);

                                }
                                else {
                                    prefs.saveIntPerferences(SP_CHANGE, 1);
                                    Intent intent = new Intent(activity, UserPostUploadActivity.class);
                                    activity.startActivity(intent);
                                    //activity.overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);
                                }
                            }
                            else if (pointInfo.getName().equals("invite") && pointInfo.getStatus().equals("true")){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", "Point Inivite Type onClick");
                                    FlurryAgent.logEvent("Point Inivite Type onClick", mix);
                                } catch (Exception e) {
                                }

                                if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                                        || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                                    prefs.saveIntPerferences(SP_CHANGE, 1);
                                    Intent intent   = new Intent(activity, ActivityLogin.class);
                                    activity.startActivity(intent);

                                }
                                else {
                                    prefs.saveIntPerferences(SP_CHANGE, 1);
                                    Intent intent = new Intent(activity, InviteFriendActivity.class);
                                    intent.putExtra("source", "fragment_profile");
                                    activity.startActivity(intent);
                                }

                            }
                        }

                    }
                });

                break;

            }



            case VIEW_TYPE_USER_PROFILE :{
                UserProfileHolder profileHolder = (UserProfileHolder) parentHolder;

                profileHolder.name_text.setTypeface(profileHolder.name_text.getTypeface(), Typeface.BOLD);
                profileHolder.txtEditProfile.setTypeface(profileHolder.txtEditProfile.getTypeface(), Typeface.BOLD);
                profileHolder.member_text.setTypeface(profileHolder.member_text.getTypeface(), Typeface.BOLD);
                profileHolder.txtPoints.setTypeface(profileHolder.txtPoints.getTypeface(), Typeface.BOLD);
                profileHolder.txtShare.setTypeface(profileHolder.txtShare.getTypeface(), Typeface.BOLD);
                profileHolder.txtRate.setTypeface(profileHolder.txtRate.getTypeface(), Typeface.BOLD);
                profileHolder.txtQA.setTypeface(profileHolder.txtQA.getTypeface(), Typeface.BOLD);

                profileHolder.txtShare.setText(resources.getString(R.string.share_app));
                profileHolder.txtRate.setText(resources.getString(R.string.rate_app));
                profileHolder.txtQA.setText(resources.getString(R.string.ask_product));

                try {
                    if (prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("null")) {
                        profileHolder.profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
                    } else if (!prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals(""))
                        profileHolder.profile_image.setImageUrl(prefs.getStringPreferences(SP_USER_PROFILEIMAGE), AppController.getInstance().getImageLoader());
                    else
                        profileHolder.profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);


                } catch (Exception e) {
                    profileHolder.profile_image.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                }

                if (prefs.getStringPreferences(SP_USER_NAME) != "") {
                    profileHolder.name_text.setVisibility(View.VISIBLE);
                    String age;

                    if (prefs.getStringPreferences(SP_USER_MOMORDAD).equals("unknown_parent_status") || prefs.getStringPreferences(SP_USER_MOMORDAD).equals("other")) {
                        age = resources.getString(R.string.family_member);
                    } else {
                        age = ageConfig.calculateKidAge(prefs.getIntPreferences(SP_USER_MONOTH), prefs.getIntPreferences(SP_USER_YEAR),
                                prefs.getStringPreferences(SP_USER_BOYORGIRL), prefs.getStringPreferences(SP_USER_MOMORDAD));
                    }


                    profileHolder.name_text.setText(prefs.getStringPreferences(SP_USER_NAME) + " \n" + age);
                } else
                    profileHolder.name_text.setVisibility(View.GONE);

                if (prefs.getStringPreferences(SP_USER_PHONE) != "") {
                    profileHolder.phone_text.setVisibility(View.VISIBLE);
                    profileHolder.phone_text.setText(prefs.getStringPreferences(SP_USER_PHONE));
                } else
                    profileHolder.phone_text.setVisibility(View.GONE);



                profileHolder.txtPoints.setText(resources.getString(R.string.your_ponint) + " " +   formatter.format(prefs.getIntPreferences(SP_USER_POINT))+"");



                if (prefs.getStringPreferences(SP_USER_VIP_ACCOUNT) != "") {
                    profileHolder.member_text.setVisibility(View.VISIBLE);
                    profileHolder.member_text.setText(prefs.getStringPreferences(SP_USER_VIP_ACCOUNT));

                } else {
                    profileHolder.member_text.setVisibility(View.GONE);
                }
                profileHolder.txtEditProfile.setText(resources.getString(R.string.edit_profile));
                profileHolder.name_text.setTypeface(profileHolder.name_text.getTypeface(), Typeface.BOLD);
                profileHolder.member_text.setTypeface(profileHolder.member_text.getTypeface(), Typeface.BOLD);
                profileHolder.txtEditProfile.setTypeface(profileHolder.txtEditProfile.getTypeface(), Typeface.BOLD);
                profileHolder.txtPoints.setTypeface(profileHolder.txtPoints.getTypeface(), Typeface.BOLD);
                profileHolder.txtEditProfile.setPaintFlags(profileHolder.txtEditProfile.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                profileHolder.txtPoints.setPaintFlags(profileHolder.txtPoints.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                profileHolder.txtShare.setPaintFlags(profileHolder.txtShare.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                profileHolder.txtRate.setPaintFlags(profileHolder.txtRate.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
                profileHolder.txtQA.setPaintFlags(profileHolder.txtQA.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);

                profileHolder.profile_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.layout_qr_code);
                        dialog.setCancelable(true);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        window.setAttributes(wlp);
                        dialog.setCanceledOnTouchOutside(true);

                        ImageView img  = (ImageView) dialog.findViewById(R.id.imgQrCode);

                        Bitmap qrCodeBitmap = EncodingUtils.createQRCode(prefs.getStringPreferences(SP_USER_PHONE), 800, 800, null);
                        img.setImageBitmap(qrCodeBitmap);


                        dialog.show();
                    }
                });

                profileHolder.txtEditProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveIntPerferences(SP_CHANGE, 0 );
                        Intent intent =  new Intent(activity, EditProfileActivity.class);
                        activity.startActivity(intent);
                    }
                });

                profileHolder.txtPoints.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(activity, PointHistoryActivity.class);
                        activity.startActivity(intent);
                    }
                });

                profileHolder.txtShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "share_app");
                            FlurryAgent.logEvent("Click Share App Button", mix);
                        } catch (Exception e) {
                        }

                        String shareBody = "https://play.google.com/store/apps/details?id=com.kyarlay.ayesunaing";

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }
                });

                profileHolder.txtRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "rate_us");
                            FlurryAgent.logEvent("Click Rate Us Button", mix);
                        } catch (Exception e) {
                        }


                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.kyarlay.ayesunaing"));
                        activity.startActivity(i);
                    }
                });

                profileHolder.txtQA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "ask_question");
                            FlurryAgent.logEvent("Click Q&A About Product Button", mix);
                        } catch (Exception e) {
                        }



                        Intent intent   = new Intent(activity, AskProdcutAcitivity.class);

                        intent.putExtra("fromCalss","fromProfile");
                        intent.putExtra("product_id", 0);
                        activity.startActivity(intent);
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

            case VIEW_TYPE_INVITE_HISTORY:{
                PointHistoryHolder pointHistoryHolder1   = (PointHistoryHolder) parentHolder;
                final InviteHistory inviteHistory = (InviteHistory) universalList.get(position);
                pointHistoryHolder1.date.setText(inviteHistory.getPhone());
                pointHistoryHolder1.usage_type.setText(inviteHistory.getName());
                pointHistoryHolder1.point.setText(inviteHistory.getStatus());

                pointHistoryHolder1.point.setTypeface(pointHistoryHolder1.point.getTypeface(), Typeface.BOLD);
                pointHistoryHolder1.usage_type.setTypeface(pointHistoryHolder1.point.getTypeface(), Typeface.BOLD);
                pointHistoryHolder1.date.setTypeface(pointHistoryHolder1.point.getTypeface(), Typeface.BOLD);


                break;

            }

            case VIEW_TYPE_POINT_HISTORY:{

                PointHistoryHolder pointHistoryHolder   = (PointHistoryHolder) parentHolder;
                final PointHistory point = (PointHistory) universalList.get(position);
                pointHistoryHolder.date.setText(point.getCreated_at());
                pointHistoryHolder.usage_type.setTypeface(pointHistoryHolder.usage_type.getTypeface(), Typeface.BOLD);
                pointHistoryHolder.date.setTypeface(pointHistoryHolder.date.getTypeface(), Typeface.BOLD);
                pointHistoryHolder.point.setTypeface(pointHistoryHolder.point.getTypeface(), Typeface.BOLD);
                pointHistoryHolder.usage_type.setText(point.getUsage_type());

                if (point.getPoint() > 0){
                    pointHistoryHolder.point.setTextColor(activity.getResources().getColor(R.color.text));
                    pointHistoryHolder.point.setText( "+" + point.getPoint());
                }else{
                    pointHistoryHolder.point.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                    pointHistoryHolder.point.setText( "" + point.getPoint());
                }


                break;
            }

            case VIEW_TYPE_GIFT:{
                GiftHolder giftHolder   = (GiftHolder) parentHolder;
                Gift gift = (Gift) universalList.get(position);

                if(gift.getTitle() != null && gift.getTitle().trim().length() > 0){
                    giftHolder.title.setVisibility(View.VISIBLE);
                    giftHolder.title.setText(gift.getTitle());
                }else
                    giftHolder.title.setVisibility(View.GONE);

                if(gift.getBody() != null && gift.getBody().trim().length() > 0){
                    giftHolder.body.setVisibility(View.VISIBLE);
                    giftHolder.body.setText(gift.getBody());
                }else
                    giftHolder.body.setVisibility(View.GONE);

                if(gift.getRelease_at() != null && gift.getRelease_at().trim().length() > 0){
                    giftHolder.time.setVisibility(View.VISIBLE);
                    giftHolder.time.setText("Release Date : "+gift.getRelease_at());
                }else
                    giftHolder.time.setVisibility(View.GONE);

                if(gift.getPhoto_url() != null && gift.getPhoto_url().trim().length() > 0){
                    giftHolder.image.setVisibility(View.VISIBLE);
                    giftHolder.image.setImageUrl(gift.getPhoto_url(), imageLoader);
                    giftHolder.image.getLayoutParams().height = (display.getWidth() * gift.getDimen()) / 100;
                }else
                    giftHolder.image.setVisibility(View.GONE);

                if(gift.getAvailable_count() > 0){
                    giftHolder.count.setVisibility(View.VISIBLE);
                    giftHolder.count.setText("Available Count  : "+gift.getAvailable_count()+" ");
                }else
                    giftHolder.count.setVisibility(View.GONE);


                if(gift.getPrice() > 0){
                    giftHolder.price.setVisibility(View.VISIBLE);
                    giftHolder.price.setText("Price   :  "+gift.getPrice()+" ");
                }

                break;

            }

            case VIEW_TYPE_CALENDAR_DAYS:{

                CalendarDayHolder calendarDayHolder = (CalendarDayHolder) parentHolder;
                LuckyDay luckyday = (LuckyDay) universalList.get(position);

                if(luckyday.getLuck_group() != 0 ) {
                    calendarDayHolder.ratingBar.setRating((float) luckyday.getLuck_group());
                    calendarDayHolder.ratingBar.setVisibility(View.VISIBLE);
                }else{
                    calendarDayHolder.ratingBar.setVisibility(View.GONE);
                }


                if(luckyday.getBirth_group() != null && luckyday.getBirth_group().trim().length() > 0){
                    calendarDayHolder.birth_group.setText(luckyday.getBirth_group());
                    calendarDayHolder.birth_group.setVisibility( View.VISIBLE);
                }else {
                    calendarDayHolder.birth_group.setVisibility( View.GONE);
                }
                if(luckyday.getWork_group() != null && luckyday.getWork_group().trim().length() > 0){
                    calendarDayHolder.work_group.setText(luckyday.getWork_group());
                    calendarDayHolder.work_group.setVisibility( View.VISIBLE);
                }else {
                    calendarDayHolder.work_group.setVisibility( View.GONE);
                }

                if(luckyday.getDate_info() != null &&  luckyday.getDate_info().length() > 3 ){
                    calendarDayHolder.date.setText(luckyday.getDate_info().substring(0, 3));
                    calendarDayHolder.date.setVisibility(View.VISIBLE);
                }else{
                    calendarDayHolder.date.setVisibility(View.GONE);
                }

                if(luckyday.getDesc() != null &&  luckyday.getDesc().trim().length() > 0){
                    calendarDayHolder.desc.setText(luckyday.getDesc());
                    calendarDayHolder.desc.setVisibility(View.VISIBLE);
                }else{
                    calendarDayHolder.desc.setVisibility(View.GONE);
                }


                if(luckyday.getDate_info() != null && luckyday.getDate_info().trim().length() > 0) {
                    try {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                        Date date = dateFormat.parse(luckyday.getDay_info());
                        DateFormat formatter = new SimpleDateFormat("d");
                        String dateStr = formatter.format(date);
                        calendarDayHolder.day.setText(dateStr);

                        calendarDayHolder.day.setVisibility(View.VISIBLE);


                    } catch (Exception e1) {
                        e1.printStackTrace();
                        Log.e(TAG, "onBindViewHolder: "  + e1.getMessage() );
                    }
                }else{
                    calendarDayHolder.day.setVisibility(View.GONE);
                }



                break;


            }

            case VIEW_TYPE_USER_POST_UPLOAD:{

                UserPostUploadHolder userPostUpoadHolder = (UserPostUploadHolder) parentHolder;
                userPostUpoadHolder.textview.setText(resources.getString(R.string.wirte_post));
                userPostUpoadHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                                || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                            prefs.saveIntPerferences(SP_READING_LOGIN, 1);
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);

                        }else if( prefs.getIntPreferences(SP_USER_MONOTH) == 0 || prefs.getIntPreferences(SP_USER_YEAR) == 0){
                            Intent intent = new Intent(activity, UserPostDetailActivity.class);
                            activity.startActivity(intent);
                        }else {
                            Intent intent = new Intent(activity, UserPostUploadActivity.class);
                            activity.startActivity(intent);
                            //activity.overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);
                        }
                    }
                });



                break;

            }

            case VIEW_TYPE_MAIN_GRID_CATEOGRY:{
                MainGridCategoryHolder categoryMainHolder = (MainGridCategoryHolder) parentHolder;
                ArrayList<UniversalPost> mainGridCats = new ArrayList<>();

                final CategoryGrid obj = (CategoryGrid) universalList.get(position);


                categoryMainHolder.txtBackground.requestLayout();

                if (obj.getBackgroundColor() != null && obj.getBackgroundColor().trim().length() != 0){
                    int bg_color  = Integer.parseInt(obj.getBackgroundColor(), 16)+0xFF000000;
                    categoryMainHolder.txtBackground.setBackgroundColor(bg_color);
                }





                List<CategoryMain> moreItemsCats = obj.getCategoryMainList();

                if (moreItemsCats.size() > 0 ){


                    for(int i = 0 ; i < moreItemsCats.size() ; i++){
                        CategoryMain uni = moreItemsCats.get(i);
                        mainGridCats.add(uni);
                    }

                    gridAdapter = new UniversalAdapter(activity, mainGridCats);

                    if (obj.getCategory_type().equals(CUSTOM_GRID_ITEM)){
                        layoutManager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);

                        if (categoryMainHolder.recyclerView != null) {


                            categoryMainHolder.recyclerView.setLayoutManager(layoutManager);
                            categoryMainHolder.recyclerView.setAdapter(gridAdapter);

                            categoryMainHolder.txtBackground.setVisibility(View.VISIBLE);
                            categoryMainHolder.txtBackground.getLayoutParams().height = obj.getHeightTextView();


                        }

                    }
                    else if (obj.getCategory_type().equals(CUSTOM_SCROLL_ITEM)){



                        layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);

                        if (categoryMainHolder.recyclerView != null) {

                            categoryMainHolder.recyclerView.setLayoutManager(layoutManager);
                            categoryMainHolder.recyclerView.setAdapter(gridAdapter);

                            //   categoryMainHolder.txtBackground.setVisibility(View.GONE);

                            categoryMainHolder.txtBackground.setVisibility(View.VISIBLE);
                            categoryMainHolder.txtBackground.getLayoutParams().height = obj.getHeightTextView();
                        }
                    }
                }



                categoryMainHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                categoryMainHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

            case VIEW_TYPE_MAIN_PAGE_CATEGORY:{
                MainGridHolder mainGridHolder = (MainGridHolder) parentHolder;

                ArrayList<UniversalPost> mainGrids2 = new ArrayList<>();
                CategoryGrid obj22 = (CategoryGrid) universalList.get(position);
                mainGridHolder.title.setText(resources.getString(R.string.choose_category));



                List<CategoryMain> moreItems12 = obj22.getCategoryMainList();

                if (moreItems12.size() > 0 ){
                    for(int i = 0 ; i < moreItems12.size() ; i++){
                        CategoryMain uni = moreItems12.get(i);
                        uni.setPostType(MAIN_PAGE_CATEGORY_ITEM);
                        mainGrids2.add(uni);
                    }

                    gridAdapter = new UniversalAdapter(activity, mainGrids2);
                    layoutManager = new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false);
                    if (mainGridHolder.recyclerView != null) {
                        mainGridHolder.recyclerView.setLayoutManager(layoutManager);
                        mainGridHolder.recyclerView.setAdapter(gridAdapter);
                    }

                }


                mainGridHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                mainGridHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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


            case VIEW_TYPE_MAIN:{
                MainGridHolder holder = (MainGridHolder) parentHolder;
                ArrayList<UniversalPost> mainGrids = new ArrayList<>();
                MainObject objMainType = (MainObject) universalList.get(position);
                holder.title.setText(objMainType.getTitle());
                List<MainItem> items = new ArrayList<>();
                items = objMainType.getItems();

                gridAdapter = new UniversalAdapter(activity, mainGrids);
                layoutManager = new GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false);

                if (holder.recyclerView != null) {
                    holder.recyclerView.setLayoutManager(layoutManager);
                    holder.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < items.size() ; i++){
                    MainItem uni = items.get(i);
                    uni.setPostType(MAIN_GRIDVIEW_ITEM);
                    mainGrids.add(uni);
                }

                holder.recyclerView.getItemAnimator().setChangeDuration(0);
                holder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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


            case VIEW_TYPE_DETAIL_GRID:{
                final DetailGridHolder detailGridHolder = (DetailGridHolder) parentHolder;
                final ArrayList<UniversalPost> mainGrids12 = new ArrayList<>();
                final Product product1 = (Product) universalList.get(position);

                final List<Images> items12  = product1.getDetailImages();
                gridAdapter = new UniversalAdapter(activity, mainGrids12);
                layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);

                if (detailGridHolder.main_recycler_view != null) {
                    detailGridHolder.main_recycler_view.setLayoutManager(layoutManager);
                    detailGridHolder.main_recycler_view.setAdapter(gridAdapter);
                }

                if(product1.getOptions() != null && product1.getOptions().trim().length() > 0) {
                    detailGridHolder.options_layout.setVisibility(View.VISIBLE);
                    detailGridHolder.options_text.setText(resources.getString(R.string.product_options));
                    detailGridHolder.options.setText(product1.getOptions());
                }else {
                    detailGridHolder.options_layout.setVisibility(View.GONE);
                }

                if(product1.getDesc().trim().length() > 0){
                    detailGridHolder.desc.setText(product1.getDesc());
                    Linkify.addLinks(detailGridHolder.desc,Linkify.ALL);
                }


                detailGridHolder.fadeEdge.setFadeSizes(0,0,0,0);
                detailGridHolder.fadeEdge.setFadeEdges(false, false, false,false);

                detailGridHolder.more.setTypeface(detailGridHolder.more.getTypeface(), Typeface.BOLD);
                detailGridHolder.more.setText(resources.getString(R.string.watch_details ) );
                detailGridHolder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        detailGridHolder.more.setVisibility(View.GONE);
                        // detailGridHolder.fadeEdge.setFadeSizes(0,0,0,0);
                        // detailGridHolder.fadeEdge.setFadeEdges(false, false, false,false);
                        detailGridHolder.linearMain.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        detailGridHolder.linearMain.requestLayout();

                    }
                });

                if (product1.getQaObjectList().size() > 0){
                    detailGridHolder.linearAnswer.setVisibility(View.VISIBLE);
                    detailGridHolder.linearQuestion.setVisibility(View.VISIBLE);
                    detailGridHolder.txtNoQA.setVisibility(View.GONE);

                    QAObject qaObj = product1.getQaObjectList().get(0);
                    detailGridHolder.txtQuestion.setText(qaObj.getQuestion());
                    detailGridHolder.txtAnswer.setText(qaObj.getAnswer());


                }else{
                    detailGridHolder.linearAnswer.setVisibility(View.GONE);
                    detailGridHolder.linearQuestion.setVisibility(View.GONE);
                    detailGridHolder.txtNoQA.setVisibility(View.VISIBLE);
                }


                detailGridHolder.txtViewAllQA.setTypeface(detailGridHolder.txtViewAllQA.getTypeface(), Typeface.BOLD );
                detailGridHolder.txtAskQA.setTypeface(detailGridHolder.txtAskQA.getTypeface(), Typeface.BOLD );
                detailGridHolder.txtViewAllQA.setText(resources.getString(R.string.view_question_all));

                detailGridHolder.txtViewAllQA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("product_id", String.valueOf(product1.getId()));
                            FlurryAgent.logEvent("View All Product Questions", mix);
                        } catch (Exception e) {
                        }

                        Intent intent   = new Intent(activity, AskProdcutAcitivity.class);
                        intent.putExtra("fromCalss","question");
                        intent.putExtra("product_id", product1.getId());
                        activity.startActivity(intent);
                    }
                });



                detailGridHolder.txtNoQA.setText(resources.getString(R.string.no_question));
                detailGridHolder.txtAskQA.setText(resources.getString(R.string.ask_question_product));

                detailGridHolder.txtAskQA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("product_id", String.valueOf(product1.getId()));
                            FlurryAgent.logEvent("Click Ask Question Icon", mix);
                        } catch (Exception e) {}

                        if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                            final Dialog dialog = new Dialog(activity);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_feedback);
                            dialog.setCanceledOnTouchOutside(true);

                            Window window = dialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                            window.setAttributes(wlp);

                            CustomButton feedback = (CustomButton) dialog.findViewById(R.id.feedback);
                            CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                            final CustomEditText editText =   (CustomEditText) dialog.findViewById(R.id.edittext);
                            title.setText(resources.getString(R.string.send_message));
                            title.setTextSize(14);
                            feedback.setText(resources.getString(R.string.chat_message_button));
                            feedback.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(prefs.isNetworkAvailable()) {
                                        if (editText.getText().toString().trim().length() > 0) {
                                            try {
                                                Map<String, String> mix = new HashMap<String, String>();
                                                mix.put("product_id",String.valueOf(product1.getId()));
                                                FlurryAgent.logEvent("Send Product Message", mix);
                                            } catch (Exception e) {}



                                            dialog.dismiss();
                                            ProgressDialog progressDialog = new ProgressDialog(activity);
                                            progressDialog.setMessage("Loading . . . ");
                                            progressDialog.setCanceledOnTouchOutside(false);
                                            progressDialog.show();

                                            sendMessageToServer(progressDialog, product1.getId(), editText.getText().toString());




                                        }else{

                                            ToastHelper.showToast(activity, resources.getString(R.string.chat_mesage_toast));
                                        }
                                    }else {
                                        ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));
                                    }
                                }

                            });
                            dialog.show();
                        }else{
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }
                    }
                });




                if (items12.size() >0) {

                    for(int i = 0 ; i < items12.size() ; i++){
                        Images uni = items12.get(i);
                        uni.setImgVisible(1);
                        uni.setPostType(DETAIL_IMAGE);
                        mainGrids12.add(uni);
                    }

                    detailGridHolder.main_recycler_view.setVisibility(View.VISIBLE);

                }
                else {
                    detailGridHolder.main_recycler_view.setVisibility(View.GONE);
                    detailGridHolder.more.setVisibility(View.GONE);


                }

                if (product1.getDetailImages().size() > 0){

                    detailGridHolder.linearMain.getLayoutParams().height  =  (display.getWidth() * 3 ) /6  ;
                    //detailGridHolder.fadeEdge.setFadeSizes(0,0,500,0);
                    //detailGridHolder.fadeEdge.setFadeEdges(false, false, true,false);
                    detailGridHolder.main_recycler_view.setVisibility(View.VISIBLE);
                    detailGridHolder.more.setVisibility(View.VISIBLE);
                }
                else if (product1.getDesc().length() > 500){
                    detailGridHolder.linearMain.getLayoutParams().height  =  (display.getWidth() * 3 ) /6  ;
                    // detailGridHolder.fadeEdge.setFadeSizes(0,0,500,0);
                    // detailGridHolder.fadeEdge.setFadeEdges(false, false, true,false);
                    detailGridHolder.more.setVisibility(View.VISIBLE);
                    detailGridHolder.main_recycler_view.setVisibility(View.GONE);


                }
                else{
                    detailGridHolder.linearMain.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    // detailGridHolder.fadeEdge.setFadeSizes(0,0,0,0);
                    // detailGridHolder.fadeEdge.setFadeEdges(false, false, false,false);
                    detailGridHolder.more.setVisibility(View.GONE);
                    detailGridHolder.main_recycler_view.setVisibility(View.GONE);

                }

                if(product1.getBrand_name().trim().length() > 0){
                    detailGridHolder.linearBrand.setVisibility(View.VISIBLE);
                    detailGridHolder.brand.setText(String.format(resources.getString(R.string.brand_view_more), product1.getBrand_name()));
                    // detailGridHolder.brand.setTypeface(detailGridHolder.brand.getTypeface(), Typeface.BOLD);
                    detailGridHolder.brandTitle.setTypeface(detailGridHolder.brand.getTypeface(), Typeface.BOLD);
                    detailGridHolder.brandTitle.setText( product1.getBrand_name());

                    if (product1.getBrand_name().length() > 20){
                        detailGridHolder.brandTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                    }

                    final Brand brand = new Brand();
                    brand.setId(product1.getBrand_id());
                    brand.setTitle(product1.getBrand_name());

                    detailGridHolder.linearBrand.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("product_id", String.valueOf(product1.getId()));
                                mix.put("brand_id", String.valueOf(product1.getBrand_id()));
                                FlurryAgent.logEvent("Click Brand", mix);
                            } catch (Exception e) {}
                            prefs.saveIntPerferences(SP_BRAND_ID, product1.getCategoryId());
                            Intent intent = new Intent(activity, BrandActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", brand.getId());
                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }
                    });
                }
                else
                    detailGridHolder.linearBrand.setVisibility(View.GONE);


                break;
            }

            case VIEW_TYPE_MAIN_GIFT_OBJECT:{

                MainDiscountGridHolder mainDiscoutHolder2 = (MainDiscountGridHolder) parentHolder;
                ArrayList<UniversalPost> mainGrids174 = new ArrayList<>();
                GiftObjectList obj2 = (GiftObjectList) universalList.get(position);
                mainDiscoutHolder2.title.setText(obj2.getTitle());
                List<GiftObject> giftObjectList = new ArrayList<>();
                giftObjectList = obj2.getItems();
                gridAdapter = new UniversalAdapter(activity, mainGrids174);
                layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);

                if (mainDiscoutHolder2.recyclerView != null) {
                    mainDiscoutHolder2.recyclerView.setLayoutManager(layoutManager);
                    mainDiscoutHolder2.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < giftObjectList.size() ; i++){
                    GiftObject uni = giftObjectList.get(i);


                    mainGrids174.add(uni);
                }


                mainDiscoutHolder2.recyclerView.getItemAnimator().setChangeDuration(0);
                mainDiscoutHolder2.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

                mainDiscoutHolder2.more.setTypeface(mainDiscoutHolder2.more.getTypeface(), Typeface.BOLD);
                mainDiscoutHolder2.more.setText(resources.getString(R.string.brand_more));
                mainDiscoutHolder2.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, GiftExchangeActivity.class);
                        activity.startActivity(intent);
                    }
                });

                break;
            }


            case VIEW_TYPE_SAFE_SUB_MAIN:{
                MainDiscountGridHolder safeSubMainHolder = (MainDiscountGridHolder) parentHolder;

                ArrayList<UniversalPost> mainGrids196 = new ArrayList<>();
                SafeMainObj objSafeMain = (SafeMainObj) universalList.get(position);
                safeSubMainHolder.title.setText(objSafeMain.getTitle());
                List<SafeItem> items196 = new ArrayList<>();
                items196 = objSafeMain.getSafeItemList();
                gridAdapter = new UniversalAdapter(activity, mainGrids196);
                layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);

                if (safeSubMainHolder.recyclerView != null) {
                    safeSubMainHolder.recyclerView.setLayoutManager(layoutManager);
                    safeSubMainHolder.recyclerView.setAdapter(gridAdapter);
                }


                for(int i = 0 ; i < items196.size() ; i++){
                    SafeItem uni = items196.get(i);
                    uni.setPostType(SAFE_SUB_ITEM);
                    mainGrids196.add(uni);
                }

                safeSubMainHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                safeSubMainHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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

                safeSubMainHolder.more.setVisibility(View.GONE);

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
                gridAdapter = new UniversalAdapter(activity, mainGrids1);
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



            case VIEW_TYPE_SHOW_IMAGE :{
                MainGridItemHolder toolsHolder = (MainGridItemHolder) parentHolder;
                Product product5 = (Product) universalList.get(position);
                toolsHolder.title.setVisibility(View.GONE);
                if(product5.getPreviewImage()!= null && product5.getPreviewImage().trim().length() > 0) {
                    toolsHolder.imageView.setImageUrl(product5.getPreviewImage(), imageLoader);
                    toolsHolder.imageView.setVisibility(View.VISIBLE);
                }
                else
                    toolsHolder.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);

                break;
            }


            case VIEW_TYPE_MAIN_PAGE_CATEGORY_ITEM:{
                MainHolder superHolder = (MainHolder) parentHolder;
                final CategoryMain gridSub12 = (CategoryMain) universalList.get(position);
                superHolder.title.setText(gridSub12.getTitle());
                if (gridSub12.getImage() != null && gridSub12.getImage().trim().length() > 0){
                    superHolder.imageView.setImageUrl(gridSub12.getImage_big(), imageLoader);
                }
                else {

                    superHolder.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }

                superHolder.title.setVisibility(View.GONE);

                superHolder.imageView.setVisibility(View.VISIBLE);

                superHolder.layout.setGravity(Gravity.CENTER);

                superHolder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                superHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("super_category_id", String.valueOf(gridSub12.getId()));
                            mix.put("source", "product_list");
                            FlurryAgent.logEvent("Click Super Category", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, MainSuperActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("title", gridSub12.getTitle());
                        bundle.putInt("id", gridSub12.getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);




                    }
                });

                break;


            }


            case VIEW_TYPE_MASTER_LEFT_ITEM:{
                CategoryGridItemHolder masterItemHolder = (CategoryGridItemHolder) parentHolder;
                final CategoryMain categoryMain = (CategoryMain) universalList.get(position);
                masterItemHolder.txtHidden.setText(categoryMain.getTitle());

                masterItemHolder.linearMain.getLayoutParams().width  =  (display.getWidth() / 3) ;
                masterItemHolder.linearMain.getLayoutParams().height  =  (display.getWidth() / 5 ) ;
                masterItemHolder.txtSelect.getLayoutParams().height  =  (display.getWidth() / 5 ) ;
                masterItemHolder.layout.setVisibility(View.GONE);
                masterItemHolder.txtHidden.setVisibility(View.VISIBLE);
                masterItemHolder.txtHidden.setTypeface(masterItemHolder.txtHidden.getTypeface(), Typeface.BOLD);
                if (prefs.getIntPreferences(SUPER_ID) == categoryMain.getId()){
                    masterItemHolder.linearMain.setBackgroundColor(activity.getResources().getColor(R.color.background));
                    masterItemHolder.txtSelect.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                    masterItemHolder.txtHidden.setTextColor(activity.getResources().getColor(R.color.black));
                    masterItemHolder.txtSelect.setVisibility(View.VISIBLE);

                }else{
                    masterItemHolder.linearMain.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    masterItemHolder.txtSelect.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    masterItemHolder.txtHidden.setTextColor(activity.getResources().getColor(R.color.text_comment));
                    masterItemHolder.txtSelect.setVisibility(View.GONE);
                }

                break;
            }






            case VIEW_TYPE_MAIN_GRIDVIEW:{
                MainGridItemHolder holder1 = (MainGridItemHolder) parentHolder;
                final MainItem grid = (MainItem) universalList.get(position);
                holder1.title.setText(grid.getTitle());
                holder1.imageView.setImageUrl(grid.getPreview_url(), imageLoader);
                holder1.imageView.setVisibility(View.VISIBLE);


                holder1.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                holder1.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prefs.saveStringPreferences(SP_BRAND_TAG, grid.getPost_type());
                        prefs.saveIntPerferences(SP_BRAND_ID, grid.getId());


                        //Intent intent = new Intent(activity, CategoryActivity.class);
                        Intent intent = new Intent(activity, CategoryActivity.class);
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



            case VIEW_TYPE_BRAND_BANNER:{
                ArrayList<UniversalPost> brands = new ArrayList<>();
                NewArrivalMainHolder brandBannerHolder = (NewArrivalMainHolder) parentHolder;
                final MainObject brandBanner = (MainObject) universalList.get(position);
                brandBannerHolder.txtSeeMore.setText(resources.getString(R.string.see_more));
                brandBannerHolder.txtSeeMore.setTypeface(brandBannerHolder.txtSeeMore.getTypeface(), Typeface.BOLD);

                brandBannerHolder.txtNewArrival.setText(brandBanner.getTitle());
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
                    gridAdapter = new UniversalAdapter(activity, brands);
                    layoutManager = new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false);
                    if (brandBannerHolder.recyclerView != null) {
                        brandBannerHolder.recyclerView.setLayoutManager(layoutManager);
                        brandBannerHolder.recyclerView.setAdapter(gridAdapter);
                        ((LinearLayout.LayoutParams) brandBannerHolder.recyclerView.getLayoutParams()).setMargins(10, 10, 10, 10);

                    }
                }

                brandBannerHolder.linearSeeMore.setOnClickListener(new View.OnClickListener() {
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

                break;

            }


            case VIEW_TYPE_SEARCHED:{
                ArrayList<UniversalPost> searchGrid = new ArrayList<>();
                MainGridHolder search = (MainGridHolder) parentHolder;
                MainObject sea = (MainObject) universalList.get(position);
                search.title.setText(sea.getTitle());
                List<MainItem> seas = new ArrayList<>();
                seas = sea.getItems();

                for(int i = 0 ; i < seas.size() ; i++){
                    UniversalPost uni = seas.get(i);
                    //uni.setPostType(SEARCHED_ITEM);
                    searchGrid.add(uni);
                }
                if(searchGrid.size() > 0) {
                    gridAdapter = new UniversalAdapter(activity, searchGrid);
                    layoutManager = new GridLayoutManager(activity.getApplicationContext(), 3);
                    if (search.recyclerView != null) {
                        search.recyclerView.setLayoutManager(layoutManager);
                        search.recyclerView.setAdapter(gridAdapter);
                    }
                }

                break;
            }



            case VIEW_TYPE_SEARCHED_ITEM_NEW:{
                SearchItemHolder searchItemHolder = (SearchItemHolder) parentHolder;
                final MainItem searchItem = (MainItem) universalList.get(position);
                searchItemHolder.title.setText(searchItem.getTitle());
                searchItemHolder.imageView.setImageUrl(searchItem.getPreview_url(), imageLoader);
                searchItemHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", "previous");
                            FlurryAgent.logEvent("Search", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, SearchResultActivity.class );
                        Bundle bun = new Bundle();
                        bun.putString("url",constantSearch + prefs.getIntPreferences(SP_CURRENT_VERSION) +
                                "&" + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE) + "&query=");
                        bun.putString("search", searchItem.getTitle());
                        intent.putExtras(bun);
                        activity.startActivity(intent);

                    }
                });

                break;
            }


            case VIEW_TYPE_TRENDING:{
                ArrayList<UniversalPost> trendingGrids = new ArrayList<>();

                MainGridHolder treHolder = (MainGridHolder) parentHolder;
                MainObject trend = (MainObject) universalList.get(position);
                treHolder.title.setText(trend.getTitle());
                List<MainItem> trendings = new ArrayList<>();
                trendings = trend.getItems();

                for(int i = 0 ; i < trendings.size() ; i++){
                    UniversalPost uni = trendings.get(i);
                    uni.setPostType(TRENDING_ITEM);
                    trendingGrids.add(uni);
                }

                if(trendingGrids.size() > 0) {
                    gridAdapter = new UniversalAdapter(activity, trendingGrids);
                    layoutManager = new LinearLayoutManager(activity.getApplicationContext());
                    if (treHolder.recyclerView != null) {
                        treHolder.recyclerView.setLayoutManager(layoutManager);
                        treHolder.recyclerView.setAdapter(gridAdapter);
                    }
                }

                break;
            }


            case VIEW_TYPE_TRENDING_ITEM:{
                TrendingItemHolder tholder = (TrendingItemHolder) parentHolder;
                final MainItem trending = (MainItem) universalList.get(position);

                tholder.text.setText(trending.getTitle());
                tholder.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("type", "popular");
                            FlurryAgent.logEvent("Search", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, SearchResultActivity.class );
                        Bundle bun = new Bundle();
                        bun.putString("url",constantSearch + prefs.getIntPreferences(SP_CURRENT_VERSION) +
                                "&" + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE) + "&query=");
                        bun.putString("search", trending.getTitle());
                        intent.putExtras(bun);
                        activity.startActivity(intent);
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





            case VIEW_TYPE_CART_TOTAL_ORDER:{
                OrderTotalHolder totalHolder = (OrderTotalHolder) parentHolder;
                final Order orderTotal = (Order) universalList.get(position);

                totalHolder.orderNo.setText(orderTotal.getOrder_id()+"");
                totalHolder.totalPrice.setText(formatter.format(orderTotal.getTotal_price()) +" "+resources.getString(R.string.currency));
                totalHolder.date.setText(orderTotal.getOrdered_at()+"");
                totalHolder.status.setText(orderTotal.getStatus()+"");

                totalHolder.orderNoString.setText(resources.getString(R.string.order_total_No));
                totalHolder.totalPriceString.setText(resources.getString(R.string.order_total_totalPrice));
                totalHolder.dateString.setText( resources.getString(R.string.order_total_date));
                totalHolder.statusString.setText(resources.getString(R.string.order_total_status));


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



            case VIEW_TYPE_BRAND_BANNER_ITEM:{
                BrandBannerHolder bbHolder = (BrandBannerHolder) parentHolder;
                final MainItem bbitem  = (MainItem) universalList.get(position);


                bbHolder.image.setVisibility(View.GONE);
                bbHolder.cardViewBrand.setVisibility(View.VISIBLE);
                bbHolder.imageCardView.setImageUrl(bbitem.getPreview_url(), imageLoader);
                bbHolder.imageCardView.getLayoutParams().width = (int) (display.getWidth() / 5.5) ;
                bbHolder.imageCardView.getLayoutParams().height =(int) (display.getWidth() / 6) ;
                /*
                  bbHolder1.imageCardView.getLayoutParams().width = (int) (display.getWidth() / 5.5) ;
                bbHolder1.imageCardView.getLayoutParams().height =  (int) (display.getWidth() / 6) ;
                 */
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



            case VIEW_TYPE_SETTINGS_TYPE:{
                SettingsHolder settingsHolder = (SettingsHolder) parentHolder;
                Settings settings = (Settings) universalList.get(position);
                settingsHolder.image.setImageResource(settings.getIcon());
                if(position == 1){
                    if(prefs.getIntPreferences(SP_VIP_ID) != 0){
                        settingsHolder.text.setText(resources.getString(R.string.member_no)+" - "+
                                prefs.getIntPreferences(SP_MEMBER_ID));
                    }else {
                        settingsHolder.text.setText(resources.getString(R.string.add_member_title));
                    }
                }else
                    settingsHolder.text.setText(resources.getString(settings.getName()));

                break;

            }


            case VIEW_TYPE_CART_DETAIL_FOOTER:{

                final FooterHoloder footerHoloder = (FooterHoloder) parentHolder;
                break;
            }

             /* case VIEW_TYPE_STAGGERED_FOOTER:{

                final FooterHoloder footerHoloder = (FooterHoloder) parentHolder;
                break;
            }
*/

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

            case VIEW_TYPE_MEMBER_CHECKING:{
                final MemberHolder memberHolder = (MemberHolder) parentHolder;
                memberHolder.textView.setText(resources.getString(R.string.add_member_title));
                memberHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            memberDialogShow(memberHolder.aSwitch);
                        }
                    }
                });


                break;
            }

            case VIEW_TYPE_PRODUCT_FLASH_SALE :{
                int flashPercentProduct = 0;

                final FlashSalesHolder flashProductHolder = (FlashSalesHolder) parentHolder;
                final Product productFlash1 = (Product) universalList.get(position);

                int barTextCount8 = 0 ;

                if (productFlash1.getFlashSalesArrayList().size() > 0){

                    final Flash_Sales flashSaleObject = productFlash1.getFlashSalesArrayList().get(0);

                    if (productFlash1.getPoint_usage() == 0){
                        flashProductHolder.imgGetPoint.setImageDrawable(activity.getResources().getDrawable(R.drawable.info));
                    }else{
                        flashProductHolder.imgGetPoint.setImageDrawable(activity.getResources().getDrawable(R.drawable.info));
                    }

                    flashProductHolder.imgGetPoint.setVisibility(View.VISIBLE);


                    CountDownTimer countDownTimerProduct ;

                    if (flashSaleObject.getReserved_quantity() > flashSaleObject.getAvailable_quantity())
                        flashSaleObject.setReserved_quantity(flashSaleObject.getAvailable_quantity());


                    ViewGroup.LayoutParams layPrarams8 = flashProductHolder.barFull.getLayoutParams();
                    int percentWidth8  = (int)(layPrarams8.width / flashSaleObject.getAvailable_quantity());

                    int percent8 = (int) ((100 * flashSaleObject.getReserved_quantity()) / flashSaleObject.getAvailable_quantity());

                    barTextCount8 = flashSaleObject.getAvailable_quantity() - flashSaleObject.getReserved_quantity();

                    flashProductHolder.barText.setText( flashSaleObject.getReserved_quantity() + " sold");
                    flashProductHolder.txtAvailable.setText(String.format(resources.getString(R.string.flash_sale_avail), barTextCount8 + ""));



                    if (flashSaleObject.getReserved_quantity() == 0) {
                        flashProductHolder.barFill.setVisibility(View.GONE);
                        flashProductHolder.barText.setText(flashSaleObject.getAvailable_quantity() +  " available");
                    }
                    else if (percent8 <= 20){
                        flashProductHolder.barFill.setVisibility(View.VISIBLE);
                        percentWidth8 = (int)(layPrarams8.width  * 20 /100);
                    }
                    else {
                        percentWidth8 = layPrarams8.width * percent8 / 100;
                        flashProductHolder.barFill.setVisibility(View.VISIBLE);
                    }


                    ViewGroup.LayoutParams param8 = flashProductHolder.barFill.getLayoutParams();
                    param8.width = percentWidth8;
                    flashProductHolder.barFill.setLayoutParams(param8);

                    flashProductHolder.bg_hide.setVisibility(View.VISIBLE);


                    flashProductHolder.title.setText(productFlash1.getTitle());

                    flashProductHolder.price.setText(formatter.format(productFlash1.getPrice()) +" "+activity.getResources().getString(R.string.currency));


                    if (productFlash1.getPreviewImage() != null && !(productFlash1.getPreviewImage().equals(""))) {
                        flashProductHolder.imageView.setImageUrl(productFlash1.getPreviewImage(), AppController.getInstance().getImageLoader());

                    }






                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                    Date futureDate = null;
                    try {
                        futureDate = df.parse(flashSaleObject.getEnd_date());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                    }

                    flashPercentProduct = flashSaleObject.getDiscount();
                    if(flashPercentProduct != 0 ) {

                        flashProductHolder.price_strike.setVisibility(View.VISIBLE);


                        flashProductHolder.price_strike.setText(formatter.format(productFlash1.getPrice()) +" "+activity.getResources().getString(R.string.currency));
                        flashProductHolder.price_strike.setPaintFlags(flashProductHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flashProductHolder.price.setText(formatter.format(productFlash1.getPrice() - (productFlash1.getPrice() * flashPercentProduct) / 100 )+
                                " "+resources.getString(R.string.currency));

                        flashProductHolder.txtMark.setText( "-" + flashSaleObject.getDiscount() + "%");
                        flashProductHolder.txtMark.setVisibility(View.VISIBLE);
                    }else{
                        flashProductHolder.member_discount.setVisibility(View.GONE);
                        flashProductHolder.price_strike.setVisibility(View.GONE);
                        flashProductHolder.price_strike.setVisibility(View.GONE);
                        flashProductHolder.txtMark.setVisibility(View.GONE);
                    }

                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {

                        final long[] diff = {
                                futureDate.getTime()
                                        - currentDate.getTime()};



                        countDownTimerProduct = this.countDownMap.get(flashProductHolder.txtDay.hashCode());
                        if (countDownTimerProduct == null){
                            countDownTimerProduct = new CountDownTimer(diff[0], 1000) {
                                public void onTick(long millisUntilFinished) {

                                    Date currentDate = new Date();
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                                    // Please here set your event date//YYYY-MM-DD
                                    Date futureDate = null;
                                    try {
                                        futureDate = df.parse(flashSaleObject.getEnd_date());
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
                                        flashProductHolder.linearLayout1.setVisibility(View.GONE);
                                    else
                                        flashProductHolder.linearLayout1.setVisibility(View.VISIBLE);

                                    flashProductHolder.txtDay.setText("" + String.format("%02d", days));
                                    flashProductHolder.txtHour.setText("" + String.format("%02d", hours));
                                    flashProductHolder.txtMinute.setText(""
                                            + String.format("%02d", minutes));
                                    flashProductHolder.txtSecond.setText(""
                                            + String.format("%02d", seconds));


                                }

                                public void onFinish() {
                                    flashProductHolder.txtDay.setText("00");
                                    flashProductHolder.txtHour.setText("00" );
                                    flashProductHolder.txtMinute.setText("00" );
                                    flashProductHolder.txtSecond.setText("00" );

                                    flashProductHolder.linearLayout1.setVisibility(View.VISIBLE);
                                }
                            }.start();

                            countDownMap.put(flashProductHolder.txtDay.hashCode(), countDownTimerProduct);
                        }

                    }
                    else{
                        flashProductHolder.txtDay.setText("00" );
                        flashProductHolder.txtHour.setText("00" );
                        flashProductHolder.txtMinute.setText("00" );
                        flashProductHolder.txtSecond.setText("00" );

                        flashProductHolder.linearLayout1.setVisibility(View.VISIBLE);
                    }

                    flashProductHolder.imgGetPoint.setOnClickListener(new View.OnClickListener() {
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

                            if (productFlash1.getMember_discount() == 1){
                                str += "- " + resources.getString(R.string.member_discount)
                                        + " " + prefs.getIntPreferences(SP_MEMBER_PERCENTAGE) + "% " + "\n";
                            }
                            else{
                                str +=  resources.getString(R.string.memeber_product_cannot) ;
                            }

                            if (productFlash1.getFlashSalesArrayList().size() > 0){
                                str += "- " + resources.getString(R.string.flash_sale_discount)
                                        + " " + productFlash1.getFlashSalesArrayList().get(0).getDiscount() + "% " + "\n";
                            }

                            if (productFlash1.getPoint_usage() == 0){
                                //str +=  String.format(resources.getString(R.string.point_product_can) , String.valueOf( prefs.getIntPreferences(SP_POINT_PERCENTAGE) ));
                                str +=   resources.getString(R.string.point_product_can);
                            }else{
                                str +=  resources.getString(R.string.point_product_cannot) ;
                            }

                            text.setText(str);

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("product_id", String.valueOf(productFlash1.getId()));
                                FlurryAgent.logEvent("Click Info Tag", mix);
                            } catch (Exception e) {
                            }
                            mBottomSheetDialog.show();
                        }
                    });



                    if (barTextCount8 == 0)
                    {
                        // 0 - 255 ( Integer Opacity ) => view.getBackground().setAlpha(45);
                        // 0 - 1 f ( Float Opacity )  => view.setAlpha(0.5f);

                        flashProductHolder.recommended.setVisibility(View.VISIBLE);
                        flashProductHolder.recommended.setText("Sold Out");
                        flashProductHolder.text_layout.setEnabled(false);
                        flashProductHolder.imageView.setEnabled(false);

                        flashProductHolder.layout.setAlpha(0.5f);


                    }else{

                        flashProductHolder.recommended.setVisibility(View.GONE);
                        flashProductHolder.text_layout.setEnabled(true);
                        flashProductHolder.imageView.setEnabled(true);
                        flashProductHolder.layout.setAlpha(1.0f);


                    }


                    flashProductHolder.imageView.setOnClickListener(new UniversalAdapter.OnClickProduct(activity, productFlash1));
                    flashProductHolder.text_layout.setOnClickListener(new UniversalAdapter.OnClickProduct(activity, productFlash1));


                }




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
                                if(stockSummeries.getQuantity() > 0){
                                    checkProductWarning1 = 0;

                                }

                            }
                        }
                    }*/
                    checkProductWarning1 = 1;
                }

                if (checkProductWarning1 == 1){
                    //catDetailHolder.product_warning.setVisibility(View.GONE);
                    // catDetailHolder.imgCar.setVisibility(View.GONE);
                    catDetailHolder.product_warning.setText(resources.getString(R.string.delivery_take_time));
                    catDetailHolder.imgCar.setImageDrawable(activity.getResources().getDrawable(R.drawable.car_one));

                }else{
                    //catDetailHolder.product_warning.setVisibility(View.VISIBLE);
                    //catDetailHolder.imgCar.setVisibility(View.VISIBLE);
                    catDetailHolder.imgCar.setImageDrawable(activity.getResources().getDrawable(R.drawable.car_option));
                    catDetailHolder.product_warning.setText(resources.getString(R.string.delivery_take_time_option));
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
                           // str += "- " +  String.format(resources.getString(R.string.point_product_can) , String.valueOf( prefs.getIntPreferences(SP_POINT_PERCENTAGE) ));
                            str +=   "- " + resources.getString(R.string.point_product_can);
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



                catDetailHolder.imageView.setOnClickListener(new UniversalAdapter.OnClickProduct(activity, detailProduct));
                catDetailHolder.text_layout.setOnClickListener(new UniversalAdapter.OnClickProduct(activity, detailProduct));




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

                catDetailHolder.addtocart.setOnClickListener(new UniversalAdapter.CategoryAddtoCartClickListener(detailProduct,  activity,
                        1, (detailProduct.getPrice() - cat_total_discount)));


                break;

            }


            case VIEW_TYPE_CATEGORY_DETAIL_WISHLIST:{

                int percentage1 = 0;
                final ProductCategoryDetailItem wishHolder = (ProductCategoryDetailItem) parentHolder;
                final Product wishProduct = (Product) universalList.get(position);
                wishHolder.title.setText(wishProduct.getTitle());
                wishHolder.price.setText(formatter.format(wishProduct.getPrice()) +" "+activity.getResources().getString(R.string.currency));

                if (wishProduct.getPreviewImage() != null && !(wishProduct.getPreviewImage().equals(""))) {
                    wishHolder.imageView.setImageUrl(wishProduct.getPreviewImage(), AppController.getInstance().getImageLoader());
                }
                if(wishProduct.getDiscounts().size() > 0){
                    String str = "";
                    for(int i = 1; i <= wishProduct.getDiscounts().size(); i++){
                        Discount dis = (Discount) wishProduct.getDiscounts().get(i-1);
                        if(dis.getMember_only() == 1){
                            if(prefs.getIntPreferences(SP_VIP_ID) != 0){
                                if(dis.getDiscountType().equals(DISCOUNT_PERCENTAGE)){
                                    if(dis.getPercentage() > percentage1)
                                        percentage1 = dis.getPercentage();
                                }else if(dis.getDiscountType().equals(DISCOUNT_GIFT)){
                                    str = str.concat(dis.getCampaign_info());
                                }
                            }
                        }else{
                            if(dis.getDiscountType().equals(DISCOUNT_PERCENTAGE)){
                                if(dis.getPercentage() > percentage1)
                                    percentage1 = dis.getPercentage();
                            }else if(dis.getDiscountType().equals(DISCOUNT_GIFT)){
                                str = str.concat(dis.getCampaign_info());
                            }
                        }

                    }


                    wishHolder.discounts.setText(str);
                }else{

                    wishHolder.discounts.setVisibility(View.GONE);
                }
                if(percentage1 <  prefs.getIntPreferences(SP_MEMBER_PERCENTAGE))
                    percentage1 =  prefs.getIntPreferences(SP_MEMBER_PERCENTAGE);

                if(percentage1 != 0) {
                    wishHolder.member_discount.setVisibility(View.VISIBLE);
                    wishHolder.member_discount.setText( percentage1 + "%");
                }


                if(databaseAdapter.idContainWishlist(wishProduct.getId()))
                    wishHolder.wishlist.setImageResource(R.drawable.wishlist_clicked);
                else
                    wishHolder.wishlist.setImageResource(R.drawable.wishlist);


                wishHolder.all.setOnClickListener(new UniversalAdapter.OnClickProduct(activity, wishProduct));

                break;


            }

            case VIEW_TYPE_ORDER_DETAIL_HEADER:{
                OrderDetailHeaderHolder header = (OrderDetailHeaderHolder) parentHolder;
                Order123 orderHeader = (Order123) universalList.get(position);

                header.date.setText(activity.getResources().getString(R.string.order_date)+orderHeader.getDate());
                header.totalPrice.setText(activity.getResources().getString(R.string.order_totalPrice)+ formatter.format(orderHeader.getTotalPrice())
                        +""+activity.getResources().getString(R.string.currency));
                header.deliveryPrice.setText(activity.getResources().getString(R.string.cart_detail_delivery_fees)
                        + formatter.format(orderHeader.getDeliveryPrice())+resources.getString(R.string.currency));
                header.name.setText(activity.getResources().getString(R.string.order_name)+orderHeader.getName());
                header.phoneNo.setText(activity.getResources().getString(R.string.order_phone_no)+orderHeader.getPhoneNo());
                header.address.setText(activity.getResources().getString(R.string.order_address)+orderHeader.getAddress());
                break;
            }


            case VIEW_TYPE_ORDER_DETAIL:{
                CategoryDetailHolder order = (CategoryDetailHolder) parentHolder;
                final Product orderItem = (Product) universalList.get(position);

                if( orderItem.getPreviewImage() != null && !(orderItem.getPreviewImage().equals(""))) {
                    order.imageView.setImageUrl(orderItem.getPreviewImage(), AppController.getInstance().getImageLoader());
                    order.imageView.getLayoutParams().height = display.getWidth() *  2/ 6;
                    order.imageView.getLayoutParams().width  = display.getWidth()* 2 / 6 ;
                    order.imageView.setVisibility(View.VISIBLE);
                }else{
                    order.imageView.setVisibility(View.GONE);
                }



                order.title.setText(orderItem.getTitle());
                if (orderItem.getPrice() == 0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        order.price.setText(Html.fromHtml( formatter.format(orderItem.getPrice())+" "+ resources.getString(R.string.currency) +"   x   "+orderItem.getLikes()+" " +
                                "<font color=#ff0000>" + "(Gift From Kyarlay)"
                                + "</font>" , Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        order.price.setText(Html.fromHtml(formatter.format(orderItem.getPrice())+" "+ resources.getString(R.string.currency) +"   x   "+orderItem.getLikes()+" " +
                                "<font color=#ff0000>" + "(Gift From Kyarlay)"
                                + "</font>"));
                    }
                }else{
                    order.price.setText(formatter.format(orderItem.getPrice())+" "+ resources.getString(R.string.currency) +"   x   "+orderItem.getLikes()+"");
                }

                order.priceStrike.setVisibility(View.GONE);

                order.linearMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source","brand");
                            mix.put("source_id", String.valueOf(orderItem.getId()));
                            FlurryAgent.logEvent("View Old Order Product", mix);
                        } catch (Exception e) {}

                        getProduct(String.valueOf(orderItem.getId()));
                    }
                });

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

            case VIEW_TYPE_CAMPAIGN_MORE:{
                CampianHolder camHolder1 = (CampianHolder) parentHolder;
                final Campaign campaign1       = (Campaign) universalList.get(position);
                if(campaign1.getTitle().trim().length() > 0){
                    camHolder1.title.setVisibility(View.VISIBLE);
                    camHolder1.title.setText(campaign1.getTitle());
                }
                if(campaign1.getDimen() > 0){
                    camHolder1.image.setVisibility(View.VISIBLE);
                    camHolder1.image.getLayoutParams().width = display.getWidth();
                    camHolder1.image.getLayoutParams().height = (campaign1.getDimen() *  (display.getWidth()  )) / 100;
                    camHolder1.image.setImageUrl(campaign1.getUrl(), imageLoader);
                }else {
                    camHolder1.image.setVisibility(View.GONE);
                }
                if(campaign1.getStart() != null && campaign1.getStart().trim().length() > 0){
                    camHolder1.time.setVisibility(View.VISIBLE);
                    camHolder1.time.setText(campaign1.getStart()+" - "+campaign1.getEnd());
                }else{
                    camHolder1.time.setVisibility(View.GONE);
                }
                camHolder1.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("campaign_id", String.valueOf(campaign1.getId()));
                            FlurryAgent.logEvent("Click Discount Campaign", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, CampainDetailActivity.class);
                        intent.putExtra("id",campaign1.getId());
                        intent.putExtra("name",campaign1.getTitle());
                        activity.startActivity(intent);
                    }
                });

                break;


            }

            case VIEW_TYPE_COLLECTIONS_MORE:{

                CampianHolder collectionMoreHolder = (CampianHolder) parentHolder;
                final Campaign collectionMore       = (Campaign) universalList.get(position);

                collectionMoreHolder.title.setVisibility(View.GONE);
                collectionMoreHolder.time.setVisibility(View.GONE);

                if(collectionMore.getDimen() > 0){
                    collectionMoreHolder.image.setVisibility(View.VISIBLE);
                    collectionMoreHolder.image.getLayoutParams().width = display.getWidth();
                    collectionMoreHolder.image.getLayoutParams().height = (collectionMore.getDimen() *  (display.getWidth()  )) / 100;
                    collectionMoreHolder.image.setImageUrl(collectionMore.getUrl(), imageLoader);
                }else {
                    collectionMoreHolder.image.setVisibility(View.GONE);
                }

                collectionMoreHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("collection_id", String.valueOf(collectionMore.getId()));
                            FlurryAgent.logEvent("Click Collection List", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, CollectionDetailActivity.class);
                        intent.putExtra("id",collectionMore.getId());
                        intent.putExtra("name",collectionMore.getTitle());
                        activity.startActivity(intent);
                    }
                });

                break;

            }

            case VIEW_TYPE_BANNER_PHOTO:{
                BannerPhotoHolder bpHolder = (BannerPhotoHolder) parentHolder;
                final MainObject object = (MainObject) universalList.get(position);
                if(Integer.parseInt(object.getEnd_at()) > 0){
                    bpHolder.imageView.setImageUrl(object.getTitle(), imageLoader);
                    bpHolder.imageView.getLayoutParams().height = (Integer.parseInt(object.getEnd_at()) * display.getWidth()) / 100;
                    bpHolder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "promotion");
                                FlurryAgent.logEvent("Click Main Category", mix);
                            } catch (Exception e) {
                            }

                            Intent intent = new Intent(activity, CampainActivity.class);
                            intent.putExtra("api", object.getUrl());
                            activity.startActivity(intent);
                        }
                    });
                }

                break;

            }

            case VIEW_TYPE_NOTIFICATION:{
                final Reading noti = (Reading) universalList.get(position);
                final NotificationHolder notiHolder = (NotificationHolder) parentHolder;
                notiHolder.title.setText(noti.getBody());
                notiHolder.read.setText(resources.getString(R.string.comment_text));
                notiHolder.time.setText(noti.getSort_by());


                notiHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                                prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){

                            notiHolder.showNoti.setVisibility(View.GONE);
                            databaseAdapter.deleteNotification(noti.getId());
                            Intent intent = new Intent(activity, ReadingCommentDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("page_url", noti.getPage_img_url());
                            bundle.putInt("id", noti.getId());
                            bundle.putString("title", noti.getBody());
                            bundle.putInt("like_count", noti.getLikes());
                            bundle.putInt("comment_count", noti.getComment_coount());
                            bundle.putString("noti_post_type", noti.getUrl());
                            bundle.putInt("show_header", 1);

                            if(noti.getUrl() != null && noti.getUrl().equals("video"))
                                bundle.putString("post_type", "video_program");


                            intent.putExtras(bundle);
                            activity.startActivity(intent);
                        }else{
                            Intent intent   = new Intent(activity, ActivityLogin.class);
                            activity.startActivity(intent);
                        }
                    }
                });


                if(databaseAdapter.getNotification(noti.getId())){
                    notiHolder.showNoti.setVisibility(View.VISIBLE);
                    notiHolder.showNoti.setVisibility(View.VISIBLE);
                    notiHolder.showNoti.setStrokeWidth(1);
                    notiHolder.showNoti.setStrokeColor("#000000");
                    notiHolder.showNoti.setSolidColor("#ff0000");
                }else{
                    notiHolder.showNoti.setVisibility(View.GONE);
                }

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

                    //////////////////////////////////////delete
                    /*if (prefs.getIntPreferences(SP_ORDER_COUNT) == 0)
                        discountTitleHolder.layout_first_time.setVisibility(View.VISIBLE);
                    else
                        discountTitleHolder.layout_first_time.setVisibility(View.GONE);*/


                    Log.e(TAG, "onBindViewHolder: **-------------" );

                    discountTitleHolder.layout_first_time.setVisibility(View.GONE);

                    if (prefs.getIntPreferences(SP_ORDER_COUNT) == 0 && prefs.getIntPreferences(SP_TOWNSHIP_ID)!=0) {

                        int checkFree = 0;

                        for (int i=0; i< delList.size(); i++){
                            if(delList.get(i).getId() == prefs.getIntPreferences(SP_TOWNSHIP_ID)) {
                                checkFree = delList.get(i).getFirst_time_free();

                            }
                        }

                        if (checkFree != 0){
                            discountTitleHolder.layout_first_time.setVisibility(View.VISIBLE);
                        }

                    }

                    ////////////////////////////////delete





                    discountTitleHolder.txtFirstTime.setText(resources.getString(R.string.free_delivery));
                }

                break;

            }


            case VIEW_TYPE_BACKGROUND_TITLE:{


                FooterHoloder discountTitleHolder1 = (FooterHoloder) parentHolder;
                Product title_product1   = (Product) universalList.get(position);
                discountTitleHolder1.space.setText(title_product1.getTitle());
                discountTitleHolder1.space.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                discountTitleHolder1.space.setPadding(20, 20, 20,  20);
                discountTitleHolder1.space.setBackgroundResource(R.color.background);
                discountTitleHolder1.space.setTextColor(activity.getResources().getColor(R.color.text_title));
                discountTitleHolder1.space.setGravity(Gravity.CENTER);

                discountTitleHolder1.titleBackground.setVisibility(View.VISIBLE);
                discountTitleHolder1.space.setVisibility(View.VISIBLE);


                break;

            }

            case VIEW_TYPE_VIDEO_POST:{

                final VideoPostHolder videoPostHolder = (VideoPostHolder) parentHolder;
                final Videos videos = (Videos) universalList.get(position);
                videoPostHolder.title.setText(videos.getTitle());

                if(videos.getNowPlaying() == 1) {
                    videoPostHolder.playing.setVisibility(View.VISIBLE);
                } else {
                    videoPostHolder.playing.setVisibility(View.GONE);
                }

                String stringVideo = videos.getPreview_url().trim();
                stringVideo = stringVideo.replace(" " , "");


                videoPostHolder.imageView.setImageUrl(stringVideo, imageLoader);




                break;

            }


            case VIEW_TYPE_CATEGORY_GRID:{


                MainGridHolder holder2 = (MainGridHolder) parentHolder;
                ArrayList<UniversalPost> mainGrids21 = new ArrayList<>();
                CategoryGrid obj21 = (CategoryGrid) universalList.get(position);
                holder2.title.setVisibility(View.GONE);

                moreItems = obj21.getCategoryMainList();

                gridAdapter = new UniversalAdapter(activity, mainGrids21);
                layoutManager = new GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false);

                if (holder2.recyclerView != null) {
                    holder2.recyclerView.setLayoutManager(layoutManager);
                    holder2.recyclerView.setAdapter(gridAdapter);
                }
                if (moreItems.size() > 0 ){
                    for(int i = 0 ; i < moreItems.size() ; i++){
                        CategoryMain uni = moreItems.get(i);
                        uni.setPostType(CATEGORY_GRID_ITEM);
                        mainGrids21.add(uni);
                    }
                }


                holder2.recyclerView.getItemAnimator().setChangeDuration(0);
                holder2.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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


            case VIEW_TYPE_SUB_CATEGORY_GRID:{

                NewArrivalMainHolder subHolder2 = (NewArrivalMainHolder) parentHolder;
                ArrayList<UniversalPost> mainGrids115 = new ArrayList<>();
                CategoryGrid obj115 = (CategoryGrid) universalList.get(position);

                subHolder2.txtNewArrival.setText(resources.getString(R.string.choose_category));

                subHolder2.linearSeeMore.setVisibility(View.GONE);
                subHolder2.txtContinue.setText(resources.getString(R.string.continue_watch));


                List<CategoryMain> moreItems1 = obj115.getCategoryMainList();

                if (obj115.getShowMore() == 1){
                    for(int i = 0 ; i < moreItems1.size() ; i++){
                        CategoryMain uni = moreItems1.get(i);
                        uni.setPostType(SUB_CATEGORY_GRID_ITEM);
                        mainGrids115.add(uni);

                    }
                }
                else{

                    if (moreItems1.size() < 8  ){
                        for(int i = 0 ; i < moreItems1.size() ; i++){
                            CategoryMain uni = moreItems1.get(i);
                            uni.setPostType(SUB_CATEGORY_GRID_ITEM);
                            mainGrids115.add(uni);

                        }

                        subHolder2.layoutContinue.setVisibility(View.GONE);


                    }
                    else if(moreItems1.size() > 8  ) {
                        for(int i = 0 ; i < 8 ; i++){
                            CategoryMain uni = moreItems1.get(i);
                            uni.setPostType(SUB_CATEGORY_GRID_ITEM);
                            mainGrids115.add(uni);

                        }

                        subHolder2.layoutContinue.setVisibility(View.VISIBLE);
                    }
                }



                gridAdapter = new UniversalAdapter(activity, mainGrids115);
                layoutManager = new GridLayoutManager(activity, 4, GridLayoutManager.VERTICAL, false);
                if (subHolder2.recyclerView != null) {
                    subHolder2.recyclerView.setLayoutManager(layoutManager);
                    subHolder2.recyclerView.setAdapter(gridAdapter);
                    ((LinearLayout.LayoutParams) subHolder2.recyclerView.getLayoutParams()).setMargins(10, 10, 10, 10);
                    // gridAdapter.notifyDataSetChanged();
                }

                subHolder2.layoutContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefs.saveBooleanPreference(SP_SHOW_MORE, true);
                        obj115.setShowMore(1);
                        notifyDataSetChanged();
                        subHolder2.layoutContinue.setVisibility(View.GONE);
                    }
                });



                subHolder2.recyclerView.getItemAnimator().setChangeDuration(0);
                subHolder2.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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


            case VIEW_TYPE_MAIN_GRID_CATEOGRY_ITEM:{

                CustomMainHolder superGridItemHolder1 = (CustomMainHolder) parentHolder;
                final CategoryMain gridSuperMain1 = (CategoryMain) universalList.get(position);
                superGridItemHolder1.title.setText(gridSuperMain1.getTitle());


                if (gridSuperMain1.getImage() != null && gridSuperMain1.getImage().trim().length() > 0){
                    superGridItemHolder1.imageView.setImageUrl(gridSuperMain1.getImage_big(), imageLoader);
                }
                else {
                    superGridItemHolder1.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }

                superGridItemHolder1.imageView.setVisibility(View.VISIBLE);


                if (gridSuperMain1.getTextBackgroundColor() != null && gridSuperMain1.getTextBackgroundColor().trim().length() > 0){
                    int text_bg_color  = Integer.parseInt(gridSuperMain1.getTextBackgroundColor(), 16)+0xFF000000;
                    superGridItemHolder1.linearBackground.setBackgroundColor(text_bg_color);
                }


                if (gridSuperMain1.getTextColor() != null && gridSuperMain1.getTextColor().trim().length() > 0){
                    int text_color  = Integer.parseInt(gridSuperMain1.getTextColor(), 16)+0xFF000000;
                    superGridItemHolder1.title.setTextColor(text_color);
                }



                if (gridSuperMain1.getIconBackgroundColor() != null && gridSuperMain1.getIconBackgroundColor().trim().length() > 0) {
                    int icon_color = Integer.parseInt(gridSuperMain1.getIconBackgroundColor(), 16) + 0xFF000000;
                    superGridItemHolder1.title.setBackgroundColor(icon_color);
                }



                if (gridSuperMain1.getCategory_type().equals(CUSTOM_GRID_ITEM)){





                    superGridItemHolder1.imageView.getLayoutParams().width  =  (display.getWidth() / 6) ;
                    superGridItemHolder1.imageView.getLayoutParams().height =  display.getWidth() / 6;
                    superGridItemHolder1.linearMain.getLayoutParams().width  =  (int)( (display.getWidth() * 1 ) / 3.5) ;
                    superGridItemHolder1.linearMain.getLayoutParams().height  =  ((int) (display.getWidth() * 2) ) / 5;

                    superGridItemHolder1.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                    superGridItemHolder1.title.setLines(2);
                    superGridItemHolder1.title.setPadding(5,5,5,5);

                    //((LinearLayout.LayoutParams) superGridItemHolder1.linearBackground.getLayoutParams()).setMargins(20, 20, 20, 20);
                }
                else if (gridSuperMain1.getCategory_type().equals(CUSTOM_SCROLL_ITEM)){
                  /*  superGridItemHolder1.imageView.getLayoutParams().width  =  (display.getWidth() / 6) ;
                    superGridItemHolder1.imageView.getLayoutParams().height =  display.getWidth() / 6;
                    superGridItemHolder1.linearBackground.getLayoutParams().width  =  (display.getWidth()  *  1 ) / 5 ;
                    superGridItemHolder1.linearBackground.getLayoutParams().height  =  ((int) (display.getWidth() * 1.9) ) / 5;
                    superGridItemHolder1. title.getLayoutParams().width  = (display.getWidth() / 5) ;
                    */
                    superGridItemHolder1.imageView.getLayoutParams().width  =  (display.getWidth() / 6) ;
                    superGridItemHolder1.imageView.getLayoutParams().height =  display.getWidth() / 6;
                    superGridItemHolder1.linearMain.getLayoutParams().width  =  ( (int) (display.getWidth() * 1 ) / 3) ;
                    superGridItemHolder1.linearMain.getLayoutParams().height  =  ((int) (display.getWidth() * 2) ) / 5;
                    superGridItemHolder1.title.setPadding(5,5,5,5);

                    superGridItemHolder1.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                    superGridItemHolder1.title.setLines(2);

                    // ((LinearLayout.LayoutParams) superGridItemHolder1.linearBackground.getLayoutParams()).setMargins(10, 10, 10, 10);



                }


                superGridItemHolder1.linearMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        prefs.saveStringPreferences(SP_BRAND_TAG, gridSuperMain1.getTag());
                        prefs.saveIntPerferences(SP_BRAND_ID, gridSuperMain1.getId());

                        MainItem grids = new MainItem();
                        grids.setId(gridSuperMain1.getId());
                        grids.setTitle(gridSuperMain1.getTitle());
                        grids.setPost_type(gridSuperMain1.getTag());
                        grids.setPreview_url(gridSuperMain1.getImage());


                        Intent intent = new Intent(activity, CategoryActivity.class);
                        //  Intent intent = new Intent(activity, CategoryActivity.class);
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



            case VIEW_TYPE_TOOL_SUPER_GRID_ITEM:{
                CategoryGridItemHolder superGridItemHolder2 = (CategoryGridItemHolder) parentHolder;
                final CategoryMain gridSuperMain2 = (CategoryMain) universalList.get(position);
                superGridItemHolder2.title.setText(gridSuperMain2.getTitle());
                if (gridSuperMain2.getImage() != null && gridSuperMain2.getImage().trim().length() > 0){
                    superGridItemHolder2.imageView.setImageUrl(gridSuperMain2.getImage(), imageLoader);
                }
                else {
                    superGridItemHolder2.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }


                superGridItemHolder2.imageView.getLayoutParams().width  =  (display.getWidth() / 4) ;
                superGridItemHolder2.imageView.getLayoutParams().height =  display.getWidth() / 4;

                superGridItemHolder2.layout.getLayoutParams().width  =  (display.getWidth()  *  2 ) / 7 ;
                superGridItemHolder2.layout.getLayoutParams().height  =  (display.getWidth() * 3 ) / 7;
                superGridItemHolder2. title.getLayoutParams().width  = (display.getWidth()  *  2 ) / 7 ;


                superGridItemHolder2.title.setPadding(2, 5, 2, 5);

                superGridItemHolder2.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                superGridItemHolder2.title.setLines(2);

                superGridItemHolder2.imageView.setVisibility(View.VISIBLE);


                superGridItemHolder2.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                superGridItemHolder2.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });


                break;

            }


            case VIEW_TYPE_SUPER_CATEGORY_ITEM:{
                CategoryGridItemHolder superGridItemHolder = (CategoryGridItemHolder) parentHolder;
                final CategoryMain gridSuperMain = (CategoryMain) universalList.get(position);
                superGridItemHolder.title.setText(gridSuperMain.getTitle());
                if (gridSuperMain.getImage() != null && gridSuperMain.getImage().trim().length() > 0){
                    superGridItemHolder.imageView.setImageUrl(gridSuperMain.getImage(), imageLoader);
                    Log.e(TAG, "onBindViewHolder: ------ "  + gridSuperMain.getImage() );
                }
                else {
                    superGridItemHolder.imageView.setImageUrl("https://res.cloudinary.com/tech-myanmar/image/upload/c_scale,h_128,w_128/so4guvfst9w7eotbrqnr.png" ,imageLoader);
                    //superGridItemHolder.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                    //Log.e(TAG, "onBindViewHolder: ------ default "  + gridSuperMain.getImage() );
                }

                superGridItemHolder.imageView.getLayoutParams().width  =  (display.getWidth() / 6) ;
                superGridItemHolder.imageView.getLayoutParams().height =  display.getWidth() / 6;

                superGridItemHolder.layout.getLayoutParams().width  =  (display.getWidth() / 5) ;

                superGridItemHolder.title.setLines(2);

                superGridItemHolder.imageView.setVisibility(View.VISIBLE);

                superGridItemHolder.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                superGridItemHolder.layout.setOnClickListener(new View.OnClickListener() {
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

            case VIEW_TYPE_CATEGORY_GRID_ITEM:{
                CategoryGridItemHolder holder5 = (CategoryGridItemHolder) parentHolder;
                final CategoryMain grid1 = (CategoryMain) universalList.get(position);
                holder5.title.setText(grid1.getTitle());
                if (grid1.getImage() != null && grid1.getImage().trim().length() > 0){
                    holder5.imageView.setImageUrl(grid1.getImage(), imageLoader);
                }
                else {
                    holder5.imageView.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }
                holder5.imageView.setVisibility(View.VISIBLE);

                holder5.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                holder5.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                       /* Intent intent = new Intent(activity, BrandClickActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("brandall", false);
                        bundle.putString("title", grid1.getTitle());
                        bundle.putInt("id", grid1.getId());
                        intent.putExtras(bundle);
                        activity.startActivity(intent);*/


                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("main_category_id", String.valueOf(prefs.getIntPreferences(BRAND_ID)));
                            mix.put("sub_category_id", String.valueOf(grid1.getId()));
                            mix.put("source", "product_list");
                            FlurryAgent.logEvent("Click Product Subcategory", mix);
                        } catch (Exception e) {
                        }
                        Intent intent = new Intent(activity, ProductListClickActivity.class);
                        intent.putExtra("title",grid1.getTitle());
                        intent.putExtra("url", constantBrand_Detail+grid1.getId()+"&brand_id="+prefs.getIntPreferences(BRAND_ID)+"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE));
                        activity.startActivity(intent);

                    }
                });



                break;

            }


            case VIEW_TYPE_SUB_CATEGORY_GRID_ITEM:{
                HotItemHolder subHolder5 = (HotItemHolder) parentHolder;
                final CategoryMain gridSub1 = (CategoryMain) universalList.get(position);
                subHolder5.title.setText(gridSub1.getTitle());
                try{
                    if (gridSub1.getImage() != null && gridSub1.getImage().trim().length() > 0){
                        subHolder5.img.setImageUrl(gridSub1.getImage(), imageLoader);
                    }
                    else {
                        subHolder5.img.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);

                    }
                }catch (Exception e){
                    Log.e(TAG, "onBindViewHolder: "  + e.getMessage() );
                    subHolder5.img.setDefaultImageResId(R.mipmap.ic_kyarlay_shape);
                }

                subHolder5.img.getLayoutParams().width  =  (display.getWidth() / 7) ;
                subHolder5.img.getLayoutParams().height =  display.getWidth() / 7;

                subHolder5.linear.getLayoutParams().width  = (int)  (display.getWidth() / 4.4) ;



                subHolder5.title.setTextSize(TypedValue.COMPLEX_UNIT_PX,activity.getResources().getDimensionPixelSize(R.dimen.small_text));
                subHolder5.title.setLines(2);

                if (gridSub1.isChoosen()){
                    subHolder5.title.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
                else{
                    subHolder5.title.setBackgroundColor(activity.getResources().getColor(R.color.background));
                }


                subHolder5.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: "  + gridSub1.getId() );
                    }
                });

                subHolder5.linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(activity.getLocalClassName().contains("CategoryActivity")) {

                            CategoryActivity pro = (CategoryActivity) activity;
                            pro.clickCategory(gridSub1.getId(),gridSub1.getTitle() );
                        }

/*
                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("main_category_id", String.valueOf(prefs.getIntPreferences(SP_BRAND_ID)));
                            mix.put("sub_category_id", String.valueOf(gridSub1.getId()));
                            mix.put("source", "product_list");
                            FlurryAgent.logEvent("Click Product Subcategory", mix);
                        } catch (Exception e) {
                        }

                        Intent intent = new Intent(activity, ProductListClickActivity.class);
                        intent.putExtra("title",gridSub1.getTitle());
                        intent.putExtra("url", constantBrand_Detail+gridSub1.getId()+"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE));
                        activity.startActivity(intent);*/
                    }
                });


                break;


            }



            default:
                break;

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

        zero.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 0));
        one.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 1));
        two.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 2));
        three.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 3));
        four.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 4));
        five.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 5));
        six.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 6));
        seven.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 7));
        eight.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 8));
        nine.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 9));
        ten.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 10));



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

    private void sendMessageToServer(final ProgressDialog progressDialog, int id, String toString) {

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("question",  toString);
            uploadMessage.put("product_id",  id);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "sendComment:  "  + e.getMessage() );
        }




        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantProductQuestions, uploadMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();


                        if(response.length() > 0){
                            try{
                                int question_id = response.getInt("question_id");
                                FirebaseMessaging.getInstance().subscribeToTopic("product_question"  + question_id);
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {

            /* *
             * Passing some request headers
             **/
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

    private void deleteCompetitionPhoto (int indexId, final int positionIndex, final Dialog dialogDelete) {


        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("photo_id",  indexId);


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "sendComment:  "  + e.getMessage() );
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                constantCompetitionDelete  , uploadMessage, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                dialogDelete.dismiss();
                try{
                    int returnSatatus =  response.getInt("status");

                    if(returnSatatus == 1){
                        universalList.remove(positionIndex);
                        prefs.saveBooleanPreference(COMPETITION_DELETE, true);

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
                Log.e(TAG, "onResponse: " + "https://www.kyarlay.com/api/shopping_carts/feedback"  + uploadOrder.toString());
                Log.e(TAG, "onResponse: ----------------------------- rating "  + response.toString() );
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
                    Log.e(TAG, "onErrorResponse: ************************************ rating errors  " + e.getMessage()  );
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ************************************ rating error  " + error.getMessage()  );

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


    private void makePurchaseGift(String pin, final Dialog dialog, int gObjId)
    {

        JSONObject uploadOrder = new JSONObject();
        try {
            uploadOrder.put("gift_id",    gObjId);
            uploadOrder.put("pin",         pin);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, String.format(constantClaimGift , prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID)), uploadOrder,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        dialog.dismiss();
                        try{
                            String text = response.getString("text");
                            ToastHelper.showToast(activity, text);


                            if (response.getInt("status") == 1){
                                prefs.saveIntPerferences(SP_CHANGE, 1);
                                if (prefs.isNetworkAvailable())
                                    getCustomerInfo();


                            }
                            else{
                                prefs.saveIntPerferences(SP_CHANGE, 0);
                            }
                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                ToastHelper.showToast(activity,resources.getString(R.string.search_error));

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


    private void sendProductDetailLike(int id, final ProductDetailsHolder holder){

        String url_end  = "/like";

        if(databaseAdapter.checkProductLiked(id)){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("product_id", String.valueOf(id));
                FlurryAgent.logEvent("Add To Product Wishlist", mix);
            } catch (Exception e) {
            }

            url_end = "/unlike";
            holder.imgWishList.setImageResource(R.drawable.ic_wishlist_black);
            databaseAdapter.insertProductLike(id, "unlike");
            prefs.saveIntPerferences(SP_PRODUCT_LIKED_COUNT, (prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT)-1));
        }else {
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("product" +
                        "_id", String.valueOf(id));
                FlurryAgent.logEvent("Remove From Product Wishlist", mix);
            } catch (Exception e) {
            }
            holder.imgWishList.setImageResource(R.drawable.wishlist_clicked);
            databaseAdapter.insertProductLike(id, "like");
            prefs.saveIntPerferences(SP_PRODUCT_LIKED_COUNT, (prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT)+1));
        }

        ProductActivity productActivity =  (ProductActivity) activity;
        productActivity.update_wishlist();



        String url  = "https://www.kyarlay.com/api/products/"+id+url_end;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


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



    public void memberDialogShow(final Switch aSwitch){
        try {
            Map<String, String> mix = new HashMap<String, String>();
            mix.put("source","shopping cart");
            FlurryAgent.logEvent("Verify Member", mix);
        } catch (Exception e) {
        }
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_member);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);
        dialog.setCanceledOnTouchOutside(false);

        CustomButton canceldialog = (CustomButton) dialog.findViewById(R.id.order_cancel_button);
        CustomButton  confirmdialog = (CustomButton) dialog.findViewById(R.id.order_yes_button);

        CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
        CustomTextView phonetext = (CustomTextView) dialog.findViewById(R.id.phone_text);

        final CustomEditText phone        = (CustomEditText) dialog.findViewById(R.id.order_phone_no_edittext);

        title.setPadding(10,10,10,10);

        title.setText(resources.getString(R.string.add_member_title));
        phonetext.setText(resources.getString(R.string.order_phone_no));

        canceldialog.setText(resources.getString(R.string.add_member_cancel));
        confirmdialog.setText(resources.getString(R.string.add_member_confirm));

        confirmdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.isNetworkAvailable()) {
                    if (phone.getText().toString().trim().length() > 0) {
                        memberCheckingDialog(phone.getText().toString(), dialog, aSwitch);
                    } else {

                        ToastHelper.showToast(activity, resources.getString(R.string.member_dialog_fill_toast));

                    }
                }else{
                    ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));

                }
            }

        });

        canceldialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSwitch.setChecked(false);
                dialog.dismiss();
            }
        });
        dialog.show();


    }
    private void memberCheckingDialog(final String number, final Dialog dialog, final Switch aSwitch)
    {
        try {
            Map<String, String> mix = new HashMap<String, String>();
            mix.put("source","shopping cart");
            mix.put("action","checking_member");
            FlurryAgent.logEvent("Verify Member", mix);
        } catch (Exception e) {
        }
        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMemberCheck+number, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {

                dialog.dismiss();
                try {
                    final int member_id                   = response.getInt("member_id");
                    final int statusResponse                = response.getInt("status");
                    String textResponse                     = response.getString("text");
                    String titleResponse                    = response.getString("text");
                    final int discountPercentageResponse    = response.getInt("discount_percentage");
                    final int freedeliveryResponse          = response.getInt("min_free_delivery");
                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_wishlist);

                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                    window.setAttributes(wlp);
                    dialog.setCanceledOnTouchOutside(false);

                    CustomTextView title  = (CustomTextView) dialog.findViewById(R.id.title);
                    CustomTextView text  = (CustomTextView) dialog.findViewById(R.id.text);

                    title.setText(titleResponse);
                    text.setText(textResponse);
                    CustomButton button = (CustomButton) dialog.findViewById(R.id.button);
                    button.setText(resources.getString(R.string.save_to_cart_error_button));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(statusResponse == 1 ){
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("source","shopping cart");
                                    mix.put("action","checking_member_confirm");
                                    FlurryAgent.logEvent("Verify Member", mix);
                                } catch (Exception e) {
                                }
                                try {
                                    prefs.saveIntPerferences(SP_MEMBER_ID, member_id);
                                }catch (Exception e){
                                    prefs.saveIntPerferences(SP_MEMBER_ID, 1500);
                                }
                                prefs.saveStringPreferences(SP_USER_PHONE, number);
                                prefs.saveIntPerferences(SP_MEMBER_PERCENTAGE, discountPercentageResponse);
                                // prefs.saveIntPerferences(SP_MEMBER_DELIVERY_AMOUNT, freedeliveryResponse);
                                dialog.dismiss();
                                activity.recreate();
                            }else{
                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("source","shopping cart");
                                    mix.put("action","checking_member_fail");
                                    FlurryAgent.logEvent("Verify Member", mix);
                                } catch (Exception e) {
                                }
                                aSwitch.setChecked(false);
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "onResponse: "  + e.getMessage() );
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                aSwitch.setChecked(false);
                dialog.dismiss();

                ToastHelper.showToast(activity, resources.getString(R.string.search_error));
            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "memberchecking");

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

    public void addToCartProduct(Product product, final Activity activity, int count, int final_item_price, String option){
        prefs.saveBooleanPreference(LOGIN_SAVECART, false);
        prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT,(prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT ) + count));


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

                                //int countBefore = databaseAdapter.getOrderCount();
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
                            Log.e(TAG, "onResponse:   810  "  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {



                Log.e(TAG, "onErrorResponse: 810  "   + error.getLocalizedMessage() );
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


       /* prefs.saveBooleanPreference(LOGIN_SAVECART, false);
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


    private void memberActivate(String text , final Dialog dialog)
    {
        JSONObject uploadPost = new JSONObject();
        try {
            uploadPost.put("member", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, constantActivateMember, uploadPost,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)  {
                        dialog.dismiss();
                        try {

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", "finish activate");
                                FlurryAgent.logEvent("Member Activate", mix);
                            } catch (Exception e) {
                            }

                            ToastHelper.showToast(activity,response.getString("text"));

                            getCustomerInfo();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                ToastHelper.showToast(activity, resources.getString(R.string.search_error));
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

        AppController.getInstance().addToRequestQueue(jsonObjReq, "Confirm");
    }

    public void getCustomerInfo(){
        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCustomerInfo, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    prefs.saveStringPreferences(SP_USER_PHONE, response.getString("phone"));
                    prefs.saveStringPreferences(SP_USER_NAME, response.getString("name"));
                    prefs.saveStringPreferences(SP_USER_VIP_ACCOUNT, response.getString("vip"));
                    prefs.saveIntPerferences(SP_USER_MONOTH, response.getInt("birth_month"));
                    prefs.saveIntPerferences(SP_USER_YEAR, response.getInt("birth_year"));
                    prefs.saveStringPreferences(SP_USER_BOYORGIRL, response.getString("kid_gender"));
                    prefs.saveIntPerferences(SP_MEMBER_ID, response.getInt("id"));
                    if(response.getString(("profile_url")) != null){
                        prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, response.getString("profile_url"));
                    }else
                        prefs.saveStringPreferences(SP_USER_PROFILEIMAGE, "");


                    prefs.saveIntPerferences(SP_USER_POINT, response.getInt("gift_points"));



                    if (response.getString("vip").trim().length() == 0) {

                        prefs.saveIntPerferences(SP_VIP_ID, 0);

                    } else {

                        prefs.saveIntPerferences(SP_VIP_ID, 1);

                    }



                    if(activity.getLocalClassName().contains("GiftExchangeActivity")) {

                        GiftExchangeActivity pro = (GiftExchangeActivity) activity;
                        pro.pointChange();

                    }

                    notifyDataSetChanged();





                } catch (JSONException e) {
                    Log.e(TAG, "onResponse:  "  + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){

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
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
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

        zero.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 0));
        one.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 1));
        two.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 2));
        three.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 3));
        four.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 4));
        five.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 5));
        six.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 6));
        seven.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 7));
        eight.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 8));
        nine.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 9));
        ten.setOnClickListener(new UniversalAdapter.clickFriendFeedback(zero, one, two, three, four, five, six, seven, eight, nine, ten, 10));



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


    private void showBottomDialogWarning( int amount, int option, String payName){
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);


        View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_warning, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setCancelable(true);


        CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
        CustomTextView txtWarning = mBottomSheetDialog.findViewById(R.id.txtWarning);
        ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);

        title.setText(resources.getString(R.string.warning));

        if (option == 0){


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtWarning.setText(Html.fromHtml(
                        String.format(resources.getString(R.string.amount_warning_less), amount + "" , payName)  , Html.FROM_HTML_MODE_COMPACT));
            } else {
                txtWarning.setText(Html.fromHtml(String.format(resources.getString(R.string.amount_warning_less), amount + "" , payName)));


            }


        }else if(option == 1)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                txtWarning.setText(Html.fromHtml(
                        String.format(resources.getString(R.string.amount_warning_greater), amount + "" , payName)  , Html.FROM_HTML_MODE_COMPACT));
            } else {
                txtWarning.setText(Html.fromHtml(String.format(resources.getString(R.string.amount_warning_greater), amount + "" , payName)));


            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();


    }

    @SuppressLint("StringFormatInvalid")
    private void showDeliveryListDialog(){
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
        title.setText(resources.getString(R.string.dialog_delivery_title));


        if(delList.size() > 0) {

            for (int i = 0; i < delList.size(); i++) {

                Delivery delivery = delList.get(i);

                LinearLayout linearHorizontal = new LinearLayout(activity);
                linearHorizontal.setId(delivery.getId() + 1);
                linearHorizontal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout deliveyLayout = new LinearLayout(activity);
                LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                childParam1.weight = 0.1f;
                deliveyLayout.setLayoutParams(childParam1);
                deliveyLayout.setId(delivery.getId());
                deliveyLayout.setOrientation(LinearLayout.VERTICAL);
                deliveyLayout.setPadding(10, 10, 10, 10);


                LinearLayout.LayoutParams childParam2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                childParam2.weight = 0.9f;
                childParam2.gravity = Gravity.CENTER;


                CustomTextView price = new CustomTextView(activity);
                price.setTextSize(16);
                price.setPadding(10, 20, 10, 10);
                price.setGravity(Gravity.LEFT);
                price.setText(delivery.getName()+" ( "+formatter.format(delivery.getPrice())+" "+activity.getResources().getString(R.string.currency)+" )");
                price.setTextColor(activity.getResources().getColor(R.color.black));
                deliveyLayout.addView(price);

                CustomTextView desc = new CustomTextView(activity);
                desc.setTextSize(14);
                desc.setPadding(20, 10, 10, 20);
                desc.setTextColor(activity.getResources().getColor(R.color.black));
                desc.setGravity(Gravity.LEFT);
                desc.setText(delivery.getDesc());
                deliveyLayout.addView(desc);

                if(delivery.getDelivery_type() == 1){
                    CustomTextView normalPrice = new CustomTextView(activity);
                    normalPrice.setTextSize(14);
                    normalPrice.setPadding(10, 10, 10, 10);
                    normalPrice.setGravity(Gravity.LEFT);
                    normalPrice.setText(String.format(resources.getString(R.string.express_sub_title), delivery.getNormal_price() + "" , delivery.getNormal_delivery_days()));
                    normalPrice.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                    deliveyLayout.addView(normalPrice);

                }


                int amt = 0;
              /*  if(prefs.getIntPreferences(SP_VIP_ID) == 1){
                    if (prefs.getIntPreferences(SP_MEMBER_DELIVERY_AMOUNT) < delivery.getFreeDelivery())
                        amt = prefs.getIntPreferences(SP_MEMBER_DELIVERY_AMOUNT);
                    else
                        amt = delivery.getFreeDelivery();
                }
                else*/
                amt = delivery.getFreeDelivery();


                CustomTextView txt = new CustomTextView(activity);
                txt.setTextSize(10);
                txt.setPadding(20, 0, 10, 20);
                txt.setTextColor(activity.getResources().getColor(R.color.black));
                txt.setGravity(Gravity.LEFT);
                txt.setText(String.format(resources.getString(R.string.free_deliver_amt), String.valueOf(amt)));
                deliveyLayout.addView(txt);
                TextView space = new TextView(activity);
                space.setHeight(6);
                space.setBackgroundResource(R.color.line_gray);




                linearHorizontal.addView(deliveyLayout);


                mainlayout.addView(linearHorizontal);
                mainlayout.addView(space);


                dialog.show();

            }
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







}
