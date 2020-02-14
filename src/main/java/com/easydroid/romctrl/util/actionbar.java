package com.easydroid.romctrl.util;
import android.app.*;
import android.graphics.drawable.*;

public class actionbar
{
	
	public void display(Activity act, int state){
		switch (state){
			case 0:
				act.getActionBar().hide();
			break;
			case 1:
				act.getActionBar().show();
			break;
			default:
		}
	}
	
	public void displayFromBool(Activity act, boolean state){
		if (state){
			display(act, 1);
		} else {
			display(act, 2);
		}
	}
	
	public void bgcolor(Activity act, int color){
		ColorDrawable cdraw = new ColorDrawable(color);
		act.getActionBar().setBackgroundDrawable(cdraw);
	}
	
	public void setelevation(Activity act, float fl){
		act.getActionBar().setElevation(fl);
	}
	
	public void isBackIcon(Activity act){
		act.getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
}
