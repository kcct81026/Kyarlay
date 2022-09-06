package com.kyarlay.ayesunaing.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.ProfileActivity;
import com.kyarlay.ayesunaing.activity.ReadingWishlistActivity;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTabLayout;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;

public class FragmentReadingPager extends Fragment implements Constant, ConstantVariable {

    MyPreference prefs;
    Resources resources;
    AppCompatActivity activity;
    ViewPager viewPager;
    ViewGroup viewGroup;
    ReadingPagerAdapter adapter;
    CustomTabLayout tabLayout;
    int position = 0;

    CustomTextView title;
    RelativeLayout save_layout,noti_layout,profile_layout, like_layout;
    CircularTextView txtSave,txtNoti;
    CircularNetworkImageView profileIcon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_pager_reading, container, false);
        viewGroup = container;
        position = getArguments().getInt("position",0);


        tabLayout = (CustomTabLayout) rootView.findViewById(R.id.tab_layout);
        activity = (AppCompatActivity) getActivity();
        new MyFlurry(activity);
        prefs = new MyPreference(activity);
        Context contextmyanmar = LocaleHelper.setLocale( getActivity(), prefs.getStringPreferences(LANGUAGE));
        resources = contextmyanmar.getResources();
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        title = rootView.findViewById(R.id.title);
        txtSave = rootView.findViewById(R.id.txtSave);
        txtNoti = rootView.findViewById(R.id.txtNoti);
        save_layout = rootView.findViewById(R.id.save_layout);
        noti_layout = rootView.findViewById(R.id.noti_layout);
        like_layout = rootView.findViewById(R.id.like_layout);
        profile_layout = rootView.findViewById(R.id.profile_layout);
        profileIcon = rootView.findViewById(R.id.profileIcon);


        title.setText(resources.getString(R.string.group_chat));

        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.popular)));

        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.news_articl)));



        viewPager = (ViewPager) rootView.findViewById(R.id.pager);



        try{
            if (prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals("null")){
                profileIcon.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
            }
            else if (!prefs.getStringPreferences(SP_USER_PROFILEIMAGE).equals(""))
                profileIcon.setImageUrl(prefs.getStringPreferences(SP_USER_PROFILEIMAGE), AppController.getInstance().getImageLoader());
            else
                profileIcon.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);


        }catch (Exception e){
            profileIcon.setDefaultImageResId(R.mipmap.ic_kyarlay_camera);
        }


        adapter = new ReadingPagerAdapter(getFragmentManager(), tabLayout.getTabCount(), activity);
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

        save_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        noti_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    Intent intent = new Intent(activity, NotificationAcitivity.class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    startActivity(intent);

                }

            }
        });

        like_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    Intent intent = new Intent(activity, ReadingWishlistActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    startActivity(intent);

                }

            }
        });



        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                    Intent intent = new Intent(activity, ProfileActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    startActivity(intent);

                }
            }
        });


        return rootView;
    }



    @Override
    public void onPause() {
        super.onPause();
    }


    class ReadingPagerAdapter extends FragmentStatePagerAdapter implements ConstantVariable {
        private static final String TAG = "ReadingPagerAdapter";
        int mNumOfTabs;

        AppCompatActivity activity;


        public ReadingPagerAdapter(FragmentManager fm, int NumOfTabs, AppCompatActivity activity) {
            super(fm);

            this.activity = activity;
            new MyFlurry(activity);

            this.mNumOfTabs = NumOfTabs;

        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0 :
                    FragmentPopular fragmentPopular = new FragmentPopular();
                    return fragmentPopular;
                case 1:
                    FragmentNew fragmentNew = new FragmentNew();
                    return fragmentNew;



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


