package com.orcs.boomshakalaka.activity.library;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.activity.BaseActivity;
import com.orcs.boomshakalaka.fragment.NewsFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/3.
 * 资讯中心预览
 */
public class InfoCenterActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;


    private String[] titles = {"企业新闻", "产品展示", "工程展示", "政府政策"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infocenter);
        initFragments();
        initViewPager();
        initTabLayout();
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        for (String title : titles) {
            mFragments.add(NewsFragment.getInstance(title));
        }
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(4);
        }
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),  mFragments,titles));

    }

    private void initTabLayout(){
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(mViewPager);
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments = new ArrayList<>();
        private String[] mTitles;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }


    }

}
