package com.zzz.zhxdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghx2 on 2016/7/6.
 */
public class GuidePagerAdapter extends PagerAdapter {

    private List<View> views = new ArrayList<View>();

    public GuidePagerAdapter(List<View> lists) {
        // TODO Auto-generated constructor stub
        this.views = lists;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }
}
