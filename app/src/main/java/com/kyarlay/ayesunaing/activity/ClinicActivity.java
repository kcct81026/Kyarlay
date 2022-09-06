package com.kyarlay.ayesunaing.activity;

import android.content.Context;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Clicnic;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClinicActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "ClinicActivity";
    
    // widgets
    LinearLayout back_layout;
    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    UniversalAdapter universalAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;
    String tag = "";
    ProgressBar progressBar;

    String name = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_click);


        activity =  ClinicActivity.this;
        display = activity.getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        databaseAdapter = new DatabaseAdapter(activity);
        universalAdapter = new UniversalAdapter((AppCompatActivity) activity, mainCatDetails);

        name = getIntent().getStringExtra("name");



        back_layout = findViewById(R.id.back_layout);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar        = (ProgressBar) findViewById(R.id.progress_bar);


        Log.e(TAG, "onCreate: " );



        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);


        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);
            Product pro = new Product();
            pro.setTitle(name);
            pro.setPostType(DISCOUNT_TITLE);
            mainCatDetails.add(pro);


            prefs.saveIntPerferences(ConstantVariable.SP_PAGE_CAT_NEWS, SP_DEFAULT);
            mainPageItem();

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
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;

                    mainPageItem();
                }

            }
        });


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
            mainPageItem();
        }

    }



    private void mainPageItem()
    {
        String url = "";

        if (prefs.getStringPreferences(ConstantVariable.SP_DIRECTORY_CLICK).equals("clinic_directory"))
            url = constantclinic + "?page=";
        else if (prefs.getStringPreferences(ConstantVariable.SP_DIRECTORY_CLICK).equals("schools"))
            url = constantschools + "?page=";



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url + prefs.getIntPreferences(SP_PAGE_CAT_NEWS)  +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            loading = false;

                            prefs.saveIntPerferences(SP_PAGE_CAT_NEWS, prefs.getIntPreferences(SP_PAGE_CAT_NEWS) + SP_DEFAULT);
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Clicnic>>() {
                                }.getType();
                                List<Clicnic> lists = mGson.fromJson(response.toString(), listType);




                                if (lists.size() > 0){
                                    for (int i=0; i < lists.size(); i++) {
                                        Clicnic clicnic = lists.get(i);
                                        clicnic.setPostType(LOGO_ITEM);
                                        mainCatDetails.add(clicnic);


                                    }

                                }

                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro2);

                                universalAdapter.notifyItemInserted(mainCatDetails.size());


                            }catch (Exception e){
                                Log.e(TAG, "onResponse:   mainPageItem  " + e.getMessage() );
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
                        //


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


                universalAdapter.notifyDataSetChanged();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }







}
