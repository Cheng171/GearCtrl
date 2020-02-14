package com.easydroid.romctrl;
import android.app.*;
import android.widget.*;
import android.os.*;
import android.view.View.*;
import android.view.*;
import java.io.*;
import android.content.*;
import android.net.*;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Process;


public class battery extends Activity implements OnClickListener
{
	private Button swap,unswap,makeswap256,makeswap128,rec;
	//private TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	{
		new com.easydroid.romctrl.conf.headerDefaultConf().conf(this, getWindow());
		new com.easydroid.romctrl.util.actionbar().isBackIcon(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battery);
		init();
	}}

	private void init(){
		//初始化按钮控件
		swap=(Button) findViewById(R.id.xingnengswap);
		unswap=(Button) findViewById(R.id.xingnengunswap);
		makeswap256=(Button) findViewById(R.id.xingnengmakeswap256);
		makeswap128=(Button) findViewById(R.id.xingnengmakeswap128);
		rec=(Button) findViewById(R.id.rec);

		// tv=(TextView) findViewById(R.id.xingnengTextView);
		//设置按钮监听
		swap.setOnClickListener(this);
	 unswap.setOnClickListener(this);
	 makeswap256.setOnClickListener(this);
	 makeswap128.setOnClickListener(this);
	 rec.setOnClickListener(this);


	}

	@Override
	public void onClick(View view){
		switch(view.getId()){
			case R.id.xingnengswap:	
				//设置电量70%
			execShell("echo 70 > /sys/class/power_supply/battery/capacity");
		break;
			case R.id.xingnengunswap:
				//设置电量100%
				execShell("echo 100 > /sys/class/power_supply/battery/capacity");
			break;
			case R.id.xingnengmakeswap128:
				//设置电量5%
				execShell("echo 5 > /sys/class/power_supply/battery/capacity");
			break;
			case R.id.xingnengmakeswap256:
				//设置电量30%
				execShell("echo 30 > /sys/class/power_supply/battery/capacity");
			break;
			case R.id.rec:	
				//设置电量恢复
			execShell("echo -1 > /sys/class/power_supply/battery/capacity");
		Toast.makeText(battery.this,"电池显示已经恢复！",Toast.LENGTH_SHORT).show();

		break;


		}
	}
	
	
	/****************
	 *
	 * 发起添加群流程。群号：Easydroid Studio(230667346) 的 key 为： ti2_srP_OlF7Tzh4KbgzB0rNH-kGKCtC
	 * 调用 joinQQGroup(ti2_srP_OlF7Tzh4KbgzB0rNH-kGKCtC) 即可发起手Q客户端申请加群 Easydroid Studio(230667346)
	 *
	 * @param key 由官网生成的key
	 * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
	 ******************/
	public boolean joinQQGroup(String key) {
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D"+key));
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try {
			startActivity(intent);
			return true;
		} catch (Exception e) {
			// 未安装手Q或安装的版本不支持
			return false;
		}
	}
//	public void execCommand(String command) throws IOException {   
//	// start the ls command running    //String[] args =  new String[]{"sh", "-c", command};    
//	Runtime runtime = Runtime.getRuntime();   
//		Process proc = runtime.exec(command);       
//	//这句话就是shell与高级语言间的调用   
//	//如果有参数的话可以用另外一个被重载的exec方法   
//	//实际上这样执行时启动了一个子进程,它没有父进程的控制台    
//    //也就看不到输出,所以需要用输出流来得到shell执行后的输出   
//	InputStream inputstream = proc.getInputStream();
//	InputStreamReader inputstreamreader = new InputStreamReader(inputstream);       
//	BufferedReader bufferedreader = new BufferedReader(inputstreamreader);     
//	// read the ls output     
//	String line = "";       
//	StringBuilder sb = new StringBuilder(line);   
//	while ((line = bufferedreader.readLine()) != null) {    
//	//System.out.println(line);      
//	sb.append(line);           
//	sb.append('\n');        }    
//    //tv.setText(sb.toString());   
//	//使用exec执行不会等执行成功以后才返回,它会立即返回    
//    //所以在某些情况下是很要命的(比如复制文件的时候)      
//	//使用wairFor()可以等待命令执行完成以后才返回    
//    try {         
//	if (proc.waitFor() != 0) {     
//	System.err.println("exit value = " + proc.exitValue());           
//	}       
//	}     
//	catch (InterruptedException e) { 
//	System.err.println(e);   
//	}    
//	}
	
	
	public void execShell(String cmd){
    	try{  
            //权限设置
            Process p = Runtime.getRuntime().exec("su");  //开始执行shell脚本
            //获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
            //将命令写入
            dataOutputStream.writeBytes(cmd);
            //提交命令
            dataOutputStream.flush();
            //关闭流操作
            dataOutputStream.close();
            outputStream.close();
				InputStream inputstream = p.getInputStream();
	InputStreamReader inputstreamreader = new InputStreamReader(inputstream);       
	BufferedReader bufferedreader = new BufferedReader(inputstreamreader);     
	// read the ls output     
	String line = "";       
	StringBuilder sb = new StringBuilder(line);   
	while ((line = bufferedreader.readLine()) != null) {    
	//System.out.println(line);      
	sb.append(line);           
	sb.append('\n');        }    
   // tv.setText(sb.toString());   
	//使用exec执行不会等执行成功以后才返回,它会立即返回    
    //所以在某些情况下是很要命的(比如复制文件的时候)      
	//使用wairFor()可以等待命令执行完成以后才返回    
		}  
		catch(Throwable t)  
        {  
			t.printStackTrace();  
		} 
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
