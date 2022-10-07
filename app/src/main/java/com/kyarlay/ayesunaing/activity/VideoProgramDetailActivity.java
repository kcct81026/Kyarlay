package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.BuildConfig;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.object.Videos;
import com.kyarlay.ayesunaing.operation.ClickeListener;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MainGridItemClickListener;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class VideoProgramDetailActivity extends AppCompatActivity implements ConstantVariable, Constant, YouTubePlayer.OnInitializedListener {
    private static final String TAG = "VideoProgramDetailActiv";

    ProgressBar progressBar;
    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> videosList = new ArrayList<>();
    MediaAdapter mediaAdapter;
    DatabaseAdapter databaseAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;

    String VIDEO_ID;

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    YouTubePlayerFragment myYouTubePlayerFragment;

    LinearLayout comment_layout;

    CustomTextView title, like_text, comment_text;
    ImageView like, share;
    Reading reading;
    String status = "";
    String isComment = "";

    public YouTubePlayer youTubePlayer;
    public boolean isFullscreen = false;
    String nowPlaying  = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_detail);

        Log.e(TAG, "onCreate:  "  );

        VIDEO_ID = BuildConfig.YOUTUBE_ID;

        display = getWindowManager().getDefaultDisplay();
        prefs           = new MyPreference(VideoProgramDetailActivity.this);
        Context context = LocaleHelper.setLocale(VideoProgramDetailActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        new MyFlurry(VideoProgramDetailActivity.this);
        databaseAdapter = new DatabaseAdapter(VideoProgramDetailActivity.this);


        reading = new Reading();
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        reading.setId(bundle.getInt("id"));
        reading.setTitle(bundle.getString("title"));
        reading.setLikes(bundle.getInt("like_count"));
        reading.setComment_coount(bundle.getInt("comment_count"));
        reading.setSort_by(bundle.getString("from_class"));
        status   = bundle.getString("status");
        reading.setSort_by(status);
        reading.setPostType(READING_DETAIL);
        isComment   = bundle.getString("comment");
        nowPlaying  = bundle.getString("now_playing");

        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("type", "read_post");
            mix.put("source", "post_detail");
            mix.put("post_id", String.valueOf(reading.getId()));
            mix.put("status", status);
            FlurryAgent.logEvent("View Video Program", mix);

        } catch (Exception e) {
        }


        getVideos();


        if(nowPlaying != null){

            VIDEO_ID = nowPlaying;
            myYouTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                    .findFragmentById(R.id.youtubeplayerfragment);
            myYouTubePlayerFragment.initialize(BuildConfig.DEVELOPER_KEY, VideoProgramDetailActivity.this);
        }

        mediaAdapter = new MediaAdapter(VideoProgramDetailActivity.this, videosList);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);

        progressBar         = (ProgressBar) findViewById(R.id.progressBar1);
        share               = (ImageView) findViewById(R.id.share);
        like_text           = (CustomTextView) findViewById(R.id.like_text);
        comment_text        = (CustomTextView) findViewById(R.id.comment_text);
        like                = (ImageView) findViewById(R.id.wishlist);
        comment_layout      = (LinearLayout) findViewById(R.id.comment_layout);

        if(reading.getLikes() > 0 || databaseAdapter.checkVideoLiked(reading.getId())) {
            like_text.setVisibility(View.VISIBLE);
            like_text.setText(reading.getLikes()+ "");

            if(databaseAdapter.checkVideoLiked(reading.getId())){
                like.setImageResource(R.drawable.wishlist_clicked);
                if(reading.getLikes() == 0)
                    like_text.setText(reading.getLikes()+1 + "");
            }else {
                like.setImageResource(R.drawable.wishlist);
            }
        }
        else {
            like_text.setVisibility(View.GONE);
            like.setImageResource(R.drawable.wishlist);
        }


        if(reading.getLikes() != 0)
            like_text.setText(String.valueOf(reading.getLikes())+" Likes");
        else
            like_text.setVisibility(View.GONE);

        if(reading.getComment_coount() != 0)
            comment_text.setText(String.valueOf(reading.getComment_coount()));
        else
            comment_text.setVisibility(View.GONE);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "video_program");
                    mix.put("post_id", String.valueOf( reading.getId()));
                    FlurryAgent.logEvent("Click Video Program Share Icon", mix);
                } catch (Exception e) {
                }
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, reading.getUrl());
                startActivity(Intent.createChooser(shareIntent, "Share link using"));
            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                        prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){
                    sendVideoLike(reading.getId());

                    prefs.saveIntPerferences(SP_POST_LIKE_COUNT, 1500);
                    prefs.saveIntPerferences(SP_POST_LIKE_COUNT_ID, reading.getId());
                }else{
                    Intent intent   = new Intent(VideoProgramDetailActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });

        comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "video_program");
                    mix.put("post_id", String.valueOf(reading.getId()));
                    FlurryAgent.logEvent("Click Video Program Comment Icon", mix);


                } catch (Exception e) {
                }

                Intent intent = new Intent(VideoProgramDetailActivity.this, ReadingCommentDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("id", reading.getId());
                bundle.putString("title", reading.getTitle());
                bundle.putInt("like_count", reading.getLikes());
                bundle.putInt("comment_count", reading.getComment_coount());
                bundle.putString("post_type", "video_programs");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);

        recyclerView.addOnItemTouchListener(new MainGridItemClickListener(getApplicationContext(),
                recyclerView, videosList, new ClickeListener() {
            @Override
            public void onClick(View view, int position) {
                myYouTubePlayerFragment.onPause();
                myYouTubePlayerFragment.onDestroy();

                for(int i =0 ; i < videosList.size(); i++){
                    Videos pro = new Videos();
                    pro = (Videos) videosList.get(i);

                    if(position == i){
                        VIDEO_ID = pro.getYoutube_id();
                        pro.setNowPlaying(1);

                        myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
                                .findFragmentById(R.id.youtubeplayerfragment);
                        myYouTubePlayerFragment.initialize(BuildConfig.DEVELOPER_KEY, VideoProgramDetailActivity.this);

                    }else{
                        pro.setNowPlaying(0);
                    }
                }
                mediaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLongClick(View view, int position) {
            }

        }));

    }

    @Override
    public void onBackPressed() {

        if (youTubePlayer != null && isFullscreen) {

            youTubePlayer.setFullscreen(false);

        } else {

            if (reading.getSort_by() != null && reading.getSort_by().equals(FROM_FIREBASE)) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "Video_Program_Detail");
                    FlurryAgent.logEvent("Incoming Pushnotification Click", mix);
                } catch (Exception e) {
                }

                Intent backIntent = new Intent(VideoProgramDetailActivity.this, MainActivity.class);
                startActivity(backIntent);

                finish();
            } else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,

                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
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
                    isFullscreen = b;
                }
            });
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(BuildConfig.DEVELOPER_KEY, this);
        }
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView)findViewById(R.id.youtubeplayerfragment);
    }



    private void getVideos()
    {


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantVideoProgramDetail+reading.getId()
                +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);
                        if(response.length() > 0) {

                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Videos>>() {
                                }.getType();
                                List<Videos> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < categoryList.size(); i++) {
                                    Videos pro = new Videos();
                                    pro = categoryList.get(i);


                                    if (reading.getSort_by() != null && reading.getSort_by().equals(FROM_FIREBASE) && nowPlaying != null &&pro.getYoutube_id().equals(nowPlaying) ) {

                                        pro.setNowPlaying(1);
                                    }else if(i == 0 && nowPlaying == null ) {
                                        pro.setNowPlaying(1);
                                        VIDEO_ID = pro.getYoutube_id();
                                        myYouTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                                                .findFragmentById(R.id.youtubeplayerfragment);
                                        myYouTubePlayerFragment.initialize(BuildConfig.DEVELOPER_KEY, VideoProgramDetailActivity.this);

                                    }else {
                                        pro.setNowPlaying(0);
                                    }
                                    videosList.add(pro);

                                }


                                Videos pro = new Videos();
                                pro.setPostType(CART_DETAIL_NO_ITEM);
                                videosList.add(pro);
                                mediaAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse:   "   + e.getMessage());
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading = false;

                ToastHelper.showToast(VideoProgramDetailActivity.this,  resources.getString(R.string.search_error));



            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



    private void sendVideoLike(int id)
    {
        String url_end  = "/like";

        if(databaseAdapter.checkVideoLiked(id)){
            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "unlike");
                mix.put("source", "post_detail");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Video Program Like Icon", mix);



            } catch (Exception e) {
            }

            url_end = "/unlike";
            like.setImageResource(R.drawable.wishlist);
            databaseAdapter.insertVideoLike(id, "unlike");
            prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)-1));
        }else {
            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "like");
                mix.put("source", "post_detail");
                mix.put("post_id", String.valueOf(id));
                FlurryAgent.logEvent("Click Video Program Like Icon", mix);
            } catch (Exception e) {
            }
            like.setImageResource(R.drawable.wishlist_clicked);
            databaseAdapter.insertVideoLike(id, "like");
            prefs.saveIntPerferences(SP_POST_LIKED_COUNT, (prefs.getIntPreferences(SP_POST_LIKED_COUNT)+1));
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, constantVideoProgramLike+id+url_end, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if(response.getInt("cached_votes_score") == 1) {

                                like_text.setText(response.getInt("cached_votes_score") + " Like");
                                like_text.setVisibility(View.VISIBLE);
                            }else if (response.getInt("cached_votes_score") > 1){

                                like_text.setText(response.getInt("cached_votes_score") + " Likes");
                                like_text.setVisibility(View.VISIBLE);

                            }else {
                                like_text.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse:  "   + e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                like.setImageResource(R.drawable.wishlist);

            }
        }) {

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

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }



}
