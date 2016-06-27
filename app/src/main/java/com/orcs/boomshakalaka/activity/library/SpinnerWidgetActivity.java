package com.orcs.boomshakalaka.activity.library;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.orcs.boomshakalaka.R;
import com.orcs.boomshakalaka.activity.BaseActivity;
import com.orcs.boomshakalaka.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/24.
 * spinner 控件 的 展示
 */
public class SpinnerWidgetActivity extends BaseActivity {

    private Spinner titleSpinner;
    private ArrayAdapter<String> titleSpinnerAdapter;

    private Spinner spinner;
    private Spinner spinnerTwo;

    private String[] datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_widget);


        //标题栏的spinner
        titleSpinner = (Spinner) findViewById(R.id.view_title_spinner);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("湖南视拓科技股份有限公司" + i);
        }
        titleSpinnerAdapter = new ArrayAdapter<String>(SpinnerWidgetActivity.this,
                R.layout.my_simple_spinner_self_item, data);
        titleSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(titleSpinnerAdapter);
        titleSpinner.setSelection(0);
        titleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Spinner在初始化时会自动调用一次OnItemSelectedListener事件
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getId() == R.id.view_title_spinner) {
                    MyToast.show(SpinnerWidgetActivity.this, titleSpinnerAdapter.getItem(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerTwo = (Spinner) findViewById(R.id.spinnerTwo);

        datas = new String[]{"张三","李四","王五","赵六"};
        //原生态样式,已android.R.layout.simple_spinner_dropdown_item为例，其他修改类似
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, datas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //根据原生态样式改变而来的自定义样式
        //Spinner中文框显示样式
        ArrayAdapter<String> adapterTwo = new ArrayAdapter<String>(this,
                R.layout.my_simple_spinner_self_item2, datas);
        //Spinner下拉菜单显示样式
        adapterTwo
                .setDropDownViewResource(R.layout.my_simple_spinner_dropdown_item);
        spinnerTwo.setAdapter(adapterTwo);
    }
}
