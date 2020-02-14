package com.easydroid.romctrl;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class FileActivity extends Activity {
	ArrayList<Map<String, String>> list;
	ArrayList<File> fileList;
	String backupParentPath; // /AppData/Backup/
	Context context = this;
	PopupMenu popup;
	String wifiPath; // /data/misc/wifi/xxx.xml
	String backupPath;
	int num;
	//String wifiFileName; // xxx.xml
	//String pickerFilePath;
	
	java.util.Date utilDate = new java.util.Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.list);
		
		backupParentPath = context.getExternalFilesDir("Backup").getPath();
		
		if (android.os.Build.VERSION.SDK_INT >= 26) wifiPath = "/data/misc/wifi/WifiConfigStore.xml"; else wifiPath = "/data/misc/wifi/wpa_supplicant.conf";
		
		work();
	}
	
	
	private void work() {
		fileList = new ArrayList<File>();
		getAllFiles(new File(backupParentPath));
		List<String> data = new ArrayList<String>();
		for(int i=0;i<fileList.size();i++) {
			String name = fileList.get(i).toString().substring(fileList.get(i).toString().lastIndexOf("/")+1,fileList.get(i).toString().length());
			data.add(name);
		}
		final String[] strings = new String[data.size()];
		data.toArray(strings);
		num = data.size();

		final ListView lv = (ListView) findViewById(R.id.lv);
		ListAdapter adapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
					//Toast.makeText(FileActivity.this, backupParentPath + "/" + strings[position], 0).show();
					popup = new PopupMenu(FileActivity.this, view);
					getMenuInflater().inflate(R.menu.wifi,popup.getMenu());
					popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
							@Override
							public boolean onMenuItemClick(MenuItem item) {
								switch(item.getItemId()) {
									case R.id.menu_recovery:
										showRestoreDialog(backupParentPath + "/" + strings[position]);
										break;
									case R.id.menu_read:
										Intent intent1 = new Intent();  
										intent1.setClass(FileActivity.this, ViewActivity.class);
										intent1.putExtra("path", backupParentPath + "/" + strings[position]);
										startActivity(intent1);
										break;
									case R.id.menu_delete1:
										try {
											Runtime.getRuntime().exec("su /n rm -f " + backupParentPath + "/" + strings[position]);
											Toast.makeText(FileActivity.this, "已删除备份", Toast.LENGTH_SHORT).show();
											Intent intent = getIntent();
											finish();
											startActivity(intent);
										} catch (IOException e) {
											Toast.makeText(FileActivity.this, "删除备份:" + e, Toast.LENGTH_LONG).show();
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0,0,0,"添加备份");
		menu.add(0,1,0,"Notice");
		menu.getItem(1).setEnabled(false);
		menu.getItem(1).setTitle("共 " + num + " 条备份");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				showBackupDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	private void showBackupDialog() {
		final EditText et = new EditText(this);
		AlertDialog.Builder BackupDialog = new AlertDialog.Builder(this);
		BackupDialog.setTitle("备份名称");
		BackupDialog.setView(et);
		et.setText(formatter.format(utilDate));
		et.setFilters(new InputFilter[] { new InputFilter() { 
							  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) { 
								  for (int i = start; i < end; i++) { 
									  if ( !Character.isLetterOrDigit(source.charAt(i)) && !Character.toString(source.charAt(i)) .equals("_") && !Character.toString(source.charAt(i)) .equals("-")) { 
										  return ""; 
									  } 
								  } 
								  return null; 
								  }}}); 
		BackupDialog.setPositiveButton("开始",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					backupPath = backupParentPath + "/" + et.getText().toString();
					try {
						Runtime.getRuntime().exec("su /n cp -f " + wifiPath + " " + backupPath);
						Toast.makeText(FileActivity.this, "备份完成", Toast.LENGTH_SHORT).show();
					} catch (IOException e){
						Toast.makeText(FileActivity.this, "备份:" + e, Toast.LENGTH_LONG).show();
					}
					Intent intent = getIntent();
					finish();
					startActivity(intent);
				}
			});
		BackupDialog.setNegativeButton("取消", null);
		BackupDialog.setNeutralButton("分享",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					backupPath = backupParentPath + "/" + et.getText().toString();
					try {
						Runtime.getRuntime().exec("su /n cp -f " + wifiPath + " " + backupPath);
					} catch (IOException e) {
						Toast.makeText(FileActivity.this, "备份:" + e, Toast.LENGTH_LONG).show();
					}
					File sendFile = new File(backupPath);
					Intent share = new Intent(Intent.ACTION_SEND);
					share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sendFile));
					share.setType("*/*");
					startActivity(Intent.createChooser(share, "分享"));
				}
			});
		BackupDialog.show();
	}
	
	private void showRestoreDialog(final String path) {
		AlertDialog.Builder RestoreDialog = new AlertDialog.Builder(this);
		RestoreDialog.setTitle("警告");
		RestoreDialog.setMessage("此操作将会替换设备中的WiFi密码文件。是否继续？\n\n" + "当前文件：\n" + path);
		RestoreDialog.setPositiveButton("继续",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog,int which) {
					try {
						Runtime.getRuntime().exec("su /n cp -f " + path + " " + wifiPath + " /n " + "chmod 660 " + wifiPath);
					}
					catch (IOException e) {
						Toast.makeText(FileActivity.this, "恢复覆盖:" + e, Toast.LENGTH_LONG).show();
					}
				}
			});
		RestoreDialog.setNegativeButton("取消", null);
		RestoreDialog.show();
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
	
	private void getAllFiles(File root) {
		File files[] = root.listFiles();
		if(files != null)
			for(File f:files) {
				if(f.isDirectory()) {
					getAllFiles(f);
				}
				else{
					this.fileList.add(f);
				}
			}
	}
	
	
}
