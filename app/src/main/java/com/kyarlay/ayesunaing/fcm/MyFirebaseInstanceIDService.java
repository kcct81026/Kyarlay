/*
package com.kyarlay.ayesunaing.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kyarlay.ayesunaing.data.AppController;
import com.kyarlay.ayesunaing.data.Constant;
import com.kyarlay.ayesunaing.data.ConstantVariable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

*/
/**
 * Created by ayesunaing on 10/14/16.
 *//*


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService implements Constant, ConstantVariable {

    private static final String TAG = "MyFirebaseIIDService";
    String refreshedToken;
    SharedPreferences prefs;


    @Override
    public void onTokenRefresh() {
        prefs =  getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        FirebaseMessaging.getInstance().subscribeToTopic("regular");
        refreshedToken      = FirebaseInstanceId.getInstance().getToken();

        FreshchatConfig freshchatConfig=new FreshchatConfig(SP_FRESH_CAHT_ID,SP_FRESH_CHAT_KEY);
        Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);

        if (refreshedToken != null && !refreshedToken.equals("")) {

            prefs.edit().putString(SP_FCM_TOEKN, refreshedToken).commit();
            Freshchat.getInstance(this).setPushRegistrationToken(refreshedToken);
            updateFCMID();


        */
/*    Freshchat.getInstance(this).setPushRegistrationToken(refreshedToken);
            updateFCMID();*//*

        }
        else{
            Log.e(TAG, "onTokenRefresh: refresh token is null "   );
        }

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

            */
/**
             * Passing some request headers
             * *//*

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

}*/
