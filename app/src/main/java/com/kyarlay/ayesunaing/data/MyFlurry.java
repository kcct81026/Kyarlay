package com.kyarlay.ayesunaing.data;

import android.app.Activity;
import android.util.Log;

import com.flurry.android.FlurryAgent;

public class MyFlurry  implements ConstantVariable {

    public MyFlurry(Activity activity) {
        new FlurryAgent.Builder()
        .withLogEnabled(true)
        .withCaptureUncaughtExceptions(true)
        .withContinueSessionMillis(10000)
        .withLogLevel(Log.VERBOSE)
        .build(activity, FLURRY_API_KEY);


    }


}
