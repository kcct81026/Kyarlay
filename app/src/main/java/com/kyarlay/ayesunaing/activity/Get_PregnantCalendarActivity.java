package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.KyarlayAds;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Get_PregnantCalendarActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "Get_PregnantCalendarAct";
    MyPreference prefs;
    RecyclerView recyclerView;
    Resources resources;
    RecyclerView.LayoutManager manager;
    int start_day, start_month, start_year, num_of_day, life_cycle;

    int nMonths [] = {31,28,31,30,31,30,31,31,30,31,30,31};

    List<EventDay> events = new ArrayList<>();
    Calendar showCalendar;
    CalendarView calendarView;

    LinearLayout back_layout, title_layout;


    CustomTextView title, get_pragnant, get_period, start_period , txtCalculate;

    Display display;

    int from_Class = 0;

    ImageLoader imageLoader;
    RelativeLayout ads_layout;
    NetworkImageView imgAds;
    LinearLayout main_ads_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_pragnant_calendar);

        Log.e(TAG, "onCreate: " );

        prefs = new MyPreference(Get_PregnantCalendarActivity.this);
        Context context = LocaleHelper.setLocale(Get_PregnantCalendarActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        title   = findViewById(R.id.title);
        txtCalculate   = findViewById(R.id.txtCalculate);
        calendarView =  findViewById(R.id.calendarView);
        title.setText(resources.getString(R.string.get_pregnant));
        get_pragnant      = findViewById(R.id.get_pregnant);
        get_period      = findViewById(R.id.get_period);
        back_layout     = findViewById(R.id.back_layout);
        title_layout    = findViewById(R.id.title_layout);
        start_period    = findViewById(R.id.start_period);



        imageLoader  = AppController.getInstance().getImageLoader();
        display = getWindowManager().getDefaultDisplay();

        ads_layout = findViewById(R.id.ads_layout);
        imgAds = findViewById(R.id.imgAds);
        main_ads_layout = findViewById(R.id.main_ads_layout);



        txtCalculate.setText(resources.getString(R.string.get_pargnant_cat));
        txtCalculate.setTypeface( txtCalculate.getTypeface(), Typeface.BOLD);
        //txtCalculate.setPaintFlags(txtCalculate.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        back_layout.getLayoutParams().width = ( display.getWidth() * 3) /20;
        title_layout.getLayoutParams().width = ( display.getWidth() * 17) /20;
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
                    Intent intent = new Intent(Get_PregnantCalendarActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });


        txtCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from_Class == 1){
                    finish();
                }else{
                    Intent intent = new Intent(Get_PregnantCalendarActivity.this,  Get_PregnantActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        get_pragnant.setText(resources.getString(R.string.get_pregnant1));
        get_period.setText(resources.getString(R.string.get_peroid));
        start_period.setText(resources.getString(R.string.start_period));
       // get_period2.setText(resources.getString(R.string.get_pregnant));

        Intent intent = getIntent();
        start_day     = intent.getIntExtra("start_day", 0);
        start_month   = intent.getIntExtra("start_month", 0);
        start_year    = intent.getIntExtra("start_year",0);
        life_cycle  = intent.getIntExtra("life_cycle",0);
        num_of_day  = intent.getIntExtra("num_of_day",0);
        from_Class  = intent.getIntExtra("from_Class",0);

        Log.e(TAG, "onBindViewHolder: start_day " + start_day );
        Log.e(TAG, "onBindViewHolder: life_cycle " + life_cycle );
        Log.e(TAG, "onBindViewHolder: start_month " + start_month );
        Log.e(TAG, "onBindViewHolder: start_year " + start_year );


        main_ads_layout.setVisibility(View.GONE);
        callAdsBanner();


        //int difference = life_cycle - 28;
       // int adjusted_day  = start_day + difference;
       // int first_day_of_ovalution = adjusted_day + num_of_day + 8;

        Calendar calendar  = Calendar.getInstance();
        calendar.set(start_year, start_month, start_day);

        Calendar tempCalendar2 = (Calendar) calendar.clone();
        events.add(new EventDay(tempCalendar2, R.drawable.flower_2));
        prefs.saveStringPreferences(SP_SINGLE_PREV_MENSTRAL_DAY, tempCalendar2.getTime().toString());

        for (int i = 0; i < num_of_day - 1; i++) {
            Calendar calendar1  = (Calendar) tempCalendar2.clone();
            calendar1.add(Calendar.DATE, 1);
            events.add(new EventDay(calendar1, R.drawable.flower_2));

            tempCalendar2 = (Calendar) calendar1.clone();


        }

        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.add(Calendar.DATE, life_cycle - 12);
        events.add(new EventDay(tempCalendar, R.drawable.pragnant));

        for (int i = 0; i < 4; i++) {
            Calendar calendar1  = (Calendar) tempCalendar.clone();
            calendar1.add(Calendar.DATE, -1);
            events.add(new EventDay(calendar1, R.drawable.pragnant));

            tempCalendar = (Calendar) calendar1.clone();


            if (i == 3)
                prefs.saveStringPreferences(SP_SINGLE_OVAL_DAY, tempCalendar.getTime().toString());

            Log.e(TAG, "onCreate:  coming oval "   + tempCalendar.getTime() );


        }

/*
        for (int i = 0; i < 3; i++) {
            Calendar calendar1  = (Calendar) tempCalendar.clone();
            calendar1.add(Calendar.DATE, -1);
            events.add(new EventDay(calendar1, R.drawable.pregnant_2));

            tempCalendar = (Calendar) calendar1.clone();
        }
*/

        showCalendar = (Calendar) tempCalendar.clone();

        Calendar tempCalendar1 = (Calendar) calendar.clone();
        tempCalendar1.add(Calendar.DATE, life_cycle-1);
        events.add(new EventDay(tempCalendar, R.drawable.flower1));

        for (int i = 0; i < num_of_day; i++) {
            Calendar calendar1  = (Calendar) tempCalendar1.clone();
            calendar1.add(Calendar.DATE, 1);
            events.add(new EventDay(calendar1, R.drawable.flower1));

            tempCalendar1 = (Calendar) calendar1.clone();



            if (i == 0)
                prefs.saveStringPreferences(SP_SINGLE_COMING_MENSTRAL_DAY, tempCalendar1.getTime().toString());

            Log.e(TAG, "onCreate:  coming "  + tempCalendar1.getTime() );
        }



        calendarView.setEvents(events);
        //calendarView.showCurrentMonthPage();
        calendarView.setSelected(true);
        try {
            calendarView.setDate(showCalendar);

        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }


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
                                                                Intent intent = new Intent(Get_PregnantCalendarActivity.this , AndroidLoadImageFromAdsUrl.class);
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
            Intent intent = new Intent(Get_PregnantCalendarActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }


}