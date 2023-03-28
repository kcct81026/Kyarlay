package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Campaign;
import com.kyarlay.ayesunaing.object.CategoryGrid;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.OrderDetailsObj;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "CategoryActivity";


    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    ArrayList<Product> universalPostsTemp = new ArrayList<>();
    ArrayList<CategoryMain> categoryList = new ArrayList<>();
    ArrayList<MainObject> brandList = new ArrayList<>();
    ArrayList<Campaign> discountList = new ArrayList<>();
    List<Product> productTempList = new ArrayList<>();
    boolean isMore = false;

    RecyclerView recyclerView ;
    UniversalAdapter universalAdapter;

    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager manager;
    Boolean loading = true;
    Boolean last = false;



    CustomTextView txtFilterTitle;
    LinearLayout title_layout, back_layout,filter_layout,filter_main_layout,search_layout;
    Display display;
    RelativeLayout cart_layout,option_layout;
    CircularTextView cart_text, wishlist_count;
    RelativeLayout layout_like;
    ImageView imgGrid,imgList;



    DatabaseAdapter databaseAdapter;

    Animation bounce;
    AnimatorSet animationSet;
    AppCompatActivity activity;
    MainItem categoryMain;



    private String url = "";
    int twoColumn = 1;
    int categoryID = -810;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_brand_click);

        new MyFlurry(CategoryActivity.this);
        databaseAdapter = new DatabaseAdapter(CategoryActivity.this);

        prefs = new MyPreference(CategoryActivity.this);
        Context context = LocaleHelper.setLocale(CategoryActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        activity = CategoryActivity.this;


        prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, SP_DEFAULT);
        prefs.saveBooleanPreference(SP_SHOW_MORE, false);

        Intent intent = getIntent();
        categoryMain = intent.getParcelableExtra("mainCat");


        display = getWindowManager().getDefaultDisplay();
        title_layout = (LinearLayout) findViewById(R.id.title_layout);
        back_layout = (LinearLayout) findViewById(R.id.back_layout);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);

        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        layout_like = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count = (CircularTextView) findViewById(R.id.wishlist_count);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        option_layout =  findViewById(R.id.option_layout);
        filter_layout =  findViewById(R.id.filter_layout);
        filter_main_layout =  findViewById(R.id.filter_main_layout);
        imgGrid =  findViewById(R.id.imgGrid);
        imgList =  findViewById(R.id.imgList);
        txtFilterTitle =  findViewById(R.id.txtFilterTitle);

        String titleText = resources.getString(R.string.top_seller);

        url = constantProductDetailSublist+categoryMain.getId()+
                "&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+
                "&version"+"="+prefs.getIntPreferences(SP_CURRENT_VERSION)  ;

        prefs.saveStringPreferences(SP_FILTER_URL, url);

        txtFilterTitle.setText(titleText);



        universalAdapter= new UniversalAdapter(CategoryActivity.this, universalPosts);
        manager  = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        filter_main_layout.setVisibility(View.GONE);


        Log.e(TAG, "onCreate: url " + url );


        if (twoColumn == 1){
            imgList.setVisibility(View.VISIBLE);
            imgGrid.setVisibility(View.GONE);
        }
        else{
            imgList.setVisibility(View.GONE);
            imgGrid.setVisibility(View.VISIBLE);
        }


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();

              /*  prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK,  SP_DEFAULT);
                universalPosts.clear();
                universalPostsTemp.clear();
                productTempList.clear();
                isMore = false;
                Product pro = new Product();
                pro.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro);
                universalAdapter.notifyDataSetChanged();

                if (categoryList.size() > 1){
                    if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                        universalPosts.remove(universalPosts.size() - 1);
                    }



                    CategoryGrid categoryGrid = new CategoryGrid();
                    categoryGrid.setShowMore(0);
                    categoryGrid.setPostType(SUB_CATEGORY_GRID);
                    categoryGrid.setCategoryMainList(categoryList);
                    universalPosts.add(categoryGrid);

                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("type", categoryList.get(0).getTag());
                        FlurryAgent.logEvent("Click Product Subcategory", mix);


                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: "  + e.getMessage() );
                    }

                    universalAdapter.notifyItemInserted(universalPosts.size());
                }

                if (brandList.size() > 0){
                    if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                        universalPosts.remove(universalPosts.size() - 1);
                    }
                    for (int i = 0 ; i < brandList.size(); i ++){
                        MainObject obj = brandList.get(i);

                        if (obj.getItems().size() > 0){
                            obj.setPostType(BRAND_BANNER);
                            universalPosts.add(obj);

                            universalAdapter.notifyItemInserted(universalPosts.size());
                        }


                    }
                }

                if (discountList.size() > 0){
                    if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                        universalPosts.remove(universalPosts.size() - 1);
                    }
                    MainObject mainObject = new MainObject();
                    mainObject.setTitle(resources.getString(R.string.discount_title));
                    mainObject.setPostType(MAIN_DISCOUNT);


                    List<MainItem> items = new ArrayList<>();
                    for (int i = 0; i < discountList.size(); i++) {
                        Campaign campaign = new Campaign();
                        campaign = discountList.get(i);
                        campaign.setPostType(CAMPAIGN);

                        MainItem mainItem = new MainItem();
                        mainItem.setDimen(campaign.getDimen());
                        mainItem.setId(campaign.getId());
                        mainItem.setTitle(campaign.getTitle());
                        mainItem.setUrl(campaign.getUrl());
                        mainItem.setPostType(CAMPAIGN);
                        items.add(mainItem);
                        mainObject.setItems(items);

                    }
                    universalPosts.add(mainObject);

                    universalAdapter.notifyItemInserted(universalPosts.size());
                }

                Product proFilter = new Product();
                if (twoColumn == 1)
                    proFilter.setId(1);
                else
                    proFilter.setId(0);
                proFilter.setPostType(FILTER_UI);
                universalPosts.add(proFilter);

                Product pro2 = new Product();
                pro2.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro2);
                universalAdapter.notifyItemInserted(universalPosts.size());

                getBrandItem(prefs.getIntPreferences(SP_PAGE_BRAND_CLICK));*/

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

                    Intent intent = new Intent(CategoryActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(CategoryActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });

        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "main");
                    FlurryAgent.logEvent("Click Search", mix);
                } catch (Exception e) {
                    Log.e(TAG, "onClick: "  + e.getMessage() );
                }
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fate_out);
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
                Intent intent = new Intent(CategoryActivity.this, WishListActivity.class);
                startActivity(intent);

            }
        });


        layout_like.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
       // title_layout.getLayoutParams().width    = ( display.getWidth() * 10) / 22;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
        search_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
        option_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
        filter_layout.getLayoutParams().width     = ( display.getWidth() * 19) / 22;

        option_layout.setVisibility(View.GONE);




        filter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFilterDilaog();

            }
        });


        option_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //setUI();
            }
        });



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
                    getBrandItem(prefs.getIntPreferences(SP_PAGE_BRAND_CLICK));
                }

                if (universalPosts.get(linearLayoutManager.findFirstVisibleItemPosition()).getPostType().equals(STAGGERED_ITEM) ){
                    filter_main_layout.setVisibility(View.VISIBLE);
                }
                else {
                    filter_main_layout.setVisibility(View.GONE);
                }

                    //STAGGERED_ITEM

            }
        });

      /*  Log.e(TAG, "onCreate: " );
        Product pro = new Product();
        pro.setPostType(CART_DETAIL_FOOTER);
        universalPosts.add(pro);
        universalAdapter.notifyDataSetChanged();
        getBrandItem(1);*/

        if(categoryMain != null) {

            Product pro = new Product();
            pro.setPostType(CART_DETAIL_FOOTER);
            universalPosts.add(pro);

            universalAdapter.notifyDataSetChanged();



            // getBrands();
            buildPostsRequest();
        }



    }

    public void clickCategory(int id, String title){
        categoryID = id;

        prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK,  SP_DEFAULT);
        universalPosts.clear();
        universalPostsTemp.clear();
        productTempList.clear();
        isMore = false;
        Product pro = new Product();
        pro.setPostType(CART_DETAIL_FOOTER);
        universalPosts.add(pro);
        universalAdapter.notifyDataSetChanged();

        if (categoryList.size() > 1){
            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                universalPosts.remove(universalPosts.size() - 1);
            }

            for (int i=0; i< categoryList.size(); i++){
                if (categoryList.get(i).getId() == id){
                    categoryList.get(i).setChoosen(true);
                }
                else
                    categoryList.get(i).setChoosen(false);
            }




            CategoryGrid categoryGrid = new CategoryGrid();

            if (prefs.getBooleanPreference(SP_SHOW_MORE))
                categoryGrid.setShowMore(1);
            else
                categoryGrid.setShowMore(0);

            categoryGrid.setPostType(SUB_CATEGORY_GRID);
            categoryGrid.setCategoryMainList(categoryList);
            universalPosts.add(categoryGrid);

            try {

                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", categoryList.get(0).getTag());
                FlurryAgent.logEvent("Click Product Subcategory", mix);


            } catch (Exception e) {
                Log.e(TAG, "onResponse: "  + e.getMessage() );
            }

            universalAdapter.notifyItemInserted(universalPosts.size());
        }

        if (id == -810 ){
            url =  constantProductDetailSublist+categoryMain.getId()+
                    "&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+
                    "&version"+"="+prefs.getIntPreferences(SP_CURRENT_VERSION)  +"&page=";

            prefs.saveStringPreferences(SP_FILTER_URL, url);


            if (brandList.size() > 0){
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                for (int i = 0 ; i < brandList.size(); i ++){
                    MainObject obj = brandList.get(i);

                    if (obj.getItems().size() > 0){
                        obj.setPostType(BRAND_BANNER);
                        universalPosts.add(obj);

                        universalAdapter.notifyItemInserted(universalPosts.size());
                    }


                }
            }

            if (discountList.size() > 0){
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                MainObject mainObject = new MainObject();
                mainObject.setTitle(resources.getString(R.string.discount_title));
                mainObject.setPostType(MAIN_DISCOUNT);


                List<MainItem> items = new ArrayList<>();
                for (int i = 0; i < discountList.size(); i++) {
                    Campaign campaign = new Campaign();
                    campaign = discountList.get(i);
                    campaign.setPostType(CAMPAIGN);

                    MainItem mainItem = new MainItem();
                    mainItem.setDimen(campaign.getDimen());
                    mainItem.setId(campaign.getId());
                    mainItem.setTitle(campaign.getTitle());
                    mainItem.setUrl(campaign.getUrl());
                    mainItem.setPostType(CAMPAIGN);
                    items.add(mainItem);
                    mainObject.setItems(items);

                }
                universalPosts.add(mainObject);

                universalAdapter.notifyItemInserted(universalPosts.size());
            }
        }
        else{

           url =   constantBrand_Detail+id+"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) +"&page=";

            prefs.saveStringPreferences(SP_FILTER_URL, url);
        }



        Product proFilter = new Product();

        if (twoColumn == 1)
            proFilter.setId(1);
        else
            proFilter.setId(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            txtFilterTitle.setText(Html.fromHtml(
                    resources.getString(R.string.top_seller)  , Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtFilterTitle.setText(Html.fromHtml(resources.getString(R.string.top_seller)));

        }

        proFilter.setTitle(resources.getString(R.string.top_seller));
        proFilter.setPostType(FILTER_UI);
        universalPosts.add(proFilter);

        Product pro2 = new Product();
        pro2.setPostType(CART_DETAIL_FOOTER);
        universalPosts.add(pro2);
        universalAdapter.notifyItemInserted(universalPosts.size());


        getBrandItem(prefs.getIntPreferences(SP_PAGE_BRAND_CLICK));
    }

    public void buildPostsRequest() {



        JsonArrayRequest request = new JsonArrayRequest(constantCategoryDetailSublist+categoryMain.getPost_type()+"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        categoryList.clear();

                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }

                        if(response.length() > 0){

                            boolean error = false;

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<CategoryMain>>() {}.getType();
                                categoryList = mGson.fromJson(response.toString(), listType);

                                //databaseAdapter.insertCategory(categoryList, database_control);
                            }catch (Exception e){
                                error = true;
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }

                            if (categoryList.size() > 1){

                                for (int i=0; i<categoryList.size();i++){
                                    categoryList.get(i).setChoosen(false);
                                }


                                CategoryMain categoryMain = new CategoryMain();
                                categoryMain.setTitle("All");
                                categoryMain.setImage("");
                                categoryMain.setId(-810);
                                categoryMain.setChoosen(true);
                                categoryList.add(0,categoryMain);


                                CategoryGrid categoryGrid = new CategoryGrid();
                                categoryGrid.setShowMore(0);
                                categoryGrid.setPostType(SUB_CATEGORY_GRID);
                                categoryGrid.setCategoryMainList(categoryList);
                                universalPosts.add(categoryGrid);

                                try {

                                    Map<String, String> mix = new HashMap<String, String>();
                                    mix.put("type", categoryList.get(0).getTag());
                                    FlurryAgent.logEvent("Click Product Subcategory", mix);


                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: "  + e.getMessage() );
                                }

                                universalAdapter.notifyItemInserted(universalPosts.size());
                            }




                        }
                        Product pro1 = new Product();
                        pro1.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro1);
                        getBrands();


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getBrands();


            }
        });


        AppController.getInstance().addToRequestQueue(request);

    }



    private void getBrands()
    {

        Log.e(TAG, "getBrands: "  + constantBrandSponsor +categoryMain.getId() +  "&language=" + prefs.getStringPreferences(SP_LANGUAGE)  );

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantBrandSponsor +categoryMain.getId() +  "&language=" + prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }


                        if(response.length() > 0) {


                            /*
                               MainCategoryObj obj = new MainCategoryObj();
                        obj.setHeader_image(response.getString("header_image"));
                        obj.setHeader_image_dimen(response.getInt("header_image_dimen"));
                        obj.setPostType(CATEGORY_BANNER);



                        JSONArray flashList = response.getJSONArray("collections");
                        List<Brand> categoryList  = new ArrayList<>(flashList.length());
                        Gson gson = new Gson();

                             */



                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<MainObject>>() {
                                }.getType();
                                brandList = mGson.fromJson(response.toString(), listType);






                                for (int i = 0 ; i < brandList.size(); i ++){
                                    MainObject obj = brandList.get(i);

                                    if (obj.getItems().size() > 0){
                                        obj.setPostType(BRAND_BANNER);
                                        universalPosts.add(obj);

                                        universalAdapter.notifyItemInserted(universalPosts.size());
                                    }


                                }


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }


                        }

                        if(prefs.isNetworkAvailable()) {

                            Product pro = new Product();
                            pro.setPostType(CART_DETAIL_FOOTER);
                            universalPosts.add(pro);
                            universalAdapter.notifyItemInserted(universalPosts.size());



                            getDiscountList();

                        }else {
                            LayoutInflater inflater1 = getLayoutInflater();
                            View toastRoot = inflater1.inflate(R.layout.custom_toast_layout, null);
                            CustomTextView textView = (CustomTextView)toastRoot.findViewById(R.id.toast_text);
                            textView.setText(resources.getString(R.string.no_internet_error));
                            textView.setGravity(Gravity.CENTER);
                            Toast toast = new Toast(activity);
                            toast.setView(toastRoot);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getDiscountList();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }

    public void getDiscountList() {


        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMainDiscountList +  "?language=" + prefs.getStringPreferences(SP_LANGUAGE) + "&category_id=" + categoryMain.getId() , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }

                if(response.length() > 0){

                    try{

                        MainCategoryObj obj = new MainCategoryObj();
                        obj.setHeader_image(response.getString("header_image"));
                        obj.setHeader_image_dimen(response.getInt("header_image_dimen"));
                        obj.setPostType(CATEGORY_BANNER);



                        JSONArray flashList = response.getJSONArray("collections");
                        discountList = new ArrayList<>(flashList.length());



                        Gson gson = new Gson();
                        for (int i = 0; i < flashList.length(); i++) {

                            JSONObject objFlash = flashList.getJSONObject(i);
                            Campaign flashSaleListObject = gson.fromJson(objFlash.toString(), Campaign.class);
                            discountList.add(flashSaleListObject);
                        }



                        if (discountList.size() > 0){

                          //  universalPosts.add(obj);

                            MainObject mainObject = new MainObject();
                            mainObject.setTitle(resources.getString(R.string.discount_title));
                            mainObject.setPostType(MAIN_DISCOUNT);


                            List<MainItem> items = new ArrayList<>();
                            for (int i = 0; i < discountList.size(); i++) {
                                Campaign campaign = new Campaign();
                                campaign = discountList.get(i);
                                campaign.setPostType(CAMPAIGN);

                                MainItem mainItem = new MainItem();
                                mainItem.setDimen(campaign.getDimen());
                                mainItem.setId(campaign.getId());
                                mainItem.setTitle(campaign.getTitle());
                                mainItem.setUrl(campaign.getUrl());
                                mainItem.setPostType(CAMPAIGN);
                                items.add(mainItem);
                                mainObject.setItems(items);

                            }
                            universalPosts.add(mainObject);

                            universalAdapter.notifyItemInserted(universalPosts.size());

                        }


                    }catch (Exception e){

                    }
                }


                Product proFilter = new Product();
                proFilter.setTitle(resources.getString(R.string.top_seller));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    txtFilterTitle.setText(Html.fromHtml(
                            resources.getString(R.string.top_seller)  , Html.FROM_HTML_MODE_COMPACT));
                } else {
                    txtFilterTitle.setText(Html.fromHtml(resources.getString(R.string.top_seller)));

                }
                if (twoColumn == 1)
                    proFilter.setId(1);
                else
                    proFilter.setId(0);
                proFilter.setPostType(FILTER_UI);
                universalPosts.add(proFilter);



                if (prefs.isNetworkAvailable()) {
                    prefs.saveIntPerferences(SP_PAGE_CATEGORY, SP_DEFAULT);

                    Product pro = new Product();
                    pro.setPostType(CART_DETAIL_FOOTER);
                    universalPosts.add(pro);
                    universalAdapter.notifyItemInserted(universalPosts.size());
                    getBrandItem(1);
                }






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                /*  buildPostsRequest();*/
                if (prefs.isNetworkAvailable()) {
                    prefs.saveIntPerferences(SP_PAGE_CATEGORY, SP_DEFAULT);

                    Product proFilter = new Product();
                    if (twoColumn == 1)
                        proFilter.setId(1);
                    else
                        proFilter.setId(0);
                    proFilter.setPostType(FILTER_UI);
                    universalPosts.add(proFilter);

                    Product pro = new Product();
                    pro.setPostType(CART_DETAIL_FOOTER);
                    universalPosts.add(pro);
                    universalAdapter.notifyItemInserted(universalPosts.size());
                    getBrandItem(1);
                }


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");




    }




    public void showFilterDilaog(){

        final int[] chooseFilter = {-1};

        Dialog mBottomSheetDialog = new Dialog(activity);

        mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBottomSheetDialog.setContentView(R.layout.filter_popup_dialog);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);

        Window window = mBottomSheetDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
        wlp.height   = activity.getWindowManager().getDefaultDisplay().getHeight();
        window.setAttributes(wlp);

        ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);
        ImageView img_most_seller = mBottomSheetDialog.findViewById(R.id.img_most_seller);
        ImageView img_new_arrival = mBottomSheetDialog.findViewById(R.id.img_new_arrival);
        ImageView img_max_min = mBottomSheetDialog.findViewById(R.id.img_max_min);
        ImageView img_min_max = mBottomSheetDialog.findViewById(R.id.img_min_max);

        LinearLayout linearSeller = mBottomSheetDialog.findViewById(R.id.linearSeller);
        LinearLayout linearNewArrival = mBottomSheetDialog.findViewById(R.id.linearNewArrival);
        LinearLayout lienarMaxMin = mBottomSheetDialog.findViewById(R.id.lienarMaxMin);
        LinearLayout linearMinMax = mBottomSheetDialog.findViewById(R.id.linearMinMax);

        CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
        CustomTextView txt_most_seller = mBottomSheetDialog.findViewById(R.id.txt_most_seller);
        CustomTextView txttxt_new_arrival = mBottomSheetDialog.findViewById(R.id.txt_new_arrival);
        CustomTextView txt_min_max = mBottomSheetDialog.findViewById(R.id.txt_min_max);
        CustomTextView txt_max_min = mBottomSheetDialog.findViewById(R.id.txt_max_min);
        CustomTextView txtCancel = mBottomSheetDialog.findViewById(R.id.txtCancel);
        CustomTextView txtApply = mBottomSheetDialog.findViewById(R.id.txtApply);

        txt_most_seller.setText(resources.getString(R.string.top_seller));
        txttxt_new_arrival.setText(resources.getString(R.string.news));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txt_min_max.setText(Html.fromHtml(
                    resources.getString(R.string.min_max_filter)  , Html.FROM_HTML_MODE_COMPACT));
            txt_max_min.setText(Html.fromHtml(
                    resources.getString(R.string.max_min_filter)  , Html.FROM_HTML_MODE_COMPACT));
        } else {
            txt_max_min.setText(Html.fromHtml(resources.getString(R.string.max_min_filter)));
            txt_min_max.setText(Html.fromHtml(resources.getString(R.string.min_max_filter)));
        }

        txt_most_seller.setText(resources.getString(R.string.min_max_price));
        txt_most_seller.setText(resources.getString(R.string.top_seller));


        linearSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_most_seller.setVisibility(View.VISIBLE);
                img_new_arrival.setVisibility(View.GONE);
                img_max_min.setVisibility(View.GONE);
                img_min_max.setVisibility(View.GONE);
                chooseFilter[0] = 0;

            }
        });

        linearNewArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_most_seller.setVisibility(View.GONE);
                img_new_arrival.setVisibility(View.VISIBLE);
                img_max_min.setVisibility(View.GONE);
                img_min_max.setVisibility(View.GONE);
                chooseFilter[0] = 1;
            }
        });


        linearMinMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_most_seller.setVisibility(View.GONE);
                img_new_arrival.setVisibility(View.GONE);
                img_max_min.setVisibility(View.GONE);
                img_min_max.setVisibility(View.VISIBLE);
                chooseFilter[0] = 3;
            }
        });

        lienarMaxMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_most_seller.setVisibility(View.GONE);
                img_new_arrival.setVisibility(View.GONE);
                img_max_min.setVisibility(View.VISIBLE);
                img_min_max.setVisibility(View.GONE);
                chooseFilter[0] = 2;
            }
        });



        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        final String[] tempFiltertitle = {""};
        txtApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( chooseFilter[0] == -1){
                    ToastHelper.showToast(activity, "Must choose filter");
                }
                else{
                    mBottomSheetDialog.cancel();

                    String tempUrl = url;



                    if ( chooseFilter[0] == 0){
                        url = url + "&sort_by=popular";
                        tempFiltertitle[0] = resources.getString(R.string.top_seller);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                            txtFilterTitle.setText(Html.fromHtml(
                                    resources.getString(R.string.top_seller)  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtFilterTitle.setText(Html.fromHtml(resources.getString(R.string.top_seller)));

                        }
                    }
                    else if ( chooseFilter[0] == 1){
                        url = url + "&sort_by=recent";
                        tempFiltertitle[0] = resources.getString(R.string.news);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                            txtFilterTitle.setText(Html.fromHtml(
                                    resources.getString(R.string.news)  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtFilterTitle.setText(Html.fromHtml(resources.getString(R.string.news)));

                        }

                    }

                    else if ( chooseFilter[0] == 2){

                        url =  url + "&sort_by=price_reverse";

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                            txtFilterTitle.setText(Html.fromHtml(
                                    resources.getString(R.string.max_min_filter)  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtFilterTitle.setText(Html.fromHtml(resources.getString(R.string.max_min_filter)));

                        }

                        tempFiltertitle[0] = resources.getString(R.string.max_min_filter);

                    }

                    else if ( chooseFilter[0] == 3){

                        url =  url + "&sort_by=price";


                        tempFiltertitle[0] = resources.getString(R.string.min_max_filter);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                            txtFilterTitle.setText(Html.fromHtml(
                                    resources.getString(R.string.min_max_filter)  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtFilterTitle.setText(Html.fromHtml(resources.getString(R.string.min_max_filter)));

                        }
                    }

                    if (!url.equals(tempUrl)){



                        prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK,  SP_DEFAULT);
                        universalPosts.clear();
                        universalPostsTemp.clear();
                        productTempList.clear();
                        isMore = false;
                        Product pro = new Product();
                        pro.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro);
                        universalAdapter.notifyDataSetChanged();

                        if (categoryList.size() > 1){
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            for (int i=0; i< categoryList.size(); i++){
                                if (categoryList.get(i).getId() == categoryID){
                                    categoryList.get(i).setChoosen(true);
                                }
                                else
                                    categoryList.get(i).setChoosen(false);
                            }



                            CategoryGrid categoryGrid = new CategoryGrid();

                            if (prefs.getBooleanPreference(SP_SHOW_MORE))
                                categoryGrid.setShowMore(1);
                            else
                                categoryGrid.setShowMore(0);

                            categoryGrid.setPostType(SUB_CATEGORY_GRID);
                            categoryGrid.setCategoryMainList(categoryList);
                            universalPosts.add(categoryGrid);

                            try {

                                Map<String, String> mix = new HashMap<String, String>();
                                mix.put("type", categoryList.get(0).getTag());
                                FlurryAgent.logEvent("Click Product Subcategory", mix);


                            } catch (Exception e) {
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }

                            universalAdapter.notifyItemInserted(universalPosts.size());
                        }

                        Log.e(TAG, "show dialog url ---- "  + categoryID);

                        if (categoryID == -810 ){
                            if (brandList.size() > 0){
                                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                    universalPosts.remove(universalPosts.size() - 1);
                                }
                                for (int i = 0 ; i < brandList.size(); i ++){
                                    MainObject obj = brandList.get(i);

                                    if (obj.getItems().size() > 0){
                                        obj.setPostType(BRAND_BANNER);
                                        universalPosts.add(obj);

                                        universalAdapter.notifyItemInserted(universalPosts.size());
                                    }


                                }
                            }

                            if (discountList.size() > 0){
                                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                    universalPosts.remove(universalPosts.size() - 1);
                                }
                                MainObject mainObject = new MainObject();
                                mainObject.setTitle(resources.getString(R.string.discount_title));
                                mainObject.setPostType(MAIN_DISCOUNT);


                                List<MainItem> items = new ArrayList<>();
                                for (int i = 0; i < discountList.size(); i++) {
                                    Campaign campaign = new Campaign();
                                    campaign = discountList.get(i);
                                    campaign.setPostType(CAMPAIGN);

                                    MainItem mainItem = new MainItem();
                                    mainItem.setDimen(campaign.getDimen());
                                    mainItem.setId(campaign.getId());
                                    mainItem.setTitle(campaign.getTitle());
                                    mainItem.setUrl(campaign.getUrl());
                                    mainItem.setPostType(CAMPAIGN);
                                    items.add(mainItem);
                                    mainObject.setItems(items);

                                }
                                universalPosts.add(mainObject);

                                universalAdapter.notifyItemInserted(universalPosts.size());
                            }
                        }





                        Product proFilter = new Product();
                        if (twoColumn == 1)
                            proFilter.setId(1);
                        else
                            proFilter.setId(0);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                            txtFilterTitle.setText(Html.fromHtml(
                                    tempFiltertitle[0]  , Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            txtFilterTitle.setText(Html.fromHtml(tempFiltertitle[0]));

                        }
                        proFilter.setTitle(tempFiltertitle[0]);
                        proFilter.setPostType(FILTER_UI);
                        universalPosts.add(proFilter);

                        Product pro2 = new Product();
                        pro2.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro2);
                        universalAdapter.notifyItemInserted(universalPosts.size());

                        recyclerView.scrollToPosition(universalPosts.size());



                        getBrandItem(prefs.getIntPreferences(SP_PAGE_BRAND_CLICK));
                    }


                }

            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.cancel();
            }
        });

/*
                View addView1 = LayoutInflater.from(activity).inflate(R.layout.layout_info_promo,null);
                CustomTextView txt1 = addView1.findViewById(R.id.txt);
                txt1.setText(str);
                productDetailsHolder.discount_layout.addView(addView1);*/

        mBottomSheetDialog.show();
    }

    public void setUI(int column, String tt){

        if (column == 0){
            twoColumn = 1;
        }
        else {
            twoColumn = 0;
        }

        universalPosts.clear();
        Product pro = new Product();
        pro.setPostType(CART_DETAIL_FOOTER);

        universalPosts.add(pro);
        universalAdapter.notifyDataSetChanged();

        if (categoryList.size() > 1){
            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                universalPosts.remove(universalPosts.size() - 1);
            }

            for (int i=0; i< categoryList.size(); i++){
                if (categoryList.get(i).getId() == categoryID){
                    categoryList.get(i).setChoosen(true);
                }
                else
                    categoryList.get(i).setChoosen(false);
            }



            CategoryGrid categoryGrid = new CategoryGrid();

            if (prefs.getBooleanPreference(SP_SHOW_MORE))
                categoryGrid.setShowMore(1);
            else
                categoryGrid.setShowMore(0);

            categoryGrid.setPostType(SUB_CATEGORY_GRID);
            categoryGrid.setCategoryMainList(categoryList);
            universalPosts.add(categoryGrid);

            try {

                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", categoryList.get(0).getTag());
                FlurryAgent.logEvent("Click Product Subcategory", mix);


            } catch (Exception e) {
                Log.e(TAG, "onResponse: "  + e.getMessage() );
            }

            universalAdapter.notifyItemInserted(universalPosts.size());
        }

        if (categoryID == -810){
            if (brandList.size() > 0){
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                for (int i = 0 ; i < brandList.size(); i ++){
                    MainObject obj = brandList.get(i);

                    if (obj.getItems().size() > 0){
                        obj.setPostType(BRAND_BANNER);
                        universalPosts.add(obj);

                        universalAdapter.notifyItemInserted(universalPosts.size());
                    }


                }
            }

            if (discountList.size() > 0){
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                MainObject mainObject = new MainObject();
                mainObject.setTitle(resources.getString(R.string.discount_title));
                mainObject.setPostType(MAIN_DISCOUNT);


                List<MainItem> items = new ArrayList<>();
                for (int i = 0; i < discountList.size(); i++) {
                    Campaign campaign = new Campaign();
                    campaign = discountList.get(i);
                    campaign.setPostType(CAMPAIGN);

                    MainItem mainItem = new MainItem();
                    mainItem.setDimen(campaign.getDimen());
                    mainItem.setId(campaign.getId());
                    mainItem.setTitle(campaign.getTitle());
                    mainItem.setUrl(campaign.getUrl());
                    mainItem.setPostType(CAMPAIGN);
                    items.add(mainItem);
                    mainObject.setItems(items);

                }
                universalPosts.add(mainObject);

                universalAdapter.notifyItemInserted(universalPosts.size());
            }

        }


        Product proFilter = new Product();
        if (twoColumn == 1)
            proFilter.setId(1);
        else
            proFilter.setId(0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            txtFilterTitle.setText(Html.fromHtml(
                   tt , Html.FROM_HTML_MODE_COMPACT));
        } else {
            txtFilterTitle.setText(Html.fromHtml(tt));

        }
        proFilter.setTitle(tt);
        proFilter.setPostType(FILTER_UI);
        universalPosts.add(proFilter);

        recyclerView.scrollToPosition(universalPosts.size());

        if (twoColumn == 1) {

            for (int i=0; i< universalPostsTemp.size();i++) {
                if (i % 2 != 0 && i != 0) {

                    List<Product> tempList1 = new ArrayList<>();
                    tempList1.add(universalPostsTemp.get(i - 1));
                    tempList1.add(universalPostsTemp.get(i));
                    OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                    orderDetailsObj.setProductList(tempList1);
                    orderDetailsObj.setPostType(STAGGERED_ITEM);

                    universalPosts.add(orderDetailsObj);


                } else if (i % 2 == 0 && i == universalPostsTemp.size() - 1) {

                    if (last) {


                        List<Product> tempList1 = new ArrayList<>();
                        tempList1.add(universalPostsTemp.get(i));
                        OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                        orderDetailsObj.setProductList(tempList1);
                        orderDetailsObj.setPostType(STAGGERED_ITEM);
                        universalPosts.add(orderDetailsObj);

                    } else {

                        productTempList.clear();
                        isMore = true;
                        productTempList.add(universalPostsTemp.get(i));
                    }

                    Log.e(TAG, "setUI: ************* "  + (universalPostsTemp.size() - 1)  );


                }
            }

        }
        else{
            for (int i=0; i< universalPostsTemp.size();i++){
                Product product = new Product();
                product = universalPostsTemp.get(i);
                product.setPostType(CATEGORY_DETAIL);
                universalPosts.add(product);
            }
        }




        if (last){
            loading = true;
            Product pro12 = new Product();
            pro12.setPostType(CART_DETAIL_NO_ITEM);
            universalPosts.add(pro12);
        }
        else
            loading = false;



        universalAdapter.notifyItemInserted(universalPosts.size()-1);


      /*  if (twoColumn == 0){
            imgList.setVisibility(View.GONE);
            imgGrid.setVisibility(View.VISIBLE);



        }
        else {
            imgList.setVisibility(View.VISIBLE);
            imgGrid.setVisibility(View.GONE);
        }*/


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();





    }




    private void getBrandItem(int page)
    {

        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url +  "&page=" + page ,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }


                            prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, prefs.getIntPreferences(SP_PAGE_BRAND_CLICK) + SP_DEFAULT);
                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> products = mGson.fromJson(response.toString(), listType);


                                for (int i=0; i< products.size();i++)
                                    universalPostsTemp.add(products.get(i));



                                if (twoColumn == 1){

                                    if (isMore){

                                        if (productTempList.size()> 0)
                                            products.add(0, productTempList.get(productTempList.size()-1));
                                    }


                                    for (int i = 0; i < products.size(); i++) {


                                        isMore = false;

                                        if(i%2 != 0 && i!=0){

                                            List<Product> tempList1 = new ArrayList<>();
                                            tempList1.add(products.get(i-1));
                                            tempList1.add(products.get(i));
                                            OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                                            orderDetailsObj.setProductList(tempList1);
                                            orderDetailsObj.setPostType(STAGGERED_ITEM);


                                            universalPosts.add(orderDetailsObj);

                                        }
                                        else if(i %2 == 0 && i== products.size() - 1){

                                            isMore = true;
                                            productTempList.add(products.get(i));

                                        }


                                    }


                                }
                                else {
                                    for (int i = 0; i < products.size(); i++) {
                                        Product product = new Product();


                                        product = products.get(i);
                                        product.setPostType(CATEGORY_DETAIL);

                                        universalPosts.add(product);
                                    }

                                }
                                Product pro = new Product();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(pro);

                                universalAdapter.notifyItemInserted(universalPosts.size());

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else{
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            loading = true;
                            last = true;
                            if (isMore) {

                                if (productTempList.size() > 0) {

                                    List<Product> tempList1 = new ArrayList<>();
                                    tempList1.add(productTempList.get(productTempList.size() - 1));
                                    OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                                    orderDetailsObj.setProductList(tempList1);
                                    orderDetailsObj.setPostType(STAGGERED_ITEM);

                                    universalPosts.add(orderDetailsObj);
                                    isMore = false;

                                }
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);



                            universalAdapter.notifyItemInserted(universalPosts.size());



                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }


                universalAdapter.notifyItemInserted(universalPosts.size());




            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


}
