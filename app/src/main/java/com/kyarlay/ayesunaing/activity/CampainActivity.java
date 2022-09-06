package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
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
import com.flurry.android.FlurryAgent;
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
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Campaign;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesu on 7/25/17.
 */

public class CampainActivity extends AppCompatActivity implements Constant, ConstantVariable {
    private static final String TAG = "CampainActivity";

    RecyclerView recyclerView;
    MyPreference prefs;
    UniversalAdapter adapter;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    RecyclerView.LayoutManager manager;
    boolean loading = true;
    DatabaseAdapter databaseAdapter;

    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar1;

    Display display;
    Resources resources;
    CustomTextView title;
    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;
    CircularTextView  cart_text, wishlist_count;
    RelativeLayout layout_like;
    ImageView cart;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_detail);
        prefs = new MyPreference(CampainActivity.this);
        databaseAdapter = new DatabaseAdapter(CampainActivity.this);
        progressBar1    = (ProgressBar) findViewById(R.id.progressBar1);
        Context context = LocaleHelper.setLocale(CampainActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        manager = new LinearLayoutManager(getApplicationContext());
        new MyFlurry(CampainActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(CampainActivity.this, universalPosts);
        recyclerView.setAdapter(adapter);


        Log.e(TAG, "onCreate: " );

        Intent intent = getIntent();
        String api_url = intent.getStringExtra("api");

        display = getWindowManager().getDefaultDisplay();

        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);

        layout_like     = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count  = (CircularTextView) findViewById(R.id.wishlist_count);

        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "discount_list");
                    FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                } catch (Exception e) {
                }
                Intent intent = new Intent(CampainActivity.this, WishListActivity.class);
                startActivity(intent);
            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "discount_list");
                        FlurryAgent.logEvent("Click Shopping Cart", mix);

                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(CampainActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(CampainActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });

        layout_like.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CampainActivity.this.finish();
            }
        });

        title.setText(resources.getString(R.string.promotion));



        if(prefs.isNetworkAvailable()){
            getList(api_url);
        }else{

            progressBar1.setVisibility(View.GONE);

            ToastHelper.showToast(CampainActivity.this, resources.getString(R.string.no_internet_error));


        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        cart_text.setStrokeWidth(1);
        cart_text.setStrokeColor("#000000");
        cart_text.setSolidColor("#ffffff");
        int count = databaseAdapter.getOrderCount();

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


    private void getList(String url){
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            progressBar1.setVisibility(View.GONE);
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Campaign>>() {
                                }.getType();
                                List<Campaign> categoryList = mGson.fromJson(response.toString(), listType);
                                for (int i = 0; i < categoryList.size(); i++) {
                                    Campaign campaign = new Campaign();
                                    campaign = categoryList.get(i);
                                    campaign.setPostType(CAMPAIGN);
                                    universalPosts.add(campaign);
                                }
                                adapter.notifyDataSetChanged();

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                            Product pro = new Product();
                            pro.setPostType(CART_DETAIL_NO_ITEM);

                            universalPosts.add(pro);
                            adapter.notifyDataSetChanged();

                        }else{

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            loading = true;
                            progressBar1.setVisibility(View.GONE);
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);

                            adapter.notifyDataSetChanged();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                loading = false;
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



}

