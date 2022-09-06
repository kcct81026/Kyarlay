package com.kyarlay.ayesunaing.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.DueDateActivity;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.ProfileActivity;
import com.kyarlay.ayesunaing.activity.ReadingWishlistActivity;
import com.kyarlay.ayesunaing.activity.UserPostUploadActivity;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.object.ChildGrowthObj;
import com.kyarlay.ayesunaing.object.KyarlayAds;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.ToolChildObject;
import com.kyarlay.ayesunaing.object.ToolGrid;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MediaAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FragmentMedia extends Fragment implements Constant, ConstantVariable {

    private static final String TAG = "FragmentMedia";

    private MyPreference prefs;
    private AppCompatActivity activity;
    private Resources resources;
    private MediaAdapter mediaAdapter;
    private ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    private ArrayList<UniversalPost> tempList = new ArrayList<>();
    private Boolean loading=true;
    int baby_month = 0, baby_year = 0, baby_day = 0;
    DatabaseAdapter databaseAdapter;
    Display display ;

    CustomTextView title;
    LinearLayout  title_layout;
    RelativeLayout like_layout, noti_layout, profile_layout;
    CircularTextView noti_circle;
    CustomTextView group_chat;
    CircularNetworkImageView profileIcon;
    RecyclerView recyclerView ;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.category_detail, container, false);

        activity = (AppCompatActivity) getActivity();
        prefs = new MyPreference(activity);
        new MyFlurry(getActivity());
        Context context = LocaleHelper.setLocale(getActivity(), prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();
        databaseAdapter     = new DatabaseAdapter(activity);
        display         = activity.getWindowManager().getDefaultDisplay();



        display         = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity);
        recyclerView        = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar         = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        like_layout         = (RelativeLayout) rootView.findViewById(R.id.like_layout);
        title_layout        = (LinearLayout) rootView.findViewById(R.id.title_layout);
        noti_layout         = (RelativeLayout) rootView.findViewById(R.id.noti_layout);
        noti_circle         = (CircularTextView) rootView.findViewById(R.id.noti_count);
        group_chat          = (CustomTextView) rootView.findViewById(R.id.action_button);

        profile_layout    = (RelativeLayout) rootView.findViewById(R.id.profile_layout);
        profileIcon    = (CircularNetworkImageView) rootView.findViewById(R.id.profileIcon);
        Log.e(TAG, "onCreateView: "  );

        group_chat.setText(resources.getString(R.string.wirte_post));

        group_chat.setVisibility(View.GONE);


        title_layout.getLayoutParams().width    = (display.getWidth() * 11 ) / 20;
        like_layout.getLayoutParams().width    = (display.getWidth() * 3 ) / 20;
        noti_layout.getLayoutParams().width     = (display.getWidth() * 3 ) / 20;
        profile_layout.getLayoutParams().width     = (display.getWidth() * 3 ) / 20;


        mediaAdapter = new MediaAdapter(FragmentMedia.this,  (AppCompatActivity) getActivity(), mainCatDetails);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mediaAdapter);



        prefs.saveBooleanPreference(SP_DIALOG_CLICK, false);
        prefs.saveBooleanPreference(SP_DUE_DATE, false);
        prefs.saveBooleanPreference(SP_CLICK_OTHER, false);
        prefs.saveBooleanPreference(SP_OVAL_DATE, false);
        prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, SP_DEFAULT);

        group_chat.setText(resources.getString(R.string.wirte_post));
        group_chat.setVisibility(View.GONE);


        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(activity, ProfileActivity.class);
                startActivity(intent);
            }
        });


        noti_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                        prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0) {
                    try {

                        FlurryAgent.logEvent("Click Notification");
                    } catch (Exception e) {
                    }


                    Intent intent   = new Intent(activity, NotificationAcitivity.class);
                    intent.putExtra("post_type","");
                    activity.startActivity(intent);
                }else{
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });

        like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                        prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0) {
                    try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "post_list");
                        FlurryAgent.logEvent("Click Liked Posts Icon", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(activity, ReadingWishlistActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });

        if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

            if (prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("null")){
                profileIcon.setDefaultImageResId(R.drawable.ic_launcher);
            }
            else if (!prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals(""))
                profileIcon.setImageUrl(prefs.getStringPreferences(SP_USER_PROFILEIMAGE), AppController.getInstance().getImageLoader());
            else
                profileIcon.setDefaultImageResId(R.drawable.ic_launcher);
        }else{
            profileIcon.setDefaultImageResId(R.drawable.member);
        }

        title            = (CustomTextView) rootView.findViewById(R.id.title);
        title.setText(resources.getString(R.string.knowledge)+"");

        mediaCheck();

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean scrollUp = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if(newState == RecyclerView.SCROLL_STATE_IDLE && scrollUp) {

                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();

                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;
                    callAds();
                }

            }
        });


        group_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getStringPreferences(SP_USER_TOKEN) == null
                        || prefs.getStringPreferences(SP_USER_TOKEN).trim().length() == 0){

                    prefs.saveIntPerferences(SP_READING_LOGIN, 1);
                    Intent intent   = new Intent(activity, ActivityLogin.class);
                    activity.startActivity(intent);

                }
                else {
                    prefs.saveIntPerferences(SP_CHANGE, 1);
                    Intent intent = new Intent(activity, UserPostUploadActivity.class);
                    activity.startActivity(intent);
                    //activity.overridePendingTransition(R.anim.slide_up, R.anim.slide_bottom);
                }

            }
        });

        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();


        if (prefs.getBooleanPreference(SP_OVAL_DATE)){

            //loading = true;


            for (int j=0; j < mainCatDetails.size(); j++){

                if (mainCatDetails.get(j).getPostType().equals(CHILD_GROWTH)){
                    mainCatDetails.remove(j);
                }

            }

            for (int j=0; j < mainCatDetails.size(); j++){


                if (mainCatDetails.get(j).getPostType().equals(BABY_INFO)){
                    mainCatDetails.remove(j);
                }

            }


            for (int j=0; j < mainCatDetails.size(); j++){

                if (mainCatDetails.get(j).getPostType().equals(SINGLE_HEALTH)){
                    mainCatDetails.remove(j);
                }

            }




            UniversalPost universalPost1 = new UniversalPost();
            universalPost1.setPostType(SINGLE_HEALTH);
            mainCatDetails.add(1,universalPost1);
            mediaAdapter.notifyDataSetChanged();

      /*      Reading pro2 = new Reading();
            pro2.setPostType(CART_DETAIL_FOOTER);
            mainCatDetails.add(pro2);
            mediaAdapter.notifyItemInserted(mainCatDetails.size());

            mainPageItem();
*/
        }

        if (prefs.getBooleanPreference(SP_DUE_DATE)){

            prefs.saveBooleanPreference(SP_DUE_DATE, false);

            for (int j=0; j < mainCatDetails.size(); j++){

                if (mainCatDetails.get(j).getPostType().equals(USER_STATUS)){
                    mainCatDetails.remove(j);
                }

                if (mainCatDetails.get(j).getPostType().equals(SINGLE_HEALTH)){
                    mainCatDetails.remove(j);
                }

                if (mainCatDetails.get(j).getPostType().equals(CHILD_GROWTH)){
                    mainCatDetails.remove(j);
                }
            }

            UniversalPost universalPost = new UniversalPost();
            universalPost.setPostType(USER_STATUS);
            mainCatDetails.add(0,universalPost);

            mediaAdapter.notifyDataSetChanged();

            changeBabyInfo();
        }

    }



    private void mediaCheck(){
        mainCatDetails.clear();
        loading = true;
        prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, SP_DEFAULT);
        prefs.saveIntPerferences(TEST,  SP_DEFAULT);



        if (!prefs.getBooleanPreference(SP_USER_STATUS)){
            progressBar.setVisibility(View.GONE);

            sendDialogStatus();
            pregOrParentMethod();
        }
        else{

            UniversalPost universalPost = new UniversalPost();
            universalPost.setPostType(USER_STATUS);
            mainCatDetails.add(universalPost);
            mediaAdapter.notifyDataSetChanged();



            if (prefs.getIntPreferences(SP_KID_YEAR) == 0 && prefs.getIntPreferences(SP_KID_MONTH) == 0 ){
                prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_SINGLE);
            }

            if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_SINGLE)){

                UniversalPost universalPost1 = new UniversalPost();
                universalPost1.setPostType(SINGLE_HEALTH);
                mainCatDetails.add(universalPost1);


                Reading pro2 = new Reading();
                pro2.setPostType(CART_DETAIL_FOOTER);
                mainCatDetails.add(pro2);
                mediaAdapter.notifyItemInserted(mainCatDetails.size());

                mainPageItem();



            }else{


                Reading pro12 = new Reading();
                pro12.setPostType(CART_DETAIL_FOOTER);
                mainCatDetails.add(pro12);
                mediaAdapter.notifyItemInserted(mainCatDetails.size());


                progressBar.setVisibility(View.GONE);

                if (prefs.getIntPreferences(SP_KID_TYPE) == 1)
                    prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PARENT);
                else
                    prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PREGNANCY);

                if (prefs.getIntPreferences(SP_KID_YEAR) == 0 && prefs.getIntPreferences(SP_KID_MONTH) == 0 ) {
                    sendFriendInvite();


                    Reading reading = new Reading();
                    reading.setPostType(BABY_INFO);
                    mainCatDetails.add(reading);
                }
                else{

                    pregOrParentMethod();
                }
            }
        }
    }

    public void sendFriendInvite(){

        prefs.saveBooleanPreference(FIRST_CALCULATION, true);

        final Date[] currentDate = new Date[2];

        SimpleDateFormat formatter =  new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_child_information);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        CustomTextView titleForAll = dialog.findViewById(R.id.titleForAll);



        final LinearLayout linearCalendar = dialog.findViewById(R.id.linearCalendar);
        final CustomTextView kid_birth_date = dialog.findViewById(R.id.txt_bd);
        final CustomTextView txt_bd_title = dialog.findViewById(R.id.txt_bd_title);
        final CustomButton btn_login = dialog.findViewById(R.id.btn_login);
        final CustomTextView  txt_due_date = dialog.findViewById(R.id.txt_due_date);


        txt_due_date.setText(resources.getString(R.string.due_date_button));
        txt_due_date.setTypeface( txt_due_date.getTypeface(), Typeface.BOLD);
        txt_due_date.setPaintFlags(txt_due_date.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        baby_month  = prefs.getIntPreferences(SP_KID_MONTH);
        baby_year   = prefs.getIntPreferences(SP_KID_YEAR);
        baby_day   = prefs.getIntPreferences(SP_KID_DAY);

        if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PARENT)){
            txt_due_date.setVisibility(View.GONE);
            titleForAll.setText(resources.getString(R.string.baby_birth_age));
            txt_bd_title.setText(resources.getString(R.string.child_bd));
            btn_login.setText(resources.getString(R.string.edit_baby_birth_info));
        }else if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PREGNANCY)){
            titleForAll.setText(resources.getString(R.string.baby_birth_due_date));
            txt_bd_title.setText(resources.getString(R.string.child_bd_due_date));
            btn_login.setText(resources.getString(R.string.edit_due_date_info));
            txt_due_date.setVisibility(View.VISIBLE);
        }

        if (baby_year != 0 && baby_month != 0 && baby_day!= 0 ){
            kid_birth_date.setText(baby_day + "/" + baby_month+"/"+baby_year);
        }
        else{
            kid_birth_date.setText(resources.getString(R.string.choose_month_hint));
        }

        txt_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();



                Intent intent = new Intent(activity, DueDateActivity.class);
                intent.putExtra("due_date", "due_date");
                startActivity(intent);
            }
        });

        kid_birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final Calendar c = Calendar.getInstance(Locale.US);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);



                if (baby_year != 0 && baby_month != 0 && baby_day!= 0 ){
                    mYear = baby_year;
                    mMonth = baby_month - 1 ;
                    mDay = baby_day;
                }


                currentDate[0] = c.getTime();
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                kid_birth_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+"");

                                baby_month = (monthOfYear+1);
                                baby_year = year;
                                baby_day = dayOfMonth;


                            }
                        }, mYear, mMonth, mDay);

                if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PARENT)){

                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                }
                else{
                    datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                }


                datePickerDialog.show();

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baby_month == 0 || baby_year == 0){

                    ToastHelper.showToast( activity, resources.getString(R.string.kid_age_error));

                }
                else {

                    final Calendar c = Calendar.getInstance(Locale.US);
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
                    String  dateAfterString= mDay + " " + (mMonth + 1)  + " " + mYear;

                    int type = 0;

                    String dateBeforeString  = baby_day + " " + baby_month + " " + baby_year;




                    try {
                        Date dateBefore = myFormat.parse(dateBeforeString);
                        Date dateAfter = myFormat.parse(dateAfterString);
                        long difference = dateAfter.getTime() - dateBefore.getTime();
                        long diff = dateBefore.getTime() - dateAfter.getTime();
                        float daysBetween = (difference / (1000 * 60 * 60 * 24));

                        int weeks = 0;

                        if (daysBetween > 0) {
                            weeks = (int) (daysBetween / 7);
                            type = 1;
                        } else if (daysBetween == 0) {
                            type = 1;
                        } else {
                            if (daysBetween < 0) {
                                dateBefore = myFormat.parse(dateAfterString);
                                dateAfter = myFormat.parse(dateBeforeString);
                                difference = dateAfter.getTime() - dateBefore.getTime();
                                daysBetween = (difference / (1000 * 60 * 60 * 24));


                                weeks = (int) (daysBetween / 7);
                                weeks = 39 - weeks;

                                type = 0;

                                if (weeks < 0) {
                                    weeks = -1;

                                }
                            }
                        }



                        if (weeks == -1) {

                            ToastHelper.showToast( activity, resources.getString(R.string.kid_age_error));

                        } else if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PREGNANCY) && diff < 0){


                            ToastHelper.showToast( activity, resources.getString(R.string.kid_due_date_error));


                        }
                        else if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PARENT) && diff > 0 ){

                            ToastHelper.showToast( activity, resources.getString(R.string.kid_age_error));


                        }
                        else{
                            dialog.dismiss();
                            prefs.saveIntPerferences(SP_KID_MONTH, baby_month);
                            prefs.saveIntPerferences(SP_KID_YEAR, baby_year);
                            prefs.saveIntPerferences(SP_KID_DAY, baby_day);
                            prefs.saveIntPerferences(SP_KID_WEEK, weeks);
                            prefs.saveIntPerferences(SP_KID_TYPE, type);
                            prefs.saveBooleanPreference(SP_USER_STATUS, true);

                            if (prefs.getIntPreferences(SP_KID_TYPE) == 1)
                                prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PARENT);
                            else
                                prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PREGNANCY);


                            for (int j=0; j < mainCatDetails.size(); j++){

                                if (mainCatDetails.get(j).getPostType().equals(BABY_INFO)){
                                    mainCatDetails.remove(j);
                                }

                                if (mainCatDetails.get(j).getPostType().equals(CHILD_GROWTH)){
                                    mainCatDetails.remove(j);
                                }

                                if (mainCatDetails.get(j).getPostType().equals(SINGLE_HEALTH)){
                                    mainCatDetails.remove(j);
                                }
                            }

                            if (prefs.getBooleanPreference(SP_DIALOG_CLICK)){

                                prefs.saveBooleanPreference(SP_DIALOG_CLICK, false);
                                getChangeBabyInformation();

                            }
                            else{
                                UniversalPost universalPost1 = new UniversalPost();
                                universalPost1.setPostType(USER_STATUS);
                                mainCatDetails.add(0,universalPost1);

                                prefs.saveIntPerferences(TEST,  SP_DEFAULT);

                                prefs.saveBooleanPreference(SP_USER_STATUS, true);
                                changeBabyInfo();

                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "calculateDate: " + e.getMessage() );
                    }

                }
            }
        });

        dialog.show();


    }

    public void sendDialogStatus(){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_user_status);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        CustomTextView statusTitle = dialog.findViewById(R.id.statusTitle);
        CustomTextView next = dialog.findViewById(R.id.next);
        LinearLayout linearSingle = dialog.findViewById(R.id.linearSingle);
        LinearLayout linearPregnancy = dialog.findViewById(R.id.linearPregnancy);
        LinearLayout linearParent = dialog.findViewById(R.id.linearParent);
        CustomTextView txtSingle = dialog.findViewById(R.id.txtSingle);
        CustomTextView txtPregnancy = dialog.findViewById(R.id.txtPregnancy);
        CustomTextView txtParent = dialog.findViewById(R.id.txtParent);
        final RadioButton rdParent = dialog.findViewById(R.id.rdParent);
        final RadioButton rdPregnancy = dialog.findViewById(R.id.rdPregnancy);
        final RadioButton rdSingle = dialog.findViewById(R.id.rdSingle);
        TextView txtclose = dialog.findViewById(R.id.txtclose);


        statusTitle.setText(resources.getString(R.string.status_title));
        txtParent.setText(resources.getString(R.string.parent));
        txtSingle.setText(resources.getString(R.string.single));
        txtPregnancy.setText(resources.getString(R.string.pregnancy));
        next.setText(resources.getString(R.string.choose));


        if (prefs.getBooleanPreference(SP_USER_STATUS)){
            if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PARENT)) {
                rdParent.setChecked(true);
                rdPregnancy.setChecked(false);
                rdSingle.setChecked(false);

            } else if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_PREGNANCY)) {
                rdParent.setChecked(false);
                rdPregnancy.setChecked(true);
                rdSingle.setChecked(false);
            } else if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_SINGLE)) {
                rdParent.setChecked(false);
                rdPregnancy.setChecked(false);
                rdSingle.setChecked(true);
            }

        }

        linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdParent.setChecked(true);
                rdPregnancy.setChecked(false);
                rdSingle.setChecked(false);
            }
        });

        linearPregnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdParent.setChecked(false);
                rdPregnancy.setChecked(true);
                rdSingle.setChecked(false);
            }
        });


        linearSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdParent.setChecked(false);
                rdPregnancy.setChecked(false);
                rdSingle.setChecked(true);
            }
        });

        rdParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdParent.setChecked(true);
                rdPregnancy.setChecked(false);
                rdSingle.setChecked(false);
            }
        });

        rdPregnancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdParent.setChecked(false);
                rdPregnancy.setChecked(true);
                rdSingle.setChecked(false);
            }
        });

        rdSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdParent.setChecked(false);
                rdPregnancy.setChecked(false);
                rdSingle.setChecked(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();




                if (rdParent.isChecked()) {
                    prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PARENT);


                }
                else if (rdPregnancy.isChecked()) {
                    prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_PREGNANCY);

                }
                else if (rdSingle.isChecked()) {
                    prefs.saveStringPreferences(SP_FILLUP_STATUS, STATUS_SINGLE);

                    prefs.saveIntPerferences(SP_KID_CHANGE_MONTH, 0);
                    prefs.saveIntPerferences(SP_KID_CHANGE_YEAR, 0);
                    prefs.saveIntPerferences(SP_KID_YEAR, 0);
                    prefs.saveIntPerferences(SP_KID_MONTH, 0);

                    prefs.saveIntPerferences(TEST,  SP_DEFAULT);

                    prefs.saveBooleanPreference(SP_USER_STATUS, true);



                }

                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("user_type", prefs.getStringPreferences(SP_FILLUP_STATUS) );

                    FlurryAgent.logEvent("User Type Change Event", mix);
                } catch (Exception e) {
                }


                if (prefs.getStringPreferences(SP_FILLUP_STATUS).equals(STATUS_SINGLE)){


                    if (!prefs.getBooleanPreference(SP_DIALOG_CLICK)){

                        UniversalPost universalPost1 = new UniversalPost();
                        universalPost1.setPostType(USER_STATUS);
                        mainCatDetails.add(0,universalPost1);
                    }



                    for (int j=0; j < mainCatDetails.size(); j++){



                        if (mainCatDetails.get(j).getPostType().equals(BABY_INFO)){
                            mainCatDetails.remove(j);
                        }

                    }

                    for (int j=0; j < mainCatDetails.size(); j++){

                        if (mainCatDetails.get(j).getPostType().equals(CHILD_GROWTH)){
                            mainCatDetails.remove(j);
                        }

                    }

                    for (int j=0; j < mainCatDetails.size(); j++){

                        if (mainCatDetails.get(j).getPostType().equals(SINGLE_HEALTH)){
                            mainCatDetails.remove(j);
                        }
                    }


                    UniversalPost universalPost1 = new UniversalPost();
                    universalPost1.setPostType(SINGLE_HEALTH);
                    mainCatDetails.add(1,universalPost1);

                    prefs.saveBooleanPreference(SP_DIALOG_CLICK, false);

                    mediaAdapter.notifyDataSetChanged();





                }else{


                    sendFriendInvite();

                }


            }
        });

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();




            }
        });
        dialog.show();


    }



    private void pregOrParentMethod(){
        if (prefs.getIntPreferences(SP_KID_YEAR) == 0 && prefs.getIntPreferences(SP_KID_MONTH) == 0 ) {

            prefs.saveIntPerferences(SP_KID_CHANGE_MONTH, 0);
            prefs.saveIntPerferences(SP_KID_CHANGE_YEAR, 0);
            prefs.saveIntPerferences(SP_KID_YEAR, 0);
            prefs.saveIntPerferences(SP_KID_MONTH, 0);

            if (prefs.getBooleanPreference(SP_USER_STATUS)) {


                Reading reading = new Reading();
                reading.setPostType(BABY_INFO);
                mainCatDetails.add(reading);

            }

            Reading pro2 = new Reading();
            pro2.setPostType(CART_DETAIL_FOOTER);
            mainCatDetails.add(pro2);
            mediaAdapter.notifyItemInserted(mainCatDetails.size());

           mainPageItem();
        }
        else{

            final Calendar c = Calendar.getInstance(Locale.US);
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
            String dateAfterString = mDay + " " + (mMonth + 1) + " " + mYear;

            String dateBeforeString = prefs.getIntPreferences(SP_KID_DAY) + " " + (prefs.getIntPreferences(SP_KID_MONTH)) + " " + prefs.getIntPreferences(SP_KID_YEAR);

            try {
                Date dateBefore = myFormat.parse(dateBeforeString);
                Date dateAfter = myFormat.parse(dateAfterString);
                long difference = dateAfter.getTime() - dateBefore.getTime();
                float daysBetween = (difference / (1000 * 60 * 60 * 24));

                int weeks = 0;



                if (daysBetween > 0) {
                    weeks = (int) (daysBetween / 7);
                    prefs.saveIntPerferences(SP_KID_TYPE,1);
                } else if (daysBetween == 0) {
                    prefs.saveIntPerferences(SP_KID_TYPE,1);
                } else {
                    if (daysBetween < 0) {
                        dateBefore = myFormat.parse(dateAfterString);
                        dateAfter = myFormat.parse(dateBeforeString);
                        difference = dateAfter.getTime() - dateBefore.getTime();
                        daysBetween = (difference / (1000 * 60 * 60 * 24));


                        weeks = (int) (daysBetween / 7);
                        weeks = 39 - weeks;

                        prefs.saveIntPerferences(SP_KID_TYPE,0);

                        if (weeks < 0) {
                            weeks = -1;
                        }
                    }
                }




                prefs.saveIntPerferences(SP_KID_WEEK,weeks);
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e.getMessage());
            }


            prefs.saveIntPerferences(SP_KID_CHANGE_DAY, prefs.getIntPreferences(SP_KID_DAY));
            prefs.saveIntPerferences(SP_KID_CHANGE_MONTH, prefs.getIntPreferences(SP_KID_MONTH));
            prefs.saveIntPerferences(SP_KID_CHANGE_WEEK, prefs.getIntPreferences(SP_KID_WEEK));
            prefs.saveIntPerferences(SP_KID_CHANGE_YEAR, prefs.getIntPreferences(SP_KID_YEAR));
            prefs.saveIntPerferences(SP_KID_CHANGE_TYPE, prefs.getIntPreferences(SP_KID_TYPE));


            getBabyInformation(true);









        }

    }


    private void mainPageItem()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantToolList  + prefs.getIntPreferences(SP_CURRENT_VERSION) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);

                        if (mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)) {
                            mainCatDetails.remove(mainCatDetails.size() - 1);
                        }



                        if(response.length() > 0) {
                            progressBar.setVisibility(View.GONE);




                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<ToolChildObject>>() {
                                }.getType();
                                List<ToolChildObject> lists = mGson.fromJson(response.toString(), listType);

                                if (lists.size() > 0){

                                    ToolGrid toolGrid = new ToolGrid();
                                    toolGrid.setPostType(CHOOSE_TOOL);
                                    toolGrid.setToolObjectArrayList(lists);
                                    mainCatDetails.add(toolGrid);
                                }

                                mediaAdapter.notifyItemInserted(mainCatDetails.size());



                                Product pro = new Product();
                                pro.setTitle(resources.getString(R.string.video_latest) + " " + resources.getString(R.string.video_news));
                                pro.setPostType(DISCOUNT_TITLE);
                                mainCatDetails.add(pro);
                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro2);
                                mediaAdapter.notifyItemInserted(mainCatDetails.size());
                                callAds();


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }


                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // loading = true;

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    private void getBabyInformation(final boolean firstTime){




        String type = "type=pregnancy";


        if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 1){
            type = "type=parent";

        }else  if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 0) {
            type = "type=pregnancy";
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,"https://www.kyarlay.com/api/weekly_highlights?period=" + prefs.getIntPreferences(SP_KID_CHANGE_WEEK) + "&" + type +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.length() > 0) {
                            if (mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)) {
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }


                            try {

                                Gson gson = new Gson();
                                ChildGrowthObj main = gson.fromJson(response.toString(), ChildGrowthObj.class);
                                prefs.saveStringPreferences(SP_BABY_INFO_PHOTO, main.getPhoto());
                                prefs.saveIntPerferences(SP_BABY_INFO_PHOTO_DIMEN, main.getPhoto_dimen());
                                Reading reading = new Reading();

                                for (int i=0; i < mainCatDetails.size(); i++){
                                    if (mainCatDetails.get(i).getPostType().equals(BABY_INFO)){
                                        mainCatDetails.remove(i);
                                    }


                                }

                                for (int i=0; i < mainCatDetails.size(); i++){

                                    if (mainCatDetails.get(i).getPostType().equals(CHILD_GROWTH)){
                                        mainCatDetails.remove(i);
                                    }
                                }


                                if(main.getId()!= 0){

                                    prefs.saveStringPreferences(SP_BABY_INFO_PHOTO, main.getPhoto());
                                    prefs.saveIntPerferences(SP_BABY_INFO_PHOTO_DIMEN, main.getPhoto_dimen());



                                    reading.setPhoto_url(main.getPhoto());
                                    reading.setDimen(main.getPhoto_dimen());
                                    reading.setPostType(BABY_INFO);
                                    mainCatDetails.add(1,reading);
                                    mediaAdapter.notifyItemInserted(mainCatDetails.size());


                                    Reading childReading = main.getReading();

                                    if ( (main.getHeight() != null && main.getHeight().trim().length() > 0)
                                    ){
                                        childReading.setSort_by(main.getHeight() );
                                    }
                                    else {
                                        childReading.setSort_by("");
                                    }

                                    if ( (main.getWeight() != null && main.getWeight().trim().length() > 0)){
                                        childReading.setPage_name(main.getWeight());
                                    }
                                    else {
                                        childReading.setPage_name("");
                                    }



                                    childReading.setPostType(CHILD_GROWTH);
                                    mainCatDetails.add(2,childReading);




                                    mediaAdapter.notifyDataSetChanged();
                                }
                                else{


                                    reading.setPhoto_url(prefs.getStringPreferences(SP_BABY_INFO_PHOTO));
                                    reading.setDimen(prefs.getIntPreferences(SP_BABY_INFO_PHOTO_DIMEN));
                                    reading.setPostType(BABY_INFO);
                                    mainCatDetails.add(1,reading);

                                    mediaAdapter.notifyDataSetChanged();
                                }



                            } catch (Exception e) {

                                for (int i=0; i < mainCatDetails.size(); i++){
                                    if (mainCatDetails.get(i).getPostType().equals(BABY_INFO)){
                                        mainCatDetails.remove(i);
                                    }


                                }

                                for (int i=0; i < mainCatDetails.size(); i++){
                                    if (mainCatDetails.get(i).getPostType().equals(CHILD_GROWTH)){
                                        mainCatDetails.remove(i);
                                    }
                                }


                                Reading reading = new Reading();
                                reading.setPhoto_url(prefs.getStringPreferences(SP_BABY_INFO_PHOTO));
                                reading.setDimen(prefs.getIntPreferences(SP_BABY_INFO_PHOTO_DIMEN));
                                reading.setPostType(BABY_INFO);
                                mainCatDetails.add(1,reading);
                                mediaAdapter.notifyDataSetChanged();
                            }

                           if (firstTime){
                               Log.e(TAG, "onResponse: "  + "this is first tiem" );
                               mainPageItem();
                           }
                           else
                               Log.e(TAG, "onResponse: "  + "this is not  tiem" );



                        }}

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                ToastHelper.showToast( activity, resources.getString(R.string.search_error));


            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void callAds(){

        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantKyarlayAds + "rectangular" , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }
                KyarlayAds main = new KyarlayAds();

                if (response.length() > 0){
                    Gson gson = new Gson();
                    main = gson.fromJson(response.toString(), KyarlayAds.class);
                }
                else{
                    main.setId(-810);
                }

                mainCategory(main);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );
                KyarlayAds main = new KyarlayAds();
                main.setId(-810);
                mainCategory(main);

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    private void mainCategory(KyarlayAds kyarlayAds)
    {



        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantRadingDetail+ "&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE)+"&page="+prefs.getIntPreferences(SP_PAGE_BRAND_CLICK) + "&post_type=media",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        progressBar.setVisibility(View.GONE);
                        if(response.length() > 0) {
                            prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, prefs.getIntPreferences(SP_PAGE_BRAND_CLICK) + SP_DEFAULT);



                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            loading = false;


                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Reading>>() {
                                }.getType();
                                List<Reading> categoryList = mGson.fromJson(response.toString(), listType);

                                for (int i = 0; i < categoryList.size(); i++) {

                                    if (i == 1){

                                        if (kyarlayAds.getId() != -810){

                                            kyarlayAds.setPostType(ADS);
                                            mainCatDetails.add(kyarlayAds);

                                        }
                                    }

                                    Reading reading = new Reading();
                                    reading = categoryList.get(i);
                                    reading.setPostType(VIDEO_NEWS);



                                    mainCatDetails.add(reading);

                                }


                                Reading pro2 = new Reading();
                                pro2.setPostType(CART_DETAIL_FOOTER);
                                mainCatDetails.add(pro2);
                                mediaAdapter.notifyItemInserted(mainCatDetails.size());


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else{
                            loading = true;
                            if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }
                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            mainCatDetails.add(noitem);


                            mediaAdapter.notifyItemInserted(mainCatDetails.size());

                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    mainCatDetails.remove(mainCatDetails.size() - 1);
                }



                mediaAdapter.notifyItemInserted(mainCatDetails.size());


            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



    public void changeNextInfo() {


        if (mainCatDetails.size() >= 2) {

            if (mainCatDetails.get(0).getPostType().equals(USER_STATUS)) {

                UniversalPost uni = mainCatDetails.get(1);

                if (uni.getPostType().equals(BABY_INFO)) {
                    // mainCatDetails.add(universalPost);


                    Product noitem = new Product();


                    if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 0) {
                        if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) == 42) {
                            prefs.saveIntPerferences(SP_KID_CHANGE_WEEK, 0);
                            prefs.saveIntPerferences(SP_KID_CHANGE_TYPE, 1);

                        }
                    } else if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 1) {
                        if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) < 1) {
                            prefs.saveIntPerferences(SP_KID_CHANGE_WEEK, 41);
                            prefs.saveIntPerferences(SP_KID_CHANGE_TYPE, 0);

                        }
                    }





                    if (prefs.getIntPreferences(SP_KID_CHANGE_WEEK) < 53) {

                        getChangeBabyInformation();
                    } else {

                        for (int i=0; i < mainCatDetails.size(); i++){
                            if (mainCatDetails.get(i).getPostType().equals(CHILD_GROWTH)){
                                mainCatDetails.remove(i);
                                mediaAdapter.notifyItemInserted(mainCatDetails.size());
                            }
                        }

                        mediaAdapter.notifyDataSetChanged();

                    }

                }
            }
        }
    }



    public  void changeBabyInfo(){

        if (mainCatDetails.size() > 0){

            if (mainCatDetails.get(0).getPostType().equals(USER_STATUS)){

                final Calendar c = Calendar.getInstance(Locale.US);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
                String dateAfterString = mDay + " " + (mMonth + 1) + " " + mYear;

                String dateBeforeString = prefs.getIntPreferences(SP_KID_DAY) + " " + (prefs.getIntPreferences(SP_KID_MONTH)) + " " + prefs.getIntPreferences(SP_KID_YEAR);

                try {
                    Date dateBefore = myFormat.parse(dateBeforeString);
                    Date dateAfter = myFormat.parse(dateAfterString);
                    long difference = dateAfter.getTime() - dateBefore.getTime();
                    float daysBetween = (difference / (1000 * 60 * 60 * 24));

                    int weeks = 0;



                    if (daysBetween > 0) {
                        weeks = (int) (daysBetween / 7);
                        prefs.saveIntPerferences(SP_KID_TYPE,1);
                    } else if (daysBetween == 0) {
                        prefs.saveIntPerferences(SP_KID_TYPE,1);
                    } else {
                        if (daysBetween < 0) {
                            dateBefore = myFormat.parse(dateAfterString);
                            dateAfter = myFormat.parse(dateBeforeString);
                            difference = dateAfter.getTime() - dateBefore.getTime();
                            daysBetween = (difference / (1000 * 60 * 60 * 24));


                            weeks = (int) (daysBetween / 7);
                            weeks = 39 - weeks;

                            prefs.saveIntPerferences(SP_KID_TYPE,0);

                            if (weeks < 0) {
                                weeks = -1;
                            }
                        }
                    }


                    prefs.saveIntPerferences(SP_KID_WEEK,weeks);
                } catch (Exception e) {
                    Log.e(TAG, "onBindViewHolder: " + e.getMessage());
                }

                prefs.saveIntPerferences(SP_KID_CHANGE_DAY, prefs.getIntPreferences(SP_KID_DAY));
                prefs.saveIntPerferences(SP_KID_CHANGE_MONTH, prefs.getIntPreferences(SP_KID_MONTH));
                prefs.saveIntPerferences(SP_KID_CHANGE_WEEK, prefs.getIntPreferences(SP_KID_WEEK));
                prefs.saveIntPerferences(SP_KID_CHANGE_YEAR, prefs.getIntPreferences(SP_KID_YEAR));
                prefs.saveIntPerferences(SP_KID_CHANGE_TYPE, prefs.getIntPreferences(SP_KID_TYPE));


                //richTextDetail();
                getBabyInformation(false);


            }
        }
    }

    private void getChangeBabyInformation(){

        String type = "type=pregnancy";


        if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 1){
            type = "type=parent";

        }else  if (prefs.getIntPreferences(SP_KID_CHANGE_TYPE) == 0) {
            type = "type=pregnancy";
        }



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,"https://www.kyarlay.com/api/weekly_highlights?period=" + prefs.getIntPreferences(SP_KID_CHANGE_WEEK) + "&" + type +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.length() > 0) {
                            if (mainCatDetails.size() != 0 && mainCatDetails.get(mainCatDetails.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)) {
                                mainCatDetails.remove(mainCatDetails.size() - 1);
                            }


                            try {

                                Gson gson = new Gson();
                                ChildGrowthObj main = gson.fromJson(response.toString(), ChildGrowthObj.class);


                                prefs.saveStringPreferences(SP_BABY_INFO_PHOTO, main.getPhoto());
                                prefs.saveIntPerferences(SP_BABY_INFO_PHOTO_DIMEN, main.getPhoto_dimen());



                                Reading reading = new Reading();

                                if(main.getId()!= 0){

                                    int indexBaby  = -1;

                                    prefs.saveStringPreferences(SP_BABY_INFO_PHOTO, main.getPhoto());
                                    prefs.saveIntPerferences(SP_BABY_INFO_PHOTO_DIMEN, main.getPhoto_dimen());

                                    reading.setPhoto_url(main.getPhoto());
                                    reading.setDimen(main.getPhoto_dimen());
                                    reading.setPostType(BABY_INFO);

                                    for (int i = 0 ; i < mainCatDetails.size(); i++){
                                        if (mainCatDetails.get(i).getPostType().equals(BABY_INFO)){
                                            mainCatDetails.remove(i);
                                            mainCatDetails.add(i,reading);

                                            indexBaby = i;



                                        }
                                    }

                                    if (indexBaby == -1){
                                        mainCatDetails.add(1,reading);
                                        indexBaby = 1;

                                    }

                                    Reading childReading = main.getReading();

                                    if ( (main.getHeight() != null && main.getHeight().trim().length() > 0)
                                    ){
                                        childReading.setSort_by(main.getHeight() );
                                    }
                                    else {
                                        childReading.setSort_by("");
                                    }

                                    if ( (main.getWeight() != null && main.getWeight().trim().length() > 0) ){
                                        childReading.setPage_name(main.getWeight());
                                    }
                                    else {
                                        childReading.setPage_name("");
                                    }



                                    childReading.setPostType(CHILD_GROWTH);

                                    for (int i = 0 ; i < mainCatDetails.size(); i++){
                                        if (mainCatDetails.get(i).getPostType().equals(CHILD_GROWTH)){
                                            mainCatDetails.remove(i);
                                            mainCatDetails.add(i, childReading);
                                            indexBaby = -1;

                                        }
                                    }


                                    if (indexBaby != -1 ){
                                        indexBaby = indexBaby + 1;
                                        mainCatDetails.add(indexBaby, childReading);
                                    }

                                    mediaAdapter.notifyDataSetChanged();


                                }
                                else{

                                    prefs.saveStringPreferences(SP_BABY_INFO_PHOTO, main.getPhoto());
                                    prefs.saveIntPerferences(SP_BABY_INFO_PHOTO_DIMEN, main.getPhoto_dimen());
                                    reading.setPhoto_url(prefs.getStringPreferences(SP_BABY_INFO_PHOTO));
                                    reading.setDimen(prefs.getIntPreferences(SP_BABY_INFO_PHOTO_DIMEN));
                                    reading.setPostType(BABY_INFO);

                                    int indexFinal = -1;
                                    for (int i = 0 ; i < mainCatDetails.size(); i++){
                                        if (mainCatDetails.get(i).getPostType().equals(BABY_INFO)){
                                            mainCatDetails.remove(i);
                                            mainCatDetails.add(i,reading);
                                            indexFinal = i;
                                        }
                                    }

                                    if (indexFinal == -1 ) {
                                        mainCatDetails.add(1, reading);
                                    }

                                    for (int i=0; i < mainCatDetails.size(); i++){
                                        if (mainCatDetails.get(i).getPostType().equals(CHILD_GROWTH)){
                                            mainCatDetails.remove(i);
                                            mediaAdapter.notifyItemInserted(mainCatDetails.size());
                                        }
                                    }

                                    mediaAdapter.notifyDataSetChanged();
                                }



                            } catch (Exception e) {

                                Reading reading = new Reading();
                                reading.setPhoto_url(prefs.getStringPreferences(SP_BABY_INFO_PHOTO));
                                reading.setDimen(prefs.getIntPreferences(SP_BABY_INFO_PHOTO_DIMEN));
                                reading.setPostType(BABY_INFO);

                                int indexFinal = -1;
                                for (int i = 0 ; i < mainCatDetails.size(); i++){
                                    if (mainCatDetails.get(i).getPostType().equals(BABY_INFO)){
                                        mainCatDetails.remove(i);
                                        mainCatDetails.add(i,reading);
                                        indexFinal = i;
                                    }
                                }

                                if (indexFinal == -1 ) {
                                    mainCatDetails.add(1, reading);
                                }

                                for (int i=0; i < mainCatDetails.size(); i++){
                                    if (mainCatDetails.get(i).getPostType().equals(CHILD_GROWTH)){
                                        mainCatDetails.remove(i);
                                        mediaAdapter.notifyItemInserted(mainCatDetails.size());
                                    }
                                }

                                mediaAdapter.notifyDataSetChanged();
                            }


                        }}

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error "  + error.getMessage() );

                ToastHelper.showToast( activity, resources.getString(R.string.search_error));

            }
        });


        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}
