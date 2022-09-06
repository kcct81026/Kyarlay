package com.kyarlay.ayesunaing.data;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;

import java.util.Calendar;

public class AgeConfig implements  ConstantVariable{

    private static final String TAG = "AgeConfig";

    AppCompatActivity activity;
    MyPreference prefs;
    Resources resources;


    public AgeConfig(AppCompatActivity activity){
        this.activity = activity;
        prefs = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
    }

    public String calculateKidAge(int month, int year, String kid, String parent){


        String age = "";
        int kid_month   = 0;
        int kid_year    = 0;
        String kid_type = "";
        String parent_type  = "";

        int cur_month   = Calendar.getInstance().get(Calendar.MONTH)+1;
        int cur_year    = Calendar.getInstance().get(Calendar.YEAR);




        int increment = 0;
        if(month > cur_month){
            increment = 1;
            kid_month = (12 - month) + cur_month;
        }else{
            kid_month   = cur_month - month;
        }

        kid_year = cur_year - year - increment;

        if(kid.equals(BOY)){
            kid_type = resources.getString(R.string.group_chat_detail_boy);
        }else if (kid.equals(GIRL)){
            kid_type = resources.getString(R.string.group_chat_detail_girl);
        }
        else{
            kid_type = resources.getString(R.string.group_chat_detail_baby);
        }




        if(parent.equals(MOTHER)){
            parent_type = resources.getString(R.string.group_chat_detail_mom);
        }else if(parent.equals(FATHER)){
            parent_type = resources.getString(R.string.group_chat_detail_dad);
        }else{
            parent_type = resources.getString(R.string.group_chat_detail_other_show);
        }



        if(kid_year == 0 &&  prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_MYANMAR))
            age = String.format(resources.getString(R.string.customer_post_kid_detail_noyear),
                    kid_month+"",
                    kid_type, parent_type);

        else  if(kid_year == 0 &&  prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_UNI))
            age = String.format(resources.getString(R.string.customer_post_kid_detail_noyear),
                    kid_month+"",
                    kid_type, parent_type);

        else if(kid_year == 0 && prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_ENGLISH))
            age = String.format(resources.getString(R.string.customer_post_kid_detail_noyear),
                    parent_type, kid_month+"",
                    kid_type);
        else if(kid_year > 0 && prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_MYANMAR))

            age = String.format(resources.getString(R.string.customer_post_kid_detail_year),
                    kid_year+"",kid_month+"",
                    kid_type, parent_type);

        else if(kid_year > 0 && prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_UNI))

            age = String.format(resources.getString(R.string.customer_post_kid_detail_year),
                    kid_year+"",kid_month+"",
                    kid_type, parent_type);

        else if(kid_year > 0 && prefs.getStringPreferences(LANGUAGE).equals(LANGUAGE_ENGLISH))

            age = String.format(resources.getString(R.string.customer_post_kid_detail_year),
                    parent_type, kid_year+"",kid_month+"",
                    kid_type);

        else if (kid_year < 0 ){
            if(parent.equals(MOTHER)){
                age = resources.getString(R.string.expecting_mom);
            }else if(parent.equals(FATHER)){
                age = resources.getString(R.string.expecting_dad);
            }
        }

        return age;

    }



}
