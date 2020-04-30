package com.matloob.myresponsiveapp.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

/**
 * This class is a custom DrawerLayout that intercept touch events in case of landscape mode
 */
public class MyCustomDrawer extends DrawerLayout {

    public MyCustomDrawer(Context context) {
        super(context);
    }

    public MyCustomDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        View drawer = getChildAt(1);

        if (getDrawerLockMode(drawer) == LOCK_MODE_LOCKED_OPEN && ev.getRawX() > drawer.getWidth()) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

}