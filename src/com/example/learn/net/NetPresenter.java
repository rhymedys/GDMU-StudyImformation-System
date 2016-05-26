package com.example.learn.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

public class NetPresenter {
	private HttpUtil httpUtil;
	private Analysis analysis;
	private static NetPresenter netPresenter;
	// cookie
	public static String JSESSIONID;
	// 验证码
	private Bitmap bitmap;
	// 获取cookie  主页
//	String url0 = "http://jwgl.hbeu.cn:8080/hbgcxy/";
	String url0 = "http://125.90.8.125:8000/portal/home.do?method=index";
	// 获取验证码  我们学校没有验证码
	String url1 = "http://jwgl.hbeu.cn:8080/hbgcxy/verifycode.servlet";
//	String url1 = "http://125.90.8.125:8000/jw/jw/stdRevampApply.do?method=loadRevampCourse";
	// 登陆地址
//	String url2 = "http://jwgl.hbeu.cn:8080/hbgcxy/Logon.do?method=logon";
	String url2 = "http://125.90.8.125:8000/portal/login.do";
	// 登入页面
//	String url3 = "http://jwgl.hbeu.cn:8080/hbgcxy/Logon.do?method=logonBySSO";
	String url3 = "http://125.90.8.125:8000/jw/application/main.jsp";
	// 成绩查询
//	String url4 = "http://jwgl.hbeu.cn:8080/hbgcxy/xszqcjglAction.do?method=queryxscj";
	String url4 = "http://125.90.8.125:8000/jw/jw/stdRevampApply.do?method=loadRevampCourse";
	// 校园新闻网站的地址
//	String url5 = "http://news.hbeu.cn";
	String url5 = "http://news.gdmc.edu.cn/";
	// 此网站前必须进 URL3 课表的网站，后面要定义所查的学期  暂时不管这功能
//	String url6 = "http://jwgl.hbeu.cn:8080/hbgcxy/tkglAction.do?method=goListKbByXs&istsxx=no&xnxqh=";
	String url6 = "http://125.90.8.125:8000/jw/stdselectedcourse.do?method=getStdSchedule";
	// 大学全部课程url
//	String url7 = "http://jwgl.hbeu.cn:8080/hbgcxy/pyfajhgl.do?method=toViewJxjhXs&tktime=1414123888000";
	String url7 = "http://125.90.8.125:8000/jw/stdselectedcourse.do?method=getStdSchedule";
	// 大学等级证书url
//	String url8 = "http://jwgl.hbeu.cn:8080/hbgcxy/kjlbgl.do?method=findXskjcjXszq&tktime=1414124059000";
	String url8 = "http://125.90.8.125:8000/jw/examScore.do?method=getScoreStd";

	private NetPresenter() {
		httpUtil = new HttpUtil();
		analysis=new Analysis();
	}

	public static NetPresenter getInstence() {
		if (netPresenter == null) {
			netPresenter = new NetPresenter();
		}
		return netPresenter;

	}
	private int cookieNub=0;
//	获取cookies
//	public int getCookie(){
//		HttpGet get = new HttpGet(url0);
//		List<Cookie> cookie = null;
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpResponse httpresponse = null;
//		try {
//			httpresponse = httpclient.execute(get);
//		if (httpresponse.getStatusLine().getStatusCode() == 200) {
//			// <strong>获取</strong>返回的cookie
//			cookie = ((AbstractHttpClient) httpclient).getCookieStore()
//					.getCookies();
//			JSESSIONID = cookie.get(0).getValue();
//			cookieNub=1;
//		} 
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		return cookieNub;
//	}

	// 获取验证码
//	public Bitmap getCheckCodePhoto() {
//		getCookie();
//		Log.i("NetPresenter", "getCheckCodePhoto");
//		HttpGet get = new HttpGet(url1);
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpResponse httpresponse = null;
//		try {
//			get.setHeader("cookie", "JSESSIONID=" + JSESSIONID);
//			httpresponse = httpclient.execute(get);
//		if (httpresponse.getStatusLine().getStatusCode() == 200) {
//			
////			List<Cookie> cookie = ((AbstractHttpClient) httpclient).getCookieStore()
////					.getCookies();
////			JSESSIONID = cookie.get(0).getValue();
//			InputStream inputread = httpresponse.getEntity().getContent();
//			bitmap = BitmapFactory.decodeStream(inputread);
//		} 
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Log.i("NetPresenter", JSESSIONID);
//		return bitmap;
//	}

	private int logInNub = 0;

	// 带cookie和用户名密码和验证码进行验证
	public int logIn(String user, String password
//			。。,String checkCode
			) {
		logInNub = 0;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("j_password", password));
//		params.add(new BasicNameValuePair("RANDOMCODE",checkCode));
		params.add(new BasicNameValuePair("j_username", user));
		String aString;
		String C=new HttpUtil().httpPost(url2, params, new HttpCallbackListener() {
					public void onFinish(String response) {	}
//
					public void onError(Exception e)
					{
						Log.e("NetPresenter", e.toString());
						logInNub = 0;
					}
				});
		String a="error";
		if ( C.equals(a)) {
			logInNub=0;
			
		}
		else {
			JSESSIONID=C;
			logInNub=1;
		}
		Log.i("NetPresenter","logIn"+logInNub);
		return logInNub;
	}

	/**
	 * 进入成绩查询预备页面，不进此页面直接查成绩会报错
	 */
	
	public void getPrepare() {
		if (JSESSIONID !=null) {
			HttpUtil.httpGet(url3, JSESSIONID, new HttpCallbackListener() {
				public void onFinish(String response) {
					
				}
				public void onError(Exception e) {
				}
			});
		} 
		else
		{
			Toast.makeText(null, "请先登录",Toast.LENGTH_SHORT );
		}

	}

	private int scoreNub = 0;

	// 进入成绩查询界面，返回页面html文件
	public int getScore() {
		scoreNub = 0;
		Log.i("NetPresenter", "getScore");
		if (JSESSIONID!=null) {
			new HttpUtil().httpPost(url4, JSESSIONID,new HttpCallbackListener() {
						public void onFinish(String response) {
							scoreNub = 1;
							analysis.analysisScore(response);
						}

						public void onError(Exception e) {
							scoreNub = 0;
							Log.e("NetPresenter", e.toString());
						}
					});
			
		}
		
		else
		{
			Toast.makeText(null, "请先登录",Toast.LENGTH_SHORT );
			
		}
		
		return scoreNub;
	}

	private int newsNub = 0;

	// 登入校园新闻网，并爬去数据
	public int getNews() {
		newsNub = 0;
		HttpUtil.httpGet(url5, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				newsNub = 1;
				analysis.analysisNews(response);
			}

			public void onError(Exception e) {
				// TODO Auto-generated method stub
				newsNub = 0;

			}
		});
		return newsNub;
	}
	//获取新闻内容
	private String newsText="";
	public String getNewsText(String param) {
		HttpUtil.httpGet(url5+param, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				newsNub = 1;
				newsText=response;
			}

			public void onError(Exception e) {
				// TODO Auto-generated method stub
				newsNub = 0;

			}
		});
		return newsText;
	}

	private int classNub = 0;

	// 获得课程表的html
	public int getClass(String a) {
		classNub = 0;
		if (JSESSIONID!=null) {
			HttpUtil.httpGet(url6, JSESSIONID,
					new HttpCallbackListener() {
						public void onFinish(String response) {
							classNub = 1;
							analysis.analysisMyClass(response);
						}

						@Override
						public void onError(Exception e) {
							classNub = 0;
						}
					});
			
		}
		else
		{
			Toast.makeText(null, "请先登录",Toast.LENGTH_SHORT );
			
		}
		return classNub;
	}

	private int allClassNub = 0;

	// 获得全部成绩的的html
	public int getAllClass() {
		allClassNub = 0;
		if (JSESSIONID!=null) {
			HttpUtil.httpGet(url7, JSESSIONID, new HttpCallbackListener() {

				@Override
				public void onFinish(String response) {
					allClassNub = 1;
					analysis.analyseAllClass(response);

				}

				@Override
				public void onError(Exception e) {
					allClassNub = 0;
					// TODO Auto-generated method stub

				}
			});
		}
		return allClassNub;
	}

	private int testNub = 0;

	// 获得等级考试的的的html
	public int getTest() {
		testNub = 0;
		if (JSESSIONID!=null) {
			new HttpUtil().httpPost(url8, JSESSIONID,new HttpCallbackListener() {
				public void onFinish(String response) {
					testNub = 1;
					Log.i("gettest", "success");
					analysis.analysisGradeTest(response);
				}

				@Override
				public void onError(Exception e) {
					testNub = 0;
				}
			});
		}
		else {
			Toast.makeText(null, "请你先登录", Toast.LENGTH_SHORT);
		}

		return testNub;
	}
	
	private Context context;
	private String testString(String param) {
		String returnText = "";
		try {
			AssetManager assetManager = context.getAssets();
			InputStream in = assetManager.open(param);
			InputStreamReader inRead = new InputStreamReader(in, "UTF-8");
			BufferedReader buffRead = new BufferedReader(inRead);
			StringBuffer sBuff = new StringBuffer();
			String data = "";
			while ((data = buffRead.readLine()) != null) {
				sBuff.append(data);
			}
			returnText = sBuff.toString();
		} catch (Exception e) {
			Log.i("AnaLysis", e.toString());
		}
		Log.i("AnaLysis", returnText);
		return returnText;
	}

}
