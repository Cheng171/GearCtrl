package com.easydroid.romctrl;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.*;
import android.util.*;
import java.lang.reflect.*;

public class Time_CPU_WindowManager{
    private static Time_CPU View;
    private static WindowManager mWindowManager;
    public static void addBallView(Context context) {
        if (View == null) {
            WindowManager windowManager = getWindowManager(context);
            View = new Time_CPU(context);
            LayoutParams params = new LayoutParams();
            params.x = 0;
            params.y = 0;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.LEFT | Gravity.BOTTOM;
            params.type = LayoutParams.TYPE_PHONE
				|LayoutParams.TYPE_SYSTEM_OVERLAY;
            params.format = PixelFormat.RGBA_8888;
            params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				|LayoutParams.FLAG_NOT_FOCUSABLE
				|LayoutParams.FLAG_NOT_TOUCHABLE
				|LayoutParams.FLAG_LAYOUT_NO_LIMITS
				|LayoutParams.FLAG_LAYOUT_IN_SCREEN
				|LayoutParams.FLAG_HARDWARE_ACCELERATED;
            View.setLayoutParams(params);
            windowManager.addView(View, params);
        }
    }

    public static void removeBallView(Context context) {
        if (View != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(View);
            View = null;
        }
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

}
