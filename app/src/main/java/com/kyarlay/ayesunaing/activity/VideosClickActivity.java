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

import com.android.volley.AuthFailureError;
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
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.object.Videos;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideosClickActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "VideosClickActivity";
    
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


    String youtubeID = "";
    String program_Id = "";
    String title = "";
    int showAds  = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_click);

        youtubeID = getIntent().getStringExtra("youtubeID");
        program_Id = getIntent().getStringExtra("program_Id");

        Log.e(TAG, "onCreate: "   );

        activity =  VideosClickActivity.this;
        display = activity.getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        new MyFlurry(VideosClickActivity.this);
        databaseAdapter = new DatabaseAdapter(activity);
        mediaAdapter = new MediaAdapter((AppCompatActivity) activity, mainCatDetails);

        back_layout = findViewById(R.id.back_layout);
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);


        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);



        if (prefs.isNetworkAvailable()){

            getAdsInfo();

        }else{
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_NO_ITEM);
            mainCatDetails.add(noitem);

        }



        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    public void getAdsInfo(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                String.format(constantVideoAds, program_Id + ""), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String ads_photo_url = response.getString("ads_photo_url");
                    if (ads_photo_url.length() != 0 && !ads_photo_url.equals("")){
                        Images images = new Images();
                        if(response.getString("ads_type").equals("link_ads"))
                            images.setId(1);
                        else if(response.getString("ads_type").equals("img_ads"))
                            images.setId(2);
                        else
                            images.setId(3);

                        images.setDimen(response.getInt("ads_photo_dimen"));
                        images.setUrl(response.getString("ads_photo_url"));
                        images.setName(response.getString("ads_url"));
                        images.setPostType(VIDEO_ADS_IMAGE);
                        mainCatDetails.add(images);



                    }else{
                        showAds = 1;
                    }

                    String photo_url = response.getString("photo_url");
                    if (photo_url.length() != 0 && !photo_url.equals("")){
                        Images images = new Images();
                        images.setDimen(response.getInt("dimen"));
                        images.setUrl(response.getString("photo_url"));
                        images.setPostType(DETAIL_IMAGE);
                        mainCatDetails.add(images);

                    }

                    title = response.getString("title");
                    Product pro = new Product();
                    pro.setTitle("\"" + title + "\"" + "  " + resources.getString(R.string.video_list)  );
                    pro.setPostType(DISCOUNT_TITLE);
                    mainCatDetails.add(pro);

                    //getVideos();
                    callAds();



                } catch (Exception e) {
                    Log.e(TAG, "onResponse: Exception  "   + e.getMessage() );

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ToastHelper.showToast(VideosClickActivity.this, resources.getString(R.string.search_error));

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
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
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

                getVideos(main);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                KyarlayAds main = new KyarlayAds();
                main.setId(-810);
                getVideos(main);

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    private void getVideos(KyarlayAds kyarlayAds)
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantVideoProgramDetail + program_Id +"&"+LANG+"="+prefs.getStringPreferences(LANGUAGE)+"&version="+
                + prefs.getIntPreferences(ConstantVariable.SP_CURRENT_VERSION),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }

                            loading = false;



                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Videos>>() {
                                }.getType();
                                List<Videos> categoryList = mGson.fromJson(response.toString(), listType);
                                for (int i = 0; i < categoryList.size(); i++) {

                                    if (i == 1 && showAds == 1){

                                        if (kyarlayAds.getId() != -810){

                                            kyarlayAds.setPostType(ADS);
                                            mainCatDetails.add(kyarlayAds);

                                        }


                                    }

                                    Videos videos = new Videos();
                                    videos = categoryList.get(i);
                                    videos.setPostType(VIDEO_ALL_CLICK);
                                    mainCatDetails.add(videos);



                                }

                                Product noitem = new Product();
                                noitem.setPostType(CART_DETAIL_NO_ITEM);
                                mainCatDetails.add(noitem);


                                mediaAdapter.notifyDataSetChanged();

                            }catch (Exception e){
                                Log.e(TAG, "onResponse:  "   + e.getMessage() );
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
                loading = false;

                ToastHelper.showToast(VideosClickActivity.this, resources.getString(R.string.search_error));


            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    @Override
    protected void onResume() {
        super.onResume();






    }
}
