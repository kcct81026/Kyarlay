package com.kyarlay.ayesunaing.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.Supplier;
import com.kyarlay.ayesunaing.operation.OrderCountAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Get_PregnantActivity extends AppCompatActivity implements ConstantVariable, Constant {

    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager manager;
    CustomTextView title;
    LinearLayout back_layout, title_layout, due_date_layout, life_cycle_layout, numofday_layout;
    Display display;
    CustomTextView due_date_title, life_cycle_title, due_date_text, numofday_text;
    ImageView due_date_image;
    int mYear, mMonth, mDay, dueYear, dueMonth, dueDay, lifecycleCount = 0, numofdayCount = 0;
    Spinner lifecycle_spinner, numofday_spinner;
    CustomButton calculate;

    int nMonths [] = {31,28,31,30,31,30,31,31,30,31,30,31};

    private static final String TAG = "Get_PregnantActivity";

    ImageLoader imageLoader;
    RelativeLayout ads_layout;
    NetworkImageView imgAds;
    LinearLayout main_ads_layout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_pragnant);

        new MyFlurry(Get_PregnantActivity.this);

        prefs = new MyPreference(Get_PregnantActivity.this);
        display = getWindowManager().getDefaultDisplay();
        Context context = LocaleHelper.setLocale(Get_PregnantActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        imageLoader  = AppController.getInstance().getImageLoader();

        ads_layout = findViewById(R.id.ads_layout);
        imgAds = findViewById(R.id.imgAds);
        main_ads_layout = findViewById(R.id.main_ads_layout);



        Log.e(TAG, "onCreate: " );

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
        numofday_layout     = findViewById(R.id.num_of_day_layout);
        numofday_text       = findViewById(R.id.num_of_day_title);
        numofday_spinner    = findViewById(R.id.num_of_day_spinner);
        calculate           = findViewById(R.id.calculate);
        calculate.setText(resources.getString(R.string.get_pargnant_cat));


        due_date_title.setText(resources.getString(R.string.due_date_title));
        life_cycle_title.setText(resources.getString(R.string.due_date_life_cycle));;
        numofday_text.setText(resources.getString(R.string.get_pregnant_numofday));

        if(prefs.getIntPreferences(SP_SINGLE_CAL_DAY) != 0
                && prefs.getIntPreferences(SP_SINGLE_CAL_MONTH) != 0
                && prefs.getIntPreferences(SP_SINGLE_CAL_YEAR) != 0  ){

            dueDay  = prefs.getIntPreferences(SP_SINGLE_CAL_DAY);
            dueMonth = (prefs.getIntPreferences(SP_SINGLE_CAL_MONTH) - 1 );
            dueYear = prefs.getIntPreferences(SP_SINGLE_CAL_YEAR);
            due_date_text.setText(dueDay + "-" + (dueMonth+1) + "-" + dueYear+"");

        }



        main_ads_layout.setVisibility(View.GONE);
        callAdsBanner();




        title.setText(resources.getString(R.string.get_pargnant_cat));
        title_layout.getLayoutParams().width = ( display.getWidth() * 17 ) / 20;
        back_layout.getLayoutParams().width = ( display.getWidth() * 3 ) / 20;
        due_date_text.getLayoutParams().width       = (display.getWidth() * 8 ) / 10;

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
                    Intent intent = new Intent(Get_PregnantActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });
        due_date_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance(Locale.US);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Get_PregnantActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dueDay  = dayOfMonth;
                                dueMonth = monthOfYear;
                                dueYear = year;
                                due_date_text.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+"");





                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        due_date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Get_PregnantActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dueDay  = dayOfMonth;
                                dueMonth = monthOfYear;
                                dueYear = year;
                                due_date_text.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+"");




                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        final ArrayList<Supplier> coutslist1 = new ArrayList<>();

        for (int i = 21; i <= DUE_DATE_COUNT; i++) {
            Supplier supplier = new Supplier();
            supplier.setId(i);
            supplier.setName((i) + "");
            coutslist1.add(supplier);
        }

        lifecycle_spinner.setAdapter(new OrderCountAdapter(getApplicationContext(),
                Get_PregnantActivity.this, R.layout.delivery_fee_spinner_layout, coutslist1));
        lifecycle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lifecycleCount = coutslist1.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lifecycle_spinner.setSelection(7);

        final ArrayList<Supplier> numofday = new ArrayList<>();

        for (int i = 1; i <= NUM_OF_DAY_COUNT; i++) {
            Supplier supplier = new Supplier();
            supplier.setId(i);
            supplier.setName((i) + "");
            numofday.add(supplier);
        }

        numofday_spinner.setAdapter(new OrderCountAdapter(getApplicationContext(),
                Get_PregnantActivity.this, R.layout.delivery_fee_spinner_layout, numofday));
        numofday_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numofdayCount = numofday.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numofday_spinner.setSelection(3);


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "Pregnant Detail");
                    FlurryAgent.logEvent("Calculate Pregnant", mix);

                } catch (Exception e) {
                }
                if(due_date_text.getText() == null || due_date_text.getText().toString() == ""){

                    ToastHelper.showToast(Get_PregnantActivity.this, resources.getString(R.string.due_date_title_hint));


                }else if(lifecycleCount == 0){

                    ToastHelper.showToast(Get_PregnantActivity.this, resources.getString(R.string.due_date_life_cycle_hint));


                }else if(numofdayCount == 0){

                    ToastHelper.showToast(Get_PregnantActivity.this, resources.getString(R.string.get_pregnant_numofday_hint));


                }else {



                    prefs.saveIntPerferences(SP_SINGLE_CAL_DAY, dueDay);
                    prefs.saveIntPerferences(SP_SINGLE_CAL_MONTH, dueMonth);
                    prefs.saveIntPerferences(SP_SINGLE_CAL_YEAR, dueYear);
                    prefs.saveIntPerferences(SP_SINGLE_CAL_LIFE_CYCLE, lifecycleCount);
                    prefs.saveIntPerferences(SP_SINGLE_CAL_NO_OF_DAY, numofdayCount);


                    Intent intent = new Intent(Get_PregnantActivity.this, Get_PregnantCalendarActivity.class);
                    intent.putExtra("from_Class", 1);
                    intent.putExtra("start_day", dueDay);
                    intent.putExtra("start_month", dueMonth);
                    intent.putExtra("start_year", dueYear);
                    intent.putExtra("life_cycle", lifecycleCount);
                    intent.putExtra("num_of_day", numofdayCount);


                    startActivity(intent);
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
                                                                Intent intent = new Intent(Get_PregnantActivity.this , AndroidLoadImageFromAdsUrl.class);
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
            Intent intent = new Intent(Get_PregnantActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }



}
