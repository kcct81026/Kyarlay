package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewsClickActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "NewsClickActivity";
    
    // widgets
    LinearLayout back_layout;
    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    MediaAdapter mediaAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;
    String tag = "";
    String name = "";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_click);

        Log.e(TAG, "onCreate: " );

        tag = getIntent().getStringExtra("tag");
        name = getIntent().getStringExtra("name");

        activity =  NewsClickActivity.this;
        display = activity.getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        new MyFlurry(NewsClickActivity.this);
        databaseAdapter = new DatabaseAdapter(activity);
        mediaAdapter = new MediaAdapter((AppCompatActivity) activity, mainCatDetails);

        back_layout = findViewById(R.id.back_layout);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);


        prefs.saveIntPerferences(ConstantVariable.SP_PAGE_CAT_NEWS, SP_DEFAULT);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);



        if (prefs.isNetworkAvailable()){
            Reading pro2 = new Reading();
            pro2.setPostType(CART_DETAIL_FOOTER);
            mainCatDetails.add(pro2);

            //mainCategory();
            callAds();
        }else{
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_NO_ITEM);
            mainCatDetails.add(noitem);

        }

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
                if (lastVisibleItem > 8 ) {
                    scrollUp = true;
                } else {
                    scrollUp = false;
                }




                int totalItemCount = linearLayoutManager.getItemCount();

                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {

                    loading = true;
                    callAds();
                }

            }
        });




        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void callAds(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "rectangular" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }
                Log.e(TAG, "onResponse: ************ "  + response.toString() );
                KyarlayAds main = new KyarlayAds();

                if (response.length() > 0){
                    Gson gson = new Gson();
                    main = gson.fromJson(response.toString(), KyarlayAds.class);
                }
                else{
                    main.setId(-810);
                }

                mainCategory(main);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                KyarlayAds main = new KyarlayAds();
                main.setId(-810);
                mainCategory(main);

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    public void clickRefresh(){

        int position = -1;
        for (int i = 0 ; i < mainCatDetails.size(); i ++){
            if (mainCatDetails.get(i).getPostType().equals(REFRESH_FOOTER)){
                position = i;
            }
        }

        if (position != -1){
            mainCatDetails.remove(position);
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_FOOTER);
            mainCatDetails.add(noitem);
            mediaAdapter.notifyItemInserted(mainCatDetails.size());
            callAds();
        }

    }


    private void mainCategory(KyarlayAds kyarlayAds)
    {
        String url = "";
        if (tag.equals("momolay"))
            url = constantMomolay + prefs.getIntPreferences(ConstantVariable.SP_PAGE_CAT_NEWS) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE);
        else
            url = String.format(constantPostClickNews, tag ) + prefs.getIntPreferences(ConstantVariable.SP_PAGE_CAT_NEWS)+ "&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE);


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(response.length() > 0) {

                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            loading = false;

                            if (prefs.getIntPreferences(SP_PAGE_CAT_NEWS) == SP_DEFAULT){
                                Product pro = new Product();
                                pro.setTitle(name  + "  " + resources.getString(R.string.video_news) );
                                pro.setPostType(DISCOUNT_TITLE);
                                mainCatDetails.add(pro);
                            }

                            prefs.saveIntPerferences(SP_PAGE_CAT_NEWS, prefs.getIntPreferences(SP_PAGE_CAT_NEWS) + SP_DEFAULT);


                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Reading>>() {
                                }.getType();
                                List<Reading> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < categoryList.size(); i++) {

                                    if (i == 1){

                                        if (kyarlayAds.getId() != -810){

                                            kyarlayAds.setPostType(ADS);
                                            mainCatDetails.add(kyarlayAds);

                                        }

                                    }

                                    Reading reading = new Reading();
                                    reading = categoryList.get(i);
                                    reading.setPostType(VIDEO_NEWS);

                                    mainCatDetails.add(reading);

                                }


                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro2);
                                mediaAdapter.notifyItemInserted(mainCatDetails.size());


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                        }else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);


                            mediaAdapter.notifyDataSetChanged();

                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }



                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                mainCatDetails.add(noitem);


                mediaAdapter.notifyDataSetChanged();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



}
