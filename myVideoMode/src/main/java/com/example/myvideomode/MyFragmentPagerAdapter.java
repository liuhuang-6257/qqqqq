package com.example.myvideomode;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class MyFragmentPagerAdapter extends FragmentPagerAdapter {
private ArrayList<Fragment> listFragments;

public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> al) {
super(fm);
listFragments = al;
}

public MyFragmentPagerAdapter(FragmentManager fm) {
super(fm);
}

@Override
public Fragment getItem(int position) {
return listFragments.get(position);
}

@Override
public int getCount() {
return listFragments.size();
}

@Override
public int getItemPosition(Object object) {
return super.getItemPosition(object);
}
}
