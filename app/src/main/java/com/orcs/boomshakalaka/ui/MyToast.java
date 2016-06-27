package com.orcs.boomshakalaka.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/6.
 */
public class MyToast {
    private static Toast sToast;

    private MyToast(Context context,String msg){
        if (sToast == null){
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else {
            sToast.setText(msg);
        }
    }

    public static void showToast(Context context,String msg){
        new MyToast(context,msg);
        sToast.show();
    }
}
