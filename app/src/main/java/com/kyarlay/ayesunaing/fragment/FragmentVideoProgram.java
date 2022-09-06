package com.kyarlay.ayesunaing.fragment;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FragmentVideoProgram extends Fragment implements Constant,ConstantVariable {

    private static final String TAG = "FragmentVideoProgram";

    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    UniversalAdapter  universalAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;
    CustomTextView title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_program, container, false);

        mainCatDetails.clear();
        activity        = (AppCompatActivity) getActivity();
        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        title       =  rootView.findViewById(R.id.title);
        title.setText(resources.getString(R.string.video_program));



        display         = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity);
        recyclerView        = (RecyclerView) rootView.findViewById(R.id.recycler_view);


        prefs.saveIntPerferences(SP_PAGE_VIDEO_PROGRAM, SP_DEFAULT);

        recyclerView.setBackgroundColor(activity.getResources().getColor(R.color.background));
        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        Log.e(TAG, "onCreateView: "  );

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

        universalAdapter = new UniversalAdapter( (MainActivity)activity, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);
        if(prefs.isNetworkAvailable())
            getVideos();
        else {

            ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

        }


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean scrollUp = false;

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
                    prefs.saveIntPerferences(SP_PAGE_VIDEO_PROGRAM, prefs.getIntPreferences(SP_PAGE_VIDEO_PROGRAM) + SP_DEFAULT);
                    getVideos();
                }

            }
        });




        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
    }


    private void getVideos()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantVideo+prefs.getIntPreferences(SP_PAGE_VIDEO_PROGRAM)
                +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

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
                                    mainCatDetails.add(pro);


                                }
                                Reading pro = new Reading();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro);
                                universalAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }
                        }else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);


                            universalAdapter.notifyDataSetChanged();

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = false;

                ToastHelper.showToast( activity, resources.getString(R.string.search_error));



            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



}

