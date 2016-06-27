package com.orcs.boomshakalaka.activity.library;

import android.os.Bundle;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.activity.BaseActivity;
import com.orcs.boomshakalaka.adapter.ExpanableListViewAdapter;
import com.orcs.boomshakalaka.entity.ActualResp;
import com.orcs.boomshakalaka.ui.expanablelistview.ActionSlideExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/8.
 */
public class ExpanableListViewActivity extends BaseActivity {
    private List<Map<String, Object>> mList;
    private ActionSlideExpandableListView mActionSlideExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanable_listview);
        mActionSlideExpandableListView = (ActionSlideExpandableListView) findViewById(R.id.expandable_lv);

        //临时用测试数据
        mList = new ArrayList<>();
        ActualResp actualResp = new ActualResp("胡帅这样的男人太帅气了", "这是实话");
        Map<String, Object> map = new HashMap<>();
        map.put("ActualResp", actualResp);
        mList.add(map);
        mList.add(map);
        ExpanableListViewAdapter adapter = new ExpanableListViewAdapter(ExpanableListViewActivity.this, mList);
        mActionSlideExpandableListView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
