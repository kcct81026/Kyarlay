package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.GiftObject;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiftExchangeActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "GiftExchangeActivity";

    // widgets
    LinearLayout back_layout, title_layout;
    RelativeLayout like_layot, cart_layout;
    CustomTextView title, txtPoint;
    ProgressBar progressBar1;
    RecyclerView recyclerView;

    // vars
    DecimalFormat formatter = new DecimalFormat("#,###,###");

    MyPreference prefs;
    Resources resources;
    Display display;
    Boolean loading = true;
    RecyclerView.LayoutManager manager;
    UniversalAdapter universalAdapter;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fcm_list_layout);

        Log.e(TAG, "onCreate: " );


       // new MyFlurry(GiftExchangeActivity.this);

        prefs  = new MyPreference(GiftExchangeActivity.this);
        Context context1 = LocaleHelper.setLocale(GiftExchangeActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context1.getResources();
        display = getWindowManager().getDefaultDisplay();


        back_layout = findViewById(R.id.back_layout);
        title_layout = findViewById(R.id.title_layout);
        like_layot = findViewById(R.id.like_layot);
        like_layot.setVisibility(View.GONE);
        cart_layout = findViewById(R.id.cart_layout);
        cart_layout.setVisibility(View.GONE);
        title = findViewById(R.id.title);
        txtPoint = findViewById(R.id.txtPoint);
        progressBar1 = findViewById(R.id.progressBar1);
        recyclerView = findViewById(R.id.recycler_view);
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);
        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefs.getIntPreferences(SP_CHANGE) == 1){
                    prefs.saveIntPerferences(SP_CHANGE, 0);
                    Intent intent = new Intent(GiftExchangeActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                    finish();
            }
        });
        title.setText(resources.getString(R.string.gift_exchange)+"");

        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        universalAdapter = new UniversalAdapter(GiftExchangeActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(universalAdapter);
        progressBar1.setVisibility(View.VISIBLE);
        txtPoint.setVisibility(View.VISIBLE);
        txtPoint.setText(resources.getString(R.string.your_ponint) + " " +  formatter.format(prefs.getIntPreferences(SP_USER_POINT))+"");
        txtPoint.setTypeface(txtPoint.getTypeface(), Typeface.BOLD);
        getGiftExchangeList();


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
                    getGiftExchangeList();
                }

            }
        });


    }

    public  void pointChange(){
        txtPoint.setText(resources.getString(R.string.your_ponint) + " " +  formatter.format(prefs.getIntPreferences(SP_USER_POINT))+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtPoint.setText(resources.getString(R.string.your_ponint) + " " +  formatter.format(prefs.getIntPreferences(SP_USER_POINT))+"");
    }

    private void getGiftExchangeList()
    {

        int page    = prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL);

        Log.e(TAG, "getGiftExchangeList: "  + String.format(constantGetGiftExchangeList,  prefs.getIntPreferences(SP_CURRENT_VERSION) + "") + page + "&" + LANG   + "=" + prefs.getStringPreferences(SP_LANGUAGE) );


        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(String.format(constantGetGiftExchangeList,  prefs.getIntPreferences(SP_CURRENT_VERSION) + "") + page + "&" + LANG   + "=" + prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e(TAG, "onResponse: "  + response.toString() );

                        //mainCatDetails.clear();
                        progressBar1.setVisibility(View.GONE);
                        if(response.length() > 0) {

                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }

                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<GiftObject>>() {
                                }.getType();
                                List<GiftObject> categoryList = mGson.fromJson(response.toString(), listType);
                                for (int i = 0; i < categoryList.size(); i++ ){
                                    GiftObject giftObject = categoryList.get(i);

                                    giftObject.setPostType(GIFT_ITEM);
                                    mainCatDetails.add(giftObject);

                                }




                                Product pro1 = new Product();
                                pro1.setPostType(CART_DETAIL_NO_ITEM);
                                mainCatDetails.add(pro1);

                                universalAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }


                        }else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }

                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);

                            universalAdapter.notifyItemInserted(mainCatDetails.size());

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar1.setVisibility(View.GONE);
                loading = true;
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                    Product pro1 = new Product();
                    pro1.setPostType(CART_DETAIL_NO_ITEM);
                    mainCatDetails.add(pro1);
                    universalAdapter.notifyDataSetChanged();
                }


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (prefs.getIntPreferences(SP_CHANGE) == 1){
            prefs.saveIntPerferences(SP_CHANGE, 0);
            Intent intent = new Intent(GiftExchangeActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
            finish();
    }
}
