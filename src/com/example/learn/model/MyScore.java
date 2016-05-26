package com.example.learn.model;

public class MyScore {
	public  String getcourseID() {
		return courseID;
	}
	public void setcourseID(String courseID) {
		this.courseID=courseID;
	}
	
//	public String getTime() {
//		return time;
//	}
//	public void setTime(String time) {
//		this.time = time;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getStudyTime() {
//		return studyTime;
//	}
//	public void setStudyTime(String studyTime) {
//		this.studyTime = studyTime;
//	}
	public String getStudyScore() {
		return studyScore;
	}
	public void setStudyScore(String studyScore) {
		this.studyScore = studyScore;
	}
	
	public String getEdutype() {
		return edutype;
	}
	public void setEdutype(String edutype) {
		this.edutype = edutype;
	}
	
	public String getEdudepartment() {
		return edudepartment;
	}
	public void setEdudepartment(String edudepartment) {
		this.edudepartment = edudepartment;
	}
	
	public String getExamtype() {
		return examtype;
	}
	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}

	
private String courseID;//课程号
//private String time;
private String name;//课程名
private String edutype;//课程类型
private String studyScore;//学分
private String edudepartment;//教学部门
private String examtype;//考试类型
//private String type;
private String score;//分数
//private String studyTime;


}
