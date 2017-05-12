package com.liu.custom.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    private final String TAG = "MainActivity";

    private LinearLayout mActivityMain;
    private Button mBtnCustomview;
    private Button mBtnFlow;
    private Button mBtnHonScroll;
    private Button mBtnStaticView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //核心代码.
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

			//给状态栏设置颜色。我设置透明。
			window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
		}*/
    }

    private void initView(){


        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mBtnCustomview = (Button) findViewById(R.id.btn_customview);
        mBtnFlow = (Button) findViewById(R.id.btn_flow);
        mBtnHonScroll = (Button) findViewById(R.id.btn_hon_scroll);
        mBtnStaticView = (Button) findViewById(R.id.btn_static_view);

        mBtnCustomview.setOnClickListener(this);
        mBtnFlow.setOnClickListener(this);
        mBtnHonScroll.setOnClickListener(this);
        mBtnStaticView.setOnClickListener(this);
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
            case R.id.btn_static_view:
                intent = new Intent(this,StatscsActivity.class);
                startActivity(intent);
                break;
            default:
                ;
        }
    }

}
