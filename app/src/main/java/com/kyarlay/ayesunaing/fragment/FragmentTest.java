package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTabLayout;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;

public class FragmentTest extends Fragment implements Constant,ConstantVariable {

    MyPreference prefs;
    Resources resources;
    AppCompatActivity activity;
    ViewPager viewPager;
    ViewGroup viewGroup;
    VideoPagerAdapter adapter;
    CustomTabLayout tabLayout;
    int position = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
        viewGroup = container;
        position = getArguments().getInt("position",0);


        tabLayout = (CustomTabLayout) rootView.findViewById(R.id.tab_layout);
        activity = (AppCompatActivity) getActivity();
        new MyFlurry(activity);
        prefs = new MyPreference(activity);
      //  tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        Context contextmyanmar = LocaleHelper.setLocale( getActivity(), prefs.getStringPreferences(LANGUAGE));
        resources = contextmyanmar.getResources();
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
/*

        tabLayout.addTab(tabLayout.newTab().setText(resources.
                getString(R.string.video_latest)));

        tabLayout.addTab(tabLayout.newTab().setText(resources.
                getString(R.string.video_news)));

        tabLayout.addTab(tabLayout.newTab().setText(resources.
                getString(R.string.video_list)));*/

        tabLayout.addTab(tabLayout.newTab().setText(resources.
                getString(R.string.video_news)));

        tabLayout.addTab(tabLayout.newTab().setText(resources.
                getString(R.string.popular)));

        viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        adapter = new VideoPagerAdapter(getFragmentManager(), tabLayout.getTabCount(), activity);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.setOffscreenPageLimit(1);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

               /* try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("tab_click", "other click" );
                    mix.put("tab_position", String.valueOf(tab.getPosition()));
                    mix.put("tab_name", String.valueOf(tab.getText()));
                    FlurryAgent.logEvent("Tab Click Event", mix);
                } catch (Exception e) {
                }


                if (tab.getPosition() == 1) {
                    prefs.saveBooleanPreference(SP_CLICK_OTHER, true);

                }
                else {
                    prefs.saveBooleanPreference(SP_CLICK_OTHER, false);

                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }
        });

       // clearAll(0);

        return rootView;
    }


    public VideoPagerAdapter getAdapter() {
        return adapter;
    }

    public ViewPager getViewPager(){
        return viewPager;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}



