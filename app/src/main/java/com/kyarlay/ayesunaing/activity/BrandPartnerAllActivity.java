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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesu on 2/22/18.
 */

public class BrandPartnerAllActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "BrandPartnerAllActivity";

    RecyclerView recyclerView;
    UniversalAdapter adapter;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    DatabaseAdapter databaseAdapter;
    boolean loading = false;
    MyPreference prefs;
    RecyclerView.LayoutManager manager;
    ProgressBar progressBar;


    Display display;
    Resources resources;
    CustomTextView title;
    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout, layout_like;
    CircularTextView  cart_text, wishlist;
    ImageView cart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product);
        display = getWindowManager().getDefaultDisplay();

        new MyFlurry(BrandPartnerAllActivity.this);
        prefs = new MyPreference(BrandPartnerAllActivity.this);
        databaseAdapter = new DatabaseAdapter(BrandPartnerAllActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar  = (ProgressBar) findViewById(R.id.progressBar1);


        Log.e(TAG, "onCreate: " );

        try {

            Map<String, String> mix = new HashMap<String, String>();
            FlurryAgent.logEvent("Click Brand All Page", mix);
        } catch (Exception e) {
        }
        Context context = LocaleHelper.setLocale(BrandPartnerAllActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();


        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        wishlist         = (CircularTextView) findViewById(R.id.wishlist);

        prefs.saveIntPerferences(SP_PAGE_REDING_NUM,SP_DEFAULT);



        layout_like     = (RelativeLayout) findViewById(R.id.like_layot);
        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "brand_all");
                        FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(BrandPartnerAllActivity.this, WishListActivity.class);
                    startActivity(intent);



            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "brand_all");
                        FlurryAgent.logEvent("Click  Shopping Cart", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(BrandPartnerAllActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(BrandPartnerAllActivity.this, ActivityLogin.class);
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
                BrandPartnerAllActivity.this.finish();
            }
        });

        title.setText(resources.getString(R.string.product));



        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(BrandPartnerAllActivity.this, universalPosts);
        recyclerView.setAdapter(adapter);


        if(prefs.isNetworkAvailable()) {
            getBrandPatner();
        }else{
            progressBar.setVisibility(View.GONE);
            universalPosts.clear();
            universalPosts = databaseAdapter.getBrand(prefs.getStringPreferences(SP_BRAND_TAG));
            Brand pro = new Brand();
            pro.setPostType(CART_DETAIL_NO_ITEM);
            universalPosts.add(pro);

            recyclerView.setLayoutManager(manager);
            adapter = new UniversalAdapter(BrandPartnerAllActivity.this, universalPosts);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.image);
                if(image != null) {
                    image.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.image);
                if(image != null) {
                    image.setVisibility(View.GONE);
                }
            }
        });


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean scrollUp = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE && scrollUp) {

                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;

                    getBrandPatner();
                }

            }
        });


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

        wishlist.setStrokeWidth(1);
        wishlist.setStrokeColor("#000000");
        wishlist.setSolidColor("#ffffff");

        int count_wish  = prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT);
        if(count_wish == 0){
            wishlist.setVisibility(View.GONE);
        }else{
            wishlist.setVisibility(View.VISIBLE);
            wishlist.setText(count_wish + "");
        }
    }

    public void getBrandPatner(){



        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMainBrandPartner + "?language=" + prefs.getStringPreferences(SP_LANGUAGE) + "&page=" + prefs.getIntPreferences(SP_PAGE_REDING_NUM), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.e(TAG, "onResponse: "  +  constantMainBrandPartner + "?language=" + prefs.getStringPreferences(SP_LANGUAGE) + "&page=" + prefs.getIntPreferences(SP_PAGE_REDING_NUM));

                if(response.length() > 0){
                    loading = false;
                    prefs.saveIntPerferences(SP_PAGE_REDING_NUM, prefs.getIntPreferences(SP_PAGE_REDING_NUM) + SP_DEFAULT);


                    if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                        universalPosts.remove(universalPosts.size() - 1);
                    }

                    try{

                        JSONArray flashList = response.getJSONArray("collections");
                        List<Brand> categoryList  = new ArrayList<>(flashList.length());
                        Gson gson = new Gson();

                       /* if (flashList.length() >= 3){

                        }*/
                        for (int i = 0; i < flashList.length(); i++) {

                            JSONObject objFlash = flashList.getJSONObject(i);
                            Brand flashSaleListObject = gson.fromJson(objFlash.toString(), Brand.class);
                            categoryList.add(flashSaleListObject);
                        }


                        if (categoryList.size() > 0){
                            progressBar.setVisibility(View.GONE);
                            //universalPosts.add(obj);
                            for (int i = 0; i < categoryList.size(); i++) {
                                Brand bb = new Brand();
                                bb = categoryList.get(i);
                                bb.setPostType(BRAND);
                                bb.setTag(prefs.getStringPreferences(SP_BRAND_TAG));
                                universalPosts.add(bb);
                            }
                           // databaseAdapter.insertBrand(universalPosts );
                            adapter.notifyDataSetChanged();
                        }





                        adapter.notifyItemInserted(universalPosts.size());


                    }catch (Exception e){
                        Log.e(TAG, "onResponse: getBrands Exception : "  + e.getMessage() );
                    }
                }
                else{
                    loading = true;

                    Product noitem = new Product();
                    noitem.setPostType(CART_DETAIL_NO_ITEM);
                    universalPosts.add(noitem);

                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                loading = false;


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }




}
