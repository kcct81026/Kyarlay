package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayesu on 9/18/17.
 */

public class SearchResultActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "SearchResultActivity";

    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    UniversalAdapter universalAdapter ;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    Resources resources;
    ProgressBar progressBar;
    DatabaseAdapter databaseAdapter;

    String search_result = "";
    Boolean loading = true;


    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;
    CircularTextView cart_text, wishlist_count;
    CustomTextView title;
    Display display;
    RelativeLayout layout_liked;

    Animation bounce;
    AnimatorSet animationSet;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_one);

        Log.e(TAG, "onCreate: "  );


        prefs   = new MyPreference(SearchResultActivity.this);
        Context context = LocaleHelper.setLocale(SearchResultActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        prefs.saveIntPerferences(SP_PAGE_CAT_NEWS, SP_DEFAULT);


        new MyFlurry(SearchResultActivity.this);


        recyclerView    = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar     = (ProgressBar) findViewById(R.id.progressBar);
        databaseAdapter = new DatabaseAdapter(SearchResultActivity.this);


        Intent intent = getIntent();
        search_result    = intent.getStringExtra("search");

        Log.e(TAG, "onCreate: ************  "  + search_result  );


        display = getWindowManager().getDefaultDisplay();

        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        title            = (CustomTextView) findViewById(R.id.title);
        layout_liked    = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count  = (CircularTextView) findViewById(R.id.wishlist_count);
        layout_liked.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "search_list");
                        FlurryAgent.logEvent("Click Shopping Cart", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(SearchResultActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent1 = new Intent(SearchResultActivity.this, ActivityLogin.class);
                    startActivity(intent1);
                }
            }
        });

        layout_liked.setVisibility(View.INVISIBLE);


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchResultActivity.this.finish();
            }
        });
        title.setText(search_result+"");

        universalAdapter = new UniversalAdapter(SearchResultActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);
        manager = new LinearLayoutManager(SearchResultActivity.this);
        recyclerView.setLayoutManager(manager);

        JsonArrayRequest jsonArrayRequest = mainCategory(search_result);
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

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
                    prefs.saveIntPerferences(SP_PAGE_CAT_NEWS, prefs.getIntPreferences(SP_PAGE_CAT_NEWS) + SP_DEFAULT);
                    JsonArrayRequest jsonArrayRequest = mainCategory(search_result);
                    AppController.getInstance().addToRequestQueue(jsonArrayRequest);
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
            JsonArrayRequest jsonArrayRequest = mainCategory(search_result);
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cart_text.setStrokeWidth(1);
        cart_text.setStrokeColor("#000000");
        cart_text.setSolidColor("#ffffff");
        int count = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);

        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }

        wishlist_count.setStrokeWidth(1);
        wishlist_count.setStrokeColor("#000000");
        wishlist_count.setSolidColor("#ffffff");

        int count_wish  = prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT);
        if(count_wish == 0){
            wishlist_count.setVisibility(View.GONE);
        }else{
            wishlist_count.setVisibility(View.VISIBLE);
            wishlist_count.setText(count_wish + "");
        }

    }



    public void bounceCount (){
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce_animation);
        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext()
                , R.animator.flip_animation);
        int count = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);
        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }

        animationSet.setTarget(cart_text);
        cart_text.startAnimation(bounce);
        animationSet.start();
    }



    private JsonArrayRequest mainCategory(final String query)
    {
        Log.e(TAG, "mainCategory:  query "  + query );
        String enquery="";
        try {
            enquery = URLEncoder.encode(query, "utf-8");
        }catch (Exception e){}

        Log.e(TAG, "mainCategory:  enquery  "  + enquery );
        Log.e(TAG, "mainCategory: ************** "   + constantSearch + prefs.getIntPreferences(SP_CURRENT_VERSION) +
                "&" + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE) +"&page"  + "=" + prefs.getIntPreferences(SP_PAGE_CAT_NEWS) + "&query="+enquery );

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantSearch + prefs.getIntPreferences(SP_CURRENT_VERSION) +
                "&" + LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE) +"&page"  + "=" + prefs.getIntPreferences(SP_PAGE_CAT_NEWS) + "&query="+enquery,
            new Response.Listener<JSONArray>() {

                    @Override
                public void onResponse(JSONArray response) {


                        
                    progressBar.setVisibility(View.GONE);
                    if(response.length() > 0) {
                        loading = false;

                        if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            mainCatDetails.remove(mainCatDetails.size() - 1);
                        }

                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<Product>>() {
                            }.getType();
                            List<Product> categoryList = mGson.fromJson(response.toString(), listType);
                            for (int i = 0; i < categoryList.size(); i++) {
                                Product pro = new Product();
                                pro = categoryList.get(i);
                                pro.setPostType(CATEGORY_DETAIL);
                                mainCatDetails.add(pro);

                            }

                            if(mainCatDetails.size() > 0 && prefs.getIntPreferences(SP_PAGE_CAT_NEWS) == SP_DEFAULT){
                                Product pro = new Product();
                                pro = (Product) mainCatDetails.get(0);
                                databaseAdapter.insertSearch(search_result,pro.getPreviewImage());
                            }

                            Product pro = new Product();
                             pro.setPostType(CART_DETAIL_FOOTER);
                            mainCatDetails.add(pro);


                            universalAdapter.notifyDataSetChanged();

                        }catch (Exception e){
                            Log.e(TAG, "onResponse:  "  + e.getMessage() );
                        }


                    }else{
                        loading = true;
                        if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER))
                            mainCatDetails.remove(mainCatDetails.size() - 1);
                            Product pro = new Product();
                            pro.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(pro);

                            universalAdapter.notifyDataSetChanged();


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


                universalAdapter.notifyDataSetChanged();

            }
        });
        return jsonObjReq;
    }


}
