package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
//import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kbzbank.payment.sdk.callback.CallbackResultActivity;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomEditText;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.DialogTypeFace;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.OrderIDs;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;
import com.rakshakhegde.stepperindicator.StepperIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.myatminsoe.mdetect.MDetect;

public class ActivityStepThreeCart extends AppCompatActivity implements Constant, ConstantVariable, ConstantsDB {

    private static final String TAG = "ActivityStepThreeCart";
    Resources resources;
    MyPreference prefs;
    AppCompatActivity activity;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    UniversalAdapter universalAdapter;
    RecyclerView.LayoutManager manager;
    DatabaseAdapter databaseAdapter;
    ImageLoader imageLoader;
    FirebaseAnalytics firebaseAnalytics;

    TextView deliverTitle;

    LinearLayout back_layout, linearDeliTime;
    CustomTextView title, txtContinue, txtTotalHeader,point_text,point_use,delivery_price_text,delivery_price,payment_text,payment_percent,
            product_discount_text,product_discount,flash_discount_text,flash_discount,member_discount_text,member_discount,
            total_price_text,total_price, name_text, name,address_text,address,delivery_date,date_text,delivery_time_text,delivery_time,
            txtPayName, txtPayPercent,item_text,item_price, promo_text, promo_percent,total_price_strike;
    StepperIndicator stepper_indicator;
    NetworkImageView img;
    ImageView imgPromo;

    int totalPriceToServer = 0, commissionPrice, finalPrice;
    DecimalFormat formatter = new DecimalFormat("#,###,###");

    String checkPromoText = "";
    int promoPrice = 0;
    int real = 0;
    int first_time_price = 0;
    int free_one_time = 0;

    RelativeLayout layoutBenefit;
    TextView txtBenefitTitle, txtBenefitText, txtBenefitUseNow, txtBenefitUseLater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_step_three_cart);

        Log.e(TAG, "onCreate: " );

        activity = ActivityStepThreeCart.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        databaseAdapter = new DatabaseAdapter(activity);
        imageLoader     = AppController.getInstance().getImageLoader();
        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);

        layoutBenefit = findViewById(R.id.layoutBenefit);
        txtBenefitTitle = findViewById(R.id.txtBenefitTitle);
        txtBenefitText = findViewById(R.id.txtBenefitText);
        txtBenefitUseNow = findViewById(R.id.txtBenefitUseNow);
        txtBenefitUseLater = findViewById(R.id.txtBenefitUseLater);

        back_layout = findViewById(R.id.back_layout);
        linearDeliTime = findViewById(R.id.linearDeliTime);
        title = findViewById(R.id.title);
        txtContinue = findViewById(R.id.txtContinue);
        stepper_indicator =  findViewById(R.id.stepper_indicator);
        txtTotalHeader =  findViewById(R.id.txtTotalHeader);
        point_text =  findViewById(R.id.point_text);
        point_use =  findViewById(R.id.point_use);

        product_discount_text =  findViewById(R.id.product_discount_text);
        product_discount =  findViewById(R.id.product_discount);
        flash_discount_text =  findViewById(R.id.flash_discount_text);
        flash_discount =  findViewById(R.id.flash_discount);
        member_discount_text =  findViewById(R.id.member_discount_text);
        member_discount =  findViewById(R.id.member_discount);
        delivery_price_text =  findViewById(R.id.delivery_price_text);
        delivery_price =  findViewById(R.id.delivery_price);
        payment_text =  findViewById(R.id.payment_text);
        payment_percent =  findViewById(R.id.payment_percent);
        total_price_text =  findViewById(R.id.total_price_text);
        total_price =  findViewById(R.id.total_price);
        name_text =  findViewById(R.id.name_text);
        name =  findViewById(R.id.name);
        address =  findViewById(R.id.address);
        address_text =  findViewById(R.id.address_text);
        delivery_date =  findViewById(R.id.delivery_date);
        date_text =  findViewById(R.id.date_text);
        delivery_time_text =  findViewById(R.id.delivery_time_text);
        delivery_time =  findViewById(R.id.delivery_time);
        img =  findViewById(R.id.img);
        txtPayName =  findViewById(R.id.txtPayName);
        txtPayPercent =  findViewById(R.id.txtPayPercent);
        item_text =  findViewById(R.id.item_text);
        item_price =  findViewById(R.id.item_price);
        promo_text =  findViewById(R.id.promo_text);
        promo_percent =  findViewById(R.id.promo_percent);
        imgPromo =  findViewById(R.id.imgPromo);

        deliverTitle = findViewById(R.id.deliverTitle);
        total_price_strike = findViewById(R.id.total_price_strike);




        ArrayList<Delivery> deliveryList = databaseAdapter.getDelivery();


        if(prefs.getBooleanPreference(TEMP_NORMAL)){
            first_time_price = prefs.getIntPreferences(TEMP_NORMAL_PRICE);
        }
        else{
            first_time_price = prefs.getIntPreferences(DELIVERY_PRICE);
        }


        for (int i=0; i < deliveryList.size();i++){
            if (deliveryList.get(i).getId() == prefs.getIntPreferences(DELIVERY_ID)) {
                if ( deliveryList.get(i).getFirst_time_free() == 1 || prefs.getIntPreferences(SP_CUSTOMER_TOTAL) >= prefs.getIntPreferences(DELIVERY_FREE_AMOUNT) )
                    first_time_price = 0;



                break;
            }

        }

        if(prefs.getIntPreferences(SP_ONE_TIME_FREE_DELIVERY) == 1) {
            layoutBenefit.setVisibility(View.VISIBLE);
            //txtBenefitTitle.setText();
            txtBenefitText.setText(resources.getString(R.string.free_delivery_one_time));
            txtBenefitUseNow.setText(resources.getString(R.string.free_delivery_use_now));
            txtBenefitUseLater.setText(resources.getString(R.string.free_delivery_use_later));
            txtBenefitUseLater.setVisibility(View.GONE);


        }
        else{
            layoutBenefit.setVisibility(View.GONE);
        }


        if (prefs.getIntPreferences(SP_ORDER_COUNT) == 0  ){

            totalPriceToServer = prefs.getIntPreferences(SP_CUSTOMER_TOTAL)  + first_time_price;
        }
        else{
            Log.e(TAG, "onCreate: ************** first time  $$$666" );
           /* if(prefs.getIntPreferences(SP_VIP_ID) == 1 && pro.getPrice() >= prefs.getIntPreferences(SP_MEMBER_DELIVERY_AMOUNT) ){
                totalPriceToServer = pro.getPrice();
            }
            else */
            if(prefs.getIntPreferences(SP_CUSTOMER_TOTAL)  >= prefs.getIntPreferences(DELIVERY_FREE_AMOUNT)){
                totalPriceToServer = prefs.getIntPreferences(SP_CUSTOMER_TOTAL) ;
            }
            else{
                if(prefs.getBooleanPreference(TEMP_NORMAL)){
                    totalPriceToServer = prefs.getIntPreferences(SP_CUSTOMER_TOTAL)  +prefs.getIntPreferences(TEMP_NORMAL_PRICE);
                }
                else{
                    totalPriceToServer = prefs.getIntPreferences(SP_CUSTOMER_TOTAL)  +prefs.getIntPreferences(DELIVERY_PRICE);
                }

            }
        }


        totalPriceToServer = totalPriceToServer   - prefs.getIntPreferences(CHECK_USE_POINT);

        ArrayList<UniversalPost> orders = new ArrayList<>();
        orders = databaseAdapter.getOrderFooter();
        int totalPrice = 0;


        item_price.setText(formatter.format((prefs.getIntPreferences(SP_CUSTOMER_TOTAL) + prefs.getIntPreferences(SP_CUSTOMER_FLASH_DISCOUNT) + prefs.getIntPreferences(SP_CUSTOMER_MEMBER_DISCOUNT) + prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_DISCOUNT)))+" "+activity.getResources().getString(R.string.currency));


        if (prefs.getStringPreferences(TEMP_COMMISSION_TYPE).equals("percentage")){
            commissionPrice = (int) (totalPriceToServer  * prefs.getFloatPreferences(TEMP_COMMISSION_RATE) / 100);

        }
        else{
            commissionPrice = (int)prefs.getFloatPreferences(TEMP_COMMISSION_RATE);
        }



        prefs.saveIntPerferences(TEMP_COMMISSION_PRICE, commissionPrice );

        finalPrice = totalPriceToServer + commissionPrice ;


        prefs.saveIntPerferences(TEMP_COMMISSION_PRICE, commissionPrice );

        totalPriceToServer = totalPriceToServer + prefs.getIntPreferences(TEMP_COMMISSION_PRICE);


        txtTotalHeader.setTypeface(txtTotalHeader.getTypeface(), Typeface.BOLD);
        promo_percent.setTypeface(txtTotalHeader.getTypeface(), Typeface.BOLD);
        total_price.setTypeface(total_price.getTypeface(), Typeface.BOLD);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);


        stepper_indicator.setCurrentStep(2);
        title.setText("Order Summary");
        txtContinue.setText(resources.getString(R.string.added_to_cart_confirm));
        item_text.setText(resources.getString(R.string.total_product_price));
        name_text.setText(resources.getString(R.string.name_hint) + "။\t\t\t။");
        address_text.setText(resources.getString(R.string.address) + "။\t\t\t။");




        img.setImageUrl(prefs.getStringPreferences(TEMP_COMMISSION_IMG), imageLoader);
        txtPayName.setText(prefs.getStringPreferences(TEMP_COMMISSION_NAME));
        txtPayPercent.setText(prefs.getFloatPreferences(TEMP_COMMISSION_RATE) + " %");


        txtTotalHeader.setText(formatter.format(finalPrice)+" "+activity.getResources().getString(R.string.currency));
        total_price.setText(formatter.format(finalPrice)+" "+activity.getResources().getString(R.string.currency));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            point_use.setText(Html.fromHtml(
                    String.format(resources.getString(R.string.currency_red),"- "+formatter.format( prefs.getIntPreferences(CHECK_USE_POINT))+ " ")  , Html.FROM_HTML_MODE_COMPACT));
        } else {
            point_use.setText(Html.fromHtml(String.format(resources.getString(R.string.currency_red),"- "+formatter.format( prefs.getIntPreferences(CHECK_USE_POINT))+ " ")));
        }


        if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)) {
            deliverTitle.setText("Pick-Up To.....");

        }
        else{
            deliverTitle.setText("Deliver To.....");
        }

        product_discount_text.setText(resources.getString(R.string.product_discount));
        flash_discount_text.setText(resources.getString(R.string.flash_sale_discount));
        member_discount_text.setText(resources.getString(R.string.member_discount));
        point_text.setText(resources.getString(R.string.point_use_text));
        delivery_price_text.setText(resources.getString(R.string.cart_detail_delivery_fees));
        total_price_text.setText(resources.getString(R.string.cart_detail_payable_text));


        payment_text.setText(prefs.getStringPreferences(TEMP_COMMISSION_NAME) + " Service Fee");
        //payment_percent.setText(prefs.getFloatPreferences(TEMP_COMMISSION_RATE) + " %");
        payment_percent.setText(prefs.getIntPreferences(TEMP_COMMISSION_PRICE) + " " + resources.getString(R.string.currency));
        name.setText(prefs.getStringPreferences(SP_USER_NAME));
        address.setText(prefs.getStringPreferences(TEMP_ADDRESS_TEXT));



        if(prefs.getBooleanPreference(TEMP_NORMAL)){
            real = prefs.getIntPreferences(TEMP_NORMAL_PRICE);
        }
        else{
            real = prefs.getIntPreferences(DELIVERY_PRICE);
        }



        if (prefs.getIntPreferences(SP_ORDER_COUNT) == 0 )
            real = first_time_price ;
        else if (totalPriceToServer >= prefs.getIntPreferences(DELIVERY_FREE_AMOUNT) )
            real = 0;


        delivery_price.setText(real + " " +resources.getString(R.string.currency));

        checkPromo();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            flash_discount.setText(Html.fromHtml(
                    String.format(resources.getString(R.string.currency_red),"- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_FLASH_DISCOUNT))+" ")  , Html.FROM_HTML_MODE_COMPACT));
        } else {
            flash_discount.setText(Html.fromHtml(String.format(resources.getString(R.string.currency_red),"- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_FLASH_DISCOUNT))+" ")));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            product_discount.setText(Html.fromHtml(
                    String.format(resources.getString(R.string.currency_red),"- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_DISCOUNT))+" ")  , Html.FROM_HTML_MODE_COMPACT));
        } else {
            product_discount.setText(Html.fromHtml(String.format(resources.getString(R.string.currency_red),"- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_DISCOUNT))+" ")));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            member_discount.setText(Html.fromHtml(
                    String.format(resources.getString(R.string.currency_red),"- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_MEMBER_DISCOUNT))+" ")  , Html.FROM_HTML_MODE_COMPACT));
        } else {
            member_discount.setText(Html.fromHtml(String.format(resources.getString(R.string.currency_red),"- "+formatter.format(prefs.getIntPreferences(SP_CUSTOMER_MEMBER_DISCOUNT))+" ")));
        }



        if (prefs.getBooleanPreference(TEMP_CHOOSE_TIMING)){

            linearDeliTime.setVisibility(View.GONE);
            delivery_date.setText(resources.getString(R.string.delivery_date));
            date_text.setText(prefs.getIntPreferences(TEMP_CHOOSE_DAY) + "-" + (prefs.getIntPreferences(TEMP_CHOOSE_MONTH)+1) + "-" + prefs.getIntPreferences(TEMP_CHOOSE_YEAR)  );
            delivery_time_text.setText(resources.getString(R.string.delivery_time));


            if (prefs.getBooleanPreference(CAN_CHOOSE_TIMESLOT)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    delivery_time.setText(Html.fromHtml(
                            prefs.getStringPreferences(TEMP_DELI_TIME)  , Html.FROM_HTML_MODE_COMPACT));
                } else {
                    delivery_time.setText(Html.fromHtml(prefs.getStringPreferences(TEMP_DELI_TIME)));
                }
            }

            if (prefs.getBooleanPreference(TEMP_NORMAL)) {

                date_text.setText(prefs.getStringPreferences(TEMP_NORMAL_DAY));

            }



        }
        else{
            linearDeliTime.setVisibility(View.GONE);
        }


        txtBenefitUseNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBenefitUseLater.setVisibility(View.VISIBLE);
                free_one_time = 1;
                txtBenefitUseNow.setVisibility(View.GONE);
                delivery_price.setText( "0 " +resources.getString(R.string.currency));

                txtTotalHeader.setText(formatter.format((finalPrice - real))+" "+activity.getResources().getString(R.string.currency));
                total_price.setText(formatter.format(finalPrice - real)+" "+activity.getResources().getString(R.string.currency));

            }
        });
         txtBenefitUseLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBenefitUseLater.setVisibility(View.GONE);
                free_one_time = 0;
                txtBenefitUseNow.setVisibility(View.VISIBLE);
                delivery_price.setText(real + " " +resources.getString(R.string.currency));
                txtTotalHeader.setText(formatter.format(finalPrice)+" "+activity.getResources().getString(R.string.currency));
                total_price.setText(formatter.format(finalPrice)+" "+activity.getResources().getString(R.string.currency));

            }
        });



        promo_percent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog pointDialog = new Dialog(activity);
                pointDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pointDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                pointDialog.setContentView(R.layout.dialog_promo);

                pointDialog.setCanceledOnTouchOutside(true);
                Window window = pointDialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);

                CustomTextView title = pointDialog.findViewById(R.id.title);
                CustomEditText editText = pointDialog.findViewById(R.id.editText);
                CustomButton btnSubmit = pointDialog.findViewById(R.id.btnSubmit);
                CustomButton promo_remove = pointDialog.findViewById(R.id.promo_remove);



                promo_remove.setText(resources.getString(R.string.remove_promo));
                btnSubmit.setText(resources.getString(R.string.promo_confirm));

                if (checkPromoText.trim().length() == 0){
                    btnSubmit.setVisibility(View.VISIBLE);
                    promo_remove.setVisibility(View.GONE);
                    editText.setVisibility(View.VISIBLE);
                    title.setText(resources.getString(R.string.enter_promo));

                }
                else{
                    btnSubmit.setVisibility(View.GONE);
                    promo_remove.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    title.setText(resources.getString(R.string.used_promo) + " " + checkPromoText);
                }


               /* InputMethodManager imm = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);*/


                promo_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkPromoText = "";
                        promoPrice = 0 ;
                        pointDialog.dismiss();
                        checkPromo();
                    }
                });

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editText.getText().toString().trim().length() > 0){

                            pointDialog.dismiss();

                            checkingPromoCodeFromServer(editText.getText().toString(),pointDialog);


                        }
                        else{
                            pointDialog.dismiss();
                            ToastHelper.showToast(activity, resources.getString(R.string.fill_promo));

                        }
                    }
                });


                pointDialog.show();
            }
        });



        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (prefs.isNetworkAvailable()){
                    txtContinue.setVisibility(View.GONE);
                    checkOutMethod();
                }
                else{
                    ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));
                }
            }
        });



        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //www.kyarlay.com/api/coupon?code=KLDELIVERY
    //https://www.kyarlay.com/api/coupon?code=KL1000OFF&fbclid=IwAR0ENk608xBv7C4gDuOcemtXkaWkIb6G5iZCLZh3hg5ytTrrbhaq0a_ROrU

    private void checkPromo(){
        if (checkPromoText.trim().length() == 0){
            promo_percent.setText(resources.getString(R.string.add_promo));
            promo_percent.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
            promo_percent.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.small_text_14));
            //promo_text.setText(" ");
            promo_text.setText(resources.getString(R.string.used_promo));
            imgPromo.setVisibility(View.GONE);
            total_price_strike.setVisibility(View.GONE);
        }
        else{

            imgPromo.setVisibility(View.VISIBLE);
            promo_text.setText(resources.getString(R.string.used_promo));
            promo_percent.setText(checkPromoText);
            promo_percent.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.title_small));
            total_price_strike.setVisibility(View.VISIBLE);
            promo_percent.setTextColor(activity.getResources().getColor(R.color.custome_blue));
            total_price_strike.setText(formatter.format(finalPrice)+" "+activity.getResources().getString(R.string.currency));
            total_price_strike.setPaintFlags(total_price_strike.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        txtTotalHeader.setText(formatter.format(finalPrice-promoPrice)+" "+activity.getResources().getString(R.string.currency));
        total_price.setText(formatter.format(finalPrice-promoPrice)+" "+activity.getResources().getString(R.string.currency));

    }

    private void checkingPromoCodeFromServer(String checkMessage,Dialog dialog){


      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                                constantCuponCode + checkMessage , null, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {


              dialog.dismiss();
              //{"status":0,"type":"not_found","min_amount":0,"amount":0}
              //{"status":1,"type":"free_delivery","min_amount":0,"amount":0}
              //{"status":1,"type":"fix_discount","min_amount":0,"amount":1000}
              try{

                  if (response.getInt("status") == 1){

                      if ((finalPrice - real ) > response.getInt("min_amount")){
                          if (response.getString("type").equals("free_delivery")){
                              promoPrice = real ;
                              checkPromoText = checkMessage;
                          }
                          else if(response.getString("type").equals("fix_discount")){
                              promoPrice = response.getInt("amount") ;
                              checkPromoText = checkMessage;
                          }
                          checkPromo();
                      }
                      else{
                          ToastHelper.showToast(activity, resources.getString(R.string.promo_minimum));
                      }


                  }else{
                      ToastHelper.showToast(activity, resources.getString(R.string.promo_code_error));
                  }

                  //checkPromoText = editText.getText().toString();
                  Log.e(TAG, "onResponse: "  + response.toString() );
              }catch (Exception e){
                  Log.e(TAG, "onResponse: " + e.getMessage() );
              }



          }}, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );

                ToastHelper.showToast(activity, resources.getString(R.string.promo_code_error));


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

        AppController.getInstance().addToRequestQueue(jsonObjectRequest,"sign_in");
    }

    private void checkOutMethod(){
        try {

            //FlurryAgent.logEvent("View Check Out");
        } catch (Exception e) {
        }

        Bundle fbundle = new Bundle();
        fbundle.putString("value", prefs.getIntPreferences(ConstantVariable.CHECK_USE_POINT)+"");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, fbundle);

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        SpannableString s = new SpannableString(activity.getResources().getString(R.string.ordering));

        String font;
        if (MDetect.INSTANCE.isUnicode()){
            font = "pyidaungsu.ttf";
        }
        else{
            font = "zawgyitwo.ttf";
        }

        s.setSpan(new DialogTypeFace(activity, font), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        progressDialog.setMessage(s);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

       // Order123 order1 = new Order123();
       /// order1.setName(prefs.getStringPreferences(SP_USER_NAME));
        //order1.setPhoneNo(prefs.getStringPreferences(SP_USER_PHONE));
        //.setAddress(prefs.getStringPreferences(TEMP_ADDRESS_TOWNSHIP) + " " + prefs.getStringPreferences(TEMP_ADDRESS_TEXT));
        //order1.setOrders(databaseAdapter.getOrderToServer());





        //order1.setUniqueIDs(prefs.getStringPreferences(SP_UNIQUE_ID_FOR_USER));
        //order1.setDeliveryID();
        //int free_delivery_amount = prefs.getIntPreferences(SP_MEMBER_DELIVERY_AMOUNT);
        int deliAmtFree = prefs.getIntPreferences(DELIVERY_FREE_AMOUNT);
        int real = prefs.getIntPreferences(DELIVERY_PRICE);
        if(prefs.getBooleanPreference(TEMP_NORMAL)){
            real = prefs.getIntPreferences(TEMP_NORMAL_PRICE);
        }

        if (totalPriceToServer >= deliAmtFree )
            real = 0;

       // order1.setDeliveryPrice(real );
        prefs.saveStringPreferences(SP_USER_ADDRESS, prefs.getStringPreferences(TEMP_ADDRESS_TEXT));
        prefs.saveIntPerferences(SP_USER_ADDRESS_ID,prefs.getIntPreferences(TEMP_ADDRESS_ID));
        uploadOrderAddress( progressDialog, real);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(prefs.getBooleanPreference(PAYMENTDONE)){
            prefs.saveBooleanPreference(PAYMENTDONE, false);
            prefs.saveBooleanPreference(PAYMENTAGAIN, false);
            Intent intent = new Intent(activity, CallbackResultActivity.class);
            startActivity(intent);
        }
    }

    private void checkOutAppsFlyerLib(int tPrice){
        ArrayList<OrderIDs> orderIDsArrayList = databaseAdapter.getOrderList();

        if (orderIDsArrayList.size() > 0){

            String[] nameList = new String[orderIDsArrayList.size()];
            int[] qtyList = new int[orderIDsArrayList.size()];
            int[] price = new int[orderIDsArrayList.size()];

            for (int i=0; i < orderIDsArrayList.size(); i++ ){
                nameList[i] = String.valueOf(orderIDsArrayList.get(i).getId());
                qtyList[i] = orderIDsArrayList.get(i).getQuantity();
                price[i] =  orderIDsArrayList.get(i).getPrice();

            }




       /*     Map<String,Object> eventData = new HashMap<>();
            eventData.put(AFInAppEventParameterName.REVENUE, String.valueOf(tPrice));
            eventData.put(AFInAppEventParameterName.CONTENT_ID, nameList);
            eventData.put(AFInAppEventParameterName.QUANTITY, qtyList);
            eventData.put(AFInAppEventParameterName.PRICE,price);
            eventData.put(AFInAppEventParameterName.CURRENCY,"MMK");
            //eventData.put(AFInAppEventParameterName.CUSTOMER_USER_ID, prefs.getIntPreferences(SP_MEMBER_ID));
            AppsFlyerLib.getInstance().trackEvent(activity, AFInAppEventType.PURCHASE,eventData);


            AppsFlyerLib.getInstance().registerValidatorListener(activity,new
                    AppsFlyerInAppPurchaseValidatorListener() {
                        public void onValidateInApp() {
                            Log.e(TAG, "Purchase validated successfully");
                        }
                        public void onValidateInAppFailure(String error) {
                            Log.e(TAG, "onValidateInAppFailure called: " + error);
                        }
                    });
*/

        }
    }


    private void  uploadOrderAddress(final ProgressDialog progerss, int deliveryPrice)
    {
        String uniqueId = "";
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        uniqueId = sb.toString();


        try {


            //FlurryAgent.logEvent("Check Out");
        } catch (Exception e) {
        }
        JSONObject uploadOrder = new JSONObject();
        try {
           // uploadOrder.put("member_id",    prefs.getIntPreferences(SP_MEMBER_ID));
            //uploadOrder.put("name",         order.getName());
            uploadOrder.put("address",  prefs.getStringPreferences(TEMP_ADDRESS_TOWNSHIP) + " " + prefs.getStringPreferences(TEMP_ADDRESS_TEXT) );
            uploadOrder.put("address_id",      prefs.getIntPreferences(SP_USER_ADDRESS_ID));
           // uploadOrder.put("phone",        order.getPhoneNo());
            uploadOrder.put("api_log_id",        prefs.getStringPreferences(SP_USER_PHONE) + "_" + uniqueId);

            uploadOrder.put("delivery_id",  prefs.getIntPreferences(DELIVERY_ID));

            uploadOrder.put("point", prefs.getIntPreferences(CHECK_USE_POINT));
            //uploadOrder.put("products",     order.getOrders());
            uploadOrder.put("payment_type_id", prefs.getIntPreferences(TEMP_CHOOSE_PAYMENT_ID));
            uploadOrder.put("payment_type_tag", prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_TAG));


            int free_delivery = 0;

            if(free_one_time == 1){
                if (prefs.getBooleanPreference(TEMP_NORMAL)) {
                    uploadOrder.put("delivery_fee", 0);
                    uploadOrder.put("delivery_type", 1);

                    free_delivery = prefs.getIntPreferences(TEMP_NORMAL_PRICE);

                }
                else{
                    free_delivery = real;
                    uploadOrder.put("delivery_fee", 0);
                    uploadOrder.put("delivery_type", 0);


                    if (prefs.getBooleanPreference(TEMP_CHOOSE_TIMING)){
                        uploadOrder.put("delivery_date",    prefs.getIntPreferences(TEMP_CHOOSE_DAY) + "-" + (prefs.getIntPreferences(TEMP_CHOOSE_MONTH)+1) + "-" + prefs.getIntPreferences(TEMP_CHOOSE_YEAR)  );

                        if (prefs.getBooleanPreference(CAN_CHOOSE_TIMESLOT))
                            uploadOrder.put("delivery_time",    prefs.getIntPreferences(TEMP_DELI_ID) );
                    }

                }


            }
            else{
                if (prefs.getBooleanPreference(TEMP_NORMAL)) {
                    uploadOrder.put("delivery_fee", prefs.getIntPreferences(TEMP_NORMAL_PRICE));
                    uploadOrder.put("delivery_type", 1);
                }
                else{
                    uploadOrder.put("delivery_fee", deliveryPrice);
                    uploadOrder.put("delivery_type", 0);

                    if (prefs.getBooleanPreference(TEMP_CHOOSE_TIMING)){
                        uploadOrder.put("delivery_date",    prefs.getIntPreferences(TEMP_CHOOSE_DAY) + "-" + (prefs.getIntPreferences(TEMP_CHOOSE_MONTH)+1) + "-" + prefs.getIntPreferences(TEMP_CHOOSE_YEAR)  );

                        if (prefs.getBooleanPreference(CAN_CHOOSE_TIMESLOT))
                            uploadOrder.put("delivery_time",    prefs.getIntPreferences(TEMP_DELI_ID) );
                    }

                }
            }

            uploadOrder.put("free_delivery" , free_one_time);
           // uploadOrder.put("total_price",  (finalPrice - free_delivery));
            if (prefs.getBooleanPreference(TEMP_CHOOSE_PICK_UP)) {
                uploadOrder.put("store_location_id", prefs.getIntPreferences(TEMP_PICK_UP_ID));
            }

            uploadOrder.put("remark", prefs.getStringPreferences(TEMP_REMARK_TEXT));



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "uploadOrderAddress: "  + uploadOrder.toString() );



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,constantUploadOrder +   "?language=" + prefs.getStringPreferences(SP_LANGUAGE)   , uploadOrder,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, "onResponse: ----------------------- 810 "  + response.toString() );

                        try {
                            int status      = response.getInt("status");
                            String message  = response.getString("text");
                            String orderId  = response.getString("order_id");
                            int point       = response.getInt("point");

                            Bundle fbundle = new Bundle();
                            fbundle.putString("transaction_id",orderId);
                            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, fbundle);

                            prefs.saveIntPerferences(SP_USER_POINT, point);
                            prefs.saveBooleanPreference(PAYMENTDONE,false);

                            if(status == 1 ){

                                checkOutAppsFlyerLib( finalPrice);

                                //databaseAdapter.deleteAllColumn(TABLE_SAVE_CART);
                               // int result = databaseAdapter.insertOrderTable(orderId, order);
                                //if(result != -1){
                                //    databaseAdapter.insertOrderItem(order, orderId);
                               // }
                                progerss.dismiss();

                                if (prefs.getStringPreferences(TEMP_CHOOSE_PAYMENT_TAG).equals("kpay")) {
                                    prefs.saveStringPreferences(SP_ORDER_INFO, response.getString("orderinfo"));
                                    prefs.saveStringPreferences(SP_SIGN, response.getString("sign"));
                                    prefs.saveStringPreferences(SP_SHA, response.getString("sign_type"));
                                }




                                try {

                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("status", "1");
                                    //FlurryAgent.logEvent("View Check Out", mix);
                                } catch (Exception e) {
                                }

                                //{"status":1,"text":"မှာပြီးပါပြီ။ ကျေးဇူးတင်ပါတယ်။ အလုပ်ရက် ၁ ရက်အတွင်း ပြန်ဆက်သွယ်ပေးပါမယ်။","order_id":302621,"point":157}

                                Intent intent = new Intent(activity, ActivityCheckOut.class);
                                intent.putExtra("status", message);
                                startActivity(intent);





                            }else if(status == 0){
                                progerss.dismiss();
                                txtContinue.setVisibility(View.VISIBLE);

                                try {


                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("status", "2");
                                    //FlurryAgent.logEvent("View Check Out", mix);
                                } catch (Exception e) {
                                }

                                final Dialog dialog = new Dialog(activity);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialog_save_cart);
                                dialog.setCancelable(true);

                                Window window = dialog.getWindow();
                                WindowManager.LayoutParams wlp = window.getAttributes();
                                wlp.gravity = Gravity.CENTER;
                                wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
                                window.setAttributes(wlp);

                                CustomTextView textView  = (CustomTextView) dialog.findViewById(R.id.dialog_save_cart_product);
                                textView.setText(message);
                                CustomButton ok = (CustomButton) dialog.findViewById(R.id.yes);
                                ok.setText(resources.getString(R.string.close_button));
                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        activity.finish();
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );

                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("status", "error");
                    //FlurryAgent.logEvent("View Check Out", mix);

                } catch (Exception e) {
                }
                progerss.dismiss();
                txtContinue.setVisibility(View.VISIBLE);
                ToastHelper.showToast(activity, resources.getString(R.string.search_error));

            }
        }) {

            /* *
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"Order123");
    }










}
