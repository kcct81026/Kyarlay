package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.KyarlayAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.myatminsoe.mdetect.MDetect;
import mmcalendar.Astro;
import mmcalendar.AstroConverter;
import mmcalendar.CalendarType;
import mmcalendar.Config;
import mmcalendar.HolidayCalculator;
import mmcalendar.Language;
import mmcalendar.LanguageCatalog;
import mmcalendar.MyanmarDate;
import mmcalendar.MyanmarDateConverter;

public class CalendarDayActivity extends AppCompatActivity implements Constant, ConstantVariable {
    private static final String TAG = "CalendarDayActivity";

    private CustomTextView myanYearInfo,  myanInfo, myanDayInfo, myanNagarInfo, myanYatyarInfo;
    private CustomTextView  engInfo, engAllInfo;
    private CustomTextView todayInfo, txtHoldiay;
    private CustomTextView title,txtFortuneTeller, work_group, txtdesc;
    private ImageView img;
    private LinearLayout linearDay, title_layout,linearFortune;
    private RatingBar rating;


    private String dateIntent ;
    private GestureDetector gestureDetector;

    private int yearIntEn, mthIntEN,dayIntEN ;
    private String teller = "", desc = "", wg = "";
    private int luck = 0;
    private boolean checkAll = false;

    private Resources resources;
    private MyPreference prefs;

    ImageLoader imageLoader;
    RelativeLayout ads_layout;
    NetworkImageView imgAds;
    LinearLayout main_ads_layout;
    Display display;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar_day);

        dateIntent = getIntent().getStringExtra("date");
        prefs = new MyPreference(CalendarDayActivity.this);
        Context context = LocaleHelper.setLocale(CalendarDayActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        new MyFlurry(CalendarDayActivity.this);

        Log.e(TAG, "onCreate: " );


        if (MDetect.INSTANCE.isUnicode()){
            Config.initDefault(
                    new Config.Builder()
                            .setCalendarType(CalendarType.ENGLISH)
                            .setLanguage(Language.MYANMAR)
                            .build());
        }
        else{
            Config.initDefault(
                    new Config.Builder()
                            .setCalendarType(CalendarType.ENGLISH)
                            .setLanguage(Language.ZAWGYI)
                            .build());
        }




        linearDay = findViewById(R.id.linearDay);
        myanInfo = findViewById(R.id.myanInfo);
        myanYearInfo = findViewById(R.id.myanYearInfo);
        myanDayInfo = findViewById(R.id.myanDayInfo);
        myanNagarInfo = findViewById(R.id.myanNagarInfo);
        myanYatyarInfo = findViewById(R.id.myanYatyarInfo);
        engInfo = findViewById(R.id.engInfo);
        engAllInfo = findViewById(R.id.engAllInfo);
        todayInfo = findViewById(R.id.todayInfo);
        txtHoldiay = findViewById(R.id.txtHoldiay);

        title = findViewById(R.id.title);
        txtdesc = findViewById(R.id.desc);
        work_group = findViewById(R.id.work_group);
        rating = findViewById(R.id.rating);
        txtFortuneTeller = findViewById(R.id.txtFortuneTeller);
        title_layout = findViewById(R.id.title_layout);
        linearFortune = findViewById(R.id.linearFortune);


        imageLoader  = AppController.getInstance().getImageLoader();
        display = getWindowManager().getDefaultDisplay();

        ads_layout = findViewById(R.id.ads_layout);
        imgAds = findViewById(R.id.imgAds);
        main_ads_layout = findViewById(R.id.main_ads_layout);


        title_layout.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(CalendarDayActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });

        try {


            Map<String, String> mix = new HashMap<String, String>();
            mix.put("type", "day_calendar");
            FlurryAgent.logEvent("View Day Calendar", mix);
        } catch (Exception e) {
        }


        try {

            String[] splits = dateIntent.split("-");
            String  day          = splits[0];
            String year         = splits[2];

            yearIntEn = Integer.parseInt(year);
            mthIntEN = stringMonth(splits[1]);
            dayIntEN = Integer.parseInt(day);

            getFortuneData();




        } catch (Exception e) {
            Log.e(TAG, "onCreate:  "  +   e.getMessage() );
        }

        setValuesIntoLayout();
        this.gestureDetector = new GestureDetector(this, new DayGestureDetector());
        linearDay.setOnTouchListener(new LinearDayTouchListener() );




        main_ads_layout.setVisibility(View.GONE);
        callAdsBanner();
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
                                                                Intent intent = new Intent(CalendarDayActivity.this , AndroidLoadImageFromAdsUrl.class);
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




    private void getFortuneData(){
        checkAll = false;
        linearFortune.setVisibility(View.GONE);

        Log.e(TAG, "getFortuneData: "  +  String.format(constantFortuneData, yearIntEn, mthIntEN, dayIntEN) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE));

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,String.format(constantFortuneData, yearIntEn, mthIntEN, dayIntEN) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            teller = response.getString("fortune_teller");
                            desc = response.getString("desc");
                            luck = response.getInt("luck_group");
                            wg = response.getString("work_group");

                            if(desc != null && desc.trim().length() > 0){
                                txtdesc.setText(desc);
                                txtdesc.setVisibility(View.VISIBLE);
                                checkAll = true;
                            }else
                                txtdesc.setVisibility(View.GONE);

                            if(wg != null && wg.trim().length() > 0){
                                work_group.setText(wg);
                                work_group.setVisibility(View.VISIBLE);
                                checkAll = true;
                            }else
                                work_group.setVisibility(View.GONE);


                            String title = "ေဟာစာတန္း";
                            if (MDetect.INSTANCE.isUnicode()){
                                title = "ဟောစာတန်း";
                            }

                            if(teller != null && teller.trim().length() > 0){
                                txtFortuneTeller.setText(teller + " " + title);
                                txtFortuneTeller.setVisibility(View.VISIBLE);
                                txtFortuneTeller.setTypeface(txtFortuneTeller.getTypeface(), Typeface.BOLD);
                                txtFortuneTeller.setPaintFlags(txtFortuneTeller.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

                            }else
                                txtFortuneTeller.setVisibility(View.GONE);

                            if(luck  != 0 ) {
                                rating.setRating((float) luck);
                                rating.setVisibility(View.VISIBLE);
                                checkAll = true;
                            }else{
                                rating.setVisibility(View.GONE);
                            }

                            if (checkAll){
                                linearFortune.setVisibility(View.VISIBLE);
                            }else
                                linearFortune.setVisibility(View.GONE);




                            } catch (JSONException e) {
                                Log.e(TAG, "onResponse: "  + e.getMessage()  );
                                e.printStackTrace();
                            }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );

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


    private void setValuesIntoLayout(){
        MyanmarDate myanmarDate = MyanmarDateConverter.convert(yearIntEn,mthIntEN, dayIntEN);
        Astro astro = AstroConverter.convert(myanmarDate);


        engAllInfo.setText(dayIntEN + " " + theMonth(mthIntEN - 1)  );
        engInfo.setText("" + yearIntEn);


        if (MDetect.INSTANCE.isUnicode()){
            myanYearInfo.setText("သာသနာနှစ် " + myanmarDate.getBuddhistEra() + " ခု");

            String text =  myanmarDate.getYear() + " ခု" + "၊ " + myanmarDate.getMonthName() + myanmarDate.getMoonPhase()+ " " ;

            if (myanmarDate.getMoonPhase().equals("လဆန်း") || myanmarDate.getMoonPhase().equals("လဆုတ်"))
                text = text  +  "(" + myanmarDate.getFortnightDay() + ")" + "ရက်"  + " ";

            myanInfo.setText(text);
        }
        else{
            myanYearInfo.setText("သာသနာႏွစ္ " + myanmarDate.getBuddhistEra() + " ခု");

            String text =  myanmarDate.getYear() + " ခု" + "၊ " + myanmarDate.getMonthName() + myanmarDate.getMoonPhase()+ " " ;

            if (myanmarDate.getMoonPhase().equals("လဆန္း") || myanmarDate.getMoonPhase().equals("လဆုတ္"))
                text = text  +  "(" + myanmarDate.getFortnightDay() + ")" + "ရက္"  + " ";

            myanInfo.setText(text);
        }

        myanDayInfo.setText(myanmarDate.getWeekDay());

        if (myanmarDate.getWeekDayInt() == 1|| myanmarDate.getWeekDayInt() == 0 || HolidayCalculator.getHoliday(myanmarDate).size() > 0  ){
            if (HolidayCalculator.getHoliday(myanmarDate, Config.get().getCalendarType()).size() > 0 ){


                List<String> stringList = HolidayCalculator.getHoliday(myanmarDate);
                LanguageCatalog languageCatalog = new LanguageCatalog();
                languageCatalog.setLanguage(Language.ZAWGYI);
                String textHoliday = "";
                for (int i = 0; i < stringList.size(); i++){
                    textHoliday  = textHoliday + languageCatalog.translate(stringList.get(i)) + " " ;

                }




                txtHoldiay.setText("ရံုးပိတ္ရက္  ( " + textHoliday + " )" );
                if (MDetect.INSTANCE.isUnicode()){
                    txtHoldiay.setText("ရုံးပိတ်ရက်  ( " + textHoliday + " )" );
                }

                txtHoldiay.setVisibility(View.VISIBLE);
            }else {
                txtHoldiay.setVisibility(View.GONE);
                txtHoldiay.setText("");
            }


            myanYearInfo.setTextColor(getResources().getColor(R.color.coloredInactive));
            myanInfo.setTextColor(getResources().getColor(R.color.coloredInactive));
            myanDayInfo.setTextColor(getResources().getColor(R.color.coloredInactive));
            engAllInfo.setTextColor(getResources().getColor(R.color.coloredInactive));
            engInfo.setTextColor(getResources().getColor(R.color.coloredInactive));

            txtHoldiay.setTextColor(getResources().getColor(R.color.coloredInactive));

        }else{
            txtHoldiay.setVisibility(View.GONE);
            txtHoldiay.setText("");
            myanYearInfo.setTextColor(getResources().getColor(R.color.black));
            myanInfo.setTextColor(getResources().getColor(R.color.black));
            myanDayInfo.setTextColor(getResources().getColor(R.color.black));


            engAllInfo.setTextColor(getResources().getColor(R.color.black));
            engInfo.setTextColor(getResources().getColor(R.color.black));
            todayInfo.setTextColor(getResources().getColor(R.color.black));
        }

        myanDayInfo.setTypeface(myanDayInfo.getTypeface(), Typeface.BOLD);
        engInfo.setTypeface(myanDayInfo.getTypeface(), Typeface.BOLD);
        txtHoldiay.setTypeface(myanDayInfo.getTypeface(), Typeface.BOLD);

        myanYatyarInfo.setVisibility(View.GONE);
        myanNagarInfo.setVisibility(View.GONE);

        String infoText =  "";

        if (astro.getAstroligicalDay().length() > 0){
            infoText = infoText +  astro.getAstroligicalDay() + "\n";


        }

        if (MDetect.INSTANCE.isUnicode()){


            infoText = infoText + "နဂါးခေါင်း " + astro.getNagahle() +  "သို လှည့်သည်။"  + "\n";

            if (astro.getSabbath().length() > 0) {
                infoText = infoText + astro.getSabbath() + "နေ ့ဖြစ်သည်။" + "\n" ;


            }

            infoText = infoText + astro.getMahabote()  +  " ဖွား "  + "( "  + "မဟာဘုတ် )"  + "\n"  ;
            infoText = infoText  + astro.getNakhat() +" ( " +  "နက္ခတ် " +  " )"  + "\n"  ;
        }
        else{


            infoText = infoText + "နဂါးေခါင္း " + astro.getNagahle() + "သို ့ လွည့္သည္။" + "\n";

            if (astro.getSabbath().length() > 0) {
                infoText = infoText + astro.getSabbath() +  "ေန ့ျဖစ္သည္။" + "\n" ;


            }

            infoText = infoText + astro.getMahabote()  +  " ဖြား "  + "( "  + "မဟာဘုတ္ )"  + "\n"  ;
            infoText = infoText  + astro.getNakhat() +" ( " + "နကၡတ္ " +  " )"  + "\n"  ;
        }


        todayInfo.setText(infoText);

    }

    String months[] = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private int stringMonth (String month){
        int index = 0;

        for (int i=0; i < months.length; i++){
            if (months[i].equals(month))
                return i +1 ;
        }

        return index;
    }

    private String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    class LinearDayTouchListener implements View.OnTouchListener {
        LinearDayTouchListener() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            v.performClick();
            return CalendarDayActivity.this.gestureDetector.onTouchEvent(event);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.gestureDetector != null) {
            this.gestureDetector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    class DayGestureDetector extends GestureDetector.SimpleOnGestureListener {

        private int day;
        private int[] daysOfMonth = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        private int month;
        private int year;


        public DayGestureDetector() {
            this.year = CalendarDayActivity.this.yearIntEn;
            this.month = CalendarDayActivity.this.mthIntEN - 1;
            this.day = CalendarDayActivity.this.dayIntEN;
        }

        void onRightToLeftSwipe() {



            if (this.year % 4 == 0) {
                this.daysOfMonth[1] = 29;
            }
            if (this.day > this.daysOfMonth[this.month] - 1) {
                this.day = 1;
                this.month++;
                if (this.month == 12) {
                    this.month = 0;
                    this.year++;
                }
            } else {
                this.day++;
            }
            CalendarDayActivity.this.yearIntEn = this.year;
            CalendarDayActivity.this.mthIntEN = this.month + 1 ;
            CalendarDayActivity.this.dayIntEN = this.day;

            getFortuneData();

            CalendarDayActivity.this.setValuesIntoLayout();
        }

        void onLeftToRightSwipe() {

            if (this.year % 4 == 0) {
                this.daysOfMonth[1] = 29;
            }
            if (this.day == 1) {
                if (this.month == 0) {
                    this.year--;
                    this.month = 11;
                } else {
                    this.month--;
                }
                this.day = this.daysOfMonth[this.month];
            } else {
                this.day--;
            }
            CalendarDayActivity.this.yearIntEn = this.year;
            CalendarDayActivity.this.mthIntEN = this.month + 1 ;
            CalendarDayActivity.this.dayIntEN = this.day;

            getFortuneData();
            CalendarDayActivity.this.setValuesIntoLayout();
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) <= 300.0f) {
                    if (e1.getX() - e2.getX() > 120.0f && Math.abs(velocityX) > 200.0f) {
                        onRightToLeftSwipe();
                    } else if (e2.getX() - e1.getX() > 120.0f && Math.abs(velocityX) > 200.0f) {
                        onLeftToRightSwipe();
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "onFling: " + e.getMessage() );
            }
            return false;
        }

        public boolean onDown(MotionEvent e) {
            return true;
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
            Intent intent = new Intent(CalendarDayActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }
}
