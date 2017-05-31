package com.liu.custom.customview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liu.custom.customview.bean.Banner;
import com.liu.custom.customview.wight.BannerLayout;
import com.liu.custom.customview.wight.HorizontalTabLayout;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends Activity {

    private BannerLayout mBanner;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        List<Banner> list = new ArrayList<>();
        list.add(new Banner("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg","施工进度骚年"));
        list.add(new Banner("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png","是多少房"));
        list.add(new Banner("https://assets.materialup.com/uploads/05e9b7d9-ade2-4aed-9cb4-9e24e5a3530d/preview.jpg","十渡"));
        list.add(new Banner("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png","啥光电开关那块是"));
        list.add(new Banner("http://pic.58pic.com/58pic/12/46/13/03B58PICXxE.jpg","所发生的"));
        list.add(new Banner("http://www.jitu5.com/uploads/allimg/121120/260529-121120232T546.jpg","发顺丰"));
        list.add(new Banner("http://pic34.nipic.com/20131025/2531170_132447503000_2.jpg","商厦啊"));
        list.add(new Banner("http://pic51.nipic.com/file/20141023/2531170_115622554000_2.jpg","啊大  昂生"));

        mBanner = (BannerLayout) findViewById(R.id.banner);
        mBanner.setDataList(list);


        String url = "https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg";
        mImage = (ImageView) findViewById(R.id.image_banner);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBanner.destroy();
    }
}
