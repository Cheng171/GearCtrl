package com.easydroid.romctrl;
import android.os.*;
import android.preference.*;
import android.view.*;


public class MainActivity extends PreferenceActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
		
		new com.easydroid.romctrl.conf.headerDefaultConf().conf(this, getWindow());
		new com.easydroid.romctrl.util.actionbar().isBackIcon(this);
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		// TODO: Implement this method
	}

}
