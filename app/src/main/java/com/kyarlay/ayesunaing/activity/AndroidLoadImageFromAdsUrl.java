package com.kyarlay.ayesunaing.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Gallery;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.data.MyPreference;
import com.kyarlay.ayesunaing.data.TouchImageView;
import com.kyarlay.ayesunaing.object.Images;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kcct on 11/10/16.
 */

public class AndroidLoadImageFromAdsUrl extends AppCompatActivity implements ConstantVariable{
    private static final String TAG = "AndroidLoadImageFromAds";
    private DecimalFormat df;
    public static TouchImageView image;
    List<Images> imageList;
    Gallery gallery;
    Display display;
    String image_url;

    CustomTextView title , footer_text;
    LinearLayout title_layout, back_layout;
    Resources resources;
    MyPreference prefs;
    DatabaseAdapter databaseAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_image_loader);

        Log.e(TAG, "onCreate:  " );

        databaseAdapter = new DatabaseAdapter(AndroidLoadImageFromAdsUrl.this);
        display = this.getWindowManager().getDefaultDisplay();
        prefs   = new MyPreference(AndroidLoadImageFromAdsUrl.this);

        image   = (TouchImageView) findViewById(R.id.image);
        gallery =  (Gallery) findViewById(R.id.gallery);
        gallery.setVisibility(View.GONE);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        image_url = intent.getStringExtra("url");


        try{
            Bitmap bitmap = getBitmap(image_url);
            image.setImageBitmap(bitmap);
            image.setMaxZoom(4f);
        }catch (Exception e){
            e.printStackTrace();
        }




        Context context = LocaleHelper.setLocale(AndroidLoadImageFromAdsUrl.this, prefs.getStringPreferences(LANGUAGE));
        resources = context.getResources();

        title            = (CustomTextView) findViewById(R.id.title);
        title_layout     = (LinearLayout) findViewById(R.id.title_layout);
        back_layout      = (LinearLayout) findViewById(R.id.back_layout);

        back_layout.getLayoutParams().width     = ( display.getWidth() * 3) / 20;
        title_layout.getLayoutParams().width    = ( display.getWidth() * 17) / 20;

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidLoadImageFromAdsUrl.this.finish();
            }
        });
        title.setText(resources.getString(R.string.app_name)+"");



    }

    private Bitmap getBitmap(String src ){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            Log.e(TAG, "getBitmap: Exception "  + e.getClass() );
            return null;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AndroidLoadImageFromAdsUrl.this.finish();
    }


}