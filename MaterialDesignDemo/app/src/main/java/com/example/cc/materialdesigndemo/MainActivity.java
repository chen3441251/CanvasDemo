package com.example.cc.materialdesigndemo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cc.materialdesigndemo.adapter.MyRecyclerViewAdapter;
import com.example.cc.materialdesigndemo.bean.Fruit;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        //找出recyclerview控件
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //下拉刷新控件
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        //让左上角导航按钮显示出来，并且给导航按钮设置图标
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }
        navigationView.setCheckedItem(R.id.nav_firends);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerlayout.closeDrawers();
                return true;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "floatingActionButton click", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "恭喜已经撤销!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorred));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模拟访问网络获取数据
                refreshFruitData();
            }
        });
        initFruitData();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                //模拟访问网络获取数据
                refreshFruitData();
            }
        });

    }

    private void refreshFruitData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(4000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //随机组装数据
                        initFruitData();
                        //刷新数据
                        mMyRecyclerViewAdapter.notifyDataSetChanged();
                        //停止刷新隐藏swipeRefreshLayout控件
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }).start();
    }

    private void initFruitData() {
        //创建一个水果数组
        Fruit[] fruits = {new Fruit("菠萝", R.mipmap.boluo), new Fruit("草莓", R.mipmap.caomei), new Fruit("橙子", R.mipmap.chengzi),
                new Fruit("蛋糕", R.mipmap.dangao), new Fruit("桔子", R.mipmap.juzi), new Fruit("蓝莓", R.mipmap.lanmei), new Fruit("猕猴桃", R.mipmap.mihoutao),
                new Fruit("南瓜", R.mipmap.nangua), new Fruit("苹果", R.mipmap.pingguo), new Fruit("琵琶", R.mipmap.pipa), new Fruit("西瓜", R.mipmap.xigua),
                new Fruit("葡萄", R.mipmap.putao), new Fruit("沙拉", R.mipmap.sala), new Fruit("桑葚", R.mipmap.shangshen), new Fruit("桃子", R.mipmap.taozi)};
        ArrayList<Fruit> fruitList = new ArrayList<>();
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int i1 = random.nextInt(fruits.length);
            fruitList.add(fruits[i1]);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(fruitList);
        mRecyclerView.setAdapter(mMyRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home://左上角调起侧滑栏
                mDrawerlayout.openDrawer(GravityCompat.START);
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
