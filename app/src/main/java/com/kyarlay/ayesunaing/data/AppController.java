package com.kyarlay.ayesunaing.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import me.myatminsoe.mdetect.MDetect;


/**
 * Created by soelinmyat on 12/14/14.
 */
public class AppController extends Application implements ConstantVariable {



    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;
    SharedPreferences prefs;

    public static TypefaceManager typefaceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
        MDetect.INSTANCE.init(this);



        AssetManager assetManager = this.getAssets();
        typefaceManager = new TypefaceManager(assetManager, this);
        prefs =  getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

      /*  FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/

       /* AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {


            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {

                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {

                for (String attrName : attributionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + attributionData.get(attrName));
                }

            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };

        AppsFlyerLib.getInstance().waitForCustomerUserId(true);
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);

        AppsFlyerLib.getInstance().setCustomerIdAndTrack(prefs.getInt(SP_MEMBER_ID,0)+"", this);*/

    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {

        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache(getApplicationContext()));
        }



        return this.mImageLoader;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }





  //@Override
    public void onStop () {
       // super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }



}
