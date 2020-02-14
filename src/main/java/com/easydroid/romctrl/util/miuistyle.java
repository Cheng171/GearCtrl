package com.easydroid.romctrl.util;
import android.app.*;

public class miuistyle
{
	public void set(Activity act){
		int miuistyleint = act.getResources().getIdentifier("Theme.Light.Settings", "style", "miui");
		if (miuistyleint > 0){
			act.setTheme(miuistyleint);
		}
	}
}
