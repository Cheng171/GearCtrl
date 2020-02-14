package com.easydroid.romctrl;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class WiFiAdapter extends BaseAdapter {
		private Context context;
		ArrayList<Map<String,String>> mainList;
		//WiFiAdapter mAdapter = new WiFiAdapter();

		public WiFiAdapter(Context context, ArrayList<Map<String,String>> list) {
			super();
			this.context = context;
			this.mainList = list;
		}

		@Override
		public int getCount() {
			return mainList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ViewHolder holder;
			Map<String,String> m = mainList.get(position);
			if (view != null) {
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(context,R.layout.item, null);
				holder = new ViewHolder();
				holder.ssid = (TextView) view.findViewById(R.id.title);
				holder.password = (TextView) view.findViewById(R.id.text);
				view.setTag(holder);
			}
			holder.ssid.setText(m.get("ssid"));
			holder.password.setText(m.get("psk"));
			return view;
		}
		
		class ViewHolder {
			public TextView ssid;
			public TextView password;
		}
	
	}
