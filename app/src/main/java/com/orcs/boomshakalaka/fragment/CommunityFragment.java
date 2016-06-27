package com.orcs.boomshakalaka.fragment;

import android.view.View;
import android.widget.Toast;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.bridge.NotifyRefreshInterface;


/**
 * Created by Administrator on 2016/6/2.
 */
public class CommunityFragment extends BaseFragment implements NotifyRefreshInterface {


    @Override
    protected int setLayout() {
        return R.layout.fragment_community;
    }

    @Override
    protected void findViews(View view) {

    }

    @Override
    protected void initialization() {

    }

    @Override
    protected void bindEvent() {

    }

    @Override
    protected void onCreateView() {

    }

    @Override
    public void onNotify() {
        Toast.makeText(mContext, "开始刷新咯", Toast.LENGTH_SHORT).show();
    }


}
