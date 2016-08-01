package com.zzz.zhxdemo.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.zhxdemo.R;

import java.util.ArrayList;

public class NameListAdapter extends BaseAdapter{
	
	private ArrayList<String> mList;
	private Context context;
	
	public NameListAdapter(Context context, ArrayList<String> lists) {
		// TODO Auto-generated constructor stub
		this.mList = lists;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int id = position; 
		ViewHolder viewHolder;
		if(convertView != null){
			viewHolder = (ViewHolder)convertView.getTag();
		}else{
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_name_list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView)convertView.findViewById(R.id.used_name);
			viewHolder.delBtn = (ImageView)convertView.findViewById(R.id.used_name_del_btn);
			convertView.setTag(viewHolder);
		}
		viewHolder.text.setText(mList.get(position));
		viewHolder.delBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mList.remove(id);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	static class ViewHolder {
		TextView text;
		ImageView delBtn;
	}
	

	

	

}
