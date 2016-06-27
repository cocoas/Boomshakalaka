package com.orcs.boomshakalaka.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.entity.ActualResp;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ExpanableListViewAdapter extends MyBaseAdapter<Map<String, Object>> {
    Context context;
    List<Map<String, Object>> list;

    public ExpanableListViewAdapter(Context context, List<Map<String, Object>> list) {
        super(context, list);
        this.context = context;
        this.list = list;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= View.inflate(context, R.layout.item_expanable_listview, null);
        }


        ActualResp actualResp = (ActualResp) list.get(position).get("ActualResp");

//        技术类型
        TextView actualType= ViewHolder.get(convertView,R.id.actual_type);
        actualType.setText(actualResp.getName());

//        活动时间
        TextView actualTime=ViewHolder.get(convertView,R.id.actual_time);
        actualTime.setText(actualResp.getName());
//        活动参与人员
        TextView actualPeople=ViewHolder.get(convertView,R.id.actual_people);
        actualPeople.setText(actualResp.getName());
//        客户参与人员
        TextView actualClientPeople=ViewHolder.get(convertView,R.id.actual_client_people);
        actualClientPeople.setText(actualResp.getName());
//        活动目的
        TextView actualClientGoal=ViewHolder.get(convertView,R.id.actual_client_goal);
        actualClientGoal.setText(actualResp.getName());
//活动内容
        TextView actualClientContent=ViewHolder.get(convertView,R.id.actual_client_content);
        actualClientContent.setText(actualResp.getName());
//        活动未完成的内容
        TextView actualClientNotContent=ViewHolder.get(convertView,R.id.actual_client_not_content);
        actualClientNotContent.setText(actualResp.getName());
//        活动周期
        TextView actualPeriod=ViewHolder.get(convertView,R.id.actual_period);
        actualPeriod.setText(actualResp.getName());
//        周期单位
        TextView actualUnit=ViewHolder.get(convertView,R.id.actual_unit);
        actualUnit.setText(actualResp.getName());
//备注
        TextView actialRemark=ViewHolder.get(convertView,R.id.actial_remark);
        actialRemark.setText(actualResp.getName());
        return convertView;
    }
}
