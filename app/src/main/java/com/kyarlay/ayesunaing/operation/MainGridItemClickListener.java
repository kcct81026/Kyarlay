package com.kyarlay.ayesunaing.operation;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.kyarlay.ayesunaing.object.UniversalPost;

import java.util.ArrayList;

;

/**
 * Created by ayesunaing on 8/24/16.
 */
public class MainGridItemClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private ClickeListener clickListener;
    private ArrayList<UniversalPost> posts;



    public MainGridItemClickListener(Context context, final RecyclerView recyclerView, ArrayList<UniversalPost> posts, final ClickeListener clickListener) {
        this.clickListener  = clickListener;
        this.posts          = posts;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
