package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.PointHistoryVOList;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.PointAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentReceived extends Fragment implements Constant, ConstantVariable {

    private static final String TAG = "FragmentReceived";

    ProgressBar progressBar;
    Boolean loading = true;
    RecyclerView.LayoutManager manager;
    MyPreference prefs;
    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    PointAdapter universalAdapter;
    RecyclerView recyclerView;

    Display display;
    Resources resources;
    AppCompatActivity activity;

    FloatingActionButton group_chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_reading_recycler, container, false);

        universalPosts.clear();
        activity = (AppCompatActivity) getActivity();

        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();




        display = activity.getWindowManager().getDefaultDisplay();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

        group_chat = rootView.findViewById(R.id.action_button);


        Log.e(TAG, "onCreateView: ");

        //group_chat.setText(resources.getString(R.string.wirte_post));


        group_chat.setVisibility(View.GONE);

        prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, SP_DEFAULT);


        manager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(manager);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                if (image != null) {
                    image.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);
                if (image != null) {
                    image.setVisibility(View.GONE);
                }
            }
        });

        universalAdapter = new PointAdapter(activity, universalPosts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);
        recyclerView.setPadding(0,0,0,0);

        Product pro = new Product();
        pro.setPostType(CART_DETAIL_FOOTER);
        universalPosts.add(pro);
        universalAdapter.notifyDataSetChanged();

        getPointInfoNew();

        recyclerView.setBackgroundColor(activity.getResources().getColor(R.color.background));

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
                    //mainCategory();



                    getPointInfoNew();
                }

            }
        });

        return rootView;
    }

    private void getPointInfoNew()
    {

        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(constantPointGetReceivedHistory +  LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE) + "&type=received&page=" + prefs.getIntPreferences(SP_PAGE_BRAND_CLICK),

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                       Log.e(TAG, "onResponse: ------------"  +  constantPointGetReceivedHistory +  LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE) + "&type=received&page=" + prefs.getIntPreferences(SP_PAGE_BRAND_CLICK) );

                        Log.e(TAG, "onResponse: ----------"  + response.toString() );



                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }
                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }

                        if(response.length() > 0) {


                            loading = false;
                            prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, prefs.getIntPreferences(SP_PAGE_BRAND_CLICK) + SP_DEFAULT);

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<PointHistoryVOList>>() {
                                }.getType();
                                List<PointHistoryVOList> pointHistoryVOListList = mGson.fromJson(response.toString(), listType);

                                if(pointHistoryVOListList.size() > 0){
                                    for(int i=0; i < pointHistoryVOListList.size(); i++){
                                        PointHistoryVOList pointHistoryVOList = pointHistoryVOListList.get(i);
                                        pointHistoryVOList.setPostType(POINT_RECEIVED_GET);
                                        universalPosts.add(pointHistoryVOList);
                                    }



                                }
                                Product pro = new Product();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(pro);


                                universalAdapter.notifyItemInserted(universalPosts.size());

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }

                        }else{

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);

                            universalAdapter.notifyItemInserted(universalPosts.size());
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                Product noitem = new Product();
                noitem.setPostType(CART_DETAIL_NO_ITEM);
                universalPosts.add(noitem);

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
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }

}



