package com.easydroid.romctrl.widget;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.easydroid.romctrl.R;

public class listHeaderText extends LinearLayout
{
	public listHeaderText(Context context)
	{
		this(context, null);
	}

	public listHeaderText(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
		LayoutInflater.from(context).inflate(R.layout.customview_listheader, this, true);
	}
}
