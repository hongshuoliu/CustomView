package com.liu.custom.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    private final String TAG = "MainActivity";

    private Button btnCustomview;
    private Button btnFlow;
    private Button btnHonScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){

        btnCustomview = (Button) findViewById(R.id.btn_customview);
        btnCustomview.setOnClickListener(this);
        btnFlow = (Button) findViewById(R.id.btn_flow);
        btnFlow.setOnClickListener(this);
        btnHonScroll = (Button) findViewById(R.id.btn_hon_scroll);
        btnHonScroll.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_customview:
                intent = new Intent(this,CustomViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_flow:
                intent = new Intent(this,FlowLayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_hon_scroll:
                intent = new Intent(this,HonTabActivity.class);
                startActivity(intent);
                break;
            default:
                ;
        }
    }

}
