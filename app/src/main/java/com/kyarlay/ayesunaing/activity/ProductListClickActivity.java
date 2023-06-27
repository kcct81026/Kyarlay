package com.kyarlay.ayesunaing.activity;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.OrderDetailsObj;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductListClickActivity extends AppCompatActivity implements Constant, ConstantVariable {

    private static final String TAG = "ProductListClick";


    ArrayList<UniversalPost> universalPosts = new ArrayList<>();
    ArrayList<Product> universalPostsTemp = new ArrayList<>();
    List<Product> productTempList = new ArrayList<>();
    boolean isMore = false;

    RecyclerView recyclerView ;
    UniversalAdapter universalAdapter;

    ProgressBar progressBar;

    MyPreference prefs;
    Resources resources;
    RecyclerView.LayoutManager manager;
    Boolean loading = true;
    Boolean last = false;



    CustomTextView title;
    LinearLayout title_layout, back_layout, search_layout;
    Display display;
    RelativeLayout cart_layout,option_layout;
    CircularTextView cart_text, wishlist_count;
    RelativeLayout layout_like;
    ImageView imgGrid,imgList;



    DatabaseAdapter databaseAdapter;

    Animation bounce;
    AnimatorSet animationSet;



    private String url = "";

    int twoColumn = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_brand_click);

        //new MyFlurry(ProductListClickActivity.this);
        databaseAdapter = new DatabaseAdapter(ProductListClickActivity.this);

        prefs = new MyPreference(ProductListClickActivity.this);
        Context context = LocaleHelper.setLocale(ProductListClickActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();



        prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, SP_DEFAULT);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
      //  progressBar = (ProgressBar) findViewById(R.id.progressBar1);
      //  progressBar.setVisibility(View.GONE);

        display = getWindowManager().getDefaultDisplay();
       // title = (CustomTextView) findViewById(R.id.title);
        title_layout = (LinearLayout) findViewById(R.id.title_layout);
        back_layout = (LinearLayout) findViewById(R.id.back_layout);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);

        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        layout_like = (RelativeLayout) findViewById(R.id.like_layot);
        wishlist_count = (CircularTextView) findViewById(R.id.wishlist_count);



        option_layout =  findViewById(R.id.option_layout);
        imgGrid =  findViewById(R.id.imgGrid);
        imgList =  findViewById(R.id.imgList);

        String titleText = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
//        title.setText(titleText);



        universalAdapter= new UniversalAdapter(ProductListClickActivity.this, universalPosts);
        manager  = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(universalAdapter);



        if (twoColumn == 1){
            imgList.setVisibility(View.VISIBLE);
            imgGrid.setVisibility(View.GONE);
        }
        else{
            imgList.setVisibility(View.GONE);
            imgGrid.setVisibility(View.VISIBLE);
        }


        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                   /* try {


                        Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "brand_page");
                        //FlurryAgent.logEvent("Click Shopping Cart", mix);
                    } catch (Exception e) {
                    }*/

                    Intent intent = new Intent(ProductListClickActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(ProductListClickActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }
            }
        });
        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              /*  try {

                    Map<String, String> mix = new HashMap<String, String>();
                    mix.put("source", "brand_page");
                    //FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                } catch (Exception e) {
                }*/
                Intent intent = new Intent(ProductListClickActivity.this, WishListActivity.class);
                startActivity(intent);

            }
        });


        layout_like.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
       // title_layout.getLayoutParams().width    = ( display.getWidth() * 10) / 22;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
        option_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;
       // search_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 22;

        search_layout.setVisibility(View.GONE);

        option_layout.setVisibility(View.VISIBLE);


        option_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (twoColumn == 0){
                    twoColumn = 1;
                }
                else {
                    twoColumn = 0;
                }

                universalPosts.clear();
                Product pro = new Product();
                pro.setPostType(CART_DETAIL_FOOTER);

                universalPosts.add(pro);
                universalAdapter.notifyDataSetChanged();
                setUI();
            }
        });



        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();
                if ((lastVisibleItem + 1) == totalItemCount && loading == false) {
                    loading = true;
                    getBrandItem(prefs.getIntPreferences(SP_PAGE_BRAND_CLICK));
                }

            }
        });

        Log.e(TAG, "onCreate: " );
        Product pro = new Product();
        pro.setPostType(CART_DETAIL_FOOTER);
        universalPosts.add(pro);
        universalAdapter.notifyDataSetChanged();
        getBrandItem(1);


    }

    private void setUI(){

        if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
            universalPosts.remove(universalPosts.size() - 1);
        }



        if (twoColumn == 1) {

            for (int i=0; i< universalPostsTemp.size();i++) {
                if (i % 2 != 0 && i != 0) {

                    List<Product> tempList1 = new ArrayList<>();
                    tempList1.add(universalPostsTemp.get(i - 1));
                    tempList1.add(universalPostsTemp.get(i));
                    OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                    orderDetailsObj.setProductList(tempList1);
                    orderDetailsObj.setPostType(STAGGERED_ITEM);

                    universalPosts.add(orderDetailsObj);


                } else if (i % 2 == 0 && i == universalPostsTemp.size() - 1) {

                    if (last) {


                        List<Product> tempList1 = new ArrayList<>();
                        tempList1.add(universalPostsTemp.get(i));
                        OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                        orderDetailsObj.setProductList(tempList1);
                        orderDetailsObj.setPostType(STAGGERED_ITEM);
                        universalPosts.add(orderDetailsObj);

                    } else {

                        productTempList.clear();
                        isMore = true;
                        productTempList.add(universalPostsTemp.get(i));
                    }

                    Log.e(TAG, "setUI: ************* "  + (universalPostsTemp.size() - 1)  );


                }
            }

        }
        else{
            for (int i=0; i< universalPostsTemp.size();i++){
                Product product = new Product();
                product = universalPostsTemp.get(i);
                product.setPostType(CATEGORY_DETAIL);
                universalPosts.add(product);
            }
        }




        if (last){
            loading = true;
            Product pro = new Product();
            pro.setPostType(CART_DETAIL_NO_ITEM);
            universalPosts.add(pro);
        }
        else
            loading = false;



        universalAdapter.notifyItemInserted(universalPosts.size()-1);


        if (twoColumn == 0){
            imgList.setVisibility(View.GONE);
            imgGrid.setVisibility(View.VISIBLE);



        }
        else {
            imgList.setVisibility(View.VISIBLE);
            imgGrid.setVisibility(View.GONE);
        }


    }


    public void bounceCount (){
        bounce = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce_animation);
        animationSet = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext()
                , R.animator.flip_animation);
        int count = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);
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

    @Override
    protected void onResume() {
        super.onResume();

        cart_text.setStrokeWidth(1);
        cart_text.setStrokeColor("#000000");
        cart_text.setSolidColor("#ffffff");
        int count = prefs.getIntPreferences(SP_CUSTOMER_PRODUCT_COUNT);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();





    }




    private void getBrandItem(int page)
    {



        final JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url +  "&page=" + page ,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e(TAG, "onResponse: "  +  url +  "&page=" + page );

                        if(response.length() > 0) {
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }





                            prefs.saveIntPerferences(SP_PAGE_BRAND_CLICK, prefs.getIntPreferences(SP_PAGE_BRAND_CLICK) + SP_DEFAULT);
                            loading = false;
                            try {
                                GsonBuilder builder = new GsonBuilder();
                                Gson mGson = builder.create();
                                Type listType = new TypeToken<List<Product>>() {
                                }.getType();
                                List<Product> products = mGson.fromJson(response.toString(), listType);


                                if (products.size() == 1){
                                    option_layout.setVisibility(View.GONE);

                                    Product product = new Product();
                                    product =  products.get(0);
                                    product.setPostType(CATEGORY_DETAIL);
                                    universalPosts.add(product);

                                    Product pro = new Product();
                                    pro.setPostType(CART_DETAIL_FOOTER);
                                    universalPosts.add(pro);

                                    universalAdapter.notifyItemInserted(universalPosts.size());
                                }
                                else{
                                    for (int i=0; i< products.size();i++)
                                        universalPostsTemp.add(products.get(i));



                                    if (twoColumn == 1){

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


                                    }
                                    else {
                                        for (int i = 0; i < products.size(); i++) {
                                            Product product = new Product();


                                            product = products.get(i);
                                            product.setPostType(CATEGORY_DETAIL);

                                            universalPosts.add(product);
                                        }

                                    }
                                    Product pro = new Product();
                                    pro.setPostType(CART_DETAIL_FOOTER);
                                    universalPosts.add(pro);

                                    universalAdapter.notifyItemInserted(universalPosts.size());
                                }




                            }catch (Exception e){
                                Log.e(TAG, "onResponse: "  + e.getMessage() );
                            }
                        }else{
                            if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                                universalPosts.remove(universalPosts.size() - 1);
                            }

                            loading = true;
                            last = true;
                            if (isMore) {

                                if (productTempList.size() > 0) {

                                    List<Product> tempList1 = new ArrayList<>();
                                    tempList1.add(productTempList.get(productTempList.size() - 1));
                                    OrderDetailsObj orderDetailsObj = new OrderDetailsObj();
                                    orderDetailsObj.setProductList(tempList1);
                                    orderDetailsObj.setPostType(STAGGERED_ITEM);

                                    universalPosts.add(orderDetailsObj);
                                    isMore = false;

                                }
                            }

                            Product noitem = new Product();
                            noitem.setPostType(CART_DETAIL_NO_ITEM);
                            universalPosts.add(noitem);



                            universalAdapter.notifyItemInserted(universalPosts.size());



                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );

                if(universalPosts.size() != 0 && universalPosts.get(universalPosts.size() - 1).getPostType().equals(CART_DETAIL_FOOTER)){
                    universalPosts.remove(universalPosts.size() - 1);
                }


                universalAdapter.notifyItemInserted(universalPosts.size());




            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayReq, "memberchecking");

    }


}
