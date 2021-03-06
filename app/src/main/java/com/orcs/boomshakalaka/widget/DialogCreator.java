package com.orcs.boomshakalaka.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.utils.SizeUtils;
import com.orcs.boomshakalaka.widget.progressbar.MaterialProgressBar;


/**
 * Created by Administrator on 2016/6/7.
 *
 */
public class DialogCreator {
    public static enum Type {
        PROGRESS, ALARM, NORMAL
    }

    public static enum Position {
        BOTTOM, CENTER, TOP
    }

    public DialogCreator() {
    }

    public static Dialog createNormalDialog(Context context, View view, Position position) {
        int style = 0;
        switch (position) {
            case BOTTOM:
                style = R.style.BottomDialog;
                break;
            case CENTER:
                style = R.style.CenterDialog;
                break;
            case TOP:
                style = R.style.TopDialog;
                break;
        }
        Dialog dialog = new Dialog(context, style);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        switch (position) {
            case BOTTOM:
                params.gravity = Gravity.BOTTOM;
                break;
            case CENTER:
                params.gravity = Gravity.CENTER;
                break;
            case TOP:
                params.gravity = Gravity.TOP;
                break;
        }
        params.width = SizeUtils.getScreenWidth(context);
        dialog.getWindow().setAttributes(params);
        return dialog;
    }

    public static Dialog createProgressDialog(Context context, Position position, int color, String message, boolean cancellable) {
        int style = 0;
        View view = null;
        switch (position) {
            case BOTTOM:
                style = R.style.BottomDialog;
                view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null, false);
                break;
            case CENTER:
                style = R.style.CenterDialog;
                view = LayoutInflater.from(context).inflate(R.layout.center_progress_dialog_layout, null, false);
                break;
            case TOP:
                style = R.style.TopDialog;
                view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null, false);
                break;
        }
        MaterialProgressBar progressBar = (MaterialProgressBar) view.findViewById(R.id.progress);
        progressBar.setBackgroundColor(color);
        TextView textView = (TextView) view.findViewById(R.id.message);
        textView.setText(message);

        Dialog dialog = new Dialog(context, style);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(cancellable);
        dialog.setCancelable(cancellable);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        switch (position) {
            case BOTTOM:
                params.gravity = Gravity.BOTTOM;
                break;
            case CENTER:
                params.gravity = Gravity.CENTER;
                break;
            case TOP:
                params.gravity = Gravity.TOP;
                break;
        }
        params.width = SizeUtils.getScreenWidth(context);
        dialog.getWindow().setAttributes(params);
        return dialog;
    }

}
