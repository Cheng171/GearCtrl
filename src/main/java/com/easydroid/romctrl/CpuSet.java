package com.easydroid.romctrl;

import android.app.*;
import android.os.*;
import android.view.*;
import android.graphics.*;
import android.widget.*;
import android.view.View.*;
import android.widget.SeekBar.*;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.content.*;
import android.util.*;
import java.lang.reflect.*;
import android.content.pm.*;
import android.net.*;
import com.easydroid.romctrl.permission.*;
import java.util.*;
import java.lang.Process;
import java.io.*;

public class CpuSet extends Activity implements OnClickListener{
	public TextView tv1,tv2,tv3;
	public static final int UPDATE_TEXT = 1;
	public static SharedPreferences sha;
    public static SharedPreferences.Editor editor;
    public static String cpu0StatusContent = "null";
	public static String cpu1StatusContent = "null";
	public static String cpu2StatusContent = "null";
	public static String cpu3StatusContent = "null";
	public static String cpu4StatusContent = "null";
	public static String cpu5StatusContent = "null";
	public static String cpu6StatusContent = "null";
	public static String cpu7StatusContent = "null";
	public static String CpurCpuGov = "null";
	public static String CpurMaxCpuFreq;
	public static String CpurMinCpuFreq;
	public static TextView showTextCPU0;
	public static TextView showTextCPU1;
	public static TextView showTextCPU2;
	public static TextView showTextCPU3;
	public static TextView showTextCPU4;
	public static TextView showTextCPU5;
	public static TextView showTextCPU6;
	public static TextView showTextCPU7;
	public Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
				case UPDATE_TEXT:
					showTextCPU0 = (TextView)findViewById(R.id.item_cpuTextView1);
					showTextCPU1 = (TextView)findViewById(R.id.item_cpuTextView2);
					showTextCPU2 = (TextView)findViewById(R.id.item_cpuTextView3);
					showTextCPU3 = (TextView)findViewById(R.id.item_cpuTextView4);
					showTextCPU4 = (TextView)findViewById(R.id.item_cpuTextView5);
					showTextCPU5 = (TextView)findViewById(R.id.item_cpuTextView6);
					showTextCPU6 = (TextView)findViewById(R.id.item_cpuTextView7);
					showTextCPU7 = (TextView)findViewById(R.id.item_cpuTextView8);
                    showTextCPU0.setText(cpu0StatusContent);
					showTextCPU1.setText(cpu1StatusContent);
					showTextCPU2.setText(cpu2StatusContent);
					showTextCPU3.setText(cpu3StatusContent);
					showTextCPU4.setText(cpu4StatusContent);
					showTextCPU5.setText(cpu5StatusContent);
					showTextCPU6.setText(cpu6StatusContent);
					showTextCPU7.setText(cpu7StatusContent);
					try{
						Time_CPU.text.setText("核心0:"+cpu0StatusContent);
						Time_CPU.text1.setText("核心1:"+cpu1StatusContent);
						Time_CPU.text2.setText("核心2:"+cpu2StatusContent);
						Time_CPU.text3.setText("核心3:"+cpu3StatusContent);
						Time_CPU.text4.setText("核心4:"+cpu4StatusContent);
						Time_CPU.text5.setText("核心5:"+cpu5StatusContent);
						Time_CPU.text6.setText("核心6:"+cpu6StatusContent);
						Time_CPU.text7.setText("核心7:"+cpu7StatusContent);
						Time_CPU.text8.setText("当前CPU最小频率:"+CpurMinCpuFreq);
						Time_CPU.text9.setText("当前CPU最大频率:"+CpurMaxCpuFreq);
						Time_CPU.text10.setText("CPU模式:"+CpurCpuGov);
					}catch(Exception e){
						System.out.println(e);
					}
					showAllDefault();
					break;
				default:
					break;
			}
		}
	};
    @Override
    protected void onCreate(Bundle savedInstanceState){
    {
		int identifier = getResources().getIdentifier("Theme.Material.Light", "style", "miui");
		     getActionBar().setHomeButtonEnabled(false);
getActionBar().setDisplayHomeAsUpEnabled(false);

        if (identifier == 0) {
            getResources().getIdentifier("Theme.Material.Light", "style", "miui");
        }
        if (identifier > 0) {
            setTheme(identifier);
        }
        super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cpuset);
		setWindowStatusBarColor(CpuSet.this,R.color.colorPrimary);
		DetectionofsuspendedPermissions();//获取悬浮权限
		
		new Thread(new Runnable() {
				@Override
				public void run() {
					while (true){
						try {
							cpu0StatusContent = IOHelper.getCpu0CruFreq_CPU0();
							cpu1StatusContent = IOHelper.getCpu0CruFreq_CPU1();
							cpu2StatusContent = IOHelper.getCpu0CruFreq_CPU2();
							cpu3StatusContent = IOHelper.getCpu0CruFreq_CPU3();
							cpu4StatusContent = IOHelper.getCpu0CruFreq_CPU4();
							cpu5StatusContent = IOHelper.getCpu0CruFreq_CPU5();
							cpu6StatusContent = IOHelper.getCpu0CruFreq_CPU6();
							cpu7StatusContent = IOHelper.getCpu0CruFreq_CPU7();
							CpurCpuGov = IOHelper.getCpu0CurGov();
							CpurMaxCpuFreq = IOHelper.getCpu0MaxFreq();
							CpurMinCpuFreq = IOHelper.getCpu0MinFreq();
							Message message = new Message();
							message.what = UPDATE_TEXT;
							handler.sendMessage(message);
							Thread.sleep(900, 0);//刷新CPU频率
						}catch (Exception ep){
							ep.printStackTrace();
						}

					}
				}
			}).start();
		
		findViewById(R.id.mainLinearLayout1).setOnClickListener(this);
		findViewById(R.id.mainLinearLayout2).setOnClickListener(this);
		findViewById(R.id.mainLinearLayout3).setOnClickListener(this);
		
		final TextView tv = (TextView)findViewById(R.id.mainTextView);
		tv.setText("隐藏");
		Switch mSwitch = (Switch)findViewById(R.id.mainSwitch);
		mSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						tv.setText("显示");
						Time_CPU_WindowManager.addBallView(CpuSet.this);
					}else{
						tv.setText("隐藏");
						Time_CPU_WindowManager.removeBallView(CpuSet.this);
					}
				}
			});
		Switch Switch1 =(Switch)findViewById(R.id.mainSwitch1);
		Switch1.setChecked(true);
		Switch1.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1,boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu0/online");
							dos.flush();
							showTextCPU0.setText(cpu0StatusContent);
							Time_CPU.text.setText("核心0:"+cpu0StatusContent);
						}
						catch (Exception e){
							System.out.println(e);
						}					
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu0/online");
							dos.flush();
							showTextCPU0.setText("");
							Time_CPU.text.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
		Switch Switch2 =(Switch)findViewById(R.id.mainSwitch2);
		Switch2.setChecked(true);
		Switch2.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu1/online");
							dos.flush();
							showTextCPU1.setText(cpu1StatusContent);
							Time_CPU.text1.setText("核心1:"+cpu1StatusContent);
						}catch(Exception e){
							System.out.println(e);
						}
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu1/online");
							dos.flush();
							showTextCPU1.setText("");
							Time_CPU.text1.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
		Switch Switch3 =(Switch)findViewById(R.id.mainSwitch3);
		Switch3.setChecked(true);
		Switch3.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu2/online");
							dos.flush();
							showTextCPU2.setText(cpu2StatusContent);
							Time_CPU.text2.setText("核心2:"+cpu2StatusContent);
						}catch(Exception e){
							System.out.println(e);
						}
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu2/online");
							dos.flush();
							showTextCPU2.setText("");
							Time_CPU.text2.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
		Switch Switch4 =(Switch)findViewById(R.id.mainSwitch4);
		Switch4.setChecked(true);
		Switch4.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu3/online");
							dos.flush();
							showTextCPU3.setText(cpu3StatusContent);
							Time_CPU.text3.setText("核心3:"+cpu3StatusContent);
						}catch(Exception e){
							System.out.println(e);
						}
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu3/online");
							dos.flush();
							showTextCPU3.setText("");
							Time_CPU.text3.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
		Switch Switch5 =(Switch)findViewById(R.id.mainSwitch5);
		Switch5.setChecked(true);
		Switch5.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu4/online");
							dos.flush();
							showTextCPU4.setText(cpu4StatusContent);
							Time_CPU.text4.setText("核心4:"+cpu4StatusContent);
						}catch(Exception e){
							System.out.println(e);
						}
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu4/online");
							dos.flush();
							showTextCPU4.setText("");
							Time_CPU.text4.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
		Switch Switch6 =(Switch)findViewById(R.id.mainSwitch6);
		Switch6.setChecked(true);
		Switch6.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu5/online");
							dos.flush();
							showTextCPU5.setText(cpu5StatusContent);
							Time_CPU.text5.setText("核心5:"+cpu5StatusContent);
						}catch(Exception e){
							System.out.println(e);
						}
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu5/online");
							dos.flush();
							showTextCPU5.setText("");
							Time_CPU.text5.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
		Switch Switch7 =(Switch)findViewById(R.id.mainSwitch7);
		Switch7.setChecked(true);
		Switch7.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu6/online");
							dos.flush();
							showTextCPU6.setText(cpu6StatusContent);
							Time_CPU.text6.setText("核心6:"+cpu6StatusContent);
						}catch(Exception e){
							System.out.println(e);
						}
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu6/online");
							dos.flush();
							showTextCPU6.setText("");
							Time_CPU.text6.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
		Switch Switch8 =(Switch)findViewById(R.id.mainSwitch8);
		Switch8.setChecked(true);
		Switch8.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2){
					if(p2){
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu7/online");
							dos.flush();
							showTextCPU7.setText(cpu7StatusContent);
							Time_CPU.text7.setText("核心7:"+cpu7StatusContent);
						}catch(Exception e){
							System.out.println(e);
						}
					}else{
						try{
							Process p = Runtime.getRuntime().exec("su");
							DataOutputStream dos = new DataOutputStream(p.getOutputStream());
							dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu7/online");
							dos.flush();
							showTextCPU7.setText("");
							Time_CPU.text7.setText("");
						}catch(Exception e){
							System.out.println(e);
						}
					}
				}
			});
	}}
	
	private void showAllDefault(){
        tv1 = (TextView)findViewById(R.id.mainTextView1);
		tv2 = (TextView)findViewById(R.id.mainTextView2);
		tv3 = (TextView)findViewById(R.id.mainTextView3);
		String curMaxCpuFreq = IOHelper.getCpu0MaxFreq();
        String curMinCpuFreq = IOHelper.getCpu0MinFreq();
        String curCpuGov = IOHelper.getCpu0CurGov();
		tv1.setText(curCpuGov);
		tv2.setText(curMinCpuFreq);
		tv3.setText(curMaxCpuFreq);
    }
    
	public static void setWindowStatusBarColor(Activity activity, int colorResId){
		try{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
				Window window = activity.getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(activity.getResources().getColor(colorResId));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	//获取悬浮权限
	public void DetectionofsuspendedPermissions(){
		FloatWindowManager.getInstance().applyOrShowFloatWindow(CpuSet.this);
	}

	@Override
	public void onClick(View p1){
		switch(p1.getId()){
			case R.id.mainLinearLayout1:
				showSelectCpuCurGov();
				break;
			case R.id.mainLinearLayout2:
				showSelectCpuMinFreqDialog();
				break;
			case R.id.mainLinearLayout3:
				showSelectCpuMaxFreqDialog();
				break;
		}
	}

	//CPU模式
	private void showSelectCpuCurGov(){
        AlertDialog.Builder cpuCurGovDialog = new AlertDialog.Builder(this);
        cpuCurGovDialog.setTitle("Select Governor");
        cpuCurGovDialog.setNegativeButton("cancel", null);

        final String[] cpuAvailableGov = IOHelper.getCpuAvailableGov();

        cpuCurGovDialog.setItems(cpuAvailableGov, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					IOHelper.setCpu0CurGov(cpuAvailableGov[which]);
				}
			});
        cpuCurGovDialog.create().show();
    }
	
	//CPU最小频率 
	private void showSelectCpuMinFreqDialog(){
        AlertDialog.Builder cpuFreqDialog = new AlertDialog.Builder(this);
        cpuFreqDialog.setTitle("Select Frequency");
        cpuFreqDialog.setNegativeButton("cancel", null);

        final String[] cpuAvailableFreq = IOHelper.getCpu0AvailableFreq();

        cpuFreqDialog.setItems(cpuAvailableFreq, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					IOHelper.setCpu0MinFreq(cpuAvailableFreq[which]);
					// showAllDefault();
				}
			});
        cpuFreqDialog.create().show();
    }
	
	//CPU最大频率
	private void showSelectCpuMaxFreqDialog(){
        AlertDialog.Builder cpuFreqDialog = new AlertDialog.Builder(this);
        cpuFreqDialog.setTitle("Select Frequency");
        cpuFreqDialog.setNegativeButton("cancel", null);

        final String[] cpuAvailableFreq = IOHelper.getCpu0AvailableFreq();

        cpuFreqDialog.setItems(cpuAvailableFreq, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					IOHelper.setCpu0MaxFreq(cpuAvailableFreq[which]);
					// showAllDefault();
				}
			});
        cpuFreqDialog.create().show();
    }
	
	public void Time(String s){
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
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

