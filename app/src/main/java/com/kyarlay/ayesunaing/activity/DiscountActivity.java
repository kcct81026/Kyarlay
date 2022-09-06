package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.custom_widget.FlowLayout;
import com.kyarlay.ayesunaing.custom_widget.TagAdapter;
import com.kyarlay.ayesunaing.custom_widget.TagFlowLayout;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Campaign;
import com.kyarlay.ayesunaing.object.Category;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
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

import me.myatminsoe.mdetect.MDetect;

public class DiscountActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "DiscountActivity";

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

    CircularTextView cart_text, wishlist_count;

    private boolean discount = false;
    String url = "";

    TagAdapter tagAdapter;
    TagFlowLayout gridView;
    RecyclerViewHeader header;
    LinearLayout linearHeader;
    List<Category> categoryList1 = new ArrayList<>();
    RecyclerView.LayoutManager manager;
    ArrayList<MainCategoryObj> mainCategoryObjArrayList = new ArrayList<>();
    int selectedPosition = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        prefs            = new MyPreference(DiscountActivity.this);
        Context context = LocaleHelper.setLocale(DiscountActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display     = getWindowManager().getDefaultDisplay();
        new MyFlurry(DiscountActivity.this);

        adapter = new UniversalAdapter(DiscountActivity.this, universalPosts);
        databaseAdapter = new DatabaseAdapter(DiscountActivity.this);
        setContentView(R.layout.discount_header_activity);
        recyclerView    = findViewById(R.id.recycler_view);
        progressBar     = findViewById(R.id.progressBar);
        back_layout     = findViewById(R.id.back_layout);
        title_layout    = findViewById(R.id.title_layout);
        like_layout     = findViewById(R.id.like_layot);
        cart_layout     = findViewById(R.id.cart_layout);
        title           = findViewById(R.id.title);
        cart_text       = findViewById(R.id.menu_cart_idenfier);
        wishlist_count  = findViewById(R.id.wishlist_count);

        header              = (RecyclerViewHeader) findViewById(R.id.header);
        gridView            = (TagFlowLayout) findViewById(R.id.category_detail_header);
        linearHeader            = (LinearLayout) findViewById(R.id.linearHeader);
        gridView.setMaxSelectCount(1);


        Log.e(TAG, "onCreate: " );

        discount = getIntent().getBooleanExtra("discount", false);

        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        like_layout.getLayoutParams().width     = ( display.getWidth() * 3 ) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;





        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    if (discount){
                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "product_list");
                            FlurryAgent.logEvent("Click Product Wishlist Icon", mix);

                        } catch (Exception e) {
                        }

                    }else{
                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "collection_list");
                            FlurryAgent.logEvent("Click Product Wishlist Icon", mix);

                        } catch (Exception e) {
                        }

                    }

                    Intent intent = new Intent(DiscountActivity.this, WishListActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DiscountActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }



            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    if (discount){
                        try {

                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "product_list");
                            FlurryAgent.logEvent("Click Shopping Cart", mix);
                        } catch (Exception e) {
                        }

                    }else{
                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("source", "collection_list");
                            FlurryAgent.logEvent("Click Shopping Cart", mix);

                        } catch (Exception e) {
                        }

                    }
                    Intent intent = new Intent(DiscountActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DiscountActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });
        prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT, SP_DEFAULT);

        if (discount){
            title.setText(resources.getString(R.string.discount_title));
            header.setVisibility(View.VISIBLE);
        }
        else{
            title.setText(resources.getString(R.string.collection_title));
            header.setVisibility(View.GONE);
        }

        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
        if (discount){
            getHeaderDiscountList();
        }
        else{
            Product pro = new Product();
            pro.setPostType(CART_DETAIL_FOOTER);
            universalPosts.add(pro);
            getDiscountList();
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
                    getDiscountList();
                }

            }
        });


        gridView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                try {



                    prefs.saveIntPerferences(discount_header_click, categoryList1.get(position).getId());
                    AppController.getInstance().onStop();

                    tagAdapter.setSelectedList(position);

                    selectedPosition = position;


                    if (prefs.isNetworkAvailable()) {
                        universalPosts.clear();
                        adapter = new UniversalAdapter(DiscountActivity.this, universalPosts);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        Product pro = new Product();
                        pro.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro);
                        prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT, SP_DEFAULT);

                        loading = true;

                        getDiscountList();

                    }

                } catch (Exception e) {
                }
                return false;
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

    private void getHeaderDiscountList(){

        mainCategoryObjArrayList = databaseAdapter.getCategorySuperList();

        if (mainCategoryObjArrayList.size() > 0){
            Category category1 = new Category();
            category1.setId(0);
            category1.setName(resources.getString(R.string.all));
            categoryList1.add(category1 );

            for (int i=0; i < mainCategoryObjArrayList.size(); i++){
                Category category = new Category();
                category.setId(mainCategoryObjArrayList.get(i).getId());
                category.setName(mainCategoryObjArrayList.get(i).getTitle());
                categoryList1.add(category);
            }

            tagAdapter = new TagAdapter(categoryList1) {
                @Override
                public View getView(FlowLayout parent, int position, Object o) {
                    Category obj =( Category) o;
                    final LayoutInflater inflater = LayoutInflater.from(DiscountActivity.this);
                    TextView tv = (TextView) inflater.inflate(R.layout.category_tab_view,
                            gridView, false);
                    Typeface myTypeface;
                    if (MDetect.INSTANCE.isUnicode()){
                        myTypeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
                    }
                    else{
                        myTypeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
                    }
                    tv.setTypeface(myTypeface);
                    tv.setText(obj.getName());
                    return tv;
                }
            };
            gridView.setAdapter(tagAdapter);


            tagAdapter.setSelectedList(selectedPosition);
            header.attachTo(recyclerView);

            header.setVisibility(View.VISIBLE);



            getDiscountList();
        }
        else{
            getDiscountList();
        }




    }




    private void getDiscountList(){

        if (discount){
            if (selectedPosition == 0)
                url = constantDiscounts+"?page="+prefs.getIntPreferences(SP_PAGE_NUM_DISCOUNT)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION);
            else
                url = constantDiscounts+"?page="+prefs.getIntPreferences(SP_PAGE_NUM_DISCOUNT) + "&super_category_id=" + prefs.getIntPreferences(discount_header_click)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION) ;
        }
        else{
            url = constantCollections +"?page="+prefs.getIntPreferences(SP_PAGE_NUM_DISCOUNT);
        }


        Log.e(TAG, "getDiscountList: ---------------------"  + url );

        final JsonArrayRequest arrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT, prefs.getIntPreferences(SP_PAGE_NUM_DISCOUNT) + SP_DEFAULT);

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            loading = false;

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Campaign>>() {
                                }.getType();
                                List<Campaign> categoryList = mGson.fromJson(response.toString(), listType);
                                List<Category> categoryList1 = new ArrayList<>();

                                for (int i = 0; i < categoryList.size(); i++) {
                                    Campaign campaign = new Campaign();
                                    campaign = categoryList.get(i);

                                    if (discount){
                                        campaign.setPostType(CAMPAIGN_MORE);
                                    }else{
                                        campaign.setPostType(COLLECTIONS_MORE);
                                    }


                                    universalPosts.add(campaign);


                                }



                                Reading pro = new Reading();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(pro);

                                adapter.notifyItemInserted(universalPosts.size());

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                        }else{
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            loading = true;

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);


                            adapter.notifyItemInserted(universalPosts.size());

                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );
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
}
