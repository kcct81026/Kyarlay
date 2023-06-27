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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.SafeMainObj;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SafeQuesMainActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "SafeQuesMainActivity";

    private LinearLayout back_layout, title_layout;
    private RelativeLayout like_layot;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView img;

    private MediaAdapter adapter;
    private ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    private AppCompatActivity activity;
    private MyPreference prefs;
    private Resources resources;
    private Display display;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show_all_names);


        Log.e(TAG, "onCreate: " );

       // new MyFlurry(SafeQuesMainActivity.this);


        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("source", "view isItSafe page");
            //FlurryAgent.logEvent("View isItSafe Activity Page", mix);
        } catch (Exception e) {
        }



        activity = SafeQuesMainActivity.this;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display = getWindowManager().getDefaultDisplay();

        back_layout = findViewById(R.id.back_layout);
        recyclerView    = findViewById(R.id.recycler_view);
        progressBar    = findViewById(R.id.progressBar1);
        like_layot = findViewById(R.id.like_layot);
        title_layout = findViewById(R.id.title_layout);
        img = findViewById(R.id.img);



        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        img.getLayoutParams().width    = ( display.getWidth() * 3) / 20;

        img.setVisibility(View.GONE);
        like_layot.setVisibility(View.GONE);

        adapter = new MediaAdapter(activity, universalPosts);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);


            getSafetyCategories();
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
                    Intent intent = new Intent(SafeQuesMainActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });

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
            Intent intent = new Intent(SafeQuesMainActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }

    private void getSafetyCategories()
    {

        Log.e(TAG, "getSafetyCategories: "  + constantSafeCategory  +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) );

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantSafeCategory  +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<SafeMainObj>>() {
                                }.getType();
                                List<SafeMainObj> categoryList = mGson.fromJson(response.toString(), listType);

                                if (categoryList.size() > 0){

                                    MainObject mainObject = new MainObject();
                                    mainObject.setTitle(resources.getString(R.string.safe_title));
                                    mainObject.setPostType(MAIN_SAFE);

                                    List<MainItem> mainItemList = new ArrayList<>();

                                    for (int i=0; i < categoryList.size(); i++){
                                       MainItem mainItem = new MainItem();
                                       mainItem.setId(categoryList.get(i).getId());
                                       mainItem.setTitle(categoryList.get(i).getTitle());
                                       mainItem.setUrl(categoryList.get(i).getPhoto_url());
                                       mainItem.setDimen(categoryList.get(i).getPhoto_dimen());
                                       mainItemList.add(mainItem);
                                    }

                                    mainObject.setItems(mainItemList);
                                    universalPosts.add(mainObject);
                                }




                                adapter.notifyDataSetChanged();



                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }

}
