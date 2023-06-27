package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Brand;
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
 * Created by ayesunaing on 4/15/17.
 */

public class BrandedDetailActivity extends AppCompatActivity implements Constant, ConstantVariable {
    private static final String TAG = "BrandedDetailActivity";
    RecyclerView recyclerView;
    MyPreference prefs;
    UniversalAdapter adapter;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    RecyclerView.LayoutManager manager;
    boolean loading = true;
    DatabaseAdapter databaseAdapter;

    Brand brand;
    String productType;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar1;


    CustomTextView title;
    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;
    CircularTextView  cart_text, wishlist_count;
    Display display;

    Animation bounce;
    AnimatorSet animationSet;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_detail);
        prefs = new MyPreference(BrandedDetailActivity.this);
        databaseAdapter = new DatabaseAdapter(BrandedDetailActivity.this);
        progressBar1    = (ProgressBar) findViewById(R.id.progressBar1);
        display = getWindowManager().getDefaultDisplay();
       // new MyFlurry(BrandedDetailActivity.this);

        Log.e(TAG, "onCreate: " );


        manager = new LinearLayoutManager(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();


        Intent intent = getIntent();
        brand = intent.getParcelableExtra("brand");
        prefs.saveIntPerferences(SP_PAGE_NUM_BRAND_DETAIL, SP_DEFAULT);
        productType = String.valueOf(prefs.getIntPreferences(SP_BRAND_ID+"-"+brand.getId()));

        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        wishlist_count      = findViewById(R.id.wishlist_count);

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

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "brand_detail");
                        //FlurryAgent.logEvent("Click Shopping Cart", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(BrandedDetailActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(BrandedDetailActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BrandedDetailActivity.this.finish();
            }
        });
        title.setText(brand.getTitle()+"");



        if(prefs.isNetworkAvailable()){
            JsonArrayRequest request = brandedList();
            AppController.getInstance().addToRequestQueue(request);
        }else{

            universalPosts.clear();
            universalPosts = databaseAdapter.getBrandDetail(productType);
            recyclerView.setLayoutManager(manager);
            adapter = new UniversalAdapter(BrandedDetailActivity.this, universalPosts);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }



    }

    public class onScrollListener extends RecyclerView.OnScrollListener {

        RecyclerView recyclerView;


        public onScrollListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        }

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
                prefs.saveIntPerferences(SP_PAGE_NUM_BRAND_DETAIL, prefs.getIntPreferences(SP_PAGE_NUM_BRAND_DETAIL) + SP_DEFAULT);
                JsonArrayRequest jsonArrayRequest = brandedList();
                AppController.getInstance().addToRequestQueue(jsonArrayRequest);
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();

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


    public void bounceCount (){
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce_animation);
        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext()
                , R.animator.flip_animation);
        int count = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);
        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }

        animationSet.setTarget(cart_text);
        cart_text.startAnimation(bounce);
        animationSet.start();
    }




    private JsonArrayRequest brandedList()
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantBrand_Detail+prefs.getIntPreferences(SP_BRAND_ID)+"&brand_id="+brand.getId()+"&page="+prefs.getIntPreferences(SP_PAGE_NUM_BRAND_DETAIL)+"&"+LANG+"="+prefs.getStringPreferences(LANGUAGE),

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
                        Type listType = new TypeToken<List<Product>>() {
                        }.getType();
                        List<Product> categoryList = mGson.fromJson(response.toString(), listType);
                        for (int i = 0; i < categoryList.size(); i++) {
                            Product product = new Product();
                            product = categoryList.get(i);
                            product.setPostType(BRANDED_DETAIL);
                            universalPosts.add(product);



                        }
                        databaseAdapter.insertBrandDetail(categoryList, productType);
                        Product pro = new Product();
                        pro.setPostType(CART_DETAIL_NO_ITEM);
                        universalPosts.add(pro);

                        recyclerView.setLayoutManager(manager);
                        adapter = new UniversalAdapter(BrandedDetailActivity.this, universalPosts);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                        recyclerView.addOnScrollListener(new onScrollListener(recyclerView));

                        adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        Log.e(TAG, "onResponse: "  + e.getMessage() );
                    }
                }else{
                    loading = true;
                    progressBar1.setVisibility(View.GONE);
                    Product noitem = new Product();
                    noitem.setPostType(CART_DETAIL_NO_ITEM);
                    universalPosts.add(noitem);

                    recyclerView.setLayoutManager(manager);
                    adapter = new UniversalAdapter(BrandedDetailActivity.this, universalPosts);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addOnScrollListener(new onScrollListener(recyclerView));


                    adapter.notifyDataSetChanged();

                }


                }

            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = false;
            }
        });
        return jsonObjReq;
    }



}
