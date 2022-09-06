package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.android.volley.AuthFailureError;
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
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.NameObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NameWishListActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "NameWishListActivity";

    private LinearLayout back_layout, title_layout;
    private RelativeLayout like_layot;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView img;

    private MediaAdapter adapter;
    private ArrayList<UniversalPost> universalPosts;
    private AppCompatActivity activity;
    private MyPreference prefs;
    private Resources resources;
    private int length = 0, first_index= 0, second_index=0, third_index = 0, fourth_index = 0 ;
    private String gender="";
    private Boolean loading=true;
    private Display display;


    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_all_names);

        Log.e(TAG, "onCreate: " );


        new MyFlurry(NameWishListActivity.this);
        databaseAdapter = new DatabaseAdapter(NameWishListActivity.this);

        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("source", "view wishlist page");
            FlurryAgent.logEvent("View WishList Name Page", mix);
        } catch (Exception e) {
        }



        activity = NameWishListActivity.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display = getWindowManager().getDefaultDisplay();

        prefs.saveIntPerferences(SP_PAGE_READ_COMMENT,  SP_DEFAULT);

        back_layout = findViewById(R.id.back_layout);
        recyclerView    = findViewById(R.id.recycler_view);
        progressBar    = findViewById(R.id.progressBar1);
        like_layot = findViewById(R.id.like_layot);
        title_layout = findViewById(R.id.title_layout);
        img = findViewById(R.id.img);


        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        img.getLayoutParams().width    = ( display.getWidth() * 3) / 20;

        like_layot.setVisibility(View.GONE);




        universalPosts = new ArrayList<>();
        adapter = new MediaAdapter(activity, universalPosts);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);



        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);


           callAdsBanner();


        }

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!prefs.getBooleanPreference(SP_ADS_LOADED)){
                    CountDownTimer countDownTimer = new CountDownTimer(5*60 *1000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {


                            prefs.saveBooleanPreference(SP_ADS_LOADED, true);
                            prefs.saveIntPerferences(ADS_COUNT_DOWN_TIME, (int)(millisUntilFinished / 1000) );



                        }

                        @Override
                        public void onFinish() {

                            prefs.saveBooleanPreference(SP_ADS_LOADED, false);

                        }
                    };

                    countDownTimer.start();
                    Intent intent = new Intent(NameWishListActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }



            }
        });




        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean scrollUp = false;

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
                    prefs.saveIntPerferences(SP_PAGE_READ_COMMENT, prefs.getIntPreferences(SP_PAGE_READ_COMMENT) + SP_DEFAULT);
                    getLikeProduct();
                }

            }
        });






    }



    private void callAdsBanner(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "banner" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);

                Log.e(TAG, "onResponse: ************ "  + response.toString() );
                KyarlayAds main = new KyarlayAds();

                if (response.length() > 0){
                    Gson gson = new Gson();
                    main = gson.fromJson(response.toString(), KyarlayAds.class);
                    main.setPostType(ADS);
                    universalPosts.add(main);
                }


                Reading pro = new Reading();
                pro.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro);
                adapter.notifyDataSetChanged();
                getLikeProduct();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                progressBar.setVisibility(View.GONE);
                getLikeProduct();

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    private void getLikeProduct()
    {



        int page    = prefs.getIntPreferences(SP_PAGE_READ_COMMENT);


        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(constantLikedNameList + page +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            loading = false;

                            progressBar.setVisibility(View.GONE);

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<NameObject>>() {
                                }.getType();
                                List<NameObject> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < categoryList.size(); i++) {
                                    NameObject nameObject = new NameObject();
                                    nameObject = categoryList.get(i);
                                    nameObject.setSaveName(true);
                                    nameObject.setPostType(NAME_OBJECT);



                                    databaseAdapter.insertNamesLike(nameObject.getId(), "like");
                                    universalPosts.add(nameObject);

                                }

                                Reading pro = new Reading();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(pro);
                                adapter.notifyDataSetChanged();




                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                        }else{
                            progressBar.setVisibility(View.GONE);
                            loading = true;
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
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
                progressBar.setVisibility(View.GONE);

                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                universalPosts.add(noitem);

                adapter.notifyItemInserted(universalPosts.size());



            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));



                return headers;
            }
        };


        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

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
            getLikeProduct();
        }

    }

    @Override
    public void onBackPressed() {

        if (!prefs.getBooleanPreference(SP_ADS_LOADED)){
            CountDownTimer countDownTimer = new CountDownTimer(5*60 *1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {


                    prefs.saveBooleanPreference(SP_ADS_LOADED, true);
                    prefs.saveIntPerferences(ADS_COUNT_DOWN_TIME, (int)(millisUntilFinished / 1000) );



                }

                @Override
                public void onFinish() {

                    prefs.saveBooleanPreference(SP_ADS_LOADED, false);

                }
            };

            countDownTimer.start();
            Intent intent = new Intent(NameWishListActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }
}
