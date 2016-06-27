package com.orcs.boomshakalaka.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.activity.BaseActivity;


/**
 * Created by Administrator on 2016/6/11.
 * 单选框的单例
 */
public class SingleChoiceDialog {

    private static volatile SingleChoiceDialog instance;
    private Context mContext;
    public AlertDialog.Builder mBuilder;
    public AlertDialog mDialog;
    private BaseActivity mActivity;
    private int mSelectedItem = -1;

    private SingleChoiceDialog(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static SingleChoiceDialog getInstance(Context context) {
        if (instance == null) {
            synchronized (SingleChoiceDialog.class) {
                if (instance == null) {
                    instance = new SingleChoiceDialog(context);
                }
            }
        }
        return instance;
    }

    /**
     * @param items   选择的数据
     * @param context 传入的Activity
     */
    public void showSingleChoiceDialog(final String[] items, final Context context) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mActivity = (BaseActivity) context;
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.KITKAT_WATCH){
            mBuilder = new AlertDialog.Builder(mActivity, R.style.MyDialog);
        }else {
            mBuilder = new AlertDialog.Builder(mActivity);
        }
        mBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                SingleChoiceDialog.this.mSelectedItem = which;
            }
        });
        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //选中某一项确定后的操作
                if (mSelectedItem != -1) {
                    mActivity.setChoice(items[mSelectedItem]);
                }
            }
        });
        mBuilder.setNegativeButton("取消", null);
        mBuilder.setCancelable(true);
        mDialog = mBuilder.create();
        mDialog.show();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();
        p.width = (int) (d.getWidth() * 0.9);
        p.gravity = Gravity.CENTER;
        mDialog.getWindow().setAttributes(p);
    }


}
