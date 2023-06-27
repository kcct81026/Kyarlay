package com.kyarlay.ayesunaing.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
//import com.flurry.android.FlurryAgent;
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
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.ChildGrowthObj;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.Supplier;
import com.kyarlay.ayesunaing.operation.OrderCountAdapter;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DueDateActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "DueDateActivity";

    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager manager;
    CustomTextView title;
    LinearLayout back_layout, title_layout, due_date_layout, life_cycle_layout;
    Display display;
    CustomTextView due_date_title, life_cycle_title, due_date_text, result;
    ImageView due_date_image;
    int mYear, mMonth, mDay, dueYear, dueMonth, dueDay, lifecycleCount = 0;
    Spinner lifecycle_spinner;
    CustomButton calculate, recalculate;
    NetworkImageView img;
   // GifImageView imgGif;
    LinearLayout layoutOne, layoutTwo;

    private String due_date = "";
    int baby_month = 0, baby_year = 0, baby_day = 0;



    int nMonths [] = {31,28,31,30,31,30,31,31,30,31,30,31};




    int weeks = 0, weekOfDays = 0;
    public static final int WEEKS_IN_PREGNANCY = 40;


    private NetworkImageView imgWeek;
    private CustomTextView txtWeek;
    private ImageLoader imageLoader;
    Reading childReading;


    RelativeLayout ads_layout;
    NetworkImageView imgAds;
    LinearLayout main_ads_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_due_date);

        Log.e(TAG, "onCreate: " );
       // new MyFlurry(DueDateActivity.this);

        if( getIntent().getExtras() != null)
        {
            due_date = getIntent().getStringExtra("due_date");
        }


        imageLoader         = AppController.getInstance().getImageLoader();

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        prefs = new MyPreference(DueDateActivity.this);
        display = getWindowManager().getDefaultDisplay();
        Context context = LocaleHelper.setLocale(DueDateActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        ads_layout = findViewById(R.id.ads_layout);
        imgAds = findViewById(R.id.imgAds);
        main_ads_layout = findViewById(R.id.main_ads_layout);



        title               = findViewById(R.id.title);
        back_layout         = findViewById(R.id.back_layout);
        title_layout        = findViewById(R.id.title_layout);
        due_date_title      = findViewById(R.id.due_date_title);
        life_cycle_title    = findViewById(R.id.life_cycle_title);
        due_date_text       = findViewById(R.id.due_date_text);
        due_date_image      = findViewById(R.id.due_date_image);
        due_date_layout     = findViewById(R.id.due_date_layout);
        life_cycle_layout   = findViewById(R.id.life_cycle_layout);
        lifecycle_spinner   = findViewById(R.id.life_cycle_spinner);
        calculate           = findViewById(R.id.calculate);
        recalculate           = findViewById(R.id.recaculate);
        result              = findViewById(R.id.due_date);
        layoutOne = findViewById(R.id.layoutOne);
        layoutTwo = findViewById(R.id.layoutTwo);


        imgWeek = findViewById(R.id.imgWeek);
        txtWeek = findViewById(R.id.txtWeek);

        layoutTwo.setVisibility(View.GONE);

        calculate.setText(resources.getString(R.string.due_date_button));
        recalculate.setText(resources.getString(R.string.re_due_date_button));

        img                 = findViewById(R.id.img);


        due_date_title.setText(resources.getString(R.string.due_date_title));
        life_cycle_title.setText(resources.getString(R.string.due_date_life_cycle));;

        if(prefs.getIntPreferences(SP_PERIOD_DAY) != 0
                && prefs.getIntPreferences(SP_PERIOD_MONTH) != 0
                && prefs.getIntPreferences(SP_PERIOD_YEAR) != 0  ){

            dueDay  = prefs.getIntPreferences(SP_PERIOD_DAY);
            dueMonth =(prefs.getIntPreferences(SP_PERIOD_MONTH) - 1 );
            dueYear = prefs.getIntPreferences(SP_PERIOD_YEAR);
            due_date_text.setText(dueDay + "-" +( dueMonth+1 ) + "-" + dueYear+"");

        }

        main_ads_layout.setVisibility(View.GONE);
        callAdsBanner();


        title.setText(resources.getString(R.string.due_date_button));
        title_layout.getLayoutParams().width = ( display.getWidth() * 17 ) / 20;
        back_layout.getLayoutParams().width = ( display.getWidth() * 3 ) / 20;
        due_date_layout.getLayoutParams().width = (display.getWidth() * 9 )/ 10;
        life_cycle_layout.getLayoutParams().width = (display.getWidth() * 9 )/ 10;
        due_date_text.getLayoutParams().width       = (display.getWidth() * 8 ) / 10;


        txtWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "post_list");
                    mix.put("post_id", String.valueOf(childReading.getId()));
                    //FlurryAgent.logEvent("Weekly_Highligh_Post", mix);
                } catch (Exception e) {
                }


                Intent intent = new Intent(DueDateActivity.this, ReadingCommentDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("id", childReading.getId());
                bundle.putString("page_url", childReading.getPage_img_url());
                bundle.putString("title", childReading.getTitle());
                bundle.putInt("like_count", childReading.getLikes());
                bundle.putInt("comment_count", childReading.getComment_coount());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        imgWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "post_list");
                    mix.put("post_id", String.valueOf(childReading.getId()));
                    //FlurryAgent.logEvent("Weekly_Highligh_Post", mix);
                } catch (Exception e) {
                }


                Intent intent = new Intent(DueDateActivity.this, ReadingCommentDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("id", childReading.getId());
                bundle.putString("page_url", childReading.getPage_img_url());
                bundle.putString("title", childReading.getTitle());
                bundle.putInt("like_count", childReading.getLikes());
                bundle.putInt("comment_count", childReading.getComment_coount());
                intent.putExtras(bundle);
                startActivity(intent);
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
                    Intent intent = new Intent(DueDateActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });




        due_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DueDateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dueDay  = dayOfMonth;
                                dueMonth = monthOfYear;
                                dueYear = year;
                                due_date_text.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+"");
                                prefs.saveIntPerferences(SP_PERIOD_DAY, dayOfMonth);
                                prefs.saveIntPerferences(SP_PERIOD_MONTH, (monthOfYear+1));
                                prefs.saveIntPerferences(SP_PERIOD_YEAR, year);


                                baby_month = (monthOfYear+1);
                                baby_year = year;
                                baby_day = dayOfMonth;


                            }
                        }, mYear, mMonth, mDay);

                if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                } else {
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                    Calendar maxCalendar = Calendar.getInstance();
                    maxCalendar.add(Calendar.YEAR, -1);
                    datePickerDialog.getDatePicker().setMinDate(maxCalendar.getTimeInMillis());

                }

                datePickerDialog.show();
            }
        });


        recalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutOne.setVisibility(View.VISIBLE);
                layoutTwo.setVisibility(View.GONE);
                txtWeek.setVisibility(View.GONE);
                imgWeek.setVisibility(View.GONE);
            }
        });



        final ArrayList<Supplier> coutslist1 = new ArrayList<>();

        for (int i = 23; i <= DUE_DATE_COUNT; i++) {
            Supplier supplier = new Supplier();
            supplier.setId(i);
            supplier.setName((i) + "");
            coutslist1.add(supplier);
        }

        lifecycle_spinner.setAdapter(new OrderCountAdapter(getApplicationContext(),
                DueDateActivity.this, R.layout.delivery_fee_spinner_layout, coutslist1));
        lifecycle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lifecycleCount = coutslist1.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lifecycle_spinner.setSelection(5);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){





                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "Due Date Detail");
                    //FlurryAgent.logEvent("Calculate Due Date", mix);

                } catch (Exception e) {
                }
                if(due_date_text.getText() == null || due_date_text.getText().toString() == ""){

                    ToastHelper.showToast(DueDateActivity.this, resources.getString(R.string.due_date_title_hint));

                }else if(lifecycleCount == 0){

                    ToastHelper.showToast(DueDateActivity.this, resources.getString(R.string.due_date_life_cycle_hint));


                }else{




                       calculateDate();

                    String dt = dueYear + "-" + (dueMonth+1) + "-" + dueDay;

                    // Start date
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = Calendar.getInstance();
                    try {
                        calendar.setTime(sdf.parse(dt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onClick: " + e.getMessage());
                    }

                    int difference1 = lifecycleCount - 28;
                    difference1 = 280 + difference1;
                    calendar.add(Calendar.DATE, difference1);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                    SimpleDateFormat sdf1 = new SimpleDateFormat("M-d-yyyy");
                    String output = sdf1.format(calendar.getTime());


                    try{
                        String[] splits = output.split("-");

                        String days = splits[1] + "";
                        String month = splits[0] + "";
                        String year = splits[2] + "";


                        String weeksString = "";
                        if (prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_MYANMAR)){
                            if (weeks <= 1)
                                weeksString = 1 + " ပတ္" ;
                            else if (weeks > 42)
                                weeksString = "42 ပတ္" ;
                            else
                                weeksString = weeks + " ပတ္ ";
                        }
                        else{
                            if (weeks <= 1)
                                weeksString = 1 + " week" ;
                            else if (weeks > 42)
                                weeksString =  42 + " weeks" ;
                            else
                                weeksString = weeks + " weeks ";
                        }

                        try{
                            if (due_date.equals("due_date")) {


                                prefs.saveBooleanPreference(SP_USER_STATUS, true);
                                prefs.saveBooleanPreference(SP_DUE_DATE, true);
                                prefs.saveIntPerferences(SP_KID_MONTH, Integer.parseInt(month));
                                prefs.saveIntPerferences(SP_KID_YEAR, Integer.parseInt(year));
                                prefs.saveIntPerferences(SP_KID_DAY, Integer.parseInt(days));
                                prefs.saveIntPerferences(SP_KID_WEEK, weeks);
                                prefs.saveIntPerferences(SP_KID_TYPE, 0);


                                if (prefs.getIntPreferences(SP_KID_TYPE) == 1)
                                    prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PARENT);
                                else
                                    prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PREGNANCY);

                            }

                        }catch (Exception e){
                            Log.e(TAG, "onClick: "  + e.getMessage() );
                        }


                        String strText = String.format(resources.getString(R.string.due_date_result), days+"", month+"",year+"", weeksString);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            result.setText(Html.fromHtml( strText, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            result.setText(Html.fromHtml(strText));
                        }

                        layoutTwo.setVisibility(View.VISIBLE);
                        layoutOne.setVisibility(View.GONE);

                        getChangeBabyInformation(weeks);

                    }catch (Exception e){
                        Log.e(TAG, "onClick:  "   + e.getMessage() );
                    }

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
                                                                Intent intent = new Intent(DueDateActivity.this , AndroidLoadImageFromAdsUrl.class);
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



    private void getChangeBabyInformation(int weeksPeriod){

        String type = "type=pregnancy";



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,"https://www.kyarlay.com/api/weekly_highlights?period=" + weeksPeriod + "&" + type +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response.length() > 0) {



                            try {

                                Gson gson = new Gson();
                                ChildGrowthObj main = gson.fromJson(response.toString(), ChildGrowthObj.class);


                                imgWeek.setImageUrl( main.getPhoto(), imageLoader);

                                imgWeek.getLayoutParams().height = (main.getPhoto_dimen()* display.getWidth()) / 100;








                                if(main.getId()!= 0){

                                    childReading = main.getReading();
                                    if(childReading.getBody().length() > 150) {
                                        txtWeek.setText(childReading.getBody().substring(0, 150) + " ...... continue reading  ");
                                    }else{
                                        txtWeek.setText(childReading.getBody() + " ...... continue reading  ");
                                    }


                                    imgWeek.setVisibility(View.VISIBLE);
                                    txtWeek.setVisibility(View.VISIBLE);

                                }




                            } catch (Exception e) {


                            }


                        }}

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );


            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void calculateDate(){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String  dateAfterString= mDay + " " + (mMonth + 1)  + " " + mYear;

        String dateBeforeString  = dueDay + " " + (dueMonth + 1)  + " " + dueYear;


        try {
            Date dateBefore = myFormat.parse(dateBeforeString);
            Date dateAfter = myFormat.parse(dateAfterString);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000*60*60*24));


            weeks = (int) (daysBetween / 7);
            weekOfDays = (int)  (daysBetween % 7);



        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "calculateDate: " + e.getMessage() );
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
            Intent intent = new Intent(DueDateActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }
}
