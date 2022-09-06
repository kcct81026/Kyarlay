package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;

import java.util.HashMap;
import java.util.Map;

public class VideoPagerAdapter extends FragmentStatePagerAdapter implements ConstantVariable {
    private static final String TAG = "VideoPagerAdapter";
    int mNumOfTabs;

    FragmentMedia fragmentMedia;
    FragmentLatest fragmentLatest;
    AppCompatActivity activity;

    Resources resources;
    MyPreference prefs;

    public VideoPagerAdapter(FragmentManager fm, int NumOfTabs, AppCompatActivity activity) {
        super(fm);

        this.activity = activity;
        new MyFlurry(activity);

        this.mNumOfTabs = NumOfTabs;

        prefs = new MyPreference(activity);
        Context contextmyanmar = LocaleHelper.setLocale( activity, prefs.getStringPreferences(LANGUAGE));
        resources = contextmyanmar.getResources();
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0 :

                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "latest all news list ");
                    FlurryAgent.logEvent("Latest Tab Change", mix);

                } catch (Exception e) {
                }



               fragmentMedia = new FragmentMedia();
               return fragmentMedia;

            case 1:
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "others list");
                    FlurryAgent.logEvent("Other Tab Change", mix);
                } catch (Exception e) {
                }

                fragmentLatest = new FragmentLatest();
                return fragmentLatest;




            default:
                return null;
        }
    }


    @Override
    public int getCount()  {
        return mNumOfTabs;
    }






}
