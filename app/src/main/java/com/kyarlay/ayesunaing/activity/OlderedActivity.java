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
//import com.flurry.android.FlurryAgent;
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
import com.kyarlay.ayesunaing.object.Order;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OlderedActivity extends AppCompatActivity implements Constant, ConstantVariable{

    private static final String TAG = "OlderedActivity";

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



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reading_wishlist);


       // new MyFlurry(OlderedActivity.this);

        prefs   = new MyPreference(OlderedActivity.this);
        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);
        Context context = LocaleHelper.setLocale(OlderedActivity.this, prefs.getStringPreferences(LANGUAGE));
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

        //FlurryAgent.logEvent("View Old Orders Activity ");


        Log.e(TAG, "onCreate: " );

        title_layout.getLayoutParams().width    = (display.getWidth() * 17 ) / 20;
        back_layout.getLayoutParams().width    = (display.getWidth() * 3 ) / 20;
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        manager = new LinearLayoutManager(OlderedActivity.this);
        recyclerView.setLayoutManager(manager);

        universalAdapter = new UniversalAdapter(OlderedActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);
        orderedList();

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
                    prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + SP_DEFAULT);
                    orderedList();
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
            orderedList();
        }

    }



    public void orderedList(){

        Log.e(TAG, "orderedList: "  + String.format(constantOrderedlist, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL), type) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) );


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(String.format(constantOrderedlist, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL), type) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {



                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {
                            loading = false;

                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }

                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Order>>() {
                                }.getType();
                                List<Order> categoryList = mGson.fromJson(response.toString(), listType);

                                for(int i = 0; i < categoryList.size(); i++){
                                    Order order = new Order();
                                    order   = categoryList.get(i);
                                    order.setPostType(ORDER_HISTORY);


                                    mainCatDetails.add( order);

                                }

                                Product noitem = new Product();
                                noitem.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add( noitem);

                                universalAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: ************** error " + e.getMessage() );
                            }
                        }else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add( noitem);
                            universalAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }



                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                mainCatDetails.add(noitem);


                universalAdapter.notifyDataSetChanged();
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
