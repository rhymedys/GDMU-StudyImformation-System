package com.example.learn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataOpenHelper extends SQLiteOpenHelper{

//成绩
//private static final String Class="create table Score(" 
//		+ "id integer primary key autoincrement," + "time text,"
//		+ "name text," + "score text,"+ "type text," 
//		+ "studyTime text," + "studyScore text)";
	private static final String Class="create table Score(" 
	+ "id integer primary key autoincrement," + "courseID text,"+ "name text,"
	+ "edutype text," + "studyscore text,"+ "edudepartment text," 
	+ "examtype text," + "score text)";
//	private String No;//课程号
//	//private String time;
//	private String name;//课程名
//	private String edutype;//课程类型
//	private String studyScore;//学分
//	private String edudepartment;//教学部门
//	private String examtype;//考试类型
//	//private String type;
//	private String score;//分数
//新闻
private static final String News="create table News(" 
		+ "id integer primary key autoincrement," + "title text,"
		+"url text)";
//全部课程
private static final String AllClass="create table AllClass(" 
		+ "id integer primary key autoincrement," + "name text," 
		+ "studyTime text," + "studyScore text," 
		+ "term text, " +  "testType text)";
//等级考试成绩
private static final String GradeTest="create table GradeTest(" 
		+ "id integer primary key autoincrement," + "name text," 
		+"end text,"+"num text," + "score text)";
//课表
private static final String MyClass="create table MyClass(" 
		+ "id integer primary key autoincrement," + "className text,"
		+"classWeek text," + "classTime text," + "schoolPlace text," 
		+ "classPlace integer,"+"matchWeek text,"+"teacherName text)";

public DataOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Class);
		db.execSQL(News);
		db.execSQL(AllClass);
		db.execSQL(MyClass);
		db.execSQL(GradeTest);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
