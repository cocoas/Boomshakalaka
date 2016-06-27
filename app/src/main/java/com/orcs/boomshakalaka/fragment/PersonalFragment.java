package com.orcs.boomshakalaka.fragment;

import android.view.View;
import android.widget.Toast;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.bridge.NotifyRefreshInterface;


/**
 * Created by Administrator on 2016/6/2.
 */
public class PersonalFragment extends BaseFragment implements NotifyRefreshInterface {
    @Override
    protected int setLayout() {
        return R.layout.fragment_personal;
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
        Toast.makeText(mContext,"刷新个人中心",Toast.LENGTH_SHORT).show();
    }
}
