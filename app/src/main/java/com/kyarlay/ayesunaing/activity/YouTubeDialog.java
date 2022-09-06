package com.kyarlay.ayesunaing.activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Config;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Videos;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class YouTubeDialog extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener ,Constant,  ConstantVariable{

    private static final String TAG = "YouTubeDialog";

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String youtubeID;
    String title;
    String programID;
    private MyPreference prefs;
    CustomTextView text;
    Resources resources;
    Videos videos;
    NetworkImageView detailNetworkImg;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtubeview);

        Log.e(TAG, "onCreate:  "  );


        prefs = new MyPreference(YouTubeDialog.this);
        Context context = LocaleHelper.setLocale(YouTubeDialog.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        new MyFlurry(YouTubeDialog.this);


        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.dimAmount = 0.10f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);
        imageLoader         = AppController.getInstance().getImageLoader();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        Intent intent = getIntent();
        videos = intent.getParcelableExtra("youtube_object");

        youtubeID = videos.getYoutube_id();
        title = videos.getTitle();
        programID = videos.getProgram_id();

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        text = (CustomTextView) findViewById(R.id.title);
        detailNetworkImg = findViewById(R.id.detailNetworkImg);

        youTubeView.initialize(Config.DEVELOPER_KEY, this);
        String ads_photo_url = videos.getAds_photo_url();
        if (ads_photo_url.length() != 0 && !ads_photo_url.equals("")){

            detailNetworkImg.setVisibility(View.VISIBLE);
            int heightImg1 = ( videos.getAds_photo_dimen()  * getWindowManager().getDefaultDisplay().getWidth()) / 100;
            detailNetworkImg.getLayoutParams().height = heightImg1;
            detailNetworkImg.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth();
            detailNetworkImg.setImageUrl(videos.getAds_photo_url(), imageLoader);
        }else
            detailNetworkImg.setVisibility(View.GONE);


        String strText = String.format(resources.getString(R.string.watch_related), title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text.setText(Html.fromHtml( strText, Html.FROM_HTML_MODE_COMPACT));
        } else {
            text.setText(Html.fromHtml(strText));
        }

        if (prefs.getIntPreferences(FIRST_PLAY) == 1) {
            prefs.saveIntPerferences(FIRST_PLAY, 0);
            text.setVisibility(View.GONE);

        }
        else
            text.setVisibility(View.VISIBLE);

        if (prefs.isNetworkAvailable()){
            checkCount(videos.getId());
        }



        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "video_program");
                    mix.put("post_id", String.valueOf( videos.getId()));
                    FlurryAgent.logEvent("Videos Program Click Item", mix);
                } catch (Exception e) {
                }

                Intent intent = new Intent(YouTubeDialog.this, VideosClickActivity.class);
                intent.putExtra("youtubeID", youtubeID);
                intent.putExtra("title", title);
                intent.putExtra("program_Id", programID);
                startActivity(intent);
                finish();
            }
        });

        detailNetworkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videos.getAds_type().equals("link_ads")){
                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "link_ads");
                        mix.put("post_id", String.valueOf( videos.getId()));
                        FlurryAgent.logEvent("Videos Ads Click", mix);
                    } catch (Exception e) {
                    }
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videos.getAds_url()));
                        startActivity(myIntent);
                    }catch(ActivityNotFoundException e){

                    }
                }
                else if (videos.getAds_type().equals("img_ads")){
                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "img_ads");
                        mix.put("post_id", String.valueOf( videos.getId()));
                        FlurryAgent.logEvent("Videos Ads Click", mix);
                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(YouTubeDialog.this, AndroidLoadImageFromAdsUrl.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", videos.getAds_photo_url());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        if (youtubeID != null) {
            if (wasRestored) {
                player.play();
            } else {
                player.loadVideo(youtubeID);
            }
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            Toast.makeText(this, getString(R.string.player_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void  checkCount(int countID)
    {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,String.format(constantCounts, "video", countID + "" , prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID)) , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {




                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"Order123");
    }

}

