package com.kyarlay.ayesunaing.fcm;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PerfInitContentProvider extends ContentProvider {

    Context context;


    @Override
    public boolean onCreate() {
        context = getContext();
        if (context != null) {
            Application app = (Application) context.getApplicationContext();
            app.registerActivityLifecycleCallbacks(
                    PerfLifecycleCallbacks.getInstance());
        }
        return false;
    }


    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,  @Nullable String sortOrder) {
        return null;
    }


    @Override
    public String getType( @NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri,  @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete( @NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( @NonNull Uri uri,  @Nullable ContentValues values,  @Nullable String selection,  @Nullable String[] selectionArgs) {
        return 0;
    }
}
