package com.orcs.boomshakalaka.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orcs.boomshakalaka.bridge.singleChoiceDialogInterface;


/**
 * Created by Administrator on 2016/6/7.
 * 暂时用的基类Activity
 */
public class BaseActivity extends AppCompatActivity implements singleChoiceDialogInterface {


    //设置单选的数据
    @Override
    public void setChoice(String item) {

    }

    /**
     * 自定义的标题栏的返回按钮的功能代码
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

}
