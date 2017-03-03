package com.liu.custom.customview;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liu.custom.customview.wight.HorizontalTabLayout;

import java.util.ArrayList;
import java.util.List;

public class HonTabActivity extends Activity {

    private HorizontalTabLayout scrollLayout;
    private Button btnAdd;
    private ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hon_tab);

        List<String> list = new ArrayList<>();
        list.add("你好");
        list.add("苹果梨");
        list.add("梨的说法");
        list.add("橙子第三方");
        list.add("香蕉舒服多是");
        list.add("苹果水电费根据");
        list.add("梨大商股份看电视");

        scrollLayout = (HorizontalTabLayout) findViewById(R.id.scroll_layout);
        scrollLayout.setDataList(list);
        scrollLayout.selectTab(2);
        scrollLayout.setOnTabClickListener(new HorizontalTabLayout.OnTabClickListener() {
            @Override
            public void OnTabClick(View view, int position) {

                Toast.makeText(getApplicationContext(),"index："+position, Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd = (Button) findViewById(R.id.btn_add);


        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }
}
