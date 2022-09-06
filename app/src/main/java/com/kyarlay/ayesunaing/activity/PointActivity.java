package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.PointHistory;
import com.kyarlay.ayesunaing.object.PointInfo;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointActivity extends AppCompatActivity implements Constant, ConstantVariable{

    private static final String TAG = "PointActivity";

    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    UniversalAdapter universalAdapter;
    RecyclerView recyclerView;
    Boolean loading = true;
    int postId      = 0;
    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager manager;
    LinearLayout back_layout, title_layout;
    ProgressBar progressBar;
    CustomTextView title;
    Display display;

    String type;
    String[] typesArray = {"order", "redeem", "signup", "posting", "invite"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_wishlist);


        new MyFlurry(PointActivity.this);

        prefs   = new MyPreference(PointActivity.this);
        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);
        Context context = LocaleHelper.setLocale(PointActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        display = getWindowManager().getDefaultDisplay();

        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar         = (ProgressBar) findViewById(R.id.progressBar1);
        back_layout         = (LinearLayout) findViewById(R.id.back_layout);
        title_layout        = (LinearLayout) findViewById(R.id.title_layout);

        title               = (CustomTextView) findViewById(R.id.title);


        Intent intent = getIntent();
        postId = intent.getIntExtra("post_id", 0);
        type = intent.getStringExtra("type");
        String titleText = intent.getStringExtra("title");
        title.setText(titleText);

        FlurryAgent.logEvent("View Old Orders Activity ");


        Log.e(TAG, "onCreate: " );




        title_layout.getLayoutParams().width    = (display.getWidth() * 17 ) / 20;

        back_layout.getLayoutParams().width    = (display.getWidth() * 3 ) / 20;
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        manager = new LinearLayoutManager(PointActivity.this);
        recyclerView.setLayoutManager(manager);

        universalAdapter = new UniversalAdapter(PointActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();



        progressBar.setVisibility(View.VISIBLE);
        UniversalPost universalPost = new UniversalPost();
        universalPost.setPostType(POINT_INFO_TEXT);
        mainCatDetails.add(universalPost);

        getPointInfo();

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
                    getPointHistory();
                }

            }
        });


    }







    private void getPointInfo()
    {

        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(constantPointInfo + prefs.getIntPreferences(SP_MEMBER_ID) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<PointInfo>>() {
                            }.getType();
                            List<PointInfo> pointInfoList = mGson.fromJson(response.toString(), listType);
                            ArrayList<PointInfo> earnList = new ArrayList<>();
                            ArrayList<PointInfo> useList = new ArrayList<>();


                            for (int i = 0; i < pointInfoList.size(); i++) {
                                PointInfo pointInfo = pointInfoList.get(i);


                                for (int k=0; k < typesArray.length ; k++){
                                    if (typesArray[k].equals(pointInfo.getName())){

                                        if (pointInfo.getType().equals("use")){
                                            pointInfo.setPostType(USER_LOGIN);
                                            useList.add(pointInfo);
                                        }


                                        else if (pointInfo.getType().equals("earn")){
                                            if (pointInfo.getStatus().equals("true")) {
                                                pointInfo.setPostType(USER_LOGIN);
                                                earnList.add(pointInfo);
                                            }
                                        }

                                    }
                                }
                            }

                            PointInfo pointInfoUse = new PointInfo();
                            pointInfoUse.setType("use");
                            pointInfoUse.setPostType(POINT_TITLE);
                            mainCatDetails.add(pointInfoUse);

                            for (int p = 0; p < useList.size() ; p++){
                                mainCatDetails.add(useList.get(p));
                            }




                            if (earnList.size() > 0){
                                PointInfo pointInfoEarn = new PointInfo();
                                pointInfoEarn.setType("earn");
                                pointInfoEarn.setPostType(POINT_TITLE);
                                mainCatDetails.add(pointInfoEarn);

                                for (int j = 0; j < earnList.size() ; j++){
                                    mainCatDetails.add(earnList.get(j));
                                }

                            }

                            Product pro = new Product();
                            pro.setPostType(DISCOUNT_TITLE);
                            pro.setTitle(resources.getString(R.string.your_ponint));
                            mainCatDetails.add(pro);




                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_FOOTER);
                            mainCatDetails.add(noitem);

                            universalAdapter.notifyDataSetChanged();

                            getPointHistory();

                        }catch (Exception e){
                            Log.e(TAG, "onResponse: "  + e.getMessage() );
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

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
            universalAdapter.notifyItemInserted(mainCatDetails.size());
            getPointHistory();
        }

    }

    private void getPointHistory()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantPointHistory+"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) + "&page=" + prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + "&" + "version=" + prefs.getIntPreferences(SP_CURRENT_VERSION) ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {




                        if(response.length() > 0) {

                            loading = false;
                            progressBar.setVisibility(View.GONE);
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + SP_DEFAULT);
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<PointHistory>>() {
                                }.getType();
                                List<PointHistory> lists = mGson.fromJson(response.toString(), listType);
                                for (int i = 0 ; i < lists.size(); i ++){
                                    PointHistory obj = lists.get(i);
                                    obj.setPostType(POINT_HISTORY);
                                    mainCatDetails.add(obj);

                                }
                                universalAdapter.notifyItemInserted(mainCatDetails.size());

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }


                        }
                        else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);


                            universalAdapter.notifyDataSetChanged();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }




}
