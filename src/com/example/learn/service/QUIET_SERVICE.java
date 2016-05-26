package com.example.learn.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.v4.text.TextDirectionHeuristicCompat;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.learn.model.DataDB;

public class QUIET_SERVICE extends Service {

//	 DataDB db=new DataDB(QUIET_SERVICE.this);
//	Cursor[] cursor = new Cursor[7];
//	private List datalist;
//	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}
//	
//	public int onStartCommand(Intent intent, int flags, int startId,String param) {
//		 //声明一个获取系统音频服务的类的对象
//		 final AudioManager audioManager = (AudioManager)getSystemService(Service.AUDIO_SERVICE);
//		 //获取手机之前设置好的铃声模式
//		 final int orgRingerMode = audioManager.getRingerMode(); 
//		
//		 //周
//		 datalist=db.loadMyClass(param);
//		 
//		 
//		 
//		//每隔一分钟从数据库中取以此数据，获得一次当前的时间，并与用用户输入的上下课时间比较，如果相等，则执行相应的静音或者恢复铃声操作
//		new Timer().schedule(new TimerTask() {		
//			@Override
//			public void run() {
//				
//				 //取出数据库中每日的数据，保存在cursor数组中
//				 for(int i=0;i<7;i++){
//					cursor[i]=db.select(i);					
//				}
//				
//				//从数据库取出用户输入的上课和下课时间，用来设置上课自动静音
//				for(int day=0;day<7;day++){ 
//					for(int row=0;row<12;row++){
//						cursor[day].moveToPosition(row);
//						for(int time=0;time<2;time++){
//							temp[day][row][time] = cursor[day].getString(time+5);
//						}
//			 			if(!temp[day][row][0].equals(""))
//							temp[day][row][0] = temp[day][row][0].substring(temp[day][row][0].indexOf(":")+2);
//					}
//				}
//								
//				//获取当前的是星期几
//				int currentDay = ShareMethod.getWeekDay();
//				for(int j=0;j<12;j++){
//					//获取手机当前的铃声模式
//					int currentRingerMode = audioManager.getRingerMode();  
//					if(temp[currentDay][j][0].equals(ShareMethod.getTime()) && currentRingerMode!=AudioManager.RINGER_MODE_VIBRATE){
//						audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
////						System.out.println("class is on");
//					}
//					if(temp[currentDay][j][1].equals(ShareMethod.getTime()) && currentRingerMode==AudioManager.RINGER_MODE_VIBRATE){
//						audioManager.setRingerMode(orgRingerMode);
////						System.out.println("class is over");
//					}   
//				}
//				
//			 }
//		}, 0, 60000);		
//		
//		return super.onStartCommand(intent, flags, startId);
//		
//	}
	

}
