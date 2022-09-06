package com.kyarlay.ayesunaing.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.flurry.android.FlurryAgent;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatCallbackStatus;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.freshchat.consumer.sdk.UnreadCountCallback;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.activity.ActivityAdsList;
import com.kyarlay.ayesunaing.activity.AskProdcutAcitivity;
import com.kyarlay.ayesunaing.activity.BrandActivity;
import com.kyarlay.ayesunaing.activity.FlashSalesActivity;
import com.kyarlay.ayesunaing.activity.MainActivity;
import com.kyarlay.ayesunaing.activity.NotificationAcitivity;
import com.kyarlay.ayesunaing.activity.ProductActivity;
import com.kyarlay.ayesunaing.activity.ReadingCommentDetailsActivity;
import com.kyarlay.ayesunaing.activity.VideoProgramDetailActivity;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;
import com.kyarlay.ayesunaing.data.LocaleHelper;
import com.kyarlay.ayesunaing.object.Product;
import com.kyarlay.ayesunaing.object.Reading;
import com.kyarlay.ayesunaing.operation.DatabaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.myatminsoe.mdetect.MDetect;
import me.myatminsoe.mdetect.Rabbit;


/**
 * Created by ayesunaing on 10/14/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService implements ConstantVariable, Constant {
    private static final String TAG = "MyFirebaseMsgService";
    String url, type, notiTitle, notiBody, notiUser, youtubeID;
    String notiUrl = "";
    DatabaseAdapter databaseAdapter;
    Context context;
    Bitmap bitmap;
    Resources resources;
    //int NOTIFICATION_ID = 1500;

    NotificationChannel mChannel = null;
    NotificationManager notifManager = null;
    int importance = NotificationManager.IMPORTANCE_HIGH;

    SharedPreferences prefs;
    String refreshedToken;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        databaseAdapter = new DatabaseAdapter(getApplicationContext());
        super.onMessageReceived(remoteMessage);
        context = getApplicationContext();

        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .withCaptureUncaughtExceptions(true)
                .withContinueSessionMillis(10000)
                .withLogLevel(Log.VERBOSE)
                .build(getApplicationContext(), FLURRY_API_KEY);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(0);

        prefs = getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Context context1 = LocaleHelper.setLocale(context, prefs.getString(LANGUAGE, LANGUAGE_MYANMAR));
        resources = context1.getResources();
        //{image_url=http://res.cloudinary.com/tech-myanmar/image/upload/q_auto/idebpilqlqoh2cb9il8o.jpg,
        // url=http://www.kyarlay.com/api/products/4225,
        // body=L,
        // type=product,
        // title=Plume Small Baby Pants L-46pcs}

        Log.e(TAG, "onMessageReceived: "   );

        if (Freshchat.isFreshchatNotification(remoteMessage)) {

            Log.e(TAG, "onMessageReceived: isFreshChatNotification "   );
            prefs.edit().putInt(SP_FRESH_CHAT_UNREAD_COUNT, 1).commit();

            Freshchat.getInstance(this).handleFcmMessage(this,remoteMessage);
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            try {
                Map<String, String> mix = new HashMap<String, String>();
                FlurryAgent.logEvent("Incoming Pushnotification Chatting", mix);
            } catch (Exception e) {
            }


            Freshchat.getInstance(getApplicationContext()).getUnreadCountAsync(new UnreadCountCallback() {
                @Override
                public void onResult(FreshchatCallbackStatus freshchatCallbackStatus, int unreadCount) {

                    Log.e(TAG, "onResult: ***** "  + unreadCount );

                   // prefs.saveIntPerferences(SP_FRESH_CHAT_UNREAD_COUNT, unreadCount);
                    prefs.edit().putInt(SP_FRESH_CHAT_UNREAD_COUNT, unreadCount).commit();
                }
            });

        } else {

            try {
                Map<String, String> mix = new HashMap<String, String>();
                mix.put("type", type);
                FlurryAgent.logEvent("Incoming Pushnotification", mix);
            } catch (Exception e) {
            }

            try{
                url  = remoteMessage.getData().get("url");
                type = remoteMessage.getData().get("type");
                notiUrl   = remoteMessage.getData().get("image_url");


                Log.e(TAG, "onMessageReceived: **** "   + remoteMessage.getData().get("url"));
                Log.e(TAG, "onMessageReceived: **** "   +remoteMessage.getData().get("type"));
                Log.e(TAG, "onMessageReceived: **** "   + remoteMessage.getData().get("image_url"));

                if (MDetect.INSTANCE.isUnicode()){
                    notiTitle = remoteMessage.getData().get("title");
                    notiBody  = remoteMessage.getData().get("body");

                    Log.e(TAG, "onMessageReceived:************* unicode "   );

                }else{
                    Log.e(TAG, "onMessageReceived:************* zawgyi  "   );
                    notiTitle = Rabbit.uni2zg(remoteMessage.getData().get("title"));
                    notiBody  = Rabbit.uni2zg(remoteMessage.getData().get("body"));
                }

                if (type.equals("comment" )) {
                    notiUser = remoteMessage.getData().get("user_id");
                }
                if(type.equals("video")){
                    youtubeID = remoteMessage.getData().get("youtube_id");
                }

                if (notifManager == null) {
                    notifManager = (NotificationManager) getSystemService
                            (Context.NOTIFICATION_SERVICE);
                }

                if(notiUrl.trim().length() > 0){
                    bitmap = getBitmapfromUrl(notiUrl);
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationOreo();
                }else{
                    notificationUnderOreo();
                }

            }catch(Exception e){
                Log.e(TAG, "Exception : "  +e.getMessage());
            }





        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        // Sending new token to AppsFlyer

        prefs =  getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        FirebaseMessaging.getInstance().subscribeToTopic("regular");
        refreshedToken      = FirebaseInstanceId.getInstance().getToken();

        FreshchatConfig freshchatConfig=new FreshchatConfig(SP_FRESH_CAHT_ID,SP_FRESH_CHAT_KEY);
        Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);

        if (refreshedToken != null && !refreshedToken.equals("")) {

            prefs.edit().putString(SP_FCM_TOEKN, refreshedToken).commit();
            Freshchat.getInstance(this).setPushRegistrationToken(refreshedToken);
            updateFCMID();

        }


        //AppsFlyerLib.getInstance().updateServerUninstallToken(getApplicationContext(), s);
    }

    @SuppressLint("WrongConstant")
    public void notificationOreo(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            if (type.equals("main")) {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"4");

                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fromClass", FROM_FIREBASE);
                intent.putExtras(bundle);


                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                //builder.setSmallIcon(R.drawable.menu_kyarlay);
                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[]{50, 125});
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);

                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("4", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                if (bitmap != null) {
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(0, builder.build());
            } else if (type.equals("link")) {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "3");

                Intent intent = new Intent(this, FirebaseWebView.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[]{50, 125});
                builder.setContentIntent(pendingIntent);

                if (bitmap != null) {
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("3", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(1, builder.build());
            } else if (type.equals("brand_page")) {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "2");

                Intent intent = new Intent(this, BrandActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(url));
                bundle.putString("fromClass", FROM_FIREBASE);
                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[]{50, 125});
                builder.setContentIntent(pendingIntent);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("2", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                if (bitmap != null) {
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(2, builder.build());
            } else if (type.equals("article")) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

                Reading reading = new Reading();
                reading.setId(Integer.parseInt(url));
                reading.setTitle(notiTitle);
                reading.setUrl(constantKyarlayPushNoti + url + "?"+LANG+"="+ prefs.getString(SP_LANGUAGE , ""));
                reading.setSort_by(FROM_FIREBASE);

                Intent intent = new Intent(this, ReadingCommentDetailsActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("id", reading.getId());
                bundle.putString("title", reading.getTitle());
                bundle.putInt("like_count", reading.getLikes());
                bundle.putInt("comment_count", reading.getComment_coount());

                bundle.putString("comment", "read");
                bundle.putString("status", FROM_FIREBASE);
                intent.putExtras(bundle);


                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("1", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[] { 50, 125 });
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(3, builder.build());


            }
            else if (type.equals("flash_sale")) {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"4");

                Intent intent = new Intent(this, FlashSalesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fromClass", FROM_FIREBASE);
                intent.putExtras(bundle);


                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                //builder.setSmallIcon(R.drawable.menu_kyarlay);
                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[]{50, 125});
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);

                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("7", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                if (bitmap != null) {
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(0, builder.build());
            }
            else if (type.equals("rich_video")) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

                Reading reading = new Reading();
                reading.setId(Integer.parseInt(url));
                reading.setTitle(notiTitle);
                reading.setUrl(constantKyarlayPushNoti + url + "?"+LANG+"="+ prefs.getString(SP_LANGUAGE , ""));
                reading.setSort_by(FROM_FIREBASE);

                Intent intent = new Intent(this, ReadingCommentDetailsActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putParcelable("richtext", reading);

                bundle.putInt("id", reading.getId());
                bundle.putString("title", reading.getTitle());
                bundle.putInt("like_count", reading.getLikes());
                bundle.putInt("comment_count", reading.getComment_coount());

                bundle.putString("comment", "read");
                bundle.putString("status", FROM_FIREBASE);
                intent.putExtras(bundle);


                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("1", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[] { 50, 125 });
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(3, builder.build());


            } else if (type.equals("product_question")) {

                if(prefs.getString(SP_USER_TOKEN , "") != null &&
                        prefs.getString(SP_USER_TOKEN , "").trim().length() > 0){


                    prefs.edit().putString(SP_PRODUCT_NOTIFICATION, "noti").commit();

                    Intent intent = new Intent(this, AskProdcutAcitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", notiTitle);
                    bundle.putString("from_class", FROM_FIREBASE);

                    intent.putExtras(bundle);

                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "0");

                    if (mChannel == null) {
                        mChannel = new NotificationChannel
                                ("8", notiTitle, importance);
                        mChannel.setDescription(notiTitle);
                        mChannel.enableVibration(true);
                        notifManager.createNotificationChannel(mChannel);
                    }

                    builder.setContentTitle(notiTitle)
                            .setSmallIcon(getNotificationIcon()) // required
                            .setContentText(resources.getString(R.string.comment_product_noti))  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setLargeIcon(bitmap)
                            .setBadgeIconType(R.drawable.ic_launcher)
                            .setContentIntent(pendingIntent)
                            .setSound(RingtoneManager.getDefaultUri
                                    (RingtoneManager.TYPE_NOTIFICATION));
                    Notification notification = builder.build();
                    notifManager.notify(0, notification);

                }


            }
            else if (type.equals("comment")) {

                if (prefs.getInt(SP_MEMBER_ID, 0) != Integer.parseInt(notiUser)) {

                    prefs.edit().putString(SP_NOTIFICATION, "noti").commit();
                    databaseAdapter.insertPostNotification(Integer.parseInt(notiBody));

                    Intent intent = new Intent(this, NotificationAcitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", Integer.parseInt(notiBody));
                    bundle.putString("title", notiTitle);
                    bundle.putInt("like_count", Integer.parseInt(url));
                    bundle.putInt("comment_count", Integer.parseInt(notiUrl));
                    bundle.putString("from_class", FROM_FIREBASE);
                    bundle.putInt("show_header", 1);
                    intent.putExtras(bundle);

                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "0");

                    if (mChannel == null) {
                        mChannel = new NotificationChannel
                                ("0", notiTitle, importance);
                        mChannel.setDescription(notiTitle);
                        mChannel.enableVibration(true);
                        notifManager.createNotificationChannel(mChannel);
                    }

                    builder.setContentTitle(notiTitle)
                            .setSmallIcon(getNotificationIcon()) // required
                            .setContentText(resources.getString(R.string.comment_noti))  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setLargeIcon(bitmap)
                            .setBadgeIconType(R.drawable.ic_launcher)
                            .setContentIntent(pendingIntent)
                            .setSound(RingtoneManager.getDefaultUri
                                    (RingtoneManager.TYPE_NOTIFICATION));
                    Notification notification = builder.build();
                    notifManager.notify(0, notification);

                }


            }

            else if (type.equals("video_program")) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

                Reading reading = new Reading();
                reading.setId(Integer.parseInt(url));
                reading.setTitle(notiTitle);
                reading.setUrl(constantKyarlayPushNoti + url + "?"+LANG+"="+ prefs.getString(SP_LANGUAGE , ""));
                reading.setSort_by(FROM_FIREBASE);

                Intent intent = new Intent(this, VideoProgramDetailActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putParcelable("richtext", reading);

                //bundle.putInt("like_count", reading.getLikes());
                //bundle.putInt("comment_count", reading.getComment_coount());

                //bundle.putString("comment", "read");
                bundle.putString("status", FROM_FIREBASE);
                bundle.putInt("id", reading.getId());
                bundle.putString("title", reading.getTitle());
                bundle.putInt("like_count", reading.getLikes());
                bundle.putString("post_type", "video_program");

                intent.putExtras(bundle);


                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("1", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[] { 50, 125 });
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(3, builder.build());


            } else if (type.equals("video")) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

                Reading reading = new Reading();
                reading.setId(Integer.parseInt(url));
                reading.setTitle(notiTitle);
                reading.setUrl(constantKyarlayPushNoti + url + "?"+LANG+"="+ prefs.getString(SP_LANGUAGE , ""));
                reading.setSort_by(FROM_FIREBASE);

                Intent intent = new Intent(this, VideoProgramDetailActivity.class);
                Bundle bundle = new Bundle();
                //bundle.putParcelable("richtext", reading);

                //bundle.putInt("like_count", reading.getLikes());
                //bundle.putInt("comment_count", reading.getComment_coount());

                //bundle.putString("comment", "read");
                bundle.putString("status", FROM_FIREBASE);
                bundle.putInt("id", reading.getId());
                bundle.putString("title", reading.getTitle());
                bundle.putInt("like_count", reading.getLikes());
                bundle.putString("now_playing", youtubeID);
                bundle.putString("post_type", "video_program");

                intent.putExtras(bundle);


                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("1", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[] { 50, 125 });
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //notificationManager.cancel(NOTIFICATION_ID);
                notificationManager.notify(3, builder.build());


            }else {

               /* JsonArrayRequest jsonArrayRequest = productList(url  + "&"+LANG+"="+prefs.getString(SP_LANGUAGE , ""));
                AppController.getInstance().addToRequestQueue(jsonArrayRequest);*/
               sendNotificationUrl(url  + "?"+LANG+"="+prefs.getString(SP_LANGUAGE , ""));
            }
        }

    }


    public void notificationUnderOreo(){

        if(type.equals("main")){
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            Intent intent = new Intent(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("fromClass", FROM_FIREBASE);
            intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            //builder.setSmallIcon(R.drawable.menu_kyarlay);
            builder.setContentTitle(notiTitle);
            builder.setContentText(notiBody);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setAutoCancel(true);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[] { 50, 125 });
            builder.setContentIntent(pendingIntent);
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(0,  builder.build());
        }

        else if(type.equals("flash_sale")){
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            Intent intent = new Intent(this, FlashSalesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("fromClass", FROM_FIREBASE);
            intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            //builder.setSmallIcon(R.drawable.menu_kyarlay);
            builder.setContentTitle(notiTitle);
            builder.setContentText(notiBody);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setAutoCancel(true);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[] { 50, 125 });
            builder.setContentIntent(pendingIntent);
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(0,  builder.build());
        }
        else if(type.equals("link")){
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            Intent intent = new Intent(this, FirebaseWebView.class);
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(notiTitle);
            builder.setContentText(notiBody);
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setAutoCancel(true);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[] { 50, 125 });
            builder.setContentIntent(pendingIntent);

            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(1, builder.build());
        }else if(type.equals("brand_page")){
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            Intent intent = new Intent(this, BrandActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id",Integer.parseInt(url));
            bundle.putString("fromClass", FROM_FIREBASE);
            intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(notiTitle);
            builder.setContentText(notiBody);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setAutoCancel(true);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[] { 50, 125 });
            builder.setContentIntent(pendingIntent);

            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(2, builder.build());
        }else if(type.equals("article")){


            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            Reading reading = new Reading();
            reading.setId(Integer.parseInt(url));
            reading.setTitle(notiTitle);
            reading.setUrl(constantKyarlayPushNoti+url + "?"+LANG+"="+ prefs.getString(SP_LANGUAGE , ""));
            reading.setSort_by(FROM_FIREBASE);

            Intent intent = new Intent(this, ReadingCommentDetailsActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putParcelable("richtext", reading);

            bundle.putInt("id", reading.getId());
            bundle.putString("title", reading.getTitle());
            bundle.putInt("like_count", reading.getLikes());
            bundle.putInt("comment_count", reading.getComment_coount());

            bundle.putString("comment", "read");
            bundle.putString("status", FROM_FIREBASE);
            intent.putExtras(bundle);


            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(notiTitle);
            builder.setContentText(notiBody);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setAutoCancel(true);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[] { 50, 125 });
            builder.setContentIntent(pendingIntent);
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(3, builder.build());
        }

        else if (type.equals("product_question")){

            if(prefs.getString(SP_USER_TOKEN , "") != null &&
                    prefs.getString(SP_USER_TOKEN , "").trim().length() > 0){


                prefs.edit().putString(SP_PRODUCT_NOTIFICATION, "noti").commit();
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                Intent intent = new Intent(this, AskProdcutAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", notiTitle);
                bundle.putString("fromCalss", FROM_FIREBASE);

                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentTitle(notiTitle);
                builder.setContentText(resources.getString(R.string.comment_product_noti));
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[] { 50, 125 });
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(4);
                notificationManager.notify(4, builder.build());


            }


        }

        else if (type.equals("comment")){

            if(prefs.getInt(SP_MEMBER_ID, 0) != Integer.parseInt(notiUser)){

                prefs.edit().putString(SP_NOTIFICATION, "noti").commit();
                databaseAdapter.insertPostNotification(Integer.parseInt(notiBody));
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                Intent intent = new Intent(this, NotificationAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(notiBody));
                bundle.putString("title", notiTitle);
                bundle.putInt("like_count", Integer.parseInt(url));
                bundle.putInt("comment_count", Integer.parseInt(notiUrl));
                bundle.putString("from_class", FROM_FIREBASE);
                bundle.putInt("show_header", 1);
                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentTitle(notiTitle);
                builder.setContentText(resources.getString(R.string.comment_noti));
                builder.setAutoCancel(true);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setVibrate(new long[] { 50, 125 });
                builder.setContentIntent(pendingIntent);
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(4);
                //notificationManager.cancelAll();
                notificationManager.notify(4, builder.build());


            }


        }else if (type.equals("video_program")) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

            Reading reading = new Reading();
            reading.setId(Integer.parseInt(url));
            reading.setTitle(notiTitle);
            reading.setUrl(constantKyarlayPushNoti + url + "?"+LANG+"="+ prefs.getString(SP_LANGUAGE , ""));
            reading.setSort_by(FROM_FIREBASE);

            Intent intent = new Intent(this, VideoProgramDetailActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putParcelable("richtext", reading);

            //bundle.putInt("like_count", reading.getLikes());
            //bundle.putInt("comment_count", reading.getComment_coount());

            //bundle.putString("comment", "read");
            bundle.putString("status", FROM_FIREBASE);
            bundle.putInt("id", reading.getId());
            bundle.putString("title", reading.getTitle());
            bundle.putInt("like_count", reading.getLikes());
            bundle.putInt("comment_count", reading.getComment_coount());
            bundle.putString("post_type", "video_program");

            intent.putExtras(bundle);


            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle(notiTitle);
            builder.setContentText(notiBody);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setAutoCancel(true);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[] { 50, 125 });
            builder.setContentIntent(pendingIntent);
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(3, builder.build());


        } else if (type.equals("video")) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");

            Reading reading = new Reading();
            reading.setId(Integer.parseInt(url));
            reading.setTitle(notiTitle);
            reading.setUrl(constantKyarlayPushNoti + url + "?"+LANG+"="+ prefs.getString(SP_LANGUAGE , ""));
            reading.setSort_by(FROM_FIREBASE);

            Intent intent = new Intent(this, VideoProgramDetailActivity.class);
            Bundle bundle = new Bundle();
            //bundle.putParcelable("richtext", reading);

            //bundle.putInt("like_count", reading.getLikes());
            //bundle.putInt("comment_count", reading.getComment_coount());

            //bundle.putString("comment", "read");
            bundle.putString("status", FROM_FIREBASE);
            bundle.putInt("id", reading.getId());
            bundle.putString("title", reading.getTitle());
            bundle.putInt("like_count", reading.getLikes());
            bundle.putInt("comment_count", reading.getComment_coount());
            bundle.putString("now_playing", youtubeID);
            bundle.putString("post_type", "video_program");

            intent.putExtras(bundle);


            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle(notiTitle);
            builder.setContentText(notiBody);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            builder.setAutoCancel(true);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setVibrate(new long[] { 50, 125 });
            builder.setContentIntent(pendingIntent);
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);
            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationManager.cancel(NOTIFICATION_ID);
            notificationManager.notify(3, builder.build());


        } else {

            /*JsonArrayRequest jsonArrayRequest = productList(url);
            AppController.getInstance().addToRequestQueue(jsonArrayRequest);*/

            sendNotificationUrl(url  + "?"+LANG+"="+prefs.getString(SP_LANGUAGE , ""));
        }



    }

    private void sendNotificationUrl(String url){



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"6");
            Intent intent = new Intent(this, ActivityAdsList.class);
            Bundle bundle = new Bundle();
            //bundle.putParcelableArrayList("product", list);
            bundle.putString("url", url);
            bundle.putString("fromClass", FROM_FIREBASE);
            intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            if (mChannel == null) {
                mChannel = new NotificationChannel
                        ("6", notiTitle, importance);
                mChannel.setDescription(notiTitle);
                mChannel.enableVibration(true);
                notifManager.createNotificationChannel(mChannel);
            }
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setContentTitle(notiTitle);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }


            builder.setContentText(notiBody).setLights(Color.BLACK, 300, 300);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setVibrate(new long[] { 50, 125 });
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

            Notification notification = builder.build();
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //nm.cancel(NOTIFICATION_ID);
            nm.notify(6, notification);
        }
        else{
            Log.e(TAG, "sendNotificationUrl: " + " --------------------- ");
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            Intent intent = new Intent(this, ActivityAdsList.class);
            Bundle bundle = new Bundle();
            //bundle.putParcelableArrayList("product", list);
            bundle.putString("fromClass", FROM_FIREBASE);
            bundle.putString("url", url);
            intent.putExtras(bundle);

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setOnlyAlertOnce(true);
            builder.setContentTitle(notiTitle);
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            if(bitmap != null){
                NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                bigPicStyle.bigPicture(bitmap);
                bigPicStyle.setBigContentTitle(notiTitle);
                bigPicStyle.setSummaryText(notiBody);
                builder.setStyle(bigPicStyle);
            }


            builder.setContentText(notiBody).setLights(Color.BLACK, 300, 300);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setVibrate(new long[] { 50, 125 });
            Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

            Notification notification = builder.build();
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //nm.cancel(NOTIFICATION_ID);
            nm.notify(6, notification);
        }

    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_launcher : R.drawable.ic_launcher;
    }

    @SuppressLint("WrongConstant")
    private void sendNotification(ArrayList<Product> list) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            if(list.size() == 1){
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"5");
                Intent intent = new Intent(this, ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("product", list.get(0));
                bundle.putString("fromClass",FROM_FIREBASE);
                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("5", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }

                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setContentIntent(pendingIntent);

                builder.setAutoCancel(true);
                builder.setVibrate(new long[] { 50, 125 });
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setSmallIcon(R.drawable.ic_launcher);
                } else {
                    builder.setSmallIcon(R.drawable.ic_launcher);
                }

                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }


                Notification notification = builder.build();
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //nm.cancel(NOTIFICATION_ID);
                nm.notify(5, notification);


            }else if(list.size() > 1){
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"6");
                Intent intent = new Intent(this, ActivityAdsList.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("product", list);
                bundle.putString("fromClass", FROM_FIREBASE);
                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                if (mChannel == null) {
                    mChannel = new NotificationChannel
                            ("6", notiTitle, importance);
                    mChannel.setDescription(notiTitle);
                    mChannel.enableVibration(true);
                    notifManager.createNotificationChannel(mChannel);
                }
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setContentTitle(notiTitle);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }


                builder.setContentText(notiBody).setLights(Color.BLACK, 300, 300);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                builder.setVibrate(new long[] { 50, 125 });
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

                Notification notification = builder.build();
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //nm.cancel(NOTIFICATION_ID);
                nm.notify(6, notification);

            }else{
            }
        }else{
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            if(list.size() == 1){
                Intent intent = new Intent(this, ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("product", list.get(0));
                bundle.putString("fromClass",FROM_FIREBASE);
                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setContentTitle(notiTitle);
                builder.setContentText(notiBody);
                builder.setContentIntent(pendingIntent);

                builder.setAutoCancel(true);
                builder.setVibrate(new long[] { 50, 125 });
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.setSmallIcon(R.drawable.ic_launcher);
                } else {
                    builder.setSmallIcon(R.drawable.ic_launcher);
                }

                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }


                Notification notification = builder.build();
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //nm.cancel(NOTIFICATION_ID);
                nm.notify(5, notification);


            }else if(list.size() > 1){
                Intent intent = new Intent(this, ActivityAdsList.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("product", list);
                bundle.putString("fromClass", FROM_FIREBASE);
                intent.putExtras(bundle);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                builder.setOnlyAlertOnce(true);
                builder.setContentTitle(notiTitle);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                if(bitmap != null){
                    NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
                    bigPicStyle.bigPicture(bitmap);
                    bigPicStyle.setBigContentTitle(notiTitle);
                    bigPicStyle.setSummaryText(notiBody);
                    builder.setStyle(bigPicStyle);
                }


                builder.setContentText(notiBody).setLights(Color.BLACK, 300, 300);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                builder.setVibrate(new long[] { 50, 125 });
                Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher);
                builder.setColor(getResources().getColor(R.color.quiz_press_true)).setSmallIcon(R.drawable.ic_launcher).setLargeIcon(bitmap);

                Notification notification = builder.build();
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //nm.cancel(NOTIFICATION_ID);
                nm.notify(6, notification);

            }else{
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
                             ArrayList<Product> arrayList = new ArrayList<>();
                             Type listType = new TypeToken<List<Product>>() {
                             }.getType();
                             List<Product> deliveries = mGson.fromJson(response.toString(), listType);

                            for(int i = 0; i < deliveries.size(); i++) {
                                Product pro = new Product();
                                pro = deliveries.get(i);
                                pro.setPostType(CATEGORY_DETAIL);
                                arrayList.add(pro);

                             }
                             //databaseAdapter.insertProduct(arrayList, OTHER);


                             sendNotification(arrayList);
                         }catch (Exception e){
                         }
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse:  "  + error.getLocalizedMessage() );
                Log.e(TAG, "onErrorResponse: "  + error.getMessage() );
            }
        });
        return jsonObjReq;
    }

    private void updateFCMID(){
        JSONObject updateFcm = new JSONObject();
        try {
            updateFcm.put("fcm_token",  refreshedToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,  String.format(constantUpdateFcmID, prefs.getInt(SP_MEMBER_ID, 0)), updateFcm,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Customer-Phone", prefs.getString(SP_USER_PHONE,""));
                headers.put("X-Customer-Token", prefs.getString(SP_USER_TOKEN,""));
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,"sign_in");
    }



}
