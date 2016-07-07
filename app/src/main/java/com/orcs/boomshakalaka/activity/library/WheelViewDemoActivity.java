package com.orcs.boomshakalaka.activity.library;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.activity.BaseActivity;
import com.orcs.boomshakalaka.ui.WheelView;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/6/27.
 * WheelView DEMO
 */
public class WheelViewDemoActivity extends BaseActivity
        implements View.OnClickListener {

    private static final String TAG = WheelViewDemoActivity.class.getSimpleName();

    private static final String[] PLANETS =
            new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheelview_demo);

        findViewById(R.id.wheelview_show_dialog_btn).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wheelview_show_dialog_btn:
                View outerView = LayoutInflater.from(this).inflate(R.layout.view_wheel, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                wv.setOffset(1);
                wv.setItems(Arrays.asList(PLANETS));
                wv.setSeletion(1);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        super.onSelected(selectedIndex, item);
                        Log.d(TAG, "selectedIndex: " + selectedIndex + ",------item:" + item);
                    }
                });

                new AlertDialog.Builder(this)
                        .setTitle("WheelView in Dialog")
                        .setView(outerView)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .show();
                break;
            default:
                break;
        }
    }


}
