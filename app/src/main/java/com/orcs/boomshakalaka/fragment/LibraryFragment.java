package com.orcs.boomshakalaka.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.activity.library.DialogWidgetActivity;
import com.orcs.boomshakalaka.activity.library.ExpanableListViewActivity;
import com.orcs.boomshakalaka.activity.library.InfoCenterActivity;
import com.orcs.boomshakalaka.activity.library.SpinnerWidgetActivity;
import com.orcs.boomshakalaka.adapter.MainTabAdapter;
import com.orcs.boomshakalaka.ui.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 * 知识库
 */
public class LibraryFragment extends BaseFragment {

    //资讯中心
    private MyGridView mInfoCenterMGV;
    private List<String> infoNames;
    private List<Integer> infoIcons;
    private MainTabAdapter mTabAdapter;

    //控件
    private MyGridView mWidgetStustyMGV;
    private List<String> widgetNames;
    private List<Integer> widgetIcons;
    private MainTabAdapter widgetStudyAdapter;


    @Override
    protected int setLayout() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void findViews(View rootView) {
        mInfoCenterMGV = (MyGridView) rootView.findViewById(R.id.information_center_MGV);
        mWidgetStustyMGV = (MyGridView) rootView.findViewById(R.id.information_widgetStudy_MGV);

    }

    @Override
    protected void initialization() {

        infoNames = new ArrayList<>();
        infoNames.add("企业资讯");
        infoNames.add("IT资讯");
        infoIcons = new ArrayList<>();
        infoIcons.add(R.drawable.company_news);
        infoIcons.add(R.drawable.industry_news);
        mTabAdapter = new MainTabAdapter(mContext, infoNames, infoIcons);
        mInfoCenterMGV.setAdapter(mTabAdapter);

        widgetNames = new ArrayList<>();
        widgetNames.add("对话框");
        widgetNames.add("ListView");
        widgetNames.add("spinner");
        widgetIcons = new ArrayList<>();
        widgetIcons.add(R.drawable.contend);
        widgetIcons.add(R.drawable.contend);
        widgetIcons.add(R.drawable.contend);
        widgetStudyAdapter = new MainTabAdapter(mContext, widgetNames, widgetIcons);
        mWidgetStustyMGV.setAdapter(widgetStudyAdapter);
    }

    @Override
    protected void bindEvent() {
        mInfoCenterMGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, InfoCenterActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, InfoCenterActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

        mWidgetStustyMGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(mContext, DialogWidgetActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(mContext, ExpanableListViewActivity.class));
                        break;

                    case 2:
                        startActivity(new Intent(mContext, SpinnerWidgetActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreateView() {

    }
}
