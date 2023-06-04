package com.kyarlay.ayesunaing.fragment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.flurry.android.FlurryAgent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.MainSuperActivity;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.PointHistoryActivity;
import com.kyarlay.ayesunaing.activity.ProfileActivity;
import com.kyarlay.ayesunaing.activity.SearchActivity;
import com.kyarlay.ayesunaing.custom_widget.CircularNetworkImageView;
import com.kyarlay.ayesunaing.custom_widget.CustomButton;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.ToastHelper;
import com.kyarlay.ayesunaing.lucky_wheel.ExplosionField;
import com.kyarlay.ayesunaing.lucky_wheel.LuckyItem;
import com.kyarlay.ayesunaing.lucky_wheel.LuckyWheelView;
import com.kyarlay.ayesunaing.object.Addresses;
import com.kyarlay.ayesunaing.object.Brand;
import com.kyarlay.ayesunaing.object.Campaign;
import com.kyarlay.ayesunaing.object.Category;
import com.kyarlay.ayesunaing.object.CategoryMain;
import com.kyarlay.ayesunaing.object.FlashList;
import com.kyarlay.ayesunaing.object.FlashSaleListObject;
import com.kyarlay.ayesunaing.object.MainBrand;
import com.kyarlay.ayesunaing.object.MainCategoryObj;
import com.kyarlay.ayesunaing.object.MainItem;
import com.kyarlay.ayesunaing.object.MainMallGrid;
import com.kyarlay.ayesunaing.object.MainObject;
import com.kyarlay.ayesunaing.object.MallGridObject;
import com.kyarlay.ayesunaing.object.MemberBenefitObject;
import com.kyarlay.ayesunaing.object.Order;
import com.kyarlay.ayesunaing.object.OrderDetailsObj;
import com.kyarlay.ayesunaing.object.PointWheel;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.object.TownShip;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.ClickeListener;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.MainGridItemClickListener;
import com.kyarlay.ayesunaing.operation.MallAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.myatminsoe.mdetect.MDetect;

/**
 * Created by ayesunaing on 8/23/16.
 */
public class FragmentMall extends Fragment implements ConstantVariable, Constant {

    private static final String TAG = "FragmentMall";

    // widget
    CustomTextView  txtAllViewAll;
    TextView txtHidden;
    LinearLayout point_layout;
    CustomTextView point_text;
    ImageView search, notifications;
    NetworkImageView profileIcon;
    RecyclerViewHeader header;
    RecyclerView recyclerHeader;
    LinearLayout linearHeader,linearTownship;
    CustomTextView txtTitle,txtTownship;
    ImageView imgViewAll;
    TextView viewHidden;
    FloatingActionButton fab ;
    ProgressBar progressBar;


    // variable
    RecyclerView recyclerView ;
    MallAdapter adapter;
    ArrayList<UniversalPost> universalPosts;
    List<Product> mainProducts = new ArrayList<>();
    List<Product> scrollProducts = new ArrayList<>();
    AppCompatActivity activity;
    MyPreference prefs;
    Resources resources;
    DatabaseAdapter databaseAdapter;
    Boolean loading = true;
    Display display;
    MyListAdapter myListAdapter;
    List<Category> categoryList1 = new ArrayList<>();
    int selectedPosition = 0;
    boolean isClicked = false;
    ExplosionField mExplosionField;
    ImageLoader imageLoader;
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    Animation bounce;
    AnimatorSet animationSet;
    RecyclerView.LayoutManager manager;
    Map<Integer, CountDownTimer> countDownMap = new HashMap<Integer, CountDownTimer>();
    String[] strNav = {"flashsale", "promotions","brands","new_arrival"};

    List<Product> productTempList = new ArrayList<>();
    boolean isMore = false;


    @AddTrace(name = "FragmentOne")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();

        databaseAdapter = new DatabaseAdapter(activity);
        universalPosts = new ArrayList<>();

        new MyFlurry(getActivity());
        prefs            = new MyPreference(getActivity());
        Context context = LocaleHelper.setLocale(getActivity(), prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();
        display     = activity.getWindowManager().getDefaultDisplay();
        imageLoader         = AppController.getInstance().getImageLoader();

        mExplosionField = ExplosionField.attach2Window(activity);

        View rootView   = inflater.inflate(R.layout.fragmentmall, container, false);
        recyclerView    = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        search          = rootView.findViewById(R.id.search);
        progressBar     = (ProgressBar) rootView.findViewById(R.id.progressBar1);




        profileIcon    = (CircularNetworkImageView) rootView.findViewById(R.id.profileIcon);
        point_layout    = (LinearLayout) rootView.findViewById(R.id.point_layout);


        point_text      = (CustomTextView) rootView.findViewById(R.id.point_text);
        txtHidden      =  rootView.findViewById(R.id.txtHidden);
        notifications      =  rootView.findViewById(R.id.notifications);
       // cardViewHidden      =  rootView.findViewById(R.id.cardViewHidden);

        adapter = new MallAdapter(activity, universalPosts, countDownMap);
        display = activity.getWindowManager().getDefaultDisplay();
        fab = rootView.findViewById(R.id.fab);

       // prefs.saveIntPerferences(SP_TOWNSHIP_STORE_ID, 0);



        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);

        manager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        header              = (RecyclerViewHeader) rootView.findViewById(R.id.header);
        linearHeader              =  rootView.findViewById(R.id.linearHeader);
        recyclerHeader = rootView.findViewById(R.id.recyclerHeader);
        txtAllViewAll = rootView.findViewById(R.id.txtAllViewAll);

        linearTownship = rootView.findViewById(R.id.linearTownship);
        txtTitle = rootView.findViewById(R.id.txtTitle);
        txtTownship = rootView.findViewById(R.id.txtTownship);
        viewHidden = rootView.findViewById(R.id.viewHidden);
        imgViewAll = rootView.findViewById(R.id.imgViewAll);


        categoryList();



        if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

            getAddressList();
        }


        myListAdapter = new MyListAdapter( categoryList1);
        recyclerHeader.setLayoutManager(new GridLayoutManager(activity, 1, RecyclerView.HORIZONTAL, false));
        recyclerHeader.setAdapter(myListAdapter);

        progressBar.setVisibility(View.VISIBLE);
        setup();

        txtTownship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TownShip> townShipList = databaseAdapter.getTownShipByList();
                if (townShipList.size() > 0){
                    showTownShipPopup(townShipList);
                }
            }
        });


        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


        imgViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("super_category_id", String.valueOf(categoryList1.get(selectedPosition).getId()));
                    mix.put("source", "product_list");
                    FlurryAgent.logEvent("Click Super Category", mix);
                } catch (Exception e) {
                }

                Intent intent = new Intent(activity, MainSuperActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", categoryList1.get(selectedPosition).getName());
                bundle.putInt("id", categoryList1.get(selectedPosition).getId());
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        });






        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
               /* try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("item", "cs_chat");
                    FlurryAgent.logEvent("Click Bottom Navigation Item", mix);

                } catch (Exception e) {
                }



                if(prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                        prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0){
                    prefs.saveIntPerferences(SP_FRESH_CHAT_UNREAD_COUNT, 0);



                    FreshchatNotificationConfig notificationConfig = new FreshchatNotificationConfig()
                            .setNotificationSoundEnabled(true)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setLargeIcon(R.drawable.ic_launcher)
                            .launchActivityOnFinish(MainActivity.class.getName())
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    Freshchat.getInstance(activity).setNotificationConfig(notificationConfig);


                    try{
                        FreshchatConfig freshchatConfig=new FreshchatConfig(BuildConfig.FRESH_CAHT_ID,BuildConfig.FRESH_CHAT_KEY);
                        Freshchat.getInstance(activity).init(freshchatConfig);
                        Freshchat.getInstance(activity).identifyUser(String.valueOf(prefs.getIntPreferences(SP_MEMBER_ID)), prefs.getStringPreferences(SP_USER_FRESH_CHAT_ID));
                        FreshchatUser freshUser=Freshchat.getInstance(activity).getUser();
                        freshUser.setFirstName(prefs.getStringPreferences(SP_USER_NAME+""));
                        freshUser.setEmail(prefs.getStringPreferences(SP_USER_TOKEN));
                        freshUser.setPhone("+95", prefs.getStringPreferences(SP_USER_PHONE));
                        Freshchat.getInstance(activity).setUser(freshUser);
                        Freshchat.showConversations(activity);
                    }catch (Exception e){

                    }

                }else{
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    startActivity(intent);
                }
*/



            }
        });

        recyclerView.addOnItemTouchListener(new MainGridItemClickListener(activity,
                recyclerView, universalPosts, new ClickeListener() {
            @Override
            public void onClick(View view, int position) {
                if (universalPosts.get(position).getPostType().equals(REFRESH_FOOTER)){
                    if (prefs.isNetworkAvailable()){

                        universalPosts.remove(position);
                        Product noitem = new Product();
                        noitem.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(noitem);
                        //adapter.notifyItemInserted(universalPosts.size());
                        getPromoteProduct();
                    }else{

                        ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

                    }
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }

        }));

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if(newState == RecyclerView.SCROLL_STATE_IDLE ) {
                    fab.setVisibility(View.GONE);

                }
                else{
                    fab.setVisibility(View.GONE);
                }

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();


                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {

                    loading = true;
                    getPromoteProduct();

                }

            }
        });


        point_text.setText(formatter.format(prefs.getIntPreferences(SP_USER_POINT)));
        point_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    try {

                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "main_list");
                        FlurryAgent.logEvent("Click Point Detail", mix);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: "  + e.getMessage() );
                    }


                    Intent intent   = new Intent(getActivity(), PointHistoryActivity.class);
                    getActivity().startActivity(intent);
                }else{
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    activity.startActivity(intent);
                }

            }
        });



        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(activity, ProfileActivity.class);
                startActivity(intent);
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("type", "main");
                    FlurryAgent.logEvent("Click Search", mix);
                } catch (Exception e) {
                    Log.e(TAG, "onClick: "  + e.getMessage() );
                }




                Intent intent = new Intent(activity, SearchActivity.class);
                startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fate_out);
            }
        });


        return rootView;
    }


    private void setup(){
        loading = true;

        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
            universalPosts.remove(universalPosts.size() - 1);
        }


        if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
              if(prefs.getIntPreferences(ConstantVariable.SP_TOWNSHIP_STORE_ID) == 0) {


                  List<TownShip> townShipList = databaseAdapter.getTownShipByList();
                  if (townShipList.size() > 0) {
                      showTownShipPopup(townShipList);
                  }
                  setUpToolbar();
              }
              else{
                if(prefs != null && prefs.getStringPreferences(SP_USER_TOKEN) != null &&
                        prefs.getStringPreferences(SP_USER_TOKEN).trim().length() > 0 &&
                        prefs.getIntPreferences(SP_NET_POINT_SHOW) != Calendar.getInstance().get(Calendar.DATE)){

                    getWheelList();
                }

                  setupUI();
             }
        }
        else{
            setupUI();
        }

    }

    private void setUpToolbar(){


        if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0 && prefs.getIntPreferences(ConstantVariable.SP_TOWNSHIP_STORE_ID) != 0){
            linearTownship.setVisibility(View.VISIBLE);
            viewHidden.setVisibility(View.VISIBLE);
            txtTitle.setText(": Deliver to ");
            txtTownship.setText(prefs.getStringPreferences(SP_TOWNSHIP_NAME) + resources.getString(R.string.township));
            //txtTownship.setTypeface(txtTownship.getTypeface(), Typeface.BOLD);


        }
        else{
            linearTownship.setVisibility(View.GONE);
            viewHidden.setVisibility(View.GONE);
        }




    }


    private void getHotCategories()
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantHotCategory + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }



                        if(response.length() > 0) {


                            ArrayList<CategoryMain> categoryList = new ArrayList<>();

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<CategoryMain>>() {}.getType();
                                categoryList = mGson.fromJson(response.toString(), listType);




                                if (categoryList.size() > 0){




                                    MainCategoryObj mainCategoryObj = new MainCategoryObj();
                                    mainCategoryObj.setCategoryMainList(categoryList);
                                    mainCategoryObj.setPostType(HOT_CATEGORY_MAIN);
                                    universalPosts.add(mainCategoryObj);
                                }

                            }catch (Exception e){

                                Log.e(TAG, "onResponse: "   + e.getMessage() );
                            }


                        }
                        Product pro1 = new Product();
                        pro1.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro1);
                        adapter.notifyItemInserted(universalPosts.size());

                        getBrands();




                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getBrands();



            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void showTownShipPopup(List<TownShip> townShipList) {

        if (townShipList.size() > 0) {

            Dialog mBottomSheetDialog = new Dialog(activity);

            mBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mBottomSheetDialog.setContentView(R.layout.bottom_dialog_pickup_shop);
            mBottomSheetDialog.setCanceledOnTouchOutside(true);

            Window window = mBottomSheetDialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
            window.setAttributes(wlp);


            LinearLayout linearTownship = mBottomSheetDialog.findViewById(R.id.linearTownship);
            linearTownship.setVisibility(View.VISIBLE);
            CustomTextView title = mBottomSheetDialog.findViewById(R.id.title);
            CustomTextView txtYgn = mBottomSheetDialog.findViewById(R.id.txtYgn);
            CustomTextView txtOther = mBottomSheetDialog.findViewById(R.id.txtOther);
            ImageView imgClose = mBottomSheetDialog.findViewById(R.id.imgClose);
            LinearLayout linearAdd = mBottomSheetDialog.findViewById(R.id.linearAdd);



            title.setText("Choose Township");
            title.setTypeface(title.getTypeface(), Typeface.BOLD);

            txtOther.setText(townShipList.get(0).getName());
            txtYgn.setText(townShipList.get(1).getName());



            txtOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                    universalPosts.clear();

                    prefs.saveIntPerferences(SP_TOWNSHIP_ID,townShipList.get(0).getId() );
                    prefs.saveStringPreferences(SP_TOWNSHIP_NAME,townShipList.get(0).getName() );
                    prefs.saveIntPerferences(SP_TOWNSHIP_STORE_ID,townShipList.get(0).getStore_location_id());







                    Product noitem = new Product();
                    noitem.setPostType(CART_DETAIL_FOOTER);
                    universalPosts.add(noitem);
                    adapter.notifyDataSetChanged();


                    prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);

                    setup();




                }
            });



            for (int i=2; i < townShipList.size(); i++){
                TownShip shopLocation = townShipList.get(i);

                LinearLayout phoneLayout = new LinearLayout(activity);
                phoneLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                phoneLayout.setOrientation(LinearLayout.HORIZONTAL);
                phoneLayout.setPadding(0, 20, 0, 20);


                LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                childParam1.weight = 0.1f;
                CustomTextView price = new CustomTextView(activity);
                price.setTextSize(14);
                price.setPadding(10, 10, 10, 10);
                price.setGravity(Gravity.CENTER);
                price.setText(shopLocation.getName());
                price.setLayoutParams(childParam1);



                phoneLayout.addView(price);
                phoneLayout.setWeightSum(1f);


                linearAdd.addView(phoneLayout);

                phoneLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBottomSheetDialog.dismiss();
                                                prefs.saveIntPerferences(SP_TOWNSHIP_ID, shopLocation.getId());
                        prefs.saveStringPreferences(SP_TOWNSHIP_NAME, shopLocation.getName() );
                        prefs.saveIntPerferences(SP_TOWNSHIP_STORE_ID,shopLocation.getStore_location_id() );


                        universalPosts.clear();
                        //adapter.stopHandler();
                        Product noitem = new Product();
                        noitem.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(noitem);
                        adapter.notifyDataSetChanged();


                        prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, SP_DEFAULT);

                        setup();

                    }
                });


            }


            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });
            mBottomSheetDialog.show();


        }



    }

    private void setupUI(){

        getPaymentText();
        if (prefs.isNetworkAvailable()){
            progressBar.setVisibility(View.GONE);

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
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_FOOTER);
            universalPosts.add(noitem);
            adapter.notifyDataSetChanged();



            getBonouosBanner();

        }else{
            progressBar.setVisibility(View.GONE);
            Product noitem = new Product();
            noitem.setPostType(CART_DETAIL_NO_ITEM);
            universalPosts.add(noitem);

            ToastHelper.showToast( activity, resources.getString(R.string.no_internet_error));

        }



    }

    private void customScrollToPosition(String tag){

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int indexFirst = linearLayoutManager.findFirstVisibleItemPosition();

        int index=0;

        for (int i=0; i < universalPosts.size(); i++){
            if (universalPosts.get(i).getPostType().equals(CATEGORY_BANNER)){

                MainCategoryObj mainCategoryObj = (MainCategoryObj) universalPosts.get(i);

                if (mainCategoryObj.getTag() != null){

                    if (mainCategoryObj.getTag().equals(tag)){

                        index = i;

                        break;
                    }
                }

            }
        }


        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(index, 0);

        adapter.notifyDataSetChanged();


    }

    private void categoryList(){


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantMainSuperCategory + "?language=" + prefs.getStringPreferences(SP_LANGUAGE)  ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(response.length() > 0) {


                            ArrayList<MainCategoryObj> categoryList = new ArrayList<>();

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<MainCategoryObj>>() {}.getType();
                                categoryList = mGson.fromJson(response.toString(), listType);

                                if (categoryList.size() > 0){

                                    for (int i=0; i < categoryList.size(); i++){
                                        List<CategoryMain> categoryMainList = new ArrayList<>();
                                        MainCategoryObj mainCategoryObj = categoryList.get(i);

                                        MainCategoryObj categoryMain = new MainCategoryObj();
                                        categoryMain.setId(mainCategoryObj.getId());
                                        categoryMain.setTitle(mainCategoryObj.getTitle());
                                        categoryMain.setTag(mainCategoryObj.getTag());
                                        categoryMain.setImage(mainCategoryObj.getImage());
                                        categoryMain.setImage_big(mainCategoryObj.getImage_big());
                                        categoryMain.setInactive_image(mainCategoryObj.getInactive_image());
                                        categoryMain.setHeader_image(mainCategoryObj.getHeader_image());
                                        categoryMain.setHeader_image_dimen(mainCategoryObj.getHeader_image_dimen());


                                        if (i == 0) {
                                            categoryMain.setShowTitle(2);

                                        }
                                        else {
                                            categoryMain.setShowTitle(1);
                                        }

                                        databaseAdapter.insertCategorySuperList(categoryMain);


                                    }

                                }




                            }catch (Exception e){

                                Log.e(TAG, "onResponse: mainCategoriesFromServer Exception : "  + e.getMessage() );
                            }


                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onResponse: mainCategoriesFromServer Exception : "  + error.getMessage() );

            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }


    public void getPaymentText(){


        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantPaymentText, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try{
                    if(  response.toString().length()  > 0){
                        prefs.saveStringPreferences(PAYMENTEXT, response.getString("desc"));
                    }

                }catch (Exception e){
                    Log.e(TAG, "onResponse: getWheelList Exception : "  + e.getMessage() );

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getWheelList Exception : "  + error.getMessage() );
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
    }



    public void getWheelList(){


        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.POST,
                default_api + "api/customers/"+ prefs.getIntPreferences(SP_MEMBER_ID) +"/daily_lucky_draw" + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try{

                    if (response.getInt("status") == 1){
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Type listType = new TypeToken<List<PointWheel>>() {
                        }.getType();
                        List<PointWheel> categoryList = mGson.fromJson(String.valueOf(response.getJSONArray("options")), listType);


                        if (categoryList.size() > 0){

                            sendWheelDialog(categoryList);
                        }
                    }

                }catch (Exception e){
                     Log.e(TAG, "onResponse: getWheelList Exception : "  + e.getMessage() );

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getWheelList Exception : "  + error.getMessage() );
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
    }

    public void getCustomerInfo(){
        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantGetCustomerInfo, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    prefs.saveIntPerferences(SP_USER_POINT, response.getInt("gift_points"));
                    prefs.saveIntPerferences(SP_KYARLAY_ACCESS, response.getInt("admin"));
                    prefs.saveIntPerferences(SP_ONE_TIME_FREE_DELIVERY, response.getInt("free_delivery"));
                    if (response.getString("vip").trim().length() == 0) {

                        prefs.saveIntPerferences(SP_VIP_ID, 0);
                    } else {

                        prefs.saveIntPerferences(SP_VIP_ID, 1);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: getCustomerInfo error "  + e.getLocalizedMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getCustomerInfo error "  + error.getLocalizedMessage() );
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
    }


    @Override
    public void onResume() {
        super.onResume();
        getCustomerInfo();


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

        point_text.setText(formatter.format(prefs.getIntPreferences(SP_USER_POINT))+"");

        point_text.setText(formatter.format(prefs.getIntPreferences(SP_USER_POINT))+"");

        removeitem();

        int count_wish1  = prefs.getIntPreferences(SP_FRESH_CHAT_UNREAD_COUNT);


        if (count_wish1  > 0){
            fab.setImageDrawable(activity.getResources().getDrawable(R.drawable.chat_plus));

        }else{
            fab.setImageDrawable(activity.getResources().getDrawable(R.drawable.chatting));
        }


        if(prefs.getBooleanPreference(LOGIN_MAIN)){
            loading = true;

            prefs.saveBooleanPreference(LOGIN_MAIN, false);
            setup();

        }

    }


    public void removeitem(){
        if(universalPosts.size() > 1) {

            for(int i = 0; i < universalPosts.size() ; i++){
                UniversalPost post = new UniversalPost();
                post = universalPosts.get(i);
                if (post.getPostType().equals(signup)) {
                    universalPosts.remove(i);
                    adapter.notifyItemRemoved(i);
                }else if(post.getPostType().equals(posting)) {
                    universalPosts.remove(i);
                    adapter.notifyItemRemoved(i);
                }else if(post.getPostType().equals(feedback)) {
                    universalPosts.remove(i);
                    adapter.notifyItemRemoved(i);
                }else if(post.getPostType().equals(invite)) {
                    universalPosts.remove(i);
                    adapter.notifyItemRemoved(i);
                }

            }

        }
    }



    private void getMainBanner()
    {


        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantMainBanner + "?language=" + prefs.getStringPreferences(SP_LANGUAGE),
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }

                        if(response.length() > 0) {

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<MainItem>>() {
                                }.getType();
                                List<MainItem> lists  = mGson.fromJson(response.toString(), listType);


                                if (lists.size() > 0){
                                    MainObject mainObject = new MainObject();
                                    mainObject.setItems(lists);
                                    mainObject.setFullSize(0);
                                    mainObject.setPostType(CUSTOM_SLIDER_MAIN);
                                    universalPosts.add(mainObject);

                                }

                                adapter.notifyItemInserted(universalPosts.size());

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: getMainBanner Exception : "  + e.getMessage() );

                            }

                        }

                        Product pro = new Product();
                        pro.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro);
                        adapter.notifyItemInserted(universalPosts.size());
                        getGridMall();

                       }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onResponse: getMainBanner Exception : "  + error.getMessage() );
                getGridMall();


            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }


    private void getGridMall()
    {

        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(constantDashBoard +  "?" + LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }


                        if(response.length() > 0) {



                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<MallGridObject>>() {
                                }.getType();
                                List<MallGridObject> lists  = mGson.fromJson(response.toString(), listType);

                                if(lists.size() > 0 ){
                                    MainMallGrid mallGrid = new MainMallGrid();
                                    mallGrid.setMallGridObjectList(lists);
                                    mallGrid.setPostType(MALL_GRID);
                                    universalPosts.add(mallGrid);
                                }

                                adapter.notifyItemInserted(universalPosts.size());


                            }catch (Exception e){
                                Log.e(TAG, "onResponse: getMainBanner Exception : "  + e.getMessage() );

                            }

                        }

                        Product pro = new Product();
                        pro.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro);
                        adapter.notifyItemInserted(universalPosts.size());

                        getBrandPatner();

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );
                getBrandPatner();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopHandler();
    }

    public void getFlashSales(){


        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMainFlashSale  + "?language=" + prefs.getStringPreferences(SP_LANGUAGE), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }

                if(response.length() > 0){



                    try{

                        MainCategoryObj obj = new MainCategoryObj();
                        obj.setHeader_image(response.getString("header_image"));
                        obj.setHeader_image_dimen(response.getInt("header_image_dimen"));
                        obj.setPostType(CATEGORY_BANNER);



                        JSONArray flashList = response.getJSONArray("collections");
                        List<FlashSaleListObject> categoryList  = new ArrayList<>(flashList.length());
                        Gson gson = new Gson();
                        for (int i = 0; i < flashList.length(); i++) {

                            JSONObject objFlash = flashList.getJSONObject(i);
                            FlashSaleListObject flashSaleListObject = gson.fromJson(objFlash.toString(), FlashSaleListObject.class);
                            categoryList.add(flashSaleListObject);
                        }


                        List<Product> proList = new ArrayList<>();
                        for (int i=0; i < categoryList.size(); i++){
                            Product product = categoryList.get(i).getProduct();
                            if (product.getFlashSalesArrayList().size() > 0){

                                product.setPostType(MALL_FLASH_SALE_ITEM);
                                proList.add(product);


                            }

                        }

                        FlashList mainObject = new FlashList();
                        mainObject.setTitle(obj.getHeader_image());
                        mainObject.setPostType(MAIN_FLASH_SALE);

                        if (proList.size() > 0){
                            mainObject.setProductArrayList(proList);
                            universalPosts.add(mainObject);

                        }

                    }catch (Exception e){
                        Log.e(TAG, "onResponse: getFlashSales Exception : "  + e.getMessage() );
                    }
                }

                Product pro = new Product();
                pro.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro);
                adapter.notifyItemInserted(universalPosts.size());

                getTopSeller();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getTopSeller();
                Log.e(TAG, "onResponse: getFlashSales Exception : "  + error.getMessage() );

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    public void getBrandPatner(){



        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMainBrandPartner + "?language=" + prefs.getStringPreferences(SP_LANGUAGE) , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }

                if(response.length() > 0){


                    try{

                        MainCategoryObj obj = new MainCategoryObj();
                        obj.setHeader_image(response.getString("header_image"));
                        obj.setHeader_image_dimen(response.getInt("header_image_dimen"));
                        obj.setPostType(CATEGORY_BANNER);



                        JSONArray flashList = response.getJSONArray("collections");
                        List<Brand> categoryList  = new ArrayList<>(flashList.length());
                        Gson gson = new Gson();


                        for (int i = 0; i < flashList.length(); i++) {

                            JSONObject objFlash = flashList.getJSONObject(i);
                            Brand flashSaleListObject = gson.fromJson(objFlash.toString(), Brand.class);
                            categoryList.add(flashSaleListObject);
                        }


                        if (categoryList.size() > 0){


                            MainBrand mainBrand = new MainBrand();
                            mainBrand.setTitle(resources.getString(R.string.brand_partner));

                            mainBrand.setItems(categoryList);
                            mainBrand.setUrl("");
                            mainBrand.setPostType(PARTNER_BRAND);
                            universalPosts.add(mainBrand);
                        }


                    }catch (Exception e){
                        Log.e(TAG, "onResponse: getBrands Exception : "  + e.getMessage() );
                    }
                }



                Product pro1 = new Product();
                pro1.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro1);
                adapter.notifyItemInserted(universalPosts.size());
                getNewArrival();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBrands Exception : "  + error.getMessage() );


                getNewArrival();


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    public void getBrands(){


        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMainPopularBrand + "?language=" + prefs.getStringPreferences(SP_LANGUAGE) , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {




                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }


                if(response.length() > 0){


                    try{

                        MainCategoryObj obj = new MainCategoryObj();
                        obj.setHeader_image(response.getString("header_image"));
                        obj.setHeader_image_dimen(response.getInt("header_image_dimen"));
                        obj.setPostType(CATEGORY_BANNER);



                        JSONArray flashList = response.getJSONArray("collections");
                        List<Brand> categoryList  = new ArrayList<>(flashList.length());
                        Gson gson = new Gson();


                        for (int i = 0; i < flashList.length(); i++) {

                            JSONObject objFlash = flashList.getJSONObject(i);
                            Brand flashSaleListObject = gson.fromJson(objFlash.toString(), Brand.class);
                            categoryList.add(flashSaleListObject);
                        }


                        if (categoryList.size() > 0){

                            //universalPosts.add(obj);

                            MainBrand mainBrand = new MainBrand();
                            mainBrand.setTitle(resources.getString(R.string.brand_store));

                            mainBrand.setItems(categoryList);
                            mainBrand.setUrl("");
                            mainBrand.setPostType(RECOMMEND_BRAND);
                            universalPosts.add(mainBrand);
                        }







                    }catch (Exception e){
                        Log.e(TAG, "onResponse: getBrands Exception : "  + e.getMessage() );
                    }
                }


                Product pro = new Product();
                pro.setPriId(1);
                pro.setPostType(POPULAR_BANNER);
                pro.setTitle(resources.getString(R.string.you_may_like));
                universalPosts.add(pro);

                Product pro1 = new Product();
                pro1.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro1);
                adapter.notifyItemInserted(universalPosts.size());

                getPromoteProduct();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getPromoteProduct();


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }



    @Override
    public void onDestroyView() {

        if (countDownMap == null) {

            return;
        }else
        {

            for(CountDownTimer timer : this.countDownMap.values()){
                timer.cancel();
            }

        }

        super.onDestroyView();
    }

    public void getMemberInfo(){
        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMemberInfo + "?" +   LANG + "=" + prefs.getStringPreferences(SP_LANGUAGE)
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }


                if(response.length() > 0) {



                    try {

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Type listType = new TypeToken<MemberBenefitObject>() {}.getType();
                        MemberBenefitObject memberBenefitObject = mGson.fromJson(response.toString(), listType);
                        if (memberBenefitObject.getStatus() == 1){
                            memberBenefitObject.setPostType(POINT_DASHBOARD);
                            universalPosts.add(memberBenefitObject);
                        }



                    }catch (Exception e){
                        Log.e(TAG, "onResponse: "   + e.getMessage() );
                    }

                }




                try {

                    Product pro = new Product();
                    pro.setPostType(CART_DETAIL_FOOTER);
                    universalPosts.add(pro);
                    adapter.notifyItemInserted(universalPosts.size());
                    orderedList("pending");

                } catch (Exception e) {
                    Log.e(TAG, "onResponse: "  + e.getMessage() );

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                orderedList("pending");
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "update_profile");
    }



    public void getBonouosBanner(){



        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                String.format(constantNewBonusBanner,prefs.getIntPreferences(SP_CURRENT_VERSION)+ "" ,prefs.getStringPreferences(SP_USER_PHONE), ""), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }

                if(response.length() > 0){


                    try {
                        if(response.getInt("status") != 0){
                            Reading reading  = new Reading();
                            reading.setPreview_image_url(response.getString("img_url"));
                            reading.setId(response.getInt("status"));
                            reading.setPostType(response.getString("type"));
                            reading.setDimen(response.getInt("img_dimen"));
                            if (response.getString("type").equals("signup") && prefs.getIntPreferences(SP_MEMBER_ID) == 0 ){
                                universalPosts.add(reading);
                            }



                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: getBonouosBanner Exception : "  + e.getMessage() );


                    }

                }


                Product pro = new Product();
                pro.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro);
                adapter.notifyItemInserted(universalPosts.size());

                getMemberInfo();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getBonouosBanner Exception : "  + error.getMessage() );
                getMemberInfo();

            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");


    }

    public void setNetPointtoClaim(final int indexID){

        JSONObject earnPoint = new JSONObject();
        try {
            earnPoint.put("type",  "daily");
            earnPoint.put("daily_lucky_draw_id",  indexID);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, String.format(constantEarnPoint, prefs.getIntPreferences(SP_MEMBER_ID)), earnPoint,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            prefs.saveIntPerferences(SP_USER_POINT, response.getInt("point"));
                            point_text.setText(formatter.format(prefs.getIntPreferences(SP_USER_POINT)));
                            prefs.saveIntPerferences(SP_NET_POINT_SHOW, Calendar.getInstance().get(Calendar.DATE));
                            bouncePoint(response.getInt("point"));
                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: setNetPointtoClaim"  + e.getMessage() );
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: setNetPointtoClaim"  + error.getMessage() );
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"set_net_point");



    }


    private void getDiscountList(){


        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMainDiscountList +  "?language=" + prefs.getStringPreferences(SP_LANGUAGE) , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }

                if(response.length() > 0){

                    try{

                        MainCategoryObj obj = new MainCategoryObj();
                        obj.setHeader_image(response.getString("header_image"));
                        obj.setHeader_image_dimen(response.getInt("header_image_dimen"));
                        obj.setPostType(CATEGORY_BANNER);



                        JSONArray flashList = response.getJSONArray("collections");
                        List<Campaign> categoryList  = new ArrayList<>(flashList.length());



                        Gson gson = new Gson();
                        for (int i = 0; i < flashList.length(); i++) {

                            JSONObject objFlash = flashList.getJSONObject(i);
                            Campaign flashSaleListObject = gson.fromJson(objFlash.toString(), Campaign.class);
                            if (flashSaleListObject.getUrl() != null && !(flashSaleListObject.getUrl().equals(""))) {
                                categoryList.add(flashSaleListObject);
                            }
                        }

                        if (categoryList.size() > 0){


                            MainObject mainObject = new MainObject();
                            mainObject.setTitle(resources.getString(R.string.discount_title));
                            mainObject.setPostType(MAIN_DISCOUNT);


                            List<MainItem> items = new ArrayList<>();
                            for (int i = 0; i < categoryList.size(); i++) {
                                Campaign campaign = new Campaign();
                                campaign = categoryList.get(i);
                                campaign.setPostType(CAMPAIGN);
                                //disLists.add(campaign);


                                MainItem mainItem = new MainItem();
                                mainItem.setDimen(campaign.getDimen());
                                mainItem.setId(campaign.getId());
                                mainItem.setTitle(campaign.getTitle());
                                mainItem.setUrl(campaign.getUrl());
                                mainItem.setPostType(CAMPAIGN);
                                items.add(mainItem);
                                mainObject.setItems(items);

                            }
                            universalPosts.add(mainObject);

                        }


                    }catch (Exception e){
                        Log.e(TAG, "onResponse: getDiscountList Exception : "  + e.getMessage() );
                    }
                }

                Product noitem = new Product();
                noitem.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(noitem);
                adapter.notifyItemInserted(universalPosts.size());


                getCollectionList();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getDiscountList Exception : "  + error.getMessage() );
                getCollectionList();


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }

    private void getCollectionList(){



        JsonObjectRequest apkDownloadRequest = new JsonObjectRequest(Request.Method.GET,
                constantMainCollectionsList  + "?language=" + prefs.getStringPreferences(SP_LANGUAGE)  , null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }


                if(response.length() > 0){


                    try{

                     /*   MainCategoryObj obj = new MainCategoryObj();
                        obj.setHeader_image(response.getString("header_image"));
                        obj.setHeader_image_dimen(response.getInt("header_image_dimen"));
                        obj.setPostType(CATEGORY_BANNER);


*/
                        JSONArray flashList = response.getJSONArray("collections");
                        List<Campaign> categoryList  = new ArrayList<>(flashList.length());



                        Gson gson = new Gson();
                        for (int i = 0; i < flashList.length(); i++) {

                            JSONObject objFlash = flashList.getJSONObject(i);
                            Campaign flashSaleListObject = gson.fromJson(objFlash.toString(), Campaign.class);
                            if (flashSaleListObject.getUrl() != null && !(flashSaleListObject.getUrl().equals(""))) {
                                categoryList.add(flashSaleListObject);
                            }
                        }



                        if (categoryList.size() > 0){

                           // universalPosts.add(obj);

                            MainObject mainObject = new MainObject();
                            mainObject.setTitle("Current Collection");
                            mainObject.setPostType(NEW_COLLECTION_LIST);


                            List<MainItem> items = new ArrayList<>();
                            for (int i = 0; i < categoryList.size(); i++) {
                                Campaign campaign = new Campaign();
                                campaign = categoryList.get(i);
                                campaign.setPostType(COLLECTIONS_LIST_ITEM);


                                MainItem mainItem = new MainItem();
                                mainItem.setDimen(campaign.getDimen());
                                mainItem.setId(campaign.getId());
                                mainItem.setTitle(campaign.getTitle());
                                mainItem.setUrl(campaign.getUrl());
                                mainItem.setPostType(COLLECTIONS_LIST_ITEM);
                                items.add(mainItem);
                                mainObject.setItems(items);

                            }
                            universalPosts.add(mainObject);

                        }


                    }catch (Exception e){
                        Log.e(TAG, "onResponse: getCollectionList Exception : "  + e.getMessage() );
                    }
                }

                Product pro = new Product();
                pro.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(pro);
                adapter.notifyItemInserted(universalPosts.size());

                getHotCategories();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onResponse: getCollectionList Exception : "  + error.getMessage() );
                getHotCategories();


            }
        });
        AppController.getInstance().addToRequestQueue(apkDownloadRequest, "VersionDownload");
    }



    public void bouncePoint (int count){
        bounce = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                R.anim.bounce_animation);
        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(activity.getApplicationContext()
                , R.animator.flip_animation);
        point_text.setText(count + "");
        animationSet.setTarget(point_text);
        point_text.startAnimation(bounce);
        animationSet.start();
    }

    private void getTopSeller()
    {

        String url  = constantProductTopList +   "language=" + prefs.getStringPreferences(SP_LANGUAGE) ;


        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }

                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }

                        if(response.length() > 0) {

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> products = mGson.fromJson(response.toString(), listType);

                                OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                                orderDetailsObj.setTitle(resources.getString(R.string.top_seller));
                                orderDetailsObj.setUrl(url);
                                orderDetailsObj.setProductList(products);


                                orderDetailsObj.setPostType(NEW_ARRIVAL);

                                universalPosts.add(orderDetailsObj);
                                adapter.notifyItemInserted(universalPosts.size());



                            }catch (Exception e){

                            }

                        }

                        Product pro = new Product();
                        pro.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro);
                        adapter.notifyItemInserted(universalPosts.size());

                        getDiscountList();



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getDiscountList();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


    private void getNewArrival()
    {

        String url  = constantProductNewList +   "language=" + prefs.getStringPreferences(SP_LANGUAGE) ;

        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }

                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }

                        if(response.length() > 0) {

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> products = mGson.fromJson(response.toString(), listType);

                                OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                                orderDetailsObj.setTitle(resources.getString(R.string.news));
                                orderDetailsObj.setUrl(url);
                                orderDetailsObj.setProductList(products);


                                orderDetailsObj.setPostType(NEW_ARRIVAL);

                                universalPosts.add(orderDetailsObj);
                                //adapter.notifyItemInserted(universalPosts.size());



                            }catch (Exception e){
                                Log.e(TAG, "onResponse: getPromoteProduct Exception : "  + e.getMessage() );
                            }

                        }

                        Product pro = new Product();
                        pro.setPostType(CART_DETAIL_FOOTER);
                        universalPosts.add(pro);
                        adapter.notifyItemInserted(universalPosts.size());

                        getFlashSales();



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getFlashSales();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


    private void getPromoteProduct()
    {


        String url  = constantPopularProduct+"page="+prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION) +   "&language=" + prefs.getStringPreferences(SP_LANGUAGE) ;
        if (prefs.getIntPreferences(SP_MEMBER_ID) != 0){
            url = constantPopularProduct+"page="+prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL)+"&version="+prefs.getIntPreferences(SP_CURRENT_VERSION) + "&customer_id=" + prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) +   "&language=" + prefs.getStringPreferences(SP_LANGUAGE) ;
        }



        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {


                        if(response.length() > 0) {



                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }
                            loading = false;

                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> products = mGson.fromJson(response.toString(), listType);

                                if (isMore){

                                    if (productTempList.size()> 0)
                                        products.add(0, productTempList.get(productTempList.size()-1));
                                }


                                for (int i = 0; i < products.size(); i++) {

                                    isMore = false;

                                    if(i%2 != 0 && i!=0){

                                        List<Product> tempList1 = new ArrayList<>();
                                        tempList1.add(products.get(i-1));
                                        tempList1.add(products.get(i));
                                        OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                                        orderDetailsObj.setProductList(tempList1);
                                        orderDetailsObj.setPostType(STAGGERED_ITEM);

                                        universalPosts.add(orderDetailsObj);


                                    }
                                    else if(i %2 == 0 && i== products.size() - 1){

                                        isMore = true;
                                        productTempList.add(products.get(i));

                                    }

                                }
                                Product pro = new Product();
                                pro.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(pro);
                                adapter.notifyItemInserted(universalPosts.size());

                                prefs.saveIntPerferences(SP_PAGE_NUM_CARTDETAIL, prefs.getIntPreferences(SP_PAGE_NUM_CARTDETAIL) + SP_DEFAULT);

                            }catch (Exception e){
                                Log.e(TAG, "onResponse: getPromoteProduct Exception : "  + e.getMessage() );
                            }

                        }else{
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            if (isMore) {

                                if (productTempList.size() > 0) {
                                    List<Product> tempList1 = new ArrayList<>();
                                    tempList1.add(0, productTempList.get(productTempList.size() - 1));
                                    OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                                    orderDetailsObj.setProductList(tempList1);
                                    orderDetailsObj.setPostType(STAGGERED_ITEM);


                                    isMore = false;

                                }
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);

                            adapter.notifyItemInserted(universalPosts.size());
                            progressBar.setVisibility(View.GONE);
                        }



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading = false;
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_NO_ITEM)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(REFRESH_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }
                Product noitem = new Product();
                noitem.setPostType(REFRESH_FOOTER);
                universalPosts.add(noitem);

                adapter.notifyItemInserted(universalPosts.size());

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


    private void sendWheelDialog(List<PointWheel> pointWheelList){


        int index = -1, id = 0;
        int amount = 0;
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_wheel_view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width   = activity.getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(wlp);

        final LuckyWheelView luckyWheelView = dialog.findViewById(R.id.luckyWheel);


        final LinearLayout linearResult = dialog.findViewById(R.id.linearResult);
        final LinearLayout linearWheel = dialog.findViewById(R.id.linearWheel);
        final TextView txtResult = dialog.findViewById(R.id.txtResult);
        final CustomTextView txtResultText = dialog.findViewById(R.id.txtResultText);
        final CustomTextView txtClose = dialog.findViewById(R.id.txtClose);
        final CustomButton play = dialog.findViewById(R.id.play);


        play.setText(resources.getString(R.string.play_wheel));

        final List<LuckyItem> data = new ArrayList<>();

        for (int i=0; i < pointWheelList.size(); i++){

            LuckyItem luckyItem1 = new LuckyItem();
            luckyItem1.topText = pointWheelList.get(i).getTitle();

            if (pointWheelList.get(i).getSelected() == 1){
                index = i;
                amount = pointWheelList.get(i).getAmount();
                id = pointWheelList.get(i).getId();
            }

            if (i %2 == 0)
                luckyItem1.color = 0xffFFF3E0;

            data.add(luckyItem1);

        }



        luckyWheelView.setData(data);
        luckyWheelView.setRound(7);
        // luckyWheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
        luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
        luckyWheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.mipmap.icon));


        luckyWheelView.setLuckyWheelCursorImage(R.drawable.down_arrow);





        if (index != -1){
            luckyWheelView.setPredeterminedNumber(index);
        }


        final int finalIndex = index;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (finalIndex != -1){
                    luckyWheelView.startLuckyWheelWithTargetIndex(finalIndex);
                }


                luckyWheelView.setTouchEnabled(false);
                dialog.findViewById(R.id.play).setClickable(false);
            }
        });


        final int finalAmount = amount;
        final int finalId = id;
        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {

                luckyWheelView.setTouchEnabled(false);
                dialog.findViewById(R.id.play).setClickable(false);

                mExplosionField.explode(linearWheel);




                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        linearResult.setVisibility(View.VISIBLE);
                        txtResult.setText( String.format(resources.getString(R.string.got_point), finalAmount + ""));
                        txtResultText.setText( String.format(resources.getString(R.string.claim_text), prefs.getIntPreferences(ConstantVariable.SP_POINT_PERCENTAGE) + ""));

                        bounce = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                                R.anim.bounce_animation);
                        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(activity.getApplicationContext()
                                , R.animator.flip_animation);


                        animationSet.setTarget(linearResult);
                        linearResult.startAnimation(bounce);
                        animationSet.start();


                        setNetPointtoClaim(finalId);


                    }
                }, 1000);
            }
        });

        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }



    public void orderedList(final String type){




        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(String.format(constantOrderedlist, SP_DEFAULT, type) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) ,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e(TAG, "onResponse: " +  String.format(constantOrderedlist, SP_DEFAULT, type) +"&"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE) );

                        Log.e(TAG, "onResponse: " + response.toString() );


                        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                            universalPosts.remove(universalPosts.size() - 1);
                        }


                        progressBar.setVisibility(View.GONE);

                        if(response.length() > 0) {


                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Order>>() {
                                }.getType();
                                List<Order> categoryList = mGson.fromJson(response.toString(), listType);



                                if (categoryList.size() > 0){

                                    if (type.equals("pending")){


                                        categoryList.get(0).setPostType(ORDER_PENDING_TRACK);
                                        categoryList.get(0).setPendingCount(categoryList.size());
                                        universalPosts.add(categoryList.get(0));

                                    }
                                    else if (type.equals("completed") ){


                                        if (categoryList.get(0).getStatus().equals("completed") && categoryList.get(0).getFeedback_point() == 0){
                                            categoryList.get(0).setPostType(ORDER_PENDING);
                                            universalPosts.add(categoryList.get(0));
                                        }

                                    }

                                    Product noitem = new Product();
                                    noitem.setPostType(CART_DETAIL_FOOTER);
                                    universalPosts.add(noitem);

                                    adapter.notifyItemInserted(universalPosts.size());

                                    getMainBanner();


                                }




                            }catch (Exception e){
                                Log.e(TAG, "onResponse: orderedList Exception ******** : "  + e.getMessage() );
                            }
                        }else{

                            if (type.equals("pending")){
                                orderedList("completed");
                            }else if(type.equals("completed")){

                                Product noitem = new Product();
                                noitem.setPostType(CART_DETAIL_FOOTER);
                                universalPosts.add(noitem);
                                adapter.notifyItemInserted(universalPosts.size());


                                getMainBanner();
                            }

                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
             /*   Product noitem = new Product();
                noitem.setPostType(CART_DETAIL_FOOTER);
                universalPosts.add(noitem);
                adapter.notifyItemInserted(universalPosts.size());*/
                getMainBanner();
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }



    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        List<Category> gridList = new ArrayList<>();

        public MyListAdapter(List<Category> gridList) {
            this.gridList = gridList;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.category_tab_sticky, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Category obj =( Category) gridList.get(position);
            holder.textView.setText(gridList.get(position).getName());
            Typeface myTypeface;
            if (MDetect.INSTANCE.isUnicode()){
                myTypeface = AppController.typefaceManager.getCompanion().getPYIDAUNGSU();
            }
            else{
                myTypeface = AppController.typefaceManager.getCompanion().getZAWGYITWO();
            }

            holder.textView.setTypeface(myTypeface);
            holder.textView.setText(obj.getName());


            if (selectedPosition == position)
                holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
            else
                holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_grey));


            //holder.textView.setTypeface(holder.textView.getTypeface(), Typeface.BOLD);

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    selectedPosition = position;

                    if (selectedPosition == position)
                        holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_yellow));
                    else
                        holder.textView.setBackground(activity.getResources().getDrawable(R.drawable.checked_bg_grey));

                    myListAdapter.notifyDataSetChanged();
                    // recyclerHeader.scrollToPosition(position);

                    isClicked = true;
                    customScrollToPosition(categoryList1.get(position).getTag());

                }
            });

        }


        @Override
        public int getItemCount() {
            return gridList.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.textView = (TextView) itemView.findViewById(R.id.txt_tab_sticky);

            }
        }
    }


    private void getAddressList()
    {



        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest( constantAddressList + "?"+LANG+"="+prefs.getStringPreferences(SP_LANGUAGE),

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {



                        if(response.length() > 0) {

                            GsonBuilder builder = new GsonBuilder();
                            Gson mGson = builder.create();
                            Type listType = new TypeToken<List<Addresses>>() {}.getType();
                            final List<Addresses> list = mGson.fromJson(response.toString(), listType);

                            if (list.size() > 0) {
                                databaseAdapter.insertCustomerAddress(list);

                            }


                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onCreateView: ***************************** error getAddressList " + error.getMessage()) ;
            }
        }){

            /*
             *
             * Passing some request headers
             */


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getStringPreferences(SP_USER_PHONE));
                headers.put("X-Customer-Token", prefs.getStringPreferences(SP_USER_TOKEN));
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


}
