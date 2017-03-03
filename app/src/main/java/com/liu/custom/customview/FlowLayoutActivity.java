package com.liu.custom.customview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.custom.customview.wight.FlowGroupLayout;
import com.liu.custom.customview.wight.HorizontalTabLayout;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutActivity extends Activity {

    private Button btnAdd;
    private FlowGroupLayout flowLayout1;
    private FlowGroupLayout flowLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        List<String> list = new ArrayList<>();
        list.add("你好");
        list.add("苹果梨");
        list.add("梨的说法");
        list.add("橙子第三方");
        list.add("香蕉舒服多是");
        list.add("苹果水电费根据");
        list.add("梨大商股份看电视");
        list.add("橙子购买数量吗是施");
        list.add("香蕉是否能打死你个是");
        list.add("苹果公司概况第三方打算");
        list.add("梨第三个男的说你少年宫看");
        list.add("橙子");
        list.add("香蕉的所发生的");
        list.add("你好第三方");
        list.add("苹果是的范德萨水电费");
        list.add("你好水电费是");
        list.add("苹果沙发沙发上");
        list.add("你好爽肤水是方式");


        flowLayout1 = (FlowGroupLayout) findViewById(R.id.flow_layout1);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                flowLayout1.addData("Hello World");
            }
        });


        flowLayout1.setDataList(list);
        flowLayout1.setOnItemClickListener(new FlowGroupLayout.ItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Toast.makeText(getApplicationContext(),"index："+position,Toast.LENGTH_SHORT).show();
            }
        });


        flowLayout2 = (FlowGroupLayout) findViewById(R.id.flow_layout2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,30,10);
        params.gravity = Gravity.CENTER_VERTICAL;
        for (int i=0;i<list.size();++i){
            final  int pos = i;
            TextView textView = new TextView(this);
            textView.setText(list.get(i));
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundColor(Color.GRAY);
            textView.setPadding(10,0,10,0);
            //textView.setMaxEms(6);
            textView.setSingleLine(true);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"index："+pos, Toast.LENGTH_SHORT).show();
                }
            });
            flowLayout2.addView(textView,params);
        }

    }
}
