package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Addresses;
import com.kyarlay.ayesunaing.object.CustomerProduct;
import com.kyarlay.ayesunaing.object.CustomerProductList;
import com.kyarlay.ayesunaing.object.Delivery;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalStepAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesunaing on 9/6/16.
 */
public class ShoppingCartActivity extends AppCompatActivity implements ConstantVariable, ConstantsDB, Constant, OnMenuItemClickListener{
    private static final String TAG = "ShoppingCartActivity";

    RecyclerView recyclerView;
    UniversalStepAdapter adapter;
    ArrayList<UniversalPost> universalList = new ArrayList<>();
    List<CustomerProduct> cartList = new ArrayList<>();
    DatabaseAdapter databaseAdapter;
    CustomTextView payabelText, payableAmount, delivery_fee, to_show_order, delivery_text;
    Display display;

    DecimalFormat formatter = new DecimalFormat("#,###,###");
    MyPreference prefs;
    ArrayList<Delivery> delList = new ArrayList<>();

    Animation animShow, animHide;
    Resources resources;
    ImageView delivery_image;



    CustomTextView title, txtCheckOut, txtEmpty, txtSolution,txtTotal, txtTotalTitle;
    LinearLayout title_layout, back_layout;
    AppCompatActivity activity;
    FirebaseAnalytics firebaseAnalytics;
    RelativeLayout relativeCart;
    LinearLayout linearEmpty, linearTotal;

    TextView viewHidden;
    LinearLayout linearTownship;
    CustomTextView txtTownship,txtTitle;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        display          = getWindowManager().getDefaultDisplay();
        prefs            = new MyPreference(this);
        prefs.saveIntPerferences(CART_DETAIL_SAVE, 100);
        Context context = LocaleHelper.setLocale(ShoppingCartActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        activity = (AppCompatActivity) ShoppingCartActivity.this;

        Log.e(TAG, "onCreate:   " );

        prefs.saveBooleanPreference(ALREADY_USE_POINT, false);
        prefs.saveIntPerferences(CHECK_USE_POINT, 0);
        prefs.saveIntPerferences(SP_TEMP_TOTAL, 0);

        new MyFlurry(ShoppingCartActivity.this);

        try {

            FlurryAgent.logEvent("View Shopping Cart");
        } catch (Exception e) {}
        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);

        setContentView(R.layout.shopping_cart);
        recyclerView     = (RecyclerView) findViewById(R.id.recycler_view);
        payableAmount    = (CustomTextView) findViewById(R.id.cart_detail_payable_amount);
        payabelText      = (CustomTextView) findViewById(R.id.cart_detail_payable_text);
        delivery_fee     = (CustomTextView) findViewById(R.id.delivery_fee);
        to_show_order    = (CustomTextView) findViewById(R.id.show_order) ;
        txtEmpty    = (CustomTextView) findViewById(R.id.txtEmpty) ;
        txtSolution    = (CustomTextView) findViewById(R.id.txtSolution) ;
        txtTotal    = (CustomTextView) findViewById(R.id.txtTotal) ;
        txtTotalTitle    = (CustomTextView) findViewById(R.id.txtTotalTitle) ;
        delivery_text    = (CustomTextView) findViewById(R.id.delivery_text);
        delivery_image   = (ImageView) findViewById(R.id.delivery_image);
        relativeCart   = (RelativeLayout) findViewById(R.id.relativeCart);
        linearEmpty   =  findViewById(R.id.linearEmpty);
        linearTotal   =  findViewById(R.id.linearTotal);
        progressBar   =  findViewById(R.id.progressBar1);

        viewHidden = findViewById(R.id.viewHidden);
        linearTownship = findViewById(R.id.linearTownship);
        txtTownship = findViewById(R.id.txtTownship);
        txtTitle = findViewById(R.id.txtTitle);

        databaseAdapter  = new DatabaseAdapter(ShoppingCartActivity.this);
        animShow = AnimationUtils.loadAnimation(ShoppingCartActivity.this, R.anim.view_show);
        animHide = AnimationUtils.loadAnimation(ShoppingCartActivity.this, R.anim.view_hide);

        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        txtCheckOut     = (CustomTextView) findViewById(R.id.txtCheckOut);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 16) / 20;

        title.setText(resources.getString(R.string.card_detail_title));
        txtTotalTitle.setText(resources.getString(R.string.price));

        txtSolution.setText(resources.getString(R.string.empty_order));
        txtEmpty.setText(resources.getString(R.string.solution_order));

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCartActivity.this.finish();
            }
        });


        prefs.saveIntPerferences(DELIVERY_ID,0);
        prefs.saveIntPerferences(DELIVERY_FREE_AMOUNT,0);
        prefs.saveIntPerferences(DELIVERY_PRICE,0);




        final RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

        progressBar.setVisibility(View.VISIBLE);
        linearEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        getCustomerProdcutList();






        delList = databaseAdapter.getDelivery();

        txtCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCheckOut.setEnabled(false);

                if (cartList.size() > 0){
                    prefs.remove(TEMP_CHOOSE_PAYMENT_ID);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_TAG);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_ACC_NUM);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_ACC_NAME);
                    prefs.remove(TEMP_CHOOSE_PAYMENT_TAG);
                    prefs.remove(TEMP_COMMISSION_RATE);
                    prefs.remove(TEMP_ADDRESS_ID);

                    if (databaseAdapter.getPickupShops().size() > 0){
                        prefs.saveBooleanPreference(PICK_UP_ON_OFF,true);
                    }
                    else{
                        prefs.saveBooleanPreference(PICK_UP_ON_OFF,false);
                    }

                    prefs.saveBooleanPreference(TEMP_CHOOSE_PICK_UP, false);
                    prefs.saveBooleanPreference(TEMP_NORMAL, false);
                    prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID,-1);
                    prefs.saveIntPerferences(TEMP_NORMAL_PRICE,0);
                    prefs.saveStringPreferences(TEMP_NORMAL_DAY,"");



                    ArrayList<Addresses> addressesList = databaseAdapter.getCustomerAddressList();


                    if (addressesList.size() > 0) {


                        prefs.saveBooleanPreference(NO_ADDRESS, false);

                        for (int j=0; j< addressesList.size(); j++){
                            if (addressesList.get(j).getTownShipID() == prefs.getIntPreferences(SP_TOWNSHIP_ID)){
                                prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID,addressesList.get(j).getId() );

                            }
                        }

                        if (prefs.getIntPreferences(TEMP_FAKE_ADDRESS_ID) == -1 ){
                            if (prefs.getStringPreferences(SP_USER_ADDRESS).trim().length() > 0){
                                String[] strings = prefs.getStringPreferences(SP_USER_ADDRESS).split(" ");
                                if (strings.length > 0){
                                    for (int j=0; j< addressesList.size(); j++){
                                        if (strings[0].equals(addressesList.get(j).getTownship_name())){
                                            prefs.saveIntPerferences(TEMP_FAKE_ADDRESS_ID,addressesList.get(j).getId() );
                                        }
                                    }
                                }
                            }
                        }

                        Intent intent = new Intent(ShoppingCartActivity.this, ActivityStepOneCart.class);
                        startActivity(intent);

                    }
                    else{
                        Intent intent = new Intent(ShoppingCartActivity.this, ActivityEditAddress.class);
                        prefs.saveBooleanPreference(NO_ADDRESS, true);
                        startActivity(intent);

                    }

                }
                else{
                    finish();


                }

            }
        });



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scrollUp = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


                super.onScrollStateChanged(recyclerView, newState);

                relativeCart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dx == 0 && dy == 0){
                    relativeCart.setVisibility(View.GONE);
                }
                else{
                    relativeCart.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    private void increaseProductFromServer(CustomerProduct product){
        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("product_id", product.getId());
            uploadMessage.put("quantity", product.getQuantity());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH,
                constantAddCurrentOrderFromServer , uploadMessage, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try{
                    int returnSatatus =  response.getInt("status");

                    if(returnSatatus == 1){
                        getCustomerProdcutList();
                    }else{
                        ToastHelper.showToast(activity, resources.getString(R.string.search_error));
                    }


                }catch (Exception e){
                    Log.e(TAG, "onResponse:  add "  + e.getMessage() );

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ------  add"   + error.getMessage() );
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



    private void decreaseProductFromServer(CustomerProduct product){

        JSONObject uploadMessage = new JSONObject();
        try {
            uploadMessage.put("product_id", product.getId());
            uploadMessage.put("quantity", product.getQuantity());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH,
                constantSubCurrentOrderFromServer  , uploadMessage, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try{
                    int returnSatatus =  response.getInt("status");

                    if(returnSatatus == 1){
                        getCustomerProdcutList();
                    }else{
                        ToastHelper.showToast(activity, resources.getString(R.string.search_error));
                    }


                }catch (Exception e){
                    Log.e(TAG, "onResponse:  sub "  + e.getMessage() );

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: ------  sub "   + error.getMessage() );
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


    private void removeProductFromServer(int id){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                constantRemoveCurrentOrderFromServer + id , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try{
                    int returnSatatus =  response.getInt("status");

                    if(returnSatatus == 1){
                      getCustomerProdcutList();
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

                Log.e(TAG, "onErrorResponse: ------  "   + error.getMessage() );
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


    private void getCustomerProdcutList()
    {

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCurrentOrderFromServer, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG, "onResponse: "  + response.toString() );
                progressBar.setVisibility(View.GONE);
                recyclerView.setEnabled(true);
                txtCheckOut.setEnabled(true);
               if (response.toString() != null){
                   universalList.clear();
                   try {
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Type listType = new TypeToken<CustomerProductList>() {
                        }.getType();
                        CustomerProductList customerProductList = mGson.fromJson(response.toString(), listType);

                        cartList = customerProductList.getProuduts();


                       prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_DISCOUNT, customerProductList.getProductCuromerInfo().getDisCountPrice());
                       prefs.saveIntPerferences(SP_CUSTOMER_MEMBER_DISCOUNT, customerProductList.getProductCuromerInfo().getMemberDiscount());
                       prefs.saveIntPerferences(SP_CUSTOMER_FLASH_DISCOUNT, customerProductList.getProductCuromerInfo().getFlashDiscount());
                       prefs.saveIntPerferences(SP_CUSTOMER_TOTAL, customerProductList.getProductCuromerInfo().getTotalPrice());


                       if (cartList.size() > 0){


                           int count = 0;
                           for (int i=0; i< cartList.size(); i++){
                               cartList.get(i).setPostType(SHOPPING_CART_ITEM);
                               count = count + customerProductList.getProuduts().get(i).getQuantity();

                            }

                           prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT , count);

                           Product dis = new Product();
                           dis.setPostType(DISCOUNT_TITLE);
                           dis.setTitle(resources.getString(R.string.more_order_noti));
                           universalList.add(dis);
                           universalList.addAll(cartList);


                            customerProductList.setPostType(SHOPPING_CART_SUMMARY);
                           universalList.add(customerProductList);
                           adapter = new UniversalStepAdapter(ShoppingCartActivity.this, universalList);
                           recyclerView.setAdapter(adapter);

                           linearEmpty.setVisibility(View.GONE);
                           recyclerView.setVisibility(View.VISIBLE);
                           txtCheckOut.setText(resources.getString(R.string.order));


                           txtTotal.setText(formatter.format(prefs.getIntPreferences(SP_CUSTOMER_TOTAL)-prefs.getIntPreferences(CHECK_USE_POINT)) +" "+resources.getString(R.string.currency));
                           txtTotal.setVisibility(View.VISIBLE);
                           linearTotal.setVisibility(View.VISIBLE);

                       }
                       else{
                           Log.e(TAG, "onCreate: **************************** 810 size zero "  );
                           prefs.saveIntPerferences(SP_CUSTOMER_PRODUCT_COUNT , 0);
                           txtTotal.setVisibility(View.GONE);
                           linearTotal.setVisibility(View.GONE);

                           txtCheckOut.setText(resources.getString(R.string.added_to_cart_cancel));
                           //txtCheckOut.setText("Go Shopping");
                           linearEmpty.setVisibility(View.VISIBLE);
                           recyclerView.setVisibility(View.GONE);
                       }

                    }catch (Exception e){
                        Log.e(TAG, "onResponse: errror       "  + e.getMessage() );
                    }
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                txtTotal.setVisibility(View.GONE);
                linearTotal.setVisibility(View.GONE);

                txtCheckOut.setText(resources.getString(R.string.added_to_cart_cancel));
                //txtCheckOut.setText("Go Shopping");
                linearEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
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

    public void addProduct(CustomerProduct product){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setEnabled(false);
        txtCheckOut.setEnabled(false);
        increaseProductFromServer(product);

    }

    public void subProduct(CustomerProduct product){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setEnabled(false);
        txtCheckOut.setEnabled(false);
        decreaseProductFromServer(product);
    }

    public void removeProdcut(int id){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setEnabled(false);
        txtCheckOut.setEnabled(false);
        removeProductFromServer(id);
    }







/*
    public void updatePayment(){

        universalList.remove(universalList.size()-1);
        adapter.notifyItemRemoved(universalList.size()-1);



        Product pro = new Product();
        pro.setPostType(SHOPPING_CART_SUMMARY);
        universalList.add(pro);

        Product fakeProduct = databaseAdapter.getShoppingCartFooter();
        if (prefs.getBooleanPreference(ALREADY_USE_POINT)){
            int toShowPrice = 0;
            int percent = prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE);

            if(((fakeProduct.getPoint_usage() * percent ) / 100 ) > prefs.getIntPreferences(SP_USER_POINT)){
                toShowPrice = prefs.getIntPreferences(SP_USER_POINT);
            }
            else{
                toShowPrice = (fakeProduct.getPoint_usage() * percent ) / 100  ;
            }

            prefs.saveIntPerferences(CHECK_USE_POINT, toShowPrice);


        }
        else{
            prefs.saveIntPerferences(CHECK_USE_POINT, 0);
        }


        adapter.notifyItemInserted(universalList.size());



    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtCheckOut.setEnabled(true);
        invalidateOptionsMenu();
        //setUpToolbar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                prefs.saveIntPerferences(CART_DETAIL_SAVE, 100);
                ShoppingCartActivity.this.finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        prefs.saveIntPerferences(CART_DETAIL_SAVE, 100);
        ShoppingCartActivity.this.finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        return false;
    }







    public void showTotalPrice(){

        if (cartList.size() > 0){
            txtTotal.setText(formatter.format(prefs.getIntPreferences(SP_CUSTOMER_TOTAL)-prefs.getIntPreferences(CHECK_USE_POINT)) +" "+resources.getString(R.string.currency));
            txtTotal.setVisibility(View.VISIBLE);
            linearTotal.setVisibility(View.VISIBLE);

        }
        else{
            txtTotal.setVisibility(View.GONE);
            linearTotal.setVisibility(View.GONE);
        }




    }


}
