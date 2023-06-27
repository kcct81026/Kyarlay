package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.FlashSaleListObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  FlashSalesActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "FlashSalesActivity";


    RecyclerView recyclerView;
    UniversalAdapter adapter;
    ArrayList<UniversalPost> universalPosts  = new ArrayList<>();
    Boolean loading = true;
    MyPreference prefs;
    Resources resources;
    Display display;
    ProgressBar progressBar;

    LinearLayout back_layout, title_layout;
    RelativeLayout like_layout, cart_layout;
    CustomTextView title;
    DatabaseAdapter databaseAdapter;
    String fromClass = "fromClass";

    CircularTextView cart_text, wishlist_count;
    Map<Integer, CountDownTimer> countDownMap = new HashMap<Integer, CountDownTimer>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        prefs            = new MyPreference(FlashSalesActivity.this);
        Context context = LocaleHelper.setLocale(FlashSalesActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display     = getWindowManager().getDefaultDisplay();


       // new MyFlurry(FlashSalesActivity.this);


        adapter = new UniversalAdapter(FlashSalesActivity.this, universalPosts, countDownMap);
        databaseAdapter = new DatabaseAdapter(FlashSalesActivity.this);
        setContentView(R.layout.discount_acitvity);
        recyclerView    = findViewById(R.id.recycler_view);
        progressBar     = findViewById(R.id.progressBar);
        back_layout     = findViewById(R.id.back_layout);
        title_layout    = findViewById(R.id.title_layout);
        like_layout     = findViewById(R.id.like_layot);
        cart_layout     = findViewById(R.id.cart_layout);
        title           = findViewById(R.id.title);
        cart_text       = findViewById(R.id.menu_cart_idenfier);
        wishlist_count  = findViewById(R.id.wishlist_count);

        fromClass = getIntent().getStringExtra("fromClass");

        Log.e(TAG, "onCreate: " );

        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        like_layout.getLayoutParams().width     = ( display.getWidth() * 3 ) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        try {

            Map<String, String> mix = new HashMap<String, String>();
            //FlurryAgent.logEvent("View Product Wishlist", mix);

        } catch (Exception e) {}



        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownMap == null) {
                    return;
                }else
                {

                    for(CountDownTimer timer : countDownMap.values()){
                        timer.cancel();
                    }

                }

                if (fromClass.equals(FROM_FIREBASE)){
                    Intent backIntent = new Intent(FlashSalesActivity.this, MainActivity.class);
                    startActivity(backIntent);
                    finish();
                }
                else
                    finish();

            }
        });
        like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "product_list");
                        //FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(FlashSalesActivity.this, WishListActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(FlashSalesActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }



            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "product_list");
                        //FlurryAgent.logEvent("Click Shopping Cart", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(FlashSalesActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(FlashSalesActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });
        prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT, SP_DEFAULT);


        title.setText(resources.getString(R.string.flash_sale_discount));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        getDiscountList();


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {

                    loading = true;
                    getDiscountList();
                }

            }
        });




    }

    public void clickRefresh(){

        int position = -1;
        for (int i = 0 ; i < universalPosts.size(); i ++){
            if (universalPosts.get(i).getPostType().equals(REFRESH_FOOTER)){
                position = i;
            }
        }

        if (position != -1){
            universalPosts.remove(position);
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_FOOTER);
            universalPosts.add(noitem);
            adapter.notifyItemInserted(universalPosts.size());
            getDiscountList();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        cart_text.setStrokeWidth(1);
        cart_text.setStrokeColor("#000000");
        cart_text.setSolidColor("#ffffff");
        int count = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);

        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }


        wishlist_count.setStrokeWidth(1);
        wishlist_count.setStrokeColor("#000000");
        wishlist_count.setSolidColor("#ffffff");

        int count_wish  = prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT);
        if(count_wish == 0){
            wishlist_count.setVisibility(View.GONE);
        }else{
            wishlist_count.setVisibility(View.VISIBLE);
            wishlist_count.setText(count_wish + "");
        }

    }


    private void getDiscountList(){


        final JsonArrayRequest arrayRequest = new JsonArrayRequest(constantFlashSalesList + prefs.getIntPreferences(ConstantVariable.SP_PAGE_NUM_DISCOUNT) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {



                        if(response.length() > 0) {


                            prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT, prefs.getIntPreferences(SP_PAGE_NUM_DISCOUNT) + SP_DEFAULT);

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            loading = false;
                            progressBar.setVisibility(View.GONE);

                           // mainObject.setTitle("Flash Sale");



                            try {
                                    GsonBuilder builder = new GsonBuilder();
                                    Gson mGson = builder.create();

                                    Type listType = new TypeToken<List<FlashSaleListObject>>() {
                                    }.getType();
                                    List<FlashSaleListObject> categoryList = mGson.fromJson(response.toString(), listType);


                                    for (int i=0; i < categoryList.size(); i++){
                                        Product product = categoryList.get(i).getProduct();
                                        product.setPostType(PRODUCT_FLASH_SALE);
                                        universalPosts.add(product);
                                    }








                                    Reading pro = new Reading();
                                    pro.setPostType(CART_DETAIL_FOOTER);
                                    universalPosts.add(pro);

                                    adapter.notifyDataSetChanged();



                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                        }else{
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            loading = true;
                            progressBar.setVisibility(View.GONE);
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);



                            adapter.notifyDataSetChanged();

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }



                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                universalPosts.add(noitem);


                adapter.notifyDataSetChanged();

            }
        });
        AppController.getInstance().addToRequestQueue(arrayRequest);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (countDownMap == null) {

            return;
        }else
        {
            for(CountDownTimer timer : this.countDownMap.values()){
                timer.cancel();
            }

        }

        if (fromClass.equals(FROM_FIREBASE)){
            Intent backIntent = new Intent(FlashSalesActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        }
        else
            finish();
    }
}
