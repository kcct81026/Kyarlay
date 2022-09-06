package com.kyarlay.ayesunaing.operation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.PointHistoryActivity;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.ConstantsDB;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.holder.FooterHoloder;
import com.kyarlay.ayesunaing.holder.NoItemHolder;
import com.kyarlay.ayesunaing.holder.PointBenefitHolder;
import com.kyarlay.ayesunaing.holder.PointDataHolder;
import com.kyarlay.ayesunaing.holder.PointGoShoppingHolder;
import com.kyarlay.ayesunaing.holder.PointHolder;
import com.kyarlay.ayesunaing.holder.PointReceivedHolder;
import com.kyarlay.ayesunaing.object.MemberBenefitObject;
import com.kyarlay.ayesunaing.object.PointHistoryVOList;
import com.kyarlay.ayesunaing.object.UniversalPost;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  implements ConstantVariable, Constant, ConstantsDB {

    private static final String TAG = "PointAdapter";

    ArrayList<UniversalPost> universalList;
    AppCompatActivity activity;
    ImageLoader imageLoader;
    Display display;
    DatabaseAdapter databaseAdapter;
    MyPreference prefs;
    FirebaseAnalytics firebaseAnalytics;
    Resources resources;
    //PointAdapter gridAdapter;
    //RecyclerView.LayoutManager layoutManager;

    DecimalFormat formatter = new DecimalFormat("#,###,###");


    public PointAdapter(AppCompatActivity activity1, ArrayList<UniversalPost> universalList) {
        this.universalList  = universalList;
        this.activity       = activity1;
        imageLoader         = AppController.getInstance().getImageLoader();
        display             = activity.getWindowManager().getDefaultDisplay();
        databaseAdapter     = new DatabaseAdapter(activity.getApplicationContext());
        prefs               = new MyPreference(activity);
        Context context = LocaleHelper.setLocale(activity, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        new MyFlurry(activity);

        firebaseAnalytics   = FirebaseAnalytics.getInstance(activity);


    }

    @Override
    public int getItemCount() {
        return universalList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (universalList.get(position).getPostType().equals(POINT_DASHBOARD)) {
            return VIEW_TYPE_POINT_DASHBOARD;
        }
        else if (universalList.get(position).getPostType().equals(POINT_WATCH_HISTORY)) {
            return VIEW_TYPE_POINT_WATCH_HISTORY;
        }
        else if (universalList.get(position).getPostType().equals(POINT_GO_SHOPPING)) {
            return VIEW_TYPE_POINT_GO_SHOPPING;
        }
        else if (universalList.get(position).getPostType().equals(POINT_BENEFITS)) {
            return VIEW_TYPE_POINT_BENEFITS;
        }
        else if (universalList.get(position).getPostType().equals(POINT_RECEIVED_GET)) {
            return VIEW_TYPE_POINT_RECEIVED_GET;
        }
        else if(universalList.get(position).getPostType().equals(CART_DETAIL_FOOTER)){
            return VIEW_TYPE_CART_DETAIL_FOOTER;
        }
        else if(universalList.get(position).getPostType().equals(CART_DETAIL_NO_ITEM)){
            return VIEW_TYPE_CART_DETAIL_NO_ITEM;
        }

        return VIEW_TYPE_DEFAULT;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == VIEW_TYPE_POINT_DASHBOARD){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_dashboard, parent, false);
            viewHolder = new PointHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_POINT_WATCH_HISTORY){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_data, parent, false);
            viewHolder = new PointDataHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_POINT_GO_SHOPPING){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_go_shopping, parent, false);
            viewHolder = new PointGoShoppingHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_POINT_BENEFITS){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_main_point_benefit, parent, false);
            viewHolder = new PointBenefitHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_POINT_RECEIVED_GET){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_point_month, parent, false);
            viewHolder = new PointReceivedHolder(viewItem);
        }
        else if(viewType == VIEW_TYPE_CART_DETAIL_FOOTER){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_footer, parent, false);
            viewHolder = new FooterHoloder(viewItem);
        }else if(viewType == VIEW_TYPE_CART_DETAIL_NO_ITEM){
            View viewItem = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cart_detail_no_item, parent, false);
            viewHolder = new NoItemHolder(viewItem);
        }

        return viewHolder;

    }

    @SuppressLint({"ResourceType", "StringFormatMatches"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder parentHolder, int position) {
        int type = getItemViewType(position);

        switch (type) {
            case VIEW_TYPE_POINT_RECEIVED_GET: {
                PointReceivedHolder pointReceivedHolder = (PointReceivedHolder) parentHolder;
                PointHistoryVOList pointHistoryVOList = (PointHistoryVOList) universalList.get(position);
                pointReceivedHolder.title.setText(pointHistoryVOList.getMonth_year());
                if (pointHistoryVOList.getPointHistoryVOList().size() > 0){
                    for(int i=0; i< pointHistoryVOList.getPointHistoryVOList().size(); i++){
                        View addView = LayoutInflater.from(activity).inflate(R.layout.layout_point_data_item,null);
                        TextView txtDate = addView.findViewById(R.id.txtDate);
                        txtDate.setText(pointHistoryVOList.getPointHistoryVOList().get(i).getCreatedAt());
                        TextView txtReason = addView.findViewById(R.id.txtReason);
                        txtReason.setText(pointHistoryVOList.getPointHistoryVOList().get(i).getType());
                        TextView txtPoint = addView.findViewById(R.id.txtPoint);


                        if (pointHistoryVOList.getPointHistoryVOList().get(i).getPoint() > 0) {
                            txtPoint.setTextColor(activity.getResources().getColor(R.color.green));
                            txtPoint.setText("+" + pointHistoryVOList.getPointHistoryVOList().get(i).getPoint() );
                        }
                        else{
                            txtPoint.setTextColor(activity.getResources().getColor(R.color.coloredInactive));
                            txtPoint.setText("" + pointHistoryVOList.getPointHistoryVOList().get(i).getPoint() );
                        }


                        pointReceivedHolder.layoutParent.addView(addView);
                    }
                }




                break;
            }
            case VIEW_TYPE_CART_DETAIL_NO_ITEM:{
                NoItemHolder noitem = (NoItemHolder) parentHolder;
                noitem.space.getLayoutParams().height = 350 ;
                noitem.space.setText(resources.getString(R.string.noitem));
                noitem.space.setGravity(Gravity.CENTER);
                noitem.space.setTextSize(12);
                break;
            }

            case VIEW_TYPE_CART_DETAIL_FOOTER:{

                final FooterHoloder footerHoloder = (FooterHoloder) parentHolder;
                break;
            }
            case VIEW_TYPE_POINT_BENEFITS: {
                PointBenefitHolder pointBenefitHolder = (PointBenefitHolder) parentHolder;
                MemberBenefitObject pointBenefits = (MemberBenefitObject) universalList.get(position);
                if (pointBenefits.getBenefitDataList().size() > 0){
                    for(int i=0; i< pointBenefits.getBenefitDataList().size(); i++){
                        View addView = LayoutInflater.from(activity).inflate(R.layout.layout_point_benefits,null);
                        TextView txt = addView.findViewById(R.id.txt);
                        txt.setText(pointBenefits.getBenefitDataList().get(i).getBody());
                        ImageView img = addView.findViewById(R.id.img);
                        if (pointBenefits.getBenefitDataList().get(i).isStatus()){
                            img.setImageDrawable(activity.getResources().getDrawable(R.drawable.unlock));

                        }
                        else{
                            img.setImageDrawable(activity.getResources().getDrawable(R.drawable.lock));

                        }


                        pointBenefitHolder.layoutParent.addView(addView);
                    }
                }

                pointBenefitHolder.title.setText(pointBenefits.getBenefit_title());


                break;
            }
            case VIEW_TYPE_POINT_GO_SHOPPING: {
                /*
                 mb.setTitle(memberBenefitObject.getTitle());
        mb.setExpired_month(memberBenefitObject.getExpired_month());
        mb.setCurrent_amt(memberBenefitObject.getCurrent_amt());
        mb.setMember_target_amt(memberBenefitObject.getMember_target_amt());
                 */
                PointGoShoppingHolder pointGoShoppingHolder = (PointGoShoppingHolder) parentHolder;
                MemberBenefitObject mb = (MemberBenefitObject) universalList.get(position);
                if (prefs.getIntPreferences(SP_VIP_ID) != 0) {
                    pointGoShoppingHolder.layout_vip.setVisibility(View.VISIBLE);
                    pointGoShoppingHolder.layout_no_vip.setVisibility(View.GONE);

                    pointGoShoppingHolder.txtVipTitle.setText(resources.getString(R.string.extend_membership));
                    //pointGoShoppingHolder.txtVipRemiderText.setText(String.format(resources.getString(R.string.vip_title), mb.getExpired_month() + "" , (mb.getMember_target_amt() - mb.getCurrent_amt()) + ""));
                    pointGoShoppingHolder.txtVipRemiderText.setText(mb.getMember_description());
                    Log.e(TAG, "onBindViewHolder: ---------------------------------------- not null "  );

                }else{
                    Log.e(TAG, "onBindViewHolder: ---------------------------------------- null "  );
                    pointGoShoppingHolder.layout_vip.setVisibility(View.GONE);
                    pointGoShoppingHolder.layout_no_vip.setVisibility(View.VISIBLE);

                    pointGoShoppingHolder.txtNoVipTitle.setText(resources.getString(R.string.change_vip));
                    pointGoShoppingHolder.txtNoVipAmount.setText(mb.getMember_target_amt() + "");
                    pointGoShoppingHolder.txtNoVipMonthBetween.setText(String.format(resources.getString(R.string.within_s_months), mb.getMember_expired_month() + ""));
                   // pointGoShoppingHolder.txtNoVipRemiderText.setText(String.format(resources.getString(R.string.no_vip_title),mb.getExpired_month() + "" , (mb.getMember_target_amt() - mb.getCurrent_amt()) + ""));
                    pointGoShoppingHolder.txtNoVipRemiderText.setText(mb.getMember_description());

                }


                pointGoShoppingHolder.txtShoppingTitle.setText(resources.getString(R.string.menu_shopping));
                pointGoShoppingHolder.layoutShopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                });

                break;
            }

            case VIEW_TYPE_POINT_DASHBOARD: {
                PointHolder pointHolder = (PointHolder) parentHolder;
                MemberBenefitObject memberBenefitObject = (MemberBenefitObject) universalList.get(position);
                pointHolder.linearMain.getLayoutParams().height  = display.getHeight() / 4  ;
                if (prefs.getIntPreferences(SP_VIP_ID) != 0) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                        Drawable wrapDrawable = DrawableCompat.wrap(pointHolder.progress.getIndeterminateDrawable());
                        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(activity.getApplicationContext(), android.R.color.white));
                        pointHolder.progress.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
                    } else {
                        pointHolder.progress.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.custom_purple_deep)));
                        pointHolder.progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity.getApplicationContext(), android.R.color.white), PorterDuff.Mode.SRC_IN);
                    }

                    pointHolder.imgHiddenOne.setVisibility(View.VISIBLE);
                    pointHolder.txtWatchMore.setText(memberBenefitObject.getExpired_month() + " " + resources.getString(R.string.point_left));
                    pointHolder.txtWatchMore.setBackground(activity.getResources().getDrawable(R.drawable.border_purple));
                    pointHolder.txtWatchMore.setTextColor(activity.getResources().getColor(R.color.white));

                } else {

                    pointHolder.linearBackground.setBackgroundResource(R.drawable.dashboard_gray);
                    pointHolder.txtMemberTitle.setTextColor(activity.getResources().getColor(R.color.white));
                    pointHolder.txtAmount.setTextColor(activity.getResources().getColor(R.color.white));
                    pointHolder.txtBottom.setTextColor(activity.getResources().getColor(R.color.white));


                   // pointHolder.txtBottom.setText(" 3000/4000" + " " + resources.getString(R.string.currency));

                    pointHolder.imgHiddenTwo.setVisibility(View.VISIBLE);
                    pointHolder.txtWatchMore.setVisibility(View.GONE);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                        Drawable wrapDrawable = DrawableCompat.wrap(pointHolder.progress.getIndeterminateDrawable());
                        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(activity.getApplicationContext(), android.R.color.white));
                        pointHolder.progress.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));


                    } else {
                        pointHolder.progress.setProgressTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.colorSplashScreen)));

                        pointHolder.progress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity.getApplicationContext(), android.R.color.white), PorterDuff.Mode.SRC_IN);
                    }
                }


                int progressValue = ( memberBenefitObject.getCurrent_amt() * 100 ) / memberBenefitObject.getMember_target_amt();
                pointHolder.progress.setProgress(progressValue);
                pointHolder.txtMemberTitle.setText(memberBenefitObject.getTitle());
                pointHolder.layoutPoint.setVisibility(View.GONE);
                pointHolder.txtDeadline.setVisibility(View.GONE);
                pointHolder.txtAmount.setText(memberBenefitObject.getCurrent_amt() + " / " + memberBenefitObject.getMember_target_amt() + " " + resources.getString(R.string.currency));
                pointHolder.txtBottom.setText(memberBenefitObject.getDescription());
                pointHolder.linearMain.setBackgroundColor(activity.getResources().getColor(R.color.white));

                break;
            }

            case VIEW_TYPE_POINT_WATCH_HISTORY : {
                PointDataHolder pointDataHolder = (PointDataHolder) parentHolder;
                MemberBenefitObject memberBenefitObject2 = (MemberBenefitObject) universalList.get(position);

                pointDataHolder.txtHistoryTitle.setText(resources.getString(R.string.point_history));
                pointDataHolder.txtPoint.setText(prefs.getIntPreferences(SP_USER_POINT) + " ");
                pointDataHolder.txtUserPointTitle.setText(resources.getString(R.string.point_history));



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if(memberBenefitObject2.getExpired_at().equals("") ){
                        pointDataHolder.txtExpireDate.setText(Html.fromHtml(
                                String.format(resources.getString(R.string.your_point_text_empty), memberBenefitObject2.getExpired_points() + "" )  , Html.FROM_HTML_MODE_COMPACT));
                    }
                    else{
                        pointDataHolder.txtExpireDate.setText(Html.fromHtml(
                                String.format(resources.getString(R.string.your_point_text), memberBenefitObject2.getExpired_points() + "" , memberBenefitObject2.getExpired_at())  , Html.FROM_HTML_MODE_COMPACT));
                    }
                } else {
                    if(memberBenefitObject2.getExpired_at().equals("") ){
                        pointDataHolder.txtExpireDate.setText(Html.fromHtml(String.format(resources.getString(R.string.your_point_text_empty), memberBenefitObject2.getExpired_points() + "" )));

                    }
                    else{
                        pointDataHolder.txtExpireDate.setText(Html.fromHtml(String.format(resources.getString(R.string.your_point_text), memberBenefitObject2.getExpired_points() + "" , memberBenefitObject2.getExpired_at())));

                    }


                }

                pointDataHolder.layoutPointHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, PointHistoryActivity.class);
                        activity.startActivity(intent);
                    }
                });

                break;
            }
        }

    }
}
