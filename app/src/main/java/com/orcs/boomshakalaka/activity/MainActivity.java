package com.orcs.boomshakalaka.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.fragment.CommunityFragment;
import com.orcs.boomshakalaka.fragment.LibraryFragment;
import com.orcs.boomshakalaka.fragment.PersonalFragment;
import com.orcs.boomshakalaka.fragment.SearchFragment;
import com.orcs.boomshakalaka.widget.DialogCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    HuLei
 * Version    V1.0
 * Date:      2016/6/3  18:18.
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2016/6/3         HuLei                1.0                1.0
 * Why & What is modified:
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    //Tab页面代号
    public static final int TAB_COMMUNITY = 0;
    public static final int TAB_LIBRARY = 1;
    public static final int TAB_SEARCH = 2;
    public static final int TAB_PERSONAL = 3;

    private long mLastClickBack = 0;

    private ViewPager mViewPager;
    private RadioButton mTabCommunity;
    private RadioButton mTabLibrary;
    private RadioButton mTabSearch;
    private RadioButton mTabPersonal;

    //抽象组件
    private ViewPagerAdapter mPagerAdapter;
    private CommunityFragment mCommunityFragment;
    private LibraryFragment mLibraryFragment;
    private SearchFragment mSearchFragment;
    private PersonalFragment mPersonalFragment;
    private List<Fragment> mFragmentList;

    private Dialog mUpdate;
    private TextView mText;
    private Button mCancel;
    private Button mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ViewPager
        mViewPager = (ViewPager) findViewById(R.id.content_container);

        //Tab
        mTabCommunity = (RadioButton) findViewById(R.id.tab_community);
        mTabLibrary = (RadioButton) findViewById(R.id.tab_library);
        mTabPersonal = (RadioButton) findViewById(R.id.tab_personal);
        mTabSearch = (RadioButton) findViewById(R.id.tab_search);

        //view
        mTabCommunity.setOnClickListener(this);
        mTabLibrary.setOnClickListener(this);
        mTabSearch.setOnClickListener(this);
        mTabPersonal.setOnClickListener(this);

        //初始化主界面4个fragment
        mFragmentList = new ArrayList<>();
        mCommunityFragment = new CommunityFragment();
        mLibraryFragment = new LibraryFragment();
        mSearchFragment = new SearchFragment();
        mPersonalFragment = new PersonalFragment();
        mFragmentList.add(mCommunityFragment);
        mFragmentList.add(mLibraryFragment);
        mFragmentList.add(mSearchFragment);
        mFragmentList.add(mPersonalFragment);

        View updateView = LayoutInflater.from(this).inflate(R.layout.update_dialog, null, false);
        mUpdate = DialogCreator.createNormalDialog(this, updateView, DialogCreator.Position.CENTER);
        mText = (TextView) updateView.findViewById(R.id.title_text);
        mCancel = (Button) updateView.findViewById(R.id.cancel);
        mConfirm = (Button) updateView.findViewById(R.id.confirm);

        //显示更新对话框
//        mUpdate.show();

        //初始化主界面的ViewPagerAdapter
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);

        //使主界面的ViewPager不销毁、不重建
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.setAdapter(mPagerAdapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handlePageChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 处理ViewPager换页，ui元素变化，如appBar和tabBar
     *
     * @param index 页面索引号
     */
    private void handlePageChange(int index) {
        switch (index) {
            case TAB_COMMUNITY:
                mTabCommunity.setChecked(true);
                break;

            case TAB_LIBRARY:
                mTabLibrary.setChecked(true);
                break;
            case TAB_SEARCH:
                mTabSearch.setChecked(true);
                break;

            case TAB_PERSONAL:
                mTabPersonal.setChecked(true);

                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int currentPage = mViewPager.getCurrentItem();
        switch (v.getId()) {
            case R.id.tab_community:
                if (currentPage == TAB_COMMUNITY) {
                    mCommunityFragment.onNotify();
                }
                mViewPager.setCurrentItem(TAB_COMMUNITY);
                break;

            case R.id.tab_library:
                mViewPager.setCurrentItem(TAB_LIBRARY);
                break;
            case R.id.tab_search:
                mViewPager.setCurrentItem(TAB_SEARCH);
                break;

            case R.id.tab_personal:
                if (currentPage == TAB_PERSONAL) {
                    mPersonalFragment.onNotify();
                }
                mViewPager.setCurrentItem(TAB_PERSONAL);
                break;

            default:
                break;
        }
    }


    /**
     * 主界面ViewPager的Adapter
     */
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

    @Override
    public void onBackPressed() {
        long current = System.currentTimeMillis();
        if (current - mLastClickBack > 2 * 1000) {
            mLastClickBack = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }

}
