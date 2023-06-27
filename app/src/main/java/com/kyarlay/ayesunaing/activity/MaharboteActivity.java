package com.kyarlay.ayesunaing.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
//import com.flurry.android.FlurryAgent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.KyarlayAds;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.myatminsoe.mdetect.MDetect;
import mmcalendar.Astro;
import mmcalendar.AstroConverter;
import mmcalendar.CalendarType;
import mmcalendar.Config;
import mmcalendar.Language;
import mmcalendar.MyanmarDate;
import mmcalendar.MyanmarDateConverter;

public class MaharboteActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "MaharboteActivity";

    private CustomTextView one, two, three, four, five, six, seven;
    private LinearLayout title_layout, back_layout;
    private CustomTextView txtTitleOne,txtTitleTwo, txtTitleAphwar, txtTitleMyanmarDay,
            txtTitleYour, txtTitleZero, txtTitleMyanmarDate, txtModulus,txtTitleEngDay;
    private CustomButton txtCalulate;

    private CustomTextView txtGoodTitle, txtBadTitle, goodOne, goodTwo, goodThree, goodFour, badOne, badTwo, badThree, txtRemind,remark;

    private LinearLayout linearOne, linearTwo;

    Resources resources;
    MyPreference prefs;
    AppCompatActivity activity;
    Display display;
    int[] modulusOne = new int[]{5,0,3,6,4,1,2};
    int[] modulusTwo = new int[]{6,1,4,0,5,2,3};
    int[] modulusThree = new int[]{0,2,5,1,6,3,4};
    int[] modulusFour = new int[]{1,3,6,2,0,4,5};
    int[] modulusFive = new int[]{2,4,0,3,1,5,6};
    int[] modulusSix = new int[]{3,5,1,4,2,6,0};
    int[] modulusZero = new int[]{4,6,2,5,3,0,1};
    MyanmarDate myanmarDate1;
    Astro astro;


    int[] checkDay = new int[7];
    String[] stringDay = new String[7];
    String[] stringDayNumber = new String[7];
    String[] stringDaylongNumber = new String[7];
    int mYear, mMonth, mDay , chooseYear, chooseMonth, chooseDay;
    ImageLoader imageLoader;
    RelativeLayout ads_layout;
    NetworkImageView imgAds,img;
    CustomTextView txtTitle;
    LinearLayout main_ads_layout,layoutHidden, layoutCalculate;
    FloatingActionButton fab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_maharbote);

        activity = MaharboteActivity.this;
         prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display = activity.getWindowManager().getDefaultDisplay();

        Log.e(TAG, "onCreate: " );

        Calendar c =Calendar.getInstance(Locale.US);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) ;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        chooseYear = mYear;
        chooseDay = mDay;
        chooseMonth = mMonth;


        imageLoader  = AppController.getInstance().getImageLoader();

        ads_layout = findViewById(R.id.ads_layout);
        imgAds = findViewById(R.id.imgAds);
        main_ads_layout = findViewById(R.id.main_ads_layout);
        txtTitle = findViewById(R.id.txtTitle);
        img = findViewById(R.id.img);
        layoutHidden = findViewById(R.id.layoutHidden);
        remark = findViewById(R.id.remark);
        layoutCalculate = findViewById(R.id.layoutCalculate);
        fab = findViewById(R.id.fab);

        layoutCalculate.setBackgroundResource(R.drawable.blue_square);
        GradientDrawable drawable1 = (GradientDrawable) layoutCalculate.getBackground();
        drawable1.setColor(Integer.parseInt("FFFFFF", 16)+0xFF000000);
        drawable1.setStroke(1,Integer.parseInt("007BFF", 16)+0xFF000000 );
        drawable1.setCornerRadius(12);




        txtTitle.setText(getIntent().getStringExtra("title"));
        remark.setText("မှတ်ချက်");
        String url = getIntent().getStringExtra("url");
        if (url.trim().length()>0){
            layoutHidden.setVisibility(View.VISIBLE);
            img.setImageUrl(url, imageLoader);
        }





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

        one = findViewById(R.id.txt_one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        title_layout = findViewById(R.id.title_layout);
        back_layout = findViewById(R.id.back_layout);
        linearOne = findViewById(R.id.layoutOne);
        linearTwo = findViewById(R.id.layoutTwo);


        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 14) / 20;
        txtTitleOne = findViewById(R.id.txtTitleOne);
        txtTitleTwo = findViewById(R.id.txtTitleTwo);
        txtTitleAphwar = findViewById(R.id.txtTitleAphwar);
        txtTitleMyanmarDay = findViewById(R.id.txtTitleMyanmarDay);
        txtTitleZero = findViewById(R.id.txtTitleZero);
        txtTitleMyanmarDate = findViewById(R.id.txtTitleMyanmarDate);
        txtTitleEngDay = findViewById(R.id.txtTitleEngDay);
        txtTitleYour = findViewById(R.id.txtTitleYour);
        txtModulus = findViewById(R.id.txtModulus);
        txtRemind = findViewById(R.id.txtRemind);
        txtCalulate = findViewById(R.id.txtCalulate);

        txtGoodTitle = findViewById(R.id.txtGoodTitle);
        txtBadTitle = findViewById(R.id.txtBadTitle);
        goodOne = findViewById(R.id.txtGoodOne);
        goodTwo = findViewById(R.id.txtGoodTwo);
        goodThree = findViewById(R.id.txtGoodThree);
        goodFour = findViewById(R.id.txtGoodFour);
        badOne = findViewById(R.id.txtBadOne);
        badTwo = findViewById(R.id.txtBadTwo);
        badThree = findViewById(R.id.txtBadThree);
        badThree = findViewById(R.id.txtBadThree);


       // new MyFlurry(MaharboteActivity.this);
        try {

            Map<String, String> mix = new HashMap<String, String>();
            mix.put("source", "view maharbote page");
            //FlurryAgent.logEvent("View Maharbote Page", mix);

        } catch (Exception e) {
        }

        txtTitleZero.setText(resources.getString(R.string.baydin_titleZero));
        txtTitleOne.setText(resources.getString(R.string.baydin_titleOne));
        txtTitleTwo.setText(resources.getString(R.string.baydin_titleZero) + " " + resources.getString(R.string.baydin_titleOne) + " " + resources.getString(R.string.baydin_titleTwo));
        txtTitleYour.setText(resources.getString(R.string.baydin_titleThree));


        txtGoodTitle.setText(resources.getString(R.string.baydin_good_title));
        txtBadTitle.setText(resources.getString(R.string.baydin_bad_title));
        txtCalulate.setText(resources.getString(R.string.name_choose_title));



        main_ads_layout.setVisibility(View.GONE);
        callAdsBanner();


        txtCalulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaharboteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                chooseDay  = dayOfMonth;
                                chooseMonth= monthOfYear + 1;
                                chooseYear = year;

                                linearOne.setVisibility(View.VISIBLE);
                                linearTwo.setVisibility(View.VISIBLE);
                                calculation();

                                fab.setVisibility(View.VISIBLE);
                                txtCalulate.setVisibility(View.GONE);
                                layoutHidden.setVisibility(View.GONE);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MaharboteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                chooseDay  = dayOfMonth;
                                chooseMonth= monthOfYear + 1;
                                chooseYear = year;

                                linearOne.setVisibility(View.VISIBLE);
                                linearTwo.setVisibility(View.VISIBLE);
                                calculation();

                                fab.setVisibility(View.VISIBLE);
                                txtCalulate.setVisibility(View.GONE);
                                layoutHidden.setVisibility(View.GONE);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



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
                    Intent intent = new Intent(MaharboteActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
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
                                                                Intent intent = new Intent(MaharboteActivity.this , AndroidLoadImageFromAdsUrl.class);
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

    private void calculation(){
        myanmarDate1 = MyanmarDateConverter.convert(chooseYear, chooseMonth,chooseDay);
        astro = AstroConverter.convert(myanmarDate1);

        txtTitleEngDay.setText(chooseDay + "." + chooseMonth + "." + chooseYear);


        int modulus = myanmarDate1.getYearInt() % 7;
        String text =  myanmarDate1.getYear() + " ခု" + "၊ " + myanmarDate1.getMonthName() + myanmarDate1.getMoonPhase()+ " " ;

        if (myanmarDate1.getMoonPhase().equals("လဆန္း") || myanmarDate1.getMoonPhase().equals("လဆုတ္")) {
            text = text + "(" + myanmarDate1.getFortnightDay() + ")" + "ရက္" + " ";

        }

        txtTitleMyanmarDate.setText(  text );
        txtTitleMyanmarDay.setText(myanmarDate1.getWeekDay() + "နေ့");

        txtTitleAphwar.setText(astro.getMahabote() + "ဖွား");


        String[] strings = resources.getStringArray(R.array.days);

        String[] stringLong = resources.getStringArray(R.array.days_long_number);


        String[] days = resources.getStringArray(R.array.daysNumber);
        int dayIndex = myanmarDate1.getWeekDayInt();

        if (modulus == 0){
            checkDay = modulusZero;
            txtModulus.setText(String.format(resources.getString(R.string.baydin_Moudulus), days[6]));

        }
        else if (modulus == 1){
            checkDay = modulusOne;
            txtModulus.setText(String.format(resources.getString(R.string.baydin_Moudulus), days[0]));
        }
        else if (modulus == 2){
            checkDay = modulusTwo;
            txtModulus.setText(String.format(resources.getString(R.string.baydin_Moudulus), days[1]));
        }
        else if (modulus == 3){
            checkDay = modulusThree;
            txtModulus.setText(String.format(resources.getString(R.string.baydin_Moudulus), days[2]));
        }
        else if (modulus == 4){
            checkDay = modulusFour;
            txtModulus.setText(String.format(resources.getString(R.string.baydin_Moudulus), days[3]));
        }
        else if (modulus == 5){
            checkDay = modulusFive;
            txtModulus.setText(String.format(resources.getString(R.string.baydin_Moudulus), days[4]));
        }
        else if (modulus == 6){
            checkDay = modulusSix;
            txtModulus.setText(String.format(resources.getString(R.string.baydin_Moudulus), days[5]));;
        }



        for (int i=0; i < checkDay.length ; i++){
            if (dayIndex == checkDay[i]){

                seven.setBackgroundColor(activity.getResources().getColor(R.color.white));
                two.setBackgroundColor(activity.getResources().getColor(R.color.white));
                one.setBackgroundColor(activity.getResources().getColor(R.color.white));
                four.setBackgroundColor(activity.getResources().getColor(R.color.white));
                six.setBackgroundColor(activity.getResources().getColor(R.color.white));
                three.setBackgroundColor(activity.getResources().getColor(R.color.white));
                five.setBackgroundColor(activity.getResources().getColor(R.color.white));

                if (i == 0){
                    one.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
                else if (i == 1){
                    two.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
                else if (i == 2){
                    three.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
                else if (i == 3){
                    four.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
                else if (i == 4){
                    five.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
                else if (i == 5){
                    six.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
                else if (i == 6){
                    seven.setBackgroundColor(activity.getResources().getColor(R.color.colorSplashScreen));
                }
            }



            if (checkDay[i] == 1) {
                stringDay[i] = days[0];
                stringDayNumber[i] = strings[0];
                stringDaylongNumber[i] = stringLong[0];
            }
            else if (checkDay[i] == 2){
                stringDay[i] = days[1];
                stringDayNumber[i] = strings[1];
                stringDaylongNumber[i] = stringLong[1];
            }
            else if (checkDay[i] == 3){
                stringDay[i] = days[2];
                stringDayNumber[i] =  strings[2];
                stringDaylongNumber[i] = stringLong[2];
            }
            else if (checkDay[i] == 4){
                stringDay[i] = days[3];
                stringDayNumber[i] =  strings[3];
                stringDaylongNumber[i] = stringLong[3];
            }
            else if (checkDay[i] == 5){
                stringDay[i] = days[4];
                stringDayNumber[i] =  strings[4];
                stringDaylongNumber[i] = stringLong[4];
            }
            else if (checkDay[i] == 6){
                stringDay[i] = days[5];
                stringDayNumber[i] =  strings[5];
                stringDaylongNumber[i] = stringLong[5];
            }
            else if (checkDay[i] == 0){
                stringDay[i] = days[6];
                stringDayNumber[i] =  strings[6];
                stringDaylongNumber[i] = stringLong[6];
            }



        }



        one.setText(resources.getString(R.string.aphwar_one ) + "\n" + stringDay[0] );
        two.setText(resources.getString(R.string.aphwar_two ) + "\n" + stringDay[1]);
        three.setText(resources.getString(R.string.aphwar_three ) + "\n" + stringDay[2]);
        four.setText(resources.getString(R.string.aphwar_four) + "\n" + stringDay[3]);
        five.setText(resources.getString(R.string.aphwar_five ) + "\n" + stringDay[4]);
        six.setText(resources.getString(R.string.aphwar_six ) + "\n" + stringDay[5]);
        seven.setText(resources.getString(R.string.aphwar_seven ) + "\n" + stringDay[6]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           goodOne.setText(Html.fromHtml(String.format(resources.getString(R.string.name_good_one), stringDaylongNumber[0])  , Html.FROM_HTML_MODE_COMPACT));
           goodTwo.setText(Html.fromHtml( String.format(resources.getString(R.string.name_good_two), stringDaylongNumber[1]) , Html.FROM_HTML_MODE_COMPACT));
           goodThree.setText(Html.fromHtml(String.format(resources.getString(R.string.name_good_three), stringDaylongNumber[2])  , Html.FROM_HTML_MODE_COMPACT));
           goodFour.setText(Html.fromHtml(String.format(resources.getString(R.string.name_good_four),stringDaylongNumber[3])  , Html.FROM_HTML_MODE_COMPACT));
           badOne.setText(Html.fromHtml(String.format(resources.getString(R.string.name_bad_one),  stringDaylongNumber[4])  , Html.FROM_HTML_MODE_COMPACT));
           badTwo.setText(Html.fromHtml( String.format(resources.getString(R.string.name_bad_two), stringDaylongNumber[5]) , Html.FROM_HTML_MODE_COMPACT));
           badThree.setText(Html.fromHtml( String.format(resources.getString(R.string.name_bad_three),  stringDaylongNumber[6]) , Html.FROM_HTML_MODE_COMPACT));
            txtRemind.setText(Html.fromHtml(String.format(resources.getString(R.string.name_remind_title),
                   modulus + "",
                   stringDayNumber[6],
                   stringDayNumber[5],
                   stringDayNumber[4],
                   stringDayNumber[3],
                   stringDayNumber[2],
                   stringDayNumber[1],
                   stringDayNumber[0]) , Html.FROM_HTML_MODE_COMPACT));
        } else {
            goodOne.setText(Html.fromHtml(String.format(resources.getString(R.string.name_good_one), stringDaylongNumber[0])));
            goodTwo.setText(Html.fromHtml( String.format(resources.getString(R.string.name_good_two), stringDaylongNumber[1]) ));
            goodThree.setText(Html.fromHtml(String.format(resources.getString(R.string.name_good_three), stringDaylongNumber[2]) ));
            goodFour.setText(Html.fromHtml(String.format(resources.getString(R.string.name_good_four),stringDaylongNumber[3])  ));
            badOne.setText(Html.fromHtml(String.format(resources.getString(R.string.name_bad_one),  stringDaylongNumber[4])  ));
            badTwo.setText(Html.fromHtml( String.format(resources.getString(R.string.name_bad_two), stringDaylongNumber[5]) ));
            badThree.setText(Html.fromHtml( String.format(resources.getString(R.string.name_bad_three),  stringDaylongNumber[6])));
            txtRemind.setText(Html.fromHtml(String.format(resources.getString(R.string.name_remind_title),
                    modulus + "",
                    stringDayNumber[6],
                    stringDayNumber[5],
                    stringDayNumber[4],
                    stringDayNumber[3],
                    stringDayNumber[2],
                    stringDayNumber[1],
                    stringDayNumber[0])));
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
            Intent intent = new Intent(MaharboteActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }
}
