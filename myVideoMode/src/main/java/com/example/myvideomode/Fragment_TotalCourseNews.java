package com.example.myvideomode;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

class Fragment_TotalCourseNews extends Fragment implements OnClickListener{
	ViewPager viewPager;
	Fragment fragmentComment,fragmentRelevantCourse,fragmentCourseList;
	TextView tv_underLine_relevant,tv_underLine_comment,tv_underLine_catalogue;
	ArrayList<Fragment> list = new ArrayList<Fragment>();
	Context context;
	public Fragment_TotalCourseNews(Context context) {
		this.context = context;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_total_coursenews, container,false);
		viewPager = (ViewPager) view.findViewById(R.id.vg_totalcourse);
		tv_underLine_catalogue = (TextView) view.findViewById(R.id.tv_underLine_catalogue);
		tv_underLine_comment = (TextView) view.findViewById(R.id.tv_underLine_comment);
		tv_underLine_relevant = (TextView) view.findViewById(R.id.tv_underLine_relevant);

		fragmentComment = new Fragment_Comment();
		fragmentCourseList = new Fragment_CourseList();
		fragmentRelevantCourse = new Fragment_RelevantCourse();
		
		list.add(fragmentCourseList);
		list.add(fragmentComment);
		list.add(fragmentRelevantCourse);
		
		viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), list));
		viewPager.setCurrentItem(0);
		
		setBackgroundWhite();
		tv_underLine_catalogue.setBackgroundColor(Color.parseColor("#4B0082"));
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:

					setBackgroundWhite();
					tv_underLine_catalogue.setBackgroundColor(Color.parseColor("#4B0082"));
					break;
				case 1:
					setBackgroundWhite();
					tv_underLine_comment.setBackgroundColor(Color.parseColor("#4B0082"));
					break;
				case 2:
					setBackgroundWhite();
					tv_underLine_relevant.setBackgroundColor(Color.parseColor("#4B0082"));
					break;
				default:
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		return view;

	}
	private void setBackgroundWhite(){
		tv_underLine_catalogue.setBackgroundColor(color.white);
		tv_underLine_comment.setBackgroundColor(color.white);
		tv_underLine_relevant.setBackgroundColor(color.white);
		tv_underLine_catalogue.setOnClickListener(this);
		tv_underLine_comment.setOnClickListener(this);
		tv_underLine_relevant.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {


		case R.id.tv_underLine_catalogue:
			selectItem(0);
			v.setBackgroundColor(Color.parseColor("#4B0082"));
			break;
		case R.id.tv_underLine_comment:
			selectItem(1);
			v.setBackgroundColor(Color.parseColor("#4B0082"));
			break;
		case R.id.tv_underLine_relevant:
			selectItem(2);
			v.setBackgroundColor(Color.parseColor("#4B0082"));
			
			break;
		default:
			break;
		}

	}
	private void selectItem(int i){
		setBackgroundWhite();
		viewPager.setCurrentItem(i);
	}

}
