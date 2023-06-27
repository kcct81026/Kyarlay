package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.kyarlay.ayesunaing.data.ToastHelper;
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

public class CampainDetailActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "CampainDetailActivity";

    RecyclerView recyclerView;
    MyPreference prefs;
    UniversalAdapter adapter;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    RecyclerView.LayoutManager manager;
    boolean loading = true;
    DatabaseAdapter databaseAdapter;

    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar1;
    Resources resources;
    int disID = 0;


    Display display ;

    CustomTextView title;
    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;
    CircularTextView  cart_text, wishlist_count;
    RelativeLayout layout_like;
    ImageView cart;

    Animation bounce;
    AnimatorSet animationSet;


    int fromdeep  = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_detail);


        prefs = new MyPreference(CampainDetailActivity.this);
        databaseAdapter = new DatabaseAdapter(CampainDetailActivity.this);
        progressBar1    = (ProgressBar) findViewById(R.id.progressBar1);
        Context context = LocaleHelper.setLocale(CampainDetailActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        manager = new LinearLayoutManager(getApplicationContext());
       // new MyFlurry(CampainDetailActivity.this);


        prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT_DETAIL, SP_DEFAULT);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(CampainDetailActivity.this, universalPosts);
        recyclerView.setAdapter(adapter);

        final Intent intent = getIntent();
        disID = intent.getIntExtra("id", 0);
        fromdeep = intent.getIntExtra("fromdeep", 0);

        display = getWindowManager().getDefaultDisplay();
       // cart             = (ImageView)    findViewById(R.id.cart);
        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);

        Log.e(TAG, "onCreate: " );


        layout_like     = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count  = (CircularTextView) findViewById(R.id.wishlist_count);
        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "discount_page");
                    //FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                } catch (Exception e) {
                }
                Intent intent = new Intent(CampainDetailActivity.this, WishListActivity.class);
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
                        //FlurryAgent.logEvent("Click Shopping Cart", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(CampainDetailActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent1 = new Intent(CampainDetailActivity.this, ActivityLogin.class);
                    startActivity(intent1);
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
                if (fromdeep == 1){
                    Intent backIntent = new Intent(CampainDetailActivity.this, MainActivity.class);
                    startActivity(backIntent);
                    finish();
                }
                else
                    finish();
            }
        });


        title.setText(intent.getStringExtra("name"));




        if(prefs.isNetworkAvailable()){
            progressBar1.setVisibility(View.VISIBLE);
            JsonArrayRequest request = getList(disID);
            AppController.getInstance().addToRequestQueue(request);
        }else{

            progressBar1.setVisibility(View.GONE);

            ToastHelper.showToast(CampainDetailActivity.this, resources.getString(R.string.no_internet_error));


        }

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
                    JsonArrayRequest jsonArrayRequest = getList(disID);
                    AppController.getInstance().addToRequestQueue(jsonArrayRequest);
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
            JsonArrayRequest jsonArrayRequest = getList(disID);
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
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



    //testing
    private JsonArrayRequest getList(int discountID)
    {


        int page    = prefs.getIntPreferences(SP_PAGE_NUM_DISCOUNT_DETAIL);

        Log.e(TAG, "getList: "   + constantCampainDetail+discountID+"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&page="+page);


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantCampainDetail+discountID+"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&page="+page,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar1.setVisibility(View.GONE);
                        if(response.length() > 0) {

                            loading = false;
                            prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT_DETAIL, prefs.getIntPreferences(SP_PAGE_NUM_DISCOUNT_DETAIL) + SP_DEFAULT);
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

                               // title.setText(categoryList.get(0).getBrand_name());

                                for (int i = 0; i < categoryList.size(); i++) {
                                    Product pro = new Product();
                                    pro = categoryList.get(i);
                                    pro.setPostType(CATEGORY_DETAIL);
                                    universalPosts.add(pro);


                                }
                                //databaseAdapter.insertProduct(categoryList, "promotion");
                                adapter.notifyDataSetChanged();

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                            Product pro = new Product();
                            pro.setPostType(CART_DETAIL_FOOTER);

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

                            prefs.remove(SP_PAGE_NUM_DISCOUNT_DETAIL);

                            adapter.notifyDataSetChanged();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar1.setVisibility(View.GONE);
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }



                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                universalPosts.add(noitem);


                adapter.notifyDataSetChanged();

            }
        });
        return jsonObjReq;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fromdeep == 1){
            Intent backIntent = new Intent(CampainDetailActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        }
        else
            finish();
    }
}
