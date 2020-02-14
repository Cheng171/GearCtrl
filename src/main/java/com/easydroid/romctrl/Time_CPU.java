package com.easydroid.romctrl;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.lang.reflect.Field;
import android.widget.*;
import android.os.*;

public class Time_CPU extends LinearLayout{
    private WindowManager mWindowManager;
	private Vibrator mVibrator;
	public static TextView text,text1,text2,text3,text4,text5,text6,text7,text8,text9,text10;
    public Time_CPU(Context context) {
        super(context);
        mVibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.time_cpu, this);
		text = (TextView)findViewById(R.id.timecpuTextView);
		text1 = (TextView)findViewById(R.id.timecpuTextView1);
		text2 = (TextView)findViewById(R.id.timecpuTextView2);
		text3 = (TextView)findViewById(R.id.timecpuTextView3);
		text4 = (TextView)findViewById(R.id.timecpuTextView4);
		text5 = (TextView)findViewById(R.id.timecpuTextView5);
		text6 = (TextView)findViewById(R.id.timecpuTextView6);
		text7 = (TextView)findViewById(R.id.timecpuTextView7);
		text8 = (TextView)findViewById(R.id.timecpuTextView8);
		text9 = (TextView)findViewById(R.id.timecpuTextView9);
		text10 = (TextView)findViewById(R.id.timecpuTextView10);
		text.setText("核心0:"+CpuSetV2.cpu0StatusContent);
		text1.setText("核心1:"+CpuSetV2.cpu1StatusContent);
		text2.setText("核心2:"+CpuSetV2.cpu2StatusContent);
		text3.setText("核心3:"+CpuSetV2.cpu3StatusContent);
		text4.setText("核心4:"+CpuSetV2.cpu4StatusContent);
		text5.setText("核心5:"+CpuSetV2.cpu5StatusContent);
		text6.setText("核心6:"+CpuSetV2.cpu6StatusContent);
		text7.setText("核心7:"+CpuSetV2.cpu7StatusContent);
		text8.setText("当前CPU最小频率:"+CpuSet.CpurMinCpuFreq);
		text9.setText("当前CPU最大频率:"+CpuSet.CpurMaxCpuFreq);
		text10.setText("CPU模式:"+CpuSet.CpurCpuGov);
	}

}
