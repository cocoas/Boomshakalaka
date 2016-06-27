package com.orcs.boomshakalaka.activity.library;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.activity.BaseActivity;
import com.orcs.boomshakalaka.ui.MyToast;
import com.orcs.boomshakalaka.widget.SingleChoiceDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 * 与Dialog有关的Activity
 */
public class DialogWidgetActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatTextView danliDialogSetATV;
    //AlertDialog构造器
    private AlertDialog.Builder builder;

    private int mSelectedItem = 0;

    ArrayList<Integer> MultiChoiceID = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_widget);

        //实例化控件
        AppCompatButton simpleDialog = (AppCompatButton) findViewById(R.id.btn_simple_dialog);
        AppCompatButton simpleListDialog = (AppCompatButton) findViewById(R.id.btn_simple_list_dialog);
        AppCompatButton singleChoiceDialog = (AppCompatButton) findViewById(R.id.btn_single_choice_dialog);
        AppCompatButton multiChoiceDialog = (AppCompatButton) findViewById(R.id.btn_multi_choice_dialog);
        AppCompatButton customeAdapterDialog = (AppCompatButton) findViewById(R.id.btn_custom_adapter_dialog);
        AppCompatButton customViewDialog = (AppCompatButton) findViewById(R.id.btn_custom_view_dialog);
        AppCompatButton danliDialogABT = (AppCompatButton) findViewById(R.id.btn_danli_dialog);
        danliDialogSetATV = (AppCompatTextView) findViewById(R.id.atv_show_danli_choice);
        //监听点击事件
        simpleDialog.setOnClickListener(this);
        simpleListDialog.setOnClickListener(this);
        singleChoiceDialog.setOnClickListener(this);
        multiChoiceDialog.setOnClickListener(this);
        customeAdapterDialog.setOnClickListener(this);
        customViewDialog.setOnClickListener(this);
        danliDialogABT.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_simple_dialog:
                showSimpleDialog();
                break;
            case R.id.btn_simple_list_dialog:
                showSimpleListDialog();
                break;
            case R.id.btn_single_choice_dialog:
                showSingleChoiceDialog();
                break;
            case R.id.btn_multi_choice_dialog:
                showMultiChoiceDialog();
//                showMultiChoiceDialog2();
                break;
            case R.id.btn_custom_adapter_dialog:
                showCustomerAdapter();
                break;
            case R.id.btn_custom_view_dialog:
                showCustomViewDialog();
                break;
            case R.id.btn_danli_dialog:
                final String[] items = {"Items-one", "Items-two", "Items-three"};
                SingleChoiceDialog.getInstance(DialogWidgetActivity.this).showSingleChoiceDialog(items, DialogWidgetActivity.this);
            default:

                break;
        }
    }

    /**
     * 基本对话框创建
     */
    private void showSimpleDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_dialog);
        builder.setMessage(R.string.dialog_message);

        builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.toast_positive, Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), R.string.toast_negative, Toast.LENGTH_SHORT).show();

            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 简单列表项对话框
     */
    private void showSimpleListDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_list_dialog);

        final String[] items = {"Items-one", "Items-two", "Items-three"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyToast.showToast(DialogWidgetActivity.this, "You clicked " + items[which]);
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 单选列表对话框
     */
    private void showSingleChoiceDialog() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            builder = new AlertDialog.Builder(this, R.style.MyDialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_list_dialog);

        final String[] items = {"Items-one", "Items-two", "Items-three"};
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogWidgetActivity.this.mSelectedItem = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyToast.showToast(DialogWidgetActivity.this, "You clicked "
                        + items[DialogWidgetActivity.this.mSelectedItem]);
            }
        });
        builder.setNeutralButton("Middle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", null);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 多选列表对话框
     */
    private void showMultiChoiceDialog() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            builder = new AlertDialog.Builder(this, R.style.MyDialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.simple_list_dialog);

        final String[] items = {"Items-one", "Items-two", "Items-three", "Items-four", "Items-five", "Items-six", "Items-seven"};
        final boolean[] selected = new boolean[]{false, false, false, false, false, false, false};
        builder.setMultiChoiceItems(items,
                null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selected[which] = isChecked;
                    }
                });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder selectedStr = new StringBuilder("");
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        selectedStr = selectedStr.append(items[i]);
                    }
                }
                Toast.makeText(DialogWidgetActivity.this, selectedStr, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyToast.showToast(DialogWidgetActivity.this, "You clicked DISLIKE");
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
        //修改dialog的高度和宽度，注意：必须在dialog.show()方法之后
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.6);
        p.width = (int) (d.getWidth() * 0.9);
        p.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(p);
    }

    private void showMultiChoiceDialog2() {

        final String[] items = {"Items-one", "Items-two", "Items-three", "Items-four", "Items-five", "Items-six", "Items-seven"};
        boolean[] checkeds = new boolean[items.length];
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            builder = new AlertDialog.Builder(this, R.style.MyDialog);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        MultiChoiceID.clear();
        builder.setTitle("公告范围");
        builder.setMultiChoiceItems(items,
                null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            MultiChoiceID.add((Integer) which);
                        } else {
                            MultiChoiceID.remove((Integer) which);
                        }
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder str = new StringBuilder("");
                for (int i = 0; i < MultiChoiceID.size(); i++) {
                    str = str.append(items[MultiChoiceID.get(i)]).append(",");
                }
                Toast.makeText(DialogWidgetActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.6);
        p.width = (int) (d.getWidth() * 0.9);
        p.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(p);
    }


    private void showCustomViewDialog() {
        builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle(R.string.custom_view_dialog);

        LinearLayout loginDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.customer_view, null);
        builder.setView(loginDialog);

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showCustomerAdapter() {
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.custom_view_dialog);

        List<ItemBean> items = new ArrayList<>();
        items.add(new ItemBean(R.mipmap.icon, "我是去年的两倍帅"));
        items.add(new ItemBean(R.mipmap.ic_launcher, "Android机器人"));
        CustomerAdapter adapter = new CustomerAdapter(items, DialogWidgetActivity.this);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyToast.showToast(DialogWidgetActivity.this, "You clicked " + which);
            }
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //单项选择的回调
    @Override
    public void setChoice(String item) {
        super.setChoice(item);
        danliDialogSetATV.setText(item);

    }

    private class CustomerAdapter extends BaseAdapter {
        private List<ItemBean> items;
        private LayoutInflater inflater;
        private ImageView image;
        private TextView text;

        public CustomerAdapter(List<ItemBean> items, Context context) {
            this.items = items;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.customer_adapter, null);
                image = (ImageView) convertView.findViewById(R.id.id_image);
                text = (TextView) convertView.findViewById(R.id.id_text);
            }
            image.setImageResource(items.get(position).getImageId());
            text.setText(items.get(position).getMessage());
            return convertView;
        }
    }

    private class ItemBean {
        private int imageId;
        private String message;

        public ItemBean(int imageId, String message) {
            this.imageId = imageId;
            this.message = message;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
