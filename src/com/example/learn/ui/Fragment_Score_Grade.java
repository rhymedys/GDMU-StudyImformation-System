package com.example.learn.ui;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.learn.R;
import com.example.learn.model.DataDB;
import com.example.learn.model.GradeTest;
import com.example.learn.model.MyScore;
import com.example.learn.tool.MyDialog;
import com.example.learn.ui.Fragment_Score_Result.ListListener;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment_Score_Grade extends Fragment{
	private View view;
	private DataDB dataDB;
	private ListView listView;
	private List nowDate;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_score_grade, null, false);
		init();
		return view;
	}
	private void init() {
		nowDate=new ArrayList<GradeTest>();
		listView=(ListView)view.findViewById(R.id.fragment_score_grade_listview);
		dataDB = DataDB.getInstance(getActivity());
		ListAdapter adapter = new SimpleAdapter(getActivity(), setDataToList(),
				R.layout.list_item_gradetest, new String[] { "name", "score" },
				new int[] { R.id.list_textView1, R.id.lsit_textView2 });
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new ListListener());
	}
	private List setDataToList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();		
		List dateList = dataDB.loadGradeTest();
		if(dateList.size()!=0){
		Map<String, Object> map;
		for (int i = 0; i < dateList.size(); i++) {
			map = new HashMap<String, Object>();
			GradeTest myScore = (GradeTest) dateList.get(i);
			map.put("name", myScore.getName());
			map.put("score", myScore.getScore()+"分");
			list.add(map);
			nowDate.add(myScore);
		}
		}
//		Log.i("Fragment_Score_Result", list.toString());
		return list;
	}
	
	//unused
	 class ListListener implements 	OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO 自动生成的方法存根
			GradeTest gradeTest = (GradeTest)nowDate.get(position);
			ListView lv = new ListView(getActivity());
			ArrayList list = new ArrayList();
			HashMap map1 = new HashMap();
			HashMap map2 = new HashMap();
			HashMap map3 = new HashMap();
			HashMap map4 = new HashMap();
			map1.put("name", "考试名称");
			map1.put("value", gradeTest.getName());
			map2.put("name", "成绩");
			map2.put("value", gradeTest.getScore());
			map3.put("name", "考试时间");
			map3.put("value", gradeTest.getEnd());
			map4.put("name", "准考证号");
			map4.put("value", gradeTest.getNo());
			list.add(map4);
			list.add(map1);
			list.add(map2);
			list.add(map3);
			ListAdapter adapter = new SimpleAdapter(getActivity(), list,
					R.layout.list_item_score_detial, new String[] { "name",
							"value" }, new int[] { R.id.list_textView1,
									R.id.lsit_textView2});
			lv.setAdapter(adapter);
			final MyDialog myDialog = new MyDialog();
			myDialog.dialogShow(4,getActivity(), lv, 
					new View.OnClickListener() {
		
						@Override
						public void onClick(View v) {
							myDialog.cancel();
						}
			}, 
					new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						myDialog.cancel();
	
					}
			});
		}
		
	}
}
