package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
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

public class RecommandBrandActivity extends AppCompatActivity implements Constant, ConstantVariable {
    private static final String TAG = "RecommandBrandActivity";

    Reading reading;
    RecyclerView recyclerView;
    private ArrayList<UniversalPost> universalPosts=new ArrayList<UniversalPost>();
    UniversalAdapter adapter;
    MyPreference prefs;
    Display display;
    ProgressBar progressBar;
    Resources resources;

    DatabaseAdapter databaseAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product);

        Log.e(TAG, "onCreate: "  );

        prefs = new MyPreference(RecommandBrandActivity.this) ;
        display = getWindowManager().getDefaultDisplay();
        recyclerView    = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        Context context = LocaleHelper.setLocale(RecommandBrandActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        databaseAdapter = new DatabaseAdapter(RecommandBrandActivity.this);

       // new MyFlurry(RecommandBrandActivity.this);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(RecommandBrandActivity.this, universalPosts);
        recyclerView.setAdapter(adapter);



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


    /*    recyclerView.addOnItemTouchListener(new MainGridItemClickListener(RecommandBrandActivity.this, recyclerView, universalPosts, new ClickeListener() {
            @Override
            public void onClick(View view, int position) {
                Brand brand = (Brand) universalPosts.get(position);
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source","brand");
                    mix.put("source_id", brand.getTag());
                    //FlurryAgent.logEvent("View Brand Detai", mix);

                } catch (Exception e) {
                }
                Intent intent = new Intent(RecommandBrandActivity.this, RecommandentBrandDetailActivity.class);
                Bundle b = new Bundle();
                b.putParcelable("brand", universalPosts.get(position));
                intent.putExtras(b);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));*/

        JsonArrayRequest request_Richtext = mainCategory();
        AppController.getInstance().addToRequestQueue(request_Richtext);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(RecommandBrandActivity.this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        CustomTextView mTitleTextView = (CustomTextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(title);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.arrowleft);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private JsonArrayRequest mainCategory()
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantBrandPromotionDetail,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            progressBar.setVisibility(View.GONE);
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Brand>>() {
                                }.getType();
                                List<Brand> categoryList = mGson.fromJson(response.toString(), listType);
                                for (int i = 0; i < categoryList.size(); i++) {
                                    Brand bb = new Brand();
                                    bb = categoryList.get(i);
                                    bb.setPostType(BRAND);
                                    bb.setTag(prefs.getStringPreferences(SP_BRAND_TAG));
                                    universalPosts.add(bb);
                                }
                                databaseAdapter.insertBrand(universalPosts );

                                Product noitem = new Product();
                                noitem.setPostType(CART_DETAIL_NO_ITEM);
                                universalPosts.add(noitem);

                                adapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse:  "  + e.getMessage() );
                            }


                        }else{
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);

                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        return jsonObjReq;
    }

}