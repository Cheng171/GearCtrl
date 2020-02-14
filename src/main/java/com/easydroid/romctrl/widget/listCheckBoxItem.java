package com.easydroid.romctrl.widget;
import android.widget.*;
import android.content.*;
import android.util.*;
import com.easydroid.romctrl.R;
import android.view.*;

public class listCheckBoxItem extends LinearLayout
{
	public listCheckBoxItem(Context context)
	{
		this(context, null);
	}

	public listCheckBoxItem(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
		LayoutInflater.from(context).inflate(R.layout.customview_checkboxlistitem, this, true);
	}
	
}
