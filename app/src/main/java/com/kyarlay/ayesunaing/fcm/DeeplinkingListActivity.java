package com.kyarlay.ayesunaing.fcm;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityLogin;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.activity.ShoppingCartActivity;
import com.kyarlay.ayesunaing.activity.WishListActivity;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;
//import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.object.UniversalPost;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.UniversalAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*

*
 * Created by ayesunaing on 10/27/16.

*/


public class DeeplinkingListActivity extends AppCompatActivity implements ConstantVariable{
    RecyclerView recyclerView;
    UniversalAdapter universalAdapter;
    ArrayList<UniversalPost> mainCatDetails = new ArrayList<>();
    RecyclerView.LayoutManager manager;
    DatabaseAdapter databaseAdapter;
    MyPreference prefs;

    LinearLayout title_layout, back_layout;
    CircularTextView  cart_text, wishlist_count;
    RelativeLayout cart_layout;
    RelativeLayout layout_like;

    Display display;


    Animation bounce;
    AnimatorSet animationSet;
    String fromclass = "fromclass";





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_deep_linking);


        Intent intent = getIntent();
        mainCatDetails = intent.getParcelableArrayListExtra("product");
        fromclass = intent.getStringExtra("fromclass");
        databaseAdapter = new DatabaseAdapter(DeeplinkingListActivity.this);
        prefs = new MyPreference(DeeplinkingListActivity.this);

        recyclerView        = (RecyclerView) findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        universalAdapter = new UniversalAdapter(DeeplinkingListActivity.this, mainCatDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(universalAdapter);
        display = getWindowManager().getDefaultDisplay();

       // new MyFlurry(DeeplinkingListActivity.this);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        layout_like     = (RelativeLayout) findViewById(R.id.like_layot);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        wishlist_count  = (CircularTextView) findViewById(R.id.wishlist_count);

        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){

                    try {
                       Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "product_list");
                        //FlurryAgent.logEvent("Click Product Wishlist Icon", mix);
                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(DeeplinkingListActivity.this, WishListActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(DeeplinkingListActivity.this, ActivityLogin.class);
                    startActivity(intent);

                }



            }
        });

        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    try {
                       Map<String, String> mix = new HashMap<String, String>();
                        mix.put("source", "product_list");
                        //FlurryAgent.logEvent("Click Shopping Cart", mix);
                    } catch (Exception e) {
                    }

                    Intent intent = new Intent(DeeplinkingListActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(DeeplinkingListActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        layout_like.getLayoutParams().width     = ( display.getWidth() * 3 ) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fromclass.equals("universal")){
                    finish();
                }else{
                    Intent intent_main = new Intent(DeeplinkingListActivity.this, MainActivity.class);
                    startActivity(intent_main);
                    finish();
                }
            }
        });

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
    public void onBackPressed() {
        super.onBackPressed();
        if (fromclass.equals("universal")){
            finish();
        }else{
            Intent intent_main = new Intent(DeeplinkingListActivity.this, MainActivity.class);
            startActivity(intent_main);
            finish();
        }


    }


}

