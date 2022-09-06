package com.kyarlay.ayesunaing.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.LuckyDay;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.myatminsoe.mdetect.MDetect;
import mmcalendar.Astro;
import mmcalendar.AstroConverter;
import mmcalendar.CalendarType;
import mmcalendar.Config;
import mmcalendar.HolidayCalculator;
import mmcalendar.Language;
import mmcalendar.MyanmarDate;
import mmcalendar.MyanmarDateConverter;

@TargetApi(3)
public class CustomCalendarActivity extends AppCompatActivity implements ConstantVariable , Constant {

    private static final String tag = "tag";

    private static final String TAG = "CustomCalendarActivity";


    private GridView calendarView;
    private ImageView imgNext, imgPrev, imgBack, imgSearch;
    private CustomTextView todayMyan, txtChangUi;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private int checkMonth, checkYear;
    private GestureDetector gestureDetector;


    private int dom = 0 , mom = 0, yoy = 0;
    private int lastValue = 0;
    private MyPreference prefs;
    private  List<LuckyDay> lists = new ArrayList<>();
    private int theMONTH = 0, theYEAR = 0;

    private Resources resources;


    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({"NewApi", "NewApi", "NewApi", "NewApi"})
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";
    private static final String dateTemplate2 = "d-MMMM-yyyy";


    private String fullmoon, newmoon, yourDay, hallSarTan, myanCalendarText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_calendar_view);

        prefs = new MyPreference(CustomCalendarActivity.this);
        prefs.saveIntPerferences(SP_CHANGE_UI,  0);
        new MyFlurry(CustomCalendarActivity.this);

        Context context = LocaleHelper.setLocale(CustomCalendarActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        if (MDetect.INSTANCE.isUnicode()){
            Config.initDefault(
                    new Config.Builder()
                            .setCalendarType(CalendarType.GREGORIAN)
                            .setLanguage(Language.MYANMAR)
                            .build());

            fullmoon = "လပြည့်";
            yourDay = "ဖွား";
            hallSarTan = "ဟောစာတန်း";
            myanCalendarText = "မြန်မာပြက္ခဒိန်";
            newmoon = "လကွယ်";

        }
        else{
            Config.initDefault(
                    new Config.Builder()
                            .setCalendarType(CalendarType.GREGORIAN)
                            .setLanguage(Language.ZAWGYI)
                            .build());
            fullmoon = "လျပည့္";
            yourDay = "ဖြား";
            hallSarTan = "ေဟာစာတန္း";
            myanCalendarText = "ျမန္မာျပကၡဒိန္";
            newmoon = "လကြယ္";
        }



        Log.e(TAG, "onCreate: " );

        _calendar = Calendar.getInstance(Locale.US);
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        checkMonth = month;
        checkYear = year;





        todayMyan = findViewById(R.id.todayMyan);
        txtChangUi = findViewById(R.id.txtChangUi);
        todayMyan.setTypeface(todayMyan.getTypeface(), Typeface.BOLD);
        txtChangUi.setTypeface(txtChangUi.getTypeface(), Typeface.BOLD);

        imgPrev = findViewById(R.id.imgPrev);
        imgNext = findViewById(R.id.imgNext);
        imgBack = findViewById(R.id.imgBack);
        imgSearch = findViewById(R.id.imgSearch);


        try {
            Map<String, String> mix = new HashMap<String, String>();
            mix.put("type", "view_calendar");
            FlurryAgent.logEvent("View Calendar", mix);
        } catch (Exception e) {
        }


        Calendar calendar = Calendar.getInstance();




        MyanmarDate myanmarDate1 = MyanmarDateConverter.convert(calendar);

        imgBack.setOnClickListener(new OnClickListener() {
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
                    Intent intent = new Intent(CustomCalendarActivity.this, CallAdsInterstitial.class);
                    startActivity(intent);
                    finish();
                }{
                    finish();
                }
            }
        });



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTemplate, Locale.ENGLISH);
        MyanmarDate firstDate = MyanmarDateConverter.convert(year, checkMonth, 1);
        MyanmarDate endDate = MyanmarDateConverter.convert(year, checkMonth, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String text =  myanmarDate1.getYear()  + " ခု " +  firstDate.getMonthName() + "/" + endDate.getMonthName();
        todayMyan.setText(simpleDateFormat.format(_calendar.getTime()) + "\n" +  text );

        getDaysList(month, year);

        if (prefs.getIntPreferences(SP_CHANGE_UI) == 0) {

            txtChangUi.setText(hallSarTan);

        }

        else if (prefs.getIntPreferences(SP_CHANGE_UI) == 1)
            txtChangUi.setText(myanCalendarText);



        txtChangUi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                setGridCellAdapterToDate(month, year);

                if (prefs.getIntPreferences(SP_CHANGE_UI) == 1){
                    prefs.saveIntPerferences(SP_CHANGE_UI, 0);
                    txtChangUi.setText(hallSarTan);


                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("type", "view_calendar");
                        mix.put("click", "fortune calendar");
                        FlurryAgent.logEvent("Click Calendar Event", mix);

                    } catch (Exception e) {
                    }
                }
                else if (prefs.getIntPreferences(SP_CHANGE_UI) == 0){
                    prefs.saveIntPerferences(SP_CHANGE_UI, 1);
                    txtChangUi.setText(myanCalendarText);

                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("type", "view_calendar");
                        mix.put("click", "myanamar calendar");
                        FlurryAgent.logEvent("Click Calendar Event", mix);
                    } catch (Exception e) {
                    }

                }
            }
        });


        imgSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CustomCalendarActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_calendar_search);
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTemplate2, Locale.ENGLISH);
                CustomTextView dialogToday = dialog.findViewById(R.id.dialogToday);
                dialogToday.setText(simpleDateFormat.format(_calendar.getTime()));

                Button btnMinusMonth = dialog.findViewById(R.id.btnMinusMonth);
                Button btnPlusMonth = dialog.findViewById(R.id.btnPlusMonth);
                Button btnMinusDay = dialog.findViewById(R.id.btnMinusDay);
                Button btnPlusDay = dialog.findViewById(R.id.btnPlusDay);
                Button btnMinusYear = dialog.findViewById(R.id.btnMinusYear);
                Button btnPlusYear = dialog.findViewById(R.id.btnPlusYear);
                final CustomTextView dialogDay = dialog.findViewById(R.id.dialogDay);
                final CustomTextView dialogMonth = dialog.findViewById(R.id.dialogMonth);
                final CustomTextView dialogYear = dialog.findViewById(R.id.dialogYear);
                CustomTextView dialogSearch = dialog.findViewById(R.id.dialogSearch);


                String text = simpleDateFormat.format(_calendar.getTime());


                String[] splits = text.split("-");


                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "search calendar");
                    FlurryAgent.logEvent("Search Calendar", mix);

                } catch (Exception e) {
                }


                if (checkMonth == stringMonth(splits[1]))
                    dom = Integer.parseInt(splits[0]);
                else
                    dom = 1;

                mom = stringMonth(splits[1]);

                yoy = Integer.parseInt(splits[2]);

                dialogDay.setText("" + dom);
                dialogMonth.setText(splits[1]);
                dialogYear.setText("" + yoy);

                Calendar cc = Calendar.getInstance(Locale.getDefault());
                cc.set(Integer.parseInt(splits[2]), stringMonth(splits[1]) - 1, dom );


                lastValue = cc.getActualMaximum(Calendar.DAY_OF_MONTH);

                btnMinusMonth.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mom == 1)
                            mom = 12;
                        else
                            mom = mom -1 ;


                        dialogMonth.setText("" + theMonth(mom - 1));

                    }
                });

                btnMinusYear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yoy = yoy -  1;


                        dialogYear.setText("" + yoy);
                    }
                });

                btnPlusYear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yoy = yoy + 1;


                        dialogYear.setText("" + yoy);
                    }
                });

                btnPlusMonth.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mom == 12)
                            mom = 1;
                        else
                            mom = mom + 1;


                        dialogMonth.setText("" + theMonth(mom - 1));
                    }
                });



                btnMinusDay.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dom == 1)
                            dom = lastValue;
                        else
                            dom = dom -1;

                        dialogDay.setText("" + dom);
                    }
                });

                btnPlusDay.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dom == lastValue)
                            dom = 1;
                        else
                            dom = dom + 1 ;

                        dialogDay.setText("" + dom);
                    }
                });

                dialogSearch.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();



                        prefs.saveIntPerferences(SP_CHANGE_UI, 0);
                        txtChangUi.setText(hallSarTan);

                        month = mom;
                        year = yoy;
                        getDaysList(mom, yoy);

                        setGridCellAdapterToDate(mom, yoy);

                    }
                });




                dialog.show();
            }
        });

        todayMyan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "search calendar");
                    FlurryAgent.logEvent("Search Calendar", mix);
                } catch (Exception e) {
                }


                final Dialog dialog = new Dialog(CustomCalendarActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_calendar_search);
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.width   = getWindowManager().getDefaultDisplay().getWidth();
                window.setAttributes(wlp);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTemplate2, Locale.ENGLISH);
                CustomTextView dialogToday = dialog.findViewById(R.id.dialogToday);
                dialogToday.setText(simpleDateFormat.format(_calendar.getTime()));

                Button btnMinusMonth = dialog.findViewById(R.id.btnMinusMonth);
                Button btnPlusMonth = dialog.findViewById(R.id.btnPlusMonth);
                Button btnMinusDay = dialog.findViewById(R.id.btnMinusDay);
                Button btnPlusDay = dialog.findViewById(R.id.btnPlusDay);
                Button btnMinusYear = dialog.findViewById(R.id.btnMinusYear);
                Button btnPlusYear = dialog.findViewById(R.id.btnPlusYear);
                final CustomTextView dialogDay = dialog.findViewById(R.id.dialogDay);
                final CustomTextView dialogMonth = dialog.findViewById(R.id.dialogMonth);
                final CustomTextView dialogYear = dialog.findViewById(R.id.dialogYear);
                CustomTextView dialogSearch = dialog.findViewById(R.id.dialogSearch);


                String text = simpleDateFormat.format(_calendar.getTime());


                String[] splits = text.split("-");



                if (checkMonth == stringMonth(splits[1]))
                    dom = Integer.parseInt(splits[0]);
                else
                    dom = 1;

                mom = stringMonth(splits[1]);

                yoy = Integer.parseInt(splits[2]);

                dialogDay.setText("" + dom);
                dialogMonth.setText(splits[1]);
                dialogYear.setText("" + yoy);

                Calendar cc = Calendar.getInstance(Locale.getDefault());
                cc.set(Integer.parseInt(splits[2]), stringMonth(splits[1]) - 1, dom );


                lastValue = cc.getActualMaximum(Calendar.DAY_OF_MONTH);

                btnMinusMonth.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mom == 1)
                            mom = 12;
                        else
                            mom = mom -1 ;


                        dialogMonth.setText("" + theMonth(mom - 1));

                    }
                });

                btnMinusYear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yoy = yoy -  1;


                        dialogYear.setText("" + yoy);
                    }
                });

                btnPlusYear.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yoy = yoy + 1;

                        dialogYear.setText("" + yoy);
                    }
                });

                btnPlusMonth.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mom == 12)
                            mom = 1;
                        else
                            mom = mom + 1;


                        dialogMonth.setText("" + theMonth(mom - 1));
                    }
                });



                btnMinusDay.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dom == 1)
                            dom = lastValue;
                        else
                            dom = dom -1;

                        dialogDay.setText("" + dom);
                    }
                });

                btnPlusDay.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dom == lastValue)
                            dom = 1;
                        else
                            dom = dom + 1 ;

                        dialogDay.setText("" + dom);
                    }
                });

                dialogSearch.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();


                        prefs.saveIntPerferences(SP_CHANGE_UI, 0);
                        txtChangUi.setText(hallSarTan);
                        month = mom;
                        year = yoy;
                        getDaysList(mom, yoy);
                        getDaysList(month, year);
                        setGridCellAdapterToDate(mom, yoy);

                    }
                });




                dialog.show();
            }
        });

        calendarView = (GridView) this.findViewById(R.id.calendar);

        // Initialised
        adapter = new GridCellAdapter(getApplicationContext(),
               month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);



        this.gestureDetector = new GestureDetector(this, new DayViewGestureDetector());
        calendarView.setOnTouchListener(new LinearTouchListener() );

        imgPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "view_calendar");
                    mix.put("click", "previous month calendar");
                    FlurryAgent.logEvent("Click Calendar Event", mix);

                } catch (Exception e) {
                }

                if (month <= 1) {
                    month = 12;
                    year--;
                } else {
                    month--;
                }
                prefs.saveIntPerferences(SP_CHANGE_UI, 0);
                txtChangUi.setText(hallSarTan);

                getDaysList(month, year);
                setGridCellAdapterToDate(month, year);
            }
        });

        imgNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "view_calendar");
                    mix.put("click", "next month calendar");
                    FlurryAgent.logEvent("Click Calendar Event", mix);

                } catch (Exception e) {
                }

                if (month > 11) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
                prefs.saveIntPerferences(SP_CHANGE_UI, 0);

                txtChangUi.setText(hallSarTan);

                getDaysList(month, year);

                setGridCellAdapterToDate(month, year);
            }
        });
    }

    String months[] = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};


    private int stringMonth (String month){
        int index = 0;

        for (int i=0; i < months.length; i++){
            if (months[i].equals(month))
                return i+1  ;
        }

        return index;
    }


    private String theMonth(int month){
        return months[month];
    }



    class LinearTouchListener implements View.OnTouchListener {
        LinearTouchListener() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            v.performClick();
            return CustomCalendarActivity.this.gestureDetector.onTouchEvent(event);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.gestureDetector != null) {
            this.gestureDetector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     *
     * @param month
     * @param year
     */
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getApplicationContext(),
                month, year);


        _calendar.set(year, month - 1,1 );
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTemplate, Locale.ENGLISH);

        try {
            Calendar c = Calendar.getInstance();
            MyanmarDate firstDate = MyanmarDateConverter.convert(year, month, 1);
            MyanmarDate engDate = MyanmarDateConverter.convert(year, month,  _calendar.get(Calendar.DAY_OF_MONTH));
            MyanmarDate endDate = MyanmarDateConverter.convert(year, month, c.getActualMaximum(Calendar.DAY_OF_MONTH));


            String text =  firstDate.getYear()  + " ခု " +  firstDate.getMonthName() + "/" + endDate.getMonthName();
            todayMyan.setText(simpleDateFormat.format(_calendar.getTime()) + "\n" +  text );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "setGridCellAdapterToDate: " + e.getMessage());
        }


        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }





    // Inner Class
    public class GridCellAdapter extends BaseAdapter  {
        private static final String tag = "GridCellAdapter";
        private final Context _context;

        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] weekdays = new String[] { "တနၤ၈ေႏြ", "တနလၤာ", "အဂါ",
                "ဗုဒဟူး", "ၾကာသပေတး", "ေသာၾကာ", "စေန" };
        private final String[] months = { "January", "February", "March",
                "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
                31, 30, 31 };
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;


        // Days in Current Month
        public GridCellAdapter(Context context,
                               int month, int year) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

            list.add("Sun");
            list.add("Mon");
            list.add("Tue");
            list.add("Wed");
            list.add("Thu");
            list.add("Fri");
            list.add("Sat");


            printMonth(month, year);


        }

        private String getMonthAsString(int i) {
            return months[i];
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * Prints Month
         *
         * @param mm
         * @param yy
         */
        private void printMonth(int mm, int yy) {

            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            int currentMonth = mm - 1;
            String currentMonthName = getMonthAsString(currentMonth);
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);


            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);

            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;


            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2)
                    ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

            // Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
                list.add(String
                        .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                                + i)
                        + "-GREY"
                        + "-"
                        + getMonthAsString(prevMonth)
                        + "-"
                        + prevYear);
            }

            // Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {
                if (i == getCurrentDayOfMonth()) {

                    list.add(String.valueOf(i) + "-BLUE" + "-"
                            + getMonthAsString(currentMonth) + "-" + yy);

                } else {
                    list.add(String.valueOf(i) + "-WHITE" + "-"
                            + getMonthAsString(currentMonth) + "-" + yy);
                }
            }

            // Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                list.add(String.valueOf(i + 1) + "-GREY" + "-"
                        + getMonthAsString(nextMonth) + "-" + nextYear);
            }



        }

        /**
         * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
         * ALL entries from a SQLite database for that month. Iterate over the
         * List of All entries, and get the dateCreated, which is converted into
         * day.
         *
         * @param year
         * @param month
         * @return
         */
        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                    int month) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();

            return map;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false);
            }

            LinearLayout linear  = row.findViewById(R.id.card_view);
            LinearLayout  cardView = row.findViewById(R.id.liear);
            CustomTextView dayTextView = row.findViewById(R.id.calendarDay);
            CustomTextView engTextView = row.findViewById(R.id.calendarEnglish);
            CustomTextView myanTextView = row.findViewById(R.id.calendarMyanmar);
            CustomTextView calendarYatyarzar = row.findViewById(R.id.calendarYatyarzar);
            RatingBar rating = row.findViewById(R.id.rating);


            if (position > 6){

                Display display = getWindowManager().getDefaultDisplay();


                cardView.getLayoutParams().width = display.getWidth() / 7;
                myanTextView.getLayoutParams().width = display.getWidth() / 7;
                engTextView.getLayoutParams().width = display.getWidth() / 7;
                dayTextView.getLayoutParams().width = display.getWidth() / 7;

                cardView.getLayoutParams().height =  (int) (cardView.getLayoutParams().width * 1.75);




                String[] day_color = list.get(position).split("-");
                String theday = day_color[0];
                String themonth = day_color[2];
                String theyear = day_color[3];

                int month = 0;

                if (themonth.equals("January"))
                    month = 1;
                else if (themonth.equals("February"))
                    month = 2;

                else if (themonth.equals("March"))
                    month = 3;

                else if (themonth.equals("April"))
                    month = 4;

                else if (themonth.equals("May"))
                    month = 5;

                else if (themonth.equals("June"))
                    month = 6;

                else if (themonth.equals("July"))
                    month = 7;

                else if (themonth.equals("August"))
                    month = 8;

                else if (themonth.equals("September"))
                    month = 9;

                else if (themonth.equals("October"))
                    month = 10;

                else if (themonth.equals("November"))
                    month = 11;

                else if (themonth.equals("December"))
                    month = 12;



                MyanmarDate myanmarDate = MyanmarDateConverter.convert(Integer.parseInt(theyear), month, Integer.parseInt(theday));
                Astro astro = AstroConverter.convert(myanmarDate);

                engTextView.setText(theday);
                cardView.setTag(theday + "-" + themonth + "-" + theyear);






                if (day_color[1].equals("GREY")) {
                    myanTextView.setTextColor(getResources()
                            .getColor(R.color.background));
                    engTextView.setTextColor(getResources()
                            .getColor(R.color.background));

                    calendarYatyarzar.setTextColor(getResources()
                            .getColor(R.color.background));

                    cardView.setBackgroundColor(getResources().getColor(R.color.white));
                    engTextView.setBackgroundColor(getResources().getColor(R.color.white));
                    myanTextView.setBackgroundColor(getResources().getColor(R.color.white));


                    if (astro.getYatyaza() == 1 || astro.getPyathada() == 1){

                        if (astro.getPyathada() != 2 && astro.getYatyaza() != 2){
                            calendarYatyarzar.setText(astro.getAstroligicalDay());
                            calendarYatyarzar.setVisibility(View.VISIBLE);
                        }
                        else{
                            calendarYatyarzar.setVisibility(View.GONE);
                        }


                    } else{

                        if (prefs.getIntPreferences(SP_CHANGE_UI) == 1){
                            calendarYatyarzar.setText("");
                            calendarYatyarzar.setVisibility(View.VISIBLE);
                        }
                        else
                            calendarYatyarzar.setVisibility(View.GONE);
                    }

                    if (prefs.getIntPreferences(SP_CHANGE_UI) == 0){
                        rating.setVisibility(View.GONE);

                        if (myanmarDate.getMoonPhraseInt() == 1){
                            myanTextView.setText(myanmarDate.getMonthName() +  "\n" + fullmoon );



                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));

                        }else if (myanmarDate.getMoonPhraseInt() == 3){
                            myanTextView.setText(myanmarDate.getMonthName() + "\n" + newmoon );
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));


                        }else{
                            myanTextView.setText(myanmarDate.getFortnightDay() );
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text));
                        }

                        rating.setVisibility(View.GONE);


                    }

                    else if (prefs.getIntPreferences(SP_CHANGE_UI) == 1){
                        myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));
                        myanTextView.setText(astro.getMahabote() + yourDay);

                        rating.setVisibility(View.GONE);
                    }


                }

                if (day_color[1].equals("WHITE")) {

                    myanTextView.setTextColor(getResources().getColor(
                            R.color.black));
                    engTextView.setTextColor(getResources().getColor(
                            R.color.black));

                    calendarYatyarzar.setTextColor(getResources()
                            .getColor(R.color.black));
                    cardView.setBackgroundColor(getResources().getColor(R.color.white));
                    engTextView.setBackgroundColor(getResources().getColor(R.color.white));
                    myanTextView.setBackgroundColor(getResources().getColor(R.color.white));

                    if (prefs.getIntPreferences(SP_CHANGE_UI) == 0){
                        rating.setVisibility(View.GONE);
                        if (myanmarDate.getMoonPhraseInt() == 1){
                            myanTextView.setBackground(getResources().getDrawable(R.mipmap.ic_fullmoon));
                            // myanTextView.setText(  "ျပည့္");
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));
                            myanTextView.setText(myanmarDate.getMonthName() + "\n" + fullmoon );

                        }else if (myanmarDate.getMoonPhraseInt() == 3){
                            myanTextView.setBackground(getResources().getDrawable(R.mipmap.ic_newmoon));
                            myanTextView.setText(myanmarDate.getMonthName() +  "\n" + newmoon );

                            myanTextView.setTextColor(getResources().getColor(
                                    R.color.white));
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));
                        }else{
                            myanTextView.setBackgroundColor(getResources().getColor(R.color.white));
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text));
                            myanTextView.setText(myanmarDate.getFortnightDay() );
                        }

                    }
                    else if (prefs.getIntPreferences(SP_CHANGE_UI) == 1){
                        myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));
                        myanTextView.setText(astro.getMahabote()+ yourDay);



                        if (month == theMONTH && Integer.parseInt(theyear) == theYEAR){




                            for (int i=0; i < lists.size() ; i++) {
                                LuckyDay luckyday = lists.get(i);

                                if(luckyday.getDay_info() != null && luckyday.getDay_info().trim().length() > 0) {

                                    try {
                                        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                                        Date date = dateFormat.parse(luckyday.getDay_info());
                                        java.text.DateFormat formatter = new SimpleDateFormat("d");
                                        String dateStr = formatter.format(date);




                                        if (dateStr.equals(theday)){
                                            if(luckyday.getLuck_group() != 0 ) {
                                                rating.setRating((float) luckyday.getLuck_group());
                                                rating.setVisibility(View.VISIBLE);
                                            }else{
                                                rating.setVisibility(View.GONE);
                                            }
                                        }


                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                        Log.e(TAG, "getView: "  + e1.getMessage() );

                                    }
                                }else{
                                    rating.setVisibility(View.GONE);
                                }

                            }


                        }else{
                            rating.setVisibility(View.GONE);
                        }

                    }






                    if (myanmarDate.getWeekDayInt() == 1|| myanmarDate.getWeekDayInt() == 0 ){
                        engTextView.setBackgroundColor(getResources().getColor(R.color.white));
                        engTextView.setTextColor(getResources().getColor(R.color.coloredInactive));
                        myanTextView.setTextColor(getResources().getColor(R.color.coloredInactive));
                        calendarYatyarzar.setTextColor(getResources().getColor(R.color.coloredInactive));
                    }


                    if (HolidayCalculator.getHoliday(myanmarDate).size() > 0 ){
                        engTextView.setBackgroundColor(getResources().getColor(R.color.coloredInactive));
                        engTextView.setTextColor(getResources().getColor(R.color.white));
                    }


                    if (astro.getYatyaza() == 1 || astro.getPyathada() == 1){
                        if (astro.getPyathada() != 2 && astro.getYatyaza() != 2){
                            calendarYatyarzar.setText(astro.getAstroligicalDay());
                            calendarYatyarzar.setVisibility(View.VISIBLE);
                        }
                        else{
                            calendarYatyarzar.setVisibility(View.GONE);
                        }
                    } else{

                        if (prefs.getIntPreferences(SP_CHANGE_UI) == 1){
                            calendarYatyarzar.setText("");
                            calendarYatyarzar.setVisibility(View.VISIBLE);
                        }
                        else
                            calendarYatyarzar.setVisibility(View.GONE);
                    }






                }



                if (day_color[1].equals("BLUE")) {

                    if (month == checkMonth && year == checkYear){
                        engTextView.setTextColor(getResources().getColor(R.color.white));
                        myanTextView.setTextColor(getResources().getColor(R.color.white));
                        calendarYatyarzar.setTextColor(getResources().getColor(R.color.white));

                        cardView.setBackgroundColor(getResources().getColor(R.color.text_comment));
                        engTextView.setBackgroundColor(getResources().getColor(R.color.text_comment));
                        myanTextView.setBackgroundColor(getResources().getColor(R.color.text_comment));
                        calendarYatyarzar.setBackgroundColor(getResources().getColor(R.color.text_comment));
                        rating.setBackgroundColor(getResources().getColor(R.color.text_comment));
                    }

                    else{
                        myanTextView.setTextColor(getResources().getColor(
                                R.color.black));
                        engTextView.setTextColor(getResources().getColor(
                                R.color.black));

                        calendarYatyarzar.setTextColor(getResources()
                                .getColor(R.color.black));
                        cardView.setBackgroundColor(getResources().getColor(R.color.white));
                        engTextView.setBackgroundColor(getResources().getColor(R.color.white));
                        myanTextView.setBackgroundColor(getResources().getColor(R.color.white));
                        calendarYatyarzar.setBackgroundColor(getResources().getColor(R.color.white));
                        rating.setBackgroundColor(getResources().getColor(R.color.white));

                    }

                    if (prefs.getIntPreferences(SP_CHANGE_UI) == 0){

                        if (myanmarDate.getMoonPhraseInt() == 1){
                            myanTextView.setBackground(getResources().getDrawable(R.mipmap.ic_fullmoon));
                            myanTextView.setText(myanmarDate.getMonthName() + "\nလျပည့္");
                            myanTextView.setBackground(getResources().getDrawable(R.mipmap.ic_fullmoon));
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));
                            myanTextView.setText(myanmarDate.getMonthName());
                        }else if (myanmarDate.getMoonPhraseInt() == 3){
                            myanTextView.setBackground(getResources().getDrawable(R.mipmap.ic_newmoon));
                            myanTextView.setText(myanmarDate.getMonthName() + "\nလကြယ္" );
                            myanTextView.setTextColor(getResources().getColor(
                                    R.color.white));
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));
                        }
                        else{
                            myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text));
                            myanTextView.setText(myanmarDate.getFortnightDay() );
                        }

                        rating.setVisibility(View.GONE);

                    }
                    else if (prefs.getIntPreferences(SP_CHANGE_UI) == 1){
                        myanTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.s_small_text));
                        myanTextView.setText(astro.getMahabote()+ "ဖြား");

                        if (month == theMONTH && Integer.parseInt(theyear) == theYEAR){

                            for (int i=0; i < lists.size() ; i++) {
                                LuckyDay luckyday = lists.get(i);
                                if(luckyday.getDay_info() != null && luckyday.getDay_info().trim().length() > 0) {
                                    try {
                                        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                                        Date date = dateFormat.parse(luckyday.getDay_info());
                                        java.text.DateFormat formatter = new SimpleDateFormat("d");
                                        String dateStr = formatter.format(date);



                                        if (dateStr.equals(theday)){
                                            if(luckyday.getLuck_group() != 0 ) {
                                                rating.setRating((float) luckyday.getLuck_group());
                                                rating.setVisibility(View.VISIBLE);
                                            }else{
                                                rating.setVisibility(View.GONE);
                                            }
                                        }


                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                        Log.e(TAG, "getView: "  + e1.getMessage() );
                                    }
                                }else{
                                    rating.setVisibility(View.GONE);
                                }

                            }


                        }else{
                            rating.setVisibility(View.GONE);
                        }

                    }



                    if (myanmarDate.getWeekDayInt() == 1|| myanmarDate.getWeekDayInt() == 0 ){
                        engTextView.setBackgroundColor(getResources().getColor(R.color.white));
                        engTextView.setTextColor(getResources().getColor(R.color.coloredInactive));
                        myanTextView.setTextColor(getResources().getColor(R.color.coloredInactive));
                        calendarYatyarzar.setTextColor(getResources().getColor(R.color.coloredInactive));
                    }



                    if (HolidayCalculator.getHoliday(myanmarDate).size() > 0 ){
                        engTextView.setBackgroundColor(getResources().getColor(R.color.coloredInactive));
                        engTextView.setTextColor(getResources().getColor(R.color.white));
                    }

                    if (astro.getYatyaza() == 1 || astro.getPyathada() == 1){
                        if (astro.getPyathada() != 2 && astro.getYatyaza() != 2){
                            calendarYatyarzar.setText(astro.getAstroligicalDay());
                            calendarYatyarzar.setVisibility(View.VISIBLE);
                        }
                        else{
                            calendarYatyarzar.setVisibility(View.GONE);
                        }


                    }

                    else{

                        if (prefs.getIntPreferences(SP_CHANGE_UI) == 1){
                            calendarYatyarzar.setText("");
                            calendarYatyarzar.setVisibility(View.VISIBLE);
                        }
                        else
                            calendarYatyarzar.setVisibility(View.GONE);
                    }


                }


                cardView.setEnabled(true);
                myanTextView.setVisibility(View.VISIBLE);
                engTextView.setVisibility(View.VISIBLE);
                dayTextView.setVisibility(View.GONE);

            }
            else{
                cardView.setBackgroundColor(getResources().getColor(R.color.background));

                Display display = getWindowManager().getDefaultDisplay();
                cardView.getLayoutParams().width = display.getWidth() / 7;
                dayTextView.getLayoutParams().width = display.getWidth() / 7;

                cardView.getLayoutParams().height =  (int) (cardView.getLayoutParams().width *  0.4);

                dayTextView.setText(list.get(position));
                dayTextView.setTypeface(dayTextView.getTypeface(), Typeface.BOLD);
                myanTextView.setVisibility(View.GONE);
                engTextView.setVisibility(View.GONE);
                dayTextView.setVisibility(View.VISIBLE);
                calendarYatyarzar.setVisibility(View.GONE);
                rating.setVisibility(View.GONE);

                cardView.setEnabled(false);
            }

            cardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("type", "view_calendar");
                        mix.put("click", "day calendar");
                        FlurryAgent.logEvent("Click Day Calendar", mix);

                    } catch (Exception e) {
                    }

                    String date_month_year = (String) v.getTag();
                    Intent intent = new Intent(CustomCalendarActivity.this, CalendarDayActivity.class);
                    intent.putExtra("date", date_month_year);
                    startActivity(intent);


                }
            });




            return row;
        }


        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }
    }

    class DayViewGestureDetector extends GestureDetector.SimpleOnGestureListener {

        public DayViewGestureDetector() {

        }

        void onRightToLeftSwipe() {

            if (month > 11) {
                month = 1;
                year++;
            } else {
                month++;
            }

            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "view_calendar");
                mix.put("click", "previous month calendar");
                FlurryAgent.logEvent("Click Calendar Event", mix);
            } catch (Exception e) {
            }


            prefs.saveIntPerferences(SP_CHANGE_UI, 0);
            txtChangUi.setText("ေဟာစာတန္း");
            getDaysList(month, year);
            setGridCellAdapterToDate(month, year);


        }

        void onLeftToRightSwipe() {

            if (month <= 1) {
                month = 12;
                year--;
            } else {
                month--;
            }

            try {


                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", "view_calendar");
                mix.put("click", "next month calendar");
                FlurryAgent.logEvent("Click Calendar Event", mix);

            } catch (Exception e) {
            }

            prefs.saveIntPerferences(SP_CHANGE_UI, 0);
            txtChangUi.setText("ေဟာစာတန္း");

            getDaysList(month, year);

            setGridCellAdapterToDate(month, year);
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) <= 250.0f) {
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

    private void getDaysList(final int month1, final int year1) {

        lists.clear();

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(String.format(constantFortuneAllData, year1, month1),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(response.length() > 0) {
                            try {


                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<LuckyDay>>() {
                                }.getType();


                                List<LuckyDay> luckyDayList = mGson.fromJson(response.toString(), listType);
                                lists = luckyDayList;



                                theMONTH = month1;
                                theYEAR = year1;








                            }catch (Exception e){
                                theMONTH = 0;
                                theYEAR = 0;
                                lists.clear();
                                Log.e(TAG, "onResponse: " + e.getMessage() );
                            }
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                theMONTH = 0;
                theYEAR = 0;
                lists.clear();
                Log.e(TAG, "onErrorResponse: " +error.getMessage() );

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
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
            Intent intent = new Intent(CustomCalendarActivity.this, CallAdsInterstitial.class);
            startActivity(intent);
            finish();
        }{
            finish();
        }
    }
}


