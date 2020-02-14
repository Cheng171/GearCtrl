package com.easydroid.romctrl;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;

import java.lang.Process;
import android.text.*;

public class LCDFix extends Activity {
	Button rootbtn;
	Button btnreboot;
	Button btnre;
	TextView defSize;
	TextView nowSize;
	TextView defDensity;
	TextView nowDensity;
	ImageButton btnr;
	ImageButton btnd;
	ImageButton btndr;
	ImageButton btndd;
	EditText etw;
	EditText eth;
	EditText etd;
	String DefSize;
	String NowSize;
	String DefDensity;
	String NowDensity;
	String W;
	String H;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    {
		new com.easydroid.romctrl.conf.headerDefaultConf().conf(this, getWindow());
				new com.easydroid.romctrl.util.actionbar().isBackIcon(this);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.lcdfix);
		
		rootbtn = (Button) findViewById(R.id.rootbtn);
		rootbtn.setText("软件没有获取权限");
		rootbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (check_root()) {
					root();
				}
			}
		});
		
		defSize = (TextView) findViewById(R.id.DefSize);
		nowSize = (TextView) findViewById(R.id.NowSize);
		defDensity = (TextView) findViewById(R.id.DefDensity);
		nowDensity = (TextView) findViewById(R.id.NowDensity);
		
		etw = (EditText) findViewById(R.id.etSizew);
		eth = (EditText) findViewById(R.id.etSizeh);
		etd = (EditText) findViewById(R.id.etd);
		
		r();
		
		if (check_root()) {
			root();
		}
		
    }}
	
	public void root() {
		ImageButton btnr = (ImageButton) findViewById(R.id.btnr);
		btnd = (ImageButton) findViewById(R.id.btnSized);
		btndr = (ImageButton) findViewById(R.id.btndr);
		btndd = (ImageButton) findViewById(R.id.btndd);
		btnreboot = (Button) findViewById(R.id.btnreboot);
		btnre = (Button) findViewById(R.id.btnre);
		
		rootbtn.setText("已经获取权限");

		btnr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					run_cmd("wm size " + DefSize);
					Toast.makeText(LCDFix.this, "已恢复默认！", Toast.LENGTH_SHORT).show();
					r();
				}
			});

		btnd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (TextUtils.isEmpty(etw.getText()) || TextUtils.isEmpty(eth.getText())) {
						Toast.makeText(LCDFix.this, "请输入值！", Toast.LENGTH_SHORT).show();
					} else {
						run_cmd("wm size " + etw.getText().toString() + "x" + eth.getText().toString());
						Toast.makeText(LCDFix.this, "完成！", Toast.LENGTH_SHORT).show();
						r();
					}
				}
			});

		btndr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					run_cmd("wm density " + DefDensity);
					Toast.makeText(LCDFix.this, "已恢复默认！", Toast.LENGTH_SHORT).show();
					r();
				}
			});

		btndd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (TextUtils.isEmpty(etd.getText())) {
						Toast.makeText(LCDFix.this, "请输入值！", Toast.LENGTH_SHORT).show();
					} else {
						run_cmd("wm density" + etd.getText().toString());
						Toast.makeText(LCDFix.this, "完成！", Toast.LENGTH_SHORT).show();
						r();
					}
				}
			});
			
		btnreboot.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					run_cmd("su -c killall system_server");
				}
			});
			
		btnre.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					run_cmd("wm size " + DefSize);
					run_cmd("wm density " + DefDensity);
					Toast.makeText(LCDFix.this, "已全部恢复默认！", Toast.LENGTH_SHORT).show();
					r();
				}
			});
	}
	
	public void r() {
		try {execCommand("wm size");} catch (IOException e) {}
		defSize.setText(DefSize);
		nowSize.setText(NowSize);
		try {execCommand("wm density");} catch (IOException e) {}
		defDensity.setText(DefDensity);
		nowDensity.setText(NowDensity);
		String a[] = NowSize.split("x");
		etw.setText(a[0]);
		eth.setText(a[1]);
		etd.setText(NowDensity);
	}
	
	public void execCommand(String command) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec(command);
		try {
			if (proc.waitFor() != 0) {
				System.err.println("exit value = " + proc.exitValue());
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuffer.append(line);
			}
			if (stringBuffer.toString().contains("size")) {
				if (stringBuffer.toString().contains("Override")) {
					DefSize = stringBuffer.toString().substring(stringBuffer.toString().indexOf(":")+2,stringBuffer.toString().indexOf("O"));
					NowSize = stringBuffer.toString().substring(stringBuffer.toString().indexOf(":",stringBuffer.toString().indexOf(":")+1)+2);
					//NowSize = stringBuffer.toString().substring(stringBuffer.toString().indexOf("Override size")+17);
				} else {
					DefSize = stringBuffer.toString().substring(stringBuffer.toString().indexOf(":")+2);
					NowSize = DefSize;
				}
			} else if (stringBuffer.toString().contains("density")) {
				if (stringBuffer.toString().contains("Override")) {
					DefDensity = stringBuffer.toString().substring(stringBuffer.toString().indexOf(":")+2,stringBuffer.toString().indexOf("O"));
					//NowDensity = stringBuffer.toString().substring(stringBuffer.toString().indexOf(":",stringBuffer.toString().indexOf(":")+1));
					NowDensity = stringBuffer.toString().substring(stringBuffer.toString().indexOf(":",stringBuffer.toString().indexOf(":")+1)+2);
				} else {
					DefDensity = stringBuffer.toString().substring(stringBuffer.toString().indexOf(":")+1);
					NowDensity = DefDensity;
				}
			}
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
	
	
	public boolean run_cmd(String cmd) {
        Process process = null;
        DataOutputStream os = null;
		int re = 1;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
			re = process.waitFor();
        } catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
					Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        return re == 0;
    }
	
	public boolean check_root() {
		try {
			return run_cmd("system/bin/mount -o rw,remount -t rootfs /data");
		} catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		return false;
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

