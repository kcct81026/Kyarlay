package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.SearchActivity;
import com.kyarlay.ayesunaing.custom_widget.FlowLayout;
import com.kyarlay.ayesunaing.custom_widget.TagAdapter;
import com.kyarlay.ayesunaing.custom_widget.TagFlowLayout;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Category;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.PatnerShopAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.myatminsoe.mdetect.MDetect;

public class FragmentSubCategory extends Fragment implements ConstantVariable, Constant {

    private static final String TAG = "FragmentSubCategory";

    ArrayList<UniversalPost> universalPosts = new ArrayList<>();

    RecyclerView recyclerView ;
    PatnerShopAdapter universalAdapter;


    MyPreference prefs;
    Resources resources;
    GridLayoutManager managerTwo;
    Boolean loading = true;


    Display display;
    //int brandID, catID = 1;
    ImageView imgSearch;

    ProgressBar progress_bar;
    int selectedPosition = 0;


    DatabaseAdapter databaseAdapter;
    //String fromClass = "";


    AppCompatActivity activity;
    LinearLayout linear;
    TagAdapter tagAdapter;
    TagFlowLayout gridView;
    RecyclerViewHeader header;
    LinearLayout linearHeader;
    RecyclerView.LayoutManager manager;
    List<MainCategoryObj> categoryList1 = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_fragment_sub_categories, container, false);

        activity  = (AppCompatActivity) getActivity();
        databaseAdapter = new DatabaseAdapter(activity);

        new MyFlurry(activity);
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        display = activity.getWindowManager().getDefaultDisplay();

        Log.e(TAG, "onCreate: "  );


        imgSearch = rootView.findViewById(R.id.imgSearch);
        linear = rootView.findViewById(R.id.linear);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerOne);

        progress_bar = rootView.findViewById(R.id.progressBar);


        header              = (RecyclerViewHeader) rootView.findViewById(R.id.header);
        gridView            = (TagFlowLayout) rootView.findViewById(R.id.category_detail_header);
        linearHeader            = (LinearLayout) rootView.findViewById(R.id.linearHeader);
        gridView.setMaxSelectCount(1);
        manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "main");
                    FlurryAgent.logEvent("Click Search", mix);
                } catch (Exception e) {
                    Log.e(TAG, "onClick: "  + e.getMessage() );
                }
                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fate_out);
            }
        });




        if (prefs.isNetworkAvailable()){
            mainSubCategories();

        }
        else{

            ToastHelper.showToast(activity, resources.getString(R.string.no_internet_error));

        }


        gridView.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                onTapClick(position);
                return false;
            }
        });



        return  rootView;

    }



    private void onTapClick(int pos){
        try {



            prefs.saveIntPerferences(discount_header_click, categoryList1.get(pos).getId());
            AppController.getInstance().onStop();

            tagAdapter.setSelectedList(pos);

            selectedPosition = pos;


            if (prefs.isNetworkAvailable()) {
                universalPosts.clear();
                universalAdapter = new PatnerShopAdapter(activity, universalPosts);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(universalAdapter);
                Product pro = new Product();
                pro.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro);
                prefs.saveIntPerferences(SP_PAGE_NUM_DISCOUNT, SP_DEFAULT);

                loading = true;

                categoryList(categoryList1.get(pos).getId());

            }

        } catch (Exception e) {
        }
    }



    private void mainSubCategories()
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantSuperMainCategories + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e(TAG, "onResponse: "  + constantSuperMainCategories + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) );



                        progress_bar.setVisibility(View.GONE);



                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }


                        if(response.length() > 0) {


                            //ArrayList<MainCategoryObj> categoryList = new ArrayList<>();
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<MainCategoryObj>>() {}.getType();
                                categoryList1 = mGson.fromJson(response.toString(), listType);



                                if (categoryList1.size() > 0){

                                    tagAdapter = new TagAdapter(categoryList1) {
                                        @Override
                                        public View getView(FlowLayout parent, int position, Object o) {
                                            MainCategoryObj obj = (MainCategoryObj) o;
                                            final LayoutInflater inflater = LayoutInflater.from(activity);
                                            TextView tv = (TextView) inflater.inflate(R.layout.category_tab_view,
                                                    gridView, false);
                                            Typeface myTypeface;
                                            if (MDetect.INSTANCE.isUnicode()) {
                                                myTypeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
                                            } else {
                                                myTypeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
                                            }
                                            tv.setTypeface(myTypeface);
                                            tv.setText(obj.getMm());
                                            return tv;
                                        }
                                    };
                                    gridView.setAdapter(tagAdapter);


                                    tagAdapter.setSelectedList(selectedPosition);
                                    header.attachTo(recyclerView);

                                    header.setVisibility(View.VISIBLE);



                                }

                                onTapClick(0);


                                loading = false;


                            }catch (Exception e){

                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }


                        }
                        else{
                            Product pro1 = new Product();
                            pro1.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(pro1);
                            universalAdapter.notifyDataSetChanged();
                        }





                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = true;


                // mainPageItem();

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

    private void categoryList(int id){



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantSubMainSuperCategory + id + "?language=" + prefs.getStringPreferences(SP_LANGUAGE)  ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {



                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }



                        if(response.length() > 0) {



                            ArrayList<MainCategoryObj> catList = new ArrayList<>();

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<MainCategoryObj>>() {}.getType();
                                catList = mGson.fromJson(response.toString(), listType);

                                if (catList.size() > 0){

                                    for (int i=0; i < catList.size(); i++){
                                        MainCategoryObj mainCategoryObj = catList.get(i);
                                        mainCategoryObj.setPostType(EXPANDABLE_LIST);
                                        mainCategoryObj.setShowTitle(0);
                                        universalPosts.add(mainCategoryObj);


                                    }


                                  /*  Product noitem = new Product();
                                    noitem.setPostType(CART_DETAIL_NO_ITEM);
                                    universalPosts.add(noitem);
*/
                                    universalAdapter.notifyItemInserted(universalPosts.size());


                                }




                            }catch (Exception e){

                                Log.e(TAG, "onResponse: mainCategoriesFromServer Exception : "  + e.getMessage() );
                            }


                        }




                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onResponse: mainCategoriesFromServer Exception : "  + error.getMessage() );


                Product pro1 = new Product();
                pro1.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro1);
                universalAdapter.notifyItemInserted(universalPosts.size());

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

