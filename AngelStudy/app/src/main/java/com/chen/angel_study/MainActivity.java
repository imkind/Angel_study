package com.chen.angel_study;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.angel_study.Activitys.BaseActivity;
import com.chen.angel_study.Activitys.PlanDetailActivity;
import com.chen.angel_study.Activitys.RecyclerViewAdapter;
import com.chen.angel_study.Listen.MyItemTouchHelperCallback;
import com.chen.angel_study.Manager.ClockManager;
import com.chen.angel_study.Manager.PlanManager;

import com.chen.angel_study.util.Toasts;




import butterknife.BindView;

public class MainActivity extends BaseActivity  {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;
    private PlanManager mPlanManger = PlanManager.getInstance();
    private ClockManager mClockManager = ClockManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mAdapter = new RecyclerViewAdapter(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initData() {
        //设置数据源，适配器
        mAdapter.setDatabases(mPlanManger.findAll());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //滑动删除
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(mAdapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void setListener() {
        mAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    //菜单添加新的计划
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent intent = new Intent();
            intent.setClass(this, PlanDetailActivity.class);
            intent.putExtra(PlanDetailActivity.EXTRA_ADD_EVENT, true);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    private RecyclerViewAdapter.OnItemClickListener mOnItemClickListener = new RecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PlanDetailActivity.class);
                intent.putExtra(PlanDetailActivity.EXTRA_EDIT_EVENT, false);
                intent.putExtra(PlanDetailActivity.EXTRA_PLAN_DATA, mAdapter.getDatabases().get(position));
                startActivity(intent);
        }

        @Override
        public void onItemLongClick(View view, int position) {
            Toasts.showToast("Long clicked");
        }
    };

   //数据更新,singleTask模式
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mAdapter.setDatabases(mPlanManger.getPlans());
    }

}