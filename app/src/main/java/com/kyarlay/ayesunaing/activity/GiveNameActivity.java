package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
//import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.KyarlayAds;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GiveNameActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "GiveNameActivity";

    private CustomTextView  txtGender, txtBoy, txtGirl, txtLength;
    private CustomTextView txtOne, txtTwo, txtThree, txtFour;
    private CircularTextView one, two, three, four;
    private RadioButton rdBoy, rdGirl;
    private Spinner spOne, spTwo, spThree, spFour;
    private LinearLayout linearOne, linearTwo, linearThree, linearFour;
    private LinearLayout back_layout, title_layout;
    private RelativeLayout like_layot;
    private Button btnSearch;


    private Resources resources;
    private MyPreference prefs;
    private Display display;

    private String[] daysInEnglish = {"Any day","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private String[] daysInMyanmar = {"မည္သည့္ေန ့နံ ျဖစ္ျဖစ္","တနဂၤေႏြ","တနလၤာ","အဂၤါ","ဗုဒၶဟူး","ၾကာသပေတး","ေသာၾကာ","စေန"};
    private String[] daysInMyanmarUnicode = {"မည်သည့်နေ့နံ ဖြစ်ဖြစ်","တနင်္ဂနွေ","တနင်္လာ","အင်္ဂါ","ဗုဒ္ဓဟူး","ကြာသပတေး","သောကြာ","စနေ"};
    private DaysAdapter endAdapter ;
    private int numClick    = 0 ;

    ImageLoader imageLoader;
    RelativeLayout ads_layout;
    NetworkImageView imgAds;
    LinearLayout main_ads_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_give_a_name);

        prefs = new MyPreference(GiveNameActivity.this);
        Context context = LocaleHelper.setLocale(GiveNameActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display = getWindowManager().getDefaultDisplay();

       // new MyFlurry(GiveNameActivity.this);

        Log.e(TAG, "onCreate: " );


        imageLoader  = AppController.getInstance().getImageLoader();

        ads_layout = findViewById(R.id.ads_layout);
        imgAds = findViewById(R.id.imgAds);
        main_ads_layout = findViewById(R.id.main_ads_layout);

        txtGender = findViewById(R.id.txtGender);
        txtBoy = findViewById(R.id.txtBoy);
        txtGirl = findViewById(R.id.txtGirl);
        txtLength = findViewById(R.id.txtLength);
        rdGirl = findViewById(R.id.rdGirl);
        rdBoy = findViewById(R.id.rdBoy);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        back_layout = findViewById(R.id.back_layout);
        btnSearch = findViewById(R.id.btnSearch);

        txtOne = findViewById(R.id.txtStartOne);
        txtTwo = findViewById(R.id.txtStartTwo);
        txtThree = findViewById(R.id.txtStartThree);
        txtFour = findViewById(R.id.txtStartFour);
        spOne = findViewById(R.id.spOne);
        spTwo = findViewById(R.id.spTwo);
        spThree = findViewById(R.id.spThree);
        spFour = findViewById(R.id.spFour);

        linearOne = findViewById(R.id.linearOne);
        linearTwo = findViewById(R.id.linearTwo);
        linearThree = findViewById(R.id.linearThree);
        linearFour = findViewById(R.id.linearFour);

        like_layot = findViewById(R.id.like_layot);
        title_layout = findViewById(R.id.title_layout);




        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        like_layot.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("source", "view name page");
            //FlurryAgent.logEvent("View Give Name Page", mix);
        } catch (Exception e) {
        }

        txtGender.setText(resources.getString(R.string.gender));
        txtBoy.setText(resources.getString(R.string.boy));
        txtGirl.setText(resources.getString(R.string.girl));
        txtLength.setText(resources.getString(R.string.lenght));
        txtOne.setText(resources.getString(R.string.startOne));
        txtTwo.setText(resources.getString(R.string.startTwo));
        txtThree.setText(resources.getString(R.string.startThree));
        txtFour.setText(resources.getString(R.string.endwith));
        one.setText(resources.getString(R.string.one));
        two.setText(resources.getString(R.string.two));
        three.setText(resources.getString(R.string.three));
        four.setText(resources.getString(R.string.four));
        btnSearch.setText(resources.getString(R.string.search_name));

        rdBoy.setChecked(true);
        rdGirl.setChecked(false);

        one.setStrokeWidth(1);
        one.setStrokeColor("#000000");
        one.setSolidColor("#F2cc23");


        txtOne.setVisibility(View.VISIBLE);
        linearOne.setVisibility(View.VISIBLE);


        txtTwo.setVisibility(View.GONE);
        linearTwo.setVisibility(View.GONE);


        txtThree.setVisibility(View.GONE);
        linearThree.setVisibility(View.GONE);

        txtFour.setVisibility(View.GONE);
        linearFour.setVisibility(View.GONE);
        two.setStrokeWidth(1);
        two.setStrokeColor("#000000");
        two.setSolidColor("#ffffff");
        three.setStrokeWidth(1);
        three.setStrokeColor("#000000");
        three.setSolidColor("#ffffff");
        four.setStrokeWidth(1);
        four.setStrokeColor("#000000");
        four.setSolidColor("#ffffff");

        one.setOnClickListener(new clickFriendFeedback( one, two, three, four,  0));
        two.setOnClickListener(new clickFriendFeedback( one, two, three, four, 1));
        three.setOnClickListener(new clickFriendFeedback( one, two, three, four,  2));
        four.setOnClickListener(new clickFriendFeedback( one, two, three, four, 3));

        rdBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdBoy.setChecked(true);
                rdGirl.setChecked(false);


            }
        });

        rdGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdGirl.setChecked(true);
                rdBoy.setChecked(false);
            }
        });



        main_ads_layout.setVisibility(View.GONE);
        callAdsBanner();

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!prefs.getBooleanPreference(SP_ADS_LOADED)){
                    CountDownTimer countDownTimer = new CountDownTimer(5*60 *1000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {


                            prefs.saveBooleanPreference(SP_ADS_LOADED, true);
                            prefs.saveIntPerferences(ADS_COUNT_DOWN_TIME, (int)(millisUntilFinished / 1000) );



                        }

                        @Override
                        public void onFinish() {

                            prefs.saveBooleanPreference(SP_ADS_LOADED, false);

                        }
                    };

                    countDownTimer.start();
                    Intent intent = new Intent(GiveNameActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });

        like_layot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "product_list");
                        //FlurryAgent.logEvent("Click Names Wishlist Icon", mix);
                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(GiveNameActivity.this, NameWishListActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(GiveNameActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("length", String.valueOf(numClick+1));

                    if (rdGirl.isChecked())
                        mix.put("gender", "female");
                    else if (rdBoy.isChecked())
                        mix.put("gender", "male");

                    //FlurryAgent.logEvent("Click Name Search", mix);

                } catch (Exception e) {
                }

                if (prefs.isNetworkAvailable()){
                    Intent intent = new Intent(GiveNameActivity.this, ShowAllNamesActivity.class);
                    intent.putExtra("length", numClick+1);
                    intent.putExtra("first_index", spOne.getSelectedItemPosition());
                    intent.putExtra("second_index", spTwo.getSelectedItemPosition());
                    intent.putExtra("third_index", spThree.getSelectedItemPosition());
                    intent.putExtra("fourth_index", spThree.getSelectedItemPosition());

                    if (rdGirl.isChecked())
                        intent.putExtra("gender", "female");
                    else if (rdBoy.isChecked())
                        intent.putExtra("gender", "male");
                    startActivity(intent);

                }
                else{

                    ToastHelper.showToast(GiveNameActivity.this, resources.getString(R.string.no_internet_error));

                }
            }
        });



        if (prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_ENGLISH)){
            endAdapter = new DaysAdapter(getApplicationContext(),GiveNameActivity.this, R.layout.days_spinner, daysInEnglish);
            endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spOne.setAdapter(endAdapter);
            spTwo.setAdapter(endAdapter);
            spThree.setAdapter(endAdapter);
            spFour.setAdapter(endAdapter);
        }
        else  if (prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_MYANMAR)){

            endAdapter = new DaysAdapter(getApplicationContext(),GiveNameActivity.this, R.layout.days_spinner, daysInMyanmar);
            endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spOne.setAdapter(endAdapter);
            spTwo.setAdapter(endAdapter);
            spThree.setAdapter(endAdapter);
            spFour.setAdapter(endAdapter);
        }
        else  if (prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_UNI)){

            endAdapter = new DaysAdapter(getApplicationContext(),GiveNameActivity.this, R.layout.days_spinner, daysInMyanmarUnicode);
            endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spOne.setAdapter(endAdapter);
            spTwo.setAdapter(endAdapter);
            spThree.setAdapter(endAdapter);
            spFour.setAdapter(endAdapter);
        }

        spOne.setSelection(0);
        spOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        spTwo.setSelection(0);
        spTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spThree.setSelection(0);
        spThree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spFour.setSelection(0);
        spFour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



    }

    private void callAdsBanner(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "banner" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {




                if (response.length() > 0){
                    Gson gson = new Gson();
                    KyarlayAds main = gson.fromJson(response.toString(), KyarlayAds.class);


                    try{


                        main_ads_layout.setVisibility(View.VISIBLE);

                        imgAds.getLayoutParams().height = ( display.getWidth() * main.getImage_dimen() ) / 100;
                        ads_layout.getLayoutParams().height = ( display.getWidth() * main.getImage_dimen() ) / 100;
                        imgAds.setImageUrl(main.getImage_url(), imageLoader);

                        ads_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                                        Request.Method.GET, String.format(constantAdsClick, main.getId()+""), null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                try{

                                                    if (response.getInt("status")  == 1){

                                                        if (main.getType().equals("link")){
                                                            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(main.getTarget_url()));
                                                            startActivity(myIntent);
                                                        }
                                                        else if(main.getType().equals("image")){
                                                            try {
                                                                Intent intent = new Intent(GiveNameActivity.this , AndroidLoadImageFromAdsUrl.class);
                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("url", main.getImage_url().toString());
                                                                intent.putExtras(bundle);
                                                                startActivity(intent);
                                                            } catch (Exception e) {
                                                                Log.e(TAG, "onSliderClick: "  + e.getMessage() );

                                                            }
                                                        }
                                                    }

                                                }catch (Exception e){

                                                }


                                            }
                                        },  new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );


                                    }
                                });
                                AppController.getInstance().addToRequestQueue(jsonObjReq, "VersionDownload");


                            }
                        });




                    }catch (Exception e){
                        Log.e(TAG, "onBindViewHolder: Exception " + e.getClass() );
                    }
                }






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    public class DaysAdapter extends ArrayAdapter<String> {

        AppCompatActivity activity;
        String[] objects;
        public DaysAdapter(Context context, AppCompatActivity activity, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            this.activity       = activity;
            this.objects        = objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.days_spinner, null);
            CustomTextView label = (CustomTextView) convertView.findViewById(R.id.delivey_fee_spinner_layout_text);

            label.setText(objects[position]);
            return convertView;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.days_spinner, null);
            CustomTextView label = (CustomTextView) convertView.findViewById(R.id.delivey_fee_spinner_layout_text);
            label.setText(objects[position]);
            return convertView;
        }
    }

    public  class clickFriendFeedback implements View.OnClickListener{

        CircularTextView  one, two, three, four;
        CircularTextView [] textViews = {};

        int position;

        public clickFriendFeedback( CircularTextView one,
                                   CircularTextView two, CircularTextView three,
                                   CircularTextView four,  int position) {
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;

            this.position = position;
            textViews= new CircularTextView[]{ one, two, three, four};
        }

        @Override
        public void onClick(View v) {
            numClick    = position;
            for(int i= 0 ; i < 4; i ++){
                if(i == position){


                    textViews[i].setStrokeWidth(1);
                    textViews[i].setStrokeColor("#000000");
                    textViews[i].setSolidColor("#F2cc23");

                    if (i==0){
                        txtOne.setVisibility(View.VISIBLE);
                        linearOne.setVisibility(View.VISIBLE);

                        txtTwo.setVisibility(View.GONE);
                        linearTwo.setVisibility(View.GONE);


                        txtThree.setVisibility(View.GONE);
                        linearThree.setVisibility(View.GONE);

                        txtFour.setVisibility(View.GONE);
                        linearFour.setVisibility(View.GONE);

                        spTwo.setSelection(0);
                        spThree.setSelection(0);
                        spFour.setSelection(0);
                    }

                    else if (i == 1){
                        txtOne.setVisibility(View.VISIBLE);
                        linearOne.setVisibility(View.VISIBLE);

                        txtTwo.setVisibility(View.VISIBLE);
                        linearTwo.setVisibility(View.VISIBLE);


                        txtThree.setVisibility(View.GONE);
                        linearThree.setVisibility(View.GONE);

                        txtFour.setVisibility(View.GONE);
                        linearFour.setVisibility(View.GONE);


                        spThree.setSelection(0);
                        spFour.setSelection(0);

                    }

                    else if (i == 2){
                        txtOne.setVisibility(View.VISIBLE);
                        linearOne.setVisibility(View.VISIBLE);

                        txtTwo.setVisibility(View.VISIBLE);
                        linearTwo.setVisibility(View.VISIBLE);


                        txtThree.setVisibility(View.VISIBLE);
                        linearThree.setVisibility(View.VISIBLE);

                        txtFour.setVisibility(View.GONE);
                        linearFour.setVisibility(View.GONE);


                        spFour.setSelection(0);
                    }

                    else if (i == 3){
                        txtOne.setVisibility(View.VISIBLE);
                        linearOne.setVisibility(View.VISIBLE);

                        txtTwo.setVisibility(View.VISIBLE);
                        linearTwo.setVisibility(View.VISIBLE);


                        txtThree.setVisibility(View.VISIBLE);
                        linearThree.setVisibility(View.VISIBLE);

                        txtFour.setVisibility(View.VISIBLE);
                        linearFour.setVisibility(View.VISIBLE);
                    }

                }else{
                    textViews[i].setStrokeWidth(1);
                    textViews[i].setStrokeColor("#000000");
                    textViews[i].setSolidColor("#ffffff");

                }


            }

        }
    }

    @Override
    public void onBackPressed() {
        if (!prefs.getBooleanPreference(SP_ADS_LOADED)){
            CountDownTimer countDownTimer = new CountDownTimer(5*60 *1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {


                    prefs.saveBooleanPreference(SP_ADS_LOADED, true);
                    prefs.saveIntPerferences(ADS_COUNT_DOWN_TIME, (int)(millisUntilFinished / 1000) );



                }

                @Override
                public void onFinish() {

                    prefs.saveBooleanPreference(SP_ADS_LOADED, false);

                }
            };

            countDownTimer.start();
            Intent intent = new Intent(GiveNameActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }


}
