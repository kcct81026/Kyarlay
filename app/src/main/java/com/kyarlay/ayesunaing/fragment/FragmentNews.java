package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.CategoryGrid;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.ClickeListener;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MainGridItemClickListener;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentNews extends Fragment implements ConstantVariable, Constant {
    private static final String TAG = "FragmentNews";

    Boolean loading=true;
    MyPreference prefs;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    MediaAdapter mediaAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_fragment_latest, container, false);

        activity = (AppCompatActivity) getActivity();

        display = getActivity().getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(getActivity());
        Context context = LocaleHelper.setLocale(getActivity(), prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        databaseAdapter = new DatabaseAdapter(getActivity());
        mediaAdapter = new MediaAdapter((AppCompatActivity) getActivity(), mainCatDetails);
        recyclerView        = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar        = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        LinearLayout linearBottom = (LinearLayout) rootView.findViewById(R.id.linearBottom);
        linearBottom.setPadding(0,0,0,0);

        prefs.saveIntPerferences(ConstantVariable.SP_PAGE_CATEGORY, SP_DEFAULT);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);

        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);
            mainPageItem();
        }else{
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_NO_ITEM);
            mainCatDetails.add(noitem);

        }

        Log.e(TAG, "onCreateView: "  );

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
                if (lastVisibleItem > 8 ) {
                    scrollUp = true;
                } else {
                    scrollUp = false;
                }




                int totalItemCount = linearLayoutManager.getItemCount();

                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {

                    loading = true;
                    callAds();

                }

            }
        });

        recyclerView.addOnItemTouchListener(new MainGridItemClickListener(activity,
                recyclerView, mainCatDetails, new ClickeListener() {
            @Override
            public void onClick(View view, int position) {
                if (mainCatDetails.get(position).getPostType().equals(REFRESH_FOOTER)){
                    if (prefs.isNetworkAvailable()){

                        mainCatDetails.remove(position);
                        Product noitem = new Product();
                        noitem.setPostType(CART_DETAIL_FOOTER);
                        mainCatDetails.add(noitem);
                        mediaAdapter.notifyItemInserted(mainCatDetails.size());
                        callAds();
                    }else{

                        ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

                    }
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }

        }));




        return rootView;
    }

    private void mainPageItem()
    {


        Log.e(TAG, "mainPageItem: " + constantPostCategory +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) );

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantPostCategory +"?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(response.length() > 0) {
                            progressBar.setVisibility(View.GONE);
                            mainCatDetails.clear();


                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<CategoryMain>>() {
                                }.getType();
                                List<CategoryMain> lists = mGson.fromJson(response.toString(), listType);

                                if (lists.size() > 0){

                                    CategoryGrid categoryGrid = new CategoryGrid();
                                    categoryGrid.setPostType(CATEGORY_POST);
                                    categoryGrid.setCategoryMainList(lists);
                                    mainCatDetails.add(categoryGrid);
                                }


                                Product pro = new Product();
                                pro.setTitle(resources.getString(R.string.video_latest) + " " + resources.getString(R.string.video_news)  );
                                pro.setPostType(DISCOUNT_TITLE);
                                mainCatDetails.add(pro);

                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro2);

                                mediaAdapter.notifyItemInserted(mainCatDetails.size());

                                callAds();
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

                mainCategory(main);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                KyarlayAds main = new KyarlayAds();
                main.setId(-810);
                mainCategory(main);

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    private void mainCategory(KyarlayAds kyarlayAds)
    {



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantRadingDetail+ "&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&page="+prefs.getIntPreferences(SP_PAGE_CATEGORY) + "&post_type=media",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);
                        if(response.length() > 0) {
                            prefs.saveIntPerferences(SP_PAGE_CATEGORY, prefs.getIntPreferences(SP_PAGE_CATEGORY) + SP_DEFAULT);

                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            loading = false;


                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Reading>>() {
                                }.getType();
                                List<Reading> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < categoryList.size(); i++) {

                                    if (i == 2){
                                        if (kyarlayAds.getId() != -810){

                                            kyarlayAds.setPostType(ADS);
                                            mainCatDetails.add(kyarlayAds);

                                        }
                                    }

                                    Reading reading = new Reading();
                                    reading = categoryList.get(i);
                                    reading.setPostType(VIDEO_NEWS);



                                    mainCatDetails.add(reading);

                                }


                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro2);
                                mediaAdapter.notifyItemInserted(mainCatDetails.size());


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);


                            mediaAdapter.notifyItemInserted(mainCatDetails.size());

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


                mediaAdapter.notifyDataSetChanged();


            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }





}
