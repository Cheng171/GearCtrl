package com.easydroid.romctrl.widget;

import android.view.*;
import android.widget.*;
import com.easydroid.romctrl.*;

public class CustomViewImpl
{
	public void setListHeaderTitle(String text, View view)
	{
		TextView setTitleObject = (TextView) view.findViewById(R.id.customview_listheaderText);
		setTitleObject.setText(text);
	}
	
	public void setListHeaderTopHrDisplayState(boolean bool, View view){
		LinearLayout setListHeaderTopHrDisplayStateObject = (LinearLayout) view.findViewById(R.id.customview_listheaderLinearLayoutTop);
		if (bool){
			setListHeaderTopHrDisplayStateObject.setVisibility(View.VISIBLE);
		} else {
			setListHeaderTopHrDisplayStateObject.setVisibility(View.GONE);
		}
	}
	
	public void setCheckBoxListTitle(String text, View view){
		TextView setCheckBoxListTitleObject = (TextView) view.findViewById(R.id.customview_checkboxlistitemTextView);
		setCheckBoxListTitleObject.setText(text);
	}
	
	public void setCheckBoxListSummary(String text, View view)
	{
		TextView setSummaryObject = (TextView) view.findViewById(R.id.customview_checkboxlistitemTextViewSmall);
		setSummaryObject.setSingleLine(true);
		setSummaryObject.setVisibility(View.VISIBLE);
		setSummaryObject.setText(text);
		if (text == ""){
			setSummaryObject.setVisibility(View.GONE);
		}
	}

	public void setCheckBoxListDisplayCheckBox(boolean bool, View view)
	{
		CheckBox setDisplayCheckBoxObject = (CheckBox) view.findViewById(R.id.customview_checkboxlistitemCheckBox);
		if (bool)
		{
			setDisplayCheckBoxObject.setVisibility(View.VISIBLE);
		}
		else
		{
			setDisplayCheckBoxObject.setVisibility(View.GONE);
		}
	}
	
	public String getCheckBoxListSummary(View view){
		TextView getCheckBoxListSummaryObject = (TextView) view.findViewById(R.id.customview_checkboxlistitemTextViewSmall);
		return getCheckBoxListSummaryObject.getText().toString();
	}
	
}
