package com.kyarlay.ayesunaing.fcm;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import java.util.HashMap;

public class PerfLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "PerfLifecycleCallbacks";

    private final HashMap<Activity, Trace> traces = new HashMap<>();
    private static final PerfLifecycleCallbacks instance =
            new PerfLifecycleCallbacks();

    private PerfLifecycleCallbacks() {}
    public static PerfLifecycleCallbacks getInstance() {
        Log.e(TAG, "getInstance: " );
        return instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated: " );
    }

    @Override
    public void onActivityStarted(Activity activity) {
        String name = activity.getClass().getSimpleName();
        Trace trace = FirebasePerformance.startTrace(name);
        traces.put(activity, trace);
        Log.e(TAG, "onActivityStarted: "  + name );
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Trace trace = traces.remove(activity);
        try{
            if (trace != null)
                trace.stop();
        }catch (Exception e){
            Log.e(TAG, "onActivityStopped: error "   + e.getMessage() );
        }
        Log.e(TAG, "onActivityStopped: "  + activity );
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.e(TAG, "onActivityResumed: " );
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e(TAG, "onActivityPaused: " );
    }


    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e(TAG, "onActivityDestroyed: " );
    }

    @Nullable
    public Trace getTrace(Activity activity) {
        Log.e(TAG, "getTrace: " + activity.getClass().getSimpleName() );
        return traces.get(activity);
    }
}
