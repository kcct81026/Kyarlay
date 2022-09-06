/*
package com.kyarlay.ayesunaing.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.CompetitionPostActivity;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.ClickeListener;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MainGridItemClickListener;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


*/
/**
 * Created by ayesu on 7/13/17.
 *//*


public class FragmentRecent extends Fragment implements Constant,ConstantVariable {

    private static final String TAG = "FragmentRecent";

    ProgressBar progressBar;
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

    CustomTextView group_chat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_recent, container, false);

        mainCatDetails.clear();
        activity        = (AppCompatActivity) getActivity();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();



        new MyFlurry(getActivity());

        display         = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity);
        recyclerView        = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar         = (ProgressBar) rootView.findViewById(R.id.progressBar1);


        group_chat          = (CustomTextView) rootView.findViewById(R.id.action_button);


        Log.e(TAG, "onCreateView: "  );

        group_chat.setText(resources.getString(R.string.competition_post));




        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.VISIBLE);
            group_chat.setVisibility(View.VISIBLE);

            prefs.saveBooleanPreference(COMPETITION_DELETE, false);

            mainCatDetails.clear();

            loading = true;
            prefs.saveIntPerferences(SP_PAGE_REDING_NUM, SP_DEFAULT);

            if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                    || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){
                mainCategory();
            }
            else
                hasPhoto();


        }else{
            progressBar.setVisibility(View.GONE);
            group_chat.setVisibility(View.GONE);

            ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

        }




        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                if(image != null) {
                    image.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                if(image != null) {
                    image.setVisibility(View.GONE);
                }
            }
        });


        universalAdapter = new UniversalAdapter( (AppCompatActivity) activity, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        recyclerView.setBackgroundColor(activity.getResources().getColor(R.color.background));

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean scrollUp = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE && scrollUp) {
                    group_chat.setVisibility(View.VISIBLE);
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
                    prefs.saveIntPerferences(SP_PAGE_REDING_NUM, prefs.getIntPreferences(SP_PAGE_REDING_NUM) + SP_DEFAULT);
                    mainCategory();
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
                        universalAdapter.notifyItemInserted(mainCatDetails.size());
                        mainCategory();
                    }else{

                        ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

                    }
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }

        }));


        group_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                        || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                    prefs.saveIntPerferences(SP_READING_LOGIN, 1);
                    Intent intent   = new Intent(activity, ActivityLogin.class);
                    activity.startActivity(intent);

                }
                else {
                    prefs.saveIntPerferences(SP_CHANGE, 1);
                    Intent intent = new Intent(activity, CompetitionPostActivity.class);
                    activity.startActivity(intent);
                }

            }
        });
        setHasOptionsMenu(true);


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();




        if (prefs.getBooleanPreference(COMPETITION_DELETE) || prefs.getIntPreferences(SP_COMPETITION_POST_ID) != 0){
            if (prefs.isNetworkAvailable()){
                progressBar.setVisibility(View.VISIBLE);
                group_chat.setVisibility(View.VISIBLE);



                prefs.saveBooleanPreference(COMPETITION_DELETE, false);
                prefs.remove(SP_COMPETITION_POST_ID);

                mainCatDetails.clear();

                prefs.saveIntPerferences(SP_PAGE_REDING_NUM, SP_DEFAULT);
                loading = true;

                hasPhoto();
            }else{
                progressBar.setVisibility(View.GONE);
                group_chat.setVisibility(View.GONE);

                ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

            }
        }


    }




    private void mainCategory()
    {
        int page    = prefs.getIntPreferences(SP_PAGE_REDING_NUM);


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantCompetition + "/" + prefs.getIntPreferences(COMPETITION_ID) + "?page=" + page,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                       if(response.length() > 0) {
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


                                    Reading pro = new Reading();
                                    pro = categoryList.get(i);

                                    if (pro.getId() != prefs.getIntPreferences(SP_USER_COMPETITION_PHOTO_ID)) {

                                        mainCatDetails.add(pro);


                                    }

                                }

                                Reading pro = new Reading();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro);
                                universalAdapter.notifyItemInserted(mainCatDetails.size());

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


                           universalAdapter.notifyItemInserted(mainCatDetails.size());

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

                universalAdapter.notifyItemInserted(mainCatDetails.size());

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void hasPhoto()
    {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, constantCompetitionHasPhoto   , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);


                        if (response.length() > 0){

                            try {
                                Gson gson = new Gson();
                                Reading reading = gson.fromJson(response.toString(), Reading.class);


                                if (reading.getPostType().trim().length() > 0 && reading.getPostType() != null) {
                                    mainCatDetails.add(reading);



                                    prefs.saveIntPerferences(SP_USER_COMPETITION_PHOTO_ID, reading.getId());

                                    group_chat.setVisibility(View.GONE);
                                }
                                else{
                                    prefs.saveIntPerferences(SP_USER_COMPETITION_PHOTO_ID, 0);
                                    group_chat.setVisibility(View.VISIBLE);
                                }

                                Product noitem = new Product();
                                noitem.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(noitem);


                                universalAdapter.notifyDataSetChanged();
                                mainCategory();

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }

                        }else{
                            prefs.saveIntPerferences(SP_USER_COMPETITION_PHOTO_ID, 0);
                            group_chat.setVisibility(View.VISIBLE);
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_FOOTER);
                            mainCatDetails.add(noitem);


                            universalAdapter.notifyDataSetChanged();
                            mainCategory();
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
                noitem.setPostType(CART_DETAIL_FOOTER);
                mainCatDetails.add(noitem);
                prefs.saveIntPerferences(SP_USER_COMPETITION_PHOTO_ID, 0);

                universalAdapter.notifyDataSetChanged();
                mainCategory();
                group_chat.setVisibility(View.VISIBLE);
            }
        }) {

            */
/**
             * Passing some request headers
             * *//*

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"Order123");
    }




}

*/
