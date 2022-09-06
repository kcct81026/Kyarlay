package com.kyarlay.ayesunaing.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.flurry.android.FlurryAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAdsList extends AppCompatActivity implements ConstantVariable, Constant {

    RecyclerView recyclerView;
    UniversalAdapter universalAdapter;
    ArrayList<UniversalPost> mainCatDetails ;
    RecyclerView.LayoutManager manager;
    DatabaseAdapter databaseAdapter;

    MyPreference prefs;
    CustomTextView no_list, titile;
    String fromClass;
    Display display ;

    CustomTextView title;
    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;
    CircularTextView cart_text, wishlist_count;
    RelativeLayout layout_like;
    ImageView cart;

    Animation bounce;
    AnimatorSet animationSet;

    String url= "";

    private static final String TAG = "ActivityAdsList";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ads_list_activity);

        display         = getWindowManager().getDefaultDisplay();


        prefs  = new MyPreference(ActivityAdsList.this);
        mainCatDetails = new ArrayList<UniversalPost>();
        Intent intent = getIntent();
        // mainCatDetails = intent.getParcelableArrayListExtra("product");
        fromClass       = intent.getStringExtra("fromClass");



        if (fromClass.equals(FROM_FIREBASE)){
            url = intent.getStringExtra("url");
        }
        else{
            mainCatDetails = intent.getParcelableArrayListExtra("product");
        }

        databaseAdapter = new DatabaseAdapter(ActivityAdsList.this);

        new MyFlurry(ActivityAdsList.this);

        no_list  = (CustomTextView) findViewById(R.id.no_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        titile      = (CustomTextView) findViewById(R.id.title);
        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        layout_like     = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count  = (CircularTextView) findViewById(R.id.wishlist_count);

        layout_like.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;


        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    Map<String, String> userParams = new HashMap<String, String>();
                    userParams.put("source", "discount_page");
                    FlurryAgent.logEvent("Click Product Wishlist Icon", userParams);
                } catch (Exception e) {
                }
                Intent intent = new Intent(ActivityAdsList.this, WishListActivity.class);
                startActivity(intent);
            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){


                    Map<String, String> userParams = new HashMap<String, String>();
                    userParams.put("source", "discount_list");
                    FlurryAgent.logEvent("Click Shopping Cart", userParams);

                    Intent intent = new Intent(ActivityAdsList.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent1 = new Intent(ActivityAdsList.this, ActivityLogin.class);
                    startActivity(intent1);
                }

            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromClass != null && fromClass.equals(FROM_FIREBASE)){
                    try {


                        Map<String, String> userParams = new HashMap<String, String>();
                        userParams.put("source", "Product_list");
                        FlurryAgent.logEvent("Incoming Pushnotification Click", userParams);

                    } catch (Exception e) {}



                    Intent adsIntent = new Intent(ActivityAdsList.this, MainActivity.class);
                    startActivity(adsIntent);
                }
                finish();
            }
        });




        titile.setText(getString(R.string.app_name));
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);

                if(image != null) {
                    image.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                NetworkImageView image = (NetworkImageView) view.findViewById(R.id.gridItemImageView);

                if(image != null) {
                    image.setVisibility(View.GONE);
                }


            }
        });


        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        universalAdapter = new UniversalAdapter(ActivityAdsList.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(universalAdapter);


        if (fromClass.equals(FROM_FIREBASE)){
            no_list.setVisibility(View.GONE);
            JsonArrayRequest jsonArrayRequest = productList(url);
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        }
        else{
            if(mainCatDetails.size() > 0) {
                no_list.setVisibility(View.GONE);
                Product pro = new Product();
                pro.setPostType(CART_DETAIL_NO_ITEM);
                mainCatDetails.add(pro);
                universalAdapter.notifyDataSetChanged();

            }else {
                Product pro = new Product();
                pro.setPostType(CART_DETAIL_NO_ITEM);
                mainCatDetails.add(pro);
                universalAdapter.notifyDataSetChanged();
            }

        }

    }

    private JsonArrayRequest productList(String url)
    {
        final JsonArrayRequest jsonObjReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0) {

                            try {
                                GsonBuilder builder = new GsonBuilder();

                                Gson mGson = builder.create();

                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                                for(int i = 0; i < deliveries.size(); i++) {
                                    Product pro = new Product();
                                    pro = deliveries.get(i);
                                    pro.setPostType(CATEGORY_DETAIL);
                                    mainCatDetails.add(pro);

                                }
                                //databaseAdapter.insertProduct(arrayList, OTHER);


                                if (mainCatDetails.size() == 1){
                                    Intent intent = new Intent(ActivityAdsList.this, ProductActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("product", mainCatDetails.get(0));
                                    bundle.putString("fromClass",FROM_FIREBASE);
                                    intent.putExtras(bundle);

                                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                    finish();

                                }
                                else{

                                    Product pro = new Product();
                                    pro.setPostType(CART_DETAIL_NO_ITEM);
                                    mainCatDetails.add(pro);
                                    universalAdapter.notifyDataSetChanged();
                                }



                            }catch (Exception e){
                            }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        return jsonObjReq;
    }


    @Override
    public void onBackPressed() {
        if(fromClass != null && fromClass.equals(FROM_FIREBASE)){
            try {


                Map<String, String> userParams = new HashMap<String, String>();
                userParams.put("source", "Product_list");
                FlurryAgent.logEvent("Incoming Pushnotification Click", userParams);

            } catch (Exception e) {}
            Intent adsIntent = new Intent(ActivityAdsList.this, MainActivity.class);
            startActivity(adsIntent);
        }
        super.onBackPressed();


    }


    @Override
    protected void onResume() {
        super.onResume();

        cart_text.setStrokeWidth(1);
        cart_text.setStrokeColor("#000000");
        cart_text.setSolidColor("#ffffff");
        int count = databaseAdapter.getOrderCount();

        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }

        wishlist_count.setStrokeWidth(1);
        wishlist_count.setStrokeColor("#000000");
        wishlist_count.setSolidColor("#ffffff");

        int count_wish  = prefs.getIntPreferences(SP_PRODUCT_LIKED_COUNT);
        if(count_wish == 0){
            wishlist_count.setVisibility(View.GONE);
        }else{
            wishlist_count.setVisibility(View.VISIBLE);
            wishlist_count.setText(count_wish + "");
        }
    }



    public void bounceCount (){
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce_animation);
        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext()
                , R.animator.flip_animation);
        int count = databaseAdapter.getOrderCount();
        if (count == 0) {
            cart_text.setVisibility(View.GONE);
        } else {
            cart_text.setVisibility(View.VISIBLE);
            cart_text.setText(count + "");
        }

        animationSet.setTarget(cart_text);
        cart_text.startAnimation(bounce);
        animationSet.start();
    }

}
