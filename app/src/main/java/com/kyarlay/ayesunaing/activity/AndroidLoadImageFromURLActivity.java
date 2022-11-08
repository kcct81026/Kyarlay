package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.flurry.android.FlurryAgent;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CircularTextView;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyFlurry;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.TouchImageView;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;
import com.kyarlay.ayesunaing.operation.GalleryImageAdapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by kcct on 9/14/16.
 */
public class AndroidLoadImageFromURLActivity extends AppCompatActivity implements ConstantVariable {
    private static final String TAG = "AndroidLoadImageFromURL";
    public static TouchImageView image;
    List<Images> imageList;
    Gallery gallery;
    Display display;
    String image_url;

    CustomTextView title ;
    LinearLayout title_layout, back_layout;
    RelativeLayout cart_layout;

    CircularTextView cart_text;
    Resources resources;
    MyPreference prefs;
    DatabaseAdapter databaseAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_image_loader);

        Log.e(TAG, "onCreate: * " );
        prefs = new MyPreference(AndroidLoadImageFromURLActivity.this);
        databaseAdapter = new DatabaseAdapter(AndroidLoadImageFromURLActivity.this);
        Context context = LocaleHelper.setLocale(AndroidLoadImageFromURLActivity.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        new MyFlurry(AndroidLoadImageFromURLActivity.this);
        display = this.getWindowManager().getDefaultDisplay();

        image   = (TouchImageView) findViewById(R.id.image);

        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);
        cart_layout      = (RelativeLayout) findViewById(R.id.cart_layout);
        cart_text        = (CircularTextView) findViewById(R.id.menu_cart_idenfier);
        LinearLayout layout_like     = (LinearLayout) findViewById(R.id.like_layot);
        layout_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AndroidLoadImageFromURLActivity.this, WishListActivity.class);
                startActivity(intent);
            }
        });
        cart_layout.setVisibility(View.GONE);
        layout_like.setVisibility(View.GONE);


        cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prefs.getIntPreferences(ConstantVariable.SP_MEMBER_ID) != 0){
                    try {

                        Map<String, String> userParams = new HashMap<String, String>();
                        userParams.put("source","Image Detail");
                        FlurryAgent.logEvent("Click Shopping Cart", userParams);
                    } catch (Exception e)  {
                    }

                    Intent intent = new Intent(AndroidLoadImageFromURLActivity.this, ShoppingCartActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(AndroidLoadImageFromURLActivity.this, ActivityLogin.class);
                    startActivity(intent);
                }

            }
        });
        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 11) / 20;
        cart_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        layout_like.getLayoutParams().width     = ( display.getWidth() * 3) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidLoadImageFromURLActivity.this.finish();
            }
        });
        title.setText(resources.getString(R.string.app_name)+"");

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





        gallery =  (Gallery) findViewById(R.id.gallery);
        gallery.setSpacing(5);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", 0);
        image_url = intent.getStringExtra("url");

        imageList = intent.getParcelableArrayListExtra("list_image");

        try{
            Bitmap bitmap = getImageBitmap(image_url);
            image.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "onCreate: "  + e.getMessage() );
        }


        image.setMaxZoom(4f);


        gallery.getLayoutParams().height = (display.getHeight() * 1)/ 10;
        final GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(this, imageList);
        gallery.setAdapter(galleryImageAdapter);
        gallery.setSelection(index);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              Images imgObj = imageList.get(position);
              image.setImageBitmap(getImageBitmap(imgObj.getUrl()));
            }
        });
    }


    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "getImageBitmap: "   + e.getMessage() );

        }catch (NetworkOnMainThreadException e){
            e.printStackTrace();
            Log.e(TAG, "getImageBitmap: "  + e.getLocalizedMessage() );
        }
        return bm;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AndroidLoadImageFromURLActivity.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                AndroidLoadImageFromURLActivity.this.finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}