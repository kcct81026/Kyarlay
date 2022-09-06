package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.kyarlay.ayesunaing.object.CategoryGrid;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrandActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "BrandActivity";

    ArrayList<CategoryMain> categoryList        = new ArrayList<>();
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();

    RecyclerView recyclerView ;
    UniversalAdapter universalAdapter;

    ProgressBar progressBar;

    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager manager;
    Boolean loading = true;


    CustomTextView title;
    LinearLayout title_layout, back_layout;
    Display display;
    int brandID;
    RelativeLayout cart_layout;
    CircularTextView cart_text, wishlist_count;
    RelativeLayout layout_like;


    DatabaseAdapter databaseAdapter;
    String fromClass = "";
    boolean fromBrandAll = false;

    Animation bounce;
    AnimatorSet animationSet;

    int fromdeep = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);


        new MyFlurry(BrandActivity.this);
        databaseAdapter = new DatabaseAdapter(BrandActivity.this);

        prefs = new MyPreference(BrandActivity.this);
        Context context = LocaleHelper.setLocale(BrandActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        brandID = bundle.getInt("id");
        fromdeep = bundle.getInt("fromdeep");
        fromClass = bundle.getString("fromClass");
        fromBrandAll = bundle.getBoolean("fromBrandAll");


        Log.e(TAG, "onCreate: " );
        prefs.saveIntPerferences(BRAND_ID, brandID);
        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("brand_id", String.valueOf(brandID));
            mix.put("status", fromClass);
            FlurryAgent.logEvent("Click Brand Page", mix);
        } catch (Exception e) {
        }



        prefs.saveIntPerferences(SP_PAGE_NUM_BRAND_DETAIL, SP_DEFAULT);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        display = getWindowManager().getDefaultDisplay();
        title = (CustomTextView) findViewById(R.id.title);
        title_layout = (LinearLayout) findViewById(R.id.title_layout);
        back_layout = (LinearLayout) findViewById(R.id.back_layout);

        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        layout_like = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count = (CircularTextView) findViewById(R.id.wishlist_count);


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromClass != null && fromClass.trim().length() > 0){
                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "Brand");
                        FlurryAgent.logEvent("Incoming Pushnotification Click", mix);
                    } catch (Exception e) {}
                    Intent mainIntent = new Intent(BrandActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }
                else if (fromdeep == 1){
                    Intent backIntent = new Intent(BrandActivity.this, MainActivity.class);
                    startActivity(backIntent);
                    finish();
                }

                else
                    finish();
            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "brand_page");
                        FlurryAgent.logEvent("Click Shopping Cart", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(BrandActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(BrandActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });
        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "brand_page");
                    FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                } catch (Exception e) {
                }
                Intent intent = new Intent(BrandActivity.this, WishListActivity.class);
                startActivity(intent);

            }
        });

        layout_like.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;


        universalAdapter= new UniversalAdapter(BrandActivity.this, universalPosts);
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(universalAdapter);


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
                    getBrandItem();
                }

            }
        });

        brandHeader();
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
            universalAdapter.notifyItemInserted(universalPosts.size());
            getBrandItem();
        }

    }




    private void brandHeader(){


        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantBrandDetailCategory+brandID +   "?language=" + prefs.getStringPreferences(SP_LANGUAGE), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {


                progressBar.setVisibility(View.GONE);

                if (response.length() > 0){

                    try {
                        String name = response.getString("name");
                        String desc = response.getString("desc");
                        JSONArray array = response.getJSONArray("cover_photos");
                        String recommandation = response.getString("recommandation");
                        JSONArray categories = response.getJSONArray("categories");
                        title.setText(name);

                        if(array.length() > 0) {


                            List<MainItem> sliderAds = new ArrayList<>();

                            for (int j = 0; j < array.length(); j++) {
                                JSONObject object = array.getJSONObject(j);
                                MainItem item = new MainItem();
                                item.setId(object.getInt("id"));
                                item.setUrl(object.getString("url"));
                                item.setTitle(object.getString("title"));
                                item.setDimen(object.getInt("dimen"));
                                item.setOpen_target(object.getString("open_target"));
                                item.setDiscount_percentage(object.getInt("discount_percentage"));
                                item.setPost_type(object.getString("post_type"));
                                item.setPreview_url(object.getString("preview_url"));

                                sliderAds.add(item);
                            }

                            MainObject mainObject = new MainObject();
                            mainObject.setPostType(SLIDER);
                            mainObject.setItems(sliderAds);


                            universalPosts.add(mainObject);

                        }

                        for(int i = 0; i < categories.length(); i ++){
                            JSONObject obj = categories.getJSONObject(i);
                            CategoryMain categoryMain = new CategoryMain();
                            categoryMain.setId(obj.getInt("id"));
                            categoryMain.setTitle(obj.getString("name"));
                            categoryMain.setTag(obj.getString("tag"));
                            categoryMain.setImage(obj.getString("image"));
                            categoryList.add(categoryMain);
                        }

                        if(categoryList.size() > 0){
                            CategoryGrid categoryGrid = new CategoryGrid();
                            categoryGrid.setPostType(CATEGORY_GRID);
                            categoryGrid.setCategoryMainList(categoryList);
                            universalPosts.add(categoryGrid);

                            prefs.saveIntPerferences(BRAND_CATEGORY_TAG, categoryList.get(0).getId());

                            Product pro = new Product();
                            pro.setPriId(0);
                            pro.setPostType(POPULAR_BANNER);
                            if (prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_ENGLISH)){
                                pro.setTitle( resources.getString(R.string.populars) + " of " + title.getText().toString());
                            }
                            if (prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_ENGLISH)){
                                pro.setTitle( resources.getString(R.string.populars) + " of " + title.getText().toString());
                            }
                            else{
                                pro.setTitle( title.getText().toString() + " "  +  resources.getString(R.string.from_text)  + " " + resources.getString(R.string.populars) );
                            }
                            universalPosts.add(pro);
                            getBrandItem();
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: "  + e.getMessage() );
                    }
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getBrandItem();
            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "memberchecking");

    }



    public void bounceCount (){
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce_animation);
        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext()
                , R.animator.flip_animation);
        int count = databaseAdapter.getOrderCount();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(fromClass != null && fromClass.trim().length() > 0){
            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("source", "Brand");
                FlurryAgent.logEvent("Incoming Pushnotification Click", mix);
            } catch (Exception e) {}
            Intent mainIntent = new Intent(BrandActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }
        else if (fromdeep == 1){
            Intent backIntent = new Intent(BrandActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        }
        else
            finish();
    }



    private void goToNextIntent(ArrayList<Product> list, Dialog dialog) {

        if(list.size() == 1){
            Product product = (Product) list.get(0);
            Intent intent = new Intent(BrandActivity.this, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("product", product);
            intent.putExtras(bundle);
            startActivity(intent);
            dialog.dismiss();

        }
        else if (list.size() > 1) {
            Intent intent = new Intent(BrandActivity.this, ActivityAdsList.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("product", list);
            bundle.putString("fromClass", "");
            intent.putExtras(bundle);
            startActivity(intent);
            dialog.dismiss();

        }
    }

    private void getBrandItem()
    {
        String url = "";


        url = constantBrand_Detail+ 0 +"&brand_id="+brandID+"&page="+prefs.getIntPreferences(SP_PAGE_NUM_BRAND_DETAIL)+"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ;


        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            prefs.saveIntPerferences(SP_PAGE_NUM_BRAND_DETAIL, prefs.getIntPreferences(SP_PAGE_NUM_BRAND_DETAIL) + SP_DEFAULT);
                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> products = mGson.fromJson(response.toString(), listType);



                                for (int i = 0; i < products.size(); i++) {
                                    Product product = new Product();
                                    product = products.get(i);
                                    product.setPostType(CATEGORY_DETAIL);
                                    universalPosts.add(product);
                                }

                                Reading pro = new Reading();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(pro);
                                universalAdapter.notifyDataSetChanged();

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

                            universalAdapter.notifyDataSetChanged();

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


                universalAdapter.notifyDataSetChanged();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


}
