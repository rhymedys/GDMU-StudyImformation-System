package com.example.learn.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import android.R.integer;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.learn.model.AllClass;
import com.example.learn.model.DataDB;
import com.example.learn.model.GradeTest;
import com.example.learn.model.MyClass;
import com.example.learn.model.MyScore;
import com.example.learn.tool.MyApplication;

public class Analysis {
	private Context context;
	private DataDB dataDB;

	public Analysis() {
		context = MyApplication.getContext();
		dataDB = DataDB.getInstance(context);
	}

	

	// html解析，只提取科目与成绩
	public void analysisScore(String source) {

		String test = testString("score.txt");

		StringBuffer sff = new StringBuffer();
//		String html = source;

		org.jsoup.nodes.Document doc = Jsoup.parse(source);

//		Elements table = doc.select("tr[class=smartTr]");
//		Elements text = table.select("td[height=23]");
		Elements text=doc.select("tr[height=21]");
		Elements detail=text.select("td");
		int i = 1;
		MyScore myScore = new MyScore();
		List list = new ArrayList<MyScore>();
		for (org.jsoup.nodes.Element link : detail) {
			Log.i("Analysis score", link.text());

			if (i == 1) {
				Log.i("Analysis score", "setcourseID");
//				myScore.setTime(link.text());				
				myScore.setcourseID(link.text());
			}
			if (i== 2) {
				Log.i("Analysis score", "setname");
				myScore.setName(link.text());
			}

			if (i == 3) {
//				Log.i("Analysis score", "setScore");
//				myScore.setScore(link.text());
				Log.i("Analysis score", "setedutype");
				myScore.setEdutype(link.text());
			}
			if (i == 4) {
//				Log.i("Analysis score", "setType");
//				myScore.setType(link.text());
				Log.i("Analysis score", "setStudyScore");
				myScore.setStudyScore(link.text());
			}
			if (i == 5) {
//				Log.i("Analysis score", "setStudyTime");
//				myScore.setStudyTime(link.text());
				Log.i("Analysis score", "setedudepartment");
				myScore.setEdudepartment(link.text());
			}
			
			if (i  == 6) {
//				Log.i("Analysis score", "setStudyTime");
//				myScore.setStudyTime(link.text());
				Log.i("Analysis score", "setexamtype");
				myScore.setExamtype(link.text());
			}
			
			if (i == 7) {
//				Log.i("Analysis score", "setStudyScore");
//				myScore.setStudyScore(link.text())
				Log.i("Analysis score", "setsocre");
				myScore.setScore(link.text());
				Log.i("Analysis score", "list add my socre");
				list.add(myScore);
				myScore = new MyScore();
			}
			if (i==8) {
				Log.i("Analysis score", "next");
				i=0;
			}
			i++;
			
		}
		dataDB.saveMyScore(list);

	}

	// 新闻网页解析新闻标题与地址
	public void analysisNews(String html) {
		List list1 = new ArrayList<String>();
		List list2 = new ArrayList<String>();
		if (null == html) {
			return;
		}
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		Elements class1 = doc.select("span[class=firsttitlestyle52311]>a");
		list1.add(class1.attr("href"));
		list2.add(class1.attr("title"));
		Elements class2 = doc.select("div[class=center]>ul>li>a[style= ]");
		for (org.jsoup.nodes.Element link : class2) {
			list1.add(link.attr("href"));
			list2.add(link.attr("title"));
		}
		dataDB.saveNews(list2, list1);

	}

	// 解析新闻内容
	public String analysisNewText(String source) {
		org.jsoup.nodes.Document doc = Jsoup.parse(source);
		Elements links_class = doc.select("div[id=zz]");
		links_class.toString();
//		links_class=links_class.select("p[style=text-align:left]");
		String text = links_class.text();
		Log.i("Analysis", text);
		return text;
	}

	// 解析大学全部课程
	public void analyseAllClass(String source) {

//		String test = testString("allClass.txt");

		List list = new ArrayList<AllClass>();

		org.jsoup.nodes.Document doc = Jsoup.parse(source);
		
		Elements table = doc.select("table[border=1]");
		Elements links_class = table.select("td[height=23]");
		int a = 1;
		AllClass allClass = new AllClass();
		for (org.jsoup.nodes.Element link : links_class) {
			String text = link.text();
			Log.i("Analysis", a+"___"+text);
			if (a % 12 == 4) {
				Log.i("allClass", "setName");
				allClass.setName(text);
				
			}
			if (a % 12 == 5) {
				Log.i("allClass", "studytime");
				allClass.setStudyTime(text);
			}
			if (a % 12 == 6) {
				Log.i("allClass", "studyscore");
				allClass.setStudyScore(text);
			}
			if (a % 12 == 8) {
				Log.i("allClass", "setterm");
				allClass.setTerm(text);
			}
			if (a % 12 == 11) {
				Log.i("allClass", "testtype");
				allClass.setTestType(text);
				
				Log.i("allClass", "list add allclass");
				list.add(allClass);
				allClass = new AllClass();
			}
			
			a++;
			Log.i("allClass", "next");
		}
		dataDB.saveAllClass(list);

	}

	// 大学等级证书考试解析
	public void analysisGradeTest(String source) {
		List list = new ArrayList<GradeTest>();
//		ArrayList<String> list1 = new ArrayList<String>();
//		List list1 = new ArrayList<GradeTest>();
		if (null == source) {
			return;
		}
		String html = source;
		int i=1;
		GradeTest gradeTest = new GradeTest();
		org.jsoup.nodes.Document doc = Jsoup.parse(source);
		Log.i("analysisGradeTest", "analysisGradeTest");
		Elements body = doc.select("body[bgcolor=ffffff]");
		Elements table=doc.select("table[class=tab_1]");
		Elements tr=table.select("tr[class=tab_5]");
		Elements links_class=tr.select("td[height=25]");
		Log.i("links_class", links_class.text());
//		Elements text=links_class.select("td[height=25]");
		for (org.jsoup.nodes.Element link : links_class) {
			Log.i("linkclass", link.text());
			String textresult = link.text();
//			list1.add(textresult);
			if(i==3)
			{
				Log.i("GradeTest", "setname");
				gradeTest.setName(textresult);
			}
			if (i==5)
			{
				Log.i("GradeTest", "setEnd");
				gradeTest.setEnd(textresult);
			}
			if (i==6) {
				Log.i("GradeTest", textresult);
				gradeTest.setNo(textresult);
			}
			if(i==7)
			{
				Log.i("GradeTest", "setScore");
				gradeTest.setScore(textresult);
				list.add(gradeTest);
				gradeTest=new GradeTest();
			}
			if (i==9) {
				i=0;
				
			}
			i++;
			Log.i("gradetext", "next");
		}
//		for (int i = 0; i <= list1.size(); i++) {
//			if ((i + 1) % 14 == 6) {
//				GradeTest gradeTest = new GradeTest();
//				gradeTest.setName(list1.get(i));
//				gradeTest.setEnd(list1.get(i + 1));
//				gradeTest.setScore(list1.get(i + 4));
//				list.add(gradeTest);
//			}
			
//		}
		dataDB.saveGradeTest(list);

	}

	ArrayList<MyClass> classNum = new ArrayList<MyClass>();

	public void analysisMyClass(String source) {
		String html = source;
		List list = new ArrayList<MyClass>();

		org.jsoup.nodes.Document doc = Jsoup.parse(source);
		Elements table = doc.select("tr[class=tab_5]");
		Elements links_class = table.select("td");
		int a = 1;
		MyClass myClass = new MyClass();
		String abcdef = "";
		for (org.jsoup.nodes.Element link : links_class) {
			String text = link.text();

			Log.i("Analysis myClass", link.text());
			if (a == 2) {

				abcdef = link.text();

			}
			if (a == 6) {
				Elements abc = link.select("td[align=left]");
				String haha = abc.text();
				if (haha == " ") {
					return;
				} else {
					String hehe[] = haha.split("; ");
					for (int d = 0; d < hehe.length; d++) {
						Log.i("123", "123");
						String e = hehe[d];
						String p = e.replace("『", " ");
						p = p.replace("』", " ");
						p = p.replace("◎", " ");
						Log.i("9999", e);
						String m[] = p.split(" ");

						for (int i = 0; i < m.length; i++) {
							if (i == 0) {
								Log.i("Analysis myClass className", "setclassName");
								myClass.setClassName(abcdef);

								myClass.setClassWeek(m[0]);
								Log.i("Analysis myClass classWeek", "setclassWeek");
							}
							if (i == 1) {
								Log.i("Analysis myClass classTime", "setclassTime");
								myClass.setclassTime(m[1]);
							}
							if (i == 2) {
								Log.i("Analysis myClass schoolPlace", "setschoolPlace");
								myClass.setschoolPlace(m[2]);
							}
							if (i == 3) {
								Log.i("Analysis myClass classPlace", "setclassPlace");
								myClass.setClassPlace(m[3]);
							}
							if (i == 4) {
								Log.i("Analysis myClass matchWeek", "setmatchWeek");
								myClass.setMatchWeek(m[4]);
							}
							if (i == 5) {
								Log.i("Analysis myClass teacherName", "setteacherName");
								myClass.setTeacherName(m[5]);
								Log.i("MyClass", "list add Myclass");
								list.add(myClass);
								myClass = new MyClass();
							}
						}

					}
				}

			}

			if (a == 10) {
				Log.i("myClass", "next");
				a = 0;
			}
			a++;

		}
		dataDB.saveMyClass(list);
	}

	private ArrayList<String> analysisTime(ArrayList<String> Ti) {
		ArrayList<String> myList = new ArrayList<String>();
		for (int nub = 0; nub < Ti.size(); nub++) {
			String myTime = Ti.get(nub);
			if ("".equals(myTime) == false) {
				Log.i("Analysis", myTime);
//				// 匹配单周
//				int danzhou = 0;
//				int shuangzhou = 0;
//				Pattern dan = Pattern.compile("(.*?)单周");
//				Matcher Mdan = dan.matcher(myTime);
//				while (Mdan.find()) {
//					myTime = myTime.replaceAll("单", "");
//					danzhou = 1;
//				}
//				// 匹配双周
//				Pattern shuang = Pattern.compile("(.*?)双周");
//				Matcher Mshuang = shuang.matcher(myTime);
//				while (Mshuang.find()) {
//					myTime = myTime.replaceAll("双", "");
//					shuangzhou = 1;
//				}
//				// System.out.println("MyTime---"+MyTime);

				// 解析周数，此时没有 单 双 字,开始解析周数
				Pattern pattern1 = Pattern.compile("(.*?)周");
				Matcher matcher1 = pattern1.matcher(myTime);
				String add = "";
				while (matcher1.find()) {
					String Time = matcher1.group(1);
					// 形如1，2，3，4周 1-2，4-5，7周
					Pattern pattern5 = Pattern.compile("(.*?)\\,");
					Matcher matcher5 = pattern5.matcher(Time);

					if (matcher5.find()) {

						Log.i("Analysis", "," + Time);

						// 形如1，2，3，4周
						Pattern pattern2 = Pattern.compile("\\,");
						Matcher matcher2 = pattern5.matcher(Time + ",");
						while (matcher2.find()) {
							String a = match2(matcher2.group(1));
							if ("".equals(a) == false) {
								add = add + a + ",";
							}

						}

						myList.add(add);
						// 没有，形如 1 1-2
					} else {
						String ar = match2(Time);
						Log.i("Analysis", ar);
						myList.add(ar);
					}

				}

			}
		}
		return myList;
	}
	
	public String matchWeek(String param){
		String re="";
		String myTime = param;
		if ("".equals(myTime) == false) {
			Log.i("Analysis", myTime);
//			// 匹配单周
//			int danzhou = 0;
//			int shuangzhou = 0;
//			Pattern dan = Pattern.compile("(.*?)单周");
//			Matcher Mdan = dan.matcher(myTime);
//			while (Mdan.find()) {
//				myTime = myTime.replaceAll("单", "");
//				danzhou = 1;
//			}
//			// 匹配双周
//			Pattern shuang = Pattern.compile("(.*?)双周");
//			Matcher Mshuang = shuang.matcher(myTime);
//			while (Mshuang.find()) {
//				myTime = myTime.replaceAll("双", "");
//				shuangzhou = 1;
//			}
			// System.out.println("MyTime---"+MyTime);

			// 解析周数，此时没有 单 双 字,开始解析周数
			Pattern pattern1 = Pattern.compile("(.*?)周");
			Matcher matcher1 = pattern1.matcher(myTime);
			String add = "";
			while (matcher1.find()) {
				String Time = matcher1.group(1);
				// 形如1，2，3，4周 1-2，4-5，7周
				Pattern pattern5 = Pattern.compile("(.*?)\\,");
				Matcher matcher5 = pattern5.matcher(Time);
				if (matcher5.find()) {
					Log.i("Analysis", "," + Time);

					// 形如1，2，3，4周
					Pattern pattern2 = Pattern.compile("\\,");
					Matcher matcher2 = pattern5.matcher(Time + ",");
					while (matcher2.find()) {
						String a = match2(matcher2.group(1));
						if ("".equals(a) == false) {
							add = add + a + ",";
						}
					}
					re=add;
					// 没有，形如 1 1-2
				} else {
					String ar = match2(Time);
					Log.i("Analysis", ar);
					re=ar;
				}

			}

		}
		return re;
	}

	/**
	 * 匹配 -
	 * 
	 * @param Time
	 * @param danzhou
	 * @param shuangzhou
	 * @return
	 */
//	private String match1(String Time, int danzhou, int shuangzhou) {
//		String add = "";
//		String firstNub = "", endNub = "";
//
//		Pattern pattern3 = Pattern.compile("(.*?)\\-");
//		Matcher matcher3 = pattern3.matcher(Time);
//		while (matcher3.find()) {
//			Log.i("Analysis", "-" + Time);
//			firstNub = matcher3.group(1);
//		}
//		Pattern pattern4 = Pattern.compile("\\-(.*)");
//		Matcher matcher4 = pattern4.matcher(Time);
//		while (matcher4.find()) {
//			// System.out.println("3---" + matcher4.group(1));
//			endNub = matcher4.group(1);
//		}
//		if (!"".equals(firstNub) && !"".equals(endNub)) {
//			int first = Integer.valueOf(firstNub);
//			int end = Integer.valueOf(endNub);
//
//			if (danzhou == 1) {
//
//				for (int i = first; i <= end; i++) {
//					if (i % 2 == 1) {
//						add = add + i + ",";
//					}
//				}
//			} else if (shuangzhou == 1) {
//				for (int i = first; i <= end; i++) {
//					if (i % 2 == 0) {
//						add = add + i + ",";
//					}
//				}
//			} else {
//				for (int i = first; i <= end; i++) {
//					add = add + i + ",";
//				}
//			}
//		}
//		return add;
//	}
	private String match1(String Time) {
		String add = "";
		String firstNub = "", endNub = "";

		Pattern pattern3 = Pattern.compile("(.*?)\\-");
		Matcher matcher3 = pattern3.matcher(Time);
		while (matcher3.find()) {
			Log.i("Analysis", "-" + Time);
			firstNub = matcher3.group(1);
		}
		Pattern pattern4 = Pattern.compile("\\-(.*)");
		Matcher matcher4 = pattern4.matcher(Time);
		while (matcher4.find()) {
			// System.out.println("3---" + matcher4.group(1));
			endNub = matcher4.group(1);
		}
		if (!"".equals(firstNub) && !"".equals(endNub)) {
			int first = Integer.valueOf(firstNub);
			int end = Integer.valueOf(endNub);

			
				for (int i = first; i <= end; i++) {
					add = add + i + ",";
				}
			
		}
		return add;
	}

	/**
	 * 匹配 - 或者没有 -
	 * 
	 * @param list
	 * @return
	 */
//	private String match2(String text, int danzhou, int shuangzhou) {
//		String re = "";
//		Pattern pattern1 = Pattern.compile("(.*)\\-");
//		Matcher matcher1 = pattern1.matcher(text);
//		if (matcher1.find()) {
//			String r = match1(text, danzhou, shuangzhou);
//			if (!"".equals(r)) {
//				re = re + match1(text, danzhou, shuangzhou) + ",";
//			}
//
//		} else {
//			re = re + text + ",";
//		}
//
//		return re;
//	}
	private String match2(String text) {
		String re = "";
		Pattern pattern1 = Pattern.compile("(.*)\\-");
		Matcher matcher1 = pattern1.matcher(text);
		if (matcher1.find()) {
			String r = match1(text);
			if (!"".equals(r)) {
				re = re + match1(text) + ",";
			}

		} else {
			re = re + text + ",";
		}

		return re;
	}
	
	//用作本地测试，读取本地缓存文档，避免频繁访问服务器
		private String testString(String param) {
			String returnText = "";
			try {
				AssetManager assetManager = context.getAssets();
				// param===="441.txt"
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
