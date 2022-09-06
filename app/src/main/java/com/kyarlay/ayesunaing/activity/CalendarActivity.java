package com.kyarlay.ayesunaing.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomFloatingTextButton;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.LuckyDay;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Supplier;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.OrderCountAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements ConstantVariable, Constant {

    private static final String TAG = "CalendarActivity";

    MyPreference prefs;
    RecyclerView recyclerView;
    Resources resources;
    RecyclerView.LayoutManager manager;

    ArrayList<UniversalPost> dayLists = new ArrayList<>();
    UniversalAdapter universalAdapter;
    CustomFloatingTextButton show_calendar;



    int year;
    int month = -1;

    Calendar calendar;
    LinearLayout backLayout;
    CustomTextView title, month_title;
    Display display;

    String[] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
    String[] monthses = new String[]{"January","February","March","April","May","June","July","August","September","October","November","December"};



    int previousSelectedPosition = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);


        display = getWindowManager().getDefaultDisplay();
        calendar = Calendar.getInstance();
        prefs = new MyPreference(CalendarActivity.this);
        Context context = LocaleHelper.setLocale(CalendarActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        Log.e(TAG, "onCreate: " );

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        show_calendar   =  findViewById(R.id.action_button);
        backLayout      =  findViewById(R.id.back_layout);
        title           =  findViewById(R.id.title) ;
        month_title    = findViewById(R.id.month_title);
        title.setText(resources.getString(R.string.calendar));

        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backLayout.getLayoutParams().width = ( display.getWidth() * 3 ) / 20;

        show_calendar.setTitle(resources.getString(R.string.choose_month_year));
        show_calendar.container.setBackgroundColor(resources.getColor(R.color.background));
        show_calendar.container.getLayoutParams().width        =  getWindowManager().getDefaultDisplay().getWidth() ;
        show_calendar.layout_container.setBackgroundColor(resources.getColor(R.color.black));
        manager = new LinearLayoutManager(CalendarActivity.this);
        recyclerView.setLayoutManager(manager);
        universalAdapter = new UniversalAdapter(CalendarActivity.this, dayLists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);

        month_title.setText(monthses[calendar.get(Calendar.MONTH)]+"  "+calendar.get(Calendar.YEAR));
        if(prefs.isNetworkAvailable()) {
            dayLists.clear();
            Product pro1 = new Product();
            pro1.setPostType(CART_DETAIL_FOOTER);
            dayLists.add(pro1);
            getDaysList((calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR));
        }else{

            ToastHelper.showToast(CalendarActivity.this, resources.getString(R.string.no_internet_error));

        }

        show_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);

                final Dialog dialog = new Dialog(CalendarActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_calendar);

                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width = getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);

                CustomButton confirm = (CustomButton) dialog.findViewById(R.id.dialog_delete_confirm);
                final GridView gridView  = (GridView) dialog.findViewById(R.id.gridview);
                final Spinner spinner    = (Spinner) dialog.findViewById(R.id.spinner);
                CustomTextView title    = (CustomTextView) dialog.findViewById(R.id.title);
                title.setText(resources.getString(R.string.choose_month_year));

                int start = year - 20 ;
                int end     = year + 20;
                final ArrayList<Supplier> coutslist1 = new ArrayList<>();
                for (int i = start; i <= end; i++) {
                    Supplier supplier = new Supplier();
                    supplier.setId(i);
                    supplier.setName((i) + "");
                    coutslist1.add(supplier);
                }

                spinner.setAdapter(new OrderCountAdapter(getApplicationContext(),
                        CalendarActivity.this, R.layout.delivery_fee_spinner_layout, coutslist1));
                spinner.setSelection(20);

                final List<String> monthList = new ArrayList<String>(Arrays.asList(months));
                final ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>
                        (CalendarActivity.this,android.R.layout.simple_list_item_1, monthList);
                gridView.setAdapter(gridViewArrayAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        TextView text = (TextView) view;
                        text.setBackgroundResource(R.color.line_gray);
                        TextView previousSelectedView = (TextView) gridView.getChildAt(previousSelectedPosition);
                        if (previousSelectedPosition != -1){
                            previousSelectedView.setBackgroundResource(R.color.white);
                        }
                        previousSelectedPosition = position;
                        month   = position;
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(month == -1) {

                            ToastHelper.showToast(CalendarActivity.this, resources.getString(R.string.choose_month_year_msg));

                        }else{

                            dialog.dismiss();
                            dayLists.clear();
                            universalAdapter.notifyDataSetChanged();
                            Supplier sup = ( Supplier) spinner.getSelectedItem();
                            year = sup.getId();
                            month_title.setText(monthses[month]+" "+ year);

                            if(prefs.isNetworkAvailable()) {
                                dayLists.clear();
                                searchDaysList(month + 1, year);

                            }else {

                                ToastHelper.showToast(CalendarActivity.this, resources.getString(R.string.no_internet_error));

                            }


                        }
                    }

                });

                dialog.show();


            }
        });


    }


    private void getDaysList(int month1, int year1) {


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantLuckyDay+"?"
                +LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION)
                +"&month="+month1+"&year="+year1,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            try {
                                if(dayLists.size() != 0 && dayLists.get(dayLists.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                    dayLists.remove(dayLists.size() - 1);
                                }

                                if(dayLists.size() != 0 && dayLists.get(dayLists.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                    dayLists.remove(dayLists.size() - 1);
                                }

                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<LuckyDay>>() {
                                }.getType();
                                List<LuckyDay> lists = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < lists.size(); i++) {
                                    LuckyDay luckyDay = new LuckyDay();
                                    luckyDay    = lists.get(i);
                                    luckyDay.setPostType(CALENDAR_DAY);
                                    dayLists.add(luckyDay);
                                }

                                universalAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                        }else{

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            dayLists.add(noitem);
                            universalAdapter.notifyDataSetChanged();
                        }
                        universalAdapter.notifyDataSetChanged();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ToastHelper.showToast(CalendarActivity.this, resources.getString(R.string.search_error));



            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void searchDaysList(int month, int year)
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantLuckyDay+"?"
                +LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION)
                +"&month="+month+"&year="+year,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {


                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<LuckyDay>>() {
                                }.getType();
                                List<LuckyDay> lists = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < lists.size(); i++) {
                                    LuckyDay luckyDay = new LuckyDay();
                                    luckyDay    = lists.get(i);
                                    luckyDay.setPostType(CALENDAR_DAY);
                                    dayLists.add(luckyDay);
                                }

                                universalAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Log.e(TAG, "onResponse: " + e.getMessage());
                            }
                        }else{

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            dayLists.add(noitem);
                            universalAdapter.notifyDataSetChanged();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                ToastHelper.showToast(CalendarActivity.this, resources.getString(R.string.search_error));




            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }





}
