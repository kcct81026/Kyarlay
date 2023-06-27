package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchPhotoActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "NewsClickActivity";
    
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
    String id = "0";
    int fromdeep = 0;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_click);

        Log.e(TAG, "onCreate: " );

        id = getIntent().getStringExtra("id");
        fromdeep = getIntent().getIntExtra("fromdeep", 0);


        activity =  SearchPhotoActivity.this;
        display = activity.getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

       // new MyFlurry(SearchPhotoActivity.this);
        databaseAdapter = new DatabaseAdapter(activity);
        universalAdapter = new UniversalAdapter((AppCompatActivity) activity, mainCatDetails);

        back_layout = findViewById(R.id.back_layout);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);


        prefs.saveIntPerferences(ConstantVariable.SP_PAGE_CAT_NEWS, SP_DEFAULT);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);


        prefs.saveBooleanPreference(COMPETITION_DELETE, false);
        if (prefs.isNetworkAvailable()){
            Reading pro2 = new Reading();
            pro2.setPostType(CART_DETAIL_FOOTER);
            mainCatDetails.add(pro2);

            mainCategory();
        }else{
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_NO_ITEM);
            mainCatDetails.add(noitem);

        }






        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromdeep == 1){
                    Intent backIntent = new Intent(SearchPhotoActivity.this, MainActivity.class);
                    startActivity(backIntent);
                    finish();
                }
                else
                    finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fromdeep == 1){
            Intent backIntent = new Intent(SearchPhotoActivity.this, MainActivity.class);
            startActivity(backIntent);
            finish();
        }
        else
            finish();
    }

    private void mainCategory()
    {

        Log.e(TAG, "mainCategory: "  + constantCompetitionSearch + id );

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, constantCompetitionSearch + id  , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        if (response.length() > 0){
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            try {
                                Gson gson = new Gson();
                                Reading reading = gson.fromJson(response.toString(), Reading.class);


                                if (reading.getPostType().trim().length() > 0 && reading.getPostType() != null) {
                                    mainCatDetails.add(reading);


                                }


                                Product noitem = new Product();
                                noitem.setPostType(CART_DETAIL_NO_ITEM);
                                mainCatDetails.add(noitem);


                                universalAdapter.notifyDataSetChanged();

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }

                        }else{
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);


                            universalAdapter.notifyDataSetChanged();
                        }
                                           }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }
                Product noitem = new Product();
                noitem.setPostType(CART_DETAIL_NO_ITEM);
                mainCatDetails.add(noitem);


                universalAdapter.notifyDataSetChanged();
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"Order123");
    }



}
