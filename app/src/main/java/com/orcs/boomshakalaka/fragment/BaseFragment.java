package com.orcs.boomshakalaka.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by lei on 2016/6/2.
 * Base Class for the fragment
 */
public abstract class BaseFragment extends Fragment {

    protected View mFragmentView;
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(setLayout(), container, false);
        findViews(mFragmentView);
        initialization();
        bindEvent();
        onCreateView();
        return mFragmentView;
    }

    /**
     * Method to set the layout
     *
     * @return The layout resources id of the file
     */
    protected abstract int setLayout();

    /**
     * Do find views in onCreateView
     *
     * @param rootView
     */
    protected abstract void findViews(View rootView);

    /**
     * Do some initialization in onCreateView()
     */
    protected abstract void initialization();

    /**
     * Do bind event in onCreateView()
     */
    protected abstract void bindEvent();

    /**
     * The main job should be done here
     */
    protected abstract void onCreateView();

    /**
     * Hide the soft keyboard
     *
     * @param view One of the views in the current window
     */
    protected void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Show the soft keyboard
     *
     * @param view One of the views in the current window
     */
    protected void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
        imm.showSoftInput(view, 0);
    }

    /**
     * Start an activity with specify Bundle
     *
     * @param paramClass  The activity class you want to start
     * @param paramBundle The bundle you want to put in the intent
     */
    protected void openActivityWithBundle(Class<?> paramClass, Bundle paramBundle) {
        Intent intent = new Intent(mContext, paramClass);
        if (paramBundle != null) {
            intent.putExtras(paramBundle);
        }
        startActivity(intent);
    }

}
