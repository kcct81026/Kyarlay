package com.kyarlay.ayesunaing.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
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
import java.util.List;

/**
 * Created by ayesu on 8/10/17.
 */

public class RecommandentBrandDetailActivity extends AppCompatActivity implements Constant, ConstantVariable {
    private static final String TAG = "RecommandentBrand";
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_detail);

        Log.e(TAG, "onCreate:  "  );


        prefs = new MyPreference(RecommandentBrandDetailActivity.this);
        databaseAdapter = new DatabaseAdapter(RecommandentBrandDetailActivity.this);
        progressBar1    = (ProgressBar) findViewById(R.id.progressBar1);

        manager = new LinearLayoutManager(getApplicationContext());

       // new MyFlurry(RecommandentBrandDetailActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        Intent intent = getIntent();
        brand = intent.getParcelableExtra("brand");
        prefs.saveIntPerferences(SP_PAGE_NUM_BRAND_DETAIL, SP_DEFAULT);
        productType = String.valueOf(prefs.getIntPreferences(SP_BRAND_ID+"-"+brand.getId()));

        if(prefs.isNetworkAvailable()){
            JsonArrayRequest request = brandedList();
            AppController.getInstance().addToRequestQueue(request);
        }else{

            universalPosts.clear();
            universalPosts = databaseAdapter.getBrandDetail(productType);
            recyclerView.setLayoutManager(manager);
            adapter = new UniversalAdapter(RecommandentBrandDetailActivity.this, universalPosts);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }


        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(RecommandentBrandDetailActivity.this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        CustomTextView mTitleTextView = (CustomTextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(brand.getTitle());
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.arrowleft);

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
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private JsonArrayRequest brandedList()
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantBrand_Detail+0+"&brand_id="+brand.getId()+"&page="+prefs.getIntPreferences(SP_PAGE_NUM_BRAND_DETAIL)+"&"+LANG+"="+prefs.getStringPreferences(LANGUAGE),
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
                                adapter = new UniversalAdapter(RecommandentBrandDetailActivity.this, universalPosts);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adapter);
                                recyclerView.addOnScrollListener(new RecommandentBrandDetailActivity.onScrollListener(recyclerView));

                                adapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse:  "  + e.getMessage() );
                            }
                        }else{
                            progressBar1.setVisibility(View.GONE);
                            loading = true;
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);

                            recyclerView.setLayoutManager(manager);
                            adapter = new UniversalAdapter(RecommandentBrandDetailActivity.this, universalPosts);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                            recyclerView.addOnScrollListener(new RecommandentBrandDetailActivity.onScrollListener(recyclerView));


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
