package com.example.learn.ui;

import java.util.Calendar;

import java.util.Date;

import com.example.learn.R;
import com.example.learn.model.UserDB;
import com.example.learn.tool.MyApplication;
import com.example.learn.tool.MyDialog;
import com.example.learn.tool.MyTime;

import android.R.raw;
import android.app.AlertDialog;
import android.app.Service;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ContextWrapper;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.zcw.togglebutton.*;
import com.zcw.togglebutton.ToggleButton.OnToggleChanged;
import android.content.SharedPreferences;

public class Fragment_setting extends Fragment{
	private View view;
	private Button weekNub;
	//private Button about;
	private UserDB userDB;
	private com.zcw.togglebutton.ToggleButton btnSwichPickBook;
	private RelativeLayout about;
	private Context context;
	private int orgRingerMode;
	private AudioManager audioManager;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		//view=inflater.inflate(R.layout.fragment_setting, null);
		view = inflater.inflate(R.layout.fragment_setting, container, false);
		context = new MyApplication().getContext();
		//声明一个获取系统音频服务的类的对象
		audioManager = (AudioManager)getActivity().getSystemService(Service.AUDIO_SERVICE);
		//从MainAcivity中获取原始设置的铃声模式
		Intent intent=getActivity().getIntent();
		orgRingerMode = intent.getIntExtra("mode_ringer", AudioManager.RINGER_MODE_NORMAL);
		
		init();
		return view;
	}
	private void init() {
		userDB=UserDB.getInstance(getActivity());	
		//weekNub=(Button)view.findViewById(R.id.button1);
		//about=(Button)view.findViewById(R.id.button2);
		about=(RelativeLayout)view.findViewById(R.id.fragment_setting_about);
		btnSwichPickBook=(ToggleButton)view.findViewById(R.id.fragment_setting_pickbook);
//		weekNub.setOnClickListener(new WeekListener());
		about.setOnClickListener(new AboutListener());
//		btnSwichPickBook.setOnClickListener(new SwitchListener());
		btnSwichPickBook.setOnToggleChanged(new SwitchListener());
	}
	
	
//	class WeekListener implements OnClickListener{
//		public void onClick(View v) {
//			final CharSequence[] a = { "第1周", "第2周", "第3周", "第4周", "第5周", "第6周",
//					"第7周", "第8周", "第9周", "第10周", "第11周", "第12周", "第13周", "第14周",
//					"第15周", "第16周", "第17周", "第18周", "第19周", "第20周", "第21周", "第22周",
//					"第23周", "第24周", "第25周" };
//			new AlertDialog.Builder(getActivity()).setTitle("设置周数")
//					.setItems(a, new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							userDB.saveNowWeek(a[which]+"",MyTime.getTimesWeekmorning()+"");
//							
//						}
//					})
//					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//						
//						public void onClick(DialogInterface dialog, int which) {
//
//						}
//					}).show();
//		}
//		
//	}
	
	class AboutListener implements OnClickListener{
		public void onClick(View v) {
			TextView textView=new TextView(getActivity().getBaseContext());
			textView.setTextSize(22);
			textView.setText("软件版本：beta1.0 \n\n"+"软件说明：本软件是个人开发,网络和界面优化方面有所不足，后期会慢慢改善，有什么建议或者意见请发邮件至rhymedys@gmail.com\n\n");
			final MyDialog myDialog = new MyDialog();
			myDialog.dialogShow(4, getActivity(), textView, new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					myDialog.cancel();
				}
			}, new OnClickListener() {

				@Override
				public void onClick(View v) {
					myDialog.cancel();

				}
			});
		}
		
	}
	class SwitchListener implements  OnToggleChanged
	{
		
		@Override
		public void onToggle(boolean on) {					
			// TODO 自动生成的方法存根
			boolean a =btnSwichPickBook.getToggle();
			Intent intent=new Intent();
			Log.i("switchPickBook", "true");
			intent.setAction("com.example.learn.service.QUIET_SERVICE");
			if(a)
			{

				if(getActivity().startService(intent) != null){
					Toast.makeText(context, "成功开启拾书提醒", 3000).show();
				}
				else{
					Toast.makeText(context, "未能成功开启，请重新尝试", 3000).show();
					//switch_quietButton.setChecked(false);
					btnSwichPickBook.toggle();					
				}
			}
			else
			{
				if(getActivity().stopService(intent)){
					Toast.makeText(context, "成功关闭，恢复到原来的响铃模式", 3000).show();
					}
				else{
					Toast.makeText(context, "未能成功关闭，请重新尝试", 3000).show();
				}
			}
			audioManager.setRingerMode(orgRingerMode);	
		}		
		
	}	

	
	class LayoutListener implements OnClickListener{

		@Override
		public void onClick(View v) {}
		
	}

}

