package com.kyarlay.ayesunaing.operation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityEditAddress;
import com.kyarlay.ayesunaing.activity.ActivityStepOneCart;
import com.kyarlay.ayesunaing.activity.ActivityStepTwoCart;
import com.kyarlay.ayesunaing.activity.ShoppingCartActivity;
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
import com.kyarlay.ayesunaing.data.StoreHelper;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.holder.EditAddressHolder;
import com.kyarlay.ayesunaing.holder.FooterHoloder;
import com.kyarlay.ayesunaing.holder.FooterSummaryHolder;
import com.kyarlay.ayesunaing.holder.MainPaymentHolder;
import com.kyarlay.ayesunaing.holder.OrderNowHolder;
import com.kyarlay.ayesunaing.holder.RefreshHolder;
import com.kyarlay.ayesunaing.holder.ShoppingCartItemHolder;
import com.kyarlay.ayesunaing.holder.StepOneItemHolder;
import com.kyarlay.ayesunaing.holder.StepOneTopHolder;
import com.kyarlay.ayesunaing.object.Addresses;
import com.kyarlay.ayesunaing.object.CustomerProduct;
import com.kyarlay.ayesunaing.object.CustomerProductList;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.MainPaymentObj;
import com.kyarlay.ayesunaing.object.PaymentObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.ShopLocation;
import com.kyarlay.ayesunaing.object.TownShip;
import com.kyarlay.ayesunaing.object.TownShipObject;
import com.kyarlay.ayesunaing.object.UniversalPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class UniversalStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements ConstantVariable, Constant, ConstantsDB {

    private static final String TAG = "UniversalStepAdapter";


    ArrayList<UniversalPost> universalList;
    AppCompatActivity activity;
    ImageLoader imageLoader;
    Display display;
    DatabaseAdapter databaseAdapter;
    MyPreference prefs;
    ArrayList<Delivery> delList ;

    List<TownShip> townShipList ;

    int totalPointToServer = 0;

    DecimalFormat formatter = new DecimalFormat("#,###,###");
    Resources resources;

    String android_id;
    FirebaseAnalytics firebaseAnalytics;
    UniversalAdapter gridAdapter;
    RecyclerView.LayoutManager layoutManager;

    boolean chooseDate = false;
    boolean isTomorrow = false;
    Date today = new Date();
    Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
    Date dayAfterTomorrow = new Date(today.getTime() + (2 * (1000 * 60 * 60 * 24)));
    int mYear, mMonth, mDay, mTomorrow, chooseDay, chooseMonth, chooseYear, mHour, mMin, mSec;
    int deliTime = -1;
    int totalPriceToServer  = 0;

    public UniversalStepAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList) {
        this.universalList = universalList;
        this.activity = activity1;
        imageLoader = AppController.getInstance().getImageLoader();
        display = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter = new DatabaseAdapter(activity.getApplicationContext());
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        delList = databaseAdapter.getDelivery();
        townShipList = databaseAdapter.getTownShipByList();

        new MyFlurry(activity);

        firebaseAnalytics = FirebaseAnalytics.getInstance(activity);

        android_id = android.provider.Settings.Secure.getString(activity.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        if (!prefs.getBooleanPreference(ALREADY_USE_POINT)) {
            prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);
        }

    }


    @Override
    public int getItemCount() {
        return universalList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (universalList.get(position).getPostType().equals(STEP_ONE_BOTTOMS)) {
            return VIEW_TYPE_STEP_ONE_BOTTOMS;
        }
        else if(universalList.get(position).getPostType().equals(STEP_ONE_TOP)){
            return VIEW_TYPE_STEP_ONE_TOP;
        }
        else if(universalList.get(position).getPostType().equals(PICK_UP_SHOP_ITEM)){
            return VIEW_TYPE_PICK_UP_SHOP_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(STEP_ONE_ITEM)){
            return VIEW_TYPE_STEP_ONE_ITEM;
        }
        else if(universalList.get(position).getPostType().equals(REFRESH_FOOTER)){
            return VIEW_TYPE_REFRESH_FOOTER;
        }
        else if(universalList.get(position).getPostType().equals(CART_DETAIL_FOOTER)){
            return VIEW_TYPE_CART_DETAIL_FOOTER;
        }
        else if(universalList.get(position).getPostType().equals(STEP_TWO_MAIN)){
            return VIEW_TYPE_STEP_TWO_MAIN;
        }
        else if(universalList.get(position).getPostType().equals(EDIT_ADDRESS_TITLE)){
            return VIEW_TYPE_EDIT_ADDRESS_TITLE;
        }
        else if(universalList.get(position).getPostType().equals(DISCOUNT_TITLE)){
            return VIEW_TYPE_DISCOUNT_TITLE;
        }
        else if(universalList.get(position).getPostType().equals(SHOPPING_CART_SUMMARY)){
            return VIEW_TYPE_SHOPPING_CART_SUMMARY;
        }
        else if(universalList.get(position).getPostType().equals(SHOPPING_CART_ITEM)){
            return VIEW_TYPE_SHOPPING_CART_ITEM;
        }
        return VIEW_TYPE_DEFAULT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_STEP_ONE_BOTTOMS) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_shopping_order_now, parent, false);
            viewHolder = new OrderNowHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_STEP_ONE_TOP) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_indicator_step, parent, false);
            viewHolder = new StepOneTopHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_PICK_UP_SHOP_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_address_item, parent, false);
            viewHolder = new StepOneItemHolder(viewItem);
        }

        else if (viewType == VIEW_TYPE_STEP_ONE_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_address_item, parent, false);
            viewHolder = new StepOneItemHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_REFRESH_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_touch_refresh, parent, false);
            viewHolder = new RefreshHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_CART_DETAIL_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_footer, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }
        else if (viewType == VIEW_TYPE_SHOPPING_CART_ITEM) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_shopping_cart_item, parent, false);
            viewHolder = new ShoppingCartItemHolder(viewItem);
        }


        else if(viewType == VIEW_TYPE_DISCOUNT_TITLE){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }
        else if (viewType == VIEW_TYPE_STEP_TWO_MAIN) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_step_two_recycler, parent, false);
            viewHolder = new MainPaymentHolder(viewItem);
        }
        else if (viewType == VIEW_TYPE_EDIT_ADDRESS_TITLE) {
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_edit_address, parent, false);
            viewHolder = new EditAddressHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_SHOPPING_CART_SUMMARY){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_shopping_cart_summary, parent, false);
            viewHolder = new FooterSummaryHolder(viewItem);
        }
        return viewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"WrongConstant", "StringFormatInvalid", "StringFormatMatches"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder parentHolder, final int position) {

        int type = getItemViewType(position);

        switch (type) {
            case VIEW_TYPE_STEP_ONE_BOTTOMS: {

                final OrderNowHolder orderNowHolder1 = (OrderNowHolder) parentHolder;
                orderNowHolder1.txtOrder.setText(resources.getString(R.string.order));
                orderNowHolder1.txtRemarkTitle.setText(resources.getString(R.string.remark_title));
                orderNowHolder1.txtcheckDeliPrice.setText(resources.getString(R.string.check_delivery_amount));
                orderNowHolder1.delivery_price_text.setText(resources.getString(R.string.cart_detail_delivery_fees));
                orderNowHolder1.txtFullDelivery.setText(resources.getString(R.string.cart_detail_delivery_fees));
                orderNowHolder1.commission_price_text.setText("Payment commission");
                orderNowHolder1.total_price_text.setText(resources.getString(R.string.cart_detail_payable_text));
                orderNowHolder1.txtcheckDeliPrice.setPaintFlags(orderNowHolder1.txtcheckDeliPrice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                orderNowHolder1.txtcheckDeliPrice.setTypeface(orderNowHolder1.txtcheckDeliPrice.getTypeface(), Typeface.BOLD);

                orderNowHolder1.nine_am_title.setText(resources.getString(R.string.title_morning));
                orderNowHolder1.twelve_am_title.setText(resources.getString(R.string.title_afternoon));
                orderNowHolder1.three_pm_title.setText(resources.getString(R.string.title_evening));
                orderNowHolder1.six_pm_title.setText(resources.getString(R.string.title_night));
                orderNowHolder1.title_choose_date.setText(resources.getString(R.string.deli_time));
                orderNowHolder1.txtRightNow.setText(resources.getString(R.string.delivery_now));

                orderNowHolder1.layout_twelve_am.getLayoutParams().width = (int) (display.getWidth() / 5);
                orderNowHolder1.layout_six_pm.getLayoutParams().width = (int) (display.getWidth() / 5);
                orderNowHolder1.layout_nine_am.getLayoutParams().width = (int) (display.getWidth() / 5);
                orderNowHolder1.layout_three_pm.getLayoutParams().width = (int) (display.getWidth() / 5);


                if (prefs.getBooleanPreference(CAN_CHOOSE_TIMESLOT)) {
                    orderNowHolder1.time_layout.setVisibility(View.VISIBLE);
                } else
                    orderNowHolder1.time_layout.setVisibility(View.GONE);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    orderNowHolder1.nine_am.setText(Html.fromHtml(
                            resources.getString(R.string.nine_am_html), Html.FROM_HTML_MODE_COMPACT));

                    orderNowHolder1.twelve_am.setText(Html.fromHtml(
                            resources.getString(R.string.twelve_am_html), Html.FROM_HTML_MODE_COMPACT));

                    orderNowHolder1.three_pm.setText(Html.fromHtml(
                            resources.getString(R.string.three_pm_html), Html.FROM_HTML_MODE_COMPACT));

                    orderNowHolder1.six_pm.setText(Html.fromHtml(
                            resources.getString(R.string.six_pm_html), Html.FROM_HTML_MODE_COMPACT));

                   /* orderNowHolder1.txtFullDelivery.setText(Html.fromHtml(
                            resources.getString(R.string.delivery_full_today)  , Html.FROM_HTML_MODE_COMPACT));*/


                } else {
                    orderNowHolder1.nine_am.setText(Html.fromHtml(resources.getString(R.string.nine_am_html)));
                    orderNowHolder1.twelve_am.setText(Html.fromHtml(resources.getString(R.string.twelve_am_html)));
                    orderNowHolder1.three_pm.setText(Html.fromHtml(resources.getString(R.string.three_pm_html)));
                    orderNowHolder1.six_pm.setText(Html.fromHtml(resources.getString(R.string.six_pm_html)));
                    // orderNowHolder1.txtFullDelivery.setText(Html.fromHtml(resources.getString(R.string.delivery_full_today)));

                }


                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                orderNowHolder1.edRemark.requestFocus();
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(orderNowHolder1.edRemark, InputMethodManager.SHOW_IMPLICIT);

                orderNowHolder1.edRemark.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        prefs.saveStringPreferences(TEMP_REMARK_TEXT, s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                orderNowHolder1.txtcheckDeliPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeliveryListDialog();
                    }
                });

                int checkTotalPriceBottom = 0;
                int commissionPriceBottom = 0;
                int finalPriceBottom = 0;

               /* if(prefs.getIntPreferences(SP_VIP_ID) == 1 && proOrderBottom.getPrice() >= prefs.getIntPreferences(SP_MEMBER_DELIVERY_AMOUNT) ){
                    orderNowHolder1.delivery_price.setText(" ( "+0 +" "+ activity.getResources().getString(R.string.currency)+" )");
                    checkTotalPriceBottom = proOrderBottom.getPrice() - prefs.getIntPreferences(CHECK_USE_POINT);
                    commissionPriceBottom = (int) (checkTotalPriceBottom  * prefs.getFloatPreferences(TEMP_COMMISSION_RATE) / 100);

                    finalPriceBottom = checkTotalPriceBottom + commissionPriceBottom ;

                    prefs.saveIntPerferences(TEMP_COMMISSION_PRICE, commissionPriceBottom );
                    orderNowHolder1.total_price.setText(formatter.format(  finalPriceBottom) +" "+resources.getString(R.string.currency));


                }
                else */
                if (prefs.getIntPreferences(SP_CUSTOMER_TOTAL) >= prefs.getIntPreferences(DELIVERY_FREE_AMOUNT)) {
                    orderNowHolder1.delivery_price.setText(" ( " + 0 + " " + activity.getResources().getString(R.string.currency) + " )");

                    checkTotalPriceBottom = prefs.getIntPreferences(SP_CUSTOMER_TOTAL)- prefs.getIntPreferences(CHECK_USE_POINT);
                    commissionPriceBottom = (int) (checkTotalPriceBottom * prefs.getFloatPreferences(TEMP_COMMISSION_RATE) / 100);
                    finalPriceBottom = checkTotalPriceBottom + commissionPriceBottom;

                    prefs.saveIntPerferences(TEMP_COMMISSION_PRICE, commissionPriceBottom);

                    orderNowHolder1.total_price.setText(formatter.format(finalPriceBottom) + " " + resources.getString(R.string.currency));
                } else {
                    for (int i = 0; i < delList.size(); i++) {
                        if (delList.get(i).getId() == prefs.getIntPreferences(DELIVERY_ID)) {

                            orderNowHolder1.delivery_price.setText(delList.get(i).getName() + " ( " + formatter.format(delList.get(i).getPrice())
                                    + activity.getResources().getString(R.string.currency) + " )");


                        }
                    }

                    checkTotalPriceBottom = prefs.getIntPreferences(SP_CUSTOMER_TOTAL) + prefs.getIntPreferences(DELIVERY_PRICE) - prefs.getIntPreferences(CHECK_USE_POINT);
                    commissionPriceBottom = (int) (checkTotalPriceBottom * prefs.getFloatPreferences(TEMP_COMMISSION_RATE) / 100);

                    finalPriceBottom = checkTotalPriceBottom + commissionPriceBottom;

                    prefs.saveIntPerferences(TEMP_COMMISSION_PRICE, commissionPriceBottom);

                    orderNowHolder1.total_price.setText(formatter.format(finalPriceBottom) + " "
                            + activity.getResources().getString(R.string.currency));
                }

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                mTomorrow = tomorrow.getDate();


                chooseDay = mDay;
                chooseMonth = mMonth;
                chooseYear = mYear;

                prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, mYear);

                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMin = c.get(Calendar.MINUTE);
                mSec = c.get(Calendar.SECOND);


                orderNowHolder1.txtDeliveryRemark.setVisibility(View.GONE);

                if (prefs.getBooleanPreference(DELIVERY_STORE_ID_MINUS)) {

                    orderNowHolder1.txtRightNow.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                    orderNowHolder1.txtRightNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            orderNowHolder1.txtRightNow.setBackgroundDrawable(resources.getDrawable(R.drawable.border_blue));
                            orderNowHolder1.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                            orderNowHolder1.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                            orderNowHolder1.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                            orderNowHolder1.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));

                            orderNowHolder1.txtDeliveryRemark.setVisibility(View.VISIBLE);

                            Log.e(TAG, "onClick: ****************** (mHour)  " + (mHour));

                            if (mHour < 12) {
                                chooseDate = true;
                                deliTime = 1;
                                prefs.saveIntPerferences(TEMP_DELI_ID, 1);
                                prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                                prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                                prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);

                                orderNowHolder1.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_pickup), chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.nine_am)) + "");
                                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.nine_am_html_long));


                            } else if (mHour < 15) {

                                chooseDate = true;
                                deliTime = 2;
                                prefs.saveIntPerferences(TEMP_DELI_ID, 2);
                                prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                                prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                                prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.twelve_am_html_long));
                                orderNowHolder1.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_pickup), chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.twelve_am)) + "");

                            } else if (mHour < 18) {

                                chooseDate = true;
                                deliTime = 3;
                                prefs.saveIntPerferences(TEMP_DELI_ID, 3);
                                prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                                prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                                prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.three_pm_html_long));
                                orderNowHolder1.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_pickup), chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.three_pm)) + "");
                            } else if (mHour < 21) {

                                chooseDate = true;
                                deliTime = 4;
                                prefs.saveIntPerferences(TEMP_DELI_ID, 4);
                                prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                                prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                                prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.six_pm_html_long));
                                orderNowHolder1.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_pickup), chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.six_pm)) + "");
                            } else {
                                chooseDate = true;
                                deliTime = 1;
                                prefs.saveIntPerferences(TEMP_DELI_ID, 1);
                                prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                                prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                                prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.nine_am_html_long));
                                orderNowHolder1.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_pickup), chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.nine_am)) + "");
                            }
                        }
                    });

                    if (prefs.getBooleanPreference(TEMP_SAME_DAY)) {


                        orderNowHolder1.choose_date.setText(chooseDay + "-" + (chooseMonth + 1) + "-" + mYear);
                        // orderNowHolder1.txt_date_only.setText(chooseDay + "-" + (chooseMonth+1) + "-" + mYear);

                        if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)) {
                            //  orderNowHolder1.linearFullDelivery.setVisibility(View.GONE);
                            orderNowHolder1.txtRightNow.setVisibility(View.VISIBLE);


                            if ((mHour + 3) < 12) {
                                orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);


                            } else if ((mHour + 3) < 15) {

                                orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                            } else if ((mHour + 3) < 18) {

                                orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                            } else if ((mHour + 3) < 21) {
                                orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.GONE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                            } else {
                                // orderNowHolder1.linearFullDelivery.setVisibility(View.VISIBLE);
                                chooseDay = mTomorrow;
                                orderNowHolder1.choose_date.setText(mTomorrow + "-" + (chooseMonth + 1) + "-" + mYear);
                                //orderNowHolder1.txt_date_only.setText(mTomorrow + "-" + (chooseMonth+1) + "-" + mYear);

                                orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                            }

                        } else {
                            //orderNowHolder1.linearFullDelivery.setVisibility(View.GONE);
                            orderNowHolder1.txtRightNow.setVisibility(View.GONE);
                            if ((mHour + 5) <= 10) {
                                orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                            } else if ((mHour + 5) <= 13) {

                                orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                            } else if ((mHour + 5) <= 16) {

                                orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                            } else if ((mHour + 5) <= 19) {
                                orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.GONE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.GONE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                            } else {
                                //orderNowHolder1.linearFullDelivery.setVisibility(View.VISIBLE);
                                chooseDay = mTomorrow;
                                orderNowHolder1.choose_date.setText(mTomorrow + "-" + (chooseMonth + 1) + "-" + mYear);
                                //orderNowHolder1.txt_date_only.setText(mTomorrow + "-" + (chooseMonth+1) + "-" + mYear);

                                orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                            }


                        }


                        orderNowHolder1.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                        orderNowHolder1.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                        orderNowHolder1.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                        orderNowHolder1.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));

                    } else {
                        // orderNowHolder1.linearFullDelivery.setVisibility(View.VISIBLE);

                        chooseDay = mTomorrow;

                        orderNowHolder1.txtRightNow.setVisibility(View.GONE);
                        orderNowHolder1.choose_date.setText(mTomorrow + "-" + (chooseMonth + 1) + "-" + mYear);
                        // orderNowHolder1.txt_date_only.setText(mTomorrow + "-" + (chooseMonth+1) + "-" + mYear);
                        orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                        orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                        orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                        orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                        orderNowHolder1.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                        orderNowHolder1.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                        orderNowHolder1.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                        orderNowHolder1.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                    }


                    if (prefs.getBooleanPreference(CAN_CHOOSE_TIMESLOT)) {
                        orderNowHolder1.time_layout.setVisibility(View.VISIBLE);
                        orderNowHolder1.linearChooseDate.setVisibility(View.VISIBLE);
                        orderNowHolder1.layout_date_only.setVisibility(View.GONE);
                    } else {
                        orderNowHolder1.time_layout.setVisibility(View.GONE);
                        orderNowHolder1.linearChooseDate.setVisibility(View.GONE);
                        orderNowHolder1.layout_date_only.setVisibility(View.GONE);

                        isTomorrow = true;
                        chooseDay = mTomorrow;
                        prefs.saveBooleanPreference(TEMP_IS_TOMORROW, true);


                        /*if ((mHour + 5) > 19) {
                            isTomorrow = true;
                            chooseDay = mTomorrow;
                            prefs.saveBooleanPreference(TEMP_IS_TOMORROW, true);
                        }*/

                        prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                        prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                        prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                        prefs.saveIntPerferences(TEMP_DELI_ID, -1);
                    }

                    orderNowHolder1.layout_nine_am.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chooseDate = true;
                            deliTime = 1;
                            prefs.saveIntPerferences(TEMP_DELI_ID, 1);
                            prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                            prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                            prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                            setDeliTimeUI(orderNowHolder1, deliTime);


                        }
                    });


                    orderNowHolder1.layout_six_pm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chooseDate = true;
                            deliTime = 4;
                            prefs.saveIntPerferences(TEMP_DELI_ID, 4);
                            prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                            prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                            prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                            setDeliTimeUI(orderNowHolder1, deliTime);


                        }
                    });

                    orderNowHolder1.layout_three_pm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chooseDate = true;
                            deliTime = 3;
                            prefs.saveIntPerferences(TEMP_DELI_ID, 3);
                            prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                            prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                            prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                            setDeliTimeUI(orderNowHolder1, deliTime);


                        }
                    });

                    orderNowHolder1.layout_twelve_am.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chooseDate = true;
                            deliTime = 2;
                            prefs.saveIntPerferences(TEMP_DELI_ID, 2);
                            prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                            prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, chooseYear);
                            prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                            setDeliTimeUI(orderNowHolder1, deliTime);


                        }
                    });


                    if (prefs.getBooleanPreference(CAN_CHOOSE_TIMESLOT)) {
                        mTomorrow = tomorrow.getDate();
                        chooseDay = mTomorrow;
                    } else {
                        if (mHour < 16) {
                            mTomorrow = tomorrow.getDate();
                            ////orderNowHolder1.txtFullDelivery.setText(Html.fromHtml(resources.getString(R.string.delivery_full_today)));
                        } else {
                            mTomorrow = dayAfterTomorrow.getDate();
                            // orderNowHolder1.txtFullDelivery.setText(Html.fromHtml(resources.getString(R.string.delivery_full_tomorrow)));
                        }

                        chooseDay = mTomorrow;
                        prefs.saveBooleanPreference(TEMP_IS_TOMORROW, true);


                        prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                        prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, mYear);
                        prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                        prefs.saveIntPerferences(TEMP_DELI_ID, -1);


                    }

                    orderNowHolder1.txt_date_only.setText(chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear + "");

                    orderNowHolder1.layout_date_only.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {

                                            Log.e(TAG, "onDateSet:  *************  ");
                                            chooseDate = false;


                                            chooseDay = dayOfMonth;
                                            chooseMonth = monthOfYear;
                                            chooseYear = year;

                                            isTomorrow = true;
                                            chooseDay = dayOfMonth;
                                            mTomorrow = dayOfMonth;
                                            prefs.saveBooleanPreference(TEMP_IS_TOMORROW, true);


                                            prefs.saveIntPerferences(TEMP_CHOOSE_DAY, chooseDay);
                                            prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, mYear);
                                            prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, chooseMonth);
                                            prefs.saveIntPerferences(TEMP_DELI_ID, -1);

                                            Log.e(TAG, "onDateSet: dayOfMonth  " + dayOfMonth);


                                            orderNowHolder1.txt_date_only.setText(dayOfMonth + "-" + (chooseMonth + 1) + "-" + mYear);
                                        }
                                    }, mYear, mMonth, chooseDay);

                            if (mHour < 16) {
                                datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis() + (1000 * 60 * 60 * 24)) - 1000);
                            } else {
                                datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2)) - 1000);

                            }

                            datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3)) - 1000);
                            datePickerDialog.show();


                        }
                    });


                    orderNowHolder1.linearChooseDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            chooseDate = false;

                                            orderNowHolder1.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                                            orderNowHolder1.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                                            orderNowHolder1.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                                            orderNowHolder1.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                                            orderNowHolder1.txtRightNow.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));


                                            prefs.saveIntPerferences(TEMP_CHOOSE_DAY, 0);
                                            prefs.saveIntPerferences(TEMP_CHOOSE_YEAR, 0);
                                            prefs.saveIntPerferences(TEMP_CHOOSE_MONTH, 0);
                                            prefs.saveIntPerferences(TEMP_DELI_ID, -1);

                                      /*  orderNowHolder.radioNineAm.setChecked(false);
                                        orderNowHolder.radioSixPm.setChecked(false);
                                        orderNowHolder.radioThreePm.setChecked(false);
                                        orderNowHolder.radiotwelveAm.setChecked(false);*/
                                            orderNowHolder1.txtDeliveryRemark.setText("");
                                            orderNowHolder1.txtDeliveryRemark.setVisibility(View.GONE);

                                            chooseDay = dayOfMonth;
                                            chooseMonth = monthOfYear;
                                            chooseYear = year;
                                            orderNowHolder1.choose_date.setText(chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear + "");
                                            if (chooseDay == mDay && chooseMonth == mMonth) {
                                                if ((mHour + 5) <= 10) {
                                                    orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                                                } else if ((mHour + 5) <= 13) {
                                                    orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                                    orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                                                } else if ((mHour + 5) <= 16) {
                                                    orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                                    orderNowHolder1.layout_twelve_am.setVisibility(View.GONE);
                                                    orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                                                } else if ((mHour + 5) <= 19) {
                                                    orderNowHolder1.layout_nine_am.setVisibility(View.GONE);
                                                    orderNowHolder1.layout_twelve_am.setVisibility(View.GONE);
                                                    orderNowHolder1.layout_three_pm.setVisibility(View.GONE);
                                                    orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                                                } else {
                                                    isTomorrow = true;
                                                    prefs.saveBooleanPreference(TEMP_IS_TOMORROW, true);
                                                    orderNowHolder1.choose_date.setText(mTomorrow + "-" + (mMonth + 1) + "-" + mYear);
                                                    orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                                    orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);
                                                }
                                            } else {
                                                orderNowHolder1.layout_nine_am.setVisibility(View.VISIBLE);
                                                orderNowHolder1.layout_twelve_am.setVisibility(View.VISIBLE);
                                                orderNowHolder1.layout_three_pm.setVisibility(View.VISIBLE);
                                                orderNowHolder1.layout_six_pm.setVisibility(View.VISIBLE);

                                            }


                                        }
                                    }, mYear, mMonth, chooseDay);


                            if (!prefs.getBooleanPreference(TEMP_SAME_DAY))
                                datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis() + (1000 * 60 * 60 * 24)) - 1000);
                            else
                                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                            datePickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 3)) - 1000);
                            datePickerDialog.show();


                        }
                    });


                    Log.e(TAG, "onBindViewHolder: " + " -------------------- linearDeliAll visible");

                    orderNowHolder1.linearDeliAll.setVisibility(View.VISIBLE);

                } else {
                    Log.e(TAG, "onBindViewHolder: " + " -------------------- linearDeliAll gone ");
                    orderNowHolder1.linearDeliAll.setVisibility(View.GONE);
                }


                orderNowHolder1.layoutNormal.setVisibility(View.GONE);
                orderNowHolder1.layoutDateForHidden.setVisibility(View.GONE);
                final boolean[] click_choose = {false};
                final int[] normal_price = {0};
                final int[] temp_type = {0};
                prefs.saveBooleanPreference(TEMP_NORMAL, false);
                prefs.saveIntPerferences(TEMP_NORMAL_PRICE, 0);
                prefs.saveStringPreferences(TEMP_NORMAL_DAY, "");
                orderNowHolder1.layoutDateForHidden.setAlpha(1f);
                orderNowHolder1.layout_date_only.setAlpha(1f);
                orderNowHolder1.linearChooseDate.setAlpha(1f);
                orderNowHolder1.txtFullDelivery.setAlpha(1f);
                orderNowHolder1.layout_date_only.setEnabled(true);
                orderNowHolder1.linearChooseDate.setEnabled(true);

                Log.e(TAG, "onBindViewHolder: ************* deli id" + prefs.getIntPreferences(DELIVERY_ID));
                for(int i = 0; i < delList.size(); i ++){
                    if(delList.get(i).getId() == prefs.getIntPreferences(DELIVERY_ID)) {
                        String expressText = String.format(resources.getString(R.string.express_title), prefs.getStringPreferences(TEMP_ADDRESS_TOWNSHIP), delList.get(i).getPrice() + "" );
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            orderNowHolder1.txtExpress.setText(Html.fromHtml(
                                    expressText  , Html.FROM_HTML_MODE_COMPACT));
                            orderNowHolder1.textDeliveryRemark.setText(Html.fromHtml(
                                    expressText  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            orderNowHolder1.txtExpress.setText(Html.fromHtml(expressText));
                            orderNowHolder1.textDeliveryRemark.setText(Html.fromHtml(expressText));


                        }
                        if(delList.get(i).getDelivery_type() == 1){
                            click_choose[0] = false;
                            orderNowHolder1.layoutNormal.setVisibility(View.GONE);
                            normal_price[0] = delList.get(i).getNormal_price();
                            String text = String.format(resources.getString(R.string.normal_sub_title), prefs.getStringPreferences(TEMP_ADDRESS_TOWNSHIP), delList.get(i).getNormal_price() + "" , delList.get(i).getNormal_delivery_days());
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                orderNowHolder1.txtFullDelivery.setText(Html.fromHtml(
                                        text  , Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                orderNowHolder1.txtFullDelivery.setText(Html.fromHtml(text));


                            }


                            prefs.saveIntPerferences(TEMP_NORMAL_PRICE, delList.get(i).getNormal_price());
                            prefs.saveStringPreferences(TEMP_NORMAL_DAY,delList.get(i).getNormal_delivery_days());
                            orderNowHolder1.txtChoose.setText(resources.getString(R.string.normal_choose));
                            Log.e(TAG, "onBindViewHolder: ************* " + prefs.getStringPreferences(TEMP_ADDRESS_TOWNSHIP) );
                            Log.e(TAG, "onBindViewHolder: ************* " + prefs.getIntPreferences(TEMP_NORMAL_PRICE) );

                            Log.e(TAG, "onBindViewHolder: ************* " + delList.get(i).getDelivery_type() );


                        }


                    }
                }

                orderNowHolder1.txtChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(click_choose[0]){
                            orderNowHolder1.layoutDateForHidden.setAlpha(1f);
                            orderNowHolder1.layout_date_only.setAlpha(1f);
                            orderNowHolder1.linearChooseDate.setAlpha(1f);
                            orderNowHolder1.layout_date_only.setEnabled(true);
                            orderNowHolder1.linearChooseDate.setEnabled(true);
                            orderNowHolder1.txtChoose.setText(resources.getString(R.string.normal_choose));
                            click_choose[0] = false;
                            prefs.saveBooleanPreference(TEMP_NORMAL, false);
                            Log.e(TAG, "onBindViewHolder: ************* unchoose " + prefs.getIntPreferences(TEMP_NORMAL_PRICE) );
                           // prefs.saveIntPerferences(TEMP_NORMAL_PRICE, 0);
                        }
                        else{
                            orderNowHolder1.layoutDateForHidden.setAlpha(0.5f);
                            orderNowHolder1.layout_date_only.setAlpha(0.5f);
                            orderNowHolder1.linearChooseDate.setAlpha(0.5f);
                            orderNowHolder1.layout_date_only.setEnabled(false);
                            orderNowHolder1.linearChooseDate.setEnabled(false);
                            orderNowHolder1.txtChoose.setText(resources.getString(R.string.normal_no_choose));
                            click_choose[0] = true;
                            prefs.saveBooleanPreference(TEMP_NORMAL, true);

                            Log.e(TAG, "onBindViewHolder: ************* " + prefs.getIntPreferences(TEMP_NORMAL_PRICE) );

                        }
                    }
                });


                orderNowHolder1.txtOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderNowHolder1.txtOrder.setEnabled(false);
                        if (prefs.isNetworkAvailable()) {

                            if (prefs.getBooleanPreference(DELIVERY_STORE_ID_MINUS)) {
                                if (prefs.getIntPreferences(TEMP_DELI_ID) != -1) {
                                    if (prefs.isNetworkAvailable()) {
                                        orderNowHolder1.txtOrder.setEnabled(true);


                                        Intent intent = new Intent(activity, ActivityStepTwoCart.class);
                                        activity.startActivity(intent);
                                    } else {


                                        orderNowHolder1.txtOrder.setEnabled(true);
                                        ToastHelper.showToast(activity, resources.getString(R.string.must_fill_address));
                                    }
                                } else {

                                    if (prefs.getBooleanPreference(CAN_CHOOSE_TIMESLOT)) {
                                        orderNowHolder1.txtOrder.setEnabled(true);
                                        ToastHelper.showToast(activity, resources.getString(R.string.deli_time_must_choose));


                                    } else {
                                        orderNowHolder1.txtOrder.setEnabled(true);
                                        Intent intent = new Intent(activity, ActivityStepTwoCart.class);
                                        activity.startActivity(intent);
                                    }


                                }
                            } else {
                                orderNowHolder1.txtOrder.setEnabled(true);

                                prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);
                                Intent intent = new Intent(activity, ActivityStepTwoCart.class);
                                activity.startActivity(intent);
                            }


                        } else {
                            orderNowHolder1.txtOrder.setEnabled(true);
                            ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));
                        }


                    }
                });


                break;

            }
            case VIEW_TYPE_SHOPPING_CART_ITEM :{
                final CustomerProduct cartProduct1 = (CustomerProduct) universalList.get(position);
                ShoppingCartItemHolder shoppingCartItemHolder = (ShoppingCartItemHolder) parentHolder;
                shoppingCartItemHolder.img.setImageUrl(cartProduct1.getImage(), imageLoader);
                shoppingCartItemHolder.title.setText(cartProduct1.getTitle());
                shoppingCartItemHolder.product_warning.setTypeface(shoppingCartItemHolder.product_warning.getTypeface(), Typeface.BOLD);

                shoppingCartItemHolder.relativeOne.getLayoutParams().width = (int) display.getWidth() * 3/11;
                //shoppingCartItemHolder.relativeThree.getLayoutParams().width = (int) display.getWidth() * 3/11;
                shoppingCartItemHolder.relativeOne.getLayoutParams().height = (int) display.getWidth() * 2/6;
                shoppingCartItemHolder.relativeTwo.getLayoutParams().height = (int) display.getWidth() * 2/6;
                // shoppingCartItemHolder.relativeThree.getLayoutParams().height = (int) display.getWidth() * 2/6;



                shoppingCartItemHolder.imgPlus.getLayoutParams().height = (int) ( display.getWidth() * 3/11) / 3;
                shoppingCartItemHolder.imgMinus.getLayoutParams().height = (int) ( display.getWidth() * 3/11) / 3;
                shoppingCartItemHolder.quantity.getLayoutParams().height = (int) ( display.getWidth() * 3/11) / 3;


                shoppingCartItemHolder.price.setText(formatter.format((int)( cartProduct1.getOriginalPrice()  * cartProduct1.getQuantity()))+" "+
                        resources.getString(R.string.currency));
                shoppingCartItemHolder.quantity.setText(cartProduct1.getQuantity()+"");
                if(cartProduct1.getOption() != null && cartProduct1.getOption().trim().length() > 0) {
                    shoppingCartItemHolder.title.setText(shoppingCartItemHolder.title.getText() + "\n" + cartProduct1.getOption().trim());
                }

                shoppingCartItemHolder.txtAllMark.setVisibility(View.GONE);
                shoppingCartItemHolder.price_strike.setVisibility(View.GONE);

                shoppingCartItemHolder.imgPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

/*

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("product_id", String.valueOf(cartProduct1.getId()));
                            FlurryAgent.logEvent("Change Product Quantity", mix);
                        } catch (Exception e) {
                        }

                        cartProduct1.setCount(cartProduct1.getCount() +1);
                        shoppingCartItemHolder.price.setText(cartProduct1.getCount() * cartProduct1.getPrice()+" "+
                                resources.getString(R.string.currency));
                        shoppingCartItemHolder.quantity.setText(cartProduct1.getCount()+"");
                        databaseAdapter.updatetOrderItem(cartProduct1.getId(), cartProduct1.getCount());

                        int toShowPrice = 0;

                        int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                        Product proPoint = databaseAdapter.getShoppingCartFooter();

                        if(((proPoint.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                            toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                        }
                        else{
                            toShowPrice = (proPoint.getPoint_usage() * percent ) / 100  ;
                        }

                        if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
                            prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);

                        }
                        else{
                            prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);
                        }
*/

                        cartProduct1.setQuantity(cartProduct1.getQuantity() + 1);
                        ShoppingCartActivity refresh = (ShoppingCartActivity) activity;
                        refresh.addProduct(cartProduct1);

                    }
                });
                shoppingCartItemHolder.imgMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(cartProduct1.getQuantity() > 1){

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("product_id", String.valueOf(cartProduct1.getId()));
                                FlurryAgent.logEvent("Change Product Quantity", mix);
                            } catch (Exception e) {
                            }
                            cartProduct1.setQuantity(cartProduct1.getQuantity() - 1);
                            ShoppingCartActivity refresh = (ShoppingCartActivity) activity;
                            refresh.subProduct(cartProduct1);
                        }else{

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("product_id", String.valueOf(cartProduct1.getId()));
                                FlurryAgent.logEvent("Remove Product From Cart", mix);
                            } catch (Exception e) {
                            }

                            ShoppingCartActivity refresh = (ShoppingCartActivity) activity;
                            refresh.removeProdcut(cartProduct1.getId());
                        }
                    }
                });



                shoppingCartItemHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_bottom_remove);

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "cart");
                            mix.put("product_id", String.valueOf(cartProduct1.getId()));
                            FlurryAgent.logEvent("Click Product Remove Icon", mix);
                        } catch (Exception e) {
                        }

                        dialog.setCancelable(true);


                        CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
                        CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
                        CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                        CustomTextView text = (CustomTextView) dialog.findViewById(R.id.titleRemove);

                        cancel.setTypeface(cancel.getTypeface(), Typeface.BOLD);
                        confirm.setTypeface(confirm.getTypeface(), Typeface.BOLD);
                        title.setTypeface(title.getTypeface(), Typeface.BOLD);
                        text.setTypeface(text.getTypeface(), Typeface.BOLD);

                        title.setText(String.format(resources.getString(R.string.dialog_delete_title_product), cartProduct1.getTitle()));
                        text.setText(resources.getString(R.string.dialog_delete_title_confirm));


                        cancel.setText(resources.getString(R.string.dialog_delete_cancel));
                        confirm.setText(resources.getString(R.string.dialog_delete_title_confirm_yes));

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("product_id", String.valueOf(cartProduct1.getId()));
                                    FlurryAgent.logEvent("Remove Product From Cart", mix);
                                } catch (Exception e) {
                                }
                               /* int result = databaseAdapter.deleteOrder(cartProduct1.getId());

                                int toShowPrice = 0;

                                int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                                Product proPoint = databaseAdapter.getShoppingCartFooter();

                                if(((proPoint.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                                    toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                                }
                                else{
                                    toShowPrice = (proPoint.getPoint_usage() * percent ) / 100  ;
                                }

                                if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
                                    prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);

                                }
                                else{
                                    prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);
                                }*/

                                ShoppingCartActivity shop = (ShoppingCartActivity) activity;
                                shop.removeProdcut(cartProduct1.getId());

                                dialog.dismiss();
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
                });




                break;
            }
           /* case VIEW_TYPE_SHOPPING_CART_ITEM:{
                final Product cartProduct1 = (Product)universalList.get(position);
                ShoppingCartItemHolder shoppingCartItemHolder = (ShoppingCartItemHolder) parentHolder;
                shoppingCartItemHolder.img.setImageUrl(cartProduct1.getPreviewImage(), imageLoader);
                shoppingCartItemHolder.title.setText(cartProduct1.getTitle());
                shoppingCartItemHolder.product_warning.setTypeface(shoppingCartItemHolder.product_warning.getTypeface(), Typeface.BOLD);




                shoppingCartItemHolder.relativeOne.getLayoutParams().width = (int) display.getWidth() * 3/11;
                //shoppingCartItemHolder.relativeThree.getLayoutParams().width = (int) display.getWidth() * 3/11;
                shoppingCartItemHolder.relativeOne.getLayoutParams().height = (int) display.getWidth() * 2/6;
                shoppingCartItemHolder.relativeTwo.getLayoutParams().height = (int) display.getWidth() * 2/6;
                // shoppingCartItemHolder.relativeThree.getLayoutParams().height = (int) display.getWidth() * 2/6;



                shoppingCartItemHolder.imgPlus.getLayoutParams().height = (int) ( display.getWidth() * 3/11) / 3;
                shoppingCartItemHolder.imgMinus.getLayoutParams().height = (int) ( display.getWidth() * 3/11) / 3;
                shoppingCartItemHolder.quantity.getLayoutParams().height = (int) ( display.getWidth() * 3/11) / 3;


                shoppingCartItemHolder.price.setText(formatter.format(cartProduct1.getPrice() * cartProduct1.getCount())+" "+
                        resources.getString(R.string.currency));
                shoppingCartItemHolder.quantity.setText(cartProduct1.getCount()+"");
                if(cartProduct1.getOptions() != null && cartProduct1.getOptions().trim().length() > 0) {
                    shoppingCartItemHolder.title.setText(shoppingCartItemHolder.title.getText() + "\n" + cartProduct1.getOptions().trim());
                }

                shoppingCartItemHolder.txtAllMark.setVisibility(View.GONE);
                shoppingCartItemHolder.price_strike.setVisibility(View.GONE);





                String strCheck = "";

                if (cartProduct1.getFlashSalesArrayList().size() > 0){

                    if(cartProduct1.getFlashSalesArrayList().get(0).getAvailable_quantity() > cartProduct1.getFlashSalesArrayList().get(0).getReserved_quantity()){
                        strCheck  = cartProduct1.getFlashSalesArrayList().get(0).getDiscount() + "% " ;

                        shoppingCartItemHolder.txtAllMark.setText(strCheck);
                        shoppingCartItemHolder.price_strike.setText(formatter.format(cartProduct1.getPrice() * cartProduct1.getCount())+" "+
                                resources.getString(R.string.currency));

                        shoppingCartItemHolder.price.setText(formatter.format(cartProduct1.getDiscount() * cartProduct1.getCount())+" "+
                                resources.getString(R.string.currency));

                        shoppingCartItemHolder.txtAllMark.setVisibility(View.VISIBLE);
                        shoppingCartItemHolder.price_strike.setVisibility(View.VISIBLE);
                        shoppingCartItemHolder.price_strike.setPaintFlags(shoppingCartItemHolder.price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }


                }




                shoppingCartItemHolder.imgPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("product_id", String.valueOf(cartProduct1.getId()));
                            FlurryAgent.logEvent("Change Product Quantity", mix);
                        } catch (Exception e) {
                        }

                        cartProduct1.setCount(cartProduct1.getCount() +1);
                        shoppingCartItemHolder.price.setText(cartProduct1.getCount() * cartProduct1.getPrice()+" "+
                                resources.getString(R.string.currency));
                        shoppingCartItemHolder.quantity.setText(cartProduct1.getCount()+"");
                        databaseAdapter.updatetOrderItem(cartProduct1.getId(), cartProduct1.getCount());

                        int toShowPrice = 0;

                        int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                        Product proPoint = databaseAdapter.getShoppingCartFooter();

                        if(((proPoint.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                            toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                        }
                        else{
                            toShowPrice = (proPoint.getPoint_usage() * percent ) / 100  ;
                        }

                        if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
                            prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);

                        }
                        else{
                            prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);
                        }

                        ShoppingCartActivity refresh = (ShoppingCartActivity) activity;
                        refresh.changeProduct();

                    }
                });
                shoppingCartItemHolder.imgMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(cartProduct1.getCount() > 1){

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("product_id", String.valueOf(cartProduct1.getId()));
                                FlurryAgent.logEvent("Change Product Quantity", mix);
                            } catch (Exception e) {
                            }
                            cartProduct1.setCount(cartProduct1.getCount() - 1);
                            shoppingCartItemHolder.price.setText(cartProduct1.getCount() * cartProduct1.getPrice()+" "+
                                    resources.getString(R.string.currency));
                            shoppingCartItemHolder.quantity.setText(cartProduct1.getCount()+"");
                            databaseAdapter.updatetOrderItem(cartProduct1.getId(), cartProduct1.getCount());

                            int toShowPrice = 0;

                            int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                            Product proPoint = databaseAdapter.getShoppingCartFooter();

                            if(((proPoint.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                                toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                            }
                            else{
                                toShowPrice = (proPoint.getPoint_usage() * percent ) / 100  ;
                            }

                            if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
                                prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);

                            }
                            else{
                                prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);
                            }

                            ShoppingCartActivity refresh = (ShoppingCartActivity) activity;
                            refresh.changeProduct();
                        }else{

                            try {
                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("product_id", String.valueOf(cartProduct1.getId()));
                                FlurryAgent.logEvent("Remove Product From Cart", mix);
                            } catch (Exception e) {
                            }
                            int result = databaseAdapter.deleteOrder(cartProduct1.getId());


                            int toShowPrice = 0;

                            int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                            Product proPoint = databaseAdapter.getShoppingCartFooter();

                            if(((proPoint.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                                toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                            }
                            else{
                                toShowPrice = (proPoint.getPoint_usage() * percent ) / 100  ;
                            }

                            if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
                                prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);

                            }
                            else{
                                prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);
                            }

                            ShoppingCartActivity shop = (ShoppingCartActivity) activity;
                            shop.changeProduct();

                        }
                    }
                });



                shoppingCartItemHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_bottom_remove);

                        try {
                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "cart");
                            mix.put("product_id", String.valueOf(cartProduct1.getId()));
                            FlurryAgent.logEvent("Click Product Remove Icon", mix);
                        } catch (Exception e) {
                        }

                        dialog.setCancelable(true);


                        CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
                        CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
                        CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                        CustomTextView text = (CustomTextView) dialog.findViewById(R.id.titleRemove);

                        cancel.setTypeface(cancel.getTypeface(), Typeface.BOLD);
                        confirm.setTypeface(confirm.getTypeface(), Typeface.BOLD);
                        title.setTypeface(title.getTypeface(), Typeface.BOLD);
                        text.setTypeface(text.getTypeface(), Typeface.BOLD);

                        title.setText(String.format(resources.getString(R.string.dialog_delete_title_product), cartProduct1.getTitle()));
                        text.setText(resources.getString(R.string.dialog_delete_title_confirm));


                        cancel.setText(resources.getString(R.string.dialog_delete_cancel));
                        confirm.setText(resources.getString(R.string.dialog_delete_title_confirm_yes));

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("product_id", String.valueOf(cartProduct1.getId()));
                                    FlurryAgent.logEvent("Remove Product From Cart", mix);
                                } catch (Exception e) {
                                }
                                int result = databaseAdapter.deleteOrder(cartProduct1.getId());

                                int toShowPrice = 0;

                                int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                                Product proPoint = databaseAdapter.getShoppingCartFooter();

                                if(((proPoint.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                                    toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                                }
                                else{
                                    toShowPrice = (proPoint.getPoint_usage() * percent ) / 100  ;
                                }

                                if (!prefs.getBooleanPreference(ALREADY_USE_POINT)){
                                    prefs.saveIntPerferences(CHECK_USE_POINT, totalPointToServer);

                                }
                                else{
                                    prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);
                                }

                                ShoppingCartActivity shop = (ShoppingCartActivity) activity;
                                shop.changeProduct();

                                dialog.dismiss();
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
                });



                if (cartProduct1.getCount() > 9){
                    shoppingCartItemHolder.quantity.getLayoutParams().width = (int) ( display.getWidth() * 3/11 )  *  3 /7;
                    shoppingCartItemHolder.imgMinus.getLayoutParams().width = (int) ( display.getWidth() * 3/11)  *  2/7;
                    shoppingCartItemHolder.imgPlus.getLayoutParams().width = (int) ( display.getWidth() * 3/11)  *  2/7;
                }else{
                    shoppingCartItemHolder.quantity.getLayoutParams().width = (int) ( display.getWidth() * 3/11) / 3;
                    shoppingCartItemHolder.imgMinus.getLayoutParams().width = (int) ( display.getWidth() * 3/11) / 3;
                    shoppingCartItemHolder.imgPlus.getLayoutParams().width = (int) ( display.getWidth() * 3/11) / 3;
                }


                break;
            }*/

            case VIEW_TYPE_STEP_ONE_TOP:{
                StepOneTopHolder stepOneTopHolder = (StepOneTopHolder) parentHolder;

                TownShipObject townShipObject1 = (TownShipObject) universalList.get(position);

                if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)){
                    stepOneTopHolder.delivery_price_text.setVisibility(View.GONE);
                    stepOneTopHolder.switch1.setSelected(true);
                }else{
                    stepOneTopHolder.switch1.setSelected(false);
                    stepOneTopHolder.linearUserAddress.setVisibility(View.VISIBLE);
                    if(townShipObject1.getAddressesList().size() == 0){
                        stepOneTopHolder.delivery_price_text.setVisibility(View.GONE);
                    }
                    else{
                        stepOneTopHolder.delivery_price_text.setVisibility(View.VISIBLE);
                    }
                }

                if (prefs.getBooleanPreference(PICK_UP_ON_OFF))
                    stepOneTopHolder.layout_pickup.setVisibility(View.VISIBLE);
                else
                    stepOneTopHolder.layout_pickup.setVisibility(View.GONE);



                stepOneTopHolder.address_title.setText(resources.getString(R.string.add_edit_address));
                stepOneTopHolder.txtAddress.setText("Address");
                stepOneTopHolder.txtPickupTitle.setText("SHOWROOM PICK-UP");
                stepOneTopHolder.txtPickupTitle.setTypeface(stepOneTopHolder.txtPickupTitle.getTypeface(), Typeface.BOLD);

                if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)){
                    stepOneTopHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_on));
                }else{
                    stepOneTopHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_off));
                }


                stepOneTopHolder.switch1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)){
                            chooseShowroomPickUp(false);

                            stepOneTopHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_on));
                        }else{
                            prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP,false);
                            prefs.saveIntPerferences(TEMP_PICK_UP_ID, -1);
                            stepOneTopHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_off));
                        }
                    }
                });

                stepOneTopHolder.linearAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(activity, ActivityEditAddress.class);
                        if(databaseAdapter.getCustomerAddressList().size() == 0){

                            Log.e(TAG, "onClick: /////////////////  size is zero " );

                            prefs.saveBooleanPreference(NO_ADDRESS, true);
                            activity.startActivity(intent);
                            activity.finish();
                        }else{

                            Log.e(TAG, "onClick: /////////////////  size is not zero " );

                            prefs.saveBooleanPreference(NO_ADDRESS, false);
                            activity.startActivity(intent);


                        }

                    }
                });

                stepOneTopHolder.delivery_price_text.setText(resources.getString(R.string.check_delivery_amount));
                stepOneTopHolder.delivery_price_text.setPaintFlags(stepOneTopHolder.delivery_price_text.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                stepOneTopHolder.delivery_price_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeliveryListDialog();
                    }
                });


                break;

            }
            case VIEW_TYPE_SHOPPING_CART_SUMMARY:{

                final FooterSummaryHolder shoppingFooter = (FooterSummaryHolder) parentHolder;

                final CustomerProductList pro = (CustomerProductList) universalList.get(position);

                shoppingFooter.summary_title.setText(resources.getString(R.string.more_shopping));
                shoppingFooter.point_text.setText(resources.getString(R.string.point_use_text));
                shoppingFooter.title_use_point.setText(resources.getString(R.string.point_switch_text));



                shoppingFooter.total_price_text.setText(resources.getString(R.string.total_product_price));

                shoppingFooter.total_price.setText(formatter.format((prefs.getIntPreferences(SP_CUSTOMER_TOTAL) - prefs.getIntPreferences(CHECK_USE_POINT))) + " "
                        + activity.getResources().getString(R.string.currency));


                if (prefs.getIntPreferences(SP_CUSTOMER_FLASH_DISCOUNT) > 0){

                    shoppingFooter.flesh_layout.setVisibility(View.VISIBLE);
                    shoppingFooter.flash_discount_text.setText(resources.getString(R.string.flash_sale_discount));
                    shoppingFooter.flash_discount.setText("- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_FLASH_DISCOUNT) )+" "+resources.getString(R.string.currency));

                }else{

                    shoppingFooter.flesh_layout.setVisibility(View.GONE);

                }

                if (prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_DISCOUNT) > 0){

                    shoppingFooter.product_discount_layout.setVisibility(View.VISIBLE);
                    shoppingFooter.product_discount.setText("- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_DISCOUNT))+" "+resources.getString(R.string.currency));
                    shoppingFooter.product_discount_text.setText(resources.getString(R.string.product_discount));


                }else{
                    shoppingFooter.product_discount_layout.setVisibility(View.GONE);

                }


                if (prefs.getIntPreferences(SP_CUSTOMER_MEMBER_DISCOUNT) > 0){

                    shoppingFooter.member_layout.setVisibility(View.VISIBLE);
                    shoppingFooter.member_discount_text.setText(resources.getString(R.string.member_discount));
                    shoppingFooter.member_discount.setText("- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_MEMBER_DISCOUNT) )+" "+resources.getString(R.string.currency));

                }else{

                    shoppingFooter.member_layout.setVisibility(View.GONE);

                }




                shoppingFooter.linearShopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });


                if(pro.getGifts().length > 0){
                    shoppingFooter.gift_layout.removeAllViews();

                    final LinearLayout.LayoutParams parms1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    parms1.gravity = Gravity.CENTER;
                    parms1.setMargins(5, 5, 5, 5);
                    LinearLayout eachLayout = new LinearLayout(activity);
                    eachLayout.setPadding(15, 15, 15, 15);
                    eachLayout.setLayoutParams(parms1);
                    eachLayout.setOrientation(LinearLayout.VERTICAL);
                    int discount_gift = 1;



                    for(int i = 0; i < pro.getGifts().length; i++){

                        String info = pro.getGifts()[i];

                        LinearLayout phoneLayout = new LinearLayout(activity);
                        phoneLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
                        phoneLayout.setPadding(0,10,0,10);

                        TextView textView1 = new TextView(activity);
                        textView1.setGravity(Gravity.CENTER_HORIZONTAL);
                        textView1.setHeight(20);
                        textView1.setWidth(20);

                        textView1.setBackgroundColor(activity.getResources().getColor(R.color.coloredInactive));

                        CustomTextView textView = new CustomTextView(activity);
                        textView.setTextSize(14);
                        textView.setTextColor(activity.getResources().getColor(R.color.text));
                        textView.setPadding(30, 30, 30, 30);
                        textView.setGravity(Gravity.LEFT);
                        textView.setText( info);

                        phoneLayout.setBackground(activity.getResources().getDrawable(R.drawable.border_white));


                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(10,10,10,10);
                        phoneLayout.setLayoutParams(params);

                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        params2.setMargins(20,20,0,0);
                        textView1.setLayoutParams(params2);



                        phoneLayout.addView(textView1);
                        phoneLayout.addView(textView);
                        eachLayout.addView(phoneLayout);

                    }

                    if(discount_gift != 0){
                        shoppingFooter.gift_layout.addView(eachLayout);
                        shoppingFooter.gift_layout.setVisibility(View.VISIBLE);
                    }


                }


                if (!prefs.getBooleanPreference(ALREADY_USE_POINT) ) {
                    shoppingFooter.radioPoint.setChecked(false);
                    prefs.saveIntPerferences(CHECK_USE_POINT, 0);
                    shoppingFooter.point_use.setText("- "+formatter.format( prefs.getIntPreferences(CHECK_USE_POINT))+ " " + resources.getString(R.string.currency));
                    shoppingFooter.point_use_layout.setVisibility(View.VISIBLE);
                }
                else {
                    shoppingFooter.radioPoint.setChecked(true);
                    shoppingFooter.point_use.setText("- "+formatter.format( prefs.getIntPreferences(CHECK_USE_POINT))+ " " + resources.getString(R.string.currency));
                    shoppingFooter.point_use_layout.setVisibility(View.VISIBLE);
                    shoppingFooter.total_price.setText(formatter.format(prefs.getIntPreferences(SP_CUSTOMER_TOTAL) - prefs.getIntPreferences(CHECK_USE_POINT))
                            +" "+resources.getString(R.string.currency));

                    //prefs.saveIntPerferences(SP_TEMP_TOTAL, totalPriceToServer - prefs.getIntPreferences(CHECK_USE_POINT));
                }

                if (prefs.getIntPreferences(SP_USER_POINT) > 0){
                    shoppingFooter.point_layout.setVisibility(View.VISIBLE);
                    shoppingFooter.point_use_layout.setVisibility(View.VISIBLE);
                }else{
                    shoppingFooter.point_layout.setVisibility(View.GONE);
                    shoppingFooter.point_use_layout.setVisibility(View.GONE);
                }

                shoppingFooter.radioPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog pointDialog = new Dialog(activity);
                        pointDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        pointDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        pointDialog.setContentView(R.layout.dialog_point_usage);

                        pointDialog.setCanceledOnTouchOutside(true);
                        Window window = pointDialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        CustomTextView title = pointDialog.findViewById(R.id.title);
                        CustomTextView point_text = pointDialog.findViewById(R.id.point_text);
                        CustomEditText editText = pointDialog.findViewById(R.id.editText);
                        CustomButton dialog_confirm = pointDialog.findViewById(R.id.dialog_confirm);


                        int toShowPrice = 0;

                        int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                        for (int i=0; i< pro.getProuduts().size(); i++){
                            if (pro.getProuduts().get(i).getPointUsage() == 0){
                                toShowPrice += pro.getProuduts().get(i).getDiscountedPrice();
                            }
                        }
                        if(((toShowPrice * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                            toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                        }
                        else{
                            toShowPrice = (toShowPrice * percent ) / 100  ;
                        }




                        final int finalToShowPrice = toShowPrice;

                        title.setText(resources.getString(R.string.point_limit_text));
                        dialog_confirm.setText(resources.getString(R.string.point_button));


                        InputMethodManager imm = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                        point_text.setText(toShowPrice +" " +  resources.getString(R.string.point));

                        editText.setText(toShowPrice + "");
                        editText.setSelectAllOnFocus(true);


                        dialog_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(editText.getText().toString().trim().length() > 0){
                                    if(finalToShowPrice >= Integer.parseInt(editText.getText().toString())){

                                        shoppingFooter.point_use.setText("-"+formatter.format(Integer.parseInt(editText.getText().toString()))+ " " + resources.getString(R.string.currency));
                                        pointDialog.dismiss();
                                        shoppingFooter.total_price.setText(formatter.format(prefs.getIntPreferences(SP_CUSTOMER_TOTAL) - Integer.parseInt(editText.getText().toString()))
                                                +" "+resources.getString(R.string.currency));

                                        //prefs.saveIntPerferences(SP_TEMP_TOTAL, totalPriceToServer - Integer.parseInt(editText.getText().toString()));

                                        prefs.saveIntPerferences(CHECK_USE_POINT, Integer.parseInt(editText.getText().toString()));
                                        prefs.saveBooleanPreference(ALREADY_USE_POINT, true);
                                        shoppingFooter.radioPoint.setChecked(true);



                                        if(activity.getLocalClassName().contains("ShoppingCartActivity")) {


                                            ShoppingCartActivity pro = (ShoppingCartActivity) activity;
                                            pro.showTotalPrice();
                                        }

                                    }else{
                                        ToastHelper.showToast(activity, resources.getString(R.string.point_grather_current));
                                    }
                                }else{
                                    ToastHelper.showToast(activity, resources.getString(R.string.point_grather_current));

                                }
                            }
                        });


                        if (!prefs.getBooleanPreference(ALREADY_USE_POINT))
                            shoppingFooter.radioPoint.setChecked(false);




                        pointDialog.show();
                    }
                });

                shoppingFooter.point_use_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog pointDialog = new Dialog(activity);
                        pointDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        pointDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        pointDialog.setContentView(R.layout.dialog_point_usage);

                        pointDialog.setCanceledOnTouchOutside(true);
                        Window window = pointDialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        CustomTextView title = pointDialog.findViewById(R.id.title);
                        CustomTextView point_text = pointDialog.findViewById(R.id.point_text);
                        CustomEditText editText = pointDialog.findViewById(R.id.editText);
                        CustomButton dialog_confirm = pointDialog.findViewById(R.id.dialog_confirm);


                        int toShowPrice = 0;
                        int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

                        for (int i=0; i< pro.getProuduts().size(); i++){
                            if (pro.getProuduts().get(i).getPointUsage() == 0){
                                toShowPrice += pro.getProuduts().get(i).getDiscountedPrice();
                            }
                        }
                        if(((toShowPrice * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                            toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
                        }
                        else{
                            toShowPrice = (toShowPrice * percent ) / 100  ;
                        }


                        final int finalToShowPrice = toShowPrice;

                        title.setText(resources.getString(R.string.point_limit_text));
                        dialog_confirm.setText(resources.getString(R.string.point_button));


                        InputMethodManager imm = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                        point_text.setText(toShowPrice +" " +  resources.getString(R.string.point));
                        editText.setText(toShowPrice + "");
                        editText.setSelectAllOnFocus(true);


                        dialog_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(editText.getText().toString().trim().length() > 0){
                                    if(finalToShowPrice >= Integer.parseInt(editText.getText().toString())){


                                        shoppingFooter.radioPoint.setChecked(true);
                                        // shoppingFooter.radioPoint.setEnabled(true);



                                        shoppingFooter.point_use.setText("-"+formatter.format(Integer.parseInt(editText.getText().toString()))+ " " + resources.getString(R.string.currency));
                                        pointDialog.dismiss();
                                        shoppingFooter.total_price.setText(formatter.format(prefs.getIntPreferences(SP_CUSTOMER_TOTAL)  - Integer.parseInt(editText.getText().toString()))
                                                +" "+resources.getString(R.string.currency));
                                       /// prefs.saveIntPerferences(SP_TEMP_TOTAL, totalPriceToServer - Integer.parseInt(editText.getText().toString()));
                                        prefs.saveIntPerferences(CHECK_USE_POINT, Integer.parseInt(editText.getText().toString()));
                                        prefs.saveBooleanPreference(ALREADY_USE_POINT, true);


                                        if(activity.getLocalClassName().contains("ShoppingCartActivity")) {


                                            ShoppingCartActivity pro = (ShoppingCartActivity) activity;
                                            pro.showTotalPrice();
                                        }


                                    }else{
                                        ToastHelper.showToast(activity, resources.getString(R.string.point_grather_current));
                                    }
                                }else{
                                    ToastHelper.showToast(activity, resources.getString(R.string.point_grather_current));

                                }
                            }
                        });



                        pointDialog.show();
                    }
                });

                break;


            }

            case VIEW_TYPE_PICK_UP_SHOP_ITEM:{
                ShopLocation spLocation = (ShopLocation) universalList.get(position);
                StepOneItemHolder spLocHolder = (StepOneItemHolder) parentHolder;
                spLocHolder.imgDelete.setVisibility(View.GONE);
                spLocHolder.chkAddress.setVisibility(View.GONE);
                spLocHolder.img_arrow.setVisibility(View.VISIBLE);
                spLocHolder.name_text.setText("SHOWROOM PICK-UP");
                spLocHolder.addressText.setText(spLocation.getName());
                spLocHolder.name_text.setTextColor(activity.getResources().getColor(R.color.text));
                spLocHolder.name_text.setTypeface(spLocHolder.name_text.getTypeface(), Typeface.BOLD);
                spLocHolder.linearAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseShowroomPickUp(false);
                    }
                });


                break;
            }
            case VIEW_TYPE_STEP_ONE_ITEM:{
                Addresses addressItem = (Addresses) universalList.get(position);
                StepOneItemHolder stepOneItemHolder = (StepOneItemHolder) parentHolder;

                stepOneItemHolder.name_text.setText(prefs.getStringPreferences(SP_USER_NAME));
                stepOneItemHolder.name_text.setTypeface(stepOneItemHolder.name_text.getTypeface(), Typeface.BOLD);
                stepOneItemHolder.addressText.setText(addressItem.getAddresses().trim());


                if (addressItem.isSelected()) {
                    stepOneItemHolder.chkAddress.setChecked(true);
                    prefs.saveStringPreferences(TEMP_ADDRESS_TEXT, addressItem.getAddresses());
                    prefs.saveStringPreferences(TEMP_ADDRESS_TOWNSHIP, addressItem.getTownship_name());
                    prefs.saveIntPerferences(TEMP_ADDRESS_ID, addressItem.getTownShipID());

                }
                else
                    stepOneItemHolder.chkAddress.setChecked(false);

                if (addressItem.isCheckable()){
                    stepOneItemHolder.chkAddress.setVisibility(View.VISIBLE);
                    stepOneItemHolder.imgDelete.setVisibility(View.GONE);
                }else{
                    stepOneItemHolder.chkAddress.setVisibility(View.GONE);
                    stepOneItemHolder.imgDelete.setVisibility(View.VISIBLE);

                }

                stepOneItemHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_logout);


                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                        window.setAttributes(wlp);

                        CustomButton cancel = (CustomButton) dialog.findViewById(R.id.dialog_delete_cancel);
                        CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
                        CustomTextView title = (CustomTextView) dialog.findViewById(R.id.title);
                        CustomTextView text = (CustomTextView) dialog.findViewById(R.id.text);

                        title.setText(resources.getString(R.string.dialog_delete_title));
                        text.setText(resources.getString(R.string.delete_address_text));
                        cancel.setText(resources.getString(R.string.dialog_delete_cancel));
                        confirm.setText(resources.getString(R.string.dialog_delete_confirm));

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                        Request.Method.DELETE, constantAddressList + "/" + addressItem.getId() , null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try{

                                                    if (response.getInt("status")  == 1){

                                                        databaseAdapter.deleteCustomerAddress(addressItem.getId());

                                                        if(addressItem.getId() == prefs.getIntPreferences(TEMP_FAKE_ADDRESS_ID))
                                                            prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID, -1);


                                                        prefs.saveBooleanPreference(TEMP_ADD_ADDRESS, true);
                                                        universalList.remove(position);
                                                        dialog.dismiss();
                                                        notifyDataSetChanged();

                                                    }
                                                    else{

                                                        ToastHelper.showToast(activity, resources.getString(R.string.search_error));
                                                    }
                                                }catch (Exception e){

                                                }


                                            }
                                        }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Log.e(TAG, "onErrorResponse: "  + error.getMessage() );
                                        ToastHelper.showToast(activity, resources.getString(R.string.search_error));
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

                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });

                stepOneItemHolder.addressText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (addressItem.isCheckable()){

                            addressItem.setSelected(true);

                            prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);
                            prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);
                            prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
                            prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP, false);
                            prefs.saveIntPerferences(TEMP_PICK_UP_ID, -1);
                            prefs.saveIntPerferences(TEMP_DELI_ID, -1);
                            int checkID = 0;
                            for (int i=0; i < universalList.size(); i++){

                                if (universalList.get(i).getPostType().equals(STEP_ONE_ITEM))
                                {

                                    Addresses fakeAddress =(Addresses) universalList.get(i);
                                    if (addressItem.getId() == fakeAddress.getId()) {


                                        checkID = StoreHelper.getStoreIdbyTownShipId(activity, addressItem.getTownShipID());
                                        fakeAddress.setSelected(true);
                                        prefs.saveStringPreferences(TEMP_ADDRESS_TEXT, addressItem.getAddresses());
                                        prefs.saveStringPreferences(TEMP_ADDRESS_TOWNSHIP, addressItem.getTownship_name());
                                        prefs.saveIntPerferences(TEMP_ADDRESS_ID, addressItem.getTownShipID());

                                        StoreHelper.saveDeliverPreferences(activity, addressItem.getDelivery_id());
                                    }
                                    else {
                                        fakeAddress.setSelected(false);
                                    }
                                    universalList.remove(i);
                                    universalList.add(i, fakeAddress);


                                }
                            }



                            if (checkID != -1) {
                                checkDelivery(checkID);
                                prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, true);
                            }
                            else{
                                prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, false);
                                prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
                                prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
                                prefs.saveBooleanPreference(TEMP_SAME_DAY, false);

                                int index = -1;
                                for (int i=0; i < universalList.size(); i++){
                                    if (universalList.get(i).getPostType().equals(PICK_UP_SHOP_ITEM)){
                                        index = i;
                                    }
                                }

                                if (index != -1 ){
                                    universalList.remove(index);
                                }

                                notifyDataSetChanged();

                            }



                        }
                    }
                });

                stepOneItemHolder.chkAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int checkID = 0 ;

                        addressItem.setSelected(true);

                        prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);
                        prefs.saveBooleanPreference(TEMP_SAME_DAY, false);
                        prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
                        prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP, false);
                        prefs.saveIntPerferences(TEMP_PICK_UP_ID, -1);
                        prefs.saveIntPerferences(TEMP_DELI_ID, -1);


                        for (int i=0; i < universalList.size(); i++){

                            if (universalList.get(i).getPostType().equals(STEP_ONE_ITEM))
                            {

                                Addresses fakeAddress =(Addresses) universalList.get(i);
                                if (addressItem.getId() == fakeAddress.getId()) {
                                    checkID = StoreHelper.getStoreIdbyTownShipId(activity, addressItem.getTownShipID());
                                    fakeAddress.setSelected(true);
                                    prefs.saveStringPreferences(TEMP_ADDRESS_TEXT, addressItem.getAddresses());
                                    prefs.saveStringPreferences(TEMP_ADDRESS_TOWNSHIP, addressItem.getTownship_name());
                                    prefs.saveIntPerferences(TEMP_ADDRESS_ID, addressItem.getTownShipID());

                                    StoreHelper.saveDeliverPreferences(activity,addressItem.getDelivery_id());
                                }
                                else {
                                    fakeAddress.setSelected(false);
                                }
                                universalList.remove(i);
                                universalList.add(i, fakeAddress);


                            }
                        }

                        if (checkID != -1) {
                            checkDelivery(checkID);
                            prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, true);
                        }
                        else{
                            prefs.saveBooleanPreference(DELIVERY_STORE_ID_MINUS, false);
                            prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
                            prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
                            prefs.saveBooleanPreference(TEMP_SAME_DAY, false);

                            int index = -1;
                            for (int i=0; i < universalList.size(); i++){
                                if (universalList.get(i).getPostType().equals(PICK_UP_SHOP_ITEM)){
                                    index = i;
                                }
                            }

                            if (index != -1 ){
                                universalList.remove(index);
                            }

                            notifyDataSetChanged();

                        }



                    }
                });


                break;
            }
            case VIEW_TYPE_STEP_TWO_MAIN :{

                MainPaymentHolder mainPaymentHolder = (MainPaymentHolder) parentHolder;
                MainPaymentObj mainPaymentObj = (MainPaymentObj) universalList.get(position);
                ArrayList<UniversalPost> mainGridPayments = new ArrayList<>();
                if (mainPaymentObj.getPaymentObjectList().size()  > 0){
                    mainPaymentHolder.title.setText("Payment methods");
                    mainPaymentHolder.title.setTypeface(mainPaymentHolder.title.getTypeface(), Typeface.BOLD);

                    for(int i = 0 ; i < mainPaymentObj.getPaymentObjectList().size() ; i++){
                        PaymentObject paymentObject = mainPaymentObj.getPaymentObjectList().get(i);

                        if (prefs.getIntPreferences(TEMP_CHOOSE_PAYMENT_ID) == paymentObject.getId()) {
                            paymentObject.setSelected(true);
                            mainPaymentHolder.txtPayment.setText(paymentObject.getName() + " Service Fee");


                            //Product pro = databaseAdapter.getShoppingCartFooter();
                            int total = 0;

                            if(prefs.getIntPreferences(SP_CUSTOMER_TOTAL) >= prefs.getIntPreferences(DELIVERY_FREE_AMOUNT)){
                                total = prefs.getIntPreferences(SP_CUSTOMER_TOTAL);
                            }
                            else{
                                total = prefs.getIntPreferences(SP_CUSTOMER_TOTAL)  +prefs.getIntPreferences(DELIVERY_PRICE);
                            }
                            total = total   - prefs.getIntPreferences(CHECK_USE_POINT);


                            int commissionPrice = (int) (total  * paymentObject.getCommission() / 100);
                            mainPaymentHolder.txtPercent.setText(commissionPrice + " "+ resources.getString(R.string.currency) );
                            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                mainPaymentHolder.txtPercent.setText(Html.fromHtml(
                                        String.format(resources.getString(R.string.currency_red), commissionPrice+ " ")  , Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                mainPaymentHolder.txtPercent.setText(Html.fromHtml(String.format(resources.getString(R.string.currency_red),commissionPrice + "")));
                            }*/
                        }
                        else
                            paymentObject.setSelected(false);

                        paymentObject.setPostType(STEP_TWO_PAYMENT_ITEM);


                        mainGridPayments.add(paymentObject);



                    }


                    gridAdapter = new UniversalAdapter(activity, mainGridPayments);
                    layoutManager = new GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false);
                    if (mainPaymentHolder.recyclerView != null) {
                        mainPaymentHolder.recyclerView.setLayoutManager(layoutManager);
                        mainPaymentHolder.recyclerView.setAdapter(gridAdapter);
                    }

                    // ((LinearLayout.LayoutParams) mainPaymentHolder.recyclerView.getLayoutParams()).setMargins(10, 10, 10, 10);


                }


                mainPaymentHolder.recyclerView.getItemAnimator().setChangeDuration(0);
                mainPaymentHolder.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
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
            case VIEW_TYPE_EDIT_ADDRESS_TITLE:{

                TownShipObject townShipObject = (TownShipObject) universalList.get(position);
                EditAddressHolder editAddressHolder = (EditAddressHolder) parentHolder;
                editAddressHolder.txtPickupTitle.setText("SHOWROOOM PICK-UP");



                if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)){
                    editAddressHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_on));
                }else{
                    editAddressHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_off));
                }



                if (townShipObject.getPrid() == 0) {
                    editAddressHolder.linearDeliverTo.setVisibility(View.VISIBLE);
                    editAddressHolder.linearAddAddress.setVisibility(View.GONE);


                }
                else if (townShipObject.getPrid() == 1) {
                    editAddressHolder.linearDeliverTo.setVisibility(View.GONE);
                    editAddressHolder.linearAddAddress.setVisibility(View.VISIBLE);
                }

                ArrayList<Addresses> addressesArrayList1 = databaseAdapter.getCustomerAddressList();
                if (addressesArrayList1.size() == 0 && prefs.getBooleanPreference(PICK_UP_ON_OFF)){
                    editAddressHolder.linearPickUp.setVisibility(View.VISIBLE);

                }else{
                    editAddressHolder.linearPickUp.setVisibility(View.GONE);
                }

                editAddressHolder.switch1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)){
                            chooseShowroomPickUp(true);

                            editAddressHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_on));
                        }else{
                            prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP,false);
                            prefs.saveIntPerferences(TEMP_PICK_UP_ID, -1);
                            editAddressHolder.switch1.setImageDrawable(activity.getResources().getDrawable(R.drawable.switch_off));
                        }
                    }
                });

                editAddressHolder.txtTownship.setText(resources.getString(R.string.choose_township));
                editAddressHolder.add_address_text.setText(resources.getString(R.string.add_address));
                editAddressHolder.txtContinue.setText(resources.getString(R.string.phone_number_continue));
                editAddressHolder.delivery_price_text.setText(resources.getString(R.string.check_delivery_amount));
                editAddressHolder.edAddress.setHint(resources.getString(R.string.fill_address));
                editAddressHolder.delivery_price_text.setPaintFlags(editAddressHolder.delivery_price_text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                final int[] townshipID = {-810};

                editAddressHolder.delivery_price_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeliveryListDialog();
                    }
                });

                editAddressHolder.linearTownship.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (townShipList.size() > 0){
                            Dialog mBottomSheetDialog = new Dialog(activity);
                            mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            mBottomSheetDialog.setContentView(R.layout.bottom_dialog_pickup_shop);
                            mBottomSheetDialog.setCanceledOnTouchOutside(true);

                            Window window = mBottomSheetDialog.getWindow();
                            WindowManager.LayoutParams wlp = window.getAttributes();
                            wlp.gravity = Gravity.CENTER;
                            wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                            window.setAttributes(wlp);

                            LinearLayout linearTownship = mBottomSheetDialog.findViewById(R.id.linearTownship);
                            linearTownship.setVisibility(View.VISIBLE);
                            CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
                            CustomTextView txtYgn = mBottomSheetDialog.findViewById(R.id.txtYgn);
                            CustomTextView txtOther = mBottomSheetDialog.findViewById(R.id.txtOther);
                            ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);
                            LinearLayout linearAdd = mBottomSheetDialog.findViewById(R.id.linearAdd);



                            title.setText("Choose Township");
                            title.setTypeface(title.getTypeface(), Typeface.BOLD);

                            txtOther.setText(townShipList.get(0).getName());
                            txtYgn.setText(townShipList.get(1).getName());



                            txtOther.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBottomSheetDialog.dismiss();
                                    townshipID[0] = townShipList.get(0).getId();

                                    editAddressHolder.txtTownship.setText(townShipList.get(0).getName());
                                }
                            });



                            for (int i=2; i < townShipList.size(); i++){
                                TownShip shopLocation = townShipList.get(i);

                                LinearLayout phoneLayout = new LinearLayout(activity);
                                phoneLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
                                phoneLayout.setPadding(0, 20, 0, 20);


                                LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                childParam1.weight = 0.1f;
                                CustomTextView price = new CustomTextView(activity);
                                price.setTextSize(14);
                                price.setPadding(10, 10, 10, 10);
                                price.setGravity(Gravity.CENTER);
                                price.setText(shopLocation.getName());
                                price.setLayoutParams(childParam1);



                                phoneLayout.addView(price);
                                phoneLayout.setWeightSum(1f);



                                linearAdd.addView(phoneLayout);

                                phoneLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mBottomSheetDialog.dismiss();

                                        townshipID[0] = shopLocation.getId();
                                        editAddressHolder.txtTownship.setText(shopLocation.getName());


                                    }
                                });


                            }


                            imgClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBottomSheetDialog.dismiss();
                                }
                            });
                            mBottomSheetDialog.show();
                        }
                    }
                });


                editAddressHolder.txtContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editAddressHolder.edAddress.getText().toString().trim().length() > 0
                                &&  townshipID[0] != -810 )
                        {


                            JSONObject uploadMessage = new JSONObject();
                            try {
                                uploadMessage.put("township_id", townshipID[0]);
                                uploadMessage.put("address", editAddressHolder.edAddress.getText().toString());


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                    Request.Method.POST, constantAddressList, uploadMessage,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            try{
                                                if (response.getInt("status") == 1) {

                                                    Addresses addresses = new Addresses();
                                                    addresses.setId(response.getInt("address_id"));
                                                    addresses.setTownShipID(response.getInt("township_id"));
                                                    addresses.setTownship_name(response.getString("township_name"));
                                                    addresses.setDelivery_id(response.getInt("delivery_id"));
                                                    addresses.setAddresses(response.getString("address"));


                                                    databaseAdapter.insertCustomerNewAdd(addresses);

                                                    List<Addresses> adList2 = new ArrayList<>();
                                                    adList2 = databaseAdapter.getCustomerAddressList();
                                                    prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID, response.getInt("address_id"));
                                                    prefs.saveBooleanPreference(TEMP_ADD_ADDRESS, true);

                                                    Log.e(TAG, "onResponse: --------------- "   + prefs.getIntPreferences(TEMP_FAKE_ADDRESS_ID) );



                                                    if (adList2.size() > 1){
                                                        activity.finish();
                                                    }
                                                    else{
                                                        Intent intent = new Intent(activity, ActivityStepOneCart.class);
                                                        activity.startActivity(intent);
                                                        activity.finish();
                                                    }
                                                }




                                            }catch (Exception e){

                                            }


                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Log.e(TAG, "onErrorResponse: " + error.getMessage());

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

                            AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");

                        } else {

                            ToastHelper.showToast(activity, resources.getString(R.string.order_dialog_no_fill));

                        }

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
            case VIEW_TYPE_CART_DETAIL_FOOTER:{

                final FooterHoloder footerHoloder = (FooterHoloder) parentHolder;
                break;
            }

            default:
                break;
        }
    }

    private void refreshClick(AppCompatActivity activity1) {
        if (activity1.getLocalClassName().contains("ActivityStepTwoCart")) {

            ActivityStepTwoCart pro = (ActivityStepTwoCart) activity1;
            pro.clickRefresh();
        }

    }

    private void checkDelivery(int deliveryId){


        JSONObject uploadOrder = new JSONObject();
        try {
            uploadOrder.put("store_location_id",    deliveryId);
            uploadOrder.put("products",    databaseAdapter.getCheckOrderToServer());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "uploadOrderAddress: "  + uploadOrder.toString() );



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantCheckDelivery  , uploadOrder,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if (response.getInt("same_day") == 1){
                                prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
                                prefs.saveBooleanPreference(TEMP_SAME_DAY, true);
                                prefs.saveBooleanPreference(TEMP_IS_TOMORROW, true);
                            }
                            else{
                                prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, true);
                                prefs.saveBooleanPreference(TEMP_SAME_DAY, false);
                                prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);
                            }

                            if (response.getInt("time_slot") == 1){
                                prefs.saveBooleanPreference(CAN_CHOOSE_TIMESLOT,true);
                            }
                            else
                                prefs.saveBooleanPreference(CAN_CHOOSE_TIMESLOT,false);

                            int index = -1;
                            for (int i=0; i < universalList.size(); i++){
                                if (universalList.get(i).getPostType().equals(PICK_UP_SHOP_ITEM)){
                                    index = i;
                                }
                            }

                            if (index != -1 ){
                                universalList.remove(index);
                            }
                            notifyDataSetChanged();

                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            /* *
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"Order123");
    }


    private void chooseShowroomPickUp(boolean isBackCheck){

        prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP, false);
        prefs.saveIntPerferences(TEMP_PICK_UP_ID, -1);

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(activity);

        View sheetView = activity.getLayoutInflater().inflate(R.layout.bottom_dialog_pickup_shop, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setCancelable(true);


        CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
        ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);
        LinearLayout linearAdd = mBottomSheetDialog.findViewById(R.id.linearAdd);



        title.setText("Choose Showroom");
        title.setTypeface(title.getTypeface(), Typeface.BOLD);

        ArrayList<ShopLocation> shopLocationArrayList = databaseAdapter.getPickupShops();


        for (int i=0; i < shopLocationArrayList.size(); i++){
            ShopLocation shopLocation = shopLocationArrayList.get(i);

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
            price.setText(shopLocation.getName());
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

            linearAdd.addView(phoneLayout);
            linearAdd.addView(space);



            phoneLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBottomSheetDialog.dismiss();

                    prefs.saveBooleanPreference(TEMP_CHOOSE_TIMING, false);
                    prefs.saveBooleanPreference(TEMP_SAME_DAY, false);
                    prefs.saveBooleanPreference(TEMP_IS_TOMORROW, false);


                    prefs.saveIntPerferences(TEMP_PICK_UP_ID, shopLocation.getId());

                    prefs.saveStringPreferences(TEMP_ADDRESS_TEXT, shopLocation.getAddress());
                    prefs.saveStringPreferences(TEMP_ADDRESS_TOWNSHIP, shopLocation.getName());
                    prefs.saveIntPerferences(TEMP_ADDRESS_ID, shopLocation.getId());

                    prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP, true);




                    if (isBackCheck) {
                        Log.e(TAG, "onClick: ****************************  isBackCheck"   );
                        Intent intent = new Intent(activity, ActivityStepOneCart.class);
                        activity.startActivity(intent);
                        activity.finish();

                    }else{
                        prefs.saveBooleanPreference(TEMP_ADD_ADDRESS, true);
                        ActivityStepOneCart refresh = (ActivityStepOneCart) activity;
                        refresh.choosePickUp();
                    }






                }
            });


        }


        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();

            }
        });
        mBottomSheetDialog.show();


    }

    private void setDeliTimeUI(OrderNowHolder spTopHolder, int deliTimeCheck){
        spTopHolder.txtRightNow.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
        spTopHolder.txtDeliveryRemark.setVisibility(View.GONE);

        switch (deliTimeCheck){
            case  1 :

                spTopHolder.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_blue));
                spTopHolder.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));

                spTopHolder.txtDeliveryRemark.setVisibility(View.VISIBLE);
                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.nine_am_html_long));
                spTopHolder.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_send)  , chooseDay+ "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.nine_am)) + "");
                break;

            case 4 :


                spTopHolder.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_blue));
                spTopHolder.txtDeliveryRemark.setVisibility(View.VISIBLE);
                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.six_pm_html_long));
                spTopHolder.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_send)  , chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.six_pm)) + "");
                break;


            case 3 :

                spTopHolder.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_blue));
                spTopHolder.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.txtDeliveryRemark.setVisibility(View.VISIBLE);
                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.three_pm_html_long));
                spTopHolder.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_send)  , chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.three_pm)) + "");
                break;

            case 2 :

                spTopHolder.nine_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.twelve_am.setBackgroundDrawable(resources.getDrawable(R.drawable.border_blue));
                spTopHolder.three_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.six_pm.setBackgroundDrawable(resources.getDrawable(R.drawable.border_grey));
                spTopHolder.txtDeliveryRemark.setVisibility(View.VISIBLE);
                prefs.saveStringPreferences(TEMP_DELI_TIME, resources.getString(R.string.twelve_am_html_long));
                spTopHolder.txtDeliveryRemark.setText(String.format(resources.getString(R.string.order_send)  , chooseDay + "-" + (chooseMonth + 1) + "-" + chooseYear, resources.getString(R.string.twelve_am)) + "");
                break;

        }
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
                   // deliveyLayout.addView(normalPrice);

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

}