package com.kyarlay.ayesunaing.data;


import android.app.ActivityManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.LruCache;


public final class TypefaceManager {

    public static AssetManager manager;
    public ActivityManager am;
    public static LruCache mCache;
    public TypefaceManager.Companion companion = new TypefaceManager.Companion();


    public static final String PYIDAUNGSU = "fonts/pyidaungsu.ttf";
    public static final String ZAWGYITWO = "fonts/zawgyitwo.ttf";



    public TypefaceManager(AssetManager manager, Context context){
        this.manager = manager;
        this.am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        int availMemInBytes = am.getMemoryClass() * 1024 * 1024;
        this.mCache = new LruCache(availMemInBytes / 8);

    }



    public static Typeface getTypeface(String filename){

        Typeface typeface = (Typeface) mCache.get(filename);
        if (typeface == null){
            typeface = Typeface.createFromAsset(manager, filename);
        }

        return typeface;

    }

    public  Companion getCompanion() {
        return companion;
    }

    public class Companion{
        public Typeface getPYIDAUNGSU(){
            return  TypefaceManager.getTypeface(PYIDAUNGSU);
        }

        public Typeface getZAWGYITWO(){
            return  TypefaceManager.getTypeface(ZAWGYITWO);        }

        public Companion(){}


    }

}

