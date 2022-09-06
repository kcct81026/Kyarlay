package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.MemberBenefitObject;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.PointAdapter;

import java.util.ArrayList;

public class PointDashboardActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "PointDashboardActivity";

    LinearLayout back_layout;
    Boolean loading=true;
    RecyclerView.LayoutManager manager;

    MyPreference prefs;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    PointAdapter universalAdapter;
    RecyclerView recyclerView ;

    Display display ;
    Resources resources;
    AppCompatActivity activity;
    ProgressBar progressBar;
    MemberBenefitObject memberBenefitObject ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_click);

        activity =  PointDashboardActivity.this;
        display = activity.getWindowManager().getDefaultDisplay();

        prefs           = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources       = context.getResources();

        universalAdapter = new PointAdapter( activity, mainCatDetails);

        back_layout = findViewById(R.id.back_layout);
        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar        = (ProgressBar) findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        memberBenefitObject = intent.getParcelableExtra("member_benefit");


        RecyclerView.LayoutManager manager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);



        memberBenefitObject.setPostType(POINT_DASHBOARD);
        mainCatDetails.add(memberBenefitObject);


        MemberBenefitObject memberBenefitObject1 = new MemberBenefitObject();
        memberBenefitObject1.setExpired_points(memberBenefitObject.getExpired_points());
        memberBenefitObject1.setExpired_at(memberBenefitObject.getExpired_at());
        memberBenefitObject1.setPostType(POINT_WATCH_HISTORY);
        mainCatDetails.add(memberBenefitObject1);

        MemberBenefitObject mb = new MemberBenefitObject();
        mb.setTitle(memberBenefitObject.getTitle());
        mb.setExpired_month(memberBenefitObject.getExpired_month());
        mb.setCurrent_amt(memberBenefitObject.getCurrent_amt());
        mb.setMember_target_amt(memberBenefitObject.getMember_target_amt());
        mb.setMember_expired_month(memberBenefitObject.getMember_expired_month());
        mb.setMember_description(memberBenefitObject.getMember_description());
        mb.setPostType(POINT_GO_SHOPPING);
        mainCatDetails.add(mb);

        MemberBenefitObject memberBenefitObject3 = new MemberBenefitObject();
        memberBenefitObject3.setBenefit_title(memberBenefitObject.getBenefit_title());
        memberBenefitObject3.setBenefitDataList(memberBenefitObject.getBenefitDataList());
        memberBenefitObject3.setPostType(POINT_BENEFITS);
        mainCatDetails.add(memberBenefitObject3);

        universalAdapter.notifyDataSetChanged();


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
