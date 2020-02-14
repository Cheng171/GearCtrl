package com.easydroid.romctrl.conf;

import android.os.*;
import com.easydroid.romctrl.util.*;
import android.app.*;
import android.view.*;

public class headerDefaultConf
{
	public void conf(Activity act, Window win)
	{
		new actionbar().displayFromBool(act, true);
		if (Build.VERSION.SDK_INT >= 23)
		{
			new actionbar().bgcolor(act, 0xffffffff);
			new actionbar().setelevation(act, 1f);
		}
		new statusbar().light(win, 0xffffffff);
		new miuistyle().set(act);
	}
}
