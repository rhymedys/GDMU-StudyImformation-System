package com.example.learn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.dimen;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.learn.db.DataOpenHelper;
import com.example.learn.net.Analysis;

public class DataDB {
	private static final String DBName = "Date";
	public static final int VERSION = 1;
	private static DataDB dataDB;
	private SQLiteDatabase db;

	 public DataDB(Context context) {
		DataOpenHelper dataOpenHelper = new DataOpenHelper(context, DBName,
				null, VERSION);
		db = dataOpenHelper.getWritableDatabase();
	}

	public synchronized static DataDB getInstance(Context context) {
		if (dataDB == null) {
			dataDB = new DataDB(context);
		}
		return dataDB;
	}
//删除数据表
	public void delTable(String tableName) {
		db.execSQL("DELETE FROM " + tableName);
	}
//保存成绩数据
	public void saveMyScore(List list) {
		if (list.size() > 0) {
			delTable("Score");
			for (Object object : list) {
				MyScore myClass = (MyScore) object;
				ContentValues contentValues = new ContentValues();
//				contentValues.put("time", myClass.getTime());
				Log.i("saveMyScore", "courseID");
				contentValues.put("courseID", myClass.getcourseID());
				Log.i("saveMyScore", "name");
				contentValues.put("name", myClass.getName());
				Log.i("saveMyScore", "edutype");
				contentValues.put("edutype", myClass.getEdutype());
				Log.i("saveMyScore", "studysoce");
				contentValues.put("studyscore", myClass.getStudyScore());
				Log.i("saveMyScore", "edudepartment");
				contentValues.put("edudepartment", myClass.getEdudepartment());
				Log.i("saveMyScore", "examtype");
				contentValues.put("examtype", myClass.getExamtype());
				Log.i("saveMyScore", "socre");
				contentValues.put("score", myClass.getScore());
				
//				contentValues.put("type", myClass.getType());
//				contentValues.put("studyTime", myClass.getStudyTime());
				
				Log.i("saveMyScore", contentValues.toString());
				db.insert("Score",null, contentValues);
			}
		}
	}

	public List loadMyScore() {
		List list = new ArrayList<MyScore>();
		Cursor cursor = db.query("Score", null, null, null, null, null, null,null);//add a new null
		if (cursor.moveToFirst()) {
			MyScore myScore;
			do {
				myScore = new MyScore();
				myScore.setcourseID(cursor.getString(cursor.getColumnIndex("courseID")));
				
//				myScore.setTime(cursor.getString(cursor.getColumnIndex("time")));
				myScore.setName(cursor.getString(cursor.getColumnIndex("name")));
				myScore.setEdutype(cursor.getString(cursor.getColumnIndex("edutype")));
				myScore.setStudyScore(cursor.getString(cursor.getColumnIndex("studyscore")));
				myScore.setEdudepartment(cursor.getString(cursor.getColumnIndex("edudepartment")));
				myScore.setExamtype(cursor.getString(cursor.getColumnIndex("examtype")));
				myScore.setScore(cursor.getString(cursor.getColumnIndex("score")));
//				myScore.setType(cursor.getString(cursor.getColumnIndex("type")));
//				myScore.setStudyTime(cursor.getString(cursor.getColumnIndex("studyTime")));
				
				Log.i("DataDB", myScore.getName());
				list.add(myScore);

			} while (cursor.moveToNext());
		}
		Log.i("DataDB", list.size() + "");
		return list;

	}

	public void saveNews(List title, List url) {
		if (title.size() > 0) {
			delTable("News");
			for (int i = 0; i < title.size(); i++) {
				String titleText = (String) title.get(i);
				String urlText = (String) url.get(i);
				ContentValues contentValues = new ContentValues();
				contentValues.put("title", titleText);
				contentValues.put("url", urlText);
				db.insert("News", null, contentValues);
			}
		}
	}

	public List loadNewsTitle() {
		List list = new ArrayList<String>();
		Cursor cursor = db.query("News", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("title")));
			} while (cursor.moveToNext());
		}
		return list;
	}

	public List loadNewsUrl() {
		List list = new ArrayList<String>();
		Cursor cursor = db.query("News", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex("url")));
			} while (cursor.moveToNext());
		}
		return list;
	}

	public void saveAllClass(List list) {
		if (list.size() > 0) {
			delTable("AllClass");
			for (Object object : list) {
				AllClass allClass = (AllClass) object;
				ContentValues contentValues = new ContentValues();
				contentValues.put("name", allClass.getName());
				contentValues.put("studyTime", allClass.getStudyTime());
				contentValues.put("studyScore", allClass.getStudyScore());
				contentValues.put("term", allClass.getTerm());
				contentValues.put("testType", allClass.getTestType());
				Log.i("saveAllClass", contentValues.toString());
				db.insert("AllClass", null, contentValues);
			}
		}
	}

	public List loadAllClass() {
		List list = new ArrayList<AllClass>();
		Cursor cursor = db
				.query("AllClass", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {

			do {
				AllClass allClass = new AllClass();
				allClass.setName(cursor.getString(cursor.getColumnIndex("name")));
				allClass.setStudyTime(cursor.getString(cursor
						.getColumnIndex("studyTime")));
				allClass.setStudyScore(cursor.getString(cursor
						.getColumnIndex("studyScore")));
				allClass.setTerm(cursor.getString(cursor.getColumnIndex("term")));
				allClass.setTestType(cursor.getString(cursor
						.getColumnIndex("testType")));
				list.add(allClass);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public void saveGradeTest(List list) {
		if (list.size() > 0) {
			delTable("GradeTest");
			for (Object object : list) {
				GradeTest gradeTest = (GradeTest) object;
				ContentValues contentValues = new ContentValues();
				contentValues.put("name", gradeTest.getName());
				contentValues.put("end", gradeTest.getEnd());
				contentValues.put("num", gradeTest.getNo());
				contentValues.put("score", gradeTest.getScore());				
				db.insert("GradeTest", null, contentValues);
			}
		}
	}

	public List loadGradeTest() {
		List list = new ArrayList<GradeTest>();
		Cursor cursor = db.query("GradeTest", null, null, null, null, null,
				null,null);//add a new null
		if (cursor.moveToFirst()) {
			do {
				GradeTest gradeTest = new GradeTest();
				gradeTest.setName(cursor.getString(cursor
						.getColumnIndex("name")));
				gradeTest
						.setEnd(cursor.getString(cursor.getColumnIndex("end")));
				gradeTest.setNo(cursor.getString(cursor
						.getColumnIndex("num")));
				
				gradeTest.setScore(cursor.getString(cursor
						.getColumnIndex("score")));
				
				list.add(gradeTest);

				
			} while (cursor.moveToNext());
		}
		return list;
	}

	public void saveMyClass(String id, String className, String teacher,
			String classPlace, String classWeek, int param) {
		if (id.equals("0")) {
			Analysis analysis = new Analysis();
			ContentValues contentValues = new ContentValues();
			contentValues.put("className", className);
			contentValues.put("teatherName", teacher);
			contentValues.put("classPlace", classPlace);
			contentValues.put("classWeek", classWeek);
			contentValues.put("matchWeek", "," + analysis.matchWeek(classWeek));
			contentValues.put("classNum", param + "");
			db.insert("MyClass", null, contentValues);
		} else {
			Analysis analysis = new Analysis();
			ContentValues contentValues = new ContentValues();
			contentValues.put("className", className);
			contentValues.put("teatherName", teacher);
			contentValues.put("classPlace", classPlace);
			contentValues.put("classWeek", classWeek);
			contentValues.put("matchWeek", "," + analysis.matchWeek(classWeek));
			contentValues.put("classNum", param + "");
			db.update("MyClass", contentValues, "id = ?", new String[] { id });
		}
	}

	public void saveMyClass(List list) {
		if (list.size() > 0) {
			delTable("MyClass");
			for (Object object : list) {
				MyClass myClass = (MyClass) object;
				ContentValues contentValues = new ContentValues();

				Log.i("saveMyClass", "ClassName");
				contentValues.put("className", myClass.getClassName());
				Log.i("saveMyClass", "classWeek");
				contentValues.put("classWeek", myClass.getClassWeek());
				Log.i("saveMyClass", "classTime");
				contentValues.put("classTime", myClass.getclassTime());
				Log.i("saveMyClass", "schoolPlace");
				contentValues.put("schoolPlace", myClass.getschoolPlace());
				Log.i("saveMyClass", "classPlace");
				contentValues.put("classPlace", myClass.getClassPlace());
				Log.i("saveMyClass", "matchWeek");
				contentValues.put("matchWeek", myClass.getMatchWeek());
				Log.i("saveMyClass", "teacherName");
				contentValues.put("teacherName", myClass.getTeacherName());

				Log.i("saveMyClass", contentValues.toString());
				db.insert("MyClass", null, contentValues);
			}
		}
	}

	public List loadMyClass(String param) {
		Log.i("DataDB", param);
		WeekClass weekClass = new WeekClass();
		Cursor cursor = db.query("MyClass", null, null, null, null, null, null);
		List list = new ArrayList<MyClass>();
		if (cursor.moveToFirst()) {
			do {
				String matchWeek = cursor.getString(cursor
						.getColumnIndex("matchWeek"));
				Pattern pattern = Pattern.compile("(.*?)" + "," + param + ","
						+ "(.*?)");
				// Log.i("DataDB", param+"..."+matchWeek);
				Matcher matcher = pattern.matcher(matchWeek);
				if (matcher.find()) {
					Log.i("DataDB", "match");
					String className = cursor.getString(cursor
							.getColumnIndex("className"));
					String teatherName = cursor.getString(cursor
							.getColumnIndex("teatherName"));
					String classPlace = cursor.getString(cursor
							.getColumnIndex("classPlace"));
					String classWeek = cursor.getString(cursor
							.getColumnIndex("classWeek"));
					String classNum = cursor.getString(cursor
							.getColumnIndex("classNum"));
					String id = cursor.getString(cursor.getColumnIndex("id"));
					weekClass.setClassName(className);
					weekClass.setClassNum(classNum);
					weekClass.setClassPlace(classPlace);
					weekClass.setClassWeek(classWeek);
					weekClass.setTeatherName(teatherName);
					weekClass.setId(id);
					list.add(weekClass);
					weekClass = new WeekClass();
				}
			} while (cursor.moveToNext());
		}
		return list;
	}

}
