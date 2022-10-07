package com.kyarlay.ayesunaing.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SearchPatnerShopActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "SearchPatnerShop";

    private MyPreference prefs;
    private AppCompatActivity activity;

    private Resources resources;

    private RecyclerView recyclerView;
    private EditText txtSearch;
    private ImageView imgBack;

    private UniversalAdapter adapter;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    boolean loading = true;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_brand_patner_search);

        activity = this;
        prefs = new MyPreference(activity);



        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();


        txtSearch = findViewById(R.id.txtSearch);
        imgBack = findViewById(R.id.imgBack);
        recyclerView = findViewById(R.id.recyclerView);





        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        adapter = new UniversalAdapter(activity, universalPosts);
        recyclerView.setAdapter(adapter);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {


                    InputMethodManager imm = (InputMethodManager)getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);

                    universalPosts.clear();

                    UniversalPost noitem = new UniversalPost();
                    noitem.setPostType(CART_DETAIL_FOOTER);
                    universalPosts.add(noitem);

                    adapter.notifyDataSetChanged();
                    prefs.saveIntPerferences(SP_SEARCH_PAGENATION , 1);

                    //loading = true;
                    JsonArrayRequest jsonArrayRequest = getBrandPatner();
                    AppController.getInstance().addToRequestQueue(jsonArrayRequest);


                    return true;

                }
                return false;
            }
        });







        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {



            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;
                   /* JsonArrayRequest request = getProductList();
                    AppController.getInstance().addToRequestQueue(request);*/
                    JsonArrayRequest jsonArrayRequest = getBrandPatner();
                    AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                }

            }
        });





    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private JsonArrayRequest getBrandPatner(){
        String enquery=txtSearch.getText().toString();
        try {
            enquery = URLEncoder.encode( enquery , "utf-8");
        }catch (Exception e){}

        String url = searchPartnerBrand + enquery +"&page="+prefs.getIntPreferences(SP_SEARCH_PAGENATION);

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {
                            Log.e(TAG, "onResponse: ------------ "  + url );
                            loading = false;
                            prefs.saveIntPerferences(SP_SEARCH_PAGENATION, prefs.getIntPreferences(SP_SEARCH_PAGENATION) + SP_DEFAULT);


                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }


                            try {
                                GsonBuilder builder = new GsonBuilder();

                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Brand>>() {
                                }.getType();
                                List<Brand> brandList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < brandList.size(); i++) {
                                    Brand bb = new Brand();
                                    bb = brandList.get(i);
                                    bb.setPostType(BRAND);
                                    bb.setTag(prefs.getStringPreferences(SP_BRAND_TAG));
                                    universalPosts.add(bb);
                                }
                                //databaseAdapter.insertProduct(arrayList, OTHER);


                                adapter.notifyItemInserted(universalPosts.size());


                            }catch (Exception e){
                            }
                        }
                        else{

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            loading = true;

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);

                            adapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = false;
                Product noitem = new Product();
                noitem.setPostType(CART_DETAIL_NO_ITEM);
                universalPosts.add(noitem);

                adapter.notifyDataSetChanged();
            }
        });
        return jsonObjReq;

    }






}
