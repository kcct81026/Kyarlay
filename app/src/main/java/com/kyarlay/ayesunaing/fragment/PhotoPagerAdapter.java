/*
package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;

public class PhotoPagerAdapter extends FragmentStatePagerAdapter implements ConstantVariable {
    private static final String TAG = "PhotoPagerAdapter";
    int mNumOfTabs;


    FragmentWebView fragmentTopTen;
    FragmentRecent fragmentRecent;
    AppCompatActivity activity;

    Resources resources;
    MyPreference prefs;

    public PhotoPagerAdapter(FragmentManager fm, int NumOfTabs, AppCompatActivity activity) {
        super(fm);

        this.activity = activity;
        //new MyFlurry(activity);

        this.mNumOfTabs = NumOfTabs;

        prefs = new MyPreference(activity);
        Context contextmyanmar = LocaleHelper.setLocale( activity, prefs.getStringPreferences(LANGUAGE));
        resources = contextmyanmar.getResources();
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0 :

             */
/*   try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "latest all news list ");
                    //FlurryAgent.logEvent("Latest Tab Change", mix);

                } catch (Exception e) {
                }*//*


                fragmentRecent = new FragmentRecent();
                return fragmentRecent;

            case 1:

                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",1);

                fragmentTopTen = new FragmentWebView();
                fragmentTopTen.setArguments(bundle1);
                return fragmentTopTen;


           */
/* case 2:

                Bundle bundle2 = new Bundle();
                bundle2.putInt("position",2);

                fragmentTopTen = new FragmentWebView();
                fragmentTopTen.setArguments(bundle2);
                return fragmentTopTen;*//*



            default:
                return null;
        }
    }


    @Override
    public int getCount()  {
        return mNumOfTabs;
    }


}
*/
