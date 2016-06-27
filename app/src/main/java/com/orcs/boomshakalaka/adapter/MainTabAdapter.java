package com.orcs.boomshakalaka.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orcs.boomshakalaka.R;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 * 主界面信息展示的adapter
 */
public class MainTabAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mName;
    private List<Integer> mIcon;

    public MainTabAdapter(Context context, List<String> name, List<Integer> icon) {
        mContext = context;
        mName = name;
        mIcon = icon;
    }

    @Override
    public int getCount() {
        return mName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_info_center, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemIV = (ImageView) view.findViewById(R.id.communicate_item_iv);
            viewHolder.mItenmTV = (TextView) view.findViewById(R.id.communicate_item_tv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mItemIV.setImageResource(mIcon.get(position));
        viewHolder.mItenmTV.setText(mName.get(position));
        return view;
    }

    class ViewHolder {
        ImageView mItemIV;
        TextView mItenmTV;

    }

}
