package com.easydroid.romctrl;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;

import java.lang.Process;

public class ViewActivity extends Activity {
	ArrayList<Map<String, String>> mainList;
	PopupMenu popup;
	Context context = this;
	String backupPath;
	String sPath;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list);
		backupPath = context.getExternalFilesDir("Backup").getPath() + "/LastBackup";
		
		Intent intent = getIntent();
		
		if(Intent.ACTION_VIEW.equals(getIntent().getAction())) {
			sPath = intent.getDataString().substring(8);
			if(sPath.contains("%20")) {
				Toast.makeText(this, "路径不能包含空格", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				mainList = get(sPath);
			}
		} else {
			Bundle bundle = intent.getExtras();
			sPath = bundle.getString("path");
			mainList = get(sPath);
		}
		
		onWork();
		
	}
	
	private void onWork() {
		if (mainList == null) {
			Toast.makeText(ViewActivity.this, "获取列表失败", Toast.LENGTH_LONG).show();
		} else {
			if (mainList.size() == 0) {
				Toast.makeText(ViewActivity.this, "列表为空", Toast.LENGTH_LONG).show();
			} else {
				doWork();
			}
		}
	}
	
	private void doWork() {
		ListView lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new WiFiAdapter(this, mainList));
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
					popup = new PopupMenu(ViewActivity.this, view);
					getMenuInflater().inflate(R.menu.copy,popup.getMenu());
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								final ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
								switch(item.getItemId()) {
									case R.id.menu_ssid:
										cm.setPrimaryClip(ClipData.newPlainText(null, mainList.get(position).get("ssid")));
										Toast.makeText(ViewActivity.this, "SSID已复制", Toast.LENGTH_SHORT).show();
										break;
									case R.id.menu_password:
										cm.setPrimaryClip(ClipData.newPlainText(null, mainList.get(position).get("psk")));
										Toast.makeText(ViewActivity.this, "密码已复制", Toast.LENGTH_SHORT).show();
										break;
									case R.id.menu_all:
										Map<String, String> s = mainList.get(position);
										cm.setPrimaryClip(ClipData.newPlainText(null, "SSID: " + s.get("ssid") + "\n" + "密码: " + s.get("psk")));
										Toast.makeText(ViewActivity.this, "SSID和密码都已复制", Toast.LENGTH_SHORT).show();
										break;
									case R.id.menu_view:
										AlertDialog.Builder TextDialog = new AlertDialog.Builder(ViewActivity.this);
										TextDialog.setTitle("源信息浏览").setMessage(mainList.get(position).get("pos") + "\n" + mainList.get(position).get("view")).setPositiveButton("关闭",null).setNeutralButton("复制", new DialogInterface.OnClickListener(){@Override public void onClick(DialogInterface dialog, int which) {
													cm.setPrimaryClip(ClipData.newPlainText(null, mainList.get(position).get("view")));
												}}).show();
										break;
									case R.id.menu_delete:
										//run("cp -f " + wifiPath + " " + backupPath);
										
										try {
											delete(mainList.get(position).get("view"));
										} catch (IOException e) {
											Toast.makeText(ViewActivity.this, "删除WiFi:" + e, Toast.LENGTH_LONG).show();
										}
										
										if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
											run("cp -f " + backupPath + " " + sPath);
											Toast.makeText(ViewActivity.this, "已删除", Toast.LENGTH_SHORT).show();
											mainList.clear();
											mainList = get(sPath);
											onWork();
											/*Intent intent = getIntent();
											finish();
											startActivity(intent);*/
										} else {
											run("cp -f " + backupPath + " " + sPath);
											run("chmod 660 " + sPath);
											Toast.makeText(ViewActivity.this, "已删除", Toast.LENGTH_SHORT).show();
											mainList.clear();
											mainList = get(sPath);
											onWork();
											/*Intent intent = getIntent();
											finish();
											startActivity(intent);*/
										}
										
										break;
									default:
										return false;
								}
								return true;
							}
						});
					popup.show();
				}
			});
			
	}

	private void delete(String ss) throws IOException {
        String s = "";
        DataOutputStream os = null;
		BufferedReader br = null;
		OutputStreamWriter pw = null;
        try {
            Process p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("cat " + sPath + "\n");
            os.writeBytes("exit\n");
            os.flush();
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                s += line.trim() + "\n";
            }
			pw = new OutputStreamWriter(new FileOutputStream(backupPath),"UTF-8");
			s = s.replace(ss,"");
			pw.write(s);
			pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,0,"刷新");
		menu.add(0,1,0,"打开WiFi设置");
		menu.add(0,2,0,"备份与恢复");
		menu.add(0,3,0,"获取列表出错");
		menu.getItem(3).setEnabled(false);
		if(mainList != null) menu.getItem(3).setTitle("共 " + mainList.size() + " 条WiFi");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				mainList.clear();
				mainList = get(sPath);
				onWork();
				return true;
			case 1:
				startActivity(new Intent().setClassName("com.android.settings","com.android.settings.wifi.WifiSettings"));
				return true;
			case 2:
				startActivity(new Intent().setClassName("com.easydroid.romctrl","com.easydroid.romctrl.FileActivity"));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public ArrayList<Map<String, String>> get(String path) {
        try {
			ReadFile file = new ReadFile(path);
            return file.getList(this.context);
        } catch (Exception e) {
			Toast.makeText(this, "ReadFile:" + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
	
	public boolean run(String command) {
        Process process = null;
        DataOutputStream os = null;
        int r = 1;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            r = process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return r == 0;
    }
	
}
