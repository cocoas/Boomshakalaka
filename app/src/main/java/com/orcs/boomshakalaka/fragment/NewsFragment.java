package com.orcs.boomshakalaka.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orcs.boomshakalaka.R;


/**
 * Created by Administrator on 2016/6/3.
 */
public class NewsFragment extends BaseFragment{

    private static final String ARG_TITLE = "title";
    private TextView mTagTV;

    public static NewsFragment getInstance(String title) {
        NewsFragment fra = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void findViews(View rootView) {
        mTagTV = (TextView) rootView.findViewById(R.id.text);
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
}
