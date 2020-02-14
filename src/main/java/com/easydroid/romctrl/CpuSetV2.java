package com.easydroid.romctrl;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.easydroid.romctrl.permission.*;
import com.easydroid.romctrl.widget.*;
import java.io.*;

import java.lang.Process;

public class CpuSetV2 extends Activity
{

	public static final int UPDATE_TEXT = 1;
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


	public Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case UPDATE_TEXT:
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl0set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu0StatusContent, (LinearLayout) findViewById(R.id.kl0set));
					}
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl1set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu1StatusContent, (LinearLayout) findViewById(R.id.kl1set));
					}
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl2set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu2StatusContent, (LinearLayout) findViewById(R.id.kl2set));
					}
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl3set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu3StatusContent, (LinearLayout) findViewById(R.id.kl3set));
					}
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl4set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu4StatusContent, (LinearLayout) findViewById(R.id.kl4set));
					}
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl5set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu5StatusContent, (LinearLayout) findViewById(R.id.kl5set));
					}
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl6set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu6StatusContent, (LinearLayout) findViewById(R.id.kl6set));
					}
					if (new com.easydroid.romctrl.widget.CustomViewImpl().getCheckBoxListSummary((LinearLayout) findViewById(R.id.kl7set)) != "已禁用")
					{
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary(cpu7StatusContent, (LinearLayout) findViewById(R.id.kl7set));
					}try
					{
						Time_CPU.text.setText("核心0:" + cpu0StatusContent);
						Time_CPU.text1.setText("核心1:" + cpu1StatusContent);
						Time_CPU.text2.setText("核心2:" + cpu2StatusContent);
						Time_CPU.text3.setText("核心3:" + cpu3StatusContent);
						Time_CPU.text4.setText("核心4:" + cpu4StatusContent);
						Time_CPU.text5.setText("核心5:" + cpu5StatusContent);
						Time_CPU.text6.setText("核心6:" + cpu6StatusContent);
						Time_CPU.text7.setText("核心7:" + cpu7StatusContent);
						Time_CPU.text8.setText("当前CPU最小频率:" + CpurMinCpuFreq);
						Time_CPU.text9.setText("当前CPU最大频率:" + CpurMaxCpuFreq);
						Time_CPU.text10.setText("CPU模式:" + CpurCpuGov);
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
					break;
				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		new com.easydroid.romctrl.conf.headerDefaultConf().conf(this, getWindow());
		new com.easydroid.romctrl.util.actionbar().isBackIcon(this);

		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cpuset_v2);
		setInitUI();

		// 悬浮窗
		FloatWindowManager.getInstance().applyOrShowFloatWindow(CpuSetV2.this);


		new Thread(new Runnable() {
				@Override
				public void run()
				{
					while (true)
					{
						try
						{
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
						}
						catch (Exception ep)
						{
							ep.printStackTrace();
						}

					}
				}
			}).start();
	}

	protected void setInitUI()
	{

		new CustomViewImpl().setCheckBoxListTitle("显示CPU详细信息悬浮窗", (LinearLayout)  findViewById(R.id.displayCPUTemp));
		new CustomViewImpl().setCheckBoxListSummary("隐藏", (LinearLayout) findViewById(R.id.displayCPUTemp));
		LinearLayout displayCPUTempObject = (LinearLayout) findViewById(R.id.displayCPUTemp);
		displayCPUTempObject.setOnClickListener(new View.OnClickListener(){
				public void onClick(View p1)
				{
					CheckBox displayCPUTempCheckBox = (CheckBox) p1.findViewById(R.id.customview_checkboxlistitemCheckBox);
					if (!displayCPUTempCheckBox.isChecked())
					{
						displayCPUTempCheckBox.setChecked(true);
					}
					else
					{
						displayCPUTempCheckBox.setChecked(false);
					}
				}
			});
		CheckBox displayCPUTempObjectCheck = (CheckBox) findViewById(R.id.displayCPUTemp).findViewById(R.id.customview_checkboxlistitemCheckBox);
		displayCPUTempObjectCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						Toast.makeText(CpuSetV2.this, "开启悬浮窗", 0).show();
						new CustomViewImpl().setCheckBoxListSummary("显示", (LinearLayout) findViewById(R.id.displayCPUTemp));
						Time_CPU_WindowManager.addBallView(CpuSetV2.this);
					}
					else
					{
						Toast.makeText(CpuSetV2.this, "关闭悬浮窗", 0).show();
						new CustomViewImpl().setCheckBoxListSummary("隐藏", (LinearLayout) findViewById(R.id.displayCPUTemp));
						Time_CPU_WindowManager.removeBallView(CpuSetV2.this);
					}
				}
			});
		try
		{
			// 进入当前 Activity 关闭悬浮窗显示 防止UI状态和当前状态冲突
			Time_CPU_WindowManager.removeBallView(CpuSetV2.this);
		}
		catch (Exception e)
		{
			Log.e("EasyDroid", "CpuSetV2.class: " + e.toString());
		}

		new CustomViewImpl().setListHeaderTitle("核心设置", (LinearLayout) findViewById(R.id.kernelSet));
		new CustomViewImpl().setCheckBoxListTitle("核心0", (LinearLayout) findViewById(R.id.kl0set));
		CheckBox kl0setC = (CheckBox) findViewById(R.id.kl0set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl0setC.setChecked(true);
		//Time_CPU.text.setText("核心 0："+cpu0StatusContent);
		kl0setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("0", true);
						//Time_CPU.text.setText("核心 0："+cpu0StatusContent);

						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl0set));
					}
					else
					{
						setKlState("0", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl0set));
					}
				}
			});
		new CustomViewImpl().setCheckBoxListTitle("核心1", (LinearLayout) findViewById(R.id.kl1set));
		CheckBox kl1setC = (CheckBox) findViewById(R.id.kl1set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl1setC.setChecked(true);
		//Time_CPU.text.setText("核心 1："+cpu0StatusContent);
		kl1setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("1", true);
						//Time_CPU.text.setText("核心 1："+cpu0StatusContent);
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl1set));
					}
					else
					{
						setKlState("1", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl1set));
					}
				}
			});
		new CustomViewImpl().setCheckBoxListTitle("核心2", (LinearLayout) findViewById(R.id.kl2set));
		CheckBox kl2setC = (CheckBox) findViewById(R.id.kl2set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl2setC.setChecked(true);
		//Time_CPU.text.setText("核心 2："+cpu0StatusContent);
		kl2setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("2", true);
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl2set));
						//Time_CPU.text.setText("核心 2："+cpu0StatusContent);
					}
					else
					{
						setKlState("2", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl2set));
					}
				}
			});
		new CustomViewImpl().setCheckBoxListTitle("核心3", (LinearLayout) findViewById(R.id.kl3set));
		CheckBox kl3setC = (CheckBox) findViewById(R.id.kl3set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl3setC.setChecked(true);
		//Time_CPU.text.setText("核心 3："+cpu0StatusContent);
		kl3setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("3", true);
						//Time_CPU.text.setText("核心 3："+cpu0StatusContent);
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl3set));
					}
					else
					{
						setKlState("3", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl3set));
					}
				}
			});
		new CustomViewImpl().setCheckBoxListTitle("核心4", (LinearLayout) findViewById(R.id.kl4set));
		CheckBox kl4setC = (CheckBox) findViewById(R.id.kl4set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl4setC.setChecked(true);
		//Time_CPU.text.setText("核心 4："+cpu0StatusContent);
		kl4setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("4", true);
						//Time_CPU.text.setText("核心 4："+cpu0StatusContent);
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl4set));
					}
					else
					{
						setKlState("4", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl4set));
					}
				}
			});
		new CustomViewImpl().setCheckBoxListTitle("核心5", (LinearLayout) findViewById(R.id.kl5set));
		CheckBox kl5setC = (CheckBox) findViewById(R.id.kl5set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl5setC.setChecked(true);
		//Time_CPU.text.setText("核心 5："+cpu0StatusContent);
		kl5setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("5", true);
						//Time_CPU.text.setText("核心 5："+cpu0StatusContent);
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl5set));
					}
					else
					{
						setKlState("5", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl5set));
					}
				}
			});
		new CustomViewImpl().setCheckBoxListTitle("核心6", (LinearLayout) findViewById(R.id.kl6set));
		CheckBox kl6setC = (CheckBox) findViewById(R.id.kl6set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl6setC.setChecked(true);
		//Time_CPU.text.setText("核心 6："+cpu0StatusContent);
		kl6setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("6", true);
						//Time_CPU.text.setText("核心 6："+cpu0StatusContent);
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl6set));
					}
					else
					{
						setKlState("6", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl6set));
					}
				}
			});
		new CustomViewImpl().setCheckBoxListTitle("核心7", (LinearLayout) findViewById(R.id.kl7set));
		CheckBox kl7setC = (CheckBox) findViewById(R.id.kl7set).findViewById(R.id.customview_checkboxlistitemCheckBox);
		kl7setC.setChecked(true);
		//Time_CPU.text.setText("核心 7："+cpu0StatusContent);
		kl7setC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if (p2)
					{
						setKlState("7", true);
						//Time_CPU.text.setText("核心 0："+cpu0StatusContent);
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("", (LinearLayout) findViewById(R.id.kl7set));
					}
					else
					{
						setKlState("7", false);
						//Time_CPU.text.setText("");
						new com.easydroid.romctrl.widget.CustomViewImpl().setCheckBoxListSummary("已禁用", (LinearLayout) findViewById(R.id.kl7set));
					}
				}
			});


		String curMaxCpuFreq = IOHelper.getCpu0MaxFreq();
		String curMinCpuFreq = IOHelper.getCpu0MinFreq();
		String curCpuGov = IOHelper.getCpu0CurGov();
		new CustomViewImpl().setListHeaderTitle("CPU设置", (LinearLayout) findViewById(R.id.cpuSetH));
		new CustomViewImpl().setCheckBoxListTitle("CPU工作模式", (LinearLayout) findViewById(R.id.cpuMode));
		new CustomViewImpl().setCheckBoxListDisplayCheckBox(false, (LinearLayout) findViewById(R.id.cpuMode));
		if (curCpuGov != "")
		{
			new CustomViewImpl().setCheckBoxListSummary(curCpuGov, (LinearLayout) findViewById(R.id.cpuMode));
		}
		else
		{
			new CustomViewImpl().setCheckBoxListSummary("获取失败", (LinearLayout) findViewById(R.id.cpuMode));
		}
		//Toast.makeText(this, curCpuGov, 0).show();
		LinearLayout cpuModeObject = (LinearLayout) findViewById(R.id.cpuMode);
		cpuModeObject.setOnClickListener(new View.OnClickListener(){
				public void onClick(View p1)
				{
					showSelectCpuCurGov();
				}
			});

		new CustomViewImpl().setCheckBoxListTitle("CPU最大频率", (LinearLayout) findViewById(R.id.cpuMax));
		new CustomViewImpl().setCheckBoxListDisplayCheckBox(false, (LinearLayout) findViewById(R.id.cpuMax));
		if (curMaxCpuFreq != "")
		{
			new CustomViewImpl().setCheckBoxListSummary(curMaxCpuFreq, (LinearLayout) findViewById(R.id.cpuMax));
		}
		else
		{
			new CustomViewImpl().setCheckBoxListSummary("Unknow", (LinearLayout) findViewById(R.id.cpuMax));
		}
		LinearLayout maxCpuObj = (LinearLayout) findViewById(R.id.cpuMax);
		maxCpuObj.setOnClickListener(new View.OnClickListener(){
				public void onClick(View p1)
				{
					showSelectCpuMaxFreqDialog();
				}
			});

		new CustomViewImpl().setCheckBoxListTitle("CPU最小频率", (LinearLayout) findViewById(R.id.cpuMin));
		new CustomViewImpl().setCheckBoxListDisplayCheckBox(false, (LinearLayout) findViewById(R.id.cpuMin));
		if (curMinCpuFreq != "")
		{
			new CustomViewImpl().setCheckBoxListSummary(curMinCpuFreq, (LinearLayout) findViewById(R.id.cpuMin));
		}
		else
		{
			new CustomViewImpl().setCheckBoxListSummary("Unknow", (LinearLayout) findViewById(R.id.cpuMin));
		}
		LinearLayout minCpuObj = (LinearLayout) findViewById(R.id.cpuMin);
		minCpuObj.setOnClickListener(new View.OnClickListener(){
				public void onClick(View p1)
				{
					showSelectCpuMinFreqDialog();
				}
			});

		new CustomViewImpl().setCheckBoxListTitle("过气萌新", (LinearLayout) findViewById(R.id.mengxinTest));
	}

	public void setKlState(String klNum, boolean state)
	{
		if (state)
		{
			try
			{
				Process p = Runtime.getRuntime().exec("su");
				DataOutputStream dos = new DataOutputStream(p.getOutputStream());
				dos.writeBytes("echo \"1\" > /sys/devices/system/cpu/cpu" + klNum + "/online");
				dos.flush();
			}
			catch (Exception e)
			{
				System.out.println(e);
				Toast.makeText(CpuSetV2.this, "错误：" + e.toString(), 0).show();
			}
		}
		else
		{
			try
			{
				Process p = Runtime.getRuntime().exec("su");
				DataOutputStream dos = new DataOutputStream(p.getOutputStream());
				dos.writeBytes("echo \"0\" > /sys/devices/system/cpu/cpu" + klNum + "/online");
				dos.flush();
			}
			catch (Exception e)
			{
				System.out.println(e);
				Toast.makeText(CpuSetV2.this, "错误：" + e.toString(), 0).show();
			}
		}
	}

	//CPU模式
	private void showSelectCpuCurGov()
	{
        AlertDialog.Builder cpuCurGovDialog = new AlertDialog.Builder(this);
        cpuCurGovDialog.setTitle("Select Governor");
        cpuCurGovDialog.setNegativeButton("cancel", null);

        final String[] cpuAvailableGov = IOHelper.getCpuAvailableGov();

        cpuCurGovDialog.setItems(cpuAvailableGov, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					IOHelper.setCpu0CurGov(cpuAvailableGov[which]);
				}
			});
        cpuCurGovDialog.create().show();
    }

	//CPU最小频率 
	private void showSelectCpuMinFreqDialog()
	{
        AlertDialog.Builder cpuFreqDialog = new AlertDialog.Builder(this);
        cpuFreqDialog.setTitle("Select Frequency");
        cpuFreqDialog.setNegativeButton("cancel", null);

        final String[] cpuAvailableFreq = IOHelper.getCpu0AvailableFreq();

        cpuFreqDialog.setItems(cpuAvailableFreq, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					IOHelper.setCpu0MinFreq(cpuAvailableFreq[which]);
					// showAllDefault();
				}
			});
        cpuFreqDialog.create().show();
    }

	//CPU最大频率
	private void showSelectCpuMaxFreqDialog()
	{
        AlertDialog.Builder cpuFreqDialog = new AlertDialog.Builder(this);
        cpuFreqDialog.setTitle("Select Frequency");
        cpuFreqDialog.setNegativeButton("cancel", null);

        final String[] cpuAvailableFreq = IOHelper.getCpu0AvailableFreq();

        cpuFreqDialog.setItems(cpuAvailableFreq, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					IOHelper.setCpu0MaxFreq(cpuAvailableFreq[which]);
					// showAllDefault();
				}
			});
        cpuFreqDialog.create().show();
    }

	public void Time(String s)
	{
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
