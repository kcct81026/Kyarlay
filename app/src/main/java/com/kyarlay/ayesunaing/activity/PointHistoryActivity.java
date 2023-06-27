package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTabLayout;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.fragment.FragmentReceived;
import com.kyarlay.ayesunaing.fragment.FragmentUsed;

public class PointHistoryActivity  extends AppCompatActivity implements Constant, ConstantVariable {

    MyPreference prefs;
    Resources resources;
    AppCompatActivity activity;
    ViewPager viewPager;
    PointPagerAdapter adapter;
    CustomTabLayout tabLayout;
    int position = 0;

    CustomTextView title;
    RelativeLayout save_layout,noti_layout,profile_layout, like_layout, back_layout;
    CircularTextView txtSave,txtNoti;
    CircularNetworkImageView profileIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pager_reading);
        tabLayout = (CustomTabLayout) findViewById(R.id.tab_layout);
        activity = this;
        prefs = new MyPreference(activity);
        Context contextmyanmar = LocaleHelper.setLocale( activity, prefs.getStringPreferences(LANGUAGE));
        resources = contextmyanmar.getResources();
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        title = findViewById(R.id.title);
        txtSave = findViewById(R.id.txtSave);
        txtNoti = findViewById(R.id.txtNoti);
        save_layout = findViewById(R.id.save_layout);
        noti_layout = findViewById(R.id.noti_layout);
        like_layout = findViewById(R.id.like_layout);
        profile_layout = findViewById(R.id.profile_layout);
        profileIcon = findViewById(R.id.profileIcon);
        back_layout = findViewById(R.id.back_layout);


        title.setText("Point History");
        title.setTextColor(activity.getResources().getColor(R.color.black));

        tabLayout.addTab(tabLayout.newTab().setText("Received"));

        tabLayout.addTab(tabLayout.newTab().setText("Used"));



        viewPager = (ViewPager) findViewById(R.id.pager);





        adapter = new PointPagerAdapter(activity.getSupportFragmentManager(), tabLayout.getTabCount(), activity);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.setOffscreenPageLimit(1);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }
        });

        save_layout.setVisibility(View.GONE);
        noti_layout.setVisibility(View.GONE);
        like_layout.setVisibility(View.GONE);
        profile_layout.setVisibility(View.GONE);
        back_layout.setVisibility(View.VISIBLE);

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    @Override
    public void onPause() {
        super.onPause();
    }


    class PointPagerAdapter extends FragmentStatePagerAdapter implements ConstantVariable {
        private static final String TAG = "PointPagerAdapter";
        int mNumOfTabs;

        AppCompatActivity activity;


        public PointPagerAdapter(FragmentManager fm, int NumOfTabs, AppCompatActivity activity) {
            super(fm);

            this.activity = activity;
            //new MyFlurry(activity);

            this.mNumOfTabs = NumOfTabs;

        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0 :
                    FragmentReceived fragmentReceived = new FragmentReceived();
                    return fragmentReceived;
                case 1:
                    FragmentUsed fragmentUsed = new FragmentUsed();
                    return fragmentUsed;



                default:
                    return null;
            }
        }


        @Override
        public int getCount()  {
            return mNumOfTabs;
        }






    }

}

      


