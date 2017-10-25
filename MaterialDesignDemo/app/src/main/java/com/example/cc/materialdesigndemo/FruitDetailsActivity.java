package com.example.cc.materialdesigndemo;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class FruitDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_details);
        initView();
//        initView1();
    }

    /*private void initView1() {
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra("fruitName");
        int fruitResId = intent.getIntExtra("fruitResId", -1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
        TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(fruitName);
        Glide.with(this).load(fruitResId).into(fruitImageView);
        String fruitContent = generateFruitName(fruitName);
        fruitContentText.setText(fruitContent);
    }*/

    private void initView() {
        Intent intent = getIntent();
        final String fruitName = intent.getStringExtra("fruitName");
        int fruitResId = intent.getIntExtra("fruitResId", -1);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingbar);
        //水果详情中的照片
        ImageView iv_fruit = (ImageView) findViewById(R.id.iv_fruit);
        //水果的详情描述
        TextView tv_fruit_des = (TextView) findViewById(R.id.tv_fruit_des);
        //悬浮按钮
        FloatingActionButton floatingbtn = (FloatingActionButton) findViewById(R.id.floatingbtn);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //设置标题栏为toolbar
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            //设置左上角home默认箭头图标显示
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            Log.d("xxx","DisplayShowHomeEnabled(true)");
        }
        //设置标题栏的标题，用collapsingToolbarLayout
        collapsingToolbarLayout.setTitle(fruitName);
        //给详情页面赋值
        Glide.with(this).load(fruitResId).into(iv_fruit);
        tv_fruit_des.setText(generateFruitName(fruitName));
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FruitDetailsActivity.this,fruitName+"详情页面来评论啦",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String generateFruitName(String fruitName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            stringBuilder.append(fruitName);
        }
        return stringBuilder.toString();
    }
    //对toolbar中的action事件进行监听

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //左上角的返回键
            case android.R.id.home:
                finish();//关闭本页面返回上一页
                break;
            default:
                break;
        }
        return true;
    }
}
