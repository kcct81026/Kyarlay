package com.kyarlay.ayesunaing.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.flurry.android.FlurryAgent;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.perf.metrics.Trace;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.DueDateActivity;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.object.Videos;
import com.kyarlay.ayesunaing.operation.ClickeListener;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MainGridItemClickListener;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kyarlay.ayesunaing.data.Config.DEVELOPER_KEY;

public class FragmentVideoList extends Fragment implements ConstantVariable, Constant , YouTubePlayer.OnInitializedListener
{

    private static final String TAG = "FragmentVideoList";

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> videosList = new ArrayList<>();
    UniversalAdapter universalAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;


    String nowPlaying  = null;

    // video
    private static String VIDEO_ID = "xS1nqnArb38";

    YouTubePlayerSupportFragment myYouTubePlayerFragment;
    YouTubePlayer youTubePlayer;

    boolean fullscreenFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_list, container, false);

        activity  = (AppCompatActivity) getActivity();


        display = getActivity().getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(getActivity());
        Context context = LocaleHelper.setLocale(getActivity(), prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        new MyFlurry(getActivity());
        databaseAdapter = new DatabaseAdapter(getActivity());
        universalAdapter = new UniversalAdapter((AppCompatActivity) getActivity(), videosList);
        myYouTubePlayerFragment = (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.youtubeplayerfragment1);
        recyclerView        = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);

        getVideos();
        Log.e(TAG, "onCreateView: "  );

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        recyclerView.addOnItemTouchListener(new MainGridItemClickListener(getActivity().getApplicationContext(),
                recyclerView, videosList, new ClickeListener() {
            @Override
            public void onClick(View view, int position) {
                if(videosList.get(position).getPostType().equals(VIDEO_POST)){

                    for(int i =0 ; i < videosList.size(); i++){
                        Videos pro = new Videos();
                        pro = (Videos) videosList.get(i);

                        try {


                            Map<String, String> mix = new HashMap<String, String>();
                            mix.put("post_id", String.valueOf( pro.getId()));
                            mix.put("source", "videos_list");
                            FlurryAgent.logEvent("Videos List Click Item", mix);
                        } catch (Exception e) {
                        }

                        if(position == i){
                            VIDEO_ID = pro.getYoutube_id().trim();
                            pro.setNowPlaying(1);

                            youTubePlayer.cueVideo(VIDEO_ID);
                            youTubePlayer.play();

                        }else{
                            pro.setNowPlaying(0);
                        }
                    }
                    universalAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));

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
                    getVideos();
                }

            }
        });

        return rootView;
    }



    public boolean isFullScreen(){
       return fullscreenFlag;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
        }
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.youtubeplayerfragment1);
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,

                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(activity, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        youTubePlayer = player;

        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
            player.setFullscreenControlFlags(0);
            player.play();

            player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                @Override
                public void onFullscreen(boolean b) {
                    fullscreenFlag = b;
                }
            });
        }

    }


    private void getVideos(){


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantGetVideoList  +prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        //
                        if(response.length() > 0) {


                            loading = false;
                            prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + SP_DEFAULT);

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Videos>>() {
                                }.getType();
                                List<Videos> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < categoryList.size(); i++) {
                                    Videos pro = new Videos();
                                    pro = categoryList.get(i);


                                    if(i == 0 && nowPlaying == null) {
                                        pro.setNowPlaying(1);
                                        VIDEO_ID = pro.getYoutube_id().trim();
                                        nowPlaying = VIDEO_ID;
                                        myYouTubePlayerFragment.initialize(DEVELOPER_KEY, FragmentVideoList.this);

                                    }else {
                                        pro.setNowPlaying(0);
                                    }
                                    videosList.add(pro);
                                    universalAdapter.notifyItemInserted(videosList.size());



                                }

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else{
                            Videos pro = new Videos();
                            pro.setPostType(CART_DETAIL_NO_ITEM);
                            videosList.add(pro);
                            universalAdapter.notifyItemInserted(videosList.size());

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

