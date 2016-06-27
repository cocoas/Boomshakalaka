package com.orcs.boomshakalaka.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 * 封装的供listView使用的adapter
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected Context context;

    protected OnMyListItemClick onMyListItemClick;

    public MyBaseAdapter(Context context, List<T> list) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    public T getItem(Object position) {
        return list.get(MyBaseAdapter.parseInt(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    /**
     * 添加数据
     *
     * @param list
     */
    public void addData(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 展现对话框
     **/
    public void showPhoneDialog(DialogInterface.OnClickListener click, String title, String[] str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(str, click);
        builder.create().show();
    }

    /**
     * 刷新列表数据
     *
     * @param list
     */
    public void refreshData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 获取列表数据
     */
    public List<T> getData() {
        return this.list;
    }

    /**
     * 清除界面数据
     */
    public void clearData() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public interface OnMyListItemClick {
        public void onClick(View v);
    }

    private AlertDialog dialog;

    public void setOnMyListItemClickListener(OnMyListItemClick onMyListItemClick) {
        this.onMyListItemClick = onMyListItemClick;
    }

    /**
     * 将object转换成int值，异常直接返回0
     *
     * @param str
     * @return
     */
    public static int parseInt(Object str) {
        int i;
        try {
            i = Integer.parseInt(String.valueOf(str));
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }
}
