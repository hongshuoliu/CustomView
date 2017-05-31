package com.liu.custom.customview.wight.circlemenu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liu.custom.customview.R;

import java.util.List;

/**
 * Created by jiemi-server on 2017/5/24.
 */

public class CircleMenuAdapter extends BaseAdapter {
    List<MenuItem> mMenuItems ;

    public CircleMenuAdapter(List<MenuItem> menuItes){
        mMenuItems = menuItes;
    }

    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mMenuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.activity_banner,parent,false);

        /*ImageView image = (ImageView) itemView.findViewById(R.id.activity_custom_view);
        TextView title = (TextView) itemView.findViewById(R.id.activity_custom_view);*/

        return itemView;
    }

}
